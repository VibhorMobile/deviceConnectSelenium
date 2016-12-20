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

public class _1003_APIAddDeleteApplicationTest extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	String appId;
	Boolean value;
	Object[] values = new Object[2];
	public Map<String,String> AppAddedMap = new HashMap<String,String>();
	public Map<String,String> AppDeletedMap = new HashMap<String,String>();
	boolean isEventSuccessfulRecordCount;
	// Creating an array having all the model properties available for Application component
	
	
	public final void testScript()
	{
		try
		{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1003_APIAddDeleteApplicationTest");
			isEventSuccessful = Login();
			
			String serverIP=dicCommon.get("ApplicationURL");
			String appId; //=androidAppId;
			String fileName="";
			//String iOSAppPath= dicConfig.get("Artifacts")+"\\Dollar_General.ipa";
			//String androidAppPath= dicConfig.get("Artifacts")+"\\com.aldiko.android.apk";
	
			//****************************Code to create expected Result Starts here *****************************************************************
			// Step 1 : login to deviceConnect with valid user credentials.
			isEventSuccessful = Login();
			
			
			String[] appPaths= {dicConfig.get("Artifacts")+"\\Dollar_General.ipa",dicConfig.get("Artifacts")+"\\com.aldiko.android.apk"}; //Declaring an array to run for Android and iOS app
			String[] appFileName={"Dollar_General.ipa","com.aldiko.android.apk"};
			
			for(int j=0; j<=1; j++)
			{
				String component= "Application", parameterList="verb,wait", valueList="add,true", formTag="upload=@"+appPaths[j]; //declaration and intialization of curl command varaiables.
				String jsonResponse; //declaration of string to capture JSON Response of the API request.
				String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", formTag);	//Creates a curl command with component, parameterList and valueList.
				
				//adding heading in APIOverallComparison HTML for the curl command created
				apiReporter.apiNewHeading(command);
				
				// Step : Execute curl command to get JSON response of the request
				jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
				
				// Step : Verifying record count is equal to 1 as using Id as a parameter
				  if (jsonResponse.indexOf("\"isSuccess\":true") > 0) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
				{
					strActualResult="As expected getting success message.";
					isEventSuccessful=true;
				}
				else
				{
					strActualResult= "Not getting success message. Hence failing stop test. <br> JSON Response =" +jsonResponse;
					isEventSuccessful=false;
				}
				reporter.ReportStep("Verifying success in JSON reponse", "Should get success message in JSON Response",strActualResult ,isEventSuccessful );
				
				// Step : Creating expected HashMap from the JSON Response
				AppAddedMap=apiMethods.getKeyValuePair(jsonResponse); //getting key value pair from the JSON response in a HashMap
				
				appId=AppAddedMap.get("id");
				
				apiReporter.apiAddBlock(AppAddedMap.toString());
				
				//****************************Code to create expected Result Starts here ****************************************************************
				
				isEventSuccessful=PerformAction("browser","navigate","http://"+serverIP+"/#/Application/Detail/" + appId);
				if(isEventSuccessful)
				{
					strActualResult= "Navigate to Application Details page of the app using GUID. ID= " + appId +" server used= " +serverIP;
				}
				else
				{
					strActualResult= "PerformAction---Navigate to URL. ID= " + appId +" server used= " +serverIP+ strErrMsg_GenLib;
				}
				reporter.ReportStep("Navigates to App details page", "Navigates successfully", strActualResult, isEventSuccessful);
				
				fileName=GetTextOrValue(dicOR.get("eleFileName_AppDetailsPage"), "text");
				
				if (fileName.equals(appFileName[j]))
				{
					apiReporter.apiPassBlock("Application uploaded successfully");
					reporter.ReportStep("Add Application through API", "Application uploaded successfully", "Application uploaded successfully "+appFileName[j] +" FilePath= "+appPaths[j], true);
				}
				else
				{
					apiReporter.apiErrorBlock("Unable to upload app "+appFileName[j] +" FilePath= "+appPaths[j]);
					reporter.ReportStep("Add Application through API", "Application uploaded successfully", "Unable to upload app "+appFileName[j] +" FilePath= "+appPaths[j], false);
				}
				
				//Deleting the application
				
				component= "Application";
				parameterList="verb,all,id";
				valueList="delete,false,"+appId;
				command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "");	//Creates a curl command with component, parameterList and valueList.
				
				//adding heading in APIOverallComparison HTML for the curl command created
				apiReporter.apiNewHeading(command);
				
				// Step : Execute curl command to get JSON response of the request
				jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
				apiReporter.apiAddBlock(jsonResponse);
				
				// Step : Verifying record count is equal to 1 as using Id as a parameter
				  if (jsonResponse.indexOf("\"isSuccess\":true") > 0) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
				{
					strActualResult="As expected getting success message.";
					isEventSuccessful=true;
				}
				else
				{
					strActualResult= "Not getting success message. Hence failing stop test. <br> JSON Response =" +jsonResponse;
					isEventSuccessful=false;
				}
				reporter.ReportStep("Verifying success in JSON reponse", "Should get success message in JSON Response",strActualResult ,isEventSuccessful );
				
				component= "Application";
				parameterList="id";
				valueList=appId;
				command=apiMethods.createCurlCommand(component, parameterList, valueList);	//Creates a curl command with component, parameterList and valueList.
				
				//adding heading in APIOverallComparison HTML for the curl command created
				apiReporter.apiNewHeading(command);
				
				// Step : Execute curl command to get JSON response of the request
				jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
				apiReporter.apiAddBlock(jsonResponse);
				
				// Step : Creating expected HashMap from the JSON Response
				AppDeletedMap=apiMethods.getKeyValuePair(jsonResponse); //getting key value pair from the JSON response in a HashMap
							
				if (AppDeletedMap.isEmpty())
				{
					apiReporter.apiPassBlock("Application deleted successfully");
					reporter.ReportStep("Delete Application through API", "Application deleted successfully", "Application deleted successfully "+appId, true);
				}
				else
				{
					apiReporter.apiErrorBlock("Unable to delete app "+appId);
					reporter.ReportStep("Delete Application through API", "Application deleted successfully", "Unable to delete app "+appId, false);
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
