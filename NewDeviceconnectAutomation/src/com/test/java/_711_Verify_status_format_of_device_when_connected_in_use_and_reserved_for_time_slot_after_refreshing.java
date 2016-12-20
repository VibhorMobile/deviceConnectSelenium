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
 * Jira Test Case Id: QA-1036
 */
public class _711_Verify_status_format_of_device_when_connected_in_use_and_reserved_for_time_slot_after_refreshing extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;

	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",outputText="",deviceName="";
	private Object[] firstdeviceSelected = null;
	Object[] Values = new Object[5]; 
	
	/**
	 * @throws InterruptedException
	 * @throws IOException
	 */
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
		// Step 4 : Connect device using CLI
		//*************************************************************//
		strstepDescription = "Connect the Reserved device using the reserver user";
		strexpectedResult = "Device should get connected using CLI.";
		
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("client", "", dicCommon.get("EmailAddress"), dicCommon.get("Password"), devicename, "web");
			isEventSuccessful = (boolean)Values[4];
			outputText=(String)Values[1];
			deviceName=(String)Values[3];			
			if (isEventSuccessful && outputText.contains("opened"))
				strActualResult = "Device Viewer launched after connecting to device:  " + Values[3] + " & processfound : " +  Values[1];
			else
				strActualResult = strErrMsg_AppLib + " - " + dicOutput.get("executedCommand");
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//*************************************************************//                     
		// Step 5 : Verify the status format for In Use Reserved device
		//*************************************************************//
		boolean flag = true;
		strstepDescription = "Verify the status format for In Use Reserved devices";
		strexpectedResult = "Status format of the Reserved In Use device should remain the same even after page refresh";
		
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
					
					
					for(int i=0; i<=deviceNames.size();i++)
					{
						String dName = GetTextOrValue(deviceNames.get(i), "text");
						if(dName.equals(devicename))
						{
							String status = GetTextOrValue(deviceStatus.get(i), "text");
							if(status.contains("by reservation"))
							{
								isEventSuccessful = PerformAction("browser", Action.Refresh);
								if(isEventSuccessful)
								{
									List <WebElement> deviceStatusafterRefresh = getelementsList("eleDeviceStatus_Indexpage");
									String statusafterRefresh = GetTextOrValue(deviceStatusafterRefresh.get(i), "text");
									if(statusafterRefresh.contains("by reservation") && status.equals(statusafterRefresh))
									{
										strActualResult = "device : " + dName +" status contains 'by reservation' even after browser refresh.";
									}
									else
									{
										strActualResult = strErrMsg_GenLib;
									}
									break;
								}
							}
							else
								flag = false;	
						}
					}
					if(!flag)
					{
						isEventSuccessful = flag;
						strActualResult = "Device not displayed in the selected filter OR the device status is not In Use/Reserved.";
					}
				}	
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//*************************************************************//                     
		// Step 6 : Release device
		//*************************************************************//
		isEventSuccessful = ReleaseDevice(devicename);
		
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