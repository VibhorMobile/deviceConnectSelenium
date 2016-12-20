package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: Last week of January
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-235
 */
public class _588_Verify_that_the_Password_field_on_create_User_accepts_at_least_8_characters extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ;
	private String strexpectedResult = "";
	private String strActualResult = "";
	String password[] = {"def" ,"devicec"};
	String password2[] = {"deviceco", "deviceconnect"};
	
 	 public final void testScript()
 	 {
 		String EmailAddress = dicCommon.get("EmailAddress");
 		
 		//**************************************************************************//
 		// Step 1 : login to deviceConnect with valid user and verify Devices page.
 		//**************************************************************************//
 		strstepDescription = "Login to deviceConnect with valid username and password and verify Devices page.";
 		strexpectedResult = "User should be logged in and navigated to Devices page.";
 		isEventSuccessful = Login();
 		if (isEventSuccessful)
 		{
 			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with user:  and navigated to Devices page.", "Pass");
 		}
 		else
 		{
 			strActualResult = strErrMsg_AppLib;
 			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
 		}

 		//**************************************************************************//
 		// Step 2 : Go to Users page and click on the Edit user button.
 		//*************************************************************
 		isEventSuccessful = GoToUsersPage();
 		
 		//**************************************************************************//
 		// Step 3 : Go to specific Users page
 		//**************************************************************************//
 		isEventSuccessful = GoToSpecificUserDetailsPage(EmailAddress);

 		// Step 4 :  Validate all passwords with different characters length.
 		strstepDescription = "Validate all passwords with different characters length.";
 		strexpectedResult = "Passwords with different characters length should be validated.";
 		for(int i =0; i< password.length ; i++)
 		{
 			isEventSuccessful = PerformAction("txtPassword", Action.Type, password[i]);
 			if(isEventSuccessful)
 			{
 				isEventSuccessful = PerformAction("txtConfirmPassword", Action.Type, password[i]);
 				isEventSuccessful = PerformAction("btnSave", Action.Click);
 				if(isEventSuccessful)
 				{
 					isEventSuccessful = GetTextOrValue("//div[@class='error-notice callout callout-danger']/div", "text").contains("Password must be at least 8 characters");
 					reporter.ReportStep(strstepDescription, strexpectedResult, "Password must be at least 8 characters" +  password[i] , isEventSuccessful);
 				}
			 }
		 }
	
		
   }

}
