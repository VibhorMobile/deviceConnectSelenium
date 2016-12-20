package com.test.java;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

import net.sf.cglib.asm.Label;

import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1458
 */
public class _761_Verify_Tester_user_isableto_install_app_on_availabledevice_from_AppList_Appdetails_page extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String serverIP=dicCommon.get("ApplicationURL");
	String deviceName, appName;
	int columnCount;
	Object[] values = new Object[5]; 
	Object[] appvalues = new Object[5];



	public final void testScript() throws InterruptedException, IOException
	{

		appName="Trust Browser";
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with tester user.
		//*************************************************************//                     
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");

		strStepDescription = "Login to deviceConnect with valid tester user.";
		strExpectedResult = "Tester User should be logged in successfully.";
		isEventSuccessful = Login(testerEmailAddress,testerPassword);

		isEventSuccessful=GoToDevicesPage();

		isEventSuccessful=selectStatus("Available");
		isEventSuccessful=selectPlatform("Android");

		values=GoTofirstDeviceDetailsPage();
		isEventSuccessful=(boolean) values[0];
		deviceName= (String) values[1];

		isEventSuccessful=PerformAction(dicOR.get("btnUninstall_TrustBrowser_InstalledAppList_DeviceDetails"),Action.Click);

		PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.WaitForElement);
		PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.Click);

		//*************************************************************//                     
		// Step 2 : Navigate to Applications page
		//*************************************************************//  
		isEventSuccessful=GoToApplicationsPage();

		isEventSuccessful=selectPlatform_Application("Android");
		//*************************************************************//                     
		// Step 3 : Verify Install app from App List page
		//*************************************************************//  

		strStepDescription="Verify install app on available device from App List page.";
		strExpectedResult="Installation successful from App List page.";

		//		appName=GetTextOrValue(dicOR.get("eleAppNameAppTable").replace("__APP_INDEX__", "1"),"text");
		//		System.out.println("Selecting one app-----"+appName);

		//Verify Install button exists for the first app on app list page
		waitForPageLoaded();
		isEventSuccessful=PerformAction(dicOR.get("lnkSpecificApp_InstallButton_AppListPage").replace("__APPNAME__", appName), Action.Exist);
		if (isEventSuccessful)
		{
			isEventSuccessful=PerformAction(dicOR.get("lnkSpecificApp_InstallButton_AppListPage").replace("__APPNAME__", appName), Action.Click);
			waitForPageLoaded();
			isEventSuccessful=PerformAction(dicOR.get("radBtn_Yes_DeviceName_InstallAppPopUp_AppListPage").replace("__DEVICENAME__", deviceName), Action.Exist);
			if (isEventSuccessful)
			{
				isEventSuccessful=PerformAction(dicOR.get("radBtn_Yes_DeviceName_InstallAppPopUp_AppListPage").replace("__DEVICENAME__", deviceName), Action.Select);
				waitForPageLoaded();
				isEventSuccessful=PerformAction(dicOR.get("ContinuebtnInstallDialog"), Action.Click);
				waitForPageLoaded();
				PerformAction("browser","waitforpagetoload");
				Thread.sleep(10000);


				waitForPageLoaded();
				isEventSuccessful=PerformAction(dicOR.get("finishbtninstallDialog"), Action.WaitForElement);

				isEventSuccessful=PerformAction(dicOR.get("ImgGreenCheckIcon_DeviceName_InstallAppPopUp_AppListPage").replace("__DEVICENAME__", deviceName),Action.isDisplayed);
				if (isEventSuccessful)
				{
					waitForPageLoaded();
					isEventSuccessful=PerformAction(dicOR.get("finishbtninstallDialog"), Action.Click);
					waitForPageLoaded();
					isEventSuccessful=GoToDevicesPage();
					isEventSuccessful=GoToSpecificDeviceDetailsPage(deviceName);
					waitForPageLoaded();
					String installedapps=getInstalledApps();
					if (installedapps.contains(appName))
					{
						isEventSuccessful=true;
						strActualResult="Application successfully installed on device from App List page";
					}
					else
					{
						isEventSuccessful=false;
						strActualResult="Application not installed on the device.";
					}
				}
				else
					strActualResult="After clicking on Continue buttton, green check icon is not displayed for device -"+deviceName;
			}
			else
				strActualResult="Install radio button is not available for device - "+deviceName;
		}
		else
			strActualResult="Install button is not available for "+appName+ " app.";



		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);


		isEventSuccessful=PerformAction(dicOR.get("btnUninstall_TrustBrowser_InstalledAppList_DeviceDetails"),Action.Click);

		PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.WaitForElement);
		PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.Click);

		isEventSuccessful=GoToApplicationsPage();
		SelectApplication("appame",appName);



		//*************************************************************//                     
		// Step 4 : Verify Install app from App Details page
		//*************************************************************//  

		strStepDescription="Verify install app on available device from App Details page.";
		strExpectedResult="Installation successful from App Details page.";


		//Verify Install button exists for the first app on app list page
		waitForPageLoaded();
		isEventSuccessful=PerformAction(dicOR.get("btnInstall_firstAppDetailsPage"), Action.Exist);
		if (isEventSuccessful)
		{
			isEventSuccessful=PerformAction(dicOR.get("lnkSpecificApp_InstallButton_AppListPage").replace("__APPNAME__", appName), Action.Click);
			waitForPageLoaded();

			isEventSuccessful=PerformAction(dicOR.get("radBtn_Yes_DeviceName_InstallAppPopUp_AppListPage").replace("__DEVICENAME__", deviceName), Action.Exist);
			if (isEventSuccessful)
			{
				waitForPageLoaded();
				isEventSuccessful=PerformAction(dicOR.get("radBtn_Yes_DeviceName_InstallAppPopUp_AppListPage").replace("__DEVICENAME__", deviceName), Action.Select);
				isEventSuccessful=PerformAction(dicOR.get("ContinuebtnInstallDialog"), Action.Click);
				waitForPageLoaded();

				PerformAction("browser","waitforpagetoload");
				Thread.sleep(10000);


				isEventSuccessful=PerformAction(dicOR.get("finishbtninstallDialog"), Action.WaitForElement);

				isEventSuccessful=PerformAction(dicOR.get("ImgGreenCheckIcon_DeviceName_InstallAppPopUp_AppListPage").replace("__DEVICENAME__", deviceName),Action.isDisplayed);
				if (isEventSuccessful)
				{
					isEventSuccessful=PerformAction(dicOR.get("finishbtninstallDialog"), Action.Click);
					waitForPageLoaded();
					isEventSuccessful=GoToDevicesPage();
					waitForPageLoaded();
					isEventSuccessful=GoToSpecificDeviceDetailsPage(deviceName);
					waitForPageLoaded();
					String installedapps=getInstalledApps();
					if (installedapps.contains(appName))
					{
						isEventSuccessful=true;
						strActualResult="Application successfully installed on device from App details page";
					}
					else
					{
						isEventSuccessful=false;
						strActualResult="Application not installed on the device.";
					}
				}
				else
					strActualResult="After clicking on Continue buttton, green check icon is not displayed for device -"+deviceName;
			}
			else
				strActualResult="Install radio button is not available for device - "+deviceName;
		}
		else
			strActualResult="Install button is not available for "+appName+ " app.";



		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

	}
}