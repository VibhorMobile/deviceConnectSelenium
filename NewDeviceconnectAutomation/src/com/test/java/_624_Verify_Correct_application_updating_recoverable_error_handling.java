package com.test.java;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: Third week of February
 * Last Modified Date: Same as creation date
 * Jira Test Case Id-1574
 */

public class _624_Verify_Correct_application_updating_recoverable_error_handling extends ScriptFuncLibrary 
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
		
		isEventSuccessful = searchDevice_DI("Flipkart");
		isEventSuccessful = !VerifyMessage_On_Filter_Selection();
		if(isEventSuccessful)
		{
			isEventSuccessful = PerformAction(dicOR.get("eleInstallAppDropdown")+"[1]", Action.ClickUsingJS);
			PerformAction("browser","wait");
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction(dicOR.get("eleDeleteOption_AppPage"), Action.ClickUsingJS);
				if (isEventSuccessful)
				{
					strActualResult = "User is able to click on 'Delete' button for first displayed application.";
				}
				else
				{
					strActualResult = "Could not click on 'Delete' button for first application.";
				}
			}
			else
			{
				strActualResult = "Could not click on 'Install' dropdown for first displayed application.";
			}
			PerformAction("//button[text()='Delete all']",Action.WaitForElement);
			isEventSuccessful = PerformAction("//button[text()='Delete all']", Action.Click);
		}
		reporter.ReportStep("Verify user is able to click on delete app", "User should be able to click on delete", strActualResult, isEventSuccessful);
		
		
		strStepDescription = "Click on upload Application button and upload the application on Applications page.";
		strExpectedResult =  "Application should be uploaded";
		isEventSuccessful =   uploadApplication("Flipkart-2.apk");
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
						Thread.sleep(15000);
						PerformAction("browser","wait");
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
		
		
		
		
	}	
  }	
