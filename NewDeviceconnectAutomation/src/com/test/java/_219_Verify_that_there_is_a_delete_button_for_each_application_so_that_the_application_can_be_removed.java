package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2194
 */
public class _219_Verify_that_there_is_a_delete_button_for_each_application_so_that_the_application_can_be_removed extends ScriptFuncLibrary
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

		///////////////////////////////////////////////////////////////////////////////////////////
		// Step 3 : Go to Applications page and verify delete button against each application name.
		///////////////////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Verify delete button against each application name.";
		strexpectedResult = "Delete button should be present against each application name.";
		isEventSuccessful = GoToApplicationsPage();
		if (isEventSuccessful)
		{
			isEventSuccessful = !PerformAction("class=message", Action.isDisplayed);
			if (isEventSuccessful)
			{
				isEventSuccessful = VerifybtnDeleteOnApplicationsPage();
				if (!isEventSuccessful)
				{
					strActualResult = "VerifybtnDeleteOnApplicationsPage() -- " + strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "No applications loaded in the system";
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
				return;
			}
		}
		else
		{
			strActualResult = "selectFromMenu() -- " + strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Delete button is displayed against each application in the list.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

	}
}