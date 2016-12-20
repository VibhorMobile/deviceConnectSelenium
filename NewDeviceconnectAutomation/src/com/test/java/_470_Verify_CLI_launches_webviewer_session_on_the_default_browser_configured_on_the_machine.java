package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
import com.common.utilities.WindowNames;

/*
 * JIRA ID --> QA-1073
 */

public class _470_Verify_CLI_launches_webviewer_session_on_the_default_browser_configured_on_the_machine extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="", defaultBrowser="";
	private String xpath = "";
	Object[] Values = new Object[5]; 

	public final void testScript() throws Exception
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
	     	 Values = ExecuteCLICommand("client", "iOS", EmailAddress, Password, "", "web");
	     	 isEventSuccessful = (boolean)Values[4];
	     	 outputText=(String)Values[1];
	     	 deviceName=(String)Values[3];
	     	 defaultBrowser=WindowNames.getDefaultBrowser();
			 if (isEventSuccessful && outputText.contains(defaultBrowser))
				{
				   strActualResult = "web Viewer launched in default browser : " + defaultBrowser;
				}
				else
				{
					strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
				}
			 reporter.ReportStep("Verify web Viewer is launched in default browser" , "Web Viewer should get launched in default browser", strActualResult, isEventSuccessful);
		}
		
		
		//**************************************************************************//
		// Step 3 : Release device.
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", EmailAddress, Password, deviceName, "","","" );
	}
}