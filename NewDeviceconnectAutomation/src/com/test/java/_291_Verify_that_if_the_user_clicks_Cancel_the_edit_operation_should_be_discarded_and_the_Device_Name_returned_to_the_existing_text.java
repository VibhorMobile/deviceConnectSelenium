package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2164
 */
public class _291_Verify_that_if_the_user_clicks_Cancel_the_edit_operation_should_be_discarded_and_the_Device_Name_returned_to_the_existing_text extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", strText = "", strTextAtEnd = "";

	public final void testScript()
	{
		//**************************************************************************//
		// Step 1 : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		strstepDescription = "Login to deviceConnect with valid username and password and verify Devices page.";
		strexpectedResult = "User should be logged in and navigated to Devices page.";
		isEventSuccessful = LoginToDC();
		if (isEventSuccessful)
		{
			strActualResult = "User - " + dicCommon.get("EmailAddress") + " is logged in to deviceConnect.";
		}
		else
		{
			strActualResult = "User - " + dicCommon.get("EmailAddress") + " is not logged in to deviceConnect.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 2 : Click on any device name
		//**************************************************************************//
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

		//**************************************************************************//
		// Step 3 : Click on edit link
		//**************************************************************************//
		strstepDescription = "Click on edit link";
		strexpectedResult = "Device name should be editable";
		isEventSuccessful = PerformAction("lblDeviceName_DeviceDetails", Action.MouseHover);
		isEventSuccessful = PerformAction("lnkEditDeviceName_DeviceDetailPage", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("txtDeviceName", Action.Exist);
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
		// Step 4 : Enter a device name
		//**************************************************************************//
		strstepDescription = "Enter a device name";
		strexpectedResult = "New device name should be entered";
		strText = GetTextOrValue("txtDeviceName", "value");
		isEventSuccessful = PerformAction("txtDeviceName", Action.Type, "Device1");
		if (isEventSuccessful)
		{
			strActualResult = "Device Name entered is 'Device1' replacing - " + strText;
		}
		else
		{
			strActualResult = "Unable to enter Device name";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 5 : Click on Cancel link
		//**************************************************************************//
		strstepDescription = "Click on Cancel link";
		strexpectedResult = "Actual Device name should be displayed";
		isEventSuccessful = PerformAction("lnkCancel_deviceDetailsEditName", Action.Click);
		if (PerformAction("lnkCancel", Action.Exist))
		{
			PerformAction("lnkCancel", Action.Click);
		}
		if (isEventSuccessful)
		{
			strTextAtEnd = GetTextOrValue("deviceName_detailsPage", "text");
			isEventSuccessful = strTextAtEnd.equals(strText);
			if (isEventSuccessful)
			{
				strActualResult = "Actual device name - " + strText + " is displayed";
			}
			else
			{
				strActualResult = "Device name could not be changed back to - '" + strText + "' but it is changed to '" + strTextAtEnd + "'. ";
			}
		}
		else
		{
			strActualResult = "Unable to click on Cancel link";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}