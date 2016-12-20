package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2179
 */
public class _388_Verify_uninstall_all_btn_for_disabled_offline_inuse_by_other_user extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "", deviceConnected = "";
	//private Tuple<Boolean, String> CLIResult;

	public final void testScript()
	{
		//Fetching data from datasheet
		String strTestUserEmailID = dicCommon.get("testerEmailAddress");
		String strTesterPassword = dicCommon.get("testerPassword");

		//Step 1 - Login to deviceConnect
		isEventSuccessful = Login();

		// Step 2 : Select In Use status from Status dropdown
		isEventSuccessful = selectStatus("In Use");
        isEventSuccessful = VerifyDeviceDetailsInGridAndListView("statusicon", "Red", "list");
		if (!isEventSuccessful)
		{
			isEventSuccessful = DisplayInUSeDevices();
		}
			
			
		// Step 5 : Go to device details page of the connected device
		isEventSuccessful = SelectDevice("first");
		if (isEventSuccessful)
		{
			strActualResult = "Device Details page is opened.";
		}
		else
		{
			strActualResult = "SelectDevice() -- " + strErrMsg_AppLib + "Device name : " + dicOutput.get("selectedDeviceName");
		}
		reporter.ReportStep("Select first available device.", "'Device Details' page should be opened.", strActualResult, isEventSuccessful);

		//Step 6 - Verify UnInstall All button
		PerformAction("browser", "waitforpagetoload");
		waitForPageLoaded();
		isEventSuccessful = PerformAction("BtnUninstallAllEnabled", Action.isDisplayed, "30");
		if (isEventSuccessful)
		{
			strActualResult = "UnInstall All button is disabled for In Use device.";
		}
		else
		{
			strActualResult = "UnInstall All button is not disabled for In Use device.";
		}

		reporter.ReportStep("Verify UnInstall All button is disabled for in use device.", "'UnInstall All' button should be disabled for In Use device.", strActualResult, isEventSuccessful);

		//Step 7 - Navigate to Devices Page and verify uninstall all btn is disabled for disabled device
		isEventSuccessful = GoToDevicesPage();
		if (isEventSuccessful)
		{
			isEventSuccessful = selectStatus("Disabled");
				// If there are no available devices then stop execution\
			waitForPageLoaded();
				if (GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results."))
				{
					isEventSuccessful = false;
					strActualResult = "deviceConnect currently has no configured devices or your filter produced no results.";
					//reporter.ReportStep("Select first available device.", "'Device Details' page should be opened.", strActualResult, isEventSuccessful);
					//return;
				}
				isEventSuccessful = SelectDevice("first");
				if (isEventSuccessful)
				{
					waitForPageLoaded();
					PerformAction("BtnUninstallAllDisabled", Action.WaitForElement);
					isEventSuccessful = PerformAction("BtnUninstallAllDisabled", Action.isDisplayed);
					if (isEventSuccessful)
					{
						strActualResult = "UnInstall All button is disabled for disabled device.";
					}
					else
					{
						strActualResult = "UnInstall All button is not disabled for disabled device.";
					}
				}
				else
				{
					strActualResult = "SelectDevice()--" + strErrMsg_AppLib;
				}

		}
		else
		{
			strActualResult = "Devices Page is not opened.";
		}
		reporter.ReportStep("Navigate to Devices details page of disabled device and verify uninstall all btn is disabled.", "'Uninstall All' button should be disabled for disabled device", strActualResult, isEventSuccessful);

		//Step 8 - Navigate to Devices Page and verify uninstall all btn is disabled for offline device
		isEventSuccessful = GoToDevicesPage();
		if (isEventSuccessful)
		{	
			isEventSuccessful = selectStatus("Offline");
				// If there are no available devices then stop execution
			waitForPageLoaded();
				if (GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results."))
				{
					isEventSuccessful = false;
					strActualResult = "deviceConnect currently has no configured devices or your filter produced no results.";
					//reporter.ReportStep("Select first available device.", "'Device Details' page should be opened.", strActualResult, isEventSuccessful);
					//return;
				}
				isEventSuccessful = SelectDevice("first");
				if (isEventSuccessful)
				{
					waitForPageLoaded();
					isEventSuccessful = PerformAction("BtnUninstallAllDisabled", Action.isDisplayed);
					if (isEventSuccessful)
					{
						strActualResult = "UnInstall All button is disabled for Offline device.";
					}
					else
					{
						strActualResult = "UnInstall All button is not disabled for Offline device.";
					}
				}
				else
				{
					strActualResult = "SelectDevice() -- " + strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "SelectFromFilterDropdowns()-- " + strErrMsg_AppLib;
			}

		reporter.ReportStep("Navigate to Devices details page of offline device and verify uninstall all btn is disabled.", "'UnInstall All' button should be disabled for Offline Device.", strActualResult, isEventSuccessful);

	}
}