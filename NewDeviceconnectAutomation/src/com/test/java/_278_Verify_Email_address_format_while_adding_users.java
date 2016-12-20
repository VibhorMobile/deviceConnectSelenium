package com.test.java;

import java.util.Calendar;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2203
 */
public class _278_Verify_Email_address_format_while_adding_users extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String username = "abc!#$%&'*+-/=?^_`{|}~00998" + Calendar.getInstance().getTime().getTime()  + "@deviceconnect.com";

	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		if(!isEventSuccessful)
			return;
	
		//**************************************************************************//
		//Step 2 - Select Users page
		//**************************************************************************//
		isEventSuccessful = GoToUsersPage();
		if(!isEventSuccessful)
			return;

		//**************************************************************************//
		//Step 3 - Click "Create User" button
		//**************************************************************************//
		strstepDescription = "Click on Create User button";
		strexpectedResult = "Users details page should be displayed";
		if( PerformAction("", Action.WaitForElement,"60"))
		{
			return;
		}
		   if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
		   {
		      isEventSuccessful = PerformAction("btnCreateUser", Action.ClickUsingJS);
		      PerformAction("browser", "waitforpagetoload" );
		   }
		  else
		  {
		    isEventSuccessful = PerformAction("btnCreateUser", Action.Click);
		  }
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleCreateUserHeader_usrDet", Action.WaitForElement, "10");
			if (isEventSuccessful)
			{
				strActualResult = "Users details page is displayed.";
			}
			else
			{
				strActualResult = "Users details page is not displayed.";
			}
		}
		else
		{
			strActualResult = "Unable to click on Create user button.";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		//Step 4 - Verify Invalid Email format
		//**************************************************************************//
		strstepDescription = "Verify Invalid Email format";
		strexpectedResult = "Email field should display error message";

		isEventSuccessful = PerformAction("txtLogin", Action.Type, "@abc.com");
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnSave", Action.Click);
			waitForPageLoaded();
			if (PerformAction("eleEmailValidationMsg", Action.isDisplayed))
			{
				strActualResult = "Error message is displayed for invalid Email address";
							}
			else
			{
				strActualResult = "Error message is not displayed for invalid email address.";
				isEventSuccessful = false;
			}
		}
		else
		{
			strActualResult = "Unable to enter the email address.";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//**************************************************************************//
		//Step 5 - Verify Invalid Email format
		//**************************************************************************//
		strstepDescription = "Verify Invalid Email format";
		strexpectedResult = "Email field should display error message";

		isEventSuccessful = PerformAction("txtLogin", Action.Type, username);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnSave", Action.Click);
			if (PerformAction("eleEmailValidationMsg", Action.isDisplayed))
			{
				strActualResult = "Error message is displayed for invalid Email address";
							}
			else
			{
				strActualResult = "Error message is not displayed for invalid email address.";
				isEventSuccessful = false;
			}
		}
		else
		{
			strActualResult = "Unable to enter the email address.";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*************************************//
		//Step 6 - Create User with valid email
		//*************************************//
		strstepDescription = "Create User with valid email";
		strexpectedResult = "User should be created with valid email format.";
 
		isEventSuccessful = createUser("Test", "User", "", "deviceconnect");
		if (isEventSuccessful)
		{
			strActualResult = "User created with the valid email address format" + dicOutput.get("EmailID");
		}
		else
		{
			strActualResult = "Unable to create user with email - " + dicOutput.get("EmailID");
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

	}
}