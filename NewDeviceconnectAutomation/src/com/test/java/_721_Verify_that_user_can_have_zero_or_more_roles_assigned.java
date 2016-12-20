package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-96
 */
public class _721_Verify_that_user_can_have_zero_or_more_roles_assigned extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String oldDeviceName="",Appname="";
	String [] Entitlement={"Application area access ","Device area access","Device list"};
	Boolean [] value={true, true, true};
	String [] role={"zTemporary","Tester"};
	String [] roles={};

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
			strstepDescription = "Set entitlements for role: zTemporary";
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
		
		//*************************************************************//     
		// Step 6 : Go to Users page.
		//*************************************************************//  
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToUsersPage();
		}
		
		//**********************************************************//
		// Step 7 - Verify roles of newly created users**********//
		//**********************************************************//
		if(isEventSuccessful)
		{
			strStepDescription = "Verify roles of newly created user are: zTemporary and Tester";
			strExpectedResult =  "Roles of newly created user should be: zTemporary and Tester";
			isEventSuccessful = searchDevice_DI(dicOutput.get("EmailID"));
			if(isEventSuccessful)
			{
				isEventSuccessful=VerifynUsersPage("userrole","zTemporary, Tester","list");
				if(isEventSuccessful)
				{
					strActualResult="Roles of newly created user are: zTemporary and Tester";
				}
				else
				{
					strActualResult="Did not found roles of newly created user";
				}
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		//*************************************************************//                     
		// Step 8 : Go to user page and delete role .
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
		
		//*************************************************************//                     
		// Step 9 : Verify deletion of role.
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
			
		//*************************************************************//                     
		// Step 10 : Go to user page.
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
		// Step 11 : Create a new user with no roles.
		//*************************************************************//
		if (isEventSuccessful)
		{
			isEventSuccessful = createUser("TestFirstName", "TestLastName", "", "deviceconnect",roles,true,true);
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
		// Step 12 - Logout //
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
		// Step 13 : login to deviceConnect with test user.
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify user is unable to login.";
			strexpectedResult = "User should be unable to logged in successfully.";
			waitForPageLoaded();
			if (PerformAction("btnLogin", Action.isDisplayed))
			{
				PerformAction("inpEmailAddress", Action.Type, dicOutput.get("EmailID"));
				PerformAction("inpPassword", Action.Type, "deviceconnect");
				if (PerformAction("btnLogin", Action.ClickUsingJS))
				{
					waitForPageLoaded();
					PerformAction("browser", Action.WaitForPageToLoad);
					isEventSuccessful = PerformAction("btnMenu", Action.Exist);
					if(isEventSuccessful)
					{
						strActualResult="User logged in successfully";
						isEventSuccessful=false;
					}
					else
					{
						strActualResult="User is not able to login";
						isEventSuccessful=true;
					}
				}
				else
				{
					strActualResult="Unable to click on login button";
				}
			}
			else
			{
				strActualResult="Login page did not displayed";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		}
		
	}	
}