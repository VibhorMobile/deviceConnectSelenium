package com.test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-567
 */

public class _542_Verify_on_unselecting_all_events_no_header_displayed_and_no_history_to_display_msg_Displayed_device_details_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();
		
		//******************************************************************//
		//*** Step 2 : Select Status Available. and select first device*****//
		//******************************************************************//  
		isEventSuccessful = selectStatus("Available");
		strstepDescription = "Open device details page for first Available device.";
		strexpectedResult =  "Device details page for the first available device should be displayed.";
		
		if (isEventSuccessful)
		{
			isEventSuccessful = !VerifyMessage_On_Filter_Selection();
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
				strActualResult = "Warning messgae displayed on devices page.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//******************************************************************//
		//*** Step 3 : navigate to history page. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToHistoryPage("3");
		}
		else
		{
			return;
		}
		
		//******************************************************************//
		//*** Step 4 : De-select all events type. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = selectEventType("None");
		}
		else
		{
			return;
		}
		
		//**********************************************************//
		// Step 5 - Verify column name of history table not displayed //
		//**********************************************************//   
		strStepDescription = "Verify column names of history table is not displayed";
		strExpectedResult = "Column names of history table should not be displayed";
		PerformAction("browser","waitforpagetoload");
		isEventSuccessful=verifyHistorColHeaders();
		if(!isEventSuccessful)
		{
			isEventSuccessful=true;
			strActualResult="Column names of history table is not displayed.";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 6 - Verify 'There is no history to display' message displayed//
		//**********************************************************//   
		strStepDescription = "Verify 'There is no history to display' message displayed";
		strExpectedResult = "'There is no history to display' message should be displayed";
		PerformAction("browser","waitforpagetoload");
		isEventSuccessful=GetTextOrValue("nohistorydisplaymsg","text").contains("There is no history to display");
		if(isEventSuccessful)
		{
			isEventSuccessful=true;
			strActualResult="'There is no history to display' message displayed";
		}
		else
		{
			strActualResult="'There is no history to display' message did not displayed";
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
	}	
}