package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-191
 */

public class _502_Verify_user_is_able_to_get_usage_report_of_device_by_customizing_Dates_on_device_deatils_Page extends ScriptFuncLibrary
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
			if (isEventSuccessful) // continue only if there are some devices under android platform.
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
		//*** Step 3 : navigate to usage page. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToUsagePage("2");
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
		//*** Step 5 : Verify usage dates are in expected range. *****//
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