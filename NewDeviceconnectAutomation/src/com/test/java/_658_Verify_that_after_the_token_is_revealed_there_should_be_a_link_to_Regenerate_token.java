package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import org.openqa.selenium.JavascriptExecutor;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-504,48
 */
public class _658_Verify_that_after_the_token_is_revealed_there_should_be_a_link_to_Regenerate_token extends ScriptFuncLibrary{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private boolean isStepSuccessful=false;
	String apiToken,lnkText,regeneratedApiToken;
	
	
	

	public final void testScript() throws InterruptedException, IOException
	{
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
			strstepDescription="Verify API token is available for User and Click on view Token link";
			strExpectedResult="API token is available for the User and clicked on view token link";
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,850)", "");

			isEventSuccessful=PerformAction(dicOR.get("eleAPIToken_UserPage"), Action.isDisplayed);
			if(isEventSuccessful){
				apiToken=GetTextOrValue(dicOR.get("eleAPIToken_UserPage"), "text");
				System.out.println(apiToken);
				if(apiToken.contains("API Token")){
					PerformAction(dicOR.get("lnkViewToken_UserDetailsPage"), Action.Click);
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
			// Step 4 : Verify API token is displayed for User
			//*************************************************************//
			// Get the API Token from the UI
			strstepDescription="Verify API token is in proper format for the user";
			strExpectedResult="API token is in right guid format for user";
			apiToken=GetTextOrValue(dicOR.get("eleAPITokenBlock_UserDetailsPage"), "text");
			apiToken=apiToken.replace("Token", "");
			apiToken=apiToken.replace("Regenerate token", "").trim();	

			System.out.println(apiToken);
			isEventSuccessful = Pattern.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", apiToken);  

			if(isEventSuccessful)
			{
				strActualResult="API token is displayed. Api Token on UI is "+apiToken;
			}
			else
			{
				strActualResult="Unable to click on View token link. displayed - "+apiToken;
			}
			reporter.ReportStep(strstepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
			//*************************************************************/                     
			// Step 5 : Verify link to regenerate token is present
			//*************************************************************//
			strstepDescription="Verify link to regenerate token is present";
			strExpectedResult="Link to regenerate token is present";
			isEventSuccessful=PerformAction(dicOR.get("lnkRegenerateToken_UserDetailsPage"), Action.isDisplayed);
			
			if(isEventSuccessful){
				lnkText=GetTextOrValue(dicOR.get("lnkRegenerateToken_UserDetailsPage"), "text");
				strActualResult="Link to Regenerate token is present at Page with link text:"+lnkText;
				isEventSuccessful=true;
			}
			else{
				strActualResult="Link to Regenerate token is not present at Page ";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strstepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
			//*************************************************************/                     
			// Step 6 : Regenerate Token and Verify its in proper format and different from initial token
			//*************************************************************//
			strstepDescription="Verify new token is generated on clicking regenerate token";
			strExpectedResult="New token is generated ";
			isEventSuccessful=PerformAction(dicOR.get("lnkRegenerateToken_UserDetailsPage"), Action.Click);
			regeneratedApiToken=GetTextOrValue(dicOR.get("eleAPITokenBlock_UserDetailsPage"), "text");
			regeneratedApiToken=regeneratedApiToken.replace("Token", "");
			regeneratedApiToken=regeneratedApiToken.replace("Regenerate token", "").trim();	

			System.out.println(regeneratedApiToken);
			isEventSuccessful = Pattern.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", regeneratedApiToken); 
			if(regeneratedApiToken.equals(apiToken)){
				strActualResult="New token generated is same as previous one "+regeneratedApiToken;
				isEventSuccessful=false;
			}
			else{
				strActualResult="New token is generated after clicking on Regenerate token "+regeneratedApiToken;
				isEventSuccessful=true;
			}
			
			reporter.ReportStep(strstepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "View Token Field for User--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

}
