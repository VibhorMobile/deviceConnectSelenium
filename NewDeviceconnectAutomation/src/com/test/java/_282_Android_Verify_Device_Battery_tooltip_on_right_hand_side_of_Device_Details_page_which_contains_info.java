package com.test.java;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;


/*
 * JIRA ID --> QA-1099
 */

public class _282_Android_Verify_Device_Battery_tooltip_on_right_hand_side_of_Device_Details_page_which_contains_info extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private Object[] arrvalues = new Object[2]  ;

	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		if(!isEventSuccessful)
			return;

		//**************************************************************************//
		// Step 2 : Select Android Platform and Available status.
		//**************************************************************************//
		
			isEventSuccessful =selectPlatform("Android");
			if (isEventSuccessful)
			{
				selectStatus("Available");
			}
			else
				return;
				 

		//**************************************************************************//
		// Step 3 :  Navigate to the device details of first Android device
		//**************************************************************************//
		arrvalues = GoTofirstDeviceDetailsPage();
		String DeviceName = (String) arrvalues[1];
		isEventSuccessful =  (boolean) arrvalues[0];
		if (!isEventSuccessful)
		{
			return;
		}
		
		//**************************************************************************//
		// Step 4 : Verify Battery status is displayed on the device details page.
		//**************************************************************************//
		strstepDescription = "Verify Battery status is displayed on the device details page.";
		strexpectedResult = "Battery status should be displayed on the device details page.";
		
	    String BatteryStatus =	getAttribute("eleBatteryStatus","title");
		if (!BatteryStatus.isEmpty())
		{
		   isEventSuccessful = true;
		   strActualResult = "Battery status is displayed on the device details page.";
		}
		else
		{
			isEventSuccessful = false;
			strActualResult = "Battery status is not displayed on the device details page.";
		}
		
		
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
						
		
	}
}