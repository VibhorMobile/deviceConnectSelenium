package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2187
 */
public class _163_Verify_editable_fields_on_Create_User_dialog extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";

	public final void testScript()
	{
		// Variables from datasheet//////////////////
//		String EmailAddress = dicCommon.get("EmailAddress");
//		String Password = dicCommon.get("Password");

		//*************************************************************//
		//Step 1 : Login to deviceConnect.
		//*************************************************************//
		isEventSuccessful = Login();

		//*************************************************************//
		//Step 2 : Go to 'Create User' page
		//*************************************************************//
		isEventSuccessful = GoToUsersPage();
		
		//*************************************************************//
		//Step 3 : Go to 'Create User' page
		//*************************************************************//
		isEventSuccessful = PerformAction("btnCreateUser", Action.WaitForElement, "10");
		  if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
		  {
			isEventSuccessful = PerformAction("btnCreateUser", Action.ClickUsingJS);
			isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
		  }
		 else
		 {
		   isEventSuccessful = PerformAction("btnCreateUser", Action.Click);
		 }
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("txtConfirmPassword", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "'Create User' page is displayed.";
			}
			else
			{
				strActualResult = "'Create User' page is not displayed on clicking on 'Create User' button.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Create User' button.";
		}
	
		reporter.ReportStep("Go to 'Create User' page", "'Create User' page should gets opened.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}

		//*************************************************************//
		//Step 4 : The following fields should be displayed and editable - First Name, Last Name 
		//*************************************************************//
		strActualResult = "";
		if (PerformAction("inpFirstNameCreateUser", Action.isDisplayed))
		{
			if (!PerformAction("inpFirstNameCreateUser", Action.Type, "TestFirstName"))
			{
				strActualResult = "First Name field is not editable ";
			}
		}
		else
		{
			strActualResult = "First Name field is not displayed ";
		}
		if (PerformAction("inpLastNameCreateUser", Action.isDisplayed))
		{
			if (!PerformAction("inpLastNameCreateUser", Action.Type, "TestFirstName"))
			{
				strActualResult = strActualResult + "First Name field is not editable. ";
			}
		}
		else
		{
			strActualResult = strActualResult + "Last Name field is not displayed. ";
		}
		if (strActualResult.equals(""))
		{
			isEventSuccessful = true;
			strActualResult = "Fields - First Name, Last Name are displayed and editable.";
		}
		else
		{
			isEventSuccessful = false;
		}
		reporter.ReportStep("Verify following fields - First Name, Last Name.", "Fields - First Name, Last Name should be displayed and editable.", strActualResult, isEventSuccessful);
	}
}