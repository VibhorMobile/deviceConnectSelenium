package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1240
 */
public class _749_Verify_that_Rebooting_from_Device_Index_page_should_not_causes_offline_status_followed_by_Online_status_quickly extends ScriptFuncLibrary {
	
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,deviceStatus;
	Object[] Values = new Object[5]; 
	boolean isRetained,isReleased;






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
			// Step 2 : Go to Devices Page and select first device
			//*************************************************************//
			strStepDescription="Select First Device and Verify Reboot option Displayed";
			strExpectedResult="Reboot option Displayed for device";
			GoToDevicesPage();
			selectPlatform("iOS");
			selectStatus("Available");
			isEventSuccessful = !GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text").contains("No devices match your filter criteria.");
			if(isEventSuccessful){
				isEventSuccessful=(boolean)selectFirstDeviceChk_DI()[0];
				if(isEventSuccessful){
					isEventSuccessful=PerformAction(dicOR.get("btnReboot"), Action.isDisplayed);
					if(isEventSuccessful){
						strActualResult="Reboot button diaplayed for device";
					}
					else{
						strActualResult="Reboot button is not displayed for device";
					}
				}
				else{
					strActualResult="Unable to select first device cb from list";
				}
				
				
			}
			else{
				strActualResult="Devices not displayed with selected filter";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//*************************************************************//      
			// Step 3 : Reboot device and Verify Status
			//*************************************************************//	
			strStepDescription="Reboot Device and Verify Status";
			strExpectedResult="Status came as Rebooting";
			PerformAction(dicOR.get("btnReboot"), Action.Click);
			PerformAction("//button[contains(text(),'Reboot devices')]", Action.Click);
			String status=GetTextOrValue("//table[@class='deviceList table data-grid']/tbody/tr[2]/td[1]/div[2]/div[2]", "text");
			if(status.equals("Rebooting")){
				strActualResult="Device got rebooted with status:"+status;
				isEventSuccessful=true;
			}
			else{
				strActualResult="Device did not got rebooted current status:"+status;
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		    System.out.println(status);
		  //*************************************************************//      
			// Step 4 : Verify device went to offline status
			//*************************************************************//	
		    strStepDescription="Verify Status of device went to offline";
			strExpectedResult="Device status changed to offline";
			
			for(int i=0;i<=100;i++){
				
				 status=GetTextOrValue("//table[@class='deviceList table data-grid']/tbody/tr[2]/td[1]/div[2]/div[2]", "text");
				 System.out.println(status);
				 if(status.equals("Offline")){
					 strActualResult="Device went to offline mode";
					 isEventSuccessful=true;
					 break;
				 }
				 else{
					 strActualResult="Device did not went to offline mode";
					 isEventSuccessful=false;
				 }
				 Thread.sleep(1000);
				 }
				
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//*************************************************************//      
			// Step 5 : Verify device did not returned to online state immediately
			//*************************************************************//	
			  strStepDescription="Verify device did not returned to online state immediately after offline";
				strExpectedResult="evice did not returned to online state immediately";
				
				for(int i=0;i<=30;i++){
					
					 status=GetTextOrValue("//table[@class='deviceList table data-grid']/tbody/tr[2]/td[1]/div[2]/div[2]", "text");
					 System.out.println(status);
					 if(status.equals("Online")){
						 strActualResult="Device turned to online immediately after offline state";
						 isEventSuccessful=false;
					 }
					 else{
						 strActualResult="Device did not turned to online immediately after offline state";
						 isEventSuccessful=true;
					 }
					 }
					
				reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
