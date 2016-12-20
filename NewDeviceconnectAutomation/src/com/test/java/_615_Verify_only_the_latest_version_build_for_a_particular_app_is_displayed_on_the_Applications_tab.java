package com.test.java;

import java.util.ArrayList;
import java.util.List;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-977
 */
public class _615_Verify_only_the_latest_version_build_for_a_particular_app_is_displayed_on_the_Applications_tab extends ScriptFuncLibrary {
	
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	Object[] values = new Object[2];
	List<String> l = new ArrayList<String>();
	private String strActualResult = "", devicename = "",EmailAddress, Password , version , selectedApp;
	Object[] appdetailsvalues = new Object[10];
	
	public final void testScript()
	{
		
		// Step 1 - Login to deviceConnect//
		isEventSuccessful = Login();
				   
		// Step 2		   
		isEventSuccessful = GoToApplicationsPage();
		
		// Step 3
		values  =  GoToFirstAppDetailsPage();
		isEventSuccessful =  (boolean) values[0] ;
		selectedApp =  (String) values[1];
				   
		// Step 4 : Verify application information on application details page.
		strStepDescription = "Verify Application Name, OS and File name fields on application details page. ";
		strExpectedResult = "Application Name, OS and File name fields should be displayed on application details page.";
		appdetailsvalues = VerifyAppDetailsOnApplicationPage("Version");
		version = (String) appdetailsvalues[6];
		isEventSuccessful = (boolean) appdetailsvalues[9];
		if (isEventSuccessful)
		{
			strActualResult = "Application Name, OS and File name fields are displayed :" + version + " ;on application details page.";
		}
		else
		{
			strErrMsg_AppLib = (String) appdetailsvalues[8];
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		// Step 5   
		isEventSuccessful = GoToApplicationsPage();   
				   
		String latestversion =   GetTextOrValue("//table[contains(@class,'applicationList table')]//tr[1]/td[3]", "text") ;
			
		// Step 6
		strStepDescription = "Verify_only_the_latest_version_build_for_a_particular_app_is_displayed_on_the_Applications_tab";	   
		strExpectedResult = "Only_the_latest_version_build_for_a_particular_app_should be _displayed_on_the_Applications_tab";		   
		if(version.contains(latestversion))
		{
			strActualResult = "Only_the_latest_version_build_for_a_particular_app_is_displayed_on_the_Applications_tab";
			isEventSuccessful = true;
		}
		
		else
		{
			strActualResult = "Only_the_latest_version_build_for_a_particular_app_is not_displayed_on_the_Applications_tab";
			isEventSuccessful = false;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);	   

	}		   
}
