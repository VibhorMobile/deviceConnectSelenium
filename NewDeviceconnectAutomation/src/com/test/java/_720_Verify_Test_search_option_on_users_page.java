package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-371
 */
public class _720_Verify_Test_search_option_on_users_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";

	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		
		//*************************************************************//     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//     
		isEventSuccessful = Login();
		
		//*************************************************************//     
		// Step 2 : Go to Users page.
		//*************************************************************//  
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToUsersPage();
		}
		
		//**********************************************************//
		//** Step 3 - Search device using search text box and verify it **********//
		//**********************************************************//
		if(isEventSuccessful)
		{
			searchUser(EmailAddress, "email");
		}
	}

}