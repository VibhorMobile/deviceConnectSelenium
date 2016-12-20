package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-896,73
 */
public class _657_Verify_that_on_clicking_View_token_link_user_able_to_reveal_it extends ScriptFuncLibrary {
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private boolean isStepSuccessful=false;
	String apiToken,apiTokenField;
	Object[] Values = new Object[5]; 
	String [] Entitlement={"Device application management","Device reboot"};
	Boolean [] value={true, true};

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		try{


			String EmailAddress = dicCommon.get("EmailAddress");
			String Password = dicCommon.get("Password");


			//*************************************************************//                     
			// Step 1 : login to deviceConnect with admin user.
			//*************************************************************//                     
			isEventSuccessful = Login();


			//*************************************************************/                     
			// Step 2 : Go to Manage your Account
			//*************************************************************//
			strstepDescription="Click on Manage Your Account from Dropdown";
			strExpectedResult="Successfully clicked Manage your Account from Dropdown";
			isEventSuccessful=PerformAction(dicOR.get("eleUserEmail_UsersPage"), Action.Click);
			if(isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("eleManageAccount_UsersPage"), Action.Click);
				if(isEventSuccessful){
					isEventSuccessful=true;
					strActualResult="Clicked on Manage your Account from Dropdown";
				}
				else{

					strActualResult="Not able to click on Manage your Account from Dropdown";
					isEventSuccessful=false;
				}
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//*************************************************************/                     
			// Step 3 : Verify API token Section
			//*************************************************************//
			strstepDescription="Verify API token section is available for user";
			strExpectedResult="API token is available for the User and clicked on view token link";
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,850)", "");

			isEventSuccessful=PerformAction(dicOR.get("eleAPIToken_UserPage"), Action.isDisplayed);
			if(isEventSuccessful){
				apiToken=GetTextOrValue(dicOR.get("eleAPIToken_UserPage"), "text");
				System.out.println(apiToken);
				if(apiToken.contains("API Token")){
					
					strActualResult="API Token field is available for the user";
					isEventSuccessful=true;

				}
			}
			else{
				strActualResult="API Token field is not available for the user";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//*************************************************************/                     
			// Step 4 : Verify API token field is initially hidden
			//*************************************************************//
			strstepDescription="Verify API token field is initially hidden for user";
			strExpectedResult="API token not visible to user";
			apiTokenField=GetTextOrValue(dicOR.get("eleAPITokenBlock_UserDetailsPage"), "text");
			System.out.println(apiTokenField);
			apiTokenField=apiTokenField.replace("Token", "");
			apiTokenField=apiTokenField.replace("View token", "").trim();	
			 
			System.out.println("api token field:"+apiTokenField);
			if(apiTokenField.equals("")){
				isEventSuccessful=true;
				strActualResult="API token not visible to user";
			}
			else{
				strActualResult="API token visible to user";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//*************************************************************/                     
			// Step 5 : Verify API token is displayed for User
			//*************************************************************//
			// Get the API Token from the UI
			strstepDescription="Verify API token is in proper format for the user";
			strExpectedResult="API token is in right guid format for user";
			PerformAction(dicOR.get("lnkViewToken_UserDetailsPage"), Action.Click);
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
			reporter.ReportStep(strstepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
			 

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "View Token Field for User--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

}
