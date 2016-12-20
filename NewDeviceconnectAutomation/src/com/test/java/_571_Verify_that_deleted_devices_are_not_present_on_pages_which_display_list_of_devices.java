package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: 11-Feb-2016
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-152, QA-1170
 */

public class _571_Verify_that_deleted_devices_are_not_present_on_pages_which_display_list_of_devices extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strexpectedResult = "", strActualResult = "",strstepDescription="",devicename="";
	String [] android={"Android"};
	
	public final void testScript()
	{
		
		//*******************************//
		// Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to Devices Page //
		//**********************************************************//   
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToDevicesPage();
		}
		
		//**************************************************************************//
		// Step 3 - Select status "Offline".
		//**************************************************************************//
		isEventSuccessful=selectPlatform("Android");
		isEventSuccessful=selectStatus("Offline");
		devicename=(String)GetDeviceDetails(1,"devicename","list","all")[1];
		
		//****************************************************************************************//
		// Step 4 - Select first device **//
		//****************************************************************************************//
		if (isEventSuccessful) 
		{
			isEventSuccessful=(Boolean)selectFirstDeviceChk_DI()[0];
		}
		
		//*************************************************************//                     
		// Step 5 : Remove first device.
		//*************************************************************//  
		if (isEventSuccessful) 
		{
			PerformAction("btnRefresh_Devices", Action.Click);
			strstepDescription = "Remove first device from DI page.";
			strexpectedResult = "First device should be removed from DI page";
			isEventSuccessful =PerformAction("btnRemove_Devices",Action.Click);
			if (isEventSuccessful)
			{
				PerformAction("btnRemoveDevices_RemoveDevice",Action.WaitForElement);
				isEventSuccessful=PerformAction("btnRemoveDevices_RemoveDevice",Action.Click);
				if(isEventSuccessful)
				{
					strActualResult ="Device removed from DI page.";
				}
				else
				{
					strActualResult ="'Remove Devices' button did not found on the page.";
				}
			}
			else
			{
				strActualResult = "'Remove' button did not found.";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
		//**********************************************************//
		// Step 6 - Verify deleted device does not appear on devices page **********//
		//**********************************************************//                                   
		if (isEventSuccessful) 
		{
			strstepDescription = "Verify deleted device does not appear on devices page.";
			strexpectedResult = "Deleted device does not appear on devices page.";
			isEventSuccessful = searchDevice_DI(devicename);
			if(isEventSuccessful)
			{
				isEventSuccessful = VerifyMessage_On_Filter_Selection();
				if(isEventSuccessful)
				{
					strActualResult ="Deleted device did not appear on devices page.";
				}
				else
				{
					strActualResult ="Deleted device appeared on devices page.";
				}
			}
			else
			{
				strActualResult =strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 7 : Go to create Reservation Page.
		//**************************************************************************//
		isEventSuccessful=GoToReservationsPage();
		if(isEventSuccessful)
		{
			isEventSuccessful=GoToCreateReservationPage();
		}
		
		//**********************************************************//
		// Step 8 - Verify deleted device does not appear on Reservation page **********//
		//**********************************************************//                                   
		if (isEventSuccessful) 
		{
			strstepDescription = "Verify deleted device does not appear on Reservation page.";
			strexpectedResult = "Deleted device does not appear on Reservation page.";
			isEventSuccessful = RSVD_SelectDevice(devicename, true);
			if(!isEventSuccessful)
			{
				isEventSuccessful=true;
				strActualResult ="Deleted device did not appear on Reservation page.";
			}
			else
			{
				strActualResult ="Deleted device appeared on Reservation page.";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
		//**********************************************************//
		// Step 9 - Go to Applications page //
		//**********************************************************//                                   
		isEventSuccessful = GoToApplicationsPage();
		
		//**********************************************************//
		// Step 10 - Select platform Android //
		//**********************************************************//  
		if(isEventSuccessful)
		{
			strStepDescription = "Select platform android";
			strExpectedResult = "Only android platform checkbox selected";
			isEventSuccessful=selectCheckboxes_DI(android, "chkPlatform_Devices");
			if(isEventSuccessful)
			{
				strActualResult="Only android platform checkbox selected";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		}
		
		
		//**********************************************************//
		// Step 11  - Click on first application link and verify user on application details page //
		//**********************************************************// 
		if(isEventSuccessful)
		{
			strStepDescription = "Verify install applications page displayed on clicking install button";
			strExpectedResult = "Install applications page displayed";
			PerformAction(dicOR.get("installdropdownApplicationsPage").replace("2", "1"),Action.WaitForElement);
			isEventSuccessful = PerformAction(dicOR.get("installdropdownApplicationsPage").replace("2", "1"),Action.Click);
			if (isEventSuccessful)
			{
				strActualResult = "Install applications page displayed";
			}
			else
			{
				strActualResult = "Unable to click on install button";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		
		//**********************************************************//
		// Step 12 - Verify deleted device does not appear on Application Install page **********//
		//**********************************************************//          
		if (isEventSuccessful) 
		{
			strstepDescription = "Verify deleted device does not appear on Application Install page.";
			strexpectedResult = "Deleted device does not appear on Application Install page.";
			PerformAction("preserveDataAppInstallPage",Action.WaitForElement);
			isEventSuccessful = PerformAction(dicOR.get("deviceName_On_InstallDialog").replace("_devicename_", devicename),Action.isNotDisplayed);
			if(isEventSuccessful)
			{
				strActualResult ="Deleted device did not appear on Application Install page.";
			}
			else
			{
				strActualResult ="Deleted device appeared on Application Install page.";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
		//**********************************************************//
		// Step 13 - Click Cancel button on install dialog box **********//
		//**********************************************************//          
		if (isEventSuccessful) 
		{
			isEventSuccessful=PerformAction("CancelInstallDialog",Action.Click);
			if(isEventSuccessful)
			{
				reporter.ReportStep("Click on Cancel button", "Cancel button should be clicked", "Cancel button clicked", isEventSuccessful);
			}
			else
			{
				reporter.ReportStep("Click on Cancel button", "Cancel button should be clicked", "Cancel button did not clicked", isEventSuccessful);
			}
		}
		
		//**********************************************************//
		// Step 14 - Go To Devices Page **********//
		//**********************************************************// 
		if(isEventSuccessful)
		{
			GoToDevicesPage();
		}
		
	}
}