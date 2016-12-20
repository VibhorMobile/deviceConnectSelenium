package com.test.java;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*
 * Jira Test Case Id: QA-887
 */
public class _712_Verify_From_and_TO_date_fields_default_value_should_not_be_same_and_should_be_exact_one_year extends ScriptFuncLibrary
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
		// Step 3 : Create reservation for current time slot for the same device
		//*************************************************************//
		strstepDescription = "Reservation tab - From & To dates";
		strexpectedResult = "Verify From & To dates on Reservations tab";
		if(isEventSuccessful)
		{	
			String startDateValue = (String) ((org.openqa.selenium.JavascriptExecutor)GenericLibrary.driver).executeScript("return document.querySelector('#start-picker > input').value;");
			String EndDateValue = (String) ((org.openqa.selenium.JavascriptExecutor)GenericLibrary.driver).executeScript("return document.querySelector('#to-picker > input').value;");
			
			if(startDateValue!="" && EndDateValue!="")
			{	
				Calendar c = Calendar.getInstance();
				Date newDate = c.getTime();
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
		        String FormatedDate = format.format(c.getTime());  // returns new date object, number of hours provided (in the future)
				
		        
				String EnddateAfteradding = addYears(null,1,"MM/dd/yy");
				if(FormatedDate.toString().contains(startDateValue))
				{
					if(EnddateAfteradding.contains(EndDateValue))
					{
						strActualResult = "Start Date & End Date are displayed correctly with a difference of exact one year.";
					}
					else
						strActualResult = "End Date is not displayed correctly with a difference of exact one year.";
				}
				else
					strActualResult = "Start Date is not displayed correctly as per current date. Current Date - " + newDate.toString() + " StartDate on UI - " + startDateValue;				
			}
			else
				strActualResult = "Start Date OR End Date value is returned as Null.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
	}
}