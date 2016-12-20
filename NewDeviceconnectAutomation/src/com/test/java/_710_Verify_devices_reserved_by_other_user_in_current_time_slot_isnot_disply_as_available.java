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
 * Jira Test Case Id: QA-1234
 */
public class _710_Verify_devices_reserved_by_other_user_in_current_time_slot_isnot_disply_as_available extends ScriptFuncLibrary
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
		// Step 4 : Logout & login using other user credentials
		//*************************************************************//
		strstepDescription = "Logout - " + dicCommon.get("EmailAddress") + " & login using - " + dicCommon.get("testerEmailAddress");
		strexpectedResult = dicCommon.get("EmailAddress") + " should be logged out & User " + dicCommon.get("testerEmailAddress") + " should get login";
		
		if(isEventSuccessful)
		{
			isEventSuccessful = LogoutDC();
			if(isEventSuccessful)
			{
				isEventSuccessful = LoginToDC(EmailAddress, Password);				
			}				
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//*************************************************************//                     
		// Step 4 : Logout & login using other user credentials
		//*************************************************************//
		boolean flag = true;
		strstepDescription = "Login & check the device status";
		strexpectedResult = "Status of the device should be In Use / Reserved for other Users on device Index page.";
		
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToDevicesPage();
			if(isEventSuccessful)
			{
				isEventSuccessful = selectStatus_DI("In Use");
				if(isEventSuccessful)
				{
					List <WebElement> deviceNames = getelementsList("eledeviceName_Indexpage");
					List <WebElement> deviceStatus = getelementsList("eleDeviceStatus_Indexpage");
					
					for (WebElement webElement : deviceNames) 
					{
						String dName = GetTextOrValue(webElement, "text");
						for (WebElement webElement2 : deviceStatus)
						{
							if(dName.equals(devicename))
							{
								String status = GetTextOrValue(webElement2, "text");
								if(status.contains("In Use") || status.contains("Reserved"))
								{
									flag = true;
									strActualResult = "device : " + dName +" status is not Available.";
									break;
								}
								else
									flag = false;
							}							
						}
					}
					if(!flag)
						strActualResult = "Device not displayed in the selected filter OR the device status is not In Use/Reserved.";
				}
				else
					strActualResult = strErrMsg_AppLib;
			}
		}		
		else
			strActualResult = "Not able to login/logout";
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}