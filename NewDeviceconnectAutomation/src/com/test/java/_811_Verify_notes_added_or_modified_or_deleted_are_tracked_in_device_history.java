package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: 31-Aug-2016
 * Last Modified Date: Same as creation date
 * Jira Test Case Id: QA-1169
 */

public class _811_Verify_notes_added_or_modified_or_deleted_are_tracked_in_device_history extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strexpectedResult = "", strActualResult = "",strstepDescription="";
	String [] android={"Android"};
	
	public final void testScript()
	{
		
		
		//*******************************//
		// Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to first device details page //
		//**********************************************************//   
		if(isEventSuccessful)
		{
			isEventSuccessful = (Boolean)GoTofirstDeviceDetailsPage()[0];
		}
		
		//**********************************************************//
		// Step 3 - Expand details section //
		//**********************************************************//                                   
		if(isEventSuccessful)
		{
			isEventSuccessful = ShowDetails();
		}
		
		//**********************************************************//
		// Step 4 - Verify notes can be add or modify by administrator  //
		//**********************************************************// 
		isEventSuccessful=EditAndVerifyNotes("Notes field is editable and modifiable");
		
		
		//****************************************************************************************//
		// Step 5 - Navigate to device history page **//
		//****************************************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToHistoryPage("3");
		}
		
		//**********************************************************//
		// Step 6 - Verify event column of first row in history table //
		//**********************************************************//  
		if(isEventSuccessful)
		{
			strStepDescription = "Verify event column contains only device notes status";
			strExpectedResult = "Event column should contain only device notes status";
			PerformAction("browser","waitforpagetoload");
			isEventSuccessful=verifyHistoryEventColumnValue("Note","Note",dicOR.get("DurationinUsageTable").replace("tr[*]", "tr[1]"));
			if(isEventSuccessful)
			{
				strActualResult="Event column contain only device notes status";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		}

		//**********************************************************//
		// Step 7 - Verify device notes listed in History are actual device notes //
		//**********************************************************//   
		if(isEventSuccessful)
		{
			strStepDescription = "Verify device notes listed in History are actual device notes.";
			strExpectedResult = "Device notes listed in History should be actual device notes";
			isEventSuccessful=getAttribute(dicOR.get("DurationinUsageTable").replace("tr[*]", "tr[1]"), "title").contains("Notes field is editable and modifiable");
			if(isEventSuccessful)
			{
				strActualResult="Device notes listed in History are actual device notes";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		}
		
		//**********************************************************//
		// Step 8 - Verify notes can be removed  //
		//**********************************************************// 
		if(isEventSuccessful)
		{
			isEventSuccessful=EditAndVerifyNotes("");
			PerformAction("browser",Action.Refresh);
		}
		
		//**********************************************************//
		// Step 9 - Verify event column of first row in history table //
		//**********************************************************//
		if(isEventSuccessful)
		{
			strStepDescription = "Verify event column contains only device notes status";
			strExpectedResult = "Event column should contain only device notes status";
			PerformAction("browser","waitforpagetoload");
			isEventSuccessful=verifyHistoryEventColumnValue("Note","Note",dicOR.get("DurationinUsageTable").replace("tr[*]", "tr[1]"));
			if(isEventSuccessful)
			{
				strActualResult="Event column contain only device notes status";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		}

		//**********************************************************//
		// Step 10 - Verify device notes listed in History are actual device notes //
		//**********************************************************//
		if(isEventSuccessful)
		{
			strStepDescription = "Verify device notes listed in History are actual device notes.";
			strExpectedResult = "Device notes listed in History should be actual device notes";

			if(getAttribute(dicOR.get("DurationinUsageTable").replace("tr[*]", "tr[1]"), "title") == null || getAttribute(dicOR.get("DurationinUsageTable").replace("tr[*]", "tr[1]"), "title").length()<1)
			{
				strActualResult="Device notes listed in History are actual device notes";
				reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, "Pass");
			}
			else
			{
				strActualResult="device note has text for deleted notes.";
				reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, "Fail");
			}
		}
		
	}
	
	
}