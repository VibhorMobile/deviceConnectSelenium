package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-466
 */

public class _386_Verify_uninstall_button_is_enabled_for_each_installed_app_on_Available_device extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private Object[] arrResult;
	private String strErrMgs_Script = "", strActualResult = "", strStepDescription = "", strExpectedResult = "";
	private int InstalledAppsCount = 0;
	private String errorIndex = "";

	public final void testScript()
	{
		//Step 1 - Login to deviceConnect
		isEventSuccessful = Login(); 

		//Step 3 - Select Android from Platform filter
		isEventSuccessful =	selectPlatform("Android");
		
		//Step 4 - Select available from Status filter
		isEventSuccessful = selectStatus("Available");

		//Step 4 - Select First Device
		arrResult = GoTofirstDeviceDetailsPage();
		isEventSuccessful = (boolean)arrResult[0];
		
		//Step 5 - Verify Install Application Table is displayed
		isEventSuccessful = PerformAction("DeviceDetails_Install_App_Table", Action.WaitForElement, "5");
		if (isEventSuccessful)
			strActualResult = "Install Application header is displayed.";
		else
			strActualResult = "Install Application header is not displayed on Device Details Page.";
		reporter.ReportStep("Verify Install Application Table is displayed on device details page.", "'Install Application' table should be displayed.", strActualResult, isEventSuccessful);

		// Step 6 : Get number of installed apps on the device
		InstalledAppsCount = getelementCount("DeviceDetails_Install_App_rows");
		isEventSuccessful = (InstalledAppsCount != 0);
		if (isEventSuccessful)
			strActualResult = "Number of installed applications are : " + InstalledAppsCount;
		else
			strActualResult = "Could not get number of installed applications.";
		reporter.ReportStep("Get number of installed apps on the device.", "There should be apps installed on the devices.", strActualResult, isEventSuccessful);


		// Verify that there is 'Install' button in front of each installed application
		try
		{
			isEventSuccessful = PerformAction("eleNoInstalledApps_deviceDetails", Action.isNotDisplayed);
			if (isEventSuccessful)
			{
				for (int i = 1; i <= InstalledAppsCount; i++)
				{
					isEventSuccessful = PerformAction(dicOR.get("btnUninstallEnabled") + "[" + i + "]", Action.isDisplayed);
					if (!isEventSuccessful)
						errorIndex = errorIndex + ", " + i;
				}
				if (errorIndex.equals("") && InstalledAppsCount > 0)
				{
					isEventSuccessful = true;
					strActualResult = "Uninstall button is present and enabled for all displayed installed applications on details page of device.";
				}
				else
				{
					isEventSuccessful = false;
					strActualResult = "Uninstall button is not present for applications at the following indices : " + errorIndex;
				}
			}
			else
				strActualResult = "No applications installed on the device.";
		}
		catch (RuntimeException e)
		{
			isEventSuccessful = false;
			strActualResult = "Error in 'for loop' for iterating over application table headers. : " + e.getMessage();
		}
		reporter.ReportStep("Verify UnInstall button is displayed infront of each installed application", "'UnInstall button' should be displayed for each installed application.", strActualResult, isEventSuccessful);

		////Step 6 - Verify UnInstall Application button for each application
		//IList<IWebElement> Rows = driver.FindElements(By.XPath(dicOR["DeviceDetails_Install_App_rows"]));
		//if (Rows.Count > 2)
		//{
		//    for (int i = 3; i < Rows.Count; i++)
		//    {
		//        string uninstallXpath = dicOR["DeviceDetails_Install_App_rows"] + "[" + i.ToString() + "]" + "/td[4]";
		//        IWebElement UninstallBtn = driver.FindElement(By.XPath(uninstallXpath));
		//        if (!UninstallBtn.Displayed)
		//        {
		//            isEventSuccessful = false;
		//            strActualResult = "UnInstall Application button is not displayed for row : " + i.ToString();
		//        }
		//    }
		//}
		//if (isEventSuccessful)
		//{
		//    strActualResult = "UnInstall Application button is displayed infront of each application.";
		//}
		//else
		//{
		//    strActualResult = "UnInstall Application button name is not displayed infront of application.";
		//}
		//reporter.ReportStep("Verify UnInstall button is displayed infront of each installed application", "'UnInstall button' should be displayed for each installed application.", strActualResult, isEventSuccessful);
	}
}