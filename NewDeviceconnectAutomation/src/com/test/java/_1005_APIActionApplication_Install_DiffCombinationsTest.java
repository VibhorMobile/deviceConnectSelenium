package com.test.java;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.lang.Math.*;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
import com.sun.jna.platform.win32.COM.Dispatch.ByReference;

import java.util.ArrayList;

import org.openqa.jetty.jetty.servlet.Default;


/*
 * Author : Jaishree Patidar
 * Creation Date: 11 May 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have atleast one online available android device
 * Admin user can only be used
 */

public class _1005_APIActionApplication_Install_DiffCombinationsTest extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	String jsonResponse=""; //declaration of string to capture JSON Response of the API request.
	String component="Application",parameterList="",valueList="";
	String command="";
	public Map<String,String> AppAddedMap = new HashMap<String,String>();
	public Map<String,String> appIdMap = new HashMap<String,String>();
	String installedAppsList;
	Boolean value;
	Object[] values = new Object[2];
	boolean alreadyInstalledFlag=false;
	String Platform[] ={"Android"}; //*********Multi-version app uploaded only for Android
	String appId,deviceId,appName,deviceName;
	String serverIP=dicCommon.get("ApplicationURL");
	String previousAppVersion,laterAppVersion;
	int seq;
	//Creating enum for various cases to be covered
	public enum Scenarios {
		DOWNGRADE, UPGRADE, ALREADYEXISTS
	}
	
	
	public void executeInstallAppCurlCommand(String deviceId,String appId)
	{
		//creating curl command
		parameterList="verb,action,Id,deviceid,wait,force";
		valueList="action,install,"+appId+","+deviceId+",true,true"; //declaration and intialization of curl command varaiables.
		command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST" ,"");	//Creates a curl command with component, parameterList and valueList.
		
		//adding heading in APIOverallComparison HTML for the curl command created
		apiReporter.apiNewHeading(command);
		
		// Step : Execute curl command to get JSON response of the request
		jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
		
		apiReporter.apiNewHeading(jsonResponse);
		
		// Step : Verifying record count is equal to 1 as using Id as a parameter
		apiMethods.apiVerifyGettingSuccessMessage(jsonResponse);
		
		if (!(jsonResponse.indexOf("\"isSuccess\":false") > 0))
		{
			for(int iwaitcounter=0; iwaitcounter<=25; iwaitcounter++) 
			{
				PerformAction("browser", Action.Refresh);
				installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
				System.out.println(installedAppsList);
				
				if (installedAppsList.indexOf(appName) > -1)
					break;
			}
        }
	}
		
	public final void testScript() throws IOException
	{
		try
		{
			apiReporter.apiScriptHeading("_1005_APIActionApplication_Install_DiffCombinationsTest");
			//Adding heading for the Script in API Overall Comparison HTML file
					//apiReporter.apiScriptHeading("Action Application");
					
					
			/*-------------------------------------------------------------------------------------------------------------------------------------
			*********************************Pre-condition: Uploading various version of Cur Music App *********************************************
			---------------------------------------------------------------------------------------------------------------------------------------*/
			
			String[] appPaths= {dicConfig.get("Artifacts")+"\\CurMusicAndroid-89.apk",
					dicConfig.get("Artifacts")+"\\CurMusicAndroid-97.apk",
					dicConfig.get("Artifacts")+"\\CurMusicAndroid-101.apk"}; 
			
			for(String uploadApp : appPaths)
			{
				String component= "Application", parameterList="verb,wait", valueList="add,true", formTag="upload=@"+uploadApp; //declaration and intialization of curl command varaiables.
				String jsonResponse; //declaration of string to capture JSON Response of the API request.
				String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", formTag);	//Creates a curl command with component, parameterList and valueList.
				
				//adding heading in APIOverallComparison HTML for the curl command created
				apiReporter.apiNewHeading(command);
				
				// Step : Execute curl command to get JSON response of the request
				jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
				
				// Step : Verifying record count is equal to 1 as using Id as a parameter
				
				apiMethods.apiVerifyGettingSuccessMessage(jsonResponse);
				
				// Step : Creating expected HashMap from the JSON Response
				AppAddedMap=apiMethods.getKeyValuePair(jsonResponse); //getting key value pair from the JSON response in a HashMap
				uploadApp=uploadApp.replace(dicConfig.get("Artifacts")+"\\", "");
				appIdMap.put(uploadApp.trim(), AppAddedMap.get("id"));
			}
			System.out.println(appIdMap);
			apiReporter.apiAddBlock("Pre-condition Completed: uploaded Cur music verious version apps");
			
			//-------------------------------------------------------------------------------------------------------------------------------------------
			//************************************************Pre-condition code Ends Here**********************************************************************************
			//---------------------------------------------------------------------------------------------------------------------------------------------------------
			
			/*---------------------------------------------------------------------------------------------------------------------------------------------------------
			****************************Code to create expected Result Starts here *****************************************************************
			---------------------------------------------------------------------------------------------------------------------------------------------------------*/
			
			// Step 1 : login to deviceConnect with valid user credentials.
			isEventSuccessful = Login();
						
				int j=0;
				reporter.ReportStep("<b>For "+Platform[j]+"- install diff Combinations</b>", "", "", true);	
				apiReporter.apiNewHeading("<b>For "+Platform[j]+"- install diff Combinations</b>");
				
				isEventSuccessful=GoToDevicesPage(); // Navigates to Device List page
				
				isEventSuccessful=selectPlatform(Platform[j]); //apply filter for Platform
				isEventSuccessful=selectStatus("Available");	//apply status filter as available	
	
				values=GoTofirstDeviceDetailsPage();
				isEventSuccessful=(boolean) values[0];
				deviceName=values[1].toString();
							
				deviceId=getAppGUID();
			
			//-------------------------------------------------------------------------------------------------------------------------------------------
			//************************************************Code Different Scenarios of Install App Starts here**********************************************************************************
			//---------------------------------------------------------------------------------------------------------------------------------------------------------
			
			
			//Initial Setup- Installing Highest Version App
			appName="CÜR Music";
			appId=appIdMap.get("CurMusicAndroid-101.apk");
			
			executeInstallAppCurlCommand(deviceId,appId);
			
			  
			for (Scenarios scene : Scenarios.values())
			{
				System.out.println("Code Started for "+scene);
				//checking if the app with same name already exists
				installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
				System.out.println(installedAppsList);
				
				if (!(installedAppsList.indexOf(appName) ==-1))
				{
					alreadyInstalledFlag=true;
					
						installedAppsList=installedAppsList.replace("Name Version Build","");
						String appsInstallSeq[] =installedAppsList.split("Uninstall");
						System.out.println(appsInstallSeq);
						int seq=0;
						for(String app : appsInstallSeq)
						{
							if (app.indexOf(appName) >0)
								break;
							else
								seq++;
						}
						previousAppVersion=(appsInstallSeq[seq].replace(appName, "")).trim();
						System.out.println("App Version before starting Downgrade code " + previousAppVersion);
						if (previousAppVersion.equals("00.00.103 102"))
						{
							System.out.println("app Installed Successfully");
						}
				}
				switch(scene)
				{
			     
			        case DOWNGRADE: 
			        	apiReporter.apiHeading2("Application downgrade");
			        	 if (alreadyInstalledFlag)
			        	 {
			        		appId=appIdMap.get("CurMusicAndroid-89.apk");
			     			
			        		executeInstallAppCurlCommand(deviceId,appId);
			     			installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
			     			System.out.println(installedAppsList);
			     			
			     			if (installedAppsList.indexOf(appName) > -1)
			     			{
			     				apiReporter.apiPassBlock("Application installed successfully.");
			     				isEventSuccessful=true;
			     			}			
			     			else
			     			{
			     				apiReporter.apiErrorBlock("Application not installed successfully.");
			     				isEventSuccessful=false;
			     			}
			     			
			     			
								installedAppsList=installedAppsList.replace("Name Version Build","");
								String appsInstallSeq[] =installedAppsList.split("Uninstall");
								System.out.println(appsInstallSeq);
								int seq=0;
								for(String app : appsInstallSeq)
								{
									if (app.indexOf(appName) >0)
										break;
									else
										seq++;
								}
								laterAppVersion=(appsInstallSeq[seq].replace(appName, "")).trim();
								
								if (laterAppVersion.equals("00.00.91 90"))
								{
									apiReporter.apiPassBlock("Application successfully downgraded from " + previousAppVersion +" to " +laterAppVersion);
								}else
								{
									apiReporter.apiErrorBlock("Application not able to successfully downgraded previous version " + previousAppVersion +" to latest version" +laterAppVersion);
								}
								
							}
							installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
							installedAppsList=installedAppsList.replace("Uninstall", "<br>");
							System.out.println(installedAppsList);
							reporter.ReportStep("Get the installed app list on device before calling API request", "If the app is present uninstall it.", "Installed list is "+installedAppsList, true);
							apiReporter.apiAddBlock("Before API Request Installed app list = <br>"+installedAppsList);
				            System.out.println("Positive Scenario");
				            previousAppVersion=laterAppVersion;
				            break;
			            	                    
			        case UPGRADE:
			        	apiReporter.apiHeading2("Application Upgrade");
			        	 if (alreadyInstalledFlag)
			        	 {
			        		appId=appIdMap.get("CurMusicAndroid-97.apk");
			        		
			        		executeInstallAppCurlCommand(deviceId,appId);
			     			installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
			     			System.out.println(installedAppsList);
			     			
			     			if (installedAppsList.indexOf(appName) > -1)
			     			{
			     				apiReporter.apiPassBlock("Application installed successfully.");
			     				isEventSuccessful=true;
			     			}			
			     			else
			     			{
			     				apiReporter.apiErrorBlock("Application not installed successfully.");
			     				isEventSuccessful=false;
			     			}
			     			
			     			
								installedAppsList=installedAppsList.replace("Name Version Build","");
								String appsInstallSeq[] =installedAppsList.split("Uninstall");
								System.out.println(appsInstallSeq);
								int seq=0;
								for(String app : appsInstallSeq)
								{
									if (app.indexOf(appName) >0)
										break;
									else
										seq++;
								}
								laterAppVersion=(appsInstallSeq[seq].replace(appName, "")).trim();
								
								if (laterAppVersion.equals("00.00.99 98"))
								{
									apiReporter.apiPassBlock("Application successfully upgraded from " + previousAppVersion +" to " +laterAppVersion);
								}else
								{
									apiReporter.apiErrorBlock("Application not able to successfully upgraded previous version " + previousAppVersion +" to latest version" +laterAppVersion);
								}
							
							}
							installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
							installedAppsList=installedAppsList.replace("Uninstall", "<br>");
							System.out.println(installedAppsList);
							reporter.ReportStep("Get the installed app list on device before calling API request", "If the app is present uninstall it.", "Installed list is "+installedAppsList, true);
							apiReporter.apiAddBlock("Before API Request Installed app list = <br>"+installedAppsList);
				            System.out.println("Positive Scenario");
				            previousAppVersion=laterAppVersion;
			        	break;
			        	
			        case ALREADYEXISTS: 
			        	apiReporter.apiHeading2("Application Already Exists");
			        	 if (alreadyInstalledFlag)
			        	 {
			        		appId=appIdMap.get("CurMusicAndroid-97.apk");
			        		
			        		executeInstallAppCurlCommand(deviceId,appId);
			     			installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
			     			System.out.println(installedAppsList);
			     			
			     			if (installedAppsList.indexOf(appName) > -1)
			     			{
			     				apiReporter.apiPassBlock("Application installed successfully.");
			     				isEventSuccessful=true;
			     			}			
			     			else
			     			{
			     				apiReporter.apiErrorBlock("Application not installed successfully.");
			     				isEventSuccessful=false;
			     			}
			     			
			     			
								installedAppsList=installedAppsList.replace("Name Version Build","");
								String appsInstallSeq[] =installedAppsList.split("Uninstall");
								System.out.println(appsInstallSeq);
								int seq=0;
								for(String app : appsInstallSeq)
								{
									if (app.indexOf(appName) >0)
										break;
									else
										seq++;
								}
								laterAppVersion=(appsInstallSeq[seq].replace(appName, "")).trim();
								
								if (laterAppVersion.equals("00.00.99 98"))
								{
									apiReporter.apiPassBlock("Application successfully installed from " + previousAppVersion +" to " +laterAppVersion);
								}else
								{
									apiReporter.apiErrorBlock("Application not able to successfully install already installed app. previous version " + previousAppVersion +" to latest version" +laterAppVersion);
								}
								
							}
							installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
							installedAppsList=installedAppsList.replace("Uninstall", "<br>");
							System.out.println(installedAppsList);
							reporter.ReportStep("Get the installed app list on device before calling API request", "If the app is present uninstall it.", "Installed list is "+installedAppsList, true);
							apiReporter.apiAddBlock("Before API Request Installed app list = <br>"+installedAppsList);
				            previousAppVersion=laterAppVersion;
			            break;
			                    
			        default:
			            System.out.println("Midweek days are so-so.");
			            break;
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
