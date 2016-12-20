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
 * Jira Test Case Id: QA-186
 */
public class _806_Verify_status_filter_is_not_showing_2_instances_of_same_device extends ScriptFuncLibrary
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
		// Step 4 : Connect device using CLI
		//*************************************************************//
		strstepDescription = "Connect the Reserved device using the other user";
		strexpectedResult = "Device should get connected using CLI.";

		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("client", "", dicCommon.get("testerEmailAddress"), dicCommon.get("testerPassword"), devicename, "web");
			isEventSuccessful = (boolean)Values[4];
			outputText=(String)Values[1];
			devicename=(String)Values[3];			
			if (isEventSuccessful && outputText.contains("opened"))
				strActualResult = "Device Viewer launched after connecting to device:  " + Values[3] + " & processfound : " +  Values[1];
			else
			{
				strActualResult = strErrMsg_AppLib + " - " + dicOutput.get("executedCommand");
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);


		//*************************************************************//                     
		// Step 5 : logout & login using Admin user
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
		// Step 6 : select status as in use 
		//*************************************************************//
		strstepDescription = "Select status as in use.";
		strexpectedResult = "Verify 2 instanced of the same device is not displayed..";
			
		int count = 0;
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToDevicesPage();
			if(isEventSuccessful)
			{
				isEventSuccessful = selectStatus_DI("In Use");
				if(isEventSuccessful)
				{
					List <WebElement> deviceNames = getelementsList("eledeviceName_Indexpage");
					//List <WebElement> deviceStatus = getelementsList("eleDeviceStatus_Indexpage");
					
					for (WebElement webElement : deviceNames) 
					{	
						String dName = GetTextOrValue(webElement, "text");
						if(dName.equals(devicename))
						{
							count ++;
						}	
					}
					if(count>2)
					{
						isEventSuccessful = false;
						strActualResult = "Device is displayed twice in the selected filter.";
					}
				}
				else
					strActualResult = strErrMsg_AppLib;
			}
		}		
		else
			strActualResult = "Not able to login/logout";
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	
		//**************************************************************************//
		// Step 7 : Release device.
		//**************************************************************************//
		Values = ExecuteCLICommand("release", "iOS", EmailAddress, Password, devicename, "","","" );
		isEventSuccessful = (boolean)Values[2];
		if(isEventSuccessful)
			strActualResult = "Device Released successfully. ";
		else
			strActualResult = "Device didn't release successfully. " + strErrMsg_AppLib;
		reporter.ReportStep("Verify device is released" , "Device should get released.", strActualResult, isEventSuccessful);

		//*************************************************************//                     
		// Step 8 : Cancel Reservation
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