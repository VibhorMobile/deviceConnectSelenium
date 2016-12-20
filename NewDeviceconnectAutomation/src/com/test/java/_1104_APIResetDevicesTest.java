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
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 27 May 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have Atlease 1 iOS and Android device with Available, and 2 devices with offline status
 * Admin user can only be used
 * 
 */
public class _1104_APIResetDevicesTest extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	public Map<String,String> expectedResultMap= new HashMap<String,String>();
	ArrayList<String> errorList = new ArrayList<String>(); 

	// Declaring a HashMap to store actual results key and values
	String deviceName,deviceStatus,unInstallValue,appStatus,component,parameterList,valueList,jsonResponse,command;
	boolean rebootStatus,isUninstalled,isAvailable;
	boolean unInstallAllStatus;
	boolean resetStatus;
	String deviceID;
	boolean isEventSuccessfulRecordCount, isRebooted=false;

	public void uncheckAllResetOptions(){
		isEventSuccessful = GoToSystemPage();
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,550)", "");
		resetStatus=PerformAction(dicOR.get("eleResetDeviceState_SystemsPage"), Action.isSelected);
		System.out.println("Status for reset CB: "+resetStatus);
		if(resetStatus){
			PerformAction(dicOR.get("eleResetDeviceState_SystemsPage"), Action.DeSelectCheckbox);
		}
		unInstallAllStatus=PerformAction(dicOR.get("eleUninstallAll_SystemsPage"), Action.isSelected);
		if(unInstallAllStatus){
			PerformAction(dicOR.get("eleUninstallAll_SystemsPage"), Action.DeSelectCheckbox);
		}
		rebootStatus=PerformAction(dicOR.get("eleRebootDevice_SystemsPage"), Action.isSelected);
		if(rebootStatus){
			PerformAction(dicOR.get("eleRebootDevice_SystemsPage"), Action.DeSelectCheckbox);
		}
	}

	//Method to Install the first app listed on device

	public void installTrustBrowserAppOnDevice() throws InterruptedException{
		
		PerformAction("//table[@class='table data-grid']/tbody/tr/td[@title='Trust Browser']/../td[@class='btn-column']/button[contains(text(),'Install')]",Action.Click);
		 
		WebDriverWait wait = new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='notification-content']"))); 

		//Thread.sleep(20000);
	}
	public final void testScript() throws InterruptedException	{
		try{

			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1104_APIResetDevicesTest");

			String serverIP=dicCommon.get("ApplicationURL");
			isEventSuccessful = Login();
		 
			uncheckAllResetOptions();
			GoToDevicesPage();

			isEventSuccessful = selectStatus("Available");


			String devicesErrorMsg=GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text");
			System.out.println("no device:"+devicesErrorMsg);
			if(devicesErrorMsg.contains("No devices match your filter criteria.")){
				System.out.println("No Devices are their");
				apiReporter.apiErrorBlock("Devices not available with selected filters");
			}
			else{
				isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0]; //Navigating to first Available or Inuse, Offline Or Disabled DeviceDetails page
				String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
				deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];

				String[] queryProp= {"unInstallAll","reboot"};
				String[] valueProp= {"false","true"};

				//************************************************Script Starts here for Initial four cases reboot=true,reboot=false and unInstallAll=true,and unInstallAll=False************

				System.out.println("************************************************Script Starts here for Initial four cases reboot=true,reboot=false and unInstallAll=true,and unInstallAll=False************");		



				for(int i=0; i<=1; i++)
				{
					for (int j=0; j<=1; j++){
						isEventSuccessful=verifyAppInstalledOnDevice("Trust Browser");
						if(!isEventSuccessful){
							installTrustBrowserAppOnDevice();
						}
						

						//installAppOnDevice();

						//************************Code for getting actual result from API Request starts here *******************************************************************
						//adding heading in APIOverallComparison HTML Property and Value used in API Request
						apiReporter.apiHeading2(" Reset Device with " +queryProp[i]+" as "+valueProp[j]);

						//Create the curl command to update user details with id
						component="Device";
						parameterList="verb,action,Id,"+ queryProp[i];
						valueList="action,reset,"+deviceID+","+valueProp[j]; //declaration and intialization of curl command varaiables.
						command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST" ,"");

						//adding heading in APIOverallComparison HTML for the curl command created
						apiReporter.apiNewHeading(command);

						// Step : Execute curl command to get JSON response of the request
						jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

						// Step : Verifying getting success in response
						apiReporter.apiNewHeading("Json Response of Query:"+jsonResponse);
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




						//***********code for getting expected starts here***************************************
						if(isEventSuccessfulRecordCount){

							PerformAction("Browser", Action.Refresh);

							if(queryProp[i].contains("unInstallAll")){
								PerformAction(dicOR.get("txtNoAppMsg_DeviceDetailsPage"), Action.WaitForElement,"20");

								if(valueProp[j].equals("false")){
									appStatus=GetTextOrValue(dicOR.get("txtNoAppMsg_DeviceDetailsPage"), "text");
									if(appStatus.contains("No applications installed")){
										apiReporter.apiNewHeading("Applications are not installed message at DC UI  : "+appStatus);
										isEventSuccessful=false;
									}
									else{
										isEventSuccessful=true;
										apiReporter.apiNewHeading("Applications are installed  for device in DC Ui");
									}
									if(isEventSuccessful){
										strActualResult="Apps are not Uninstalled";
									}
									else{
										strActualResult="Apps are Uninstalled";
									}
								}
								if(valueProp[j].equals("true")){
									appStatus=GetTextOrValue(dicOR.get("txtNoAppMsg_DeviceDetailsPage"), "text");


									apiReporter.apiNewHeading("Applications at DC UI: "+appStatus);
									//***********code for getting expected ends here***************************************

									//******Code to compare expected Starts Here*********************
									if(appStatus.contains("No applications installed")){
										strActualResult="All apps Uninstalled from device";
										isEventSuccessful=true;
										System.out.println("Device reset and apps are uninstalled");
									}
								}
								reporter.ReportStep("Verifying apps Uninstalled status", "Apps Uninstall all should be"+valueProp, strActualResult, isEventSuccessful);
							}
							if(queryProp[i].contains("reboot")){
								PerformAction(dicOR.get("lnkShowDetails"), Action.WaitForElement,"20");
								for(int iwaitcounter=0; iwaitcounter<=5; iwaitcounter++) 
								{

									isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
									deviceStatus = GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text"); 
									System.out.println(deviceStatus);
									PerformAction("browser", Action.Refresh);		
									if (deviceStatus.contains("Offline"))
									{
										isRebooted=true;
										strActualResult="Device went offline after reboot";
										apiReporter.apiNewHeading("Device got offline after reboot operation. current device status "+deviceStatus);
										//apiReporter.apiPassBlock("Device successfully rebooted after running API Query device status is "+deviceStatus);
										break;
									}


								}

								if(valueProp[j].equals("false")){
									if(isRebooted){
										isEventSuccessful=false;

									}
									else{
										isEventSuccessful=true;
										apiReporter.apiNewHeading("Device did not went to offline state. current device status "+deviceStatus);
									}
								}
								if(valueProp[j].equals("true")){


									if (isRebooted)
									{
										for(int iwaitcounter=0; iwaitcounter<=10; iwaitcounter++) 
										{
											isEventSuccessful=false;
											PerformAction("browser", Action.Refresh);		
											isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
											deviceStatus = GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text"); 
											if (deviceStatus.contains("Available"))
											{
												//apiReporter.apiNewHeading("Device Status at UI isRebooted: "+isRebooted);
												isEventSuccessful=true;
												apiReporter.apiNewHeading("Device got Online again. current device status "+deviceStatus);
												break;
											}


										}


									}
									else{
										isEventSuccessful=false;

									}
								}
							}

							if(isEventSuccessful)
							{
								strActualResult= "Reset Done, "+queryProp[i]+" with value "+valueProp[j] +"operation succesfully done";
								apiReporter.apiPassBlock(" Device Reset Done, "+queryProp[i]+"operation with value "+valueProp[j]+" performed");
							}
							else
							{
								strActualResult= "Reset Not Done," +queryProp[i]+"operation with value "+valueProp[j] +" did not happen";
								apiReporter.apiErrorBlock(" Device reset Unsuccesful, " +queryProp[i]+"operation with value "+valueProp[j]+" did not happen");
							}
							reporter.ReportStep("Compares Actual and Expected Map", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);
						}
						else{
							apiReporter.apiErrorBlock("Getting Error in API respose");
						}




					}
				}
				//************************************************Script Ends here for Initial four cases reboot=true,reboot=false and unInstallAll=true,and unInstallAll=False************
				System.out.println("************************************************Script Ends here for Initial four cases reboot=true,reboot=false and unInstallAll=true,and unInstallAll=False************");
				System.out.println();
				System.out.println();
				System.out.println();
				//************************************************Script Starts here for reset case reboot=true and unInstallAll=true************
				System.out.println("************************************************Script Starts here for reset case reboot=true ,unInstallAll=true and reboot=false,unInstall=false************");
				for (int k=0; k<=1; k++){
					
					apiReporter.apiHeading2(" Reset Device with reboot and unInstallAll as  "+valueProp[k]);
					isEventSuccessful=verifyAppInstalledOnDevice("Trust Browser");
					if(!isEventSuccessful){
						installTrustBrowserAppOnDevice();
					}

					//Create the curl command to update user details with id
					component="Device";
					parameterList="verb,action,Id,reboot,unInstallAll";
					valueList="action,reset,"+deviceID+","+valueProp[k]+","+valueProp[k]; //declaration and intialization of curl command varaiables.
					command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST" ,"");

					//adding heading in APIOverallComparison HTML for the curl command created
					apiReporter.apiNewHeading(command);

					// Step : Execute curl command to get JSON response of the request
					jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

					// Step : Verifying getting success in response
					apiReporter.apiNewHeading("Json Response of Query:"+jsonResponse);
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




					//***********code for getting expected starts here***************************************
					if(isEventSuccessfulRecordCount){


						PerformAction(dicOR.get("txtNoAppMsg_DeviceDetailsPage"), Action.WaitForElement,"20");
						PerformAction(dicOR.get("lnkShowDetails"), Action.WaitForElement,"20");
						String appStatus=GetTextOrValue(dicOR.get("txtNoAppMsg_DeviceDetailsPage"), "text");

						for(int iwaitcounter=0; iwaitcounter<=5; iwaitcounter++) 
						{
							PerformAction("lnkShowDetails", Action.Click);
							deviceStatus = GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text"); 
							System.out.println(deviceStatus);
							PerformAction("browser", Action.Refresh);		
							if (deviceStatus.contains("Offline"))
							{
								isRebooted=true;
								strActualResult="Device went offline after reboot";
								apiReporter.apiNewHeading("Device got offline after reboot operation. current device status "+deviceStatus);
								//apiReporter.apiPassBlock("Device successfully rebooted after running API Query device status is "+deviceStatus);
								break;
							}
							else{
								isRebooted=false;
								//strActualResult="Device did not went offline";

							}


						}
						if(appStatus.contains("No applications installed")){
							apiReporter.apiNewHeading("Applications are not installed message at DC UI  : "+appStatus);
							isUninstalled = true;
							System.out.println("Apps are Uninstalled from Device");
						}
						else{
							isUninstalled = false;
							apiReporter.apiNewHeading("Applications are installed  for device in DC Ui ");
							System.out.println("Apps are not Uninstalled from Device");
						}
						if(valueProp[k].equals("false")){

							if(!isRebooted && !isUninstalled){
								isEventSuccessful=true;
								strActualResult="Application Reboot and UnInstallation did not happened";
								apiReporter.apiPassBlock("Device did not got rebooted and apps are not unInstalled from device");
							}
							else{
								isEventSuccessful=false;
								strActualResult="Application Reboot and UnInstallation did happened";
								apiReporter.apiErrorBlock("Device got rebooted and apps are Installed in device");
							}

						}

						if(valueProp[k].equals("true")){
							if (isRebooted)
							{
								for(int iwaitcounter=0; iwaitcounter<=10; iwaitcounter++) 
								{
									PerformAction("browser", Action.Refresh);		
									PerformAction("lnkShowDetails", Action.Click);
									deviceStatus = GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text"); 
									if (deviceStatus.contains("Available"))
									{   
										isAvailable=true;
										apiReporter.apiNewHeading("Device got Online again. current device status "+deviceStatus);
										break;

									}
									else{
										isAvailable=false;
									}

								}


							}
							if(isAvailable && isUninstalled){
								isEventSuccessful=true;
								strActualResult="device got rebooted and Apps are Uninstalled from Device";
								apiReporter.apiPassBlock("Device got rebooted and apps got uninstalled from device");

							}
							else{
								isEventSuccessful=false;
								strActualResult="device did not got rebooted and Apps are not Uninstalled from Device";
								apiReporter.apiErrorBlock("Device did not  got rebooted and apps did not uninstalled from device");
							}

						}

						reporter.ReportStep("Check if reset with reboot and apps unInstallation of device is: "+valueProp[k], "reset of device with reboot and unInstallation should be: "+valueProp[k], strActualResult, isEventSuccessful);
					}
					else{
						apiReporter.apiErrorBlock("Getting Error in API respose");
					}




				}


				//************************************************Script Ends here for  reboot,unInstallAll=true,and reboot,unInstallAll=False************
				System.out.println("************************************************Script Ends here for  reboot,unInstallAll=true,and reboot,unInstallAll=False************");
				System.out.println();
				System.out.println();
				System.out.println();
				//************************************************Script Starts here for reset case reboot=true and unInstallAll=true************
				System.out.println("************************************************Script Starts here for reset case reboot=true unInstallAll=false and reboot=false,unInstall=true************");

				for (int l=0; l<=1; l++){
					isEventSuccessful=verifyAppInstalledOnDevice("Trust Browser");
					if(!isEventSuccessful){
						installTrustBrowserAppOnDevice();
					}
					if(valueProp[l].equals("true")){
						unInstallValue="false";
					}
					else{
						unInstallValue="true";
					}
					apiReporter.apiHeading2(" Reset Device with reboot "+valueProp[l]+" and unInstallAll as "+unInstallValue);

					//Create the curl command to update user details with id
					component="Device";
					parameterList="verb,action,Id,reboot,unInstallAll";
					valueList="action,reset,"+deviceID+","+valueProp[l]+","+unInstallValue; //declaration and intialization of curl command varaiables.
					command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST" ,"");

					//adding heading in APIOverallComparison HTML for the curl command created
					apiReporter.apiNewHeading(command);

					// Step : Execute curl command to get JSON response of the request
					jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.

					// Step : Verifying getting success in response
					apiReporter.apiNewHeading("Json Response of Query:"+jsonResponse);
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




					//***********code for getting expected starts here***************************************
					if(isEventSuccessfulRecordCount){


						PerformAction(dicOR.get("txtNoAppMsg_DeviceDetailsPage"), Action.WaitForElement,"20");
						PerformAction(dicOR.get("lnkShowDetails"), Action.WaitForElement,"20");


						for(int iwaitcounter=0; iwaitcounter<=5; iwaitcounter++) 
						{
							isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
							deviceStatus = GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text"); 
							System.out.println(deviceStatus);
							PerformAction("browser", Action.Refresh);		
							if (deviceStatus.contains("Offline"))
							{
								isRebooted=true;
								strActualResult="Device went offline after reboot";
								apiReporter.apiNewHeading("Device got offline after reboot operation. current device status "+deviceStatus);
								//apiReporter.apiPassBlock("Device successfully rebooted after running API Query device status is "+deviceStatus);
								break;
							}
							else{

								isRebooted=false;
							}

						}
						String appStatus=GetTextOrValue(dicOR.get("txtNoAppMsg_DeviceDetailsPage"), "text");
						if(appStatus.contains("No applications installed")){
							apiReporter.apiNewHeading("Applications are not installed message at DC UI  : "+appStatus);
							isUninstalled = true;
							System.out.println("Apps are Uninstalled from Device");
						}
						else{
							isUninstalled = false;
							apiReporter.apiNewHeading("Applications are installed for device in DC Ui  "+appStatus);
							System.out.println("Apps are not Uninstalled from Device");
						}
						if(valueProp[l].equals("false") && unInstallValue.equals("true")){

							if(!isRebooted && isUninstalled){
								isEventSuccessful=true;
								strActualResult="Device did not Reboot and Applications uninstalled from device happened";
								apiReporter.apiPassBlock("Device did not Reboot and Applications uninstalled from device happened");
							}
							else{
								isEventSuccessful=false;
								strActualResult="Device Reboot false and Applications uninstalled true from device did not happened";
								apiReporter.apiErrorBlock("Device Reboot false and Applications uninstalled true from device did not happened");
							}

						}

						if(valueProp[l].equals("true") && unInstallValue.equals("false")){
							if (isRebooted)
							{
								for(int iwaitcounter=0; iwaitcounter<=10; iwaitcounter++) 
								{
									PerformAction("browser", Action.Refresh);		
									PerformAction("lnkShowDetails", Action.Click);
									deviceStatus = GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text"); 
									if (deviceStatus.contains("Available"))
									{   
										isAvailable=true;
										apiReporter.apiNewHeading("Device got Online again. current device status "+deviceStatus);
										break;

									}
									else{
										isAvailable=false;
									}

								}


							}
							if(isAvailable && !isUninstalled){
								isEventSuccessful=true;
								strActualResult="device got rebooted and Apps are not Uninstalled from Device";
								apiReporter.apiPassBlock("Device got rebooted and apps are not uninstalled from device");

							}
							else{
								isEventSuccessful=false;
								strActualResult="device did not got rebooted and Apps are  Uninstalled from Device";
								apiReporter.apiErrorBlock("device did not got rebooted and Apps are  Uninstalled from Device");
							}

						}

						reporter.ReportStep("Check if reset with reboot : "+valueProp[l]+" and unInstallAll: "+unInstallValue,"reset of device with reboot as: "+valueProp[l]+" and unInstallAll as:"+unInstallValue+" should be happen", strActualResult, isEventSuccessful);
					}
					else{
						apiReporter.apiErrorBlock("Getting Error in API respose");
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

	}

}
