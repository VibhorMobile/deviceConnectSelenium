	package com.test.java;

import java.io.IOException;
import java.util.ArrayList;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-874
 */

public class _555_Verify_the_multifunction_buttons_available_for_tester_and_admin_users_for_retained_device_having_In_Use_status extends ScriptFuncLibrary
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
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");
		
		objects.add("Release");
		objects.add("View Log");
		objects.add("Reboot");
		objects.add("Disable");

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		isEventSuccessful = Login();


		//**************************************************************************//
		// Step 2 : Connect to an iOS device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		strstepDescription = "Launch desktop viewer on connecting device.";
		strexpectedResult = "Desktop viewer should be launched on connecting devices.";
		if (isEventSuccessful)
		{	
			Values = ExecuteCLICommand("client", "iOS", EmailAddress, Password, "", "desktop");
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
			reporter.ReportStep("Verify desktop deviceViewer is launched " , "User should get connected and desktop deviceviewer should get launched.", strActualResult, isEventSuccessful);

		}
		
		
		//**********************************************************//
		//** Step 3 - Select status from Filters dropdown **********//
		//**********************************************************//     
		isEventSuccessful =  selectStatus("In Use");
		PerformAction("btnRefresh_Devices", Action.Click);
		
		//**********************************************************//
		//** Step 4 - Search device using search text box and verify it **********//
		//**********************************************************//                                   
		searchDevice(deviceName, "devicename");
		
		//****************************************************************************************//
		//** Step 5 - Verify Release, View Log, Disable and Reboot button displayed for admin **//
		//****************************************************************************************//
		strstepDescription = "Verify Release, View Log, Disable and Reboot button displayed for admin.";
		strexpectedResult =  "Release, View Log, Disable and Reboot button should be displayed for admin.";
		if (isEventSuccessful)
		{	
			isEventSuccessful = VerifyDeviceOptions(objects,"In Use",1,"list");
			if (isEventSuccessful)
			{
				strActualResult = "Release, View Log, Disable and Reboot button dispalyed for admin.";
			}
			else
			{
				strActualResult = "Release, View Log, Disable and Reboot button did not displayed for admin.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//**************************************************************************//
		// Step 6 : Release device.
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", EmailAddress, Password, deviceName, "","","" );
		Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
		objects.remove("Disable");
		
		//****************************************************************************************//
		//** Step 7 - Logout **//
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
		// Step 8 : login to deviceConnect with test user.
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
		// Step 9 : Connect to an iOS device and verify deviceViewer is launched on the windows desktop.
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
		
		//**********************************************************//
		//** Step 10 - Select status from Filters dropdown **********//
		//**********************************************************//     
		isEventSuccessful =  selectStatus("In Use");
		PerformAction("btnRefresh_Devices", Action.Click);	
		
		//**********************************************************//
		//** Step 11 - Search device using search text box and verify it **********//
		//**********************************************************//                                   
		searchDevice(deviceName, "devicename");
						
		//****************************************************************************************//
		//** Step 12 - Verify Release, View Log and Reboot button displayed for tester **//
		//****************************************************************************************//
		strstepDescription = "Verify Release, View Log and Reboot button displayed for tester.";
		strexpectedResult =  "Release, View Log and Reboot button should be displayed for tester.";
		if (isEventSuccessful) 
		{
			isEventSuccessful = VerifyDeviceOptions(objects,"In Use",1,"list");
			if(isEventSuccessful)
			{
				strActualResult = "Release, View Log and Reboot button dispalyed for tester.";
			}
			else
			{
				strActualResult = "Release, View Log and Reboot button did not displayed for tester.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//**************************************************************************//
		// Step 13 : Release device.
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", testerEmailAddress, testerPassword, deviceName, "","","" );
		Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
	}
}