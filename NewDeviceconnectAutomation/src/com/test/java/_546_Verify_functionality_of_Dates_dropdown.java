package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1184, QA-567
 */

public class _546_Verify_functionality_of_Dates_dropdown extends ScriptFuncLibrary
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
		//*** Step 2 : navigate to usage page. *****//
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
		//*** Step 3 : Set Customize date. *****//
		//******************************************************************//
		strstepDescription = "Set customizing dates";
		strexpectedResult =  "Dates should be set in start and end date boxes";
		if(isEventSuccessful)
		{
			isEventSuccessful=setCustomizedDates(getDate("from"), getDate("to"));
			if(isEventSuccessful)
			{
				strActualResult = "Dates set in start and end date boxes.";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		//******************************************************************//
		//*** Step 4 : Verify history dates are in expected range. *****//
		//******************************************************************//
		strstepDescription = "Verify history dates are in expected date range";
		strexpectedResult =  "History should be in expected range";
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful=verifyUsagenHistoryDates(getDate("reportfrom"), getDate("to"),"history");
			if(isEventSuccessful)
			{
				strActualResult = "History dates are in expected range";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 5 : Set drop down value 'Past 7 days'. *****//
		//******************************************************************//
		strstepDescription = "Set date drop down value 'Past 7 days'";
		strexpectedResult =  "'Past 7 days' should be selected in dates drop down";
		PerformAction("browser",Action.Refresh);
		waitForPageLoaded();
		if(isEventSuccessful)
		{
			isEventSuccessful=PerformAction("DatesDrpDwnonUsagePage",Action.Select,"Past 7 days");
			if(isEventSuccessful)
			{
				strActualResult = "'Past 7 days' selected in dates drop down";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		//******************************************************************//
		//*** Step 6 : Verify history dates are in expected range. *****//
		//******************************************************************//
		strstepDescription = "Verify history dates are in expected date range";
		strexpectedResult =  "History should be in expected range";
		waitForPageLoaded();
		PerformAction("browser",Action.WaitForPageToLoad);
		if(isEventSuccessful)
		{
			isEventSuccessful=verifyUsagenHistoryDates(getDate("7"), getDate("to"),"history");
			if(isEventSuccessful)
			{
				strActualResult = "History dates are in expected range";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 7 : Set drop down value 'Today'. *****//
		//******************************************************************//
		strstepDescription = "Set date drop down value 'Today'";
		strexpectedResult =  "'Today' should be selected in dates drop down";
		PerformAction("browser",Action.Refresh);
		waitForPageLoaded();
		if(isEventSuccessful)
		{
			isEventSuccessful=PerformAction("DatesDrpDwnonUsagePage",Action.Select,"Today");
			if(isEventSuccessful)
			{
				strActualResult = "'Today' selected in dates drop down";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		//******************************************************************//
		//*** Step 8 : Verify history dates are in expected range. *****//
		//******************************************************************//
		strstepDescription = "Verify history dates are in expected date range";
		strexpectedResult =  "History should be in expected range";
		waitForPageLoaded();
		PerformAction("browser",Action.WaitForPageToLoad);
		if(isEventSuccessful)
		{
			isEventSuccessful=verifyUsagenHistoryDates(getDate("to"), getDate("to"),"history");
			if(isEventSuccessful)
			{
				strActualResult = "History dates are in expected range";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}	
}