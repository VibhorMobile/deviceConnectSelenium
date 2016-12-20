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
 * Creation Date: 18th Oct 2016
 * Last Modified Date: NA
 * Pre-requisite: TestData.xlsx should have tester user and token no updated,
 * Jira Test Case Id: QA-1867
 * Coverage from Jira Case: Complete
 */


public class _1115_Verify_update_of_device_properties_by_tester_user_having_minimum_permissions extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	


	public Map<String,String> expectedResultMap= new HashMap<String,String>();//Result Map for Hubs From UI

	Object[]values;
	String deviceID,deviceName, serverIP=dicCommon.get("ApplicationURL"),deviceUser;
	String errorMessage,entitlementMessage,parameterList,valueList,command,component,jsonResponse,dataTag;
	String entitlementErrorModify="User does not have required entitlement DeviceModify";
	String entitlementErrorEnablement="User does not have required entitlement DeviceEnablement";
	String entitlementErrorDeleted="User does not have required entitlement DeviceDelete";

	public void createAndexecuteCurlCommand(String deviceId, String property,String value)
	{
		//creating curl command
		parameterList="verb"+","+"Id";
		valueList="update,"+deviceId; //declaration and intialization of curl command varaiables.
		dataTag="{"+property+":\\\"" +value+"\\\"}";
		command=apiMethods.createCurlCommand("Device", parameterList, valueList, "POST" ,"Content-Type: application/json",dataTag,"","tester");	//Creates a curl command with component, parameterList and valueList.

		//adding heading in APIOverallComparison HTML for the curl command created
		apiReporter.apiNewHeading(command);

		// Step : Execute curl command to get JSON response of the request
		jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

		apiReporter.apiNewHeading(jsonResponse);

		// Step : Verifying record count is equal to 1 as using Id as a parameter
		//apiMethods.apiVerifyGettingSuccessMessage(jsonResponse);

	}
	public void verifyJsonErrorMessage(String entitlementMessage){
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
		reporter.ReportStep("Verifying is success is false in JSON reponse", "Should get success as false in JSON Response",strActualResult ,isEventSuccessful );
		errorMessage=apiMethods.getErrorMessage(command);
		//apiReporter.apiNewHeading("entitlement error message shown for user:"+errorMessage);


		//reporter.ReportStep("Verifying is success is false in JSON reponse", "Should get success as false in JSON Response",strActualResult ,isEventSuccessful );
		if(errorMessage.equals(entitlementMessage)){
			strActualResult="Verified as expected getting entitlement error message.";
			isEventSuccessful=true;
			apiReporter.apiPassBlock("Received entitlement error message as expected , Message shown was: "+errorMessage);
		}
		else{
			strActualResult="Verified not getting entitlement error message as expected: "+errorMessage;
			isEventSuccessful=false;
			apiReporter.apiErrorBlock("Not getting entitlement error message as expected , Message shown was: "+errorMessage);
		}
		reporter.ReportStep("Verify entitlement error message displayed", "expected entitlement error message: "+entitlementMessage, strActualResult, isEventSuccessful);


	}

	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1115_Verify_update_of_device_properties_by_tester_user_having_minimum_permissions");

			//Login to DC Server
			isEventSuccessful = Login();
			isEventSuccessful=GoToUsersPage();
			String[]entitlement={"Device modify","Device enablement","Device delete"};
			Boolean[]entitlementValue={false,false,false};
			setUserRoleSettings("Tester",entitlement , entitlementValue);
			//String[]status={"Available","In Use","Offline","Disabled"};
			//String[]deviceType={"iOS","Android"};

			GoToDevicesPage();

			//****************Update Scenarios's for available devices*******************************
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();
			String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
			deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];
			String []property={"name","slotNumber","notes","enabled","enabled","deleted","deleted"};
			String []value={"editedName","125","editedNotes","false","invalid","true","false"};
			for(int i=0;i<=property.length-1;i++){
				apiReporter.apiHeading2("Update device property: "+property[i]+" to "+value[i]+" for an online device");

				switch(property[i]){
				case "enabled":
					entitlementMessage=entitlementErrorEnablement;
					break;
				case "deleted":
					entitlementMessage=entitlementErrorDeleted;
					break;
				default:
					entitlementMessage=entitlementErrorModify;
					break;
				}
				//************Create cURL command********************************
				createAndexecuteCurlCommand(deviceID, property[i], value[i]);
				//************ExpectedResultsFrom UI*****************************
				expectedResultMap = deviceExpectedMap(deviceID,deviceName,serverIP );
				apiReporter.apiNewHeading("Results from UI after query executed: "+expectedResultMap);
				//****************Verify json Error Message*************************
				verifyJsonErrorMessage(entitlementMessage);
				//****************** Verify Details from UI*************************
				if(!expectedResultMap.get(property[i]).equals(value[i])){
					isEventSuccessful=true;
					strActualResult="value for property "+property[i]+" not updated for device "+deviceName;
					apiReporter.apiPassBlock("value for property "+property[i]+" not updated for device "+deviceName);

				}
				else{
					isEventSuccessful=false;
					strActualResult="value for property "+property[i]+" not updated for device "+deviceName;
					apiReporter.apiPassBlock("value for property "+property[i]+" not updated for device "+deviceName);
				}

			}

			//****************Update Scenarios's for In Use devices*******************************
			//Retain a device by other user
			//apiReporter.apiAddBlock("Get Device Properties for an In-Use " +deviceType[j]+" device");
			GoToDevicesPage();
			values = ExecuteCLICommand("run","Android","","","","");
			isEventSuccessful = (boolean)values[2];
			String deviceNameOtherUser= (String)values[3];
			if (isEventSuccessful)
			{
				strActualResult = "Viewer launched after connecting to an  device:  " + values[0] + " & processfound : " +  values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			//Retain a device for tester user
			values = ExecuteCLICommand("run","iOS",dicCommon.get("testerEmailAddress"),dicCommon.get("testerPassword"),"","");
			isEventSuccessful = (boolean)values[2];
			String deviceNameSameUser= (String)values[3];
			if (isEventSuccessful)
			{
				strActualResult = "Viewer launched after connecting to an  device:  " + values[0] + " & processfound : " +  values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}


			String[] devices={deviceNameOtherUser,deviceNameSameUser};
			String[] platform={"Android","iOS"};
			String[]propertyName={"enabled","deleted"};
			String[]propertyValue={"false","true"};
			for(int i=0;i<=1;i++){
				GoToDevicesPage();
				selectPlatform(platform[i]);
				selectStatus("In Use");
				searchDevice_DI(devices[i]);
				GoTofirstDeviceDetailsPage();
				deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
				deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];
				for(int j=0;j<=1;j++){
					if(i==0){
						deviceUser="OtherUser";
					}
					else{
						deviceUser="SameUser";
					}
					apiReporter.apiHeading2("update device property "+propertyName[j]+" to "+propertyValue[j]+" for an inUse device by "+deviceUser);
					switch(propertyName[j]){
					case "enabled":
						entitlementMessage=entitlementErrorEnablement;
						break;
					case "deleted":
						entitlementMessage=entitlementErrorDeleted;
						break;
					}
					createAndexecuteCurlCommand(deviceID, propertyName[j], propertyValue[j]);
					//************ExpectedResultsFrom UI*****************************
					expectedResultMap = deviceExpectedMap(deviceID,deviceName,serverIP );
					apiReporter.apiNewHeading("Results from UI after query executed: "+expectedResultMap);
					//****************Verify json Error Message*************************
					verifyJsonErrorMessage(entitlementMessage);
					//****************** Verify Details from UI*************************
					if(!expectedResultMap.get(propertyName[j]).equals(propertyValue[j])){
						isEventSuccessful=true;
						strActualResult="value for property "+propertyName[j]+" not updated for device "+deviceName;
						apiReporter.apiPassBlock("value for property "+propertyName[j]+" not updated for device "+deviceName);

					}
					else{
						isEventSuccessful=false;
						strActualResult="value for property "+propertyName[j]+" not updated for device "+deviceName;
						apiReporter.apiPassBlock("value for property "+propertyName[j]+" not updated for device "+deviceName);
					}

				}



			}
			//**************************************************************************//
			// Step  : Release device.
			//**************************************************************************//
			ExecuteCLICommand("release", "", "", "", deviceNameOtherUser, "","","" );
			ExecuteCLICommand("release", "", dicCommon.get("testerEmailAddress"),dicCommon.get("testerPassword"), deviceNameSameUser, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");

			//********************************************** Scenario's related to Offline and Disabled Devices*************************

			String [] statusDevice={"Offline","Disabled"};
			String [] propName={"enabled","deleted"};
			String [] propValue={"false","true"};


			for(int i=0;i<=1;i++){
				GoToDevicesPage();
				selectStatus(statusDevice[i]);
				isEventSuccessful =!PerformAction(dicOR.get("eleNoDevicesWarning_Devices"), Action.isDisplayed);
				//System.out.println("no device:"+devicesErrorMsg);
				if(isEventSuccessful){
					GoTofirstDeviceDetailsPage();
					deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
					deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];
					for (int j=0;j<=1;j++){
						if(statusDevice[i].equals("Disabled")&&propName[j].equals("enabled")){
							propValue[j]="true";
						}
						apiReporter.apiHeading2("update device property "+propName[j]+" to "+propValue[j]+" for a/an"+statusDevice[i]+" device "+deviceName);
						switch(propName[j]){
						case "enabled":
							entitlementMessage=entitlementErrorEnablement;
							break;
						case "deleted":
							entitlementMessage=entitlementErrorDeleted;
							break;
						}

						createAndexecuteCurlCommand(deviceID, propName[j], propValue[j]);
						//************ExpectedResultsFrom UI*****************************
						expectedResultMap = deviceExpectedMap(deviceID,deviceName,serverIP );
						apiReporter.apiNewHeading("Results from UI after query executed: "+expectedResultMap);
						//****************Verify json Error Message*************************
						verifyJsonErrorMessage(entitlementMessage);
						//****************** Verify Details from UI*************************
						if(!expectedResultMap.get(propName[j]).equals(propValue[j])){
							isEventSuccessful=true;
							strActualResult="value for property "+propName[j]+" not updated for device "+deviceName;
							apiReporter.apiPassBlock("value for property "+propName[j]+" not updated for device "+deviceName);

						}
						else{
							isEventSuccessful=false;
							strActualResult="value for property "+propName[j]+" not updated for device "+deviceName;
							apiReporter.apiPassBlock("value for property "+propName[j]+" not updated for device "+deviceName);
						}



					}
				}
				else{
					apiReporter.apiErrorBlock("No Devices available with selected filter");
				}
			}
			//**************************************************************************//
			// Step  : Release device.
			//**************************************************************************//
			ExecuteCLICommand("release", "", "", "", deviceNameOtherUser, "","","" );
			ExecuteCLICommand("release", "", dicCommon.get("testerEmailAddress"),dicCommon.get("testerPassword"), deviceNameSameUser, "","","" );
			Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");

			apiReporter.apiOverallHtmlLinkStep();

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




}
