package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-858
 */
public class _783_Verify_downloading_reports_with_timezone_parameter_along_with_format_parameter_CLI extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="",deviceNameUI;
	private String xpath = "";
	int count=0;
	Object[] Values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{
		try{
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
			// Step 2: Go to devices and get Available iOS device Name
			//**************************************************************************//
			GoToDevicesPage();
			selectPlatform("iOS");
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();
			deviceNameUI=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");


			//**************************************************************************//
			// Step 3 : Extract device usage report in json format in local timezone
			//**************************************************************************//

			Values = ExecuteCLICommand("usageJsonFormat", "Android", EmailAddress, Password, deviceNameUI, "");
			isEventSuccessful = (boolean)Values[2];
			if (isEventSuccessful)
			{
				isEventSuccessful=true;
				strActualResult ="File Exporeted, Response on CLI:"+(String)Values[0];
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + (String)Values[0];
			}


			reporter.ReportStep("Extract device usage report in json format in local timezone" , "File should get written", strActualResult, isEventSuccessful);

			//**************************************************************************//
			// Step 4 : Extract device history report in csv format in local timezone
			//**************************************************************************//

			Values = ExecuteCLICommand("historyCSVFormat", "Android", EmailAddress, Password, deviceNameUI, "");
			isEventSuccessful = (boolean)Values[2];
			deviceName=(String)Values[3];
			if (isEventSuccessful)
			{
				isEventSuccessful=true;
				strActualResult ="File Exporeted, Response on CLI:"+(String)Values[0];
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + (String)Values[0];
			}


			reporter.ReportStep("Extract device history report in csv format in local timezone" , "File should get written", strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 5 : Extract device history report in json format in local timezone
			//**************************************************************************//

			Values = ExecuteCLICommand("historyJSONFormat", "Android", EmailAddress, Password, deviceNameUI, "");
			isEventSuccessful = (boolean)Values[2];
			deviceName=(String)Values[3];
			if (isEventSuccessful)
			{
				isEventSuccessful=true;
				strActualResult ="Result Displayed, Got history on CLI for:"+(String)Values[0];
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "ExecuteCLICommand---" + (String)Values[0];
			}


			reporter.ReportStep("Get device history in json format" , "History result should be displayed without any error", strActualResult, isEventSuccessful);		  
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Tster able to connect--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}

	}


}
