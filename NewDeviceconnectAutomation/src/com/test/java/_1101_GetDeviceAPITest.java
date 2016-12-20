package com.test.java;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.ApplicationLibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 18 May 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have atlease one inuse and disabled iOS and Android devices
 * Admin user can only be used
 * 
 */


public class _1101_GetDeviceAPITest extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	/*private String[] arrDeviceStatus_Single = {"Available", "In Use", "Offline", "Disabled"};*/
	Boolean value;
	Object[] values = new Object[2];
	public Map<String,String> expectedResultMap= new HashMap<String,String>();
	public Map<String,String> actualResultMap = new HashMap<String,String>(); // Declaring a HashMap to store actual results key and values
	public Map<String,String> oldActualResultMap = new HashMap<String,String>();
	public Map<String,String> orgExpectedResultMap = new HashMap<String,String>();
	private String valueOfProp="";
	String deviceName = "";
	String deviceID =  "";
	Object[] Values = new Object[4]; 
	String  expDeviceID = "";
	String retainedByUserName= "";
	String DisabledeviceName = "";
	String DisabledeviceID="";
	String component= "Device";
	String parameterList="";
	String valueList="";
	String enabledDevice = "";	
	String OfflinedeviceName = "";
	String OfflinedeviceID="";
	/*private String[] arrDeviceStatus_Single = { "In Use", "Offline", "Disabled"};*/


	// Creating an array having all the model properties available for Application component
	String[] devModel ={"id","name","availability","retainedById","retainedByDisplayName","enabled","batteryStatus","batteryPercentCharged",
			"diskSpace","slotNumber","diskSpaceUsed","nextReservationStartTime","nextReservationEndTime","reservedById",
			"reservedByDisplayName","lastInuseAt","lastDisconnectedAt","lastConnectedAt","deleted","notes","retainedByUserName","model","operatingSystem",
			"operatingSystemVersion","macAddress","serialNumber","manufacturer","friendlyModel","vendorUniqueIdentifier","vendorDeviceName",
			"deviceClass","formFactor","productType","isAudioEnabled","hardwareModel","operatingSystemBuild"};//,"retainedByUserName"
	//Creating an array of all the query properties available for Application component
	String[] queryProp ={"id","name","slotNumber","enabled","deleted","notes","availability"}; //,"operatingSystem" 


	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("Get Device");
			isEventSuccessful = Login();

			String serverIP=dicCommon.get("ApplicationURL");

			//****************************Code to create expected Result Starts here *****************************************************************
			// Step 1 : login to deviceConnect with valid user credentials.
			//	isEventSuccessful = Login();


			String[] deviceType= {"Android","iOS"}; //Declaring an array to run for Android and iOS app
			/*This loop run for iOS first and then Android*/
			for(int j=0; j<=1; j++)
			{

				isEventSuccessful = selectPlatform(deviceType[j]);
				if(deviceType[j].equals("Android"))
				{
					isEventSuccessful = PerformAction("txtDeviceSearch", Action.Clear);
					PerformAction("Browser", Action.Refresh);
				}

				isEventSuccessful = selectStatus("Available");//Checking different statuses Available, InUse, Offline, Disabled
				String devicesErrorMsg=GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text");
				//System.out.println("no device:"+devicesErrorMsg);
				if(devicesErrorMsg.contains("No devices match your filter criteria.")){
					System.out.println("No Devices are their");
					apiReporter.apiErrorBlock("Devices not available with selected filters");
				}
				else{
					isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0]; //Navigating to first Available or Inuse, Offline Or Disabled DeviceDetails page
					String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
					deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];
					isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
					isEventSuccessful = PerformAction("eleSlotMousehover_deviceDetailsPage", Action.DoubleClick);
					isEventSuccessful = PerformAction( "eleEditSlot_deviceDetailsPage", Action.Clear);
					isEventSuccessful = PerformAction( "eleEditSlot_deviceDetailsPage", Action.sendkeys , "1011");
					isEventSuccessful = PerformAction("eleSlotSaveChangesButton_DeviceDetailsPage", Action.Click);
					isEventSuccessful = PerformAction("eleNoteMousehover_deviceDetailsPage", Action.DoubleClick);
					isEventSuccessful = PerformAction( "eleEditNotes_deviceDetailsPage", Action.Clear);
					isEventSuccessful = PerformAction( "eleEditNotes_deviceDetailsPage", Action.sendkeys , "Notes field ");	
					isEventSuccessful = PerformAction("eleNotesSaveChangesButton_DeviceDetailsPage", Action.Click);

					//Step 2: Getting expected Result map from application details page
					expectedResultMap = getDeviceExpectedMap(deviceID,deviceName,serverIP );
					//expectedResultMap.put("operatingSystem",deviceType[j]);

					orgExpectedResultMap=expectedResultMap;

					//Running the script for all the query properties available for Application component
					for (String propertyInUse: queryProp)
					{
						if (!((orgExpectedResultMap.get(propertyInUse).equals("Not Verified")) | (orgExpectedResultMap.get(propertyInUse).equals("None")) | (orgExpectedResultMap.get(propertyInUse).equals("null")))){
							valueOfProp=orgExpectedResultMap.get(propertyInUse);}
						else{
							if ((expectedResultMap.get(propertyInUse).equals("Not Verified")) & (oldActualResultMap.containsKey(propertyInUse))){ //If the property is "Not Verified". Get the value from oldActualResultMap
								valueOfProp=oldActualResultMap.get(propertyInUse);
							}
							else{
								valueOfProp=expectedResultMap.get(propertyInUse);
							}}
						if(valueOfProp.equals("null")){
							valueOfProp="";
						}
						//If value exists other than "Not Verified",use the value in API Request
						/*}*/

						//************************Code for getting actual result from API Request starts here *******************************************************************
						//adding heading in APIOverallComparison HTML  for the AppType, Property and Value used in API Request
						apiReporter.apiNewHeading(deviceType[j]+" Device - Get Device (Using "+propertyInUse+" with value = "+valueOfProp+")");

						//Create the curl command to get application details with Id
						String component= "Device", parameterList=propertyInUse.trim()+",operatingSystem", valueList=valueOfProp.trim()+","+deviceType[j]; //declaration and intialization of curl command varaiables.
						String jsonResponse; //declaration of string to capture JSON Response of the API request.
						String command=apiMethods.createCurlCommand(component, parameterList, valueList);	//Creates a curl command with component, parameterList and valueList.

						//adding heading in APIOverallComparison HTML for the curl command created
						apiReporter.apiNewHeading(command);

						// Step : Execute curl command to get JSON response of the request
						jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

						// Step : Verifying record count is equal to 1 as using Id as a parameter
						if (apiMethods.getRecordCount(jsonResponse) > 0) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
						{
							strActualResult="As expected getting atleast 1 record.";
							isEventSuccessful=true;
						}
						else
						{
							strActualResult= "Either getting no records which is not expected. Hence failing stop test. <br> JSON Response =" +jsonResponse;
							isEventSuccessful=false;
						}
						reporter.ReportStep("Verifying record count in JSON reponse", "Record count should be equal to 1 as using Id as a parameter",strActualResult ,isEventSuccessful );

						// Step : Creating expected HashMap from the JSON Response
						actualResultMap=apiMethods.getKeyValuePair(jsonResponse); //getting key value pair from the JSON response in a HashMap

						//Converting Bytes to GB for DISKSPACE
						String diskSpacebytesVal=actualResultMap.get("diskSpace");
						String diskSpaceGB = apiMethods.convertFromBytes(diskSpacebytesVal, "gb");
						actualResultMap.put("diskSpace", diskSpaceGB);

						//Converting Bytes to GB for DISKSPACEUSED
						String diskSpaceUsedbytesVal=actualResultMap.get("diskSpaceUsed");
						String diskSpaceUsedGB = apiMethods.convertFromBytes(diskSpaceUsedbytesVal, "gb");
						actualResultMap.put("diskSpaceUsed", diskSpaceUsedGB);
						//*******************************************************Code to create expected Result Ends here *******************************************************/

						//***********code for getting expected starts here***************************************
						String actualValue;

						String nextReservationStartTime = actualResultMap.get("nextReservationStartTime");
						actualResultMap.put("nextReservationStartTime", apiMethods.convertDateUTCToLocalTimezone(nextReservationStartTime));
						String nextReservationEndTime = actualResultMap.get("nextReservationEndTime");
						actualResultMap.put("nextReservationEndTime", apiMethods.convertDateUTCToLocalTimezone(nextReservationEndTime));
						String lastInuseAt = actualResultMap.get("lastInuseAt");
						actualResultMap.put("lastInuseAt", apiMethods.convertDateUTCToLocalTimezone(lastInuseAt));
						String lastConnectedAt = actualResultMap.get("lastConnectedAt");
						actualResultMap.put("lastConnectedAt", apiMethods.convertDateUTCToLocalTimezone(lastConnectedAt));
						String lastDisConnectedAt = actualResultMap.get("lastDisconnectedAt");
						actualResultMap.put("lastDisconnectedAt", apiMethods.convertDateUTCToLocalTimezone(lastDisConnectedAt));
						deviceID = actualResultMap.get("id");
						expectedResultMap=getDeviceExpectedMap(deviceID, "", serverIP);
						expectedResultMap.put("id", deviceID);
						//expectedResultMap.put("operatingSystem",deviceType[j]);
						//***********code for getting expected ends here***************************************


						//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************		
						//Comparing both the maps and reporting
						strActualResult="";
						Map<String,String> errorMap=new HashMap<String,String>();
						isEventSuccessful=apiMethods.compareActualAndExpectedMap(devModel, expectedResultMap,actualResultMap,errorMap);
						System.out.println(errorMap);
						if(isEventSuccessful)
						{
							strActualResult= "All the properties verified are matching";
							apiReporter.apiPassBlock("Properties at UI and received through api matches");
						}
						else
						{
							apiReporter.apiErrorBlock("Properties Did not match");
							strActualResult= "compareActualAndExpectedMap---" + errorMap + com.common.utilities.APILibrary.strErrMsg_ApiLib;
						}
						reporter.ReportStep("Compares Actual and Expected Map", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);


						//Step 10: Reporting app model with expected and actual values in the HTML report
						apiReporter.CreateAppModelTableInReport(devModel,expectedResultMap,actualResultMap,errorMap);
						reporter.ReportStep("Added Entry in API Overall Comparison file", "Entry added successfully", "Refer <a href=\"" + dicConfig.get("ReportPath") + "\\" +"APIOverallReportLastRun\"" +"> Directory Link </a>", true);

						//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************

						oldActualResultMap=actualResultMap; //Storing actualResult map for next iteration

					}
				}

				GoToDevicesPage();

				System.out.println("----------------------------------------------- CODE to verify In-Use devices STARTS HERE -----------------------------------------------");

				//Below code runs for Online/InUse device for Android
				isEventSuccessful = selectPlatform(deviceType[j]);
				apiReporter.apiAddBlock("Get Device Properties for an In-Use " +deviceType[j]+" device");
				values = ExecuteCLICommand("run",deviceType[j] ,"","","","");
				isEventSuccessful = (boolean)values[2];
				String devicename= (String)values[3];
				if (isEventSuccessful)
				{
					strActualResult = "Viewer launched after connecting to an Android device:  " + values[0] + " & processfound : " +  values[1];
				}
				else
				{
					strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
				}
				if(deviceType[j].equals("Android"))
				{
					isEventSuccessful = PerformAction("txtDeviceSearch", Action.Clear);
					PerformAction("Browser", Action.Refresh);
				}
				
				isEventSuccessful = selectStatus("In Use");
				devicesErrorMsg=GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text");
				//System.out.println("no device:"+devicesErrorMsg);
				if(devicesErrorMsg.contains("No devices match your filter criteria.")){
					System.out.println("No Devices are their");
					apiReporter.apiErrorBlock("Devices not available with selected filters");
				}
				else{
					isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0];
					expDeviceID= GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];		




					//************************Code for getting actual result from API Request starts here *******************************************************************
					//adding heading in APIOverallComparison HTML  for the AppType, Property and Value used in API Request
					apiReporter.apiNewHeading(deviceType[j]+expDeviceID +" Device - Get Device (Using "+"availability"+" with value = "+"Online-InUse"+")");

					//Create the curl command to get application details with Id
					String component= "Device", parameterList="availability,id", valueList="Online,"+expDeviceID; //declaration and intialization of curl command varaiables.
					String jsonResponse; //declaration of string to capture JSON Response of the API request.

					String command=apiMethods.createCurlCommand(component, parameterList, valueList);	//Creates a curl command with component, parameterList and valueList.

					//adding heading in APIOverallComparison HTML for the curl command created
					apiReporter.apiNewHeading(command);

					// Step : Execute curl command to get JSON response of the request
					jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

					// Step : Verifying record count is equal to 1 as using Id as a parameter
					if (apiMethods.getRecordCount(jsonResponse) == 1) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
					{
						strActualResult="As expected getting 1 record.";
						isEventSuccessful=true;
					}
					else
					{
						strActualResult= "Either getting no records which is not expected. Hence failing stop test. <br> JSON Response =" +jsonResponse;
						isEventSuccessful=false;
					}
					reporter.ReportStep("Verifying record count in JSON reponse", "Record count should be equal to 1 as using Id as a parameter",strActualResult ,isEventSuccessful );

					// Step : Creating expected HashMap from the JSON Response
					actualResultMap=apiMethods.getKeyValuePair(jsonResponse); //getting key value pair from the JSON response in a HashMap
					//Converting Bytes to GB for DISKSPACE
					String diskSpacebytesVal=actualResultMap.get("diskSpace");
					String diskSpaceGB = apiMethods.convertFromBytes(diskSpacebytesVal, "gb");
					actualResultMap.put("diskSpace", diskSpaceGB);

					//Converting Bytes to GB for DISKSPACEUSED
					String diskSpaceUsedbytesVal=actualResultMap.get("diskSpaceUsed");
					String diskSpaceUsedGB = apiMethods.convertFromBytes(diskSpaceUsedbytesVal, "gb");
					actualResultMap.put("diskSpaceUsed", diskSpaceUsedGB);


					//***********code for getting expected starts here***************************************


					String nextReservationStartTime = actualResultMap.get("nextReservationStartTime");
					actualResultMap.put("nextReservationStartTime", apiMethods.convertDateUTCToLocalTimezone(nextReservationStartTime));
					String nextReservationEndTime = actualResultMap.get("nextReservationEndTime");
					actualResultMap.put("nextReservationEndTime", apiMethods.convertDateUTCToLocalTimezone(nextReservationEndTime));
					String lastInuseAt = actualResultMap.get("lastInuseAt");
					actualResultMap.put("lastInuseAt", apiMethods.convertDateUTCToLocalTimezone(lastInuseAt));
					String lastConnectedAt = actualResultMap.get("lastConnectedAt");
					actualResultMap.put("lastConnectedAt", apiMethods.convertDateUTCToLocalTimezone(lastConnectedAt));
					String lastDisConnectedAt = actualResultMap.get("lastDisconnectedAt");
					actualResultMap.put("lastDisconnectedAt", apiMethods.convertDateUTCToLocalTimezone(lastDisConnectedAt));
					deviceID = actualResultMap.get("id");
					expectedResultMap=getDeviceExpectedMap(deviceID, "", serverIP);
					expectedResultMap.put("id", deviceID);
					//*******************************************************Code to create expected Result Ends here *******************************************************/
					//expectedResultMap.put("operatingSystem",deviceType[j]);
					//***********code for getting expected ends here***************************************


					//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************		
					//Comparing both the maps and reporting
					strActualResult="";
					Map<String,String> errorMap=new HashMap<String,String>();
					isEventSuccessful=apiMethods.compareActualAndExpectedMap(devModel, expectedResultMap,actualResultMap,errorMap);
					System.out.println(errorMap);
					if(isEventSuccessful)
					{
						strActualResult= "All the properties verified are matching";
						apiReporter.apiPassBlock("Properties at UI and received through api matches");
					}
					else
					{
						apiReporter.apiErrorBlock("Properties Did not match");
						strActualResult= "compareActualAndExpectedMap---" + errorMap + com.common.utilities.APILibrary.strErrMsg_ApiLib;
					}
					reporter.ReportStep("Compares Actual and Expected Map", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);


					//Step 10: Reporting app model with expected and actual values in the HTML report
					apiReporter.CreateAppModelTableInReport(devModel,expectedResultMap,actualResultMap,errorMap);
					reporter.ReportStep("Added Entry in API Overall Comparison file", "Entry added successfully", "Refer <a href=\"" + dicConfig.get("ReportPath") + "\\" +"APIOverallReportLastRun\"" +"> Directory Link </a>", true);

					//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************

					oldActualResultMap=actualResultMap; //Storing actualResult map for next iteration
					
					  //**************************************************************************//
					   // Step  : Release device.
					   //**************************************************************************//
					   ExecuteCLICommand("release", "", "", "", devicename, "","","" );
					   Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
				}
				GoToDevicesPage();
				System.out.println("----------------------------------------------- Disabled CODE STARTS HERE -----------------------------------------------");

				//Below code runs for Online/InUse device for Android
				isEventSuccessful = selectPlatform(deviceType[j]);
				apiReporter.apiAddBlock("Get Device Properties for an DIsabled " +deviceType[j]+" device");
				if(deviceType[j].equals("Android"))
				{
					isEventSuccessful = PerformAction("txtDeviceSearch", Action.Clear);
					PerformAction("Browser", Action.Refresh);
				}
				isEventSuccessful = selectStatus("Offline");
				PerformAction(dicOR.get("eleNoDevice_DevicesPage"), Action.WaitForElement,"20");
				devicesErrorMsg=GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text");
				//System.out.println("no device:"+devicesErrorMsg);
				if(devicesErrorMsg.contains("No devices match your filter criteria.")){
					System.out.println("No Devices are their");
					apiReporter.apiErrorBlock("Devices not available with selected filters");
				}
				else{
					
					/*WebDriverWait wait = new WebDriverWait(driver, 20);
				   wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='deviceList table data-grid']/tbody/tr[2]/td[1]"))); */
					isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0];
					expDeviceID= GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];		
					

					//************************Code for getting actual result from API Request starts here *******************************************************************
					//adding heading in APIOverallComparison HTML  for the AppType, Property and Value used in API Request
					apiReporter.apiNewHeading(deviceType[j]+expDeviceID +" Device - Get Device (Using "+"availability"+" with value = "+"Online-Disabled"+")");

					//Create the curl command to get application details with Id
					String Component= "Device", ParameterList="id", ValueList=expDeviceID; //declaration and intialization of curl command varaiables.
					//declaration of string to capture JSON Response of the API request.

					String command=apiMethods.createCurlCommand(Component, ParameterList, ValueList);	//Creates a curl command with component, parameterList and valueList.

					//adding heading in APIOverallComparison HTML for the curl command created
					apiReporter.apiNewHeading(command);

					// Step : Execute curl command to get JSON response of the request
					String jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

					// Step : Verifying record count is equal to 1 as using Id as a parameter
					if (apiMethods.getRecordCount(jsonResponse) == 1) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
					{
						strActualResult="As expected getting 1 record.";
						isEventSuccessful=true;
					}
					else
					{
						strActualResult= "Either getting no records which is not expected. Hence failing stop test. <br> JSON Response =" +jsonResponse;
						isEventSuccessful=false;
					}
					reporter.ReportStep("Verifying record count in JSON reponse", "Record count should be equal to 1 as using Id as a parameter",strActualResult ,isEventSuccessful );

					// Step : Creating expected HashMap from the JSON Response
					actualResultMap=apiMethods.getKeyValuePair(jsonResponse); //getting key value pair from the JSON response in a HashMap
					//Converting Bytes to GB for DISKSPACE
					String diskSpacebytesVal=actualResultMap.get("diskSpace");
					String diskSpaceGB = apiMethods.convertFromBytes(diskSpacebytesVal, "gb");
					actualResultMap.put("diskSpace", diskSpaceGB);

					//Converting Bytes to GB for DISKSPACEUSED
					String diskSpaceUsedbytesVal=actualResultMap.get("diskSpaceUsed");
					String diskSpaceUsedGB = apiMethods.convertFromBytes(diskSpaceUsedbytesVal, "gb");
					actualResultMap.put("diskSpaceUsed", diskSpaceUsedGB);


					//***********code for getting expected starts here***************************************


					String nextReservationStartTime = actualResultMap.get("nextReservationStartTime");
					actualResultMap.put("nextReservationStartTime", apiMethods.convertDateUTCToLocalTimezone(nextReservationStartTime));
					String nextReservationEndTime = actualResultMap.get("nextReservationEndTime");
					actualResultMap.put("nextReservationEndTime", apiMethods.convertDateUTCToLocalTimezone(nextReservationEndTime));
					String lastInuseAt = actualResultMap.get("lastInuseAt");
					actualResultMap.put("lastInuseAt", apiMethods.convertDateUTCToLocalTimezone(lastInuseAt));
					String lastConnectedAt = actualResultMap.get("lastConnectedAt");
					actualResultMap.put("lastConnectedAt", apiMethods.convertDateUTCToLocalTimezone(lastConnectedAt));
					String lastDisConnectedAt = actualResultMap.get("lastDisconnectedAt");
					actualResultMap.put("lastDisconnectedAt", apiMethods.convertDateUTCToLocalTimezone(lastDisConnectedAt));
					deviceID = actualResultMap.get("id");
					expectedResultMap=getDeviceExpectedMap(deviceID, "", serverIP);
					expectedResultMap.put("id", deviceID);
					//*******************************************************Code to create expected Result Ends here *******************************************************/
					//expectedResultMap.put("operatingSystem",deviceType[j]);
					//***********code for getting expected ends here***************************************


					//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************		
					//Comparing both the maps and reporting
					strActualResult="";
					Map<String,String> errorMap=new HashMap<String,String>();
					isEventSuccessful=apiMethods.compareActualAndExpectedMap(devModel, expectedResultMap,actualResultMap,errorMap);
					System.out.println(errorMap);
					if(isEventSuccessful)
					{
						strActualResult= "All the properties verified are matching";
						apiReporter.apiPassBlock("Properties at UI and received through api matches");
					}
					else
					{
						apiReporter.apiErrorBlock("Properties Did not match");
						strActualResult= "compareActualAndExpectedMap---" + errorMap + com.common.utilities.APILibrary.strErrMsg_ApiLib;
					}
					reporter.ReportStep("Compares Actual and Expected Map", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);


					//Step 10: Reporting app model with expected and actual values in the HTML report
					apiReporter.CreateAppModelTableInReport(devModel,expectedResultMap,actualResultMap,errorMap);
					reporter.ReportStep("Added Entry in API Overall Comparison file", "Entry added successfully", "Refer <a href=\"" + dicConfig.get("ReportPath") + "\\" +"APIOverallReportLastRun\"" +"> Directory Link </a>", true);

					//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************

					oldActualResultMap=actualResultMap; //Storing actualResult map for next iteration

				}
				GoToDevicesPage();			 

				System.out.println("----------------------------------------------- Offline CODE STARTS HERE -----------------------------------------------");

				//Below code runs for Online/InUse device for Android
				isEventSuccessful = selectPlatform(deviceType[j]);
				apiReporter.apiAddBlock("Get Device Properties for an Offline " +deviceType[j]+" device");
				if(deviceType[j].equals("Android"))
				{
					isEventSuccessful = PerformAction("txtDeviceSearch", Action.Clear);
					PerformAction("Browser", Action.Refresh);
				}
				isEventSuccessful = selectStatus("Offline");

				devicesErrorMsg=GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text");
				//System.out.println("no device:"+devicesErrorMsg);
				if(devicesErrorMsg.contains("No devices match your filter criteria.")){
					System.out.println("No Devices are their");
					apiReporter.apiErrorBlock("Devices not available with selected filters");
				}
				else{
					/*WebDriverWait wait = new WebDriverWait(driver, 20);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='deviceList table data-grid']/tbody/tr[2]/td[1]"))); */
					isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0];
					expDeviceID= GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];		



					//************************Code for getting actual result from API Request starts here *******************************************************************
					//adding heading in APIOverallComparison HTML  for the AppType, Property and Value used in API Request
					apiReporter.apiNewHeading(deviceType[j]+expDeviceID +" Device - Get Device (Using "+"availability"+" with value = "+"Offline"+")");

					//Create the curl command to get application details with Id
					String Component1= "Device", ParameterList1="availability,id", ValueList1="Offline,"+expDeviceID; //declaration and intialization of curl command varaiables.
					//declaration of string to capture JSON Response of the API request.

					String command=apiMethods.createCurlCommand(Component1, ParameterList1, ValueList1);	//Creates a curl command with component, parameterList and valueList.

					//adding heading in APIOverallComparison HTML for the curl command created
					apiReporter.apiNewHeading(command);

					// Step : Execute curl command to get JSON response of the request
					String jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

					// Step : Verifying record count is equal to 1 as using Id as a parameter
					if (apiMethods.getRecordCount(jsonResponse) == 1) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
					{
						strActualResult="As expected getting 1 record.";
						isEventSuccessful=true;
					}
					else
					{
						strActualResult= "Either getting no records which is not expected. Hence failing stop test. <br> JSON Response =" +jsonResponse;
						isEventSuccessful=false;
					}
					reporter.ReportStep("Verifying record count in JSON reponse", "Record count should be equal to 1 as using Id as a parameter",strActualResult ,isEventSuccessful );

					// Step : Creating expected HashMap from the JSON Response
					actualResultMap=apiMethods.getKeyValuePair(jsonResponse); //getting key value pair from the JSON response in a HashMap
					//Converting Bytes to GB for DISKSPACE
					String diskSpacebytesVal=actualResultMap.get("diskSpace");
					String diskSpaceGB = apiMethods.convertFromBytes(diskSpacebytesVal, "gb");
					actualResultMap.put("diskSpace", diskSpaceGB);

					//Converting Bytes to GB for DISKSPACEUSED
					String diskSpaceUsedbytesVal=actualResultMap.get("diskSpaceUsed");
					String diskSpaceUsedGB = apiMethods.convertFromBytes(diskSpaceUsedbytesVal, "gb");
					actualResultMap.put("diskSpaceUsed", diskSpaceUsedGB);


					//***********code for getting expected starts here***************************************


					String nextReservationStartTime = actualResultMap.get("nextReservationStartTime");
					actualResultMap.put("nextReservationStartTime", apiMethods.convertDateUTCToLocalTimezone(nextReservationStartTime));
					String nextReservationEndTime = actualResultMap.get("nextReservationEndTime");
					actualResultMap.put("nextReservationEndTime", apiMethods.convertDateUTCToLocalTimezone(nextReservationEndTime));
					String lastInuseAt = actualResultMap.get("lastInuseAt");
					actualResultMap.put("lastInuseAt", apiMethods.convertDateUTCToLocalTimezone(lastInuseAt));
					String lastConnectedAt = actualResultMap.get("lastConnectedAt");
					actualResultMap.put("lastConnectedAt", apiMethods.convertDateUTCToLocalTimezone(lastConnectedAt));
					String lastDisConnectedAt = actualResultMap.get("lastDisconnectedAt");
					actualResultMap.put("lastDisconnectedAt", apiMethods.convertDateUTCToLocalTimezone(lastDisConnectedAt));
					deviceID = actualResultMap.get("id");
					expectedResultMap=getDeviceExpectedMap(deviceID, "", serverIP);
					expectedResultMap.put("id", deviceID);
					//*******************************************************Code to create expected Result Ends here *******************************************************/
					 

					//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************		
					//Comparing both the maps and reporting
					strActualResult="";
					Map<String,String> errorMap=new HashMap<String,String>();
					isEventSuccessful=apiMethods.compareActualAndExpectedMap(devModel, expectedResultMap,actualResultMap,errorMap);
					System.out.println(errorMap);
					if(isEventSuccessful)
					{
						strActualResult= "All the properties verified are matching";
						apiReporter.apiPassBlock("Properties at UI and received through api matches");
					}
					else
					{
						apiReporter.apiErrorBlock("Properties Did not match");
						strActualResult= "compareActualAndExpectedMap---" + errorMap + com.common.utilities.APILibrary.strErrMsg_ApiLib;
					}
					reporter.ReportStep("Compares Actual and Expected Map", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);


					//Step 10: Reporting app model with expected and actual values in the HTML report
					apiReporter.CreateAppModelTableInReport(devModel,expectedResultMap,actualResultMap,errorMap);
					reporter.ReportStep("Added Entry in API Overall Comparison file", "Entry added successfully", "Refer <a href=\"" + dicConfig.get("ReportPath") + "\\" +"APIOverallReportLastRun\"" +"> Directory Link </a>", true);

					//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************

					oldActualResultMap=actualResultMap; //Storing actualResult map for next iteration

				}
				GoToDevicesPage();			

			}	 
		}
		catch(Exception ex)
		{
			apiReporter.apiErrorBlock("Something went wrong... Error Message "+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			//Adds a step Provides API Overall Comparison file link in DC Selenium Execution Report
			apiReporter.apiOverallHtmlLinkStep();

		}

	}	
}
