package com.test.java;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-1234, QA-1554
 */
public class _603_Reserved_devices_displayed_as_Reserved_to_other_users_for_the_reserved_time_period extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ,EmailAddress , Password;
	private String strexpectedResult = "" , strActualResult , starttime,endTime;
	DateFormat dateFormat;
	Calendar objCalendar;
	Object[] values = new Object[2];
	
	
	
	public final void testScript() 
	{
		// Step 1
		isEventSuccessful = Login(); 
		
		//Step  - Delete All Reservations
		GoToReservationsPage();
		RSVD_DeleteAllReservations();
		GoToDevicesPage();
		
		// Step 2
		isEventSuccessful = selectStatus("Available");
		
		// Step 3
		waitForPageLoaded();
		values = AvailableDevices_DIpage();
		isEventSuccessful  = (boolean) values[0] ;
		devicesSelected =  (ArrayList) values[1] ;
		    
		// Step 4
		strStepDescription = "Create reservation for the device on reservation page";
		strExpectedResult = "Reservation should be created Successfully for device." ;
		if(GoToReservationsPage())
		{
			if(GoToCreateReservationPage())
			{
				isEventSuccessful=  RSVD_CreateReservationNever(devicesSelected.get(0),true);
				if(isEventSuccessful)
				{
					strActualResult = "Successfully created reservation for." + devicesSelected.get(0);
				}	
				else
				{
					strActualResult = "Could not created reservation for." + devicesSelected.get(0);
				}	
			}
			else
			{
				strActualResult = "Could not clicked on create reservation button.";
			}
		}
		else
		{
			strActualResult = "Could not navigated to reservation page" ;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	    
		// Step 5
		isEventSuccessful = Logout();
	      
		//**************************************************************************//
		// Step 6 : Login again as tester.
		//**************************************************************************//
		EmailAddress = dicCommon.get("testerEmailAddress");
		Password = dicCommon.get("testerPassword");
		strStepDescription = "Login to deviceConnect with valid test user.";
		strExpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login(EmailAddress, Password);
		
		// Step 7
		isEventSuccessful= selectStatus("In Use");
		waitForPageLoaded();
		isEventSuccessful = searchDevice(devicesSelected.get(0), "devicename");
		waitForPageLoaded();
		String userName = GetTextOrValue("//div[@class='status-description-row'][2]", "text");
		strStepDescription = "Reserved_devices_displayed_as_Reserved_to_other_users_for_the_reserved_time_period ";
		strExpectedResult = "Reserved_devices_should be displayed_as_Reserved_to_other_users_for_the_reserved_time_period ";
		if(userName.contains("Reserved"))
		{
			strActualResult = "Reserved_devices_is displayed_as_Reserved_to_other_users_for_the_reserved_time_period ";
			isEventSuccessful = true;
		}
		else
		{
			strActualResult = "Reserved_devices_is not displayed_as_Reserved_to_other_users_for_the_reserved_time_period ";
			isEventSuccessful = false;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		
	}
}
