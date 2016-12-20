package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case id: QA-2165
 */
public class _121_Verify_that_Connect_button_displayed_on_the_upper_right_corner_of_the_Device_Detail_view extends ScriptFuncLibrary
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
		//********************************************************//
		//******   Step 1 - Login to deviceConnect   *************//
		//********************************************************//
		isEventSuccessful = Login();

		//*************************************************************************//
		//****** Step 2 : Open device details page for an Android device. *********//
		//*************************************************************************//           
		strstepDescription = "Open device details page for an Android device.";
		strexpectedResult = "Device details page for the corresponding Android device should be displayed.";

		isEventSuccessful = selectPlatform("Android");
		isEventSuccessful = selectStatus("Available");
		isEventSuccessful = GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results.");
		if (!isEventSuccessful) // continue only if there are some devices under android platform.
		{
			isEventSuccessful = SelectDevice("first");
			if (!isEventSuccessful)
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "No Android devices displayed on devices page.";
		}

		//*************************************************************************//           
		//********** Step 3 : Verify Connect button on the page.  ****************//
		//***********************************************************************//           
		strstepDescription = "Check the existence of Connect button on the device details page.";
		strexpectedResult = "Connect button should be displayed on the device details page.";
		isEventSuccessful = PerformAction("btnConnect", Action.Exist);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Connect button is displayed on the device details page for Android device.", "Pass");
		}
		else
		{
			strActualResult = "Connect button is not displayed on the device detail page.";
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*****************************************************************//           
		//********** Step 4 : Go back to "Devices" page.  ****************//
		//****************************************************************//           
		isEventSuccessful = GoToDevicesPage();

		//*****************************************************************//           
		//**** Step 5 : Open device details page for an iOS device. ******//
		//*****************************************************************//           
		strstepDescription = "Open device details page for an iOS device.";
		strexpectedResult = "Device details page for the corresponding iOS device should be displayed.";

		PerformAction("browser", Action.Scroll, "30");
		isEventSuccessful = selectPlatform("iOS");

			isEventSuccessful = !(GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results."));
			if (isEventSuccessful) // continue only if there are some devices under android platform.
			{
				isEventSuccessful = SelectDevice("first");
				if (!isEventSuccessful)
				{
					strActualResult = "SelectDevice---" + strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "No iOS devices displayed on devices page.";
			}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Device details page is displayed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//****************************************************************//           
		//******* Step 6 : Verify Connect button on the page. ***********//
		//***************************************************************//           
		strstepDescription = "Check the existence of Connect button on the device details page.";
		strexpectedResult = "Connect button should be displayed on the device details page.";
		isEventSuccessful = PerformAction("btnConnect", Action.Exist);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Connect button is displayed on the device details page for an iOS device.", "Pass");
		}
		else
		{
			strActualResult = "Connect button is not displayed on the device detail page.";
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
	}
}