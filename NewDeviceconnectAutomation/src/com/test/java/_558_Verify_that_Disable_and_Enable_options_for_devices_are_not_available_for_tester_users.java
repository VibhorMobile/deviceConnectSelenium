package com.test.java;

import java.io.IOException;
import java.util.ArrayList;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-449
 */

public class _558_Verify_that_Disable_and_Enable_options_for_devices_are_not_available_for_tester_users extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	

	public final void testScript() throws InterruptedException, IOException
	{
		//Variables from data sheet
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful = Login();
		
			
		//****************************************************************************************//
		//** Step 2 - Select status Disable **//
		//****************************************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful=selectStatus("Disabled");
		}
		
		//****************************************************************************************//
		//** Step 3 - Verify at-least one disable device displayed on UI if not disable one device **//
		//****************************************************************************************//
		if(isEventSuccessful)
		{	
			isEventSuccessful=VerifyMessage_On_Filter_Selection();
			if(isEventSuccessful)
			{
				isEventSuccessful=selectStatus("Offline");
				if(isEventSuccessful)
				{
					selectFirstDeviceChk_DI();
					isEventSuccessful=PerformAction("btnBulkDisable_Devices", Action.Click);
					waitForPageLoaded();
					isEventSuccessful=PerformAction("btnDisableDevices_DisableDevice", Action.Click);
					waitForPageLoaded();
				}
			}
			else
			{
				isEventSuccessful=true;
			}
		}
		
		//****************************************************************************************//
		//** Step 4 - Logout **//
		//****************************************************************************************//
		strstepDescription = "Verify user logout from application";
		strexpectedResult =  "User should be able to logout.";
		isEventSuccessful=Logout();
		if (isEventSuccessful)
		{
			strActualResult = "User logout from application.";
		}
		else
		{
			strActualResult = "User is not able to logout.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//*************************************************************//                     
		// Step 5 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid test user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(testerEmailAddress, testerPassword);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with " + testerEmailAddress, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
		
		//**********************************************************//
		//** Step 6 - Select status from Filters dropdown **********//
		//**********************************************************//     
		isEventSuccessful =  selectStatus("Disabled");
		PerformAction("btnRefresh_Devices", Action.Click);	
		waitForPageLoaded();
		
		//**********************************************************//
		//** Step 7 - Verify Enable button not displayed for tester user **********//
		//**********************************************************//     
		isEventSuccessful = (Boolean) selectFirstDeviceChk_DI()[0];
		strstepDescription = "Verify Enable button is not displayed for tester.";
		strexpectedResult =  "Enable button should not displayed for tester.";
		waitForPageLoaded();
		isEventSuccessful=PerformAction("btnBulkDisable_Devices", Action.isNotDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Enable button did not displayed for tester";
		}
		else
		{
			strActualResult = "Enable button displayed for tester";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}