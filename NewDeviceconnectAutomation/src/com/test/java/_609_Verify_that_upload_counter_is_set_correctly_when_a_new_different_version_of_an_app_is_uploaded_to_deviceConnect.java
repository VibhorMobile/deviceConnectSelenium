package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.thoughtworks.selenium.webdriven.commands.GetExpression;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-55, QA-147
 */
public class _609_Verify_that_upload_counter_is_set_correctly_when_a_new_different_version_of_an_app_is_uploaded_to_deviceConnect extends ScriptFuncLibrary

{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	Object[] Values = new Object[5]; 
	private String strActualResult = "", devicename = "",EmailAddress, Password;
	int counter , counter1;
	
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
		strStepDescription = "Click on upload Application and upload application";
		strExpectedResult =  "Application should be uploaded";
		if(isEventSuccessful)
		{
			isEventSuccessful=uploadApplication("Flipkart.apk");
			if(isEventSuccessful)
			{
				PerformAction("browser", Action.WaitForPageToLoad);
				searchDevice_DI("Flipkart");
				waitForPageLoaded();
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
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 4 : Check counter increment for app uploaded*****//
		//******************************************************************//
		strStepDescription = "On uploading Application counter increases";
		strExpectedResult =  "App counter should increasing on uploading same app again";
		isEventSuccessful=PerformAction("applicationCounterApplicationPage",Action.isDisplayed);
		if(isEventSuccessful)
		{
			try
			{
				
				 counter = Integer.parseInt(GetTextOrValue("applicationCounterApplicationPage", "text"));
			}
			catch(Exception e)
			{
				isEventSuccessful=false;
			}
		}
		if(isEventSuccessful)
		{
			isEventSuccessful=uploadApplication("Flipkart-2.apk");
			if(isEventSuccessful)
			{
				PerformAction("browser", Action.WaitForPageToLoad);
				counter1 = Integer.parseInt(GetTextOrValue("applicationCounterApplicationPage", "text"));
				if(counter1>counter )
				{
					strActualResult = "App counter incresing on uploading same application" + "before : " + " " + counter + "after:" + " " + counter1 ;
				}
			}
			else
			{
				strActualResult = "App did not uploaded";
			}
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	
	}
}
