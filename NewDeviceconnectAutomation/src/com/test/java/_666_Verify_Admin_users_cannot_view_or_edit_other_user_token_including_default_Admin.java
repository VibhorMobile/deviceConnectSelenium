package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-525
 */
public class _666_Verify_Admin_users_cannot_view_or_edit_other_user_token_including_default_Admin extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	Object[] Values = new Object[5]; 
	String [] Entitlement={"User area access","User list","User modify","User modify role"};
	Boolean [] value={true,true,true,true};
	public final void testScript() throws InterruptedException, IOException
	{
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful = Login();


		//*************************************************************//                     
		// Step 2 : Go to user page.
		//*************************************************************// 
		isEventSuccessful=GoToUsersPage();
		
		
		// Step 3 : Click on 'Edit' button for admin user.
		if(isEventSuccessful)
		{
			if( PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", "admin"), Action.WaitForElement))
			{
				isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", "admin"), Action.Click);
				if (isEventSuccessful)
				{
					strActualResult = "Admin user page opened";
				}
				else
				{
					strActualResult ="Unable to open admin user page.";
				}
			}
			else
			{
				strActualResult ="Unable to find admin user page.";
			}
			reporter.ReportStep("Verify admin user page opened.", "Admin user page should be opened.", strActualResult, isEventSuccessful);
		}
		
		
		//Step 4: Get URL
		if(isEventSuccessful)
		{
			isEventSuccessful=PerformAction("browser",Action.GetURL);
		}
		
		// Step 4: Verify user cannot view Token
		if(isEventSuccessful)
		{
			strstepDescription = "Verify user is unable to view admin token.";
			strexpectedResult = "User should not be able to view admin token.";
			isEventSuccessful=PerformAction("viewTokenlnk",Action.isNotDisplayed);
			if(isEventSuccessful)
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "User cannot view admin token", true);
			}
			else
			{
				strActualResult = "User can view admin token.";
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, false);
			}
		}
		
		
		//*************************************************************//                     
		// Step 5 : Go to user page.
		//*************************************************************// 
		if(isEventSuccessful)
		{
			isEventSuccessful=GoToUsersPage();
		}
		
		// Step 6 : Click on 'Edit' button for admin user.
		if(isEventSuccessful)
		{
			if( PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", "deepak_tester@ml.com"), Action.WaitForElement))
			{
				isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", "deepak_tester@ml.com"), Action.Click);
				if (isEventSuccessful)
				{
					strActualResult = "user page opened";
				}
				else
				{
					strActualResult ="Unable to open user page.";
				}
			}
			else
			{
				strActualResult ="Unable to find user page.";
			}
			reporter.ReportStep("Verify user page opened.", "User page should be opened.", strActualResult, isEventSuccessful);
		}

		// Step 7: Verify user cannot view Token
		if(isEventSuccessful)
		{
			strstepDescription = "Verify user is unable to view another user's token.";
			strexpectedResult = "User should not be able to view other user's token.";
			isEventSuccessful=PerformAction("viewTokenlnk",Action.isNotDisplayed);
			if(isEventSuccessful)
			{
				reporter.ReportStep(strstepDescription, strexpectedResult, "User cannot view other user's token", true);
			}
			else
			{
				strActualResult = "User can view other user's token.";
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, false);
			}
		}


	}
}