package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

public class _526_Verify_upload_the_firmware_database_link_is_present_and_opens_up_the_window_dialogue_box_to_upload_desired_file extends ScriptFuncLibrary
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
		// Step 2 - Go to Systems page //
		//**********************************************************//                                   
		isEventSuccessful = GoToSystemPage();
			
		//**********************************************************//
		// Step 3 - Click on iOS Management link on System tab //
		//**********************************************************//   
		strStepDescription = "Verify iOS Management page open after clicking on iOS Management link";
		strExpectedResult = "iOS Management page should displayed.";
		isEventSuccessful=PerformAction("linkiOSManagement",Action.Click);
		if(isEventSuccessful)
		{
			strActualResult="iOS Management page displayed.";
		}
		else
		{
			strActualResult="iOS Management page did not displayed.";
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 4 - Verify upload firmware link present and open up window dialog  //
		//**********************************************************// 
		strStepDescription = "Verify upload firmware link present and open up window dialog";
		strExpectedResult = "Upload firmware link should be displayed and open up window dialog box";
		isEventSuccessful = PerformAction("uploadfirmwarelink",Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("uploadfirmwarelink",Action.Click);
			if(!isEventSuccessful)
			{
				strActualResult = "Upload firmware link displayed and open up window dialog box";
				isEventSuccessful=true;
			}
			else
			{
				strActualResult = "Window dialog box did not displayed";
			}
		}
		else
		{
			strActualResult = "Notification did not get disabled";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}
}