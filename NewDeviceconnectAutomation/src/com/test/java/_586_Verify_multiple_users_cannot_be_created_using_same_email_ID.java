package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: Last week of January
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-1382
 */
public class _586_Verify_multiple_users_cannot_be_created_using_same_email_ID extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ;
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String editedNotes = "Trying to serach the notes on device index page";
	Object[] values = new Object[2];
	
	public final void testScript()
	{
		
		// Step 1 - Login to deviceConnect//
		isEventSuccessful = Login(); 
		
		// Step 2 : Go to Users page
		isEventSuccessful =	GoToUsersPage( );
		
		// Step 3 : Click on 'Create User'.
		isEventSuccessful = GoToCreateUserPage();
		 
		//Step 4 : Add a new user 
		isEventSuccessful = createUser("TestFirstName", "TestLastName", "", "deviceconnect");
		if (isEventSuccessful)
		{
			strActualResult = "User " + dicOutput.get("EmailID") + " is created.";
		}
		else
		{
			strActualResult = "createUser()--" + strErrMsg_AppLib;
		}
		reporter.ReportStep("Add a new user.", "A new user should gets created.", strActualResult, isEventSuccessful);

		// Step 5 : Try to create user with the same email id  created.
		String username = dicOutput.get("EmailID");
		
		isEventSuccessful = !createUser("TestFirstName", "TestLastName", dicOutput.get("EmailID") , "deviceconnect");
		if (isEventSuccessful)
		{
			strActualResult = "User " + username + " is not created.";
		}
		else
		{
			strActualResult = "User " + username + " is created.";
		}
		reporter.ReportStep("Try to create user with the same created email id." , "User should not be created with same created email id" , strActualResult , isEventSuccessful );
		
	}	
}
