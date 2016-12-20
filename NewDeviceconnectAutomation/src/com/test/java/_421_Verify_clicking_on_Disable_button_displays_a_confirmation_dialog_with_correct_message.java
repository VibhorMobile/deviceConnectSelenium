package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1254
 */

public class _421_Verify_clicking_on_Disable_button_displays_a_confirmation_dialog_with_correct_message extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String selectedDeviceName = "";
	private String ExpectedMessageText = "", ActualMessageText = "";
	private String devicesSelected = "";

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
		
		isEventSuccessful =  selectStatus("Available");
		

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
		// Step 5 : Click on 'Disable' button and verify 'Disable devices' dialog.//
		//****************************************************************//
		strstepDescription = "Click on 'Disable' button and verify 'Disable devices' dialog.";
		strexpectedResult = "'Disable Devices' dialog should be displayed.";
		isEventSuccessful = PerformAction("btnBulkDisable_Devices", Action.Click);
		if (isEventSuccessful)
		{	
			isEventSuccessful = PerformAction("btnDisableDevices_DisableDevice", Action.WaitForElement, "20");
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

		//***********************************************************************************//
		// Step 6 : Click on 'Cancel' button and verify user returns to devices index page.";//
		//***********************************************************************************//
		strstepDescription = "Click on 'Cancel' button and verify user returns to devices index page.";
		strexpectedResult = "'Remove Device' dialog should disappear and user should return to devices index page.";
		isEventSuccessful = PerformAction("eleConfirmDisableMsg", Action.isDisplayed);
		
		if (isEventSuccessful)
		{
			ActualMessageText = GetTextOrValue("eleConfirmDisableMsg", "text");
			int noOfDevices	 = getelementCount(dicOR.get("eleDevicesHolderListView")) - 1; // Get number of devices' rows . '-1' because it counts the header also.
			ExpectedMessageText = "Do you wish to disable these " + noOfDevices + " devices? These devices will no longer be usable until enabled. This action can not be undone.";
			isEventSuccessful = (ExpectedMessageText.equals(ActualMessageText));
			if (isEventSuccessful)
			{
				strActualResult = "Correct warning message is displayed : '" + ActualMessageText + "'.";
			}
			else
			{
				strActualResult = "Warning message is not correct, it is : '" + ActualMessageText + "' and not <br>'" + ExpectedMessageText;
			}
		}
		else
		{
			strActualResult = "Message not displayed on the dialog.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}