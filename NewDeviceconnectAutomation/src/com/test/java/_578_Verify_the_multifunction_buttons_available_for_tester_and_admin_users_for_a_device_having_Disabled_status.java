package com.test.java;

import java.lang.reflect.Array;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: Last week of January
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-979
 */
public class _578_Verify_the_multifunction_buttons_available_for_tester_and_admin_users_for_a_device_having_Disabled_status extends ScriptFuncLibrary

{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	

	public final void testScript()
	
	{
		// Step 1 : login to deviceConnect with valid user credentials.
		isEventSuccessful = Login();
	
		//*********************************************//
		// Step 2 : Select removed from status filters //
		//*********************************************//
		isEventSuccessful =  selectStatus_DI("Disabled");
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("connect", "disable", "list");
		if (!isEventSuccessful) 
		{
			isEventSuccessful = DisplayDisabledDevices();
		}
	
		//************************************************************************************//
		// Step 3 : Click on select all checkbox on top of all devices on devices index page. //
		//************************************************************************************//
		isEventSuccessful = (boolean)selectAllDevicesCheckbox_DI()[0];
			
		// Step 4 : Verify the button for disabled devices.
		strStepDescription = "Verify the buttons for disabled devices.";
		strExpectedResult = "Enable & Remove buttons should be displayed.";
		String[] buttons = {"Enable","Remove"};
		for(int i= 0 ; i<buttons.length;i++)
		{
			isEventSuccessful = PerformAction("//button[text()='buttonname']".replace("buttonname", buttons[i]), Action.isDisplayed);
			if(isEventSuccessful)
			{
				strActualResult = buttons[i] + "is displayed";
				reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			}
			else
			{
				strActualResult = buttons[i] + "is not displayed";
				reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			}
		}
			
		// Step 5 : Logout from the Application'
		isEventSuccessful = LogoutDC();
	   
		//**************************************************************************//
		// Step 6 : Login again as tester.
		//**************************************************************************//
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");
		
		strStepDescription = "Login to deviceConnect with valid test user.";
		strExpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login(EmailAddress, Password);
	 		
		//*********************************************//
		// Step 7 : Select removed from status filters //
		//*********************************************//
		isEventSuccessful =  selectStatus_DI("Disabled");
		if (!isEventSuccessful) 
		{
			isEventSuccessful = DisplayDisabledDevices();
		}
	 		
		//************************************************************************************//
		// Step 8 : Click on select all checkbox on top of all devices on devices index page. //
		//************************************************************************************//
	 	isEventSuccessful = (boolean)selectAllDevicesCheckbox_DI()[0];
	 				
	 	// Step 9 : Verify the button for disabled devices.
	 	strStepDescription = "Verify the buttons for disabled devices.";
	 	strExpectedResult = "Enable button should not be displayed.";
	 	isEventSuccessful = PerformAction("//button[text()='Enable']", Action.isDisplayed);
	 	if(!isEventSuccessful)
	 	{
	 		isEventSuccessful=true;
	 		strActualResult = "Enable did not  displayed";
	 		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	 	}
	 	else
	 	{
	 		isEventSuccessful=false;
	 		strActualResult = "Enable displayed";
	 		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	 	}
	}	
}
