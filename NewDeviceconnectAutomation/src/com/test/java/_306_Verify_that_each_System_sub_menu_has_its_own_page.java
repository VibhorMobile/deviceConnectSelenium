package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2202
 */
public class _306_Verify_that_each_System_sub_menu_has_its_own_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		// Step 1 : login to deviceConnect with valid user credentials
		isEventSuccessful = Login();

		// Step 2 : Go to systems page
		isEventSuccessful = GoToSystemPage();

		// Step 3 : Click on 'System Management' link.
		strstepDescription = "Click on 'System Management' link.";
		strexpectedResult = "System page should be opened.";
		isEventSuccessful = PerformAction("lnkSystemMgmnt", Action.Click);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = PerformAction("btnSavelogs", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "System management is displayed";
			}
			else
			{
				strActualResult = "System management page is not displayed";
			}
		}
		else
		{
			strActualResult = "Unable to click on System Management link.";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Step 4 : Click on 'iOS Management' link
		strstepDescription = "Click on iOS Management link.";
		strexpectedResult = "iOS Management page should be displayed";
		isEventSuccessful = PerformAction("lnkiOSMgmnt", Action.Click);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = PerformAction("hdrIOSMgmnt_System", Action.WaitForElement, "10");
			if (isEventSuccessful)
			{
				strActualResult = "iOS Management page is displayed";
			}
			else
			{
				strActualResult = "iOS Management page is not displayed";
			}
		}
		else
		{
			strActualResult = "Unable to click on iOS Management link";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

	}
}