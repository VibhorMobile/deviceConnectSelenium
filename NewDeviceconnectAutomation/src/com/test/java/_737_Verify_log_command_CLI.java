package com.test.java;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2028
 */
public class _737_Verify_log_command_CLI extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="", cmdText="";
	private String xpath = "",email="";
	Object[] Values = new Object[5]; 

	public final void testScript() 
	{
		
		//**************************************************************************//
		// Step 1 : Verify user information in out file generated using -format json.
		//**************************************************************************//
		isEventSuccessful=Login();
		
		//**************************************************************************//
		// Step 2 : Go to Devices page.
		//**************************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful=GoToDevicesPage();
		}
		
		//**************************************************************************//
		// Step 3 : Verify logs generated using -log command.
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("log", "iOS", "", "", "", "","","");
			isEventSuccessful = (boolean)Values[2];
			if (isEventSuccessful && Verify_Logs_Generated())
			{
				strActualResult = "Device logs generated.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify logs generated using -log command." , "Device log should be generated.", strActualResult, isEventSuccessful);
		}
		
		
	}
}