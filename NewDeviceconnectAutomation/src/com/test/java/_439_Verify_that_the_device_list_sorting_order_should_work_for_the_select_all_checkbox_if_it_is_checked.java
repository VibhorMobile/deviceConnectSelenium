package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1210
 */

public class _439_Verify_that_the_device_list_sorting_order_should_work_for_the_select_all_checkbox_if_it_is_checked extends ScriptFuncLibrary
{
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String selectedDeviceName = "";
	private java.util.List<String> devicesSelected = new java.util.ArrayList<String>();

	//****************************************************************************************************************************************************************************************************************************************************************************//
	// Select Offline status checkbox, select the select all checkbox on top on devices index page. Then select Available checkbox and verify that sleect all checkbox gets deselected. Again select select all checkbox and then verify that all devices' checkboxes get selected.
	//****************************************************************************************************************************************************************************************************************************************************************************//
	public final void testScript() throws Exception
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");

		//********************************************************//
		//** Step 1 : Launch deviceConnect and verify homepage. **//
		//********************************************************//
		isEventSuccessful = Login(EmailAddress, Password);

		//************************************************************************************//
		// Step 2 : Click on select all checkbox on top of all devices on devices index page. //
		//************************************************************************************//
		isEventSuccessful = (boolean)selectAllDevicesCheckbox_DI()[0];

		//********************************************************//
		// Step 3 : Select iOS and Android platform checkbox//
		//********************************************************//
		isEventSuccessful = selectPlatform("iOS,Android");
 
		//**********************************************************//
		// Step 4 : Verify sorting when select all checkbox is checked.//
		//**********************************************************//
		SelectColumn_Devices_SFL("Manufacturer");
		strStepDescription = "Verify sorting when select all checkbox is checked." ;
		strExpectedResult =  "Columns should be listed in sorted order.";
		
		isEventSuccessful = VerifySorting_in_Order(dicOR.get("table_devicespage"),"Manufacturer", "ascending");
		if (isEventSuccessful)
		{
			strActualResult = "Columns should be listed in sorted order.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

	}
}