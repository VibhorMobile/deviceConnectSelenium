package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

public class _304_Verify_that_the_new_field_Vendor_Device_Name_is_read_only_on_UI extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		// Step 1 : login to deviceConnect with valid user credentials.
		isEventSuccessful = Login();
		
		
		// Step 2 : Click on any device name
		strstepDescription = "Click on any device name";
		strexpectedResult = "Device details page should be displayed";
		isEventSuccessful = SelectDevice("first");
		if (isEventSuccessful)
		{
			strActualResult = "Device details page is displayed";
		}
		else
		{
			strActualResult = "Device details page is not displayed";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Step 3 : Click on 'Show details...' link
		strstepDescription = "Click on 'Show details...' link to display hidden device details";
		strexpectedResult = "User should be able to click on the 'more...' link";
		isEventSuccessful = PerformAction("lnkShowDetails", Action.isDisplayed);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
			if (isEventSuccessful)
			{
				strActualResult = "User is able to click on 'Show details...' link successfully.";
			}
			else
			{
				strActualResult = "Could not click on 'Show details...' link on device details page.";
			}
		}
		else
		{
			strActualResult = "'Show details...' link is not displayed.";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Step 3 : Verify Vendor Device Name field
		strstepDescription = "Verify Vendor Device Name field";
		strexpectedResult = "Vendor Device Name field should be displayed as read only";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Vendor Name");
		//isEventSuccessful = PerformAction("eleVendorDeviceName", Action.Exist);
		if (isEventSuccessful)
		{
			strActualResult = "Vendor Device Name field is displayed as read only";
		}
		else
		{
			strActualResult = "Vendor Device Name field is not displayed";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);



	}
}