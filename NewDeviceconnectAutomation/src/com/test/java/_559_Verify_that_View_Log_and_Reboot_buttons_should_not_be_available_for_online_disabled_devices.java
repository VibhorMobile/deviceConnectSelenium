package com.test.java;

import java.io.IOException;
import java.util.ArrayList;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-531
 */

public class _559_Verify_that_View_Log_and_Reboot_buttons_should_not_be_available_for_online_disabled_devices extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	

	public final void testScript() throws InterruptedException, IOException
	{
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful = Login();
		
			
		//****************************************************************************************//
		//** Step 2 - Verify Online disabled devices displayed **//
		//****************************************************************************************//
		if(isEventSuccessful)
		{   
			isEventSuccessful=selectStatus("Disabled");
		}
		
		//******************************************************************//
		//*** Step 3 : Select first device*****//
		//******************************************************************//  
		strstepDescription = "Open device details page for first device.";
		strexpectedResult =  "Device details page for the first device should be displayed.";
		if (isEventSuccessful)
		{
			isEventSuccessful = SelectDevice("first");
			if (isEventSuccessful)
			{
				strActualResult = "Device details page displayed";
			}
			else
			{
				strActualResult = "Device details page did not displayed";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 4 : Verify Reboot button is not displayed*****//
		//******************************************************************//  
		strstepDescription = "Verify Reboot button is not displayed.";
		strexpectedResult =  "Reboot button should not displayed.";
		if (isEventSuccessful)
		{
			PerformAction("browser",Action.WaitForPageToLoad);
			isEventSuccessful = PerformAction("btnReboot",Action.isNotDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Reboot button did not displayed.";
			}
			else
			{
				strActualResult = "Reboot button displayed.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 5 : Verify View Log button is not displayed*****//
		//******************************************************************//  
		strstepDescription = "Verify View Log button is not displayed.";
		strexpectedResult =  "View Log button should not displayed.";
		if (isEventSuccessful)
		{
			PerformAction("browser",Action.WaitForPageToLoad);
			isEventSuccessful = PerformAction("btnViewLog",Action.isNotDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "View Log button did not displayed.";
			}
			else
			{
				strActualResult = "View Log button displayed.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 6 : Verify View Log button is not displayed*****//
		//******************************************************************// 
		EnableDevice(GetTextOrValue("eleDeviceNameinDeviceDetailsHeader","text"));
	}
}