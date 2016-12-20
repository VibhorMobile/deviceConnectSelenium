package com.test.java;

import java.io.IOException;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-390
 */
public class _670_Verify_User_is_able_to_enable_disable_non_reserved_offline_online_device extends ScriptFuncLibrary 
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="",SelectedDeviceName = "",devicestatus = "";
	private Object[] arrResult = null;
	private Object[] firstdeviceSelected = null;
	
	public final void testScript() throws InterruptedException, IOException
	{
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful = Login();
		
		//************************************************************//
		// Step 2 : Select only Available checkbox, if Available device not found move on to only Offline, if not found then move on to In Use, if not then exit
		//************************************************************//
		strstepDescription = "Select Available checkbox & vrify device found, if not then move to Offline & then to reserved/In Use.";
		strexpectedResult = "Verify the device with the status selected gets filtered out.";
		String[] StatusTobeTested = {"Available","In Use","Offline"};
		for (String status : StatusTobeTested) 
		{
			GoToDevicesPage();
			if(status.equals("Offline"))
			{
				devicestatus="Disabled";
			}
			else
			{
				devicestatus="Connected";
			}
			isEventSuccessful = selectStatus_DI(status);
			if(isEventSuccessful)
			{
				if(VerifyMessage_On_Filter_Selection())
				{
					return;
				}
				//************************************************************//
				// Step 3 : Select first available device & store the device name
				//************************************************************//
				arrResult = selectFirstDeviceChk_DI();
				isEventSuccessful = (boolean)arrResult[0];
				deviceName = (String)arrResult[1];
				if(!isEventSuccessful)
					return;
				
				//************************************************************//
				// Step 4 : Click on Disable for the selected first device
				//************************************************************//
				strstepDescription = "Click on 'Disable' and verify device status icon changed to disabled & status changed to 'Connected/'Disabled' by searching the device.";
				strexpectedResult = "Verify the device gets disabled without any error.";
				isEventSuccessful = PerformAction("btnBulkDisable_Devices", Action.Click);
				if(isEventSuccessful)
				{
					isEventSuccessful = PerformAction("hdrConfirmDisable",Action.WaitForElement);
					if(isEventSuccessful)
					{
						isEventSuccessful = PerformAction("btnContinue_Disable",Action.Click);
						if(isEventSuccessful)
						{
							isEventSuccessful = PerformAction("errorPopup", Action.Exist);
							if(!isEventSuccessful)
							{
								selectStatus_DI("Disabled");
								
								firstdeviceSelected = GoTofirstDeviceDetailsPage();
								isEventSuccessful = (boolean) firstdeviceSelected[0];
								SelectedDeviceName = (String) firstdeviceSelected[1];
								isEventSuccessful=ShowDetails();
								if(isEventSuccessful)
								{
									isEventSuccessful = VerifyOnDeviceDetailsPage("Status ", "CONTAINS__" + devicestatus);
									if (isEventSuccessful)
										reporter.ReportStep(strstepDescription, strexpectedResult, "Status on device details page is: " + devicestatus, "Pass");
									else
									{
										strActualResult = strErrMsg_AppLib;
										reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult + "Status on device details page is: " + devicestatus, "Fail");
									}	
								}
							}
							else
							{
								strActualResult = strErrMsg_GenLib;
								reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult + "Error popup notification occured on disabling a device.", "Fail");
							}
						}
					}
				}
				
				//************************************************************//
				// Step 5 : Enable the device which is disabled in the above step
				//************************************************************//
				strstepDescription = "Enable the device which is disabled in the above step";
				strexpectedResult = "Verify the device gets enabled without any error.";
				if(isEventSuccessful)
				{
					isEventSuccessful = GoToDevicesPage();
					if(isEventSuccessful)
					{	
						if(selectStatus_DI("Disabled"))
						{	
							isEventSuccessful = searchDevice(deviceName, "devicename");
							if(isEventSuccessful)
							{
								arrResult = selectFirstDeviceChk_DI();
								isEventSuccessful = (boolean)arrResult[0];
								deviceName = (String)arrResult[1];
								if(isEventSuccessful)
								{	
									if(PerformAction("btnEnable_Devices", Action.Click))
									{	
										if(PerformAction("hdrConfirmEnable",Action.WaitForElement))
											isEventSuccessful = PerformAction("btnEnableDevices_EnableDevice",Action.Click);
										else
										{
											isEventSuccessful = false;
											strActualResult = strErrMsg_GenLib;
											reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
										}
									}
									else
									{
										strActualResult = strErrMsg_GenLib;
										reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
									}
								}
							}
						}
						else
						{
							strActualResult = strErrMsg_AppLib;
							reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
						}
					}
				}
			 
				//************************************************************//
				// Step 6 : Verify that error message is not displayed
				//************************************************************//
				strstepDescription = "Error message should not be displayed & device should be enabled.";
				strexpectedResult = "Verify that error message is not displayed & device status is changed to enabled.";
				if(isEventSuccessful)
				{
					isEventSuccessful = PerformAction("errorPopup", Action.Exist);
					if(!isEventSuccessful)
					{	
						selectStatus_DI("Available,In Use,Offline");
						firstdeviceSelected = GoTofirstDeviceDetailsPage();
						isEventSuccessful = (boolean) firstdeviceSelected[0];
						isEventSuccessful=ShowDetails();
						if(isEventSuccessful)
						{
							isEventSuccessful = VerifyOnDeviceDetailsPage("Status ", "CONTAINS__" + status);
							if (isEventSuccessful)
								reporter.ReportStep(strstepDescription, strexpectedResult, "Device is enabled without any error message & Status on device details page is: " + devicestatus, "Pass");
							else
							{
								strActualResult = strErrMsg_AppLib;
								reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult + "Status on device details page is: " + devicestatus, "Fail");
							}	
						}
					}
					else
					{
						strActualResult = strErrMsg_GenLib;
						reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult + "Error popup notification occured on disabling a device.", "Fail");
					}
				}
				else
				{
					strActualResult = strErrMsg_GenLib;
					reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
				}
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult + "devices are not displayed for the selected filter : " + status, "Fail");
			}		
		}
	}
}
