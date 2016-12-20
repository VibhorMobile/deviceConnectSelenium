package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1193
 */

public class _456_Verify_bulk_reboot_on_devices_list_filtered_on_the_basis_of_Platform_value extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private int devicecount;
	

	public final void testScript() throws InterruptedException
	{
		//*************************************************************//                     
		// Step 1 : login to deviceConnect .
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC();

		//**************************************************************************//
		// Step 2 :Select status 'Available'
		//**************************************************************************//
		isEventSuccessful=selectStatus("Available");
		
		//**************************************************************************//
		// Step 3 : Check if warning message is displayed.
		//**************************************************************************//
		isEventSuccessful = !(VerifyMessage_On_Filter_Selection());
		
		//**************************************************************************//
		// Step 4 : Select all device then filter 'iOS' and reboot it.
		//**************************************************************************//
		if(isEventSuccessful)
		{	
			selectAllDevicesCheckbox_DI();
			isEventSuccessful=selectPlatform("iOS");
			PerformAction("btnReboot", Action.ClickUsingJS);
			waitForPageLoaded();
			PerformAction("btnRebootDevice", Action.ClickUsingJS);
			
			//**************************************************************************//
			// Step 5 :Select android platform and available status.
			//**************************************************************************//
			isEventSuccessful=selectStatus("Available");
			isEventSuccessful=selectPlatform("Android");
			
		}
		
		//**************************************************************************//
		// Step 6 : Check details of devices.
		//**************************************************************************//
		isEventSuccessful = !(VerifyMessage_On_Filter_Selection());
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful=VerifyDeviceDetailsInGridAndListView("devicestatus", "Available", "list");
		}
		
		//**************************************************************************//
		// Step 7 : Verify devices are available.
		//**************************************************************************//
		
		strstepDescription = "Verify bulk reboot is working for filtered devices.";
		strexpectedResult = "Bulk reboot should work for filtered devices.";
		
		if (isEventSuccessful)
		{
			strActualResult = "Bulk reboot is working for filtered devices.";
		}
		else
		{
			strActualResult = "Bulk Reboot---" + strErrMsg_GenLib;
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
	}
}