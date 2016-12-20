package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1178
 */
public class _694_Verify_that_user_name_for_a_reserved_device_should_not_be_blank_in_case_the_user_details_have_no_name extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String emailID,deviceName,deviceStatus,reservedByName,deviceStatus_DI,reservedByName_DI;






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
			GoToDevicesPage();

			//*************************************************************/                     
			// Step 2 : Go to Manage your Account
			//*************************************************************//
			strstepDescription="Click on Manage Your Account from Dropdown";
			strExpectedResult="Successfully clicked Manage your Account from Dropdown";
			waitForPageLoaded();
			isEventSuccessful=PerformAction(dicOR.get("eleUserEmail_UsersPage"), Action.Click);
			if(isEventSuccessful){
				waitForPageLoaded();
				isEventSuccessful=PerformAction(dicOR.get("eleManageAccount_UsersPage"), Action.Click);
				if(isEventSuccessful){
					waitForPageLoaded();
					isEventSuccessful=true;
					strActualResult="Clicked on Manage your Account from Dropdown";
				}
				else{

					strActualResult="Not able to click on Manage your Account from Dropdown";
					isEventSuccessful=false;
				}
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);


			//*************************************************************/                     
			// Step 3 : Clear first name and last name for user
			//*************************************************************//

			strstepDescription="Clear First and last Name for user";
			strExpectedResult="First Name and Last name for user edited";
			waitForPageLoaded();
			isEventSuccessful=PerformAction(dicOR.get("eleFirstName_UserDetailsPage"), Action.Clear);
			if(isEventSuccessful){
				waitForPageLoaded();
				isEventSuccessful=PerformAction(dicOR.get("eleLastName_UserDetailsPage"), Action.Clear);
				if(isEventSuccessful){
					waitForPageLoaded();
					isEventSuccessful=PerformAction("btnSave", Action.Click);
					if(isEventSuccessful){
						waitForPageLoaded();
						strActualResult="FirstName and Last Name for User cleared";
					}
					else{
						strActualResult="Unable to save edited details";
					}
				}
				else{
					strActualResult="Unable to edit Last name";
				}
			}
			else{
				strActualResult="Unable to edit First Name ";
			}

			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//*************************************************************/                     
			// Step 4 : Go to Devices Page and get Available device Name
			//*************************************************************//
			GoToDevicesPage();
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();
			deviceName=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");


			//*************************************************************/                     
			// Step 4 : Go to Create reservation page
			//*************************************************************//
			isEventSuccessful=GoToReservationsPage();
			if(isEventSuccessful)
			{
				isEventSuccessful=GoToCreateReservationPage();
			}

			//**************************************************************************//
			// Step 5 : Reserve the device
			//**************************************************************************//
			strstepDescription = "Create Reservation for device";
			strExpectedResult = "Reservation Done";
			isEventSuccessful=RSVD_CreateReservationNever(deviceName,true);
			if(isEventSuccessful){
				strActualResult="Reservation Created successfully";
			}
			else{
				strActualResult="No reservation created";
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//**************************************************************************//
			// Step 6 : Logout
			//**************************************************************************//
			Logout();

			//**************************************************************************//
			// Step 7 : Login with tester user
			//**************************************************************************//
			Login(EmailAddress,Password);
			//**************************************************************************//
			// Step 7 : Search for device and go to device details page
			//**************************************************************************//
			strstepDescription="Verify in reserved by space emailid of user is coming";
			strExpectedResult="email id of user coming in reserved by";
			PerformAction("browser",Action.Refresh);
			searchDevice(deviceName,"devicename");
			waitForPageLoaded();
			deviceStatus_DI=GetTextOrValue(dicOR.get("eleDeviceStatus_ListView"), "text");
			System.out.println(deviceStatus_DI);

			//Reserved by name from index page
			waitForPageLoaded();
			reservedByName_DI=deviceStatus_DI.substring(deviceStatus_DI.indexOf("(")+1,deviceStatus_DI.indexOf(")"));
			System.out.println(reservedByName_DI);
			GoTofirstDeviceDetailsPage();
			waitForPageLoaded();
			PerformAction(dicOR.get("lnkShowDetails_DeviceDetailsPage"),Action.Click);
			waitForPageLoaded();
			deviceStatus=GetTextOrValue(dicOR.get("eleStatus_DeviceDetailsPage"), "text");
			System.out.println(deviceStatus);

			//Reserved by name from details page

			reservedByName=deviceStatus.substring(deviceStatus.indexOf("(")+1,deviceStatus.indexOf(")"));
			System.out.println(reservedByName);

			if(reservedByName_DI.contains(dicCommon.get(EmailAddress))&&reservedByName.contains(dicCommon.get(EmailAddress))){
				strActualResult="Device is reserved by Email";
				isEventSuccessful=true;
			}
			else{
				strActualResult="Device is not reserved by Email";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			
			 //**************************************************************************//
			 // Step 8 : Cancel the resrvation for device
			 //**************************************************************************//
			 CancelReservation(deviceName);
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}




}
