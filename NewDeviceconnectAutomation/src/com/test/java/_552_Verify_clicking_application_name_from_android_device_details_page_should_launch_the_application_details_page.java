package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-936
 */

public class _552_Verify_clicking_application_name_from_android_device_details_page_should_launch_the_application_details_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String appName = "",strstepDescription="",strexpectedResult="", strActualResult = "";
	Object[] values = new Object[2];

	public final void testScript()
	{
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
		isEventSuccessful =  selectStatus("Available");
		isEventSuccessful= selectPlatform("Android");
		
		//****************************************************************************************//
		//** Step 4 - select first device **//
		//****************************************************************************************//
		if (isEventSuccessful) 
		{
			values = GoTofirstDeviceDetailsPage();
			isEventSuccessful=(Boolean)values[0];
		}
		
		
		//****************************************************************************************//
		//** Step 4 - select first device **//
		//****************************************************************************************//
		strstepDescription = "Click on first installed app";
		strexpectedResult =  "Application details page should be displayed.";
		waitForPageLoaded();
		PerformAction("browser","waitforpagetoload");						
		if (isEventSuccessful)
		{
			appName=getText((dicOR.get("deviceDetailsUnInstallFirstApp"))+"/td/a[2]");
			isEventSuccessful = PerformAction((dicOR.get("deviceDetailsUnInstallFirstApp"))+"/td/a[2]",Action.Click);
			if (isEventSuccessful) 
			{
				waitForPageLoaded();
				isEventSuccessful = PerformAction("eleAppNameDisplay", Action.WaitForElement);
				if (isEventSuccessful)
				{
					isEventSuccessful=GetTextOrValue("eleAppNameDisplay", "text").equals(appName);
					if (isEventSuccessful)
					{
						strActualResult = "Application details page displayed";
					}
					else
					{
						strActualResult = "Application details page did not displayed";
					}
				}
				else
				{
					strActualResult = "Application details page did not displayed";
				}
			}
			else
			{
				strActualResult = "Unable to click on first installed app.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}

}