package com.test.java;

import java.io.IOException;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-581
 */
public class _750_Launch_Application_dialog_will_display_App_name_if_the_name_is_enclosed_within_arrows extends ScriptFuncLibrary{
	
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,appName,appNameEdited;
	Object[] Values = new Object[5]; 
	boolean isAppDialog,isAppDispayed;






	public final void testScript() throws InterruptedException, IOException
	{
		try{
			String EmailAddress = dicCommon.get("testerEmailAddress");
			String Password = dicCommon.get("testerPassword");

			//*************************************************************//                     
			// Step 1 : login to deviceConnect with Tester User
			//*************************************************************//  


			isEventSuccessful = Login();
			//*************************************************************//      
			// Step 2 : Go to APPLICATIONS Page and rename first iOS device
			//*************************************************************//
			strStepDescription="Rename first iOS app within <>";
			strExpectedResult="App name is renamed within <>";
			GoToApplicationsPage();
			//selectPlatform("iOS");
			selectPlatform_DI("iOS");
			GoToFirstAppDetailsPage();
			waitForPageLoaded();
			appName=GetTextOrValue(dicOR.get("eleAppNameDisplay"), "text");
			PerformAction(dicOR.get("lnkEdit_ApplicationDetailsPage"), Action.Click);
			waitForPageLoaded();
		    System.out.println("appname is :"+appName);
			PerformAction(dicOR.get("eleAppNameInputBox"), Action.Type,"<"+appName+">");
			PerformAction(dicOR.get("lnkSaveAppName_ApplicationDetailsPage"), Action.Click);
			waitForPageLoaded();
			appNameEdited=GetTextOrValue(dicOR.get("eleAppNameDisplay"), "text");
			 System.out.println("appname is :"+appNameEdited);
			if(appNameEdited.equals("<"+appName+">")){
				strActualResult="App Display name is edited ";
				isEventSuccessful=true;
			}
			else{
				strActualResult="App display name is not edited ";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			
			//*************************************************************//      
			// Step 3 :Go to Any available iOS Device and Connect
			//*************************************************************//	
			strStepDescription="Click on connect and Verify Launch App Dialog Displayed";
			strExpectedResult="Launch App Dialog displayed";
			GoToDevicesPage();
			selectPlatform("iOS");
			selectStatus_DI("Available");
			GoTofirstDeviceDetailsPage();
			waitForPageLoaded();
			PerformAction(dicOR.get("btnConnect"), Action.Click);
			waitForPageLoaded();
			isAppDialog=PerformAction(dicOR.get("eleAppLaunchDialog"), Action.isDisplayed);
			
			if(isAppDialog){
				strActualResult="Application Launch Dialog Displayed";
				isEventSuccessful=true;
			}
			else{
				strActualResult="Application Launch dialog did not dispayed";
				isEventSuccessful=false;
			}
			 
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			
			//*************************************************************//      
			// Step 4 :Verify Edited App is Displayed in Launch Dialog
			//*************************************************************//	
			strStepDescription="Verify App edited is displayed on Launch Dialog";
			strExpectedResult="Edited App Displayed in Launch Dialog";
			isAppDispayed=PerformAction("//a[contains(text(),'"+appNameEdited+"')]", Action.isDisplayed);
			
			if(isAppDispayed){
				strActualResult="App with edited display name inside <> displayed on Launch Dialog ";
				isEventSuccessful=true;
			}
			else{
				strActualResult="App with edited display name inside <> did not displayed on Launch Dialog ";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


	

}
