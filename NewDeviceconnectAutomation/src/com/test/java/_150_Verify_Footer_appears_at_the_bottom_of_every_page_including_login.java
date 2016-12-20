package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2168
 */
public class _150_Verify_Footer_appears_at_the_bottom_of_every_page_including_login extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "";
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		
		
		//*************************************************************//     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//     
		isEventSuccessful = Login();
		
		//*************************************************************//     
		// Step 2 : Verify footer appears on Devices page.
		//*************************************************************//     
		strstepDescription = "Verify correct deviceConnect logo on Devices page.";
		strexpectedResult = "Footer should be displayed on the page.";
		isEventSuccessful = PerformAction("eleFooter", Action.isDisplayed);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Footer is displayed on  Devices page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Footer is not displayed on  Devices page.", "Fail");
		}

		//*************************************************************//     
		// Step 3 : Verify footer on device details page.
		//*************************************************************//     
		strstepDescription = "Verify footer on device details page.";
		strexpectedResult = "Footer should be displayed on page.";
		isEventSuccessful = GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results.");
		if (!isEventSuccessful) // continue only if there are some devices under android platform.
		{
			isEventSuccessful = SelectDevice("first");
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleFooter", Action.isDisplayed);
				if (!isEventSuccessful)
				{
					strActualResult = "Footer is not displayed on device details page.";
				}
			}
			else
			{
				strActualResult = "Device details page is not displayed.";
			}
		}
		else
		{
			strActualResult = "No devices displayed on devices page.";
		}
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Footer is displayed on device details page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//     
		// Step 4 : Go to 'Users' page and verify that Footer is displayed.
		//*************************************************************//     
		
		isEventSuccessful = GoToUsersPage();
		strstepDescription = "Go to 'Users' page and verify that footer is displayed.";
		strexpectedResult = "Footer should be displayed on the page.";
		isEventSuccessful = PerformAction("eleFooter", Action.isDisplayed);
		if (!isEventSuccessful)
		{
			strActualResult = "Footer is not displayed on Users page.";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Footer is displayed on Users page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//     
		// Step 5 : Go to User Details page and verify that Footer is displayed.
		//*************************************************************//     
		isEventSuccessful = GoToSpecificUserDetailsPage(dicCommon.get("EmailAddress"));
		strstepDescription = "Go to User Details page and verify that footer is displayed.";
		strexpectedResult = "Footer should be displayed on the page.";
		if (isEventSuccessful)
			{
			  isEventSuccessful = PerformAction("eleFooter", Action.isDisplayed);
				if (!isEventSuccessful)
				{
					strActualResult = "Footer is not displayed on User details page.";
				}
			else
					strActualResult = "Footer is displayed on User details page.";
			}
		else
		{
				strActualResult = "User Details page not displayed on clicking on User name.";
		}
				
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	
		//*************************************************************//     
		// Step 6 : Go to User Details page for 'Create User' and verify that Footer is displayed.
		//*************************************************************//     
		strstepDescription = " Go to User Details page for 'Create User' and verify that Footer is displayed.";
		strexpectedResult = "Footer should be displayed on the page.";
		
		isEventSuccessful = GoToUsersPage();
		isEventSuccessful = PerformAction("btnCreateUser", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("txtConfirmPassword", Action.WaitForElement);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleFooter", Action.isDisplayed);
				if (!isEventSuccessful)
				{
					strActualResult = "Footer is not displayed on device details page.";
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
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Footer is displayed on User Details for 'Create User' page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//     
		// Step 7 : Go to Applications page and verify that Footer is displayed.
		//*************************************************************//     
		strstepDescription = "Go to Applications page and verify that footer is displayed.";
		strexpectedResult = "Footer should be displayed on the page.";
		
		isEventSuccessful = GoToApplicationsPage();
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleFooter", Action.isDisplayed);
			if (!isEventSuccessful)
			{
				strActualResult = "Footer is not displayed on device details page.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		//*************************************************************//     
		// Step 8 : Go to Reservations page and verify that Footer is displayed.
		//*************************************************************//     
		strstepDescription = "Go to Reservations page and verify that footer is displayed.";
		strexpectedResult = "Footer should be displayed on the page.";
		isEventSuccessful = GoToReservationsPage();		
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleFooter", Action.isDisplayed);
			if (!isEventSuccessful)
			{
				strActualResult = "Footer is not displayed on Reservations page.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		//*************************************************************//     
		// Step 9 : Click the Create Reservation button and verify that Footer is displayed.
		//*************************************************************//     
		strstepDescription = "Click the Create Reservation button and verify that Footer is displayed.";
		strexpectedResult = "Footer should be displayed on the page.";
		if (dicCommon.get("BrowserName").toLowerCase().equals("ie"))
		{
			isEventSuccessful = PerformAction("btnCreateReservation", Action.ClickAtCenter);
		}
		else
		{
			isEventSuccessful = PerformAction("btnCreateReservation", Action.Click);
		}
		if(!isEventSuccessful)
		{
			isEventSuccessful = UnHandeledException();
			if(isEventSuccessful)
			{
				isEventSuccessful = PerformAction("btnCreateReservation", Action.ClickAtCenter);
			}
		}
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction(dicOR.get("eleHeader").replace("__EXPECTED_HEADER__", "New Reservation"), Action.WaitForElement);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleFooter", Action.isDisplayed);
				if (!isEventSuccessful)
				{
					strActualResult = "Footer is not displayed on New Reservation page.";
				}
			}
			else
			{
				strActualResult = "New Reservation page not displayed on clicking on 'Create' button.";
			}
		}
		else
		{
			strActualResult = "'Create' button does not exist on New Reservation page.";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Footer is displayed on New Reservation page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		

		
	}
}