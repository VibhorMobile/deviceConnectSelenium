package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-981, QA-433, QA-373, QA-57, QA-743
 */

public class _515_Verify_all_the_information_of_application_on_application_details_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "", AppName="",Version="",Date="";
	String [] iOS={"iOS"};

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
			
		//**********************************************************//
		// Step 3 - Select platform Android //
		//**********************************************************//   
		strStepDescription = "Select platform iOS";
		strExpectedResult = "Only iOS platform checkbox selected";
		isEventSuccessful=selectCheckboxes_DI(iOS, "chkPlatform_Devices");
		if(isEventSuccessful)
		{
			strActualResult="Only iOS platform checkbox selected";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 4 - Click on first application link and verify user on application details page //
		//**********************************************************// 
		AppName=GetTextOrValue("appNameColumnDetails", "text");
		Version=GetTextOrValue("appLatestVersionDetails", "text");
		Date=GetTextOrValue("appLastUpdateDetails", "text");
		strStepDescription = "Click on first app link and app details page get displayed";
		strExpectedResult = "App details page should be displayed.";
		isEventSuccessful = SelectApplication("first");
		if (isEventSuccessful)
		{
			strActualResult = "App details page displayed";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 5 - Verify details of application on application details page //
		//**********************************************************//        
		strStepDescription = "Verify details of application";
		strExpectedResult = "All the details of application should be displayed";
		isEventSuccessful = verifyallAppDetails_ApplicationDetails(AppName,Version,Date);
		if (isEventSuccessful)
		{
			strActualResult = "App details displayed";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}

}