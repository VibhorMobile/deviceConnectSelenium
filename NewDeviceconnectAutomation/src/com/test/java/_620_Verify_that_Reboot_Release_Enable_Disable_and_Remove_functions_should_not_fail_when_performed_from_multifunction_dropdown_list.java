package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: Third week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-359
 */
public class _620_Verify_that_Reboot_Release_Enable_Disable_and_Remove_functions_should_not_fail_when_performed_from_multifunction_dropdown_list extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "", devicename;
	Object[] values = new Object[2];
	
	
	public final void testScript() throws IOException
	{
	
		//Step 1 - Login to deviceConnect
		 isEventSuccessful = Login(); 
		 
		 isEventSuccessful = selectStatus("Available");
		 
		 strStepDescription = "Verify_that_Reboot_functions_should_not_fail_when_performed_from_multifunction_dropdown_list";
		 strExpectedResult = "Reboot_functions_should_not_fail_when_performed_from_multifunction_dropdown_list on device index page.";
		 
		 isEventSuccessful =  PerformAction("btnConnectArrow_Devicespage", Action.Click);
		 if(isEventSuccessful)
		 {
			 waitForPageLoaded();
			 isEventSuccessful =  PerformAction("btnRebootConnectArrow_Devicespage", Action.Click);
			 if(isEventSuccessful)
			 {
				 waitForPageLoaded();
				 isEventSuccessful = PerformAction("//button[text()='Reboot device']", Action.Click);
				 if(isEventSuccessful)
				 {
					 waitForPageLoaded();
					 isEventSuccessful = PerformAction("(//td[@class='status-row'])[1]//div[@class='status-description-row' and text() = 'Rebooting']", Action.WaitForElement,"60"); 
					 isEventSuccessful = PerformAction("(//td[@class='status-row'])[1]//div[@class='status-description-row' and text() = 'Rebooting']", Action.isDisplayed); 
					 if(isEventSuccessful)
					 {
						 isEventSuccessful = PerformAction("(//td[@class='status-row'])[1]//div[@class='status-description-row' and text() = 'Available']", Action.WaitForElement,"120"); 
						 isEventSuccessful = PerformAction("(//td[@class='status-row'])[1]//div[@class='status-description-row' and text() = 'Available']", Action.isDisplayed); 
					 if(isEventSuccessful)
					 {
						 strActualResult = "Device is rebooted successfully.."; 
					 }
					 else
					 {
						 strActualResult = "Device is not rebooted successfully.." + strErrMsg_GenLib;
					 }
					 }
					 else
					 {
						 strActualResult = "Rebooting status is not dispalyed." + strErrMsg_GenLib;
					 }
					 
					 
				 }
				 else
				 {
					 strActualResult = "Could not clicked on Reboot device button";
				 }
			 }
			 else
			 {
				 strActualResult = "Could not clicked on Reboot button";
			 }
		 }
		 else
		 {
			 strActualResult = "Could not clicked on connect arrow button";
		 }
		 
		 reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		 
		 
		 strStepDescription = "Verify_that_Disable_functions_should_not_fail_when_performed_from_multifunction_dropdown_list";
		 strExpectedResult = "Disable_functions_should_not_fail_when_performed_from_multifunction_dropdown_list on device index page.";
		 
		 waitForPageLoaded();
		 isEventSuccessful =  PerformAction("btnConnectArrow_Devicespage", Action.Click);
		 if(isEventSuccessful)
		 {
			 waitForPageLoaded();
			 isEventSuccessful =  PerformAction("btnDisableConnectArrow_Devicespage", Action.Click);
			 if(isEventSuccessful)
			 {
				 waitForPageLoaded();
				 isEventSuccessful = PerformAction("//button[text()='Disable device']", Action.Click);
				 if(isEventSuccessful)
				 {
					 waitForPageLoaded();
					 isEventSuccessful = PerformAction("(//td[@class='status-row'])[1]//span[contains(@class,'disabled status-icon')]", Action.isDisplayed); 
					 
					 if(isEventSuccessful)
					 {
						 strActualResult = "Device is disabled successfully.."; 
					 }
					 
					 else
					 {
						 strActualResult = "Device is not  disabled successfully.";
					 }
					 
					 
				 }
				 else
				 {
					 strActualResult = "Could not clicked on Disable device button";
				 }
			 }
			 else
			 {
				 strActualResult = "Could not clicked on disable button";
			 }
		 }
		 else
		 {
			 strActualResult = "Could not clicked on connect arrow button";
		 } 
		
		 reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		 
		 strStepDescription = "Verify_that_Enable_functions_should_not_fail_when_performed_from_multifunction_dropdown_list";
		 strExpectedResult = "Enable_functions_should_not_fail_when_performed_from_multifunction_dropdown_list on device index page."; 

		 waitForPageLoaded();
		 isEventSuccessful =  PerformAction("btnConnectArrow_Devicespage", Action.Click);
		 if(isEventSuccessful)
		 {
			 waitForPageLoaded();
			 isEventSuccessful =  PerformAction("btnEnableConnectArrow_Devicespage", Action.Click);
			 if(isEventSuccessful)
			 {
				 waitForPageLoaded();
				 isEventSuccessful = PerformAction("//button[text()='Enable device']", Action.Click);
				 if(isEventSuccessful)
				 {
					 waitForPageLoaded();
					 isEventSuccessful = PerformAction("(//td[@class='status-row'])[1]//span[contains(@class,'available status-icon')]", Action.isDisplayed); 
					 
					 if(isEventSuccessful)
					 {
						 strActualResult = "Device is Enabled successfully.."; 
					 }
					 else
					 {
						 strActualResult = "Device is not Enabled successfully..";
					 }
					 								 
				 }
				 else
				 {
					 strActualResult = "Could not clicked on enable device button";
				 }
			 }
			 else
			 {
				 strActualResult = "Could not clicked on Enable button";
			 }
		 }
		 else
		 {
			 strActualResult = "Could not clicked on connect arrow button";
		 }
		 
		 reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		 
		 isEventSuccessful = selectStatus("Offline");
		 
		 strStepDescription = "Verify_that_Remove_functions_should_not_fail_when_performed_from_multifunction_dropdown_list";
		 strExpectedResult = "Remove_functions_should_not_fail_when_performed_from_multifunction_dropdown_list on device index page."; 

		 waitForPageLoaded();
		 isEventSuccessful =  PerformAction("btnConnectArrow_Devicespage", Action.Click);
		 if(isEventSuccessful)
		 {
			 waitForPageLoaded();
			 isEventSuccessful =  PerformAction("btnRemoveConnectArrow_Devicespage", Action.Click);
			 if(isEventSuccessful)
			 {
				 waitForPageLoaded();
				 isEventSuccessful = PerformAction("//button[text()='Remove device']", Action.Click);
				// isEventSuccessful = PerformAction("(//td[@class='status-row'])[1]//span[contains(@class,'available status-icon')]", Action.isDisplayed); 
					 
					 if(isEventSuccessful)
					 {
						 strActualResult = "Device is removed successfully.."; 
					 }
					 else
					 {
						 strActualResult = "Device is not removed successfully..";
					 }
					 								 
				 
			 }
			 else
			 {
				 strActualResult = "Could not clicked on Removed button";
			 }
		 }
		 else
		 {
			 strActualResult = "Could not clicked on connect arrow button";
		 }
		 
		 reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		 
		 isEventSuccessful = selectStatus("Available"); 
		 
		 if (isEventSuccessful)
			{	
			   values = ExecuteCLICommand("run", "Android");
		     	  isEventSuccessful = (boolean)values[2];
		     	 devicename= (String)values[3];
				 if (isEventSuccessful)
					{
					   strActualResult = "Viewer launched after connecting to an Android device:  " + values[0] + " & processfound : " +  values[1];
					}
					else
					{
						strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
					}
				
			}
			else
			{
				return; // Return if in use is not selected.
			}
			reporter.ReportStep("Connect to an android device and verify deviceViewer is launched with" + dicOutput.get("executedCommand"), "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
			


			//**************************************************************************//
			// Step : Select status 'In Use'
			//**************************************************************************//
			isEventSuccessful = selectStatus("In Use");
			
			strStepDescription = "Verify_that_Reboot_functions_should_not_fail_when_performed_from_multifunction_dropdown_list";
			 strExpectedResult = "Reboot_functions_should_not_fail_when_performed_from_multifunction_dropdown_list on device index page.";
			 waitForPageLoaded();
			 isEventSuccessful =  PerformAction("btnConnectArrow_Devicespage", Action.Click);
			 if(isEventSuccessful)
			 {
				 waitForPageLoaded();
				 isEventSuccessful =  PerformAction("btnReleaseConnectArrow_Devicespage", Action.Click);
				 if(isEventSuccessful)
				 {
					 waitForPageLoaded();
					 isEventSuccessful = PerformAction("//button[text()='Release device']", Action.Click);
					 if(isEventSuccessful)
					 {
						 waitForPageLoaded();
						 isEventSuccessful = PerformAction("(//td[@class='status-row'])[1]//div[@class='status-description-row' and text() = 'Available']", Action.WaitForElement); 
						 isEventSuccessful = PerformAction("(//td[@class='status-row'])[1]//div[@class='status-description-row' and text() = 'Available']", Action.isDisplayed); 
						 if(isEventSuccessful)
						 {
							 strActualResult = "Device is released successfully.."; 
						 }
						 else
						 {
							 strActualResult = "Device is not released successfully..";
						 }
						 
						 
						 
					 }
					 else
					 {
						 strActualResult = "Could not clicked on Reboot device button";
					 }
				 }
				 else
				 {
					 strActualResult = "Could not clicked on Reboot button";
				 }
			 }
			 else
			 {
				 strActualResult = "Could not clicked on connect arrow button";
			 }
			 
			 reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			 
			//**************************************************************************//
			// Step : Release device.
			//**************************************************************************//
			ExecuteCLICommand("release", "Android", "", "", devicename, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
  }
}