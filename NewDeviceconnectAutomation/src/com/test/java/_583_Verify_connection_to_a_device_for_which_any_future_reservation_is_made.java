package com.test.java;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: Last week of January
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-564
 */
public class _583_Verify_connection_to_a_device_for_which_any_future_reservation_is_made  extends ScriptFuncLibrary

{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	Object[] values = new Object[6];
	private String strstepDescription = "",devicename;
	private String strexpectedResult = "";
	private String strActualResult = "",starttime,endTime;
	DateFormat dateFormat;
	Calendar objCalendar;
	
	public final void testScript() throws IOException
	
	{

		// Step 1 - Login to deviceConnect//
		isEventSuccessful = Login();

		//Step  - Delete All Reservations
		GoToReservationsPage();
		RSVD_DeleteAllReservations();
		GoToDevicesPage();
		
		// Step 2: Get the first Available device name.
		values =  GetDeviceDetails(1,"devicename","list","Available");
		isEventSuccessful  = (boolean) values[0] ;
		devicename = (String) values[1];

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
		
		//Step 
		strStepDescription = "Verify that device get reserved for Repetition Never.";
		strExpectedResult = "Tester should be able to create reservation with Repetition Never.";
		if(GoToReservationsPage())
		{
			if(GoToCreateReservationPage())
			{
				isEventSuccessful=  RSVD_CreateReservationNever(devicename,endTime,starttime,true);
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
			      
		// Step  : Go to Device Index page.
		isEventSuccessful =  GoToDevicesPage();
				
		//**************************************************************************//
		// Step  : Search for the device which we have created reservation.
		//**************************************************************************//
		strStepDescription = "Verify that device is available to connect.";
		strExpectedResult = "Device should be available to connect after reserving device for future.";
		if(isEventSuccessful)
		{
			isEventSuccessful = searchDevice(devicename, "devicename");
			if(isEventSuccessful)
			{
				values = ExecuteCLICommand("run", "","","",devicename,"");
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
		}
   
	   //**************************************************************************//
	   // Step  : Release device.
	   //**************************************************************************//
	   ExecuteCLICommand("release", "", "", "", devicename, "","","" );
	   Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
	}	
	
}
