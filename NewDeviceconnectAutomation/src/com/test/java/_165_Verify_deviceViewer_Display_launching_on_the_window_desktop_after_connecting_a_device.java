package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

public class _165_Verify_deviceViewer_Display_launching_on_the_window_desktop_after_connecting_a_device extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",deviceName="";
	Object[] Values = new Object[4]; 

	public final void testScript()
	{
		//**************************************************************************//
		//Step 1 - : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		isEventSuccessful = Login();

		
		//**************************************************************************//
		// Step 2 : Connect to an android device and verify Ai display is launched on the windows desktop.
		//**************************************************************************//

		Values = ExecuteCLICommand("run", "Android");
		isEventSuccessful = (boolean)Values[2];
		deviceName=(String)Values[3];
		if (isEventSuccessful)
		{
			strActualResult = "Viewer launched after connecting to an Android device:  " + Values[0] + " & processfound : " +  Values[1];
		
		}
		else
		{
			strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
		}

		reporter.ReportStep("Connect to an android device and verify deviceViewer is launched with" + dicOutput.get("executedCommand"), "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
	
	 	//*************************************************************//
	 	//Step 4 - Post-Condition - Close device Viewer
	 	//*************************************************************//
	 	//CloseWindow("MobileLabs.deviceviewer");
		ExecuteCLICommand("release", "Android", "", "", deviceName, "","","" );
	 	isEventSuccessful =	KillObjectInstances("MobileLabs.deviceViewer.exe");
	 	reporter.ReportStep("Verfiy Device is not connected after closing devcieViewer window","Device should not be connected after closing devcieViewer window","Device is not connected after closing devcieViewer window",isEventSuccessful);
	
	
	}
}