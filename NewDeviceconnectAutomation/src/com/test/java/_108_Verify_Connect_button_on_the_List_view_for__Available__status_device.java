package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-81
 */

public class _108_Verify_Connect_button_on_the_List_view_for__Available__status_device extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";

	public final void testScript()
	{
		//*************************************************************//     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//     
		isEventSuccessful = Login();
		
		//**********************************************************//
		//** Step 2 - Select status from Filters dropdown **********//
		//**********************************************************//                                   
		
		isEventSuccessful =  selectStatus("Available");
		if(isEventSuccessful)
		{
			isEventSuccessful=VerifyMultipleValof_DI_UI("Status", "Available");
		}
		

		//****************************************************************************************//
		//** Step 3 - Verify Connect button on the List view for 'Available' status device **//
		//****************************************************************************************//
		if (isEventSuccessful) // check this only if Available checkbox is correctly checked.
		{
			isEventSuccessful = VerifyDeviceDetailsInGridAndListView("connect", "enable", "list");
			if (isEventSuccessful)
			{
				strActualResult = "Connect button is displayed for all 'Available' devices in grid and list view.";
			}
			else
			{
				strActualResult = "VerifyDeviceDetailsInGridAndListView---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify Connect button on the Card/List view for all 'Available' status devices.", "Connect button should be displayed for all 'Available' devices in grid view.", strActualResult, isEventSuccessful);
		}
	}

}