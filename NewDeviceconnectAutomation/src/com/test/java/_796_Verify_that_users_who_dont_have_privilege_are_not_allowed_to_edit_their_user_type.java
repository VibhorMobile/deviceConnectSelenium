package com.test.java;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-281
 */
public class _796_Verify_that_users_who_dont_have_privilege_are_not_allowed_to_edit_their_user_type extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private boolean isStepSuccessful=false;

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		try{


			String EmailAddress = dicCommon.get("EmailAddress");
			String Password = dicCommon.get("Password");


			//*************************************************************//                     
			// Step 1 : login to deviceConnect with Tester user.
			//*************************************************************//                     
			isEventSuccessful = Login(EmailAddress,Password);


			//*************************************************************/                     
			// Step 2 : Go to Manage your Account
			//*************************************************************//
			strstepDescription="Click on Manage Your Account from Dropdown";
			strExpectedResult="Successfully clicked Manage your Account from Dropdown";
			isEventSuccessful=PerformAction(dicOR.get("eleUserEmail_UsersPage"), Action.Click);
			if(isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("eleManageAccount_UsersPage"), Action.Click);
				if(isEventSuccessful){
					isEventSuccessful=true;
					strActualResult="Clicked on Manage your Account from Dropdown";
				}
				else{

					strActualResult="Not able to click on Manage your Account from Dropdown";
					isEventSuccessful=false;
				}
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//*************************************************************/                     
			// Step 3 : Verify User type field is not available for user
			//*************************************************************//
			strstepDescription="Verify User type field is not available for User";
			strExpectedResult="User type field should not be available for user";


			isEventSuccessful=PerformAction(dicOR.get("roleBlockUsersPage"), Action.isDisplayed);
			if(isEventSuccessful){
				strActualResult="Usertype field is available for user";
			}

			else{
				strActualResult="User type field is not available for user";
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);




		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "View Token Field for User--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

}
