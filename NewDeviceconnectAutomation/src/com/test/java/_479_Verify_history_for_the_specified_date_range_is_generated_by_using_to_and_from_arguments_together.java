package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1421
 */

public class _479_Verify_history_for_the_specified_date_range_is_generated_by_using_to_and_from_arguments_together extends ScriptFuncLibrary
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
	     	 Values = ExecuteCLICommand("historyfromto", "iOS", EmailAddress, Password, "", "");
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
		reporter.ReportStep("History file generated using -from and -to arguments" , "File should be created", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
		
		if(isEventSuccessful)
		{
			isEventSuccessful=verifyUsage_HistoryToFrom("history",fileName,"Date",dates);
			if(isEventSuccessful)
			{
				strActualResult="History file for specified date range created";
			}
			else
			{
				strActualResult=strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify history file created for specified date range" , "History file should contain specified date range", strActualResult, isEventSuccessful);
		}
		
	}
}