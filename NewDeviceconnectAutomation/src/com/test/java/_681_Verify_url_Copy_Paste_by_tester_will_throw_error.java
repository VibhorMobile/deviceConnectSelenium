package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: 08-June-2016
 * Last Modified Date: Same as creation date
 * Jira Test Case ID: QA-475
 */

public class _681_Verify_url_Copy_Paste_by_tester_will_throw_error extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strexpectedResult = "", strActualResult = "",strstepDescription="";
	String [] android={"Android"};
	
	public final void testScript()
	{
		
		//Variables from data sheet
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");
		
		//*******************************//
		// Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to users page //
		//**********************************************************//   
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToUsersPage();
		}
		
		// Step 3 : Click on 'Edit' button for admin user.
		if(isEventSuccessful)
		{
			if( PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", "admin"), Action.WaitForElement))
			{
				isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", "admin"), Action.Click);
				if (isEventSuccessful)
				{
					strActualResult = "Admin user page opened";
				}
				else
				{
					strActualResult ="Unable to open admin user page.";
				}
			}
			else
			{
				strActualResult ="Unable to find admin user page.";
			}
			reporter.ReportStep("Verify admin user page opened.", "Admin user page should be opened.", strActualResult, isEventSuccessful);
		}
		
		// Step 4: Get User's URL
		if(isEventSuccessful)
		{
			isEventSuccessful=PerformAction("browser",Action.GetURL);
		}
		
		//****************************************************************************************//
		// Step 5 - Logout **//
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
		// Step 6 : Login to deviceConnect with test user.
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

		//**********************************************************//
		// Step 7 - Navigate to copied URL and get the error //
		//**********************************************************//   
		if(isEventSuccessful)
		{
			strstepDescription = "Verify when user navigate to copied URL will get the error.";
			strexpectedResult = "User should get the error on navigating..";
			isEventSuccessful = PerformAction("browser",Action.Navigate,dicOutput.get("URL"));
			if(isEventSuccessful)
			{
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