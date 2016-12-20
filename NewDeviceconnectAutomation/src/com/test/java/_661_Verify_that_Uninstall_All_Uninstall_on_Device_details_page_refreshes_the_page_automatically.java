package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1740
 */
public class _661_Verify_that_Uninstall_All_Uninstall_on_Device_details_page_refreshes_the_page_automatically extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="",expectedText="Usage: MobileLabs.DeviceConnect.Cli <host> <username> <password> [options]";
	private String installedApps = "";
	Object[] values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login();


		//*************************************************************//                     
		// Step 2 : Navigate to first available device details page
		//*************************************************************//  
		isEventSuccessful =GoToDevicesPage();  //Navigates to Devices page
        
        //Applying filters
        isEventSuccessful = selectPlatform("Android,iOS");
        isEventSuccessful = selectStatus("Available");
        
        //Selecting first device
        values=GoTofirstDeviceDetailsPage(); 
        

		//*************************************************************//                     
		// Step 3 : Click on uninstall all button to uninstall all applications
		//*************************************************************//  
 
        installedApps=getInstalledApps();

        if (!installedApps.contains("Trust Browser"))  // If Trust Browser app not already installed, installing it
        {
            isEventSuccessful=PerformAction(dicOR.get("btnInstall_TrustBrowser_AvailableAppList_DeviceDetails"),Action.ClickUsingJS);
            Thread.sleep(20000);
            PerformAction("browser", Action.Refresh);
        }
      //table[@class='table data-grid installed-apps-table']/tbody/tr/td[@title='Trust Browser']/../td[@class='btn-column']/button[contains(text(),'Uninstall')]
        
        installedApps=getInstalledApps();
        System.out.println(installedApps);
        
        
        isEventSuccessful=PerformAction(dicOR.get("btnUninstall_TrustBrowser_InstalledAppList_DeviceDetails"),Action.Click);
        waitForPageLoaded();
    	PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.WaitForElement);
    	PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.Click);
    	
        isEventSuccessful=false;
        strActualResult="Uninstalled application does not gets removed automatically from installed app list.";
        for(int iwaitcounter=0; iwaitcounter<=10; iwaitcounter++) 
        {
          Thread.sleep(4000);    
          installedApps=getInstalledApps();
          if (!installedApps.contains("Trust Browser"))
          {
        	 strActualResult="Uninstalled application removed from installed app list as expected.";
            isEventSuccessful=true;
            break;
          }              
        }
        
        reporter.ReportStep("Uninstalling an application removed it from installed app list automatically.", "Page refreshes automatically and changes are reflected.", strActualResult, isEventSuccessful);
       
        
        if (installedApps.contains("No applications installed"))  // If Trust Browser app not already installed, installing it
        {
            PerformAction(dicOR.get("btnInstall_TrustBrowser_AvailableAppList_DeviceDetails"),Action.Click);
            Thread.sleep(20000);
            PerformAction("browser", Action.Refresh);
        }
        
        installedApps=getInstalledApps();
        System.out.println(installedApps);
        
    	PerformAction(dicOR.get("BtnUninstallAllEnabled"), Action.Click);
    	waitForPageLoaded();
    	PerformAction("browser","waitforpagetoload");
    	
    	isEventSuccessful=PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.WaitForElement);
    	if (isEventSuccessful)
    	{
    		
    		reporter.ReportStep("Verify Continue button is available on Uninstall All Applications pop-up.", "Continue button should be available.", "Continue button is available.", isEventSuccessful);
    		
    		//*********************Verify Cancel button is displayed********************
    		boolean isCancelButtonAppeared=PerformAction(dicOR.get("btnCancel"), Action.isDisplayed);
    		if (isCancelButtonAppeared)
    			reporter.ReportStep("Cancel button should be displayed.", "Cancel button is displayed.", "Cancel button is available." , isCancelButtonAppeared);
    		else
    			reporter.ReportStep("Cancel button should be displayed.", "Cancel button is displayed.", "Cancel button is not available." , isCancelButtonAppeared);
    		//***********************************************************************************************
    		
    		
    		isEventSuccessful=PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.Click);
        	waitForPageLoaded();
        	PerformAction("browser",Action.WaitForPageToLoad);
    		
        	isEventSuccessful=PerformAction(dicOR.get("finishbtninstallDialog"), Action.WaitForElement);
        	if (isEventSuccessful)
        	{
        		reporter.ReportStep("Verify Finish button is available on Uninstall All Applications pop-up.", "Finish button should be available.", "Finish button is available.", isEventSuccessful);
        		
        		//*********************Verify Continue button disappears when finish button appears********************
        		boolean isContinueButtonDisappeared=PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.isNotDisplayed);
        		if (isContinueButtonDisappeared)
        			reporter.ReportStep("Continue button should disappear when finish button is displayed.", "Continue button disappears.", "Continue button dispappeared." , isContinueButtonDisappeared);
        		else
        			reporter.ReportStep("Continue button should disappear when finish button is displayed.", "Continue button disappears.", "Continue button is available." , isContinueButtonDisappeared);
        		//***********************************************************************************************
        		
        		//*********************Verify Cancel button disappears when finish button appears********************
        		boolean isCancelButtonDisappeared=PerformAction(dicOR.get("btnCancel"), Action.isNotDisplayed);
        		if (isCancelButtonDisappeared)
        			reporter.ReportStep("Cancel button should disappear when finish button is displayed.", "Cancel button disappears.", "Cancel button dispappeared." , isCancelButtonDisappeared);
        		else
        			reporter.ReportStep("Cancel button should disappear when finish button is displayed.", "Cancel button disappears.", "Cancel button is available." , isCancelButtonDisappeared);
        		//***********************************************************************************************
        		
        		PerformAction(dicOR.get("finishbtninstallDialog"), Action.Click);
            	
            	PerformAction("browser","waitforpagetoload");
            	
            	isEventSuccessful=false;
                strActualResult="Uninstalling all application does not gets reflected automaticall. from installed app list.";
                for(int iwaitcounter=0; iwaitcounter<=5; iwaitcounter++) 
                {
                  Thread.sleep(1000);    
                  installedApps=getInstalledApps();
                  if (installedApps.contains("No applications installed"))
                  {
                	 strActualResult="Uninstalling all applications get reflected automatically as expected.";
                	 isEventSuccessful=true;
                	 break;
                  }              
                }
            	   	
                reporter.ReportStep("Uninstalling all applications get reflected in installed app list automatically.", "Page refreshes automatically and changes are reflected.", strActualResult, isEventSuccessful);
        	}
        	else
        		reporter.ReportStep("Verify Finish button is available on Uninstall All Applications pop-up.", "Finish button should be available.", "Finish button is not available.", isEventSuccessful);
    	}
    	else
    		reporter.ReportStep("Verify Continue button is available on Uninstall All Applications pop-up.", "Continue button should be available.", "Continue button is not available.", isEventSuccessful);
    		
    	      
		
	}
}