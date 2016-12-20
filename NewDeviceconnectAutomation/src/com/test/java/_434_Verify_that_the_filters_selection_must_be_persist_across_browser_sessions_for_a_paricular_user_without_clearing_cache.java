package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-910
 */

public class _434_Verify_that_the_filters_selection_must_be_persist_across_browser_sessions_for_a_paricular_user_without_clearing_cache extends ScriptFuncLibrary
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
		//** Step 2 - Go to Device Index page **********//
		//**********************************************************//                                   
		isEventSuccessful = GoToDevicesPage();
		
		//**********************************************************//
		//** Step 3 - Select status from Filters dropdown and logout**********//
		//**********************************************************//     
		isEventSuccessful =  selectStatus("Available");
		isEventSuccessful= selectPlatform("iOS");
		if(isEventSuccessful)
		{
			isEventSuccessful=LogoutDC();
		}
		

		//****************************************************************************************//
		//** Step 4 - login again to deviceConnect with same admin user. **//
		//****************************************************************************************//
		if (isEventSuccessful) 
		{
			isEventSuccessful = Login();
		}
		
		//****************************************************************************************//
		//** Step 5 - Verify Selected filters **//
		//****************************************************************************************//
		verifyselectedfilters("Available");
	}

}