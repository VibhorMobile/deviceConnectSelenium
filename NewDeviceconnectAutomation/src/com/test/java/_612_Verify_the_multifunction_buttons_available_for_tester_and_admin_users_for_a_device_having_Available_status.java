package com.test.java;

import java.util.ArrayList;
import java.util.List;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-688
 */
public class _612_Verify_the_multifunction_buttons_available_for_tester_and_admin_users_for_a_device_having_Available_status extends ScriptFuncLibrary {
	
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	//Object[] Values = new Object[5]; 
	List<String> l = new ArrayList<String>();
	private String strActualResult = "", strstepDescription = "",strexpectedResult, Password , version;
	Object[] Values = new Object[5]; 
	ArrayList<String> objects=new ArrayList<String>();
	
	public final void testScript()
	{
		
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");
				
		objects.add("deviceControl");
		objects.add("View Log");
		objects.add("Reboot");
		objects.add("Disable");

		//*************************************************************//                     
		//** Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		isEventSuccessful = Login();


		//**********************************************************//
		//** Step 2 - Select status from Filters drop down **********//
		//**********************************************************//     
		isEventSuccessful =  selectStatus("Available");
		PerformAction("btnRefresh_Devices", Action.Click);
				
		//****************************************************************************************//
		//** Step 3 - Verify deviceControl, View Log, Disable and Reboot button displayed for admin **//
		//****************************************************************************************//
		strstepDescription = "Verify deviceControl, View Log, Disable and Reboot button displayed for admin.";
		strexpectedResult =  "deviceControl, View Log, Disable and Reboot button should be displayed for admin.";
		if (isEventSuccessful)
		{	
			isEventSuccessful = VerifyDeviceOptions(objects,"Available",1,"list");
			if (isEventSuccessful)
			{
				strActualResult = "deviceControl, View Log, Disable and Reboot button dispalyed for admin.";
			}
			else
			{
				strActualResult = "deviceControl, View Log, Disable and Reboot button did not displayed for admin.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		//objects.remove("Reboot");
		objects.remove("Disable");
		
		//****************************************************************************************//
		//** Step 4 - Logout **//
		//****************************************************************************************//
		strstepDescription = "Verify user logout from application";
		strexpectedResult =  "User should be able to logout.";
		isEventSuccessful=Logout();
		if (isEventSuccessful)
		{
			strActualResult = "User logout from application.";
		}
		else
		{
			strActualResult = "User is not able to logout.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
						
		//*************************************************************//                     
		// Step 5 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid test user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = LoginToDC(testerEmailAddress, testerPassword);
		if (isEventSuccessful)
		{	
			reporter.ReportStep(strstepDescription, strexpectedResult, "Logged in successfully with " + testerEmailAddress, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
					
			
		//**********************************************************//
		//** Step 6 - Select status from Filters dropdown **********//
		//**********************************************************//     
		isEventSuccessful =  selectStatus("Available");
		PerformAction("btnRefresh_Devices", Action.Click);	
				
		//****************************************************************************************//
		//** Step 7 - Verify Release, View Log and Reboot button displayed for tester **//
		//****************************************************************************************//
		strstepDescription = "Verify deviceControl, 'View Log' and Reboot button displayed for tester.";
		strexpectedResult =  "deviceControl, 'View Log' and Reboot button should be displayed for tester.";
		if (isEventSuccessful) 
		{
			isEventSuccessful = VerifyDeviceOptions(objects,"Available",1,"list");
			if(isEventSuccessful)
			{
				strActualResult = "deviceControl, 'View Log' and Reboot button displayed for tester.";
			}
			else
			{
				strActualResult = "deviceControl, 'View Log' and Reboot button did not displayed for tester.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
	}
}
