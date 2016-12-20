package com.test.java;

import java.util.Iterator;
import java.util.Set;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1606
 */
public class _734_Verify_that_Enable_deviceControl_Quick_Connect_is_displayed_on_System_Page_for_Admin_User extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "";
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	private Set windowids;
	String parentwindowid = "";
	String childwindowid = "";
	
	public final void testScript()
	{
		//*************************************************************//
		// Step 1 - Login to DC with Admin Credentials
		//*************************************************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to Systems page //
		//**********************************************************//                                   
		isEventSuccessful = GoToSystemPage();
		
		
		//**********************************************************//
		// Step 3 - Verify 'Enable deviceControl quick connect' displayed on system page //
		//**********************************************************//   
		strStepDescription = "Verify 'Enable deviceControl quick connect' displayed on system page";
		strExpectedResult = "'Enable deviceControl quick connect' should be displayed on system page.";
		//isEventSuccessful=GetTextOrValue(dicOR.get("enablequickConnect"),"text").contains("deviceControl quick connect");
		isEventSuccessful=PerformAction(dicOR.get("enablequickConnect"),Action.isDisplayed);
		if(isEventSuccessful)
		{
			strActualResult="'Enable deviceControl quick connect' displayed on system page.";
		}
		else
		{
			strActualResult="'Enable deviceControl quick connect' did not displayed on system page.";
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
	}
}