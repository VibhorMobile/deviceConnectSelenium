package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2154
 */

public class _253_Verify_if_a_device_is_disabled_a_circle_with_a_slash_graphic_will_replace_indicator_on_grid_and_list_screens extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String deviceName = "";
	Object[] values = new Object[2];
	private String text = "";
	Object[] Values = new Object[4]; 
	

	public final void testScript()
	{
		//Step 1 - Login to deviceConnect
		 isEventSuccessful = Login(); 
		
		//Step 2 - Click on Device name link
		isEventSuccessful = selectStatus("Available");
		if (isEventSuccessful)
		{
			values = GoTofirstDeviceDetailsPage();
		    isEventSuccessful = (boolean) values[0] ;
		    deviceName =(String) values[1];
			if (isEventSuccessful)
			{
				strActualResult = "Device details page is displayed :" + deviceName ;
			}
			else
			{
				strActualResult = "SelectDevice() -- " + strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "SelectFromFilterDropdowns() -- " + strErrMsg_AppLib;
		}

		reporter.ReportStep("Click on Device name link", "Device details page should be displayed", strActualResult, isEventSuccessful);

		//Step 3 - Click Disable button at the top
		strstepDescription = "Click on 'Disable' and verify confirmation message.";
		strexpectedResult = "Correct confirmation message should be displayed on dialog.";
		isEventSuccessful = PerformAction("btnDisable", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("hdrConfirmDisable", Action.WaitForElement);
			if (isEventSuccessful)
			{
				text = GetTextOrValue("eleConfirmDisableMsg", "text");
				String t = "Do you wish to disable"+" "+ deviceName+"? The device will no longer be usable until it is enabled.";
				isEventSuccessful = text.contains(t);
				if (!isEventSuccessful)
				{
					strActualResult = "Confirmation message is not correct. It reads : " + text;
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


		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Correct confirmation message is displayed on the dialog: " + text, "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}


		///////////////////////////////////////////////////////////////////////////////
		// Step 4 : Click on Continue button and verify notification message.
		///////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Click on 'continue' button and verify confirmation message.";
		strexpectedResult = "Correct 'Confirm Enable' message should be displayed on the page.";
		isEventSuccessful = PerformAction("btnContinue_Disable", Action.Click);
		if (isEventSuccessful)
		{
			strActualResult = "Successfully click on 'Continue' button.";

		}
		else
		{
			strActualResult = "Could not click on 'Continue' button.";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Device is enabled.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//Step 5 - Verify Disabled icon for the device.
		strstepDescription = "Verify icon is changed to 'disabled icon' on device details page.";
		strexpectedResult = "Status Icon should change to 'disabled' status icon . i.e. red icon with slash.";
		isEventSuccessful = PerformAction(dicOR.get("eleStatusIcon_DeviceDetails").replace("__STATUS__", "Connected"), Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Disabled status icon is displayed on the device details page of disabled device.";
		}
		else
		{
			strActualResult = "Disabled status icon is not displayed.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//Step 6 - Verify Available icon is not displayed for the device.
		strstepDescription = "Verify 'available status icon' is not displayed on device details page.";
		strexpectedResult = "Available status icon should not be displayed on page.";
		isEventSuccessful = !PerformAction(dicOR.get("eleStatusIcon_DeviceDetails").replace("__STATUS__", "Available"), Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Available icon is not displayed on the device details page of disabled device.";
		}
		else
		{
			strActualResult = "Available status icon is displayed.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		
		//Step 7: Verify status icon is changed to 'disabled' icon on devies list view.
		strstepDescription = "Verify status icon is changed to 'disabled' icon on devices list view.";
		strexpectedResult = "Status icon should be changed to disabled icon on devices list view.";
		
		isEventSuccessful = navigateToNavBarPages("Devices", "eleDevicesHeader");
		if (isEventSuccessful)
		{
		     isEventSuccessful = selectStatus("Disabled");
		     if (isEventSuccessful)
		     {
			     isEventSuccessful = PerformAction(dicOR.get("eleDevStatusIconByName_ListView").replace("__DEVICENAME__", deviceName).replace("__Status__", "disabled") , Action.WaitForElement, "5");
			     if (isEventSuccessful)
			     {
				     strActualResult = "Disabled icon is displayed in list view for the device name - " + deviceName;
			     }
			     else
			     {
				     strActualResult = "Disabled icon is not displayed ibn list view for device name - " + deviceName;
			     }
		    }
		    else
		    {
			strActualResult = "selectStatus() -- " + strErrMsg_AppLib ;
		    }
		}
		else
		{
			strActualResult = "Unable to navigate to Devices page.";
		}
		
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//Step 5 - Post - Condition - Enable the device
		strstepDescription = "Post-Condition : Enable the disable device.";
		strexpectedResult = "Device should be enabled.";
		Values = ExecuteCLICommand("enable", "", "", "", deviceName,"");
		isEventSuccessful = PerformAction(dicOR.get("eleDevStatusIconByName_ListView").replace("__DEVICENAME__", deviceName).replace("__Status__", "available") , Action.WaitForElement, "5");
		
		if (isEventSuccessful)
		{
			strActualResult = "Device enabled successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

	}
}