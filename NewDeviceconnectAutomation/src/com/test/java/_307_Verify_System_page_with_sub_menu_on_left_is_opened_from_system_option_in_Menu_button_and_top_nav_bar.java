package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2202
 */
public class _307_Verify_System_page_with_sub_menu_on_left_is_opened_from_system_option_in_Menu_button_and_top_nav_bar extends ScriptFuncLibrary
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

		// Step 2 : Go to systems page
		strstepDescription = "Go to Systems page using 'Menu' option.";
		strexpectedResult = "Systems page should be displayed";
		isEventSuccessful = navigateToNavBarPages("System", "eleSystemHeader");
		if (isEventSuccessful)
		{
			strActualResult = "Systems page is displayed";
		}
		else
		{
			strActualResult = "Systems page is not displayed";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Step 3 : Verify by default 'System Management' page opens.
		strstepDescription = "Verify by default 'System Management' page opens";
		strexpectedResult = "System page should be opened by default.";
	 //isEventSuccessful = PerformAction("lnkSystemMgmnt", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnSavelogs", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "System management page is displayed by default.";
			}
			else
			{
				strActualResult = "System management page is not displayed by default.";
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
			isEventSuccessful = PerformAction("chkdisableNotif_system", Action.isDisplayed);
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