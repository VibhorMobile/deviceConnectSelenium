package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1728
 */
public class _682_Verify_application_details_page_of_deleted_app_should_show_an_exception extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";
	String [] android={"Android"};
	
	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to Applications page //
		//**********************************************************//                                   
		isEventSuccessful = GoToApplicationsPage();
			
		//**********************************************************//
		// Step 3 - Select platform Android //
		//**********************************************************//   
		strStepDescription = "Select platform android";
		strExpectedResult = "Only android platform checkbox selected";
		isEventSuccessful=selectCheckboxes_DI(android, "chkPlatform_Devices");
		if(isEventSuccessful)
		{
			strActualResult="Only android platform checkbox selected";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 4 - Click on first application link and verify user on application details page //
		//**********************************************************// 
		strStepDescription = "Click on first app link and app details page get displayed";
		strExpectedResult = "App details page should be displayed.";
		isEventSuccessful = SelectApplication("first");
		if (isEventSuccessful)
		{
			strActualResult = "App details page displayed";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		// Step 5: Get Application URL
		if(isEventSuccessful)
		{
			isEventSuccessful=PerformAction("browser",Action.GetURL);
			PerformAction("browser",Action.Back);
			PerformAction("browser",Action.Refresh);
		}
		
		//**********************************************************//
		// Step 6 - Click on Delete button for first Applications.//
		//**********************************************************// 
		if(isEventSuccessful)
		{
			strStepDescription = "Click on Delete button for one of the applications.";
			strExpectedResult = "Correct confirmation message should be displayed.";
			isEventSuccessful = PerformAction(dicOR.get("eleInstallAppDropdown")+"[1]", Action.ClickUsingJS);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction(dicOR.get("eleDeleteOption_AppPage"), Action.ClickUsingJS);
				if (isEventSuccessful)
				{
					isEventSuccessful=PerformAction(dicOR.get("eleDialogDeleteAll_ApplicationPage").replace("__EXPECTED_HEADER__", "Delete all"),Action.ClickUsingJS);
					if(isEventSuccessful)
					{
						strActualResult="User is able to Delete App.";
					}
					else
					{
						strActualResult="User cannot delete app.";
					}
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
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			PerformAction("browser",Action.Refresh);
		}
		
		//**********************************************************//
		// Step 7 - Verify deleted app page on navigating should throw error.
		//**********************************************************// 
		if(isEventSuccessful)
		{
			strStepDescription = "Verify deleted app page on navigating throw standard error";
			strExpectedResult = "Deleted app page on navigation should throw standard error.";
			isEventSuccessful = PerformAction("browser",Action.Navigate,dicOutput.get("URL"));
			if(isEventSuccessful)
			{
				isEventSuccessful=PerformAction("errorPopup",Action.isDisplayed);
				if(isEventSuccessful)
				{
					strActualResult="User got the standord error on navigating.";
				}
				else
				{
					strActualResult="User did not get the error on navigating.";
				}
			}
			else
			{
				strActualResult="Unable to paste copied URL";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
	}
}