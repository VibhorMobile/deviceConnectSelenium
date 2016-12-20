package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-82
 */

public class _132_Verify_tester_is_able_to_view_all_devices extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String[] arrDeviceStatus_Single = {"Available", "In Use", "Offline", "Disabled"};
	private int AdminNoOfDevices;
	private int TesterNoOfDevices;
	private boolean flag = false;
	
	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String AdminEmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");
		String deviceCountAdmin = "", deviceCountTester = "";

		//*************************************************************//                     
		// Step 1 : Login to deviceConnect with Admin user and find total devices displayed.
		//*************************************************************//                     
		isEventSuccessful = Login();
 		
		//*************************************************************//                     
		// Step 2 : Select all statuses and count number of Devices displayed 
		//*************************************************************//   
			isEventSuccessful = selectStatus("Available,In Use,Offline,Disabled");
			AdminNoOfDevices = getelementCount(dicOR.get("eleDevicesHolderListView")); 
			
			isEventSuccessful = !deviceCountAdmin.equals("0");
			if (isEventSuccessful)
			{
				strActualResult = "Devices count is not 0 " + deviceCountAdmin;
			}
			else
			{
				strActualResult = "Devices Count is 0 " + deviceCountAdmin;
			}
			reporter.ReportStep("Verify device count is not zero", "Device count should not be zero", strActualResult, isEventSuccessful);	
			
		//*************************************************************//                     
		// Step 3 : LogOut Admin User 
		//*************************************************************//   
			isEventSuccessful =Logout();
		
		//*************************************************************//                     
		// Step 4 : login to deviceConnect with test user.
		//*************************************************************//                     
			isEventSuccessful =Login(testerEmailAddress,testerPassword);

		//*************************************************************//                     
		// Step 5: Select all statuses and count number of Devices displayed 
		//*************************************************************//                     
			isEventSuccessful = selectStatus("Available,In Use,Offline,Disabled");
			TesterNoOfDevices = getelementCount(dicOR.get("eleDevicesHolderListView")); 
			
			isEventSuccessful = !deviceCountAdmin.equals("0");
			if (isEventSuccessful)
			{
				strActualResult = "Devices count is not 0 " + deviceCountAdmin;
			}
			else
			{
				strActualResult = "Devices Count is 0 " + deviceCountAdmin;
			}
		reporter.ReportStep("Verify device count is not zero", "Device count should not be zero", strActualResult, isEventSuccessful);
		
		//*************************************************************//                     
		// Step 6: Verify number of devices visible for Test User and Admin User are same 
		//*************************************************************// 
		isEventSuccessful = (AdminNoOfDevices==TesterNoOfDevices);
		if(isEventSuccessful)
			strActualResult = "Device count for Admin and Tester user are equal i.e " + TesterNoOfDevices; 
		else
		strActualResult = "Device count for Admin is "+ AdminNoOfDevices + " and Device count for Tester is " + TesterNoOfDevices + " which are not the same";	
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}