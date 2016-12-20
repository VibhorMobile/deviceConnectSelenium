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
 * JIRA ID --> QA-846
 */

public class _566_Verify_that_search_with_date_field_should_work_fine_on_DI_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "",Connected_Since="",strstepDescription="",strexpectedResult="";
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
		// Step 3 - Verify "Connected Since" column is displayed and extract Connected Since//
		//********************************************//
		SelectColumn_Devices_SFL("Connected Since");
		Connected_Since=GetTextOrValue(dicOR.get("eleDeviceModel_ListView").replace("__INDEX__", "2"),"text").split(" ")[0];
		
		//**********************************************************//
		// Step 4 - Search device using search text box and verify it **********//
		//**********************************************************//
		strstepDescription = "Verify device can be search with date field.";
		strexpectedResult =  "Device should be displayed when searched with date field.";
		if (isEventSuccessful)
		{
			isEventSuccessful = searchDevice_DI(Connected_Since);
			PerformAction("btnRefresh_Devices", Action.Click);
			if (isEventSuccessful)
			{
				isEventSuccessful=GetTextOrValue(dicOR.get("eleDeviceModel_ListView").replace("__INDEX__", "2"),"text").contains(Connected_Since);
				if(isEventSuccessful)
				{
					strActualResult = "Device displayed correctly when searched with date field.";
				}
				else
				{
					strActualResult = "Device did not displayed as per the search.";
				}
			}
			else
			{
				strActualResult = "Device can not be searched.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
	}

}