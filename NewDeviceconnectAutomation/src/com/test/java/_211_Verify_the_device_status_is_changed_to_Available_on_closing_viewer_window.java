package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test case Id: QA-2199
 */

public class _211_Verify_the_device_status_is_changed_to_Available_on_closing_viewer_window extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "", devicename = "";
	Object[] Values = new Object[4]; 

	public final void testScript() throws IOException
	{
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		///**************************************************************************//
		//Step 1 - : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		isEventSuccessful = Login();

		//**************************************************************************//
		//Step 2 - Connect to iOS device using CLI 
		//**************************************************************************//

		Values = ExecuteCLICommand("run", "iOS", EmailAddress, Password, "", "");
		isEventSuccessful = (boolean)Values[2];
		devicename = (String) Values[3];
		if (isEventSuccessful)
		{
			strActualResult = "Viewer launched after connecting to an iOS device:  " + Values[0] + " & processfound : " +  Values[1];
		}
		else
		{
			strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
		}
		
		reporter.ReportStep("Connect to an android device and verify deviceViewer is launched ", "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
    		  return;
		}
	
		
    	  
		//**************************************************************************//
		//Step 3 - Releasing iOS device using CLI 
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", EmailAddress, Password, devicename, "","","" );
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
		
		//**************************************************************************//
		//Step 4 - Close Viewer
		//**************************************************************************//
		isEventSuccessful =	KillObjectInstances("MobileLabs.deviceViewer.exe");
     	reporter.ReportStep("Verfiy Device is not connected after closing devcieViewer window","Device should not be connected after closing devcieViewer window","Device is not connected after closing devcieViewer window",isEventSuccessful);
     
     	
		//**************************************************************************//
		// Step 5 : Select filter of Status : Available
		//**************************************************************************//
     	PerformAction("", Action.WaitForElement,"15");
     	isEventSuccessful = selectStatus( "Available");
		if (isEventSuccessful)
		{
			strActualResult = "Able to Filter Available.";
		}
		else
		{
			strActualResult = "SelectFromFilterDropdowns---" + strErrMsg_AppLib;
		}
		reporter.ReportStep("Verify filters get selected to check Available device.", "Filter should get selected.", strActualResult, isEventSuccessful);

		//**************************************************************************//
		//Step 6 - Verify Device Status
		//**************************************************************************//
		 devicename = (String) Values[3];
		 PerformAction("", Action.WaitForElement,"10");
		 
		 if (GetSingleDeviceDetails(devicename, "devicestatus","list").contains("Available"))
		 {
		 	isEventSuccessful = true;
			strActualResult = "Device status is changed to 'Available' after Viewer is closed.";
		 }
		 else
		 {
			isEventSuccessful = false;
			strActualResult = "GetSingleDeviceDetails---" + strErrMsg_GenLib;
		 }

		reporter.ReportStep("Verify Device Status.", "Device Status should be Available.", strActualResult, isEventSuccessful);

	}
}