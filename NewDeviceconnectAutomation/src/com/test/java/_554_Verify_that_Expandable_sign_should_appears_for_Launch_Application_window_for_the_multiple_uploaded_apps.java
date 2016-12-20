package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-526
 */


public class _554_Verify_that_Expandable_sign_should_appears_for_Launch_Application_window_for_the_multiple_uploaded_apps extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription="",strexpectedResult="", strActualResult = "";
	Object[] values = new Object[2];

	public final void testScript()
	{
		
		//*************************************************************//     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//     
		isEventSuccessful = Login();
		
		//**********************************************************//
		//** Step 2 - Go to Device Index page **********//
		//**********************************************************//                                   
		isEventSuccessful = GoToDevicesPage();
		
		//**********************************************************//
		//** Step 3 - Select 'Available' status from Filters dropdown **********//
		//**********************************************************//     
		isEventSuccessful =  selectStatus("Available");
		
		
		//****************************************************************************************//
		//** Step 4 - Click on Connect button on first available device **//
		//****************************************************************************************//
		strstepDescription = "Verify application launch dialog box displayed.";
		strexpectedResult =  "Application launch dialog box should be displayed.";
		if (isEventSuccessful)
		{
			isEventSuccessful = OpenLaunchAppDialog("first");
			if (isEventSuccessful) 
			{
				strActualResult = "Application launch dialog box displayed.";
			}
			else
			{
				strActualResult = "Application launch dialog box did not displayed.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		
				
		//****************************************************************************************//
		//** Step 5 - Verify expandable sign displayed for multiple uploaded apps **//
		//****************************************************************************************//
		strstepDescription = "Verify expandable sign displayed for multiple uploaded apps.";
		strexpectedResult =  "Expandable sign should be displayed for multiple uploaded apps.";
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("appCount",Action.isDisplayed);
			if (isEventSuccessful) 
			{
				isEventSuccessful = PerformAction("expandableSign", Action.isDisplayed);
				if (isEventSuccessful)
				{
					strActualResult = "Expandable sign displayed for multiple uploaded apps.";
				}
				else
				{
					strActualResult = "Expandable sign did not displayed for multiple uploaded apps.";
				}
			}
			else
			{
				strActualResult = "No app found which has multiple uploads.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}

}