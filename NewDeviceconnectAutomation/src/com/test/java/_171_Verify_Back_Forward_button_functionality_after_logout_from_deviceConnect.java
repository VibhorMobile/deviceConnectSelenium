package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1133
 */

public class _171_Verify_Back_Forward_button_functionality_after_logout_from_deviceConnect extends ScriptFuncLibrary
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
		//*************************************************************//
		// Step 1 : Login to deviceConnect with admin credentials
		//*************************************************************//
		isEventSuccessful = Login();

		//*************************************************************//
		//Step 2 : Go to Users Page
		//*************************************************************//
		isEventSuccessful = GoToUsersPage();

		//*************************************************************//
		// Step 3 :Log out of the application.
		//*************************************************************//
		strstepDescription = "Log out of the application";
		strexpectedResult = "User should return to the deviceConnect login screen.";
		isEventSuccessful = Logout();
		if (isEventSuccessful)
		{
			strActualResult = "User returns to the deviceConnect login screen.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//
		//Step 4 : Click Back button on the browser
		//*************************************************************//
		strstepDescription = "Click Back button on the browser.";
		strexpectedResult = "User should stay to the deviceConnect login screen.";
		waitForPageLoaded();
		isEventSuccessful = PerformAction("browser",Action.Back);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = PerformAction("inpEmailAddress", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "User stays to the deviceConnect login screen.";
			}
			else
			{
				strActualResult = "User doesnot stay to the deviceConnect login screen.";
			}
		}
		else
		{
			strActualResult = "Could not click on back button.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//
		//Step 5 : Click Forward button on the browser
		//*************************************************************//
		strstepDescription = "Click Forward button on the browser.";
		strexpectedResult = "User should stay to the deviceConnect login screen.";
		waitForPageLoaded();
		isEventSuccessful = PerformAction("browser", Action.Forward);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = PerformAction("inpEmailAddress", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "User stays to the deviceConnect login screen.";
			}
			else
			{
				strActualResult = "User doesnot stay to the deviceConnect login screen.";
			}
		}
		else
		{
			strActualResult = "Could not click on back button.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}