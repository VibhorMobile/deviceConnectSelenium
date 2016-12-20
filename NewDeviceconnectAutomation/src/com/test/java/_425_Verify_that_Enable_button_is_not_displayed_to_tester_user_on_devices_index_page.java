package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-449
 */

public class _425_Verify_that_Enable_button_is_not_displayed_to_tester_user_on_devices_index_page extends ScriptFuncLibrary
{
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String selectedDeviceName = "";
	private java.util.List<String> devicesSelected = new java.util.ArrayList<String>();

	//***********************************************************************************************************//
	// Select disabled status checkbox, click on Enable button, and verify various button on the dialog that appears.
	//***********************************************************************************************************//
	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");

		//********************************************************//
		//** Step 1 : Launch deviceConnect and verify homepage. **//
		//********************************************************//
		
		isEventSuccessful = Login(EmailAddress, Password);
		

		//*********************************************//
		// Step 2 : Select Disabled from status filters //
		//*********************************************//
		isEventSuccessful = selectStatus("Disabled");
		
		//*****************************************************//
		// Step 3 : Check if there are disabled devices present.//
		//*****************************************************//
		strstepDescription = "Check if there are disabled devices.";
		strexpectedResult = "There should be disabled devices displayed on the devices index page.";
		isEventSuccessful = VerifyMultipleValuesOfProperty_DI("Status", "Disabled,Connected");
		if (isEventSuccessful)
		{
			strActualResult = "Removed devices are displayed on the page.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		if (!isEventSuccessful) // Return if no disabled devices are displayed on the page.
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


		//****************************************************//
		// Step 5 : Verify that 'Enable' button is not displayed.//
		//****************************************************//
		strstepDescription = "Verify that 'Enable' button is not displayed.";
		strexpectedResult = "'Enable' button should not be displayed.";
		isEventSuccessful = PerformAction("btnEnable_Devices", Action.isNotDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "'Enable' button is not displayed on the page.";
		}
		else
		{
			strActualResult = "Enable devices button is displayed on page." + "\r\n Reason (GenLib error): '" + strErrMsg_GenLib + "'.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*******************************************************************************//
		// Step 6 : Verify all the buttons on page and verify none of them has text 'Enable'//
		//*******************************************************************************//
		isEventSuccessful = VerifyNoelementWithGivenText("//button", "Enable");
		
	}
}