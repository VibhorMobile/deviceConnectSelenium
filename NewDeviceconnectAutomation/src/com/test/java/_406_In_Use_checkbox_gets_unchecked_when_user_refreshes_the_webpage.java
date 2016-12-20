package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ApplicationLibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Jira Test Case Id: QA-2166
 */
public class _406_In_Use_checkbox_gets_unchecked_when_user_refreshes_the_webpage extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";
	private boolean clickable = false;
	private boolean flag = false;
	private int errorAppIndex = 0;

	public final void testScript()
	{
		//Step 1 - Launch deviceConnect and verify homepage.
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

		//Step 2 - Login to deviceConnect
		isEventSuccessful = LoginToDC();
		if (isEventSuccessful)
		{
			strActualResult = "User - " + dicCommon.get("EmailAddress") + " is logged in to deviceConnect.";
		}
		else
		{
			strActualResult = "User - " + dicCommon.get("EmailAddress") + " is not logged in to deviceConnect.";
		}
		reporter.ReportStep("Login to deviceConnect", "User should be logged in.", strActualResult, isEventSuccessful);

		//Step 3 - Navigate to devices Page
/*		isEventSuccessful = selectFromMenu("Devices", "eleDevicesHeader");
		if (isEventSuccessful)
		{
			strActualResult = "Devices Page is opened.";
		}
		else
		{
			strActualResult = "Devices Page is not opened.";
		}
		reporter.ReportStep("Verify Devices Page is loaded", "'Devices' Page should be loaded.", strActualResult, isEventSuccessful);
*/
		//Step 4 - Select In Use CheckBox
		isEventSuccessful = selectStatus("In Use");
		
		// Step 5 - Refresh the page
		RefreshPage();
		
		//Step 6 - Verify that in use checkbox is still selected after page reload
		if (PerformAction(dicOR.get("chkStatus_Devices")+"[2]", Action.isSelected))
		{
			strActualResult = "In Use checkbox is checked even after page refresh.";
		}
		else
		{
			strActualResult = "In Use checkbox is not checked after page refresh.";
		}
		reporter.ReportStep("Verify in use checkbox is checked after page refresh", "'In Use' should be checked.", strActualResult, isEventSuccessful);
	}
}