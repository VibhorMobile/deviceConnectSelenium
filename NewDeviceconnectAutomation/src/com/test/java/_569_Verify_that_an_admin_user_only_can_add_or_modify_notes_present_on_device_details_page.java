package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: 9-Feb-2016
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-728, QA-537, QA-1151
 */

public class _569_Verify_that_an_admin_user_only_can_add_or_modify_notes_present_on_device_details_page extends ScriptFuncLibrary
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
			waitForPageLoaded();
			isEventSuccessful = ShowDetails();
		}
		
		//**********************************************************//
		// Step 4 - Verify notes can be add or modify by administrator  //
		//**********************************************************// 
		isEventSuccessful=EditAndVerifyNotes("Notes field is editable");
		
		
		//****************************************************************************************//
		// Step 5 - Logout **//
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
		// Step 9 : Verify user cannot edit notes.
		//*************************************************************//                     
		strstepDescription = "Verify user cannot add or edit notes";
		strexpectedResult = "User should not be able to add or edit notes.";
		isEventSuccessful =Add_Modify_Notes("Unable to edit notes");
		if (!isEventSuccessful)
		{
			isEventSuccessful=true;
			strActualResult ="User cannot edit notes";
		}
		else
		{
			strActualResult = "User is able to edit notes";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}