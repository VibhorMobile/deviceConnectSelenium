package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1207
 */

public class _442_Verify_When_no_platform_and_any_status_checked_is_selected_then_warning_message_should_be_displayed extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", devicename = "";
	private String xpath = "";
	private int devicecount;
	Object[] Values = new Object[4]; 

	public final void testScript()
	{
		///**************************************************************************//
		//Step 1 - : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		isEventSuccessful = Login();

		//**************************************************************************//
		// Step 2 :Select no platform and status as Available.
		//**************************************************************************//
		isEventSuccessful=selectPlatform("","uncheckall");
		isEventSuccessful=selectStatus("Available");
		
		
		//**************************************************************************//
		// Step 3 : Check Warning Message on Device Index page
		//**************************************************************************//
		isEventSuccessful = VerifyMessage_On_Filter_Selection();
		strstepDescription = "Verify_When_no_platform_and_any_status_checked_is_selected_then_warning_message_should_be_displayed.";
		strexpectedResult = "Warning_message_should_be_displayed.";
		
		if (isEventSuccessful)
		{
			strActualResult = "Warning_message_appeared_on_Device_Index_page.";
		}
		else
		{
			strActualResult = "GetSingleDeviceDetails---" + strErrMsg_GenLib;
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
    }
}