package com.test.java;

import java.util.Iterator;
import java.util.Set;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

public class _198_Verify_the_link_Click_here_for_more_information_about_Mobile_Labs extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "";
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	private Set windowids;
	String parentwindowid = "";
	String childwindowid = "";
	
	public final void testScript()
	{
		//*************************************************************//
		// Step 1 - Login to DC with Admin Credentials
		//*************************************************************//
		isEventSuccessful = Login();
		
		//*************************************************************//
		// Step 2 - Click About DC link in footer
		//*************************************************************//
		strStepDescription = "Click on the link 'About deviceConnect' in footer section.";
		strExpectedResult = "'About deviceConnect' page should gets opened as a popup.";
		isEventSuccessful = PerformAction("lnkAbout", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleDeviceConnectLogoOnAbout", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "'About deviceConnect' page is opened as a popup.";
			}
			else
			{
				strActualResult = "'About deviceConnect' page is not opened.";
			}
		}
		else
		{
			strActualResult = "Page is not opened in a new window.";
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//
		// Step 3 - Click More info about DC link
		//*************************************************************//
		strStepDescription = "Click 'More information about Mobile Labs.' link in the About deviceConnect popup.";
		strExpectedResult = "It should redirect to 'http://www.mobilelabsinc.com/'.";
		strActualResult = "";
		isEventSuccessful = PerformAction("lnkmore_aboutPage", Action.Click);
		if (isEventSuccessful)
		{
			windowids = driver.getWindowHandles();
			Iterator<String> itr = windowids.iterator();
			parentwindowid = (String) itr.next();
			childwindowid = (String) itr.next();
			isEventSuccessful = PerformAction("browser", Action.SelectWindow,childwindowid);
			if (isEventSuccessful)
			{
				strActualResult = "Page is redirected to 'http://www.mobilelabsinc.com/'.";
				PerformAction("browser", Action.SelectWindow,parentwindowid);
			}
			else
			{
				strActualResult = "Page is not redirected to 'http://www.mobilelabsinc.com/'";
			}
		}
			else
			{
				strActualResult = "Not able to navigate to new tab";
			}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}
}