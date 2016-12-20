package com.test.java;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-424
 */
public class _736_Verify_any_user_can_not_install_OR_uninstall_applications_on_online_device_which_has_been_disabled_via_CLI extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="", cmdText="";
	private String xpath = "",email="";
	Object[] Values = new Object[5]; 

	public final void testScript() 
	{

		//**************************************************************************//
		// Step 1 : Login.
		//**************************************************************************//
		isEventSuccessful=Login();
		
		//**************************************************************************//
		// Step 2 : Disable android device.
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("disable", "Android", "", "", "","");
			isEventSuccessful = (boolean)Values[2];
			deviceName=(String)Values[3];
			if (isEventSuccessful)
			{
				strActualResult = "Device disabled successfully.";
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Disable android device." , "Device should get disabled", strActualResult, isEventSuccessful);
		}

		
		//**************************************************************************//
		// Step 3 : Go to device details page.
		//**************************************************************************//
		selectStatus("Disabled");
		if(isEventSuccessful)
		{
			isEventSuccessful=GoToSpecificDeviceDetailsPage(deviceName);
		}
		
		//**************************************************************************//
		// Step 4 : Verify device is disabled.
		//**************************************************************************//
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction(dicOR.get("eleStatusIcon_DeviceDetails").replace("__STATUS__", "Connected"), Action.WaitForElement,"50");
			if (isEventSuccessful)
			{
				strActualResult = "Disabled icon is displayed in front of device name , i.e. it is disabled now.";
			}
			else
			{
				strActualResult = "Device status icon did not change to 'Disabled icon'.";
			}
			reporter.ReportStep("Verify Disabled icon displayed for device.", "Disbale icon should be displayed for device disabled via CLI.", strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 5 : Verify user cannot install application on disabled device
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("install", "", "", "", deviceName, "","","");
			isEventSuccessful = (boolean)Values[2];
			cmdText=(String)Values[0];
			if (isEventSuccessful && cmdText.contains("disabled"))
			{
				strActualResult = "Cannot install app as device is disabled.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify user cannot install application on disabled device" , "Exception should be thrown", strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 6 : Verify user cannot uninstall application on disabled device
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("uninstall", "", "", "", deviceName, "","","");
			isEventSuccessful = (boolean)Values[2];
			cmdText=(String)Values[0];
			if (isEventSuccessful && cmdText.contains("disabled"))
			{
				strActualResult = "Cannot uninstall app as device is disabled.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify user cannot uninstall application on disabled device" , "Exception should be thrown", strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 7 : Post-Condition : Enable the disabled device
		//**************************************************************************//
		if(isEventSuccessful)
		{
			
			strstepDescription = "Post-Condition : Enable the disabled device";
			strexpectedResult = "Device should get enabled.";
			Values = ExecuteCLICommand("enable", "", "", "", deviceName,"");
			if ((Boolean)Values[2])
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "Device " + deviceName + " enabled successfully.", "Pass");
			}
			else
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "Device " + deviceName + " could not be enabled.", "Fail");
			}
		}
	}
}