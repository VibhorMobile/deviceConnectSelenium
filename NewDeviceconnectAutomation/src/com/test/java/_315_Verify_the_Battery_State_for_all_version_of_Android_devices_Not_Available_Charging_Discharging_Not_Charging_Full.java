package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2200
 */
public class _315_Verify_the_Battery_State_for_all_version_of_Android_devices_Not_Available_Charging_Discharging_Not_Charging_Full extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		
		// /////////////////////////////////////////////////////////////////////////////////////
		// Step 1 - Login to deviceConnect//
		// /////////////////////////////////////////////////////////////////////////////////////
		   isEventSuccessful = Login();

		///////////////////////////////////////////////////////////////////////////////////////
		//Step 2 - Select Available & In Use Android devices.
		///////////////////////////////////////////////////////////////////////////////////////
		isEventSuccessful = selectStatus("Available,In Use");
		if(isEventSuccessful)
		{
			isEventSuccessful = selectPlatform("Android");
			
		}
		else
			return;
		
		
		
		// Verify Battery status is displayed on the device details page after clicking on device name in Device index page.
		strstepDescription = "Verify Battery status on the device details page.";
		strexpectedResult = "Battery status should be displayed on the device details page.";
		try
		{
		    String xpathDevicesHolder = dicOR.get("eleDevicesHolderListView");
		    int Devicescount = getelementCount(xpathDevicesHolder);
		    for(int i =1; i<Devicescount; i++)
		    {
		    	   String deviceNameLink = dicOR.get("eleDeviceName_ListView").replace("__INDEX__", String.valueOf(i));
		    	   isEventSuccessful = PerformAction(deviceNameLink, Action.Click);
		    	   if(isEventSuccessful)
		    	   {
		    		    isEventSuccessful =  VerifyOnDeviceDetailsPage("Battery Status", "Not Available || Charging || Discharging || Not Charging || Full");
		    		    if (isEventSuccessful)
		    		    {
		    			     isEventSuccessful = PerformAction(dicOR.get("eleTopNavTab").replace("__TABNAME__", "Devices"), Action.ClickUsingJS);
		    		    }
		    		   else
		    		   {
		    			   throw new RuntimeException("Could not fetch the Battery status details");
		    		   }
		    	 }
		    	else
		    	{
		    		 throw new RuntimeException("Could not click on device name");
		    	}
		    }
			
			
		}
		catch (RuntimeException e)
        {
			isEventSuccessful = false;
            strErrMsg_AppLib = "Battery Status--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
        }
		
		reporter.ReportStep(strstepDescription, strexpectedResult, "Battery status is being displayed on the device details page.", isEventSuccessful);
		
	}
}