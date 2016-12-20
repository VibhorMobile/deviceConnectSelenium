package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-699
 */
public class _676_Verify_that_if_single_build_of_an_application_is_uploaded_then_no_app_badge_icon_should_display extends ScriptFuncLibrary
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
		
		
		//*************************************************************//                     
		// Step 3 : Verify no instance of Aldiko app available on server. if Available, delete it.
		//*************************************************************//  
		strStepDescription = "Pre-condition: Delete Aldiko Application is already exists on the server.";
		strExpectedResult = "If available deleted else do Nothing";
		
		searchDevice_DI("Aldiko");
		
		isEventSuccessful=PerformAction(dicOR.get("NoAppMatchFilter_AppListPage"), Action.Exist);
		
		if (!isEventSuccessful)
		{
			String index="1";
			isEventSuccessful =  PerformAction(dicOR.get("btnMultiFunctionDropdown_AppListPage").replace("__INDEX__", index), Action.Click);
			 if(isEventSuccessful)
			 {
				 isEventSuccessful =  PerformAction(dicOR.get("btnDelete_Multifunction_AppList").replace("__INDEX__", index), Action.Click);
				 if(isEventSuccessful)
				 {
					 isEventSuccessful = PerformAction(dicOR.get("eleDialogDeleteAll_ApplicationPage").replace("__EXPECTED_HEADER__", "Delete all"), Action.Click);
					 if(isEventSuccessful)
					 {
						 strActualResult="Successfully clicked on Delete all button";
					 }
					 else
					 {
						 strActualResult = "Could not clicked on Delete all button";
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
				 
		}
		else
		{
			isEventSuccessful=true;
			strActualResult="Aldiko app is not available on server : did Nothing";
		}
		 reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		PerformAction("browser", Action.Refresh);
		
		
		strStepDescription = "Pre-condition: Uploading 1 instance of aldiko App";
		strExpectedResult = "Uploaded 1 instance of app.";
		values = uploadAppThroughCLI("com.aldiko.android.apk");
    	 isEventSuccessful = (boolean)values[2];
    	 cmdText=(String)values[0];
    	 deviceName=(String)values[3];
		 if (isEventSuccessful)
		 {
			   isEventSuccessful=true;
			   strActualResult = "Applications uploaded successfully";
		 }
		 else
		 {
			 isEventSuccessful=false;
			 strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
		 }
		 reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		 
		 PerformAction("browser", Action.Refresh);
		 
		 boolean isExist=PerformAction(dicOR.get("eleAppName_AppsPage").replace("__APPNAME__", "Aldiko"), Action.Exist);
		 
		 if (isExist)
		 {
			 strStepDescription = "Verify no app badge icon available for Aldiko app";
				strExpectedResult = "No app badge icon should be present for single instance of aldiko.";
				
				isExist=PerformAction(dicOR.get("eleBadgeIcon_ForAnApp_AppList").replace("__APP_TITLE__", "Aldiko"), Action.Exist);
				if (isExist)
				{
					isEventSuccessful=false;
					strActualResult="Badge icon available for single instance of app.";
					      
				}
				else
				{
					isEventSuccessful=true;
					strActualResult="As expected badge icon is not available for single instance of Aldiko app";
				}
		 }else{
			 strActualResult="Aldiko app is not available";
		 }
		
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		

		
	}
}