package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1576
 */	
public class _814_dC_Web_UI_Verify_Expandable_sign_should_appear_only_for_multiple_instances_of_the_apps_upload_on_Launch_Application_window extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String  strActualResult = "",Appname="com.brighthouse.mybhn.apk";



	public final void testScript() throws InterruptedException
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();


			//*************************************************************//                     
			// Step 1 : Go to Applications PAge and Verify App is present if not install app
			//*************************************************************//                     
			GoToApplicationsPage();
			boolean flag=PerformAction(dicOR.get("eleAppName_AppsPage").replace("__APPNAME__", "My BHN"), Action.isDisplayed);
			if(!flag){
				isEventSuccessful=uploadApplication(Appname);
				if(isEventSuccessful){
					strActualResult="My BHN App Installed on Applications Page";
				}
				else{
					strActualResult="Unable to install My BHN app ";
				}
			}
			else{
				strActualResult="My BHN App Installed on Applications Page ";
				isEventSuccessful=true;
			}
			reporter.ReportStep("Verify My BHN app present on page", "My BHN app should be there", strActualResult, isEventSuccessful);

			//*************************************************************//                     
			// Step 2 : Verify no multiple instance for app on device if multiple instances present delete and upload app again
			//*************************************************************//     
			flag=verifyMultipleAppUploads("My BHN");
			if(flag){
				isEventSuccessful=deleteApplication("My BHN");
				if(isEventSuccessful){
					PerformAction("Browser", Action.Refresh);
					isEventSuccessful=uploadApplication(Appname);
					if(isEventSuccessful){
						strActualResult="App is present with one instance";
					}
					else{
						strActualResult="Application is there with multiple uploads sign";
					}
				}
				else{
					strActualResult="Unable to delete app with multiple Uploads";
				}


			}
			else{
				strActualResult="Only one App upload instance found";
				isEventSuccessful=true;
			}
			reporter.ReportStep("Verify My BHN app having only one upload", "My BHN app with one upload instance should be there", strActualResult, isEventSuccessful);

			//*************************************************************/                     
			// Step4: Go to devices and verify No Multiple instance icon for device
			//**************************************************************/
			GoToDevicesPage();
			selectPlatform("Android");
			GoTofirstDeviceDetailsPage();
			PerformAction(dicOR.get("btnConnect"), Action.Click);
			isEventSuccessful=!verifyMultipleInstanceofApp_LaunchDialog("My BHN");
			if(isEventSuccessful){
				strActualResult="No Multiple instance icon + sign is present on launch dialog";
			}
			else{
				strActualResult="Multiple instance icon + sign is present with app on launch dialog";
			}
			reporter.ReportStep("Verify multiple instance + sign on launch dialog", "No + Sign should be present with app", strActualResult, isEventSuccessful); 			



		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Multiple Instance Icon--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}




}
