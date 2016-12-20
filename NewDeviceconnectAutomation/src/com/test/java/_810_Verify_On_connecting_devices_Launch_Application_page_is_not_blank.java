package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Jira Test Case Id: QA-1575
 */
public class _810_Verify_On_connecting_devices_Launch_Application_page_is_not_blank extends ScriptFuncLibrary
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
		//** Step 5 - Verify launch application page is not blank. **//
		//****************************************************************************************//
		strstepDescription = "Verify launch application page is not blank.";
		strexpectedResult =  "Launch application page should be non-empty.";
		if (isEventSuccessful)
		{
			if(getelementsList("AppListOnConnectButton").size()>0)
			{
				
					strActualResult = "Launch application page is non-empty.";
			}
			else
			{
				strActualResult = "No app found.";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		PerformAction("btnClose",Action.Click);
	}

}