package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2163
 */
public class _122_Verify_the_system_will_refresh_the_device_list_to_show_only_devices_with_an_In_Use_status_when_the_user_checks_the_In_Use_checkbox extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";

	public final void testScript()
	{
		//Step 1 - Login to deviceConnect
		isEventSuccessful = Login();

		//Step 2 - Select check box for In Use in left pane
		isEventSuccessful = selectStatus("In Use");

		//Step 3 - Verify only devices with status in-use is displayed
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("devicestatus", "In Use");
		
		if (isEventSuccessful)
		{
			strActualResult = "Only devices with status in-use is displayed.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep("Verify only devices with status in-use is displayed.", "Only devices with status in-use should be displayed.", strActualResult, isEventSuccessful);
	}

}