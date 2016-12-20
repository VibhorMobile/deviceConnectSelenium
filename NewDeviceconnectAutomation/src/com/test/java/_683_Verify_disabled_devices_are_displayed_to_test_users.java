package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-291
 */
public class _683_Verify_disabled_devices_are_displayed_to_test_users extends ScriptFuncLibrary {


	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private boolean isStepSuccessful=false;

	String pageURL,errorMessage,wrongURL;



	public final void testScript() throws InterruptedException, IOException
	{
		try{


			String EmailAddress = dicCommon.get("testerEmailAddress");
			String Password = dicCommon.get("testerPassword");


			//*************************************************************//                     
			// Step 1 : login to deviceConnect with admin user.
			//*************************************************************//                     
			isEventSuccessful = Login();



			//*************************************************************/                     
			// Step 2 : Go to Devices and disable a device
			//*************************************************************//
			//isEventSuccessful = selectStatus("Offline");
			strstepDescription="Disable a device";
			strExpectedResult="Device diabled successfully.";
			String devicesErrorMsg=GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text");
			System.out.println("no device:"+devicesErrorMsg);
			if(devicesErrorMsg.contains("No devices match your filter criteria.")){
				System.out.println("No Devices are their");

			}
			else{
				isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0]; //Navigating to first Available or Inuse, Offline Or Disabled DeviceDetails page
				String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
				//deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];
				isEventSuccessful = PerformAction("btnDisable", Action.Click);
				isEventSuccessful = PerformAction("hdrConfirmDisable", Action.isDisplayed);
				isEventSuccessful=PerformAction("btnContinue_Disable", Action.Click);
				if(isEventSuccessful){
					strActualResult="Disabled a device";
					isEventSuccessful=true;
				}
				else{
					strActualResult="Unable to disable device";
					isEventSuccessful=false;
				}
				reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful); 
				//*************************************************************/                     
				// Step 3 : Logout from DC
				//*************************************************************//

				Logout();
				//*************************************************************/                     
				// Step 4 : Login with tester credentials
				//*************************************************************//
				Login(EmailAddress,Password);
				//*************************************************************/                     
				// Step 5: Verify Disabled devices are visible to tester user
				//*************************************************************//
				isEventSuccessful = selectStatus("Disabled");
				strstepDescription="Verify Disabled devices are visible to test user";
				strExpectedResult="Disabled devices are visible to test user";

				devicesErrorMsg=GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text");
				System.out.println("no device:"+devicesErrorMsg);
				if(devicesErrorMsg.contains("No devices match your filter criteria.")){
					strActualResult="Disabled devices are not visible to user";
					isEventSuccessful=false;
					System.out.println("No Devices are their");

				}
				else{
					strActualResult="Disabled devices are visible to test user";
					isEventSuccessful=true;
				}
				reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful); 

			}	

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "View Token Field for User--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
