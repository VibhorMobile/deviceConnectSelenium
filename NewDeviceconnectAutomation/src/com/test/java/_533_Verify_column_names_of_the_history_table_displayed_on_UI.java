package com.test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-567
 */

public class _533_Verify_column_names_of_the_history_table_displayed_on_UI extends ScriptFuncLibrary
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
		// Step 3 - Verify newest first is selected in order drop down by default //
		//**********************************************************//   
		strStepDescription = "Verify column names of history table displayed";
		strExpectedResult = "Column names of history table should be displayed";
		waitForPageLoaded();
		PerformAction("browser","waitforpagetoload");
		isEventSuccessful=verifyHistorColHeaders();
		if(isEventSuccessful)
		{
			strActualResult="Column names of history table displayed.";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
	}	
}