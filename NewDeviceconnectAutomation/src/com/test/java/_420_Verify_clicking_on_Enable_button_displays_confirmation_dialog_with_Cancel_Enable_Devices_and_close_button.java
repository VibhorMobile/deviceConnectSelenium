package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2159
 */
public class _420_Verify_clicking_on_Enable_button_displays_confirmation_dialog_with_Cancel_Enable_Devices_and_close_button extends ScriptFuncLibrary
{
	//GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String selectedDeviceName = "";
	private java.util.List<String> devicesSelected = new java.util.ArrayList<String>();

	//***********************************************************************************************************//
	// Select removed status checkbox, click on Enable button, and verify various button on the dialog that appears.
	//***********************************************************************************************************//
	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		
		//********************************************************//
		//** Step 1 : Launch deviceConnect and verify homepage. **//
		//********************************************************//
		isEventSuccessful = Login(EmailAddress, Password);
		
		//*********************************************//
		// Step 2 : Select removed from status filters //
		//*********************************************//
		isEventSuccessful = selectStatus("Disabled");
		
		//*****************************************************//
		// Step 3 : Check if there are Disablede devices present.//
		//*****************************************************//
		strstepDescription = "Check if there are disabled devices.";
		strexpectedResult = "There should be disabled devices displayed on the devices index page.";
		isEventSuccessful = VerifyMultipleValuesOfProperty_DI("status", "Disabled,Connected");
		
		if (isEventSuccessful)
		{
			strActualResult = "Removed devices are displayed on the page.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		if (!isEventSuccessful) // Return if no removed devices are displayed on the page.
		{
			isEventSuccessful = DisplayDisabledDevices();
		}

		//************************************************************************************//
		// Step 4 : Click on select all checkbox on top of all devices on devices index page. //
		//************************************************************************************//
		if(isEventSuccessful)
		{
		isEventSuccessful = (boolean)selectAllDevicesCheckbox_DI()[0];
		}
		else 
		{
			throw new RuntimeException("disable devices are not displayed");
		}
		
		//****************************************************************//
		// Step 5 : Click on 'Enable' button and verify 'Enable devices' dialog.//
		//****************************************************************//
		strstepDescription = "Click on 'Enable' button and verify 'Enable devices' dialog.";
		strexpectedResult = "'Enable Devices' dialog should be displayed.";
		isEventSuccessful = PerformAction("btnEnable_Devices", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("hdrEnableDevice", Action.WaitForElement, "20");
			if (isEventSuccessful)
			{
				strActualResult = "Enable device dialog displayed successfully after clicking on 'Enable' button.";
			}
			else
			{
				strActualResult = "'Enable Device' dialog not displayed after clicking on 'Enable' button.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Enable' button.";
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
			strActualResult = "'Cancel' button is displayed on 'Enable Devices' dialog.";
		}
		else
		{
			strActualResult = "'Cancel' button is not displayed on 'Enable Devices' dialog.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*****************************************************************//
		// Step 7 : Verify 'Enable Devices' button is displayed on the dialog.//
		//*****************************************************************//
		strstepDescription = "Verify 'Enable Devices' button is displayed on the dialog.";
		strexpectedResult = "'Enable Devices' button should be displayed on the dialog that appears.";
		isEventSuccessful = PerformAction("btnEnableDevices_EnableDevice", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "'Enable Devices' button is displayed on 'Enable Devices' dialog.";
		}
		else
		{
			strActualResult = "'Enable Devices' button is not displayed on 'Enable Devices' dialog.";
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
			strActualResult = "'Close' button is displayed on 'Enable Devices' dialog.";
		}
		else
		{
			strActualResult = "'Close' button is not displayed on 'Enable Devices' dialog.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//************************************************************//
		// Step 9 : Post condition click the Cancel button, so that Logout can be called for IE.//
		//************************************************************//
		PerformAction("btnCancel_RemoveDevice", Action.Click);
	}
}