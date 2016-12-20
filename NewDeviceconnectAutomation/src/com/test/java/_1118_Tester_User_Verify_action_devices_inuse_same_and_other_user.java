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
 * Coverage from Jira Case: InUse Same and other user devices.
 */
public class _1118_Tester_User_Verify_action_devices_inuse_same_and_other_user extends ScriptFuncLibrary {

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
				if(Status.equals("Offline")){
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
					isEventSuccessful=true;
					break;
				}
				else{
					strActualResult="Device did not retained, status at UI is:"+Status;
					//apiReporter.apiPassBlock("Device did not retained, current status of device at DC UI is:"+Status);
					isEventSuccessful=false;
				}

			}
			if(isEventSuccessful){
				apiReporter.apiPassBlock(strActualResult);
			}
			else{
				apiReporter.apiErrorBlock(strActualResult);
			}
			break;
		case "disable":
			if(!Status.equals("Disabled")){
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

		case "release":
			if(!Status.equals("available")){
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
			apiReporter.apiScriptHeading("_1118_Tester_User_Verify_action_devices_inuse_same_and_other_user");
			isEventSuccessful = Login();
			isEventSuccessful=GoToUsersPage();
			String[]entitlement1={"Device reboot","Device enablement","Device connect"};
			Boolean[]entitlementValue1={false,false,true};
			setUserRoleSettings("Tester",entitlement1 , entitlementValue1);
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
			isEventSuccessful=GoToUsersPage();
			String[]entitlement2={"Device reboot","Device enablement","Device connect"};
			Boolean[]entitlementValue2={false,false,false};
			setUserRoleSettings("Tester",entitlement2 , entitlementValue2);
			GoToDevicesPage();
			String[] devices={deviceNameOtherUser,deviceNameSameUser};
			String[] platform={"Android","iOS"};
			for(int i=0;i<=1;i++){
				GoToDevicesPage();
				isEventSuccessful=selectPlatform(platform[i]);
				if(isEventSuccessful){
					isEventSuccessful=selectStatus("In Use");
					if(isEventSuccessful){
						isEventSuccessful=searchDevice_DI(devices[i]);
						if(isEventSuccessful){
							GoTofirstDeviceDetailsPage();
							String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
							deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];

							//String []propertyValue={"editedName","125","editedNotes","false","invalid","true","false"};
							for(int j=0;j<=3;j++){
								if(i==0){
									deviceUser="Other User:"+dicCommon.get("EmailAddress");
								}
								else{
									deviceUser="Same User:"+dicCommon.get("testerEmailAddress");
								}

								apiReporter.apiHeading2("Perform "+propertyName[j]+" action on inuse device by "+deviceUser);

								switch(propertyName[j]){
								case "reboot":
									entitlementMessage=entitlementErrorReboot;
									break;
								case "retain":
									entitlementMessage=entitlementErrorConnect;
									break;
								case "disable":
									entitlementMessage=entitlementErrorEnablement;
									break;		
								}
								//************Create cURL command********************************
								createAndexecuteCurlCommand(deviceID, propertyName[j]);
								//************ExpectedResultsFrom UI*****************************
								if(!propertyName[j].equals("release")){
									verifyJsonErrorMessage(entitlementMessage);
								}
								else{
									if(devices[i].equals(deviceNameSameUser)){
										if (jsonResponse.contains("{\"isSuccess\":true")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
										{
											strActualResult="As expected getting success message.";
											isEventSuccessful=true;
											apiReporter.apiPassBlock("Getting success in api response");
											System.out.println("pass");
										}

										else
										{
											strActualResult= "Not getting success message . Hence failing stop test. <br> JSON Response =" +jsonResponse;
											isEventSuccessful=false;
											apiReporter.apiErrorBlock("Not getting success message in api response");
										}
										reporter.ReportStep("Verifying is success is true in JSON reponse", "Should get success as true in JSON Response",strActualResult ,isEventSuccessful );

									}
									else{
										if (jsonResponse.contains("{\"isSuccess\":false")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
										{
											strActualResult="As expected getting failure message.";
											isEventSuccessful=true;
											apiReporter.apiPassBlock("Getting failure in api response");
											System.out.println("pass");
										}

										else
										{
											strActualResult= "Not getting failure message . Hence failing stop test. <br> JSON Response =" +jsonResponse;
											isEventSuccessful=false;
											apiReporter.apiErrorBlock("Not getting failure message in api response");
										}
										reporter.ReportStep("Verifying is success is false in JSON reponse", "Should get success as false in JSON Response",strActualResult ,isEventSuccessful );

										errorMessage=apiMethods.getErrorMessage(command);
										String error="Device '"+deviceNameOtherUser+"' is in use by '"+dicCommon.get("EmailAddress")+"'.";
										System.out.println(error);
										if(errorMessage.equals(error)){
											strActualResult="Verified error message meg received is:Device '"+deviceNameOtherUser+"' is in use by '"+dicCommon.get("EmailAddress")+"'.";
											apiReporter.apiPassBlock(strActualResult);
										}
										else{
											strActualResult="Verified error message meg received is:"+errorMessage;
											apiReporter.apiErrorBlock(strActualResult);
										}

									}
								}


								//****************** Verify Details from UI*************************
								if(!(propertyName[j].equals("release") && devices[i].equals(deviceNameSameUser))){
									verifyFromUI(propertyName[j]);
								}
								else{
									if(propertyName[j].equals("release") && devices[i].equals(deviceNameSameUser)){
										PerformAction("browser", Action.Refresh);
										isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
										Status=GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text");
										if(Status.contains("In Use")){
											strActualResult="Status of device not changed,current status at UI is:"+Status;
											apiReporter.apiErrorBlock(strActualResult);
										}
										else{
											strActualResult="Status of device changed, current status at UI is:"+Status;
											apiReporter.apiPassBlock(strActualResult);
										}
									}
									

								}

							}
						}
						else{
							apiReporter.apiErrorBlock("No Devices available with selected filter");
						}


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
