package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-456
 */

public class _142_Verify_that_Save_button_exists_on_the_Create_User_dialog extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful = Login();
		
		//***********************************************************************************************//                     
		// Step 2 : Go to User Details page for 'Create User' and verify that 'Save' button is displayed.
		//***********************************************************************************************//                     
		strstepDescription = "Go to User Details page for 'Create User' and verify that 'Save' button is displayed.";
		strexpectedResult = "'Save' button should be displayed.";
		isEventSuccessful= GoToUsersPage();
		isEventSuccessful = PerformAction("btnCreateUser", Action.Click);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("txtConfirmPassword", Action.WaitForElement);
				if (isEventSuccessful)
				{
					isEventSuccessful = PerformAction("btnSave", Action.WaitForElement, "15");
					if (!isEventSuccessful)
					{
						strActualResult = "'Save' button is not displayed on 'Create User' page.";
					}
				}
				else
				{
					strActualResult = "User Details page not displayed after clicking on 'Create User' button.";
				}
			}
			else
			{
				strActualResult = "'Create User' button does not exist on Users page.";
			}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Save' button displayed successfully on 'Create User' page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
	}
}