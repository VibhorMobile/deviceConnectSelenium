package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-537
 */

public class _284_Verify_that_When_the_user_clicks_on_the_Edit_link_the_Device_Name_should_become_an_editable_field extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	Object[] values = new Object[2];
	String deviceName = "";

	public final void testScript()
	{
		///**************************************************************************//
		//Step 1 - : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		isEventSuccessful = Login();

		//**************************************************************************//
		// Step 2 : Click on any device name
		//**************************************************************************//
		strstepDescription = "Click on any device name";
		strexpectedResult = "Device details page should be displayed";
		if (isEventSuccessful)
		{
			values = GoTofirstDeviceDetailsPage();
		    isEventSuccessful = (boolean) values[0] ;
		    deviceName =(String) values[1];
			
		if (isEventSuccessful)
		{
			strActualResult = "Device details page is displayed";
		}
		else
		{
			strActualResult = "Device details page is not displayed";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 3 : Click on Edit link
		//**************************************************************************//
		strstepDescription = "Click on Edit link";
		strexpectedResult = "Device name should become editable";
		isEventSuccessful = PerformAction("lnkEditDeviceName_DeviceDetailPage",Action.ClickUsingJS);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("txtDeviceName", Action.isEnabled);
			if (isEventSuccessful)
			{
				strActualResult = "Device name is editable";
			}
			else
			{
				strActualResult = "Device name is not editable";
			}
		}
		else
		{
			strActualResult = "Unable to click on Edit link";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 4 : Verify Save link
		//**************************************************************************//
		strstepDescription = "Verify Save link";
		strexpectedResult = "Save link should be present";
		isEventSuccessful = PerformAction("btnSaveDeviceName_DeviceDetails", Action.Exist);
		if (isEventSuccessful)
		{
			strActualResult = "Save link is displayed";
		}
		else
		{
			strActualResult = "Save link is not displayed";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 5 : Verify Cancel link
		//**************************************************************************//
		strstepDescription = "Verify Cancel link";
		strexpectedResult = "Cancel link should be present";
		isEventSuccessful = PerformAction("lnkCancel_deviceDetailsEditName", Action.Exist);
		if (isEventSuccessful)
		{
			strActualResult = "Cancel link is displayed";
		}
		else
		{
			strActualResult = "Cancel link is not displayed";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
	}
		
  }
	
}