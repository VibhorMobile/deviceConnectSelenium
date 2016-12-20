package com.test.java;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2211
 */
public class _235_Verify_device_will_automatically_come_back_on_line_after_Reboot extends ScriptFuncLibrary
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

		//**************************************************************************************************************************************************************//
		// verify that when reboot is done for any device, then first its icon changes to grey icon and when it comes back online automatically, its icon becomes green.//
		//**************************************************************************************************************************************************************//

		///Step 1 - Login to deviceConnect
		isEventSuccessful = Login();
			
		// ////////////////////////////////////////////////
		// Step 3 : Go to device details page for an available device.
		// /////////////////////////////////////////////////
		strstepDescription = "Go to device details page for an available device.";
		strexpectedResult = "Device details page should be opened.";
		// isEventSuccessful = PerformAction("chkAvailable",
		// Action.SelectCheckbox);
		isEventSuccessful = selectStatus("Available");
		if (isEventSuccessful) {
			values = GoTofirstDeviceDetailsPage();
			isEventSuccessful = (boolean) values[0];
			deviceName = (String) values[1];
			if (!isEventSuccessful) {
				strActualResult = "SelectDevice() -- " + strErrMsg_AppLib;
			}
		} else {
			strActualResult = "SelectFromFilterDropdowns() -- "
					+ strErrMsg_AppLib;
		}

		if (isEventSuccessful) {
			reporter.ReportStep(strstepDescription,strexpectedResult,"Device details page of available device displayed successfully.","Pass");
		} else {
			reporter.ReportStep(strstepDescription, strexpectedResult,strActualResult, "Fail");
		}

		//**********************************//
		//Step 5 - Click on 'Reboot' button.//
		//**********************************//
		waitForPageLoaded();
		isEventSuccessful = PerformAction("btnReboot", Action.Click);
		waitForPageLoaded();
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = PerformAction("eleConfirmReboot", Action.WaitForElement,"10"); //Verifying confirmation popup is opened.
			if (isEventSuccessful)
			{
				strActualResult = "'Confirm Reboot' popup is opened.";
			}
			else
			{
				strActualResult = "Clicked on 'Reboot' button. 'Confirm Reboot' popup is not opened.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Reboot'.";
		}
		reporter.ReportStep("Click on 'Reboot' button.", "'Confirm Reboot' popup should be opened.", strActualResult, isEventSuccessful);

		//**************************************************************//
		//Step 6 - Click on 'Continue' button on 'Confirm Reboot' popup.//
		//**************************************************************//
		//isEventSuccessful = PerformAction("btnContinue", Action.Click);
		
		if (dicCommon.get("BrowserName").toLowerCase().equals("ie"))
		{
			isEventSuccessful = PerformAction("btnContinue", Action.Click);
			if (isEventSuccessful)
			{
				waitForPageLoaded();
				strActualResult = "Message 'Device reboot command sent' is displayed.";
			}
			else
			{
				strActualResult = "Clicked on 'Continue' button. Message 'Device reboot command sent' is not displayed.";
			}
		}
		else
		{
			driver.findElement(By.xpath("//button[text()='Reboot device']")).click();
			waitForPageLoaded();
		}
		reporter.ReportStep("Click on 'Continue' button on 'Confirm Reboot' popup.", "Message 'Device reboot command sent' should be displayed.", strActualResult, isEventSuccessful);


		//********************************************************************************************************************************************************//
		// Step 7 - Wait until the 'Offline' status icon is displayed. Insert the title of status icon object in place of "__STATUS__" variable of object locator.//
		//********************************************************************************************************************************************************//
		waitForPageLoaded();
		PerformAction("browser","waitforpagetoload");
		isEventSuccessful = PerformAction(dicOR.get("eleStatusIcon_DeviceDetails").replace("__STATUS__", "Rebooting"), Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Offline icon is displayed in front of device name , i.e. it is offline now.";
		}
		else
		{
			strActualResult = "Device status icon did not change to 'Grey icon', i.e. it did not go offline even after waiting for 1 minute.";
		}
		reporter.ReportStep("Wait until the 'Offline' status icon is displayed.", "Offline icon should be displayed on device details page.", strActualResult, isEventSuccessful);

		//**************************************************************//
		// Step 8 - Wait until the 'Available' status icon is displayed.//
		//**************************************************************//
		waitForPageLoaded();
		isEventSuccessful = PerformAction(dicOR.get("eleStatusIcon_DeviceDetails").replace("__STATUS__", "Available"), Action.WaitForElement,"50");
		if (isEventSuccessful)
		{
			strActualResult = "Available icon is displayed in front of device name , i.e. it is online now.";
		}
		else
		{
			strActualResult = "Device status icon did not change to 'Available icon', i.e. it did not comne back online even after waiting for 1 minute.";
		}
		reporter.ReportStep("Wait until the 'Available' status icon is displayed.", "Available icon should be displayed on device details page.", strActualResult, isEventSuccessful);
	}
}