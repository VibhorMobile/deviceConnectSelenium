package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-1347
 */
public class _119_Verify_There_will_be_five_statuses_presented_to_the_user_in__status__dropdown_filter extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";
	private String[] arrDeviceStatus_Single = {"Available", "In Use", "Offline", "Disabled"};

	public final void testScript()
	{
		//Step 1 - Login to deviceConnect with valid credentials
		isEventSuccessful = Login();

		//step 2 - Verify 'Available' option in the 'Status' filter.
		
		isEventSuccessful = selectStatus("Available");
		
		//step 3 - Verify 'In Use' option in the 'Status' filter.
		isEventSuccessful = selectStatus("In Use");
		
		//step 4 - Verify 'offline' option in the 'Status' filter.
		isEventSuccessful = selectStatus("Offline");

		//step 5 - Verify 'Disabled' option in the 'Status' filter.
		isEventSuccessful = selectStatus_DI("Disabled");
		if (!isEventSuccessful) 
		{
			isEventSuccessful = DisplayDisabledDevices();
		}
		
		for (String status : arrDeviceStatus_Single)
		{
			isEventSuccessful = selectStatus(status); // It already checks if all the displayed devices have 'Available' status only
			if (isEventSuccessful)
			{
				strActualResult = "All required devices are selected.";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
			
			reporter.ReportStep("Verify 'Available','In Use','Offline','Disabled' options in the 'Status' filter.", "User should be able to see 'Available','In Use','Offline','Disabled' devices when he clicks on 'all' or indivisual filters.", strActualResult, isEventSuccessful);
		}
	}
}