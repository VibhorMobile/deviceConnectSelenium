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
 * Jira Test Case Id: QA-264
 */
public class _793_Verify_Reset_Clear_Button_should_reset_filters_and_clear_filters_on_Reservation_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;

	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String outputText="",deviceNameTesterUser="",XPath="",XPathValue="";
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
		deviceNameTesterUser = (String) values[1];
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
				isEventSuccessful = RSVD_CreateReservationNever(deviceNameTesterUser, true );
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

		//**************************************************************************//
		// Step 4 : Verify user is not able to connect using webviewer
		//**************************************************************************//	
		strstepDescription = "Connect using other user";
		strexpectedResult = "Verify other user is not able to connect to the device which is reserved by other user.";

		if (isEventSuccessful)
		{	
			Values = ExecuteCLICommand("client", "", dicCommon.get("EmailAddress"),  dicCommon.get("Password") , deviceNameTesterUser, "web");
			isEventSuccessful = (boolean)Values[4];
			outputText=(String)Values[1];
			deviceNameTesterUser=(String)Values[3];
			if (outputText.contains("opened"))
			{
				strActualResult = "web Viewer launched after connecting to device:  " + Values[3] + " & processfound : " +  Values[1];
				isEventSuccessful=false;
			}
			else
			{
				strActualResult = "Web Viewer not launched, User - " + EmailAddress + " was not able to connect";
				isEventSuccessful=true;
			}
		}		
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//                     
		// Step 5 : Logout & login using Admin User
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
					strActualResult = "User logged in successfully.";
				}
				else
					strActualResult = "User not logged in." + strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);
		
		//*************************************************************//                     
		// Step 6: Get the first Available device name.
		//*************************************************************//    
		strstepDescription = "Get the first Available device name";
		strexpectedResult = "Get the first Available device name & store it in deviceName";

		values =  GetDeviceDetails(2,"devicename","list","Available");
		isEventSuccessful  = (boolean) values[0] ;
		devicename = (String) values[1];
		GoToReservationsPage();
		
		//*************************************************************//                     
		// Step 7 : Create reservation for current time slot for the same device
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
		// Step 8 : Select User on Reservations page
		//*************************************************************//
		strstepDescription = "Select Device on Reservations page";
		strexpectedResult = "Verify USer is able to select Device from dropdown on Reservations page.";
		if (isEventSuccessful)
		{	
			isEventSuccessful = GoToReservationsPage();
			if(isEventSuccessful)
			{
				waitForPageLoaded();
				if (PerformAction("drpDevices_ReservationsPage", Action.WaitForElement, "2"))
				{
					if (PerformAction("drpDevices_ReservationsPage", Action.Click))
					{	
						waitForPageLoaded();
						XPath = getValueFromDictionary(dicOR, "eleDeviceOptions_ReservationsPage");
						XPathValue = XPath.replace("__DEVICENAME__", devicename);
						if (dicCommon.get("BrowserName").toLowerCase().contains("ie") && (GenericLibrary.IEversion.equals("11")))
						{
							waitForPageLoaded();
							PerformAction(XPathValue, Action.WaitForElement,"5");
							isEventSuccessful = PerformAction(XPathValue, Action.ClickUsingJS);
						}
						else
						{
							waitForPageLoaded();
							isEventSuccessful = PerformAction(XPathValue, Action.ClickUsingJS);
						}
						if(isEventSuccessful)
							strActualResult = "The Devices dropdown contains the device name to be selected.";
						else
							strActualResult = "The Devices dropdown doesn't contains the device name to be selected.";
					}
					else
						strActualResult = "Unable to click the Devices dropdown on Reservations Index page.";
				}
				else
					strActualResult = "The Devices dropdown didn't display.";
			}
		}
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);
		
		//*************************************************************//                     
		// Step 9 : Click on Apply filter
		//*************************************************************//
		strstepDescription = "Click on apply filter";
		strexpectedResult = "Verify User is apply to click on apply filter.";
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnApply_ReservationsPage", Action.Click);
			if(isEventSuccessful)
			{
				waitForPageLoaded();
				String XPATHdeviceNameCol = getValueFromDictAndReplace(dicOR, "eleReservationTableCol_ReservationIndex", "__COLUMNHEADER__", "Device");
				//String XPATHNextReservationCol = getValueFromDictAndReplace(dicOR, "eleDeviceTableCol_DevicesIndex", "__COLUMNHEADER__", "Next Reservation");
				List <WebElement> ReservationDeviceList = getelementsList(XPATHdeviceNameCol);
			    ReservationDeviceList.size();
				int counter = 0;
				for(int i=0; i<ReservationDeviceList.size();i++)
				{
					String dName = GetTextOrValue(ReservationDeviceList.get(i), "text");
					if(dName.equals(devicename))
						strActualResult = "Device displayed on row number " + i + " is  - " + devicename;
					else
					{
						strActualResult = "Device displayed on row number " + i + " is  - " + devicename;
						counter++;
					}
				}
						
				if(counter>0)
				{
					strActualResult = "Filter didn't apply correctly.";
					isEventSuccessful = false;
				}
				else
				{
					strActualResult = "Filter applied correctly.";
					isEventSuccessful = true;
				}
			}
		}
		reporter.ReportStep("Cancel Reservation", "User should be able to cancel reservation", strActualResult, isEventSuccessful);

		//*************************************************************//                     
		// Step 9 : Click on Apply filter
		//*************************************************************//
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = PerformAction("btnClear_ReservationPage", Action.Click);
			if(isEventSuccessful)
			{
				waitForPageLoaded();
				String XPATHdeviceNameCol = getValueFromDictAndReplace(dicOR, "eleReservationTableCol_ReservationIndex", "__COLUMNHEADER__", "Device");
				//String XPATHNextReservationCol = getValueFromDictAndReplace(dicOR, "eleDeviceTableCol_DevicesIndex", "__COLUMNHEADER__", "Next Reservation");
				List <WebElement> ReservationDeviceList = getelementsList(XPATHdeviceNameCol);
				int counter = 0;
				for(int i=0; i<ReservationDeviceList.size();i++)
				{
					String dName = GetTextOrValue(ReservationDeviceList.get(i), "text");
					if(dName.equals(devicename))
						strActualResult = "Device displayed on row number " + i + " is  - " + devicename;
					else
					{
						strActualResult = "Device displayed on row number " + i + " is  - " + devicename;
						counter++;
					}
				}
						
				if(counter>0)
				{
					strActualResult = "Reset Filter Worked.";
					isEventSuccessful = true;
				}
				else
					isEventSuccessful = false;
			}
		}
		
		//*************************************************************//                     
		// Step 10 : Cancel Reservation
		//*************************************************************//	
		GoToReservationsPage();
		isEventSuccessful = RSVD_DeleteReservationbydeviceName(devicename);
		if(isEventSuccessful)
			strActualResult = "Reservation got canceled.";
		else
			strActualResult = "Not able to cancel reservation - " + strErrMsg_AppLib;
		reporter.ReportStep("Cancel Reservation", "User should be able to cancel reservation", strActualResult, isEventSuccessful);

		//*************************************************************//                     
		// Step 11 : Cancel Reservation
		//*************************************************************//	
		GoToReservationsPage();
		isEventSuccessful = RSVD_DeleteReservationbydeviceName(deviceNameTesterUser);
		if(isEventSuccessful)
			strActualResult = "Reservation got canceled.";
		else
			strActualResult = "Not able to cancel reservation - " + strErrMsg_AppLib;
		reporter.ReportStep("Cancel Reservation", "User should be able to cancel reservation", strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 12 : Release device.
		//**************************************************************************//
		Values = ExecuteCLICommand("release", "", dicCommon.get("EmailAddress"), dicCommon.get("Password"), deviceNameTesterUser, "","","" );
		isEventSuccessful = (boolean)Values[2];
		if(isEventSuccessful)
			strActualResult = "Device Released successfully. ";
		else
			strActualResult = "Device didn't release successfully. " + strErrMsg_AppLib;
		reporter.ReportStep("Verify device is released" , "Device should get released.", strActualResult, isEventSuccessful);
		
	}
}
