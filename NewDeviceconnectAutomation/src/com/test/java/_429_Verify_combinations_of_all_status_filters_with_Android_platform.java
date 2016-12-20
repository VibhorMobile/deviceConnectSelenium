package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1316
 */

public class _429_Verify_combinations_of_all_status_filters_with_Android_platform extends ScriptFuncLibrary
{
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String[] arrDeviceStatus_Single = {"Available", "In Use", "Offline", "Disabled"};
	private String[] arrDeviceStatus_Double = {"Available,In Use", "Available,Offline", "Available,Disabled", "In Use,Offline", "In Use,Disabled", "Offline,Disabled"};
	private String[] arrDeviceStatus_Triple = {"Available,In Use,Offline", "Available,In Use,Disabled", "In Use,Offline,Disabled"};

	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		//********************************************************//
		//** Step 1 : Launch deviceConnect and verify homepage. **//
		//********************************************************//
		isEventSuccessful = Login(EmailAddress, Password);

		//********************************************************//
		// Step 2 : Select only 'Android' from the platform filter//
		//********************************************************//
		isEventSuccessful =	selectPlatform("Android");

		//************************************************************************************************************//
		// Step 3 : Select single status checkbox and verify only Android devices with the given status are displayed.//
		//************************************************************************************************************//
		for (String status : arrDeviceStatus_Single)
		{
			// Step 3.1 : Select only required status checkboxes.//
			
			isEventSuccessful = selectStatus(status); // It already checks if all the displayed devices have 'Available' status only

			// Step 3.2 : Reverify that only Android devices are displayed.//
			strstepDescription = "Reverify that only Android devices are displayed.";
			strexpectedResult = "Only android devices should be displayed.";
			isEventSuccessful = VerifyMultipleValuesOfProperty_DI("Platform", "Android");
			if (isEventSuccessful)
			{
				strActualResult = "Only Android devices are displayed.";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}

		//************************************************************************************************************//
		// Step 4 : Select two status checkboxes and verify only Android devices with the given statuses are displayed.//
		//************************************************************************************************************//
		for (String status : arrDeviceStatus_Double)
		{
			// Step 4.1 : Select required status checkboxes.
			
			isEventSuccessful = selectStatus(status); // It already checks if all the displayed devices have 'Available' status only

			// Step 4.2 : Reverify that only Android devices are displayed.//
			strstepDescription = "Reverify that only Android devices are displayed.";
			strexpectedResult = "Only android devices should be displayed.";
			isEventSuccessful = VerifyMultipleValuesOfProperty_DI("Platform", "Android");
			if (isEventSuccessful)
			{
				strActualResult = "Only Android devices are displayed.";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}

		//************************************************************************************************************//
		// Step 5 : Select three status checkboxes and verify only Android devices with the given statuses are displayed.//
		//************************************************************************************************************//
		for (String status : arrDeviceStatus_Triple)
		{
			// Step 5.1 : Select required status checkboxes.
			
			isEventSuccessful = selectStatus(status);  // It already checks if all the displayed devices have 'Available' status only

			// Step 5.2 : Reverify that only Android devices are displayed.//
			strstepDescription = "Reverify that only Android devices are displayed.";
			strexpectedResult = "Only android devices should be displayed.";
			isEventSuccessful = VerifyMultipleValuesOfProperty_DI("Platform", "Android");
			if (isEventSuccessful)
			{
				strActualResult = "Only Android devices are displayed.";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
	}
}