package com.test.java;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-693,295,691
 */
public class _717_Verify_Loadingscreen_is_displayed_immediately_after_Clicking_savelogs_button_and_verifyDefaultDropdownValues extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "", strSelectedValue = "";;

	public final void testScript() throws InterruptedException, IOException
	{
			
			// Step 1 : login to deviceConnect with valid user credentials.
			  isEventSuccessful = Login();
		
			// Step 2 : Navigate to System tab.

			  isEventSuccessful =  GoToSystemPage();
			  
			  //QA-295	System Logs: Verify by default "None" and "2 days" are selected in the drop down.			 
			  
			  //Step 3 : Verify 'None' value is by default selected under device drop-down.
			  strStepDescription = "'None' value is selected by default in device drop-down.";
			  strExpectedResult = "'None' value should be displayed under device drop-down.";
			 
			  //strSelectedValue=GetTextOrValue(dicOR.get("drpSelectDevice_SystemsPage"), "text");			  
			  WebElement mySelectElm = GenericLibrary.driver.findElement(By.xpath(dicOR.get("drpSelectDevice_SystemsPage")));
			  Select mySelect= new Select(mySelectElm); 
			  String strSelectedValue = mySelect.getFirstSelectedOption().getText();
			  
			  if(strSelectedValue.equals("None"))
			  {
				  isEventSuccessful=true;
				  strActualResult =  "'None' value is selected by default under device drop-down. Value selected is -"+strSelectedValue;
			  }
			  else
			  {
				  isEventSuccessful=false;
				  strActualResult =  "'None' value is not selected by default under device drop-down. Value selected is -"+strSelectedValue;
			  }
			  reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//Step 4 : verify '2 days' value is by default selected under days drop-down.
			  strStepDescription = "Verify '2 days' value is by default selected under days drop-down";
			  strExpectedResult = "'2 days' value should be displayed under days drop-down";
			 
			  
			  //strSelectedValue=GetTextOrValue(dicOR.get("drpDuration_SystemsPage"), "text");			  
			  mySelectElm = GenericLibrary.driver.findElement(By.xpath(dicOR.get("drpDuration_SystemsPage")));
			  mySelect= new Select(mySelectElm); 
			  strSelectedValue = mySelect.getFirstSelectedOption().getText();
			  
			  if(strSelectedValue.equals("2 days"))
			  {
				  isEventSuccessful=true;
				  strActualResult =  "'2 days' value is selected by default under days drop-down. Value selected is -"+strSelectedValue;
			  }
			  else
			  {
				  isEventSuccessful=false;
				  strActualResult =  "'2 days' value is not selected by default under days drop-down. Value selected is -"+strSelectedValue;
			  }
			  reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
				    
			  
			  //QA-693	:Verify Loading screen is displayed immediately after the save logs button is pressed

					  
			  isEventSuccessful = PerformAction(dicOR.get("btnSavelogs"), Action.Click);
			  
			  if(isEventSuccessful)
			  {
				  strActualResult =  "Save logs button is clickable";
				  
				  isEventSuccessful = PerformAction(dicOR.get("eleBlockedUI_PageLoading"), Action.Exist);
				  if (isEventSuccessful)
				  		strActualResult="Loading screen is displayed (blocking UI).";
				  else
					  	strActualResult =  "Loading screen is not displayed (blocking UI).";
				
			  }
			  else
				  strActualResult="Unable to click on save logs button.";  				 
			  reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}
}