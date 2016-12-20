package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-309
 */
public class _744_Verify_that_Uninstall_button_is_not_enabled_for_devices_in_use_by_other_users extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,inUse,outputText = "";
	Object[] Values = new Object[5]; 
	boolean isUnInstallAllDisabled,isUnInstallDisabled;

 




	public final void testScript() throws InterruptedException, IOException
	{
		try{
			String EmailAddress = dicCommon.get("testerEmailAddress");
			String Password = dicCommon.get("testerPassword");

			//*************************************************************//                     
			// Step 1 : login to deviceConnect with tester user 
			//*************************************************************//  


			isEventSuccessful = Login(EmailAddress,Password);
			//*************************************************************//      
			// Step 2 : Go to Devices Page and get Available device Name
			//*************************************************************//
			GoToDevicesPage();
			selectPlatform("iOS");
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();
			deviceName=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");
			//**************************************************************************//
			// Step 3 : Retain same Device
			//**************************************************************************//

			Values = ExecuteCLICommand("run", "iOS", EmailAddress, Password, deviceName, "");
			isEventSuccessful = (boolean)Values[4];
			outputText=(String)Values[1];
			deviceName=(String)Values[3];
			if (isEventSuccessful)
			{
				strActualResult = "Viewer launched after connecting to an iOS device:  " + Values[3] + " & processfound : " +  Values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
      
      

			reporter.ReportStep("Connect to an iOS device and verify deviceViewer is launched " , "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 4:Logout from Application
			//**************************************************************************//
			isEventSuccessful=Logout();
			if(isEventSuccessful){
				strActualResult="Logged out of application";
			}
			else{
				strActualResult="Not able to Log out from  application";
			}
			reporter.ReportStep("Log out from DC", "Logout successfully", strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 5:Login with Admin credentials
			//**************************************************************************//
			isEventSuccessful= Login();

			

			//**************************************************************************//
			// Step 6:Serach for the same device and Verify reboot button is hidden
			//**************************************************************************//
			GoToDevicesPage();
			selectPlatform("iOS");
			selectStatus("In Use");
			searchDevice(deviceName, "devicename");
			isEventSuccessful = (boolean) GoTofirstDeviceDetailsPage()[0];
			strstepDescription = "Verify UnInstall button is disabled for device in use by other user";
			strExpectedResult = "Uninstall  button is Disabled";
			//PerformAction(dicOR.get("lnkShowDetails_DeviceDetailsPage"),Action.Click);
			waitForPageLoaded();
			isUnInstallAllDisabled=	PerformAction(dicOR.get("BtnUninstallAllDisabled"),Action.isDisplayed);
			//isUnInstallDisabled=PerformAction(dicOR.get("btnUnInstallDisabled"),Action.isDisplayed);
			System.out.println(isUnInstallAllDisabled);
			//System.out.println(isUnInstallDisabled);
            if(isUnInstallAllDisabled){
            	strActualResult="UnInstall option is disabled for devices in use by other User";
            	isEventSuccessful=true;
            }
            else{
            	strActualResult="UnInstall option is enabled for devices in use by other User";
            	isEventSuccessful=false;
            }
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			
			//**************************************************************************//
			// Step 7 : Release device.
			//**************************************************************************//
			ExecuteCLICommand("release", "iOS", EmailAddress, Password, deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
