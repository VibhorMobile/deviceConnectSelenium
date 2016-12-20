package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-1306, QA-1252, QA-1357
 */
public class _593_Verify_that_the_sorting_for_the_devices_list_should_work_in_descending_order extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ;
	private String strexpectedResult = "" , strActualResult ;
	String headername[] = {"Date", "Event", "Device", "User", "Application"};
	
	public final void testScript()  
	{
	 
		//**** Step 1 - Login to DC. *****//
		isEventSuccessful = Login(); 
	 
		//*******************************************************//
		//Step 2 - Verify Notes columns is  sortable//
		//*****************************************************//
   	 	isEventSuccessful = selectStatus("Available");
     
   	 	isEventSuccessful = VerifySorting_sfl(dicOR.get("table_devicespage"),"Name");
	}
}
