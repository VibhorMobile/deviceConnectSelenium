package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1849,1848,836
 */
public class _654_Verify_that_apitoken_parameter_in_CLI_returns_the_authenticated_users_api_token_and_apiKey_authentication_option_works extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="",apiToken="";
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
		// Step 2 : Go to current user's details page
		//**************************************************************************//
		isEventSuccessful=GoToUsersPage();
		PerformAction("browser","waitforpagetoload");
		searchDevice_DI(EmailAddress);
		

		//**************************************************************************//
		// Step 3 : Go to specific user account
		//**************************************************************************//
		strstepDescription = "Verify user account page opened";
		strexpectedResult = "User account page should be displayed";
		PerformAction("browser","waitforpagetoload");
		isEventSuccessful=PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", EmailAddress), Action.Click);
		if(isEventSuccessful)
		{
			strActualResult="User account page displayed";
		}
		else
		{
			strActualResult="Unable to click on given mail id";
		}
		reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		
		
		//**************************************************************************//
		// Step 4 : Click on view Token Link and get API Token from UI
		//**************************************************************************//
		strstepDescription = "Click on View token link.";
		strexpectedResult = "API token is displayed.";
		PerformAction("browser","waitforpagetoload");
		isEventSuccessful=PerformAction(dicOR.get("lnkViewToken_UserDetailsPage"), Action.Click);
		
		
		// Get the API Token from the UI
		apiToken=GetTextOrValue(dicOR.get("eleAPITokenBlock_UserDetailsPage"), "text");
		apiToken=apiToken.replace("Token", "");
		apiToken=apiToken.replace("Regenerate token", "").trim();	
		
		System.out.println(apiToken);
		boolean isEventSuccessful = Pattern.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", apiToken);  

		if(isEventSuccessful)
		{
			strActualResult="API token is displayed. Api Token on UI is "+apiToken;
		}
		else
		{
			strActualResult="Unable to click on View token link. displayed - "+apiToken;
		}
		reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		
		
		//**************************************************************************//
		// Step 5 : Get API Token from CLI
		//**************************************************************************//
	
     	 Values = ExecuteCLICommand("apitoken", "", "", "", "device", "" ,"", "");
     	 isEventSuccessful = (boolean)Values[2];
     	 outputText=(String)Values[0];
     	 deviceName=(String)Values[3];
     	 strActualResult="Command executed is <br><blockquote><div style=\"background-color:#DCDCDC; color:#000000; font-style: normal; font-family: Georgia;\">"+dicOutput.get("executedCommand") +"</div></blockquote> <br>";
		 if (isEventSuccessful && outputText.equals(apiToken))
			{
			   strActualResult += "API Token from CLI command and DC UI matches. APIToken from CLI is "+outputText;
			}
			else
			{
				isEventSuccessful=false;
				strActualResult += "ExecuteCLICommand--- CLI Output - "+ outputText+ strErrMsg_AppLib;
			}
		reporter.ReportStep("Verify api token from CLI command matches user token on UI" , "API Token from CLI as same as on UI.", strActualResult, isEventSuccessful);
	
		//**************************************************************************//
		// Step 6 : Validating CLI Authentication using <username>:<api key>@<host> [options]
		//**************************************************************************//
		//
		 Values = ExecuteCLICommand("authtype4apitoken", "", "", apiToken, "device", "" ,"", "");
     	 isEventSuccessful = (boolean)Values[2];
     	 outputText=(String)Values[0];
     	 deviceName=(String)Values[3];
     	strActualResult="Command executed is <br><blockquote><div style=\"background-color:#DCDCDC; color:#000000; font-style: normal; font-family: Georgia;\">"+dicOutput.get("executedCommand") +"</div></blockquote> <br>";
		 if (isEventSuccessful && outputText.equals(apiToken))
			{
			   strActualResult += "Authentication using <username>:<api key>@<host> [options] is successful. APIToken is "+outputText;
			}
			else
			{
				isEventSuccessful=false;
				strActualResult += "Authentication using <username>:<api key>@<host> [options] is not successful. ExecuteCLICommand--- CLI Output - "+ outputText+ strErrMsg_AppLib;
			}
		reporter.ReportStep("Validating CLI Authentication using <username>:<api key>@<host> [options]" , "Authentication using <username>:<api key>@<host> [options] is successful.", strActualResult, isEventSuccessful);
	
		
	}
}