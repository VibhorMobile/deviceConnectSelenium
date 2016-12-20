package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2184
 */
public class _155_Verify_Logout_will_logout_the_user_and_returns_the_page_to_the_deviceConnect_login_screen extends ScriptFuncLibrary
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
		// Step 1 : Login to deviceConnect as Admin User
		//*************************************************************//
		isEventSuccessful = Login();
		//*************************************************************//
		// Step 2 :Log out of the application.
		//*************************************************************//
		strstepDescription = "Log out of the application";
		strexpectedResult = "Logout will logout the user and returns the page to the deviceConnect login screen.";
		isEventSuccessful = Logout();
		if (isEventSuccessful)
		{
			if (PerformAction("inpEmailAddress", Action.isDisplayed))
			{
				strActualResult = "User is returned to the deviceConnect login screen.";
			}
			else
			{
				strActualResult = "User is not returned to the deviceConnect login screen.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}