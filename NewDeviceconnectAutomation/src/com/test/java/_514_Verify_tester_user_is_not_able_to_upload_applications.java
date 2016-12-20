package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1082, QA-743
 */

public class _514_Verify_tester_user_is_not_able_to_upload_applications extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private int counter=0;

	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid test user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with " + EmailAddress, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
		
		//******************************************************************//
		//*** Step 2 : Navigate to Applications Page. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToApplicationsPage();
		}
		else
		{
			return;
		}
		
		//******************************************************************//
		//*** Step 3 : Click on upload Application and upload application*****//
		//******************************************************************//
		strstepDescription = "Verify tester is not able to upload application";
		strexpectedResult =  "Tester should not be able to upload application";
		if(isEventSuccessful)
		{
			isEventSuccessful=PerformAction("uploadApplicationlnk",Action.isNotDisplayed);
			if(isEventSuccessful)
			{
				strActualResult = "Tester is not able to upload application";
			}
			else
			{
				strActualResult = "Tester is able to upload application";
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}	
}