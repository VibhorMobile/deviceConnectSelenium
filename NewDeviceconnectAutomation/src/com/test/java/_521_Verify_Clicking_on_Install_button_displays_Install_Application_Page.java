package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-743
 */

public class _521_Verify_Clicking_on_Install_button_displays_Install_Application_Page extends ScriptFuncLibrary
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
		PerformAction(dicOR.get("installdropdownApplicationsPage").replace("2", "1"),Action.WaitForElement);
		isEventSuccessful = PerformAction(dicOR.get("installdropdownApplicationsPage").replace("2", "1"),Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful=PerformAction("installAppHeader",Action.isDisplayed);
			if(isEventSuccessful)
			{
				strActualResult = "Install applications page displayed";
			}
			else
			{
				strActualResult = "Install applications page did not displayed";
			}
		}
		else
		{
			strActualResult = "Unable to click on install button";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}

}