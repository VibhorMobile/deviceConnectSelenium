package com.test.java;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
import com.opencsv.CSVReader;
/*
 * Jira Test Case Id: QA-299
 */
public class _634_Verify_that_Admin_users_can_download_the_zip_installer_package_iOS_Management_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "";
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	
	
	public final void testScript() throws Exception
	{

		//*************************************************************//
		//Step 1 : login to deviceConnect with admin user.
		//*************************************************************//
		isEventSuccessful = Login(); 
		isEventSuccessful =  GoToSystemPage();
		 waitForPageLoaded();
		isEventSuccessful = PerformAction("lnkiOSMgmnt", Action.Click);
		 waitForPageLoaded();
	
		isEventSuccessful = PerformAction("//p//a[text()='Download the deviceConnect provisioning tool.']", Action.ClickUsingJS);
	  
		if(isEventSuccessful)
		{
			Thread.sleep(5000);
			if(dicCommon.get("BrowserName").toLowerCase().equals("firefox"))
			{
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_DOWN);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyPress(KeyEvent.VK_ENTER);
			
				Thread.sleep(12000);
			}
			String dir = System.getProperty("user.dir");
			File sourceDir = new File("C:\\Users\\dsolanki\\Downloads");
			if ((new File("C:\\Users\\dsolanki\\Downloads\\deviceConnect_iOS_Provisioning_Tool_7.2.5346.1010.production@d3b43fb.zip")).isFile())
			{
				isEventSuccessful = true;
				strActualResult = "Admin_users is able to_download_the_zip_installer_package_iOS_Management_page";
   			}
			else
			{
				strActualResult = "Admin_users is not able to_download_the_zip_installer_package_iOS_Management_page";
			}
		 }
		 else
		 {
			strActualResult = "Unable to click on 'Download the deviceConnect provisioning tool.' link";
		 }
		 strStepDescription = "Verify_that_Admin_users_can_download_the_zip_installer_package_iOS_Management_page";
		 strExpectedResult = "_Admin_users should be able to_download_the_zip_installer_package_iOS_Management_page";
		 reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);	
	
	}
}