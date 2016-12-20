package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-470
 */
public class _802_Verify_Uninstall_All_button_does_not_appear_as_disabled_on_device_details_page_when_it_is_enabled extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String  strActualResult = "",pageHeader,onlineSince,offlineSince, outputText = "",InUseSince,deviceNameUI,deviceName;
	private String [] Options={"Connected Since","Offline Since","In Use Since"};

	Object[] Values = new Object[5]; 
	public final void testScript() throws InterruptedException
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();

			//**********************************************************//
			// Step 2 - Go to Devices and First Device details Page
			//**********************************************************//  
			GoToDevicesPage();
			GoTofirstDeviceDetailsPage();

			//**********************************************************//
			// Step 2 - Verify that Uninstall all button not disabled.
			//**********************************************************//  
			isEventSuccessful=PerformAction(dicOR.get("NoAppsInstalled_DeviceDetailsPage"), Action.isDisplayed);
			if(isEventSuccessful){
				isEventSuccessful=installAppOnDevice("deviceControl");
			}
			isEventSuccessful=!PerformAction(dicOR.get("BtnUninstallAllDisabled"), Action.isDisplayed);
			if(isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("BtnUninstallAllEnabled"), Action.isDisplayed);
				if(isEventSuccessful){
					strActualResult="UnInstallAll button is in Enabled State";	
				}
				else{
					strActualResult="Enabled UnInstallAll button Not Displayed";	
				}
			}
			else{
				strActualResult="UnInstallAll button is in Disabled State";
			}
			reporter.ReportStep("Verify Uninstall All button not disabled", "Uninstall all button not disabled", strActualResult, isEventSuccessful);


		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
