package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-567
 */

public class _547_Verify_functionality_of_Dates_dropdown_device_details_page extends ScriptFuncLibrary
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
		//*** Step 4 : Set Customize date. *****//
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
		//*** Step 5 : Verify history dates are in expected range. *****//
		//******************************************************************//
		waitForPageLoaded();
		PerformAction("browser","waitforpagetoload");
		strstepDescription = "Verify history dates are in expected date range";
		strexpectedResult =  "History should be in expected range";
		if(isEventSuccessful)
		{
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
		//*** Step 6 : Set drop down value 'Past 7 days'. *****//
		//******************************************************************//
		strstepDescription = "Set date drop down value 'Past 7 days'";
		strexpectedResult =  "'Past 7 days' should be selected in dates drop down";
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
		//*** Step 7 : Verify history dates are in expected range. *****//
		//******************************************************************//
		waitForPageLoaded();
		PerformAction("browser","waitforpagetoload");
		strstepDescription = "Verify history dates are in expected date range";
		strexpectedResult =  "History should be in expected range";
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
		//*** Step 8 : Set drop down value 'Today'. *****//
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
		//*** Step 9 : Verify history dates are in expected range. *****//
		//******************************************************************//
		waitForPageLoaded();
		PerformAction("browser","waitforpagetoload");
		strstepDescription = "Verify history dates are in expected date range";
		strexpectedResult =  "History should be in expected range";
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