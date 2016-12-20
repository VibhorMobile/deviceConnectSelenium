package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2188
 */
public class _168_Verify_that_the_passwords_errors_are_not_shown_by_default_while_modifying_user extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		String userPassword = dicTestData.get("Password");
		String emailID = dicTestData.get("userName");
		String firstName = dicTestData.get("firstName");
		String lastName = dicTestData.get("lastName");
		String UserNameXpath = dicOR.get("eleUserName") + "'" + emailID + "']";
		String UserNameEditBtnXpath = UserNameXpath + "//..//..//a";

		//**************************************************************************//
		// Step 1 : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		strstepDescription = "Login to deviceConnect with valid username and password and verify Devices page.";
		strexpectedResult = "User should be logged in and navigated to Devices page.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with user: " + EmailAddress + " and navigated to Devices page.", "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//**************************************************************************//
		// Step 2 : Go to Users page and click on the Edit user button.
		//*************************************************************
		isEventSuccessful = GoToUsersPage();
		
		//**************************************************************************//
		// Step 3 : Go to specific Users page
		//**************************************************************************//
		isEventSuccessful = GoToSpecificUserDetailsPage(emailID);

		//**************************************************************************//
		// Step 3 : Enter password in 'Password' field and click on confirm password field and verify "Passwords do not match" error should not be shown by default as user has still not entered anything in the confirm password field.
		//**************************************************************************//
		strstepDescription = "Enter password in 'Password' field and click on confirm password field and verify 'Passwords do not match' error should not be shown by default as user has still not entered anything in the confirm password field.";
		strexpectedResult = "'Passwords do not match' error should not be shown by default as user has still not entered anything in the confirm password field.";
		isEventSuccessful = PerformAction("txtPassword", Action.Type, userPassword);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("txtConfirmPassword", Action.Click);
			if (isEventSuccessful)
			{
				//isEventSuccessful = !PerformAction(dicOR["txtConfirmPassword"] + "//..//span", Action.isDisplayed);
				isEventSuccessful = !(GetTextOrValue(dicOR.get("txtConfirmPassword") + "//..//span", "text")).contains("Passwords do not match.");
				if (!isEventSuccessful)
				{
					strActualResult = "'Passwords do not match' error is shown by default";
				}
			}
			else
			{
				strActualResult = "Unable to click on Confirm password textbox.";
			}
		}
		else
		{
			strActualResult = "Unable to type password in Password field.";
		}


		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Passwords do not match' error is not shown by default.", "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//**************************************************************************//
		// Step 4 : Go to 'Users' Page
		//**************************************************************************//
		isEventSuccessful = GoToUsersPage();
		
		//**************************************************************************//
		// Step 5 : Go to 'Users' page and click on 'Create User'.
		//**************************************************************************//
		strstepDescription = "Go to 'Users' page and click on 'Create User'.";
		strexpectedResult = "Create User page should be displayed.";

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
		
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Create User page is displayed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//**************************************************************************//
		// Step 6 : Verify "Password cannot be blank" and "Passwords do not match" errors should not be shown by default as user has still not entered anything.
		//**************************************************************************//
		strstepDescription = "Verify 'Password cannot be blank' and 'Passwords do not match' errors should not be shown by default as user has still not entered anything.";
		strexpectedResult = "'Password cannot be blank' and 'Passwords do not match' errors should not be shown by default.";
		isEventSuccessful = !PerformAction(dicOR.get("txtConfirmPassword") + "//..//span", Action.isDisplayed); //GetTextOrValue(dicOR["txtConfirmPassword"] + "//..//span", "text").Contains("Passwords do not match.") // check text
		if (isEventSuccessful)
		{
			isEventSuccessful = !PerformAction(dicOR.get("txtPassword") + "//..//span", Action.isDisplayed); // (GetTextOrValue(dicOR["txtPassword"] + "//..//span", "text")).Contains("Passwords do not match.") // verify error text
			if (!isEventSuccessful)
			{
				strActualResult = "Password cannot be blank error is shown by default";
			}
		}
		else
		{
			strActualResult = "'Passwords do not match' error is shown by default";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Password cannot be blank' and 'Passwords do not match' errors are not shown by default.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
	}
}