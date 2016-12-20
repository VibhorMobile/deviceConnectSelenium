package com.test.java;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Jira Test Case Id:  QA-541.
 */

public class _819_Verify_If_the_email_is_already_present_in_the_system_that_row_must_be_skipped_while_user_import extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "",importMessage;
	String [] android={"Android"};

	public final void testScript() throws IOException, AWTException, InterruptedException
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();

			//**********************************************************//
			// Step 2 - Go to UsersPage
			//**********************************************************//                                   
			isEventSuccessful = GoToUsersPage();

			//**********************************************************//
			// Step 3- import with existing user and verify row skipped
			//**********************************************************//  
			strStepDescription = "Import user with existing email address and verify row skipped";
			strExpectedResult = "Existing email row skipped";
			String message="No users were successfully imported. ritdhwaj@ml.com, Row 1: Already a user.";
			isEventSuccessful = PerformAction("ImportUserbtn",Action.Click);
			if(isEventSuccessful)
			{
				isEventSuccessful =PerformAction("uploaduserlistbtn",Action.isDisplayed);
				if(isEventSuccessful)
				{
					isEventSuccessful = PerformAction("uploaduserlistbtn",Action.Click);
					if(isEventSuccessful)
					{
						StringSelection path = new StringSelection(dicConfig.get("Artifacts") +"\\Applications\\userImportDuplicate.csv");
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(path, null);
						Robot robot = new Robot();
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_V);
						robot.keyRelease(KeyEvent.VK_V);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_ENTER);
						robot.keyRelease(KeyEvent.VK_ENTER);
						//isEventSuccessful=importUser(dicCommon.get("EmailAddress"), "", "", "", "", 1);
						//if(isEventSuccessful){
						/*WebDriverWait wait = new WebDriverWait(driver, 50);
						wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='alert in fade alert-success']"))); */
						Thread.sleep(5000);
						importMessage=GetTextOrValue(dicOR.get("userImportResult"), "text");
						System.out.println(importMessage.trim());
						if(importMessage.trim().contains("ritdhwaj@ml.com, Row 1: Already a user.")){
							isEventSuccessful=true;
							strActualResult="Got row skipped Message :"+importMessage;
						}
						else{
							isEventSuccessful=false;
							strActualResult="No row skipped Message :"+importMessage;
						}
					}
				}
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Existing Email Skipped--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}

	}



}
