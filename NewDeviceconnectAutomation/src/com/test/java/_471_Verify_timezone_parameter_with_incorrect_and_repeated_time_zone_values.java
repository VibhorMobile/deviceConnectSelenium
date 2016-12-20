package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-503
 */

public class _471_Verify_timezone_parameter_with_incorrect_and_repeated_time_zone_values extends ScriptFuncLibrary
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
		isEventSuccessful = Login();


		//**************************************************************************//
		// Step 2 : Connect to an android device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("incorrecttimezone", "Android", EmailAddress, Password, "", "");
	     	 //isEventSuccessful = (boolean)Values[4];
	     	 //outputText=(String)Values[1];
	     	 cmdText=(String)Values[0];
	     	 deviceName=(String)Values[3];
			 if (cmdText.contains("System.NullReferenceException: Unable to find timezone 'Asia/calcutta'"))
				{
				   isEventSuccessful=true;
				   strActualResult = "Incorrect timezone thrown exception";
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
		reporter.ReportStep("Verify timezone parameter with incorrect timezone" , "Incorrect timezone should throw exception", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
	}
}