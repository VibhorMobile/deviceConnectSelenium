package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id:QA-2168
 */
public class _151_Verify_Text_to_appear_in_Footer_will_be_centred extends ScriptFuncLibrary
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
		strexpectedResult = "Footer text should be displayed on center in the page.";
		isEventSuccessful = getAttribute("eleFooter", "css", "text-align").contains("center");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Footer text is displayed on center in Devices page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Footer text is not displayed on center in Devices page.", "Fail");
		}

		//*************************************************************//     
		// Step 3 : Verify footer on device details page.
		//*************************************************************//     
		strstepDescription = "Verify footer on device details page.";
		strexpectedResult = "Footer text should be displayed on center in the page.";
		isEventSuccessful = GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results.");
		if (!isEventSuccessful) // continue only if there are some devices under android platform.
		{
			isEventSuccessful = SelectDevice("first");
			if (isEventSuccessful)
			{
				isEventSuccessful = getAttribute("eleFooter", "css", "text-align").contains("center");
				if (!isEventSuccessful)
				{
					strActualResult = "Footer text is not displayed on center in device details page.";
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
			reporter.ReportStep(strstepDescription, strexpectedResult, "Footer text is displayed on center in device details page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//     
		// Step 4 : Go to 'Users' page and verify that Footer is displayed.
		//*************************************************************//     
		strstepDescription = "Go to 'Users' page and verify that footer is displayed.";
		strexpectedResult = "Footer text should be displayed on center in the page.";
		isEventSuccessful = GoToUsersPage();
		isEventSuccessful = getAttribute("eleFooter", "css", "text-align").contains("center");
		if (!isEventSuccessful)
		{
			strActualResult = "Footer text is not displayed on center in Users page.";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Footer text is displayed on center in Users page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//     
		// Step 5 : Go to User Details page and verify that Footer is displayed.
		//*************************************************************//     
		strstepDescription = "Go to User Details page and verify that footer is displayed.";
		strexpectedResult = "Footer text should be displayed on center in the page.";
		
		isEventSuccessful = GoToSpecificUserDetailsPage(dicCommon.get("EmailAddress"));
		if (isEventSuccessful)
		{
			isEventSuccessful = getAttribute("eleFooter", "css", "text-align").contains("center");
			if (!isEventSuccessful)
			{
				strActualResult = "Footer text is not displayed on center in User details page.";
			}
		}
		else
		{
			strActualResult = "Unable to click on username";
		}
		
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Footer text is displayed on center in User Details page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//     
		// Step 6 : Go to User Details page for 'Create User' and verify that Footer is displayed.
		//*************************************************************//     
		strstepDescription = " Go to User Details page for 'Create User' and verify that Footer is displayed.";
		strexpectedResult = "Footer text should be displayed on center in the page.";
		isEventSuccessful = GoToUsersPage();
		isEventSuccessful = PerformAction("btnCreateUser", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("txtConfirmPassword", Action.WaitForElement);
			if (isEventSuccessful)
			{
				isEventSuccessful = getAttribute("eleFooter", "css", "text-align").contains("center");
				if (!isEventSuccessful)
				{
					strActualResult = "Footer text is not displayed on center in device details page.";
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
			reporter.ReportStep(strstepDescription, strexpectedResult, "Footer text is displayed on center in User Details for 'Create User' page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//     
		// Step 7 : Go to Applications page and verify that Footer is displayed.
		//*************************************************************//     
		strstepDescription = "Go to Applications page and verify that footer is displayed.";
		strexpectedResult = "Footer text should be displayed on center in the page.";
		isEventSuccessful = GoToApplicationsPage();
		if (isEventSuccessful)
		{
			isEventSuccessful = getAttribute("eleFooter", "css", "text-align").contains("center");
			if (!isEventSuccessful)
			{
				strActualResult = "Footer text is not displayed on center in device details page.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Footer text is displayed on center in Applications page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*************************************************************//     
		// Step 8 : Go to Reservations page and verify that Footer is displayed.
		//*************************************************************//     
		strstepDescription = "Go to Reservations page and verify that footer is displayed.";
		strexpectedResult = "Footer text should be displayed on center in the page.";
		isEventSuccessful = GoToReservationsPage();
		if (isEventSuccessful)
		{
			isEventSuccessful = getAttribute("eleFooter", "css", "text-align").contains("center");
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
		strexpectedResult = "Footer text should be displayed on center in the page.";
		isEventSuccessful = PerformAction("btnCreateReservation", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction(dicOR.get("eleHeader").replace("__EXPECTED_HEADER__", "New Reservation"), Action.WaitForElement);
			if (isEventSuccessful)
			{
				isEventSuccessful = getAttribute("eleFooter", "css", "text-align").contains("center");
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

		//*************************************************************//     
		// Step 5 : Verify that correct deviceConnect logo is displayed on about deviceConnect page.
		//*************************************************************//     
		//strstepDescription = "Verify that Footer is displayed on about deviceConnect page.";
		//strexpectedResult = "Footer text should be displayed on center in the page.";

		////PerformAction("browser", Action.Scroll, "30");
		//isEventSuccessful = PerformAction("link=About deviceConnect", Action.Click);
		//if (isEventSuccessful)
		//{
		//    isEventSuccessful = PerformAction("browser", Action.SelectWindow, "1");
		//    if (isEventSuccessful) // continue only if there are some devices under android platform.
		//    {
		//        isEventSuccessful = getAttribute("eleFooter", "css", "text-align").contains("center");
		//        if (!isEventSuccessful)
		//            strActualResult = "Footer text is not displayed on center in about deviceConnect page.";
		//    }
		//    else
		//        strActualResult = "'About' page is not displayed after clicking on 'About deviceConnect' link.";
		//}
		//else
		//    strActualResult = " 'About deviceConnect' link does not exist on page.";

		//if (isEventSuccessful)
		//    reporter.ReportStep(strstepDescription, strexpectedResult, "Footer text is displayed on center in About deviceConnect page.", "Pass");
		//else
		//    reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");


		//*************************************************************//     
		// Step 10 :Log out of the application.
		//*************************************************************//     
		strstepDescription = "Log out of the application";
		strexpectedResult = "Login page should be displayed.";
	
		isEventSuccessful = Logout();
		if (isEventSuccessful)
		{
			strActualResult = "User logged out seccessfully";
		}
		else
		{
			strActualResult = "Logout() -- " + strErrMsg_AppLib;
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//*************************************************************//     
		// Step 11 : Verify footer is displayed on login page
		//*************************************************************//     
		strstepDescription = "Verify footer is displayed on login page.";
		strexpectedResult = "Footer should be displayed on login page.";
		isEventSuccessful = getAttribute("eleHomeFooter", "css", "text-align").contains("center");
		if (isEventSuccessful)
		{
			strActualResult = "Footer text is displayed on center on Login page.";
		}
		else
		{
			strActualResult = "Footer text is not displayed on center on Login page.";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}