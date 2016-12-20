package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2201
 */
public class _297_Verify_that_If__Admin_user_logout_from_any_specific_page_then_relogin_with_other_Admin_user_shoud_not_navigate_to_the_same_page extends ScriptFuncLibrary
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
		String Password = dicCommon.get("Password");
		String EmailAddressAdmin2 = dicTestData.get("EmailAddress");
		String Password1=dicTestData.get("Password");

		//**************************************************************************//
		// Step 1 : login to deviceConnect with valid user and verify Devices page.
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
		// Step 2 : Go to 'Users' page.
		//**************************************************************************//
		isEventSuccessful = GoToUsersPage();

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

		//**************************************************************************//
		// Step 5 : Login with another admin user and verify Devices page is displayed.
		//**************************************************************************//
		strstepDescription = "Login with another admin user and verify Devices page is displayed.";
		strexpectedResult = "Other admin user should not be navigated to 'Users' page.";
		isEventSuccessful = LoginToDC(EmailAddressAdmin2, Password1, false);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleDevicesTab_Devices", Action.WaitForElement, "10");
			if (!isEventSuccessful)
			{
				strActualResult = "Other admin user is not navigated to 'Devices' page after logging in again."; // screenshot shows which page is opened
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Another admin user is not navigated to same page after logging in.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
	}
}