package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: Third week of February
 * Last Modified Date: Same as creation date
 * Jira Test Case Id: QA-12,158
 */

public class _622_Verify_that_the_testers_are_not_llowed_to_edit_their_active_status_This_function_is_not_available_to_tester_user extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "", devicename;
	Object[] values = new Object[2];
	
	
	public final void testScript()
	{
	
		//Step 1 - Login to deviceConnect
		isEventSuccessful = Login(dicCommon.get("testerEmailAddress"),dicCommon.get("testerPassword")); 
				
		// Step 2 : Select 'Manage your account' option from menu dropdown to go to own user details.
		strStepDescription = "Select 'Manage your account' option from menu dropdown to go to own user details.";
		strExpectedResult = "User's own details page should be opened.";
		isEventSuccessful = selectFromMenu("Manage your account", "txtConfirmPassword");
		if (isEventSuccessful)
		{
			strActualResult = "user details page opened successfully.";
		}
		else
		{
			strActualResult = "selectFromMenu() -- " + strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//Step 3 : Verify_that_the_testers_are_not_llowed_to_edit_their_active_status
						
		isEventSuccessful = !PerformAction("chkActive_CreateUser", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "User is not able to see active field.";
		}
		else
		{
			strActualResult = "User is able to see active field for his own account.";
		}
		reporter.ReportStep("Verify tester is not able to see active role field.", "tester user should not be able to see active field.", strActualResult, isEventSuccessful);


	}
}