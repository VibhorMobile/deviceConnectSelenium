package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2158
 */
public class _422_Verify_clicking_on_Disable_button_displays_confirmation_dialog_with_Cancel_Disable_Devices_and_close_button extends ScriptFuncLibrary
{
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String selectedDeviceName = "";
	private java.util.List<String> devicesSelected = new java.util.ArrayList<String>();

	//***********************************************************************************************************//
	// Select available status checkbox, click on Disable button, and verify various button on the dialog that appears.
	//***********************************************************************************************************//
	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		//********************************************************//
		//** Step 1 : Launch deviceConnect and verify homepage. **//
		//********************************************************//
		isEventSuccessful =Login(EmailAddress, Password);
		

		//*********************************************//
		// Step 2 : Select available from status filters //
		//*********************************************//
		isEventSuccessful = selectStatus("Available");
		

		//*****************************************************//
		// Step 3 : Check if there are available devices present.//
		//*****************************************************//
		strstepDescription = "Check if there are available devices.";
		strexpectedResult = "There should be available devices displayed on the devices index page.";
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("devicestatus", "Available", "list");
		if (isEventSuccessful)
		{
			strActualResult = "Available devices are displayed on the page.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		if (!isEventSuccessful) // Return if no available devices are displayed on the page.
		{
			return;
		}

		//************************************************************************************//
		// Step 4 : Click on select all checkbox on top of all devices on devices index page. //
		//************************************************************************************//
		isEventSuccessful = (boolean)selectAllDevicesCheckbox_DI()[0];

		//****************************************************************//
		// Step 5 : Click on 'Disable' button and verify 'Disable devices' dialog.//
		//****************************************************************//
		strstepDescription = "Click on 'Disable' button and verify 'Disable devices' dialog.";
		strexpectedResult = "'Disable Devices' dialog should be displayed.";
		isEventSuccessful = PerformAction("btnBulkDisable_Devices", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("hdrDisableDevices_devicepage", Action.WaitForElement, "20");
			if (isEventSuccessful)
			{
				strActualResult = "Disable device dialog displayed successfully after clicking on 'Disable' button.";
			}
			else
			{
				strActualResult = "'Disable Device' dialog not displayed after clicking on 'Disable' button.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Disable' button.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//************************************************************************************//
		// Step 6 : Verify 'Cancel' button is displayed on the dialog.//
		//************************************************************************************//
		strstepDescription = "Verify 'Cancel' button is displayed on the dialog.";
		strexpectedResult = "'Cancel' button should be displayed on the dialog that appears.";
		isEventSuccessful = PerformAction("btnCancel_Device", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "'Cancel' button is displayed on 'Remove Devices' dialog.";
		}
		else
		{
			strActualResult = "'Cancel' button is not displayed on 'Remove Devices' dialog.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*****************************************************************//
		// Step 7 : Verify 'Disable Devices' button is displayed on the dialog.//
		//*****************************************************************//
		strstepDescription = "Verify 'Disable Devices' button is displayed on the dialog.";
		strexpectedResult = "'Disable Devices' button should be displayed on the dialog that appears.";
		isEventSuccessful = PerformAction("btnDisableDevices_DisableDevice", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult  = "'Disable Devices' button is displayed on 'Disable Devices' dialog.";
		}
		else
		{
			strActualResult = "'Disable Devices' button is not displayed on 'Disable Devices' dialog.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//************************************************************//
		// Step 8 : Verify 'Cancel' button is displayed on the dialog.//
		//************************************************************//
		strstepDescription = "Verify 'Cancel' button is displayed on the dialog.";
		strexpectedResult = "'Close' button should be displayed on the dialog that appears.";
		isEventSuccessful = PerformAction("btnX_dialogs", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "'Close' button is displayed on 'Disable Devices' dialog.";
		}
		else
		{
			strActualResult = "'Close' button is not displayed on 'Disable Devices' dialog.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

	}
	
}