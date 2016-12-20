package com.test.java;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.ApplicationLibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

import net.sourceforge.htmlunit.corejs.javascript.ast.SwitchCase;

import com.common.utilities.GenericLibrary.Action;


public class _1009_APIActionDeviceTest_Reset_AdministrativeAreaSetting extends ScriptFuncLibrary
{
	
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	private boolean isRebooted,isRebootedOptionSelected;
	private boolean isUninstallAll,isUninstallAllOptionSelected;
	String installedAppsList;
	
	Object[] values = new Object[2];
	String jsonResponse=""; //declaration of string to capture JSON Response of the API request.
	String component="Device",parameterList="",valueList="";
	String command="",deviceName,deviceID,appStatus,deviceStatus;
	
	
	public void createAndexecuteCurlCommand(String deviceId, String strAction)
	{
		//creating curl command
		parameterList="verb,action,Id";
		valueList="action,"+strAction+","+deviceId; //declaration and intialization of curl command varaiables.
		command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST" ,"");	//Creates a curl command with component, parameterList and valueList.
		
		//adding heading in APIOverallComparison HTML for the curl command created
		apiReporter.apiNewHeading(command);
		
		// Step : Execute curl command to get JSON response of the request
		jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
		
		apiReporter.apiNewHeading(jsonResponse);
		
		// Step : Verifying record count is equal to 1 as using Id as a parameter
		//apiMethods.apiVerifyGettingSuccessMessage(jsonResponse);
		
	}
	
	
	public final void testScript() throws InterruptedException, IOException
	{
		
		String[] deviceType= {"Android","iOS"}; //Declaring an array to run for Android and iOS app
		int j=-1;
		try{
			
		
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1009_APIActionDeviceTest_Reset_AdministrativeAreaSetting");
			
			
			
			isEventSuccessful = Login(); // Login into dC with Admin credentials
			
			
			for(int k=0; k<=3; k++)
			{
				
				isEventSuccessful=GoToSystemPage();  // Navigates to System page
				
				//Setting different combinations of uninstall All and Reboot administrative settings
				switch(k)
				{
					case 0:
						//Case 1: Uninstall -ON & Reboot- ON
						apiReporter.apiHeading2("Case 1: Uninstall -ON & Reboot- ON");
						PerformAction(dicOR.get("chkResetDeviceState_System"), Action.SelectCheckbox);
						PerformAction(dicOR.get("chkUninstallAll_System"), Action.SelectCheckbox);
						PerformAction(dicOR.get("chkRebootDevice_System"), Action.SelectCheckbox);
						isRebootedOptionSelected = true;
						isUninstallAllOptionSelected = true;
						break;
					case 1:
						//Case 2: Uninstall -OFF & Reboot- ON
						apiReporter.apiHeading2("Case 2: Uninstall -OFF & Reboot- ON");
						PerformAction(dicOR.get("chkResetDeviceState_System"), Action.SelectCheckbox);
						PerformAction(dicOR.get("chkUninstallAll_System"), Action.DeSelectCheckbox);
						PerformAction(dicOR.get("chkRebootDevice_System"), Action.SelectCheckbox);
						isRebootedOptionSelected = true;
						isUninstallAllOptionSelected = false;
						break;
					case 2:
						//Case 3: Uninstall -ON & Reboot- OFF
						apiReporter.apiHeading2("Case 3: Uninstall -ON & Reboot- OFF");
						PerformAction(dicOR.get("chkResetDeviceState_System"), Action.SelectCheckbox);
						PerformAction(dicOR.get("chkUninstallAll_System"), Action.SelectCheckbox);
						PerformAction(dicOR.get("chkRebootDevice_System"), Action.DeSelectCheckbox);
						isRebootedOptionSelected = false;
						isUninstallAllOptionSelected = true;
						break;
					case 3:
						//Case 4: Uninstall -OFF & Reboot- OFF
						apiReporter.apiHeading2("Case 4: Uninstall -OFF & Reboot- OFF");
						PerformAction(dicOR.get("chkResetDeviceState_System"), Action.SelectCheckbox);
						PerformAction(dicOR.get("chkUninstallAll_System"), Action.DeSelectCheckbox);
						PerformAction(dicOR.get("chkRebootDevice_System"), Action.DeSelectCheckbox);
						isRebootedOptionSelected = false;
						isUninstallAllOptionSelected = false;
						break;
						
				}
				
	
				//This loop run for iOS first and then Android
				for(j=0; j<=1; j++)
				{
					isEventSuccessful=GoToSystemPage();  // Added this step as it was clicking on device link but unable to go to Devices page
					
					isEventSuccessful =GoToDevicesPage();  //Navigates to Devices page
					
					//Applying filters
					isEventSuccessful = selectPlatform(deviceType[j]);
					isEventSuccessful = selectStatus("Available");
					
					//Selecting first device
					values=GoTofirstDeviceDetailsPage(); 
					isEventSuccessful=(boolean) values[0];
					deviceName = (String) values[1];
					deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];
					
					PerformAction("//table[@class='table data-grid']/tbody/tr/td[@title='Trust Browser']/../td[@class='btn-column']/button[contains(text(),'Install')]",Action.Click);
					Thread.sleep(20000);
					PerformAction("browser", Action.Refresh);
					
					installedAppsList=apiMethods.getInstalledApps();
					
					apiReporter.apiAddBlock("Applications installed are "+installedAppsList);
					
					createAndexecuteCurlCommand(deviceID, "reset");
					
					//Verify device is rebooted if Reboot option is checked in System -Administrative Settings
					if(isRebootedOptionSelected)
					{
						isRebooted=false;
						if (!(jsonResponse.indexOf("\"isSuccess\":false") > 0))
						{
							for(int iwaitcounter=0; iwaitcounter<=10; iwaitcounter++) 
							{
								PerformAction("browser", Action.Refresh);		
								isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
								deviceStatus = GetTextOrValue(dicOR.get("eleDeviceStatus_UsingDeviceName"), "text"); 
								if (deviceStatus.contains("Offline"))
								{
									isRebooted=true;
									apiReporter.apiPassBlock("Device successfully rebooted after running API Query device status is "+deviceStatus);
									break;
								}
									
							}
							if (!isRebooted)
							{
								apiReporter.apiErrorBlock("Device is not rebooted whereas it is set as true in Reset device state settings");
							}
							else
							{
								Thread.sleep(20000);
								for(int iwaitcounter=0; iwaitcounter<=5; iwaitcounter++) 
								{
									PerformAction("browser", Action.Refresh);		
									isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
									deviceStatus = GetTextOrValue(dicOR.get("eleDeviceStatus_UsingDeviceName"), "text"); 
									if (deviceStatus.contains("Available"))
									{
										apiReporter.apiPassBlock("Device got Online again. current device status "+deviceStatus);
										break;
									}							
								}
							}
								
				        }
					}
					else
					{
						PerformAction("browser", Action.Refresh);		
						isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
						deviceStatus = GetTextOrValue(dicOR.get("eleDeviceStatus_UsingDeviceName"), "text"); 
						if (!deviceStatus.equals("Available"))
							apiReporter.apiErrorBlock("Device is not rebooted. current device status "+deviceStatus);
						else
							apiReporter.apiPassBlock("Device is not rebooted. current device status "+deviceStatus);
						
					}
	
					//Verify if all the applications are uninstalled if uninstallAll option is checked in System -Administrative Settings
					if(isUninstallAllOptionSelected)
					{
						isUninstallAll=false;
						if (!(jsonResponse.indexOf("\"isSuccess\":false") > 0))
						{
							for(int iwaitcounter=0; iwaitcounter<=5; iwaitcounter++) 
							{
								PerformAction("browser", Action.Refresh);		
								//isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
								appStatus = GetTextOrValue(dicOR.get("txtNoAppMsg_DeviceDetailsPage"), "text"); 
								if (appStatus.contains("No applications installed"))
								{
									isUninstallAll=true;
									apiReporter.apiPassBlock("Apps uninstalled successfully. Now "+appStatus);
									break;
								}
									
							}
							if (!isUninstallAll)
							{
								apiReporter.apiErrorBlock("Unable to uninstalled all apps. Current Status is  "+appStatus);
							}
								
				        }
					}
					else
					{
							if (!(jsonResponse.indexOf("\"isSuccess\":false") > 0))
							{
								for(int iwaitcounter=0; iwaitcounter<=10; iwaitcounter++) 
								{
									PerformAction("browser", Action.Refresh);		
									installedAppsList=apiMethods.getInstalledApps();
									if (installedAppsList.contains("Trust Browser"))
									{
										isUninstallAll=true;
										apiReporter.apiPassBlock("Apps not uninstalled as uninstall all option is uncheked. Now "+installedAppsList);
										break;
									}
										
								}
							}
							if (!isUninstallAll)
							{
								apiReporter.apiErrorBlock("Uninstalled all apps. Whereas uninstall all option is unchecked. Installed app list is  "+appStatus);
							}
					}	
				}
			}
		}catch(ArrayIndexOutOfBoundsException ex)
		{
			apiReporter.apiErrorBlock(deviceType[j] +" device not available on the server... Error Message "+ex.getMessage());
		}
		catch(Exception ex)
		{
			apiReporter.apiErrorBlock("Something went wrong... Error Message "+ex.getMessage());
			ex.printStackTrace();
		}finally
		{
			System.out.println("finally block");
			PerformAction(dicOR.get("chkResetDeviceState_System"), Action.DeSelectCheckbox);
			PerformAction(dicOR.get("chkUninstallAll_System"), Action.DeSelectCheckbox);
			PerformAction(dicOR.get("chkRebootDevice_System"), Action.DeSelectCheckbox);

			//Adds a step Provides API Overall Comparison file link in DC Selenium Execution Report
					apiReporter.apiOverallHtmlLinkStep();
		}
	}
}