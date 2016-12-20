package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2191
 */
public class _379_Verify_that_if_the_user_clicks_Cancel_go_back_to_page extends ScriptFuncLibrary
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
		
		/*STEP 1 : Login to DeviceConnect with Valid login Credentials*/
		isEventSuccessful = Login();
				
		/*STEP 2 : Go To System Page*/
		isEventSuccessful = GoToSystemPage();
		
		
		// STEP 3 : Click on 'Restart Services' button.
		strstepDescription = "Click on 'Restart Services' button.";
		strexpectedResult = "Confirmation message should appear :::    'About to restart all services. All active user and device sessions will be disconnected.  This may cause a generic error message in any browser running an instance of deviceConnect until the web site is restarted.' 'Continue' 'Cancel'";
		isEventSuccessful = PerformAction("btnRestartServices", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleConfirmDisableMsg", Action.WaitForElement);
			if (isEventSuccessful)
			{
				warningMsg = GetTextOrValue("eleConfirmDisableMsg", "text");
				isEventSuccessful = warningMsg.contains("All services will be restarted. All active user and device sessions will be disconnected. This may cause a generic error message in any browser running deviceConnect until the web site is restarted.");
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

		// STEP 4 : Verify 'Cancel' button on confirmation pop-up.
		strstepDescription = "Click on Cancel' button on confirmation pop-up.";
		strexpectedResult = "User should be navigated back to 'System' page.";
		isEventSuccessful = PerformAction("btnCancel", Action.isDisplayed);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnCancel", Action.Click);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleConfirmDisableMsg", Action.isNotDisplayed);
				if (isEventSuccessful)
				{
					isEventSuccessful = PerformAction("lnkSystemMgmnt", Action.isDisplayed);
					if (isEventSuccessful)
					{
						strActualResult = "Confirmation pop-up disappeared and user is navigated back to 'System' page.";
					}
					else
					{
						strActualResult = "Confirmation pop-up disappeared but user is not nvaigated back to 'System' page.";
					}
				}
				else
				{
					strActualResult = "Warning message did not disappear after clicking on 'Cancel' button.";
				}
			}
			else
			{
				strActualResult = "Unable to click on 'Cancel' button.";
			}
		}
		else
		{
			strActualResult = "Cancel button is not displayed on the confirmation pop-up.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}