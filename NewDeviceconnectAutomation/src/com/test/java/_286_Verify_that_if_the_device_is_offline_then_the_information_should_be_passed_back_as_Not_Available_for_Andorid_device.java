package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1331
 */

public class _286_Verify_that_if_the_device_is_offline_then_the_information_should_be_passed_back_as_Not_Available_for_Andorid_device extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	 {
		String DeviceUsage = "";

		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		if(!isEventSuccessful)
			return;

		//**************************************************************************//
		// Step 2 : Select Android Platform .
		//**************************************************************************//
		isEventSuccessful =   selectPlatform("Android");
		
		//**************************************************************************//
		// Step 3 : Select Offline status.
		//**************************************************************************//
		isEventSuccessful =   selectStatus("Offline");

		//**************************************************************************//
		// Step 4 :  Navigate to the device details of first Android Offline device
		//**************************************************************************//
		   GoTofirstDeviceDetailsPage();

		//**************************************************************************//
		// Step 4 : Verify Battery status is not displayed on the device details page.
		//**************************************************************************//
		 strstepDescription = "Verify Battery status is not displayed on the device details page.";
		 strexpectedResult =  "Battery status should not be displayed for Android Offline device on the device details page.";
	   	isEventSuccessful = PerformAction("eleBatteryStatus_DeviceDetails", Action.isNotDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Successfully Battery status is not displayed for Android Offline device on the device details page.";
			}
			else
			{
				strActualResult = "Battery status is displayed for Android Offline device on the device details page.";
			}

			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		 
		//**************************************************************************//
		// Step 5 : Click on 'Show details' link on device details page.
		//**************************************************************************//
			isEventSuccessful = ShowDetails();

		//**************************************************************************//
		// Step 6 : Verify Disk Usage is displayed as 'Not Available' on the device details page.
		//**************************************************************************//
		strstepDescription = "Verify Disk Usage is displayed as ' Not Available' on the device details page.";
		strexpectedResult =  "Disk Usage should be displayed as ' Not Available' for Android Offline device on the device details page.";
		DeviceUsage =   GetTextOrValue("eleDiskUsageValue_DeviceDetails", "text");
			if (DeviceUsage.equals("Not Available"))
			{
				strActualResult = "Successfully Disk Usage is displayed as ' Not Available' for Android Offline device on the device details page.";
			}
			else
			{
				strActualResult = "Disk Usage is not displayed as ' Not Available' for Android Offline device on the device details page..";
			}

			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
	}
}