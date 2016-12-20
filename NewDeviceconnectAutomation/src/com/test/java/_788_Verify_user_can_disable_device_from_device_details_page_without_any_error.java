package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-1566
 */
public class _788_Verify_user_can_disable_device_from_device_details_page_without_any_error extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String deviceName = "";


	public final void testScript()
	{
		
		// Step 1 : login to deviceConnect as admin user.
		isEventSuccessful = LoginToDC();
		
		// Step 2 : Select Status "Available"
		if(isEventSuccessful)
		{
			isEventSuccessful = selectStatus("Available");
		}
		
		
		// Step 3 : Go to device details page for a device.
		if(isEventSuccessful)
		{
			strstepDescription = "Go to device details page for a device.";
			strexpectedResult = "Device details page should be opened.";
			isEventSuccessful = SelectDevice("first");
			if (isEventSuccessful)
			{
				strActualResult="Device details page opened successfully";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
		// Step 4 : Disable the device.
		if(isEventSuccessful)
		{
			strstepDescription = "Verify device disable without any error.";
			strexpectedResult = "Device should get disabled without any error.";
			deviceName = GetTextOrValue("eleDeviceNameinDeviceDetailsHeader", "text");
			if (!deviceName.equals(""))
			{
				isEventSuccessful = PerformAction("btnDisable", Action.Click);
				if (isEventSuccessful)
				{
					isEventSuccessful = PerformAction("btnContinue_Disable", Action.Click);
					if (isEventSuccessful)
					{
						isEventSuccessful = PerformAction("btnEnable",Action.Exist);
						if (isEventSuccessful)
						{
							strActualResult="Device disabled successfully";
							PerformAction("btnEnable",Action.Click);
							PerformAction("btnEnableDevice_DeviceDetails",Action.Click);
						}
						else
						{
							strActualResult="Unable to disabled device";
						}
					}
					else
					{
						strActualResult = "'Confirm disable' dialog not displayed after clicking on Disable.";
					}
				}
				else
				{
					strActualResult = "Unable to click on Disable button.";
				}
			}
			else
			{
				isEventSuccessful = false;
				strActualResult = "Could not get device name from device details page.";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult,strActualResult , isEventSuccessful);
		}
	
	}
}