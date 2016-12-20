package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2191
 */
public class _319_Verify_that_there_should_be_an_option_on_Web_UI_to_extract_the_logcat_file extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		// Step 1 : login to deviceConnect with valid user and verify Devices page.
		isEventSuccessful = Login();
		
		// Step 2 : Go to systems page
		isEventSuccessful =GoToSystemPage();
		
	
		// Step 3 : Click on Logs option
		strstepDescription = "Click on Logs option";
		strexpectedResult = "Logs Info page should be displayed";
			isEventSuccessful = PerformAction("btnSavelogs", Action.Exist);
			if (isEventSuccessful)
			{
				strActualResult = "Logs Info page is displayed";
			}
			else
			{
				strActualResult = "Logs Info page is not displayed";
			}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Step 4 : Click on Devices Dropdown
		strstepDescription = "Click on Devices button";
		strexpectedResult = "Devices list should be displayed";
			isEventSuccessful = PerformAction("//select[@name='logExportInput.deviceId']", Action.Click);
			if (isEventSuccessful)
			{
				strActualResult = "Devices list is displayed on clicking Devices Dropdown";
			}
			else
			{
				strActualResult = "Devices list is not displayed on clicking Devices Dropdown";
			}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Step 5 : Select any Device
		strstepDescription = "Select any Device";
		strexpectedResult = "Devices should be selected and displayed as a link";
		isEventSuccessful = selectDeviceFromDropDown(dicOR.get("selectDeviceSytemLog_system"));
		if(isEventSuccessful)
		{
			strActualResult = "Devices is selected";
		}
		else
		{
			strActualResult = "Devices is not selected";			
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}