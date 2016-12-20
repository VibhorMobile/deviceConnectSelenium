package com.test.java;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
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
 * Creation Date: 13 Oct 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have some user related history within last 2 days 
 * Admin user can only be used
 */

public class _2009_Report_History_OnlyToDate extends ScriptFuncLibrary {

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
	
	String reportOf[] = {"application"};
	
	
	public final void testScript()
	{
		try
		{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_2009_Report_History_OnlyToDate");
			
			String Id="";
			
			//****************************Code to create expected Result Starts here *****************************************************************
			// Step 1 : login to deviceConnect with valid user credentials.
			isEventSuccessful = Login();
			
			for (String reportFor : reportOf)
			{
				
				//************************Code for getting actual result from API Request starts here *******************************************************************
				//adding heading in APIOverallComparison HTML  for the AppType, Property and Value used in API Request
				apiReporter.apiNewHeading("Application Specific History");
				
				//Getting today's date in yyyy-MM-dd format
				String todaydt=apiMethods.getTodaysDate("yyyy-MM-dd");
				
				
				//Getting a date 2 days ago for defining the date range
				String twoDaysAfterdt = apiMethods.getDate(-2,"yyyy-MM-dd");
		        System.out.println(twoDaysAfterdt);
		        
		        
		        //Navigating to Usage page.		        
		        isEventSuccessful=GoToDevicesPage();
				if(isEventSuccessful)
					isEventSuccessful = GoToHistoryPage("4");
				else
					return;
				
				
				//******************************************************************//
				//*Set drop down value 'All'. *****//
				//******************************************************************//
				strstepDescription = "Set date drop down value 'All'";
				strexpectedResult =  "'Today' should be selected in dates drop down";

				if(isEventSuccessful)
				{
					isEventSuccessful=PerformAction("DatesDrpDwnonUsagePage",Action.Select,"All");
					if(isEventSuccessful)
					{
						strActualResult = "'All' selected in dates drop down";
					}
					else
					{
						strActualResult = strErrMsg_AppLib;
					}
				}
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
				
				

				//******************************************************************//
				//*Set drop down value 'Oldest first'. *****//
				//******************************************************************//
				strstepDescription = "Set date drop down value 'Oldest first'";
				strexpectedResult =  "'Oldest first' should be selected in dates drop down";
				
				if(isEventSuccessful)
				{
					isEventSuccessful=PerformAction("orderdropdown",Action.Select,"Oldest first");
					if(isEventSuccessful)
					{
						strActualResult = "'All' selected in dates drop down";
					}
					else
					{
						strActualResult = "Unable to select value from order drop down";
					}
				}
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
				
				PerformAction("browser","waitforpagetoload");
				
				selectEventType("Application Launched,Application Install");
				
				
				PerformAction("browser","waitforpagetoload");
				
				
				String stDate=GetTextOrValue("//*[@id='actual-content-body']//table/tbody/tr[1]/td[1]", "text");
				stDate=stDate.split(" ")[0];
				
				System.out.println(stDate);
				

				
				DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
				DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
				Date date =formatter1.parse(stDate); 
				
				String startDate=formatter2.format(date);
				



				Calendar c = Calendar.getInstance();
				c.setTime(date); 
				c.add(Calendar.DATE, 2);			
				
				
				
				twoDaysAfterdt= formatter2.format(c.getTime());
				
				
				System.out.println(startDate + " ---------------------" +twoDaysAfterdt);
				
				
				PerformAction("browser","waitforpagetoload");
				//Set Customize date.
				isEventSuccessful=setCustomizedDates_SFL(startDate,twoDaysAfterdt);
				
				strstepDescription = "Set date drop down value 'Newest first'";
				strexpectedResult =  "'Newest first' should be selected in dates drop down";
				
				if(isEventSuccessful)
				{
					isEventSuccessful=PerformAction("orderdropdown",Action.Select,"Newest first");
					if(isEventSuccessful)
					{
						strActualResult = "'Newest first' selected in dates drop down";
					}
					else
					{
						strActualResult = "Unable to select value from order drop down";
					}
				}
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
				
				
				
				PerformAction("browser","waitforpagetoload");
				
				
				/*//Getting today's date in yyyy-MM-dd format
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date todaydate = new Date();
				String todaydt=dateFormat.format(todaydate);
				
				
				//Getting a date 2 days ago for defining the date range
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -2);
				Date twoDayAgodate = cal.getTime();    
		        String twoDaysAfterdt = dateFormat.format(twoDayAgodate);
		        
		        
		        //Navigating to Usage page.		        
		        isEventSuccessful=GoToDevicesPage();
				if(isEventSuccessful)
					isEventSuccessful = GoToHistoryPage("4");
				else
					return;
				

				//Step 3 : Set Customize date. /
				strstepDescription = "Set customizing dates";
				strexpectedResult =  "Dates should be set in start and end date boxes";
				if(isEventSuccessful)
				{
					isEventSuccessful=setCustomizedDates(twoDaysAfterdt,todaydt);
					if(isEventSuccessful)
					{
						strActualResult = "Dates set in start and end date boxes.";
					}
					else
					{
						strActualResult = strErrMsg_AppLib;
					}
				}
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
				*/
				
				//Getting App ID of the application in the first usage record with App Name  -- To get the API response having atleast 1 record.
				if (!(PerformAction(dicOR.get("eleNoUsageErrorMessage"), Action.Exist)))
				{
					Id=getAttribute("("+dicOR.get("eleRowHavingAppURL_Usage")+")[1]", "href"); //getting appID for the first usage 	
					Id=Id.substring(Id.lastIndexOf("/")+1);
				}
				
				
				//--------------------------------------Creating and executing API requests --------------------------------------------------------
//		        String apitoday=todaydt+"%2023%3A59%3A59.8";
				//Create the curl command to get application details with Id
//				String component= "Report", parameterList="type,"+reportFor+"Id,from,to", valueList="history,"+Id+","+apiMethods.convertDateLocalTimezoneToUTC(twoDaysAfterdt).replace(":", "%3A").replace(" ", "%20")+".000000Z,"+apiMethods.convertDateLocalTimezoneToUTC(todaydt).replace(":", "%3A").replace(" ", "%20")+".000000Z"; //declaration and intialization of curl command varaiables. 
		        
		        String component= "Report", parameterList="type,"+reportFor+"Id,toDate", valueList="history,"+Id+","+twoDaysAfterdt; //declaration and intialization of curl command varaiables.
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
				}
				reporter.ReportStep("Verifying record count in JSON reponse", "Record count should be equal to 1 as using Id as a parameter",strActualResult ,isEventSuccessful );
				System.out.println(jsonResponse);
				
				isEventSuccessful=apiMethods.verifyfieldsNotNullInAPIResponse(jsonResponse, "id,eventDate,applicationId", "history");
				//--------------------------------------------------------------------------
			
				Thread.sleep(10000);
				
				ArrayList<String> uIHistoryRecords = new ArrayList<>();
				ArrayList<String> udeviceNameRecords = new ArrayList<>();
				ArrayList<String> uIEventDateRecords = new ArrayList<>();
				ArrayList<String> uIPerfomedByNameRecords = new ArrayList<>();
				ArrayList<String> uIAppNameRecords = new ArrayList<>();
				
				//-------------------------------------------Getting UI history records for the app Id------------------------				
				int expectedAppSpecificRecordCount=driver.findElements(By.xpath(dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id))).size();
//				uIHistoryRecords.add(GetTextOrValue(dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id), "text"));
				for (int ui=1; ui<=expectedAppSpecificRecordCount; ui++)
				{
					uIHistoryRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+ui+"]", "text"));
					udeviceNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[3]", "text"));
					uIEventDateRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[1]", "text"));
					uIPerfomedByNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[4]", "text"));
					uIAppNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[5]", "text"));
					
				}
					
				Thread.sleep(10000);
				
				//If more pages are available.. adding data to the expected 
				int curcount=0;
				while (PerformAction(dicOR.get("NextUsagePagebtn"), Action.isDisplayed))
				{
					System.out.println("Clicked on next");
					PerformAction(dicOR.get("NextUsagePagebtn"), Action.Click);
					Thread.sleep(30000);
					curcount=driver.findElements(By.xpath(dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id))).size();
					expectedAppSpecificRecordCount=expectedAppSpecificRecordCount+curcount;
					for (int ui=1; ui<=curcount; ui++)
					{
						uIHistoryRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+ui+"]", "text"));
						udeviceNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[3]", "text"));
						uIEventDateRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[1]", "text"));
						uIPerfomedByNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[4]", "text"));
						uIAppNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[5]", "text"));
						
					}
					
				}
				
				
				System.out.println("app specific rows count on UI :"+expectedAppSpecificRecordCount);
				apiReporter.apiAddBlock("app specific rows count on UI :"+expectedAppSpecificRecordCount);
				System.out.println(uIHistoryRecords);
				//--------------------------------------------------------------------------------------------------------------------------------------------
				
				//comparing records counts returned from API and UI
				if(apiRecordCount==expectedAppSpecificRecordCount)
					apiReporter.apiPassBlock("correct number of records are returned by aPI");
				else
					apiReporter.apiErrorBlock("Incorrect number of records are returned by API actual:"+apiRecordCount+" and expected: "+expectedAppSpecificRecordCount);
				
				//----------------Comparing expected values with the actual values (max 5 records are verified so far)-----------------------
				String[] expectedArray=new String[5];
				String expdeviceName,expeventDate,expperformedByName,expappName;
				String actdeviceName,acteventDate,actperformedByName,actappName;
				int count=0;
				
				boolean flagMatch=true;
				int verificationRowCount=5;
				if (apiRecordCount<verificationRowCount)
					verificationRowCount=apiRecordCount;
				
				for (int row=1; row<=verificationRowCount ; row++)
				{
					if (row>apiRecordCount)
						break;
					
					actualResultMap=apiMethods.getKeyValuePair_Report(jsonResponse,count,"history"); //getting key value pair from the JSON response in a HashMap
					System.out.println(actualResultMap);
					/*expectedArray[count]=GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(row)+"]", "text");
					
					
					expdeviceName=GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(row)+"]/td[3]", "text");
					expeventDate=GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(row)+"]/td[1]", "text");
					expperformedByName=GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(row)+"]/td[4]", "text");
					expappName=GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(row)+"]/td[5]", "text");
					System.out.println(expdeviceName+" "+expeventDate+" "+expperformedByName+" "+expappName);
//					apiReporter.apiAddBlock("Expected (from UI): "+expdeviceName+" "+expeventDate+" "+expperformedByName+" "+expappName);
*/					

					expdeviceName=udeviceNameRecords.get(count);
					expeventDate=uIEventDateRecords.get(count);
					expperformedByName=uIPerfomedByNameRecords.get(count);
					expappName=uIAppNameRecords.get(count);
					System.out.println(expdeviceName+" "+expeventDate+" "+expperformedByName+" "+expappName);
//					apiReporter.apiAddBlock("Expected (from UI): "+expdeviceName+" "+expeventDate+" "+expperformedByName+" "+expappName);
					
					actdeviceName=actualResultMap.get("deviceName");
					acteventDate=apiMethods.convertDateUTCToLocalTimezone(actualResultMap.get("eventDate"));
					actperformedByName=actualResultMap.get("performedByName");
					actappName=actualResultMap.get("applicationName");
					System.out.println(actdeviceName+" "+acteventDate+" "+actperformedByName+" "+actappName);
					
//					apiReporter.apiAddBlock("Actual (from API): " +actdeviceName+" "+acteventDate+" "+actperformedByName+" "+actappName);
					
					if ((expappName.equals(actappName)) && (expdeviceName.equals(actdeviceName)) && (expeventDate.equals(acteventDate)) && (expperformedByName.equals(actperformedByName)))
					{
						apiReporter.apiPassBlock("Comparison Passed <br>Expected (from UI): "+expdeviceName+" "+expeventDate+" "+expperformedByName+" "+expappName +"<br> Actual (from API): " +actdeviceName+" "+acteventDate+" "+actperformedByName+" "+actappName);
					}
					else
					{
						flagMatch=false;
						apiReporter.apiErrorBlock("Comparison failed <br>Expected (from UI): "+expdeviceName+" "+expeventDate+" "+expperformedByName+" "+expappName +"<br> Actual (from API): " +actdeviceName+" "+acteventDate+" "+actperformedByName+" "+actappName);
						break;
					}
					System.out.println(expectedArray[count]);
					count++;
				}
				//--------------------------------------------------------------------------------------------------------------------------

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
