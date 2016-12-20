package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-716
 */
public class _752_Verify_battery_icon_should_not_be_displayed_for_offline_devices extends ScriptFuncLibrary {
	

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,appName,appNameEdited,deviceStatus;
	Object[] Values = new Object[5]; 
	boolean isBatteryIconDisplayed;

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
 			// Step 2 : Go to Devices Page and open offline device
 			//*************************************************************//
			GoToDevicesPage();
			selectPlatform("iOS");
			selectStatus("Offline");
			GoTofirstDeviceDetailsPage();
			

			//**************************************************************************//
			// Step 3 : Verify Battery icon not displayed for device
			//**************************************************************************//
			strStepDescription = "Verify battery icon not displayed for device";
			strExpectedResult = "Battery icon not displayed for device";
			
			isBatteryIconDisplayed=PerformAction(dicOR.get("eleBatteryStatusIcon"), Action.isDisplayed);
			if(isBatteryIconDisplayed){
				strActualResult="BatterIcon Displyaed for offline device";
				isEventSuccessful=false;
			}
			else{
				strActualResult="BatterIcon not Displyaed for offline device";
				isEventSuccessful=true;
			}
			 
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			 
			
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}





}
