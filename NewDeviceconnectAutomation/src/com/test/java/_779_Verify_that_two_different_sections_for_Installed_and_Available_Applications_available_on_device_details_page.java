package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-696
 */
public class _779_Verify_that_two_different_sections_for_Installed_and_Available_Applications_available_on_device_details_page extends ScriptFuncLibrary {


	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "",appName,editedAppName;


	public final void testScript()
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();

			//**********************************************************//
			// Step 2 - Go to Devices Page
			//**********************************************************//                                   
			GoToDevicesPage();
			//**********************************************************//
			// Step 3 - Go to Device Details Page of first Device
			//**********************************************************//     
			GoTofirstDeviceDetailsPage();
			//**********************************************************//
			// Step 4- Verify Installed Apps section is present on page
			//**********************************************************//     
			strStepDescription="Verify Installed apps section is present on the page";
			strExpectedResult="Installed apps section should be there on device details page.";
			waitForPageLoaded();
			isEventSuccessful=PerformAction(dicOR.get("installedAppsSection_DeviceDetailsPage"), Action.isDisplayed);
			if(isEventSuccessful){
				strActualResult="Installed apps section is present on device details page";
			}
			else{
				strActualResult="No Installed apps section on Device details page";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//**********************************************************//
			// Step 5- Verify available apps section is present on page
			//**********************************************************//     
			strStepDescription="Verify Available apps section is present on the page";
			strExpectedResult="Available apps section should be there on device details page.";
			waitForPageLoaded();
			isEventSuccessful=PerformAction(dicOR.get("availableAppsSection_DeviceDetailsPage"), Action.isDisplayed);
			if(isEventSuccessful){
				strActualResult="Available apps section is present on device details page";
			}
			else{
				strActualResult="No available apps section on Device details page";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			
	

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Form factor--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}

	}


}
