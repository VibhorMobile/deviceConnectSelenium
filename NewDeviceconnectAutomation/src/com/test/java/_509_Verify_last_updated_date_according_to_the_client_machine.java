package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-900, QA-743
 */

public class _509_Verify_last_updated_date_according_to_the_client_machine extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();
		
		//******************************************************************//
		//*** Step 2 : Select first device*****//
		//******************************************************************//  
		isEventSuccessful=GoToApplicationsPage();
		searchDevice_DI("Aldiko");
		
		//**************************************************************************//
		// Step 3 : Verify application updated
		//**************************************************************************//
		strstepDescription = "Verify application updated";
		strexpectedResult = "Application should be updated";
		isEventSuccessful=verifylastupdateDateisTodays_Date(getLastUpdateDetails());
		if(isEventSuccessful)
		{
			strActualResult="Application updated successfully";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
	}	
}