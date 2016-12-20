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
 * Creation Date: 13th Oct 2016
 * Last Modified Date: NA
 * Pre-requisite: TestData.xlsx should have tester user and token no updated, 
 * Jira Test Case Id: QA-1900
 * Coverage from Jira Case: Complete
 */
public class _1109_Tester_User_Verify_Add_User extends ScriptFuncLibrary {

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
	String errorMessage;
	String entitlementErrorExpected="User does not have required entitlement UserImport";
	//String pattern=([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])(:|\.)\d{2};
	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("1109_Tester_User_Verify_Add_User");

			//Login to DC Server
			isEventSuccessful = Login();
			isEventSuccessful=GoToUsersPage();
			String[]entitlement={"User import","User create"};
			Boolean[]entitlementValue={false,false};
			setUserRoleSettings("Tester",entitlement , entitlementValue);
			//*********************** Case1-  Adding User with only UserName and Password  *********************************************************


			System.out.println("*************************** Case1-Starts Here(Adding User with only Mandatory Fields*************************");
			apiReporter.apiAddBlock("Adding a User with Only userName and Password");
			String email="addUser@email.com"+apiMethods.getDateTime();
			String password="Password";

			String component= "User", parameterList="verb,wait", valueList="add,true", dataTag="{Email:\\\""+email+"\\\""+","+"Password:\\\""+password+"\\\""+"}"; //declaration and intialization of curl command varaiables.
			String jsonResponse; //declaration of string to capture JSON Response of the API request.
			String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json", dataTag,"","tester");	//Creates a curl command with component, parameterList and valueList.

			//adding heading in APIOverallComparison HTML for the curl command created

			apiReporter.apiNewHeading(command);
			jsonResponse=apiMethods.execCurlCmd(command);
			apiReporter.apiNewHeading("Response for Query from API: "+jsonResponse);

			reporter.ReportStep("Verifying success in JSON reponse", "Should get success message in JSON Response",strActualResult ,isEventSuccessful );
			errorMessage=apiMethods.getErrorMessage(command);
			apiReporter.apiNewHeading("entitlement errormessage shown for user:"+errorMessage);

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

			//************************************ Verify User is not created in DC UI*********************************************
			isEventSuccessful = GoToUsersPage();
			isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__","Active"), Action.SelectCheckbox);
			isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__","Inactive"), Action.SelectCheckbox);
			//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='userListTable table data-grid user-list-table']/tbody/tr[1]/td[1]"))); 
			isEventSuccessful=searchUser(email);
			if(!isEventSuccessful){
				strActualResult="User is not created in DC UI";
				apiReporter.apiPassBlock("User is not present at DC UI with Email:"+email);
				isEventSuccessful=true;
			}
			else{
				strActualResult="User is available in DC UI";
				apiReporter.apiErrorBlock("User is present in DC UI");
				isEventSuccessful=false;
			}
			reporter.ReportStep("Verifying if newly added user is their in DC UI", "User Should be their in UI", strActualResult, isEventSuccessful);
			//GoToFirstUserDetailsPage();

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
