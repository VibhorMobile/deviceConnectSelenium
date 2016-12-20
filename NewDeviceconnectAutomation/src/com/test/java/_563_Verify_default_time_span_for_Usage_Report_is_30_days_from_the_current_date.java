package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Deepak
 * Creation Date: 5-Feb-2016
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-1005, QA-1214
 */
public class _563_Verify_default_time_span_for_Usage_Report_is_30_days_from_the_current_date extends ScriptFuncLibrary
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
		//*** Step 4 : Verify usage dates are in expected range. *****//
		//******************************************************************//
		strstepDescription = "Verify usage dates are in expected date range";
		strexpectedResult =  "Usage should be in expected range";
		if(isEventSuccessful)
		{
			isEventSuccessful=verifyUsagenHistoryDates(getDate("30"), getDate("to"),"history"); //history is use to call specific function
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