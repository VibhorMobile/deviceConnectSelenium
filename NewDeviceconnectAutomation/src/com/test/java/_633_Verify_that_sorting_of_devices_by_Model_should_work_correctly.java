package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: Third week of February
 * Last Modified Date: Same as creation date
 * jIRA tEST cASE iD:QA-1106
 */
public class _633_Verify_that_sorting_of_devices_by_Model_should_work_correctly extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ;
	private String strexpectedResult = "";
	private String strActualResult = "";
 

   public final void testScript()

   {
	
	   // Step 1 - Login to deviceConnect//
	   isEventSuccessful = Login();

	   //*********************************************//
	   //Step 2 - Verify Notes columns is visible//
	   //********************************************//
	   SelectColumn_Devices_SFL("Model");


	   //*******************************************************//
	   //Step 3 - Verify Notes columns is  sortable//
	   //*****************************************************//
	   isEventSuccessful = VerifySorting_sfl(dicOR.get("table_devicespage"),"Model");

   }
}
