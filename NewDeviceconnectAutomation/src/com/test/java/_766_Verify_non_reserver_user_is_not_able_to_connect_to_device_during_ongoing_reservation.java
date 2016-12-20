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
 * Jira Test Case Id: QA-824
 */
public class _766_Verify_non_reserver_user_is_not_able_to_connect_to_device_during_ongoing_reservation extends ScriptFuncLibrary
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
		
		//Step  - Delete All Reservations
		GoToReservationsPage();
		RSVD_DeleteAllReservations();
		GoToDevicesPage();
		//*************************************************************//                     
		// Step 2: Get the first Available device name.
		//*************************************************************//    
		strstepDescription = "Get the first Available device name";
		strexpectedResult = "Get the first Available device name & store it in deviceName";
	
		values =  GetDeviceDetails(1,"devicename","list","Available");
		isEventSuccessful  = (boolean) values[0] ;
		devicename = (String) values[1];
		GoToReservationsPage();

		//*************************************************************//                     
		// Step 3 : Create reservation for current time slot for the same device
		//*************************************************************//
		strstepDescription = "Creation of Daily Reservation for current time slot";
		strexpectedResult = "Verify creation of Daily Reservation for current reservation time.";
		if(isEventSuccessful)
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
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		
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
		
		//**************************************************************************//
		// Step 5 : Verify other user is not able to connect
		//**************************************************************************//	
		strstepDescription = "Connect using other user";
		strexpectedResult = "Verify other user is not able to connect to the device which is reserved by other user.";
	
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("client", "", EmailAddress, Password, devicename, "web");
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
	     	reporter.ReportStep("Verify web Viewer is not launched " , "User should not be able to connect device.", strActualResult, !isEventSuccessful);
		}		
		
		//*************************************************************//                     
		// Step 6 : logout & login admin user
		//*************************************************************//
		strstepDescription = "Logout & login using other User";
		strexpectedResult = "Verify User logged out & logged in.";
		if (!isEventSuccessful)
		{	
			isEventSuccessful = LogoutDC();
			if(isEventSuccessful)
			{
				isEventSuccessful = LoginToDC();
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
		// Step 7 : Cancel Reservation
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