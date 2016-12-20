package com.test.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 03 Oct 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have Atleast one Available and one Inuse device
 *                Admin user can only be used
 * Jira Test Case Id: QA-1838
 * Coverage from Jira Case: Positive Scenario's
 */

public class _1108_Get_Device_Logs extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	


	public Map<String,String> expectedResultMapHub= new HashMap<String,String>();//Result Map for Hubs From UI
	public Map<String,String> expectedResultMapPort= new HashMap<String,String>();//Result Map for Ports from UI
	public Map<String,String> actualResultMapPort = new HashMap<String,String>();//Result Map for Hub from API
	Map<String,String> actualResultMapHub = new HashMap<String,String>();//Result Map for Port from API
	Object[]values;
	String devicename;
	//String pattern=([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])(:|\.)\d{2};

	String[] hubModel ={"publicPort","GatewayName","serialNumber","model","location","description","firmware","errorMessage","fiveVoltNow","fiveVoltMax","fiveVoltMin","uptime","firmwareDate","isErrored","name"};
	String[] portModel ={"name","serialNumber","location","usbHubSerialNumber","usbHubPortNumber","milliamps","state","flags","chargingTime","chargingProfile"};

	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("1108_Get_Device_Logs");

			//Login to DC Server
			isEventSuccessful = Login();

			String serverIP=dicCommon.get("ApplicationURL");
			isEventSuccessful = Login();
			String[] deviceType= {"Android"}; //Declaring an array to run for Android and iOS app
			String[] status={"In Use"};
			/*This loop run for iOS first and then Android*/
			for(int j=0; j<=1; j++)
			{
				for(int k=0;k<=1;k++){


					if(deviceType[j].equals("Android"))
					{
						isEventSuccessful = PerformAction("txtDeviceSearch", Action.Clear);
						PerformAction("Browser", Action.Refresh);
					}

/*					isEventSuccessful = selectPlatform(deviceType[j]);
					if(status[k].equals("In Use")){
						values = ExecuteCLICommand("run",deviceType[j] ,"","","","");
						isEventSuccessful = (boolean)values[2];
						devicename= (String)values[3];
						if (isEventSuccessful)
						{
							strActualResult = "Viewer launched after connecting to an "+deviceType[j]+"   device:  " + values[0] + " & processfound : " +  values[1];
						}
						else
						{
							strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
						}
					}
*/

					isEventSuccessful = selectStatus(status[k]);
					apiReporter.apiNewHeading(" Devices Logs for "+deviceType[j]+" with "+status[k]+" status");

					String devicesErrorMsg=GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text");
					System.out.println("no device:"+devicesErrorMsg);
					if(devicesErrorMsg.contains("No devices match your filter criteria.")){
						System.out.println("No Devices are their");
						apiReporter.apiErrorBlock("Devices not available with selected filters");
					}
					else{
						isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0]; //Navigating to first Available or Inuse, Offline Or Disabled DeviceDetails page
						String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
						String deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];


						
						//Adding Response received against API request to API Overall report
						//Create the curl command to get Gateway Details
						String component= "Device/Log", parameterList="id", valueList=deviceID;
						String jsonResponse; //declaration of string to capture JSON Response of the API request.
						String command=apiMethods.createCurlCommand(component, parameterList, valueList);	//Creates a curl command with component, parameterList and valueList.

						//adding heading in APIOverallComparison HTML for the curl command created
						apiReporter.apiNewHeading(command);

						// Step : Execute curl command to get JSON response of the request
						jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
						//System.out.println(jsonResponse);
						
						PerformAction(dicOR.get("btnViewLog"), Action.Click);

						String parentWindow = driver.getWindowHandle();
						Set<String> handles =  driver.getWindowHandles();
						String log="";
						for(String windowHandle  : handles)
						{
							if(!windowHandle.equals(parentWindow))
							{
								driver.switchTo().window(windowHandle);
								Thread.sleep(10000);
								log =GetTextOrValue(dicOR.get("deviceLogs"), "text");
								//log=log.replace(" ", "\n");
								driver.close(); //closing child window
								driver.switchTo().window(parentWindow); //cntrl to parent window
							}
						}
						log=log.replaceAll("\"", "").concat("}").trim();
						//log=log.replace("??", "��");
						//log=log.replace("...,", "…");
						//String str = "SomeMoreTextIsHere";
						File newTextFile = new File("C:/Users/ritdhwajs/Documents/uilog"+deviceType[j]+"_"+status[k]+".txt");

						FileWriter fw = new FileWriter(newTextFile);
						fw.write(log);
						fw.close();

						apiReporter.apiHeading2(log);


						
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

						jsonResponse=jsonResponse.replace("{\"isSuccess\":true,\"data\":\"", "").replace("\"", "");
						jsonResponse=jsonResponse.replace("\"", "").replace("’","�");
						File newTextFileAPI = new File("C:/Users/ritdhwajs/Documents/apilog"+deviceType[j]+"_"+status[k]+".txt");

						FileWriter fwAPI = new FileWriter(newTextFileAPI);
						fwAPI.write(jsonResponse);
						fwAPI.close();
						apiReporter.apiNewHeading(jsonResponse.trim());
						
						//jsonResponse=jsonResponse.replaceAll("\\<.*?\\>", " ");
						///jsonResponse =jsonResponse.replaceAll("\\\\n", " ");
						///jsonResponse.replaceAll("\\u00a0","");

						////jsonResponse=jsonResponse.replace("\\\"", "");
						//jsonResponse.replace("\\ \\\\t\\\\t","}");

						///jsonResponse.replace("’","'");
                      // String fl=jsonResponse.split("\\\\n")[0]+jsonResponse.split("\\\\n")[1]+jsonResponse.split("\\\\n")[2];
                       /*String fl=log.split("\n")[0];
                       //System.out.println(fl1);
                       System.out.println("first line:"+fl);
                       if(jsonResponse.contains(fl.replace("\"", "")))
							apiReporter.apiPassBlock("passes first contains test");
						else
							apiReporter.apiErrorBlock("fails first contains test test");
              
                       String[]tempArray=jsonResponse.split("\\\\n");
                       System.out.println(tempArray.length);
                       String ll=jsonResponse.split("\\\\n")[tempArray.length-1];
                       System.out.println("last line from api response is: "+ll);
                       
                       if(log.replace("\"", "").contains(ll))
							apiReporter.apiPassBlock("passes last contains test");
						else
							apiReporter.apiErrorBlock("fails last contains test test");
              	*/
						/*String loggg=log.replaceAll("\"", "");
						//String jsonResponse1=jsonResponse.replace(loggg,"");
						//System.out.println(jsonResponse1);
						//jsonResponse=jsonResponse.replace(jsonResponse1, "");
						apiReporter.apiNewHeading(jsonResponse);
						if(jsonResponse.regionMatches(0, loggg, 0, 500))
							apiReporter.apiPassBlock("passes region test");
						else
							apiReporter.apiErrorBlock("fails region test");
*/
						/*if(loggg.startsWith(jsonResponse)){
							isEventSuccessful=true;
							apiReporter.apiPassBlock("Verified device logs both matches");
						}*/

						if(jsonResponse.contains(log.replaceAll("\"", ""))){
							isEventSuccessful=true;
							apiReporter.apiPassBlock("Verified device logs both matches");
						}
						else{
							isEventSuccessful=false;
							apiReporter.apiErrorBlock("Verified device logs both does not match");
						}

						//**************************************************************************//
						// Step  : Release device.
						//**************************************************************************//
						ExecuteCLICommand("release", "", "", "", devicename, "","","" );
						Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
						GoToDevicesPage();
					}
				}
			}

			apiReporter.apiOverallHtmlLinkStep();



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
