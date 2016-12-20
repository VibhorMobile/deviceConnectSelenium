package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2197
 */
public class _207_Verify_confirmation_message_when_the_user_clicks_on_Disable_on_device_details_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String deviceName = "";


	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		String text = "";
		Object[] CLIResult=new Object[6];

		////////////////////////////////////////////////////
		//Step 1 - Launch deviceConnect and verify homepage.
		////////////////////////////////////////////////////
		genericLibrary.LaunchWebDriver();
		isEventSuccessful = PerformAction("imgDCLogo", Action.Exist);
		if (isEventSuccessful)
		{
			strActualResult = "Navigated to " + dicCommon.get("ApplicationURL") + ". deviceConnect homepage is opened.";
		}
		else
		{
			strActualResult = "deviceConnect homepage is not opened.";
		}
		reporter.ReportStep("Launch deviceConnect and verify homepage.", "deviceConnect homepage should be opened.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}

		///////////////////////////////////////////////////////////////////////////
		// Step 2 : login to deviceConnect with valid user and verify Devices page.
		///////////////////////////////////////////////////////////////////////////
		strstepDescription = "Login to deviceConnect with valid username and password and verify Devices page.";
		strexpectedResult = "User should be logged in and navigated to Devices page.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with user: " + EmailAddress + " and navigated to Devices page.", "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		///////////////////////////////////////////////////
		// Step 3 : Select Status "Available"
		//////////////////////////////////////////////////
		isEventSuccessful = selectStatus("Available");	
		
		
		///////////////////////////////////////////////////
		// Step 4 : Go to device details page for a device.
		///////////////////////////////////////////////////
		strstepDescription = "Go to device details page for a device.";
		strexpectedResult = "Device details page should be opened.";
		isEventSuccessful = SelectDevice("first");
		if (!isEventSuccessful)
		{
			strActualResult = strErrMsg_AppLib;
		}
		
		///////////////////////////////////////////////////////////////
		// Step 5 : Click on 'Disable' and verify confirmation message.
		///////////////////////////////////////////////////////////////
		strstepDescription = "Click on 'Disable' and verify confirmation message.";
		strexpectedResult = "Correct confirmation message should be displayed on dialog.";
		deviceName = GetTextOrValue("eleDeviceNameinDeviceDetailsHeader", "text");
		if (!deviceName.equals(""))
		{
			isEventSuccessful = PerformAction("btnDisable", Action.Click);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("hdrConfirmDisable", Action.isDisplayed);
				if (isEventSuccessful)
				{
					text = GetTextOrValue("eleConfirmDisableMsg", "text");
					System.out.println(text);
					
					isEventSuccessful = text.contains("Do you wish to disable "+deviceName+"?"+" The device will no longer be usable until it is enabled.");
					if (!isEventSuccessful)
					{
						strActualResult = "Confirmation message is not correct. It reads : " + text;
					}
				}
				else
				{
					strActualResult = "'Confirm disable' dialog not displayed after clicking on Disable.";
				}
			}
			else
			{
				strActualResult = "Unable to click on Disable button.";
			}
		}
		else
		{
			isEventSuccessful = false;
			strActualResult = "Could not get device name from device details page.";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Correct confirmation message is displayed on the dialog: " + text, "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
			return;
		}

		///////////////////////////////////////////////////////////////////////
		// Step 6 : Verify presence of Cancel button on Confirm disable dialog.
		///////////////////////////////////////////////////////////////////////
		strstepDescription = "Verify presence of Cancel button on Confirm disable dialog.";
		strexpectedResult = "Cancel button should be present on the Confirm disable dialog.";
		isEventSuccessful = PerformAction("btnCancel", Action.isDisplayed);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Cancel button is present on the Confirm disable dialog.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Cancel button is not present on the Confirm disable dialog.", "Fail");
		}

		///////////////////////////////////////////////////////////////////////
		// Step 7 : Verify presence of Continue button on Confirm disable dialog.
		///////////////////////////////////////////////////////////////////////
		strstepDescription = "Verify presence of Continue button on Confirm disable dialog.";
		strexpectedResult = "Continue button should be present on the Confirm disable dialog.";
		isEventSuccessful = PerformAction("btnContinue_Disable", Action.isDisplayed);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Continue button is present on the Confirm disable dialog.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Continue button is not present on the Confirm disable dialog.", "Fail");
		}

		//Post-Condition : Enable the disabled device
		strstepDescription = "Post-Condition : Enable the disabled device";
		strexpectedResult = "Device should get enabled.";
		CLIResult = ExecuteCLICommand("enable", "", "", "", deviceName,"");
		if ((Boolean)CLIResult[2])
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Device " + deviceName + " enabled successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Device " + deviceName + " could not be enabled.", "Fail");
		}
	}
}