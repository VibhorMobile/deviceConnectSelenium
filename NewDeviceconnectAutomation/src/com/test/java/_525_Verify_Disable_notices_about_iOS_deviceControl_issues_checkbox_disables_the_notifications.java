package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

public class _525_Verify_Disable_notices_about_iOS_deviceControl_issues_checkbox_disables_the_notifications extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";
	String [] android={"Android"};
	
	public final void testScript()
	{
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
			isEventSuccessful=GetTextOrValue("iOSStatusText","text").equals("deviceControl Status: Good");
			if(isEventSuccessful)
			{
				strActualResult="iOS Management page displayed.";
			}
			else
			{
				strActualResult="iOS Management page did not displayed.";
			}
		}
		else
		{
			strActualResult="iOS Management page did not displayed.";
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 4 - Disable notices about iOS and verify it  //
		//**********************************************************// 
		strStepDescription = "Verify Disable notices about iOS deviceControl issues checkbox disables the notifications";
		strExpectedResult = "Notification should get disabled";
		isEventSuccessful = PerformAction("iOSNotificationDisablechkbox",Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("iOSStatusText",Action.isNotDisplayed);
			strActualResult = "Notifications disabled";
		}
		else
		{
			strActualResult = "Notification did not get disabled";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		PerformAction("iOSNotificationDisablechkbox",Action.Click);
	}
}