package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-342
 */

public class _475_Verify_downloaded_reports_with_timezone_parameter_json extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	int count=0;
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
		isEventSuccessful = Login();


		//**************************************************************************//
		// Step 2 : Extract device usage report in json format in file.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("usagejsonfile", "Android", EmailAddress, Password, "", "");
	     	 isEventSuccessful = (boolean)Values[2];
	     	 deviceName=(String)Values[3];
			 if (isEventSuccessful && Verify_timezone_JSON())
				{
				   isEventSuccessful=true;
				   strActualResult = "Downloaded report is in local time format";
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
		reporter.ReportStep("Verify timezones in Json file" , "Report should be in local time format", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
		
		//**************************************************************************//
		// Step 3 : Extract device usage report in json format on console.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
			Values = ExecuteCLICommand("usagejsonfile", "Android", EmailAddress, Password, "", "");
			isEventSuccessful = (boolean)Values[2];
			deviceName=(String)Values[3];
			if (isEventSuccessful && Verify_timezone_JSON())
			{
				isEventSuccessful=true;
				strActualResult = "Downloaded report is in local time format";
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
		reporter.ReportStep("Verify timezones on console output" , "Report should be in local time format", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
	}
}