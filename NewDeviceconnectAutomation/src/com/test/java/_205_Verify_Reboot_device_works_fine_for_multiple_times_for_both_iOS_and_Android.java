package com.test.java;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1265
 */

public class _205_Verify_Reboot_device_works_fine_for_multiple_times_for_both_iOS_and_Android extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";


	public final void testScript()
	{
	
		//Step 1 - Login to deviceConnect
		isEventSuccessful = LoginToDC();
		
		//Step 2 - Select check box for Available in left pane
		isEventSuccessful = selectStatus("Available");
		
		//Perform Step 3 - 10 for two different platforms - Android and iOS
		String[] PlatformsArray = {"Android", "iOS"};
		for (String Platform : PlatformsArray)
		{
			if (!PerformAction("eleDevicesTab_Devices", Action.isDisplayed))
			{
				isEventSuccessful = selectFromMenu("Devices", "eleDevicesHeader");
				if (isEventSuccessful)
				{
					strActualResult = "User navigated to devices page successfully.";
				}
				else
				{
					strErrMsg_AppLib = "selectFromMenu -- " + strErrMsg_AppLib;
				}
				reporter.ReportStep("Navigate to 'Devices' page.", "Devices page should be opened.", strActualResult, isEventSuccessful);
			}
			//Step 4 - Select first available device
			isEventSuccessful = selectPlatform(Platform);
			isEventSuccessful = SelectDevice("first");
			if (isEventSuccessful)
			{
				strActualResult = "Device Details page is opened.";
			}
			else
			{
				strActualResult = "SelectDevice --" + strErrMsg_AppLib;
			}
		
			reporter.ReportStep("Select first available device for " + Platform, "'Device Details' page should be opened.", strActualResult, isEventSuccessful);

			//**********************************//
			//Step 5 - Click on 'Reboot' button.//
			//**********************************//
			waitForPageLoaded();
			isEventSuccessful = PerformAction("btnReboot", Action.ClickUsingJS);
			//driver.findElement(By.xpath("//button[text()[normalize-space()='Reboot']]")).click();
			if (isEventSuccessful)
			{
				waitForPageLoaded();
				isEventSuccessful = PerformAction("eleConfirmReboot", Action.WaitForElement); //Verifying confirmation popup is opened.
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
			 if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
			  {
				isEventSuccessful = PerformAction("btnContinue", Action.Click);
				waitForPageLoaded();
			  }
			 else
			 {
			    driver.findElement(By.xpath("//button[text()='Reboot device']")).click();
			    waitForPageLoaded();
			 }
			if (!isEventSuccessful)
			{
			   strActualResult = "Could not click on 'Continue' button on 'Confirm Reboot' popup.";
			}
			reporter.ReportStep("Click on 'Continue' button on 'Confirm Reboot' popup.", "Message 'Device reboot command sent' should be displayed.", strActualResult, isEventSuccessful);


			//********************************************************************************************************************************************************//
			// Step 7 - Wait until the 'Offline' status icon is displayed. Insert the title of status icon object in place of "__STATUS__" variable of object locator.//
			//********************************************************************************************************************************************************//
			waitForPageLoaded();
			isEventSuccessful = PerformAction(dicOR.get("eleStatusIcon_DeviceDetails").replace("__STATUS__", "Offline"), Action.WaitForElement, "60");
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
			isEventSuccessful = PerformAction(dicOR.get("eleStatusIcon_DeviceDetails").replace("__STATUS__", "Available"), Action.WaitForElement, "120");
			if (isEventSuccessful)
			{
				strActualResult = "Available icon is displayed in front of device name , i.e. it is online now.";
			}
			else
			{
				strActualResult = "Device status icon did not change to 'Available icon', i.e. it did not comne back online even after waiting for 1 minute.";
			}
			reporter.ReportStep("Wait until the 'Available' status icon is displayed.", "Available icon should be displayed on device details page.", strActualResult, isEventSuccessful);

			//****************************************//
			//Step 9 - Again click on 'Reboot' button.//
			//****************************************//
			
			waitForPageLoaded();
			isEventSuccessful = PerformAction("btnReboot", Action.isDisplayed);
			isEventSuccessful = PerformAction("btnReboot", Action.ClickUsingJS);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleConfirmReboot", Action.WaitForElement); //Verifying confirmation popup is opened.
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
			//Step 10 - Click on 'Continue' button on 'Confirm Reboot' popup.//
			//**************************************************************//
			isEventSuccessful = PerformAction("btnContinue", Action.Click);
			waitForPageLoaded();
			if (!isEventSuccessful)
			{
				strActualResult = "Could not click on 'Continue' button on 'Confirm Reboot' popup.";
			}
			reporter.ReportStep("Click on 'Continue' button on 'Confirm Reboot' popup.", "Message 'Device reboot command sent' should be displayed.", strActualResult, isEventSuccessful);


			//********************************************************************************************************************************************************//
			// Step 11 - Wait until the 'Offline' status icon is displayed. Insert the title of status icon object in place of "__STATUS__" variable of object locator.//
			//********************************************************************************************************************************************************//
			isEventSuccessful = PerformAction(dicOR.get("eleStatusIcon_DeviceDetails").replace("__STATUS__", "Offline"), Action.WaitForElement, "60");
			if (isEventSuccessful)
			{
				strActualResult = "Disabled icon is displayed in front of device name , i.e. it is offline now.";
			}
			else
			{
				strActualResult = "Device status icon did not change to 'Grey icon', i.e. it did not go offline even after waiting for 1 minute.";
			}

			//*************************************
			// Step 12 - go Back to Devices Page //
			//*************************************
			waitForPageLoaded();
			isEventSuccessful = PerformAction(dicOR.get("eleStatusIcon_DeviceDetails").replace("__STATUS__", "Available"), Action.WaitForElement, "120");
			isEventSuccessful = GoToDevicesPage();
			
			reporter.ReportStep("Wait until the 'Offline' status icon is displayed.", "Offline icon should be displayed on device details page.", strActualResult, isEventSuccessful);
		}
	}

}