package com.test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: Second week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-195
 */
public class _619_Verify_that_there_should_be_automatic_polling_on_Devices_page extends ScriptFuncLibrary

{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	Object[] Values = new Object[5]; 
	private String strActualResult = "", devicename = "",EmailAddress, Password;
	WebDriver driver1;
	
	public final void testScript() 
	{

		// Step 1 - Login to deviceConnect with admin credentials//
		isEventSuccessful = Login(dicCommon.get("testerEmailAddress"),dicCommon.get("testerPassword")); 
				   
		isEventSuccessful =  selectPlatform("Android");
		
		isEventSuccessful = selectStatus("Available");
		waitForPageLoaded();
		// Step 2 : Get Main Window ID.
		String mainWindow = driver.getWindowHandle();
		System.out.println(mainWindow);
		
		WebDriver old_driver=GenericLibrary.driver;
		GenericLibrary.driver=null;
		
		
		if (dicCommon.get("BrowserName").toLowerCase().equals("ie"))
		{
			genericLibrary.LaunchWebDriver();
			Logout();
			LoginToDC(dicCommon.get("EmailAddress"), dicCommon.get("Password"),true);
		}
		else
		{
			Login();
		}
		//GenericLibrary.driver.manage().window().fullscreen();
		
		// Step 3 : Navigate to Application page.
		isEventSuccessful = GoToApplicationsPage();
		
		
		//Step 3 : Click on upload and upload the 'deviceControl.apk'.
		strStepDescription = "Click on upload Application button and upload the application on Applications page.";
		strExpectedResult =  "Application should be uploaded";
		isEventSuccessful =   uploadApplication("ARIA.APK");
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
			isEventSuccessful = searchDevice_DI("ARIA");
			waitForPageLoaded();
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
		
		GenericLibrary.driver.quit();
		
		//PerformAction("browser", Action.WaitForPageToLoad);
		
		GenericLibrary.driver=null;
		GenericLibrary.driver = old_driver ;
		old_driver.switchTo().window(mainWindow);
		
		
		//Step 4 : Connect with the first available device.
		PerformAction("browser",Action.Refresh);
		if (dicCommon.get("BrowserName").toLowerCase().equals("ie"))
		{
			Login(dicCommon.get("testerEmailAddress"),dicCommon.get("testerPassword"));
		}
		waitForPageLoaded();
		isEventSuccessful =  PerformAction(dicOR.get("btnConnect_ListView") + "[" + 1 + "]", Action.Click);
		waitForPageLoaded();
		isEventSuccessful = searchApp_deviceconnectDialogbox("ARIA");
		waitForPageLoaded();
		strStepDescription = "Verify_that_there_should_be_automatic_polling_on_Devices_page";
		strExpectedResult = "there_should_be_automatic_polling_on_Devices_page after uploading an app";
		
		if(isEventSuccessful)
		{
			strActualResult = "uploaded application is dispalyed without refreshing the page & automatic_polling_on_Devices_page"; 
		}
		
		else
		{
			strActualResult = "uploaded application is not dispalyed without refreshing the page & automatic_polling_on_Devices_page"; 
		}
		
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

	}
}
