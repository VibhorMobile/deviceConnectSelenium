package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-567
 */

public class _534_Verify_By_Default_Past_30_days_is_selected_in_Dates_dropdown_and_displaying_history_of_past_30_days extends ScriptFuncLibrary
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