package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-743
 */

public class _518_Verify_Clicking_on_Name_link_on_Compatible_devices_section_should_navigate_to_the_device_details_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "", deviceName="";
	String [] filter={"iOS"};

	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to Applications page //
		//**********************************************************//                                   
		isEventSuccessful = GoToApplicationsPage();
			
		//**********************************************************//
		// Step 3 - Click on first application link and verify user on application details page //
		//**********************************************************// 
		strStepDescription = "Click on first app link and app details page get displayed";
		strExpectedResult = "App details page should be displayed.";
		isEventSuccessful = SelectApplication("first");
		if (isEventSuccessful)
		{
			strActualResult = "App details page displayed";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 5 - Verify provisioned values //
		//**********************************************************//        
		strStepDescription = "Verify clicking on campatible device navigating to device details page";
		strExpectedResult = "Device details page should be opened";
		deviceName=GetTextOrValue("CompatibledeviceAppDetails", "text");
		isEventSuccessful = PerformAction("CompatibledeviceAppDetails",Action.Click);
		if (isEventSuccessful)
		{
			if(deviceName.equals(GetTextOrValue("eleDeviceNameinDeviceDetailsHeader", "text")))
			{
				isEventSuccessful=true;
				strActualResult = "Device details page opened.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "Device details page did not opened";
			}
		}
		else
		{
			strActualResult = "Unable to click on compatible device";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}

}