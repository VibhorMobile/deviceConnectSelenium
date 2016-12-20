package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2175
 */
public class _133_Verify_Device_Name_should_be_an_active_link_on_the_grid_view_and_list_view_screens extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";

	public final void testScript()
	{
		//*************************************************************//                                 
		//Step 1 - Login to deviceConnect
		//*************************************************************//                     
		isEventSuccessful = Login();

		//**********************************************************************//                     
		//Step 2 - Verify Device Name should be an active link on the grid view
		//*********************************************************************//                     
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("DeviceName", "Link");
		if (isEventSuccessful)
		{
			strActualResult = "All Device names are active links on the grid view.";
		}
		else
		{
			strActualResult = "VerifyDeviceDetailsInGridAndListView---" + strErrMsg_AppLib;
		}
		reporter.ReportStep("Verify Device Name should be an active link on the grid view.", "Device Name should be an active link on the grid view.", strActualResult, isEventSuccessful);

		//*********************************************************************//                     
		//Step 3 - Verify Device Name should be an active link on the list view
		//*********************************************************************//                     
			isEventSuccessful = VerifyDeviceDetailsInGridAndListView("DeviceName", "Link","list");
			if (isEventSuccessful)
			{
				strActualResult = "All Device names are active links on the list view.";
			}
			else
			{
				strActualResult = "VerifyDeviceDetailsInGridAndListView---" + strErrMsg_AppLib;
			}

		reporter.ReportStep("Verify Device Name should be an active link on the list view.", "Device Name should be an active link on the list view.", strActualResult, isEventSuccessful);
	}

}