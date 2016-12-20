package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1300
 */

public class _459_Verify_that_a_tester_user_can_release_the_In_Use_devices_after_reserving_the_devices extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", devicename = "";
	private String xpath = "";
	Object[] Values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
						
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with " + EmailAddress, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
		
		//Step  - Delete All Reservations
		GoToReservationsPage();
		RSVD_DeleteAllReservations();
		GoToDevicesPage();

		//**************************************************************************//
		// Step 2 : Connect to an android device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("run", "Android", EmailAddress, Password);
	     	  isEventSuccessful = (boolean)Values[2];
	     	 devicename=(String)Values[3];
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
		isEventSuccessful = LogoutDC();
		

		//**************************************************************************//
		// Step 4 : Login again as tester.
		//**************************************************************************//
		EmailAddress = dicCommon.get("testerEmailAddress");
		Password = dicCommon.get("testerPassword");

		strstepDescription = "Login to deviceConnect with valid test user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);

		//**************************************************************************//
		// Step 5 : Select status 'In Use' and platform android and check warning message not displayed.
		//**************************************************************************//
		isEventSuccessful=selectStatus("In Use");
		isEventSuccessful=selectPlatform("Android");
		isEventSuccessful=!(VerifyMessage_On_Filter_Selection());
		
		//**************************************************************************//
		// Step 6 : Search for the device which we have connected using CLI and verify release button not available.
		//**************************************************************************//
		if(isEventSuccessful)
		{
			searchDevice(devicename, "devicename");
			selectFirstDeviceChk_DI();
			strstepDescription = "Verify that release button not available.";
			strexpectedResult = "Release button should not displayed.";
			isEventSuccessful=PerformAction("btnRelease_Devices", Action.isNotDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Release button is not displayed.";
			}
			else
			{
				strActualResult = "Release Devices---" + strErrMsg_GenLib;
			}

			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
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
		strstepDescription = "Verify that device get reserved for Repetition Never.";
		strexpectedResult = "Tester should be able to create reservation with Repetition Never.";
		isEventSuccessful=RSVD_CreateReservationNever(devicename,true);
		if (isEventSuccessful)
		{
			strActualResult = "Tester is able to create reservation with repetition Never.";
		}
		else
		{
			strActualResult = "Reserve Devices---" + strErrMsg_GenLib;
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 9 : Go to Device Index page.
		//**************************************************************************//
		isEventSuccessful=GoToDevicesPage();
		
		//**************************************************************************//
		// Step 9 : Search for the device which we have connected using CLI and verify release button is available now.
		//**************************************************************************//
		if(isEventSuccessful)
		{
			searchDevice(devicename, "devicename");
			selectFirstDeviceChk_DI();
			strstepDescription = "Verify that release button is available.";
			strexpectedResult = "Release button should displayed.";
			PerformAction("btnRelease_Devices", Action.WaitForElement,"4");
			isEventSuccessful=PerformAction("btnRelease_Devices", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Release button is displayed.";
			}
			else
			{
				strActualResult = "Release Devices---" + strErrMsg_GenLib;
			}

			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 10 : Release the selected device.
		//**************************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Verify that device get released.";
			strexpectedResult = "Device should get released.";
			PerformAction("btnRelease_Devices", Action.Click);
			isEventSuccessful=PerformAction("btnReleaseDevices", Action.Click);
			if(isEventSuccessful)
			{
				strActualResult = "Device released.";
			}
			else
			{
				strActualResult = "Release Devices---" + strErrMsg_GenLib;
			}

			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
		}
	}
}