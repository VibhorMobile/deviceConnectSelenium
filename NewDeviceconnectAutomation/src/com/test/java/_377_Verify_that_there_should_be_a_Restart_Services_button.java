package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2191
 */
public class _377_Verify_that_there_should_be_a_Restart_Services_button extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		
		/*Step 1: Login to Deviceconnect with Valid Credentials */
		isEventSuccessful= Login();
		
		// Step 1 : login to deviceConnect with valid user and verify Devices page.
		/*strstepDescription = "Login to deviceConnect with valid username and password and verify Devices page.";
		strexpectedResult = "User should be logged in and navigated to Devices page.";
		isEventSuccessful = LoginToDC();
		if (isEventSuccessful)
		{
			strActualResult = "User - " + dicCommon.get("EmailAddress") + " is logged in to deviceConnect.";
		}
		else
		{
			strActualResult = "User - " + dicCommon.get("EmailAddress") + " is not logged in to deviceConnect.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);*/

		
		/*Step 2 : Navigate to System Page*/
		isEventSuccessful = GoToSystemPage();
		
		// Step 2 : Go to system page
		/*strstepDescription = "Go to System page";
		strexpectedResult = "System page should be displayed";
		isEventSuccessful = selectFromMenu("System", "eleSystemHeader");
		if (isEventSuccessful)
		{
			strActualResult = "System page is displayed";
		}
		else
		{
			strActualResult = "selectFromMenu()-- " + strErrMsg_AppLib;
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);*/

		//// Step 3 : Verify Logs option
		//strstepDescription = "Verify Logs option";
		//strexpectedResult = "Logs option should be present";
		//isEventSuccessful = PerformAction("lnkLogs", Action.Exist);
		//if (isEventSuccessful)
		//    strActualResult = "Logs option is displayed";
		//else
		//    strActualResult = "Logs option is not displayed";

		//reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);


		//// Step 4 : Verify System Services option
		//strstepDescription = "Verify System Services option";
		//strexpectedResult = "System Services option should be present";
		//isEventSuccessful = PerformAction("btnSystemManagement", Action.Exist);
		//if (isEventSuccessful)
		//    strActualResult = "System Services option is displayed";
		//else
		//    strActualResult = "System Services option is not displayed";

		//reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Step 3 : Click on System Services option
		strstepDescription = "Verify that Restart services button is displayed.";
		strexpectedResult = "Restart Services button should be displayed on the page.";

		isEventSuccessful = PerformAction("btnRestartServices", Action.WaitForElement, "5");
		if (isEventSuccessful)
		{
			strActualResult = "Restart Services button is displayed on the page.";
		}
		else
		{
			strActualResult = "Restart Services button does not exist.";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}