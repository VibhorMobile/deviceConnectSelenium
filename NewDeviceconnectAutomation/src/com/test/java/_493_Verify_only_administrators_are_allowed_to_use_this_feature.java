package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-62
 */

public class _493_Verify_only_administrators_are_allowed_to_use_this_feature extends ScriptFuncLibrary
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
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid test user.";
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
		// Step 2 : Connect to an iOS device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("userexporttester", "iOS", EmailAddress, Password, "", "");
	     	 cmdText=(String)Values[0];
	     	 deviceName=(String)Values[3];
			 if (cmdText.contains("User does not have required entitlement"))
				{
				   isEventSuccessful=true;
				   strActualResult = "Tester can not export user list";
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
		reporter.ReportStep("Verify Tester are not able to export user list" , "Tester should not export user list", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
	}
}