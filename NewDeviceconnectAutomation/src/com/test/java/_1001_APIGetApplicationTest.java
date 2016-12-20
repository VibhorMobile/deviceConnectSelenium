package com.test.java;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.lang.Math.*;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.sun.jna.platform.win32.COM.Dispatch.ByReference;

import java.util.ArrayList;

import org.openqa.jetty.jetty.servlet.Default;


/*
 * Author : Jaishree Patidar
 * Creation Date: 31 March 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have atleast 1 android and iOS application
 * Admin user can only be used
 */

public class _1001_APIGetApplicationTest extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	
	Boolean value;
	Object[] values = new Object[2];
	public Map<String,String> expectedResultMap= new HashMap<String,String>();
	public Map<String,String> actualResultMap = new HashMap<String,String>(); // Declaring a HashMap to store actual results key and values
	public Map<String,String> oldActualResultMap = new HashMap<String,String>();
	private String valueOfProp="";
	// Creating an array having all the model properties available for Application component
	String[] appModel ={"id","displayName","applicationIdentifier","version","buildVersion","operatingSystem","minimumOperatingSystemVersion",
			"applicationBlob","originalApplicationBlob","fileName","remotePort","versionCounter","iconBlob","fileByteCount","vendorApplicationName",
			"provisionExpirationDate","provisionsAllDevices","signingCertificateName","isSigningCertificatePresentInEmbeddedProvisionProfile","supportedFormFactors",
			"applicationErrors","enabled","createdDate","appTeamIdentifier","trustDylibTeamIdentifier","isTrustDylibEmbeddedInApp","notes","agentHash"};
	//Creating an array of all the query properties available for Application component
	String[] queryProp ={"id","displayName","applicationIdentifier","version","buildVersion","minimumOperatingSystemVersion",
			"applicationBlob","originalApplicationBlob","fileName"}; //,"operatingSystem"
	
	
	public final void testScript()
	{
		try
		{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1001_APIGetApplicationTest");
			
			String serverIP=dicCommon.get("ApplicationURL");
			String appId; //=androidAppId;
			
			//****************************Code to create expected Result Starts here *****************************************************************
			// Step 1 : login to deviceConnect with valid user credentials.
			isEventSuccessful = Login();
			
			
			String[] appType= {"iOS","Android"}; //Declaring an array to run for Android and iOS app
			//To run the query properties for both Android as wee
			
			for(int j=0; j<=1; j++)
			{
				isEventSuccessful=GoToApplicationsPage();
				searchDevice_DI(appType[j]); // Searching for Android or iOS on Application List page
				GoToFirstAppDetailsPage(); //Clicks on the first application listed
				appId=GetAppGUID(); //Gets the GUID of the Application whose application details page is displayed
				
				
				//Step 2: Getting expected Result map from application details page
				expectedResultMap=getApplicationExpectedMap(appId, serverIP);
				
				//Running the script for all the query properties available for Application component
				//Things to keep in mind while using this:
				//	1. for the first property in queryProp, value should be present in expectedResultMap
				//	2. If a property is not in Response Model, its values should be available in expectedResultMap
				//	3. Assign actualResultMap to oldActualResultMap at the end of the loop. So, that if the value of a property is not available in expectedResultMap, it can be assigned from the value returned in last aPI response
				
				for (String propertyInUse: queryProp)
				{
					System.out.println("------------------------------"+propertyInUse);
					if ((expectedResultMap.get(propertyInUse).equals("Not Verified")) & (oldActualResultMap.containsKey(propertyInUse))) //If the property is "Not Verified". Get the value from oldActualResultMap
						valueOfProp=oldActualResultMap.get(propertyInUse);
					else
						valueOfProp=expectedResultMap.get(propertyInUse); //If value exists other than "Not Verified",use the value in API Request
					
					//************************Code for getting actual result from API Request starts here *******************************************************************
					//adding heading in APIOverallComparison HTML  for the AppType, Property and Value used in API Request
					apiReporter.apiNewHeading(appType[j]+" Application - Get App (Using "+propertyInUse+" with value = "+valueOfProp+")");
					
					//Create the curl command to get application details with Id
					String component= "Application", parameterList=propertyInUse.trim()+",operatingSystem", valueList=valueOfProp.trim()+","+appType[j]; //declaration and intialization of curl command varaiables.
					String jsonResponse; //declaration of string to capture JSON Response of the API request.
					String command=apiMethods.createCurlCommand(component, parameterList, valueList);	//Creates a curl command with component, parameterList and valueList.
					
					//adding heading in APIOverallComparison HTML for the curl command created
					apiReporter.apiNewHeading(command);
					
					// Step : Execute curl command to get JSON response of the request
					jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
					
					// Step : Verifying record count is equal to 1 as using Id as a parameter
					if (apiMethods.getRecordCount(jsonResponse) > 0) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
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
					
					// Step : Creating expected HashMap from the JSON Response
					
					actualResultMap=apiMethods.getKeyValuePair(jsonResponse); //getting key value pair from the JSON response in a HashMap
		
					
					//Converting Bytes to MB
					String bytesVal=actualResultMap.get("fileByteCount");
					String fileSizeMB = apiMethods.convertFromBytes(bytesVal, "mb");
					actualResultMap.put("fileByteCount", fileSizeMB);
					
					//Converting date from UTC to local timezone & also updated its format
					String actualValue;
					ArrayList<String> keyArray= apiMethods.getmodel(jsonResponse);
					for (int i=0; i< keyArray.size() ; i++)
					{
						if (keyArray.get(i).indexOf("Date")>0)
						{
							actualValue=apiMethods.convertDateUTCToLocalTimezone(actualResultMap.get(keyArray.get(i)));
							actualResultMap.put(keyArray.get(i),actualValue);
						}
					}
					
					//****************************Code to create expected Result Ends here *****************************************************************		
					
					//***********code for getting expected starts here***************************************
					appId=actualResultMap.get("id");
					expectedResultMap=getApplicationExpectedMap(appId, serverIP);
					expectedResultMap.put("id", appId);
				
					//***********code for getting expected ends here***************************************
				
					//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************		
					//Comparing both the maps and reporting
					strActualResult="";
					Map<String,String> errorMap=new HashMap<String,String>();
					isEventSuccessful=apiMethods.compareActualAndExpectedMap(appModel, expectedResultMap,actualResultMap,errorMap);
					System.out.println(errorMap);
					if(isEventSuccessful)
					{
						strActualResult= "All the properties verified are matching";
						apiReporter.apiPassBlock("All the properties verified are matching");
					}
					else
					{
						strActualResult= "compareActualAndExpectedMap---" + errorMap + com.common.utilities.APILibrary.strErrMsg_ApiLib;
						apiReporter.apiErrorBlock("Comparison failing for "+ errorMap);
					}
					reporter.ReportStep("Compares Actual and Expected Map", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);
				
					
					
					//Step 10: Reporting app model with expected and actual values in the HTML report
					apiReporter.CreateAppModelTableInReport(appModel,expectedResultMap,actualResultMap,errorMap);
					//Adds a step Provides API Overall Comparison file link in DC Selenium Execution Report
					apiReporter.apiOverallHtmlLinkStep();
									
					//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************
					
					oldActualResultMap=actualResultMap; //Storing actualResult map for next iteration
				}
			
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
