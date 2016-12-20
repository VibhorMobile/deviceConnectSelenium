package com.test.java;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-1586
 */
public class _756_dC_web_Verify_device_details_page_of_the_Offline_Disable_devices extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,appName,appNameEdited,deviceStatus;
	Object[] Values = new Object[5]; 
	boolean isRetained,isRebooted;

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
			// Step 2 : Go to Devices Page 
			//*************************************************************//
			GoToDevicesPage();


			//**************************************************************************//
			// Step 3 : Go to Details Page of Any offline Device and Verify page opened
			//**************************************************************************//
			strStepDescription="Go to offline and Verify page is not blank";
			strExpectedResult="Device details page opened";
			selectStatus("Offline");
			GoTofirstDeviceDetailsPage();
			isEventSuccessful=PerformAction(dicOR.get("lnkShowDetails"), Action.isDisplayed);
			if(isEventSuccessful){
				PerformAction(dicOR.get("lnkShowDetails"), Action.Click);
				isEventSuccessful=PerformAction(dicOR.get("eleStatus_DeviceDetailsPage"), Action.isDisplayed);
				if(isEventSuccessful){
					strActualResult="Device details page opened";	
				}
				else{
					strActualResult="Device details page not opened correctly";
				}
			}
			else{
				strActualResult="Device details page not opened correctly";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 4 : Go to Disabled device page and Verify no blank page opens
			//**************************************************************************//
			strStepDescription="Go to offline and Verify page is not blank";
			strExpectedResult="Device details page opened";
			GoToDevicesPage();
			selectStatus("Disable");
			isEventSuccessful = !VerifyMessage_On_Filter_Selection();
			if(isEventSuccessful){
				GoTofirstDeviceDetailsPage();
				isEventSuccessful=PerformAction(dicOR.get("lnkShowDetails"), Action.isDisplayed);
				if(isEventSuccessful){
					PerformAction(dicOR.get("lnkShowDetails"), Action.Click);
					isEventSuccessful=PerformAction(dicOR.get("eleStatus_DeviceDetailsPage"), Action.isDisplayed);
					if(isEventSuccessful){
						strActualResult="Device details page opened";	
					}
					else{
						strActualResult="Device details page not opened correctly";
					}
				}
				else{
					strActualResult="Device details page not opened correctly";
				}

			}
			else{
				strActualResult="No Disable devices are available";
			}

			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);



		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}





}
