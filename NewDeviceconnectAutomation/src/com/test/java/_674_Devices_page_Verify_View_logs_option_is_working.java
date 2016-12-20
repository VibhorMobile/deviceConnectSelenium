package com.test.java;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1569
 */
public class _674_Devices_page_Verify_View_logs_option_is_working extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false, isExist=false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	private Set windowids;
	String parentwindowid = "";
	String childwindowid = "",text="";
	Object[] values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{
		//Step 1 - Login to deviceConnect
		 isEventSuccessful = Login(); 
		 
		 isEventSuccessful = selectStatus("Available");
		 
		 strStepDescription = "Click on View Log button in available device on devices page";
		 strExpectedResult = "clicked on View log successfully";
		 
		 isEventSuccessful =  PerformAction("btnConnectArrow_Devicespage", Action.Click);
		 if(isEventSuccessful)
		 {
			 isEventSuccessful =  PerformAction("btnViewLog", Action.Click);
			 if(isEventSuccessful)
			 {
				 strActualResult="Successfully clicked on View Log button.";
			 }
			 else
			 {
				 strActualResult = "Could not clicked on Reboot button";
			 }
		 }
		 else
		 {
			 strActualResult = "Could not clicked on connect arrow button. May be there is no  available device on the server.";
		 }
		 
		 reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		 
		 
		 
		//*************************************************************//
		// Step 5 - Verify 'About App Distribution' link opened in new tab
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
	}
}