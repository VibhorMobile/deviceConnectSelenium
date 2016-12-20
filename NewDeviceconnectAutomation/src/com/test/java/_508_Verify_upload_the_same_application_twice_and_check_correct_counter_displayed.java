package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-570, QA-743
 */

public class _508_Verify_upload_the_same_application_twice_and_check_correct_counter_displayed extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private int counter=0;

	public final void testScript()
	{
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();
		
		//******************************************************************//
		//*** Step 2 : Navigate to Applications Page. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToApplicationsPage();
		}
		else
		{
			return;
		}
		
		//******************************************************************//
		//*** Step 3 : Click on upload Application and upload application*****//
		//******************************************************************//
		strstepDescription = "Click on upload Application and upload application";
		strexpectedResult =  "Application should be uploaded";
		if(isEventSuccessful)
		{
			isEventSuccessful=uploadApplication();
			if(isEventSuccessful)
			{
				PerformAction("browser",Action.WaitForPageToLoad);
				PerformAction("browser",Action.Refresh);
				searchDevice_DI("Aldiko");
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
		
		//******************************************************************//
		//*** Step 4 : Check counter increment for app uploaded*****//
		//******************************************************************//
		strstepDescription = "On uploading Application counter increases";
		strexpectedResult =  "App counter should increasing on uploading same app again";
		isEventSuccessful=PerformAction("applicationCounterApplicationPage",Action.isDisplayed);
		if(isEventSuccessful)
		{
			try
			{
				counter=Integer.parseInt(getText("applicationCounterApplicationPage"));
			}
			catch(Exception e)
			{
				isEventSuccessful=false;
			}
		}
		counter++;
		if(isEventSuccessful)
		{
			isEventSuccessful=uploadApplication();
			if(isEventSuccessful)
			{
				PerformAction("",Action.WaitForElement,"20");
				PerformAction("browser",Action.Refresh);
				if(counter==Integer.parseInt(getText("applicationCounterApplicationPage")))
				{
					strActualResult = "App counter incresing on uploading same application";
				}
				else
				{
					strActualResult = "App counter is not incresing on uploading same application";
					isEventSuccessful=false;
				}
			}
			else
			{
				strActualResult = "App did not uploaded";
			}
		}
		else
		{
			isEventSuccessful=false;
			strActualResult = "Unable to get app counter.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
	}	
}