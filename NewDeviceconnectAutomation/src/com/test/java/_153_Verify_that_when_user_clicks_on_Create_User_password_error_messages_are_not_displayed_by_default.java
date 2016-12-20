package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2183
 */
public class _153_Verify_that_when_user_clicks_on_Create_User_password_error_messages_are_not_displayed_by_default extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "";
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		//*************************************************************//
		//Step 1 : login to deviceConnect with admin user.
		//*************************************************************//
		isEventSuccessful = Login();
		
		//*************************************************************//
		//Step 2 : Go to 'Create User' page
		//*************************************************************//
		isEventSuccessful = GoToUsersPage();
		PerformAction("btnCreateUser", Action.WaitForElement,"5");
		isEventSuccessful = PerformAction("btnCreateUser", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("txtConfirmPassword", Action.isDisplayed);
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
		//Step 3 : Verify error messages are not displayed by default
		//*************************************************************//
		isEventSuccessful = PerformAction("elePwdValidationMsg", Action.isNotDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Error messages are not displayed by default.";
		}
		else
		{
			strActualResult = "Error messages are displayed by default." + "\r\n Reason (GenLib error): '" + strErrMsg_GenLib + "'.";
		}
		reporter.ReportStep("Verify error messages are not displayed by default", "Error messages should not be displayed by default", strActualResult, isEventSuccessful);
	}
}