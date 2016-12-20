package com.test.java;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Jira Test Case Id: QA-2172
 */
public class _392_Verify_Install_dropdown_with_delete_button extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";
	private boolean clickable = false;
	private boolean flag = false;
	private int errorAppIndex = 0;

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
			strActualResult = "Application Page is not opened.";
		}
		reporter.ReportStep("Verify Applications Page is loaded", "'Applications' Page should be loaded.", strActualResult, isEventSuccessful); */
		

		//////////////////////////////////////////////////////////////////////////
		//Step 3 - Navigate to Applications Page
		//////////////////////////////////////////////////////////////////////////
		
		isEventSuccessful = PerformAction("hdrApplicationLink", Action.Click);

		
		
		//Step 4 - Verify that applications are loaded
		List<WebElement> TableRows = driver.findElements(By.xpath(dicOR.get("eleAppTableRows")));
		if (TableRows.size() > 0)
		{
			isEventSuccessful = true;
			strActualResult = "Atleast more than one Application is uploaded.";
		}
		else
		{
			strActualResult = "Not a single Application is present.";
		}
		reporter.ReportStep("Verify Applications are loaded", "'Applications' should be loaded.", strActualResult, isEventSuccessful);

		//Step 5 - Verify that each application has Install button and a dropdown with delete button as menu item
		if (isEventSuccessful)
		{
			List<WebElement> InstallButtons = driver.findElements(By.xpath("//table[@class='table data-grid tablesorter']/tbody/tr/td[5]/div/button[@class='btn btn-info app-install-btn']"));
			if (TableRows.size() == InstallButtons.size())
			{
				strActualResult = "Every Application has an Install button.";
				for (int j = 1; j <= TableRows.size(); j++)
				{
					//clickable = PerformAction(dicOR["eleInstallAppDropdown"] + "[" + j + "]", Action.Click);
					flag = PerformAction("(//a[@class='app-delete-btn'])[" + j + "]", Action.isDisplayed);
					if (!flag)
					{
						errorAppIndex = j;
						break;
					}
				}
			}
			else
			{
				strActualResult = "Either of the Application has no Install butotn displayed.";
			}
		}
		else
		{
			strActualResult = "No applications are uploaded.";
		}
		if (!flag)
		{
			strActualResult = "Delete button is not visible for application at index: " + errorAppIndex;
		}
		reporter.ReportStep("Verify Every Application Install button has dropdown with delete button","Evrey Install button has Dropdown with delete",strActualResult,isEventSuccessful);
	}
}