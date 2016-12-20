package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1222
 */
public class _686_Verify_admin_unable_to_disable_devices_in_use_by_other_users_before_releasing_it extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";;
	private int counter=0;
	Object[] Values = new Object[5];

	public final void testScript() throws IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect.
		//*************************************************************//                     
		isEventSuccessful=Login();
		
		//**************************************************************************//
		// Step 2 : Connect to an iOS device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
			Values = ExecuteCLICommand("client", "iOS", EmailAddress, Password, "", "desktop");
			isEventSuccessful = (boolean)Values[4];
			outputText=(String)Values[1];
			deviceName=(String)Values[3];
			if (isEventSuccessful && outputText.contains("MobileLabs.deviceViewer.exe"))
			{
				strActualResult = "desktop Viewer launched after connecting to an iOS device:  " + Values[3] + " & processfound : " +  Values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify desktop deviceViewer is launched " , "User should get connected and desktop deviceviewer should get launched.", strActualResult, isEventSuccessful);
		}
		
		//**********************************************************//
		//** Step 3 - Search device using search text box and verify it **********//
		//**********************************************************//  
		if (isEventSuccessful)
		{
			isEventSuccessful =  selectStatus("Available,In Use");
			searchDevice(deviceName, "devicename");
		}
		
		//**********************************************************//
		//** Step 3 - Select the device **********//
		//**********************************************************// 
		if (isEventSuccessful)
		{
			isEventSuccessful=(Boolean)selectFirstDeviceChk_DI()[0];
		}
		
		//**********************************************************//
		//** Step 4 - Verify disable button did not displayed **********//
		//**********************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify Disable button is not available.";
			strexpectedResult = "Disable button should not be available.";
			isEventSuccessful=PerformAction("btnBulkDisable_Devices",Action.isNotDisplayed);
			if(isEventSuccessful)
			{
				strActualResult = "Disable button is not available.";
			}
			else
			{
				strActualResult = "Disable button is available";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 5 : Release device.
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", EmailAddress, Password, deviceName, "","","" );
		Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
				
		//**********************************************************//
		//** Step 6 - Select the device **********//
		//**********************************************************// 
		if (isEventSuccessful)
		{
			searchDevice(deviceName, "devicename");
			isEventSuccessful=(Boolean)selectFirstDeviceChk_DI()[0];
		}

		//**********************************************************//
		//** Step 7 - Verify disable button displayed **********//
		//**********************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify Disable button is available.";
			strexpectedResult = "Disable button should be available.";
			isEventSuccessful=PerformAction("btnBulkDisable_Devices",Action.isDisplayed);
			if(isEventSuccessful)
			{
				strActualResult = "Disable button is available.";
			}
			else
			{
				strActualResult = "Disable button is not available";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
	}	
}