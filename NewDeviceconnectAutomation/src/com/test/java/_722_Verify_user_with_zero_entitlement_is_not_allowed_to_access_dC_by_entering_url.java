package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1653
 */
public class _722_Verify_user_with_zero_entitlement_is_not_allowed_to_access_dC_by_entering_url extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String oldDeviceName="",Appname="";
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
		// Step 6 - Go to device details page of first device**********//
		//**********************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = (Boolean)GoTofirstDeviceDetailsPage()[0];
		}
		
		// Step 7: Get User's URL
		if(isEventSuccessful)
		{
			isEventSuccessful=PerformAction("browser",Action.GetURL);
		}

		//****************************************************************************************//
		// Step 8 - Logout **//
		//****************************************************************************************//
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

		//*************************************************************//                     
		// Step 9 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid test user.";
		strexpectedResult = "User should be logged in successfully.";
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
					isEventSuccessful=true;
				}
				else
				{
					strActualResult="User is not able to login";
					isEventSuccessful=false;
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

		//**********************************************************//
		// Step 10 - Navigate to copied URL and get the error //
		//**********************************************************//   
		if(isEventSuccessful)
		{
			strstepDescription = "Verify when user navigate to copied URL will get the error.";
			strexpectedResult = "User should get the error on navigating..";
			isEventSuccessful = PerformAction("browser",Action.Navigate,dicCommon.get("ApplicationURL")+"/#/Device/Index");
			isEventSuccessful=PerformAction("errorPopup",Action.isDisplayed);
			if(isEventSuccessful)
			{
				strActualResult="User got the error on navigating.";
			}
			else
			{
				strActualResult="User did not get the error on navigating.";
			}

			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
		//****************************************************************************************//
		// Step 11 - Logout **//
		//****************************************************************************************//
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
		
		//*********************************//
		// Step 12 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();
		
		//*************************************************************//                     
		// Step 13 : Go to user page and delete role .
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