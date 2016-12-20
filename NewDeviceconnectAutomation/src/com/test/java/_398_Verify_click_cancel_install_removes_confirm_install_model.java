package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2167
 */

public class _398_Verify_click_cancel_install_removes_confirm_install_model extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";

	public final void testScript()
	{
		//Step 1 - Login to DC
		
		isEventSuccessful= Login();
				
		//////////////////////////////////////////////////////////////////////////
		//Step 3 - Navigate to Applications Page
		//////////////////////////////////////////////////////////////////////////
		
		isEventSuccessful = GoToApplicationsPage();		

		//Step 4 - Click on Delete button against first application
		/*
		 * To verify Application rows are displayed on the Applications details Page
		 */
		
		isEventSuccessful = PerformAction("eleAppTableRows", Action.isDisplayed);
				
		/*isEventSuccessful = !VerifyNoRowsWarningOnTable() && strErrMsg_AppLib.equals("No warning message displayed on table.");*/
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction(dicOR.get("btnInstall_appsPage") + "[1]", Action.Click);
			if (isEventSuccessful)
			{
				strActualResult = "Successfully clicked on 'Install' button against first displayed application.";
			}
			else
			{
				strActualResult = "Could not click on 'Delete' button against first displayed application.";
			}
		}
		else
		{
			strActualResult = "No application available in applications table. OR warning message is wrong i.e. : '" + strErrMsg_AppLib;
		}
		reporter.ReportStep("Click on Delete button against first application", "User should be able to click on 'Install' button.", strActualResult, isEventSuccessful);


		//Step 5 : Verify confirmation popup is opened.
		isEventSuccessful = PerformAction(dicOR.get("eleInstallAppDialog_ApplicationsPage").replace("__EXPECTED_HEADER__", "Install Application"), Action.WaitForElement, "5");
		if (isEventSuccessful)
		{
			strActualResult = "'Install Application' popup is opened.";
		}
		else
		{
			strActualResult = "'Install Application' popup is not opened for the first application displayed on applications index page.";
		}
		reporter.ReportStep("verify Confirm 'Install Application' Popup is opened.", "Install Application Popup should be opened.", strActualResult, isEventSuccessful);


		//Step 5 - Click cancel on 'Install Application' popup
		isEventSuccessful = PerformAction("btnCancel", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction(dicOR.get("eleDialog").replace("__EXPECTED_HEADER__", "Install Application"), Action.isNotDisplayed);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleApplicationsHeader", Action.isDisplayed);
				if (isEventSuccessful)
				{
					strActualResult = "User is returned to the application details screen.";
				}
				else
				{
					strActualResult = "User is not return to the application details screen.";
				}
			}
			strActualResult = "'Install Application' pop is not removed after clicking on 'Cancel' button.";
		}
		else
		{
			strActualResult = "Could not click on 'Cancel'.";
		}
		reporter.ReportStep("Verify user is returned to 'Application' page after clicking on 'Cancel' button.", "User should be returned to the applications index page.", strActualResult, isEventSuccessful);
	}
}