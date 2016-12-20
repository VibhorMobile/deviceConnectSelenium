package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: Third week of February
 * Last Modified Date: Same as creation date
 * Jira Test Case Id:QA-558,50
 */

public class _623_Verify_that_the_testers_are_not_allowed_to_edit_their_Email_or_user_name extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";

	
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
				
		//isEventSuccessful = !PerformAction("//div/p[@class='form-control-static']", Action.isEnabled);
		String attvalue = getAttribute("//div[@class='col-sm-9']/p", "class");
		if(attvalue.contains("form-control-static"))
		{
			if(isEventSuccessful)
			{
				strActualResult = "Test User is not able to edit their_Email_or_user_name";
			}
		}
		
		else
		{
			strActualResult = "Test User is able to edit their_Email_or_user_name";
		}
		
		 reporter.ReportStep("Verify Test User is not able to edit their_Email_or_user_name", "Test User should not be able to edit their_Email_or_user_name", strActualResult, isEventSuccessful);

	}	
}
