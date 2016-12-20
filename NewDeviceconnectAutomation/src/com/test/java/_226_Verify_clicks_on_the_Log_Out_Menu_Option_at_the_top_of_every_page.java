package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira test case id: QA-2184
 */
public class _226_Verify_clicks_on_the_Log_Out_Menu_Option_at_the_top_of_every_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		/////////////////////////////////////////////////////////////////////
		// Step 1 : login to deviceConnect with admin user.
		/////////////////////////////////////////////////////////////////////
		strstepDescription = "Login to deviceConnect with valid admin user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with " + EmailAddress, "Pass");
		}
		else
		{
			strActualResult = "LoginToDC-- " + strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		/////////////////////////////////////////////////////////////////////
		// Step 2 : Verify 'Logout' is clickable on Devices page.
		/////////////////////////////////////////////////////////////////////
		strstepDescription = "Verify 'Logout' is clickable on Devices page.";
		strexpectedResult = "'Logout' should be clickable on Devices page.";
		isEventSuccessful = Logout();
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Logout' is clickable on Devices page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logout() -- " + strErrMsg_AppLib, "Fail");
		}

		/////////////////////////////////////////////////////////////////////
		// Step 4 : Verify 'Logout' is clickable on device details page.
		/////////////////////////////////////////////////////////////////////
		strstepDescription = "Verify 'Logout' is clickable on device details page.";
		strexpectedResult = "'Logout' should be clickable on device details page.";
		isEventSuccessful = LoginToDC(EmailAddress, Password, false);
		if (isEventSuccessful)
		{
			isEventSuccessful = SelectDevice("first");
			if (isEventSuccessful)
			{
				isEventSuccessful = Logout();
				if (!isEventSuccessful)
				{
					strActualResult = "Logout()-- " + strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "SelectDevice() -- " + strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "LoginToDC() -- " + strErrMsg_AppLib;
		}
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Logout' is clickable on device details page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//////////////////////////////////////////////////////////////////////////////////////////////
		// Step 4 : Go to 'Users' page and verify 'Logout' is clickable.
		//////////////////////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Go to 'Users' page and verify 'Logout' is clickable.";
		strexpectedResult = "'Logout' should be clickable on Users page.";
		isEventSuccessful = LoginToDC(EmailAddress, Password, false);
		if (isEventSuccessful)
		{
			isEventSuccessful = navigateToNavBarPages("Users", "eleUsersHeader");
			if (isEventSuccessful)
			{
				isEventSuccessful = Logout();
				if (!isEventSuccessful)
				{
					strActualResult = "Logout() -- " + strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "selectFromMenu() -- " + strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "LoginToDC() -- " + strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Logout' is clickable on Users page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//////////////////////////////////////////////////////////////////////////////////////////////
		// Step 5 : Go to User Details page and verify 'Logout' is clickable.
		//////////////////////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Go to User Details page and verify 'Logout' is clickable.";
		strexpectedResult = "'Logout' should be clickable on user details page.";
		isEventSuccessful = LoginToDC(EmailAddress, Password, false);
		if (isEventSuccessful)
		{
			isEventSuccessful = navigateToNavBarPages("Users", "eleUsersHeader");
			waitForPageLoaded();
			isEventSuccessful = createUser("TestFirstName", "TestLastName", "", "deviceconnect");
			waitForPageLoaded();
			isEventSuccessful = navigateToNavBarPages("Users", "eleUsersHeader");
			waitForPageLoaded();
			if (isEventSuccessful)
			{
				
				if( PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", dicOutput.get("EmailID")), Action.WaitForElement))
				  {
					  isEventSuccessful = PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", dicOutput.get("EmailID")), Action.Click);
					    if (isEventSuccessful)
					 		{
					    	waitForPageLoaded();
						isEventSuccessful = Logout();
						if (!isEventSuccessful)
						{
							strActualResult = "Logout() -- " + strErrMsg_AppLib;
						}
					}
					else
					{
						strActualResult = "User Details page not displayed on clicking on 'Edit' button.";
					}
				}
				else
				{
					strActualResult = "Unable to click on 'Edit' button on Users page.";
				}
			}
			else
			{
				strActualResult = "edit button is not displayed on users page.";
			}
		}
		else
		{
			strActualResult = "LoginToDC() -- " + strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Logout' is clickable on User Details page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Step 6 : Go to User Details page for 'Create User' and verify 'Logout' is clickable.
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Go to User Details page for 'Create User' and verify 'Logout' is clickable.";
		strexpectedResult = "'Logout' should be clickable on User details page for 'Create User'.";
		isEventSuccessful = LoginToDC(EmailAddress, Password, false);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = navigateToNavBarPages("Users", "eleUsersHeader");
			if (isEventSuccessful)
			{
				waitForPageLoaded();
				isEventSuccessful = PerformAction("btnCreateUser", Action.Click);
				if (isEventSuccessful)
				{
					waitForPageLoaded();
					isEventSuccessful = PerformAction("txtConfirmPassword", Action.Exist);
					if (isEventSuccessful)
					{
						isEventSuccessful = Logout();
						if (!isEventSuccessful)
						{
							strActualResult = "Logout()-- " + strErrMsg_AppLib;
						}
					}
					else
					{
						strActualResult = "User Details page not displayed on clicking on 'Create User' button.";
					}
				}
				else
				{
					strActualResult = "'Create User' button does not exist on Users page.";
				}
			}
			else
			{
				strActualResult = "selectFromMenu() -- " + strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "LoginToDC() -- " + strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Logout' is clickable on User Details for 'Create User' page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//////////////////////////////////////////////////////////////////////////////////////////////
		// Step 7 : Go to Applications page and verify that 'Logout' is clickable.
		//////////////////////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Go to Applications page and verify that 'Logout' is clickable.";
		strexpectedResult = "'Logout' should be clickable on Applications page.";
		isEventSuccessful = LoginToDC(EmailAddress, Password, false);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = navigateToNavBarPages("Applications", "eleApplicationsHeader");
			if (isEventSuccessful)
			{
				waitForPageLoaded();
				isEventSuccessful = Logout();
				if (!isEventSuccessful)
				{
					strActualResult = "Logout()-- " + strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "selectFromMenu() -- " + strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "LoginToDC()-- " + strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Logout' should be clickable. on Applications page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//////////////////////////////////////////////////////////////////////////////////////////////
		// Step 8 : Go to Application details page and verify that 'Logout' is clickable.
		//////////////////////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Go to Application details page and verify that 'Logout' is clickable.";
		strexpectedResult = "'Logout' should be clickable on Application details page.";
		isEventSuccessful = LoginToDC(EmailAddress, Password, false);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = navigateToNavBarPages("Applications", "eleApplicationsHeader");
			waitForPageLoaded();
			isEventSuccessful = SelectApplication("first");
			if (isEventSuccessful)
			{
				isEventSuccessful = Logout();
				if (!isEventSuccessful)
				{
					strActualResult = "Logout()-- " + strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "SelectApplication()-- " + strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "LoginToDC() -- " + strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Logout' should be clickable. on Application details page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//////////////////////////////////////////////////////////////////////////////////////////////
		// Step 9 : Go to Reservations page and verify that 'Logout' is clickable.
		//////////////////////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Go to Reservations page and verify that 'Logout' is clickable.";
		strexpectedResult = "'Logout' should be clickable on Reservations page.";
		isEventSuccessful = LoginToDC(EmailAddress, Password, false);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = navigateToNavBarPages("Reservations", "btnCreateReservation");
			if (isEventSuccessful)
			{
				waitForPageLoaded();
				isEventSuccessful = Logout();
				if (!isEventSuccessful)
				{
					strActualResult = "Logout()-- " + strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "SelectApplication()-- " + strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "selectFromMenu() -- " + strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Logout' should be clickable. on Reservations page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//////////////////////////////////////////////////////////////////////////////////////////////
		// Step 10 : Go to Reservations page and verify that 'Logout' is clickable.
		//////////////////////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Go to Reservation details page and verify that 'Logout' is clickable.";
		strexpectedResult = "'Logout' should be clickable on Reservation details page.";
		isEventSuccessful = LoginToDC(EmailAddress, Password, false);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = navigateToNavBarPages("Reservations", "btnCreateReservation");
			waitForPageLoaded();
			if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
			{
			  isEventSuccessful = PerformAction("btnCreateReservation", Action.ClickUsingJS);
			  waitForPageLoaded();
			  PerformAction("browser", "waitforpagetoload" );
			}
			else
			{
			   isEventSuccessful = PerformAction("btnCreateReservation", Action.Click);
			}
			waitForPageLoaded();
			if (isEventSuccessful)
			{
				if(dicCommon.get("BrowserName").toLowerCase().equals("ie") && (GenericLibrary.IEversion.equals("11")))
				{
					PerformAction("browser", "waitforpagetoload", "10");
				}
				isEventSuccessful = PerformAction(dicOR.get("eleHeader").replace("__EXPECTED_HEADER__", "New Reservation"), Action.isDisplayed);
				if (isEventSuccessful)
				{
					isEventSuccessful = Logout();
					if (!isEventSuccessful)
					{
						strActualResult = "Logout()-- " + strErrMsg_GenLib;
					}
				}
				else
				{
					strActualResult = "Create reservation page is not displayed after clicking on 'create' button on reservations index page.";
				}
			}
			else
			{
				strActualResult =  strErrMsg_GenLib;
			}
		}
		else
		{
			strActualResult = " Could not click on 'Create' button on reservations page.";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Logout' should be clickable. on Reservation details page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//////////////////////////////////////////////////////////////////////////////////////////////
		// Step 11 : Go to System page and verify that 'Logout' is clickable.
		//////////////////////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Go to System page and verify that 'Logout' is clickable.";
		strexpectedResult = "'Logout' should be clickable on System page.";
		isEventSuccessful = LoginToDC(EmailAddress, Password, false);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = navigateToNavBarPages("System", "eleSystemHeader");
			if (isEventSuccessful)
			{
				waitForPageLoaded();
				isEventSuccessful = Logout();
				if (!isEventSuccessful)
				{
					strActualResult = "Logout()-- " + strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "selectFromMenu() -- " + strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "LoginToDC()-- " + strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Logout' should be clickable. on System page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
	}
}