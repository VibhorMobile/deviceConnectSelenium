package com.test.java;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*
 * Jira Test Case Id: QA-1572
 */
public class _703_Verify_monthly_reservation_does_not_result_in_creation_of_redundant_today_reservation extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;

	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", deviceName = "";
	private Object[] firstdeviceSelected = null;
	
	public final void testScript() throws InterruptedException, IOException
	{
		String strActualResult = "";
		String devicename = "";
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
		strstepDescription = "Creation of Monthly Reservation";
		strexpectedResult = "Verify creation of Monthly Reservation without any error message.";
		String ProjectedScheduleText ="";
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
			
		    	Object[] ReservationDetails = RSVD_CreateQuickReservationMonthly(devicename,FormatedTodayDate,EnddateAfteradding);
		    	
		    	ProjectedScheduleText = (String) ReservationDetails[1];
		    	isEventSuccessful = (boolean) ReservationDetails[0];
		    	if(isEventSuccessful && ProjectedScheduleText!=null)
		    		strActualResult = "Monthly Reservation got created without any error. ";
		    	else
		    		strActualResult = strErrMsg_AppLib;
			}
		}	
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//*************************************************************//                     
		// Step 4 : Verify the created reservation on Reservations Index page
		//*************************************************************//
		strstepDescription = "Reservation Index page verification.";
		strexpectedResult = "Verify Monthly reservation doesn't result in redundant today's reservation.";
		if(isEventSuccessful)
		{
			try
			{
				List <WebElement> ReservationsCreatedList = getelementsList(dicOR.get("eleReservationTableCol_ReservationIndex").replace("_COLUMNHEADER__", "Reservation"));
				int Counter=0;
				for (WebElement webElement : ReservationsCreatedList)
				{
					if(webElement.getText().contains(ProjectedScheduleText))
					{
						List <WebElement> ReservationDeviceList = getelementsList(dicOR.get("eleReservationTableCol_ReservationIndex").replace("__COLUMNHEADER__", "Device"));
						for (WebElement webElement2 : ReservationDeviceList) 
						{
							if(webElement2.getText().contains(devicename))
							{
								Counter++;		
							}
						}
					}
				}
				if(Counter==0)
				{
					strActualResult = "Entry for the Monthly reservtion created is not displayed : CounterValue : " + Counter;
					isEventSuccessful=false;
				}
				else
				{
					strActualResult = "Entry for the Monthly reservtion created displayed : CounterValue : " + Counter;
					isEventSuccessful=true;
				}
			}
			catch(ElementNotFoundException e)
			{
				strActualResult = "Unable to get the webelements list : ";
				reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			}
			finally
			{
				reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);		
			}
		}	
	}
}