package com.test.java;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-95
 */
public class _655_Verify_that_API_Token_section_will_be_visible_to_logged_in_user extends ScriptFuncLibrary {
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private boolean isStepSuccessful=false;
	String apiToken;
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
			strstepDescription="Verify API token is available for User";
			strExpectedResult="API token is available for the User";
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
			// Step 4 : Go to Users page and Search for the User
			//*************************************************************//

			isEventSuccessful=GoToUsersPage();

			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='userListTable table data-grid user-list-table']/tbody/tr[1]/td[1]"))); 
			if(isEventSuccessful)
			{

				isEventSuccessful=Searchuser_Users(dicCommon.get("EmailAddress"));//Search user with UserName

				if(isEventSuccessful){

					GoToFirstUserDetailsPage();

					//*************************************************************/                     
					// Step 5 : Search for API token Section in Users Page
					//*************************************************************//	 
					strstepDescription="Verify API token is available when going to User details from Users Page for loggin user";
					strExpectedResult="API token is available for the User";
					jse.executeScript("window.scrollBy(0,550)", "");
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
				}


			}

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "View Token Field for User--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}
}
