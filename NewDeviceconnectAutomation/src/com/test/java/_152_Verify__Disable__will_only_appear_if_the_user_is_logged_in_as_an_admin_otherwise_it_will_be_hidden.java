package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test case Id: QA-2182
 */
public class _152_Verify__Disable__will_only_appear_if_the_user_is_logged_in_as_an_admin_otherwise_it_will_be_hidden extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private Object[] result;
	private String devicename="";
	
	public final void testScript()
	{
		String Password = dicCommon.get("testerPassword");
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		
		//*************************************************************//
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//
		isEventSuccessful = Login();
		//*************************************************************//
		// Step 2 : Select filter Android and Available.
		//*************************************************************//
		strstepDescription = "Select Android and Available filter.";
		strexpectedResult = "Filters should get selected.";
		isEventSuccessful = selectPlatform("Android");
		//isEventSuccessful = selectStatus();
		
		//*************************************************************//
		// Step 3 : Select filter Offline and InUse and navigate to device details page.
		//*************************************************************//
		isEventSuccessful = selectStatus("Offline,Available");
//		isEventSuccessful = SelectDevice("first");
		result= GoTofirstDeviceDetailsPage();
		isEventSuccessful = (boolean)result[0];
		devicename = (String) result[1];

		//*************************************************************//
		// Step 4 : Verify Disable button is visible for admin user.
		//*************************************************************//
		strstepDescription = "Verify Disable button is visible for admin user.";
		strexpectedResult = "Disable button should be visible for admin user.";
		isEventSuccessful = PerformAction("btnDisable", Action.isDisplayed);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Disable button is visible for admin user.", "Pass");
		}
		else
		{
			strActualResult = "Disable button is not visible for admin user.";
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//
		// Step 5 : Go back to "Devices" page.
		//*************************************************************//
		isEventSuccessful = GoToDevicesPage();
		

		//*************************************************************//
		// Step 6 : Select iOS filter.
		//*************************************************************//
		strstepDescription = "Select iOS filter.";
		strexpectedResult = "iOS filter should get selected.";
		
		isEventSuccessful = selectPlatform("iOS");

		//*************************************************************//
		// Step 7 : Select first iOS device and navigate to device details page.
		//*************************************************************//     
		strstepDescription = "Select first iOS device and navigate to device details page.";
		strexpectedResult = "iOS device should be selected and navigate to device details page.";
		isEventSuccessful = GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results.");
		if (!isEventSuccessful) // continue only if there are some devices under android platform.
		{
			isEventSuccessful = SelectDevice("first");
			if (!isEventSuccessful)
			{
				strActualResult = "SelectDevice---" + strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "No iOS device displayed on devices page.";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "iOS device is selected and navigated to it device details page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//
		// Step 8 : Verify Disable button is visible for admin user.
		//*************************************************************//
		strstepDescription = "Verify Disable button is visible for admin user.";
		strexpectedResult = "Disable button should be visible for admin user.";
		isEventSuccessful = PerformAction("btnDisable", Action.isDisplayed);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Disable button is visible for admin user.", "Pass");
		}
		else
		{
			strActualResult = "Disable button is not visible for admin user.";
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//
		// Step 9 : Go to devices page and log out of the application.
		//*************************************************************//
		strstepDescription = "Go to devices page and log out of the application";
		strexpectedResult = "Login page should be displayed.";
		isEventSuccessful = GoToDevicesPage();
		isEventSuccessful = Logout();
		if (!isEventSuccessful)
		{
			strActualResult = strErrMsg_AppLib;
		}
	
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Login page displayed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//
		// Step 10 : login to deviceConnect with test user.
		//*************************************************************//
		strstepDescription = "Login to deviceConnect with valid test user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(testerEmailAddress, Password);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with test user: " + testerEmailAddress, "Pass");
		}
		else
		{
			strActualResult = "LoginToDC---" + strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//
		// Step 11 : Open device details page for an Android device.
		//*************************************************************//
		strstepDescription = "Open device details page for an Android device.";
		strexpectedResult = "Device details page for the corresponding Android device should be displayed.";

		isEventSuccessful = selectPlatform("Android");
		//isEventSuccessful = selectStatus("Available");
		
		//*************************************************************//
		// Step 12 : Select filter Offline and InUse and navigate to device details page.
		//*************************************************************//
		strstepDescription = "Select filter Offline and InUse and Reserved and navigate to device details page.";
		strexpectedResult = "Offline, InUse, Reserved should be selected and navigated to device details page.";
		isEventSuccessful = selectStatus("Offline,Available");
		if (isEventSuccessful)
		{
			isEventSuccessful = GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results.");
			if (!isEventSuccessful) // continue only if there are some devices under android platform.
			{
				isEventSuccessful = SelectDevice("first");
				if (!isEventSuccessful)
				{
					strActualResult = "SelectDevice---" + strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "No Android device displayed on devices page.";
			}
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Android device is selected and navigated to device details page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//
		// Step 13 : Verify Disable button is not visible for test user.
		//*************************************************************//
		strstepDescription = "Verify Disable button is not visible for test user.";
		strexpectedResult = "Disable button should not be visible for test user.";
		isEventSuccessful = !PerformAction("btnDisable", Action.isDisplayed);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Disable button is not visible for test user.", "Pass");
		}
		else
		{
			strActualResult = "Disable button is visible for test user.";
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//
		// Step 14 : Go back to "Devices" page.
		//*************************************************************//
		strstepDescription = "Go back to 'Devices' page.";
		strexpectedResult = "Devices Page should be displayed.";
		isEventSuccessful = GoToDevicesPage();
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleDevicesHeader", Action.Exist);
			if (!isEventSuccessful)
			{
				strActualResult = "Devices page is not displayed after clicking on 'Devices' tab.";
			}
		}
		else
		{
			strActualResult = "GoToDevicesPage---" + strErrMsg_AppLib;
		}

		//*************************************************************//
		// Step 15 : Open device details page for an iOS device.
		//*************************************************************//
		strstepDescription = "Open device details page for an iOS device.";
		strexpectedResult = "Device details page for the corresponding iOS device should be displayed.";
		isEventSuccessful = selectPlatform("iOS");
		isEventSuccessful = !(GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results."));
		if (isEventSuccessful) // continue only if there are some devices under android platform.
		{
			isEventSuccessful = SelectDevice("first");
			if (!isEventSuccessful)
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "No iOS devices displayed on devices page.";
		}

		//*************************************************************//
		// Step 16 : Verify Disable button is not visible for test user.
		//*************************************************************//
		strstepDescription = "Verify Disable button is not visible for test user.";
		strexpectedResult = "Disable button should not be visible for test user.";
		isEventSuccessful = !PerformAction("btnDisable", Action.isDisplayed);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Disable button is not visible for test user.", "Pass");
		}
		else
		{
			strActualResult = "Disable button is visible for test user.";
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
	}
}