package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-567
 */

public class _549_Verify_functionality_of_Order_dropdown_device_details_page extends ScriptFuncLibrary
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
		//*** Step 4 : Set drop down value 'Today'. *****//
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
		//*** Step 5 : Verify Dates order drop down. *****//
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