package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;


public class _157_Verify_a_Confirm_Password_field_is_displayed_under_the_Password_field extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";

	public final void testScript()
	{
		//*************************************************************//
		//Step 1 : Login to deviceConnect.
		//*************************************************************//
		isEventSuccessful = Login();

		//*************************************************************//
		//Step 2 : Go to 'Users' page
		//*************************************************************//
		isEventSuccessful = GoToUsersPage();

		//*************************************************************//
		//Step 3 : Click on 'Edit' button
		//*************************************************************//
		isEventSuccessful = GoToSpecificUserDetailsPage(dicCommon.get("EmailAddress"));
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("txtConfirmPassword", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "There is a Confirm Password field displayed.";
			}
			else
			{
				strActualResult = "Confirm Password field is not displayed.";
			}
		}
		else
		{
			strActualResult = "User Details page not displayed on clicking on 'UserName'";
		}

		reporter.ReportStep("Click on 'user name'.", "There should be a Confirm Password field displayed.", strActualResult, isEventSuccessful);
	}
}