package com.test.java;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1830
 */
public class _735_Verify_getting_users_information_by_user_id extends ScriptFuncLibrary
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
		// Variables from datasheet
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");

		
		
		//**************************************************************************//
		// Step 1 : Verify by default user information displayed in list format.
		//**************************************************************************//
		Values = ExecuteCLICommand("userinfodefault", "", EmailAddress, Password, "None", "","",EmailAddress);
		isEventSuccessful = (boolean)Values[2];
		cmdText=(String)Values[0];
		if (isEventSuccessful && cmdText.contains("Output is in list format"))
		{
			isEventSuccessful=true;
			strActualResult = "Output is in list format.";
		}
		else
		{
			isEventSuccessful=false;
			strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
		}
		reporter.ReportStep("Verify by default user information displayed in list format" , "User information should be displayed in list format", strActualResult, isEventSuccessful);

		
		//**************************************************************************//
		// Step 2 : Verify user information displayed in list format using format option.
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("userinfolist", "", EmailAddress, Password, "None", "","",EmailAddress);
			isEventSuccessful = (boolean)Values[2];
			cmdText=(String)Values[0];
			if (isEventSuccessful && cmdText.contains("Output is in list format"))
			{
				isEventSuccessful=true;
				strActualResult = "Output is in list format.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify user information displayed in list format using format option" , "User information should be displayed in list format", strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 3 : Verify user information displayed in json format using format option.
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("userinfojson", "", EmailAddress, Password, "None", "","",EmailAddress);
			isEventSuccessful = (boolean)Values[2];
			cmdText=(String)Values[0];
			if (isEventSuccessful && cmdText.contains("Output is in json format"))
			{
				isEventSuccessful=true;
				strActualResult = "Output is in json format.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify user information displayed in json format using format option" , "User information should be displayed in json format", strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 4 : Verify exception thrown while trying to get user information in unsupported format
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("userinfotext", "", EmailAddress, Password, "None", "","",EmailAddress);
			isEventSuccessful = (boolean)Values[2];
			cmdText=(String)Values[0];
			if (isEventSuccessful && cmdText.contains("Unknown format requested"))
			{
				isEventSuccessful=true;
				strActualResult = "Unknown format requested exception thrown.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify exception thrown while trying to get user information in unsupported format" , "Exception should be thrown", strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 5 : Verify admin can view other admin info
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("userinfodefault", "", EmailAddress, Password, "None", "","","admin");
			isEventSuccessful = (boolean)Values[2];
			cmdText=(String)Values[0];
			if (isEventSuccessful && cmdText.contains("Output is in list format"))
			{
				isEventSuccessful=true;
				strActualResult = "Admin can view other admin information";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify admin can view other admin information" , "Admin should be able to view other admin information", strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 6 : Verify admin can view any tester info
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("userinfodefault", "", EmailAddress, Password, "None", "","",testerEmailAddress);
			isEventSuccessful = (boolean)Values[2];
			cmdText=(String)Values[0];
			if (isEventSuccessful && cmdText.contains("Output is in list format"))
			{
				isEventSuccessful=true;
				strActualResult = "Admin can view other admin information";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify admin can view any tester information" , "Admin should be able to view any tester information", strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 7 : Verify tester can view its own info
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("userinfodefault", "", testerEmailAddress, Password, "None", "","",testerEmailAddress);
			isEventSuccessful = (boolean)Values[2];
			cmdText=(String)Values[0];
			if (isEventSuccessful && cmdText.contains("Output is in list format"))
			{
				isEventSuccessful=true;
				strActualResult = "Tester can view its own information";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify tester can view its own information" , "Tester should be able to view its own information", strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 8 : Verify tester can not view admin information
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("userinfodefault", "", testerEmailAddress, Password, "None", "","",EmailAddress);
			isEventSuccessful = (boolean)Values[2];
			cmdText=(String)Values[0];
			if (isEventSuccessful && cmdText.contains("Unable to locate user"))
			{
				isEventSuccessful=true;
				strActualResult = "Tester user cannot access admin information.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify tester user cannot access admin information" , "Tester should not be able to view admin information", strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 9 : Verify user information in out file generated using -format json.
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("userinfooutputjson", "", EmailAddress, Password, "None", "","",EmailAddress);
			isEventSuccessful = (boolean)Values[2];
			if (isEventSuccessful && Verify_UserInfo_JSON())
			{
				strActualResult = "Output is in json format.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify user information in out file generated using -format json" , "User information should be in json format", strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 10 : Verify user information in out file generated using -format list.
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("userinfooutputlist", "", EmailAddress, Password, "None", "","",EmailAddress);
			isEventSuccessful = (boolean)Values[2];
			if (isEventSuccessful && Verify_UserInfo_List())
			{
				strActualResult = "Output is in list format.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify user information in out file generated using -format list" , "User information should be in list format", strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 11 : Verify user information in out file generated without -format list.
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("userinfooutput", "", EmailAddress, Password, "None", "","",EmailAddress);
			isEventSuccessful = (boolean)Values[2];
			if (isEventSuccessful && Verify_UserInfo_List())
			{
				strActualResult = "Output is in list format.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify user information in out file generated using -format list" , "User information should be in list format", strActualResult, isEventSuccessful);
		}
		
		//**************************************************************************//
		// Step 11 : Verify user information in out file generated with -format csv.
		//**************************************************************************//
		if(isEventSuccessful)
		{
			Values = ExecuteCLICommand("userinfooutputcsv", "", EmailAddress, Password, "None", "","",EmailAddress);
			isEventSuccessful = (boolean)Values[2];
			if (isEventSuccessful && Verify_UserInfo_CSV())
			{
				strActualResult = "Output is in csv format.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify user information in out file generated using -format csv" , "User information should be in csv format", strActualResult, isEventSuccessful);
		}
	}
}