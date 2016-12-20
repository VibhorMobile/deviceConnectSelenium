package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1280
 */

public class _419_Verify_clicking_on_Enable_button_displays_a_confirmation_dialog_with_correct_message extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String selectedDeviceName = "";
	private String ExpectedMessageText = "", ActualMessageText = "";
	private java.util.List<String> devicesSelected = new java.util.ArrayList<String>();
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
		// Step 2 : Select disabled from status filters //
		//*********************************************//
		isEventSuccessful = selectStatus("Disabled");


		//*****************************************************//
		// Step 3 : Check if there are disabled devices present.//
		//*****************************************************//
		strstepDescription = "Check if there are disabled devices.";
		strexpectedResult = "There should be disabled devices displayed on the devices index page.";
		isEventSuccessful = VerifyMultipleValuesOfProperty_DI("status", "Disabled,Connected");
		if (isEventSuccessful)
		{
			strActualResult = "disabled devices are displayed on the page.";
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
	

		//****************************************************************//
		// Step 5 : Click on 'Enable' button and verify 'Enable devices' dialog.//
		//****************************************************************//
		strstepDescription = "Click on 'Enable' button and verify 'Enable devices' dialog.";
		strexpectedResult = "'Enable Devices' dialog should be displayed.";
		int noOfDevices	 = getelementCount(dicOR.get("eleDevicesHolderListView")) - 1; // Get number of devices' rows . '-1' because it counts the header also.
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
		strstepDescription = "Click on 'Cancel' button and verify user returns to devices index page.";
		strexpectedResult = "'Enable Device' dialog should disappear and user should return to devices index page.";
		isEventSuccessful = PerformAction("eleConfirmDisableMsg", Action.isDisplayed);
		if (isEventSuccessful)
		{
			ActualMessageText = GetTextOrValue("eleConfirmDisableMsg", "text");
			ExpectedMessageText = "Do you wish to enable these " +noOfDevices + " devices?";
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