package com.test.java;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.Hosts;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1468
 */

public class _498_Verify_CLI_works_fine_if_we_pass_domain_name_instead_of_IP_address_in_place_of_host_address extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",deviceName="", email="";
	private String xpath = "";
	Object[] Values = new Object[5]; 

	public final void testScript() throws Exception
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		//*************************************************************//                     
		// Step 1 : Add entry of host in host file.
		//*************************************************************//  
		String [] val=new String[3];
		val[0]="--add";
		val[1]="www.mobdeep.com";
		val[2]=dicCommon.get("ApplicationURL");
		Hosts.main(val);
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************// 
		LaunchWebDriver(dicCommon.get("BrowserName"), val[1]);
		
		strstepDescription = "Login to deviceConnect with valid user using domain name in URL.";
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
		
	}
}