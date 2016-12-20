package com.test.java;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
import com.opera.core.systems.scope.protos.EcmascriptProtos.Value;
/*
 * Jira Test Case Id: QA-1560
 */
public class _701_Verify_that_there_is_no_old_symbol_displayed_for_recurring_reservations extends ScriptFuncLibrary{
	
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String emailID,deviceName,deviceStatus,reservedByName,deviceStatus_DI,reservedByName_DI,recurringIcon;

	String weekday[] = {"TH"};
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
			// Step 4 : Go to Devices Page and get Available device Name
			//*************************************************************//
			GoToDevicesPage();
			Values =  GetDeviceDetails(1,"devicename","list","Available");
			isEventSuccessful  = (boolean) Values[0] ;
			deviceName = (String) Values[1];
			GoToReservationsPage();
			/*selectStatus("Available");
			Values = GoTofirstDeviceDetailsPage();			
			deviceName=(String) Values[1];*/
			
			
			//*************************************************************/                     
			// Step 4 : Go to Create reservation page
			//*************************************************************//
			isEventSuccessful=GoToReservationsPage();
			if(isEventSuccessful)
			{
				isEventSuccessful=GoToCreateReservationPage();
			}
			
			//**************************************************************************//
			// Step 5 : Create recurring reservation for device
			//**************************************************************************//
			strstepDescription = "Create recurring reservation for device Reservation for device";
			strExpectedResult = "Recurring reservation completed for device";
			Calendar c = Calendar.getInstance();
			Date newDate = c.getTime();
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
	        String FormatedTodayDate = format.format(c.getTime());  // returns new date object, number of hours provided (in the future)
			String EnddateAfteradding = addDays(null, 30,"MM/dd/yy");
			
			
			isEventSuccessful = RSVD_CreateReservationWeekly(weekday, FormatedTodayDate, EnddateAfteradding, "PM", "AM", "2:30", "3:30", deviceName,"2",true);
				
			if(isEventSuccessful){
				strActualResult="Reservation Created successfully";
			}
			else{
				strActualResult="No reservation created";
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			
			//**************************************************************************//
			// Verify recurring reservation does not contain @ symbol
			//**************************************************************************//
			strstepDescription = "Verify @ symbol is not present";
			strExpectedResult = "@ Symbol is not present with recurring reservations";
			isEventSuccessful=GoToReservationsPage();
			isEventSuccessful=PerformAction(dicOR.get("recurrenceIcon_ReservationsPage"), Action.isDisplayed);
			if(isEventSuccessful){
				recurringIcon=GetTextOrValue(dicOR.get("recurrenceIcon_ReservationsPage"), "text");
				if(recurringIcon.contains("@")){
					isEventSuccessful=false;
					strActualResult="@symbol is displayed for recurring reservation";
				}
				else{
					isEventSuccessful=true;
					strActualResult="@symbol is not present with recurring reservations";
				}
			}
			else{
				strActualResult="No recurring reservation icon found";
			}
			
		 
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}
}