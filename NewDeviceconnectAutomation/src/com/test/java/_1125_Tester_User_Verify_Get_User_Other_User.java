package com.test.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 4th Nov 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have atleast one device for all status
 * Jira Test Case Id: QA-1876
 * Coverage from Jira Case: Scenario's for other user 
 */

public class _1125_Tester_User_Verify_Get_User_Other_User extends ScriptFuncLibrary {


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
			"country","homePhone","mobilePhone","officePhone","faxPhone"};
	//Creating an array of all the query properties available for User component
	String[] queryProp ={"id","isActive","email","firstName","middleName","lastName","notes",
			"organization","title","location","address1","address2","city","region","postalCode",
			"country","homePhone","mobilePhone","officePhone","faxPhone"}; //,"roles","isActive"


	public boolean fillCompleteUserDetails(){
		isEventSuccessful=false;
		String firstName=GetTextOrValue(dicOR.get("eleFirstName_UserDetailsPage"), "value");
		if(firstName.isEmpty()){
			PerformAction(dicOR.get("eleFirstName_UserDetailsPage"), Action.Type,"AdminfirstName");
			firstName=GetTextOrValue(dicOR.get("eleFirstName_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("firstName", firstName);

		String middleName=GetTextOrValue(dicOR.get("eleMiddleName_UserDetailsPage"), "value");
		if(middleName.isEmpty()){
			PerformAction(dicOR.get("eleMiddleName_UserDetailsPage"), Action.Type,"AdminmiddleName");
			middleName=GetTextOrValue(dicOR.get("eleMiddleName_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("middleName", middleName);

		String lastName=GetTextOrValue(dicOR.get("eleLastName_UserDetailsPage"), "value");
		if(lastName.isEmpty()){
			PerformAction(dicOR.get("eleLastName_UserDetailsPage"), Action.Type,"AdminlastName");
			lastName=GetTextOrValue(dicOR.get("eleLastName_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("lastName", lastName);
		String notes=GetTextOrValue(dicOR.get("eleNotes_UserDetailsPage"), "value");
		if(notes.isEmpty()){
			PerformAction(dicOR.get("eleNotes_UserDetailsPage"), Action.Type,"Adminnotes");
			notes=GetTextOrValue(dicOR.get("eleNotes_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("notes", notes);

		String organization=GetTextOrValue(dicOR.get("eleOrganization_UserDetailsPage"), "value");
		if(organization.isEmpty()){
			PerformAction(dicOR.get("eleOrganization_UserDetailsPage"), Action.Type,"Adminorganization");
			organization=GetTextOrValue(dicOR.get("eleOrganization_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("organization", organization);


		String title=GetTextOrValue(dicOR.get("eleTitle_UserDetailsPage"), "value");
		if(title.isEmpty()){
			PerformAction(dicOR.get("eleTitle_UserDetailsPage"), Action.Type,"Admintitle");
			title=GetTextOrValue(dicOR.get("eleTitle_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("title", title);

		String location=GetTextOrValue(dicOR.get("eleLocation_UserDetailsPage"), "value");
		if(location.isEmpty()){
			PerformAction(dicOR.get("eleLocation_UserDetailsPage"), Action.Type,"Adminlocation");
			location=GetTextOrValue(dicOR.get("eleLocation_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("location", location);

		String address1=GetTextOrValue(dicOR.get("eleAddress1_UserDetailsPage"), "value");
		if(address1.isEmpty()){
			PerformAction(dicOR.get("eleAddress1_UserDetailsPage"), Action.Type,"Adminaddress1");
			address1=GetTextOrValue(dicOR.get("eleAddress1_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("address1", address1);

		String address2=GetTextOrValue(dicOR.get("eleAddress2_UserDetailsPage"), "value");
		if(address2.isEmpty()){
			PerformAction(dicOR.get("eleAddress2_UserDetailsPage"), Action.Type,"Adminaddress2");
			address2=GetTextOrValue(dicOR.get("eleAddress2_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("address2", address2);

		String city=GetTextOrValue(dicOR.get("eleCity_UserDetailsPage"), "value");
		if(city.isEmpty()){
			PerformAction(dicOR.get("eleCity_UserDetailsPage"), Action.Type,"Admincity");
			city=GetTextOrValue(dicOR.get("eleCity_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("city", city);

		String region=GetTextOrValue(dicOR.get("eleRegion_UserDetailsPage"), "value");
		if(region.isEmpty()){
			PerformAction(dicOR.get("eleRegion_UserDetailsPage"), Action.Type,"Adminregion");
			region=GetTextOrValue(dicOR.get("eleRegion_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("region", region);

		String postalCode=GetTextOrValue(dicOR.get("elePostalCode_UserDetailsPage"), "value");
		if(postalCode.isEmpty()){
			PerformAction(dicOR.get("elePostalCode_UserDetailsPage"), Action.Type,"AdminpostalCode");
			postalCode=GetTextOrValue(dicOR.get("elePostalCode_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("postalCode", postalCode);

		String country=GetTextOrValue(dicOR.get("eleCountry_UserDetailsPage"), "value");
		if(country.isEmpty()){
			PerformAction(dicOR.get("eleCountry_UserDetailsPage"), Action.Type,"Admincountry");
			country=GetTextOrValue(dicOR.get("eleCountry_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("country", country);

		String homePhone=GetTextOrValue(dicOR.get("eleHomePhone_UserDetailsPage"), "value");
		if(homePhone.isEmpty()){
			PerformAction(dicOR.get("eleHomePhone_UserDetailsPage"), Action.Type,"AdminhomePhone");
			homePhone=GetTextOrValue(dicOR.get("eleHomePhone_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("homePhone", homePhone);

		String mobilePhone=GetTextOrValue(dicOR.get("eleMobilePhone_UserDetailsPage"), "value");
		if(mobilePhone.isEmpty()){
			PerformAction(dicOR.get("eleMobilePhone_UserDetailsPage"), Action.Type,"AdminmobilePhone");
			mobilePhone=GetTextOrValue(dicOR.get("eleMobilePhone_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("mobilePhone", mobilePhone);

		String officePhone=GetTextOrValue(dicOR.get("eleOfficePhone_UserDetailsPage"), "value");
		if(officePhone.isEmpty()){
			PerformAction(dicOR.get("eleOfficePhone_UserDetailsPage"), Action.Type,"AdminofficePhone");
			officePhone=GetTextOrValue(dicOR.get("eleOfficePhone_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("officePhone", officePhone);
		// expectedResultMap.put("officePhone", officePhone);

		String faxPhone=GetTextOrValue(dicOR.get("eleFaxPhone_UserDetailsPage"), "value");
		if(faxPhone.isEmpty()){
			PerformAction(dicOR.get("eleFaxPhone_UserDetailsPage"), Action.Type,"AdminfaxPhone");
			faxPhone=GetTextOrValue(dicOR.get("eleFaxPhone_UserDetailsPage"), "value");
		}
		//expectedResultMap.put("faxPhone", faxPhone);
		PerformAction("btnSave", Action.Click);

		return isEventSuccessful;
	}

	public final void testScript()
	{
		try{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1125_Tester_User_Verify_Get_User_Other_User");

			String serverIP=dicCommon.get("ApplicationURL");
			String userId; //=userId;
			String userName=dicCommon.get("EmailAddress");

			//****************************Code to create expected Result Starts here *****************************************************************
			// Step 1 : login to deviceConnect with valid user credentials.
			isEventSuccessful = Login();
			if(isEventSuccessful){
				isEventSuccessful=GoToUsersPage();
				if(isEventSuccessful){
					String[]entitlement={"User list"};
					Boolean[]entitlementValue={false};
					setUserRoleSettings("Tester",entitlement , entitlementValue);
				}
				else{
					apiReporter.apiErrorBlock("Unable to navigate to users page");
				}
			}
			else{
				apiReporter.apiErrorBlock("Unable to login to dc");
			}



			isEventSuccessful = GoToUsersPage();
			isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__'","Active"), Action.SelectCheckbox);
			isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__'","Inactive"), Action.SelectCheckbox);
			isEventSuccessful=searchUser(userName);//Search user with UserName
			GoToFirstUserDetailsPage(); //Clicks on the first User listed in the page after search
			userId=GetUserID(); //Gets the Id of the User whose  details page is displayed
			fillCompleteUserDetails();
			expectedResultMap=getUserExpectedMap(userId, serverIP);
			System.out.println("Expected Result Map: "+expectedResultMap);
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
				apiReporter.apiHeading2(" User - Get User (Using "+propertyInUse+" with value = "+valueOfProp+")");
				if(propertyInUse.equals("email")){
					propertyInUse="UserName";
					System.out.println(propertyInUse);

				}
				System.out.println("valueof prop:"+propertyInUse);

				//Create the curl command to get application details with Id
				String component= "User", parameterList=propertyInUse.trim(), valueList=valueOfProp.trim(); //declaration and intialization of curl command varaiables.
				String jsonResponse; //declaration of string to capture JSON Response of the API request.
				String command=apiMethods.createCurlCommand(component, parameterList, valueList,"","","","","tester");	//Creates a curl command with component, parameterList and valueList.

				//adding heading in APIOverallComparison HTML for the curl command created
				apiReporter.apiNewHeading(command);

				// Step : Execute curl command to get JSON response of the request
				jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
				apiReporter.apiNewHeading(jsonResponse);
				// Step : Verifying record count is equal to 1 as using Id as a parameter
				if (apiMethods.getRecordCount(jsonResponse) == 0) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
				{
					strActualResult="As expected getting no record from json response count of entries from json="+apiMethods.getRecordCount(jsonResponse);
					isEventSuccessful=true;
					apiReporter.apiPassBlock(strActualResult);

					if(jsonResponse.equals("[]")){
						isEventSuccessful=true;
						strActualResult="Verified response returned no user details";
						apiReporter.apiPassBlock(strActualResult);
					}
					else{
						isEventSuccessful=false;
						strActualResult="Verified Response returned is not as expected , Response for the query:"+jsonResponse;
						apiReporter.apiErrorBlock(strActualResult);
					}

				}
				else
				{
					if (apiMethods.getRecordCount(jsonResponse) == 1){
						strActualResult="getting one record in response";
						apiReporter.apiNewHeading(strActualResult);
						actualResultMap=apiMethods.getKeyValuePair(jsonResponse);
						if(actualResultMap.get("email").equals(dicCommon.get("testerEmailAddress"))){
							strActualResult="getting one record for the suser itself, record for no other user displayed";
							apiReporter.apiPassBlock(strActualResult);
						}
						else{
							strActualResult="getting one record for any other user:"+actualResultMap.get("email");
							apiReporter.apiErrorBlock(strActualResult);
						}

					}
					else{
						strActualResult= "Getting more than one  record in json response. <br> no of records in received  =" +apiMethods.getRecordCount(jsonResponse);
						isEventSuccessful=false;
						apiReporter.apiErrorBlock(strActualResult);
					}

				}

				reporter.ReportStep("Verifying record count in JSON reponse", "Record count should be equal to 1 as using Id as a parameter",strActualResult ,isEventSuccessful );

				// Step : Creating expected HashMap from the JSON Response

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
