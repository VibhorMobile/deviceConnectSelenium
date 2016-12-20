package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-209
 */
public class _684_Verify_that_release_button_should_not_be_displayed_to_testers_for_devices_used_by_other_users extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private boolean isStepSuccessful=false;
	Object[] Values = new Object[5]; 




	public final void testScript() throws InterruptedException, IOException
	{
		try{

			String Email=dicCommon.get("EmailAddress");
			String Pass=dicCommon.get("Password");
			String EmailAddress = dicCommon.get("testerEmailAddress");
			String Password = dicCommon.get("testerPassword");


			//*************************************************************//                     
			// Step 1 : login to deviceConnect with tester user.
			//*************************************************************//                     
			Login(EmailAddress,Password);
            GoToDevicesPage();
            selectPlatform("iOS");
            selectStatus("Available");
            GoTofirstDeviceDetailsPage();
            deviceName=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");

			//*************************************************************/                     
			// Step 2 : Go to Devices and retain a device
			//*************************************************************/
			Values = ExecuteCLICommand("run", "iOS", "", "", deviceName, "");
			isEventSuccessful = (boolean)Values[4];
			outputText=(String)Values[1];
			deviceName=(String)Values[3];
			if (isEventSuccessful)
			{
				strActualResult = "Viewer launched after connecting to an iOS device:  " + Values[3] + " & processfound : " +  Values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}


			reporter.ReportStep("Verify desktop deviceViewer is launched " , "User should get connected and desktop deviceviewer should get launched.", strActualResult, isEventSuccessful);


			//*************************************************************/                     
			// Step 3 : Select In use filter and open retained device
			//*************************************************************//
			GoToDevicesPage();
			strstepDescription="Verify Release button for tester user";
			strExpectedResult="Release button is not dispayed for tester user";
			isEventSuccessful = selectStatus("In Use");
			searchDevice(deviceName, "deviceName");
			GoTofirstDeviceDetailsPage();
			isEventSuccessful=PerformAction(dicOR.get("btnRelease"), Action.isDisplayed);
			if(isEventSuccessful){
				strActualResult="Release button is available for tester user";
				isEventSuccessful=false;
			}
			else{
				strActualResult="Release button is not available for tester user";
				isEventSuccessful=true;
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful); 

			//**************************************************************************//
			// Step 4 : Release device.
			//**************************************************************************//
			ExecuteCLICommand("release", "iOS", Email, Pass, deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify release button for tester --" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

}
