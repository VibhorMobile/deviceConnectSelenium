package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-90
 */


public class _215_Verify_that_delete_application_operation_should_be_followed_by_a_user_confirmation_message extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String text = "";
	private String appName = "";

	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();

		///////////////////////////////////////////////////////
		// Step 2 : Go to Applications page and get AppNname of first displayed application.
		///////////////////////////////////////////////////////
		strstepDescription = "Go to Applications page and get appname of first displayed application.";
		strexpectedResult = "Application name should be saved in variable and it should not be blank or empty.";
		isEventSuccessful = GoToApplicationsPage();
//		selectFromMenu("Applications", "eleApplicationsHeader");
		if (isEventSuccessful)
		{
			if (!GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured applications.")) //Check if there are applications uploaded to system.
			{
				//appName =  getAttribute(dicOR.get("eleAppNameAppTable").replace("__APP_INDEX__", "1"), "title");
				appName =  GetTextOrValue(dicOR.get("eleAppNameAppTable").replace("__APP_INDEX__", "1"), "text");
				if (appName!=null)
				{
					isEventSuccessful = true;
					strActualResult = "Name of first displayed application is added to variable . i.e. '" + appName + "'. ";
				}
				else
				{
					strActualResult = "Name of application could not be fetched properly from Applications index page.";
				}
			}
			else
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "deviceConnect currently has no configured applications.", "Fail");
				return;
			}
		}
		else
		{
			strActualResult = "SelectFromMenu -- " + strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		///////////////////////////////////////////////////////////////////////////////////////////
		// Step 3 : Click on Delete button for one of the applications.
		///////////////////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Click on Delete button for one of the applications.";
		strexpectedResult = "Correct confirmation message should be displayed.";
		isEventSuccessful = PerformAction(dicOR.get("eleInstallAppDropdown")+"[1]", Action.ClickUsingJS);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction(dicOR.get("eleDeleteOption_AppPage"), Action.ClickUsingJS);
			if (isEventSuccessful)
			{
				strActualResult = "User is able to click on 'Delete' button for first displayed application.";
			}
			else
			{
				strActualResult = "Could not click on 'Delete' button for first application.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Install' dropdown for first displayed application.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Stpe 4. Get application name of first application displayed.
		//isEventSuccessful = GetAppDetails_AppPage("1", "appname");
		//if (isEventSuccessful)
		//{
		//    strActualResult = "Name of first displayed application fetched successfully.";
		//}
		//else
		//    strActualResult = "GetAppDetails_AppPage -- " + strErrMsg_AppLib;

		////////////////////////////////////////////////////////////////////////////////////
		// Step 4 : Verify Confirm Delete dialog is opened and correct message is displayed.
		////////////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Verify Confirm Delete dialog is opened and correct message is displayed.";
		strexpectedResult = "Confirm Delete dialog should be opened and correct message should be displayed.";
		PerformAction("", Action.WaitForElement,"10");
		isEventSuccessful = PerformAction(dicOR.get("eleDialog").replace("__EXPECTED_HEADER__", appName), Action.isDisplayed);
		if (isEventSuccessful)
		{
			
			text = GetTextOrValue("eleWarningOrConfirmationPopUpBody", "text");
			isEventSuccessful = text.equals("This will delete all uploaded versions of the application from the server. This action can not be undone.");
			if (isEventSuccessful)
			{
				strActualResult = "Confirm Delete dialog is opened and correct message is displayed.";
			}
			else
			{
				strActualResult = "Confirmation messsage displayed on the pop-up is not correct. It is : '" + text + "'.";
			}
		}
		else
		{
			strActualResult = "Confirmation message is not displayed after clicking on 'Delete' button.";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}