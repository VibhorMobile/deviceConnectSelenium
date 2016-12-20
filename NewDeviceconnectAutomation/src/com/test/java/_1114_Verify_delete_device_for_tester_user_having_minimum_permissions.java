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
 * Jira Test Case Id: QA-1871
 * Coverage from Jira Case: Complete
 */


public class _1114_Verify_delete_device_for_tester_user_having_minimum_permissions extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	


	public Map<String,String> expectedResultMapHub= new HashMap<String,String>();//Result Map for Hubs From UI
	public Map<String,String> expectedResultMapPort= new HashMap<String,String>();//Result Map for Ports from UI
	public Map<String,String> actualResultMapPort = new HashMap<String,String>();//Result Map for Hub from API
	Map<String,String> actualResultMapHub = new HashMap<String,String>();//Result Map for Port from API
	Object[]values;
	String deviceID,deviceName;
	String errorMessage;
	String entitlementErrorExpected="User does not have required entitlement DeviceDelete";
	//String pattern=([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])(:|\.)\d{2};
	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1114_Verify_delete_device_for_tester_user_having_minimum_permissions");

			//Login to DC Server
			isEventSuccessful = Login();
			isEventSuccessful=GoToUsersPage();
			String[]entitlement={"Device delete"};
			Boolean[]entitlementValue={false};
			setUserRoleSettings("Tester",entitlement , entitlementValue);
			String[]status={"Available","In Use","Offline","Disabled"};
			String[]deviceType={"iOS","Android"};

			GoToDevicesPage();

			for(int i=0;i<2;i++){
				for(int j=0;j<4;j++){
					isEventSuccessful=selectPlatform(deviceType[i]);
					if(isEventSuccessful){
						isEventSuccessful=selectStatus(status[j]);
						apiReporter.apiHeading2("Deleting a "+deviceType[i]+" device with "+status[j]+" status");
						if(isEventSuccessful){
							 isEventSuccessful =!PerformAction(dicOR.get("eleNoDevicesWarning_Devices"), Action.isDisplayed);
							//System.out.println("no device:"+devicesErrorMsg);
							if(isEventSuccessful){
								GoTofirstDeviceDetailsPage();
								String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
								deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];

								
								String component="Device",parameterList="verb,Id,",valueList="delete,"+deviceID; //declaration and intialization of curl command varaiables.
								String jsonResponse;
								String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST" ,"","","","tester");

								jsonResponse=apiMethods.execCurlCmd(command);
								apiReporter.apiNewHeading("Response for Query from API: "+jsonResponse);

								//reporter.ReportStep("Verifying success in JSON reponse", "Should get success message in JSON Response",strActualResult ,isEventSuccessful );
								errorMessage=apiMethods.getErrorMessage(command);
								apiReporter.apiNewHeading("entitlement error message shown for user:"+errorMessage);

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

								//*************************   Verify from UI that device status is not removed at UI****************************************

								isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
								String Status=GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text");
								if(!Status.equalsIgnoreCase("removed")){
									strActualResult="Device is not removed from UI,current status at UI is:"+status;
									apiReporter.apiPassBlock("Device is not removed from UI,current status at UI is:"+status);
									isEventSuccessful=true;

								}
								else{
									strActualResult="Device is removed from UI,current status at UI is:"+status;
									apiReporter.apiErrorBlock("Device is removed from UI,current status at UI is:"+status);
									isEventSuccessful=false;
								}
								reporter.ReportStep("Verify device is not removed from UI", "device status should not be removed", strActualResult, isEventSuccessful);



								GoToDevicesPage();
							}
							else{
								apiReporter.apiErrorBlock("No Devices Available with selected filter criteria");
							}
						}
					}
				}
				Thread.sleep(3000);
				



				
			}
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
