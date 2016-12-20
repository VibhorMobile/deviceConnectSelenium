package com.test.java;

import org.openqa.selenium.WebElement;

import com.Reporting.Reporter;
import com.common.utilities.ApplicationLibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2162
 */

public class _411_Verify_Undefined_is_not_appended_application_name_on_Install_App_dialog_while_connecting extends ApplicationLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "", EmailAddress = dicCommon.get("EmailAddress"), PassWord = dicCommon.get("Password");
	private java.util.List<WebElement> ListApps = new java.util.ArrayList<WebElement>();
	private String errorIndex = "";

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
			strActualResult = "User - " + EmailAddress + " is not logged in to deviceConnect.";
		}
		reporter.ReportStep("Login to deviceConnect", "User should be logged in.", strActualResult, isEventSuccessful);

		//Step 3 - Navigate to devices Page
		isEventSuccessful = PerformAction("eleDevicesTab_Devices", Action.isDisplayed);
		
		/*isEventSuccessful = selectFromMenu("Devices", "eleDevicesHeader");
		if (isEventSuccessful)
		{
			strActualResult = "Devices Page is opened.";
		}
		else
		{
			strActualResult = "Devices Page is not opened.";
		}
		reporter.ReportStep("Verify Devices Page is loaded", "'Devices' Page should be loaded.", strActualResult, isEventSuccessful);*/

		//Step 4 - Select First Available Device

		
		if (isEventSuccessful)
		{
			isEventSuccessful = OpenLaunchAppDialog("first");
			if (isEventSuccessful)
			{
				/*ListApps = getelementsList("eleAppListItem");*/
				ListApps = getelementsList("appConnectList");
				if (ListApps.size() > 0)
				{
					verifyUndefinedNotAppended();
				}
				else
				{
					strActualResult = "Could not get number of applications displayed on 'Launch Application' dialog.";
				}
			}
			else
			{
				strActualResult = "OpenLaunchAppDialog()-- " + strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "SelectFromFilterDropdowns()-- " + strErrMsg_AppLib;
		}

		reporter.ReportStep("Verify Application Name is correct", "'Undefined' should not be displayed in Applications list on Connect dialog.", strActualResult, isEventSuccessful);
	}

	public final void verifyUndefinedNotAppended()
	{
		strActualResult = "";
		try
		{
			for (int i = 0; i < ListApps.size(); i++)
			{
				if (!ListApps.get(i).getText().equals("") && ListApps.get(i).getText().contains("(Undefined)"))
				{
					errorIndex = errorIndex + ", " + i;
				}
			}
			if (errorIndex.equals(""))
			{
				isEventSuccessful = true;
				strActualResult = "Undefined is not displayed in App List.";
			}
			else
			{
				isEventSuccessful = false;
				strActualResult = "Undefined is appended to applications at indices : " + errorIndex;
			}
		}
		catch (RuntimeException e)
		{
			isEventSuccessful = false;
			strActualResult = strActualResult + e.getMessage();
		}
	}
}