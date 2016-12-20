package com.test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1384, QA-1215, QA-567
 */

public class _543_Verify_By_selecting_particular_event_checkbox_respective_device_events_are_displayed_on_UI extends ScriptFuncLibrary
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
		
		//******************************************************************//
		//*** Step 3 : Select event 'Device Availability' type. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = selectEventType("Device Availability");
		}
		else
		{
			return;
		}
		
		//**********************************************************//
		// Step 4 - Verify column of history table //
		//**********************************************************//   
		strStepDescription = "Verify event column contains only devices availability status";
		strExpectedResult = "Event column should contain only devices availability status";
		waitForPageLoaded();
		PerformAction("browser","waitforpagetoload");
		isEventSuccessful=verifyHistoryEventColumnValue("Device Online","Device Offline","DurationinUsageTable");
		if(isEventSuccessful)
		{
			strActualResult="Event column contain only devices availability status";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 5 : Select event 'Device Retainment' type. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = selectEventType("Device Retainment");
		}
		else
		{
			return;
		}
		
		//**********************************************************//
		// Step 6 - Verify column of history table //
		//**********************************************************//   
		strStepDescription = "Verify event column contains only devices retainment status";
		strExpectedResult = "Event column should contain only devices retainment status";
		waitForPageLoaded();
		PerformAction("browser","waitforpagetoload");
		isEventSuccessful=verifyHistoryEventColumnValue("Device Released","Device Retained","DurationinUsageTable");
		if(isEventSuccessful)
		{
			strActualResult="Event column contain only devices retainment status";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 7 : Select event 'Device Enablement' type. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = selectEventType("Device Enablement");
		}
		else
		{
			return;
		}
				
		//**********************************************************//
		// Step 8 - Verify column of history table //
		//**********************************************************//   
		strStepDescription = "Verify event column contains only devices enablement status";
		strExpectedResult = "Event column should contain only devices enablement status";
		waitForPageLoaded();
		PerformAction("browser","waitforpagetoload");
		isEventSuccessful=verifyHistoryEventColumnValue("Device Enabled","Device Disabled","DurationinUsageTable");
		if(isEventSuccessful)
		{
			strActualResult="Event column contain only devices enablement status";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 9 : Select event 'Device Deletion' type. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = selectEventType("Device Deletion");
		}
		else
		{
			return;
		}
						
		//**********************************************************//
		// Step 10 - Verify column of history table //
		//**********************************************************//   
		strStepDescription = "Verify event column contains only devices deletion status";
		strExpectedResult = "Event column should contain only devices deletion status";
		waitForPageLoaded();
		PerformAction("browser","waitforpagetoload");
		isEventSuccessful=verifyHistoryEventColumnValue("Device Deleted","Device Undeleted","DurationinUsageTable");
		if(isEventSuccessful)
		{
			strActualResult="Event column contain only devices deletion status";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 11 : Select event 'Device Note' type. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = selectEventType("Device Note");
		}
		else
		{
			return;
		}
		
		//**********************************************************//
		// Step 12 - Verify column of history table //
		//**********************************************************//   
		strStepDescription = "Verify event column contains only devices note status";
		strExpectedResult = "Event column should contain only devices note status";
		waitForPageLoaded();
		PerformAction("browser","waitforpagetoload");
		isEventSuccessful=verifyHistoryEventColumnValue("Note","Note","DurationinUsageTable");
		if(isEventSuccessful)
		{
			strActualResult="Event column contain only devices note status";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 13 : Select event 'Device OS Changed' type. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = selectEventType("Device OS Changed");
		}
		else
		{
			return;
		}
		
		//**********************************************************//
		// Step 14 - Verify column of history table //
		//**********************************************************//   
		strStepDescription = "Verify event column contains only device os status";
		strExpectedResult = "Event column should contain only device os status";
		waitForPageLoaded();
		PerformAction("browser","waitforpagetoload");
		isEventSuccessful=verifyHistoryEventColumnValue("","","DurationinUsageTable");
		if(isEventSuccessful)
		{
			strActualResult="Event column contain only device os status";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 15 : Select event 'Application Launched' type. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = selectEventType("Application Launched");
		}
		else
		{
			return;
		}
				
		//**********************************************************//
		// Step 16 - Verify column of history table //
		//**********************************************************//   
		strStepDescription = "Verify event column contains only Application Launched status";
		strExpectedResult = "Event column should contain only Application Launched status";
		waitForPageLoaded();
		PerformAction("browser","waitforpagetoload");
		isEventSuccessful=verifyHistoryEventColumnValue("Application Launched","Application Launched","DurationinUsageTable");
		if(isEventSuccessful)
		{
			strActualResult="Event column contain only Application Launched status";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 17 : Select event 'Application Launched' type. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = selectEventType("Application Install");
		}
		else
		{
			return;
		}
						
		//**********************************************************//
		// Step 18 - Verify column of history table //
		//**********************************************************//   
		strStepDescription = "Verify event column contains only Application installation status";
		strExpectedResult = "Event column should contain only Application installation status";
		waitForPageLoaded();
		PerformAction("browser","waitforpagetoload");
		isEventSuccessful=verifyHistoryEventColumnValue("Application Installed","Application Uninstalled","DurationinUsageTable");
		if(isEventSuccessful)
		{
			strActualResult="Event column contain only Application installation status";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
	}	
}