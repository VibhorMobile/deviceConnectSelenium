package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2164
 */

public class _285_Verify_that_on_the_Device_Details_page_next_to_the_Device_Name_text_there_should_be_a_link_Edit extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "";
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
		// Step 2 : Open device details page for an Android device.
		//**************************************************************************//
		strstepDescription = "Open device details page for an Android device.";
		strexpectedResult = "Device details page for the corresponding Android device should be displayed.";

		isEventSuccessful = selectPlatform( "Android");
		if (isEventSuccessful)
		{
			values = GoTofirstDeviceDetailsPage();
		    isEventSuccessful = (boolean) values[0] ;
		    deviceName =(String) values[1];
				if (!isEventSuccessful)
				{
					strActualResult = "SelectDevice---" + strErrMsg_AppLib;
				}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Device details page is displayed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//**************************************************************************//
		// Step 3 : Verify Edit link next to the device name on device details page.
		//**************************************************************************//
		strstepDescription = "Verify 'Edit' link next to the device name on device details page.";
		strexpectedResult = "'Edit' link should be displayed on the device details page.";
		isEventSuccessful = PerformAction("lnkEditDeviceName_DeviceDetailPage",Action.MouseHover);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Edit' link is displayed on the device details page.", "Pass");
		}
		else
		{
			strActualResult = "'Edit' link is not displayed on the device detail page.";
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//**************************************************************************//
		// Step 4 : Go back to "Devices" page.
		//**************************************************************************//
		strstepDescription = "Go back to 'Devices' page.";
		strexpectedResult = "Devices Page should be displayed.";
		isEventSuccessful = returnToDevicesPage();
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleDevicesHeader", Action.isDisplayed);
			if (!isEventSuccessful)
			{
				strActualResult = "Devices page is not displayed after clicking on 'Devices' tab.";
			}
		}
		else
		{
			strActualResult = "returnToDevicesPage---" + strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Devices page displayed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//**************************************************************************//
		// Step 5 : Open device details page for an iOS device.
		//**************************************************************************//
		strstepDescription = "Open device details page for an iOS device.";
		strexpectedResult = "Device details page for the corresponding iOS device should be displayed.";

		//PerformAction("browser", Action.Scroll, "30");
		isEventSuccessful = selectPlatform("iOS");
		if (isEventSuccessful)
		{
			values = GoTofirstDeviceDetailsPage();
		    isEventSuccessful = (boolean) values[0] ;
		    deviceName =(String) values[1];
				if (!isEventSuccessful)
				{
					strActualResult = "SelectDevice---" + strErrMsg_AppLib;
				}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Device details page is displayed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//**************************************************************************//
		// Step 6 : Verify Edit link next to the device name on device details page.
		//**************************************************************************//
		strstepDescription = "Verify 'Edit' link next to the device name on device details page.";
		strexpectedResult = "'Edit' link should be displayed on the device details page.";
		isEventSuccessful = PerformAction("lnkEditDeviceName_DeviceDetailPage",Action.MouseHover);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Edit' link is displayed on the device details page.", "Pass");
		}
		else
		{
			strActualResult = "'Edit' link is not displayed on the device detail page.";
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
	}
}