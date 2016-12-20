package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1739
 */
public class _718_Verify_that_Last_uploaded_application_in_dC_is_reflected_in_main_Applications_page extends ScriptFuncLibrary
{

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false, isExist=false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	
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
		// Step 2 : Navigate to Application list page
		//*************************************************************//  
		isEventSuccessful =GoToApplicationsPage();  
		
		
		//*************************************************************//                     
		// Step 3 : Verify no instance of Aldiko app available on server. if Available, delete it.
		//*************************************************************//  
		strStepDescription = "Pre-condition: Delete Aldiko Application is already exists on the server.";
		strExpectedResult = "If available deleted else do Nothing";
		
		searchDevice_DI("Aldiko");
		waitForPageLoaded();
		isEventSuccessful=PerformAction(dicOR.get("NoAppMatchFilter_AppListPage"), Action.Exist);
		
		if (!isEventSuccessful)
		{
			String index="1";
			isEventSuccessful =  PerformAction(dicOR.get("btnMultiFunctionDropdown_AppListPage").replace("__INDEX__", index), Action.Click);
			 if(isEventSuccessful)
			 {
				 waitForPageLoaded();
				 isEventSuccessful =  PerformAction(dicOR.get("btnDelete_Multifunction_AppList").replace("__INDEX__", index), Action.Click);
				 if(isEventSuccessful)
				 {
					 waitForPageLoaded();
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
		
		
		strstepDescription = "Click on upload Application and upload application";
		strexpectedResult =  "Application should be uploaded";
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful=uploadApplication();
			if(isEventSuccessful)
			{
				waitForPageLoaded();
				PerformAction("browser",Action.WaitForPageToLoad);
				searchDevice_DI("Aldiko");
				waitForPageLoaded();
				isEventSuccessful = GetTextOrValue(dicOR.get("eleAppNameAppTable").replace("__APP_INDEX__", "1"), "text").equals("Aldiko");
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
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		
	}
}