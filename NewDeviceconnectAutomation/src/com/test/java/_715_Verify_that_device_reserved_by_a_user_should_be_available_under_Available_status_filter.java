package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1165
 */
public class _715_Verify_that_device_reserved_by_a_user_should_be_available_under_Available_status_filter extends ScriptFuncLibrary {
	
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String emailID,deviceName,deviceStatus,reservedByName,deviceStatus_DI,reservedByName_DI,recurringIcon;

	




	public final void testScript() throws InterruptedException, IOException
	{
		try{
			
			//*************************************************************//                     
			// Step 1 : login to deviceConnect with admin user.
			//*************************************************************//  


			isEventSuccessful = Login();
			
			//Step  - Delete All Reservations
			GoToReservationsPage();
			RSVD_DeleteAllReservations();
			
			
			//*************************************************************/                     
			// Step 2 : Go to Devices Page and get Available device Name
			//*************************************************************//
			GoToDevicesPage();
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();
			deviceName=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");
			

			//*************************************************************/                     
			// Step 3 : Go to Create Reservation
			//*************************************************************//
			isEventSuccessful=GoToReservationsPage();
			if(isEventSuccessful)
			{
				isEventSuccessful=GoToCreateReservationPage();
			}
			//**************************************************************************//
			// Step 4 : Reserve the device
			//**************************************************************************//
			strstepDescription = "Create Reservation for device";
			strExpectedResult = "Reservation Created for Device";
			isEventSuccessful=RSVD_CreateReservationNever(deviceName,true);
			if(isEventSuccessful){
				strActualResult="Reservation Created successfully";
			}
			else{
				strActualResult="No reservation created";
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 6 : Go to Devices and Check if Device Appearing in Available Devices
			//**************************************************************************//
			strstepDescription = "Verify in Devices that Device is present with Available Status";
			strExpectedResult = "Reserved devic for same user should show up with available Status";
			GoToDevicesPage();
			selectStatus("Available");
			isEventSuccessful=searchDevice(deviceName,"devicename");
			 if(isEventSuccessful){
				 strActualResult="Device is present with Available Status";
			 }
			 else{
				 strActualResult="device is not present with availabe status";
			 }
			 //**************************************************************************//
			 // Step 6 : Cancel the resrvation for device
			 //**************************************************************************//
			 CancelReservation(deviceName);
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

}
