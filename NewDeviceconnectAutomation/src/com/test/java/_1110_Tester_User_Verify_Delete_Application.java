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
 * Creation Date: 14th Oct 2016
 * Last Modified Date: NA
 * Pre-requisite: TestData.xlsx should have tester user and token no updated, 
 * Jira Test Case Id: QA-1886
 * Coverage from Jira Case: Complete
 */
import com.common.utilities.GenericLibrary.Action;

public class _1110_Tester_User_Verify_Delete_Application extends ScriptFuncLibrary {

	

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
	String appId,appName;
	String errorMessage;
	String entitlementErrorExpected="User does not have required entitlement ApplicationDelete";
	//String pattern=([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])(:|\.)\d{2};
	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1110_Tester_User_Verify_Delete_Application");

			//Login to DC Server
			isEventSuccessful = Login();
			isEventSuccessful=GoToUsersPage();
			String[]entitlement={"Application delete"};
			Boolean[]entitlementValue={false};
			setUserRoleSettings("Tester",entitlement , entitlementValue);
			
			GoToApplicationsPage();
			GoToFirstAppDetailsPage();
			appId= GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];
			appName=GetTextOrValue(dicOR.get("eleAppNameDisplay"), "text");

			
			//*********************** Case1-  Adding User with only UserName and Password  *********************************************************


			System.out.println("*************************** Case- Delete application with user having no entitlement*************************");
			apiReporter.apiAddBlock("Deleting an Application using tester credentials");
			
			//Create the curl command to update user details with id
			String component="Application",parameterList="verb,Id,",valueList="delete,"+appId; //declaration and intialization of curl command varaiables.
			String jsonResponse;
			String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST" ,"","","","tester");
		
			jsonResponse=apiMethods.execCurlCmd(command);
			apiReporter.apiNewHeading("Response for Query from API: "+jsonResponse);

			//reporter.ReportStep("Verifying success in JSON reponse", "Should get success message in JSON Response",strActualResult ,isEventSuccessful );
			errorMessage=apiMethods.getErrorMessage(command);
			apiReporter.apiNewHeading("entitlement error message shown for user:"+errorMessage);

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
			//***** Verify app is not deleted from DC UI**********
			isEventSuccessful=GoToApplicationsPage();
			isEventSuccessful=PerformAction(dicOR.get("appNameLnk_Applications").replace("__appName__", appName),Action.isDisplayed);
			if(isEventSuccessful){
				strActualResult="Application "+appName+" is not deleted from DC";
				apiReporter.apiPassBlock("Application "+appName+" not deleted from DC UI");
			}
			else{
				strActualResult="Application "+appName+" is deleted from DC";
				apiReporter.apiErrorBlock("Application "+appName+" deleted from DC UI");
			}
			reporter.ReportStep("Verifying device is not deleted from DC", "Device should not be deleted from DC",strActualResult ,isEventSuccessful );
			
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
