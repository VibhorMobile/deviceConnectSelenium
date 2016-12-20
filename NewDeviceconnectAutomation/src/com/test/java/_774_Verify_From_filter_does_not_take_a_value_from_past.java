package com.test.java;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-16
 */
public class _774_Verify_From_filter_does_not_take_a_value_from_past extends ScriptFuncLibrary{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";
	String [] android={"Android"};

	public final void testScript()
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();

			//**********************************************************//
			// Step 2 - Go to Reservations Page //
			//**********************************************************//                                   
			isEventSuccessful = GoToReservationsPage();

			//**********************************************************//
			// Step 3- Click on Form Calender
			//**********************************************************//  

			strStepDescription = "Open from Caleneder";
			strExpectedResult = "Calender Opened ";
			isEventSuccessful =PerformAction(dicOR.get("eleStartDateCalendar_CreateReservation"), Action.Click);

			if(isEventSuccessful)
			{
				strActualResult="From Date Calender Open";

			}
			else
			{
				strActualResult = "Calender not open";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//**********************************************************//
			// Step 3-Get Past date 
			//**********************************************************// 
			/*DateFormat dateFormat = new SimpleDateFormat("dd");
			 Date date =new Date();
			 String currentDate=dateFormat.format(date);
			 System.out.println(currentDate);*/
			Calendar lCal = Calendar.getInstance();
			int Date=lCal.get(Calendar.DATE);
			int date=Date-1;
			if(date==0){
				date=30;
			}
			System.out.println(date);


			//**********************************************************//
			// Step 4- Click on Calender and Verify if date is disabled
			//**********************************************************// 
			strStepDescription = "Verify past date is disabled in calender";
			strExpectedResult = "past date should be disabled";
			isEventSuccessful=verifyDateDisabled(date);
			if(isEventSuccessful){
				strActualResult="Date is Disabled";
			}
			else{
				strActualResult="Date is not disabled in calender";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);	

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Form factor--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}

	}
}
