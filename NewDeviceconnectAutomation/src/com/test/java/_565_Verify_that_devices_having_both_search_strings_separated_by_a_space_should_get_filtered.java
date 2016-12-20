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
 * JIRA ID --> QA-1407
 */

public class _565_Verify_that_devices_having_both_search_strings_separated_by_a_space_should_get_filtered extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "",Vendor="",strstepDescription="",strexpectedResult="";

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
		
		//**********************************************************//
		// Step 3 - Search device using search text box and verify it **********//
		//**********************************************************//
		PerformAction("btnRefresh_Devices", Action.Click);
		searchDevice("iPad", "devicemodel");
		
		//**********************************************************//
		// Step 4 - Search device using search text box and verify it **********//
		//**********************************************************//  
		PerformAction("btnRefresh_Devices", Action.Click);
		searchDevice("iPad mini", "devicemodel");
	}

}