package com.test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-746
 */
public class _607_Verify_User_is_not_able_to_copy_and_paste_password_from_the_Password_field_to_the_Confirm_Password_field extends ScriptFuncLibrary

{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	Object[] Values = new Object[5]; 
	private String strActualResult = "", devicename = "",EmailAddress, Password;
	
	public final void testScript() throws Exception 
	
	{
		//**************************************************************************//
 		// Step 1 : login to deviceConnect with valid user and verify Devices page.
 		//**************************************************************************//
 		strStepDescription = "Login to deviceConnect with valid username and password and verify Devices page.";
 		strExpectedResult = "User should be logged in and navigated to Devices page.";
 		isEventSuccessful = Login();
 		if (isEventSuccessful)
 		{
 			reporter.ReportStep(strStepDescription, strExpectedResult, "Logged in successfully with user:  and navigated to Devices page.", "Pass");
 		}
 		else
 		{
 			strActualResult = strErrMsg_AppLib;
 			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, "Fail");
 		}

 		//**************************************************************************//
 		// Step 2 : Go to Users page and click on the Edit user button.
 		//*************************************************************
 		strStepDescription = "Verify_User_is_not_able_to_copy_and_paste_password_from_the_Password_field_to_the_Confirm_Password_field";
 		strExpectedResult = "Verify_User_should not be_able_to_copy_and_paste_password_from_the_Password_field_to_the_Confirm_Password_field";
 		isEventSuccessful = GoToUsersPage();
 		
 		isEventSuccessful = Searchuser_Users(dicCommon.get("EmailAddress"));
		isEventSuccessful = PerformAction("//div[@class='status-description-row']//a", Action.Click);
		isEventSuccessful = PerformAction("txtPassword", Action.Type, "dollyagarwal");
		
		driver.findElement(By.name("userEdit.password")).sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.chord(Keys.CONTROL,"c"));
				
				
		Actions action= new Actions(driver);
		action.contextClick(driver.findElement(By.name("userEdit.passwordConfirm"))).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.RETURN).build().perform();
		isEventSuccessful = PerformAction("btnSave", Action.Click);
		if(isEventSuccessful)
		 {
			 isEventSuccessful = GetTextOrValue("//div[@class='error-notice callout callout-danger']/div", "text").contains("Confirmation password does not match the entered password.");
			 if(isEventSuccessful)
			  {
				 strActualResult =  "User_is_not_able_to_copy_and_paste_password_from_the_Password_field_to_the_Confirm_Password_field";
		      }
			 else
			 {
				 strActualResult =  "User_is__able_to_copy_and_paste_password_from_the_Password_field_to_the_Confirm_Password_field";
			 }
		 }
		
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult , isEventSuccessful);		
		

	
	}

}
