package com.test.java;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1028
 */
public class _678_Verify_no_import_error_thrown_while_importing_csv_file_having_invalid_user_roles extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",email="";
	private int counter=0;

	public final void testScript() throws IOException
	{
		/*if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
		{
			return;
		}*/
		
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();
		
		//**************************************************************************//
		// Step 2 : Update csv
		//**************************************************************************//
		strstepDescription = "Verify csv file updated";
		strexpectedResult = "csv file should be updated";
		email="Check"+RandomStringUtils.randomAlphabetic(12)+"@ml.com";
		isEventSuccessful=updateCSV(dicConfig.get("Artifacts")+"\\userImportInvalidRole.csv",email,1,0);
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
		//*** Step 5 : Upload user list*****//
		//******************************************************************//
		strstepDescription = "Verify user is able to upload user list";
		strexpectedResult =  "User should be able to upload user list";
		isEventSuccessful=PerformAction("uploaduserlistbtn",Action.Click);
		if(isEventSuccessful)
		{
			isEventSuccessful=PerformAction("uploaduserlistbtn",Action.UploadUserList,"userImportInvalidRole.csv");
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
		// Step 6 : Verify users list imported successfully.
		//**************************************************************************//
		strstepDescription = "Verify users list imported successfully.";
		strexpectedResult = "Users list should be imported successfully.";
		isEventSuccessful=PerformAction("userImportResult",Action.isNotDisplayed);
		if(isEventSuccessful)
		{
			strActualResult = "User list imported without error.";
		}
		else
		{
			strActualResult = "Unable to import users.";
		}
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);
		
		//**************************************************************************//
		// Step 6 : Logout.
		//**************************************************************************//
		PerformAction("browser",Action.Refresh);
		Logout();

		//**************************************************************************//
		// Step 7 : Login with new user credentials.
		//**************************************************************************//
		strstepDescription="Login with new user created.";
		strexpectedResult="User should be loogged in.";
		isEventSuccessful = LoginToDC(email, "deviceconnect");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with " + email, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
	}	
}