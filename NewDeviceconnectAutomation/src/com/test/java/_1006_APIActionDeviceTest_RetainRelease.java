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


public class _1006_APIActionDeviceTest_RetainRelease extends ScriptFuncLibrary
{
	
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	
	Object[] values = new Object[2];
	String jsonResponse=""; //declaration of string to capture JSON Response of the API request.
	String component="Device",parameterList="",valueList="";
	String command="",deviceName,deviceID,deviceStatus;
	
	
	public void createAndexecuteCurlCommand(String deviceId, String strAction, String userType)
	{
		
		
		//creating curl command
		parameterList="verb,action,Id";
		valueList="action,"+strAction+","+deviceId; //declaration and intialization of curl command varaiables.
		//command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST" ,"");	//Creates a curl command with component, parameterList and valueList.
		command= apiMethods.createCurlCommand(component , parameterList , valueList, "POST", "", "", "", userType);
		//adding heading in APIOverallComparison HTML for the curl command created
		apiReporter.apiNewHeading(command);
		
		// Step : Execute curl command to get JSON response of the request
		jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
		
		apiReporter.apiNewHeading(jsonResponse);
		
		// Step : Verifying record count is equal to 1 as using Id as a parameter
		//apiMethods.apiVerifyGettingSuccessMessage(jsonResponse);
		
	}
	
	public void createAndexecuteCurlCommand(String deviceId, String strAction)
	{
		createAndexecuteCurlCommand(deviceId, strAction, "");
		
	}
	
	public final void testScript() throws InterruptedException, IOException
	{
		try
		{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("_1006_APIActionDeviceTest_RetainRelease");
			isEventSuccessful = Login();
	
			String[] deviceType= {"iOS","Android"}; //Declaring an array to run for Android and iOS app
			
			/*This loop run for iOS first and then Android*/
			for(int j=0; j<=1; j++)
			{
				apiReporter.apiNewHeading(deviceType[j] + " Device");
				isEventSuccessful =GoToDevicesPage();
						
				isEventSuccessful = selectPlatform(deviceType[j]);
				if(deviceType[j].equals("Android"))
				{
					isEventSuccessful = PerformAction("txtDeviceSearch", Action.Clear);
					PerformAction("Browser", Action.Refresh);
				}
				
				isEventSuccessful = selectStatus("Available");
				values=GoTofirstDeviceDetailsPage(); 
				
				isEventSuccessful=(boolean) values[0];
				deviceName = (String) values[1];
				deviceID =  GenericLibrary.driver.getCurrentUrl().split("\\#")[1].split("\\/")[3];
				
				//Retain code starts here
				//adding heading in APIOverallComparison HTML  for the AppType, Property and Value used in API Request
				apiReporter.apiHeading2("Device - Action Device- Retain");
				
				isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
				deviceStatus = GetTextOrValue(dicOR.get("eleDeviceStatus_UsingDeviceName"), "text"); 
				apiReporter.apiAddBlock("Device Status before running API Query is "+deviceStatus);
				
				
				if (j==0) //code for retaining a device with same or other user for diff scenarios for Release
				{
					createAndexecuteCurlCommand(deviceID, "retain");
				}else
				{
					createAndexecuteCurlCommand(deviceID, "retain","other");
				}			
				
				
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
				
				deviceStatus = GetTextOrValue(dicOR.get("eleDeviceStatus_UsingDeviceName"), "text"); 
				if (deviceStatus.contains("In Use"))
					apiReporter.apiPassBlock("Device successfully retained after running API Query device status is "+deviceStatus);
				else
					apiReporter.apiErrorBlock("Unable to retain device. Device status is "+deviceStatus + " expected was conatining In Use");
				apiReporter.apiAddBlock("Device Status before running API Query is "+deviceStatus);
				
				//Retaining an already retained device by same user code starts here
				
				//apiReporter.apiAddBlock("Retaining an already retained device by same user");
				if (j==0)
				{
					apiReporter.apiHeading2("Device - Action Device- Retain (Retaining an already retained device by same user) ");
					createAndexecuteCurlCommand(deviceID, "retain");
				
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
					
					deviceStatus = GetTextOrValue(dicOR.get("eleDeviceStatus_UsingDeviceName"), "text"); 
					if (deviceStatus.contains("In Use"))
						apiReporter.apiPassBlock("Device successfully retained already retained device. After running API Query device status is "+deviceStatus);
					else
						apiReporter.apiErrorBlock("Unable to retain device already retained device. Device status is "+deviceStatus + " expected was conatining In Use");
			}
			//Release code Starts here
			if (j==0)
			{
				apiReporter.apiHeading2("Device - Action Device- Release (Retained by same user)");
			}else
			{
				apiReporter.apiHeading2("Device - Action Device- Release (Retained by another user)");
			}
			
			deviceStatus = GetTextOrValue(dicOR.get("eleDeviceStatus_UsingDeviceName"), "text"); 
			apiReporter.apiAddBlock("Device Status before running API Query is "+deviceStatus);
			createAndexecuteCurlCommand(deviceID, "release");
			
			if (!(jsonResponse.indexOf("\"isSuccess\":false") > 0))
			{
				for(int iwaitcounter=0; iwaitcounter<=5; iwaitcounter++) 
				{
					PerformAction("browser", Action.Refresh);		
					isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
					deviceStatus = GetTextOrValue(dicOR.get("eleDeviceStatus_UsingDeviceName"), "text"); 
					if (deviceStatus.contains("Available"))
						break;
				}
	        }
			
			deviceStatus = GetTextOrValue(dicOR.get("eleDeviceStatus_UsingDeviceName"), "text"); 
			if (deviceStatus.contains("Available"))
				apiReporter.apiPassBlock("Device successfully released after running API Query device status is "+deviceStatus);
			else
				apiReporter.apiErrorBlock("Unable to release device. Device status is "+deviceStatus + " expected was Available");
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