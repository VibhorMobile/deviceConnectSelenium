package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1235
 */

public class _427_Verify_Remove_button_is_not_displayed_to_Tester_User extends ScriptFuncLibrary
{
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String selectedDeviceName = "";
	private java.util.List<String> devicesSelected = new java.util.ArrayList<String>();

	//************************************************************************************************************************************************************//
	// Select available status checkbox, select the select all checkbox on top on devices index page. Verify that Remove button is not displayed to the test user.//
	//************************************************************************************************************************************************************//
	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");

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
		if (!isEventSuccessful) // Return if no removed devices are displayed on the page.
		{
			return;
		}

		//************************************************************************************//
		// Step 4 : Click on select all checkbox on top of all devices on devices index page. //
		//************************************************************************************//
		isEventSuccessful = (boolean)selectAllDevicesCheckbox_DI()[0];
		
		/*isEventSuccessful = selectAllCheckbox_DI();
		devicesSelected = tempRef_devicesSelected.argValue;*/

		//****************************************************//
		// Step 5 : Verify that 'Remove' button is not displayed.//
		//****************************************************//
		strstepDescription = "Verify that 'Remove' button is not displayed.";
		strexpectedResult = "'Remove' button should not be displayed.";
		isEventSuccessful = PerformAction("btnRemove_Devices", Action.isNotDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "'Remove' button is not displayed on the page.";
		}
		else
		{
			strActualResult = "Remove devices button is displayed on page." + "\r\n Reason (GenLib error): '" + strErrMsg_GenLib + "'.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*******************************************************************************//
		// Step 6 : Verify all the buttons on page and verify none of them has text 'Remove'//
		//*******************************************************************************//
		isEventSuccessful = VerifyNoelementWithGivenText("//button", "Remove");
		System.out.println("test cases execution completed");
	}
}