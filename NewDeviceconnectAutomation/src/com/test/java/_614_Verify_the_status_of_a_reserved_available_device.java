package com.test.java;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-1050, QA-702
 */
public class _614_Verify_the_status_of_a_reserved_available_device extends ScriptFuncLibrary

{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	Object[] Values = new Object[5]; 
	private String strActualResult = "",EmailAddress, Password , starttime,endTime;
	DateFormat dateFormat;
	Calendar objCalendar;
	
	public final void testScript()
	{
		
		// Step 1 - Login to deviceConnect//
		isEventSuccessful = Login();
		   
		//Step  - Delete All Reservations
		GoToReservationsPage();
		RSVD_DeleteAllReservations();
		
		
		isEventSuccessful = GoToDevicesPage();
		isEventSuccessful = selectStatus("Available");
		String DeviceName = GetTextOrValue(dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(1)).toString()) , "text");
		
		strStepDescription = "Create reservation for the device on reservation page";
		strExpectedResult = "Reservation should be created Successfully for." + DeviceName;
		
		//Step 3 : Go to reservation page and create non recurring reservation.
		
		dateFormat = new SimpleDateFormat("H:mm");
		objCalendar = Calendar.getInstance();
		starttime = dateFormat.format(objCalendar.getTime());
		String [] temp= starttime.split(":");
		int t = Integer.valueOf(temp[0]) + 1 ;
		int t1 = Integer.parseInt(temp[0]) + 1 ;
		temp[0] = String.valueOf(t);
		temp[0] = String.valueOf(t1);
		if(Integer.parseInt(temp[1])<30)
		{
			starttime=get12HourTime_in_Format(temp[0])+":"+"00";
			endTime=get12HourTime_in_Format(temp[0])+":"+"30";
		}
		else
		{
			starttime=get12HourTime_in_Format(temp[0])+":"+"30";
			endTime=(get12HourTime_in_Format(temp[0])+1)+":"+"00";
		}
		
		
		
		strStepDescription = "Verify that device get reserved for Repetition Never.";
		strExpectedResult = "Tester should be able to create reservation with Repetition Never.";
		
		if(GoToReservationsPage())
		{
			if(GoToCreateReservationPage())
			{
				isEventSuccessful=  RSVD_CreateReservationNever(DeviceName,endTime,starttime,true);
				if(isEventSuccessful)
				{
					strActualResult = "Tester is able to create reservation with repetition Never.";
				}
				else
				{
					strActualResult = "Reserve Devices---" + strErrMsg_GenLib;
				}
			}
			else
			{
				throw new RuntimeException("Could not clicked on create reservation button.");
			}
		}
		else
		{
			throw new RuntimeException("Could not navigated to reservation page");
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		// Step
		isEventSuccessful = Logout();
		
		//**************************************************************************//
		// Step  : Login again as tester.
		//**************************************************************************//
		EmailAddress = dicCommon.get("testerEmailAddress");
		Password = dicCommon.get("testerPassword");
		
		strStepDescription = "Login to deviceConnect with valid test user.";
		strExpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login(EmailAddress, Password);
		
		
		// Step
		isEventSuccessful= selectStatus("Available");
		
		// Step
		isEventSuccessful = searchDevice(DeviceName, "devicename");
		String devciestatus = GetTextOrValue("//div[@class='status-description-row'][2]", "text");
		
		// Step
		strStepDescription = "Verify the_status_of_a_reserved_in_use_device";
		strExpectedResult = "the_status_of_a_reserved_in_use_device should be displayed as 'Available' ";
		System.out.println(devciestatus);
		if(devciestatus.contains("Available"))
		{
			strActualResult = "the_status_of_a_reserved_in_use_device is displayed as 'Available' "; 
			isEventSuccessful = true;
		}
		else
		{
			strActualResult = "the_status_of_a_reserved_in_use_device is not displayed as 'Available' "; 
			isEventSuccessful = false;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
			
			
	}
}
