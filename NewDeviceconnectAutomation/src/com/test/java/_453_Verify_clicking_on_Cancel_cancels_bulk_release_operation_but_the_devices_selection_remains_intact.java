package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1314
 */

public class _453_Verify_clicking_on_Cancel_cancels_bulk_release_operation_but_the_devices_selection_remains_intact extends ScriptFuncLibrary
{
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
//C# TO JAVA CONVERTER WARNING: There is no Java equivalent to C#'s shadowing via the 'new' keyword:
//ORIGINAL LINE: new string strActualResult = "";
	private String strActualResult = ""; // new is appended as this hides the inherited class's(ScriptFuncLibrary's) strActualResult variable
	private java.util.List<String> devicesSelected = new java.util.ArrayList<String>();
	private java.util.List<String> devicesSelected_afterCancel = new java.util.ArrayList<String>();
	Object[] Values = new Object[4]; 

	//*******************************************************************************************************************************************************************************************************************//
	// Select available status checkbox, click on Reboot button, then click on 'Cancel' button on dialog and verify that the user returns to devices index page. Also, verify that all the checkboxes are still selected.//
	//*******************************************************************************************************************************************************************************************************************//
	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		//********************************************************//
		//** Step 1 : Launch deviceConnect and verify homepage. **//
		//********************************************************//
		isEventSuccessful = Login(EmailAddress, Password);
		
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
				
		
		//*********************************************//
		// Step 3 : Select available from status filters //
		//*********************************************//
		isEventSuccessful = selectStatus("In Use");

		//*****************************************************//
		// Step 4 : Check if there are In Use devices present.//
		//*****************************************************//
		strstepDescription = "Check if there are In Use devices.";
		strexpectedResult = "There should be In Use devices displayed on the devices index page.";
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("devicestatus", "In Use", "list");
		if (isEventSuccessful)
		{
			strActualResult = "In Use devices are displayed on the page.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		if (!isEventSuccessful) // Return if no removed devices are displayed on the page.
		{
			return;
		}

		//************************************************************************************//
		// Step 5 : Click on select all checkbox on top of all devices on devices index page. //
		//************************************************************************************//
		isEventSuccessful = (boolean)selectAllDevicesCheckbox_DI()[0];
		
		//****************************************************************//
		// Step 6 : Click on 'Release' button and verify 'Release devices' dialog.//
		//****************************************************************//
		strstepDescription = "Click on 'Release' button and verify 'Release devices' dialog.";
		strexpectedResult = "'Release Devices' dialog should be displayed.";
		isEventSuccessful = PerformAction("btnRelease_Devices", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("hdrReleaseDevice", Action.WaitForElement, "20");
			if (isEventSuccessful)
			{
				strActualResult = "Release device dialog displayed successfully after clicking on 'Release' button.";
			}
			else
			{
				strActualResult = "'Release Device' dialog not displayed after clicking on 'Release' button.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Release' button.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//***********************************************************************************//
		// Step 7 : Click on 'Cancel' button and verify user returns to devices index page.";//
		//***********************************************************************************//
		strstepDescription = "Click on 'Cancel' button and verify user returns to devices index page."; //";
		strexpectedResult = "'Release Device' dialog should disappear and user should return to devices index page.";
		isEventSuccessful = PerformAction("btnCancel", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("hdrReleaseDevice", Action.isNotDisplayed);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleDevicesTab_Devices", Action.isDisplayed);
				if (isEventSuccessful)
				{
					strActualResult = "'Release Device' dialog closed and user returned to devices index page after clicking on 'Cancel' button.";
				}
				else
				{
					strActualResult = "Devices index page is not displayed.";
				}
			}
			else
			{
				strActualResult = "Release device dialog did not disappear after clicking on 'Cancel' button." + "<br> Reason (GenLib error): '" + strErrMsg_GenLib + "'.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Cancel' button.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*******************************************************//
		// Step 8 : Verify that the select all checkbox is still selected.//
		//*******************************************************//
		strstepDescription = "Verify that the select all checkbox is still selected.";
		strexpectedResult = "Select all checkbox should remain checked.";
		isEventSuccessful = PerformAction("chkSelectAll_Devices", Action.isSelected);
		if (isEventSuccessful)
		{
			strActualResult = "Select all checkbox is still selected.";
		}
		else if (PerformAction("chkSelectAll_Devices", Action.isNotSelected))
		{
			strActualResult = "Select all checkbox is not selected.";
		}
		else
		{
			strActualResult = "Select all checkbox not found on page.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************************//
		// Step 9 : Verify that checkboxes in front of all the devices are checked.//
		//*************************************************************************//
		strstepDescription = "Verify that checkboxes in front of all the devices are checked.";
		strexpectedResult = "Checkboxes in front of all the devices should be checked/selected.";
		isEventSuccessful = (boolean)VerifyAllCheckedOrUnchecked_DI(Action.isSelected)[0];
		if (isEventSuccessful)
		{
			strActualResult = "All checkboxes are still checked/selected.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}