package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-842
 */

public class _474_Verify_application_launch_using_app_display_name_or_GUID_or_Application_Identifier extends ScriptFuncLibrary
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
	     	 Values = ExecuteCLICommand("client", "iOS", EmailAddress, Password, "", "deviceControl", "desktop");
	     	 isEventSuccessful = (boolean)Values[4];
	     	 outputText=(String)Values[1];
	     	 deviceName=(String)Values[3];
			 if(isEventSuccessful && outputText.contains("MobileLabs.deviceViewer.exe"))
				{
				   strActualResult = "desktop Viewer launched after connecting to an iOS device using appName";
				}
				else
				{
					strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
				}
			 reporter.ReportStep("Verify desktop deviceViewer is launched " , "User should get connected and desktop deviceviewer should get launched.", strActualResult, isEventSuccessful);
				
		}
		
		
		
		
		
		//**************************************************************************//
		// Step 3 : Release device.
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", EmailAddress, Password, deviceName, "","","" );
		Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
		
		//**************************************************************************//
		// Step 4 : Go to Application's Page and search deviceControl GUID
		//**************************************************************************//
		isEventSuccessful=GoToApplicationsPage();
		searchDevice_DI("deviceControl");
		GoToFirstAppDetailsPage();
		waitForPageLoaded();
		String GUID=GetAppGUID();
		isEventSuccessful=GoToDevicesPage();
		
		//**************************************************************************//
		// Step 5 : Connect to an iOS device using app GUID and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("client", "iOS", EmailAddress, Password, "", GUID, "desktop");
	     	 isEventSuccessful = (boolean)Values[4];
	     	 outputText=(String)Values[1];
	     	 deviceName=(String)Values[3];
			 if(isEventSuccessful && outputText.contains("MobileLabs.deviceViewer.exe"))
				{
				   strActualResult = "desktop Viewer launched after connecting to an iOS device using app GUID: "+GUID;
				}
				else
				{
					strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
				}
			 reporter.ReportStep("Verify desktop deviceViewer is launched " , "User should get connected and desktop deviceviewer should get launched.", strActualResult, isEventSuccessful);
				
		}
		
		
		
		
		
		//**************************************************************************//
		// Step 6 : Release device.
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", EmailAddress, Password, deviceName, "","","" );
		Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
		
		
		//**************************************************************************//
		// Step 7 : Connect to an iOS device using app GUID and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("client", "iOS", EmailAddress, Password, "", "deviceControl", "web");
	     	 isEventSuccessful = (boolean)Values[4];
	     	 outputText=(String)Values[1];
	     	 deviceName=(String)Values[3];
	     	 if(isEventSuccessful && outputText.contains("opened"))
				{
				   strActualResult = "Web Viewer launched after connecting to an iOS device using appName";
				}
				else
				{
					strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
				}
	     	reporter.ReportStep("Verify web deviceViewer is launched " , "User should get connected and web deviceviewer should get launched.", strActualResult, isEventSuccessful);
		}
		
		
		
		
		
		//**************************************************************************//
		// Step 8 : Release device.
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", EmailAddress, Password, deviceName, "","","" );
		
		
		//**************************************************************************//
		// Step 9 : Connect to an iOS device using app GUID and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("client", "iOS", EmailAddress, Password, "", GUID, "web");
	     	 isEventSuccessful = (boolean)Values[4];
	     	 outputText=(String)Values[1];
	     	 deviceName=(String)Values[3];
	     	 if(isEventSuccessful && outputText.contains("opened"))
				{
				   strActualResult = "Web Viewer launched after connecting to an iOS device using app GUID: "+GUID;
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
		reporter.ReportStep("Verify Web deviceViewer is launched " , "User should get connected and web deviceviewer should get launched.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
		
		
		
		//**************************************************************************//
		// Step 10 : Release device.
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", EmailAddress, Password, deviceName, "","","" );
	}
}