package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-406
 */
public class _727_Verify_data_displayed_for_User_Created_event_on_user_details_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",fName="",lName="";

	public final void testScript()
	{
		String EmailAddress = dicCommon.get("EmailAddress");
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();
		
		//******************************************************************//
		//*** Step 2 : Select first device*****//
		//******************************************************************//  
		if(isEventSuccessful)
		{
			isEventSuccessful=GoToUsersPage();
			PerformAction("browser","waitforpagetoload");
			isEventSuccessful=searchUser(EmailAddress, "email");
		}
		
		
		//**************************************************************************//
		// Step 3 : Go to specific user account
		//**************************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Verify user account page opened";
			strexpectedResult = "User account page should be displayed";
			PerformAction("browser","waitforpagetoload");
			isEventSuccessful=PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", "deepak_admin@ml.com"), Action.Click);
			if(isEventSuccessful)
			{
				strActualResult="User account page displayed";
			}
			else
			{
				strActualResult="Unable to click on given mail id";
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		//Extract user first name and last name
		fName=getAttribute("inpFirstNameCreateUser","value");
		lName=getAttribute("inpLastNameCreateUser","value");
		
		//**************************************************************************//
		// Step 4 : Verify user header info
		//**************************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Verify user header contain user's name";
			strexpectedResult = "User header should displaye user's name";
			isEventSuccessful=GetTextOrValue("usersHeaderInfo", "text").equals(fName+" "+lName);
			if(isEventSuccessful)
			{
				strActualResult="User header displayed user's name";
			}
			else
			{
				strActualResult="Unable to get user's name";
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		//******************************************************************//
		//*** Step 5 : navigate to history page. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToHistoryPage("3");
		}
		
		//**************************************************************************//
		// Step 6 : Verify user header info on History page
		//**************************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Verify user's name is same on history page";
			strexpectedResult = "User's name should be same on history page";
			isEventSuccessful=GetTextOrValue("usersHeaderInfo", "text").equals(fName+" "+lName);
			if(isEventSuccessful)
			{
				strActualResult="User's name is same on history page";
			}
			else
			{
				strActualResult="Unable to get user's name";
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 7 : Verify 'User Created' event displayed in user's history table
		//**************************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Verify 'User Created' event info displayed in user's history table.";
			strexpectedResult = "'User Created' event info should be displayed in user's history table";
			isEventSuccessful=VerifyUsersHistoryPage("event","user created");
			if(isEventSuccessful)
			{
				strActualResult="'User Created' event info displayed in user's history table.";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
	}	
}