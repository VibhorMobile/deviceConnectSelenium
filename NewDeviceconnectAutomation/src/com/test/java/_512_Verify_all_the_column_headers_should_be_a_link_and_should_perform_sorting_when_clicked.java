package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-743
 */


public class _512_Verify_all_the_column_headers_should_be_a_link_and_should_perform_sorting_when_clicked extends ScriptFuncLibrary
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
		//Step 3 - Verify os column sorting in asc//
		//*****************************************************//
		strStepDescription = "Verify the os column is clickable and sorted in asc order on Applications page." ;
		strExpectedResult =  "OS Column is sorted in asc order on Applications page.";
		
		isEventSuccessful = VerifySortingonApplicationsPage("appcolumnOS","OS", "ascending");
		if (isEventSuccessful)
		{
			strActualResult = "OS Column is sorted in asc order on Applications page.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//*******************************************************//
		//Step 4 - Verify Latest Version column sorting in asc//
		//*****************************************************//
		strStepDescription = "Verify the latest version column is clickable and sorted in asc order on Applications page." ;
		strExpectedResult =  "Latest Version Column is sorted in asc order on Applications page.";
		
		isEventSuccessful = VerifySortingonApplicationsPage("appcolumnLatestVersion","Latest Version", "ascending");
		if (isEventSuccessful)
		{
			strActualResult = "Latest Version Column is sorted in asc order on Applications page.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//*******************************************************//
		//Step 5 - Verify Latest Version column sorting in asc//
		//*****************************************************//
		strStepDescription = "Verify the last updated column is clickable and sorted in asc order on Applications page." ;
		strExpectedResult =  "Last Updated Column is sorted in asc order on Applications page.";
		
		isEventSuccessful = VerifySortingonApplicationsPage("appcolumnLastUpdated","Last Updated", "ascending");
		if (isEventSuccessful)
		{
			strActualResult = "Last Updated Column is sorted in asc order on Applications page.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}

}