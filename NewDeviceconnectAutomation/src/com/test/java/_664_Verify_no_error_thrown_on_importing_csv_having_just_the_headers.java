package com.test.java;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: 
 * Last Modified Date: Same as creation date
 * Jira Test Case Id-1332
 */

public class _664_Verify_no_error_thrown_on_importing_csv_having_just_the_headers extends ScriptFuncLibrary
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

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************// 
		/*if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
		{
			return;
		}*/
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
		
		
		//******************************************************************//
		//*** Step 2 : Navigate to Users Page. *****//
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
		//*** Step 3 : Click on upload Application and upload application*****//
		//******************************************************************//
		strstepDescription = "Click on import user button";
		strexpectedResult =  "Import dialog should be displayed";
		if(isEventSuccessful)
		{ 
			PerformAction("browser","waitforpagetoload");
			isEventSuccessful=PerformAction("ImportUserbtn",Action.Click);
			if(isEventSuccessful)
			{
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
		//*** Step 4 : Upload user list*****//
		//******************************************************************//
		strstepDescription = "Verify user is able to upload user list";
		strexpectedResult =  "User should be able to upload user list";
		isEventSuccessful=PerformAction("uploaduserlistbtn",Action.Click);
		if(isEventSuccessful)
		{
			isEventSuccessful=PerformAction("uploaduserlistbtn",Action.UploadUserList,"userImportHeader.csv");
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
		// Step 5 : Verify message contains 'Users were imported with the following exceptions'
		//**************************************************************************//
		strstepDescription = "Verify message contains 'No users were successfully imported'.";
		strexpectedResult = "Message should contains 'No users were successfully imported'.";
		isEventSuccessful=GetTextOrValue("userImportResult","text").contains("No users were successfully imported");
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
		PerformAction("browser",Action.Refresh);
	}
}