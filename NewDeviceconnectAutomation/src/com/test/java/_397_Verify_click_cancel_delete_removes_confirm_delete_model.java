package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2169
 */

public class _397_Verify_click_cancel_delete_removes_confirm_delete_model extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String replace = "", strActualResult = "";

	public final void testScript()
	{
		//Step 1 - Launch deviceConnect and verify homepage.
		isEventSuccessful = Login();

		
		//////////////////////////////////////////////////////////////////////////
		//Step 2 - Navigate to Applications Page
		//////////////////////////////////////////////////////////////////////////
		
		isEventSuccessful = GoToApplicationsPage();	
		
		//Step 4 - Click on delete button in front of first application
		isEventSuccessful = PerformAction("eleInstallAppDropdown", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleDeleteOption_AppPage", Action.Click);
			if (isEventSuccessful)
			{
				//Verifying confirmation popup is opened.
				replace = dicOR.get("eleDialogDeleteAll_ApplicationPage").replace("__EXPECTED_HEADER__", "Delete all");
				isEventSuccessful = PerformAction(replace, Action.WaitForElement, "5");
				if (isEventSuccessful)
				{
					strActualResult = "'Confirm Delete' popup is opened.";
				}
				else
				{
					strActualResult = "Clicked on 'Delete' button. 'Confirm Delete' popup is not opened.";
				}
			}
			else
			{
				strActualResult = "Could not click on 'Delete' option.";
			}
		}
		else
		{
			strActualResult = "Not able to Click on Install App dropdown.";
		}
		reporter.ReportStep("Confirm Delete Popup", "Verify Confirm Delete Popup", strActualResult, isEventSuccessful);

		//Step 5 - Click cancel on 'Confirm Delete' popup
		isEventSuccessful = PerformAction("btnCancel", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction(replace, Action.isNotDisplayed) && PerformAction("eleApplicationsHeader", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "User is returned to the application details screen.";
			}
			else
			{
				strActualResult = "User is not return to the application details screen.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Cancel'.";
		}
		reporter.ReportStep("Click Cancel.", "User should return to the application details screen.", strActualResult, isEventSuccessful);
	}
}