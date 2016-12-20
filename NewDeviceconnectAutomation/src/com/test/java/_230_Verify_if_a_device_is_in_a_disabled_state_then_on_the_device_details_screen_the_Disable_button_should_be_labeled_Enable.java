package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2210
 */
public class _230_Verify_if_a_device_is_in_a_disabled_state_then_on_the_device_details_screen_the_Disable_button_should_be_labeled_Enable extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "", text = "";
	private String disabledDevice = "", deviceName="";
	Object[] Values = new Object[4]; 
	private Boolean deviceDisabledHere;
	

	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();


		// Step 2 : Select 'Disabled' from 'Status' filter.
		isEventSuccessful = selectStatus_DI("Disabled");
		
		//****************************************************************************************//
		//*********  Step 3 - Verify that 'Disabled' devices is displayed or not. *******//
		//****************************************************************************************//

		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("connect", "disable", "list");
		if(!isEventSuccessful)
		{
			try
			{
				isEventSuccessful = selectStatus("Available");
				if(isEventSuccessful)
				{
					Values = ExecuteCLICommand("disable", "Android");
					isEventSuccessful = (boolean)Values[2];
					if(isEventSuccessful)
					{
						deviceDisabledHere=true;
						PerformAction("", Action.WaitForElement,"10");
						isEventSuccessful = selectStatus("Disabled");
						 if(isEventSuccessful)
						 {
							 isEventSuccessful = VerifyDeviceDetailsInGridAndListView("connect", "disable", "list");
							
							 if (isEventSuccessful)
							 {
								strActualResult = "Disbled devices is being dispalyed.";
							 }
							 else
							 {
								strActualResult = "VerifyDeviceDetailsInGridAndListView---" + strErrMsg_AppLib;
							 }

							reporter.ReportStep("Disabled status device is not being dispalyed, connecting with CLI command", "Disabled status device should be dispalyed.", strActualResult, isEventSuccessful);
							
						}
						else
						{
							throw new RuntimeException("Could not selected 'In Use' staus checkbox.");
						}
					}
					else
					{
						throw new RuntimeException("Could not make a device status to 'Disable'");
					}
				}
				else
				{
					throw new RuntimeException("Could not selected 'Available' staus checkbox.");
				}
			}
			catch (RuntimeException e)
		       {
		           isEventSuccessful = false;
		           strActualResult = "Disable the device" + "Exception at line number : '" + e.getMessage() + e.getStackTrace()[0].getLineNumber() + "'.; " ;
		           reporter.ReportStep("Disabled status device is not being dispalyed, connecting with CLI command", "Disabled status device should be dispalyed", strActualResult, isEventSuccessful);
		       }
			
			
		}
		else
		{
			strActualResult = "Disbled devices being dispalyed.";
			reporter.ReportStep("Verify that 'Disabled' devices is displayed or not.", "Disabled devices should be displayed after selecting Disabled status checkbox.", strActualResult, isEventSuccessful);
		}

		
	 
				
		
		
		
		// Step 3 : Select the first disabled device.
		isEventSuccessful = SelectDevice("first");
		deviceName = GetTextOrValue("eleDeviceNameinDeviceDetailsHeader", "text");
		if (isEventSuccessful)
		{
			strActualResult = "First displayed device selected successfully.";
		}
		else
		{
			strActualResult = "SelectDevice -- " + strErrMsg_AppLib;
		}
		reporter.ReportStep("Go to device details page of first displayed device.", "device details page should be displayed.", strActualResult, isEventSuccessful);

		//step 4 : Verify 'Disable' button is not displayed
		isEventSuccessful = !PerformAction("btnDisable",Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Disable button is not displayed on details page of disabled device.";
		}
		else
		{
			strActualResult = "Disable button is displayed for disabled device.";
		}
		reporter.ReportStep("Verify 'Disable' button is not displayed for disabled device.", "Disable button should not be displayed.", strActualResult, isEventSuccessful);

		// Step 5 : Verify 'Enable' button is displayed
		isEventSuccessful = PerformAction("btnEnable", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Enable button is displayed on details page of disabled device.";
		}
		else
		{
			strActualResult = "Disable button is not displayed for disabled device.";
		}
		reporter.ReportStep("Verify 'Enable' button is displayed for disabled device.", "Enable button should be displayed.", strActualResult, isEventSuccessful);

		// Step 6 : Enable the disabled device
		if (deviceDisabledHere)
		{
			Values = ExecuteCLICommand("enable", deviceName);
			isEventSuccessful =(Boolean) Values[2];
			disabledDevice =(String) Values[1];
			if (isEventSuccessful)
			{
				strActualResult = "Device '" + disabledDevice + "' enabled successfully.";
			}
			else
			{
				strActualResult = "ExecuteCLICommand -- " + strErrMsg_AppLib;
			}
			reporter.ReportStep("Enable the device that was disabled by script device.", "Device should get enabled.", strActualResult, isEventSuccessful);
		}
	}
}