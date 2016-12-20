package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1378, QA-567
 */

public class _538_Verify_By_Default_Past_30_days_is_selected_in_Dates_dropdown_and_displaying_history_of_past_30_days_Device_details_page extends ScriptFuncLibrary
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
		
		
		//**********************************************************//
		// Step 3 - Verify 'Past 30 days' is selected in dates drop down by default //
		//**********************************************************//   
		strStepDescription = "Verify 'Past 30 days' is selected in dates drop down by default";
		strExpectedResult = "'Past 30 days' should be selected in dates drop down by default";
		isEventSuccessful=GetSelectedDropDownValue("DatesDrpDwnonUsagePage").equals("Past 30 days");
		if(isEventSuccessful)
		{
			strActualResult="'Past 30 days' is selected in dates drop down by default";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 4 : Verify history dates are in expected range. *****//
		//******************************************************************//
		strstepDescription = "Verify history dates are in expected date range";
		strexpectedResult =  "History should be in expected range";
		if(isEventSuccessful)
		{
			isEventSuccessful=verifyUsagenHistoryDates(getDate("30"), getDate("to"),"history");
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