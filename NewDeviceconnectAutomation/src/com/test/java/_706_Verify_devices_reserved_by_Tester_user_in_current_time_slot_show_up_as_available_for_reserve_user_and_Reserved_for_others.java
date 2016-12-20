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
 * Jira Test Case Id: QA-1520
 */
public class _706_Verify_devices_reserved_by_Tester_user_in_current_time_slot_show_up_as_available_for_reserve_user_and_Reserved_for_others extends ScriptFuncLibrary
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
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");
		String strActualResult = "";
		String devicename;
		Object[] values = new Object[6];
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with Tester user.
		//*************************************************************//  
		isEventSuccessful = LoginToDC(EmailAddress,Password);
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
                    	strActualResult = "Future Reservation got created for the device : " + devicename;
                    }
                    else
                    	strActualResult = "Future Reservation didn't get created for device : " + devicename;
                }
                else
                	strActualResult = "Create Reservation header not displayed.";
			}
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);	
		
		//*************************************************************//                     
		// Step 4 : Create reservation for current time slot for the same device
		//*************************************************************//
		strstepDescription = "Creation of Daily Reservation for current time slot";
		strexpectedResult = "Verify creation of Daily Reservation for current reservation time.";
		if(isEventSuccessful)
		{	
		    if(GoToCreateReservationPage())
			{
		    	isEventSuccessful=RSVD_CreateReservationNever(devicename,true);
		    	if (isEventSuccessful)
					strActualResult = "User is able to create Reservation for current time slot.";
				else
					strActualResult = "Reserve Devices---" + strErrMsg_GenLib;
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		    	
		//*************************************************************//                     
		// Step 5 : Device Status check
		//*************************************************************//
		strstepDescription = "Device Status Check";
		strexpectedResult = "Verify the status of the device for the reserved tester user : " + EmailAddress;
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToDevicesPage();
			if(isEventSuccessful)
			{
				isEventSuccessful = GoToSpecificDeviceDetailsPage(devicename);
				if(isEventSuccessful)
				{
					waitForPageLoaded();
					isEventSuccessful = PerformAction("lnkShowDetails_DeviceDetailsPage", Action.Click);
					if(isEventSuccessful)
					{
						waitForPageLoaded();
						isEventSuccessful = VerifyOnDeviceDetailsPage("Status ", "CONTAINS__" + "Available");
						if (isEventSuccessful)
						{
							reporter.ReportStep(strstepDescription, strexpectedResult, "Status on device details page is Available. ", isEventSuccessful);
						}
						else
						{
							strActualResult = strErrMsg_AppLib;
							reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult + " - Status on device details page is not Available ", isEventSuccessful);
						}	
					}
				}
			}
		}
		
		//*************************************************************//                     
		// Step 6 : Logout of dC
		//*************************************************************//
		if(isEventSuccessful)
			isEventSuccessful = LogoutDC();
		
		//*************************************************************//                     
		// Step 7 : Login using other credentials
		//*************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = Login();
			if(isEventSuccessful)
			{
				isEventSuccessful = GoToSpecificDeviceDetailsPage(devicename);
				if(isEventSuccessful)
				{
					isEventSuccessful = PerformAction("lnkShowDetails_DeviceDetailsPage", Action.Click);
					if(isEventSuccessful)
					{
						isEventSuccessful = VerifyOnDeviceDetailsPage("Status ", "CONTAINS__" + "Reserved");
						if (isEventSuccessful)
							reporter.ReportStep(strstepDescription, strexpectedResult, "Status on device details page is: Reserved", "Pass");
						else
						{
							strActualResult = strErrMsg_AppLib;
							reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult + "Status on device details page is not displayed as Reserved", "Fail");
						}	
					}
				}
			}
		}		
	}
}