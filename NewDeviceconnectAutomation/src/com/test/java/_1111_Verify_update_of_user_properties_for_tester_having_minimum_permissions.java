package com.test.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 14th Oct 2016
 * Last Modified Date: NA
 * Pre-requisite: TestData.xlsx should have tester user and token no updated, D
 * Jira Test Case Id: QA-1866
 * Coverage from Jira Case: Complete
 */
import com.common.utilities.GenericLibrary.Action;

public class _1111_Verify_update_of_user_properties_for_tester_having_minimum_permissions extends ScriptFuncLibrary {

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
	String errorMessage;
	String entitlementErrorExpected="User does not have required entitlement UserModify";

	// Creating an array having all the model properties available for User component

	String[] userModel ={"id","email","isActive","firstName","middleName","lastName","notes",
			"organization","title","location","address1","address2","city","region","postalCode",
			"country","homePhone","mobilePhone","officePhone","faxPhone","roles"};
	//Creating an array of all the query properties needed to be updated

	String[] updateProp ={"invalidEmail","existingEmail","email","isActive","firstName","middleName","lastName","notes",
			"organization","title","location","address1","address2","city","region","postalCode",
			"country","homePhone","mobilePhone","officePhone","faxPhone","password"}; 
	String oldValue,newValue;
	boolean isEventSuccessfulRecordCount ;
	ArrayList<String> errorList = new ArrayList<String>(); 
	/*
	//*********Code to get datetime************************
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	String currentDate=dateFormat.format(date);
	 */
	public final void testScript()	{
		try{

			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1111_Verify_update_of_user_properties_for_tester_having_minimum_permissions");

			String serverIP=dicCommon.get("ApplicationURL");
			String userId; //UserId of the user in DC
			String userName = "akd@gmail.com";//User to search 

			//****************************Code to create expected Result Starts here *****************************************************************
			// Step 1 : login to deviceConnect with valid user credentials.
			isEventSuccessful = Login();



			//*******Code to get the User details Page ********
			isEventSuccessful = GoToUsersPage();
			isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__","Active"), Action.SelectCheckbox);
			isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__","Inactive"), Action.SelectCheckbox);
			isEventSuccessful=searchUser(userName);//Search user with UserName
			if(!isEventSuccessful){
				isEventSuccessful = CreateUserWithCompleteDetails(userName);//If User is not Present Create a new user with all details
				if(isEventSuccessful){
					isEventSuccessful = GoToUsersPage();
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
			String[]entitlement={"User modify"};
			Boolean[]entitlementValue={false};
			setUserRoleSettings("Tester",entitlement , entitlementValue);

			//Step 2: Getting expected Result map from User details page
			expectedResultMap=userExpectedMap(userId, serverIP);


			//Running the script for all the query properties for which details need to be updated.


			for (String propertyInUse: updateProp)
			{
				
				isEventSuccessful=true;
				oldValue=expectedResultMap.get(propertyInUse);
				valueOfProp="API Updated "+propertyInUse+""+apiMethods.getDateTime();
				String email="APIUPDATEUSER"+apiMethods.getDateTime()+"@UPDATEUSER.COM";

				//When property in update is email changing it to username and putting a unique value to it.
				switch(propertyInUse)
				{
				case "email":
					propertyInUse="UserName";
					valueOfProp=email;
					System.out.println(propertyInUse);
					break;
				case "isActive":
					String isActiveValue=expectedResultMap.get("isActive");
					if(isActiveValue.equalsIgnoreCase("true")){
						valueOfProp="False";
					}
					else{
						valueOfProp="True";
					}
					break;
				case "invalidEmail":
					propertyInUse="UserName";
					valueOfProp="ababababa";
					System.out.println(propertyInUse);
					break;
				case "existingEmail":
					propertyInUse="UserName";
					valueOfProp=dicCommon.get("testerEmailAddress");
					System.out.println(propertyInUse);
					break;
				}
				apiReporter.apiHeading2(" User - Update user (Using "+propertyInUse+" with value = "+valueOfProp+")");

				//************************Code for getting actual result from API Request starts here *******************************************************************
				//adding heading in APIOverallComparison HTML Property and Value used in API Request


				//Create the curl command to update user details with id
				String component= "User", parameterList="verb"+",id", valueList="update"+","+userId, dataTag="{"+propertyInUse+":\\\"" +valueOfProp+"\\\"}"; //declaration and intialization of curl command varaiables.
				String jsonResponse; //declaration of string to capture JSON Response of the API request.
				String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json", dataTag,"","tester");	//Creates a curl command with component, parameterList and valueList.

				//adding heading in APIOverallComparison HTML for the curl command created
				apiReporter.apiNewHeading(command);
				jsonResponse=apiMethods.execCurlCmd(command);
				apiReporter.apiNewHeading("Response for Query from API: "+jsonResponse);

				
				errorMessage=apiMethods.getErrorMessage(command);
				apiReporter.apiNewHeading("entitlement error message shown for user:"+errorMessage);
				//***********code for getting expected starts here***************************************
				expectedResultMap=userExpectedMap(userId, serverIP);
				expectedResultMap.put("id", userId);

				apiReporter.apiAddBlock("expectedResultMap =" +expectedResultMap);
				//***********code for getting expected ends here***************************************



				// if property in use is username reverting it to email
				if(propertyInUse.equals("UserName")){
					propertyInUse="email";

				}
				email=expectedResultMap.get("email");
				expectedResultMap.put("invalidEmail", email);
				expectedResultMap.put("existingEmail",email);


				if (jsonResponse.contains("{\"isSuccess\":false")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
				{
					strActualResult="As expected not getting success message.";
					isEventSuccessful=true;
					apiReporter.apiPassBlock("Getting Error Message at api response");
					System.out.println("pass");
				}

				else
				{
					strActualResult= "Not getting Failure message message. Hence failing stop test. <br> JSON Response =" +jsonResponse;
					isEventSuccessful=false;
					apiReporter.apiErrorBlock("Not getting Failure message in api response");
				}
				reporter.ReportStep("Verifying Failure in JSON reponse", "Should get is success false message in JSON Response",strActualResult ,isEventSuccessful );
				if(errorMessage.equals(entitlementErrorExpected)){
					strActualResult="Verified as expected getting entitlement error message.";
					isEventSuccessful=true;
					apiReporter.apiPassBlock("Received entitlement error message as expected , Message shown was: "+errorMessage);
				}
				else{
					strActualResult="Verified not getting entitlement error message as expected: "+errorMessage;
					isEventSuccessful=false;
					apiReporter.apiErrorBlock("Not getting entitlement error message as expected , Message shown was: "+errorMessage);
				}
				reporter.ReportStep("Verify entitlement error message displayed", "expected entitlement error message: "+entitlementErrorExpected, strActualResult, isEventSuccessful);
				//***** Verify app is not deleted from 

				//****************************Code to create expected Result Ends here *****************************************************************		


				//Comparing both the maps and reporting		
				if(!propertyInUse.equals("password")){
					
				
				if (!expectedResultMap.get(propertyInUse).equals(valueOfProp))
				{
					isEventSuccessful=true;
					System.out.println("User details at UI not updated");
					apiReporter.apiPassBlock(propertyInUse+" details not updated in UI");
				}

				else
				{
					strActualResult= propertyInUse+" property updated successfully";
					apiReporter.apiErrorBlock(propertyInUse+" property updated successfully");
				}
				reporter.ReportStep("Compares Actual and Expected Map", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);

				}
				//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************

				//****************************Code to comparing expected Result with Actual Result & Reporting Starts here *****************************************************************		

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
	}
	//Adds a step Provides API Overall Comparison file link in DC Selenium Execution Report



}
