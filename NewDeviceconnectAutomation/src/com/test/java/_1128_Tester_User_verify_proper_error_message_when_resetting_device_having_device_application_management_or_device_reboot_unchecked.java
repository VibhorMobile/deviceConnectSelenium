package com.test.java;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 8th Nov 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have atleast one device for all status
 * Jira Test Case Id: QA-1898
 * Coverage from Jira Case: Complete
 */
public class _1128_Tester_User_verify_proper_error_message_when_resetting_device_having_device_application_management_or_device_reboot_unchecked extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	


	public Map<String,String> expectedResultMap= new HashMap<String,String>();//Result Map for Hubs From UI

	String deviceID,deviceName, serverIP=dicCommon.get("ApplicationURL"),deviceUser,Status,entitlementMessage,Test;
	String errorMessage,parameterList,valueList,command,component,jsonResponse,dataTag;


	public void createAndexecuteCurlCommand(String deviceId, String action)
	{
		//creating curl command
		component="device";
		parameterList="verb,action,Id,reboot,unInstallAll,pretty";
		valueList="action,"+action+","+deviceId+",true,true,true"; //declaration and intialization of curl command varaiables.
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
	public void uncheckAllResetOptions(){
		isEventSuccessful = GoToSystemPage();
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,550)", "");
		PerformAction(dicOR.get("eleResetDeviceState_SystemsPage"), Action.DeSelectCheckbox);
		PerformAction(dicOR.get("eleUninstallAll_SystemsPage"), Action.DeSelectCheckbox);
		PerformAction(dicOR.get("eleRebootDevice_SystemsPage"), Action.DeSelectCheckbox);

	}
	public void verifyJsonErrorMessage(String entitlementMessage){
		if (jsonResponse.contains("\"isSuccess\": false")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
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
		//errorMessage=apiMethods.getErrorMessage(command);
		//apiReporter.apiNewHeading("entitlement error message shown for user:"+errorMessage);
		errorMessage=jsonResponse.split(":")[2]+jsonResponse.split(":")[3];
		errorMessage=errorMessage.split(",")[0];
		errorMessage=errorMessage.replace("\"", "");

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
	//Method to Install the first app listed on device

	public void installTrustBrowserAppOnDevice() throws InterruptedException{

		PerformAction("//table[@class='table data-grid']/tbody/tr/td[@title='Trust Browser']/../td[@class='btn-column']/button[contains(text(),'Install')]",Action.Click);

		WebDriverWait wait = new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='notification-content']"))); 

		//Thread.sleep(20000);
	}

	public boolean verifyFromUI(){
		isEventSuccessful=false;

		//Verifying if device rebooted or not
		isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
		String Status=GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text");
		PerformAction(dicOR.get("lnkShowDetails"), Action.WaitForElement,"20");
		for(int iwaitcounter=0; iwaitcounter<=5; iwaitcounter++) 
		{

			isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
			Status = GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text"); 
			System.out.println(Status);
			PerformAction("browser", Action.Refresh);		
			if (Status.contains("Offline"))
			{
				isEventSuccessful=false;
				strActualResult="Device went offline after reset command";
				apiReporter.apiNewHeading("Device got offline after reboot operation. current device status "+Status);
				//apiReporter.apiPassBlock("Device successfully rebooted after running API Query device status is "+deviceStatus);
				break;
			}
			else{
				isEventSuccessful=true;
				strActualResult="Device not rebooted after reset query execution";
			}

		}
		reporter.ReportStep("Verifying device rebooted", "Status of device should not change",strActualResult ,isEventSuccessful );
		if(isEventSuccessful){
			apiReporter.apiPassBlock(strActualResult);
		}
		else{
			apiReporter.apiErrorBlock(strActualResult);
		}
		//Verifying if apps uninstalled 
		String appStatus=GetTextOrValue(dicOR.get("txtNoAppMsg_DeviceDetailsPage"), "text");
		if(appStatus.contains("No applications installed")){
			strActualResult="All apps are uninstalled after reset query execution";
			isEventSuccessful=false;
			System.out.println("All apps are uninstalled after reset query execution");
		}
		else{
			strActualResult="Apps are not uninstalled after reset query execution";
			isEventSuccessful=true;
		}
		if(isEventSuccessful){
			apiReporter.apiPassBlock(strActualResult);
		}
		else{
			apiReporter.apiErrorBlock(strActualResult);
		}

		reporter.ReportStep("Verifying apps Uninstalled status", "Apps shouldn't be uninstalled from device", strActualResult, isEventSuccessful);

		return isEventSuccessful;

	}
	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1128_Tester_User_verify_proper_error_message_when_resetting_device_having_device_application_management_or_device_reboot_unchecked--------------- QA:1898");
			isEventSuccessful = Login();
			
			String[]entitlement={"Device reboot","Device application management"};
			Boolean[]entitlementValue1={true,false};
			Boolean[]entitlementValue2={false,true};
			Boolean[]entitlementValue;
			for(int j=0;j<=1;j++){
				if(j==0){
					entitlementValue=entitlementValue1;
					entitlementMessage=" User does not have required entitlement DeviceApplicationManagement";
					Test="reboot=true and uninstallAll=false";
				}
				else{
					entitlementValue=entitlementValue2;
					entitlementMessage=" User does not have required entitlement DeviceReboot";
					Test="reboot=false and uninstallAll=true";
				}
				isEventSuccessful=GoToUsersPage();
				setUserRoleSettings("Tester",entitlement , entitlementValue);
				//uncheckAllResetOptions();
				GoToDevicesPage();
				String[] platform={"Android","iOS"};
				for(int i=0;i<=1;i++){
					GoToDevicesPage();
					isEventSuccessful=selectPlatform(platform[i]);
					if(isEventSuccessful){
						isEventSuccessful=selectStatus("Available");
						if(isEventSuccessful){
							GoTofirstDeviceDetailsPage();
							String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
							deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];
							isEventSuccessful=verifyAppInstalledOnDevice("Trust Browser");
							if(!isEventSuccessful){
								installTrustBrowserAppOnDevice();
							}
							apiReporter.apiHeading2("Perform reset operation on avaialable "+platform[i]+" device "+deviceName+" with "+Test );

							//************Create cURL command********************************
							createAndexecuteCurlCommand(deviceID, "reset");
							//************ExpectedResultsFrom UI*****************************
							verifyJsonErrorMessage(entitlementMessage);
							//****************** Verify Details from UI*************************
							verifyFromUI();
						}

					}
					else{
						apiReporter.apiErrorBlock("No Devices available with selected filter");
					}
				}
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
