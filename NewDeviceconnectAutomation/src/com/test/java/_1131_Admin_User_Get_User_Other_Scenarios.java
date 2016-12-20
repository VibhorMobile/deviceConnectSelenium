package com.test.java;

import java.util.HashMap;
import java.util.Map;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 15th Nov 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have users added.
 * Jira Test Case Id: QA-1820
 * Coverage from Jira Case: Covers error message verification scenario's(Remaining from Script-1201)
 */

public class _1131_Admin_User_Get_User_Other_Scenarios extends ScriptFuncLibrary {
	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	private String parameterList,valueList,command,jsonResponse,component="user",queryP,responseValue,errorMessage;
	private String valueOfProp="",valueString="",errArr="";
	//Creating an array of the query properties available for Application component
	String[] queryProp ={"UnavailableId","InvalidId","UserName","isActive"};
	String[] dataValue={"33333933-3023-4343-3333-333333333333","33933-23-43.3-3.33-3.33333333333","sssssssss@koi.hai","invalid"};


	public void executeGetUserCurlCommand(String queryProperty,String propValue)
	{
		//creating curl command
		parameterList=queryProperty;
		valueList=propValue; //declaration and intialization of curl command varaiables.
		command=apiMethods.createCurlCommand(component, parameterList, valueList, "" ,"","","","tester");	//Creates a curl command with component, parameterList and valueList.

		//adding heading in APIOverallComparison HTML for the curl command created
		apiReporter.apiNewHeading(command);

		// Step : Execute curl command to get JSON response of the request
		jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

		apiReporter.apiNewHeading(jsonResponse);
	}

	public final void testScript()
	{
		try
		{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1131_Admin_User_Get_User_Other_Scenarios");
			for(int i=0;i<=3;i++){
				apiReporter.apiHeading2("Get User details with "+queryProp[i]+" with value:"+dataValue[i]);
				switch(queryProp[i]){
				case "UnavailableId":
					queryP="id";
					responseValue="[]";
					break;
				case "InvalidId":
					queryP="id";
					responseValue="Invalid Guid format "+dataValue[i];
					break;
				case "UserName":
					queryP="UserName";
					responseValue="[]";
					break;
				case "isActive":
					queryP="isActive";
					responseValue="Value is not equivalent to either TrueString or FalseString.";
					break;
				}
				executeGetUserCurlCommand(queryP,dataValue[i]);
				if(queryProp[i].equals("UnavailableId")||queryProp[i].equals("UserName")){
					if(jsonResponse.equals(responseValue)){
						strActualResult="Received expected response from query, returned value is:"+jsonResponse;
						isEventSuccessful=true;
						apiReporter.apiPassBlock(strActualResult);

					}
					else{
						strActualResult="Did not received expected response from query, returned value is:"+jsonResponse;
						isEventSuccessful=false;
						apiReporter.apiErrorBlock(strActualResult);
					}
					reporter.ReportStep("Verify getting empty result", "should get empty result", strActualResult, isEventSuccessful);
				}
				else{
					if(jsonResponse.contains("isSuccess\":false")){
						strActualResult="As expected getting Failure in Json Response";
						isEventSuccessful=true;
						apiReporter.apiPassBlock(strActualResult);
					}
					else{
						strActualResult="Not getting Failure in Json Response";
						isEventSuccessful=false;
						apiReporter.apiErrorBlock(strActualResult);
					}
					reporter.ReportStep("Verify is Success is false", "Should return false", strActualResult, isEventSuccessful);
					errorMessage=apiMethods.getErrorMessage(command);
					if(errorMessage.equals(responseValue)){
						strActualResult="As expected received error in response, error message is:"+errorMessage;
						isEventSuccessful=true;
						apiReporter.apiPassBlock(strActualResult);
					}
					else{
						strActualResult="Error received is not as, error message is:"+errorMessage;
						isEventSuccessful=false;
						apiReporter.apiErrorBlock(strActualResult);
					}
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
