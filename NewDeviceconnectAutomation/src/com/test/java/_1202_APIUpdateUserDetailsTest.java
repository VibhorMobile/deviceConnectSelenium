package com.test.java;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
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
import java.util.Date;
import org.openqa.jetty.jetty.servlet.Default;


/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 3 May 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have atleast 1 User
 * Admin user can only be used
 * Roles can not be updated
 */

public class _1202_APIUpdateUserDetailsTest  extends ScriptFuncLibrary {

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

	// Creating an array having all the model properties available for User component

	String[] userModel ={"id","email","isActive","firstName","middleName","lastName","notes",
			"organization","title","location","address1","address2","city","region","postalCode",
			"country","homePhone","mobilePhone","officePhone","faxPhone","roles"};
	//Creating an array of all the query properties needed to be updated

	String[] updateProp ={"email","isActive","firstName","middleName","lastName","notes",
			"organization","title","location","address1","address2","city","region","postalCode",
			"country","homePhone","mobilePhone","officePhone","faxPhone"}; 
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
		apiReporter.apiScriptHeading("_1202_APIUpdateUserDetailsTest");

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
			if(propertyInUse.equals("email")){
				propertyInUse="UserName";
				valueOfProp=email;
				System.out.println(propertyInUse);

			}
			//when property in use is  isActive changing its value from true to false
			if(propertyInUse.equals("isActive")){
				String isActiveValue=expectedResultMap.get("isActive");
				if(isActiveValue.equalsIgnoreCase("true")){
					valueOfProp="False";
				}
				else{
					valueOfProp="True";
				}

			}
			

			//************************Code for getting actual result from API Request starts here *******************************************************************
			//adding heading in APIOverallComparison HTML Property and Value used in API Request
			apiReporter.apiNewHeading(" User - Update user (Using "+propertyInUse+" with value = "+valueOfProp+")");

			//Create the curl command to update user details with id
			String component= "User", parameterList="verb"+",id", valueList="update"+","+userId, dataTag="{"+propertyInUse+":\\\"" +valueOfProp+"\\\"}"; //declaration and intialization of curl command varaiables.
			String jsonResponse; //declaration of string to capture JSON Response of the API request.
			String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json", dataTag);	//Creates a curl command with component, parameterList and valueList.

			//adding heading in APIOverallComparison HTML for the curl command created
			apiReporter.apiNewHeading(command);

			// Step : Execute curl command to get JSON response of the request
			jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

			// Step : Verifying getting success in response
			if (jsonResponse.equals("{\"isSuccess\":true}")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
			{
				strActualResult="As expected getting success message.";
				isEventSuccessfulRecordCount = true;
				System.out.println("getting success");
			}
			else
			{
				strActualResult= "Not getting success message. <br> JSON Response =" +jsonResponse;
				isEventSuccessfulRecordCount=false;
				System.out.println("getting no success");
			}
			reporter.ReportStep("Verifying getting success message in JSON reponse", "Should get success message ",strActualResult ,isEventSuccessfulRecordCount );


			//****************************Code to create expected Result Ends here *****************************************************************		

			//***********code for getting expected starts here***************************************
			expectedResultMap=userExpectedMap(userId, serverIP);
			expectedResultMap.put("id", userId);

			apiReporter.apiAddBlock("expectedResultMap =" +expectedResultMap);
			//***********code for getting expected ends here***************************************

			

			// if property in use is username reverting it to email
			if(propertyInUse.equals("UserName")){
				propertyInUse="email";

			}

			//Comparing both the maps and reporting		
			if (!expectedResultMap.get(propertyInUse).equals(valueOfProp))
			{
				isEventSuccessful=false;
				System.out.println("value not matching");
				errorList.add(propertyInUse);
				strActualResult= "Unable to update property from API" + errorList;
				apiReporter.apiErrorBlock("Unable to update property " + propertyInUse);
			}

			else{
			
				strActualResult= propertyInUse+" property updated successfully";
				apiReporter.apiPassBlock(propertyInUse+" property updated successfully");
			}

			reporter.ReportStep("Compares Actual and Expected Map", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);
			//POst condition: reverting display 
			//Again if property is email changing it to userName
			if(propertyInUse.equals("email")){
				propertyInUse="userName";

			}
			if(oldValue.equals("null")){
				oldValue="";
			}

			component= "User";
			parameterList="verb"+",id";
			valueList="update"+","+userId;
			dataTag="{"+propertyInUse+":\\\"" +oldValue+"\\\"}"; //declaration and intialization of curl command varaiables.
			command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json", dataTag);	//Creates a curl command with component, parameterList and valueList.

			// Step : Execute curl command to get JSON response of the request
			jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.


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








