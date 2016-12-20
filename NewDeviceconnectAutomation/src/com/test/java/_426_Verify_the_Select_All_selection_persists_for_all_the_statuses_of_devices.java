package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1397
 */

public class _426_Verify_the_Select_All_selection_persists_for_all_the_statuses_of_devices extends ScriptFuncLibrary
{
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String selectedDeviceName = "";
	private java.util.List<String> devicesSelected = new java.util.ArrayList<String>();

	//****************************************************************************************************************************************************************************************************************************************************************************//
	// Select Offline status checkbox, select the select all checkbox on top on devices index page. Then select Available checkbox and verify that sleect all checkbox gets deselected. Again select select all checkbox and then verify that all devices' checkboxes get selected.
	//****************************************************************************************************************************************************************************************************************************************************************************//
	public final void testScript() throws Exception
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");

		//********************************************************//
		//** Step 1 : Launch deviceConnect and verify homepage. **//
		//********************************************************//
		isEventSuccessful = Login(EmailAddress, Password);

		//*********************************************//
		// Step 2 : Select Offline from status filters //
		//*********************************************//
		isEventSuccessful =  selectStatus("Offline");
		
		//*****************************************************//
		// Step 3 : Check if there are Offline devices present.//
		//*****************************************************//
		strstepDescription = "Check if there are Offline devices.";
		strexpectedResult = "There should be Offline devices displayed on the devices index page.";
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("devicestatus", "Offline", "list");
		if (isEventSuccessful)
		{
			strActualResult = "Offline devices are displayed on the page.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		if (!isEventSuccessful) // Return if no offline devices are displayed on the page.
		{
			return;
		}

		//************************************************************************************//
		// Step 4 : Click on select all checkbox on top of all devices on devices index page. //
		//************************************************************************************//
		isEventSuccessful = (boolean)selectAllDevicesCheckbox_DI()[0];

		//********************************************************//
		// Step 5 : Select available status checkbox//
		//********************************************************//
		isEventSuccessful = selectStatus("Available,Offline");
 
		//**********************************************************//
		// Step 6 : Verify that select all checkbox is not selected.//
		//**********************************************************//
		strstepDescription = "Verify that the select all checkbox is not selected.";
		strexpectedResult = "Select all checkbox should not be selected.";
		
		isEventSuccessful = PerformAction("chkSelectAll_Devices", Action.isNotSelected);
		
		if (isEventSuccessful)
		{
			strActualResult = "Select all checkbox is not selected.";
		}
		else
		{
			strActualResult = "Select all checkbox is still selected.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************************************************************//
		// Step 7 : Click on select all checkbox on top of all devices on devices index page and verify all checkboxes get selected.//
		//**************************************************************************************************************************//
		isEventSuccessful = (boolean)selectAllDevicesCheckbox_DI()[0];
	}
}