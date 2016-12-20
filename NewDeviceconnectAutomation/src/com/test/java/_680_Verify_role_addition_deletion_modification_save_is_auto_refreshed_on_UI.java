package com.test.java;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1647
 */
public class _680_Verify_role_addition_deletion_modification_save_is_auto_refreshed_on_UI extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	String [] Entitlement={"Device application management","Device reboot"};
	Boolean [] value={true, true};
	
	public final void testScript() throws InterruptedException, IOException
	{
		
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
		// Step 3 : Verify add role auto refreshed .
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify added role auto refreshed on other tab.";
			strexpectedResult = "Added role should be auto refreshed on other tab.";
			isEventSuccessful=verifyRoleExistence("zTemporary");
			if(isEventSuccessful)
			{
				strActualResult = "Added role auto refreshed on other tab.";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		//*************************************************************//                     
		// Step 4 : Rename existing role .
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
		// Step 5 : Verify renamed role auto refreshed.
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify renamed role auto refreshed on other tab.";
			strexpectedResult = "Renamed role should be auto refreshed on other tab.";
			isEventSuccessful=verifyRoleExistence("RenamedRole");
			if(isEventSuccessful)
			{
				strActualResult = "Renamed role auto refreshed on other tab.";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		//*************************************************************//                     
		// Step 6 : Go to user page and set entitlement .
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
		// Step 9 : Verify modification of role auto refreshed.
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify modification of role auto refreshed on other tab.";
			strexpectedResult = "Modification of role should be auto refreshed on other tab.";
			isEventSuccessful=verifyModifiedRoleExistence("RenamedRole", Entitlement, value);
			if(isEventSuccessful)
			{
				strActualResult = "Modification of role auto refreshed on other tab.";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		//*************************************************************//                     
		// Step 10 : Go to user page and delete role .
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
		
		
			
		//*************************************************************//                     
		// Step 10 : Verify deletion of role auto refreshed.
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify deletion of role auto refreshed on other tab.";
			strexpectedResult = "Deletion of role should be auto refreshed on other tab.";
			isEventSuccessful=verifyRoleExistence("RenamedRole");
			if(!isEventSuccessful)
			{
				strActualResult = "Deletion of role auto refreshed on other tab.";
				isEventSuccessful=true;
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
				isEventSuccessful=false;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
	}
}