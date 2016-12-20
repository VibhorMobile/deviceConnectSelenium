package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-1159
 */
public class _597_Verify_that_only_a_valid_ipa_or_apk_file_is_allowed_to_be_uploaded_to_Applications_Tab extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ;
	private String strexpectedResult = "" , strActualResult ;
	
	public final void testScript() 
	 
	{
		// Step 1 : login to deviceConnect with valid user credentials.
		isEventSuccessful = Login();
		 
		// Step 2 : Navigate to Application page.
		isEventSuccessful = GoToApplicationsPage();
		
		// Step 3 : Click on upload and upload the 'Dollar General 2.1.ipa'.
		strStepDescription = "Click on upload Application button and upload the invalid application on Applications page.";
		strExpectedResult =  "Application should not be uploaded";
		isEventSuccessful =   uploadApplication("user-export.csv");
		if(isEventSuccessful)
		{
			strActualResult = GetTextOrValue("Errorhandling_everyPAge", "text");
			
		}
		else
		{
			strActualResult = "App uploaded";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		  
	    // Step 4
		strStepDescription = "Click on upload Application button and upload the application on Applications page.";
		strExpectedResult =  "Application should be uploaded";
		isEventSuccessful =   uploadApplication("Flipkart.apk");
		if(isEventSuccessful)
		{
			PerformAction("browser", Action.WaitForPageToLoad);
			isEventSuccessful = searchDevice_DI("Flipkart");
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
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

	}
	
}
