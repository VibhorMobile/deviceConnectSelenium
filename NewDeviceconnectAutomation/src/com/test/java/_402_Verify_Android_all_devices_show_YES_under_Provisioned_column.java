package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-347
 */

public class _402_Verify_Android_all_devices_show_YES_under_Provisioned_column extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";

	public final void testScript()
	{

		//**********************************************************************************************************************//
		// Go to application details page of any android application and verify that all devices have 'Yes' in front of them --
		// under provisioned column (Compatible Devices section)
		//**********************************************************************************************************************//

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
		isEventSuccessful = LoginToDC();
		if (isEventSuccessful)
		{
			strActualResult = "User - " + dicCommon.get("EmailAddress") + " is logged in to deviceConnect.";
		}
		else
		{
			strActualResult = "User - " + dicCommon.get("EmailAddress") + " is not logged in to deviceConnect.";
		}
		reporter.ReportStep("Login to deviceConnect", "User should be logged in.", strActualResult, isEventSuccessful);

		//Step 3 - Navigate to Device Menu -> Application page
		/*isEventSuccessful = selectFromMenu("Applications", "eleApplicationsHeader");
		if (isEventSuccessful)
		{
			strActualResult = "Applications Page is opened.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep("Verify Applications Page is loaded", "'Applications' Page should be loaded.", strActualResult, isEventSuccessful);
*/
		////Step 4 - Verify that application page is loaded
		//isEventSuccessful = PerformAction("//a[@class='platform-filter'][text()='Android']", Action.Click);
		//if (isEventSuccessful)
		//{
		//    string replace = dicOR["eleAppNameAppTable"].Replace("__APP_INDEX__", "1");                
		//    isEventSuccessful = PerformAction(replace, Action.Click);
		//    if (isEventSuccessful)
		//    {
		//        replace = dicOR["eleHeader"].Replace("__EXPECTED_HEADER__","Device List");
		//        isEventSuccessful = PerformAction(replace, Action.Exist);
		//        if (isEventSuccessful)
		//            strActualResult = "Device List Heading is displayed.";
		//        else
		//            strActualResult = "Device List Heading is not displayed.";
		//    }
		//    else
		//        strActualResult = "Not able to click on first application name.";
		//}
		//reporter.ReportStep("Verify device list header is displayed", "'Device List' header should be displayed.", strActualResult, isEventSuccessful);


		
		//////////////////////////////////////////////////////////////////////////
		//Step 3 - Navigate to Applications Page
		//////////////////////////////////////////////////////////////////////////
		
		isEventSuccessful = PerformAction("hdrApplicationLink", Action.Click);
		
		
		PerformAction(dicOR.get("OSFilter_applications"), Action.Click);
		
		// Select 'Android' option for platform filter.
		/*isEventSuccessful = SelectFromFilterDropdowns("Platform", "Android", "Applications", "list");
		if (isEventSuccessful)
		{
			strActualResult = "Android option selected successfully from platform filter.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep("select 'Android' from 'Platform' filter.", "Only android applications should be displayed.", strActualResult, isEventSuccessful);

		*/
		
		// Go to app details page of an android application
		isEventSuccessful = SelectApplication("first");
		if (isEventSuccessful)
		{
			strActualResult = "Application details page of first device opened successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep("Go to app details page of first android app displayed.", "Application details page should be opened.", strActualResult, isEventSuccessful);

		//Step 5 - Verify that every device has provisioned as YES
		isEventSuccessful = VerifyProvisionedValue_AppDetails("Yes");
		if (isEventSuccessful)
		{
			strActualResult = "Value for Provisioned is 'YES' for all devices displayed.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		//IList<IWebElement> DeviceRowsName = driver.FindElements(By.XPath(dicOR["eleDeviceListNameCol"]));
		//for (int i = 1; i <= DeviceRowsName.Count; i++)
		//{
		//    string provisionedstatus = "//tr["+ (i+1).ToString() +"]/td[3]";
		//    isEventSuccessful = PerformAction(provisionedstatus, Action.Exist);
		//    if (isEventSuccessful)
		//    {
		//        string Status = driver.FindElement(By.XPath(provisionedstatus)).Text;
		//        if (Status != "Yes")
		//        {
		//            isEventSuccessful = false;
		//            strActualResult = "YES is not displayed under provisioned column at index : " + (i+1);
		//        }
		//        else
		//            strActualResult = "YES is displayed under provisioned column.";
		//    }
		//    else
		//        strActualResult = "Provision Status is not displayed.";
		//}                
		reporter.ReportStep("Verify Provisioned status is displayed", "'Provisioned status' should be displayed.", strActualResult, isEventSuccessful);
	}
}