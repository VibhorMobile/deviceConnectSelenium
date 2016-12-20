package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1652
 */
public class _723_Verify_user_with_zero_entitlement_is_not_allowed_to_access_dC_via_CLI extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",outputText="";
	private String oldDeviceName="",Appname="";
	String [] role={"zTemporary"};
	Object[] Values = new Object[5]; 
	
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
		// Step 3 : Go to user page.
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
		// Step 4 : Create a new user with above entitlements.
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
		
		//*************************************************************//     
		// Step 5 : Go to Devices page.
		//*************************************************************//  
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToDevicesPage();
		}
		
		//**********************************************************//
		// Step 6 - Verify newly created user cannot use device**********//
		//**********************************************************//
		if (isEventSuccessful)
		{	
			Values = ExecuteCLICommand("client", "iOS", dicOutput.get("EmailID"), "deviceconnect", "", "desktop");
			isEventSuccessful = (boolean)Values[2];
			outputText=(String)Values[0];
			if (isEventSuccessful && outputText.contains("not found"))
			{
				strActualResult = "User is unable to use device.";
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify user is not able to use device." , "User should not be able to use device.", strActualResult, isEventSuccessful);
		}
		
		

		
		//*************************************************************//                     
		// Step 13 : Go to user page and delete role .
		//*************************************************************// 
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
		
		
		//*************************************************************//                     
		// Step 14 : Verify deletion of role.
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify 'zTemporary' role deleted.";
			strexpectedResult = "'zTemporary' role should get deleted.";
			isEventSuccessful=verifyRoleExistence("zTemporary");
			if(!isEventSuccessful)
			{
				strActualResult = "'zTemporary' role deleted.";
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