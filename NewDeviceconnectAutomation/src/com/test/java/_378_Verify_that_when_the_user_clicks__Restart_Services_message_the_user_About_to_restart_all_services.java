package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2191
 */
public class _378_Verify_that_when_the_user_clicks__Restart_Services_message_the_user_About_to_restart_all_services extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String warningMsg = "";

	public final void testScript()
	{
		// Step 1 : login to deviceConnect with valid user credentials
		isEventSuccessful = Login();
		
		// Step 2 : Go to System page
		isEventSuccessful = GoToSystemPage();

		// Step 3 : Click on 'Restart Services' button.
		strstepDescription = "Click on 'Restart Services' button.";
		strexpectedResult = "Confirmation message should appear :::    'About to restart all services. All active user and device sessions will be disconnected.  This may cause a generic error message in any browser running an instance of deviceConnect until the web site is restarted.' 'Continue' 'Cancel'";
		isEventSuccessful = PerformAction("btnRestartServices", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleConfirmDisableMsg", Action.WaitForElement);
			if (isEventSuccessful)
			{
				warningMsg = GetTextOrValue("eleConfirmDisableMsg", "text");
				isEventSuccessful = warningMsg.contains("All services will be restarted. All active user and device sessions will be disconnected. This may cause a generic error message in any browser running deviceConnect until the web site is restarted");
				if (isEventSuccessful)
				{
					strActualResult = "Correct confirmation message is displayed.";
				}
				else
				{
					strActualResult = "Confirmation message is not correct. Actual message is :::  '" + warningMsg + "'. ";
				}
			}
			else
			{
				strActualResult = "Confirmation pop up not displayed after clicking on Restart Services button.";
			}
		}
		else
		{
			strActualResult = "Unable to click on 'Restart Services' button.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Step 4 : Verify 'Cancel' button on confirmation pop-up.
		strstepDescription = "Verify 'Cancel' button on confirmation pop-up.";
		strexpectedResult = "'Cancel' button should be displayed on the confirmation pop-up.";
		isEventSuccessful = PerformAction("btnCancel", Action.isDisplayed);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnCancel", Action.isEnabled);
			if (isEventSuccessful)
			{
				strActualResult = "'Cancel' button is displayed on confirmation pop-up.";
			}
			else
			{
				strActualResult = "'Cancel' button is not enabled.";
			}
		}
		else
		{
			strActualResult = "Cancel button is not displayed on the confirmation pop-up.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Step 5 : Verify 'Continue' button on confirmation pop-up.
		strstepDescription = "Verify 'Continue' button on confirmation pop-up.";
		strexpectedResult = "'Continue' button should be displayed on the confirmation pop-up.";
		isEventSuccessful = PerformAction("btnContinue_RestartService", Action.isDisplayed);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnContinue_RestartService", Action.isEnabled);
			if (isEventSuccessful)
			{
				strActualResult = "'Continue' button is displayed on confirmation pop-up.";
			}
			else
			{
				strActualResult = "'Continue' button is not enabled.";
			}
		}
		else
		{
			strActualResult = "Continue button is not displayed on the confirmation pop-up.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}