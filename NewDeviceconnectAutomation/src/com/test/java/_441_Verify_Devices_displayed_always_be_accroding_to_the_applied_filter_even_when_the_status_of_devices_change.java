package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1343
 */

public class _441_Verify_Devices_displayed_always_be_accroding_to_the_applied_filter_even_when_the_status_of_devices_change extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", devicename = "";
	private String xpath = "";
	private int devicecount;
	Object[] Values = new Object[4]; 

	public final void testScript()
	{
		///**************************************************************************//
		//Step 1 - : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		isEventSuccessful = Login();

		//**************************************************************************//
		// Step 2 :Select 'iOS' platform.
		//**************************************************************************//
		isEventSuccessful=selectPlatform("iOS");
		
		
		//**************************************************************************//
		// Step 3 : Count number of Devices displayed for iOS platform.
		//**************************************************************************//
		devicecount = getelementCount(dicOR.get("eleDevicesHolderListView"));
		
		//**************************************************************************//
		// Step 4 : Select first device and disable it.
		//**************************************************************************//
		//selectfirstDevice();
		selectFirstDeviceChk_DI();
		PerformAction("btnBulkDisable_Devices", Action.Click);
		PerformAction("btnDisableDevices_DisableDevice", Action.Click);
		
		//**************************************************************************//
		// Step 5 : Verify device is still shown in selected platform filter.
		//**************************************************************************//
		strstepDescription = "Verify device displayed according to the applied filter even status of device changed";
		strexpectedResult = "Device should be appeared in the same selected filter after staus got changed.";
		devicename = (String) Values[3];

		if (devicecount==getelementCount(dicOR.get("eleDevicesHolderListView")))
		{
			isEventSuccessful = true;
			strActualResult = "Device appeared in the same selected filter after staus got changed.";
		}
		else
		{
			isEventSuccessful = false;
			strActualResult = "GetSingleDeviceDetails---" + strErrMsg_GenLib;
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		//Step 6 - Post Condition - Enable that device
		//**************************************************************************//
		isEventSuccessful =	PerformAction("btnEnable_Devices", Action.Click);
		PerformAction("btnEnableDevices_EnableDevice", Action.Click);
		
	}
}