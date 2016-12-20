package com.test.java;

import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.lang.Math.*;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.sun.jna.platform.win32.COM.Dispatch.ByReference;

import java.util.ArrayList;

import org.openqa.jetty.jetty.servlet.Default;

/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 24 May 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have atleast 2 android and iOS devices with offline status.
 * Admin user can only be used
 */

public class _1102_APIDeleteDevicesTest extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	String userId;
	//String userName;
	//Boolean value;
	//Object[] values = new Object[2];
	public Map<String,String> expectedResultMap= new HashMap<String,String>();
	public Map<String,String> actualResultMap = new HashMap<String,String>();
	boolean isEventSuccessfulRecordCount;
	// Creating an array having all the model properties available for Application component
	String[] devModel ={"id","name","availability","retainedById","retainedByDisplayName","enabled","batteryStatus","batteryPercentCharged",
			"diskSpace","slotNumber","diskSpaceUsed","nextReservationStartTime","nextReservationEndTime","reservedById",
			"reservedByDisplayName","lastInuseAt","lastDisconnectedAt","lastConnectedAt","deleted","notes","retainedByUserName","model","operatingSystem",
			"operatingSystemVersion","macAddress","serialNumber","manufacturer","friendlyModel","vendorUniqueIdentifier","vendorDeviceName",
			"deviceClass","formFactor","productType","isAudioEnabled","hardwareModel","operatingSystemBuild"};//,"retainedByUserName"
	//Creating an array of all the query properties available for Application component
	String[] queryProp ={"id","name","slotNumber","enabled","deleted","notes","availability"};
	String deviceID;

	public final void testScript()
	{
		try{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("Delete Devices");
			isEventSuccessful = Login();

			String serverIP=dicCommon.get("ApplicationURL");



			//*********************** Case1-  Deleting Disabled devices  *********************************************************


			System.out.println("*************************** Case1-Starts Here(Deleting disabled iOS and Android Devices)*************************");
			String[] deviceType= {"iOS","Android"}; //Declaring an array to run for Android and iOS app
			/*This loop run for iOS first and then Android*/
			for(int j=0; j<=1; j++)
			{
				if(deviceType[j].equals("Android"))
				{
					isEventSuccessful = PerformAction("txtDeviceSearch", Action.Clear);
					PerformAction("Browser", Action.Refresh);
				}
				isEventSuccessful = selectPlatform(deviceType[j]);

				apiReporter.apiAddBlock("Deleting a Offline Disabled"+deviceType[j]+"Device");
				isEventSuccessful = selectStatus("Offline");
		 
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
					isEventSuccessful = PerformAction("btnDisable", Action.Click);
					isEventSuccessful = PerformAction("hdrConfirmDisable", Action.isDisplayed);
					if(isEventSuccessful){
						PerformAction("btnContinue_Disable", Action.Click);
						System.out.println("click on disable");
					}



					String component= "Device", parameterList="verb"+",id", valueList="delete"+","+deviceID; //declaration and intialization of curl command varaiables.
					String jsonResponse; //declaration of string to capture JSON Response of the API request.
					String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "");	//Creates a curl command with component, parameterList and valueList.

					//adding heading in APIOverallComparison HTML for the curl command created
					apiReporter.apiNewHeading(command);

					// Step : Execute curl command to get JSON response of the request
					jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

					jsonResponse=apiMethods.execCurlCmd(command);
					apiReporter.apiNewHeading("JSON Response of Delete Query:"+"   "+jsonResponse);
					System.out.println("json response is:"+jsonResponse);

					if (jsonResponse.equals("{\"isSuccess\":true}")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
					{
						strActualResult="As expected getting success message.";
						isEventSuccessful=true;
						System.out.println("pass");
					}

					else
					{
						strActualResult= "Not getting success message. Hence failing stop test. <br> JSON Response =" +jsonResponse;
						isEventSuccessful=false;
					}
					reporter.ReportStep("Verifying success in JSON reponse", "Should get success message in JSON Response",strActualResult ,isEventSuccessful );
					//Code to get expected starts here
					expectedResultMap = deviceExpectedMap(deviceID,deviceName,serverIP );
					apiReporter.apiNewHeading("At DC UI getting status as:"+expectedResultMap);
					if(expectedResultMap.get("deleted").equalsIgnoreCase("true")){
						strActualResult= "Status of device is removed";
						apiReporter.apiPassBlock("Device is deleted from DC");
					}
					else{
						strActualResult= "Status of device is not removed";
						apiReporter.apiErrorBlock("Device is not deleted from DC");
					}
				}
				GoToDevicesPage();
				System.out.println("*************************** Case2-Starts Here(Deleting offline  iOS and Android Devices)*************************");
				//Declaring an array to run for Android and iOS app
				/*This loop run for iOS first and then Android*/


				isEventSuccessful = selectPlatform(deviceType[j]);
				if(deviceType[j].equals("Android"))
				{
					isEventSuccessful = PerformAction("txtDeviceSearch", Action.Clear);
					PerformAction("Browser", Action.Refresh);
				}
				apiReporter.apiAddBlock("Deleting a Offline"+deviceType[j]+"Device");
				isEventSuccessful = selectStatus("Offline");
				if(GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text").contains("No devices match your filter criteria.")){
					apiReporter.apiErrorBlock("Devices not available with selected filters");
				}
				else{
					isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0]; //Navigating to first Available or Inuse, Offline Or Disabled DeviceDetails page
					String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
					deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];




					String Component= "Device", ParameterList="verb"+",id", ValueList="delete"+","+deviceID; //declaration and intialization of curl command varaiables.
					String JsonResponse; //declaration of string to capture JSON Response of the API request.
					String Command=apiMethods.createCurlCommand(Component, ParameterList, ValueList, "POST","");	//Creates a curl command with component, parameterList and valueList.

					//adding heading in APIOverallComparison HTML for the curl command created
					apiReporter.apiNewHeading(Command);

					// Step : Execute curl command to get JSON response of the request
					JsonResponse=apiMethods.execCurlCmd(Command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

					JsonResponse=apiMethods.execCurlCmd(Command);
					apiReporter.apiNewHeading("JSON Response of Delete Query:"+"   "+JsonResponse);
					System.out.println("json response is:"+JsonResponse);

					if (JsonResponse.equals("{\"isSuccess\":true}")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
					{
						strActualResult="As expected getting success message.";
						isEventSuccessful=true;
						System.out.println("pass");
					}

					else
					{
						strActualResult= "Not getting success message. Hence failing stop test. <br> JSON Response =" +JsonResponse;
						isEventSuccessful=false;
					}
					reporter.ReportStep("Verifying success in JSON reponse", "Should get success message in JSON Response",strActualResult ,isEventSuccessful );
					//Code to get expected starts here
					expectedResultMap = deviceExpectedMap(deviceID,deviceName,serverIP );

					apiReporter.apiNewHeading("At DC Ui getting status as:"+expectedResultMap);
					if(expectedResultMap.get("deleted").equalsIgnoreCase("true")){
						strActualResult= "Status of device is removed";
						apiReporter.apiPassBlock("Device is deleted from DC");
					}
					else{
						strActualResult= "Status of device is not removed";
						apiReporter.apiErrorBlock("Device is not deleted from DC");
					}

				}

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


