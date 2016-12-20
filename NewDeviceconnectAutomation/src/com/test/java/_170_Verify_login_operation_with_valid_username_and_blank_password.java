package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case ID: QA-2173
 */
public class _170_Verify_login_operation_with_valid_username_and_blank_password extends ScriptFuncLibrary
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
		String Password = "";

		//*************************************************************//
		//Step 1 - Launch deviceConnect and verify homepage.
		//*************************************************************//
		genericLibrary.LaunchWebDriver();
		isEventSuccessful = PerformAction("imgDCLogo", Action.Exist);
		if (isEventSuccessful)
		{
			strActualResult = "Navigated to " + dicCommon.get("ApplicationURL") + ". deviceConnect homepage is opened.";
		}
		else
		{
			strActualResult = "deviceConnect homepage is not opened.";
		}
		reporter.ReportStep("Launch deviceConnect and verify homepage.", "deviceConnect homepage should be opened.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}

		//*************************************************************//
		// Step 2 : Submit valid username and blank password.
		//*************************************************************//
		strstepDescription = "Submit valid username and blank password.";
		strexpectedResult = "A warning message should be displayed.";
		strActualResult = "";
		if (!PerformAction("inpEmailAddress", Action.Type, EmailAddress))
		{
			strActualResult = "Could not enter Email Address " + EmailAddress + " in correponding field.";
		}
		if (!PerformAction("inpPassword", Action.Type, Password))
		{
			strActualResult = strActualResult + "Could not enter Email Address " + Password + " in correponding field.";
		}
		if (!PerformAction("btnLogin", Action.Click))
		{
			strActualResult = strActualResult + "Could not click on 'Login' button.";
		}
		isEventSuccessful = PerformAction("eleLoginWarning", Action.WaitForElement, "3");
		if (isEventSuccessful)
		{
			String strMsg = GetTextOrValue("eleLoginWarning", "text");
			if(strMsg.contains("The username or password is incorrect.") || strMsg.contains("LDAP: The username and/or password is invalid"))
			{
				isEventSuccessful = true;
			}
			else
			{
				isEventSuccessful = false;
			}
			if (isEventSuccessful)
			{
				strActualResult = "Warning message - 'The username or password is incorrect.' is displayed.";
			}
			else
			{
				strActualResult = "Warning message is wrong it is : '" + strMsg + "'";
			}
		}
		else
		{
			strActualResult = "Warning message not displayed even after waiting for 3 seconds.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}