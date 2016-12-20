package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: 8-Feb-2016
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-7
 */

public class _567_Verify_model_names_for_all_the_devices_on_UI_are_friendly_model_names extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "",strstepDescription="",strexpectedResult="";
	private int deviceCount;

	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to Device Index page //
		//**********************************************************//                                   
		isEventSuccessful = GoToDevicesPage();
		
		
		//*********************************************//
		// Step 3 - Verify "Model" column is displayed//
		//********************************************//
		SelectColumn_Devices_SFL("Model");
		PerformAction("btnRefresh_Devices", Action.Click);
		
		//**************************************************************************//
		// Step 4 : Verify Battery status is displayed on the all the device details page.
		//**************************************************************************//
		strstepDescription = "Verify model name of all devices on DI page are devices friendly name.";
		strexpectedResult = "Model name of all devices on DI page should be devices friendly name.";
		
		isEventSuccessful=VerifyDevicesFriendlyNameDisplayedAsModel(getelementCount("eleDevicesHolderListView") - 1);
		if (isEventSuccessful)
		{
			strActualResult = "Model name of all devices on DI page are devices friendly name.";
		}
		else
		{
			strActualResult = "Model name of all devices on DI page are bnot devices friendly name";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
	}

}