package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1253
 */

public class _458_Verify_bulk_reboot_by_admin_including_one_or_more_devices_for_which_the_action_is_not_allowed extends ScriptFuncLibrary
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
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid admin user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);

		//**************************************************************************//
		// Step 2 :Select status 'Available'
		//**************************************************************************//
		isEventSuccessful=selectStatus("Disabled,Offline");
		
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
			isEventSuccessful=PerformAction("btnReboot", Action.isNotDisplayed);
			
			
		}
		if(isEventSuccessful)
		{
			//**************************************************************************//
			// Step 5 :Select available status.
			//**************************************************************************//
			isEventSuccessful=selectStatus("Available");
		}
		
		//**************************************************************************//
		// Step 6 : Select one available device and reboot it
		//**************************************************************************//
		isEventSuccessful = (boolean)selectFirstDeviceChk_DI()[0];
		if(isEventSuccessful)
		{
			PerformAction("btnReboot", Action.Click);
			PerformAction("btnRebootDevice", Action.Click);
		}
		
		//**************************************************************************//
		// Step 7 : Verify Offline devices remain unaffected.
		//**************************************************************************//
		isEventSuccessful=selectStatus("Offline");
		isEventSuccessful=VerifyDeviceDetailsInGridAndListView("devicestatus", "Offline", "list");
		strstepDescription = "Verify bulk reboot by admin including one or more devices for which the action is not allowed.";
		strexpectedResult = "Devices for which action is not allowed should remain unaffected.";
		
		if (isEventSuccessful)
		{
			strActualResult = "Devices for which action is not allowed remain unaffected.";
		}
		else
		{
			strActualResult = "Bulk Reboot---" + strErrMsg_GenLib;
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
	}
}