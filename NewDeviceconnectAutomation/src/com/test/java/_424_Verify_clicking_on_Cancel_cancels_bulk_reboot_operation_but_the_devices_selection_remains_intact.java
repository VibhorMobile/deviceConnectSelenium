package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1066
 */

public class _424_Verify_clicking_on_Cancel_cancels_bulk_reboot_operation_but_the_devices_selection_remains_intact extends ScriptFuncLibrary
{
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
//C# TO JAVA CONVERTER WARNING: There is no Java equivalent to C#'s shadowing via the 'new' keyword:
//ORIGINAL LINE: new string strActualResult = "";
	private String strActualResult = ""; // new is appended as this hides the inherited class's(ScriptFuncLibrary's) strActualResult variable
	private java.util.List<String> devicesSelected = new java.util.ArrayList<String>();
	private java.util.List<String> devicesSelected_afterCancel = new java.util.ArrayList<String>();

	//*******************************************************************************************************************************************************************************************************************//
	// Select available status checkbox, click on Reboot button, then click on 'Cancel' button on dialog and verify that the user returns to devices index page. Also, verify that all the checkboxes are still selected.//
	//*******************************************************************************************************************************************************************************************************************//
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
		if (!isEventSuccessful) // Return if no removed devices are displayed on the page.
		{
			return;
		}

		//************************************************************************************//
		// Step 4 : Click on select all checkbox on top of all devices on devices index page. //
		//************************************************************************************//
		isEventSuccessful = (boolean)selectAllDevicesCheckbox_DI()[0];
		
		//****************************************************************//
		// Step 5 : Click on 'Reboot' button and verify 'Remove devices' dialog.//
		//****************************************************************//
		strstepDescription = "Click on 'Reboot' button and verify 'Reboot devices' dialog.";
		strexpectedResult = "'Reboot Devices' dialog should be displayed.";
		isEventSuccessful = PerformAction("btnReboot_Devices", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("hdrRebootDevice", Action.WaitForElement, "20");
			if (isEventSuccessful)
			{
				strActualResult = "Reboot device dialog displayed successfully after clicking on 'Reboot' button.";
			}
			else
			{
				strActualResult = "'Reboot Device' dialog not displayed after clicking on 'Reboot' button.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Reboot' button.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//***********************************************************************************//
		// Step 6 : Click on 'Cancel' button and verify user returns to devices index page.";//
		//***********************************************************************************//
		strstepDescription = "Click on 'Cancel' button and verify user returns to devices index page."; //";
		strexpectedResult = "'Remove Device' dialog should disappear and user should return to devices index page.";
		isEventSuccessful = PerformAction("btnCancel_Device", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("hdrRemoveDevice", Action.isNotDisplayed);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleDevicesTab_Devices", Action.isDisplayed);
				if (isEventSuccessful)
				{
					strActualResult = "'Remove Device' dialog closed and user returned to devices index page after clicking on 'Cancel' button.";
				}
				else
				{
					strActualResult = "Devices index page is not displayed.";
				}
			}
			else
			{
				strActualResult = "Remove device dialog did not disappear after clicking on 'Cancel' button." + "<br> Reason (GenLib error): '" + strErrMsg_GenLib + "'.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Cancel' button.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*******************************************************//
		// Step 7 : Verify that the select all checkbox is still selected.//
		//*******************************************************//
		strstepDescription = "Verify that the select all checkbox is still selected.";
		strexpectedResult = "Select all checkbox should remain checked.";
		isEventSuccessful = PerformAction("chkSelectAll_Devices", Action.isSelected);
		if (isEventSuccessful)
		{
			strActualResult = "Select all checkbox is still selected.";
		}
		else if (PerformAction("chkSelectAll_Devices", Action.isNotSelected))
		{
			strActualResult = "Select all checkbox is not selected.";
		}
		else
		{
			strActualResult = "Select all checkbox not found on page.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************************//
		// Step 8 : Verify that checkboxes in front of all the devices are checked.//
		//*************************************************************************//
		strstepDescription = "Verify that checkboxes in front of all the devices are checked.";
		strexpectedResult = "Checkboxes in front of all the devices should be checked/selected.";
		isEventSuccessful = (boolean)VerifyAllCheckedOrUnchecked_DI(Action.isSelected)[0];
		if (isEventSuccessful)
		{
			strActualResult = "All checkboxes are still checked/selected.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}