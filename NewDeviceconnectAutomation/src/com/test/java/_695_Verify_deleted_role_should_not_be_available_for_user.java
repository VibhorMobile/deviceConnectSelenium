package com.test.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-306
 */
public class _695_Verify_deleted_role_should_not_be_available_for_user extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	List<WebElement> roles=new ArrayList<WebElement>();
	
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
			isEventSuccessful=addNewRole("RoletobeDeleted_Automation");
			if(isEventSuccessful)
				strActualResult = "User is able to add new role: RoletobeDeleted_Automation";
			else
				strActualResult=""+strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		//*************************************************************//                     
		// Step 3 : Go to user page and Rename role .
		//*************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Verify User can rename the role created.";
			strexpectedResult = "User should be able to rename role.";
			isEventSuccessful= renameRole("RoletobeDeleted_Automation", "RoleTobeUpdated_beforeDeletion");
			if(isEventSuccessful)
				strActualResult = "User is able to rename role : RoletobeDeleted_Automation to RoleTobeUpdated_beforeDeletion";
			else
				strActualResult=""+strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
				
		
		//*************************************************************//                     
		// Step 4 : Go to user page and delete the renamed role
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify User can delete role";
			strexpectedResult = "User should be able to delete role.";
			isEventSuccessful= deleteRole("RoleTobeUpdated_beforeDeletion");
			if(isEventSuccessful)
			{
				strActualResult = "Admin user is able to delete role: RoleTobeUpdated_beforeDeletion";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		//*************************************************************//                     
		// Step 5 : Verify that after deleting the role, the role isn't displayed to the user without performing page refresh
		//*************************************************************//
		strstepDescription = "Verify that the role is not displayed for the user without performing page refresh";
		strexpectedResult = "Role should get deleted from User Roles page without the need to perform page refresh.";
		verifyRoleExistence("RoleTobeUpdated_beforeDeletion");
	}
}