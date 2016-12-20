package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * JIRA ID --> QA-743
 */

public class _511_Verify_default_sorting_should_be_as_per_the_Application_Name extends ScriptFuncLibrary
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
		// Step 2 - Go to Applications page //
		//**********************************************************//                                   
		isEventSuccessful = GoToApplicationsPage();
		
		//*******************************************************//
		//Step 3 - Verify sorting in asc//
		//*****************************************************//
		strStepDescription = "Verify the app columns is sorted in asc order on Applications page." ;
		strExpectedResult =  "App Column is sorted in asc order on Applications page.";
		
		isEventSuccessful = VerifySortingonApplicationsPage("appcolumnName","Name", "ascending");
		if (isEventSuccessful)
		{
			strActualResult = "App Column is sorted in asc order on Applications page.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
	}

}