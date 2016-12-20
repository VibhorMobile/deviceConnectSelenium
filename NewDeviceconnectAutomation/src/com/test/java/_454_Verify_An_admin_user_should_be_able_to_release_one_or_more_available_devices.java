package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1305
 */

public class _454_Verify_An_admin_user_should_be_able_to_release_one_or_more_available_devices extends ScriptFuncLibrary
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
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid admin user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);


		//**************************************************************************//
		// Step 2 : Connect to an android device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("run", "Android", EmailAddress, Password);
	     	  isEventSuccessful = (boolean)Values[2];
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
		
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("run", "Android", EmailAddress, Password);
	     	  isEventSuccessful = (boolean)Values[2];
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

		isEventSuccessful = selectStatus("In Use");
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
		// Step 4 : Check if warning message is displayed.
		//**************************************************************************//
		isEventSuccessful = !(VerifyMessage_On_Filter_Selection());

		//**************************************************************************//
		// Step 5 : Select first 'In-Use' device and release this device.
		//**************************************************************************//
		isEventSuccessful = selectStatus("In Use");
		if(isEventSuccessful)
		{
			selectAllDevicesCheckbox_DI();
			PerformAction("chkDeviceName_Devices", Action.DeSelectCheckbox);
			PerformAction("btnRelease_Devices", Action.Click);
			isEventSuccessful=PerformAction("btnReleaseDevices", Action.Click);
		}
		
		strstepDescription = "Verify that admin can release multiple devices.";
		strexpectedResult = "Admin should be able to release multiple devices.";
		
		if (isEventSuccessful)
		{
			strActualResult = "Admin is able to release multiple devices.";
		}
		else
		{
			strActualResult = "Release Devices---" + strErrMsg_GenLib;
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		//Step 6 - Post Condition - Close viewer
		//**************************************************************************//
		isEventSuccessful =	KillObjectInstances("MobileLabs.deviceViewer.exe");
		
	}
}