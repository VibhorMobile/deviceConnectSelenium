package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2170
 */
public class _126_Verify_the_default_view_should_be_show_all_and_the_all_selection_should_be_highlighted_in_the_platform_filter extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "", PlatformSelected = "";

	public final void testScript()
	{
	
		//*************************************************************//                 
		//Step 1 - Login to deviceConnect
		//*************************************************************//     
		isEventSuccessful = Login();
	
		//***********************************************************************************//     
		//Step 2 - Verify All filter is selected for Platform Dropdown
		//**********************************************************************************//    
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("existenceofplatform", "android");
			if (isEventSuccessful)
			{
				isEventSuccessful = VerifyDeviceDetailsInGridAndListView("existenceofplatform", "ios");
				if (!isEventSuccessful)
				{
					strActualResult = strErrMsg_AppLib;
				}
				else
				{
					strActualResult = "Device list for the default selection is displayed";
				}
			}
			else
			{
				strActualResult = "VerifyDeviceDetailsInGridAndListView---" + strErrMsg_AppLib;
			}

		reporter.ReportStep("Verify All filter displays all the platform devices.", "Device list for the default selection should be displayed.", strActualResult, isEventSuccessful);
	}
}