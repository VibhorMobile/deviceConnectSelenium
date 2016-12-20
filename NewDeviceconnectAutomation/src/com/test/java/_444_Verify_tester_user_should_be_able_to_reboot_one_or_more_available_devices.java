package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1088
 */

public class _444_Verify_tester_user_should_be_able_to_reboot_one_or_more_available_devices extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "";
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid test user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with " + EmailAddress, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//Step 2 - Select check box for Available in left pane and very no warning is there on UI
		isEventSuccessful = selectStatus("Available");
		isEventSuccessful=!(VerifyMessage_On_Filter_Selection());
		
		if(isEventSuccessful)
		{
			isEventSuccessful = SelectDevice("first");
			waitForPageLoaded();
			if (isEventSuccessful)
			{
				strActualResult = "Device Details page is opened.";
			}
			else
			{
				strActualResult = "SelectDevice --" + strErrMsg_AppLib;
			}
		
			reporter.ReportStep("Select first available device ", "'Device Details' page should be opened.", strActualResult, isEventSuccessful);
		}
		
		//**********************************//
		//Step 3 - Click on 'Reboot' button.//
		//**********************************//
		isEventSuccessful = PerformAction("btnReboot", Action.Click);
		waitForPageLoaded();
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleConfirmReboot", Action.WaitForElement); //Verifying confirmation popup is opened.
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

		//**************************************************************//
		//Step 4 - Click on 'Continue' button on 'Confirm Reboot' popup.//
		//**************************************************************//
		waitForPageLoaded();
		isEventSuccessful = PerformAction("btnContinue", Action.Click);
		if (!isEventSuccessful)
		{
			waitForPageLoaded();
		   strActualResult = "Could not click on 'Continue' button on 'Confirm Reboot' popup.";
		}
		reporter.ReportStep("Click on 'Continue' button on 'Confirm Reboot' popup.", "Message 'Device reboot command sent' should be displayed.", strActualResult, isEventSuccessful);


		//********************************************************************************************************************************************************//
		// Step 5 - Wait until the 'Offline' status icon is displayed. Insert the title of status icon object in place of "__STATUS__" variable of object locator.//
		//********************************************************************************************************************************************************//
		waitForPageLoaded();
		isEventSuccessful = PerformAction(dicOR.get("eleStatusIcon_DeviceDetails").replace("__STATUS__", "Offline"), Action.WaitForElement, "60");
		if (isEventSuccessful)
		{
			strActualResult = "Offline icon is displayed in front of device name , i.e. it is offline now.";
		}
		else
		{
			strActualResult = "Device status icon did not change to 'Grey icon', i.e. it did not go offline even after waiting for 1 minute.";
		}
		reporter.ReportStep("Wait until the 'Offline' status icon is displayed.", "Offline icon should be displayed on device details page.", strActualResult, isEventSuccessful);

		//**************************************************************//
		// Step 6 - Wait until the 'Available' status icon is displayed.//
		//**************************************************************//
		waitForPageLoaded();
		isEventSuccessful = PerformAction(dicOR.get("eleStatusIcon_DeviceDetails").replace("__STATUS__", "Available"), Action.WaitForElement, "60");
		if (isEventSuccessful)
		{
			strActualResult = "Available icon is displayed in front of device name , i.e. it is online now.";
		}
		else
		{
			strActualResult = "Device status icon did not change to 'Available icon', i.e. it did not comne back online even after waiting for 1 minute.";
		}
		reporter.ReportStep("Wait until the 'Available' status icon is displayed.", "Available icon should be displayed on device details page.", strActualResult, isEventSuccessful);


	}
}