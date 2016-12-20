package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Jira Test Case Id: QA-2203
 */
public class _277_Verify_that_there_should_be_status_in_Text_and_in_Symbol_for_Enabled_and_Disabled_users_in_web_UI extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";

	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		if(!isEventSuccessful)
			return;

		//**************************************************************************//
		//Step 2 - Select Users page
		//**************************************************************************//
		isEventSuccessful = GoToUsersPage();
		if(!isEventSuccessful)
			return;
		
		////**************************************************************************//
		////Step 4 - Verify status of user in list view
		////**************************************************************************//    
		
		
			isEventSuccessful = VerifynUsersPage("statusiconandtext", "", "list");

			if (isEventSuccessful)
			{
				strActualResult = "Correct symbol and status text is displayed for all the Users. - List";
			}
			else
			{
				strActualResult = "VerifynUsersPage---" + strErrMsg_AppLib;
			}
		
		reporter.ReportStep("Verify the status of user in list view", "Correct symbol and status text should be displayed for all Users.", strActualResult, isEventSuccessful);


	}
}