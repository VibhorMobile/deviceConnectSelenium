package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id; QA-2171
 */


public class _399_Verify_uninstall_all_btn_enabled_only_for_that_admin_who_is_using_it extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";
	private String devicename = "";
	Object[] Values = new Object[4];


	public final void testScript()
	{
		///Step 1 - Login to deviceConnect
		isEventSuccessful = Login();
		
		//Step 2 - Connect any android device.

		Values = ExecuteCLICommand("connect", "iOS");
	  	isEventSuccessful = (boolean)Values[2];
	  	if (isEventSuccessful)
	  	{
	  		strActualResult = "Viewer launched after connecting to an Android device:  " + Values[0] + " & processfound : " +  Values[1];
	  	}
	  	else
	  	{
	  		strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
	  	}
			
	  	reporter.ReportStep("Connect to an android device and verify deviceViewer is launched with" + dicOutput.get("executedCommand"), "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
		
		//**************************************************************************//
		// Step 3 : Select filter of Status : In Use
		//**************************************************************************//
		isEventSuccessful = PerformAction("", Action.WaitForElement,"10");
		isEventSuccessful = selectStatus( "In Use");
				

		// Step 4 : Go to device details page of application connected in previous steps.
		devicename = (String) Values[3];
		isEventSuccessful = SelectDevice("devicename", devicename);
		if (isEventSuccessful)
		{
			strActualResult = "Details page of correct device displayed properly. :" + devicename;
		}
		else
		{
			strActualResult = "Could not go to device details page of :" + devicename;
		}
						
		reporter.ReportStep("Go to device details page of application connected in previous steps.", "Device details page should be displayed for the device connected in previous step.", strActualResult, isEventSuccessful);
			
			
		// Step 5: Verify Uninstall All button is enabled for the user using the device
		PerformAction("browser", "waitforpagetoload", "10");
		waitForPageLoaded();
		isEventSuccessful = PerformAction("BtnUninstallAllEnabled", Action.isDisplayed) && PerformAction("BtnUninstallAllDisabled", Action.isNotDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "'Uninstall All' button is enabled for admin user using the device.";
		}
		else
		{
			strActualResult = "'Uninstall All' button is not enabled for admin user using the device.";
		}
		reporter.ReportStep("Verify Uninstall All button is enabled for the user using the device", "'Uninstall All' button should be enabled for admin user using the device.", strActualResult, isEventSuccessful);

		//Step 6 - Logout and Login using other admin credentials
		isEventSuccessful = Logout();
		if (isEventSuccessful)
		{
			isEventSuccessful = Login("admin","deviceconnect");
		}
		else
		{
			strActualResult = "Not able to logout from application. ";
		}
		
		if (!isEventSuccessful)
		{
			return;
		}

		//**************************************************************************//
		// Step 7 : Select filter of Status : In Use
		//**************************************************************************//
	      isEventSuccessful = selectStatus( "In Use");
	      
		// Step 8 : Go to device details page of device connected by first admin use and verify that uninstall all button is not enabled for this admin user
		isEventSuccessful = SelectDevice("devicename", devicename);
		if (isEventSuccessful)
		{
			PerformAction("browser", "waitforpagetoload", "10");
			waitForPageLoaded();
			isEventSuccessful =  PerformAction("BtnUninstallAllDisabled",Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "'Uninstall All' button is not enabled for admin user not using the device.";
			}
			else
			{
				strActualResult = "'Uninstall All' button is enabled for admin user not using the device.";
			}
		}
		else
		{
			strActualResult = "Could not go to device details page of " + devicename;
		}

		reporter.ReportStep("Go to device details page of device connected by other admin user and verify uninstall all button is disabled.", "Uninstall all button should be disabled for device not in use by logged in admin user.", strActualResult, isEventSuccessful);

		//Step 8 - Logout and Login using tester credentials
		isEventSuccessful = Logout();
		if (isEventSuccessful)
		{
			isEventSuccessful = Login(dicCommon.get("testerEmailAddress"), dicCommon.get("testerPassword"));
		}
		else
		{
			strActualResult = "Not able to logout from application. ";
		}
		
		if (!isEventSuccessful)
		{
			return;
		}

		//**************************************************************************//
		// Step 3 : Select filter of Status : In Use
		//**************************************************************************//
		isEventSuccessful = selectStatus( "In Use");
		
		// Step 9 : Go to device details page of device connected by first admin use and verify that uninstall all button is not enabled for this tester user
		isEventSuccessful = SelectDevice("devicename", devicename);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful =  PerformAction("BtnUninstallAllDisabled",Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "'Uninstall All' button is not enabled for tester user not using the device.";
			}
			else
			{
				strActualResult = "'Uninstall All' button is enabled for tester user not using the device.";
			}
		}
		else
		{
			strActualResult = "Could not go to device details page of " + devicename;
		}

		reporter.ReportStep("Go to device details page of device connected by tester user and verify uninstall all button is disabled.", "Uninstall all button should be disabled for device not in use by logged in tester user.", strActualResult, isEventSuccessful);

			
		//**************************************************************************//
		//Step 6 - Close Viewer
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", "", "", devicename, "","","" );
     	isEventSuccessful =	KillObjectInstances("MobileLabs.deviceViewer.exe");
 	    reporter.ReportStep("Verfiy Device is not connected after closing devcieViewer window","Device should not be connected after closing devcieViewer window","Device is not connected after closing devcieViewer window",isEventSuccessful);

	}
}