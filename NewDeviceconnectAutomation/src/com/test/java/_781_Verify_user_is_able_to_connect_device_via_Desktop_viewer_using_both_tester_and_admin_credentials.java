package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1550
 */
public class _781_Verify_user_is_able_to_connect_device_via_Desktop_viewer_using_both_tester_and_admin_credentials extends ScriptFuncLibrary {

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
			Values = ExecuteCLICommand("run", "Android", EmailAddress, Password, deviceNameUI, "");
			isEventSuccessful = (boolean)Values[4];
			outputText=(String)Values[1];
			deviceName=(String)Values[3];
			if (isEventSuccessful && deviceNameUI.equals(deviceName))
			{
				strActualResult = "Viewer launched after connecting to an iOS device:  " + Values[3] + " & processfound : " +  Values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}



			reporter.ReportStep("Connect to an iOS device and verify deviceViewer is launched " , "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);


			//**************************************************************************//
			// Step 4 : Release device.
			//**************************************************************************//
			ExecuteCLICommand("release", "Android", EmailAddress, Password, deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");	
			//**************************************************************************//
			// Step 5 : Verify Device is not retained
			//**************************************************************************//
			PerformAction(dicOR.get("lnkShowDetails"),Action.Click);
			isEventSuccessful = VerifyOnDeviceDetailsPage("Status ", "CONTAINS__" + "Available");
			if (isEventSuccessful)
			{
			   strActualResult ="Device is not retained";
			}
			else
			{
				strActualResult = "Device status is not Available."+ strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify device is released after disconnected" , "Device should not be retained", strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 5 : Logout from Application
			//**************************************************************************//
			Logout();
			//**************************************************************************//
			// Step 6 : Login using Tester Credentials
			//**************************************************************************//
			Login(EmailAddress,Password);
			
			//**************************************************************************//
			// Step 7: Go to devices and get Available iOS device Name
			//**************************************************************************//
			GoToDevicesPage();
			selectPlatform("Android");
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();
			deviceNameUI=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");



			//**********************************************************//
			// Step 8 - Retain iOS device
			//**********************************************************//     
			Values = ExecuteCLICommand("run", "Android", EmailAddress, Password, deviceNameUI, "");
			isEventSuccessful = (boolean)Values[4];
			outputText=(String)Values[1];
			deviceName=(String)Values[3];
			if (isEventSuccessful && deviceNameUI.equals(deviceName))
			{
				strActualResult = "Viewer launched after connecting to an iOS device:  " + Values[3] + " & processfound : " +  Values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}



			reporter.ReportStep("Connect to an iOS device and verify deviceViewer is launched " , "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);


			//**************************************************************************//
			// Step 9 : Release device.
			//**************************************************************************//
			ExecuteCLICommand("release", "Android", EmailAddress, Password, deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");	
			 
			//**************************************************************************//
			// Step 5 : Verify Device is not retained
			//**************************************************************************//			
			PerformAction(dicOR.get("lnkShowDetails"),Action.Click);
			isEventSuccessful = VerifyOnDeviceDetailsPage("Status ", "CONTAINS__" + "Available");
			if (isEventSuccessful)
			{
			   strActualResult ="Device is not retained";
			}
			else
			{
				strActualResult = "Device status is not Available."+ strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify device is released after disconnected" , "Device should not be retained", strActualResult, isEventSuccessful);


		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Tster able to connect--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}

	}

}
