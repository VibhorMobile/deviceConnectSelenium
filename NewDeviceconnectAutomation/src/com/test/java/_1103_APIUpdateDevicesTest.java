package com.test.java;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.lang.Math.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
import com.sun.jna.platform.win32.COM.Dispatch.ByReference;
import java.util.ArrayList;
import java.util.Date;
import org.openqa.jetty.jetty.servlet.Default;


/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 25 May 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have Atlease 1 iOS and Android device with Available, and 2 devices with offline status
 * Admin user can only be used
 * 
 */

public class _1103_APIUpdateDevicesTest extends ScriptFuncLibrary {
	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	public Map<String,String> expectedResultMap= new HashMap<String,String>();
	ArrayList<String> errorList = new ArrayList<String>(); 

	// Declaring a HashMap to store actual results key and values
	String deviceName;
	private String valueOfProp="";

	// Creating an array having all the model properties available for User component
	String[] devModel ={"id","name","availability","retainedById","retainedByDisplayName","enabled","batteryStatus","batteryPercentCharged",
			"diskSpace","slotNumber","diskSpaceUsed","nextReservationStartTime","nextReservationEndTime","reservedById",
			"reservedByDisplayName","lastInuseAt","lastDisconnectedAt","lastConnectedAt","deleted","notes","retainedByUserName","model","operatingSystem",
			"operatingSystemVersion","macAddress","serialNumber","manufacturer","friendlyModel","vendorUniqueIdentifier","vendorDeviceName",
			"deviceClass","formFactor","productType","isAudioEnabled","hardwareModel","operatingSystemBuild"};//,"retainedByUserName"
	//Creating an array of all the query properties available for Application component
	String[] updateProp ={"name","slotNumber","enabled","notes"};
	String oldValue,newValue;
	String deviceID;
	boolean isEventSuccessfulRecordCount ;

	public final void testScript()	{
		try{

			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1103_APIUpdateDevicesTest");

			String serverIP=dicCommon.get("ApplicationURL");
			isEventSuccessful = Login();
			String[] deviceType= {"Android","iOS"}; //Declaring an array to run for Android and iOS app
			/*This loop run for iOS first and then Android*/
			for(int j=0; j<=1; j++)
			{
				if(deviceType[j].equals("Android"))
				{
					isEventSuccessful = PerformAction("txtDeviceSearch", Action.Clear);
					PerformAction("Browser", Action.Refresh);
				}
				isEventSuccessful = selectPlatform(deviceType[j]);


				isEventSuccessful = selectStatus("Available");
				 
				String devicesErrorMsg=GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text");
				System.out.println("no device:"+devicesErrorMsg);
				if(devicesErrorMsg.contains("No devices match your filter criteria.")){
					System.out.println("No Devices are their");
					apiReporter.apiErrorBlock("Devices not available with selected filters");
				}
				else{
					isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0]; //Navigating to first Available or Inuse, Offline Or Disabled DeviceDetails page
					String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
					deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];

					// Editing device details
					isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
					isEventSuccessful = PerformAction("eleSlotMousehover_deviceDetailsPage", Action.DoubleClick);
					isEventSuccessful = PerformAction( "eleEditSlot_deviceDetailsPage", Action.Clear);
					isEventSuccessful = PerformAction( "eleEditSlot_deviceDetailsPage", Action.sendkeys , "77");
					isEventSuccessful = PerformAction("eleSlotSaveChangesButton_DeviceDetailsPage", Action.Click);
					isEventSuccessful = PerformAction("eleNoteMousehover_deviceDetailsPage", Action.DoubleClick);
					isEventSuccessful = PerformAction( "eleEditNotes_deviceDetailsPage", Action.Clear);
					isEventSuccessful = PerformAction( "eleEditNotes_deviceDetailsPage", Action.sendkeys , "Notes 007 field ");	
					isEventSuccessful = PerformAction("eleNotesSaveChangesButton_DeviceDetailsPage", Action.Click);

					//Step 2: Getting expected Result map from application details page
					expectedResultMap = deviceExpectedMap(deviceID,deviceName,serverIP );
					//expectedResultMap.put("operatingSystem",deviceType[j]);




					//Running the script for all the query properties for which details need to be updated.

					for (String propertyInUse: updateProp)
					{

						isEventSuccessful=true;
						oldValue=expectedResultMap.get(propertyInUse);
						valueOfProp="API Updated "+propertyInUse+""+apiMethods.getDateTime();
						//When Property is enabled reversing the value.
						if(propertyInUse.equals("enabled")){
							String enabledValue=expectedResultMap.get("enabled");
							if(enabledValue.equalsIgnoreCase("true")){
								valueOfProp="false";
							}
							else{
								valueOfProp="true";
							}

						}
						if(propertyInUse.equals("slotNumber")){
							String slotNumber=expectedResultMap.get("slotNumber");

							valueOfProp="17"+slotNumber;
						}




						//************************Code for getting actual result from API Request starts here *******************************************************************
						//adding heading in APIOverallComparison HTML Property and Value used in API Request
						apiReporter.apiNewHeading(" Update "+deviceType[j]+" Devices (Using "+propertyInUse+" with value = "+valueOfProp+")");

						//Create the curl command to update user details with id
						String component= "Device", parameterList="verb"+",id", valueList="update"+","+deviceID, dataTag="{"+propertyInUse+":\\\"" +valueOfProp+"\\\"}"; //declaration and intialization of curl command varaiables.
						String jsonResponse; //declaration of string to capture JSON Response of the API request.
						String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json", dataTag);	//Creates a curl command with component, parameterList and valueList.

						//adding heading in APIOverallComparison HTML for the curl command created
						apiReporter.apiNewHeading(command);

						// Step : Execute curl command to get JSON response of the request
						jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

						// Step : Verifying getting success in response
						apiReporter.apiNewHeading("Json Response of Query:"+jsonResponse);
						if (jsonResponse.equals("{\"isSuccess\":true}")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
						{
							strActualResult="As expected getting success message.";
							isEventSuccessfulRecordCount = true;
							System.out.println("getting success");
						}
						else
						{
							strActualResult= "Not getting success message. <br> JSON Response =" +jsonResponse;
							isEventSuccessfulRecordCount=false;
							System.out.println("getting no success");
						}
						reporter.ReportStep("Verifying getting success message in JSON reponse", "Should get success message ",strActualResult ,isEventSuccessfulRecordCount );


						//****************************Code to create expected Result Ends here *****************************************************************		

						//***********code for getting expected starts here***************************************
						expectedResultMap = deviceExpectedMap(deviceID,deviceName,serverIP );
						expectedResultMap.put("id", deviceID);

						apiReporter.apiNewHeading("expectedResultMap =" +expectedResultMap);
						//***********code for getting expected ends here***************************************





						//Comparing both the maps and reporting		
						if (!expectedResultMap.get(propertyInUse).equals(valueOfProp))
						{
							isEventSuccessful=false;
							System.out.println("value not matching");
							errorList.add(propertyInUse);
						}

						if(isEventSuccessful)
						{
							strActualResult= propertyInUse+" property updated successfully";
							apiReporter.apiPassBlock(propertyInUse+" property updated successfully");
						}
						else
						{
							strActualResult= "Unable to update property from API" + errorList;
							apiReporter.apiErrorBlock("Unable to update property " + propertyInUse);
						}
						reporter.ReportStep("Compares Actual and Expected Map", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);
						//POst condition: reverting display 
						//Again if property is email changing it to userName

						if(oldValue.equals("null")){
							oldValue="";
						}

						component= "Device";
						parameterList="verb"+",id";
						valueList="update"+","+deviceID;
						dataTag="{"+propertyInUse+":\\\"" +oldValue+"\\\"}"; //declaration and intialization of curl command varaiables.
						command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json", dataTag);	//Creates a curl command with component, parameterList and valueList.

						// Step : Execute curl command to get JSON response of the request
						jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
					}

				}
				GoToDevicesPage();
				apiReporter.apiAddBlock("Update "+deviceType[j]+" Devices deleted property for Disabled devices");
				isEventSuccessful = selectStatus("Offline");
				/*if(PerformAction(dicOR.get("eleNoDevice_DevicesPage"), Action.isDisplayed)){
			System.out.println("No Devices are their");
			apiReporter.apiErrorBlock("Devices not available with selected filters");
		}*/
				devicesErrorMsg=GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text");
				System.out.println("no device:"+devicesErrorMsg);
				if(devicesErrorMsg.contains("No devices match your filter criteria.")){
					System.out.println("No Devices are their");
					apiReporter.apiErrorBlock("Devices not available with selected filters");
				}
				else{
					isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0]; //Navigating to first Available or Inuse, Offline Or Disabled DeviceDetails page
					deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
					deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];
					isEventSuccessful = PerformAction("btnDisable", Action.Click);
					isEventSuccessful = PerformAction("hdrConfirmDisable", Action.isDisplayed);
					if(isEventSuccessful){
						PerformAction("btnContinue_Disable", Action.Click);
						System.out.println("click on disable");
					}
					oldValue="false";
					valueOfProp="true";


					String component= "Device", parameterList="verb"+",id", valueList="update"+","+deviceID, dataTag="{deleted:\\\"" +valueOfProp+"\\\"}"; //declaration and intialization of curl command varaiables.
					String jsonResponse; //declaration of string to capture JSON Response of the API request.
					String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json",dataTag);//Creates a curl command with component, parameterList and valueList.

					//adding heading in APIOverallComparison HTML for the curl command created
					apiReporter.apiNewHeading(command);

					// Step : Execute curl command to get JSON response of the request
					jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

					apiReporter.apiNewHeading("Json Response of Query:"+jsonResponse);
					if (jsonResponse.equals("{\"isSuccess\":true}")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
					{
						strActualResult="As expected getting success message.";
						isEventSuccessfulRecordCount = true;
						System.out.println("getting success");
					}
					else
					{
						strActualResult= "Not getting success message. <br> JSON Response =" +jsonResponse;
						isEventSuccessfulRecordCount=false;
						System.out.println("getting no success");
					}
					reporter.ReportStep("Verifying getting success message in JSON reponse", "Should get success message ",strActualResult ,isEventSuccessfulRecordCount );


					//****************************Code to create expected Result Ends here *****************************************************************		

					//***********code for getting expected starts here***************************************
					expectedResultMap = deviceExpectedMap(deviceID,deviceName,serverIP );



					apiReporter.apiNewHeading("Expected Map is:"+expectedResultMap);

					if (!expectedResultMap.get("deleted").equals(valueOfProp))
					{
						isEventSuccessful=false;
						System.out.println("value not matching");
						errorList.add("deleted");
					}

					if(isEventSuccessful)
					{
						strActualResult= "deleted property updated successfully";
						apiReporter.apiPassBlock(" deleted property updated successfully");
					}
					else
					{
						strActualResult= "Unable to update property from API" + errorList;
						apiReporter.apiErrorBlock("Unable to update property: Deleted");
					}
					reporter.ReportStep("Compares Actual and Expected Map", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);
					//POst condition: reverting display 

					component= "Device";
					parameterList="verb"+",id";
					valueList="update"+","+deviceID;
					dataTag="{deleted:\\\"" +oldValue+"\\\"}"; //declaration and intialization of curl command varaiables.
					command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json", dataTag);	//Creates a curl command with component, parameterList and valueList.

					// Step : Execute curl command to get JSON response of the request
					jsonResponse=apiMethods.execCurlCmd(command); 
				}
				GoToDevicesPage();
				System.out.println("*************************** Case2-Starts Here(Deleting Disabled  iOS and Android Devices)*************************");
				//Declaring an array to run for Android and iOS app
				/*This loop run for iOS first and then Android*/

				apiReporter.apiAddBlock("Update "+deviceType[j]+" Devices deleted property for Offline devices");
				isEventSuccessful = selectStatus("Offline");
				/*if(PerformAction(dicOR.get("eleNoDevice_DevicesPage"), Action.isDisplayed)){
			System.out.println("No Devices are their");
			apiReporter.apiErrorBlock("Devices not available with selected filters");
		}*/
				devicesErrorMsg=GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text");
				System.out.println("no device:"+devicesErrorMsg);
				if(devicesErrorMsg.contains("No devices match your filter criteria.")){
					System.out.println("No Devices are their");
					apiReporter.apiErrorBlock("Devices not available with selected filters");
				}
				else{
					isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0]; //Navigating to first Available or Inuse, Offline Or Disabled DeviceDetails page
					deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
					deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];

					oldValue="false";
					valueOfProp="true";


					String Component= "Device", ParameterList="verb"+",id", ValueList="update"+","+deviceID, DataTag="{deleted:\\\"" +valueOfProp+"\\\"}"; //declaration and intialization of curl command varaiables.
					String JsonResponse; //declaration of string to capture JSON Response of the API request.
					String Command=apiMethods.createCurlCommand(Component, ParameterList, ValueList, "POST", "Content-Type: application/json",DataTag);//Creates a curl command with component, parameterList and valueList.

					//adding heading in APIOverallComparison HTML for the curl command created
					apiReporter.apiNewHeading(Command);

					// Step : Execute curl command to get JSON response of the request
					String jsonResponse=apiMethods.execCurlCmd(Command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

					apiReporter.apiNewHeading("Json Response of Query:"+jsonResponse);
					if (jsonResponse.equals("{\"isSuccess\":true}")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
					{
						strActualResult="As expected getting success message.";
						isEventSuccessfulRecordCount = true;
						System.out.println("getting success");
					}
					else
					{
						strActualResult= "Not getting success message. <br> JSON Response =" +jsonResponse;
						isEventSuccessfulRecordCount=false;
						System.out.println("getting no success");
					}
					reporter.ReportStep("Verifying getting success message in JSON reponse", "Should get success message ",strActualResult ,isEventSuccessfulRecordCount );


					//****************************Code to create expected Result Ends here *****************************************************************		

					//***********code for getting expected starts here***************************************
					expectedResultMap = deviceExpectedMap(deviceID,deviceName,serverIP );



					apiReporter.apiNewHeading("Expected Map is:"+expectedResultMap);

					if (!expectedResultMap.get("deleted").equals(valueOfProp))
					{
						isEventSuccessful=false;
						System.out.println("value not matching");
						errorList.add("deleted");
					}

					if(isEventSuccessful)
					{
						strActualResult= "deleted property updated successfully";
						apiReporter.apiPassBlock(" deleted property updated successfully");
					}
					else
					{
						strActualResult= "Unable to update property from API" + errorList;
						apiReporter.apiErrorBlock("Unable to update property: Deleted");
					}
					reporter.ReportStep("Compares Actual and Expected Map", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);
					//POst condition: reverting display 

					String component= "Device";
					String parameterList="verb"+",id";
					String valueList="update"+","+deviceID;
					String dataTag="{deleted:\\\"" +oldValue+"\\\"}"; //declaration and intialization of curl command varaiables.
					String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json", dataTag);	//Creates a curl command with component, parameterList and valueList.

					// Step : Execute curl command to get JSON response of the request
					jsonResponse=apiMethods.execCurlCmd(command); 

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

