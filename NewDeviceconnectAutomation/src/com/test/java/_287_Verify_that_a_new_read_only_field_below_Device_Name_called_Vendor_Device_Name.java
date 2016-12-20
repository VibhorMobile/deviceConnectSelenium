package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

public class _287_Verify_that_a_new_read_only_field_below_Device_Name_called_Vendor_Device_Name extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		if(!isEventSuccessful)
			return;
				

		//**************************************************************************//
		// Step 2 : Click on any device name
		//**************************************************************************//
		 GoTofirstDeviceDetailsPage();
		
		//**************************************************************************//
		// Step 2 : Click on Show details... link onto Device Details page
		//**************************************************************************//
		 isEventSuccessful =  ShowDetails();
			 
		//**************************************************************************//
		// Step 4 : Verify Vendor Name field
		//**************************************************************************//
		strstepDescription = "Verify Vendor Name field";
		strexpectedResult = "Vendor Name field should be displayed";
		isEventSuccessful = PerformAction("eleVendorName_DeviceDetails", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Vendor Name field is displayed";
		}
		else
		{
			strActualResult = "Vendor Name field is not displayed";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}