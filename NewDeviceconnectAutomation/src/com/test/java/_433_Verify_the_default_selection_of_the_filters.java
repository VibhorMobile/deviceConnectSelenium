package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1018
 */

public class _433_Verify_the_default_selection_of_the_filters extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";

	public final void testScript()
	{
		//*************************************************************//     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//     
		isEventSuccessful = Login();
		
		//**********************************************************//
		//** Step 2 - Go To Device Index page **********//
		//**********************************************************//                                   
		isEventSuccessful = GoToDevicesPage();
		
		
		//**********************************************************//
		//** Step 3 - Verify default selected checkboxes of status filters  **//
		//**********************************************************//
		isEventSuccessful=verify_default_selected_Status_of_filters();
		
		//**********************************************************//
		//** Step 4 - Verify default selected checkboxes of platform filters  **//
		//**********************************************************//
		verify_default_selected_Status_of_platform();
	}

}