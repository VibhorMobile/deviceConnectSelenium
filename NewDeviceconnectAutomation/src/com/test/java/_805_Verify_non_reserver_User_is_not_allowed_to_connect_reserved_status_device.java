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
import com.common.utilities.GenericLibrary.Action;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*
 * Jira Test Case Id: QA-91
 */
public class _805_Verify_non_reserver_User_is_not_allowed_to_connect_reserved_status_device extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;

	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",outputText="",XPath="",XPathValue="",queryEndDate="",endDateValue="";
	private String queryEndDate1="",endDateValue1="",queryEndDate2="",endDateValue2="";;
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
		// Step 3 : Create reservation for current time slot for the same device
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
		// Step 4 : logout & login using Admin user
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
					strActualResult = "Admin User logged in successfully.";
				}
				else
					strActualResult = "User not logged in." + strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);

		
		//*************************************************************//                     
		// Step 5 : status of the device
		//*************************************************************//
		strstepDescription = "get status of the device";
		strexpectedResult = "Verify status of the device is reserved.";
			
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
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);

		
		//*************************************************************//                     
		// Step 6 : Connect device using CLI
		//*************************************************************//
		strstepDescription = "Connect the Reserved device using the other user";
		strexpectedResult = "Device should not get connected using CLI.";

		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("client", "", dicCommon.get("EmailAddress"), dicCommon.get("Password"), devicename, "web");
			isEventSuccessful = (boolean)Values[4];
			outputText=(String)Values[1];
			devicename=(String)Values[3];			
			if (isEventSuccessful && outputText.contains("opened"))
				strActualResult = "Device Viewer launched after connecting to device:  " + Values[3] + " & processfound : " +  Values[1];
			else
			{
				strActualResult = "devieviewer didnt launch.";
				isEventSuccessful = true;
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

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
