package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-99
 */
public class _754_Verify_that_a_tester_user_should_be_able_to_Release_or_Reboot_a_device_retained_by_himself extends ScriptFuncLibrary {
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	private String deviceName,appName,appNameEdited,deviceStatus,outputText = "";
	Object[] Values = new Object[5]; 
	boolean isRetained,isRebooted;

	public final void testScript() throws InterruptedException, IOException
	{
		try{
			String EmailAddress = dicCommon.get("testerEmailAddress");
			String Password = dicCommon.get("testerPassword");
			//*************************************************************//                     
			// Step 1 : login to deviceConnect with Tester User
			//*************************************************************//  


			isEventSuccessful = Login(EmailAddress,Password);
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
			Values = ExecuteCLICommand("run", "iOS", EmailAddress, Password, deviceName, "");
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
			// Step 3 : Reboot device used by user
			//**************************************************************************//
			PerformAction("browser",Action.Refresh);
			strStepDescription = "Verify Device is rebooted";
			strExpectedResult = "Device rebooted";
			waitForPageLoaded();
			PerformAction("browser",Action.WaitForPageToLoad);
			isEventSuccessful=PerformAction(dicOR.get("btnReboot"), Action.isDisplayed);
			if(isEventSuccessful){
				PerformAction(dicOR.get("btnReboot"), Action.ClickUsingJS);
				waitForPageLoaded();
				PerformAction(dicOR.get("btnRebootDevice_RebootDialog"), Action.ClickUsingJS);
				waitForPageLoaded();
				
				for(int iwaitcounter=0; iwaitcounter<=10; iwaitcounter++) 
				{

					Thread.sleep(5000);
					waitForPageLoaded();
					isEventSuccessful = PerformAction("lnkShowDetails", Action.ClickUsingJS);
					deviceStatus = GetTextOrValue(dicOR.get("eleStatus_DeviceDetailsPage"), "text"); 
					System.out.println(deviceStatus);
					PerformAction("browser", Action.Refresh);		
					if (deviceStatus.contains("Offline"))
					{

						for(int iwaitcounter1=0; iwaitcounter1<=10; iwaitcounter1++) 
						{
							isEventSuccessful=false;
							Thread.sleep(5000);
							PerformAction("browser", Action.Refresh);	
							waitForPageLoaded();
							isEventSuccessful = PerformAction("lnkShowDetails", Action.ClickUsingJS);
							deviceStatus = GetTextOrValue(dicOR.get("eleStatus_DeviceDetailsPage"), "text"); 
							if (deviceStatus.contains("Available"))
							{

								isEventSuccessful=true;
								strActualResult="Device got rebooted";
								break;
							}
							else{
								isEventSuccessful=false;
								strActualResult="Device not returned to online";
							}
						}

						break;
					}
					else{
						isEventSuccessful=false;
						strActualResult="Device did not Rebooted";

					}
				}

			}
			else{
				isEventSuccessful=false;
				strActualResult="Reboot button not available for device";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//**************************************************************************//
			// Step 4 : Close desktop Viewer
			//**************************************************************************//
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
			//**************************************************************************//
			// Step 5 : Retain same Device and Verify Status
			//**************************************************************************//
			//**************************************************************************//
			Values = ExecuteCLICommand("run", "iOS", EmailAddress, Password, deviceName, "");
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
			// Step 6 : Release the Device and Verify Status
			//**************************************************************************//
			strStepDescription = "Verify User is Able to release Device";
			strExpectedResult = "User is able to release device";
			waitForPageLoaded();
			isEventSuccessful=PerformAction(dicOR.get("btnRelease"), Action.ClickUsingJS);
			if(isEventSuccessful){
				waitForPageLoaded();
				isEventSuccessful=PerformAction(dicOR.get("btnReleaseDevice"),Action.ClickUsingJS);
				if(isEventSuccessful){
					waitForPageLoaded();
					for(int waitCounter=0;waitCounter<=10;waitCounter++)
					{
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
				}
				else{
					strActualResult="Unable to click on Release device button on popup";
				}

			}
			else{
				strActualResult="Unable to click on Release button";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//**************************************************************************//
			// Step 6 : Close the desktop viewer
			//**************************************************************************//	
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");	
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
