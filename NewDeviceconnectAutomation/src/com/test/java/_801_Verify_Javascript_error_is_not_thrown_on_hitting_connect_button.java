package com.test.java;

import java.util.regex.Pattern;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-985
 */
public class _801_Verify_Javascript_error_is_not_thrown_on_hitting_connect_button extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String  strActualResult = "",pageHeader,onlineSince,offlineSince, outputText = "",InUseSince,deviceNameUI,deviceName;
	private String [] Options={"Connected Since","Offline Since","In Use Since"};

	Object[] Values = new Object[5]; 
	public final void testScript() throws InterruptedException
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();

			//**********************************************************//
			// Step 2 - Go to Devices and First Device details Page
			//**********************************************************//  
			GoToDevicesPage();
			GoTofirstDeviceDetailsPage();

			//**********************************************************//
			// Step 2 - Verify on clicking connect no error message displayed ,Launch Dialog gets opened
			//**********************************************************//  
			isEventSuccessful=PerformAction(dicOR.get("btnConnect"), Action.Click);
			if(isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("eleAppLaunchDialog"), Action.isDisplayed);
				if(isEventSuccessful){
					isEventSuccessful=PerformAction("//th[text()='Application']", Action.isDisplayed);
					if(isEventSuccessful){
						isEventSuccessful=PerformAction("//th[text()='Version']", Action.isDisplayed);
						if(isEventSuccessful){
							strActualResult="App Launch Dialog Opened after clicking on connect";
						}
						else{
							strActualResult="Version Header is not Displayed";
						}
					}
					else{
						strActualResult="Application Header not present in app launch Dialog";
					}
				}
				else{
					strActualResult="App Launch Dialog did not displayed";
				}

			}
			else{
				strActualResult="Unable to click on connect button";
			}
			reporter.ReportStep("Verify Offline since is in valid date time format", "Oflline since should be in MM/DD/YYYY HH:MM:SS AM/PM format", strActualResult, isEventSuccessful);
			

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

}
