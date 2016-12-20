package com.test.java;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.lang.Math.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.APILibrary.Component;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
import com.sun.jna.platform.win32.COM.Dispatch.ByReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.record.formula.udf.UDFFinder;
import org.openqa.jetty.jetty.servlet.Default;
import org.openqa.selenium.By;


/*
 * Author : Jaishree Patidar
 * Creation Date: 05 Oct 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have atleast 1 android and iOS application
 * Admin user can only be used
 */

public class _2006_Report_Usage_UserSpecific extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	
	Boolean value;
	Object[] values = new Object[2];
	public Map<String,String> expectedResultMap= new HashMap<String,String>();
	public Map<String,String> actualResultMap = new HashMap<String,String>(); // Declaring a HashMap to store actual results key and values
	public Map<String,String> oldActualResultMap = new HashMap<String,String>();
//	String reportOf[] = {"application","device","user"};
	
	String reportOf[] = {"user"};
	
	
	public final void testScript()
	{
		try
		{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_2006_Report_Usage_UserSpecific");
			
			String Id="";
			String serverIP=dicCommon.get("ApplicationURL");
			
			//****************************Code to create expected Result Starts here *****************************************************************
			// Step 1 : login to deviceConnect with valid user credentials.
			isEventSuccessful = Login();
			
			for (String reportFor : reportOf)
			{
				
				//************************Code for getting actual result from API Request starts here *******************************************************************
				//adding heading in APIOverallComparison HTML  for the AppType, Property and Value used in API Request
				apiReporter.apiNewHeading("User Specific History");
				
				
				//Getting today's date in yyyy-MM-dd format
				String todaydt=apiMethods.getTodaysDate("yyyy-MM-dd");
				
				
				//Getting a date 5 days ago for defining the date range
				String fiveDaysAgodt = apiMethods.getDate(-5,"yyyy-MM-dd");
		        System.out.println(fiveDaysAgodt);
		        
		        
		        //**************************************************************************//
				// Step 2 : Go to current user's details page
				//**************************************************************************//
				isEventSuccessful=GoToUsersPage();
				PerformAction("browser","waitforpagetoload");
				searchDevice_DI("admin" );
				
				//isEventSuccessful=(boolean) GoToFirstUserDetailsPage()[0];

				//**************************************************************************//
				// Step 3 : Go to specific user account
				//**************************************************************************//
				strstepDescription = "Verify user account page opened";
				strexpectedResult = "User account page should be displayed";
				PerformAction("browser","waitforpagetoload");
				isEventSuccessful=PerformAction(dicOR.get("btnEditUser_ListView").replace("__EMAILID__", "admin"), Action.Click);
				if(isEventSuccessful)
				{
					strActualResult="User account page displayed";
				}
				else
				{
					strActualResult="Unable to click on given mail id";
				}
				reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
				
				//Id=getAppGUID();
				
				String temp[]=driver.getCurrentUrl().split("/");
				Id=temp[temp.length-1];
				
		        //isEventSuccessful=GoToSpecificUserDetailsPage()
				if(isEventSuccessful)
					isEventSuccessful = GoToUsagePage("2");
				else
					return;
				
				//Set Customize date.
				isEventSuccessful=setCustomizedDates_SFL(fiveDaysAgodt,todaydt);
		        
			
				//--------------------------------------Creating and executing API requests --------------------------------------------------------
		        String component= "Report", parameterList="type,"+reportFor+"Id,fromDate,toDate", valueList="usage,"+Id+","+fiveDaysAgodt+","+todaydt; //declaration and intialization of curl command varaiables.
				String jsonResponse; //declaration of string to capture JSON Response of the API request.
				String command=apiMethods.createCurlCommand(component, parameterList, valueList);	//Creates a curl command with component, parameterList and valueList.
				
				//adding heading in APIOverallComparison HTML for the curl command created
				apiReporter.apiNewHeading(command);
				
				// Step : Execute curl command to get JSON response of the request
				jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
				apiReporter.apiAddBlock("API Response: "+jsonResponse);

				int apiRecordCount=apiMethods.getRecordCount_Report(jsonResponse);
				System.out.println("API Record count : "+apiRecordCount);
				apiReporter.apiAddBlock("API Record count : "+apiRecordCount);
				
				// Step : Verifying record count is equal to 1 as using Id as a parameter
				if (apiRecordCount > 0) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
				{
					strActualResult="As expected getting atleast 1 record.";
					isEventSuccessful=true;
				}
				else
				{
					strActualResult= "Either getting no records which is not expected. Hence failing stop test. <br> JSON Response =" +jsonResponse;
					isEventSuccessful=false;
					reporter.ReportStep("Verifying record count in JSON reponse", "Record count should be equal to 1 as using Id as a parameter",strActualResult ,isEventSuccessful );
					System.out.println(jsonResponse);
					throw new RuntimeException("No usage available for admin user in given date range (last 5 days).");
				}
				reporter.ReportStep("Verifying record count in JSON reponse", "Record count should be equal to 1 as using Id as a parameter",strActualResult ,isEventSuccessful );
				System.out.println(jsonResponse);
				
				isEventSuccessful=apiMethods.verifyfieldsNotNullInAPIResponse(jsonResponse, "id,deviceId,deviceName,operatingSystem,operatingSystemVersion,startDate,endDate,duration,performedById", "usage");
				//--------------------------------------------------------------------------
			
				Thread.sleep(10000);
				
				int expectedAppSpecificRecordCount;
				
				//Declaring ArrayList to get the updated lists after function execution
				ArrayList<String> udeviceNameRecords = new ArrayList<>();
				ArrayList<String> uIEventDateRecords = new ArrayList<>();
				ArrayList<String> uIPerfomedByNameRecords = new ArrayList<>();
				ArrayList<String> uIAppNameRecords = new ArrayList<>();
				
				//-------------------------------------------Getting UI history records for the app Id------------------------
				expectedAppSpecificRecordCount=apiMethods.getReportRecordFromUIForSpecificApp(Id, udeviceNameRecords, uIEventDateRecords, uIPerfomedByNameRecords, uIAppNameRecords);
			
				//comparing records counts returned from API and UI
				if(apiRecordCount==expectedAppSpecificRecordCount)
					apiReporter.apiPassBlock("correct number of records are returned by aPI");
				else
					apiReporter.apiErrorBlock("Incorrect number of records are returned by API actual:"+apiRecordCount+" and expected: "+expectedAppSpecificRecordCount);
				
				//----------------Comparing expected values with the actual values (max 5 records are verified so far)-----------------------
				isEventSuccessful=apiMethods.compareAPIAndUIReportData("usage",jsonResponse, Id, actualResultMap, udeviceNameRecords, uIEventDateRecords, uIPerfomedByNameRecords, uIAppNameRecords);
				
			}
		}
		catch(RuntimeException rnErr)
		{
			apiReporter.apiErrorBlock("Error Message "+rnErr.getMessage() +" "+apiMethods.strErrMsg_ApiLib);
			rnErr.printStackTrace();
		}
		catch(Exception ex)
		{
			apiReporter.apiErrorBlock("Something went wrong... Error Message "+ex.getMessage() +" "+apiMethods.strErrMsg_ApiLib);
			ex.printStackTrace();
		}
		finally
		{
			//Adds a step Provides API Overall Comparison file link in DC Selenium Execution Report
			apiReporter.apiOverallHtmlLinkStep();

		}
			
	}	

}
