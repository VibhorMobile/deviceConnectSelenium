package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1399
 */

public class _438_Verify_the_default_column_headers_should_be_present_on_the_devices_index_page_while_logging_to_the_dc_web extends ScriptFuncLibrary
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
		//Step 2 - Go to Device Index page //
		//**********************************************************//                                   
		isEventSuccessful = GoToDevicesPage();
		
		//*******************************************************//
		//Step 3 - Verify default column headers//
		//*****************************************************//
		strStepDescription = "Verify the default column headers should be present on the devices index page while logging to the dc web" ;
		strExpectedResult =  "Name/Status, Model, OS & Slot# column headers should be present by default";
		
		isEventSuccessful = CoulmnsDisplayed_Devices("Model,OS,Slot #");
		if (isEventSuccessful)
		{
			strActualResult = "Name/Status, Model, OS & Slot# column headers should be present by default";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
	}

}