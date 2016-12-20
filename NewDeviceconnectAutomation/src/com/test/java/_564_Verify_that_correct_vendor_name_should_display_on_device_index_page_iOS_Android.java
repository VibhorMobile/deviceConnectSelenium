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
 * JIRA ID --> QA-1176
 */

public class _564_Verify_that_correct_vendor_name_should_display_on_device_index_page_iOS_Android extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "",Vendor="",strstepDescription="",strexpectedResult="";

	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to Device Index page //
		//**********************************************************//                                   
		isEventSuccessful = GoToDevicesPage();
		
		//**************************************************************************//
		// Step 3 :Select 'iOS' platform and status "Available, In Use".
		//**************************************************************************//
		isEventSuccessful=selectPlatform("iOS");
		isEventSuccessful=selectStatus("Available,In Use");
		
		//*********************************************//
		// Step 4 - Verify "Vendor Name" column is displayed and extract Vendor Name//
		//********************************************//
		SelectColumn_Devices_SFL("Vendor Name");
		PerformAction("btnRefresh_Devices", Action.Click);
		Vendor=GetTextOrValue(dicOR.get("eleDeviceModel_ListView").replace("__INDEX__", "2"),"text");
		
		//******************************************************************//
		//*** Step 5 : Select first device*****//
		//******************************************************************//  
		strstepDescription = "Open device details page for first device.";
		strexpectedResult =  "Device details page for the first device should be displayed.";
		if (isEventSuccessful)
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
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 6 : Verify usage dates are in expected range. *****//
		//******************************************************************//
		strstepDescription = "Verify correct vendor name displayed on device index page for iOS";
		strexpectedResult =  "Correct vendor name should be displayed on device index page for iOS";
		isEventSuccessful=ShowDetails();
		if(isEventSuccessful)
		{
			isEventSuccessful=VerifyOnDeviceDetailsPage("Vendor Name ",Vendor);
			if(isEventSuccessful)
			{
				strActualResult = "Correct vendor name displayed on device index page for iOS";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 7 - Go to Device Index page //
		//**********************************************************//                                   
		isEventSuccessful = GoToDevicesPage();
				
		//**************************************************************************//
		// Step 8 :Select 'iOS' platform and status "Available, In Use".
		//**************************************************************************//
		PerformAction("browser",Action.WaitForPageToLoad);
		SelectColumn_Devices_SFL("Model,OS");
		isEventSuccessful=selectPlatform("Android");
		isEventSuccessful=selectStatus("Available,In Use");
				
		//*********************************************//
		// Step 9 - Verify "Vendor Name" column is displayed and extract Vendor Name//
		//********************************************//
		SelectColumn_Devices_SFL("Vendor Name");
		PerformAction("btnRefresh_Devices", Action.Click);
		Vendor=GetTextOrValue(dicOR.get("eleDeviceModel_ListView").replace("__INDEX__", "2"),"text");
				
		//******************************************************************//
		//*** Step 10 : Select first device*****//
		//******************************************************************//  
		strstepDescription = "Open device details page for first device.";
		strexpectedResult =  "Device details page for the first device should be displayed.";
		if (isEventSuccessful)
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
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
				
		//******************************************************************//
		//*** Step 11 : Verify usage dates are in expected range. *****//
		//******************************************************************//
		strstepDescription = "Verify correct vendor name displayed on device index page for Android";
		strexpectedResult =  "Correct vendor name should be displayed on device index page for Android";
		isEventSuccessful=ShowDetails();
		if(isEventSuccessful)
		{
			isEventSuccessful=VerifyOnDeviceDetailsPage("Vendor Name ",Vendor);
			if(isEventSuccessful)
			{
				strActualResult = "Correct vendor name displayed on device index page for Android";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}

}