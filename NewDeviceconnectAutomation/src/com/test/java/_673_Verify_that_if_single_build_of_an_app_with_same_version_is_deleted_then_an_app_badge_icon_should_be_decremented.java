package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-727
 */
public class _673_Verify_that_if_single_build_of_an_app_with_same_version_is_deleted_then_an_app_badge_icon_should_be_decremented extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false, isExist=false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", cmdText = "", deviceName="",expectedMessage="This will delete all uploaded versions of the application from the server. This action can not be undone.";
	private String installedApps = "";
	Object[] values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
				
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login();


		//*************************************************************//                     
		// Step 2 : Navigate to Application list page
		//*************************************************************//  
		isEventSuccessful =GoToApplicationsPage();  
		
		
		strStepDescription = "Pre-condition: Grouped application should be present";
		strExpectedResult = "If not present, upload it.";
		isExist=PerformAction(dicOR.get("eleFirstAppBadgeCountIcon_AppListPage"), Action.Exist);
		if (!isExist)
		{
			
			      
			      for(int count=0; count<=3; count++)
			      {
			    	  	values = uploadAppThroughCLI("com.aldiko.android.apk");
				     	 isEventSuccessful = (boolean)values[2];
				     	 cmdText=(String)values[0];
				     	 deviceName=(String)values[3];
						 if (isEventSuccessful)
						 {
							   isEventSuccessful=true;
							   strActualResult = "Not already present. Applications uploaded successfully";
						 }
						 else
						 {
							 isEventSuccessful=false;
							 strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
							 break;
						 }
						
			      }
			      
		}
		else
		{
			isEventSuccessful=true;
			strActualResult="Grouped Application already exists.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		
		
		isEventSuccessful=PerformAction("browser", Action.Refresh);
		
		
		String index="";
		int iprevCount = 0,iCurrCount=0;
		String strPrev = GetTextOrValue(dicOR.get("eleAppBadgeCountIcon_ParticularApp").replace("__APPTITLE__", "Aldiko"),"text");
		
		iprevCount=Integer.parseInt(strPrev);
		
		
		boolean isExist=false;
		isExist=PerformAction(dicOR.get("eleAppName_AppsPage").replace("__APPNAME__", "Aldiko"), Action.Click);
		
		if (isExist)
		{
			isEventSuccessful=PerformAction(dicOR.get("eleAppName_AppsPage").replace("__APPNAME__", "Aldiko"), Action.Click);
		
		
			strStepDescription = "Delete 1 instance of the aldiko app.";
			strExpectedResult = "1 instance deleted successfully";
				
				isEventSuccessful =  PerformAction(dicOR.get("btnMultiFunctionDropdown_AppDetailsPage").replace("__INDEX__", "1"), Action.Click);
				 if(isEventSuccessful)
				 {
					 isEventSuccessful =  PerformAction(dicOR.get("btnDelete_Multifunction_AppDetailsPage").replace("__INDEX__", "1"), Action.Click);
					 if(isEventSuccessful)
					 {
						 
						 isEventSuccessful = PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.Click);
						 if(isEventSuccessful)
						 {
							 strActualResult="Successfully uninstalled 1 instance";
						 }
						 else
						 {
							 strActualResult = "Unable to delete 1 instance of Aldiko App.";
						 }
						 
					 }
					 else
					 {
						 strActualResult = "Could not clicked on Delete button in multi function dropdown";
					 }
				 }
				 else
				 {
					 strActualResult = "Could not clicked on multi funtion dropdown arrow button on Application List page";
				 }
				 
				 reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
				 isEventSuccessful=GoToApplicationsPage();
				 
				 
				 String strCurr = GetTextOrValue(dicOR.get("eleAppBadgeCountIcon_ParticularApp").replace("__APPTITLE__", "Aldiko"),"text");
					
				 iCurrCount=Integer.parseInt(strCurr);
				 
			
			
			strStepDescription = "Verify app badge icon should be decremented by 1";
			strExpectedResult = "Badge icon decremented by 1";
			if (iCurrCount==(iprevCount-1))
			{
				isEventSuccessful=true;
				strActualResult="App badge decremented by 1 previousValue="+iprevCount +" currentValue="+iCurrCount;
			}
			else
			{
				isEventSuccessful=false;
				strActualResult="App badge is not decremented by 1 previousValue="+iprevCount +" currentValue="+iCurrCount;
			}
			
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			
		}
		else
		{
			reporter.ReportStep("Verify Aldiko app is available on app page", "Multiple version are available on the app page", "Aldiko app not available on app list page.", false);
		}
		
		
	}
}