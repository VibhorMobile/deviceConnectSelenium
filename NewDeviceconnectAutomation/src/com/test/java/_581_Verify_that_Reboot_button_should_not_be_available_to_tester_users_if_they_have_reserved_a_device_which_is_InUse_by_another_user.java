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
 * JIRA ID --> QA-478
 */
public class _581_Verify_that_Reboot_button_should_not_be_available_to_tester_users_if_they_have_reserved_a_device_which_is_InUse_by_another_user extends ScriptFuncLibrary

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
		
		//Step  - Delete All Reservations
		GoToReservationsPage();
		RSVD_DeleteAllReservations();
		GoToDevicesPage();

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
		// Step 7 : Go to create Reservation Page.
		//**************************************************************************//
		isEventSuccessful=GoToReservationsPage();
		if(isEventSuccessful)
		{
			isEventSuccessful=GoToCreateReservationPage();
		}
		
		//**************************************************************************//
		// Step 8 : Reserve the device which is already connected using CLI in step 2.
		//**************************************************************************//
		strStepDescription = "Verify that device get reserved for Repetition Never.";
		strExpectedResult = "Tester should be able to create reservation with Repetition Never.";
		isEventSuccessful=RSVD_CreateReservationNever(devicename,true);
		if (isEventSuccessful)
		{
			strActualResult = "Tester is able to create reservation with repetition Never.";
		}
		else
		{
			strActualResult = "Reserve Devices---" + strErrMsg_GenLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 9 : Go to Device Index page.
		//**************************************************************************//
		isEventSuccessful=GoToDevicesPage();
		
		//**************************************************************************//
		// Step 10 : Search for the device which we have connected using CLI and verify Reboot button is not available.
		//**************************************************************************//
		strStepDescription = "Verify that Reboot button not available.";
		strExpectedResult = "Reboot button should not displayed.";
		if(isEventSuccessful)
		{
			searchDevice(devicename, "devicename");
			selectFirstDeviceChk_DI();
			waitForPageLoaded();
			PerformAction("btnRebootDevice", Action.WaitForElement,"4");
			isEventSuccessful=PerformAction("btnRebootDevice", Action.isNotDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Release button is not displayed.";
			}
			else
			{
				strActualResult = "Release Devices---" + strErrMsg_GenLib;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
			
		// Step 11: Release the device
		ExecuteCLICommand("release", "Android", "", "", devicename, "","","" );
		Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
	}

}
