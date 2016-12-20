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
 * Jira Test Case Id: QA-1571
 */
public class _704_Verify_validation_message_is_displayed_when_default_weekly_repeat_day_contradicts_with_the_device_reservation_dates extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;

	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private Object[] firstdeviceSelected = null;
	
	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		String strActualResult = "";
		String devicename ="";
		Object[] values = new Object[6];
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
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
		if(isEventSuccessful)
		{
		values =  GetDeviceDetails(1,"devicename","list","Available");
		isEventSuccessful  = (boolean) values[0] ;
		devicename = (String) values[1];
		GoToReservationsPage();
		}
		//*************************************************************//                     
		// Step 3 : Create a Monthly Reservation & verify if the projected schedule
		//*************************************************************//
		strstepDescription = "Creation of Weekly Reservation";
		strexpectedResult = "Verify creation of Weekly Reservation with default value results in Configuration error message.";
		if(isEventSuccessful)
		{	
		    if(GoToCreateReservationPage())
			{
		    	isEventSuccessful = PerformAction("eleCreateRsrvtnHeader", Action.isDisplayed);
                if (isEventSuccessful)
                {
                	isEventSuccessful = RSVD_SelectDevice(devicename, true );
                    if (isEventSuccessful)
                    {
                    	isEventSuccessful = RSVD_SelectReservationType("Weekly");
                    	if(isEventSuccessful)
                    	{
                    		Calendar c = Calendar.getInstance();
                			SimpleDateFormat format = new SimpleDateFormat("E");
                	        String FormatedTodayDate = format.format(c.getTime());  // returns new date object, number of hours provided (in the future)
                	        String Day = FormatedTodayDate.substring(0, 2);
                	        if(Day.equals("Mo"))
                	        {
                	        	String EnddateAfteradding = addDayofWeek(null, 1,"E");
                	        	String[] weekday = {EnddateAfteradding.substring(0, 2),Day};
                	        	isEventSuccessful = RSVD_SelectWeekDay(weekday);
                	        }	
            			
                    		String ProjectedSchedule = GetTextOrValue("eleProposedConfigurationWarning_CreateReservation","text");
                    		if(ProjectedSchedule !=null)
                    		{
                    			if(ProjectedSchedule.contains("The current configuration results in no reservations."))
                    				strActualResult = "Weekly Reservation creation usign default values results in Configuration message : " + ProjectedSchedule;
                    		}
                    		else
                    			strActualResult = strErrMsg_GenLib;
                    	}
                    }
                    else
                    	strActualResult = strErrMsg_AppLib;
                }
                else
                	strActualResult = "Unable to find Reservations header." + strErrMsg_GenLib;
			}
		}	
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}
}