package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2201
 */
public class _290_Verify_if_user_logout_or_the_application_times_out_when_user_log_back_in_the_application_should_reload_the_last_page_he_she_were_on extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		///**************************************************************************//
		//Step 1 - : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		isEventSuccessful = Login();


		//**************************************************************************//
		// Step 2 : Go to 'Users' page.
		//**************************************************************************//
		strstepDescription = "Go to 'Users' page.";
		strexpectedResult = "User details page should be opened for admin.";
		isEventSuccessful = GoToUsersPage();
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Users page displayed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
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

		//**************************************************************************//
		// Step 5 : Login again with same user and verify Users page is displayed.
		//**************************************************************************//
		strstepDescription = "Login again with same user and verify Users page is not being displayed.";
		strexpectedResult = "Users page should not be displayed & Device index page always should be displayed.";
		isEventSuccessful = Login();
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleDevicesHeader", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Device index page displayed .";
			}
		}
		else
		{
			strActualResult = "User navigated to Users page";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "User is not navigated to same page after logging in again & Device index page is displayed.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
	}
}