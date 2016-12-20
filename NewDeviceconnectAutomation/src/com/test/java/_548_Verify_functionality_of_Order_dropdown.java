package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1083, QA-567
 */

public class _548_Verify_functionality_of_Order_dropdown extends ScriptFuncLibrary
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
		//*** Step 3 : Set drop down value 'Today'. *****//
		//******************************************************************//
		strstepDescription = "Set date drop down value 'Today'";
		strexpectedResult =  "'Today' should be selected in dates drop down";
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
		//*** Step 4 : Verify Dates order drop down. *****//
		//******************************************************************//
		strstepDescription = "Verify functionality of order drop down";
		strexpectedResult =  "History should be displayed as per drop down value";
		if(isEventSuccessful)
		{
			isEventSuccessful=verifyOrderDropDownHistory();
			if(isEventSuccessful)
			{
				strActualResult = "History displayed as per drop down";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}	
}