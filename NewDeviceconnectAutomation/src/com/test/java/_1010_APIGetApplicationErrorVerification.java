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


public class _1010_APIGetApplicationErrorVerification extends ScriptFuncLibrary
{
	
	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	Boolean value;
	Object[] values = new Object[2];
	private String valueOfProp="",valueString="",errArr="";
	public Map<String,String> actualResultMap = new HashMap<String,String>();
	//Creating an array of the query properties available for Application component
	String[] queryProp ={"id","operatingSystem"};
	String[] dataTypes={"guid","enum"};
	String ActualErrorMessage,dt;
	
	public final void testScript()
	{
		try
		{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1010_APIGetApplicationErrorVerification");
				int rowcounter=0;
				for (String propertyInUse: queryProp)
				{
					dt=dataTypes[rowcounter];
					switch(dt)
					{
					case "string":
						break;
					case "guid":
						valueString="2d6c6651-4a6b-4634-828e-f225b73211";
						errArr="Invalid Guid format: 2d6c6651-4a6b-4634-828e-f225b73211";
						break;
					case "boolean":
						break;
					case "enum":
						valueString="invalid";
						errArr="The requested value 'invalid' was not found.";
						break;
					}
					
					String propValues[]=  valueString.split(",");
					String errArrValues[] = errArr.split(",");
					int innerrowcounter=0;
					for (String val : propValues)
					{
						valueOfProp=val;
						
						//************************Code for getting actual result from API Request starts here *******************************************************************
						//adding heading in APIOverallComparison HTML  for the AppType, Property and Value used in API Request
						apiReporter.apiNewHeading(" Application - Get App (Using "+propertyInUse+" with invalid value = "+valueOfProp+")");
						
						//Create the curl command to get application details with Id
						String component= "Application", parameterList=propertyInUse.trim(), valueList=valueOfProp.trim(); //declaration and intialization of curl command varaiables.
						String jsonResponse; //declaration of string to capture JSON Response of the API request.
						String command=apiMethods.createCurlCommand(component, parameterList, valueList);	//Creates a curl command with component, parameterList and valueList.
						
						//adding heading in APIOverallComparison HTML
						apiReporter.apiNewHeading(command);
						
						// Step : Execute curl command to get JSON response of the request
						jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
						
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
						
						// Step : Creating actual HashMap from the JSON Response
						actualResultMap=apiMethods.getKeyValuePair(jsonResponse); //getting key value pair from the JSON response in a HashMap
			
						
						ActualErrorMessage=actualResultMap.get("message").trim();
						System.out.println("Actual Error Message-----------" + ActualErrorMessage);
						
						if ((errArrValues[innerrowcounter].trim()).equals(ActualErrorMessage))
						{
							apiReporter.apiPassBlock("Error message is as expected. <br> Expected Error message: "+errArrValues[innerrowcounter]+ "& Actual is :"+ActualErrorMessage);
						}else
						{
							apiReporter.apiErrorBlock("Error message is not as expected. <br> Expected Error message: "+errArrValues[innerrowcounter]+ "& Actual is :"+ActualErrorMessage);
						}
						
						innerrowcounter++;
					}
					rowcounter++;
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