package com.test.java;

import java.util.ArrayList;
import java.util.List;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1655
 */
public class _724_Verify_list_of_all_default_and_user_defined_roles_are_displayed_on_user_creation_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",outputText="";
	private String oldDeviceName="",Appname="";
	List<String> roles=new ArrayList<String>(); 
	List<String> userroles=new ArrayList<String>(); 
	
	public final void testScript() throws InterruptedException
	{
		//*********************************//
		// Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();

		//*************************************************************//                     
		// Step 2 : Go to user page extract  roles list .
		//*************************************************************// 
		isEventSuccessful=GoToUsersPage();
		if(isEventSuccessful)
		{
			strstepDescription = "Verify user can extract view roles list on roles page.";
			strexpectedResult = "User should be able to extract view roles list on roles page.";
			isEventSuccessful=PerformAction("RolesLocatorUsersPage",Action.Click);
			if(isEventSuccessful)
			{
				roles=getColumnsValue("RolesList");
				strActualResult = "User can extract view roles list on roles page.";
			}
			else
			{
				strActualResult="User is unable to extract roles list on roles page.";
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		
		//*************************************************************//                     
		// Step 3 : Go to user page.
		//*************************************************************//
		isEventSuccessful = GoToUsersPage();
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnCreateUser", Action.Click);
			if (isEventSuccessful)
			{
				strActualResult = "Create users page is opened.";
			}
			else
			{
				strActualResult = "Create users page is not opened.";
			}
			reporter.ReportStep("Go to 'Create User' page", "'Users' page should gets opened.", strActualResult, isEventSuccessful);
		}
		
		//*************************************************************//                     
		// Step 4 : Create a new user with above entitlements.
		//*************************************************************//
		if (isEventSuccessful)
		{
			strstepDescription = "Verify user can view roles list on user creation page.";
			strexpectedResult = "User should be able to view roles list on user creation page.";
			userroles=getColumnsValue("roleOnUserDetailsPage");
			if(userroles.size()>0)
			{
				strActualResult = "User can view roles list on user creation page.";
			}
			else
			{
				strActualResult="User is unable to extract roles list on user creation page.";
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		
		//*************************************************************//                     
		// Step 5 : Go to user page and delete role .
		//*************************************************************// 
		if (isEventSuccessful)
		{
			strstepDescription = "Verify all roles displayed on user creation page";
			strexpectedResult = "All role should be displayed on user creation page.";
			if(roles.equals(userroles))
			if(isEventSuccessful)
			{
				strActualResult = "All roles displayed on user creation page.";
			}
			else
			{
				strActualResult="All role not displayed on user creation page";
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		
	}	
}