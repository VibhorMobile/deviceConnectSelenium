package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2205
 */
public class _270_Verify_if_the_user_is_logged_in_as_an_admin__they_will_see_all_devices_in_the_inventory extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", devicename = "";

	public final void testScript()
	{
		///Step 1 - Login to deviceConnect
		isEventSuccessful = Login();
			
		//**************************************************************************//
		// Step 2: Select the all Status checkboxes
		//**************************************************************************//
		isEventSuccessful = selectStatus("Available,Offline,Disabled,In Use");
		
		
		//**************************************************************************//
		// Step 3 : Verify admin can see all devices : Available
		//**************************************************************************//
		strstepDescription = "Verify admin can see all devices in inventory: Available.";
		strexpectedResult = "Admin user should be able to see 'Available' devices in inventory.";
		                    
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("existenceofstatus", "Available" , "list");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Admin can see all devices in inventory: Available.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		}

		//**************************************************************************//
		// Step 4 : Verify admin can see all devices :  Offline.
		//**************************************************************************//
		strstepDescription = "Verify admin can see all devices in inventory: Offline.";
		strexpectedResult = "Admin user should be able to see 'Offline' devices in inventory.";
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("existenceofstatus", "Offline", "list");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Admin can see all devices in inventory: Offline.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		}

		//**************************************************************************//
		// Step 5 : Verify admin can see all devices : Disabled
		//**************************************************************************//
		strstepDescription = "Verify admin can see all devices in inventory: Disabled.";
		strexpectedResult = "Admin user should be able to see 'Disabled' devices in inventory : Disabled.";
		isEventSuccessful = DisplayDisabledDevices();
		if(isEventSuccessful)
		{
		    isEventSuccessful = VerifyMultipleValuesOfProperty_DI("Status", "Disabled,Connected");
		    if (isEventSuccessful)
		    {
			   reporter.ReportStep(strstepDescription, strexpectedResult, "Admin can see all devices in inventory: Disabled.", "Pass");
		    }
		    else
		    {
		 	  reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		    }
	   }
	   else
	   {
			throw new RuntimeException("disable devices are nto displayed");
	   }

		//**************************************************************************//
		// Step 6 : Verify admin can see all devices : In Use.
		//**************************************************************************//
		strstepDescription = "Verify admin can see all devices in inventory: In Use.";
		strexpectedResult = "Admin user should be able to see 'In Use' devices in inventory : In Use.";
		isEventSuccessful = DisplayInUSeDevices();
		if(isEventSuccessful)
		{
			isEventSuccessful = VerifyDeviceDetailsInGridAndListView("existenceofstatus", "In Use", "list");
		    if (isEventSuccessful)
		    {
			   reporter.ReportStep(strstepDescription, strexpectedResult, "Admin can see all devices in inventory: In Use.", "Pass");
		    }
	    	else
		    {
		     	reporter.ReportStep(strstepDescription, strexpectedResult, "Unable to see In Use Inventory", "Fail");
		    }
		    
		}
		else
	    {
				throw new RuntimeException("In Use devices are not displayed");
		}
		
  
     }
	
}
