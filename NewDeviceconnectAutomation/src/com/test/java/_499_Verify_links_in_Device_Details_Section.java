package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-86, QA-14
 */

public class _499_Verify_links_in_Device_Details_Section extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();

		//******************************************************************//
		//*** Step 2 : Select Status Available. *****//
		//******************************************************************//  
		isEventSuccessful = selectStatus("Available");
		strstepDescription = "Open device details page for first Available.";
		strexpectedResult =  "Device details page for the first available device should be displayed.";

		if (isEventSuccessful)
		{
			isEventSuccessful = !VerifyMessage_On_Filter_Selection();
			if (isEventSuccessful) // continue only if there are some devices under android platform.
			{
				isEventSuccessful = SelectDevice("first");
				if (!isEventSuccessful)
				{
					strActualResult = strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "Warning messgae displayed on devices page.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}


		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Device details page is displayed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		
		//******************************************************************//
		//** Step 3 : click on 'Show Details...' link to display hidden details ****//
		//******************************************************************//
		strstepDescription = "Click on 'Show Details...' link to display hidden device details";
		strexpectedResult = "User should be able to click on the 'Show Details...' link";
		isEventSuccessful = PerformAction("lnkShowDetails", Action.isDisplayed);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
			if (isEventSuccessful)
			{
				strActualResult = "User is able to click on 'Show Details...' link successfully.";
			}
			else
			{
				strActualResult = "Could not click on 'Show Details...' link on device details page.";
			}
		}
		else
		{
			strActualResult = "'Show Details...' link is not displayed.";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//******************************************************************//
		//** Step 4 : click on 'More' link to display Reservation details ****//
		//******************************************************************//
		strstepDescription = "Click on 'More' link to display Reservation details";
		strexpectedResult = "User should be able to click on the 'More' link";
		isEventSuccessful = PerformAction("linkMoreonDeviceDatils", Action.isDisplayed);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("linkMoreonDeviceDatils", Action.Click);
			if (isEventSuccessful)
			{
				strActualResult = "User is able to click on 'Show Details...' link successfully.";
			}
			else
			{
				strActualResult = "Could not click on 'Show Details...' link on device details page.";
			}
		}
		else
		{
			strActualResult = "'Show Details...' link is not displayed.";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//** Step 5 : Verify Reservations Page Displayed//
		//******************************************************************//
		strstepDescription = "User will be redirected to Reservations page";
		strexpectedResult = "User should land on Reservations page";
		isEventSuccessful = PerformAction("eleReservationsHeader", Action.isDisplayed);
		if(isEventSuccessful)
		{
			strActualResult = "User lands on Reservations page";
		}
		else
		{
			strActualResult = "User lands somewhere other than Reservations Page";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//** Step 5 : Navigate Back to Device Details Page
		//******************************************************************//
		isEventSuccessful=PerformAction("browser","Back");
		strstepDescription = "User will be redirected to Device Details page";
		strexpectedResult = "User should redirected to Device Details page";
		isEventSuccessful = PerformAction("eleDeviceNameinDeviceDetailsHeader", Action.isDisplayed);
		if(isEventSuccessful)
		{
			strActualResult = "User redirected to Device Details page";
		}
		else
		{
			strActualResult = "User redirected somewhere other than Device Details Page";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//** Step 6 : Click on Show Details link and Hide links
		//******************************************************************//
		isEventSuccessful=PerformAction("lnkShowDetails", Action.Click);
		strstepDescription = "Click on 'Hide' link to display Reservation details";
		strexpectedResult = "User should be able to click on the 'Hide' link";
		PerformAction("browser", "waitforpagetoload");
		isEventSuccessful = PerformAction("lnkHideDetails_deviceDetailsPage", Action.isDisplayed);
		if(isEventSuccessful)
		{
			isEventSuccessful = PerformAction("lnkHideDetails_deviceDetailsPage", Action.Click);
			if (isEventSuccessful)
			{
				strActualResult = "User is able to click on 'Hide' link successfully.";
			}
			else
			{
				strActualResult = "Could not click on 'Hide' link on device details page.";
			}
		}
		else
		{
			strActualResult = "User redirected somewhere other than Device Details Page";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}	
}