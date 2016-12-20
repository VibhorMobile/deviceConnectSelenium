package com.test.java;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebElement;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*
 * Jira Test Case Id: QA-740
 */
public class _767_Verify_Next_Reservation_column_exist_with_upcoming_reservation_on_device_index_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;

	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",outputText="",deviceName="",ProjectedSchedule="";
	private Object[] firstdeviceSelected = null;
	Object[] Values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");
		String strActualResult = "";
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
		// Step 3 : Create a Daily Reservation & verify if it got created for future time
		//*************************************************************//
		strstepDescription = "Creation of Daily Reservation";
		strexpectedResult = "Verify creation of Daily Reservation for future time.";
		if(isEventSuccessful)
		{	
			if(GoToCreateReservationPage())
			{
				isEventSuccessful = PerformAction("eleCreateRsrvtnHeader", Action.isDisplayed);
				if (isEventSuccessful)
				{
					String StartAddTime = addTime(new Date(),2, "h:mm a");
					String EndAddTime = addTime(new Date(),3, "h:mm a");
					isEventSuccessful = RSVD_CreateReservationNever(devicename,"", StartAddTime, "", EndAddTime,true);
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
					/*if (isEventSuccessful)
					{
						strActualResult = "Future Reservation got created for the device : " + devicename;
					}
					else
						strActualResult = "Future Reservation didn't get created for device : " + devicename;*/
				}
				else
					strActualResult = "Create Reservation header not displayed.";
			}
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);	
		
		//*************************************************************//                     
		// Step 4 : Navigate to devices Index page & select 'Next Reservation'
		//*************************************************************//
		strstepDescription = "Selecting 'Next Reservation' checkbox";
		strexpectedResult = "Verify 'Next Reservation' checkbox lists the column on the device Index page.";
		if (isEventSuccessful)
		{	
	     	if(GoToDevicesPage())
	     	{
	     		isEventSuccessful = SelectColumn_Devices("Next Reservation");
	     		if(isEventSuccessful)
	     			strActualResult = "Able to select 'Next Reservation' column checkbox from the gear icon.";
	     		else
	     			strActualResult = strErrMsg_AppLib;
	     	}
		}
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);
		
		//**************************************************************************//
		// Step 5 : Verify if the Next Reservation is displayed on device Index page
		//**************************************************************************//	
		if(isEventSuccessful)
		{
			String XPATHdeviceNameCol = getValueFromDictAndReplace(dicOR, "eleDeviceTableCol_DevicesIndex", "__COLUMNHEADER__", "Status / Name");
			String XPATHNextReservationCol = getValueFromDictAndReplace(dicOR, "eleDeviceTableCol_DevicesIndex", "__COLUMNHEADER__", "Next Reservation");
			List <WebElement> NameStatusCol = getelementsList(XPATHdeviceNameCol);
			List <WebElement> NextReservation = getelementsList(XPATHNextReservationCol);

			for (WebElement webElement : NameStatusCol)
			{
				for (WebElement webElement2 : NextReservation) 
				{
					String Value = getAttribute(webElement, "title", "");
					if(Value.contains(devicename))
					{
						String Value2 = getAttribute(webElement2, "title", "");
						if(Value2==ProjectedSchedule || Value2.contains(ProjectedSchedule))
						{
							isEventSuccessful = true;
							strActualResult = "The Next Reservation shown is - " + Value2 + " in column for the device : " + devicename;
							break;
						}
						else
							isEventSuccessful = false;	
					}
					else
						isEventSuccessful = false;
					if(isEventSuccessful)
						break;
				}
				if(isEventSuccessful)
					break;
			}
		}
		else
			strActualResult = "Not able to select Next Reservation Column.";

		reporter.ReportStep("Verify Next Reservation is displayed." , "Next Reservation should be displayed in column for the device having reservation.", strActualResult, isEventSuccessful);
			
	}
}