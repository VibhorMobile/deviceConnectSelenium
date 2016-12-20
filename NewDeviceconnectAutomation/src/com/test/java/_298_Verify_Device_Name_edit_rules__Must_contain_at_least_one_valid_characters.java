package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2164
 */
public class _298_Verify_Device_Name_edit_rules__Must_contain_at_least_one_valid_characters extends ScriptFuncLibrary
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
		// Step 2: Select Status filter : Offline 
		//**************************************************************************//
		isEventSuccessful = selectStatus("Offline");
		

		//**************************************************************************//
		// Step 3: Go to details page of an offline device.
		//**************************************************************************//
		       values = GoTofirstDeviceDetailsPage();
		       isEventSuccessful = (boolean) values[0] ;
		       deviceName =(String) values[1];
		
		      if (!isEventSuccessful)
		     {
		 	     return;
		     }

		//**************************************************************************//
		// Step 4: Click on Edit link in front of device name.
		//**************************************************************************//
		strstepDescription = "Click on Edit link in front of device name.";
		strexpectedResult = "Name field should become editable";
		isEventSuccessful = PerformAction("lnkEditDeviceName_DeviceDetailPage",Action.ClickUsingJS);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("txtDeviceName", Action.isEnabled);
			if (!isEventSuccessful)
			{
				strActualResult = "Name field did not become editable after clicking on 'edit' link.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Edit' link";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Name field became editable after clicking on 'Edit' link.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//**************************************************************************//
		// Step 5 : Enter white-spaces in Name field and verify error message.
		//**************************************************************************//
		strstepDescription = "Enter white-spaces in Name field and try to save device name.";
		strexpectedResult = "Device name should not be saved on device details page.";
		isEventSuccessful = PerformAction("txtDeviceName", Action.Type, "    ");
		if (isEventSuccessful)
		{
			 isEventSuccessful = PerformAction("btnSaveDeviceName_DeviceDetails", Action.Click);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("txtDeviceName", Action.isEnabled);
				if (!isEventSuccessful)
				{
					strActualResult = "Device name able to saved on device details page.";
				}
			}
			else
			{
				strActualResult = "Could not click, after entering characters in device name field.";
			}
		}
		else
		{
			strActualResult = "Could not enter white spaces in Name field.";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Device name is not saved on device details page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
	}
}