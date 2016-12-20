package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1648
 */
public class _730_Verify_deselection_or_selection_of_any_role_is_allowed_at_user_modification extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";
	String [] userType={"none"};
	
	public final void testScript()
	{
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to Users page //
		//**********************************************************// 
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToUsersPage();
		}
		
		//*********************************************//
		//Step 3 - Select user//
		//********************************************//
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			if( PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", testerEmailAddress), Action.WaitForElement))
			 {
				  isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", testerEmailAddress), Action.ClickUsingJS);
				  if (isEventSuccessful)
				  {
					  strActualResult = testerEmailAddress+" is displayed on list view.";
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
			reporter.ReportStep("Verify "+testerEmailAddress+" account visible.", testerEmailAddress+" account should be displayed", strActualResult, isEventSuccessful);	

		}
		
		//*******************************************************//
		//Step 4 - Verify de-selection of Tester role//
		//*****************************************************//
		if(isEventSuccessful)
		{
			strStepDescription = "Verify de-selection of Tester role." ;
			strExpectedResult =  "'Tester' role should be de-selected.";
			waitForPageLoaded();
			isEventSuccessful = selection_Deselection_Role(userType);
			if (isEventSuccessful)
			{
				strActualResult = "'Tester' role de-selected.";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		
		//*********************************************//
		//Step 5 - Select user//
		//********************************************//
		if(isEventSuccessful)
		{
			PerformAction("",Action.WaitForElement);
			waitForPageLoaded();
			if( PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", testerEmailAddress), Action.WaitForElement))
			{
				waitForPageLoaded();
				isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", testerEmailAddress), Action.ClickUsingJS);
				if (isEventSuccessful)
				{
					strActualResult = testerEmailAddress+" is displayed on list view.";
				}
				else
				{
					strActualResult = testerEmailAddress+" is not displayed on list view.";
				}
			}
			else
			{
				strActualResult = "Users list did not displayed on UI.";
			}
			reporter.ReportStep("Verify "+testerEmailAddress+" account visible.", testerEmailAddress+" account should be displayed", strActualResult, isEventSuccessful);	

		}

		//*******************************************************//
		//Step 6 - Verify selection of Tester role//
		//*****************************************************//
		if(isEventSuccessful)
		{
			userType[0]="Tester";
			strStepDescription = "Verify selection of Tester role." ;
			strExpectedResult =  "'Tester' role should be selected.";
			isEventSuccessful = selection_Deselection_Role(userType);
			if (isEventSuccessful)
			{
				strActualResult = "'Tester' role selected.";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
	}

}