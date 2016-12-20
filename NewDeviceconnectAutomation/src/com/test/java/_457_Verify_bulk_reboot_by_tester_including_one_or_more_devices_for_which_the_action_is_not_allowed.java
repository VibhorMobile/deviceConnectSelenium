package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1171
 */

public class _457_Verify_bulk_reboot_by_tester_including_one_or_more_devices_for_which_the_action_is_not_allowed extends ScriptFuncLibrary
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
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");
				
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid test user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with " + EmailAddress, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

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
		strstepDescription = "Verify bulk reboot by tester including one or more devices for which the action is not allowed.";
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