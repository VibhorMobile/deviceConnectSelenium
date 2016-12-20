package com.test.java;

import java.io.IOException;
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
 * Creation Date: 7th Nov 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have atleast one device for all status
 * Jira Test Case Id: QA-1875
 * Coverage from Jira Case: Scenario with no entitlement to view applist
 */

public class _1127_Tester_user_verify_get_Device_with_partial_access extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	public Map<String,String> expectedResultMap= new HashMap<String,String>();
	private String valueOfProp="";
	String deviceID =  "";
     String[] queryProp ={"id","name","slotNumber","enabled","deleted","notes","availability"}; //,"operatingSystem" 


	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1127_Tester_user_verify_get_Device_with_partial_access");
			
			//********* Remove entitlement from tester user to view application details*************
			isEventSuccessful = Login();
			if(isEventSuccessful){
				isEventSuccessful=GoToUsersPage();
				if(isEventSuccessful){
					String[]entitlement={"Device list"};
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
			//*******************************************************************************************
			String serverIP=dicCommon.get("ApplicationURL");
			
			

			//****************************Code to create expected Result Starts here *****************************************************************
			// Step 1 : login to deviceConnect with valid user credentials.
			//	isEventSuccessful = Login();

            GoToDevicesPage();
			String[] deviceType= {"Android","iOS"}; //Declaring an array to run for Android and iOS app
			/*This loop run for iOS first and then Android*/
			for(int j=0; j<=1; j++)
			{

				isEventSuccessful = selectPlatform(deviceType[j]);
				if(deviceType[j].equals("Android"))
				{
					isEventSuccessful = PerformAction("txtDeviceSearch", Action.Clear);
					PerformAction("Browser", Action.Refresh);
				}

				isEventSuccessful = selectStatus("Available");//Checking different statuses Available, InUse, Offline, Disabled
				String devicesErrorMsg=GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text");
				//System.out.println("no device:"+devicesErrorMsg);
				if(devicesErrorMsg.contains("No devices match your filter criteria.")){
					System.out.println("No Devices are their");
					apiReporter.apiErrorBlock("Devices not available with selected filters");
				}
				else{
					isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0]; //Navigating to first Available or Inuse, Offline Or Disabled DeviceDetails page
					String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
					deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];
                   //Step 2: Getting expected Result map from application details page
					expectedResultMap = getDeviceExpectedMap(deviceID,deviceName,serverIP );
					//expectedResultMap.put("operatingSystem",deviceType[j]);

					//Running the script for all the query properties available for Application component
					for (String propertyInUse: queryProp)
					{
						
								valueOfProp=expectedResultMap.get(propertyInUse);
							
						if(valueOfProp.equals("null")){
							valueOfProp="";
						}
						//If value exists other than "Not Verified",use the value in API Request
						/*}*/

						//************************Code for getting actual result from API Request starts here *******************************************************************
						//adding heading in APIOverallComparison HTML  for the AppType, Property and Value used in API Request
						apiReporter.apiHeading2(deviceType[j]+" Device - Get Device (Using "+propertyInUse+" with value = "+valueOfProp+") for tester user");

						//Create the curl command to get application details with Id
						String component= "Device", parameterList=propertyInUse.trim()+",operatingSystem", valueList=valueOfProp.trim()+","+deviceType[j]; //declaration and intialization of curl command varaiables.
						String jsonResponse; //declaration of string to capture JSON Response of the API request.
						String command=apiMethods.createCurlCommand(component, parameterList, valueList,"","","","","tester");	//Creates a curl command with component, parameterList and valueList.

						//adding heading in APIOverallComparison HTML for the curl command created
						apiReporter.apiNewHeading(command);

						// Step : Execute curl command to get JSON response of the request
						jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
                        apiReporter.apiNewHeading("Response of query executed is: "+jsonResponse);
						// Step : Verifying record count is equal to 1 as using Id as a parameter
						if (apiMethods.getRecordCount(jsonResponse) > 0) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
						{
							strActualResult="Getting device details in response";
							isEventSuccessful=false;
							apiReporter.apiErrorBlock(strActualResult);
						}
						else
						{
							strActualResult= "No Device details in response";
							isEventSuccessful=true;
							apiReporter.apiPassBlock(strActualResult);
						}
						reporter.ReportStep("Verifying record count in JSON reponse", "Record count should be zero",strActualResult ,isEventSuccessful );

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
	}}
