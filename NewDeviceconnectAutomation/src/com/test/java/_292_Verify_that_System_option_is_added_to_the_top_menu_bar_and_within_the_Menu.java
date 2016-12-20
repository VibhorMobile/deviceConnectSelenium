package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2191
 */
public class _292_Verify_that_System_option_is_added_to_the_top_menu_bar_and_within_the_Menu extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		String SystemHdrXPath = "//span[text()='System']";

		////////////////////////////////////////////////////
		//Step 1 - Launch deviceConnect and verify homepage.
		////////////////////////////////////////////////////
		genericLibrary.LaunchWebDriver();
		isEventSuccessful = PerformAction("imgDCLogo", Action.Exist);
		if (isEventSuccessful)
		{
			strActualResult = "Navigated to " + dicCommon.get("ApplicationURL") + ". deviceConnect homepage is opened.";
		}
		else
		{
			strActualResult = "deviceConnect homepage is not opened.";
		}
		reporter.ReportStep("Launch deviceConnect and verify homepage.", "deviceConnect homepage should be opened.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}

		///////////////////////////////////////////////////////////////////////////
		// Step 2 : login to deviceConnect with valid user and verify Devices page.
		///////////////////////////////////////////////////////////////////////////
		strstepDescription = "Login to deviceConnect with valid username and password and verify Devices page.";
		strexpectedResult = "User should be logged in and navigated to Devices page.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with admin user: " + EmailAddress + " and navigated to Devices page.", "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		///////////////////////////////////////////////////////////////////////////////////////
		// Step 3 : Verify that 'System' option is added on the top menu bar on 'Devices' page.
		///////////////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Verify that 'System' option is added on the top menu bar on 'Devices' page.";
		strexpectedResult = "'System' option should be added on the top menu bar on 'Devices' page.";
		isEventSuccessful = PerformAction(SystemHdrXPath, Action.isDisplayed);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'System' option is displayed on the top menu bar on 'Devices' page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'System' option is not displayed on the top menu bar on 'Devices' page.", "Fail");
		}


		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Step 4 : Go to 'Users' page and verify that 'System' option is added on the top menu bar.
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		isEventSuccessful = GoToUsersPage();

		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Step 5 : On 'Users' page verify that 'System' option is added on the top menu bar.
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		strstepDescription = "Go to 'Users' page and verify that 'System' option is added on the top menu bar and dropdown menu.";
		strexpectedResult = "'System' option should be added on the top menu bar and in 'Menu' dropdown.";
		if (isEventSuccessful) // Check only if 'Users' page is displayed.
		{
			// check in top menu bar
			isEventSuccessful = PerformAction(SystemHdrXPath, Action.isDisplayed);
			if (isEventSuccessful)
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "'System' option is added on the top menu bar on 'Users' page.", "Pass");
			}
			else
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "'System' option is not added on the top menu bar on 'Users' page.", "Fail");
			}
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		}

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Step 5 : Go to 'Applications' page and verify that 'System' option is added on the top menu bar and dropdown menu.
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		isEventSuccessful = GoToApplicationsPage();
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Step 6 : On 'Applications' page verify that 'System' option is added on the top menu bar.
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		strstepDescription = "Go to 'Applications' page and verify that 'System' option is added on the top menu bar and dropdown menu.";
		strexpectedResult = "'System' option should be added on the top menu bar and in 'Menu' dropdown.";
		if (isEventSuccessful) // Check only if 'Applications' page is displayed.
		{
			// check in top menu bar
			isEventSuccessful = PerformAction(SystemHdrXPath, Action.isDisplayed);
			if (isEventSuccessful)
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "'System' option is added on the top menu bar on 'Applications' page.", "Pass");
			}
			else
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "'System' option is not added on the top menu bar on 'Applications' page.", "Fail");
			}
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		}

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Step 7 : Go to 'System' page and verify that 'System' option is added on the top menu bar.
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		isEventSuccessful = GoToSystemPage();
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Step 7 : On 'System' page verify that 'System' option is added on the top menu bar.
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		strstepDescription = "Go to 'System' page and verify that 'System' option is added on the top menu bar and dropdown menu.";
		strexpectedResult = "'System' option should be added on the top menu bar and in 'Menu' dropdown.";
		if (isEventSuccessful) // Check only if 'Applications' page is displayed.
		{
			// check in top menu bar
			isEventSuccessful = PerformAction(SystemHdrXPath, Action.isDisplayed);
			if (isEventSuccessful)
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "'System' option is added on the top menu bar on 'System' page.", "Pass");
			}
			else
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "'System' option is not added on the top menu bar on 'System' page.", "Fail");
			}
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		}
	}
}