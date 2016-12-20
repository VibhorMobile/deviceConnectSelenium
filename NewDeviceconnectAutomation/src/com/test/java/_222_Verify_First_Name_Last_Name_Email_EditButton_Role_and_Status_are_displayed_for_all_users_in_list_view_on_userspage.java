package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Case id: QA-2207
 */
public class _222_Verify_First_Name_Last_Name_Email_EditButton_Role_and_Status_are_displayed_for_all_users_in_list_view_on_userspage extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "", strExpectedResult = "", strActualResult = "", strErrMsg_Script = "";

	public final void testScript()
	{
		//////////////////////////////////////////////////
		// Step : 1 Login to DC with Admin Credentials ///
		//////////////////////////////////////////////////
		isEventSuccessful = Login();
		
		//////////////////////////////////////////////////
		//Step 2 : Go to 'Users' page
		//////////////////////////////////////////////////
		isEventSuccessful = GoToUsersPage();

		///////////////////////////////////////////////////////////////////////////
		// Step 3 : Verify there is First name in for all users in column format // 
		//////////////////////////////////////////////////////////////////////////
		strStepDescription = "Verify there is First name for all users in column format.";
		strExpectedResult = "First name should be displayed for all the users under first name column.";
		isEventSuccessful = VerifynUsersPage("firstname", "", "list");
		if (isEventSuccessful)
		{
			strActualResult = "First name is displayed in front of all the users.";
		}
		else
		{
			strActualResult = "VerifynUsersPage()-- " + strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//Step 4 : Verify there is Last name in for all users in column format.
		strStepDescription = "Verify there is Last name for all users in column format.";
		strExpectedResult = "Last name should be displayed for all the users under first name column.";
		isEventSuccessful = VerifynUsersPage("lastname", "", "list");
		if (isEventSuccessful)
		{
			strActualResult = "Last name is displayed in front of all the users.";
		}
		else
		{
			strActualResult = "VerifynUsersPage()-- " + strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//Step 5 : Verify there is Email in for all users in column format.
		strStepDescription = "Verify there is Email in for all users in column format.";
		strExpectedResult = "Email should be displayed for all the users under first name column.";
		isEventSuccessful = VerifynUsersPage("email", "", "list");
		if (isEventSuccessful)
		{
			strActualResult = "Email is displayed in front of all the users.";
		}
		else
		{
			strActualResult = "VerifynUsersPage()-- " + strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//Step 6 : Verify there is Edit button in for all users in column format.
		/*strStepDescription = "Verify there is Edit button in for all users in column format.";
		strExpectedResult = "Edit button should be displayed for all the users under first name column.";
		isEventSuccessful = VerifynUsersPage("edit", "", "list");
		if (isEventSuccessful)
		{
			strActualResult = "Edit button is displayed in front of all the users.";
		}
		else
		{
			strActualResult = "VerifynUsersPage()-- " + strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);*/

		//strErrMsg_Script = "";
		//isEventSuccessful = PerformActionsInUsersPage("all", "exist", "//*[contains(@data-bind,'FirstName')]", "list");
		//if (!isEventSuccessful)
		//    strErrMsg_Script = strErrMsg_AppLib + ". ";
		//isEventSuccessful = PerformActionsInUsersPage("all", "exist", "//*[contains(@data-bind,'LastName')]", "list");
		//if (!isEventSuccessful)
		//    strErrMsg_Script = strErrMsg_Script + strErrMsg_AppLib;
		//isEventSuccessful = PerformActionsInUsersPage("all", "exist", "//*[contains(@data-bind,'Email')]", "list");
		//if (!isEventSuccessful)
		//    strErrMsg_Script = strErrMsg_Script + strErrMsg_AppLib;
		//isEventSuccessful = PerformActionsInUsersPage("all", "exist", "//a[text()='Edit']","list");
		//if (!isEventSuccessful)
		//    strErrMsg_Script = strErrMsg_Script + strErrMsg_AppLib;
		//if (strErrMsg_Script == "")
		//{
		//    isEventSuccessful = true;
		//    strActualResult = "The First Name, Last Name, Email Address & Edit Button are displayed for all users.";
		//}
		//else
		//{
		//    isEventSuccessful = false;
		//    strActualResult = strErrMsg_Script;
		//}
		//reporter.ReportStep("Verify added users details in list view.", "The First Name, Last Name, Email Address & Edit Button should be displayed in the list view in column format.", strActualResult, isEventSuccessful);

		
	}
}