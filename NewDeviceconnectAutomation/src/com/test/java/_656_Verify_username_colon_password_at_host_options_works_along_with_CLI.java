package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1847
 */
public class _656_Verify_username_colon_password_at_host_options_works_along_with_CLI extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="",expectedText="Usage: MobileLabs.DeviceConnect.Cli <host> <username> <password> [options]";
	private String xpath = "";
	Object[] Values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{

		//**************************************************************************//
		// Step 1 : Verify execution of query using  <username>:<password>@<host> [options] 
		//**************************************************************************//
	
     	 Values = ExecuteCLICommand("authtype3empty", "", "", "", "device", "" ,"", "");
     	 isEventSuccessful = (boolean)Values[2];
     	 outputText=(String)Values[0];
     	 deviceName=(String)Values[3];
     	 strActualResult="Command executed is <br><blockquote><div style=\"background-color:#DCDCDC; color:#000000; font-style: normal; font-family: Georgia; \">"+dicOutput.get("executedCommand") +"</div></blockquote> <br>";
		 if (isEventSuccessful && outputText.equals(expectedText))
			{
			   strActualResult += "Authentication is successful . Output compared from CLI is "+outputText;
			}
			else
			{
				isEventSuccessful=false;
				strActualResult += "ExecuteCLICommand--- CLI Output - "+ outputText+ strErrMsg_AppLib;
			}
		reporter.ReportStep("Authentication using <username>:<password>@<host> [options] should be successful" , "Authentication is successful.", strActualResult, isEventSuccessful);
	
		
	}
}