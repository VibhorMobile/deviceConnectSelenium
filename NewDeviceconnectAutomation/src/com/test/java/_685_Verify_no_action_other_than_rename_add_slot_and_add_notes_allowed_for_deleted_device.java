package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1216
 */
public class _685_Verify_no_action_other_than_rename_add_slot_and_add_notes_allowed_for_deleted_device extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String oldDeviceName="";

	public final void testScript()
	{
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();

		//******************************************************************//
		//*** Step 2 : Select Status Offline. and select first device*****//
		//******************************************************************//  
		isEventSuccessful = selectStatus("Offline");
		strstepDescription = "Open device details page for first offline device.";
		strexpectedResult =  "Device details page for the first offline device should be displayed.";
						
		if (isEventSuccessful)
		{
			isEventSuccessful = !VerifyMessage_On_Filter_Selection();
			if (isEventSuccessful) 
			{
				isEventSuccessful = SelectDevice("first");
				if (isEventSuccessful)
				{
					strActualResult = "Device details page displayed";
				}
				else
				{
					strActualResult = "Device details page did not displayed";
				}
			}
			else
			{
				strActualResult = "Warning messgae displayed on devices page.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 3 : Delete the device *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Verify offline device marked for deletion when removed.";
			strexpectedResult =  "Offline device should be marked for deletion when removed.";
			isEventSuccessful=verifyofflineRemoveDeviceMarkDeletion();
			if(isEventSuccessful)
			{
				strActualResult="Offline device marked for deletion when removed";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
				
		//******************************************************************//
		//** Step 4 : click on 'more...' link to display hidden details ****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Click on 'more...' link to display hidden device details";
			strexpectedResult = "User should be able to click on the 'more...' link";
			isEventSuccessful = PerformAction("lnkShowDetails", Action.isDisplayed);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
				if (isEventSuccessful)
				{
					strActualResult = "User is able to click on 'more...' link successfully.";
				}
				else
				{
					strActualResult = "Could not click on 'more...' link on device details page.";
				}
			}
			else
			{
				strActualResult = "'more...' link is not displayed.";
			}

			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
		//******************************************************************//
		//** Step 5 : Verify Reserve button is not available ****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Verify Reserve button is not available.";
			strexpectedResult = "Reserve button should not be available.";
			isEventSuccessful=PerformAction("btnReserve_devciedetails",Action.isNotDisplayed);
			if(isEventSuccessful)
			{
				strActualResult = "Reserve button is not available.";
			}
			else
			{
				strActualResult = "Reserve button is available";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
		//******************************************************************//
		//** Step 6 : Verify Disable button is not available ****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Verify Disable button is not available.";
			strexpectedResult = "Disable button should not be available.";
			isEventSuccessful=PerformAction("btnDisable",Action.isNotDisplayed);
			if(isEventSuccessful)
			{
				strActualResult = "Disable button is not available.";
			}
			else
			{
				strActualResult = "Disable button is available";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
		//**********************************************************//
		// Step 7 - Verify slot can be add or modify //
		//**********************************************************// 
		if(isEventSuccessful)
		{
			isEventSuccessful=EditAndVerifySlot("1111");
		}
		
		//**********************************************************//
		// Step 8 - Verify notes can be add or modify  //
		//**********************************************************// 
		if(isEventSuccessful)
		{
			isEventSuccessful=EditAndVerifyNotes("Notes field is editable");
		}
		
		//**********************************************************//
		// Step 9 - Get device name  //
		//**********************************************************// 
		if(isEventSuccessful)
		{
			oldDeviceName=GetTextOrValue("eleDeviceNameinDeviceDetailsHeader", "text");
		}
		
		//**************************************************************************//
		// Step 10 : Click on edit link
		//**************************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Click on edit link";
			strexpectedResult = "Device name should be editable";
			isEventSuccessful = PerformAction("lnkEditDeviceName_DeviceDetailPage",Action.ClickUsingJS);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("txtDeviceName", Action.isEnabled);
				if (isEventSuccessful)
				{
					strActualResult = "Device name is editable";
				}
				else
				{
					strActualResult = "Device name is not editable";
				}
			}
			else
			{
				strActualResult = "Unable to click on Edit link";
			}

			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		//**************************************************************************//
		// Step 11 : Enter a device name
		//**************************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Enter a device name";
			strexpectedResult = "New device name should be entered";
			isEventSuccessful = PerformAction("txtDeviceName", Action.Type,"newDevice");
			if (isEventSuccessful)
			{
				strActualResult = "Device Name entered is 'newDevice' replacing - " + oldDeviceName;
			}
			else
			{
				strActualResult = "Unable to enter Device name";
			}

			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}

		//**************************************************************************//
		// Step 12 : Click on Save link
		//**************************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Click on Save link";
			strexpectedResult = "New Device name should be displayed";
			isEventSuccessful = PerformAction("btnSaveDeviceName_DeviceDetails", Action.Click);
			if (isEventSuccessful)
			{
				if(!(GetTextOrValue("eleDeviceNameinDeviceDetailsHeader", "text").equals(oldDeviceName)))
				{
					isEventSuccessful = true;
					strActualResult = "New Device name displayed";
				}
				else
				{
					isEventSuccessful = false;
					strActualResult = "New Device name not displayed";
				}
			}
			else
			{
				strActualResult = "Unable to click on Save link";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
			
	}	
}