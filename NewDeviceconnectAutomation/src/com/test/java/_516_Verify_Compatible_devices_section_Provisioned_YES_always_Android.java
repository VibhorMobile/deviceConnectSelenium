package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-433, QA-981, QA-743
 */

public class _516_Verify_Compatible_devices_section_Provisioned_YES_always_Android extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "", AppName="",Version="",Date="";
	String [] filter={"Android"};

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
		strStepDescription = "Select platform Android";
		strExpectedResult = "Only Android platform checkbox selected";
		isEventSuccessful=selectCheckboxes_DI(filter, "chkPlatform_Devices");
		if(isEventSuccessful)
		{
			strActualResult="Only Android platform checkbox selected";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 4 - Click on first application link and verify user on application details page //
		//**********************************************************// 
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
		// Step 5 - Verify provisioned values //
		//**********************************************************//        
		strStepDescription = "Verify provisioned value is Yes for android device";
		strExpectedResult = "Provisioned value should be Yes for Android devices";
		isEventSuccessful = verifyProvisionedValues("android");
		if (isEventSuccessful)
		{
			strActualResult = "Provisioned value is Yes for android device";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}

}