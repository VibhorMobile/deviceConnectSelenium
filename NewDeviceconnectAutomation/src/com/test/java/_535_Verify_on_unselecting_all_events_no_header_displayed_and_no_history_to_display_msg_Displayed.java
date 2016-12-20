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

public class _535_Verify_on_unselecting_all_events_no_header_displayed_and_no_history_to_display_msg_Displayed extends ScriptFuncLibrary
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
		//*** Step 2 : navigate to history page. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToHistoryPage("4");
		}
		else
		{
			return;
		}
		
		//******************************************************************//
		//*** Step 3 : De-select all events type. *****//
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
		// Step 4 - Verify column name of history table not displayed //
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
		// Step 5 - Verify 'There is no history to display' message displayed//
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