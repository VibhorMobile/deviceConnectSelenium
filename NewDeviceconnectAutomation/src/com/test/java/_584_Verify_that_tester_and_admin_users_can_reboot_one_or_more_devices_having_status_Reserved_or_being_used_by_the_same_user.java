package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: Last week of January
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-1392
 */
public class _584_Verify_that_tester_and_admin_users_can_reboot_one_or_more_devices_having_status_Reserved_or_being_used_by_the_same_user extends ScriptFuncLibrary

{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "",devicename, EmailAddress,Password ;
	private String strexpectedResult = "";
	private String strActualResult = "";
	Object[] values = new Object[2];
	
	public final void testScript() throws IOException
	{
			
		// Step 1 - Login to deviceConnect//
		isEventSuccessful = Login();


		// Step 2 : Connect to an android device and verify deviceViewer is launched on the windows desktop.
		if (isEventSuccessful)
		{	
			values = ExecuteCLICommand("run", "Android");
			isEventSuccessful = (boolean)values[2];
			devicename= (String)values[3];
			if (isEventSuccessful)
			{
				strActualResult = "Viewer launched after connecting to an Android device:  " + values[0] + " & processfound : " +  values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			
		}
		else
		{
			return; // Return if in use is not selected.
		}
		reporter.ReportStep("Connect to an android device and verify deviceViewer is launched with" + dicOutput.get("executedCommand"), "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
		
		
		//**************************************************************************//
		// Step 3 : Select status 'In Use' and platform android and check warning message not displayed.
		//**************************************************************************//
		isEventSuccessful = selectStatus("In Use");
		isEventSuccessful = selectPlatform("Android");
		isEventSuccessful = !(VerifyMessage_On_Filter_Selection());
		
		//**************************************************************************//
		// Step 4 : Search for the device which we have connected using CLI and verify release button not available.
		//**************************************************************************//
		isEventSuccessful = searchDevice(devicename, "devicename");
		
		
		//**************************************************************************//
		// Step 5 : Search for the device which we have connected using CLI and verify Reboot button is not available.
		//**************************************************************************//
		selectFirstDeviceChk_DI();
		strStepDescription = "Verify that Reboot button is available, which we have connected using CLI .";
		strExpectedResult = "Reboot button should be displayed.";
		isEventSuccessful = PerformAction("btnReboot", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Reboot button is displayed.";
		}
		else
		{
			strActualResult = "Reboot Devices---" + strErrMsg_GenLib;
		}
		
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		
		//**************************************************************************//
		// Step 6 : Logout from Device Connect
		//**************************************************************************//
		isEventSuccessful = Logout();
		ExecuteCLICommand("release", "Android", "", "", devicename, "","","" );
		KillObjectInstances("MobileLabs.deviceViewer.exe");
		
		//**************************************************************************//
		// Step 7 : Login again as tester.
		//**************************************************************************//
		EmailAddress = dicCommon.get("testerEmailAddress");
		Password = dicCommon.get("testerPassword");
		
		strStepDescription = "Login to deviceConnect with valid test user.";
		strExpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login(EmailAddress, Password);
							
			
		// Step 8 : Connect to an android device and verify deviceViewer is launched on the windows desktop.
		if (isEventSuccessful)
		{	
			values = ExecuteCLICommand("run", "Android");
			isEventSuccessful = (boolean)values[2];
			devicename= (String)values[3];
			if (isEventSuccessful)
			{
				strActualResult = "Viewer launched after connecting to an Android device:  " + values[0] + " & processfound : " +  values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			
		}
		else
		{
			return; // Return if in use is not selected.
		}
		reporter.ReportStep("Connect to an android device and verify deviceViewer is launched with" + dicOutput.get("executedCommand"), "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
		
		
		//**************************************************************************//
		// Step 9 : Select status 'In Use' and platform android and check warning message not displayed.
		//**************************************************************************//
		isEventSuccessful=selectStatus("In Use");
		isEventSuccessful=selectPlatform("Android");
		isEventSuccessful=!(VerifyMessage_On_Filter_Selection());
		
		//**************************************************************************//
		// Step 10 : Search for the device which we have connected using CLI and verify release button not available.
		//**************************************************************************//
		strStepDescription = "Verify that searched device name is being dispalyed.";
		strExpectedResult = "Searched device name should be dispalyed.";
		
		isEventSuccessful = searchDevice(devicename, "devicename");
		if(isEventSuccessful)
		{
			strActualResult = "Searched device name should be displayed.";
		}
		else
		{
			strActualResult = "Searched device name is not displayed.";
		}
		
		
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		
		//**************************************************************************//
		// Step 11 : Search for the device which we have connected using CLI and verify Reboot button is not available.
		//**************************************************************************//
		
		selectFirstDeviceChk_DI();
		strStepDescription = "Verify that Reboot button is available, which we have connected using CLI .";
		strExpectedResult = "Reboot button should be displayed.";
		isEventSuccessful = !PerformAction("btnReboot", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Reboot button is not displayed.";
		}
		else
		{
			strActualResult = "Reboot Devices---" + strErrMsg_GenLib;
		}
		
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		
		//**************************************************************************//
		// Step 12 : Release device.
		//**************************************************************************//
		ExecuteCLICommand("release", "Android", "", "", devicename, "","","" );
		Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");	
	
	}	
	
}
