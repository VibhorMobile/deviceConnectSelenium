package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2195
 */
public class _231_Verify_confirmation_message_when_the_user_selects_Reboot extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private Object[] values = new Object[2];
	private String deviceName, text = "";

	public final void testScript()
	{
		///Step 1 - Login to deviceConnect
		isEventSuccessful = Login();
	
		///////////////////////////////////////////////////
		// Step 3 : Go to device details page for an available device.
		///////////////////////////////////////////////////
		strstepDescription = "Go to device details page for an available device.";
		strexpectedResult = "Device details page should be opened.";
		//isEventSuccessful = PerformAction("chkAvailable", Action.SelectCheckbox);
		isEventSuccessful = selectStatus("Available");
		if (isEventSuccessful)
		{
			values = GoTofirstDeviceDetailsPage();
		    isEventSuccessful = (boolean) values[0] ;
		    deviceName =(String) values[1];
			if (!isEventSuccessful)
			{
				strActualResult = "SelectDevice() -- " + strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "SelectFromFilterDropdowns() -- " + strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Device details page of available device displayed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		///////////////////////////////////////////////////////////////
		// Step 4 : Click on 'Reboot' and verify confirmation message.
		///////////////////////////////////////////////////////////////
		strstepDescription = "Click on 'Reboot' and verify confirmation message.";
		strexpectedResult = "Correct confirmation message should be displayed on dialog.";

	if (!deviceName.equals(""))
		{
			isEventSuccessful = PerformAction("btnReboot", Action.Click);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction(dicOR.get("eleHeader").replace("__EXPECTED_HEADER__", "Reboot device"), Action.isDisplayed);
				if (isEventSuccessful)
				{
					text = GetTextOrValue("eleWarningOrConfirmationPopUpBody", "text");
					String t = "Do you wish to reboot"+" "+ deviceName+"?";
					isEventSuccessful = text.contains(t);
					if (!isEventSuccessful)
					{
						strActualResult = "Confirmation message is not correct. It reads : " + text;
					}
				}
				else
				{
					strActualResult = "'Confirm Reboot' dialog not displayed after clicking on Reboot.";
				}
			}
			else
			{
				strActualResult = "Unable to click on Reboot button.";
			}
		}
		else
		{
			strActualResult = "Could not get device name from device details page.";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Correct confirmation message is displayed on the dialog: " + text, "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		///////////////////////////////////////////////////////////////////////
		// Step 5 : Verify presence of Cancel button on Confirm reboot dialog.
		///////////////////////////////////////////////////////////////////////
		strstepDescription = "Verify presence of Cancel button on Confirm reboot dialog.";
		strexpectedResult = "Cancel button should be present on the Confirm reboot dialog.";
		isEventSuccessful = PerformAction("btnCancel", Action.isDisplayed);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Cancel button is present on the Confirm reboot dialog.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Cancel button is not present on the Confirm reboot dialog.", "Fail");
		}

		///////////////////////////////////////////////////////////////////////
		// Step 6 : Verify presence of Continue button on Confirm reboot dialog.
		///////////////////////////////////////////////////////////////////////
		strstepDescription = "Verify presence of Continue button on Confirm reboot dialog.";
		strexpectedResult = "Continue button should be present on the Confirm reboot dialog.";
		isEventSuccessful = PerformAction("btnContinue", Action.isDisplayed);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Continue button is present on the Confirm reboot dialog.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Continue button is not present on the Confirm reboot dialog.", "Fail");
		}
	}
}