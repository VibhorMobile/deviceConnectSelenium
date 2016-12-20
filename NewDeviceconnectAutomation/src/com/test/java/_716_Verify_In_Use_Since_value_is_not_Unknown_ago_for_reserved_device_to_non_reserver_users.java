package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-218
 */
public class _716_Verify_In_Use_Since_value_is_not_Unknown_ago_for_reserved_device_to_non_reserver_users extends ScriptFuncLibrary {


	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,inUse,outputText = "";
	Object[] Values = new Object[5]; 






	public final void testScript() throws InterruptedException, IOException
	{
		try{

			String EmailAddress = dicCommon.get("testerEmailAddress");
			String Password = dicCommon.get("testerPassword");

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
			selectPlatform("iOS");
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
			// Step 4 : Retain Device
			//**************************************************************************//

			//**************************************************************************//
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
			// Step 5:Logout from Application
			//**************************************************************************//
			isEventSuccessful=Logout();
			//**************************************************************************//
			// Step 6:Login with tester credentials
			//**************************************************************************//
			isEventSuccessful=Login(EmailAddress,Password);

			GoToDevicesPage();

			//**************************************************************************//
			// Step 7:Search for device and Verify In use details
			//**************************************************************************//

			searchDevice(deviceName, "devicename");
			isEventSuccessful = (boolean) GoTofirstDeviceDetailsPage()[0];
			strstepDescription = "Verify In use for reserved device does not contain Unknown";
			strExpectedResult = "In use field showing valid details ";
			PerformAction(dicOR.get("lnkShowDetails_DeviceDetailsPage"),Action.Click);
			inUse=GetTextOrValue(dicOR.get("eleInUse_DeviceDetailsPage"), "text");
			if(inUse.contains("Unknown")){
				strActualResult="In Use details showing Unknown for the device: "+deviceName+"with Value:"+inUse;
				isEventSuccessful=false;
			}
			else{
				isEventSuccessful=true;
				strActualResult="In Use showing correct details for device : "+deviceName+"with Value:"+inUse;
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		
			//**************************************************************************//
			// Step 8 : Release device.
			//**************************************************************************//
			ExecuteCLICommand("release", "iOS", "", "", deviceName, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");

			//**************************************************************************//
			// Step 5:Logout from Application
			//**************************************************************************//
			isEventSuccessful=Logout();
			//**************************************************************************//
			// Step 6:Login with Admin credentials
			//**************************************************************************//
			isEventSuccessful=Login();

			
			//**************************************************************************//
			// Step 8 : Cancel the resrvation for device
			//**************************************************************************//
			CancelReservation(deviceName);
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify In Use for reserved device--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

}
