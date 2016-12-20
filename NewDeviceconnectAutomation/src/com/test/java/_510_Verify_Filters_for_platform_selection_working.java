package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-743
 */

public class _510_Verify_Filters_for_platform_selection_working extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	String [] Android={"Android"};
	

	public final void testScript()
	{
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();
		
		//******************************************************************//
		//*** Step 2 : Go to applications page and Select platform Android*****//
		//******************************************************************//  
		isEventSuccessful=GoToApplicationsPage();
		strstepDescription = "Select platform Android";
		strexpectedResult = "Only Android platform checkbox selected";
		isEventSuccessful=selectCheckboxes_DI(Android, "chkPlatform_Devices");
		if(isEventSuccessful)
		{
			strActualResult="Only Android platform checkbox selected";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		
		//**************************************************************************//
		// Step 3 : Verify only android apps displayed
		//**************************************************************************//
		strstepDescription = "Verify only android apps displayed";
		strexpectedResult = "Only Android apps should be displayed";
		isEventSuccessful=verifyApplicationsFilteredOSColumn();
		if(isEventSuccessful)
		{
			strActualResult="Only Android apps displayed.";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
	}	
}