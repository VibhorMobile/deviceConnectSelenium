package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2203
 */

public class _259_Verify_that_disabled_accounts_are_not_allowed_to_login_into_DeviceConnect extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";
	private String text = "";
	String [] role={};

	public final void testScript()
	{
		 //Step 1 - Login to deviceConnect
		 isEventSuccessful = Login(); 
			
		//Step 2 : Go to 'Users' page
			isEventSuccessful = navigateToNavBarPages("Users", "eleUsersHeader");
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("btnCreateUser", Action.isDisplayed);
				if (isEventSuccessful)
				{
					strActualResult = "Users page is opened.";
				}
				else
				{
					strActualResult = "Users page is not opened.";
				}
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
			reporter.ReportStep("Go to 'Create User' page", "'Users' page should gets opened.", strActualResult, isEventSuccessful);
			if (!isEventSuccessful)
			{
				return;
			}

			//Step 3 : Add a new disabled user
			isEventSuccessful = createUser("TestFirstSeleniumName", "TestLastSeleniumName", "deviceconnect",role, false, true);
			if (isEventSuccessful)
			{
				strActualResult = "User " + dicOutput.get("EmailID") + " is created.";
			}
			else
			{
				strActualResult = "createUser()--" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Add a new user.", "A new user should gets created.", strActualResult, isEventSuccessful);

		
		//Step 4 - Logout from application.
		isEventSuccessful = Logout();
		if (isEventSuccessful)
		{
			strActualResult = "Successfully logged out from application";
		}
		else
		{
			strActualResult = "Logout() -- " + strErrMsg_AppLib;
		}

		reporter.ReportStep("Logout from application.", "Login page should be displayed", strActualResult, isEventSuccessful);

		//Step 5 - Login with disabled account.
		strErrMsg_AppLib = "";
		if (!PerformAction("inpEmailAddress", Action.Type, dicOutput.get("EmailID")))
		{
			strActualResult = "Could not enter Email Address " + dicOutput.get("EmailID") + " in correponding field.";
		}
		if (!PerformAction("inpPassword", Action.Type, "deviceconnect"))
		{
			strActualResult = strErrMsg_AppLib + "; Could not enter Email Address " + "" + " in correponding field.";
		}
		if (!PerformAction("btnLogin", Action.Click))
		{
			strActualResult = strErrMsg_AppLib + "; Could not click on 'Login' button.";
		}
		isEventSuccessful = !PerformAction("btnMenu", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Disabled user: '" + dicOutput.get("EmailID") + "' is not able to log in to deviceConnect.";
		}
		else
		{
			strActualResult = "Logged in with disabled user: '" + dicOutput.get("EmailID");
		}

		reporter.ReportStep("Login with disabled account.", "User should not be able to login to deviceConnect", strActualResult, isEventSuccessful);
	}
}