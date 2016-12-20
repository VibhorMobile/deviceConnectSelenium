package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-492
 */
public class _733_Verify_timezone_paramter_is_optional extends ScriptFuncLibrary
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
			Values = ExecuteCLICommand("usagejsonfilewithouttimezone", "Android", EmailAddress, Password, "", "");
			isEventSuccessful = (boolean)Values[2];
			deviceName=(String)Values[3];
			if (isEventSuccessful && !Verify_timezone_JSON())
			{
				isEventSuccessful=true;
				strActualResult = "Timezone is optional parameter";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}

			reporter.ReportStep("Verify timezone is optional parameter" , "Timezone should be optional paramater", strActualResult, isEventSuccessful);	
		}
		
		
		
	}
}