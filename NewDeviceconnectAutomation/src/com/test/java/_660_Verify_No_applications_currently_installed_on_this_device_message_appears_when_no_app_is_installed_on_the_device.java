package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1342
 */
public class _660_Verify_No_applications_currently_installed_on_this_device_message_appears_when_no_app_is_installed_on_the_device extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="",expectedText="Usage: MobileLabs.DeviceConnect.Cli <host> <username> <password> [options]";
	private String installedApps = "";
	Object[] values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login();


		//*************************************************************//                     
		// Step 2 : Navigate to first available device details page
		//*************************************************************//  
		isEventSuccessful =GoToDevicesPage();  //Navigates to Devices page
        
        //Applying filters
        isEventSuccessful = selectPlatform("Android,iOS");
        isEventSuccessful = selectStatus("Available");
        
        //Selecting first device
        values=GoTofirstDeviceDetailsPage(); 
        

		//*************************************************************//                     
		// Step 3 : Click on uninstall all button to uninstall all applications
		//*************************************************************//  
        //installedApps=getInstalledApps();
        
        if (!installedApps.contains("No applications installed"))
        {
        	PerformAction(dicOR.get("BtnUninstallAllEnabled"), Action.Click);
        	PerformAction("browser","waitforpagetoload");
        	
        }
        
        
		
	}
}