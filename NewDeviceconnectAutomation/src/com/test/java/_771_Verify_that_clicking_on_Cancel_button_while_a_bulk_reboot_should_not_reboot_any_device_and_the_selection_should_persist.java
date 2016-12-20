package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-1066
 */
public class _771_Verify_that_clicking_on_Cancel_button_while_a_bulk_reboot_should_not_reboot_any_device_and_the_selection_should_persist extends ScriptFuncLibrary{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String  strActualResult = "",deviceStatus;
	private int noOfDevices;
	String [] android={"Android"};

	public final void testScript() throws InterruptedException
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();

			//**********************************************************//
			// Step 2 - Go To devices pAge and Select Available filter
			//**********************************************************//  
			GoToDevicesPage();
			selectStatus("Available");

			//**********************************************************//
			// Step 3- Click on Select All check box and click Reboot
			//**********************************************************//  
			strStepDescription="Click SelectAll Devices Checkbox and Click on Reboot button";
			strExpectedResult="SelectAllDevices checkbox checked and reboot dialog opened";
			isEventSuccessful=PerformAction(dicOR.get("chkSelectAll_Devices"), Action.SelectCheckbox);
			if(isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("btnReboot"), Action.Click);	
				if(isEventSuccessful){
					isEventSuccessful=PerformAction(dicOR.get("DialogBox_DC"), Action.isDisplayed);	
					if(isEventSuccessful){
						strActualResult="Reboot popup appeared for all devices selected";
					}
					else{
						strActualResult="Reboot popup did not show up";
					}
				}
				else{
					strActualResult="Unable o click on reboot button";
				}
			}
			else{
				strActualResult="Unable to click on checkbox to selectall devices";
			}

			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//**********************************************************//
			// Step 4-Click on cancel from reboot dialog verify no devices rebooted
			//**********************************************************//  
			strStepDescription="Click on cancel from reboot dialog verify no devices rebooted";
			strExpectedResult="Clicked on cancel and No devices rebooted";
			isEventSuccessful=PerformAction(dicOR.get("btnCancel"), Action.Click);
			if(isEventSuccessful){
				noOfDevices=getelementCount(dicOR.get("noOfDevices_DI"))+1;
				if(noOfDevices>0){
					for(int i=2;i<=noOfDevices;i++){
						deviceStatus=GetTextOrValue(dicOR.get("statusDevices_DI").replace("*", String.valueOf(i)), "text");
						System.out.println("Device Status devive is:"+deviceStatus);
						if(!deviceStatus.equalsIgnoreCase("Available")){
							isEventSuccessful=false;
							strActualResult="Device Status is changed, current status of device is:"+deviceStatus;
							break;
						}
						else{
							isEventSuccessful=true;
							strActualResult="Devices not rebooted status for devices are :"+deviceStatus;
						}
					}
				}
				else{
					isEventSuccessful=false;
					strActualResult="Can not verify if devices rebooted available device count is:"+deviceStatus;
				}
			}
			else{
				isEventSuccessful=false;
				strActualResult="Unable to click on Cancel button";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Cancelation of bulk reboot--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
