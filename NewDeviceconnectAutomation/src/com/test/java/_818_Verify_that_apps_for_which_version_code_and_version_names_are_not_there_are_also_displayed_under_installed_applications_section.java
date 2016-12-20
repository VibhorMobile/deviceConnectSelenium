package com.test.java;
import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Jira Test Case Id:  QA-368
 */
public class _818_Verify_that_apps_for_which_version_code_and_version_names_are_not_there_are_also_displayed_under_installed_applications_section extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "",appName="android-server-2.38.0.apk";
	String [] android={"Android"};

	public final void testScript()
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();

			//**********************************************************//
			// Step 2 - Go to Applications page //
			//**********************************************************//                                   
			isEventSuccessful = GoToApplicationsPage();

			//**********************************************************//
			// Step 3- Upload application for which version code and version is missing
			//**********************************************************//  

			strStepDescription = "Upload webdriver app for which version code and name is missing";
			strExpectedResult = "App uploaded successfully";
			isEventSuccessful =   uploadApplication(appName);

			if(isEventSuccessful)
			{
				strActualResult="Application Uploaded ";

			}
			else
			{
				strActualResult = "Application not uploaded";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//**********************************************************//
			// Step 4- Go to Devices and Open details page for any android available device
			//**********************************************************// 
			GoToDevicesPage();
			selectPlatform_DI("Android");
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();

			//**********************************************************//
			// Step 5- Install Application on device
			//**********************************************************//   
			waitForPageLoaded();
			installAppOnDevice("WebDriver");
			PerformAction("browser",Action.Refresh);
			//**********************************************************//
			// Step 6- Verify app is present on Installed section for device
			//**********************************************************//   
			waitForPageLoaded();
			verifyAppInstalledOnDevice("WebDriver");


		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Form factor--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}

	}

}
