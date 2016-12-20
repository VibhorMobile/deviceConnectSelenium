package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1006
 */
public class _731_Verify_create_another_checkbox_should_not_appear_for_tester_user_while_updating_password extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";
	
	public final void testScript()
	{
		
		//*******************************//
		//Step 1 - Login to deviceConnect with test user//
		//*******************************//
		isEventSuccessful = Login(dicCommon.get("testerEmailAddress"),dicCommon.get("testerPassword"));
		
		//**********************************************************//
		// Step 2 - Go To Manage your Account //
		//**********************************************************// 
		if(isEventSuccessful)
		{
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
		}
		
		//*********************************************//
		//Step 3 - Verify 'create another' user check box not displayed on UI.//
		//********************************************//
		if(isEventSuccessful)
		{
			strStepDescription = "Verify 'create another' user check box not displayed on UI.";
			strExpectedResult = "'create another' user check box should not be displayed on UI";
			waitForPageLoaded();
			isEventSuccessful = PerformAction("CreateAnotherUser",Action.isNotDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "'create another' user check box did not displayed on UI.";
			}
			else
			{
				strActualResult = "'create another' user check box displayed on UI.";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
	}

}