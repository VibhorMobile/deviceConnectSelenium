package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2160
 */
public class _418_Verify_clicking_on_Cancel_on_Enable_confirmation_dialog_dialog_disappears_and_User_remains_on_same_page extends ScriptFuncLibrary
{
	//GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String selectedDeviceName = "";
	private java.util.List<String> devicesSelected = new java.util.ArrayList<String>();

	//*****************************************************************************************************************************************************//
	// Select removed status checkbox, click on Add button, then click on 'Cancel' button on dialog and verify that the user returns to devices index page.
	//*****************************************************************************************************************************************************//
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
		// Step 2 : Select Disabled from status filters //
		//*********************************************//
		isEventSuccessful = selectStatus("Disabled");

		//*****************************************************//
		// Step 3 : Check if there are Disabled devices present.//
		//*****************************************************//
		strstepDescription = "Check if there are Disabled devices.";
		strexpectedResult = "There should be Disabled devices displayed on the devices index page.";
		isEventSuccessful = VerifyMultipleValuesOfProperty_DI("status", "Disabled,Connected");
		if (isEventSuccessful)
		{
			strActualResult = "Disabled devices are displayed on the page.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		if (!isEventSuccessful) // Return if no Disabled devices are displayed on the page.
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

		//***********************************************************************************//
		// Step 6 : Click on 'Cancel' button and verify user returns to devices index page.";//
		//***********************************************************************************//
		strstepDescription = "Click on 'Cancel' button and verify user returns to devices index page."; //";
		strexpectedResult = "'Enable Device' dialog should disappear and user should return to devices index page.";
		isEventSuccessful = PerformAction("btnCancel_Device", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("hdrEnableDevice", Action.isNotDisplayed);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleDevicesTab_Devices", Action.isDisplayed);
				if (isEventSuccessful)
				{
					strActualResult = "'Enable Device' dialog closed and user returned to devices index page after clicking on 'Cancel' button.";
				}
				else
				{
					strActualResult = "Devices index page is not displayed.";
				}
			}
			else
			{
				strActualResult = "Enable device dialog did not disappear after clicking on 'Cancel' button." + "<br> Reason (GenLib error): '" + strErrMsg_GenLib + "'.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Cancel' button.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}