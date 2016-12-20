package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

public class _128_Verify_the_currently_viewed_list_should_be_filtered_by_that_platform_if_the_user_clicks_any_other_platform extends ScriptFuncLibrary
{
	private boolean isEventSuccessful = false;
	/*	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private String strErrMgs_Script = "", strActualResult = "";
*/
	public final void testScript()
	{
		//*************************************************************//
		//Step 1 - Login to deviceConnect
		//*************************************************************//               
		isEventSuccessful = Login();

		//*************************************************************//               
		//Step 2 - Set filter for Platform option - 'Android'
		//*************************************************************//                           
		isEventSuccessful = selectPlatform("Android");

		//*************************************************************//               
		//Step 3 - Set filter for Platform option - 'iOS'
		//*************************************************************//               
		isEventSuccessful = selectPlatform("iOS");
	}
}