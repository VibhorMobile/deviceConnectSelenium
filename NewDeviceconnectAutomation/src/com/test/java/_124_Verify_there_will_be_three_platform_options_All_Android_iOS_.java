package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-1347
 */
public class _124_Verify_there_will_be_three_platform_options_All_Android_iOS_ extends ScriptFuncLibrary
{
	private boolean isEventSuccessful = false;
	/*private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private String strErrMgs_Script = "", strActualResult = "";*/

	public final void testScript()
	{
		//*****************************************//
		//Step 1 - Login to deviceConnect
		//****************************************//
		isEventSuccessful = Login();
		
		//*************************************************************//
		//Step 2 - Verify Platform option - 'Android' in Devices page.
		//*************************************************************//            
		isEventSuccessful = selectPlatform("Android");

		//*************************************************************//
		//Step 3 - Verify Platform option - 'iOS' in Devices page.
		//*************************************************************//            
		isEventSuccessful = selectPlatform("iOS");
		
	}
}