package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
import java.util.ArrayList;

/*
 * Author : Dolly
 * Creation Date: Third week of February
 * Last Modified Date: Same as creation date
 * Jira Test Case Id:QA-1294,476
 */

public class _629_Verify_that_admin_users_can_reboot_one_or_more_devices_having_status_Reserved extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "";
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	Object [] Values=new Object[5];

	public final void testScript()
	{

		//*************************************************************//
		//Step 1 : login to deviceConnect with admin user.
		//*************************************************************//
		Login();
		//Step  - Delete All Reservations
		GoToReservationsPage();
		RSVD_DeleteAllReservations();
		GoToDevicesPage();
		Logout();
		isEventSuccessful = Login(dicCommon.get("testerEmailAddress"),dicCommon.get("testerPassword")); 
		isEventSuccessful = selectStatus("Available");
				 
		Values = AvailableDevices_DIpage();
		devicesSelected.clear();
		isEventSuccessful  = (boolean) Values[0] ;
		devicesSelected =  (ArrayList) Values[1] ;
				 
				
		strStepDescription = "Create reservation for the device on reservation page";
		strExpectedResult = "Reservation should be created Successfully for." ;
				
		if(GoToReservationsPage())
		{
			if(GoToCreateReservationPage())
			{
				isEventSuccessful=  RSVD_CreateReservationNever(devicesSelected.get(0),true);
				if(isEventSuccessful)
				{
					strActualResult = "Successfully created reservation for." + devicesSelected.get(0);
				}
				else
				{
					strActualResult = "Could not created reservation for." + devicesSelected.get(0);
				}
			}
			else
			{
				strActualResult = "Could not clicked on create reservation button.";
			}
		}
		else
		{
			strActualResult = "Could not navigated to reservation page" ;
		}
		
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		isEventSuccessful = Logout();
		
		//**************************************************************************//
		// Step 4 : Login again as admin.
		//**************************************************************************//
		//EmailAddress = dicCommon.get("testerEmailAddress");
		//Password = dicCommon.get("testerPassword");
		
		strStepDescription = "Login to deviceConnect with valid test user.";
		strExpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login();
		
		isEventSuccessful= selectStatus("In Use");
		if(dicOutput.get("devcie")!=null)
		{
			isEventSuccessful = searchDevice(dicOutput.get("devcie"), "devicename");
		}
		else
		{
			isEventSuccessful = searchDevice(devicesSelected.get(0), "devicename");
		}
		String userName = GetTextOrValue("//div[@class='status-description-row'][2]", "text");
		strStepDescription = "Reserved_devices_displayed_as_Reserved_to_other_users_for_the_reserved_time_period ";
		strExpectedResult = "Reserved_devices_should be displayed_as_Reserved_to_other_users_for_the_reserved_time_period ";
		
		if(userName.contains("Reserved"))
		{
			strActualResult = "Reserved_devices_is displayed_as_Reserved_to_other_users_for_the_reserved_time_period ";
			isEventSuccessful = true;
		}
		else
		{
			strActualResult = "Reserved_devices_is not displayed_as_Reserved_to_other_users_for_the_reserved_time_period ";
			isEventSuccessful = false;
		}
		
		
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		strStepDescription = "Verify_that_admin_users_can_reboot_one_or_more_devices_having_status_Reserved";
		strExpectedResult = "_admin_users_should be able to_reboot_one_or_more_devices_having_status_Reserved.";
		waitForPageLoaded();
		isEventSuccessful =  PerformAction("btnConnectArrow_Devicespage", Action.Click);
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful =  PerformAction("(//td[@class='btn-column last-column'])[1]//ul[@class='dropdown-menu']//a[text()='Reboot']", Action.Click);
			if(isEventSuccessful)
			{
				waitForPageLoaded();
				isEventSuccessful = PerformAction("//button[text()='Reboot device']", Action.Click);
				if(isEventSuccessful)
				{
					waitForPageLoaded();
					isEventSuccessful = PerformAction("(//td[@class='status-row'])[1]//div[@class='status-description-row' and text() = 'Rebooting']", Action.WaitForElement,"60"); 
					isEventSuccessful = PerformAction("(//td[@class='status-row'])[1]//div[@class='status-description-row' and text() = 'Rebooting']", Action.isDisplayed); 
					if(isEventSuccessful)
					{
						waitForPageLoaded();
						PerformAction("browser",Action.WaitForPageToLoad);
						isEventSuccessful = PerformAction("(//td[@class='status-row'])[1]//div[@class='status-description-row' and text()[contains(.,'Reserved')]]", Action.WaitForElement,"120");
						PerformAction("btnRefresh_Devices",Action.Click);
						waitForPageLoaded();
						isEventSuccessful = PerformAction("(//td[@class='status-row'])[1]//div[@class='status-description-row' and text()[contains(.,'Reserved')]]", Action.isDisplayed); 
						if(isEventSuccessful)
						{
							strActualResult = "Device is rebooted successfully.. + admin_users_is able to_reboot_one_or_more_devices_having_status_Reserved."; 
						}
						else
						{
							strActualResult = "Device is not rebooted successfully.." + strErrMsg_GenLib;
						}
					}
					else
					{
						strActualResult = "Rebooting status is not dispalyed." + strErrMsg_GenLib;
					}
					
					
				}
				else
				{
					strActualResult = "Could not clicked on Reboot device button";
				}
			}
			else
			{
				strActualResult = "Could not clicked on Reboot button";
			}
		}
		else
		{
			strActualResult = "Could not clicked on connect arrow button";
		}
		
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}	

}
