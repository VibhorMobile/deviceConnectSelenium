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
import com.common.utilities.GenericLibrary.Action;


public class _1008_APIActionDeviceTest_Reboot extends ScriptFuncLibrary
{
	
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	private boolean isRebooted=false;
	
	Object[] values = new Object[2];
	String jsonResponse=""; //declaration of string to capture JSON Response of the API request.
	String component="Device",parameterList="",valueList="";
	String command="",deviceName,deviceID,deviceStatus;
	
	
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
		try
		{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1008_APIActionDeviceTest_Reboot");
			
			isEventSuccessful = Login();
	
			String[] deviceType= {"Android","iOS"}; //Declaring an array to run for Android and iOS app
			String[] deviceStatusArr= {"Available","Available"};
			
			/*This loop run for iOS first and then Android*/
			for(int j=0; j<=1; j++)
			{
				apiReporter.apiNewHeading(deviceType[j] + " Device");
				isEventSuccessful =GoToDevicesPage();
						
				isEventSuccessful = selectPlatform(deviceType[j]);
				
				
				isEventSuccessful = selectStatus(deviceStatusArr[j]);
				values=GoTofirstDeviceDetailsPage(); 
				
				isEventSuccessful=(boolean) values[0];
				deviceName = (String) values[1];
				deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];
				
				
				for (int k=0; k<=1; k++)
				{
									
					if(k==1)
					{
						createAndexecuteCurlCommand(deviceID, "retain");
						PerformAction("browser", Action.Refresh);	
						if (!(jsonResponse.indexOf("\"isSuccess\":false") > 0))
						{
							for(int iwaitcounter=0; iwaitcounter<=5; iwaitcounter++) 
							{
								PerformAction("browser", Action.Refresh);		
								isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
								deviceStatus = GetTextOrValue(dicOR.get("eleDeviceStatus_UsingDeviceName"), "text"); 
								if (deviceStatus.contains("In Use"))
									break;
							}
				        }
					}
					
					//Reboot code starts here
				
					isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
					deviceStatus = GetTextOrValue(dicOR.get("eleDeviceStatus_UsingDeviceName"), "text"); 
					
					apiReporter.apiHeading2("Device - Action Device- Reboot (" +deviceStatus +")");
					apiReporter.apiAddBlock("Device Status before running API Query is "+deviceStatus);
					
					createAndexecuteCurlCommand(deviceID, "reboot");
					
					if (!(jsonResponse.indexOf("\"isSuccess\":false") > 0))
					{
						for(int iwaitcounter=0; iwaitcounter<=5; iwaitcounter++) 
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
			        }
					
					if (isRebooted)
					{
						for(int iwaitcounter=0; iwaitcounter<=10; iwaitcounter++) 
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