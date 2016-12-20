package com.test.java;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*
 * Jira Test Case Id: QA-478
 */
public class _792_Verify_Reboot_button_not_available_to_tester_user_if_reserved_device_InUse_by_other_user extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;

	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",outputText="",deviceName="";
	private Object[] firstdeviceSelected = null;
	Object[] Values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");
		String strActualResult = "";
		String ProjectedSchedule = "";
		String devicename;
		Object[] values = new Object[6];
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with Tester user.
		//*************************************************************//  
		isEventSuccessful = Login();
		if(!isEventSuccessful)
			return;
		
		//*************************************************************//                     
		// Step 2: Get the first Available device name.
		//*************************************************************//    
		strstepDescription = "Get the first Available device name";
		strexpectedResult = "Get the first Available device name & store it in deviceName";
	
		values =  GetDeviceDetails(1,"devicename","list","Available");
		isEventSuccessful  = (boolean) values[0] ;
		devicename = (String) values[1];

		//*************************************************************//                     
		// Step 3: Connect device using Admin User.
		//*************************************************************//    		
		strstepDescription = "Connect using Admin user";
		strexpectedResult = "Verify Admin user is able to connect to the device.";
	
		if (isEventSuccessful)
		{	
		     Values = ExecuteCLICommand("run", "",dicCommon.get("EmailAddress"), dicCommon.get("Password"), devicename,"");
	     	 isEventSuccessful = (boolean)Values[4];
	     	 outputText=(String)Values[1];
	     	 deviceName=(String)Values[3];
	     	 if (isEventSuccessful && outputText.contains("opened"))
	     	 {
	     		 strActualResult = "web Viewer launched after connecting to device:  " + Values[3] + " & processfound : " +  Values[1];
	     	 }
	     	 else
	     	 {
	     		 strActualResult = "Web Viewer not launched, User - " + EmailAddress + " was not able to connect";
	     	 }
		}		
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);
		
		//PerformAction("browser",Action.Refresh);
		PerformAction("",Action.WaitForElement);
		waitForPageLoaded();
		//*************************************************************//                     
		// Step 4 : logout & login using other user
		//*************************************************************//
		strstepDescription = "Logout & login using other User";
		strexpectedResult = "Verify User logged out & logged in.";
		if (isEventSuccessful)
		{	
			isEventSuccessful = LogoutDC();
			if(isEventSuccessful)
			{
				isEventSuccessful = LoginToDC(EmailAddress,Password );
				if(isEventSuccessful)
				{
					strActualResult = "User - " + EmailAddress + " logged in successfully.";
				}
				else
					strActualResult = "User not logged in." + strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);


		//*************************************************************//                     
		// Step 5 : Create reservation for current time slot for the same device using Tester User
		//*************************************************************//
		strstepDescription = "Creation of Daily Reservation for current time slot by Tester User";
		strexpectedResult = "Verify creation of Daily Reservation for current reservation time by Tester User.";
		if(isEventSuccessful)
		{	
			if(GoToReservationsPage())
			{
				if(GoToCreateReservationPage())
				{
					isEventSuccessful = RSVD_CreateReservationNever(devicename, true );
					if (isEventSuccessful)
					{	
						ProjectedSchedule = dicOutput.get("ProjectedSchedule");
						if(ProjectedSchedule!=null)
						{
							strActualResult = "Device Reservation got created for time : " + ProjectedSchedule;
						}
						else
							strActualResult = "Device Reservation not got created for time : " + ProjectedSchedule;
					}
					else
						strActualResult = strErrMsg_AppLib;
				}
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		
		//*************************************************************//                     
		// Step 6 : Verify Reboot button is not displayed for Tester user for the reserved device
		//*************************************************************//
		strstepDescription = "Reboot button on reserved device details page";
		strexpectedResult = "Verify Reboot button is not available on reserved device details page.";
		if (isEventSuccessful)
		{	
			isEventSuccessful = GoToDevicesPage();
			if(isEventSuccessful)
			{
				isEventSuccessful = GoToSpecificDeviceDetailsPage(devicename);
				if(isEventSuccessful)
				{
					isEventSuccessful = PerformAction("btnReboot",Action.isNotDisplayed);
					if(isEventSuccessful)
					{
						strActualResult = "Reboot button is not displayed for Tester User.";
					}
					else
						strActualResult = "Reboot button is displated for tester user.";
				}
			}
		}
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);
		
		//*************************************************************//                     
		// Step 7 : logout & login using Admin user
		//*************************************************************//
		strstepDescription = "Logout & login using other User";
		strexpectedResult = "Verify User logged out & logged in.";
		if (isEventSuccessful)
		{	
			isEventSuccessful = LogoutDC();
			if(isEventSuccessful)
			{
				isEventSuccessful = Login();
				if(isEventSuccessful)
				{
					strActualResult = "Admin User logged in successfully.";
				}
				else
					strActualResult = "User not logged in." + strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 8 : Release device.
		//**************************************************************************//
		Values = ExecuteCLICommand("release", "", dicCommon.get("EmailAddress"), dicCommon.get("Password"), devicename, "","","" );
		isEventSuccessful = (boolean)Values[2];
		if(isEventSuccessful)
			strActualResult = "Device Released successfully. ";
		else
			strActualResult = "Device didn't release successfully. " + strErrMsg_AppLib;
		reporter.ReportStep("Verify device is released" , "Device should get released.", strActualResult, isEventSuccessful);

		
		//*************************************************************//                     
		// Step 9 : Cancel Reservation
		//*************************************************************//	
		GoToReservationsPage();
		isEventSuccessful = RSVD_DeleteReservationbydeviceName(devicename);
		if(isEventSuccessful)
			strActualResult = "Reservation got canceled.";
		else
			strActualResult = "Not able to cancel reservation - " + strErrMsg_AppLib;
		reporter.ReportStep("Cancel Reservation", "User should be able to cancel reservation", strActualResult, isEventSuccessful);

	}
}