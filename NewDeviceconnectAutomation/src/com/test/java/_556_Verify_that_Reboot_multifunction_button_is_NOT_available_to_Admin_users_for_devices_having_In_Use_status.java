package com.test.java;

import java.io.IOException;
import java.util.ArrayList;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-129
 */

public class _556_Verify_that_Reboot_multifunction_button_is_NOT_available_to_Admin_users_for_devices_having_In_Use_status extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	Object[] Values = new Object[5]; 
	ArrayList<String> objects=new ArrayList<String>();

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");
		
		objects.add("Reboot");
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
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
			
		//**************************************************************************//
		// Step 2 : Connect to an iOS device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		strstepDescription = "Launch desktop viewer on connecting device.";
		strexpectedResult = "Desktop viewer should be launched on connecting devices.";
		if (isEventSuccessful)
		{	
			Values = ExecuteCLICommand("client", "iOS", testerEmailAddress, testerPassword, "", "desktop");
			isEventSuccessful = (boolean)Values[4];
			outputText=(String)Values[1];
			deviceName=(String)Values[3];
			if (isEventSuccessful && outputText.contains("MobileLabs.deviceViewer.exe"))
			{
				strActualResult = "desktop Viewer launched after connecting to an iOS device:  " + Values[3] + " & processfound : " +  Values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
					
		}
		else
		{
			return; 
		}
		reporter.ReportStep("Verify desktop deviceViewer is launched " , "User should get connected and desktop deviceviewer should get launched.", strActualResult, isEventSuccessful);
		PerformAction("btnRefresh_Devices", Action.Click);
		
		//****************************************************************************************//
		//** Step 3 - Logout **//
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
		// Step 4 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful=Login();
		
		//**********************************************************//
		//** Step 5 - Select status from Filters dropdown **********//
		//**********************************************************//     
		isEventSuccessful =  selectStatus("In Use");
		PerformAction("btnRefresh_Devices", Action.Click);	
		
		//**********************************************************//
		//** Step 6 - Search device using search text box and verify it **********//
		//**********************************************************//                                   
		searchDevice(deviceName, "devicename");
						
		//****************************************************************************************//
		//** Step 7 - Verify Reboot button did not displayed for admin **//
		//****************************************************************************************//
		strstepDescription = "Verify Reboot button did not displayed for admin.";
		strexpectedResult =  "Reboot button should not be displayed for admin.";
		if (isEventSuccessful) 
		{
			isEventSuccessful = VerifyDeviceOptions(objects,"In Use",1,"list");
			if(!isEventSuccessful)
			{
				strActualResult = "Reboot button did not dispalyed for admin.";
				isEventSuccessful=true;
			}
			else
			{
				strActualResult = "Reboot button displayed for admin.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//**************************************************************************//
		// Step 8 : Release device.
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", testerEmailAddress, testerPassword, deviceName, "","","" );
		Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
	}
}