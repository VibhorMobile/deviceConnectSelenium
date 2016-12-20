package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-932
 */

public class _197_Verify_the_about_screen_contents extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "";
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		// Fetching data
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		//*************************************************************//
		// Step 1 - Login to DC
		//*************************************************************//
		strStepDescription = "Login to deviceConnect.";
		strExpectedResult = "User should be logged in.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);
		if (isEventSuccessful)
		{
			strActualResult = "User " + EmailAddress + " is logged in to dC.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//
		// Step 2 - Click the about deviceconnect link in footer
		//*************************************************************//
		strStepDescription = "Click on the link 'About deviceConnect' in footer section.";
		strExpectedResult = "'About deviceConnect' page should gets opened as a popup.";
		isEventSuccessful = PerformAction("lnkAbout", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleDeviceConnectLogoOnAbout", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "'About deviceConnect' page is opened as a popup.";
			}
			else
			{
				strActualResult = "'About deviceConnect' page is not opened.";
			}
		}
		else
		{
			strActualResult = " 'About deviceConnect' link does not exist on page.";
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//
		// Step 3 - Verify the DeviceConnect Logo on about dc page.
		//*************************************************************//
		strStepDescription = "Verify The deviceConnect Logo on About dC popup.";
		strExpectedResult = "deviceConnect Logo should be displayed.";
		isEventSuccessful = PerformAction("eleDeviceConnectLogoOnAbout", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "'deviceConnect Logo' is displayed.";
		}
		else
		{
			strActualResult = "'deviceConnect Logo' is not displayed.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//
		// Step 4 - Verify Build information on About dc popup
		//*************************************************************//
		strStepDescription = "Verify The build number on About dC popup.";
		strExpectedResult = "Build number should be displayed.";
		isEventSuccessful = PerformAction("eleDeviceConnectLogoOnAbout", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "'Build number' - " + GetTextOrValue(dicOR.get("eledetail_aboutPage").replace("__DETAILNAME__", "Build Version:"), "text") + "   is displayed.";
		}
		else
		{
			strActualResult = "'Build number' is not displayed.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//
		// Step 5 - Verify License information on About dc popup
		//*************************************************************//
		strStepDescription = "Verify License information on About dC popup.";
		strExpectedResult = "License information should be displayed.";
		isEventSuccessful = PerformAction(dicOR.get("eledetail_aboutPage").replace("__DETAILNAME__", "License Detail:"), Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "'Licence information' is displayed.";
		}
		else
		{
			strActualResult = "'Licence information' is not displayed.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//
		// Step 6 - Verify Node key info on About dc popup
		//*************************************************************//
		strStepDescription = "Verify Node Key information on About dC popup.";
		strExpectedResult = "Node Key information should be displayed.";
		isEventSuccessful = PerformAction(dicOR.get("eledetail_aboutPage").replace("__DETAILNAME__", "Node Key:"), Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "'Node Key information' is displayed.";
		}
		else
		{
			strActualResult = "'Node Key information' is not displayed.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//
		// Step 7 - Verify automation info on About dc page
		//*************************************************************//
		strStepDescription = "Verify Automation information on About dC popup.";
		strExpectedResult = "Automation information should be displayed.";
		isEventSuccessful = PerformAction(dicOR.get("eledetail_aboutPage").replace("__DETAILNAME__", "Automation:"), Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "'Automation information' is displayed.";
		}
		else
		{
			strActualResult = "'Automation information' is not displayed.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//
		// Step 8 - Verify expiration info on About dc popup
		//*************************************************************//
		strStepDescription = "Verify Expiration information on About dC popup.";
		strExpectedResult = "Expiration information should be displayed.";
		isEventSuccessful = PerformAction(dicOR.get("eledetail_aboutPage").replace("__DETAILNAME__", "Expiration:"), Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "'Expiration information' is displayed.";
		}
		else
		{
			strActualResult = "'Expiration information' is not displayed.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//
		 // Step 9 - Verify More about dc on About popup
		//*************************************************************//
		strStepDescription = "Verify More About DC link on About dC popup.";
		strExpectedResult = "More About DC link should be displayed.";
		isEventSuccessful = PerformAction("lnkmore_aboutPage", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "'More About DC link' is displayed.";
		}
		else
		{
			strActualResult = "'More About DC link' is not displayed.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//
		// Step 10 - Verify close button on About dc popup
		//*************************************************************//
		strStepDescription = "Verify Close on About dC popup.";
		strExpectedResult = "Close button should be displayed.";
		isEventSuccessful = PerformAction("btnClose_aboutPage", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "'Close button' is displayed.";
		}
		else
		{
			strActualResult = "'Close button' is not displayed.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

	}
}