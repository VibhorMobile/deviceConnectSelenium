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

public class _809_Verify_Recurring_non_recurring_reservation_can_be_created_by_tester_user extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;

	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",outputText="";
	private Object[] firstdeviceSelected = null;
	Object[] Values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");
		String strActualResult = "";
		String ProjectedSchedule = "";
		String devicenameAvailable="",devicenameInUse="",devicenameOffline="",devicename="";
		//String devicenameAvailablerec = "", devicenameInUserec="", devicenameOfflinerec=""
		Object[] values = new Object[6];
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with Tester user.
		//*************************************************************//  
		isEventSuccessful = LoginToDC(EmailAddress,Password );
		if(!isEventSuccessful)
			return;
		
		//*************************************************************//                     
		// Step 2: Get the first Available device name.
		//*************************************************************//    
		strstepDescription = "Get the first Available device name";
		strexpectedResult = "Get the first Available device name & store it in deviceName";
	
		isEventSuccessful = selectStatus_DI("Available");
		if(isEventSuccessful)
		{			
			values =  GetDeviceDetails(1,"devicename","list","Available");
			isEventSuccessful  = (boolean) values[0] ;
			devicename = (String) values[1];
		}
		
		//*************************************************************//                     
		// Step 6 : Connect device using CLI
		//*************************************************************//
		strstepDescription = "Connect the Reserved device using the other user";
		strexpectedResult = "Device should not get connected using CLI.";

		Values = ExecuteCLICommand("client", "", dicCommon.get("EmailAddress"), dicCommon.get("Password"), devicename, "web");
		isEventSuccessful = (boolean)Values[4];
		outputText=(String)Values[1];
		devicenameInUse=(String)Values[3];			
		if (isEventSuccessful && outputText.contains("opened"))
			strActualResult = "Device Viewer launched after connecting to device:  " + Values[3] + " & processfound : " +  Values[1];
		else
		{
			strActualResult = "devieviewer didnt launch.";
			isEventSuccessful = true;
		}
		//GoToReservationsPage();

		//*************************************************************//                     
		// Step 2: Get the first Available device name.
		//*************************************************************//    
		strstepDescription = "Get the first Available device name";
		strexpectedResult = "Get the first Available device name & store it in deviceName";

		isEventSuccessful = GoToDevicesPage();
		if(isEventSuccessful)
		{	
			isEventSuccessful = selectStatus_DI("Available");
			if(isEventSuccessful)
			{			
				values =  GetDeviceDetails(1,"devicename","list","Available");
				isEventSuccessful  = (boolean) values[0] ;
				devicenameAvailable = (String) values[1];
			}
		}
		//*************************************************************//                     
		// Step 3 : Create reservation for current time slot for the same device using Tester User
		//*************************************************************//
		strstepDescription = "Creation of Daily Reservation for current time slot by Tester User";
		strexpectedResult = "Verify creation of Daily Reservation for current reservation time by Tester User.";
		GoToReservationsPage();
		if(isEventSuccessful)
		{	
			if(GoToCreateReservationPage())
			{
				isEventSuccessful = RSVD_CreateReservationNever(devicenameAvailable, true );
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
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//                     
		// Step 5 : Create reservation for current time slot for the same device using Tester User
		//*************************************************************//
		strstepDescription = "Creation of Daily Reservation for current time slot by Tester User";
		strexpectedResult = "Verify creation of Daily Reservation for current reservation time by Tester User.";
		if(isEventSuccessful)
		{	
			if(GoToCreateReservationPage())
			{
				isEventSuccessful = RSVD_CreateReservationNever(devicenameInUse, true );
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
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//                     
		// Step 6: Get the first in use device name.
		//*************************************************************//    
		strstepDescription = "Get the first Available device name";
		strexpectedResult = "Get the first Available device name & store it in deviceName";
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToDevicesPage();
			{
				isEventSuccessful = selectStatus_DI("Offline");
				if(isEventSuccessful)
				{
					values =  GetDeviceDetails(1,"devicename","list","Offline");
					isEventSuccessful  = (boolean) values[0] ;
					devicenameOffline = (String) values[1];
					GoToReservationsPage();
				}
			}
		}
		
		//*************************************************************//                     
		// Step 5 : Create reservation for current time slot for the same device using Tester User
		//*************************************************************//
		strstepDescription = "Creation of Daily Reservation for current time slot by Tester User";
		strexpectedResult = "Verify creation of Daily Reservation for current reservation time by Tester User.";
		if(isEventSuccessful)
		{	
			if(GoToCreateReservationPage())
			{
				isEventSuccessful = RSVD_CreateReservationNever(devicenameOffline, true );
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
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//                     
		// Step 7 : Cancel Reservation
		//*************************************************************//	
		GoToReservationsPage();
		isEventSuccessful = RSVD_DeleteReservationbydeviceName(devicenameAvailable);
		if(isEventSuccessful)
		{
			GoToReservationsPage();
			isEventSuccessful = RSVD_DeleteReservationbydeviceName(devicenameInUse);
			if(isEventSuccessful)
			{
				GoToReservationsPage();
				isEventSuccessful = RSVD_DeleteReservationbydeviceName(devicenameOffline);
				if(isEventSuccessful)
				{
					strActualResult = "All Reservations got canceled.";
				}
				else
					strActualResult = "Offline device Reservation didnt get canceled.";
			}
			else
				strActualResult = "In Use device Reservation didnt get canceled.";
		}
		else
			strActualResult = "Available device Reservation didnt get canceled.";
		reporter.ReportStep("Cancel Reservation", "User should be able to cancel reservation", strActualResult, isEventSuccessful);

		//*************************************************************//                     
		// Step 3 : Create a Monthly Reservation & verify if the projected schedule
		//*************************************************************//
		strstepDescription = "Creation of Monthly Reservation";
		strexpectedResult = "Verify creation of Monthly Reservation without any error message.";
		String ProjectedScheduleText1 ="";
		isEventSuccessful = GoToReservationsPage();
		if(isEventSuccessful)
		{	
			isEventSuccessful = GoToCreateReservationPage();
			if(isEventSuccessful)
			{
				Calendar c = Calendar.getInstance();
				Date newDate = c.getTime();
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
				String FormatedTodayDate = format.format(c.getTime());  // returns new date object, number of hours provided (in the future)
				String EnddateAfteradding = addDays(null, 30,"MM/dd/yy");

				Object[] ReservationDetails = RSVD_CreateQuickReservationMonthly(devicenameInUse,FormatedTodayDate,EnddateAfteradding);

				ProjectedScheduleText1 = (String) ReservationDetails[1];
				isEventSuccessful = (boolean) ReservationDetails[0];
				if(isEventSuccessful && ProjectedScheduleText1!=null)
					strActualResult = "Monthly Reservation for in use device got created without any error. ";
				else
					strActualResult = strErrMsg_AppLib;
			}
		}	
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//*************************************************************//                     
		// Step 3 : Create a Monthly Reservation & verify if the projected schedule
		//*************************************************************//
		strstepDescription = "Creation of Monthly Reservation";
		strexpectedResult = "Verify creation of Monthly Reservation without any error message for offline status device.";
		String ProjectedScheduleText2 ="";
		isEventSuccessful = GoToReservationsPage();
		if(isEventSuccessful)
		{	
			isEventSuccessful = GoToCreateReservationPage();
			if(isEventSuccessful)
			{
				Calendar c = Calendar.getInstance();
				Date newDate = c.getTime();
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
				String FormatedTodayDate = format.format(c.getTime());  // returns new date object, number of hours provided (in the future)
				String EnddateAfteradding = addDays(null, 30,"MM/dd/yy");

				Object[] ReservationDetails = RSVD_CreateQuickReservationMonthly(devicenameOffline,FormatedTodayDate,EnddateAfteradding);

				ProjectedScheduleText2 = (String) ReservationDetails[1];
				isEventSuccessful = (boolean) ReservationDetails[0];
				if(isEventSuccessful && ProjectedScheduleText2!=null)
					strActualResult = "Monthly Reservation for offline device got created without any error. ";
				else
					strActualResult = strErrMsg_AppLib;
			}
		}	
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//                     
		// Step 7 : Cancel Reservation
		//*************************************************************//	
		isEventSuccessful = GoToReservationsPage();
		if(isEventSuccessful)
		{
			GoToReservationsPage();
			isEventSuccessful = RSVD_DeleteReservationbydeviceName(devicenameInUse);
			if(isEventSuccessful)
			{
				GoToReservationsPage();
				isEventSuccessful = RSVD_DeleteReservationbydeviceName(devicenameOffline);
				if(isEventSuccessful)
				{
					strActualResult = "All Reservations got canceled.";
				}
				else
					strActualResult = "Offline device Reservation didnt get canceled.";
			}
			else
				strActualResult = "In Use device Reservation didnt get canceled.";
		}
		else
			strActualResult = "Unabe to navigate to reservations page.";
		reporter.ReportStep("Cancel Reservation", "User should be able to cancel reservation", strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 10 : Release device.
		//**************************************************************************//
		Values = ExecuteCLICommand("release", "iOS", EmailAddress, Password, devicenameInUse, "","","" );
		isEventSuccessful = (boolean)Values[2];
		if(isEventSuccessful)
			strActualResult = "Device Released successfully. ";
		else
			strActualResult = "Device didn't release successfully. " + strErrMsg_AppLib;
		reporter.ReportStep("Verify device is released" , "Device should get released.", strActualResult, isEventSuccessful);


	}
}