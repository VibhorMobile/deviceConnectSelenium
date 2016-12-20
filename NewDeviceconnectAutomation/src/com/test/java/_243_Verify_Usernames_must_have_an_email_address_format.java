package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2203
 */
public class _243_Verify_Usernames_must_have_an_email_address_format extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String[] emailAddressTextArray = {"abcddeviceconnect.com", "abcd#$@%#$@deviceconnect.com", "abcd@deviceconnect.@%$#", "deviceconnect.com};", "%@#"};
	//string[] emailAddressTextArray = { };
	private boolean flag = false, loopEntered = false;
	private String ValueWithTypingError = "", ValueWithNoError = "", allValuesTested = "",Username = "",strUserID = "";

	public final void testScript()
	{
		
		//Step 1 - Login to deviceConnect
		 isEventSuccessful = Login(); 
			
		// Step 2 : Go to Users page
		strstepDescription = "Go to Users page.";
		strexpectedResult = "Users page should be displayed";
		isEventSuccessful = navigateToNavBarPages("Users", "eleUsersHeader");
		waitForPageLoaded();
		if (isEventSuccessful)
		{
			strActualResult = "Users page is displayed";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//Step 3 : Click on Email id & verfiy Users details page is opened.
		strstepDescription = "Click on Email id" + " " + dicCommon.get("testerEmailAddress");
		strexpectedResult = "Users details page should be displayed";
		waitForPageLoaded();
		isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", dicCommon.get("testerEmailAddress")),  Action.WaitForElement);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", dicCommon.get("testerEmailAddress")), Action.Click);
			if (isEventSuccessful)
			{
				waitForPageLoaded();
				String Username =  getAttribute("eleUserName_UsersPage", "value");
				if (Username.startsWith(dicCommon.get("testerEmailAddress")))
				{
				strActualResult = "User Details page is displayed after click on" + " " + dicCommon.get("testerEmailAddress");
				}
				else 
				{
				strActualResult = "User Details page is not displayed after click on" + " " + dicCommon.get("testerEmailAddress");
				}
			}
			else
			{
				strActualResult = "Could not click on Email id on Users page for user : " + dicCommon.get("testerEmailAddress");
			}
		}
		else
		{
			strActualResult = "Unable to click on Email id on Users page for user : " + dicCommon.get("testerEmailAddress");
		} 
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}

		// Step 4 : Verify Login field accepts email format
		strstepDescription = "Verify Login field accepts email format.";
		strexpectedResult = "There should not be any error message when user enters anything in email format in 'Login' field.";
		waitForPageLoaded();
		isEventSuccessful = PerformAction("txtLogin", Action.Type, "abc@def.com");
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = PerformAction("eleEmailValidationMsg", Action.isNotDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Login field accepts email address format.";
			}
			else
			{
				strActualResult = "Error message displayed even when string in proper Email format is entered in Login field.";
			}
		}
		else
		{
			strActualResult = "Could not type text in Login field.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//Step 5 : Verify Login field does not accept strings in non-email format
		strstepDescription = "Verify Login field does not accept strings in non-email format.";
		strexpectedResult = "Error message should be displayed when user enter strings in non-email format in 'Login' field.";
		try
		{
			//allValuesTested = "abc@def.com";
			for (String value : emailAddressTextArray)
			{
				loopEntered = true;
				allValuesTested = allValuesTested + ", " + value;
				waitForPageLoaded();
				flag = PerformAction("txtLogin",  Action.Type, value) && PerformAction("btnSave",  Action.Click);
				waitForPageLoaded();
				 // Click on password textbox so that error message is displayed
				if (flag)
				{
					flag = PerformAction("eleEmailValidationMsg",  Action.isDisplayed);
					if (!flag)
					{
						ValueWithNoError = ValueWithNoError + ", " + value;
					}
				}
				else
				{
					ValueWithTypingError = ValueWithTypingError + ", " + value;
				}
			}
			if (ValueWithNoError.equals("") && ValueWithTypingError.equals("") && loopEntered == true)
			{
				flag = true;
				strActualResult = "Error message is displayed for all non-email format strings entered in 'Login' textbox on user details page: " + allValuesTested;
			}
			else
			{
				flag = false;
				if (!ValueWithTypingError.equals(""))
				{
					strActualResult = "Could not type following strings in 'Login' textbox :::: " + ValueWithTypingError;
				}
				if (!ValueWithNoError.equals(""))
				{
					strActualResult = strActualResult + "Correct error message not displayed for the following strings in non-email format ::: " + ValueWithNoError;
				}
			}
		}
		catch (RuntimeException e)
		{
			isEventSuccessful = false;
			strActualResult = "Exception in foreach loop used in script : " + e.getMessage();
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, flag);
	}
}