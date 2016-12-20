package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2195
 */
public class _204_Verify_if_the_user_selects_cancel_then_return_to_originating_screen_else_continue_with_Reboot extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";


	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//*******************************************************//
		//Step 2 - Select Available option from 'status' dropdown//
		//*******************************************************//
		isEventSuccessful = selectStatus("Available");
		
		//***********************************************************************//
		//Step 3 - Select and go to device details page of first available device//
		//***********************************************************************//
		isEventSuccessful = SelectDevice("first");
		if (isEventSuccessful)
		{
			strActualResult = "Device Details page is opened.";
		}
		else
		{
			strActualResult = "SelectDevice --" + strErrMsg_AppLib;
		}
		reporter.ReportStep("Select first available device.", "'Device Details' page should be opened.", strActualResult, isEventSuccessful);

		//**********************************//
		//Step 4 - Click on 'Reboot' button.//
		//**********************************//
		isEventSuccessful = PerformAction("btnReboot",Action.Click);
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

		//**********************************************//
		//Step 5 - Click Cancel button on opened dialog.//
		//*********************************************//
		isEventSuccessful = PerformAction("btnCancel", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = !PerformAction("eleConfirmReboot", Action.Exist);
			if (isEventSuccessful)
			{
				strActualResult = "User is returned to the device details screen.";
			}
			else
			{
				strActualResult = "User is not return to the device details screen.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Cancel'.";
		}
		reporter.ReportStep("Click Cancel.", "User should BE returned to the device details screen.", strActualResult, isEventSuccessful);

		//**********************************//
		//Step 6 - Click on 'Reboot' button.//
		//**********************************//
		isEventSuccessful = PerformAction("btnReboot", Action.Click);
		if (isEventSuccessful)
		{
			//Verifying confirmation popup is opened.
			isEventSuccessful = PerformAction("eleConfirmReboot", Action.WaitForElement);
			if (isEventSuccessful)
			{
				strActualResult = "'Confirm Reboot' popup is opened.";
			}
			else
			{
				strActualResult = "'Confirm Reboot' popup is not opened.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Reboot' button.";
		}
		reporter.ReportStep("Click on 'Reboot' button.", "'Confirm Reboot' popup should be opened.", strActualResult, isEventSuccessful);

		//**************************************************************//
		//Step 7 - Click on 'Continue' button on 'Confirm Reboot' popup.//
		//**************************************************************//
		isEventSuccessful = PerformAction("btnContinue",Action.Click);
		if (!isEventSuccessful)
		{
				strActualResult = "Could not click on 'Continue' button on 'Confirm Reboot' popup.";
		}
		reporter.ReportStep("Click on 'Continue' button on 'Confirm Reboot' popup.", "Message 'Device reboot command sent' should be displayed.", strActualResult, isEventSuccessful);
	}

}