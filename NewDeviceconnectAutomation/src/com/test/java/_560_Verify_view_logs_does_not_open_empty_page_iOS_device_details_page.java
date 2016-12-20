package com.test.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: 5-Feb-2016
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-522
 */

public class _560_Verify_view_logs_does_not_open_empty_page_iOS_device_details_page extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private Set windowids;
	String parentwindowid = "";
	String childwindowid = "",text="";

	public final void testScript() throws InterruptedException, IOException
	{
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful = Login();
		
			
		//****************************************************************************************//
		//** Step 2 - Select status 'Available' and platform 'iOS' **//
		//****************************************************************************************//
		if(isEventSuccessful)
		{   
			isEventSuccessful=selectStatus("Available");
		}
		if(isEventSuccessful)
		{
			isEventSuccessful=selectPlatform("iOS");
		}
		
		//******************************************************************//
		//*** Step 3 : Select first device*****//
		//******************************************************************//  
		strstepDescription = "Open device details page for first device.";
		strexpectedResult =  "Device details page for the first device should be displayed.";
		if (isEventSuccessful)
		{
			isEventSuccessful = SelectDevice("first");
			if (isEventSuccessful)
			{
				strActualResult = "Device details page displayed";
			}
			else
			{
				strActualResult = "Device details page did not displayed";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 4 : Verify View Log button is clickable*****//
		//******************************************************************//  
		strstepDescription = "Verify View Log button is clickable.";
		strexpectedResult =  "View Log button should be clickable.";
		if (isEventSuccessful)
		{
			PerformAction("btnViewLog",Action.WaitForElement);
			isEventSuccessful = PerformAction("btnViewLog",Action.Click);
			if (isEventSuccessful)
			{
				strActualResult = "View Log button is clickable.";
			}
			else
			{
				strActualResult = "Unable to click on 'View Log' button.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//*************************************************************//
		// Step 5 - Verify log page is not empty
		//*************************************************************//
		strStepDescription = "Verify log page is not empty.";
		strExpectedResult = "Log page should not be empty.";
		strActualResult = "";
		if (isEventSuccessful)
		{
			windowids = driver.getWindowHandles();
			Iterator<String> itr = windowids.iterator();
			parentwindowid = (String) itr.next();
			childwindowid = (String) itr.next();
			isEventSuccessful = PerformAction("browser", Action.SelectNewWindow,childwindowid);
			if (isEventSuccessful)
			{
				text=driver.findElement(By.cssSelector("pre")).getText();
				if(text.length()>1)
				{
					strActualResult = "Log page is not empty.";
				}
				else
				{
					strActualResult = "Log page is empty.";
				}
				PerformAction("browser", Action.SelectWindow,parentwindowid);
			}
			else
			{
				strActualResult = "User did not navigated to new window";
			}
		}
		else
		{
			strActualResult = "Not able to navigate to new tab";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		GoToDevicesPage();
		PerformAction("btnRefresh_Devices", Action.Click);
	}
}