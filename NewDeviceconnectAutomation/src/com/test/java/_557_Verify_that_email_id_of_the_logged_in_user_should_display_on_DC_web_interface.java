package com.test.java;

import java.io.IOException;
import java.util.ArrayList;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-819
 */

public class _557_Verify_that_email_id_of_the_logged_in_user_should_display_on_DC_web_interface extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
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
			
		//****************************************************************************************//
		//** Step 2 - Verify user email_id displayed on DC web UI **//
		//****************************************************************************************//
		strstepDescription = "Verify user email_id displayed on DC web UI";
		strexpectedResult =  "User email_id should be displayed on DC web UI.";
		isEventSuccessful=GetTextOrValue("LogoutMenuLinkIE","text").equals(testerEmailAddress);
		if (isEventSuccessful)
		{
			strActualResult = "User email_id displayed on DC web UI";
		}
		else
		{
			strActualResult = "User email_id did not displayed on DC web UI";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}