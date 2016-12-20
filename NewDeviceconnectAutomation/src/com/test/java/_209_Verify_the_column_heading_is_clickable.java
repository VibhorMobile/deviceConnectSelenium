package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2198
 */
public class _209_Verify_the_column_heading_is_clickable extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";

	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//*********************************************//
		//Step 2 - Verify no. of columns are displayed//
		//********************************************//
		SelectColumn_Devices_SFL("Manufacturer");
		
		//*******************************************************//
		//Step 3 - Verify columns are click able//
		//*****************************************************//
	   
		isEventSuccessful = VerifySorting_sfl(dicOR.get("table_devicespage"),"Manufacturer");
		
		
	}

}