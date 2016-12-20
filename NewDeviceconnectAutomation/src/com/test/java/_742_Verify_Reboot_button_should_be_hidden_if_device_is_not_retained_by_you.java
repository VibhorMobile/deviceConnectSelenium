package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-773
 */
public class _742_Verify_Reboot_button_should_be_hidden_if_device_is_not_retained_by_you extends ScriptFuncLibrary{
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,inUse,outputText = "";
	Object[] Values = new Object[5]; 
	boolean isRebootDisplayed;

 




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
			selectPlatform("Android");
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();
			deviceName=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");
			//**************************************************************************//
			// Step 3 : Retain same Device
			//**************************************************************************//

			Values = ExecuteCLICommand("run", "Adroid", EmailAddress, Password, deviceName, "");
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
			selectPlatform("Android");
			searchDevice(deviceName, "devicename");
			isEventSuccessful = (boolean) GoTofirstDeviceDetailsPage()[0];
			strstepDescription = "Verify Reboot button not displayed";
			strExpectedResult = "Reboot button is hidden for other users ";
			waitForPageLoaded();
			PerformAction(dicOR.get("lnkShowDetails_DeviceDetailsPage"),Action.Click);
			waitForPageLoaded();
			isRebootDisplayed=	PerformAction(dicOR.get("btnReboot"),Action.isDisplayed);
            if(isRebootDisplayed){
            	strActualResult="reboot option is displayed for the device not displayed by  user";
            	isEventSuccessful=false;
            }
            else{
            	strActualResult="reboot option is hidden for the device not displayed by  user";
            	isEventSuccessful=true;
            }
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			
			//**************************************************************************//
			// Step 7 : Release device.
			//**************************************************************************//
			ExecuteCLICommand("release", "Android", EmailAddress, Password, deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
			 
		
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
