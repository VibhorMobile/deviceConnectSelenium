package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Jira Test Case Id: QA-2193
 */

public class _218_Verify_that_when_the_session_is_ended_forcibly_then_device_status_changes_to_available extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "", devicename = "";
	Object[] Values = new Object[4]; 
	
	public final void testScript()
	{
		///**************************************************************************//
		//Step 1 - : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
	   	isEventSuccessful = Login();
		
		//**************************************************************************//
		//Step 3 - Connect to iOS device
		//**************************************************************************//
	   	Values = ExecuteCLICommand("run", "iOS");
  	    isEventSuccessful = (boolean)Values[2];
		if (isEventSuccessful)
			{
			   strActualResult = "Viewer launched after connecting to an Android device:  " + Values[0] + " & processfound : " +  Values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
		
		reporter.ReportStep("Connect to an android device and verify deviceViewer is launched with" + dicOutput.get("executedCommand"), "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}

		//**************************************************************************//
		// Step 4 : Select filter of Status : In Use
		//**************************************************************************//
		PerformAction("", Action.WaitForElement,"10");
		isEventSuccessful = selectStatus( "In Use");
		

		//**************************************************************************//
		//Step 5 - Verify Device Status
		//**************************************************************************//
		devicename = (String) Values[3];
		isEventSuccessful = SelectDevice("devicename", devicename);
		 if (isEventSuccessful)
		 {
			isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
			if (isEventSuccessful)
			{
				if (VerifyOnDeviceDetailsPage("Status ", "CONTAINS__" + "In Use")) //GetSingleDeviceDetails(devicename, "devicestatus").Contains("In Use"))
				{
					isEventSuccessful = true;
					strActualResult = "Device status is changed to 'In-Use' after Viewer is opened.";
				}
				else
				{
					isEventSuccessful = false;
					strActualResult = "VerifyOnDeviceDetailsPage---" + strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "Not able to click on Show details... link.";
			}
		}
		else
		{
			strActualResult = "SelectDevice---" + strErrMsg_AppLib + "\r\n Devicename : " + devicename;
		}

		reporter.ReportStep("Verify Device Status.", "Device Status should be In Use.", strActualResult, isEventSuccessful);

		//**************************************************************************//
		//Step 6 - Click on Release button
		//**************************************************************************//
		isEventSuccessful = PerformAction("btnRelease", Action.Click);
		if (isEventSuccessful)
		{
			PerformAction("", Action.WaitForElement, "30");
			isEventSuccessful = PerformAction("btnReleaseDevice", Action.Click);

			isEventSuccessful = VerifyOnDeviceDetailsPage("Status ", "CONTAINS__" + "Available"); //GetSingleDeviceDetails(devicename, "devicestatus").Contains("In Use"))
			if (isEventSuccessful)
			{
				strActualResult = "Device status is changed to  Available after clicking release button.";
			}
			else
			{
				strActualResult = "VerifyOnDeviceDetailsPage---" + strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "Could not click on 'Release' button.";
		}
		reporter.ReportStep("Click on 'Release' button.", "Device status should be changed to  Available after clicking on release button.", strActualResult, isEventSuccessful);
		
		//**************************************************************************//
		//Step 4 - Close Viewer
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", "", "", devicename, "","","" );
		isEventSuccessful =	KillObjectInstances("MobileLabs.deviceViewer.exe");
     	reporter.ReportStep("Verfiy Device is not connected after closing devcieViewer window","Device should not be connected after closing devcieViewer window","Device is not connected after closing devcieViewer window",isEventSuccessful);
	}
}