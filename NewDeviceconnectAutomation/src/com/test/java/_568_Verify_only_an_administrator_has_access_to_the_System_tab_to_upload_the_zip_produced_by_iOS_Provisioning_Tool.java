package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: 8-Feb-2016
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-1390
 */

public class _568_Verify_only_an_administrator_has_access_to_the_System_tab_to_upload_the_zip_produced_by_iOS_Provisioning_Tool extends ScriptFuncLibrary
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
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to Systems page //
		//**********************************************************//                                   
		isEventSuccessful = GoToSystemPage();
			
		//**********************************************************//
		// Step 3 - Click on iOS Management link on System tab //
		//**********************************************************//   
		strStepDescription = "Verify iOS Management page open after clicking on iOS Management link";
		strExpectedResult = "iOS Management page should displayed.";
		isEventSuccessful=PerformAction("linkiOSManagement",Action.Click);
		if(isEventSuccessful)
		{
			strActualResult="iOS Management page displayed.";
		}
		else
		{
			strActualResult="iOS Management page did not displayed.";
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 4 - Verify upload firmware link present and open up window dialog  //
		//**********************************************************// 
		strStepDescription = "Verify upload output file can be accessed by administrator";
		strExpectedResult = "Upload output file should be accessed by administrator";
		waitForPageLoaded();
		isEventSuccessful = PerformAction("uploadoutputFileSystemTab",Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Upload output file can be accessed by administrator";
		}
		else
		{
			strActualResult = "Upload output file can not be accessed by administrator";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
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
		
		//*************************************************************//                     
		// Step 7 : Verify user cannot access System tab.
		//*************************************************************//                     
		strstepDescription = "Verify user cannot access System tab.";
		strexpectedResult = "User should not be able to access System tab.";
		isEventSuccessful =PerformAction(dicOR.get("eleTopNavTab").replace("__TABNAME__", "System"), Action.isDisplayed);
		if (!isEventSuccessful)
		{
			isEventSuccessful=true;
			strActualResult ="User cannot access System tab";
		}
		else
		{
			strActualResult = "User is able to access System tab";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}