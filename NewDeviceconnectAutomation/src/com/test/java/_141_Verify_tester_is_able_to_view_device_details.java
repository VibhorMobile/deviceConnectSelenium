package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2178
 */
public class _141_Verify_tester_is_able_to_view_device_details extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "";
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid test user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with " + EmailAddress, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
		
		//*************************************************************//                     
		// Step 2 : Open device details page for an Android device.
		//*************************************************************//                     
		strstepDescription = "Open and verify device details page for an Android device.";
		strexpectedResult = "Device details page for the corresponding Android device should be displayed.";

		isEventSuccessful =selectPlatform("Android");
			isEventSuccessful = !GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results.");
			if (isEventSuccessful) // continue only if there are some devices under android platform.
			{
				isEventSuccessful = SelectDevice("first");
				if (!isEventSuccessful)
				{
					strActualResult = "Device Details page is not opened.";
				}
			}
			else
			{
				strActualResult = "No Android devices displayed on devices page.";
			}
		
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Device details page is successfully displayed for Android device.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//                     
		// Step 3 : Go back to "Devices" page.
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
			strActualResult = "returnToDevicesPage---" + strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Devices page displayed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//                     
		// Step 4 : Open and verify device details page for an iOS device.
		//*************************************************************//                     
		strstepDescription = "Open and verify device details page for an iOS device.";
		strexpectedResult = "Device details page for the corresponding iOS device should be displayed.";
		PerformAction("browser", Action.Scroll, "30");
		
		isEventSuccessful = selectPlatform("iOS");
		isEventSuccessful = !GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results.");
			if (isEventSuccessful) // continue only if there are some devices under android platform.
			{
				isEventSuccessful = SelectDevice("first");
				if (!isEventSuccessful)
				{
					strActualResult = "Device Details page is not opened.";
				}
			}
			else
			{
				strActualResult = "No iOS devices displayed on devices page.";
			}
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Device details page is successfully displayed for iOS device.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
	}
}