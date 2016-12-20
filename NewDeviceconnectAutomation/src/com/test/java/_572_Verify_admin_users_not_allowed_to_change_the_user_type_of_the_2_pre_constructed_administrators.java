package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: 16-Feb-2016
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-22
 */

public class _572_Verify_admin_users_not_allowed_to_change_the_user_type_of_the_2_pre_constructed_administrators extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "", strStepDescription = "", strExpectedResult = "";
	private String strUserID = "";
	private String NotificationText = "";

	public final void testScript()
	{		
		
		 //Step 1 - Login to deviceConnect
		 isEventSuccessful = Login(); 
				
		 //Step 2 : Go to 'Users' page
		 isEventSuccessful = GoToUsersPage();
		 if (!isEventSuccessful)
		 {
			 return;
		 }

		 //Step 3 : Open Admin account  and try to edit role.
		 if( PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", "admin"), Action.WaitForElement))
		 {
			  isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", "admin"), Action.Click);
			  if (isEventSuccessful)
			  {
				  strActualResult = "Admin is displayed on list view.";
			  }
			  else
			  {
				  strActualResult = strErrMsg_AppLib;
			  }
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep("Verify admin account visible.", "Admin account should be displayed", strActualResult, isEventSuccessful);	

		//Step 4 : Verify user can not modify default admin role.
		strStepDescription = "Verify user can not modify default admin role.";
		strExpectedResult = "User should not be able to modify default admin role";
		isEventSuccessful = PerformAction("btnTesterDropdown_CreateUserPage", Action.isDisplayed);
		if (!isEventSuccessful)
		{
			isEventSuccessful=true;
			strActualResult = "User can not modify default admin role.";
		}
		else
		{
			strActualResult = "User can modify default admin role.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//Step 5 : Go to 'Users' page
		isEventSuccessful = GoToUsersPage();
		if (!isEventSuccessful)
		{
			return;
		}
		
		//Step 6 : Open sysadmin@localhost account  and try to edit role.
		 if( PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", "sysadmin@localhost"), Action.WaitForElement))
		 {
			  isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", "sysadmin@localhost"), Action.Click);
			  if (isEventSuccessful)
			  {
				  strActualResult = "Sysadmin is displayed on list view.";
			  }
			  else
			  {
				  strActualResult = strErrMsg_AppLib;
			  }
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep("Verify Sysadmin account visible.", "Sysadmin account should be displayed", strActualResult, isEventSuccessful);	

		//Step 7 : Verify user can not modify default Sysadmin role.
		strStepDescription = "Verify user can not modify default Sysadmin role.";
		strExpectedResult = "User should not be able to modify default Sysadmin role";
		isEventSuccessful = PerformAction("btnTesterDropdown_CreateUserPage", Action.isDisplayed);
		if (!isEventSuccessful)
		{
			isEventSuccessful=true;
			strActualResult = "User can not modify default Sysadmin role.";
		}
		else
		{
			strActualResult = "User can modify default Sysadmin role.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
	}
}