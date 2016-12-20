package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1357
 */

public class _436_Verify_that_the_sorting_for_the_devices_list_should_work_in_descending extends ScriptFuncLibrary
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
		SelectColumn_Devices_SFL("Manufacturer");
		
		//*******************************************************//
		//Step 3 - Verify sorting in asc//
		//*****************************************************//
		strStepDescription = "Verify the columns are sorted in reverse order on Devices Index page." ;
		strExpectedResult =  "Columns are sorted in reverse order on Devices Index page.";
		
		isEventSuccessful = VerifySorting_in_Order(dicOR.get("table_devicespage"),"Manufacturer", "descending");
		if (isEventSuccessful)
		{
			strActualResult = "Columns are sorted in reverse order on Devices Index page.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
	}

}