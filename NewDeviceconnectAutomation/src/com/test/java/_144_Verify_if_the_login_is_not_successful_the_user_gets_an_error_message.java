package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2173
 */
public class _144_Verify_if_the_login_is_not_successful_the_user_gets_an_error_message extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "";
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicTestData.get("Password");

		//*************************************************************//                 
		//Step 1 - Launch deviceConnect and verify homepage.
		//*************************************************************//    
		genericLibrary.LaunchWebDriver();
		isEventSuccessful = PerformAction("imgDCLogo", Action.Exist);
		if (isEventSuccessful)
		{
			strActualResult = "Navigated to " + dicCommon.get("ApplicationURL") + ". deviceConnect homepage is opened.";
		}
		else
		{
			strActualResult = "deviceConnect homepage is not opened.";
		}
		reporter.ReportStep("Launch deviceConnect and verify homepage.", "deviceConnect homepage should be opened.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}

		//******************************************************************************************//                 
		// Step 2 : Enter admin user but invalid password and verify error message on login screen.
		//*****************************************************************************************//                 
		strstepDescription = "Enter admin user but invalid password and verify error message on login screen.";
		strexpectedResult = "User should not be logged in and error message should be displayed.";

		if (!PerformAction("inpEmailAddress", Action.Type, EmailAddress))
		{
			strActualResult = "Could not enter Email Address " + EmailAddress + " in correponding field.";
		}
		if (!PerformAction("inpPassword", Action.Type, Password))
		{
			strActualResult = "Could not enter Email Address " + Password + " in correponding field.";
		}
		if (!PerformAction("btnLogin", Action.Click))
		{
			strActualResult = "Could not click on 'Login' button.";
		}
		isEventSuccessful = !PerformAction("btnMenu", Action.Exist);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleLoginWarning", Action.WaitForElement);
			if (!isEventSuccessful)
			{
				strActualResult = "Error message is not displayed when login is not successful.";
			}
		}
		else
		{
			strActualResult = "Logged in with email: " + EmailAddress + " and invalid password: " + Password;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Error message is displayed on login screen for email: " + EmailAddress + " and invalid password: " + Password, "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
	}
}