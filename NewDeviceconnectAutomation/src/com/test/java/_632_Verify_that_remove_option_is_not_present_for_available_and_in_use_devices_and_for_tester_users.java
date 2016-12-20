package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: Third week of February
 * Last Modified Date: Same as creation date
 * Jira Test Case Id:QA-1297
 */

public class _632_Verify_that_remove_option_is_not_present_for_available_and_in_use_devices_and_for_tester_users extends ScriptFuncLibrary {
	
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	Object[] Values = new Object[5]; 
	private String strActualResult = "", devicename = "",EmailAddress, Password;
	
	public final void testScript()
	{

		//*************************************************************//
		//Step 1 : login to deviceConnect with admin user.
		//*************************************************************//
		 isEventSuccessful = Login(dicCommon.get("testerEmailAddress"),dicCommon.get("testerPassword")); 
		 
		//******************************************************************//
		//*** Step 2 : Select Status Available. *****//
		//******************************************************************// 
		
		 isEventSuccessful = selectStatus("Available,In Use");
		 
		 selectAllDevicesCheckbox_DI();
		 
		 strStepDescription = "Verify_that_remove_option_is_not_present_for_available_and_in_use_devices_and_for_tester_users";
		 strExpectedResult = "Remove_option_should_not_be present_for_available_and_in_use_devices_for_tester_users";
		 
		 isEventSuccessful = !PerformAction("//button[text()='Remove' and @style='display: inline-block;']", Action.isDisplayed);
		 
		if(isEventSuccessful)
		{
			strActualResult = "Remove_option_is not present_for_available_and_in_use_devices_for_tester_users";
		}
		else
		{
			strActualResult = "Remove_option_is present_for_available_and_in_use_devices_for_tester_users";
		}
	
		reporter.ReportStep(strStepDescription, strExpectedResult , strActualResult,isEventSuccessful);

	}
}