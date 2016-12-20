package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-138,12
 */
public class _659_Verify_that_the_edit_user_page_is_accessed_through_Manage_your_account_or_as_an_administrator_clicking_on_User_Name_link extends ScriptFuncLibrary{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	String editedFirstName,editedEmailID,editedRole,userName,firstName,editedPassword,editedEmail;
	private boolean isAdminSelected,isTesterSelected,isTesterSelectedEdited,isAdminSelectedEdited;



	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//*************************************************************//                     
			// Step 1 : login to deviceConnect with admin user.
			//*************************************************************//                     
			isEventSuccessful = Login();


			//*************************************************************/                     
			// Step 2 : Go to Manage your Account
			//*************************************************************//
			strstepDescription="Click on Manage Your Account from Dropdown";
			strExpectedResult="Successfully clicked Manage your Account from Dropdown";
			waitForPageLoaded();
			isEventSuccessful=PerformAction(dicOR.get("eleUserEmail_UsersPage"), Action.Click);
			if(isEventSuccessful){
				waitForPageLoaded();
				isEventSuccessful=PerformAction(dicOR.get("eleManageAccount_UsersPage"), Action.Click);
				if(isEventSuccessful){
					waitForPageLoaded();
					isEventSuccessful=true;
					strActualResult="Clicked on Manage your Account from Dropdown";
				}
				else{

					strActualResult="Not able to click on Manage your Account from Dropdown";
					isEventSuccessful=false;
				}
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//*************************************************************/                     
			// Step 3 : Verify Page is User Edit Page
			//*************************************************************//
			editedFirstName="updatedFirstName"+getDateTime();
			strstepDescription="Verify Manage User Page is User Edit Page by editing First Name of the User";
			strExpectedResult="First Name of User Edited to:"+editedFirstName;
			waitForPageLoaded();
			PerformAction("browser",Action.WaitForPageToLoad);
			PerformAction(dicOR.get("eleFirstName_UserDetailsPage"), Action.Clear);
			PerformAction(dicOR.get("eleFirstName_UserDetailsPage"), Action.Type,editedFirstName);
			waitForPageLoaded();

			PerformAction("btnSave", Action.Click);
			waitForPageLoaded();
			PerformAction("browser", Action.Back);
			waitForPageLoaded();
			PerformAction("browser",Action.WaitForPageToLoad);

			firstName=GetTextOrValue(dicOR.get("eleFirstName_UserDetailsPage"), "value");

			if(editedFirstName.equals(firstName)){
				strActualResult="Page is User edit page and edited first name of the user is:"+firstName;
				isEventSuccessful=true;
			}
			else{
				strActualResult="Not able to edit first name for user first name is:"+firstName;
				isEventSuccessful=false;
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);


			//*************************************************************/                     
			// Step 4 : Go to User Edit Page for a random User from Users Page
			//*************************************************************//
			strstepDescription="Go to edit page for a random User ";
			strExpectedResult="Navigated to edit page of a random user";
			isEventSuccessful=GoToUsersPage();

			waitForPageLoaded();
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='userListTable table data-grid user-list-table']/tbody/tr[1]/td[1]"))); 
			if(isEventSuccessful)
			{
				GoToFirstUserDetailsPage();
				waitForPageLoaded();
				isEventSuccessful=PerformAction(dicOR.get("textHeader_UserEditPage"), Action.isDisplayed);
				userName=GetTextOrValue(dicOR.get("eleUserName_UserDetailsPage"), Action.Click);
				if(isEventSuccessful){
					waitForPageLoaded();
					strActualResult="Navigated to user edit page for the user:"+userName;
					isEventSuccessful=true;
				}
				else{
					strActualResult="Did not navigated to user edit page.";
					isEventSuccessful=false; 
				}
				reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful); 


				//*************************************************************/                     
				// Step 5 : Edit Email id  and Password and Login with new details.
				//*************************************************************//
				waitForPageLoaded();
				editedEmailID=getDateTime()+"@user.com";
				editedPassword="deviceconnect";
				strstepDescription="Edit email and password for the user ";
				strExpectedResult="Email and password of user is edited, new email for user is:"+editedEmailID;
				waitForPageLoaded();
				PerformAction("browser",Action.WaitForPageToLoad);
				PerformAction(dicOR.get("eleUserName_UserDetailsPage"), Action.Clear);
				isAdminSelected=PerformAction("//li[@title='Administrator']/label/input", Action.isSelected);
				isTesterSelected=PerformAction("//li[@title='Tester']/label/input", Action.isSelected);
				if(!isAdminSelected){
					PerformAction("//li[@title='Administrator']/label/input", Action.Select);
					waitForPageLoaded();
				}
				if(!isTesterSelected){
					PerformAction("//li[@title='Tester']/label/input", Action.Select);
					waitForPageLoaded();
				}

				PerformAction(dicOR.get("eleUserName_UserDetailsPage"), Action.Type,editedEmailID);
				waitForPageLoaded();
				PerformAction(dicOR.get("elePassword_UserDetailsPage"), Action.Type,editedPassword);
				waitForPageLoaded();
				PerformAction(dicOR.get("eleConfirmPassword_UserDetailsPage"), Action.Type,editedPassword);
				waitForPageLoaded();

				PerformAction("btnSave", Action.Click);
				waitForPageLoaded();
				PerformAction("browser", Action.Back);
				waitForPageLoaded();
				editedEmail=GetTextOrValue(dicOR.get("eleUserName_UserDetailsPage"), "value");
				isAdminSelectedEdited=PerformAction("//li[@title='Administrator']/label/input", Action.isSelected);
				isTesterSelectedEdited=PerformAction("//li[@title='Tester']/label/input", Action.isSelected);

				if(editedEmailID.equals(editedEmail)&& isAdminSelectedEdited && isTesterSelectedEdited){
					strActualResult="User Details Edited, new email for user is:"+editedEmail+"and Admin role selected value is:"+isAdminSelected +" and tester role selected value is "+isTesterSelected;
					isEventSuccessful=true;
				}
				else{
					strActualResult="User details are not edited, new email for user is:"+editedEmail+"and Admin role selected value is:"+isAdminSelected +" and tester role selected value is "+isTesterSelected;
					isEventSuccessful=false;
				}
				reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);

				//*************************************************************/                     
				// Step 6 : Logout and Login with new User.
				//*************************************************************//
				isEventSuccessful=Logout();
				waitForPageLoaded();
				if(isEventSuccessful){
					isEventSuccessful=Login(editedEmailID, editedPassword);
				}



			}
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Edit user details--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}



}
