package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: 10-Feb-2016
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-748
 */

public class _570_Verify_that_list_of_reservations_displays_date_in_the_format_of_MM_DD_YYYY extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strexpectedResult = "", strActualResult = "",strstepDescription="";
	String [] android={"Android"};
	
	public final void testScript()
	{
		
		//*******************************//
		// Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to Reservations Page //
		//**********************************************************//   
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToReservationsPage();
		}
		
		//*************************************************************//                     
		// Step 3 : Verify Reservation dates are in format mm/dd/yyyy.
		//*************************************************************//                     
		strstepDescription = "Verify Reservation dates are in format mm/dd/yyyy";
		strexpectedResult = "Reservation dates should be in format mm/dd/yyyy";
		isEventSuccessful =verifyReservationDateColumnValue("ReservationDates");
		if (isEventSuccessful)
		{
			strActualResult ="Reservation dates are in format mm/dd/yyyy.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}