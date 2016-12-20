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
 * Jira Test Case Id: QA-1172
 */
public class _808_Verify_Release_button_is_not_available_for_reserved_device_by_other_user extends ScriptFuncLibrary
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
		isEventSuccessful = LoginToDC(EmailAddress,Password );
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
		// Step 3 : Create reservation for current time slot for the same device using Tester User
		//*************************************************************//
		strstepDescription = "Creation of Daily Reservation for current time slot by Tester User";
		strexpectedResult = "Verify creation of Daily Reservation for current reservation time by Tester User.";
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
				isEventSuccessful = Login();
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
		// Step 5 : Verify Release button is not displayed for Tester user for the reserved device
		//*************************************************************//
		strstepDescription = "Release button on reserved device details page";
		strexpectedResult = "Verify Release button is not available on reserved device details page.";
		if (isEventSuccessful)
		{	
			isEventSuccessful = GoToDevicesPage();
			if(isEventSuccessful)
			{
				isEventSuccessful = selectStatus_DI("In Use");
				if(isEventSuccessful)
				{
					waitForPageLoaded();
					String xpathvalue = getValueFromDictAndReplace(dicOR,"btnTogglebydevice_DevicesPage","__DEVICENAME__",devicename);
					isEventSuccessful = PerformAction(xpathvalue,Action.isDisplayed);
					if(isEventSuccessful)
					{
						waitForPageLoaded();
						isEventSuccessful = PerformAction(xpathvalue,Action.Click);
						if(isEventSuccessful)
						{
							String xpathvalue1 = getValueFromDictAndReplace(dicOR,"btnToggleValue_DevicesPage","__BUTTONTOVERIFY__","Release");
							isEventSuccessful = PerformAction(xpathvalue1,Action.isNotDisplayed);
							if(isEventSuccessful)
								strActualResult = "Release button is not displayed for the reserved device.";
							else
								strActualResult = "Release button is displayed for the reserved device.";
						}
						else
							strActualResult = "Unable to click the dropdown button.";
					}
					else
						strActualResult = "Unable to locate the dropdown button.";
				}
				else
				{
					strActualResult = "Not able to select the in use status.";
				}
			}
		}
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);
			
		
		//*************************************************************//                     
		// Step 6 : Verify Release button is not displayed for Tester user for the reserved device
		//*************************************************************//
		strstepDescription = "Release button on reserved device details page";
		strexpectedResult = "Verify Release button is not available on reserved device details page.";
		if (isEventSuccessful)
		{	
			isEventSuccessful = GoToSpecificDeviceDetailsPage(devicename);
			if(isEventSuccessful)
			{
				isEventSuccessful = PerformAction("btnRelease",Action.isNotDisplayed);
				if(isEventSuccessful)
				{
					strActualResult = "Release button is not displayed for reserved device by tester User.";
				}
				else
					strActualResult = "Release button is displayed for reserved device by tester user.";
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