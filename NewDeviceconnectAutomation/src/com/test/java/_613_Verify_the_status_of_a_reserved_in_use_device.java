package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import java.util.ArrayList;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-678
 */
public class _613_Verify_the_status_of_a_reserved_in_use_device extends ScriptFuncLibrary

{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	Object[] Values = new Object[5]; 
	private String strActualResult = "", devicename = "",EmailAddress, Password;
	
	public final void testScript()
	{
		
		// Step 1 - Login to deviceConnect//
		isEventSuccessful = Login();
		 
		//Step  - Delete All Reservations
		GoToReservationsPage();
		RSVD_DeleteAllReservations();
		
		// Step 2
		isEventSuccessful = GoToDevicesPage();
		
		// Step 3
		isEventSuccessful = selectStatus("Available");
			
		Values = AvailableDevices_DIpage();
		isEventSuccessful  = (boolean) Values[0] ;
		devicesSelected =  (ArrayList) Values[1] ;
		  
		// Step 4
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
					strActualResult = "Could not created reservation for." + devicesSelected.get(0) + strErrMsg_AppLib ;
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
		   
		// Step 5
		isEventSuccessful = Logout();
		      
		//**************************************************************************//
		// Step 6 : Login again as tester.
		//**************************************************************************//
		EmailAddress = dicCommon.get("testerEmailAddress");
		Password = dicCommon.get("testerPassword");
		
		strStepDescription = "Login to deviceConnect with valid test user.";
		strExpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login(EmailAddress, Password);
				
		// Step 7
		isEventSuccessful= selectStatus("In Use");
		if(dicOutput.get("devcie")!=null)
		{
			isEventSuccessful = searchDevice(dicOutput.get("devcie"), "devicename");
		}
		else
		{
			isEventSuccessful = searchDevice(devicesSelected.get(0), "devicename");
		}
				
		// Step 8		
		String devciestatus = GetTextOrValue("//div[@class='status-description-row'][2]", "text");
		strStepDescription = "Verify the_status_of_a_reserved_in_use_device";
		strExpectedResult = "the_status_of_a_reserved_in_use_device should be displayed as 'Reserved' ";
		
		if(devciestatus.contains("Reserved"))
		{
			strActualResult = "the_status_of_a_reserved_in_use_device is displayed as 'Reserved' "; 
			isEventSuccessful = true;
		}
		else
		{
			strActualResult = "the_status_of_a_reserved_in_use_device is not displayed as 'Reserved' "; 
			isEventSuccessful = false;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		   
	}
}
