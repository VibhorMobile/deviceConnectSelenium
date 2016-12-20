package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2201
 */

public class _247_Verify_that_logout_button_on_all_pages_is_changed_to_a_dropdown extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		//Step 1 - Login to deviceConnect
		 isEventSuccessful = Login(); 
		
		/////////////////////////////////////////////////////////////////////////////////////
		// Step 2 : Verify Logout button is under dropdown menu on devices page
		/////////////////////////////////////////////////////////////////////////////////////
		strStepDescription = "Verify Logout button is under dropdown menu on devices page.";
		strExpectedResult = "Logout button should be under dropdown menu on devices page.";
		isEventSuccessful = VerifyLogoutIsDropdownOption();
		//if (isEventSuccessful)
		//    strActualResult = "Logout button is under dropdown menu on devices page.";
		//else
		//    strActualResult = "Logout button is not under dropdown menu on devices page.";
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		/////////////////////////////////////////////////////////////////////////////////////
		// Step 3 : Verify Logout button is under dropdown menu on device details page
		/////////////////////////////////////////////////////////////////////////////////////
		strStepDescription = "Verify Logout button is under dropdown menu on device details page.";
		strExpectedResult = "Logout button should be under dropdown menu on device details page.";
		isEventSuccessful = GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results.");
		if (!isEventSuccessful) // continue only if there are some devices under android platform.
		{
			isEventSuccessful = SelectDevice("first");
			if (isEventSuccessful)
			{
				//isEventSuccessful = PerformAction(dicOR["eleMenuHolder"] + dicOR["lnkLogout"], Action.Exist);
				isEventSuccessful = VerifyLogoutIsDropdownOption();
				//if (isEventSuccessful)
				//    strActualResult = "Logout button is under dropdown menu on device details page.";
				//else
				//    strActualResult = "Device details page -- " + strActualResult;
			}
			else
			{
				strActualResult = "Device details page is not displayed.";
			}
		}
		else
		{
			strActualResult = "No devices displayed on devices page.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		/////////////////////////////////////////////////////////////////////////////////////
		// Step 4 : Verify Logout button is under dropdown menu on Users page
		/////////////////////////////////////////////////////////////////////////////////////
		strStepDescription = "Verify Logout button is under dropdown menu on Users page.";
		strExpectedResult = "Logout button should be under dropdown menu on Users page.";
		isEventSuccessful = navigateToNavBarPages("Users", "eleUsersHeader");
		isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", dicCommon.get("testerEmailAddress")),  Action.WaitForElement);
		if (isEventSuccessful)
		{
			
			isEventSuccessful = VerifyLogoutIsDropdownOption();
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		/////////////////////////////////////////////////////////////////////////////////////
		// Step 5 : Verify Logout button is under dropdown menu on Users Details page
		/////////////////////////////////////////////////////////////////////////////////////
		strStepDescription = "Verify Logout button is under dropdown menu on Users Details page.";
		strExpectedResult = "Logout button should be under dropdown menu on Users Details page.";
		isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", dicCommon.get("testerEmailAddress")), Action.Click);
		if (isEventSuccessful)
		{
			String Username =  getAttribute("eleUserName_UsersPage", "value");
			if (Username.startsWith(dicCommon.get("testerEmailAddress")))
			{
				isEventSuccessful = VerifyLogoutIsDropdownOption();
			}
			else
			{
				strActualResult = "User Details page not displayed on clicking on 'Edit' button.";
			}
		}
		else
		{
			strActualResult = "'Edit' button does not exist on Users page.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		/////////////////////////////////////////////////////////////////////////////////////
		// Step 6 : Verify Logout button is under dropdown menu on Create User page
		/////////////////////////////////////////////////////////////////////////////////////
		strStepDescription = "Verify Logout button is under dropdown menu on Create User page.";
		strExpectedResult = "Logout button should be under dropdown menu on Create User page.";
		isEventSuccessful = navigateToNavBarPages("Users", "eleUsersHeader");
		if (isEventSuccessful)
		{
			//Click on Menu button if menu list is already open.
			//if (PerformAction("lnkDevicesMenu", Action.isDisplayed))
			//    PerformAction("btnMenu", Action.Click);
			isEventSuccessful = PerformAction("btnCreateUser", Action.Click);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("btnTesterDropdown_CreateUserPage", Action.WaitForElement, "5");
				if (isEventSuccessful)
				{
					//isEventSuccessful = PerformAction(dicOR["eleMenuHolder"] + dicOR["lnkLogout"], Action.Exist);
					isEventSuccessful = VerifyLogoutIsDropdownOption();
					//if (isEventSuccessful)
					//    strActualResult = "Logout button is under dropdown menu on Create User page.";
					//else
					//    strActualResult = "Logout button is not under dropdown menu on Create User page.";
				}
				else
				{
					strActualResult = "User Details page not displayed on clicking on 'Create User' button.";
				}
			}
			else
			{
				strActualResult = "'Create User' button does not exist on Users page.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		/////////////////////////////////////////////////////////////////////////////////////
		// Step 6 : Verify Logout button is under dropdown menu on Applications page
		/////////////////////////////////////////////////////////////////////////////////////
		strStepDescription = "Verify Logout button is under dropdown menu on Applications page.";
		strExpectedResult = "Logout button should be under dropdown menu on Applications page.";
		isEventSuccessful = navigateToNavBarPages("Applications", "eleApplicationsHeader");
		if (isEventSuccessful)
		{
			isEventSuccessful = VerifyLogoutIsDropdownOption();
		
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		/////////////////////////////////////////////////////////////////////////////////////
		// Step 6 : Verify Logout button is under dropdown menu on Application details page
		/////////////////////////////////////////////////////////////////////////////////////
		strStepDescription = "Verify Logout button is under dropdown menu on Application details page.";
		strExpectedResult = "Logout button should be under dropdown menu on Application details page.";
		isEventSuccessful = SelectApplication("first");
		if (isEventSuccessful)
		{
			
			isEventSuccessful = VerifyLogoutIsDropdownOption();
			
		}
		else
		{
			strActualResult = "SelectApplication() -- " + strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);


	}

	private boolean VerifyLogoutIsDropdownOption()
	{
		boolean flag = false;
		strActualResult = "";
		flag = PerformAction("lnkLogout", Action.isDisplayed) && PerformAction("btnMenu", Action.isDisplayed);
		if (flag)
		{
			flag = PerformAction("btnMenu", Action.Click);
			if (flag)
			{
				if (!PerformAction("lnkLogout", Action.isDisplayed))
				{
					strActualResult = "'Logout' option is displayed as an option under 'Menu' option.";
				}
				else
				{
					strActualResult = "'Logout' option is displayed even when clicked on 'Menu' button. Probably it is not an option under the Menu button.";
				}
			}
			else
			{
				strActualResult = "Could not click on 'Menu' button.";
			}
		}
		else
		{
			flag = PerformAction("btnMenu", Action.Click);
			if (flag)
			{
				flag = PerformAction("lnkLogout", Action.isDisplayed);
				if (flag)
				{
					strActualResult = "'Logout' option is displayed as an option under 'Menu' option.";
				}
				else
				{
					strActualResult = "'Logout' option is not displayed under 'Menu' option.";
				}
			}
			else
			{
				strActualResult = "Could not click on 'Menu' button.";
			}
		}
		return flag;
	}
}