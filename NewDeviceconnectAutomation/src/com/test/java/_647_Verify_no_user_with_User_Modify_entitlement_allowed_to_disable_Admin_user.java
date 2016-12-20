package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1814
 */
public class _647_Verify_no_user_with_User_Modify_entitlement_allowed_to_disable_Admin_user extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	Object[] Values = new Object[5]; 
	String [] Entitlement={"User area access","User list","User modify","User modify role"};
	Boolean [] value={true,true,true,true};
	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful = Login();


		//*************************************************************//                     
		// Step 2 : Go to user page and set entitlement .
		//*************************************************************// 
		isEventSuccessful=GoToUsersPage();
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
		// Step 4 : login to deviceConnect with test user.
		//*************************************************************//  
		if(isEventSuccessful)
		{
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
		}
				
		//*************************************************************//                     
		// Step 5 : Go To Users Page
		//*************************************************************//  
		if(isEventSuccessful)
		{
			isEventSuccessful=navigateToNavBarPages("Users", "eleUsersHeader");
		}
		
		// Step 6 : Click on 'Edit' button for admin user.
		if(isEventSuccessful)
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
		
		// Step 7: Verify user cannot change Admin user role
		if(isEventSuccessful)
		{
			strstepDescription = "Verify user is unable to change role of admin user.";
			strexpectedResult = "User should not be able to change admin user role";
			if(getelementsList("rolesUsersPage").size()<=0)
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "User cannot change role of admin user", true);
			}
			else
			{
				strActualResult = "User can change Admin user role.";
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, false);
			}
		}
		
		
		//****************************************************************************************//
		// Step 8 - Logout **//
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
		// Step 9 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful = Login();
		
		// Step 10: Disable user modify entitlement
		isEventSuccessful=GoToUsersPage();
		if(isEventSuccessful)
		{
			strstepDescription = "Set entitlements for role: Tester";
			strexpectedResult = "User should be able to set entitlements.";
			value[0]=false;
			value[1]=false;
			value[2]=false;
			value[3]=false;
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