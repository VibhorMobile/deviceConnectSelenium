package com.test.java;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Math.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
import com.sun.jna.platform.win32.COM.Dispatch.ByReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.openqa.jetty.jetty.servlet.Default;
import org.openqa.selenium.By;


/*
 * Author : Jaishree Patidar
 * Creation Date: 14 Oct 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have some user related history within last 2 days 
 * Admin user can only be used
 */

public class _2010_Report_History_TakeSkip extends ScriptFuncLibrary {

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

	String endDateTime;
	
	public final void testScript()
	{
		try
		{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_2010_Report_History_TakeSkip");
			
			String Id="";
			
			//Getting a date 2 days ago for defining the date range
			String oneDaysAgodt = apiMethods.getDate(-1,"yyyy-MM-dd");
	        System.out.println(oneDaysAgodt);
	        
				
				//************************Code for getting actual result from API Request starts here *******************************************************************
				//adding heading in APIOverallComparison HTML  for the AppType, Property and Value used in API Request
				apiReporter.apiNewHeading("Application Specific History");
				
				//--------------------------------------Creating and executing API requests --------------------------------------------------------
//		        String apitoday=todaydt+"%2023%3A59%3A59.8";
				//Create the curl command to get application details with Id
//				String component= "Report", parameterList="type,"+reportFor+"Id,from,to", valueList="history,"+Id+","+apiMethods.convertDateLocalTimezoneToUTC(twoDaysAfterdt).replace(":", "%3A").replace(" ", "%20")+".000000Z,"+apiMethods.convertDateLocalTimezoneToUTC(todaydt).replace(":", "%3A").replace(" ", "%20")+".000000Z"; //declaration and intialization of curl command varaiables. 
		        
		        String component= "Report", parameterList="type,toDate,take", valueList="history,"+oneDaysAgodt+",10"; //declaration and intialization of curl command varaiables.
				String jsonResponseTake; //declaration of string to capture JSON Response of the API request.
				String command=apiMethods.createCurlCommand(component, parameterList, valueList);	//Creates a curl command with component, parameterList and valueList.
				
				//adding heading in APIOverallComparison HTML for the curl command created
				apiReporter.apiNewHeading(command);
				
				// Step : Execute curl command to get JSON response of the request
				jsonResponseTake=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
				apiReporter.apiAddBlock("API Response: "+jsonResponseTake);
				
				
				

				
				
				

				int apiRecordCount=apiMethods.getRecordCount_Report(jsonResponseTake);
				int apiPageSize=apiMethods.getPageSize_Report(jsonResponseTake);
				System.out.println("API Record count : "+apiRecordCount +"API Page Size: "+apiPageSize);
				apiReporter.apiAddBlock("API Record count : "+apiRecordCount+"API Page Size: "+apiPageSize);
				
				// Step : Verifying record count is equal to 1 as using Id as a parameter
				if (apiRecordCount > 0) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
				{
					strActualResult="As expected getting atleast 1 record.";
					isEventSuccessful=true;
				}
				else
				{
					strActualResult= "Either getting no records which is not expected. Hence failing stop test. <br> JSON Response =" +jsonResponseTake;
					isEventSuccessful=false;
				}
				reporter.ReportStep("Verifying record count in JSON reponse", "Record count should be equal to 1 as using Id as a parameter",strActualResult ,isEventSuccessful );
				System.out.println(jsonResponseTake);
				
				isEventSuccessful=apiMethods.verifyfieldsNotNullInAPIResponse(jsonResponseTake, "id,eventDate,applicationId", "history");
				//--------------------------------------------------------------------------
				
				
				
				if ((apiRecordCount<10 && apiPageSize==apiRecordCount) || (apiPageSize==10))
					apiReporter.apiPassBlock("API page size is as expected: "+apiPageSize);
				else
					apiReporter.apiErrorBlock("API page size is not as expected:"+apiPageSize);
				
			
				Pattern pattern = Pattern.compile("\"eventDate\":\".*?\"");
				Matcher matcher = pattern.matcher(jsonResponseTake);
				if (matcher.find())
				{
					endDateTime=matcher.group(0).replace("\"", "").replace("eventDate:", "");
				    System.out.println(endDateTime);
				}

				
				//------------------------SKIP----------------------------------------------------------------------
				
			
				 	parameterList="type,take,skip,toDate";
				 	valueList="history,5,5,"+oneDaysAgodt; 
				 	//declaration and intialization of curl command varaiables.
					String jsonResponseSkip; //declaration of string to capture JSON Response of the API request.
					command=apiMethods.createCurlCommand(component, parameterList, valueList);	//Creates a curl command with component, parameterList and valueList.
					
					//adding heading in APIOverallComparison HTML for the curl command created
					apiReporter.apiNewHeading(command);
					
					// Step : Execute curl command to get JSON response of the request
					jsonResponseSkip=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
					apiReporter.apiAddBlock("API Response: "+jsonResponseSkip);

					int apiRecordSkipCount=apiMethods.getRecordCount_Report(jsonResponseSkip);
					int apiSkipPageSize=apiMethods.getPageSize_Report(jsonResponseSkip);
					System.out.println("API Record count : "+apiRecordSkipCount +"API Page Size: "+apiSkipPageSize);
					apiReporter.apiAddBlock("API Record count : "+apiRecordSkipCount+"API Page Size: "+apiSkipPageSize);
					
					// Step : Verifying record count is equal to 1 as using Id as a parameter
					if (apiRecordSkipCount > 0) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
					{
						strActualResult="As expected getting atleast 1 record.";
						isEventSuccessful=true;
					}
					else
					{
						strActualResult= "Either getting no records which is not expected. Hence failing stop test. <br> JSON Response =" +jsonResponseTake;
						isEventSuccessful=false;
					}
					reporter.ReportStep("Verifying record count in JSON reponse", "Record count should be equal to 1 as using Id as a parameter",strActualResult ,isEventSuccessful );
					System.out.println(jsonResponseSkip);
					isEventSuccessful=apiMethods.verifyfieldsNotNullInAPIResponse(jsonResponseSkip, "id,eventDate,applicationId", "history");
					//--------------------------------------------------------------------------
				

					if ((apiRecordSkipCount<5 && apiSkipPageSize==apiRecordSkipCount) || (apiSkipPageSize==5))
						apiReporter.apiPassBlock("API page size is as expected: "+apiSkipPageSize);
					else
						apiReporter.apiErrorBlock("API page size is not as expected: "+apiSkipPageSize );
					
					
					
					for (int recIt=1; recIt<=5; recIt++)
					{
						boolean flagMatch=true;
						actualResultMap=apiMethods.getKeyValuePair_Report(jsonResponseTake,recIt+5,"history");
						expectedResultMap=apiMethods.getKeyValuePair_Report(jsonResponseSkip,recIt,"history");
						
						Set <String> keySet =actualResultMap.keySet();
						for (String key: keySet)
						{
							if (!(actualResultMap.get(key).equals(expectedResultMap.get(key))))
								flagMatch=false;
									
						}
						if (flagMatch)
							apiReporter.apiPassBlock("Actual: "+actualResultMap.toString() +" <br>Expected: "+expectedResultMap.toString());
						else
							apiReporter.apiErrorBlock("Actual: "+actualResultMap.toString() +" <br>Expected: "+expectedResultMap.toString());
					}
					
					
			
		
			
		}catch(Exception ex)
		{
			apiReporter.apiErrorBlock("Something went wrong... Error Message "+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			//Adds a step Provides API Overall Comparison file link in DC Selenium Execution Report
			apiReporter.apiOverallHtmlLinkStep();

		}
			
	}	

}
