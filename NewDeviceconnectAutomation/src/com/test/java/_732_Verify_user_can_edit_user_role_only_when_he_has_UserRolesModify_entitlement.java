package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-464
 */
public class _732_Verify_user_can_edit_user_role_only_when_he_has_UserRolesModify_entitlement extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	Object[] Values = new Object[5]; 
	String [] Entitlement={"User area access","User roles modify"};
	Boolean [] value={true, true};
	
	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");
		
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with tester.
		//*************************************************************//                     
		isEventSuccessful = Login(EmailAddress,Password);


		//*************************************************************//                     
		// Step 2 : Verify users page is not accessible .
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify users page is not accessible";
			strexpectedResult = "Users page should not be accessible.";
			isEventSuccessful=!navigateToNavBarPages("Users", "eleUsersHeader");
			if(isEventSuccessful)
			{
				strActualResult = "Users page is not accessible";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}

		//****************************************************************************************//
		// Step 3 - Logout **//
		//****************************************************************************************//
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
		// Step 4 : login to deviceConnect with admin user.
		//*************************************************************//  
		if(isEventSuccessful)
		{
			isEventSuccessful = Login();
		}
		
		//*************************************************************//                     
		// Step 5 : Go to to users page.
		//*************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToUsersPage();
		}
		
		//*************************************************************//                     
		// Step 6 : Go to user page and set entitlement .
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Set entitlements for role: Tester";
			strexpectedResult = "User should be able to set entitlements.";
			isEventSuccessful=setUserRoleSettings("Tester", Entitlement, value);
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
		
		//****************************************************************************************//
		// Step 7 - Logout **//
		//****************************************************************************************//
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
		// Step 8 : login to deviceConnect with tester.
		//*************************************************************//                     
		isEventSuccessful = Login(EmailAddress,Password);
		
		//*************************************************************//                     
		// Step 9 : Navigate to users page.
		//*************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = navigateToNavBarPages("Users", "eleUsersHeader");
		}
		
		//*************************************************************//                     
		// Step 10 : Go to user page and remove entitlement .
		//*************************************************************//
		value[0]=false;
		value[1]=false;
		if(isEventSuccessful)
		{
			strstepDescription = "Set entitlements for role: Tester";
			strexpectedResult = "User should be able to set entitlements.";
			isEventSuccessful=setUserRoleSettings("Tester", Entitlement, value);
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

		
	}
}