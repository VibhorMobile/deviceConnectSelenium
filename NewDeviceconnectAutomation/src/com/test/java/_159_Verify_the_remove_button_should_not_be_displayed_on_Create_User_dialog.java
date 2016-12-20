package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2185
 */
public class _159_Verify_the_remove_button_should_not_be_displayed_on_Create_User_dialog extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";

	public final void testScript()
	{
		//*************************************************************//
		//Step 1 : Login to deviceConnect with admin credentials.
		//*************************************************************//
		isEventSuccessful = Login();

		//*************************************************************//
		//Step 2 : Go to 'Create User' page
		//*************************************************************//
		isEventSuccessful = GoToUsersPage();
		
		//*************************************************************//
		//Step 3 : Go to 'Create User' page
		//*************************************************************//
		isEventSuccessful = PerformAction("btnCreateUser", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("txtConfirmPassword", Action.WaitForElement);
			if (isEventSuccessful)
			{
				strActualResult = "'Create User' page is displayed.";
			}
			else
			{
				strActualResult = "'Create User' page is not displayed on clicking on 'Create User' button.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Create User' button.";
		}

		reporter.ReportStep("Go to 'Create User' page", "'Create User' page should gets opened.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}

		//*************************************************************//
		//Step 3 : Verify 'Remove' button does not exist on 'Create User' page 
		//*************************************************************//
		isEventSuccessful = PerformAction("lnkRemove",Action.isDisplayed);
		if (!isEventSuccessful)
		{
			strActualResult = "'Remove' button does not exist on 'Create User' page.";
		}
		else
		{
			strActualResult = "'Remove' button exists on 'Create User' page.";
		}
		reporter.ReportStep("Verify 'Remove' button does not exist on 'Create User' page.", "'Remove' button should not exist on 'Create User' page.", strActualResult, !isEventSuccessful);
	}
}