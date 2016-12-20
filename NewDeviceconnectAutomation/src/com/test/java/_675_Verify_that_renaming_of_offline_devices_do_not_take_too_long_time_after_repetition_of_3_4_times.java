package com.test.java;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1490
 */
public class _675_Verify_that_renaming_of_offline_devices_do_not_take_too_long_time_after_repetition_of_3_4_times extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false, isExist=false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName="", renamedText="";
	Object[] values = new Object[5]; 
	String orgDeviceName ="",currentDeviceName="";

	public final void testScript() throws InterruptedException, IOException
	{
		///**************************************************************************//
				//Step 1 - : login to deviceConnect with valid user and verify Devices page.
				//**************************************************************************//
				isEventSuccessful = Login();
				
				selectStatus_DI("Offline");

				//**************************************************************************//
				// Step 2 : Click on any device name
				//**************************************************************************//
				
				
				strStepDescription = "Click on any device name";
				strExpectedResult = "Device details page should be displayed";
				if (isEventSuccessful)
				{
					values = GoTofirstDeviceDetailsPage();
				    isEventSuccessful = (boolean) values[0] ;
				    deviceName =(String) values[1];
					
				if (isEventSuccessful)
				{
					strActualResult = "Device details page is displayed";
				}
				else
				{
					strActualResult = "Device details page is not displayed";
				}

				reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
				
				orgDeviceName=GetTextOrValue(dicOR.get("lnkmousehoverDeviceName_DeviceDetailPage"),"text");
				System.out.println("org-------------------------"+orgDeviceName);
				
				for (int loopcounter=1; loopcounter<=5; loopcounter++)
				{
					//**************************************************************************//
					// Step 3 : Click on Edit link
					//**************************************************************************//
					strStepDescription = "Click on Edit link";
					strExpectedResult = "Device name should become editable";
					isEventSuccessful = PerformAction("lnkEditDeviceName_DeviceDetailPage",Action.ClickUsingJS);
					if (isEventSuccessful)
					{
						isEventSuccessful = PerformAction("txtDeviceName", Action.isEnabled);
						if (isEventSuccessful)
						{
							strActualResult = "Device name is editable";
						}
						else
						{
							strActualResult = "Device name is not editable";
						}
					}
					else
					{
						strActualResult = "Unable to click on Edit link";
					}

					reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
					
					if (loopcounter==5)
						renamedText=orgDeviceName;
					else
						renamedText="RenamedDeviceName"+loopcounter;
					
					PerformAction(dicOR.get("txtDeviceName"), Action.Type, renamedText);

					//**************************************************************************//
					// Step 4 : Verify Save link
					//**************************************************************************//
					strStepDescription = "Verify Save link";
					strExpectedResult = "Save link should be present";
					isEventSuccessful = PerformAction("btnSaveDeviceName_DeviceDetails", Action.Click);
					if (isEventSuccessful)
					{
						strActualResult = "Save link is displayed";
					}
					else
					{
						strActualResult = "Save link is not displayed";
					}

					reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
					
					
					strStepDescription = "Verify device is renamed";
					strExpectedResult = "Devices get renamed instantly. (Should not take too long time)";
					currentDeviceName=GetTextOrValue(dicOR.get("lnkmousehoverDeviceName_DeviceDetailPage"),"text");
					System.out.println("----------------------"+currentDeviceName);
					if (currentDeviceName.equals(renamedText))
					{
						isEventSuccessful=true;
						strActualResult = "Device gets renamed instantly";
					}
					else
					{
						isEventSuccessful=false;
						strActualResult = "Device doesn't gets renamed instantly";
					}
					
					reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
				}
				}
				
	}
}