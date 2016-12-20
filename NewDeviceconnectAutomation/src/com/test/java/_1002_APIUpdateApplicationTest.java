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
 * Creation Date: 6 April 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have atleast 1 android and iOS application
 * Admin user can only be used
 */

public class _1002_APIUpdateApplicationTest extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	
	Boolean value;
	Object[] values = new Object[2];
	public Map<String,String> expectedResultMap= new HashMap<String,String>();
	public Map<String,String> actualResultMap = new HashMap<String,String>(); // Declaring a HashMap to store actual results key and values
	private String valueOfProp="";
	// Creating an array having all the model properties available for Application component
	String[] appModel ={"id","displayName","applicationIdentifier","version","buildVersion","operatingSystem","minimumOperatingSystemVersion",
			"applicationBlob","originalApplicationBlob","fileName","remotePort","versionCounter","iconBlob","fileByteCount","vendorApplicationName",
			"provisionExpirationDate","provisionsAllDevices","signingCertificateName","isSigningCertificatePresentInEmbeddedProvisionProfile","supportedFormFactors",
			"applicationErrors","enabled","createdDate","appTeamIdentifier","trustDylibTeamIdentifier","isTrustDylibEmbeddedInApp","notes","agentHash"};
	//Creating an array of all the query properties available for Application component
	String[] updateProp ={"displayName"}; //,"operatingSystem"
	String oldValue,newValue;
	boolean isEventSuccessfulRecordCount ;
	ArrayList<String> errorList = new ArrayList<String>(); 
	
	public final void testScript()
	{
		try
		{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1002_APIUpdateApplicationTest");
			
			
			String serverIP=dicCommon.get("ApplicationURL");
			String appId; //=androidAppId;
			
			//****************************Code to create expected Result Starts here *****************************************************************
			// Step 1 : login to deviceConnect with valid user credentials.
			isEventSuccessful = Login();
			
			
			String[] appType= {"iOS","Android"}; //Declaring an array to run for Android and iOS app
			
			for(int j=0; j<=1; j++)
			{
				isEventSuccessful=GoToApplicationsPage();
				searchDevice_DI(appType[j]); // Searching for Android or iOS on Application List page
				GoToFirstAppDetailsPage(); //Clicks on the first application listed
				appId=GetAppGUID(); //Gets the GUID of the Application whose application details page is displayed
				
				
				//Step 2: Getting expected Result map from application details page
				expectedResultMap=getApplicationExpectedMap(appId, serverIP);
				
				
				//Running the script for all the query properties available for Application component
				
				for (String propertyInUse: updateProp)
				{
					isEventSuccessful=true;
					oldValue=expectedResultMap.get(propertyInUse);
					valueOfProp="API Updated Application Name";
					
					//************************Code for getting actual result from API Request starts here *******************************************************************
					//adding heading in APIOverallComparison HTML  for the AppType, Property and Value used in API Request
					apiReporter.apiNewHeading(appType[j]+" Application - Get App (Using "+propertyInUse+" with value = "+valueOfProp+")");
					
					//Create the curl command to get application details with Id
					String component= "Application", parameterList="verb"+",id", valueList="update"+","+appId, dataTag="{"+propertyInUse+":\\\"" +valueOfProp+"\\\"}"; //declaration and intialization of curl command varaiables.
					String jsonResponse; //declaration of string to capture JSON Response of the API request.
					String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json", dataTag);	//Creates a curl command with component, parameterList and valueList.
					
					//adding heading in APIOverallComparison HTML for the curl command created
					apiReporter.apiNewHeading(command);
					
					// Step : Execute curl command to get JSON response of the request
					jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
					
					// Step : Verifying record count is equal to 1 as using Id as a parameter
					if (jsonResponse.contains("{\"isSuccess\":true}")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
					{
						strActualResult="As expected getting success message.";
						isEventSuccessfulRecordCount = true;
					}
					else
					{
						strActualResult= "Not getting success message. <br> JSON Response =" +jsonResponse;
						isEventSuccessfulRecordCount=false;
					}
					reporter.ReportStep("Verifying getting success message in JSON reponse", "Should get success message ",strActualResult ,isEventSuccessfulRecordCount );
					
					
					//****************************Code to create expected Result Ends here *****************************************************************		
					
					//***********code for getting expected starts here***************************************
					expectedResultMap=getApplicationExpectedMap(appId, serverIP);
					expectedResultMap.put("id", appId);
					
					apiReporter.apiAddBlock("expectedResultMap =" +expectedResultMap);
					//***********code for getting expected ends here***************************************
				
					//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************		
					//Comparing both the maps and reporting				
					if (!expectedResultMap.get(propertyInUse).equals(valueOfProp))
					{
						isEventSuccessful=false;
						errorList.add(propertyInUse);
					}
					
					//POst condition: reverting display 
					//Create the curl command to get application details with Id
					component= "Application";
					parameterList="verb"+",id";
					valueList="update"+","+appId;
					dataTag="{"+propertyInUse+":\\\"" +oldValue+"\\\"}"; //declaration and intialization of curl command varaiables.
					command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json", dataTag);	//Creates a curl command with component, parameterList and valueList.
					
					// Step : Execute curl command to get JSON response of the request
					jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
					
									
					//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************
					
					if(isEventSuccessful)
					{
						strActualResult= propertyInUse+" properties updated successfully";
						apiReporter.apiPassBlock(propertyInUse+" properties updated successfully");
					}
					else
					{
						strActualResult= "Unable to update property from API" + errorList;
						apiReporter.apiErrorBlock("Unable to update property " + propertyInUse);
					}
					reporter.ReportStep("Compares Actual and Expected Map", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);
					
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
