package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: Last week of January
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-651
 */
public class _575_Verify_that_application_name_link_on_Main_applications_page_navigates_to_application_information_page_for_Android_applications extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	private String selectedApp = null,AppName,OS,FileName;
	Object[] values = new Object[2];
	Object[] appdetailsvalues = new Object[10];


	public final void testScript()
	{

		// Step 1 : login to deviceConnect with valid user credentials.
		isEventSuccessful = Login();
		 
		// Step 2 : Navigate to Application page.
		isEventSuccessful = GoToApplicationsPage();
		
		//Step 3 : Go to first app details page.
		values  =  GoToFirstAppDetailsPage();
		isEventSuccessful =  (boolean) values[0] ;
		selectedApp =  (String) values[1];
		
		// Step 4 : Verify application information on application details page.
		strStepDescription = "Verify Application Name, OS and File name fields on application details page. ";
		strExpectedResult = "Application Name, OS and File name fields should be displayed on application details page.";
		appdetailsvalues = VerifyAppDetailsOnApplicationPage("Application Name,OS,File name");
		AppName = (String) appdetailsvalues[0];
		OS = (String) appdetailsvalues[2];
		FileName = (String) appdetailsvalues[4];
		isEventSuccessful = (boolean) appdetailsvalues[1];
		if (isEventSuccessful)
		{
			strActualResult = "Application Name, OS and File name fields are displayed :" + AppName + "\r\n" +  OS + "\r\n" +  FileName + "\r\n" +  " ;on application details page.";
		}
		else
		{
			strErrMsg_AppLib = (String) appdetailsvalues[8];
			strActualResult = strErrMsg_AppLib;
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

	}	
}
