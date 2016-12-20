package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1331
 */

public class _294_Verify_that_if_the_device_is_offline_then_the_information_should_be_passed_back_as_Not_Available_for_iOS_device extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	Object[] values = new Object[2];
	String deviceName = "";

	public final void testScript()
	{
		///**************************************************************************//
		//Step 1 - : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		isEventSuccessful = Login();


		//**************************************************************************//
		// Step 2 : Select Android Platform and open details page of an Android Offline Device
		//**************************************************************************//
		if (isEventSuccessful)
		{
			isEventSuccessful = selectStatus("Offline");
			if (isEventSuccessful)
			{
				isEventSuccessful = selectPlatform("Android");
				
			}
			else
			{
				return;
			}
		}
		
		else
		{
			
			return;
		}

		//**************************************************************************//
		// Step 3 :  Navigate to the device details of first Android Offline device
		//**************************************************************************//
		
		 values = GoTofirstDeviceDetailsPage();
		isEventSuccessful = (boolean) values[0] ;
		 deviceName =(String) values[1];
		
		if (!isEventSuccessful)
		{
			return;
		}

		//**************************************************************************//
		// Step 4 : Verify Battery status is not displayed on the device details page.
		//**************************************************************************//
		VerifyonDeviceDetails("Battery Status","Not Displayed");
		
		if (!isEventSuccessful)
		{
			return;
		}


		//**************************************************************************//
		// Step 5 : Click on 'Show details' link on device details page.
		//**************************************************************************//
		strstepDescription = "Click on 'more...' link to display hidden device details";
		strexpectedResult = "User should be able to click on the 'more...' link";
		isEventSuccessful = PerformAction("lnkShowDetails", Action.isDisplayed);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
			if (isEventSuccessful)
			{
				strActualResult = "User is able to click on 'more...' link successfully.";
			}
			else
			{
				strActualResult = "Could not click on 'more...' link on device details page.";
			}
		}
		else
		{
			strActualResult = "'more...' link is not displayed.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}

		//**************************************************************************//
		// Step 6 : Verify Disk Usage is displayed on the device details page.
		//**************************************************************************//
		if (isEventSuccessful)
		{
			VerifyonDeviceDetails("Disk Usage ", "Not Available");
			
		}
	}
}