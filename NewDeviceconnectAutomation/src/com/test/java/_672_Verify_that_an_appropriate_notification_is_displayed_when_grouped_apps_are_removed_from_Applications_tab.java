package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1890
 */
public class _672_Verify_that_an_appropriate_notification_is_displayed_when_grouped_apps_are_removed_from_Applications_tab extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false, isExist=false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", cmdText = "", deviceName="",expectedMessage="This will delete all uploaded versions of the application from the server. This action can not be undone.";
	private String installedApps = "";
	Object[] values = new Object[5]; 

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


		//*************************************************************//                     
		// Step 2 : Navigate to Application list page
		//*************************************************************//  
		isEventSuccessful =GoToApplicationsPage();  
		
		
		strStepDescription = "Pre-condition: Grouped application should be present";
		strExpectedResult = "If not present, upload it.";
		isExist=PerformAction(dicOR.get("eleFirstAppBadgeCountIcon_AppListPage"), Action.Exist);
		if (!isExist)
		{
			
			      
			      for(int count=0; count<=1; count++)
			      {
			    	  	values = uploadAppThroughCLI("com.aldiko.android.apk");
				     	 isEventSuccessful = (boolean)values[2];
				     	 cmdText=(String)values[0];
				     	 deviceName=(String)values[3];
						 if (isEventSuccessful)
						 {
							   isEventSuccessful=true;
							   strActualResult = "Not already present. Applications uploaded successfully";
						 }
						 else
						 {
							 isEventSuccessful=false;
							 strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
							 break;
						 }
						
			      }
			      
		}
		else
		{
			isEventSuccessful=true;
			strActualResult="Grouped Application already exists.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			

		strStepDescription = "Verify notification message when grouped applications are removed from Applications tab";
		strExpectedResult = "Getting message as expected";
		
		PerformAction("browser", Action.Refresh);
		isExist=PerformAction(dicOR.get("eleFirstAppBadgeCountIcon_AppListPage"), Action.Exist);
		
		String index="";
		int iCount = 0;
		iCount = driver.findElements(By.xpath(dicOR.get("elePrecedingRowSibling_FirstAppWithMultipleInstance"))).size();
		iCount++;
		
		index= Integer.toString(iCount);
		if (isExist)
		{
			
			isEventSuccessful =  PerformAction(dicOR.get("btnMultiFunctionDropdown_AppListPage").replace("__INDEX__", index), Action.Click);
			 if(isEventSuccessful)
			 {
				 isEventSuccessful =  PerformAction(dicOR.get("btnDelete_Multifunction_AppList").replace("__INDEX__", index), Action.Click);
				 if(isEventSuccessful)
				 {
					 
					 String notificationMsgText=GetTextOrValue(dicOR.get("eleConfirmDisableMsg"),"text");
					 	
					 if (expectedMessage.equals(notificationMsgText.trim()))
					 {
						 isEventSuccessful=true;
						 strActualResult= "Getting notification message as expected.";
					 }
					 else
					 {
						 isEventSuccessful=false;
						 strActualResult= "Not getting notification messsage as expected. Message displayed is " +notificationMsgText + "Error "+ strErrMsg_GenLib;
					 } 
					 
				 }
				 else
				 {
					 strActualResult = "Could not clicked on Delete button in multi function dropdown";
				 }
			 }
			 else
			 {
				 strActualResult = "Could not clicked on multi funtion dropdown arrow button on Application List page";
			 }
			 
			 reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			 
			 isEventSuccessful = PerformAction(dicOR.get("btnCancel"), Action.Click);
			 if(isEventSuccessful)
			 {
				 strActualResult="Successfully clicked on Cancel button";
			 }
			 else
			 {
				 strActualResult = "Could not clicked on Cancel button";
			 }
			 reporter.ReportStep("Click on Cancel button in the notification pop-up.", "Successfully clicked on Cancel button.", strActualResult, isEventSuccessful);
			 
		}
		else
		{
			reporter.ReportStep(strStepDescription, strExpectedResult, "Unable to get grouped application on Applications page", false);
		}
		
		
	}
}