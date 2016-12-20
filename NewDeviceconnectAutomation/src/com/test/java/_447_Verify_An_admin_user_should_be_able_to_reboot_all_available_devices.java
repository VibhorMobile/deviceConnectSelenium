package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1345, QA-1337
 */

public class _447_Verify_An_admin_user_should_be_able_to_reboot_all_available_devices extends ScriptFuncLibrary
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
		///**************************************************************************//
		//Step 1 - : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		isEventSuccessful = Login();

		//**************************************************************************//
		// Step 2 :Select status "Available".
		//**************************************************************************//
		isEventSuccessful=selectStatus("Available");
		
		//**************************************************************************//
		// Step 3 : Check if warning message is displayed.
		//**************************************************************************//
		isEventSuccessful = VerifyMessage_On_Filter_Selection();
		
		//**************************************************************************//
		// Step 4 : Select all device and reboot it.
		//**************************************************************************//
		if(!isEventSuccessful)
		{	
			selectAllDevicesCheckbox_DI();
			PerformAction("btnReboot", Action.Click);
			PerformAction("btnRebootDevice", Action.Click);
			
			//**************************************************************************//
			// Step 5 :Wait for devices: Available.
			//**************************************************************************//
			isEventSuccessful=PerformAction("btnReboot", Action.WaitForElement);
			
		}
		
		
		
		//**************************************************************************//
		// Step 6 : Verify devices are available.
		//**************************************************************************//
		Thread.sleep(20000);
		isEventSuccessful=selectStatus("Available");
		isEventSuccessful = !(VerifyMessage_On_Filter_Selection());
		strstepDescription = "Verify Admin User should be able to reboot all available devices.";
		strexpectedResult = "Admin user is able to reboot all available devices.";
		
		if (isEventSuccessful)
		{
			strActualResult = "Admin user rebooted all the available devices.";
		}
		else
		{
			strActualResult = "GetAllDeviceDetails---" + strErrMsg_GenLib;
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
	}
}