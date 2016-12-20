package com.test.java;




import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Math.*;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.sun.jna.platform.win32.COM.Dispatch.ByReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.openqa.jetty.jetty.servlet.Default;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 06 May 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have atleast 1 User
 * Admin user can only be used
 */

public class _1201_APIGetUserTest  extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	

	//Boolean value;
	//Object[] values = new Object[2];
	public Map<String,String> expectedResultMap= new HashMap<String,String>();
	public Map<String,String> actualResultMap = new HashMap<String,String>(); // Declaring a HashMap to store actual results key and values
	public Map<String,String> oldActualResultMap = new HashMap<String,String>();
	private String valueOfProp="";
	String roles,role;

	// Creating an array having all the model properties available for User component

	String[] userModel ={"id","roles","email","isActive","firstName","middleName","lastName","notes",
			"organization","title","location","address1","address2","city","region","postalCode",
			"country","homePhone","mobilePhone","officePhone","faxPhone"
	};
	//Creating an array of all the query properties available for User component
	String[] queryProp ={"id","isActive","email","firstName","middleName","lastName","notes",
			"organization","title","location","address1","address2","city","region","postalCode",
			"country","homePhone","mobilePhone","officePhone","faxPhone"}; //,"roles","isActive"


	public final void testScript()
	{
		try{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("Get Users");

			String serverIP=dicCommon.get("ApplicationURL");
			String userId; //=userId;
			String userName="atp@gmail.com";

			//****************************Code to create expected Result Starts here *****************************************************************
			// Step 1 : login to deviceConnect with valid user credentials.
			isEventSuccessful = Login();



			isEventSuccessful = GoToUsersPage();
			isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__'","Active"), Action.SelectCheckbox);
			isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__'","Inactive"), Action.SelectCheckbox);
			isEventSuccessful=searchUser(userName);//Search user with UserName
			if(!isEventSuccessful){
				isEventSuccessful = CreateUserWithCompleteDetails(userName);//If User is not Present Create a new user with all details
				if(isEventSuccessful){
					isEventSuccessful = GoToUsersPage();
					WebDriverWait wait = new WebDriverWait(driver, 20);
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='userListTable table data-grid user-list-table']/tbody/tr[1]/td[1]"))); 
					if(isEventSuccessful){
						isEventSuccessful=searchUser(userName);
					}
					else{
						throw new RuntimeException("user page did not open");
					}

				}
				else{
					throw new RuntimeException("Was not able to create user");
				}


			}
			GoToFirstUserDetailsPage(); //Clicks on the first User listed in the page after search
			userId=GetUserID(); //Gets the Id of the User whose  details page is displayed

			expectedResultMap=getUserExpectedMap(userId, serverIP);


			for (String propertyInUse: queryProp)
			{
				if ((expectedResultMap.get(propertyInUse).equals("Not Verified")) & (oldActualResultMap.containsKey(propertyInUse))) //If the property is "Not Verified". Get the value from oldActualResultMap
					valueOfProp=oldActualResultMap.get(propertyInUse);
				else
					valueOfProp=expectedResultMap.get(propertyInUse); //If value exists other than "Not Verified",use the value in API Request

				//************************Code for getting actual result from API Request starts here *******************************************************************
				//adding heading in APIOverallComparison HTML  for the AppType, Property and Value used in API Request
				if(valueOfProp.equals("null")){  //If Value of property to get is null again getting after filling the details in UI
					expectedResultMap=getUserExpectedMap(userId, serverIP);
					if ((expectedResultMap.get(propertyInUse).equals("Not Verified")) & (oldActualResultMap.containsKey(propertyInUse))) //If the property is "Not Verified". Get the value from oldActualResultMap
						valueOfProp=oldActualResultMap.get(propertyInUse);
					else
						valueOfProp=expectedResultMap.get(propertyInUse);
				}
				apiReporter.apiNewHeading(" User - Get User (Using "+propertyInUse+" with value = "+valueOfProp+")");
				if(propertyInUse.equals("email")){
					propertyInUse="UserName";
					System.out.println(propertyInUse);

				}
				System.out.println("valueof prop:"+propertyInUse);

				//Create the curl command to get application details with Id
				String component= "User", parameterList=propertyInUse.trim(), valueList=valueOfProp.trim(); //declaration and intialization of curl command varaiables.
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
				System.out.println("actual Map is:"+actualResultMap);


				//***********code for getting expected starts here***************************************
				userId=actualResultMap.get("id");
				expectedResultMap=getUserExpectedMap(userId, serverIP);
				expectedResultMap.put("id", userId);

				//***********code for getting expected ends here***************************************

				//***** Sorting Roles received from API query*******
				roles=actualResultMap.get("roles");
				System.out.println(roles);
				roles=roles.replace("[", "").replace("]", "").replace("\"", "");
				System.out.println(roles);
				String[]userRole=roles.split(",");
				Arrays.sort(userRole,String.CASE_INSENSITIVE_ORDER);
				role=Arrays.toString(userRole).replace(" ", "");
				System.out.println(role);

				actualResultMap.put("roles", role);

				//**********Sorted and added roles to script.**************
				//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************		
				//Comparing both the maps and reporting
				strActualResult="";
				Map<String,String> errorMap=new HashMap<String,String>();
				isEventSuccessful=apiMethods.compareActualAndExpectedMap(userModel, expectedResultMap,actualResultMap,errorMap);
				System.out.println(errorMap);
				if(isEventSuccessful)
				{
					System.out.println("pass");
					strActualResult= "All the properties verified are matching";
					apiReporter.apiPassBlock("Successfully Verified API get user with property: "+propertyInUse);
				}
				else
				{
					System.out.println("Fail");
					strActualResult= "compareActualAndExpectedMap---" + errorMap + com.common.utilities.APILibrary.strErrMsg_ApiLib;
					apiReporter.apiErrorBlock("There is some Mismatch when getting User details through API using:"+propertyInUse);
				}
				reporter.ReportStep("Compares Actual and Expected Map", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);


				//Step 10: Reporting app model with expected and actual values in the HTML report

				apiReporter.CreateAppModelTableInReport(userModel,expectedResultMap,actualResultMap,errorMap);
				//Adds a step Provides API Overall Comparison file link in DC Selenium Execution Report
				apiReporter.apiOverallHtmlLinkStep();


				oldActualResultMap=actualResultMap; //Storing actualResult map for next iteration
			}
		}
		catch(Exception ex)
		{
			apiReporter.apiErrorBlock("Something went wrong... Error Message "+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			//Adds a step Provides API Overall Comparison file link in DC Selenium Execution Report
			apiReporter.apiOverallHtmlLinkStep();

		}

		//System.out.println("actual result map is"+actualResultMap);
	}




}



