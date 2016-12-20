package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ApplicationLibrary;
import com.common.utilities.GenericLibrary;

/*
 * JIRA ID --> QA-323
 */

public class _409_Verify_No_field_in_Applications_table_and_Added_field_on_appDetails_Page_should_be_empty extends ApplicationLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "", EmailAddress = dicCommon.get("EmailAddress"), PassWord = dicCommon.get("Password");
	private String strStepDescription = "";
	private String strExpectedResult = "";

	public final void testScript()
	{
		//******************************************************************************************************//
		// Verify no cell on applications index page and All builds section  on app details page should be empty.
		//******************************************************************************************************//

		//Step 1 - Launch deviceConnect and verify homepage.
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

		//Step 2 - Login to deviceConnect
		isEventSuccessful = LoginToDC();
		if (isEventSuccessful)
		{
			strActualResult = "User - " + EmailAddress + " is logged in to deviceConnect.";
		}
		else
		{
			strActualResult = "User - " + EmailAddress + " is not logged in to deviceConnect.";
		}
		reporter.ReportStep("Login to deviceConnect", "User should be logged in.", strActualResult, isEventSuccessful);

	    
		/////////////////////////////////////////	
		//Step 3 - Navigate to Applications Page //
		//////////////////////////////////////////
		
		/*isEventSuccessful = selectFromMenu("Applications", "eleApplicationsHeader");
		if (isEventSuccessful)
		{
			strActualResult = "Applications Page is opened.";
		}
		else
		{
			strActualResult = "Applications Page is not opened.";
		}*/
		
		isEventSuccessful = PerformAction("hdrApplicationLink", Action.Click);
		
		reporter.ReportStep("Verify Applications Page is loaded", "'Applications' Page should be loaded.", strActualResult, isEventSuccessful);

		//Step 4 - Verify Last Updated Column
		strStepDescription = "Verify that no cell in applications index page is empty.";
		strExpectedResult = "No cell value should be empty.";
		isEventSuccessful = VerifyAppDetailsInListView("cellvalues");
		if (isEventSuccessful)
		{
			strActualResult = "No cell in applications index page is empty.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//Step 5 - Go to app details page of first application displayed and verify the All Builds Section
		strStepDescription = "Go to app details page of first application displayed and verify the All Builds Section";
		strExpectedResult = "No cell value should be empty.";
		isEventSuccessful = SelectApplication("first");
		if (isEventSuccessful)
		{
			isEventSuccessful = verifyOnAllBuildsSection("cellValues");
			if (isEventSuccessful)
			{
				strActualResult = "No cell under All Builds section is empty.";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}
}