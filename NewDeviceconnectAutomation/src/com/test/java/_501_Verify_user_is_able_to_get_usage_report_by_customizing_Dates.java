package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1005, QA-191
 */


public class _501_Verify_user_is_able_to_get_usage_report_by_customizing_Dates extends ScriptFuncLibrary
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
		//*** Step 4 : Verify usage dates are in expected range. *****//
		//******************************************************************//
		strstepDescription = "Verify usage dates are in expected date range";
		strexpectedResult =  "Usages should be in expected range";
		if(isEventSuccessful)
		{
			waitForPageLoaded();
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
	}	
}