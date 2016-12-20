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

public class _532_Verify_By_default_newest_first_is_selected_in_order_drop_down extends ScriptFuncLibrary
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
		strStepDescription = "Verify newest first is selected in order drop down by default";
		strExpectedResult = "Newest first should be selected in order drop down by default";
		isEventSuccessful=GetSelectedDropDownValue("orderdropdown").equals("Newest first");
		if(isEventSuccessful)
		{
			strActualResult="Newest first is selected in order drop down by default.";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
	}	
}