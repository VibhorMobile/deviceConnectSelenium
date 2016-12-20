package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary; 
/*
 * Jira Test Case Id: QA-2168
 */
public class _123_Verify_that_correct_deviceConnect_logo_appears_on_all_of_the_deviceConnect_screens extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;
	
	public final void testScript()
	{
		//*************************************************************//
		//***** Step 1 - login to deviceConnect with admin user. ***//
		//*************************************************************//   
		
		isEventSuccessful = Login();
		
		//************************************************************//   
		// Step 2 : Verify correct deviceConnect logo on Devices page.
		//************************************************************//
		strStepDescription = "Verify correct deviceConnect logo on Homepage.";
		strExpectedResult = "Correct deviceConnect logo should be displayed on Homepage.";
		isEventSuccessful = PerformAction("eleDeviceConnectLogoOnPages", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Correct deviceConnect logo is displayed on Homepage.";
			}
			else
			{
				strActualResult = "Correct deviceConnect logo is not displayed on Homepage.";
			}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//*****************************************************************//   
		// Step 3 : Verify correct deviceConnect logo on device details page.
		//*****************************************************************//   
		strStepDescription = "Verify correct deviceConnect logo on device details page.";
		strExpectedResult = "Correct deviceConnect logo should be displayed on the page.";
		isEventSuccessful = GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results.");
		if (!isEventSuccessful) // continue only if there are some devices under android platform.
		{
			isEventSuccessful = SelectDevice("first");
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleDeviceConnectLogoOnPages", Action.isDisplayed);
				if (!isEventSuccessful)
				{
					strActualResult = "Correct deviceConnect logo is not displayed on device details page.";
				}
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "No devices displayed on device details page.";
		}
		if (isEventSuccessful)
		{
			reporter.ReportStep(strStepDescription, strExpectedResult, "Correct deviceConnect logo is displayed on the page.", isEventSuccessful);
		}
		else
		{
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, "Fail");
		}

		//************************************************************************************//   
		// Step 4 : Go to 'Users' page and verify that correct deviceConnect logo is displayed.
		//************************************************************************************//   

		isEventSuccessful= GoToUsersPage();
		
		strStepDescription = "Verify that correct deviceConnect logo is displayed on Users Page.";
		strExpectedResult = "Correct deviceConnect logo should be displayed on the page.";

			isEventSuccessful = PerformAction("eleDeviceConnectLogoOnPages", Action.isDisplayed);
			if (!isEventSuccessful)
			{
				strActualResult = "Correct deviceConnect logo is not displayed on Users page.";
			}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strStepDescription, strExpectedResult, "Correct deviceConnect logo is displayed on Users page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, "Fail");
		}

		//*******************************************************************************************//   
		// Step 5 : Go to User Details page and verify that correct deviceConnect logo is displayed.
		//*******************************************************************************************//   
		isEventSuccessful = GoToSpecificUserDetailsPage(dicCommon.get("EmailAddress"));
		strStepDescription = "Verify that correct deviceConnect logo is displayed on User Details Page.";
		strExpectedResult = "Correct deviceConnect logo should be displayed on the page.";
		if(isEventSuccessful)
			{
			  isEventSuccessful = PerformAction("eleDeviceConnectLogoOnPages", Action.isDisplayed);
				if (!isEventSuccessful)
				{
					strActualResult = "Correct deviceConnect logo is not displayed on User Details page.";
				}
			}
			else
			{
				strActualResult = "User Details page not displayed for -" + dicCommon.get("EmailAddress");
			}
		
		

		if (isEventSuccessful)
		{
			reporter.ReportStep(strStepDescription, strExpectedResult, "Correct deviceConnect logo is displayed on User Details page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, "Fail");
		}

		//************************************************************************************************************//   
		// Step 6 : Go to 'Create User' page and verify that correct deviceConnect logo is displayed.
		//************************************************************************************************************//   
		isEventSuccessful = GoToUsersPage();
		strStepDescription = "Verify that correct deviceConnect logo is displayed after clicking on 'Create USer'.";
		isEventSuccessful = PerformAction("btnCreateUser", Action.Click);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("txtConfirmPassword", Action.WaitForElement);
				if (isEventSuccessful)
				{
					isEventSuccessful = PerformAction("eleDeviceConnectLogoOnPages", Action.isDisplayed);
					if (!isEventSuccessful)
					{
						strActualResult = "Correct deviceConnect logo is not displayed on Create User page.";
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
			reporter.ReportStep(strStepDescription, "Correct deviceConnect logo should be displayed on the 'Create User' page.", "Correct deviceConnect logo is displayed on Create User page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strStepDescription, "Correct deviceConnect logo should be displayed on the 'Create User' page.", strActualResult, "Fail");
		}

		//******************************************************************************************//   
		// Step 7 : Go to Applications page and verify that correct deviceConnect logo is displayed.
		//******************************************************************************************//   
		 isEventSuccessful = GoToApplicationsPage();
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleDeviceConnectLogoOnPages", Action.isDisplayed);
			if (!isEventSuccessful)
			{
				strActualResult = "Correct deviceConnect logo is not displayed on Applications page.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep("Verify that correct deviceConnect logo is displayed on'Applications' Page.", "Correct deviceConnect logo should be displayed on 'Applications' Page.", "Correct deviceConnect logo is displayed on Applications page.", "Pass");
		}
		else
		{
			reporter.ReportStep("Verify that correct deviceConnect logo is displayed on'Applications' Page.", "Correct deviceConnect logo should be displayed on 'Applications' Page.", "Correct deviceConnect logo is not displayed on Applications page.", "Fail");
		}

		//***********************************************************************//   
		// Step 8 : Verify correct deviceConnect logo on Application details page.
		//***********************************************************************//   
		strStepDescription = "Verify correct deviceConnect logo on Application details page.";
		strExpectedResult = "Correct deviceConnect logo should be displayed on the page.";
		isEventSuccessful = SelectApplication("first");
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleDeviceConnectLogoOnPages", Action.isDisplayed);
				if (!isEventSuccessful)
				{
					strActualResult = "Correct deviceConnect logo is not displayed on Application details page.";
				}
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		if (isEventSuccessful)
		{
			reporter.ReportStep(strStepDescription, strExpectedResult, "Correct deviceConnect logo is displayed on Application details page.", "Pass");
		}
		else
		{
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, "Fail");
		}

		//******************************************************************************************//   
		// Step 9 : Go to Reservations page and verify that correct deviceConnect logo is displayed.
		//******************************************************************************************//   
		isEventSuccessful = GoToReservationsPage();
		strStepDescription = "Verify correct deviceConnect logo on 'Reservations' page.";
		strExpectedResult = "Correct deviceConnect logo should be displayed on the 'Reservations' page.";
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleDeviceConnectLogoOnPages", Action.isDisplayed);
			if (!isEventSuccessful)
			{
				strActualResult = "Correct deviceConnect logo is not displayed on device details page.";
			}
			else
			  strActualResult = "Correct deviceConnect logo is displayed on device details page.";
			
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		//***********************************************************************//   
		// Step 10 : Verify correct deviceConnect logo on create reservation page.
		//***********************************************************************//   
		isEventSuccessful = GoToCreateReservationPage();
		strStepDescription = "Verify that correct deviceConnect logo is displayed on 'Reservation' page.";
		strExpectedResult = "Correct deviceConnect logo should be displayed on the 'Reservation' page.";
		isEventSuccessful = PerformAction("eleCreateRsrvtnHeader", Action.isDisplayed);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleDeviceConnectLogoOnPages", Action.isDisplayed);
				if (!isEventSuccessful)
				{
					strActualResult = "Correct deviceConnect logo is not displayed on the 'Reservation' page.";
				}
			}
			else
			{
				strActualResult = "Create Reservation page not displayed after clicking on the 'Reservation' page.";
			}
			
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		//*******************************************************************************************//   
		// Step 11 : Verify that correct deviceConnect logo is displayed on about deviceConnect popup.
		//*******************************************************************************************//   
		strStepDescription = "Verify that correct deviceConnect logo is displayed on about deviceConnect popup.";
		strExpectedResult = "Correct deviceConnect logo should be displayed on the about dc popup.";
		//PerformAction("browser", Action.Scroll, "30");
		isEventSuccessful = PerformAction("lnkAbout", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleDeviceConnectLogoOnAbout", Action.isDisplayed);
			if (!isEventSuccessful)
			{
				strActualResult = "Correct deviceConnect logo is not displayed on about deviceConnect popup.";
			}
		}
		else
		{
			strActualResult = " 'About deviceConnect' link does not exist on page.";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strStepDescription, strExpectedResult, "Correct deviceConnect logo is displayed on the about dc popup.", "Pass");
		}
		else
		{
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, "Fail");
		}
	}
}
}
