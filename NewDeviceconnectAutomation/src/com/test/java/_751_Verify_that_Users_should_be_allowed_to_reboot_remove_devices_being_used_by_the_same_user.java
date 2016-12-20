package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1507
 */
public class _751_Verify_that_Users_should_be_allowed_to_reboot_remove_devices_being_used_by_the_same_user extends ScriptFuncLibrary {


	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "",outputText = "";
	String deviceName,appName,appNameEdited,deviceStatus;
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


			isEventSuccessful = Login();
			//*************************************************************//      
			// Step 2 : Go to Devices Page and get Available device Name
			//*************************************************************//
			GoToDevicesPage();
			selectPlatform_DI("iOS");
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();
			deviceName=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");

			//**************************************************************************//
			// Step 3 : Retain same Device and Verify Status
			//**************************************************************************//
			strStepDescription = "Verify User is Able to retain Device";
			strExpectedResult = "User is able to retain device";
			//ExecuteCLICommand("client", "iOS", "", "", deviceName, "desktop");
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
			// Step 3 : Reboot device used by user
			//**************************************************************************//
			strStepDescription = "Verify Device is rebooted";
			strExpectedResult = "Device rebooted";
			isEventSuccessful=PerformAction(dicOR.get("btnReboot"), Action.isDisplayed);
			if(isEventSuccessful){
				PerformAction(dicOR.get("btnReboot"), Action.ClickUsingJS);
				waitForPageLoaded();
				PerformAction(dicOR.get("btnRebootDevice_RebootDialog"), Action.ClickUsingJS);
				waitForPageLoaded();
				PerformAction(dicOR.get("lnkShowDetails"), Action.WaitForElement,"20");
				for(int iwaitcounter=0; iwaitcounter<=10; iwaitcounter++) 
				{

					isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
					deviceStatus = GetTextOrValue(dicOR.get("eleStatus_DeviceDetailsPage"), "text"); 
					System.out.println(deviceStatus);
					Thread.sleep(1000);
					PerformAction("browser", Action.Refresh);		
					if (deviceStatus.contains("Offline"))
					{

						for(int iwaitcounter1=0; iwaitcounter1<=10; iwaitcounter1++) 
						{
							isEventSuccessful=false;
							Thread.sleep(5000);
							PerformAction("browser", Action.Refresh);
							waitForPageLoaded();
							isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
							deviceStatus = GetTextOrValue(dicOR.get("eleStatus_DeviceDetailsPage"), "text"); 
							if (deviceStatus.contains("Available"))
							{
								//apiReporter.apiNewHeading("Device Status at UI isRebooted: "+isRebooted);
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
			
		}


		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}





}
