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
 * Jira Test Case Id: QA-1447
 */
public class _791_Verify_connection_to_device_for_which_any_future_reservation_is_made extends ScriptFuncLibrary
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
		isEventSuccessful = Login();
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
					/*if (isEventSuccessful
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
		// Step 4 : logout & login using other user
		//*************************************************************//
		strstepDescription = "Logout & login using other User";
		strexpectedResult = "Verify User logged out & logged in.";
		if (isEventSuccessful)
		{	
			isEventSuccessful = LogoutDC();
			if(isEventSuccessful)
			{
				isEventSuccessful = LoginToDC(EmailAddress,Password );
				if(isEventSuccessful)
				{
					strActualResult = "User - " + EmailAddress + " logged in successfully.";
				}
				else
					strActualResult = "User not logged in." + strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);
		
		//**************************************************************************//
		// Step 5 : Verify user is able to connect using webviewer
		//**************************************************************************//	
		strstepDescription = "Connect using other user";
		strexpectedResult = "Verify other user is able to connect to the device which is future reserved by other user.";
	
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("client", "", EmailAddress, Password, devicename, "web");
	     	 isEventSuccessful = (boolean)Values[4];
	     	 outputText=(String)Values[1];
	     	 deviceName=(String)Values[3];
	     	 if (outputText.contains("opened"))
	     	 {
	     		 strActualResult = "web Viewer launched after connecting to device:  " + Values[3] + " & processfound : " +  Values[1];
	     	 }
	     	 else
	     	 {
	     		 strActualResult = "Web Viewer not launched, User - " + EmailAddress + " was not able to connect";
	     	 }
		}		
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);
		
		//**************************************************************************//
		// Step 6 : Release device.
		//**************************************************************************//
		Values = ExecuteCLICommand("release", "", EmailAddress, Password, devicename, "","","" );
		isEventSuccessful = (boolean)Values[2];
		if(isEventSuccessful)
			strActualResult = "Device Released successfully. ";
		else
			strActualResult = "Device didn't release successfully. " + strErrMsg_AppLib;
		reporter.ReportStep("Verify device is released" , "Device should get released.", strActualResult, isEventSuccessful);
		
		//**************************************************************************//
		// Step 7 : Verify user is able to connect using deviceViewer
		//**************************************************************************//	
		if (isEventSuccessful)
		{	
			Values = ExecuteCLICommand("client", "", EmailAddress, Password, devicename, "desktop");
	     	//Values = ExecuteCLICommand("connect", "", EmailAddress, Password, devicename, "");
			isEventSuccessful = (boolean)Values[4];
			outputText=(String)Values[1];
			deviceName=(String)Values[3];
			if (isEventSuccessful && outputText.contains("MobileLabs.deviceViewer.exe"))
			{
				strActualResult = "Device Viewer launched after connecting to device:  " + Values[3] + " & processfound : " +  Values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
		}		
		reporter.ReportStep("Verify Device Viewer is launched " , "User should get connected and device Viewer should get launched.", strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 8 : Release device.
		//**************************************************************************//
		Values = ExecuteCLICommand("release", "", EmailAddress, Password, devicename, "","","" );
		isEventSuccessful = (boolean)Values[2];
		if(isEventSuccessful)
			strActualResult = "Device Released successfully. ";
		else
			strActualResult = "Device didn't release successfully. " + strErrMsg_AppLib;
		reporter.ReportStep("Verify device is released" , "Device should get released.", strActualResult, isEventSuccessful);

		//*************************************************************//                     
		// Step 9 : Logout & login using Admin User
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
		// Step 10 : Cancel Reservation
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