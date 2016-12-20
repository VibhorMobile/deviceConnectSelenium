package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id-1550
 */
public class _203_Verify_tester_is_able_to_use_devices extends ScriptFuncLibrary
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
		///**************************************************************************//
		//Step 1 - : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		isEventSuccessful = Login();

		//**************************************************************************//
		// Step 2 : Connect to an android device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
			
		Values = ExecuteCLICommand("run", "iOS", dicCommon.get("testerEmailAddress"), dicCommon.get("testerPassword"),"", "");
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
		if (!isEventSuccessful)
		{
			return;
		}
		
		//**************************************************************************//
		//Step 6 - Post Condition - Close viewer
		//**************************************************************************//
		ExecuteCLICommand("release", "iOS", dicCommon.get("testerEmailAddress"), dicCommon.get("testerPassword"), deviceName, "","","" );
		isEventSuccessful =	KillObjectInstances("MobileLabs.deviceViewer.exe");
		reporter.ReportStep("Verfiy Device is not connected after closing devcieViewer window","Device should not be connected after closing devcieViewer window","Device is not connected after closing devcieViewer window",isEventSuccessful);

		
	}
}