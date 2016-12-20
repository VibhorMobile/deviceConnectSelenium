package com.test.java;

import java.io.IOException;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 11th Nov 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have atleast one device for all status
 * Jira Test Case Id: QA-1856
 * Coverage from Jira Case: Covers uninstall scenario
 */
public class _1130_Tester_User_Device_Action_uninstall_application extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	String jsonResponse=""; //declaration of string to capture JSON Response of the API request.
	String component="Application",parameterList="",valueList="";
	String command="";
	String errorMessage,entitlementErrorExpected="User does not have required entitlement DeviceApplicationManagement";

	String Platform[] ={"Android"}; //*********Multi-version app uploaded only for Android
	String appId,deviceId,appName,deviceName;
	String serverIP=dicCommon.get("ApplicationURL");
	String previousAppVersion,laterAppVersion;
	int seq;
	//Create curl command for install query
	public void executeInstallAppCurlCommand(String deviceId,String appId)
	{
		//creating curl command
		parameterList="verb,action,Id,deviceid,wait,force";
		valueList="action,uninstall,"+appId+","+deviceId+",5000,true"; //declaration and intialization of curl command varaiables.
		command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST" ,"","","","tester");	//Creates a curl command with component, parameterList and valueList.

		//adding heading in APIOverallComparison HTML for the curl command created
		apiReporter.apiNewHeading(command);

		// Step : Execute curl command to get JSON response of the request
		jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

		apiReporter.apiNewHeading(jsonResponse);
	}

	//Executing async query
	public void executeAsyncCommandAndVerifyErrorMessage(String asyncId)
	{
		//creating curl command
		parameterList="id";
		valueList=asyncId;
		command=apiMethods.createCurlCommand("Async", parameterList, valueList, "" ,"","","","tester");	//Creates a curl command with component, parameterList and valueList.

		//adding heading in APIOverallComparison HTML for the curl command created
		apiReporter.apiNewHeading(command);

		// Step : Execute curl command to get JSON response of the request
		jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

		apiReporter.apiNewHeading(jsonResponse);


	}
	//Verify if getting success on initial install query execution
	public boolean veriFyJsonResponse(){
		if (jsonResponse.contains("{\"isSuccess\":false")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
		{
			strActualResult="As expected not getting success message.";
			isEventSuccessful=true;
			apiReporter.apiPassBlock("Getting Error Message at api response");
			System.out.println("pass");
		}

		else
		{
			strActualResult= "Not getting Failure message message. Hence failing stop test. <br> JSON Response =" +jsonResponse;
			isEventSuccessful=false;
			apiReporter.apiErrorBlock("Not getting Failure message in api response");
		}
		reporter.ReportStep("Verifying is success is false in JSON reponse", "Should get success as false in JSON Response",strActualResult ,isEventSuccessful );

		errorMessage=apiMethods.getErrorMessage(command);
		if(errorMessage.equals(entitlementErrorExpected)){
			strActualResult="Verified as expected getting entitlement error message.";
			isEventSuccessful=true;
			apiReporter.apiPassBlock("Received entitlement error message as expected , Message shown was: "+errorMessage);
		}
		else{
			strActualResult="Verified not getting entitlement error message as expected: "+errorMessage;
			isEventSuccessful=false;
			apiReporter.apiErrorBlock("Not getting entitlement error message as expected , Message shown was: "+errorMessage);
		}
		reporter.ReportStep("Verify entitlement error message displayed", "expected entitlement error message: "+entitlementErrorExpected, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	public final void testScript() throws IOException
	{
		try
		{
			apiReporter.apiScriptHeading("_1129_Tester_User_Action_Install_application");
			//Adding heading for the Script in API Overall Comparison HTML file
			//apiReporter.apiScriptHeading("Action Application");
			/*---------------------------------------------------------------------------------------------------------------------------------------------------------
			 ****************************Code to create expected Result Starts here *****************************************************************
			---------------------------------------------------------------------------------------------------------------------------------------------------------*/
			appName="deviceControl";
			// Step 1 : login to deviceConnect with valid user credentials.
			isEventSuccessful = Login();
			isEventSuccessful=GoToUsersPage();
			String[]entitlement={"Device application management"};
			Boolean[]entitlementValue={false};
			setUserRoleSettings("Tester",entitlement , entitlementValue);
			int j=0;
			reporter.ReportStep("<b>For "+Platform[j]+"- install diff Combinations</b>", "", "", true);	
			apiReporter.apiNewHeading("<b>For "+Platform[j]+"- install diff Combinations</b>");

			isEventSuccessful=GoToApplicationsPage();
			if(isEventSuccessful){
				isEventSuccessful=selectPlatform_Application("Android");
				if(isEventSuccessful){
					isEventSuccessful=searchDevice_DI(appName);
					if(isEventSuccessful){
						GoToFirstAppDetailsPage();
						appId=getAppGUID(); 
						strActualResult="Fetched GUid for app";
					}
					else{
						strActualResult="Application did not found";
					}
				}
				else{
					strActualResult="Unable to select platform of device";
				}
			}
			else{
				strActualResult="Unable to Navigate to Applications page";
			}
			reporter.ReportStep("get GUID of app", "GUID should be received from application", strActualResult, isEventSuccessful);

			isEventSuccessful=GoToDevicesPage(); // Navigates to Device List page
			if(isEventSuccessful){
				isEventSuccessful=selectPlatform(Platform[j]); //apply filter for Platform
				isEventSuccessful=selectStatus("Available");	//apply status filter as available	
				GoTofirstDeviceDetailsPage();
				deviceId=getAppGUID();
				strActualResult="Received device guid";
			}
			else{
				strActualResult="Unable to Navigate to Devices page";
			}
			reporter.ReportStep("get GUID of device", "GUID should be received from device", strActualResult, isEventSuccessful);


			//-------------------------------------------------------------------------------------------------------------------------------------------
			//************************************************Code Different Scenarios of Install App Starts here**********************************************************************************
			//---------------------------------------------------------------------------------------------------------------------------------------------------------
			installAppOnDevice("deviceControl");
			executeInstallAppCurlCommand(deviceId,appId);
			veriFyJsonResponse();

			PerformAction("browser", Action.Refresh);
			isEventSuccessful=PerformAction(dicOR.get("appsInstalled_Device").replace("__appName__", appName), Action.isDisplayed);
			if(isEventSuccessful){
				strActualResult=appName+" App is not uninstalled from device";
				apiReporter.apiPassBlock(strActualResult);
			}
			else{
				strActualResult=appName+" App is uninstalled from device";
				apiReporter.apiErrorBlock(strActualResult);
			}
			reporter.ReportStep("Verify app isn't installed on device", "App should not be installed on device", strActualResult, isEventSuccessful);

		}catch(Exception ex)
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
