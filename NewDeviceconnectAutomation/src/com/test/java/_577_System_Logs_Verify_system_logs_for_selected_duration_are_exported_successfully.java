package com.test.java;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: Last week of January
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-906, QA-881
 */

public class _577_System_Logs_Verify_system_logs_for_selected_duration_are_exported_successfully extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	

	public final void testScript() throws InterruptedException
	
	{
		
		// Step 1 : login to deviceConnect with valid user credentials.
		  isEventSuccessful = Login();
	
		// Step 2 : Navigate to System tab.

		  isEventSuccessful =  GoToSystemPage();
		  
		  //Step 3 : Select 'None' value under device drop-down.
		  strStepDescription = "Select 'None' value under device drop-down.";
		  strExpectedResult = "'None' value should be displayed under device drop-down.";
		  //isEventSuccessful = PerformAction("//select[@name='deviceId']", Action.Click);
		  //isEventSuccessful = SelectvaluesonSystemPAge("//select[@name='deviceId']","None");
		  waitForPageLoaded();
		  isEventSuccessful = PerformAction("//select[@name='logExportInput.deviceId']", Action.Select,"None");
		  if(isEventSuccessful)
		  {
			  strActualResult =  "'None' value is selected under device drop-down.";
		  }
		  else
		  {
			  strActualResult =  "'None' value is not selected under device drop-down.";
		  }
		  reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		//Step 4 : Select 'days' value under days drop-down.
		  strStepDescription = "Select 'day' value under days drop-down and Verify able to click on Save logs button";
		  strExpectedResult = "'Day' value should be displayed under days drop-down and Save logs button should be clickable";
		  //int days = GenericLibrary.driver.findElements(By.xpath("//select[@name='days']")).size();
		  waitForPageLoaded();
		  WebElement mySelectElm = GenericLibrary.driver.findElement(By.xpath("//select[@name='logExportInput.days']"));
		  Select mySelect= new Select(mySelectElm);
		  List<WebElement> options = mySelect.getOptions();
		  for(WebElement option : options)
		  {
		      String day = option.getText();
		      waitForPageLoaded();
			  isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
			  /*WebDriverWait wait = new WebDriverWait(GenericLibrary.driver, 240);
			  wait.until(new ExpectedCondition<Boolean>()
					  {
				  		public Boolean apply(WebDriver webDriver)
				  		{
				  			return GenericLibrary.driver.findElement(By.xpath("//button[text()='Save logs']")).isDisplayed() && GenericLibrary.driver.findElement(By.xpath("//button[text()='Save logs']")).isEnabled(); 
				  		}
					  });*/
			  waitForPageLoaded();
			  isEventSuccessful = PerformAction("//select[@name='logExportInput.days']", Action.Select, day);
			  
			  if(isEventSuccessful)
			  {
				  //Thread.sleep(60000);
				  waitForPageLoaded();
				  isEventSuccessful = PerformAction("//button[text()='Save logs']", Action.ClickUsingJS);
				  
				  if(isEventSuccessful)
				  {
					  strActualResult =  "Save logs button is clickable";
					  /*if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
			            {
			               Thread.sleep(20000);
			            }*/
					  waitForPageLoaded();
					  isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
				  }
				  else
				  {
					  strActualResult =  "Save logs button is not clickable";
					  //reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
				  }
			  }
			  else
			  {
				  strActualResult =  day +" value is not selected under days drop-down.";
			  }
			 reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			    
		  }
   }
}
