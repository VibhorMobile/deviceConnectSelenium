package com.test.java;

import java.io.IOException;
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
 * Creation Date: 17th Oct 2016
 * Last Modified Date: NA
 * Pre-requisite: TestData.xlsx should have tester user and token no updated,
 * Jira Test Case Id: QA-1869
 * Coverage from Jira Case: Complete
 */
public class _1113_Tester_user_verify_add_Application extends ScriptFuncLibrary {


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
	String appId,appName="ARIA",appPath=dicConfig.get("Artifacts")+"\\Applications\\ARIA.apk";
	String errorMessage;
	String entitlementErrorExpected="User does not have required entitlement ApplicationUpload";
	//String pattern=([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])(:|\.)\d{2};
	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1113_Tester_user_verify_add_Application");

			//Login to DC Server
			isEventSuccessful = Login();
			isEventSuccessful=GoToUsersPage();
			String[]entitlement={"Application upload"};
			Boolean[]entitlementValue={false};
			setUserRoleSettings("Tester",entitlement , entitlementValue);

			GoToApplicationsPage();
			//GoToFirstAppDetailsPage();
			//*************************** Verify Aria App is present or not in UI if present delete it**********************
			isEventSuccessful=PerformAction(dicOR.get("eleAppName_AppsPage").replace("__APPNAME__", appName), Action.isDisplayed);
			if(isEventSuccessful){
				deleteApplication(appName);
			}

			//*********************** Case1-  Adding User with only UserName and Password  *********************************************************


			System.out.println("*************************** Case- Add application with user having no entitlement app modify*************************");
			apiReporter.apiAddBlock("Deleting an Application using tester credentials");

			String component= "Application", parameterList="verb,wait", valueList="add,true", formTag="upload=@"+appPath; //declaration and intialization of curl command varaiables.
			String jsonResponse; //declaration of string to capture JSON Response of the API request.
			String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST","","", formTag,"tester");
			apiReporter.apiNewHeading("cURL query created:  "+command);
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
				strActualResult= "Not getting Failure message . Hence failing stop test. <br> JSON Response =" +jsonResponse;
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
			//isEventSuccessful=GoToApplicationsPage();
			PerformAction("browser", Action.Refresh);
			Thread.sleep(10000);
			isEventSuccessful=!PerformAction(dicOR.get("eleAppName_AppsPage").replace("__APPNAME__", appName), Action.isDisplayed);
			//appNameEdited=GetTextOrValue(dicOR.get("eleAppNameDisplay"), "text");
			//isEventSuccessful=PerformAction(dicOR.get("appNameLnk_Applications").replace("__appName__", appName),Action.isDisplayed);
			if(isEventSuccessful){
				strActualResult="Application :"+appName+"is not added to DC";
				apiReporter.apiPassBlock("Application :"+appName+" is not added to DC");
			}
			else{
				strActualResult="Application :"+appName+"is  added to DC";
				apiReporter.apiErrorBlock("Application :"+appName+" is added to DC");
			}
			reporter.ReportStep("Verifying application is not added  to DC", "Application shouldn't be added to DC",strActualResult ,isEventSuccessful );

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
