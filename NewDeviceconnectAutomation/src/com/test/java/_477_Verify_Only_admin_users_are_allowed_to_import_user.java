package com.test.java;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-697
 */

public class _477_Verify_Only_admin_users_are_allowed_to_import_user extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="", cmdText="";
	private String xpath = "",email="";
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
		// Step 3 : Try to import user using tester credentials.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
			Values = ExecuteCLICommand("importtester", "iOS", EmailAddress, Password, "", "");
			//isEventSuccessful = (boolean)Values[4];
			//outputText=(String)Values[1];
			cmdText=(String)Values[0];
			deviceName=(String)Values[3];
			if (cmdText.contains("User does not have required entitlement"))
			{
				isEventSuccessful=true;
				strActualResult = "Tester can not import users";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Tester should not be able to import user" , "Tester can not import user", strActualResult, isEventSuccessful);
		}
	}
}