package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-52
 */
public class _690_Verify_automatic_Uninstall_all_on_releasing_is_working_for_Android_below_4_2_without_any_error extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	Object[] Values = new Object[5]; 
	String OSneedverified[] = {"Android 3.2","Android 2.3.3","Android 4.1.2","Android 4.0.4","Android 4.0.3","Android 3.2"};
	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful = Login();
		
		//*************************************************************//                     
		// Step 2 : Go To DevicesPage.
		//*************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful=GoToDevicesPage();
		}
		
		//**********************************************************//
		//** Step 3 - Select status from Filters dropdown **********//
		//**********************************************************//                                   
		if(isEventSuccessful)	
		{
			isEventSuccessful =  selectStatus("Available");
		}
		
		//**********************************************************//
		//** Step 4 - Select platform from Filters dropdown **********//
		//**********************************************************//
		if(isEventSuccessful)	
		{
			isEventSuccessful =  selectPlatform("Android");
		}
		
		//**********************************************************//
		//** Step 5 - Verify OS available on DeviceIndex page **********//
		//**********************************************************//
		if(isEventSuccessful)	
		{
			strstepDescription = "Verify Android < 4.2 available.";
			strexpectedResult = "Android < 4.2 should be available.";
			isEventSuccessful = VerifyOSonDeviceINdexpage(OSneedverified);
			if(isEventSuccessful)
			{
				strActualResult = "Android < 4.2 is present on device index page" + dicOutput.get("devicename");
			}
			else
			{
				strActualResult = "Android < 4.2 is not present on device index page";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult , strActualResult, isEventSuccessful);
		}		
		//*************************************************************//                     
		// Step 6 : Go To System page and set Reset settings.
		//*************************************************************//                     
		isEventSuccessful = GoToSystemPage();
		if(isEventSuccessful)
		{
			strstepDescription = "Verify Reset settings can be changed.";
			strexpectedResult = "Reset settings should be changed.";
			isEventSuccessful=setDeviceStateSettings(true,true,false);
			if(isEventSuccessful)
			{
				strActualResult="Reset settings done.";
			}
			else
			{
				strActualResult="Unable to change reset settings.";
			}
			reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);
		}

		//**************************************************************************//
		// Step 7 : Connect to  device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	 
			 isEventSuccessful=GoToDevicesPage();
			 if(isEventSuccessful)
			 {
				 Values = ExecuteCLICommand("run", "Android", EmailAddress, Password, dicOutput.get("devicename"), "");
				 isEventSuccessful = (boolean)Values[2];
				 deviceName=(String)Values[3];
				 if (isEventSuccessful)
				 {
					 strActualResult = "device Viewer launched after connecting to  device:  " + Values[3];
				 }
				 else
				 {
					 strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
				 }
				 reporter.ReportStep("Verify device Viewer is launched " , "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
			 }
				
		}
		
		
		//**************************************************************************//
		// Step 8 : Reset device via CLI command.
		//**************************************************************************//
		Values = ExecuteCLIResetCommands("releasereset", "Android", EmailAddress, Password, deviceName, "","");
		isEventSuccessful = (boolean)Values[2];
		if (isEventSuccessful)
		{
			strActualResult = "Device relase and reset.";
		}
		else
		{
			strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
		}
		reporter.ReportStep("Verify device get released and reset." , "Device should get released and reset.", strActualResult, isEventSuccessful);
			
		
		
		
		//**************************************************************************/
		// Step 9 : Release device.
		//**************************************************************************//
		if(!deviceName.isEmpty())
		{
			ExecuteCLICommand("release", "Android", EmailAddress, Password, deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
		}
		
		//**************************************************************************/
		// Step 10 : Verify all applications gets uninstalled.
		//**************************************************************************//
		isEventSuccessful =  selectStatus("Available,In Use,Offline");	
		
		if(isEventSuccessful)
		{
			isEventSuccessful=GoToSpecificDeviceDetailsPage(deviceName);
			isEventSuccessful=ShowDetails();
			isEventSuccessful = PerformAction(dicOR.get("eleStatusIcon_DeviceDetails").replace("__STATUS__", "Available"), Action.WaitForElement, "120");
			if (isEventSuccessful)
			{
				strActualResult = "Available icon is displayed in front of device name , i.e. it is online now.";
			}
			else
			{
				strActualResult = "Device status icon did not change to 'Available icon', i.e. it did not comne back online even after waiting for 1 minute.";
			}
			reporter.ReportStep("Wait until the 'Available' status icon is displayed.", "Available icon should be displayed on device details page.", strActualResult, isEventSuccessful);
			if(isEventSuccessful)
			{
				strstepDescription = "Verify application list is empty.";
				strexpectedResult = "Application list should be empty.";
				if(getelementsList(dicOR.get("deviceDetailsUnInstallFirstApp").replace("tr[1]", "tr[*]")+"/td/a[2]").size()<1)
				{
					strActualResult="Apllication list is empty";
					reporter.ReportStep(strstepDescription ,strexpectedResult, strActualResult, true);
				}
				else
				{
					strActualResult="Application list is not empty";
					reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, false);
				}
			}
		}
		
		//*************************************************************//                     
		// Step 11 : Go To System page and set Reset settings.
		//*************************************************************//                     
		isEventSuccessful = GoToSystemPage();
		if(isEventSuccessful)
		{
			strstepDescription = "Verify Reset settings can be changed.";
			strexpectedResult = "Reset settings should be changed.";
			isEventSuccessful=setDeviceStateSettings(false,false,false);
			if(isEventSuccessful)
			{
				strActualResult="Reset settings done.";
			}
			else
			{
				strActualResult="Unable to change reset settings.";
			}
			reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);
		}

		
		
		
	}
}