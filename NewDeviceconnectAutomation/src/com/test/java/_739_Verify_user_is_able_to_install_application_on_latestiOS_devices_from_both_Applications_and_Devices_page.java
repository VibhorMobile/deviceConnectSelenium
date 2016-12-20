
package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1128
 */
public class _739_Verify_user_is_able_to_install_application_on_latestiOS_devices_from_both_Applications_and_Devices_page extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String installedApps = "", deviceName="", deviceId="";
	String serverIP=dicCommon.get("ApplicationURL");
	String appName="PhoneLookup"; 

	Object[] values = new Object[5]; 
	Object[] appvalues = new Object[5];

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
        isEventSuccessful = selectPlatform("iOS");
        isEventSuccessful = selectStatus("Available");
        
        //Getting latest iOS device available on the server
        
        isEventSuccessful = VerifySorting(dicOR.get("table_devicespage"),"OS", "descending");
    	if (isEventSuccessful)
    	{
    		strActualResult = "OS Column is sorted in asc order on Applications page.";
    	}
    	else
    	{
    		strActualResult = strErrMsg_AppLib;
    	}
    	reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
        
        String OSFirstValue=GetTextOrValue(dicOR.get("eleFirstDeviceOSValue_DeviceListPage"),"text");
        
        
        //Selecting first device
        values=GoTofirstDeviceDetailsPage(); 
        
        isEventSuccessful=(boolean) values[0];
        deviceName=(String) values[1];
        deviceId=getAppGUID();
        
        installedApps=getInstalledApps();
        if (installedApps.contains(appName))  // If PhoneLookup app is already installed, un-installing it
        {
        	 isEventSuccessful=PerformAction(dicOR.get("btnUninstall_AnyApp_InstalledAppList_DeviceDetails").replace("__APPNAME__", appName),Action.Click);

         	PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.WaitForElement);
         	PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.Click);
         	
             isEventSuccessful=false;
             strActualResult="Uninstalled application does not gets removed automatically from installed app list.";
             for(int iwaitcounter=0; iwaitcounter<=10; iwaitcounter++) 
             {
               Thread.sleep(1000);    
               installedApps=getInstalledApps();
               if (!installedApps.contains(appName))
               {
             	 strActualResult="Uninstalled application removed from installed app list as expected.";
                 isEventSuccessful=true;
                 break;
               }              
             }
             
             reporter.ReportStep("Pre-condition : Uninstall PhoneLookup.", "Page refreshes automatically and changes are reflected.", strActualResult, isEventSuccessful);
            
        }
       
        //Verify install from device details page
        isEventSuccessful=PerformAction(dicOR.get("btnInstall_AnyApp_AvailableAppList_DeviceDetails").replace("__APPNAME__", appName),Action.Click);
        waitForPageLoaded();
        PerformAction("browser",Action.Refresh);
        waitForPageLoaded();
        PerformAction("browser",Action.WaitForPageToLoad);
        if(isEventSuccessful)
        {
        	installedApps=getInstalledApps();
            if (installedApps.contains(appName))
            {
          	 strActualResult="Installed successful from Device details page. Device OS Version -"+OSFirstValue;
              isEventSuccessful=true;
            }
            else
            {
            	strActualResult="Unable to install application from device details page. Device OS Version -"+OSFirstValue;
            	isEventSuccessful=false;
            }
        }
        reporter.ReportStep("Install an application from Device details page.", "App successfully installed from Device details page.", strActualResult, isEventSuccessful);
       
        if (installedApps.contains(appName))  // If PhoneLookup app is already installed, un-installing it
        {
        	 isEventSuccessful=PerformAction(dicOR.get("btnUninstall_AnyApp_InstalledAppList_DeviceDetails").replace("__APPNAME__", appName),Action.Click);

         	PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.WaitForElement);
         	PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.Click);
         	
             isEventSuccessful=false;
             strActualResult="Uninstalled application does not gets removed automatically from installed app list.";
             for(int iwaitcounter=0; iwaitcounter<=10; iwaitcounter++) 
             {
               Thread.sleep(1000);    
               installedApps=getInstalledApps();
               if (!installedApps.contains(appName))
               {
             	 strActualResult="Uninstalled application removed from installed app list as expected.";
                 isEventSuccessful=true;
                 break;
               }              
             }
             
             reporter.ReportStep("Pre-condition : Uninstall PhoneLookup.", "Page refreshes automatically and changes are reflected.", strActualResult, isEventSuccessful);
            
        }
        
        isEventSuccessful=GoToApplicationsPage();
        
        isEventSuccessful=selectPlatform_Application("iOS");
        
        if (isEventSuccessful)
        {
        
	        isEventSuccessful=searchDevice_DI(appName);
	        
	        
	        appvalues=GoToFirstAppDetailsPage();
	        isEventSuccessful=(boolean) appvalues[0];
	        
	        strStepDescription= "Installing application from app details page.";
	        strExpectedResult="Successfully completed installation process from app details page.";
	        
	        if (isEventSuccessful)
	        {
	        	isEventSuccessful=PerformAction(dicOR.get("btnInstall_AppDetailsPage"), Action.Click);
	        	
	        	if (isEventSuccessful)
	        	{
	        		isEventSuccessful=PerformAction("browser", "waitforpagetoload");
	        		isEventSuccessful=PerformAction(dicOR.get("radBtn_Yes_DeviceName_InstallAppPopUp_AppDetailsPage").replace("__DEVICENAME__", deviceName), Action.Exist);
	        		
	        		if(isEventSuccessful)
	        		{
	        			isEventSuccessful=PerformAction(dicOR.get("radBtn_Yes_DeviceName_InstallAppPopUp_AppDetailsPage").replace("__DEVICENAME__", deviceName), Action.Click);
	        			isEventSuccessful=PerformAction(dicOR.get("radBtn_Yes_DeviceName_InstallAppPopUp_AppDetailsPage").replace("__DEVICENAME__", deviceName), Action.isSelected);
	        			if (isEventSuccessful)
	        			{
	        				isEventSuccessful=PerformAction(dicOR.get("ContinuebtnInstallDialog"), Action.WaitForElement);
	        				isEventSuccessful=PerformAction(dicOR.get("ContinuebtnInstallDialog"), Action.Click);
	        				 for(int iwaitcounter=0; iwaitcounter<=10; iwaitcounter++) 
	        	             {
	        	               Thread.sleep(1000);    
	        	               if (!PerformAction(dicOR.get("eleLoadingPane"), Action.Exist))   
	        	            	   break;
	        	             }
	        				 isEventSuccessful=PerformAction(dicOR.get("finishbtninstallDialog"), Action.WaitForElement);
	        				 isEventSuccessful=PerformAction(dicOR.get("finishbtninstallDialog"), Action.Click);
	        				 if (isEventSuccessful)
	        					 strActualResult="Application installation complete from Application details page.";
	        				 else
	        					 strActualResult="Unable to complete installation from Application details page.";
	        			}
	        			else
	        				strActualResult="Unable to select Yes radio button against Phone - "+deviceName;
	        		}
	        		else
	        			strActualResult="Yes radio button does not exist against Phone - "+deviceName;
	        	}
	        	else
	        		strActualResult="Unable to click on Install button on PhoneLookup (iOS) app details page.";
	        }
	        else
	        	strActualResult="Unable to navigate to PhoneLookup (iOS) app details page.";
	        
	        reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	        
	        isEventSuccessful=PerformAction("browser","navigate","http://"+serverIP+"/#/Device/Detail/" + deviceId);
			if(isEventSuccessful)
			{
				strActualResult= "Navigate to Device Details page of the Device using GUID. ID= " + deviceId +" server used= " +serverIP;
			}
			else
			{
				strActualResult= "PerformAction---Navigate to URL. ID= " + deviceId +" server used= " +serverIP+ strErrMsg_GenLib;
			}
			reporter.ReportStep("Navigates to Device details page", "Navigates successfully", strActualResult, isEventSuccessful);
	       
			isEventSuccessful=false;
	        strActualResult="Unable to install application from application details page. Device OS Version -"+OSFirstValue;
	        for(int iwaitcounter=0; iwaitcounter<=4; iwaitcounter++) 
	        {
	          Thread.sleep(1000);    
	          installedApps=getInstalledApps();
	          if (installedApps.contains(appName))
	          {
	        	 strActualResult="Installed successful from application details page. Device OS Version -"+OSFirstValue;
	            isEventSuccessful=true;
	            break;
	          }              
	        }
	        reporter.ReportStep("Verify app installed on device details page.", "application available in installed app list.", strActualResult, isEventSuccessful);
			
        }
        else
        	reporter.ReportStep("Select iOS Platform filter on Applications list page.", "iOS filter selected successfully.", "Unable to select iOS filter.", false);
	}
}