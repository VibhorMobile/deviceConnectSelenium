package com.test.java;

import java.util.ArrayList;
import java.util.List;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-863
 */

public class _553_Verify_the_multifunction_buttons_available_for_tester_and_admin_users_for_a_device_having_Offline_status extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription="",strexpectedResult="", strActualResult = "";
	Object[] values = new Object[2];
	ArrayList<String> objects=new ArrayList<String>();

	public final void testScript()
	{
		
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");
		objects.add("Disable");
		objects.add("Remove");
		
		//*************************************************************//     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//     
		isEventSuccessful = Login();
		
		//**********************************************************//
		//** Step 2 - Go to Device Index page **********//
		//**********************************************************//                                   
		isEventSuccessful = GoToDevicesPage();
		
		//**********************************************************//
		//** Step 3 - Select status from Filters dropdown **********//
		//**********************************************************//     
		isEventSuccessful =  selectStatus("Offline");
		
		
		//****************************************************************************************//
		//** Step 4 - Verify Disable and Remove button displayed for admin **//
		//****************************************************************************************//
		strstepDescription = "Verify Disable and Remove button displayed for admin.";
		strexpectedResult =  "Disable and Remove button should be displayed for admin.";
		if (isEventSuccessful)
		{
			isEventSuccessful = VerifyDeviceOptions(objects,"Offline",1,"list");
			if (isEventSuccessful)
			{
				strActualResult = "Disable and Remove button dispalyed for admin.";
			}
			else
			{
				strActualResult = "Remove button did not displayed for admin.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//****************************************************************************************//
		//** Step 5 - Logout **//
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
		// Step 6 : login to deviceConnect with test user.
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
		
		//**********************************************************//
		//** Step 7 - Select status from Filters dropdown **********//
		//**********************************************************//     
		isEventSuccessful =  selectStatus("Offline");
		
				
		//****************************************************************************************//
		//** Step 8 - Verify Disable and Remove button not displayed for tester **//
		//****************************************************************************************//
		strstepDescription = "Verify Disable and Remove button not displayed for tester.";
		strexpectedResult =  "Disable and Remove button should not be displayed for tester.";
		if (isEventSuccessful) 
		{
			isEventSuccessful = VerifyDeviceOptions(objects,"Offline",1,"list");
			if(!isEventSuccessful)
			{
				strActualResult = "Disable and Remove button did not dispalyed for tester.";
				isEventSuccessful=true;
			}
			else
			{
				strActualResult = "Remove button displayed for tester.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}

}