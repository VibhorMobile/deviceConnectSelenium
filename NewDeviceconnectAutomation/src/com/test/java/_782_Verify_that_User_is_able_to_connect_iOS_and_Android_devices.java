package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.sun.java.swing.plaf.windows.resources.windows;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1538
 */
public class _782_Verify_that_User_is_able_to_connect_iOS_and_Android_devices extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "", outputText = "", deviceName="",EmailAddress,Password,deviceNameUI;
	Object[] Values = new Object[5]; 

	public final void testScript() throws IOException
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect using Admin credentials
			//*******************************//
			EmailAddress=dicCommon.get("testerEmailAddress");
			Password=dicCommon.get("testerPassword");
			isEventSuccessful = Login();

			//**************************************************************************//
			// Step 2: Go to devices and get Available iOS device Name
			//**************************************************************************//
			GoToDevicesPage();
			selectPlatform("iOS");
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();
			deviceNameUI=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");



			//**********************************************************//
			// Step 5 - Retain iOS device
			//**********************************************************//     
			//Values = ExecuteCLICommand("run", "Android", EmailAddress, Password, deviceNameUI, "");
			Values = ExecuteCLICommand("runapp", "iOS", "", "", deviceNameUI, "Trust Browser", "web");
			isEventSuccessful = (boolean)Values[4];
			outputText=(String)Values[1];
			System.out.println(outputText);
			deviceName=(String)Values[3];
			if (isEventSuccessful && outputText.contains("opened"))
			{
				strActualResult = "WebViewer launched after connecting to an iOS device:  " + Values[3];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}



			reporter.ReportStep("Connect to an iOS device and launch trust browser app" , "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);


			//**************************************************************************//
			// Step 4 : Release device.
			//**************************************************************************//
			ExecuteCLICommand("release", "iOS", EmailAddress, Password, deviceNameUI, "","","" );

			//**************************************************************************//
			// Step 4 : Close webviewer window
			//**************************************************************************//
			 
			//**************************************************************************//
			// Step 2: Go to devices and get Available iOS device Name
			//**************************************************************************//
			GoToDevicesPage();
			selectPlatform("Android");
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();
			deviceNameUI=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");



			//**********************************************************//
			// Step 5 - Retain iOS device
			//**********************************************************//     
			//Values = ExecuteCLICommand("run", "Android", EmailAddress, Password, deviceNameUI, "");
			Values = ExecuteCLICommand("runapp", "Android", "", "", deviceNameUI, "Phone Lookup", "web");
			isEventSuccessful = (boolean)Values[4];
			outputText=(String)Values[1];
			
			deviceName=(String)Values[3];

			if (isEventSuccessful && outputText.contains("opened"))
			{
				strActualResult = "web Viewer launched after connecting to device:  " + Values[3] + " & processfound : " +  Values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("connect to android device and verify phone lookup launched in web viewer","Phone lookup should get launched", strActualResult, isEventSuccessful);

			//**************************************************************************//
			// Step 4 : Release device.
			//**************************************************************************//
			ExecuteCLICommand("release", "Android", EmailAddress, Password, deviceNameUI, "","","" );
			
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Tster able to connect--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}

	}

	
}
