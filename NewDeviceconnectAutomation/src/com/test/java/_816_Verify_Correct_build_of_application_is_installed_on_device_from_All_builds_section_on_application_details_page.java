package com.test.java;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Jira Test Case Id: QA-816
 */

public class _816_Verify_Correct_build_of_application_is_installed_on_device_from_All_builds_section_on_application_details_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",build="",devicename="";

	public final void testScript() throws InterruptedException
	{
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();

		// Step 2 : Navigate to Application page.
		isEventSuccessful = GoToApplicationsPage();

		isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
		isEventSuccessful = searchDevice_DI("Aldiko");
		String AppName=GetTextOrValue("appNameColumnDetails", "text");
		strstepDescription = "Open application details page for first Available app.";
		strexpectedResult =  "Application details page for the first available app should be displayed.";
  
		if (isEventSuccessful)
		{
			isEventSuccessful = !VerifyMessage_On_Filter_Selection();
			if (isEventSuccessful) // continue only if there are some devices under android platform.
			{
				isEventSuccessful = SelectApplication("first");
				if (isEventSuccessful)
				{
					strActualResult = "App details page displayed";
				}
				else
				{
					strActualResult = "App details page did not displayed";
				}
			}
			else
			{
				strActualResult = "Warning messgae displayed on app page.";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		


		//******************************************************************//
		//** Step 3 : Install application on the device ****//
		//******************************************************************//
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			build=GetTextOrValue(dicOR.get(dicOR.get("versionBuildUploadDateAppDetailsPage").replace("tr[*]/td[2]", "tr[1]/td[3]")),"text");

			strStepDescription = "Install the uploaded Application name.";
			strExpectedResult =  "Application should be uploaded on Available devices.";

			isEventSuccessful = PerformAction("eleInstallAppDetails", Action.Click);
			if(isEventSuccessful)
			{
				waitForPageLoaded();
				devicename = GetTextOrValue("deviceNameAfterInstall_AppPage", "text");
				if(devicename!=null)
				{
					isEventSuccessful = PerformAction("InstalldeviceAfterInstall_AppPage", Action.Click);
					if(isEventSuccessful)
					{
						waitForPageLoaded();
						isEventSuccessful = PerformAction("ContinuebtnInstallDialog", Action.Click);
						if(isEventSuccessful)
						{
							Thread.sleep(40000);
							waitForPageLoaded();
							isEventSuccessful =  PerformAction("browser", Action.WaitForPageToLoad);
							if(isEventSuccessful)
							{
								waitForPageLoaded();
								isEventSuccessful = PerformAction("SuuceesfulAfterInstallappondevice_AppPage", Action.WaitForElement);
								if(isEventSuccessful)
								{
									String classvalue =  getAttribute("SuuceesfulAfterInstallappondevice_AppPage", "class");
									if(classvalue.equalsIgnoreCase("modal-success-indicator"))
									{
										strActualResult =  "Application is successfully installed on device";  
									}
									else
									{
										strActualResult =  "Application is not installed successfully on device"; 
									}
								}
								else
								{
									strActualResult =  "Successfull indicator not displayed on dialog box.";  
								}
							}
						}
						else
						{
							strActualResult =  "Could not clicked on Continue button."; 
						}
					}
					else
					{
						strActualResult =  "Could not clicked on Install radio button.";
					}
				}
				else 
				{
					strActualResult =  "Could not get the device name";
				}

			}
			else 
			{
				strActualResult =  "Install button is not clicked successfully.";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		// Step 5 : Click on finish button
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("finishbtninstallDialog", Action.Click);
		}

		// Step 6 : Go to Devcies page.
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToDevicesPage();
		}

		// Step 7 : Search for the device which we have installed an APplication.
		if (isEventSuccessful)
		{
			strStepDescription = "Verify that searched device name is being dispalyed.";
			strExpectedResult = "Searched device name should be dispalyed.";

			isEventSuccessful = searchDevice(devicename, "devicename");
			if(isEventSuccessful)
			{
				strActualResult = "Searched device name displayed.";
			}
			else
			{
				strActualResult = "Searched device name is not displayed.";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		// Step 8 : CLick on searched device Name.
		if (isEventSuccessful)
		{
			Object[] Values = GoTofirstDeviceDetailsPage();
			isEventSuccessful = (boolean) Values[0];
			devicename = (String) Values[1];
		}
		// Step 9 : Verfiy the app is available under installed section.
		if (isEventSuccessful)
		{
			strStepDescription = "Verfiy the app is available under installed section";
			strExpectedResult = "Application should be available under installed section.";
			int apps = GenericLibrary.driver.findElements(By.xpath("//table[contains(@class,'installed-apps-table')]/tbody/tr")).size(); // get count of compatible devices

			for (int i = 1; i <= apps; i++)
			{
				isEventSuccessful =  GetTextOrValue(dicOR.get("eleinstalledApplication_DevcieDetailsPage").replace("index", String.valueOf(i)), "text").equals(AppName);

				if(isEventSuccessful)
				{
					if(build.contains(GetTextOrValue(dicOR.get("//table[contains(@class,'installed-apps-table')]/tbody/tr[index]/td[3]").replace("index", String.valueOf(i)), "text")))
					{
						strActualResult="Latest build installed on the device";
					}
					else
					{
						strActualResult="Build version do not matches.";
					}

				}
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
	}	
}