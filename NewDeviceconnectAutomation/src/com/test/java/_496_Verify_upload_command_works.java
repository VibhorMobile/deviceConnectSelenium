package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1549
 */

public class _496_Verify_upload_command_works extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="", cmdText="";
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
		// Step 2 : Connect to an Android device and upload apk app.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("uploadadmin", "Android", EmailAddress, Password, "", "");
	     	 isEventSuccessful = (boolean)Values[2];
	     	 cmdText=(String)Values[0];
	     	 deviceName=(String)Values[3];
			 if (isEventSuccessful)
			 {
				   isEventSuccessful=true;
				   strActualResult = "Application uploaded successfully";
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
		reporter.ReportStep("Verify admin acan upload application" , "Application should be uploaded", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
		
		//**************************************************************************//
		// Step 3 : Go to Application's Page and search deviceControl GUID
		//**************************************************************************//
		isEventSuccessful=GoToApplicationsPage();
		searchDevice_DI("Aldiko");
		
		//**************************************************************************//
		// Step 4 : Verify application updated
		//**************************************************************************//
		strstepDescription = "Verify application updated";
		strexpectedResult = "Application should be updated";
		isEventSuccessful=verifylastupdateDateisTodays_Date(getLastUpdateDetails());
		if(isEventSuccessful)
		{
			strActualResult="Application updated successfully";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		
	}
}