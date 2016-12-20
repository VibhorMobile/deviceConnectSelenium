package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1863
 */
public class _640_Verify_automatic_Uninstall_All_on_releasing_using_uninstallAll_using_CLI extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	Object[] Values = new Object[5]; 
	String [] Entitlement={"Device application management","Device reboot"};
	Boolean [] value={true, true};
	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		
		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful = Login();


		//*************************************************************//                     
		// Step 2 : Go to user page and set entitlement .
		//*************************************************************// 
		isEventSuccessful=GoToUsersPage();
		if(isEventSuccessful)
		{
			strstepDescription = "Set entitlements for role: Tester";
			strexpectedResult = "User should be able to set entitlements.";
			isEventSuccessful=setUserRoleSettings("Tester", Entitlement, value);
			if(isEventSuccessful)
			{
				strActualResult = "User is able to set entitlements.";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		//****************************************************************************************//
		//** Step 3 - Logout **//
		//****************************************************************************************//
		if(isEventSuccessful)
		{
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
		}
		
		//*************************************************************//                     
		// Step 4 : login to deviceConnect with test user.
		//*************************************************************//  
		if(isEventSuccessful)
		{
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
		}
				
		//**************************************************************************//
		// Step 5 : Connect to  device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("run", "iOS", testerEmailAddress, testerPassword, "", "");
	     	 isEventSuccessful = (boolean)Values[2];
	     	 deviceName=(String)Values[3];
			 if (isEventSuccessful)
				{
				   strActualResult = "device Viewer launched after connecting to  device:  " + Values[3];
				}
				else
				{
				   strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
				}
			 reporter.ReportStep("Verify device Viewer is launched " , "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
				
		}
		
		
		//**************************************************************************//
		// Step 5 : Reset device via CLI command.
		//**************************************************************************//
		Values = ExecuteCLIResetCommands("releaseuninstall", "iOS", testerEmailAddress, testerPassword, deviceName, "","");
		isEventSuccessful = (boolean)Values[2];
		outputText=(String)Values[0];
		if (isEventSuccessful && !(outputText.contains("User does not have required entitlement")))
		{
			strActualResult = "Device released and app uninstall.";
		}
		else
		{
			strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
		}
		reporter.ReportStep("Verify device get released." , "Device should get released.", strActualResult, isEventSuccessful);
			
		
		
		
		//**************************************************************************/
		// Step 6 : Release device.
		//**************************************************************************//
		if(!deviceName.isEmpty())
		{
			ExecuteCLICommand("release", "iOS", testerEmailAddress, testerPassword, deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
		}
		
		//**************************************************************************/
		// Step 7 : Verify all applications gets uninstalled.
		//**************************************************************************//
		isEventSuccessful =  selectStatus("Available,In Use,Offline");	
		
		if(isEventSuccessful)
		{
			isEventSuccessful=GoToSpecificDeviceDetailsPage(deviceName);
			if(isEventSuccessful)
			{
				strstepDescription = "Verify application list is empty.";
				strexpectedResult = "Application list should be empty.";
				if(getelementsList(dicOR.get("deviceDetailsUnInstallFirstApp").replace("tr[1]", "tr[*]")+"/td/a[2]").size()<1)
				{
					strActualResult="Apllication list is empty";
					reporter.ReportStep(strstepDescription ,strexpectedResult, strActualResult, true);
				}
				else
				{
					strActualResult="Application list is not empty";
					reporter.ReportStep(strstepDescription , strexpectedResult, strActualResult, false);
				}
			}
		}
		
		
	}
}