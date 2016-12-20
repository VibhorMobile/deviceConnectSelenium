package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: Third week of February
 * Last Modified Date: Same as creation date
 * Jira Test Case Id-1528
 */

public class _627_Verify_tester_user_is_able_to_Uninstall_any_app_or_all_apps_from_any_device_from_device_details_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "";
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		//*************************************************************//
		//Step 1 : login to deviceConnect with test user.
		//*************************************************************//
		isEventSuccessful = Login(dicCommon.get("testerEmailAddress"),dicCommon.get("testerPassword")); 
		
		//******************************************************************//
		//*** Step 2 : Select Status Available. *****//
		//******************************************************************// 
			
		isEventSuccessful = selectStatus("Available");
		strstepDescription = "Open device details page for first Available device.";
		strexpectedResult =  "Device details page for the first available device should be displayed.";
					
		if (isEventSuccessful)
		{
			isEventSuccessful = !VerifyMessage_On_Filter_Selection();
			if (isEventSuccessful) // continue only if there are some devices under android platform.
			{
				isEventSuccessful = SelectDevice("first");
				if (isEventSuccessful)
				{
					strActualResult = "Device details page displayed";
				}
				else
				{
					strActualResult = "Device details page did not displayed";
				}
			}
			else
			{
				strActualResult = "Warning messgae displayed on devices page.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
					
		//******************************************************************//
		//** Step 4 : Uninstall first installed application ****//
		//******************************************************************//
		strstepDescription = "Uninstall first app on the device";
		strexpectedResult = "App should get uninstalled";
		isEventSuccessful = installnUninstallApp_On_Device("uninstall", false);
		if (isEventSuccessful)
		{
			strActualResult = "Application uninstalled successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful); 
	}
}
