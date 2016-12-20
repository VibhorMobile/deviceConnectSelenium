package com.test.java;

import java.io.IOException;

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
 * Jira Test Case Id: QA-2205
 */
public class _271_Verify_connect_to_application_if_user_has_logged_into_more_than_one_sessions extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "", devicename = "";
	private String strText = "";
	Object[] Values = new Object[4]; 
	WebDriver driver1;
	public final void testScript()
	{
			
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		
		//**************************************************************************//
		//Step 1 - Login to deviceConnect with admin account
		//**************************************************************************//
		isEventSuccessful = Login();
		
		//**************************************************************************//
		//Step 2 - Connect to one application in one device which is available
		//**************************************************************************//
		
		Values = ExecuteCLICommand("run", "iOS", EmailAddress, Password,"", "");
		isEventSuccessful = (boolean)Values[2];
		devicename = (String)Values[3];
		if (isEventSuccessful)
		{
			strActualResult = "Able to connect to an iOS device through 'DeviceViewer'.";
		}
		else
		{
			strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
		}
		reporter.ReportStep("Connect iOS device.", "iOS device should get connected.", strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 3 : Select filter of Status : In Use
		//**************************************************************************//
		PerformAction("", Action.WaitForElement,"10");
		isEventSuccessful = selectStatus("In Use");
		
		//**************************************************************************//
		//Step 4 - Verify Device status In-Use on first browser.
		//**************************************************************************//
		isEventSuccessful = SelectDevice("devicename", devicename);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
			if (isEventSuccessful)
			{
				if (VerifyOnDeviceDetailsPage("Status", "In Use")) 
				{
					isEventSuccessful = true;
					strActualResult = "Device status is changed to 'In-Use' after Viewer is opened.";
				}
			}
		}
		else
		{
			strActualResult = "SelectDevice---" + strErrMsg_AppLib + "\r\n Device name : " + devicename;
		}

		reporter.ReportStep("Check the device availability on first browser", "Device '" + devicename + "' should be in In-Use", strActualResult, isEventSuccessful);
		
		//**************************************************************************//
		//Step 5 - Login to deviceConnect with tester account
		//**************************************************************************//
		
		try
		{
			
			if (dicCommon.get("BrowserName").toLowerCase().equals("firefox"))
			{
				driver1 = new FirefoxDriver();
			}
			else if (dicCommon.get("BrowserName").toLowerCase().equals("chrome"))
			{
				System.setProperty("webdriver.chrome.driver", GenericLibrary.dicConfig.get("Artifacts") + "/chromedriver.exe");
				driver1 = new ChromeDriver();
			}
			else
			{
				try 
				{
					Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 1");
					Thread.sleep(6000);
					driver1 = new InternetExplorerDriver();
				} catch (Throwable e)
				{
					e.printStackTrace();
				}
				
			}
			driver1.manage().window().maximize();
			applicationURL = dicCommon.get("ApplicationURL");
			if (!GenericLibrary.applicationURL.toLowerCase().startsWith("http"))
			{
				GenericLibrary.applicationURL = "http://" + GenericLibrary.applicationURL;
			}
			driver1.navigate().to(GenericLibrary.applicationURL);
			if (!(dicCommon.get("BrowserName").toLowerCase().equals("ie") && GenericLibrary.IEversion.equals("11")))
			{
				driver1.findElement(By.name("username")).sendKeys(dicCommon.get("EmailAddress"));
				driver1.findElement(By.name("password")).sendKeys(dicCommon.get("Password"));
				driver1.findElement(By.xpath(dicOR.get("btnLogin"))).click();
			}
			PerformAction("browser", Action.WaitForPageToLoad);
			PerformAction("eleDevicesTab_Devices", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Successfully logged in to deviceConnect on second browser with User - " + dicCommon.get("testerEmailAddress");
			}
			else
			{
				strActualResult = "Unable to logged in to deviceConnect on second browser with User - " + dicCommon.get("testerPassword");
			}

		}
		catch (RuntimeException e1)
		{
			
			isEventSuccessful = false;
			strActualResult = "Unable to login to the deviceConnect" + e1.getMessage();
		}

		reporter.ReportStep("Login to deviceConnect on second browser", "User should be logged in.", strActualResult, isEventSuccessful);

			
		//**************************************************************************//
		// Step 6 : Select filter of Status : In Use
		//**************************************************************************//
		driver1.findElement(By.xpath("//label[text()[normalize-space()='Available']]")).click();
		driver1.findElement(By.xpath("//label[text()[normalize-space()='Offline']]")).click();
		driver1.findElement(By.xpath("//label[text()[normalize-space()='Android']]")).click();
		
		
		//**************************************************************************//
		//Step 6 - Verify Device status In-Use on second browser
		//**************************************************************************//
		 
		driver1.findElement(By.xpath("//a[starts-with(@href,'/#/Device/Detail/') and text()='"+devicename+"']")).click();
		driver1.findElement(By.xpath("//div[@class='details-container']/a[contains(text(),'Show details...')]")).click();
		isEventSuccessful = driver1.findElement(By.xpath("//span[contains(normalize-space(text()),'In Use')]")).isDisplayed();
		driver1.close();
		  if (isEventSuccessful)
		   {
		
			strActualResult = "Device status is changed to 'In-Use' after Viewer is opened.";
		   }
			
		 else
		 {
			strActualResult = "Device status is not changed to 'In-Use' after Viewer is opened " + devicename;
		 }

		reporter.ReportStep("Check the device availability on second browser", "Device '" + devicename + "' should be in In-Use", strActualResult, isEventSuccessful);


		//**************************************************************************//
		//Step 7 - Post Condition - Close viewer
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", EmailAddress, Password, devicename, "","","" );
		 isEventSuccessful = (boolean)Values[2];
		 if (isEventSuccessful)
		 {
			 strActualResult = "Device released";
				
		 }
		 else
		 {
			 strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
		 }
		isEventSuccessful =	KillObjectInstances("MobileLabs.deviceViewer.exe");
		reporter.ReportStep("Verfiy Device is not connected after closing devcieViewer window","Device should not be connected after closing devcieViewer window","Device is not connected after closing devcieViewer window",isEventSuccessful);
		

	}
}