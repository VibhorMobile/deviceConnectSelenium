package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ApplicationLibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-885
 */

public class _408_Verify_Delete_option_is_not_displayed_to_test_user_on_applications_table extends ApplicationLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "", EmailAddress = dicCommon.get("testerEmailAddress"), PassWord = dicCommon.get("testerPassword");

	public final void testScript()
	{
		//Step 1 - Launch deviceConnect and verify homepage.
		genericLibrary.LaunchWebDriver();
		isEventSuccessful = PerformAction("imgDCLogo", Action.Exist);
		if (isEventSuccessful)
		{
			strActualResult = "Navigated to " + dicCommon.get("ApplicationURL") + ". deviceConnect homepage is opened.";
		}
		else
		{
			strActualResult = "deviceConnect homepage is not opened.";
		}
		reporter.ReportStep("Launch deviceConnect and verify homepage.", "deviceConnect homepage should be opened.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}

		//Step 2 - Login to deviceConnect
		isEventSuccessful = LoginToDC(EmailAddress, PassWord);
		if (isEventSuccessful)
		{
			strActualResult = "User - " + EmailAddress + " is logged in to deviceConnect.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep("Login to deviceConnect", "User should be logged in.", strActualResult, isEventSuccessful);

		//Step 3 - Navigate to Applications Page
		
/*		isEventSuccessful = selectFromMenu("Applications", "eleApplicationsHeader");
		if (isEventSuccessful)
		{
			strActualResult = "Applications Page is opened.";
		}
		else
		{
			strActualResult = "Applications Page is not opened.";
		}*/

		isEventSuccessful = PerformAction("hdrApplicationLink", Action.Click);
		reporter.ReportStep("Verify Applications Page is loaded", "'Applications' Page should be loaded.", strActualResult, isEventSuccessful);
		

		//Step 4 - Verify delete button is disabled for Test User
		isEventSuccessful = VerifybtnDeleteOnApplicationsPage(false);

		if (isEventSuccessful)
		{
			strActualResult = "Delete button is not displayed to test user on applications index page.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		//IList<IWebElement> InstallAppDrop = driver.FindElements(By.XPath(dicOR["eleInstallAppDropdown"]));
		//for (int j = 1; j < InstallAppDrop.Count; j++)
		//{
		//    isEventSuccessful = !PerformAction(InstallAppDrop[j] + "["+ j +"]", Action.isEnabled);
		//    if (isEventSuccessful)
		//    {
		//        //isEventSuccessful = !PerformAction("(//a[@class='app-delete-btn'])[" + j + "]", Action.isEnabled);
		//        //if (isEventSuccessful)
		//        //    strActualResult = "Delete Button is not enabled for " + EmailAddress;
		//        //else
		//        strActualResult = "Delete Button is not enabled for " + EmailAddress;
		//    }
		//    else
		//        strActualResult = "Delete Button is enabled for " + EmailAddress;
		//}   
		
		
		reporter.ReportStep("Verify Delete button is disabled", "'Delete Button' should be disabled for Test User.", strActualResult, isEventSuccessful);
	}
}