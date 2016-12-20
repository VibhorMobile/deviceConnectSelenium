package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1338
 */

public class _469_Verify_Launch_invalid_CLI_Client_value extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
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
	     	 Values = ExecuteCLICommand("client", "iOS", EmailAddress, Password, "", "webviewer");
	     	 isEventSuccessful = (boolean)Values[2];
	     	 outputText=(String)Values[0];
	     	 deviceName=(String)Values[3];
			 if (isEventSuccessful && outputText.contains("not a valid value"))
				{
				   strActualResult = "Using webviewer as -client option unable to connect";
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
		reporter.ReportStep("Verify web Viewer is launched using -client webviewer" , "Throw exception 'webviewer is not a valid option'", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
		
		//**************************************************************************//
		// Step 3 : Release device.
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", EmailAddress, Password, deviceName, "","","" );
		Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
	}
}