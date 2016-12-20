package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-358, QA-14
 */

public class _500_Verify_install_and_uninstall_app_from_device_details_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();

		//******************************************************************//
		//*** Step 2 : Select Status Available. *****//
		//******************************************************************//  
		isEventSuccessful = selectStatus("Available");
		isEventSuccessful=selectPlatform("Android");
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
		//** Step 3 : Install first application ****//
		//******************************************************************//
		strstepDescription = "Install first app on the device";
		strexpectedResult = "App should get installed";
		isEventSuccessful = installnUninstallApp_On_Device("install", false);
		if (isEventSuccessful)
		{
			strActualResult = "Application installed successfully.";
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