package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-4, QA-322, QA-870, QA-12
 */

public class _224_Verify_Admins_can_add__edit_and_disable_users extends ScriptFuncLibrary
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
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep("Go to 'Create User' page", "'Users' page should gets opened.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}

		//Step 3 : Add a new user
		isEventSuccessful = createUser("TestFirstName", "TestLastName", "", "deviceconnect");
		if (isEventSuccessful)
		{
			strActualResult = "User " + dicOutput.get("EmailID") + " is created.";
		}
		else
		{
			strActualResult = "createUser()--" + strErrMsg_AppLib;
		}
		reporter.ReportStep("Add a new user.", "A new user should gets created.", strActualResult, isEventSuccessful);
		
		// Step
		isEventSuccessful = GoToUsersPage();

		//Step 4 : Go to 'Users' page & Click on 'Edit' button for just created user.
		waitForPageLoaded();
		if( PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", dicOutput.get("EmailID")), Action.WaitForElement))
		  {
			  isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", dicOutput.get("EmailID")), Action.Click);
			  waitForPageLoaded();
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

		//Step 5 : Edit the emailID of the created user.
		strStepDescription = "Edit EmailID of created user.";
		strExpectedResult = "Email ID should be edited and saved.";
		strUserID = FetchDateTimeInSpecificFormat("MMddyy_hhmmss") + "Newselenium@deviceconnect.com" ;
		
		isEventSuccessful = PerformAction("txtLogin", Action.Type, strUserID);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = PerformAction("btnSave", Action.ClickUsingJS);
			if (isEventSuccessful)
			{
				waitForPageLoaded();
                    if(dicCommon.get("BrowserName").toLowerCase().equals("chrome") ||dicCommon.get("BrowserName").toLowerCase().equals("ie"))
                    {
                    	waitForPageLoaded();
                	   isEventSuccessful = PerformAction("eleNotificationRightBottom", Action.WaitForElement);
                	   NotificationText = GetTextOrValue("eleNotificationRightBottom", "text");
                    }
                    else
                    {
                      NotificationText = GetTextOrValue("eleNotificationRightBottom", "text");
                    }
					isEventSuccessful = NotificationText.contains("Information updated successfully");
					if (isEventSuccessful)
					{
						strActualResult = "Correct notification message is displayed.";
					}
					else
					{
						strActualResult = "Notification message is incorrect : " + NotificationText;
					}
			}
			else
			{
				strActualResult = "Could not click on 'Save' button.";
			}
		}
		else
		{
			strActualResult = "Could not edit email ID of the user.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		
		// Step 8 : Go to user details page of the edited user
		strStepDescription = "Go to user details page of the edited user.This will also verify that the user's email ID is editable by Admin user.";
		strExpectedResult = "User with edited emailID should exist and its user details page should be opened.";
		waitForPageLoaded();
		if( PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", strUserID), Action.WaitForElement))
		  {
			  isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", strUserID), Action.Click);
			    if (isEventSuccessful)
			    {
			    	waitForPageLoaded();
				      strActualResult = "Newly added user - " + strUserID + " is displayed on list view.";
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

		//Step 9 : Disable the user
		strStepDescription = "Uncheck the 'Active' checkbox to disable the user.";
		strExpectedResult = "'Active' checkbox should be unchecked.";
		isEventSuccessful = PerformAction("chkActive_CreateUser", Action.Click);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = !PerformAction("chkActive_CreateUser", Action.isSelected);
			if (isEventSuccessful)
			{
				waitForPageLoaded();
				strActualResult = "'Active' checkbox unchecked successfully.";
			}
			else
			{
				strActualResult = "'Active' checkbox is not unchecked.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Active' Checkbox.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//Step 10 : Click on 'Save' button and verify success notification
		isEventSuccessful = PerformAction("btnSave", Action.ClickUsingJS);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			//isEventSuccessful = PerformAction("eleNotificationRightBottom", Action.WaitForElement);
			if (isEventSuccessful)
			{
				if(dicCommon.get("BrowserName").toLowerCase().equals("chrome"))
                {
					waitForPageLoaded();
            	   isEventSuccessful = PerformAction("eleNotificationRightBottom", Action.WaitForElement);
            	   NotificationText = GetTextOrValue("eleNotificationRightBottom", "text");
                }
                else
                {
                  NotificationText = GetTextOrValue("eleNotificationRightBottom", "text");
                }
				
				String text = GetTextOrValue("eleNotificationRightBottom", "text");
				isEventSuccessful = text.contains("Information updated successfully.");
				if (isEventSuccessful)
				{
					strActualResult = "User disabled successfully.";
				}
				else
				{
					strActualResult = "'Notification does not read : 'User updated.' but : " + text;
				}
			}
			else
			{
				strActualResult = "No notification displayed at right bottom of page after clicking on 'Save' button.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Save' button";
		}
		reporter.ReportStep("Click on 'Save' button and verify notification.", "'User updated.' notification should be displayed at right bottom of page after clicking on 'save' button.", strActualResult, isEventSuccessful);
	}
}