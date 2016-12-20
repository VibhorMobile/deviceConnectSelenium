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
 * Creation Date: 16th Nov 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have users added.
 * Jira Test Case Id: QA-1827
 * Coverage from Jira Case: Covers error message verification scenario's(Remaining from Script-1201)
 */

public class _1132_Admin_user_verify_update_user_properties_other_scenarios extends ScriptFuncLibrary {
	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	private String parameterList,valueList,command,jsonResponse,component="user",queryP,responseValue,errorMessage,dataTag,userId,serverIP=dicCommon.get("ApplicationURL");
	private String valueOfProp="",valueString="",errArr="";
	public Map<String,String> expectedResultMap= new HashMap<String,String>();
	//Creating an array of the query properties available for Application component
	String[] queryProp ={"invalidUserName","existingUserName","notesWithSpaces","multiLineNotes","password"};
	String[] dataValue={"333339332323",dicCommon.get("EmailAddress"),"aPI Testing Notes With Space","aPI Testing Notes With Space\\nLine 1\\nLine2\\nLine 3 text\\nLine 4 text\\n Last Line","deviceconnectpassword"};

	public final void testScript()
	{
		try
		{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1132_Admin_user_verify_update_user_properties_other_scenarios");
			isEventSuccessful=Login();
			if(isEventSuccessful){
				isEventSuccessful=GoToUsersPage();
				if(isEventSuccessful){
					GoToFirstUserDetailsPage();
					userId=GetUserID();
				}
				else{
					apiReporter.apiErrorBlock("unable to navigate to users page");
				}
			}
			else{
				apiReporter.apiErrorBlock("Unable to login to DC");
			}
			for(int i=0;i<=4;i++){
				apiReporter.apiHeading2("update User details with "+queryProp[i]+" with value:"+dataValue[i]);
				switch(queryProp[i]){
				case "invalidUserName":
					queryP="UserName";
					responseValue="Invalid username, an email is expected.\\nParameter name UserName";
					break;
				case "existingUserName":
					queryP="UserName";
					responseValue="ERROR 23505";
					break;
				case "password":
					queryP="password";
					break;
				default:
					queryP="notes";
					break;
				}

				//executeGetUserCurlCommand(queryP,dataValue[i],userId);
				parameterList="verb"+",id"; valueList="update"+","+userId; dataTag="{"+queryP+":\\\"" +dataValue[i]+"\\\"}"; //declaration and intialization of curl command varaiables.
				String jsonResponse; //declaration of string to capture JSON Response of the API request.
				String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json", dataTag);	//Creates a curl command with component, parameterList and valueList.

				//adding heading in APIOverallComparison HTML for the curl command created
				apiReporter.apiNewHeading(command);

				// Step : Execute curl command to get JSON response of the request
				jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

				apiReporter.apiNewHeading(jsonResponse);
				if(queryProp[i].equals("invalidUserName")||queryProp[i].equals("existingUserName")){
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
				else{
					if(jsonResponse.contains("isSuccess\":true")){
						strActualResult="As expected getting success in Json Response";
						isEventSuccessful=true;
						apiReporter.apiPassBlock(strActualResult);
					}
					else{
						strActualResult="Not getting success in Json Response";
						isEventSuccessful=false;
						apiReporter.apiErrorBlock(strActualResult);
					}
				}
				expectedResultMap=userExpectedMap(userId, serverIP);
				//apiReporter.apiNewHeading("User Details from UI"+expectedResultMap);
				if(queryP.equals("UserName")){
					queryP="email";
				}
				if(!queryP.equals("password")){
					if(expectedResultMap.get(queryP).equals(dataValue[i])){
						strActualResult="Value of Prop Updated in UI";
						isEventSuccessful=true;		
					}
					else{
						strActualResult="Value of Prop isn't updated in UI";
						isEventSuccessful=false;
					}
					reporter.ReportStep("Verify property updated in UI", "value of prop should updated in case of notes and password", strActualResult, isEventSuccessful);
					if(queryP.equals("notes")){
						if(isEventSuccessful){
							apiReporter.apiPassBlock(strActualResult);
						}
						else{
							apiReporter.apiErrorBlock(strActualResult);
						}
					}
					else{
						if(isEventSuccessful){
							apiReporter.apiErrorBlock(strActualResult);
						}
						else{
							apiReporter.apiPassBlock(strActualResult);
						}
					}
				}
				else{
					Logout();
					isEventSuccessful=Login(expectedResultMap.get("email"),dataValue[i]);
					if(isEventSuccessful){
						strActualResult="Able to Loginin app with updated password";
						apiReporter.apiPassBlock(strActualResult);
					}
					else{
						strActualResult="Not able to Login to app with updated password";
						apiReporter.apiErrorBlock(strActualResult);
					}
					reporter.ReportStep("Verify if user is able to login with updated password", "User should be able to login", strActualResult, isEventSuccessful);
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
