package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1137
 */

public class _210_Verify_that_the_correct_message_is_displayed_every_time_the_user_tries_to_enable_an_disabled_device extends  ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String deviceName = "";
	private String notificationText = "";
	private String text = "", ExpectedMsg = "";
	private Object[] arrResult;
	Object [] CLIResult=new Object[6];

	public final void testScript()
	{
		//***************************************************************************************************************************//
		// Verify_that_the_correct_message_is_displayed_every_time_the_user_tries_to_enable_a_disabled_device from device details page.
		//***************************************************************************************************************************//

		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//*******************************************************//
		//Step 2 - Select Available option from 'status' dropdown//
		//*******************************************************//
		isEventSuccessful = selectStatus("Available");
		
		//***********************************************************************//
		//Step 3 - Go to device details page of first available device//
		//***********************************************************************//
		
		arrResult = GoTofirstDeviceDetailsPage();
		isEventSuccessful = (boolean)arrResult[0];
		deviceName = (String)arrResult[1];
		if(!isEventSuccessful)
			return;           //Skip the following streps if device details page is not opened
				
		// /////////////////////////////////////////////////////////////
		// Step 4 : Click on 'Disable' and verify confirmation message.
		// /////////////////////////////////////////////////////////////
		strstepDescription = "Click on 'Disable' and verify confirmation message.";
		strexpectedResult = "Correct confirmation message should be displayed on dialog.";
		isEventSuccessful = PerformAction("btnDisable", Action.Click);
		if (isEventSuccessful) 
		{
			isEventSuccessful = PerformAction("hdrConfirmDisable",Action.isDisplayed);
			if (isEventSuccessful)
			{
				text = GetTextOrValue("eleConfirmDisableMsg", "text");
				ExpectedMsg = "Do you wish to disable " + deviceName + "? The device will no longer be usable until it is enabled.";
				isEventSuccessful = text.equals(ExpectedMsg);
				if (!isEventSuccessful)
				{
					strActualResult = "Confirmation message is not correct. It reads :" + text + "and not :" + ExpectedMsg;
				}
			} 
			  else
			   {
				strActualResult = "'Confirm disable' dialog not displayed after clicking on Disable.";
			   }
		    } 
		    else
			strActualResult = "Unable to click on Disable button.";

		    if (isEventSuccessful)
		    {
			reporter.ReportStep(strstepDescription, strexpectedResult,"Correct confirmation message is displayed on the dialog: "+ text, "Pass");
		} 
		    else
		    {
			reporter.ReportStep(strstepDescription, strexpectedResult,
					strActualResult, "Fail");
			return;
		 }

		///////////////////////////////////////////////////////////////////////////////
		// Step 5 : Click on Disable Device button and verify Disabled icon is displayed.
		///////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Click on 'Disable Device' button and  verify Disabled icon is displayed.";
		strexpectedResult = "Disabled icon should be displayed on the page";
		isEventSuccessful = PerformAction("btnDisableDevice_DialogDeviceDetails", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eledeviceDisabled_Icon", Action.WaitForElement, "10");
			if (isEventSuccessful)
			{
				strActualResult = "Success Disabled icon is displayed when 'Disable Device' button is clicked.";
			}
			else
			{
				strActualResult = "Success Disabled icon is not displayed after clicking on 'Disable Device' button for device.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Disable Device' button.";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Device is disabled.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		///////////////////////////////////////////////////////////////////////////////
		// Step 5 : Click on 'Enable' button and verify confirmation message.
		///////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Click on 'Enable' button and verify confirmation message.";
		strexpectedResult = "Correct 'Enable Device' message should be displayed on the page.";
		isEventSuccessful = PerformAction("btnEnable", Action.WaitForElement);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnEnable", Action.Click);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("hdrConfirmEnable_DeviceDetails", Action.WaitForElement,"20");
				if (isEventSuccessful)
				{
					text = GetTextOrValue("eleConfirmDisableMsg", "text");
					ExpectedMsg = "Do you wish to enable " + deviceName + "?";
					isEventSuccessful = text.equals(ExpectedMsg);
					if (!isEventSuccessful)
					{
						strActualResult = "Confirmation message is not correct. It reads : " + text + "&" + ExpectedMsg ;
					}
				}
				else
				{
					strActualResult = "'Confirm Enable' dialog not displayed after clicking on Enable.";
				}
			}
			else
			{
				strActualResult = "Could not click on 'Enable' button.";
			}
		}
		else
		{
			strActualResult = "'Enable' button is not displayed after the device is disabled.";
		}
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Correct 'Enable Device' message is displayed on the page : " + text, "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		/**Step 6 : click on Enable Device button on notification dialog
		*/
		isEventSuccessful = PerformAction("btnEnableDevice_DeviceDetails", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnConnect_DeviceDetailsPage", Action.WaitForElement, "10");
			if (isEventSuccessful)
			{
				strActualResult = "Connect button is displayed when device is enabled.";
			}
			else
			{
				strActualResult = "Connect button is not displayed after clicking on 'Enable Device' button on 'Enable DEvice' dialog for device.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Enable Device' button on confirm 'Enable Device' dialog.";
		}

		reporter.ReportStep("Enable the device.", "Device should be enabled.", strActualResult, isEventSuccessful);

		// Step 7 : Verify that 'disable' button is displayed
		isEventSuccessful = PerformAction("btnDisable", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Disable' button displayed for the device.";
		}
		else
		{
			strActualResult = "'Disable' button is not displayed for the device. i.e. the device may not be enabled.";
		}
		reporter.ReportStep("Verify that 'Disable' button appears for the device.", "Disable button should be disaplayed.", strActualResult, isEventSuccessful);

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