package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2201
 */
public class _310_Verify_that_If_user_logged_in_as_an_Admin_user_and_logout_from_any_page_except_System_Page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String testerEmail = "", testerPassword = "";

	public final void testScript()
	{
		//*************************************************************************************************************//
		// Verify that If user logged in as an Admin user and logout from any page except "System Page" then re-login with a Test user should not navigate to the same page
		//*************************************************************************************************************//

		// Getting values from TestData sheet
		testerEmail = getValueFromDictionary(dicCommon, "testerEmailAddress");
		testerPassword = getValueFromDictionary(dicCommon, "testerPassword");

		// Step 1 : login to deviceConnect with valid user and verify Devices page.
		isEventSuccessful = Login();

		// Step 3 : Go to users page
		isEventSuccessful = GoToUsersPage();

		// Step 4 : Logout from DC
		isEventSuccessful = LogoutDC();

		// Step 5 : Login with different test user
		isEventSuccessful = LoginToDC(testerEmail,testerPassword);

		// Step 6 : Verify Devices page is dispayed
		strstepDescription = "Verify Devices page";
		strexpectedResult = "Devices page should be displayed";
		isEventSuccessful = PerformAction("eleDevicesTab_Devices", Action.WaitForElement, "3");
		if (isEventSuccessful)
		{
			strActualResult = "Devices page is displayed";
		}
		else
		{
			strActualResult = "Devices page is not displayed";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Step 4 : Logout from DC
		isEventSuccessful = LogoutDC();

		/** Now test for System page
		*/
		// Step 1 : login to deviceConnect with valid admin user and verify Devices page.
		isEventSuccessful = Login();

		// Step 3 : Go to system page
		isEventSuccessful = GoToSystemPage();

		// Step 4 : Logout from DC
		isEventSuccessful = LogoutDC();

		// Step 5 : Login with different test user
		isEventSuccessful = LoginToDC(testerEmail,testerPassword);

		// Step 6 : Verify Devices page
		strstepDescription = "Verify Devices page";
		strexpectedResult = "Devices page should be displayed";
		isEventSuccessful = PerformAction("eleDevicesTab_Devices", Action.WaitForElement, "3");
		if (isEventSuccessful)
		{
			strActualResult = "Devices page is displayed";
		}
		else
		{
			strActualResult = "Devices page is not displayed";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}