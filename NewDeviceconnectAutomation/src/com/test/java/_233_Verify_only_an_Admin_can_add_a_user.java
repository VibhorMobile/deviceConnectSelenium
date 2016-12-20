package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-624
 */

public class _233_Verify_only_an_Admin_can_add_a_user extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();

		////////////////////////////////////////////////////////
		// Step 2 : Go to Users page
		////////////////////////////////////////////////////////
		isEventSuccessful =	GoToUsersPage( );
		
		// //////////////////////////////////////////////////////
		// Step 2 : Click on 'Create User'.
		// //////////////////////////////////////////////////////
		isEventSuccessful = GoToCreateUserPage();
				
		///////////////////////////////////////
		// Step 3 : Log out of the application.
		///////////////////////////////////////
	     isEventSuccessful =	LogoutDC();
	     
		///////////////////////////////////////////////////////////////////////////
		// Step 4 : login to deviceConnect with test user and verify Devices page.
		///////////////////////////////////////////////////////////////////////////
	     isEventSuccessful = Login(dicCommon.get("testerEmailAddress"),dicCommon.get("testerPassword"));
		
		////////////////////////////////////////////////////////////////////////////
		// Step 5 : Verify 'Users' link is not displayed.
		////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Verify 'Users' link is not displayed for test user.";
		strexpectedResult = "'Users' link should not be displayed for test user.";
		isEventSuccessful = !PerformAction("//a[contains(@class,'toolbarItem')]/span[text()='Users']", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Users link is not displayed for test user." ;
		}
		else
		{
			strActualResult = "Users link is displayed for test user. : " ;
	    }
		
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}