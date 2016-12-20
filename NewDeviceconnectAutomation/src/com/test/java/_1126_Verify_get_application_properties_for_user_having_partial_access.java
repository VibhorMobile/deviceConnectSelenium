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
 * Creation Date: 7th Nov 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have atleast one device for all status
 * Jira Test Case Id: QA-1874
 * Coverage from Jira Case: Scenario with no entitlement to view applist
 */

public class _1126_Verify_get_application_properties_for_user_having_partial_access extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	

	Boolean value;
	Object[] values = new Object[2];
	public Map<String,String> expectedResultMap= new HashMap<String,String>();
	public Map<String,String> actualResultMap = new HashMap<String,String>(); // Declaring a HashMap to store actual results key and values
	public Map<String,String> oldActualResultMap = new HashMap<String,String>();
	private String valueOfProp="";
	// Creating an array having all the model properties available for Application component
	String[] appModel ={"id","displayName","applicationIdentifier","version","buildVersion","operatingSystem","minimumOperatingSystemVersion",
			"applicationBlob","originalApplicationBlob","fileName","remotePort","versionCounter","iconBlob","fileByteCount","vendorApplicationName",
			"provisionExpirationDate","provisionsAllDevices","signingCertificateName","isSigningCertificatePresentInEmbeddedProvisionProfile","supportedFormFactors",
			"applicationErrors","enabled","createdDate","appTeamIdentifier","trustDylibTeamIdentifier","isTrustDylibEmbeddedInApp","notes","agentHash"};
	//Creating an array of all the query properties available for Application component
	String[] queryProp ={"id","displayName","applicationIdentifier","version","buildVersion","minimumOperatingSystemVersion",
			"applicationBlob","originalApplicationBlob","fileName"}; //,"operatingSystem"


	public final void testScript()
	{
		try
		{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1126_Verify_get_application_properties_for_user_having_partial_access");

			String serverIP=dicCommon.get("ApplicationURL");
			String appId; //=androidAppId;

			//****************************Code to create expected Result Starts here *****************************************************************
			// Step 1 : login to deviceConnect with valid user credentials.
			isEventSuccessful = Login();
			if(isEventSuccessful){
				isEventSuccessful=GoToUsersPage();
				if(isEventSuccessful){
					String[]entitlement={"Application list"};
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

			String[] appType= {"iOS","Android"}; //Declaring an array to run for Android and iOS app
			//To run the query properties for both Android as wee

			for(int j=0; j<=1; j++)
			{
				isEventSuccessful=GoToApplicationsPage();
				searchDevice_DI(appType[j]); // Searching for Android or iOS on Application List page
				GoToFirstAppDetailsPage(); //Clicks on the first application listed
				appId=GetAppGUID(); //Gets the GUID of the Application whose application details page is displayed


				//Step 2: Getting expected Result map from application details page
				expectedResultMap=getApplicationExpectedMap(appId, serverIP);

				//Step :Running script for all the application properties

				for (String propertyInUse: queryProp)
				{
					valueOfProp=expectedResultMap.get(propertyInUse); //If value exists other than "Not Verified",use the value in API Request

					//************************Code for getting actual result from API Request starts here *******************************************************************
					//adding heading in APIOverallComparison HTML  for the AppType, Property and Value used in API Request
					apiReporter.apiHeading2(appType[j]+" Application - Get App (Using "+propertyInUse+" with value = "+valueOfProp+") for tester user");

					//Create the curl command to get application details with Id
					String component= "Application", parameterList=propertyInUse.trim()+",operatingSystem", valueList=valueOfProp.trim()+","+appType[j]; //declaration and intialization of curl command varaiables.
					String jsonResponse; //declaration of string to capture JSON Response of the API request.
					String command=apiMethods.createCurlCommand(component, parameterList, valueList,"","","","","tester");	//Creates a curl command with component, parameterList and valueList.

					//adding heading in APIOverallComparison HTML for the curl command created
					apiReporter.apiNewHeading(command);

					// Step : Execute curl command to get JSON response of the request
					jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
					apiReporter.apiNewHeading("Json Response for executed query: "+jsonResponse);
					// Step : Verifying record count is equal to 1 as using Id as a parameter
					if (apiMethods.getRecordCount(jsonResponse) > 0) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
					{
						strActualResult="getting application records in response";
						isEventSuccessful=false;
						apiReporter.apiErrorBlock(strActualResult);
					}
					else
					{
						strActualResult= "Not getting any application record in response";
						isEventSuccessful=true;
						apiReporter.apiPassBlock(strActualResult);
					}
					reporter.ReportStep("Verifying record count in JSON reponse", "Record count should be equal to 1 as using Id as a parameter",strActualResult ,isEventSuccessful );

					// Step : Verifying json response is empty
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


					//Adds a step Provides API Overall Comparison file link in DC Selenium Execution Report
					apiReporter.apiOverallHtmlLinkStep();
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
