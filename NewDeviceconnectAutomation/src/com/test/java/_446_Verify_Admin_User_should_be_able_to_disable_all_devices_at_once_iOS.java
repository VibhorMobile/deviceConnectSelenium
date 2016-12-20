package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1136
 */

public class _446_Verify_Admin_User_should_be_able_to_disable_all_devices_at_once_iOS extends ScriptFuncLibrary
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
		isEventSuccessful=selectPlatform("iOS");
		isEventSuccessful=selectStatus("Available,In Use");
		
		//**************************************************************************//
		// Step 3 : Check if warning message is displayed.
		//**************************************************************************//
		isEventSuccessful = VerifyMessage_On_Filter_Selection();
		
		//**************************************************************************//
		// Step 4 : Select all device and disable it.
		//**************************************************************************//
		if(!isEventSuccessful)
		{	
			selectAllDevicesCheckbox_DI();
			PerformAction("btnBulkDisable_Devices", Action.Click);
			PerformAction("btnDisableDevices_DisableDevice", Action.Click);
			
			//**************************************************************************//
			// Step 3 :Check is there any available device.
			//**************************************************************************//
			isEventSuccessful=selectStatus("Available");
			isEventSuccessful = VerifyMessage_On_Filter_Selection();
		}
		
		
		
		//**************************************************************************//
		// Step 5 : Verify devices are disabled.
		//**************************************************************************//
		strstepDescription = "Verify Admin User should be able to disable all devices at once iOS.";
		strexpectedResult = "Admin User should be able to disable all devices at once iOS.";
		
		if (isEventSuccessful)
		{
			strActualResult = "All iOS devices disabled.";
		}
		else
		{
			strActualResult = "GetAllDeviceDetails---" + strErrMsg_GenLib;
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		//Step 6 - Post Condition - Enable that devices
		//**************************************************************************//
		isEventSuccessful=selectStatus("Disabled");
		isEventSuccessful =	PerformAction("btnEnable_Devices", Action.Click);
		PerformAction("btnEnableDevices_EnableDevice", Action.Click);
		
	}
}