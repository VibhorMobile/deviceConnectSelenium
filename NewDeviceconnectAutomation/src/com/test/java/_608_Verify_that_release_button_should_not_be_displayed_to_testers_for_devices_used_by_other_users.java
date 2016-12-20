package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-209
 */
public class _608_Verify_that_release_button_should_not_be_displayed_to_testers_for_devices_used_by_other_users extends ScriptFuncLibrary

{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	Object[] Values = new Object[5]; 
	private String strActualResult = "", devicename = "",EmailAddress, Password;
	
	public final void testScript() throws IOException
	{
		
		// Step 1 - Login to deviceConnect//
		isEventSuccessful = Login();


		// Step 2 : Connect to an android device and verify deviceViewer is launched on the windows desktop.
		if (isEventSuccessful)
		{	
			Values = ExecuteCLICommand("run", "Android");
			isEventSuccessful = (boolean)Values[2];
			devicename= (String)Values[3];
			if (isEventSuccessful)
			{
				strActualResult = "Viewer launched after connecting to an Android device:  " + Values[0] + " & processfound : " +  Values[1];
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
		// Step 3 : Logout from Device Connect
		//**************************************************************************//
		isEventSuccessful = Logout();

		//**************************************************************************//
		// Step 4 : Login again as tester.
		//**************************************************************************//
		EmailAddress = dicCommon.get("testerEmailAddress");
		Password = dicCommon.get("testerPassword");

		strStepDescription = "Login to deviceConnect with valid test user.";
		strExpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login(EmailAddress, Password);

		//**************************************************************************//
		// Step 5 : Select status 'In Use' and platform android and check warning message not displayed.
		//**************************************************************************//
		isEventSuccessful=selectStatus("In Use");
		isEventSuccessful=selectPlatform("Android");
		waitForPageLoaded();
		isEventSuccessful=!(VerifyMessage_On_Filter_Selection());
		
		//**************************************************************************//
		// Step 6 : Search for the device which we have connected using CLI and verify release button not available.
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
		// Step 10 : Search for the device which we have connected using CLI and verify Reboot button is not available.
		//**************************************************************************//
		
		if(isEventSuccessful)
		{
			
			selectFirstDeviceChk_DI();
			strStepDescription = "Verify that release button not available.";
			strExpectedResult = "Release button should not be displayed.";
			isEventSuccessful=PerformAction("//button[text()='Release' and @style='display: inline-block;']", Action.isNotDisplayed);
			
			if (isEventSuccessful)
			{
				strActualResult = "Reboot button is not displayed.";
			}
			else
			{
				strActualResult = "Reboot Devices---" + strErrMsg_GenLib;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
			
			
		//**************************************************************************//
		// Step 8 : Release device.
		//**************************************************************************//
		ExecuteCLICommand("release", "Android", "", "", devicename, "","","" );
		Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
	}

}
