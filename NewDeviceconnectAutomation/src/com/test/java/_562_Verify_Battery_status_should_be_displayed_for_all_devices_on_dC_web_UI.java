package com.test.java;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Deepak
 * Creation Date: 5-Feb-2016
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-1099
 */

public class _562_Verify_Battery_status_should_be_displayed_for_all_devices_on_dC_web_UI extends ScriptFuncLibrary
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
		{
			return;
		}

		//**************************************************************************//
		// Step 2 : Select Android Platform and Available status.
		//**************************************************************************//
		
		isEventSuccessful =selectPlatform("iOS,Android");
		if (isEventSuccessful)
		{
			isEventSuccessful=selectStatus("Available,In Use");
			if(!isEventSuccessful)
			{
				return;
			}
		}
		else
		{
			return;
		}
		

		//**************************************************************************//
		// Step 3 : Verify Battery status is displayed on the all the device details page.
		//**************************************************************************//
		strstepDescription = "Verify Battery status is displayed on all the device details page.";
		strexpectedResult = "Battery status should be displayed on all the device details page.";
		
		isEventSuccessful=VerifyBatteryStatusDisplayedOnAllDevices(getelementCount("eleDevicesHolderListView") - 1);
		if (isEventSuccessful)
		{
		   strActualResult = "Battery status is displayed on all the device details page.";
		}
		else
		{
			strActualResult = "Battery status is not displayed on all the device details page.";
		}
		
		
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
						
		
	}
}