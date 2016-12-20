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
 * Jira Test Case Id: QA-115
 */
public class _803_Verify_User_is_not_allowed_to_create_recurring_reservation_for_more_than_1_year_and_time_less_than_30_mins extends ScriptFuncLibrary
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
		// Step 3 : Set End date for after 2 years from now
		//*************************************************************//
		strstepDescription = "Set End date for after 2 years from now";
		strexpectedResult = "Verify User is not able to set the end date to after 2 years from today.";
		if(isEventSuccessful)
		{	
			if(GoToCreateReservationPage())
			{
				Calendar c = Calendar.getInstance();
		    	Date newDate = c.getTime();
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
		        String FormatedTodayDate = format.format(c.getTime());  // returns new date object, number of hours provided (in the future)
				String EnddateAfteradding = addYears(null, 2,"MM/dd/yy");
				
				isEventSuccessful = PerformAction("eleCreateRsrvtnHeader", Action.isDisplayed);
				if (isEventSuccessful)
				{
					isEventSuccessful = RSVD_SelectDevice(devicename, true );
	 				if (isEventSuccessful)
					{
	 					isEventSuccessful = RSVD_SelectReservationType("Monthly");
						if (isEventSuccessful)
						{
							try
							{
								queryEndDate = "document.querySelector('div.input-group.date.end-date-picker > input.form-control').value";
							    endDateValue = (String) ((org.openqa.selenium.JavascriptExecutor)GenericLibrary.driver).executeScript("return " + queryEndDate);
							    endDateValue = addYears(null,1,"MM/dd/yy");
							    
								queryEndDate1 = "document.querySelector('div.input-group.date.end-date-picker > input.form-control').value=''";
								endDateValue1 = (String) ((org.openqa.selenium.JavascriptExecutor)GenericLibrary.driver).executeScript("return " + queryEndDate1);

								genericLibrary.driver.findElement(By.cssSelector("div.input-group.date.end-date-picker > input.form-control")).sendKeys(EnddateAfteradding);
								genericLibrary.driver.findElement(By.cssSelector("div.input-group.date.end-date-picker > input.form-control")).sendKeys(Keys.TAB);
								strActualResult = "set enddate to more than 2 years.";
							}
							catch(RuntimeException e)
							{
								isEventSuccessful = false;
								strActualResult = "Could not set the enddate to more than 2 years." + e.getMessage();
							}
						}
						else
							strActualResult = strErrMsg_AppLib;
					}
	 				else
	 					strActualResult = strErrMsg_AppLib;
				}
				else
					strActualResult = strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 4 : Verify the date gets reset to one year
		//**************************************************************************//	
		strstepDescription = "Connect using other user";
		strexpectedResult = "Verify the date gets reset to one year.";

		if (isEventSuccessful)
		{	
			try
			{
				String queryEndDate2 = "document.querySelector('div.input-group.date.end-date-picker > input.form-control').value";
				String endDateValue2 = (String) ((org.openqa.selenium.JavascriptExecutor)GenericLibrary.driver).executeScript("return " + queryEndDate);
				if(endDateValue.equals(endDateValue2))
					strActualResult =  "User is not allowed to set the enddate value for 2 years & got reset to max 1 year.";
				else
					strActualResult = "User is allowed to set the enddate value for 2 years & didn't got reset to max 1 year.";
			}
			catch(RuntimeException e)
			{
				isEventSuccessful = false;
				strActualResult = "User is allowed to set the value of end date for 2 years." + e.getMessage();
			}
		}		
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);
	}
}
