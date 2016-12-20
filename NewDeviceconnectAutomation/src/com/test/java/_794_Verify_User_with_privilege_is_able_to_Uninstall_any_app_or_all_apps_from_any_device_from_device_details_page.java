 package com.test.java;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Jira Test Case Id: QA-1528,407
 */
public class _794_Verify_User_with_privilege_is_able_to_Uninstall_any_app_or_all_apps_from_any_device_from_device_details_page extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,inUse,outputText,appAreaText,appUninstallMsg;
	Object[] Values = new Object[5]; 
	boolean flag,isAndroidSelected;






	public final void testScript() throws InterruptedException, IOException
	{
		try{

			//*************************************************************//                     
			// Step 1 : login to deviceConnect with Admin User
			//*************************************************************//  


			isEventSuccessful = Login();
			//*************************************************************//      
			// Step 2 : Go to Device Page and Open details page of first device displayed
			//*************************************************************//
			GoToDevicesPage();
			GoTofirstDeviceDetailsPage();

			//**************************************************************************//
			// Step 3 : If Apps are installed click on uninstall all and verify apps uninstalled from device.
			//**************************************************************************//
			waitForPageLoaded();
			flag=PerformAction(dicOR.get("NoAppsInstalled_DeviceDetailsPage"), Action.isDisplayed);
			if(flag){
				isEventSuccessful=installAppOnDevice("deviceControl");
			}
			else{
				waitForPageLoaded();
				isEventSuccessful=PerformAction(dicOR.get("BtnUninstallAllEnabled"), Action.Click);
				if (isEventSuccessful){
					waitForPageLoaded();
					isEventSuccessful=PerformAction(dicOR.get("ContinueButton_ConfirmationDialog"), Action.isDisplayed);
					if(isEventSuccessful){
						isEventSuccessful=PerformAction(dicOR.get("ContinueButton_ConfirmationDialog"), Action.Click);
						if(isEventSuccessful){
							waitForPageLoaded();
							WebDriverWait wait = new WebDriverWait(driver, 50);
							wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Finish']"))); 
							isEventSuccessful=PerformAction(dicOR.get("finishbtninstallDialog"), Action.isDisplayed);
							if(isEventSuccessful){
								isEventSuccessful=PerformAction(dicOR.get("finishbtninstallDialog"), Action.Click);
								if(isEventSuccessful){
									waitForPageLoaded();
									appAreaText=GetTextOrValue(dicOR.get("NoAppsInstalled_DeviceDetailsPage"), "text");
									if(appAreaText.equals("No applications installed")){
										strActualResult="All apps uninstalled after clicking on Uninstall All button text at app area is:"+appAreaText;
										isEventSuccessful=true;
									}
									else{
										strActualResult="All apps not  uninstalled after clicking on Uninstall All button"+appAreaText;
										isEventSuccessful=false;
									}
								}
								else{
									strActualResult="Unable to click on finish button";
								}
							}
							else{
								strActualResult="Finish button not displayed";
							}
						}
						else{
							strActualResult="Unable to click on continue button";
						}

					}
					else{
						strActualResult="Continue button not displayed";;
					}
				}
				else{
					strActualResult="Unable to click on uninstall all button";
				}
				reporter.ReportStep("Uninstall all apps using uninstall all button", "All apps should be uninstalled", strActualResult, isEventSuccessful);
			}
			
			//**************************************************************************//
			// Step 4 : Install an app and Uninstall it using uninstall button
			//**************************************************************************//
			waitForPageLoaded();
			isEventSuccessful=installAppOnDevice("deviceControl");
			if(isEventSuccessful){
				waitForPageLoaded();
				Thread.sleep(10000);
				isEventSuccessful=PerformAction(dicOR.get("UninstallButtonFirstApp_DeviceDetailsPage"), Action.Click);
				if(isEventSuccessful){
					waitForPageLoaded();
					isEventSuccessful=PerformAction(dicOR.get("ContinueButton_ConfirmationDialog"), Action.Click);
					if(isEventSuccessful){
						WebDriverWait wait = new WebDriverWait(driver, 50);
						wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='notification-content']"))); 
						appUninstallMsg=GetTextOrValue(dicOR.get("appUninstalledMsg_Notification"), "text");
						if(appUninstallMsg.equals("Application uninstalled successfully.")){
							strActualResult="app uninstalledafter clicking on Uninstallbutton text at notification area is:"+appUninstallMsg;
							isEventSuccessful=true;
						}
						else{
							strActualResult="app not uninstalled after clicking on Uninstallbutton, text at notification area is:"+appUninstallMsg;
							isEventSuccessful=false;
						}
					}
					else{
						strActualResult="Unable to click on continue button";
					}
				}
				else{
					strActualResult="Unable to click on Uninstall button";
				}
			}
			else{
				strActualResult="Unable to install devicecontrol app on device";
			}
			reporter.ReportStep("Uninstall  app using uninstall button", " app should be uninstalled", strActualResult, isEventSuccessful);
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify checkbox is unchecked after refresh--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}