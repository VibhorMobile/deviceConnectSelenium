package com.test.java;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-1213
 */
public class _602_Verify_users_can_install_uninstall_apps_on_an_Android_L_device_via_deviceConnect extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ;
	private String strexpectedResult = "" , strActualResult ;
	String OSneedverified[] = {"Android 5.1"};
	
	public final void testScript() 
	{
		// Step 1
		isEventSuccessful = Login();
		
		// Step 2
		selectPlatform("Android");
		
		// Step 3
		isEventSuccessful=selectStatus("Available");
		
		/*// Step 4
		//isEventSuccessful = VerifyOSonDeviceINdexpage(OSneedverified);
		if(isEventSuccessful)
		{
			strActualResult = "Android lollipop is present on device index page" + dicOutput.get("devicename");
		}
		else
		{
			strActualResult = "Android lollipop is not present on device index page";
		}
		reporter.ReportStep("Verify Android lollipop is present on device index page", "Android lollipop should be present on device index page" , strActualResult, isEventSuccessful);
		*/
		// Step 5
		isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0];
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
			isEventSuccessful =  PerformAction("//table[@class='table data-grid']/tbody/tr[3]/td[4]", Action.Click);
			if(isEventSuccessful)
			{
				waitForPageLoaded();
				 isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
				 PerformAction("successfulAfterInstallappondevice_devicedetails", Action.WaitForElement);
				 String succesmessage = GetTextOrValue("successfulAfterInstallappondevice_devicedetails", "text"); 
				 if(isEventSuccessful)
					{
						strActualResult = "An app is Installed on Android lollipop device" + succesmessage;
					}
					else
					{
						strActualResult = "An app is not Installed on Android lollipop device";
						return;
					}
					
			}
			else
			{
				strActualResult = "Could not clicked on install button on app";
			}
		}
		else
		{
			strActualResult = "Could not clicked on device";
		}
 	    reporter.ReportStep("Verify an app gets installed on Android lollipop device" , "An app should be installed on Android lollipop device" , strActualResult , isEventSuccessful);
		
 	    // Step 6
 	   waitForPageLoaded();
 	    isEventSuccessful =  PerformAction("//table[contains(@class,'installed-apps-table')]/tbody/tr[1]/td[4]", Action.Click);
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = PerformAction("Continuebutton_Uninstallapp_devciedetails", Action.WaitForElement);
			isEventSuccessful = PerformAction("Continuebutton_Uninstallapp_devciedetails", Action.Click);
			if(isEventSuccessful)
			{
				waitForPageLoaded();
				PerformAction("successfulAfterInstallappondevice_devicedetails", Action.WaitForElement);
				String succesmessage = GetTextOrValue("successfulAfterInstallappondevice_devicedetails", "text"); 
				if(isEventSuccessful)
				{
					strActualResult = "An app is UnInstalled on Android lollipop device" + succesmessage;
				}
				else
				{
					strActualResult = "An app is not UnInstalled on Android lollipop device";
				}
			}
			else
			{
			 strActualResult = "Could not clicked on Continue button on Uninstall app dialog box";
			}
		}
		else
		{
			strActualResult = "Could not clicked on Uninstall button on app";
		}	
		reporter.ReportStep("Verify an app gets uninstalled on Android lollipop device" , "An app should be uninstalled on Android lollipop device" , strActualResult , isEventSuccessful);	
		
	}	
}
