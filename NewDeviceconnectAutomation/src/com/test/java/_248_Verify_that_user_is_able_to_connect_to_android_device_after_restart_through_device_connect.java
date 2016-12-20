package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2206
 */

public class _248_Verify_that_user_is_able_to_connect_to_android_device_after_restart_through_device_connect extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";
	private Object[] values = new Object[2];
	private String deviceName, text = "";
	private Object[] Values = new Object[4];
	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		//Step 1 - Login to deviceConnect
		 isEventSuccessful = Login(); 
		
		//**************************************************************************//
		//Step 2 - Select Available filter
		//**************************************************************************//            
		isEventSuccessful = selectStatus("Available");
		
		//**************************************************************************//
		//Step 3 - Select first available Android device
		//**************************************************************************//            
		isEventSuccessful = selectPlatform("Android");
		if (isEventSuccessful)
		{
			values = GoTofirstDeviceDetailsPage();
			isEventSuccessful = (boolean) values[0];
			deviceName = (String) values[1];
		}
		else
		{
			strActualResult = "SelectFromFilterDropdowns---" + strErrMsg_AppLib;
			return;
		}
		
		//**************************************************************************//
		//Step 5- Click on 'Reboot' button.
		//**************************************************************************//
		
		isEventSuccessful = PerformAction("btnReboot", Action.Click);
		if (isEventSuccessful)
		{
			//Verifying confirmation popup is opened.
			waitForPageLoaded();
			isEventSuccessful = PerformAction("eleConfirmReboot",  Action.WaitForElement);
			if (isEventSuccessful)
			{
				strActualResult = "'Confirm Reboot' popup is opened.";
			}
			else
			{
				strActualResult = "Clicked on 'Reboot' button. 'Confirm Reboot' popup is not opened.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Reboot'.";
		}
		reporter.ReportStep("Click on 'Reboot' button.", "'Confirm Reboot' popup should be opened.", strActualResult, isEventSuccessful);

		//**************************************************************************//
		//Step 6 - Click on 'Continue' button on 'Confirm Reboot' popup.
		//**************************************************************************//
		
		isEventSuccessful = PerformAction("btnContinue", Action.Click);
		if (isEventSuccessful)
		{
			//Verify 'Device Status -
			waitForPageLoaded();
			isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
			if(isEventSuccessful)
			{
				waitForPageLoaded();
			       isEventSuccessful = VerifyOnDeviceDetailsPage("Status ", "Rebooting");
			     if (isEventSuccessful)
			     {
				        strActualResult = "Rebooting status is displayed.";
			     }
			    else
			    {
				    strActualResult = "Clicked on 'Continue' button. Message Rebooting status is not displayed.";
			    }
			}
			else
			{
				strActualResult = "Couldn't click on Show details... link.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Continue' button on 'Confirm Reboot' popup.";
		}
		reporter.ReportStep("Click on 'Continue' button on 'Confirm Reboot' popup.", "Message 'Device reboot command sent' should be displayed.", strActualResult, isEventSuccessful);

		//**************************************************************************//
		//Step 7 - Wait till 'Reboot' button is enabled again
		//**************************************************************************//
		//Thread.sleep(5000);
		waitForPageLoaded();
		isEventSuccessful = PerformAction("btnReboot", Action.WaitForElement);
		if (isEventSuccessful)
		{
			strActualResult = "'Reboot' button is enabled again.";
		}
		else
		{
			strActualResult = "'Reboot' button is not enabled. Waited for 60 seconds.";
		}
		reporter.ReportStep("Wait till 'Reboot' button is enabled againd.", "'Reboot' button should be enabled again.", strActualResult, isEventSuccessful);

		//**************************************************************************//
		//Step 8 - Verify device status is 'Available'
		//**************************************************************************//
		//isEventSuccessful = PerformAction("lnkMore", Action.Click);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			PerformAction("", Action.WaitForElement, "5");
			isEventSuccessful = VerifyOnDeviceDetailsPage("Status ", "Available");
			if (isEventSuccessful)
			{
				strActualResult = "Device status is Available.";
			}
			else
			{
				strActualResult = "VerifyOnDeviceDetailsPage---" + strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "Couldn't click on Show details... link.";
		}
		reporter.ReportStep("Verify Device Status.", "Device Status should be Available.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}

		//**************************************************************************//
		//Step 9 - Click on 'Connect
		//**************************************************************************//
		Values = ExecuteCLICommand("run", "iOS", EmailAddress, Password,deviceName, "");
		isEventSuccessful = (boolean)Values[2];
		if (isEventSuccessful)
		{
			strActualResult = "Viewer launched after connecting to an Android device:  " + Values[0] + " & processfound : " +  Values[1];
			
		}
		else
		{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
		}
		//**************************************************************************//
		//Step 10 - Verify Device Status
		//**************************************************************************//
		waitForPageLoaded();
		isEventSuccessful = VerifyOnDeviceDetailsPage("Status ", "In Use");
		if (isEventSuccessful)
		{
			strActualResult = "Device status is In Use.";
		}
		else
		{
			strActualResult = "VerifyOnDeviceDetailsPage---" + strErrMsg_AppLib;
		}
		reporter.ReportStep("Verify Device Status.", "Device Status should be In Use.", strActualResult, isEventSuccessful);

		//**************************************************************************//
		//Step 11 - Post Condition - Close Viewer
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", EmailAddress, Password, deviceName, "","","" );
		 isEventSuccessful = (boolean)Values[2];
		 if (isEventSuccessful)
		 {
			 strActualResult = "Device released";
				
		 }
		 else
		 {
			 strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
		 }
		isEventSuccessful =	KillObjectInstances("MobileLabs.deviceViewer.exe");
		reporter.ReportStep("Verfiy Device is not connected after closing devcieViewer window","Device should not be connected after closing devcieViewer window","Device is not connected after closing devcieViewer window",isEventSuccessful);
		
	}
}