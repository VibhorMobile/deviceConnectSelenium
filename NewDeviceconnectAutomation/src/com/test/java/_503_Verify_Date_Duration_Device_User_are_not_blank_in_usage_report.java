package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1129, QA-1247, QA-1326, QA-1005, QA-1255, QA-1064, QA-191
 */

public class _503_Verify_Date_Duration_Device_User_are_not_blank_in_usage_report extends ScriptFuncLibrary
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
		//*** Step 4 : Verify columns date, device, user, duration and application are not blank. *****//
		//******************************************************************//
		strstepDescription = "Verify columns dates, device, duration, user and application are not blank";
		strexpectedResult =  "Columns dates, device, duration, user and application should not be blank";
		if(isEventSuccessful)
		{
			isEventSuccessful=verifyUsageColumns();
			if(isEventSuccessful)
			{
				strActualResult = "Columns dates, device, duration, user and application are not blank";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}	
}