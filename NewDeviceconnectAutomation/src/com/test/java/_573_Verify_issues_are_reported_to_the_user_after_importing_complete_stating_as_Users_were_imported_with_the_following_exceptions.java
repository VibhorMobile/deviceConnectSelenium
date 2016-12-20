package com.test.java;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: 16-Feb-2016
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-543
 */

public class _573_Verify_issues_are_reported_to_the_user_after_importing_complete_stating_as_Users_were_imported_with_the_following_exceptions extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",deviceName="", email="";
	private String xpath = "";
	Object[] Values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		/*if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
		{
			return;
		}*/
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(EmailAddress, Password);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with " + EmailAddress, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
		
		//**************************************************************************//
		// Step 2 : Update csv
		//**************************************************************************//
		strstepDescription = "Verify csv file updated";
		strexpectedResult = "csv file should be updated";
		email=RandomStringUtils.randomAlphabetic(12)+"@ml.com";
		isEventSuccessful=updateCSVForImporting2User(dicConfig.get("Artifacts")+"\\userImport_Error.csv",email,1,0);
		if(isEventSuccessful)
		{
			strActualResult = "csv file updated successfully";
		}
		else
		{
            strActualResult = "Exception occurred";
		}
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);

		//******************************************************************//
		//*** Step 3 : Navigate to Users Page. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToUsersPage();
		}
		else
		{
			return;
		}
		
		//******************************************************************//
		//*** Step 4 : Click on upload Application and upload application*****//
		//******************************************************************//
		strstepDescription = "Click on import user button";
		strexpectedResult =  "Import dialog should be displayed";
		if(isEventSuccessful)
		{ 
			waitForPageLoaded();
			PerformAction("browser","waitforpagetoload");
			isEventSuccessful=PerformAction("ImportUserbtn",Action.Click);
			if(isEventSuccessful)
			{
				waitForPageLoaded();
				isEventSuccessful=PerformAction("uploaduserlistbtn",Action.isDisplayed);
				if(isEventSuccessful)
				{
					strActualResult = "Import dialog box displayed";
				}
				else
				{
					strActualResult = "Upload dialog box did not displayed";
				}
			}
			else
			{
				strActualResult = "Unable to click import user button";
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 5 : Upload user list*****//
		//******************************************************************//
		strstepDescription = "Verify user is able to upload user list";
		strexpectedResult =  "User should be able to upload user list";
		isEventSuccessful=PerformAction("uploaduserlistbtn",Action.Click);
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful=PerformAction("uploaduserlistbtn",Action.UploadUserList,"userImport_Error.csv");
			if(isEventSuccessful)
			{
				strActualResult = "User list imported successfully";
			}
			else
			{
				strActualResult = "Unable to import user list";
			}
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//**************************************************************************//
		// Step 6 : Verify message contains 'Users were imported with the following exceptions'
		//**************************************************************************//
		strstepDescription = "Verify message contains 'Users were imported with the following exceptions'.";
		strexpectedResult = "Message should contains 'Users were imported with the following exceptions'.";
		isEventSuccessful=GetTextOrValue("userImportResult","text").contains("Users were imported with the following exceptions");
		if(isEventSuccessful)
		{
			strActualResult = "Correct message displayed.";
		}
		else
		{
			strActualResult = "Mesage were not correct.";
		}
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);
		PerformAction("btnClose",Action.Click);
		waitForPageLoaded();
		PerformAction("browser",Action.Refresh);
		
	}
}