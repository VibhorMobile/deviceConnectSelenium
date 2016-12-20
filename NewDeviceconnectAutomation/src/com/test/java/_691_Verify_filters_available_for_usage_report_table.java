package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-1398
 */
public class _691_Verify_filters_available_for_usage_report_table extends ScriptFuncLibrary
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
			isEventSuccessful = GoToUsagePage("3");
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
		strstepDescription = "Verify usage dates are in expected date range";
		strexpectedResult =  "Usage should be in expected range";
		if(isEventSuccessful)
		{
			isEventSuccessful=verifyUsagenHistoryDates(getDate("reportfrom"), getDate("to"),"usage");
			if(isEventSuccessful)
			{
				strActualResult = "Usage dates are in expected range";
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
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful=PerformAction("DatesDrpDwnonUsagePage",Action.Select,"Past 7 days");
			PerformAction("browser",Action.WaitForPageToLoad);
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
		strstepDescription = "Verify usage dates are in expected date range";
		strexpectedResult =  "Usage should be in expected range";
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful=verifyUsagenHistoryDates(getDate("7"), getDate("to"),"usage");
			if(isEventSuccessful)
			{
				strActualResult = "Usage dates are in expected range";
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
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful=PerformAction("DatesDrpDwnonUsagePage",Action.Select,"Today");
			PerformAction("browser",Action.WaitForPageToLoad);
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
		strstepDescription = "Verify usage dates are in expected date range";
		strexpectedResult =  "Usage should be in expected range";
		if(isEventSuccessful)
		{
			isEventSuccessful=verifyUsagenHistoryDates(getDate("to"), getDate("to"),"usage");
			if(isEventSuccessful)
			{
				strActualResult = "Usage dates are in expected range";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}	
}