package com.test.java;

import java.util.Iterator;
import java.util.Set;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-207
 */

public class _527_Verify_Mobile_Labs_Support_link_opens_up_Mobile_Labs_Support_page extends ScriptFuncLibrary
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
		
		//*************************************************************//
		// Step 3 - Click on Mobile Labs Support link
		//*************************************************************//
		strStepDescription = "Click on Mobile Labs Support link";
		strExpectedResult = "User should be able to click on Mobile Labs Support link";
		isEventSuccessful = PerformAction("mobsupportlnkSystemTab", Action.Click);
		if (isEventSuccessful)
		{
			strActualResult = "User is able to click on Mobile Labs Support link";
		}
		else
		{
			strActualResult = "Page is not opened in a new window.";
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//
		// Step 4 - Click More info about DC link
		//*************************************************************//
		strStepDescription = "Verify user is on MobileLabs Support site";
		strExpectedResult = "User should be navigated to MobileLabs support site";
		strActualResult = "";
		if (isEventSuccessful)
		{
			windowids = driver.getWindowHandles();
			Iterator<String> itr = windowids.iterator();
			parentwindowid = (String) itr.next();
			childwindowid = (String) itr.next();
			isEventSuccessful = PerformAction("browser", Action.SelectSupportWindow,childwindowid);
			PerformAction("browser", "waitforpagetoload");
			if (isEventSuccessful)
			{
				strActualResult = "Page is redirected to 'https://mobilelabs.zendesk.com'.";
				PerformAction("browser", Action.SelectWindow,parentwindowid);
			}
			else
			{
				strActualResult = "Page is not redirected to 'https://mobilelabs.zendesk.com'";
			}
		}
		else
		{
			strActualResult = "Not able to navigate to new tab";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}
}