package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1568,1503,1734
 */
public class _780_Verify_that_Testers_users_are_able_to_connect_any_device extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "", outputText = "", deviceName="",EmailAddress,Password,deviceNameUI;
	Object[] Values = new Object[5]; 


	public final void testScript() throws IOException
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect using tester credentials
			//*******************************//
			EmailAddress=dicCommon.get("testerEmailAddress");
			Password=dicCommon.get("testerPassword");
			isEventSuccessful = Login(EmailAddress,Password);
			//**************************************************************************//
			// Step 2 : Go to devices and get Available iOS device Name
			//**************************************************************************//
			GoToDevicesPage();
			selectPlatform("iOS");
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();
			deviceNameUI=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");


			//**************************************************************************//
			// Step 3 : Connect to device and verify web viewer is launched
			//**************************************************************************//
			Values = ExecuteCLICommand("client", "iOS", EmailAddress, Password, deviceNameUI, "web");
			isEventSuccessful = (boolean)Values[4];
			outputText=(String)Values[1];
			deviceName=(String)Values[3];

			if (isEventSuccessful && outputText.contains("opened")&&deviceName.equals(deviceNameUI))
			{
				strActualResult = "web Viewer launched after connecting to device:  " + Values[3] + " & processfound : " +  Values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Verify web Viewer is launched " , "User should get connected and web deviceviewer should get launched.", strActualResult, isEventSuccessful);



			//**************************************************************************//
			// Step 4 : Release device.
			//**************************************************************************//
			ExecuteCLICommand("release", "iOS", EmailAddress, Password, deviceName, "","","" );
			//**************************************************************************//
			// Step 5: Go to Android vailable device and get deviceName
			//**************************************************************************//
			GoToDevicesPage();
			selectPlatform("Android");
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();
			deviceNameUI=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");
			//**************************************************************************//

			//**********************************************************//
			// Step 5 - Retain Android device 
			//**********************************************************//     
		 
				Values = ExecuteCLICommand("client", "Android", EmailAddress, Password, deviceNameUI, "web");
				isEventSuccessful = (boolean)Values[4];
				outputText=(String)Values[1];
				deviceName=(String)Values[3];
				if (isEventSuccessful && outputText.contains("opened")&& deviceName.equals(deviceNameUI))
				{
					strActualResult = "Web Viewer launched after connecting to device:  " + Values[3] + " & processfound : " +  Values[1];
				}
				else
				{
					strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
				}
				reporter.ReportStep("Verify web Viewer is launched " , "User should get connected and web deviceviewer should get launched.", strActualResult, isEventSuccessful);
	 


			//**************************************************************************//
			// Step 4 : Release device.
			//**************************************************************************//
			ExecuteCLICommand("release", "Android", EmailAddress, Password, deviceName, "","","" );

			
			// Step 6 : Open Device using desktop viewer
			//**************************************************************************//
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
			// Step 7 : Release device and close desktop viewer
			//**************************************************************************//
			ExecuteCLICommand("release", "Android", EmailAddress, Password, deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");	


		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Tster able to connect--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}

	}


}
