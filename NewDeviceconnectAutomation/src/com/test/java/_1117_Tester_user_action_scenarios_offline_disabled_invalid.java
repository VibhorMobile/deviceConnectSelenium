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
 * Pre-requisite: Server should have atleast one device for all status
 * Jira Test Case Id: QA-1872
 * Coverage from Jira Case: Offline Disable and invalid scenario's
 */

public class _1117_Tester_user_action_scenarios_offline_disabled_invalid extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	


	public Map<String,String> expectedResultMap= new HashMap<String,String>();//Result Map for Hubs From UI

	Object[]values;
	String []propertyName={"retain","reboot","disable","release"},propertyValue;
	String deviceID,deviceName, serverIP=dicCommon.get("ApplicationURL"),deviceUser,Status;
	String errorMessage,entitlementMessage,parameterList,valueList,command,component,jsonResponse,dataTag;
	String entitlementErrorReboot="User does not have required entitlement DeviceReboot";
	String entitlementErrorEnablement="User does not have required entitlement DeviceEnablement";
	String entitlementErrorConnect="User does not have required entitlement DeviceConnect";

	public void createAndexecuteCurlCommand(String deviceId, String action)
	{
		//creating curl command
		component="device";
		parameterList="verb,action,Id";
		valueList="action,"+action+","+deviceId; //declaration and intialization of curl command varaiables.
		//command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST" ,"");	//Creates a curl command with component, parameterList and valueList.
		command= apiMethods.createCurlCommand(component , parameterList , valueList, "POST", "", "", "", "tester");

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

	public boolean verifyFromUI(String strAction){
		isEventSuccessful=false;
		isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
		String Status=GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text");
		switch(strAction){
		case "reboot":
			for(int iWaitCounter=0;iWaitCounter<=5;iWaitCounter++){
				PerformAction("browser", Action.Refresh);
				isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
				Status=GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text");
				if(Status.equals("Available")){
					strActualResult="Device rebooted status at UI is:"+Status;
					//apiReporter.apiErrorBlock("Device rebooted current status of device at DC UI is:"+Status);
					isEventSuccessful=false;
					break;
				}
				else{
					strActualResult="Device did not rebooted status at UI is:"+Status;

					isEventSuccessful=true;
				}
			}
			if(isEventSuccessful){
				apiReporter.apiPassBlock(strActualResult);
			}
			else{
				apiReporter.apiErrorBlock(strActualResult);
			}

			break;
		case "retain":
			for(int iWaitCounter=0;iWaitCounter<=5;iWaitCounter++){
				PerformAction("browser", Action.Refresh);
				isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
				Status=GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text");
				if(Status.contains("In Use")){
					strActualResult="Device  retained, status at UI is:"+Status;
					//apiReporter.apiErrorBlock("Device retained, current status of device at DC UI is:"+Status);
					isEventSuccessful=false;
					break;
				}
				else{
					strActualResult="Device did not retained, status at UI is:"+Status;
					//apiReporter.apiPassBlock("Device did not retained, current status of device at DC UI is:"+Status);
					isEventSuccessful=true;
				}

			}
			if(isEventSuccessful){
				apiReporter.apiPassBlock(strActualResult);
			}
			else{
				apiReporter.apiErrorBlock(strActualResult);
			}
			break;
		case "enable":
			if(!Status.equals("offline")){
				strActualResult="Device did not "+strAction+"ed, status at UI is:"+Status;
				apiReporter.apiPassBlock("Device did not "+strAction+"ed, current status of device at DC UI is:"+Status);
				isEventSuccessful=true;
			}
			else{
				strActualResult="Device   "+strAction+"ed, status at UI is:"+Status;
				apiReporter.apiErrorBlock("Device   "+strAction+"ed, current status of device at DC UI is:"+Status);
				isEventSuccessful=false;
			}


			reporter.ReportStep("Verifying status of device from UI", "Status of device should not change",strActualResult ,isEventSuccessful );
			break;

		}

		return isEventSuccessful;

	}

	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1117_tester_user_action_scenarios_offline_disabled_invalid");

			//Login to DC Server
			isEventSuccessful = Login();
			isEventSuccessful=GoToUsersPage();
			String[]entitlement={"Device reboot","Device enablement","Device connect"};
			Boolean[]entitlementValue={false,false,false};
			setUserRoleSettings("Tester",entitlement , entitlementValue);
			String[]status={"invalid","Disabled","Offline"};
			//String[]deviceType={"iOS","Android"};

			for(int i=0;i<=2;i++){
				GoToDevicesPage();
				if(!(status[i].equals("invalid"))){
					selectStatus(status[i]);
					isEventSuccessful =!PerformAction(dicOR.get("eleNoDevicesWarning_Devices"), Action.isDisplayed);
					if(isEventSuccessful){
						GoTofirstDeviceDetailsPage();
						String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
						deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];
					}
					else{
						apiReporter.apiErrorBlock("No Devices available with selected filter");
					}

				}
				else{
					deviceID="30c0750e-0000-0000-b156-46963419d70f";
				}
				String[]propertyName={"reboot","retain","enable"};
				for(int j=0;j<=2;j++){
					apiReporter.apiHeading2("Perform "+propertyName[j]+" action on "+status[i]+" device with device id "+deviceID);
					switch(propertyName[j]){
					case "reboot":
						entitlementMessage=entitlementErrorReboot;
						break;
					case "retain":
						entitlementMessage=entitlementErrorConnect;
						break;
					case "enable":
						entitlementMessage=entitlementErrorEnablement;
					}
					//************Create cURL command********************************
					createAndexecuteCurlCommand(deviceID, propertyName[j]);
					//****************Verify json Error Message*************************

					if(status[i].equals("invalid")&&propertyName[j].equals("enable")){
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
							
					}
					else{
						verifyJsonErrorMessage(entitlementMessage);
					}
					

					//***** Verify from UI***********************************
					if(!status[i].equals("invalid")){
						verifyFromUI(propertyName[j]);
					}


				}


			}

            //****************Action Scenarios's(Reboot,Retain,release and disable) for available devices*******************************


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
