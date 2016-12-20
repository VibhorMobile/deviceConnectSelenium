package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-518
 */
public class _726_Verify_create_user_page_is_not_accessible_through_the_UI_and_direct_URL_to_tester_users extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String oldDeviceName="",Appname="";
	String [] role={"zTemporary"};
	
	public final void testScript()
	{
		//Variables from data sheet
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");

		//*********************************//
		// Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();

		//*************************************************************//                     
		// Step 2 : Go to create user page.
		//*************************************************************//
		isEventSuccessful = GoToUsersPage();
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnCreateUser", Action.Click);
			if (isEventSuccessful)
			{
				strActualResult = "Create Users page is opened.";
			}
			else
			{
				strActualResult = "Create Users page is not opened.";
			}
			reporter.ReportStep("Go to 'Create User' page", "'Users' page should gets opened.", strActualResult, isEventSuccessful);
		}
		
		
		// Step 3: Get User's URL
		if(isEventSuccessful)
		{
			isEventSuccessful=PerformAction("browser",Action.GetURL);
		}

		//****************************************************************************************//
		// Step 4 - Logout **//
		//****************************************************************************************//
		strstepDescription = "Verify user logout from application";
		strexpectedResult =  "User should be able to logout.";
		isEventSuccessful=Logout();
		if (isEventSuccessful)
		{
			strActualResult = "User logout from application.";
		}
		else
		{
			strActualResult = "User is not able to logout.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//*************************************************************//                     
		// Step 5 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid test user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(testerEmailAddress, testerPassword);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with " + testerEmailAddress, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
		

		//*************************************************************//                     
		// Step 6 : Go to user page.
		//*************************************************************// 
		strstepDescription = "Verify tester user cannot open users page.";
		strexpectedResult = "Tester user should not be able to open users page.";
		isEventSuccessful=navigateToNavBarPages("Users", "eleUsersHeader");
		if (isEventSuccessful)
		{
			if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
			{
				waitForPageLoaded();
				isEventSuccessful = PerformAction("btnCreateUser", Action.WaitForElement,"20");
			}
			isEventSuccessful = PerformAction("btnCreateUser", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Users page is opened.";
				isEventSuccessful=false;
			}
			else
			{
				strActualResult = "Users page is not opened.";
				isEventSuccessful=true;
			}
		}
		else
		{
			strActualResult = "Users page is not opened";
			isEventSuccessful=true;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		
		//**********************************************************//
		// Step 7 - Navigate to copied URL and get the error //
		//**********************************************************//   
		if(isEventSuccessful)
		{
			strstepDescription = "Verify when user navigate to copied URL will get the error.";
			strexpectedResult = "User should get the error on navigating..";
			isEventSuccessful = PerformAction("browser",Action.Navigate,dicCommon.get("ApplicationURL")+"/#/User/Create");
			if(isEventSuccessful)
			{
				waitForPageLoaded();
				isEventSuccessful=PerformAction("errorPopup",Action.isDisplayed);
				if(isEventSuccessful)
				{
					strActualResult="User got the error on navigating.";
				}
				else
				{
					strActualResult="User did not get the error on navigating.";
				}
			}
			else
			{
				strActualResult="Unable to paste  copied URL";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
	}	
}