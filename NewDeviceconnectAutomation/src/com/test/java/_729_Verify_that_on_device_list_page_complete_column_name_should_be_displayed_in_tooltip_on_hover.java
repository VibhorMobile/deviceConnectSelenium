package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-1001
 */
public class _729_Verify_that_on_device_list_page_complete_column_name_should_be_displayed_in_tooltip_on_hover extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";
	String columns="Model,Hardware Model,OS,OS Build ID,Manufacturer,Serial Number,Vendor Name,Vendor Unique ID,Connected Since,Offline Since,In Use Since,Notes,Next Reservation,Disk Space,Slot #";

	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to Device Index page //
		//**********************************************************// 
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToDevicesPage();
		}
		
		//*********************************************//
		//Step 3 - Select all columns//
		//********************************************//
		if(isEventSuccessful)
		{
			SelectColumn_Devices_SFL(columns);
		}
		
		//*******************************************************//
		//Step 4 - Verify all selected columns displayed//
		//*****************************************************//
		if(isEventSuccessful)
		{
			strStepDescription = "Verify all selected columns displayed" ;
			strExpectedResult =  "All selected columns should be displayed.";
			isEventSuccessful = CoulmnsDisplayed_Devices(columns);
			if (isEventSuccessful)
			{
				strActualResult = "All selected columns displayed on UI.";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		
		//*******************************************************//
		//Step 5 - Verify tooltip for all selected columns displayed//
		//*****************************************************//
		if(isEventSuccessful)
		{
			strStepDescription = "Verify tooltip for all selected columns displayed" ;
			strExpectedResult =  "Tooltip for all selected columns should be displayed.";
			isEventSuccessful =VerifyTooltip_DI_Columns(columns);
			if (isEventSuccessful)
			{
				strActualResult = "Tooltip for all selected columns displayed";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		
		//*********************************************//
		//Step 6 - Select Battery columns//
		//********************************************//
		if(isEventSuccessful)
		{
			SelectColumn_Devices_SFL("Battery");
		}
		
		//*******************************************************//
		//Step 7 - Verify tooltip for 'Battery' column displayed//
		//*****************************************************//
		if(isEventSuccessful)
		{
			strStepDescription = "Verify tooltip for 'Battery' column displayed" ;
			strExpectedResult =  "Tooltip for 'Battery' column should be displayed.";
			isEventSuccessful =VerifyTooltip_DI_Columns("Battery");
			if (isEventSuccessful)
			{
				strActualResult = "Tooltip for 'Battery' column displayed";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
	}

}