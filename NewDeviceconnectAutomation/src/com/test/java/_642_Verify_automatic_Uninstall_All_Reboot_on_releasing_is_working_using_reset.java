package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1861
 */
public class _642_Verify_automatic_Uninstall_All_Reboot_on_releasing_is_working_using_reset extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	Object[] Values = new Object[5]; 
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
		// Step 2 : Go To System page and set Reset settings.
		//*************************************************************//                     
		isEventSuccessful = GoToSystemPage();
		if(isEventSuccessful)
		{
			strstepDescription = "Verify Reset settings can be changed.";
			strexpectedResult = "Reset settings should be changed.";
			isEventSuccessful=setDeviceStateSettings(true,false,true);
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
		// Step 3 : Connect to  device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	 
			 isEventSuccessful=GoToDevicesPage();
			 if(isEventSuccessful)
			 {
				 Values = ExecuteCLICommand("run", "Android", EmailAddress, Password, "", "");
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
		// Step 4 : Reset device via CLI command.
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
		// Step 5 : Release device.
		//**************************************************************************//
		if(!deviceName.isEmpty())
		{
			ExecuteCLICommand("release", "Android", EmailAddress, Password, deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
		}
		
		//**************************************************************************/
		// Step 6 : Verify all applications gets uninstalled.
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
				strstepDescription = "Verify device rebooted.";
				strexpectedResult = "Device should be rebooted.";
				if(!GetTextOrValue(dicOR.get("OnlinesinceLocator").replace("li[6]", "li[5]"),"text").contains("m"))
				{
					strActualResult="Device rebooted recently.";
					reporter.ReportStep(strstepDescription ,strexpectedResult, strActualResult, true);
				}
				else
				{
					strActualResult="Device did not rebooted.";
					reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, false);
				}
			}
		}
		
		//*************************************************************//                     
		// Step 7 : Go To System page and set Reset settings.
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
		// Step 8 : Connect to  device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	 
			isEventSuccessful=GoToDevicesPage();
			if(isEventSuccessful)
			{
				Values = ExecuteCLICommand("run", "Android", EmailAddress, Password, "", "");
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
		// Step 9 : Reset device via CLI command.
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
		// Step 10 : Release device.
		//**************************************************************************//
		if(!deviceName.isEmpty())
		{
			ExecuteCLICommand("release", "Android", EmailAddress, Password, deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
		}
		
		//**************************************************************************/
		// Step 11 : Verify all applications gets uninstalled.
		//**************************************************************************//
		isEventSuccessful =  selectStatus("Available,In Use,Offline");	

		if(isEventSuccessful)
		{
			isEventSuccessful=GoToSpecificDeviceDetailsPage(deviceName);
			PerformAction("browser",Action.Refresh);
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
		// Step 12 : Go To System page and set Reset settings.
		//*************************************************************//                     
		isEventSuccessful = GoToSystemPage();
		if(isEventSuccessful)
		{
			strstepDescription = "Verify Reset settings can be changed.";
			strexpectedResult = "Reset settings should be changed.";
			isEventSuccessful=setDeviceStateSettings(true,true,true);
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
		// Step 13 : Connect to  device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	 
			isEventSuccessful=GoToDevicesPage();
			if(isEventSuccessful)
			{
				Values = ExecuteCLICommand("run", "Android", EmailAddress, Password, "", "");
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
		// Step 14 : Reset device via CLI command.
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
		// Step 15 : Release device.
		//**************************************************************************//
		if(!deviceName.isEmpty())
		{
			ExecuteCLICommand("release", "Android", EmailAddress, Password, deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
		}
		
		//**************************************************************************/
		// Step 16 : Verify all applications gets uninstalled and device rebooted.
		//**************************************************************************//
		isEventSuccessful =  selectStatus("Available,In Use,Offline");	

		if(isEventSuccessful)
		{
			isEventSuccessful=GoToSpecificDeviceDetailsPage(deviceName);
			PerformAction("browser",Action.Refresh);
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
				strstepDescription = "Verify device rebooted.";
				strexpectedResult = "Device should be rebooted.";
				if(!GetTextOrValue(dicOR.get("OnlinesinceLocator").replace("li[6]", "li[5]"),"text").contains("m"))
				{
					strActualResult="Device rebooted recently.";
					reporter.ReportStep(strstepDescription ,strexpectedResult, strActualResult, true);
				}
				else
				{
					strActualResult="Device did not rebooted.";
					reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, false);
				}
			}
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
		// Step 17 : Go To System page and set Reset settings.
		//*************************************************************//                     
		isEventSuccessful = GoToSystemPage();
		if(isEventSuccessful)
		{
			strstepDescription = "Verify Reset settings can be changed.";
			strexpectedResult = "Reset settings should be changed.";
			isEventSuccessful=setDeviceStateSettings(true,false,false);
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
		// Step 18 : Connect to  device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	 
			isEventSuccessful=GoToDevicesPage();
			if(isEventSuccessful)
			{
				Values = ExecuteCLICommand("run", "Android", EmailAddress, Password, "", "");
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
		// Step 19 : Reset device via CLI command.
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
		// Step 20 : Release device.
		//**************************************************************************//
		if(!deviceName.isEmpty())
		{
			ExecuteCLICommand("release", "Android", EmailAddress, Password, deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
		}

		//**************************************************************************/
		// Step 21 : Verify applications did not get uninstalled and device did not rebooted.
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
				strstepDescription = "Verify device did not rebooted.";
				strexpectedResult = "Device should not be rebooted.";
				PerformAction("OnlinesinceLocator",Action.WaitForElement);
				if(GetTextOrValue(dicOR.get("OnlinesinceLocator").replace("li[6]", "li[5]"),"text").contains("m"))
				{
					strActualResult="Device did not rebooted recently.";
					reporter.ReportStep(strstepDescription ,strexpectedResult, strActualResult, true);
				}
				else
				{
					strActualResult="Device rebooted recently.";
					reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, false);
				}
			}
			if(isEventSuccessful)
			{
				strstepDescription = "Verify application list is not empty.";
				strexpectedResult = "Application list should not be empty.";
				if(getelementsList(dicOR.get("deviceDetailsUnInstallFirstApp").replace("tr[1]", "tr[*]")+"/td/a[2]").size()>0)
				{
					strActualResult="Apllication list is not empty";
					reporter.ReportStep(strstepDescription ,strexpectedResult, strActualResult, true);
				}
				else
				{
					strActualResult="Application list is empty";
					reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, false);
				}
			}
		}
		
	}
}