package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-974
 */
public class _649_Verify_renaming_and_modification_of_user_role extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	Object[] Values = new Object[5]; 
	String [] Entitlement={"Device application management","Device reboot"};
	Boolean [] value={true, true};
	
	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//                     
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
		// Step 3 : Go to user page and rename role .
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify admin can rename role";
			strexpectedResult = "Admin user should be able to rename role.";
			isEventSuccessful= renameRole("zTemporary","RenamedRole");
			if(isEventSuccessful)
			{
				strActualResult = "Admin user is able to rename role: zTemporary to : RenamedRole";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		//*************************************************************//                     
		// Step 4 : Go to user page and rename role to an existing role name .
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify duplicate role name cannot exist";
			strexpectedResult = "Role names should not be same.";
			isEventSuccessful= renameRole("RenamedRole","Administrator");
			if(isEventSuccessful)
			{
				strActualResult = "Admin user is able to rename role: RenamedRole to : Administrator";
				isEventSuccessful=false;
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
				isEventSuccessful=true;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
			PerformAction("browser",Action.Refresh);
		}
		
		//*************************************************************//                     
		// Step 5 : Go to user page and set entitlement .
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Set entitlements for role: Tester";
			strexpectedResult = "User should be able to set entitlements.";
			isEventSuccessful=setUserRoleSettings("RenamedRole", Entitlement, value);
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
		// Step 5 : Go to user page and remove entitlement .
		//*************************************************************//
		value[0]=false;
		value[1]=false;
		if(isEventSuccessful)
		{
			strstepDescription = "Set entitlements for role: Tester";
			strexpectedResult = "User should be able to set entitlements.";
			isEventSuccessful=setUserRoleSettings("RenamedRole", Entitlement, value);
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
		// Step 6 : Go to user page and delete role .
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify admin can delete role";
			strexpectedResult = "Admin user should be able to delete role.";
			isEventSuccessful= deleteRole("RenamedRole");
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