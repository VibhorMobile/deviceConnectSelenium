package com.test.java;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-636
 */

public class _497_Verify_Imported_users_must_be_able_to_be_logged_into_successfully extends ScriptFuncLibrary
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
		
		/*if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
		{
			return;
		}*/
		
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

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
		email="Check"+RandomStringUtils.randomAlphabetic(12)+"@ml.com";
		isEventSuccessful=updateCSV(dicConfig.get("Artifacts")+"\\userImport.csv",email,1,0);
		if(isEventSuccessful)
		{
			strActualResult = "csv file updated successfully";
		}
		else
		{
            strActualResult = "Exception occurred";
		}
		reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 3 : Import user.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("importadmin", "iOS", EmailAddress, Password, "", "");
	     	 isEventSuccessful=(Boolean)Values[2];
	     	 deviceName=(String)Values[3];
			 if (isEventSuccessful)
			 {
				 isEventSuccessful=true;
				 strActualResult = "User imported successfully";
			 }
			 else
			 {
				 isEventSuccessful=false;
				 strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			 }
			
		}
		else
		{
			return; // Return if in use is not selected.
		}
		reporter.ReportStep("Verify admin can import users" , "Users should be imported", strActualResult, isEventSuccessful);
		PerformAction("btnRefresh_Devices", Action.Click);
		waitForPageLoaded();
		//**************************************************************************//
		// Step 4 : Logout.
		//**************************************************************************//
		Logout();
		
		//**************************************************************************//
		// Step 5 : Login with new user credentials.
		//**************************************************************************//
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