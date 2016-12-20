package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2154
 */
public class _110_Verify_that_when_device_is_available_the_indicator_is_GREEN extends ScriptFuncLibrary
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
		//********************************************************//
		//******   Step 1 - Login to deviceConnect   *************//
		//********************************************************//
		isEventSuccessful = Login();

		//**********************************************************//
		//** Step 2 - Select Available status **********//
		//**********************************************************//          

		isEventSuccessful = selectStatus("Available");

		//****************************************************************************************//
		//*********  Step 3 - Verify that when device is available the indicator is Green. *******//
		//****************************************************************************************//
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("statusicon", "green", "list");
		
		if (isEventSuccessful)
		{
			strActualResult = "The indicator for devices Available is GREEN.";
		}
		else
		{
			strActualResult = "VerifyDeviceDetailsInGridAndListView---" + strErrMsg_AppLib;
		}

		reporter.ReportStep("Verify that when device is Available the indicator is GREEN", "Indicator for devices Available should be GREEN.", strActualResult, isEventSuccessful);
	}
}