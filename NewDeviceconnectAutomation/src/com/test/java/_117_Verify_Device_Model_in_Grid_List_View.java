package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1347
 */

public class _117_Verify_Device_Model_in_Grid_List_View extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";

	public final void testScript()
	{
		//********************************************//
		//***** Step 1 - Login to deviceConnect *****//
		//*******************************************//                                    
		isEventSuccessful = Login();

		//**********************************************************************************//
		//*** Step 2 - Verify Device Model number should be displayed on the list view  ****//
		//**********************************************************************************//
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("DeviceModel", "Displayed","list");
			if (isEventSuccessful)
			{
				strActualResult = "All Devices' model number are displayed on the list view.";
			}
			else
			{
				strActualResult = "VerifyDeviceDetailsInGridAndListView---" + strErrMsg_AppLib;
			}

		reporter.ReportStep("Verify Device Model number should be displayed on the list view.", "Device Model number should be displayed on the list view.", strActualResult, isEventSuccessful);
	}
}