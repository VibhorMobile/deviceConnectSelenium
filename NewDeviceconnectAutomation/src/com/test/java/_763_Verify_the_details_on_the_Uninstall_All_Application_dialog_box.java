package com.test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-806,619
 */
public class _763_Verify_the_details_on_the_Uninstall_All_Application_dialog_box extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";

	private String warningMessage,isAppInstalled,isAppUnInstalled,noAppMessage;
	boolean isWarningDisplayed;

	public final void testScript() throws Exception
	{
		try{
			
			//*************************************************************//                     
			// Step 1 : login to deviceConnect with ADMIN User
			//*************************************************************//  


			isEventSuccessful = Login();
			//*************************************************************//      
			// Step 2 : Go to Devices Page 
			//*************************************************************//
			GoToDevicesPage();
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();

			//**************************************************************************//
			// Step 3 : Install TrustBrowser app On device
			//**************************************************************************//
			waitForPageLoaded();
			isAppInstalled=GetTextOrValue(dicOR.get("installedAppsSection_DeviceDetailsPage"), "text");
			if(isAppInstalled.contains("No applications installed")){
				PerformAction("//table[@class='table data-grid']/tbody/tr/td[@title='Trust Browser']/../td[@class='btn-column']/button[contains(text(),'Install')]",Action.Click);

				WebDriverWait wait = new WebDriverWait(driver, 200);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("html/body/div[7]/div/div"))); 
				isAppInstalled=GetTextOrValue(dicOR.get("installedAppsSection_DeviceDetailsPage"), "text");
				if(!isAppInstalled.contains("No applications installed")){
					strActualResult="Trust Browser app is installed  on device";
					isEventSuccessful=true;
				}
				else{
					strActualResult="No app installed on device, unable to install trust browser app";
					isEventSuccessful=true;
				}


			}
			else{
				strActualResult="Apps are installed for device";
				isEventSuccessful=true;
			}
			reporter.ReportStep("Verify apps installed on device","Apps are installed on device", strActualResult, isEventSuccessful);

			//**************************************************************************//
			// Step 4 : Click on UnInstallAll Button and Verify Dialog gets Displayed
			//**************************************************************************//
			waitForPageLoaded();
			isEventSuccessful=PerformAction(dicOR.get("BtnUninstallAllEnabled"), Action.isDisplayed);
			if(isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("BtnUninstallAllEnabled"), Action.Click);
				if(isEventSuccessful){
					waitForPageLoaded();
					isEventSuccessful=PerformAction(dicOR.get("UnInstallAllDialog"), Action.isDisplayed);
					if(isEventSuccessful){
						strActualResult="UnInstallAll Dialog displayed on screen";
					}
					else{
						strActualResult="UnInstallAll Dialog did not  displayed on screen";
					}

				}
				else{
					strActualResult="Unable to click on UnInstallAll button";
				}
			}
			else{
				strActualResult="UnInstall All button not displayed";
			}
			reporter.ReportStep("Click on UninstallAll Button and Verify UnInstall Dialog gets Displayed","Clicked on Uninstall button and UnInstall Dialog displayed", strActualResult, isEventSuccessful);


			//**************************************************************************//
			// Step 5 : Click on Cancel and Verify dialog closes
			//**************************************************************************//
			isEventSuccessful=PerformAction(dicOR.get("btnCancel_UnInstallAllDialog"), Action.Click);
			if(isEventSuccessful){
				waitForPageLoaded();
				isEventSuccessful=!PerformAction(dicOR.get("UnInstallAllDialog"), Action.isDisplayed);
				if(isEventSuccessful){
					strActualResult="After Click on Cancel button UnInstallAll Dialog disappeared";
				}
				else{
					strActualResult="UnInstall all dialog did not closed";
				}

			}
			else{
				strActualResult="Unable to click on Cancel button in UnInstallAll dialog.";
			}
			reporter.ReportStep("Click on Cancel and Verify UnInstallAll Dialog gets closed", "UnInstallAll dialog should get closed", strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 6 : Click On UnInstallAll and then click on X icon and Verify Dialog gets Closed
			//**************************************************************************//
			strStepDescription="Click on X icon from UnInstallAll Dialog and Verify dialog gets closed";
			strExpectedResult="Dialog gets closed after click on x icon";
			isEventSuccessful=PerformAction(dicOR.get("BtnUninstallAllEnabled"), Action.Click);
			waitForPageLoaded();
			isEventSuccessful=PerformAction(dicOR.get("btnClose_UnInstallAllDialog"), Action.Click);
			if(isEventSuccessful){
				waitForPageLoaded();
				isEventSuccessful=!PerformAction(dicOR.get("UnInstallAllDialog"), Action.isDisplayed);
				if(isEventSuccessful){
					strActualResult="After Click on X button UnInstallAll Dialog disappeared";
				}
				else{
					strActualResult="UnInstall all dialog did not closed";
				}

			}
			else{
				strActualResult="Unable to click on x  button in UnInstallAll dialog.";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);



			//**************************************************************************//
			// Step 7 : Verify Warning Message on UnInstallAll Dialog
			//**************************************************************************//
			strStepDescription="Verify Warning message on UninstallAll Dialog";
			strExpectedResult="Warning Message displayed:  Are you sure you would like to uninstall all applications? No data will be preserved during this uninstall process.";
			isEventSuccessful=PerformAction(dicOR.get("BtnUninstallAllEnabled"), Action.Click);
			waitForPageLoaded();
			isEventSuccessful=PerformAction(dicOR.get("msgWarning_UnInstallAllDialog"), Action.isDisplayed);

			if(isEventSuccessful){
				warningMessage=GetTextOrValue(dicOR.get("msgWarning_UnInstallAllDialog"), "text");
				System.out.println("message: "+warningMessage);
				if(warningMessage.contains("Are you sure you would like to uninstall all applications? No data will be preserved during this uninstall process.")){
					strActualResult="Expected warning message displayed on dialog: "+warningMessage;
					isEventSuccessful=true;
				}
				else{
					strActualResult="warning message is not as expected :"+warningMessage;
					isEventSuccessful=false;
				}

			}
			else{
				strActualResult="warning message not dispayed";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//**************************************************************************//
			// Step 8 : Click Continue from dialog and verify Apps gets Uninstalled.
			//**************************************************************************//
			strStepDescription="Click on continue from dialog and verify apps uninstalled";
			strExpectedResult="On continue apps gets uninstalled from device";
			isEventSuccessful=PerformAction(dicOR.get("btnContinue_UnInstallAllDialog"), Action.Click);
			waitForPageLoaded();
			if(isEventSuccessful){
				WebDriverWait wait = new WebDriverWait(driver, 200);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='btn btn-info btn-finish']")));
				isEventSuccessful=PerformAction(dicOR.get("btnFinish_UnInstallAllDialog"), Action.Click);
				if(isEventSuccessful){
					PerformAction("browser",Action.Refresh);
					waitForPageLoaded();
					isAppUnInstalled=GetTextOrValue(dicOR.get("installedAppsSection_DeviceDetailsPage"), "text");

					if(isAppUnInstalled.contains("No applications installed")){
						strActualResult="Apps Uninstalled from device after clicking on continue";
						isEventSuccessful=true;
					}
					else{
						strActualResult="Apps are not uninstalled from device";
						isEventSuccessful=false;
					}
				}
				else{
					strActualResult="Unable to click on Finish button";
				}


			}
			else{
				strActualResult="Unable to click on continue";
			}

			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//**************************************************************************//
			// Step 9 : Verify warning message when no apps installed
			//**************************************************************************//
			strStepDescription="When No apps installed Verify warning Message";
			strExpectedResult="When no apps are installed warning message displayed on dialog : No applications are installed on this device.";
			if(isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("BtnUninstallAllEnabled"), Action.Click);
				waitForPageLoaded();
				isEventSuccessful=PerformAction(dicOR.get("msgWarning_UnInstallAllDialog"), Action.isDisplayed);

				if(isEventSuccessful){
					noAppMessage=GetTextOrValue(dicOR.get("msgWarning_UnInstallAllDialog"), "text");
					System.out.println("message: "+noAppMessage);
					if(noAppMessage.contains("No applications are installed on this device.")){
						strActualResult="Expected  message displayed on dialog: "+noAppMessage;
						isEventSuccessful=true;
					}
					else{
						strActualResult=" message is not as expected :"+noAppMessage;
						isEventSuccessful=false;
					}

				}
				else{
					strActualResult="no app message not dispayed";
				}

			}
			else{
				strActualResult="Apps are installed on device so can not verify message";
			}


			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);



		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify UninstallAll Dialog--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
