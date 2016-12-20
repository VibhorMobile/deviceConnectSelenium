package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id:QA-2209
 */
public class _229_Verify_newly_added_users_are_displayed_in_the_user_list_and_card_view extends ScriptFuncLibrary

{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "", strUserID = "";

	public final void testScript()
	{
		//Step 1 - Login to deviceConnect
			isEventSuccessful = Login();

		//Step 2 : Go to 'Users' page
		isEventSuccessful = GoToUsersPage( );
		

		//Step 3 : Add a new user
		if(PerformAction("", Action.WaitForElement,"15"))
		{
			return;
	    }
		isEventSuccessful = createUser("TestFirstName", "TestLastName", strUserID, "deviceconnect");
		if (isEventSuccessful)
		{
			strActualResult = "User " + dicOutput.get("EmailID") + " is created.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep("Add a new user.", "A new user should be created.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
		
		/*//Step 5 : Verify newly added user is displayed on card view
		isEventSuccessful = PerformAction(dicOR["btnEditUser_GridView"].Replace("__EMAILID__", strUserID), tangible.Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Newly added user - " + strUserID + " is displayed on card view.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep("Verify newly added user is displayed in users card view.", "Newly added user should be added in users card view.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}*/

		/*//Step 6: Go to list view
		//isEventSuccessful = PerformAction("lnkListView", tangible.Action.Click);
		if (isEventSuccessful)
		
			isEventSuccessful = PerformAction("eleListViewTable", tangible.Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "List view is displayed.";
			}
			else
			{
				strActualResult = "List view is not displayed.";
			}
		}
		else
		{
			strActualResult = "Could not click on list view icon.";
		}
		reporter.ReportStep("Go to list view.", "List view should gets displayed.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
*/
		//Step 4 : Verify newly added user is displayed on list view
		if( PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", dicOutput.get("EmailID")), Action.WaitForElement))
		  {
			  isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", dicOutput.get("EmailID")), Action.isDisplayed);
			    if (isEventSuccessful)
			    {
				      strActualResult = "Newly added user - " + dicOutput.get("EmailID") + " is displayed on list view.";
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
		reporter.ReportStep("Verify newly added user is displayed in users list view.", "Newly added user should be added in users list view.", strActualResult, isEventSuccessful);	

		
	}
}