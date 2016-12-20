package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-485
 */
public class _667_Verify_Test_users_cannot_view_or_edit_other_user_token extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	Object[] Values = new Object[5]; 
	public final void testScript() throws InterruptedException, IOException
	{
		
		// Variables from datasheet//////////////////
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with tester user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid test user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(testerEmailAddress, testerPassword);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with " + testerEmailAddress, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}


		//*************************************************************//                     
		// Step 2 : Go to user page.
		//*************************************************************// 
		strstepDescription = "Verify tester user cannot open users page.";
		strexpectedResult = "Tester user should not be able to open users page.";
		isEventSuccessful=navigateToNavBarPages("Users", "eleUsersHeader");
		if (isEventSuccessful)
		{
			if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
			{
			   isEventSuccessful = PerformAction("btnCreateUser", Action.WaitForElement,"20");
			}
			isEventSuccessful = PerformAction("btnCreateUser", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Users page is opened.";
				isEventSuccessful=false;
			}
			else
			{
				strActualResult = "Users page is not opened.";
				isEventSuccessful=true;
			}
		}
		else
		{
			strActualResult = "Users page is not opened";
			isEventSuccessful=true;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		
		// Step 3 : Click on 'Edit' button for admin user.
		if(!isEventSuccessful)
		{
			if( PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", "admin"), Action.WaitForElement))
			{
				isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", "admin"), Action.Click);
				if (isEventSuccessful)
				{
					strActualResult = "Admin user page opened";
				}
				else
				{
					strActualResult ="Unable to open admin user page.";
				}
			}
			else
			{
				strActualResult ="Unable to find admin user page.";
			}
			reporter.ReportStep("Verify admin user page opened.", "Admin user page should be opened.", strActualResult, isEventSuccessful);
		}
		
		// Step 4: Verify user cannot view Token
		if(isEventSuccessful)
		{
			strstepDescription = "Verify user is unable to view admin token.";
			strexpectedResult = "User should not be able to view admin token.";
			isEventSuccessful=PerformAction("viewTokenlnk",Action.isNotDisplayed);
			if(isEventSuccessful)
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "User cannot view admin token", true);
			}
			else
			{
				strActualResult = "User can view admin token.";
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, false);
			}
		}
		
		
		


	}
}