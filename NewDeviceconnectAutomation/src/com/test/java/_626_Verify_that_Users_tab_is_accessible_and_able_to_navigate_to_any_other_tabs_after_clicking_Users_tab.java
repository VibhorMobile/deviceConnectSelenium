package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: Third week of February
 * Last Modified Date: Same as creation date
 * Jira Test Case Id-
 */

public class _626_Verify_that_Users_tab_is_accessible_and_able_to_navigate_to_any_other_tabs_after_clicking_Users_tab extends ScriptFuncLibrary
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
		
		
		isEventSuccessful = GoToSystemPage();
		
		if(isEventSuccessful)
		{
			strActualResult = "Users_tab_is_accessible_and is able_to_navigate_to_any_other_tabs_after_clicking_Users_tab";
		}
		else
		{
			strActualResult = "Users_tab_is_accessible_and is not able_to_navigate_to_any_other_tabs_after_clicking_Users_tab";
		}
		reporter.ReportStep("Verify_that_Users_tab_is_accessible_and_able_to_navigate_to_any_other_tabs_after_clicking_Users_tab", "Users_tab_is_accessible_and should be able_to_navigate_to_any_other_tabs_after_clicking_Users_tab", strActualResult, isEventSuccessful);


  }
}
