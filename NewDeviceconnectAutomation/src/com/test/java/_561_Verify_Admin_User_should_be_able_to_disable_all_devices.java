package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: 5-Feb-2016
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-649, QA-1136, QA-1201
 */

public class _561_Verify_Admin_User_should_be_able_to_disable_all_devices extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private int devicecount;
	

	public final void testScript()
	{
		///**************************************************************************//
		//Step 1 - : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		isEventSuccessful = Login();

		//**************************************************************************//
		// Step 2 :Select 'Android' platform and status "Available, In Use and Disabled".
		//**************************************************************************//
		isEventSuccessful=selectPlatform("iOS,Android");
		isEventSuccessful=selectStatus("Available,Offline");
		
		//**************************************************************************//
		// Step 3 : Check if warning message is displayed.
		//**************************************************************************//
		isEventSuccessful = !VerifyMessage_On_Filter_Selection();
		
		//**************************************************************************//
		// Step 4 : Select all device and disable it.
		//**************************************************************************//
		if(isEventSuccessful)
		{	
			selectAllDevicesCheckbox_DI();
			isEventSuccessful=PerformAction("btnBulkDisable_Devices", Action.Click);
			isEventSuccessful=PerformAction("btnDisableDevices_DisableDevice", Action.Click);
		}
		
		
		
		//**************************************************************************//
		// Step 5 : Verify devices are disabled.
		//**************************************************************************//
		strstepDescription = "Verify Admin User should be able to disable all devices.";
		strexpectedResult = "Admin User should be able to disable all devices.";
		isEventSuccessful=VerifyDeviceDetailsInGridAndListView("statusicon", "disabled", "list");
		if (isEventSuccessful)
		{
			strActualResult = "All devices are disabled.";
		}
		else
		{
				strActualResult = "GetAllDeviceDetails---" + strErrMsg_GenLib;
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		//Step 6 - Post Condition - Enable that devices
		//**************************************************************************//
		isEventSuccessful =	PerformAction("btnEnable_Devices", Action.Click);
		isEventSuccessful = PerformAction("btnEnableDevices_EnableDevice", Action.Click);
		
		
	}
}