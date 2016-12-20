package com.test.java;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: Last week of January
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-440
 */
public class _582_Verify_that_if_the_install_will_overwrite_the_app_alrready_on_the_device extends ScriptFuncLibrary 
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	Object[] Values = new Object[5]; 
	private String strActualResult = "", devicename = "",EmailAddress, Password,Version;
	String AppNameLink = "",version, build, build1 , version1;
	Object[] appdetailsvalues = new Object[10];
	
	public final void testScript() throws InterruptedException
	{
	
		// Step 1 - Login to deviceConnect//
		isEventSuccessful = Login();
			
		// Step 2 : Navigate to Application page.
		isEventSuccessful = GoToApplicationsPage();
					  
		//Step 3 : Click on upload and upload the 'Dollar General 2.1.ipa'.
		strStepDescription = "Click on upload Application button and upload the application on Applications page.";
		strExpectedResult =  "Application should be uploaded";
		isEventSuccessful =   uploadApplication("Flipkart.apk");
		if(isEventSuccessful)
		{
			
			isEventSuccessful = searchDevice_DI("Flipkart");
			isEventSuccessful = !VerifyMessage_On_Filter_Selection();
			if(isEventSuccessful)
			{
				strActualResult = "App uploaded successfully";
			}
			else
			{
				strActualResult = "App did not uploaded";
			}
		}	
		else
		{
			strActualResult = "Unable to upload application";
		}	
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
					  
		//Step 4 : Click on uploaded application name.
		strStepDescription = "Install the uploaded Application name.";
		strExpectedResult =  "Application should be uploaded on Available devices.";
		
		isEventSuccessful = PerformAction("btnInstall_appsPage", Action.Click);
		if(isEventSuccessful)
		{
			devicename = GetTextOrValue("deviceNameAfterInstall_AppPage", "text");
			if(devicename!=null)
			{
				isEventSuccessful = PerformAction("InstalldeviceAfterInstall_AppPage", Action.Click);
				if(isEventSuccessful)
				{
					isEventSuccessful = PerformAction("(//button[contains(@class,'btn-info btn-continue')])", Action.Click);
					if(isEventSuccessful)
					{
						Thread.sleep(60000);
						isEventSuccessful =  PerformAction("browser", Action.WaitForPageToLoad);
						if(isEventSuccessful)
						{
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
					  
		// Step 5 : Click on finish button
		isEventSuccessful = PerformAction("(//button[contains(@class,'btn-info btn-finish')])", Action.Click);
				
		// Step 6 : Go to Devcies page.
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToDevicesPage();
		}
					   
		// Step 7 : Search for the device which we have installed an APplication.
		strStepDescription = "Verify that searched device name is being dispalyed.";
		strExpectedResult = "Searched device name should be dispalyed.";
		
		isEventSuccessful = searchDevice(devicename, "devicename");
		if(isEventSuccessful)
		{
			strActualResult = "Searched device name should be displayed.";
		}
		else
		{
			strActualResult = "Searched device name is not displayed.";
		}	
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		// Step 8 : CLick on searched device Name.
		Values = GoTofirstDeviceDetailsPage();
		isEventSuccessful = (boolean) Values[0];
		devicename = (String) Values[1];
		
		// Step 9 : Verfiy the app is available under installed section.
		strStepDescription = "Verfiy the app is available under installed section";
		strExpectedResult = "Application should be available under installed section.";
		int apps = GenericLibrary.driver.findElements(By.xpath("//table[contains(@class,'installed-apps-table')]/tbody/tr")).size(); // get count of compatible devices
		
		for (int i = 1; i <= apps; i++)
		{
			isEventSuccessful =  GetTextOrValue(dicOR.get("eleinstalledApplication_DevcieDetailsPage").replace("index", String.valueOf(i)), "text").equals("Flipkart");
			if(isEventSuccessful)
			{
				version =  GetTextOrValue("//table[contains(@class,'installed-apps-table')]/tbody/tr[index]/td[2]".replace("index", String.valueOf(i)), "text");
				build =  GetTextOrValue("//table[contains(@class,'installed-apps-table')]/tbody/tr[index]/td[3]".replace("index", String.valueOf(i)), "text");
				strActualResult = "Application is available under installed section" ;
				break;
			}
			else
			{
				strActualResult = "Application not found under installed section" ;
			}
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		// Step 10 : Navigate to Application page.
		isEventSuccessful = GoToApplicationsPage();
			
		
		//Step 11 : Click on upload and upload the 'ScreenRefresh -2.apk'.
		strStepDescription = "Click on upload Application button and upload the application on Applications page.";
		strExpectedResult =  "Application should be uploaded";
		isEventSuccessful =   uploadApplication("Flipkart-2.apk");
		if(isEventSuccessful)
		{
			isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
			isEventSuccessful = searchDevice_DI("Flipkart");
			isEventSuccessful = !VerifyMessage_On_Filter_Selection();
			if(isEventSuccessful)
			{
				strActualResult = "App uploaded successfully";
			}
			else
			{
				strActualResult = "App did not uploaded";
			}
		}
		else
		{
			strActualResult = "Unable to upload application";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
						  
				
		//Step  : Click on uploaded application name.
		strStepDescription = "Install the uploaded Application name.";
		strExpectedResult =  "Application should be uploaded on Available devices.";
		
		isEventSuccessful = PerformAction("btnInstall_appsPage", Action.Click);
		if(isEventSuccessful)
		{
			int totaldevciecount = GenericLibrary.driver.findElements(By.xpath("//div[@class='modal-dialog']//..//ul//li")).size(); // get count of compatible devices
			
			for (int i = 3; i <= totaldevciecount; i++)
			{
				isEventSuccessful =  GetTextOrValue("//div[@class='modal-dialog']//..//ul//li[index]//span[1]".replace("index", String.valueOf(i)), "text").equals(devicename);
				if(isEventSuccessful)
				{
					break;
				}
			}
			
			if(isEventSuccessful)
			{
				isEventSuccessful = PerformAction("InstalldeviceAfterInstall_AppPage", Action.Click);
				if(isEventSuccessful)
				{
					isEventSuccessful = PerformAction("(//button[contains(@class,'btn-info btn-continue')])", Action.Click);
					if(isEventSuccessful)
					{
						Thread.sleep(60000);
						isEventSuccessful =  PerformAction("browser", Action.WaitForPageToLoad);
						if(isEventSuccessful)
						{
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
		
		// Step  : Click on finish button
		isEventSuccessful = PerformAction("(//button[contains(@class,'btn-info btn-finish')])", Action.Click);
		
		// Step  : Go to Devcies page.
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToDevicesPage();
		}
		
		// Step  : Search for the device which we have installed an APplication.
		strStepDescription = "Verify that searched device name is being dispalyed.";
		strExpectedResult = "Searched device name should be dispalyed.";
		
		isEventSuccessful = searchDevice(devicename, "devicename");
		if(isEventSuccessful)
		{
			strActualResult = "Searched device name should be displayed.";
		}
		else
		{
			strActualResult = "Searched device name is not displayed.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		// Step  : CLick on searched device Name.
		Values = GoTofirstDeviceDetailsPage();
		isEventSuccessful = (boolean) Values[0];
		devicename = (String) Values[1];
						 
		// Step  : Verfiy the app is available under installed section.
		strStepDescription = "Verfiy the app is available under installed section";
		strExpectedResult = "Application should be available under installed section.";
		apps = GenericLibrary.driver.findElements(By.xpath("//table[contains(@class,'installed-apps-table')]/tbody/tr")).size(); // get count of compatible devices
							
		for (int i = 1; i <= apps; i++)
		{
			isEventSuccessful =  GetTextOrValue(dicOR.get("eleinstalledApplication_DevcieDetailsPage").replace("index", String.valueOf(i)), "text").equals("Flipkart");
			if(isEventSuccessful)
			{
				version1 =  GetTextOrValue("//table[contains(@class,'installed-apps-table')]/tbody/tr[index]/td[2]".replace("index", String.valueOf(i)), "text");
				build1 =  GetTextOrValue("//table[contains(@class,'installed-apps-table')]/tbody/tr[index]/td[3]".replace("index", String.valueOf(i)), "text");
			}
					             
			if(version1!=null && build1!=null)
			{
				strActualResult = " version & build no. is being dispalyed on device details page." + "version1 : " + " " + version1 + " " + "build1 : " + " " +build1 ;
				break;
			}
			else
			{
				strActualResult = " version & build no. not dispalyed on device details page.";
			}
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			        
		//Step  : Verify version & build is not same after updating application on device details page.
		strStepDescription = "Verfiy the version & build is not same after upgrading or downgrading the application builds";
		strExpectedResult = "Version & build is not same after upgrading or downgrading the application builds";
		if(!version.equals(version1) && !build.equals(build1))
		{
			strActualResult = "Successfully build is upgrading or downgrading on the device." ;   
		}
		else
		{
			strActualResult = "Successfully build is not  upgraded or downgraded on the device." ; 
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

	}	
}
