package com.test.java;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1376
 */
public class _758_Verify_that_releasing_the_retained_device_then_retaining_again_should_be_retained extends ScriptFuncLibrary {
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,inUse,outputText = "";
	Object[] Values = new Object[5]; 
	boolean isRetained,isReleased;






	public final void testScript() throws InterruptedException, IOException
	{
		try{
			String EmailAddress = dicCommon.get("testerEmailAddress");
			String Password = dicCommon.get("testerPassword");

			//*************************************************************//                     
			// Step 1 : login to deviceConnect with Admin user 
			//*************************************************************//  


			isEventSuccessful = Login();
			//*************************************************************//      
			// Step 2 : Go to Devices Page and get Available device Name
			//*************************************************************//
			GoToDevicesPage();
			selectPlatform("iOS");
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();
			deviceName=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");
			//**************************************************************************//
			// Step 3 : Retain same Device and Verify Status
			//**************************************************************************//
			strstepDescription = "Verify User is Able to retain Device";
			strExpectedResult = "User is able to retain device";
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
      
      

			reporter.ReportStep("Connect to an iOS device and verify deviceViewer is launched " , "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 4 : Release device and Verify Status
			//**************************************************************************//
			strstepDescription = "Verify User is Able to release Device";
			strExpectedResult = "User is able to release device";
			ExecuteCLICommand("release", "Android", "", "", deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");

			for(int waitCounter=0;waitCounter<=10;waitCounter++){

				isRetained=PerformAction(dicOR.get("btnRelease"),Action.isDisplayed);
				System.out.println("Device Retained:"+isRetained);

				if(isRetained){
					strActualResult="Unable to release device";
					isEventSuccessful=false;

				}
				else{
					strActualResult="Device is released by user";
					isEventSuccessful=true;
					break;
				}
				Thread.sleep(10000);
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 5 : Retain Device Again
			//**************************************************************************//
			strstepDescription = "Verify User is Able to retain Device Again";
			strExpectedResult = "User is able to retain device";
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
      
      

			reporter.ReportStep("Connect to an iOS device and verify deviceViewer is launched " , "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 4 : Release device and Verify Status
			//**************************************************************************//
			strstepDescription = "Verify User is Able to release Device";
			strExpectedResult = "User is able to release device";
			ExecuteCLICommand("release", "Android", "", "", deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");

			for(int waitCounter=0;waitCounter<=10;waitCounter++){

				isRetained=PerformAction(dicOR.get("btnRelease"),Action.isDisplayed);
				System.out.println("Device Retained:"+isRetained);

				if(isRetained){
					strActualResult="Unable to release device";
					isEventSuccessful=false;

				}
				else{
					strActualResult="Device is released by user";
					isEventSuccessful=true;
					break;
				}
				Thread.sleep(10000);
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);						 

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}



}
