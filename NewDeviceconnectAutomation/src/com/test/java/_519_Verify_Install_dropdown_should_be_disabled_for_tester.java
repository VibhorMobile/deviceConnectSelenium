package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-743
 */

public class _519_Verify_Install_dropdown_should_be_disabled_for_tester extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private int counter=0;

	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid test user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with " + EmailAddress, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
		
		//******************************************************************//
		//*** Step 2 : Navigate to Applications Page. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToApplicationsPage();
		}
		else
		{
			return;
		}
		
		//******************************************************************//
		//*** Step 3 : Verify install drop down is not available for tester on applications page*****//
		//******************************************************************//
		strstepDescription = "Verify install drop down disabled for tester";
		strexpectedResult =  "Install drop down should be disabled for tester";
		if(isEventSuccessful)
		{
			isEventSuccessful=PerformAction("installdropdownApplicationsPage",Action.isNotDisplayed);
			if(isEventSuccessful)
			{
				strActualResult = "Install drop down is disabled for tester";
			}
			else
			{
				strActualResult = "Install drop down is enabled for tester";
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
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
		
		//******************************************************************//
		//*** Step 5 : Verify install drop down is disabled for tester on application details page*****//
		//******************************************************************//
		strstepDescription = "Verify install drop down disabled for tester";
		strexpectedResult =  "Install drop down should be disabled for tester";
		if(isEventSuccessful)
		{
			isEventSuccessful=PerformAction("installdropdownApplicationsPage",Action.isEnabled);
			if(!isEventSuccessful)
			{
				strActualResult = "Install drop down is disabled for tester";
			}
			else
			{
				strActualResult = "Install drop down is enabled for tester";
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}	
}