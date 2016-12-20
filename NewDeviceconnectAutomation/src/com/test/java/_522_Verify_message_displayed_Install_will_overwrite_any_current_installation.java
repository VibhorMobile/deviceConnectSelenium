package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-743
 */

public class _522_Verify_message_displayed_Install_will_overwrite_any_current_installation extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "", AppName="",Version="",Date="";
	
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
		// Step 3 - Click on first application link and verify user on application details page //
		//**********************************************************// 
		strStepDescription = "Verify install applications page displayed on clicking install button";
		strExpectedResult = "Install applications page displayed";
		waitForPageLoaded();
		PerformAction(dicOR.get("installdropdownApplicationsPage").replace("2", "1"),Action.WaitForElement);
		isEventSuccessful = PerformAction(dicOR.get("installdropdownApplicationsPage").replace("2", "1"),Action.Click);
		waitForPageLoaded();
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
		// Step 4 - Verify warning message displayed //
		//**********************************************************// 
		strStepDescription = "Verify warning message is displayed.";
		strExpectedResult = "Warning message should be displayed.";
		isEventSuccessful = GetTextOrValue("installwarningmsg","text").contains("Install will overwrite any current installation");
		if (isEventSuccessful)
		{
			strActualResult = "Warning message displayed.";
		}
		else
		{
			strActualResult = "Did not displayed warning message.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}
}