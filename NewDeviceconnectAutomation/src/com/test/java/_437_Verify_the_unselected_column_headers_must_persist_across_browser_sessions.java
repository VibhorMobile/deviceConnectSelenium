package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1232, QA-1420
 */

public class _437_Verify_the_unselected_column_headers_must_persist_across_browser_sessions extends ScriptFuncLibrary
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
		
		//**********************************************************//
		// Step 2 - Go to Device Index page //
		//**********************************************************//                                   
		isEventSuccessful = GoToDevicesPage();
		
		//*********************************************//
		//Step 3 - Verify "Manufacturer" column is displayed//
		//********************************************//
		SelectColumn_Devices_SFL("Manufacturer,Vendor Name");
		
		//*******************************************************//
		//Step 4 - Logout from DC UI and Log in again //
		//*****************************************************//
		isEventSuccessful=LogoutDC();
		isEventSuccessful = Login();
		
		//*******************************************************//
		//Step 5 - Verify unselected column headers must persist across browser session//
		//*****************************************************//
		strStepDescription = "Verify the unselected column headers must persist across browser session." ;
		strExpectedResult =  "Unselected headers persists across browser sessions.";
		
		isEventSuccessful = CoulmnsDisplayed_Devices("Manufacturer,Vendor Name");
		if (isEventSuccessful)
		{
			strActualResult = "Unselected headers persists across browser sessions.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
	}

}