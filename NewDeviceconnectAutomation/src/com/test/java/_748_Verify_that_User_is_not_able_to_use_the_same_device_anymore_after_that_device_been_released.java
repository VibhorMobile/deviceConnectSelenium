package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1452
 */
public class _748_Verify_that_User_is_not_able_to_use_the_same_device_anymore_after_that_device_been_released extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,deviceStatus,outputText = "";
	Object[] Values = new Object[5]; 
	boolean isRetained,isReleased;






	public final void testScript() throws InterruptedException, IOException
	{
		try{
			String EmailAddress = dicCommon.get("testerEmailAddress");
			String Password = dicCommon.get("testerPassword");

			//*************************************************************//                     
			// Step 1 : login to deviceConnect with Tester User
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
			// Step 3 : Retain same Device and Verify Status
			//**************************************************************************//
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
			// Step 4 : Logout From DC
			//**************************************************************************//
			Logout();
			
			//**************************************************************************//
			// Step 5 : Login with Admin Credentials
			//**************************************************************************//
			isEventSuccessful = Login();
			
			//**************************************************************************//
			// Step 6 : Go to Device details Page for the device retained by tester user
			//**************************************************************************//
			GoToDevicesPage();
			selectPlatform("iOS");
			searchDevice(deviceName, "devicename");
			isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0];
			 
			//**************************************************************************//
			// Step 6 : Release Device 
			//**************************************************************************//
			strStepDescription = "Release the device retained by Tester User";
			strExpectedResult = "Device Released succesfully";
			waitForPageLoaded();
			isEventSuccessful=PerformAction(dicOR.get("btnRelease"), Action.ClickUsingJS);
			waitForPageLoaded();
			isEventSuccessful=PerformAction(dicOR.get("btnReleaseDevice"), Action.ClickUsingJS);
			PerformAction("browser",Action.Refresh);
			if(isEventSuccessful){
				strActualResult="Clicked on Release button for device retained by tester user";
			}
			else{
				strActualResult="Unable to click on Release button";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			
			//**************************************************************************//
			// Step 7 : Verify Status of Device is Available
			//**************************************************************************//
			strStepDescription="Verify Device is now coming with available status";
			strExpectedResult="Device is coming with status as available";
			waitForPageLoaded();
			isEventSuccessful = PerformAction("lnkShowDetails", Action.ClickUsingJS);
			deviceStatus=GetTextOrValue(dicOR.get("eleStatus_DeviceDetailsPage"), "text");
			if(deviceStatus.equals("Available")){
				
				strActualResult="Status for device in details page is coming as "+deviceStatus;
				isEventSuccessful=true;
			}
			else{
				strActualResult="Status for device in details page is coming as "+deviceStatus;
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 8: Logout of the application
			//**************************************************************************//
			Logout();
			
			//**************************************************************************//
			// Step 9: Login with tester credentials again
			//**************************************************************************//
			Login(EmailAddress,Password);
			
			//**************************************************************************//
			// Step 10: Verify Device status is available for tester User also
			//**************************************************************************//
			strStepDescription="Verify Device is now coming with available status for tester user also";
			strExpectedResult="Device is coming with status as available";
			GoToDevicesPage();
			selectPlatform("iOS");
			searchDevice(deviceName, "devicename");
			isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0];
			waitForPageLoaded();
			isEventSuccessful = PerformAction("lnkShowDetails", Action.ClickUsingJS);
			deviceStatus=GetTextOrValue(dicOR.get("eleStatus_DeviceDetailsPage"), "text");
			if(deviceStatus.equals("Available")){
				
				strActualResult="Status for device in details page is coming as "+deviceStatus;
				isEventSuccessful=true;
			}
			else{
				strActualResult="Status for device in details page is coming as "+deviceStatus;
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
			
		
						 

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


	
	
}
