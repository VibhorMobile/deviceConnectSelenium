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
public class _708_User_can_connect_using_both_WebViewer_and_Device_Viewer_if_reserved_by_other_User_for_future extends ScriptFuncLibrary
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
		//String devicename;
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
		deviceName = (String) values[1];
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
                	isEventSuccessful = RSVD_CreateReservationNever(deviceName,StartAddTime.split(" ")[1], StartAddTime, EndAddTime.split(" ")[1], EndAddTime,true);
                    if (isEventSuccessful)
                    {
                    	strActualResult = "Future Reservation got created for the device : " + deviceName;
                    }
                    else
                    	strActualResult = "Future Reservation didn't get created for device : " + deviceName;
                }
                else
                	strActualResult = "Create Reservation header not displayed.";
			}
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);	
		
		
		//*************************************************************//                     
		// Step 4 : Connect to the same device using web viewer
		//*************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("client", "", EmailAddress, Password, deviceName, "web");
	     	 isEventSuccessful = (boolean)Values[4];
	     	 outputText=(String)Values[1];
	     	 deviceName=(String)Values[3];
			 if (isEventSuccessful && outputText.contains("opened"))
				{
				   strActualResult = "web Viewer launched after connecting to device:  " + Values[3] + " & processfound : " +  Values[1];
				}
				else
				{
					strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
				}
			 reporter.ReportStep("Verify web Viewer is launched " , "User should get connected and web deviceviewer should get launched.", strActualResult, isEventSuccessful);
		}		
		
		
		//*************************************************************//                     
		// Step 6 : Connect to the same device using device viewer
		//*************************************************************//
		if (isEventSuccessful)
		{	
			Values = ExecuteCLICommand("connect", "", EmailAddress, Password, deviceName, "");
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
		// Step 7 : Release device.
		//**************************************************************************//
		Values = ExecuteCLICommand("release", "", EmailAddress, Password, deviceName, "","","" );
		isEventSuccessful = (boolean)Values[4];
		if(isEventSuccessful)
			strActualResult = "Device Released successfully. ";
		else
			strActualResult = "Device didn't release successfully. " + strErrMsg_AppLib;
		reporter.ReportStep("Verify device is releases" , "Device should get released.", strActualResult, isEventSuccessful);
		Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
		
		CancelReservation(deviceName);
				
	}
}