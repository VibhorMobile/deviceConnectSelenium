package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: 08-June-2016
 * Last Modified Date: Same as creation date
 * Jira Test Case Id:QA-989
 */

public class _679_Verify_Slot_field_can_be_modified_or_added_by_admin_users_only extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strexpectedResult = "", strActualResult = "",strstepDescription="";
	String [] android={"Android"};
	
	public final void testScript()
	{
		
		//Variables from data sheet
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");
		
		//*******************************//
		// Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to first device details page //
		//**********************************************************//   
		if(isEventSuccessful)
		{
			isEventSuccessful = (Boolean)GoTofirstDeviceDetailsPage()[0];
		}
		
		//**********************************************************//
		// Step 3 - Expand details section //
		//**********************************************************//                                   
		if(isEventSuccessful)
		{
			isEventSuccessful = ShowDetails();
		}
		
		//**********************************************************//
		// Step 4 - Verify slot can be add or modify by administrator  //
		//**********************************************************// 
		if(isEventSuccessful)
		{
			isEventSuccessful=EditAndVerifySlot("789");
		}


		//****************************************************************************************//
		// Step 5 - Logout **//
		//****************************************************************************************//
		if(isEventSuccessful)
		{
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
		}

		//*************************************************************//                     
		// Step 6 : login to deviceConnect with test user.
		//*************************************************************// 
		if(isEventSuccessful)
		{
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
		}

		//**********************************************************//
		// Step 7 - Go to first device details page //
		//**********************************************************//   
		if(isEventSuccessful)
		{
			isEventSuccessful = (Boolean)GoTofirstDeviceDetailsPage()[0];
		}

		//**********************************************************//
		// Step 8 - Expand details section //
		//**********************************************************//                                   
		if(isEventSuccessful)
		{
			isEventSuccessful = ShowDetails();
		}
		
		//*************************************************************//                     
		// Step 9 : Verify user cannot edit slot.
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify user cannot add or edit slot";
			strexpectedResult = "User should not be able to add or edit slot.";
			isEventSuccessful =Add_Modify_Slot("456");
			if (!isEventSuccessful)
			{
				isEventSuccessful=true;
				strActualResult ="User cannot edit slot";
			}
			else
			{
				strActualResult = "User is able to edit slot";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
	}
}