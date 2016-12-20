package com.test.java;

import java.util.Iterator;
import java.util.Set;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-207
 */

public class _528_Verify_all_links_on_System_tab_opened_in_new_tab extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "";
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	private Set windowids;
	String parentwindowid = "";
	String childwindowid = "";
	
	public final void testScript()
	{
		//*************************************************************//
		// Step 1 - Login to DC with Admin Credentials
		//*************************************************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to Systems page //
		//**********************************************************//                                   
		isEventSuccessful = GoToSystemPage();
		
		
		//**********************************************************//
		// Step 3 - Click on iOS Management link on System tab //
		//**********************************************************//   
		strStepDescription = "Verify iOS Management page open after clicking on iOS Management link";
		strExpectedResult = "iOS Management page should displayed.";
		isEventSuccessful=PerformAction("linkiOSManagement",Action.Click);
		waitForPageLoaded();
		if(isEventSuccessful)
		{
			strActualResult="iOS Management page displayed.";
		}
		else
		{
			strActualResult="iOS Management page did not displayed.";
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//*************************************************************//
		// Step 4 - Click on Mobile Labs Support link
		//*************************************************************//
		strStepDescription = "Click on 'About App Distribution' link";
		strExpectedResult = "User should be able to click on 'About App Distribution' link";
		waitForPageLoaded();
		PerformAction("aboutappdistributionlinkSystemtab",Action.WaitForElement);
		isEventSuccessful = PerformAction("aboutappdistributionlinkSystemtab", Action.Click);
		waitForPageLoaded();
		if (isEventSuccessful)
		{
			strActualResult = "User is able to click on 'About App Distribution' link";
		}
		else
		{
			strActualResult = "User is not able to click on About App Distribution' link";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//
		// Step 3 - Verify 'About App Distribution' link opened in new tab
		//*************************************************************//
		strStepDescription = "Verify 'About App Distribution' link opened in new tab";
		strExpectedResult = "User should be navigated to new tab";
		strActualResult = "";
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			windowids = driver.getWindowHandles();
			Iterator<String> itr = windowids.iterator();
			parentwindowid = (String) itr.next();
			childwindowid = (String) itr.next();
			isEventSuccessful = PerformAction("browser", Action.AboutAppDistribution,childwindowid);
			waitForPageLoaded();
			if (isEventSuccessful)
			{
				strActualResult = "Page is redirected to 'About App Distribution'.";
				PerformAction("browser", Action.SelectWindow,parentwindowid);
			}
			else
			{
				strActualResult = "User did not navigated to new tab";
			}
		}
		else
		{
			strActualResult = "Not able to navigate to new tab";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

	}
}