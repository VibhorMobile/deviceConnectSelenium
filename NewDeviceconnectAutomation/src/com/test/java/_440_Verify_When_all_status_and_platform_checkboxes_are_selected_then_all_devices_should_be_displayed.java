package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1224
 */

public class _440_Verify_When_all_status_and_platform_checkboxes_are_selected_then_all_devices_should_be_displayed extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", devicename = "";

	public final void testScript()
	{
		///Step 1 - Login to deviceConnect
			isEventSuccessful = Login();
			
		//**************************************************************************//
		// Step 2: Select the all Status checkboxes
		//**************************************************************************//
			isEventSuccessful = selectStatus("Available,Offline,Disabled,In Use");
		//**************************************************************************//
			
			
		//**************************************************************************//
		// Step 3: Select the all Platform checkboxes
		//**************************************************************************//	
			isEventSuccessful = selectPlatform("iOS,Android");
			
		// Step 4 : Verify_When_all_status_and_platform_checkboxes_are_selected_then_all_devices_should_be_displayed
		//**************************************************************************//
		strstepDescription = "Verify When_all_status_and_platform_checkboxes_are_selected_then_all_devices_should_be_displayed: Available.";
		strexpectedResult = "When_all_status_and_platform_checkboxes_are_selected_then_all_devices_should_be_displayed: Available";
		                    
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("existenceofstatus", "Available" , "list");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "User can see all devices on DC UI: Available.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		}

		//**************************************************************************//
		// Step 5 : Verify When_all_status_and_platform_checkboxes_are_selected_then_all_devices_should_be_displayed :  Offline.
		//**************************************************************************//
		strstepDescription = "Verify When_all_status_and_platform_checkboxes_are_selected_then_all_devices_should_be_displayed: Offline.";
		strexpectedResult = "When_all_status_and_platform_checkboxes_are_selected_then_all_devices_should_be_displayed: Offline";
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("existenceofstatus", "Offline", "list");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "User can see all devices on DC UI: Offline.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		}

		//**************************************************************************//
		// Step 6 : Verify When_all_status_and_platform_checkboxes_are_selected_then_all_devices_should_be_displayed : Disabled
		//**************************************************************************//
		strstepDescription = "Verify When_all_status_and_platform_checkboxes_are_selected_then_all_devices_should_be_displayed: Disabled.";
		strexpectedResult = "When_all_status_and_platform_checkboxes_are_selected_then_all_devices_should_be_displayed: Disabled.";
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("existenceofstatus", "Connected", "list");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "User can see all devices on DC UI: Disabled.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		}

		//**************************************************************************//
		// Step 7 : Verify When_all_status_and_platform_checkboxes_are_selected_then_all_devices_should_be_displayed : In Use.
		//**************************************************************************//
		strstepDescription = "Verify When_all_status_and_platform_checkboxes_are_selected_then_all_devices_should_be_displayed: In Use.";
		strexpectedResult = "When_all_status_and_platform_checkboxes_are_selected_then_all_devices_should_be_displayed: In Use.";
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("existenceofstatus", "In Use", "list");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "User can see all devices on DC UI: In Use.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		}
		
     }
	
}
