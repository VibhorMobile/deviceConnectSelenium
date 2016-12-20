package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2180
 */

public class _387_Verify_uninstall_all_button_for_available_device extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String EmailAddress = dicCommon.get("testerEmailAddress");
	private String Password = dicCommon.get("Password");
	private String strActualResult = "", strStepDescription = "", strExpectedResult = "";

	public final void testScript()
	{

		//Step 1 - Login to deviceConnect
		isEventSuccessful = Login(); 
		
		//Step 2 - Select available from Status filter
		strStepDescription = "Select available from Status filter";
		strExpectedResult = "Only available devices should be displayed on the page.";
//		isEventSuccessful = SelectFromFilterDropdowns("status", "available", "devices", "list");
		
	/*Tarun Ahuja : Selecting the Available device*/
		isEventSuccessful = selectStatus("Available");
		if (isEventSuccessful)
		{
			// If there are no available devices then stop execution
			if (strErrMsg_AppLib.contains("deviceConnect currently has no configured"))
			{
				strActualResult = "No available devices on devices page.";
				reporter.ReportStep("Select first available device.", "'Device Details' page should be opened.", strActualResult, isEventSuccessful);
				return;
			}
			else
			{
				isEventSuccessful = SelectDevice("first");
				if (isEventSuccessful)
				{
					strActualResult = "Device Details page is opened.";
				}
				else
				{
					strActualResult = "SelectDevice()--" + strErrMsg_AppLib;
				}
			}
		}
		else
		{
			strActualResult = "SelectFromFilterDropdowns()--" + strErrMsg_AppLib;
		}
		reporter.ReportStep("Select first available device.", "'Device Details' page should be opened.", strActualResult, isEventSuccessful);

		//Step 3 - Verify Uninstall All button above 'Installed' table.
		PerformAction("browser", "waitforpagetoload" );
		isEventSuccessful = PerformAction("BtnUninstallAllEnabled", Action.isDisplayed);
		if (isEventSuccessful) 
		{
			strActualResult = "Uninstall All button is displayed.";
		}
		else
		{
			strActualResult = "Uninstall All button is not displayed on Device Details Page.";
		}
		reporter.ReportStep("Verify Uninstall All button is displayed above 'Installed' table.", "'Uninstall All' button should be displayed.", strActualResult, isEventSuccessful);
	}
}