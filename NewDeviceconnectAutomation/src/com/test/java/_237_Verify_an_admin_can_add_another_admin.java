package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Jira Test Case Id: QA-2203
 */

public class _237_Verify_an_admin_can_add_another_admin extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";
	String [] role={"Administrator"};

	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();

		// Step 2 : Go to users page.
		isEventSuccessful =	GoToUsersPage( );
		
		// Step 2 : Create new Admin type user
		isEventSuccessful = createUser_SFL("admin","test","", "deviceconnect", role,true);
		PerformAction("browser",Action.Refresh);
		
        //Step 4 : Logout the current user
		isEventSuccessful = Logout();
		
		//Step 5 : Login to deviceConnect with just created admin user.
		isEventSuccessful = Login(dicOutput.get("EmailID"), "deviceconnect");
		
	}
}