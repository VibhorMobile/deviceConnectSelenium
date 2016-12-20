package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: Second week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-414
 */
public class _618_Verify_that_uploading_device_control_and_trust_browser_apks_should_not_fail_with_error extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ,devicename = "",EmailAddress, Password,Version ;
	private String strexpectedResult = "" , strActualResult ;
	String AppNameLink = "",version, build, build1 , version1;
	Object[] appdetailsvalues = new Object[10];
	Object[] Values = new Object[5];
	
	
	public final void testScript() 
	 
	{
		// Step 1 : login to deviceConnect with valid user credentials.
		  isEventSuccessful = Login();

		// Step 2 : Navigate to Application page.
		  isEventSuccessful = GoToApplicationsPage();
		  

		//Step 3 : Click on upload and upload the 'deviceControl.apk'.
		  strStepDescription = "Click on upload Application button and upload the application on Applications page.";
		  strExpectedResult =  "Application should be uploaded";
		  isEventSuccessful =   uploadApplication("deviceControl.apk");
        isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
        
			  isEventSuccessful = searchDevice_DI("deviceControl");
				isEventSuccessful = !VerifyMessage_On_Filter_Selection();
				if(isEventSuccessful)
				{
					strActualResult = "App uploaded successfully";
				}
				else
				{
					strActualResult = "App did not uploaded";
				}
			
		  reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		  


			//Step 4 : Click on upload and upload the 'TrustBrowser.apk'.
			  strStepDescription = "Click on upload Application button and upload the application on Applications page.";
			  strExpectedResult =  "Application should be uploaded";
			  isEventSuccessful =   uploadApplication("TrustBrowser.apk");
			 
				  isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
				  isEventSuccessful = searchDevice_DI("TrustBrowser");
					isEventSuccessful = !VerifyMessage_On_Filter_Selection();
					if(isEventSuccessful)
					{
						strActualResult = "App uploaded successfully";
					}
					else
					{
						strActualResult = "App did not uploaded";
					}
				
			  reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

	}
}
