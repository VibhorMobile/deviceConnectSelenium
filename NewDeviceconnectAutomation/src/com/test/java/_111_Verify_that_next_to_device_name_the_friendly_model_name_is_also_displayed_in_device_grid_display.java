package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

public class _111_Verify_that_next_to_device_name_the_friendly_model_name_is_also_displayed_in_device_grid_display extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{

		//********************************************************//
		//** Step 1 - Login to deviceConnect. **//
		//********************************************************//
		isEventSuccessful = Login();

		//**************************************************************************************************************//
		//*** Step 2 : Verify that next to device name, friendly model name is also displayed in device grid display. **//
		//**************************************************************************************************************//
		strstepDescription = "Verify that next to device name, friendly model name is also displayed in device grid display ";
		strexpectedResult = "Friendly model name should be displayed for all devices in grid view.";
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("devicemodel", "", "list");

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Friendly model name is displayed for all devices in grid view.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		}
	}
}