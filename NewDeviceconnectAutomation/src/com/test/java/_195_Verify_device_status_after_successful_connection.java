package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2189
 */
public class _195_Verify_device_status_after_successful_connection extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", devicename = "";
	private String xpath = "";
	Object[] Values = new Object[4]; 

	public final void testScript()
	{
		///**************************************************************************//
		//Step 1 - : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		isEventSuccessful = Login();


		//**************************************************************************//
		// Step 2 : Connect to an android device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("run", "Android");
	     	 isEventSuccessful = (boolean)Values[2];
	     	 devicename = (String) Values[3];
	     	 if (isEventSuccessful)
	     	 {
	     		 strActualResult = "Viewer launched after connecting to an Android device:  " + Values[0] + " & processfound : " +  Values[1];
	     	 }
	     	 else
	     	 {
	     		 strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
	     	 }
			
		}
		else
		{
			return; // Return if in use is not selected.
		}
		 reporter.ReportStep("Connect to an android device and verify deviceViewer is launched with" + dicOutput.get("executedCommand"), "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}


		//**************************************************************************//
		// Step 3 : Select filter of Status : In Use
		//**************************************************************************//
         PerformAction("chkStatus_Devices",Action.WaitForElement,"8");
		 isEventSuccessful = selectStatus( "In Use");
		   if (isEventSuccessful)
		    {
			   strActualResult = "Able to Filter Platform Android and In Use.";
		    }
		   else
		    {
			  strActualResult = "SelectFromFilterDropdowns---" + strErrMsg_AppLib;
		    }
		reporter.ReportStep("Verify filters get selected to check In Use device.", "Filter should get selected.", strActualResult, isEventSuccessful);


		//**************************************************************************//
		// Step 5 : Verify device status is changed to 'In-Use'.
		//**************************************************************************//
		strstepDescription = "Verify device status is changed to 'In-Use'.";
		strexpectedResult = "Device status should be changed to 'In-Use'.";
		if (GetSingleDeviceDetails(devicename, "devicestatus","list").contains("In Use"))
		{
			isEventSuccessful = true;
			strActualResult = "Device status is changed to 'In-Use' after Viewer is opened.";
		}
		else
		{
			isEventSuccessful = false;
			strActualResult = "GetSingleDeviceDetails---" + strErrMsg_GenLib;
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		//Step 6 - Post Condition - Close viewer
		//**************************************************************************//
		ExecuteCLICommand("release", "Android", "", "", devicename, "","","" );
		isEventSuccessful =	KillObjectInstances("MobileLabs.deviceViewer.exe");
		reporter.ReportStep("Verfiy Device is not connected after closing devcieViewer window","Device should not be connected after closing devcieViewer window","Device is not connected after closing devcieViewer window",isEventSuccessful);
	
	}
}