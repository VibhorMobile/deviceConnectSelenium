package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2177
 */
public class _389_Verify_tester_not_allowed_to_edit_app_name extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String EmailAddress = dicCommon.get("testerEmailAddress");
	private String Password = dicCommon.get("testerPassword");
	private String warningText = "";

	public final void testScript()
	{
		//Step 2 - Login to deviceConnect
		isEventSuccessful = Login(EmailAddress,Password); 
		
		//Step 3 - Navigate to Applications Page
		isEventSuccessful = GoToApplicationsPage();
	

		//Step 4 : Go to details page of first application displayed.
		isEventSuccessful = SelectApplication("first");
		if (isEventSuccessful)
		{
			strActualResult = "Application details page of first displayed application is displayed successfully.";
		}
		else
		{
			strActualResult = "SelectApplication() -- " + strErrMsg_AppLib;
		}
		reporter.ReportStep("Go to details page of first application displayed.", "Application details page should be opened.", strActualResult, isEventSuccessful);

		//Step 5 : Verify Warning message is displayed when user tries to edit application name.
		PerformAction("browser",Action.WaitForPageToLoad);
		PerformAction("lnkEdit_ApplicationDetailsPage",Action.WaitForElement,"30");
		isEventSuccessful = !PerformAction("lnkEdit_ApplicationDetailsPage", Action.isDisplayed);
		if(isEventSuccessful)
		{
			isEventSuccessful=true;
			strActualResult = "Tester can not edit App.";
		}
		else
		{
			strActualResult = "Tester can edit app.";
		}
		reporter.ReportStep("Verify tester can not edit app name.", "Tester should not be able to edit app name.' ", strActualResult, isEventSuccessful);
		
		

	}
}