package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2186
 */
public class _162_Verify_First_Name_and_Last_Name_are_displayed_below_the_user_icon extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";

	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		//*************************************************************//
		//Step 1 : Login to deviceConnect.
		//*************************************************************//            
		isEventSuccessful = LoginToDC(EmailAddress, Password);
		if (isEventSuccessful)
		{
			strActualResult = "User " + EmailAddress + " is logged in to device connect. ";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep("Login to deviceConnect", "User should gets logged in.", strActualResult, isEventSuccessful);

		//*************************************************************//
		//Step 2 : Go to 'Users' page
		//*************************************************************//
		isEventSuccessful = GoToUsersPage();

		//*************************************************************//
		//Step 3 : Verify First Name and Last Name are displayed for each user
		//*************************************************************//            
		isEventSuccessful = VerifynUsersPage("firstname", "exist", "list");
		if (isEventSuccessful)
		{
			isEventSuccessful = VerifynUsersPage("lastname", "exist", "list");
			if (!isEventSuccessful)
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep("Verify First Name and Last Name are displayed.", "Fields - First Name, Last Name should be displayed.", strActualResult, isEventSuccessful);
	}
}