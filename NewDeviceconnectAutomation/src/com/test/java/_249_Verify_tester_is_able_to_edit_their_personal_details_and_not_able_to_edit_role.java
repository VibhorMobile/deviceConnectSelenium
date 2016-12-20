package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-281
 */

public class _249_Verify_tester_is_able_to_edit_their_personal_details_and_not_able_to_edit_role extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "", fieldsnotenabled = "";
	private String[] usersPageFields = {"firstName","middleName","lastName","notes","companyName","title","location",
			                            "line1","city","region","postalCode","country","mobilePhone","password","passwordConfirm" };
	

	public final void testScript()
	{
	
		//Step 1 - Login to deviceConnect
		 isEventSuccessful = Login(dicCommon.get("testerEmailAddress"),dicCommon.get("testerPassword")); 
		 
		// Step 2 : Verify 'Users' option is not accessible by tester user
		strStepDescription = "Go to 'Users' page and verify no user other than logged in user is visible on this page.";
		strExpectedResult = "No user other than logged in user should be visible on users page.";
		isEventSuccessful = !navigateToNavBarPages("Users", "eleUsersHeader");
		if (strErrMsg_AppLib.contains("link does not exist in the 'Menu' button's dropdown."))
		{
			strActualResult = "Users option is not there for tester user.";
			isEventSuccessful = true;
		}
		else
		{
			strActualResult = "selectFromMenu() -- " + strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		// Step 3 : Select 'Manage your account' option from menu dropdown to go to own user details.
		strStepDescription = "Select 'Manage your account' option from menu dropdown to go to own user details.";
		strExpectedResult = "User's own details page should be opened.";
		isEventSuccessful = selectFromMenu("Manage your account", "txtConfirmPassword");
		if (isEventSuccessful)
		{
			strActualResult = "user details page opened successfully.";
		}
		else
		{
			strActualResult = "selectFromMenu() -- " + strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		
		//Step 4 : Verify Test User is able to edit fields - First Name, Last Name, Password.....
		strActualResult = "";
		try
		{
			isEventSuccessful = false;
			waitForPageLoaded();
		    for(String values : usersPageFields)
		    {
		    	isEventSuccessful = PerformAction(dicOR.get("eleUserPagefields").replace("__Fields__", values), Action.isEnabled);
		    	if(values.equals("notes"))
		    	{
		    		isEventSuccessful = PerformAction("//textarea[@name='userEdit.user.contact.notes']", Action.isEnabled);
		    	}
		    	   if(!isEventSuccessful)
		    	   {
		    		  fieldsnotenabled = fieldsnotenabled + " " + ": ," + values;
		    	   }
		    }
		       if(fieldsnotenabled.equals("") && isEventSuccessful)
		       {
		    	   isEventSuccessful = true;
		    	   strActualResult = "TesterUser is able to edit their personal details on user details page.";
		       }
		       else
		       {
		    	   isEventSuccessful = false;
		    	   strActualResult = "TesterUser is not able to edit their personal details on user details page for the following fields :" + fieldsnotenabled; 
		       }
		
		
		}catch(RuntimeException e)
		{
			isEventSuccessful = false;
			strActualResult = "Exception in foreach loop used in script : " + e.getMessage();
		
		}
		  reporter.ReportStep("Verify Test User is able to edit fields - First Name, Last Name, Password ...", "Test User should be able to edit fields - First Name, Last Name, Password..", strActualResult, isEventSuccessful);

		
		//Step 7 : Verify tester is not able to edit role field
		isEventSuccessful = !PerformAction("btnTesterDropdown_CreateUserPage", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "User is not able to see edit role field.";
		}
		else
		{
			strActualResult = "User is able to see role field for his own account.";
		}
		reporter.ReportStep("Verify tester is not able to see edit role field.", "tester user should not be able to see role field.", strActualResult, isEventSuccessful);

		//Step 8 : Verify tester is not able to see active field
		isEventSuccessful = !PerformAction("chkActive_CreateUser", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "User is not able to see active field.";
		}
		else
		{
			strActualResult = "User is able to see active field for his own account.";
		}
		reporter.ReportStep("Verify tester is not able to see active role field.", "tester user should not be able to see active field.", strActualResult, isEventSuccessful);

		//Step 9 : verify user is able to click on save button
		isEventSuccessful = PerformAction("btnSave", Action.Click);
		if (isEventSuccessful)
		{
			strActualResult = "user is able to click on 'save' button.";
		}
		else
		{
			strActualResult = "User is not able to click on 'Save' button.";
		}
		reporter.ReportStep("Verify test user should be able to click on 'Save' button.", "test user should be able to click on 'Save' button.", strActualResult, isEventSuccessful);
	}
}