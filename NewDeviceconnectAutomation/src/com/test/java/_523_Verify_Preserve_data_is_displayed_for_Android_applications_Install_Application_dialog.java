package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-743
 */

public class _523_Verify_Preserve_data_is_displayed_for_Android_applications_Install_Application_dialog extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";
	String [] android={"Android"};
	
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
		strStepDescription = "Select platform android";
		strExpectedResult = "Only android platform checkbox selected";
		isEventSuccessful=selectCheckboxes_DI(android, "chkPlatform_Devices");
		if(isEventSuccessful)
		{
			strActualResult="Only android platform checkbox selected";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 4  - Click on first application link and verify user on application details page //
		//**********************************************************// 
		strStepDescription = "Verify install applications page displayed on clicking install button";
		strExpectedResult = "Install applications page displayed";
		PerformAction(dicOR.get("installdropdownApplicationsPage").replace("2", "1"),Action.WaitForElement);
		isEventSuccessful = PerformAction(dicOR.get("installdropdownApplicationsPage").replace("2", "1"),Action.Click);
		if (isEventSuccessful)
		{
			strActualResult = "Install applications page displayed";
		}
		else
		{
			strActualResult = "Unable to click on install button";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 5 - Verify preserve displayed for android devices //
		//**********************************************************// 
		strStepDescription = "Verify preserve data is displayed for android devices.";
		strExpectedResult = "Preserve should displayed for android devices.";
		isEventSuccessful = GetTextOrValue("preserveDataAppInstallPage","text").contains("Preserve");
		if (isEventSuccessful)
		{
			strActualResult = "Preserve is displayed for android devices.";
		}
		else
		{
			strActualResult = "Preserve is not displayed for android devices.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}
}