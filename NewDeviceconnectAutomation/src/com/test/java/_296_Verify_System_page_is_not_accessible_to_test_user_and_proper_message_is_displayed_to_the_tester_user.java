package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2191
 */
public class _296_Verify_System_page_is_not_accessible_to_test_user_and_proper_message_is_displayed_to_the_tester_user extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");
		String Password = dicCommon.get("Password");
		String SystemHdrXPath = "//span[text()='System']";

		//**************************************************************************//
		//Step 1 - Launch deviceConnect and verify homepage.
		//**************************************************************************//
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

		//**************************************************************************//
		// Step 2 : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		strstepDescription = "Login to deviceConnect with valid username and password and verify Devices page.";
		strexpectedResult = "User should be logged in and navigated to Devices page.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with user: " + EmailAddress + " and navigated to Devices page.", "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//**************************************************************************//
		// Step 3 : Log out and verify login page.
		//**************************************************************************//
		strstepDescription = "Log out and verify login page.";
		strexpectedResult = " User should be logged out and login page should be displayed.";
		isEventSuccessful = Logout();
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "User logged out and login page displayed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		}

		//**********************************************************************************************************************//
		// Step 4 : Login again with test user and verify System option is not there in top header or in 'Menu' dropdown options.
		//*********************************************************************************************************************//
		strstepDescription = "Login again with test user and verify System option is not there in top header or in 'Menu' dropdown options.";
		strexpectedResult = "Test user sould be logged in and 'System' option should not be there in top header or in 'Menu' dropdown options.";
		isEventSuccessful = LoginToDC(testerEmailAddress, testerPassword);
		if (isEventSuccessful)
		{
			// check in top menu bar
			isEventSuccessful = !PerformAction(SystemHdrXPath, Action.isDisplayed);
			if (isEventSuccessful)
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "'System' option is not displayed on the top menu bar.", "Pass");
			}
			else
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "'System' option is displayed on the top menu bar.", "Fail");
			}
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		}
	}
}