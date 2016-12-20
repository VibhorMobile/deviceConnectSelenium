package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-884
 */

public class _461_Verify_CLI_forward_command_accept_2_or_1_values_to_listen_specified_device extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="", cmdText="";
	private String xpath = "";
	Object[] Values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login();


		//**************************************************************************//
		// Step 2 : Connect to an iOS device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("forward", "iOS", EmailAddress, Password, "", "");
	     	 isEventSuccessful = (boolean)Values[4];
	     	 outputText=(String)Values[1];
	     	 cmdText=(String)Values[0];
	     	 deviceName=(String)Values[3];
			 if (isEventSuccessful && cmdText==null && outputText.contains("MobileLabs.DeviceConnect.Cli"))
				{
				   strActualResult = "Viewer launched after connecting to an iOS device:  " + Values[3] + " & processfound : " +  Values[1];
				}
				else
				{
					strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
				}
			
		}
		else
		{
			return; // Return if in use is not selected.
		}
		reporter.ReportStep("Connect to an iOS device and verify deviceViewer is launched " , "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
		
		//**************************************************************************//
		// Step 3 : Release device and kill process 'MobileLabs.DeviceConnect.Cli.exe'.
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", EmailAddress, Password, deviceName, "","","" );
		Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.DeviceConnect.Cli.exe");
		
		
		//**************************************************************************//
		// Step 4 : Connect to an Android device and verify process MobileLabs.DeviceConnect.Cli.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("forward", "Android", EmailAddress, Password, "", "");
	     	 isEventSuccessful = (boolean)Values[4];
	     	 outputText=(String)Values[1];
	     	 cmdText=(String)Values[0];
	     	 deviceName=(String)Values[3];
			 if (isEventSuccessful && cmdText==null && outputText.contains("MobileLabs.DeviceConnect.Cli"))
				{
				   strActualResult = "Viewer launched after connecting to an iOS device:  " + Values[3] + " & processfound : " +  Values[1];
				}
				else
				{
					strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
				}
			
		}
		else
		{
			return; // Return if in use is not selected.
		}
		reporter.ReportStep("Connect to an android device and verify deviceViewer is launched " , "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
		
		//**************************************************************************//
		// Step 5 : Release device and kill process 'MobileLabs.DeviceConnect.Cli.exe'.
		//**************************************************************************//
		ExecuteCLICommand("release", "Android", EmailAddress, Password, deviceName, "","","" );
		Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.DeviceConnect.Cli.exe");
	}
}