package com.test.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 26 Sep 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have Atleast one USB Hub configured with it.
 *                Admin user can only be used
 * Jira Test Case Id: QA-1995
 * Coverage from Jira Case: Complete
 */
public class _1106_API_Verify_that_setting_port_mode_by_usb_hub_serial_number_and_port_number_works_using_REST_API extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	public Map<String,String> expectedResultMap= new HashMap<String,String>();
	ArrayList<String> errorList = new ArrayList<String>(); 

	// Declaring a HashMap to store actual results key and values
	String deviceName,vendorId;
	private String valueOfProp="";
	String updateProp ="usbHubPortState";
	String []propValue={"charge","data","off"};

	String oldValue,newValue;
	String deviceID;
	boolean isEventSuccessfulRecordCount ;

	public final void testScript()	{
		try{

			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("1106_API_Verify_that_setting_port_mode_by_usb_hub_serial_number_and_port_number_works_using_REST_API");

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
					apiReporter.apiErrorBlock(" Available Devices not available with selected filters:"+deviceType[j]);
				}
				else{
					isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0]; //Navigating to first Available or Inuse, Offline Or Disabled DeviceDetails page
					String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
					deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];
					PerformAction(dicOR.get("lnkShowDetails"), Action.Click);
					String portNo= GetTextOrValue(dicOR.get("USBLocation_DeviceDetailsPage"), "text").split("@")[0].split(" ")[3];
					String Serial= GetTextOrValue(dicOR.get("USBLocation_DeviceDetailsPage"), "text").split("@")[0].split(" ")[0];

					//****************Get Hub Serial # for device***************************************
					GoToDeviceGatewaysPage();
					/*String USBHubHeader=GetTextOrValue(dicOR.get("UsbHubSerial_Gateways").replace("__deviceName__",deviceName), "text");
					String Serial=USBHubHeader.substring(USBHubHeader.indexOf("(")+1,USBHubHeader.indexOf(")"));*/
					System.out.println(Serial);
					for (String valueOfProp: propValue)
					{

						//************************Code for getting actual result from API Request starts here *******************************************************************
						//adding heading in APIOverallComparison HTML Property and Value used in API Request
						apiReporter.apiHeading2(" Update port mode to --"+valueOfProp+"-- for "+deviceType[j]+" Device:"+deviceName+"with hub serial #: "+Serial+" and port:"+portNo);
						//Create the curl command to update user details with id
						String component= "Gateway/UsbHub", parameterList="verb"+",id"+",usbHubPort"+","+updateProp, valueList="update"+","+Serial+","+portNo+","+valueOfProp; //declaration and intialization of curl command varaiables.
						String jsonResponse; //declaration of string to capture JSON Response of the API request.
						String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST" ,"");	//Creates a curl command with component, parameterList and valueList.

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
							System.out.println("getting no success response against API query");
						}
						reporter.ReportStep("Verifying getting success message in JSON reponse", "Should get success message ",strActualResult ,isEventSuccessfulRecordCount );


						//****************************Code to create expected Result Ends here *****************************************************************		

						//***********Verify from Gate way Screens details for the device***************************************
						isEventSuccessful=verifyDevicePortStatus(valueOfProp, deviceName,Serial,portNo);
						if(isEventSuccessful){
							apiReporter.apiPassBlock("Verified in Device Gateways screen value for device :"+deviceName+" is: "+valueOfProp);
						}
						else{
							apiReporter.apiErrorBlock("Verified in Device Gateways screen value for device :"+deviceName+" is not set to "+valueOfProp);
						}

						//***********Verify from Device details status of device***************************************

						isEventSuccessful=PerformAction("browser","navigate","http://"+serverIP+"/#/Device/Detail/" + deviceID);
						if(isEventSuccessful){
							isEventSuccessful=PerformAction(dicOR.get("lnkShowDetails"), Action.Click);
							if(isEventSuccessful){
								String deviceStatus=GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text");
								switch(valueOfProp.toLowerCase()){
								case "data":
									if(deviceStatus.equals("Available")){
										strActualResult="device Status for device in details page is:"+deviceStatus;
										apiReporter.apiPassBlock("Verified on device details Page device status is :"+deviceStatus+" when port mode is:"+valueOfProp);
										isEventSuccessful=true;
									}
									else{
										strActualResult="device Status for device in details page is:"+deviceStatus;
										apiReporter.apiErrorBlock("Verified on device details Page  device status is :"+deviceStatus+" when port mode is:"+valueOfProp);
										isEventSuccessful=false;
									}
									break;
								case "charge":
									if(deviceStatus.equals("Offline")){
										strActualResult="device Status for device in details page is:"+deviceStatus;
										apiReporter.apiPassBlock("Verified on device details Page device status is :"+deviceStatus+" when port mode is:"+valueOfProp);
										isEventSuccessful=true;
									}
									else{
										strActualResult="device Status for device in details page is:"+deviceStatus;
										apiReporter.apiErrorBlock("Verified on device details Page  device status is :"+deviceStatus+" when port mode is:"+valueOfProp);
										isEventSuccessful=false;
									}
									break;
								case "off":
									if(deviceStatus.equals("Offline")){
										strActualResult="device Status for device in details page is:"+deviceStatus;
										apiReporter.apiPassBlock("Verified on device details Page device status is :"+deviceStatus+" when port mode is:"+valueOfProp);
										isEventSuccessful=true;
									}
									else{
										strActualResult="device Status for device in details page is:"+deviceStatus;
										apiReporter.apiErrorBlock("Verified on device details Page  device status is :"+deviceStatus+" when port mode is:"+valueOfProp);
										isEventSuccessful=false;
									}
									break;
								}


							}
							else{
								strActualResult="Unable to click on show link button";
								apiReporter.apiErrorBlock("Unable to Get Details from Device details page");
							}
						}
						else{
							strActualResult="Unable to navigate to details page for the device";
							apiReporter.apiErrorBlock("Unable to navigate to details page for the device");
						}

					}		 
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
