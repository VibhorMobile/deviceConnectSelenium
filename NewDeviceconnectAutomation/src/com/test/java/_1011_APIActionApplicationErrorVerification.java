package com.test.java;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.ApplicationLibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

import net.sourceforge.htmlunit.corejs.javascript.ast.SwitchCase;

import com.common.utilities.GenericLibrary.Action;


public class _1011_APIActionApplicationErrorVerification extends ScriptFuncLibrary
{
	
	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	Boolean value;
	Object[] values = new Object[2];
	private String valueOfProp="";
	public Map<String,String> actualResultMap = new HashMap<String,String>();
	String expectedErrorMessage;
	//Creating an array of the query properties available for Application component
	
	String ActualErrorMessage,dt;
	
	public final void testScript()
	{
		try
		{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1011_APIActionApplicationErrorVerification");
			
			String serverIP=dicCommon.get("ApplicationURL");
			String appId; //=androidAppId;
			String fileName="";
			//String iOSAppPath= dicConfig.get("Artifacts")+"\\Dollar_General.ipa";
			//String androidAppPath= dicConfig.get("Artifacts")+"\\com.aldiko.android.apk";
			
			String[] appPaths= {dicConfig.get("Artifacts")+"\\ipa.txt"}; //Declaring an array to run for Android and iOS app
			String[] appFileName={"ipa.txt"};
			
			String component= "Application", parameterList="verb,wait", valueList="add,true", formTag="upload=@"+appPaths[0]; //declaration and intialization of curl command varaiables.
			String jsonResponse; //declaration of string to capture JSON Response of the API request.
			String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", formTag);	//Creates a curl command with component, parameterList and valueList.
			
			//adding heading in APIOverallComparison HTML for the curl command created
			apiReporter.apiNewHeading(command);
			
			// Step : Execute curl command to get JSON response of the request
			jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
			
			// Step : Creating actual HashMap from the JSON Response
			actualResultMap=apiMethods.getKeyValuePair(jsonResponse); //getting key value pair from the JSON response in a HashMap

			
			// Step : Verifying getting error message
			if (jsonResponse.indexOf("\"isSuccess\":false") > 0)
			 {
	          	System.out.println("API Request returning error");
	          	strActualResult="API Request returning error";
	          	isEventSuccessful=true;
	         }else
	         {
	        	 System.out.println("API Request not returning error");
	        	 strActualResult="API Request not returning error";
		         isEventSuccessful=false;
	         }
			reporter.ReportStep("Verifying error in JSON reponse", "Error Response should be generated",strActualResult ,isEventSuccessful );
			
			ActualErrorMessage=actualResultMap.get("message").trim();
			System.out.println("Actual Error Message-----------" + ActualErrorMessage);
			
			expectedErrorMessage="Unknown file type.\nParameter name: filename\n.txt";
			if (ActualErrorMessage.equals(expectedErrorMessage))
			{
				apiReporter.apiPassBlock("Error message is as expected. <br> Expected Error message: "+expectedErrorMessage+ "& Actual is :"+ActualErrorMessage);
			}else
			{
				apiReporter.apiErrorBlock("Error message is not as expected. <br> Expected Error message: "+expectedErrorMessage+ "& Actual is :"+ActualErrorMessage);
			}
			
			
			//Deleting the application
			
			component= "Application";
			parameterList="verb,all,id";
			valueList="delete,false,e6370197-2b9a-4cab-b161-8deb82b11111";
			command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "");	//Creates a curl command with component, parameterList and valueList.
			
			//adding heading in APIOverallComparison HTML for the curl command created
			apiReporter.apiNewHeading(command);
			
			// Step : Execute curl command to get JSON response of the request
			jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
			apiReporter.apiAddBlock(jsonResponse);
			
			// Step : Creating actual HashMap from the JSON Response
			actualResultMap=apiMethods.getKeyValuePair(jsonResponse); //getting key value pair from the JSON response in a HashMap

			
			// Step : Verifying getting error message
			if (jsonResponse.indexOf("\"isSuccess\":false") > 0)
			 {
	          	System.out.println("API Request returning error");
	          	strActualResult="API Request returning error";
	          	isEventSuccessful=true;
	         }else
	         {
	        	 System.out.println("API Request not returning error");
	        	 strActualResult="API Request not returning error";
		         isEventSuccessful=false;
	         }
			reporter.ReportStep("Verifying error in JSON reponse", "Error Response should be generated",strActualResult ,isEventSuccessful );
			
			ActualErrorMessage=actualResultMap.get("message").trim();
			System.out.println("Actual Error Message-----------" + ActualErrorMessage);

			expectedErrorMessage="Application not found.\nParameter name: application";
			if (ActualErrorMessage.equals(expectedErrorMessage))
			{
				apiReporter.apiPassBlock("Error message is as expected. <br> Expected Error message: "+expectedErrorMessage+ "& Actual is :"+ActualErrorMessage);
			}else
			{
				apiReporter.apiErrorBlock("Error message is not as expected. <br> Expected Error message: "+expectedErrorMessage+ "& Actual is :"+ActualErrorMessage);
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