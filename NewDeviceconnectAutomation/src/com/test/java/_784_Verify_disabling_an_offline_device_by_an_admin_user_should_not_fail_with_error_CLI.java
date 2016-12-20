package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-134
 */
public class _784_Verify_disabling_an_offline_device_by_an_admin_user_should_not_fail_with_error_CLI extends ScriptFuncLibrary {

	
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "", deviceNameUI;
	Object[] Values = new Object[5]; 


	public final void testScript() 
	{

		
			String EmailAddress = dicCommon.get("EmailAddress");
			String Password = dicCommon.get("Password");

			//*************************************************************//                     
			// Step 1 : login to deviceConnect with test user.
			//*************************************************************//                     
			strStepDescription = "Login to deviceConnect with valid user.";
			strExpectedResult = "User should be logged in successfully.";
			isEventSuccessful = Login();

			//**************************************************************************//
			// Step 2: Go to devices and get Offline Device Name
			//**************************************************************************//
			GoToDevicesPage();
			selectStatus("Offline");
			GoTofirstDeviceDetailsPage();
			deviceNameUI=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");


			//**************************************************************************//
			// Step 3 : Disable device using CLI
			//**************************************************************************//

			Values = ExecuteCLICommand("disable", "Android", EmailAddress, Password, deviceNameUI, "");
			isEventSuccessful = (boolean)Values[2];
			if(isEventSuccessful){
				strActualResult="Disabled command executed without any error";
			}
			else{
				strActualResult="device not disabled";
			}
			reporter.ReportStep("Disable device using CLI" , "Command should be executed without any error", strActualResult, isEventSuccessful);

			//**************************************************************************//
			// Step 4 : Extract device history report in csv format in local timezone
			//**************************************************************************//
			PerformAction(dicOR.get("lnkShowDetails"),Action.Click);
			isEventSuccessful = VerifyOnDeviceDetailsPage("Status ", "Disable");
			if (isEventSuccessful)
			{
				strActualResult ="Device is Disabled";
			}
			else
			{
				strActualResult = "Device status is not Disabled."+ strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify device is disabled" , "Device should be disabled", strActualResult, isEventSuccessful);

		

	}

}
