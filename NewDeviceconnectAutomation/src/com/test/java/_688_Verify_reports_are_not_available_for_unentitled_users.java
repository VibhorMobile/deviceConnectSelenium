package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1815
 */
public class _688_Verify_reports_are_not_available_for_unentitled_users extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String oldDeviceName="",Appname="";
	String [] Entitlement={"User area access","System configuration area access"};
	Boolean [] value={true, true};
	String [] role={"zTemporary"};

	public final void testScript()
	{
		//*********************************//
		// Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();

		//*************************************************************//                     
		// Step 2 : Go to user page and add new role .
		//*************************************************************// 
		isEventSuccessful=GoToUsersPage();
		if(isEventSuccessful)
		{
			strstepDescription = "Verify admin can add role";
			strexpectedResult = "Admin user should be able to add role.";
			isEventSuccessful=addNewRole("zTemporary");
			if(isEventSuccessful)
			{
				strActualResult = "Admin user is able to add new role: zTemporary";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		//*************************************************************//                     
		// Step 3 : Go to user page and set entitlement .
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Set entitlements for role: Tester";
			strexpectedResult = "User should be able to set entitlements.";
			isEventSuccessful=setUserRoleSettings("zTemporary", Entitlement, value);
			if(isEventSuccessful)
			{
				strActualResult = "User is able to set entitlements.";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		//*************************************************************//                     
		// Step 4 : Go to user page.
		//*************************************************************//
		isEventSuccessful = GoToUsersPage();
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnCreateUser", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Users page is opened.";
			}
			else
			{
				strActualResult = "Users page is not opened.";
			}
			reporter.ReportStep("Go to 'Create User' page", "'Users' page should gets opened.", strActualResult, isEventSuccessful);
		}
		
		//*************************************************************//                     
		// Step 5 : Create a new user with above entitlements.
		//*************************************************************//
		if (isEventSuccessful)
		{
			isEventSuccessful = createUser("TestFirstName", "TestLastName", "", "deviceconnect",role,true,true);
			if (isEventSuccessful)
			{
				strActualResult = "User " + dicOutput.get("EmailID") + " is created.";
			}
			else
			{
				strActualResult = "createUser()--" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Add a new user.", "A new user should gets created.", strActualResult, isEventSuccessful);
		}
		
		//**********************************************************//
		// Step 6 - Logout //
		//**********************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify user logout from application";
			strexpectedResult =  "User should be able to logout.";
			isEventSuccessful=Logout();
			if (isEventSuccessful)
			{
				strActualResult = "User logout from application.";
			}
			else
			{
				strActualResult = "User is not able to logout.";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
		//*************************************************************//                     
		// Step 6 : login to deviceConnect with test user.
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Login to deviceConnect with valid test user.";
			strexpectedResult = "User should be logged in successfully.";
			isEventSuccessful = LoginToDC(dicOutput.get("EmailID"), "deviceconnect");
			if (!isEventSuccessful)
			{
				isEventSuccessful=true;
				reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with " + dicOutput.get("EmailID"), "Pass");
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
			}
		}
		
		//******************************************************************//
		// Step 7 : Verify History page cannot opened by user.*****//
		//******************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify History page cannot opened by user.";
			strexpectedResult =  "History page should not be available to user.";
			isEventSuccessful = navigateToNavLinkPages("4", "DatesDrpDwnonUsagePage");
			if (!isEventSuccessful)
			{
				isEventSuccessful=true;
				strActualResult="History page is not available to user.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "History page is available to user.";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}

		//******************************************************************//
		// Step 8 : Verify Usage page cannot opened by user.*****//
		//******************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify usage page cannot opened by user.";
			strexpectedResult =  "Usage page should not be available to user.";
			isEventSuccessful = navigateToNavLinkPages("3", "DatesDrpDwnonUsagePage");
			if (!isEventSuccessful)
			{
				isEventSuccessful=true;
				strActualResult="Usage page is not available to user.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "Usage page is available to user.";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
		//**********************************************************//
		// Step 9 - Logout //
		//**********************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify user logout from application";
			strexpectedResult =  "User should be able to logout.";
			isEventSuccessful=Logout();
			if (isEventSuccessful)
			{
				strActualResult = "User logout from application.";
			}
			else
			{
				strActualResult = "User is not able to logout.";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
		//*********************************//
		// Step 10 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();
		
		//*************************************************************//                     
		// Step 11 : Go to user page and delete role .
		//*************************************************************// 
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToUsersPage();
			strstepDescription = "Verify admin can delete role";
			strexpectedResult = "Admin user should be able to delete role.";
			isEventSuccessful= deleteRole("zTemporary");
			if(isEventSuccessful)
			{
				strActualResult = "Admin user is able to delete role: zTemporary";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
	}	
}