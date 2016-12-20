package com.test.java;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 28th Oct 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have atleast one device for all status
 * Jira Test Case Id: QA-1904
 * Coverage from Jira Case: Complete
 */
public class _1123_Tester_User_Verify_Get_Report_History extends ScriptFuncLibrary {
	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	


	public Map<String,String> expectedResultMap= new HashMap<String,String>();//Result Map for Hubs From UI

	String deviceID,deviceName,appID,userID;
	String errorMessage,parameterList,valueList,command,component,jsonResponse,dataTag;
	String entitlementMessage="User does not have required entitlement UserList";

	public void createAndexecuteCurlCommand(String idType, String idValue)
	{
		//creating curl command
		component="Report";
		parameterList="type,"+idType;
		valueList="history,"+idValue; //declaration and intialization of curl command varaiables.
		//command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST" ,"");	//Creates a curl command with component, parameterList and valueList.
		command= apiMethods.createCurlCommand(component , parameterList , valueList, "POST", "", "", "", "tester");

		//adding heading in APIOverallComparison HTML for the curl command created
		apiReporter.apiNewHeading(command);

		// Step : Execute curl command to get JSON response of the request
		jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

		apiReporter.apiNewHeading(jsonResponse);

		// Step : Verifying record count is equal to 1 as using Id as a parameter
		//apiMethods.apiVerifyGettingSuccessMessage(jsonResponse);

	}

	public void verifyJsonErrorMessage(String entitlementMessage){
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
		//apiReporter.apiNewHeading("entitlement error message shown for user:"+errorMessage);


		//reporter.ReportStep("Verifying is success is false in JSON reponse", "Should get success as false in JSON Response",strActualResult ,isEventSuccessful );
		if(errorMessage.equals(entitlementMessage)){
			strActualResult="Verified as expected getting entitlement error message.";
			isEventSuccessful=true;
			apiReporter.apiPassBlock("Received entitlement error message as expected , Message shown was: "+errorMessage);
		}
		else{
			strActualResult="Verified not getting entitlement error message as expected: "+errorMessage;
			isEventSuccessful=false;
			apiReporter.apiErrorBlock("Not getting entitlement error message as expected , Message shown was: "+errorMessage);
		}
		reporter.ReportStep("Verify entitlement error message displayed", "expected entitlement error message: "+entitlementMessage, strActualResult, isEventSuccessful);
	}
	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1123_Tester_User_Verify_Get_Report_History---------------QA:1904");
			isEventSuccessful = Login();
			isEventSuccessful=GoToUsersPage();
			String[]entitlement={"User list"};
			Boolean[]entitlementValue={false};
			setUserRoleSettings("Tester",entitlement , entitlementValue);
			//get Device Id for which Report has to be fetched
			GoToDevicesPage();
			GoTofirstDeviceDetailsPage();
			String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
			deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];

			//Get Application id for which report has to be fetched
			GoToApplicationsPage();
			GoToFirstAppDetailsPage();
			appID=GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];

			//Get UserID for which Usage report has to be fetched
			GoToUsersPage();
			searchUser(dicCommon.get("EmailAddress"));
			GoToFirstUserDetailsPage();
			userID=GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];

			//Scenario:1 Get All Usage Report without any parameter
			apiReporter.apiHeading2("Get Usage Report without any parameters");
			createAndexecuteCurlCommand("", "");
			verifyJsonErrorMessage(entitlementMessage);

			//Scenario:2 Get Usage Report with User iD of other User
			apiReporter.apiHeading2("Get Usage Report with userId of another user");
			createAndexecuteCurlCommand("userId", userID);
			verifyJsonErrorMessage(entitlementMessage);

			//Scenario:3 Get Usage Report with application ID
			apiReporter.apiHeading2("Get Usage Report with applicationId");
			createAndexecuteCurlCommand("applicationId", appID);
			verifyJsonErrorMessage(entitlementMessage);

			//Scenario:4 Get Usage report with device Id
			apiReporter.apiHeading2("Get Usage Report with deviceId");
			createAndexecuteCurlCommand("deviceId", deviceID);
			verifyJsonErrorMessage(entitlementMessage);

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
