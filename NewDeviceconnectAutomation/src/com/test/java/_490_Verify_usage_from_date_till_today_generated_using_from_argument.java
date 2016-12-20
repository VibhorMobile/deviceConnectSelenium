package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1430
 */

public class _490_Verify_usage_from_date_till_today_generated_using_from_argument extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", fileName = "", deviceName="", dates="";
	private String xpath = "";
	Object[] Values = new Object[7]; 

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid admin user.";
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
		// Step 2 : Generate history using -from -to arguments.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("usagefrom", "iOS", EmailAddress, Password, "", "");
	     	 isEventSuccessful = (boolean)Values[2];
	     	 fileName=(String)Values[6];
	     	 dates=(String)Values[5];
	     	 deviceName=(String)Values[3];
			 if (isEventSuccessful)
				{
				   isEventSuccessful=true;
				   strActualResult = "File created successfully";
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
		reporter.ReportStep("Usage file generated using -from arguments" , "File should be created", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
		
		if(isEventSuccessful)
		{
			isEventSuccessful=verifyUsage_HistoryToFrom("usage",fileName,"Date",dates);
			if(isEventSuccessful)
			{
				strActualResult="Usage file for specified days created";
			}
			else
			{
				strActualResult=strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify usage file created for specified days" , "Usage file should contain specified days", strActualResult, isEventSuccessful);
		}
		
	}
}