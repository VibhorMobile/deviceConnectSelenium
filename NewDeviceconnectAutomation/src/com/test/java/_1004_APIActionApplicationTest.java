package com.test.java;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.lang.Math.*;
import java.text.ParseException;
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


/*
 * Author : Jaishree Patidar
 * Creation Date: 31 March 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have Trust Browser application for android and iOS devices
 * Admin user can only be used
 */

public class _1004_APIActionApplicationTest extends ScriptFuncLibrary {

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
	String Platform[] ={"Android","iOS"};
	String appId,deviceId,appName,deviceName;
	String serverIP=dicCommon.get("ApplicationURL");
	String latestEventName, latestApplication, eventDateTime;
	String[] forceArray={"true", "false"};
	
	/*public String getInstalledApps()
	{
		String installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
		int waitTime=0;
		while (installedAppsList.contains("Loading...") || waitTime==25)
		{ 
			waitTime++;
			installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
		}
		System.out.println(installedAppsList);
		return installedAppsList;
	}
	*/
	public void executeInstallCurlCommand(String deviceId,String appId,String forceFlag)
	{
		//creating curl command
		parameterList="verb,action,Id,deviceid,wait,force";
		valueList="action,install,"+appId+","+deviceId+",true,"+forceFlag; //declaration and intialization of curl command varaiables.
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
				/*installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
				int waitTime=0;
				while (installedAppsList.contains("Loading...") || waitTime==25)
				{ 
					waitTime++;
					installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
				}
				System.out.println(installedAppsList);*/
				
				installedAppsList=apiMethods.getInstalledApps();
				
				if (installedAppsList.indexOf(appName) > -1)
					break;
			}
        }
	}
		
	public final void testScript() throws IOException
	{
		
		try
		{	
			//Adding heading for the Script in API Overall Comparison HTML file
					apiReporter.apiScriptHeading("Action Application");
					
			/*---------------------------------------------------------------------------------------------------------------------------------------------------------
			****************************Code to create expected Result Starts here *****************************************************************
			---------------------------------------------------------------------------------------------------------------------------------------------------------*/
			
			// Step 1 : login to deviceConnect with valid user credentials.
			isEventSuccessful = Login();
			for (String forceVal : forceArray)
			{
				try
				{
						
					for(int j=0; j<=1; j++) //Running for Android and iOS device
					{
						reporter.ReportStep("<b>For "+Platform[j]+"- install</b>", "", "", true);	
						apiReporter.apiHeading2("<b>For "+Platform[j]+"- install -- force " +forceVal+ "</b>");
						
						//Get Available app ID
						isEventSuccessful=GoToApplicationsPage();
						
						isEventSuccessful=selectPlatform(Platform[j]);
						
						isEventSuccessful=searchDevice_DI("Trust Browser"); //Selecting Trust Browser as its Minimum OS Version is 6.0 for iOS and 2.2 for Android app.. to avoid getting error
						if (isEventSuccessful)
							strActualResult= "Search for an application on Application List page. Search string= Trust Browser";
						else
							strActualResult= "Unable to search for an application on Application List page. Search string= Trust Browser";
						reporter.ReportStep("Search for an application on Application list page", "Searched successfully", strActualResult, isEventSuccessful);
						
						try{
							
							values=GoToFirstAppDetailsPage();
							isEventSuccessful=(boolean) values[0];
							appName=values[1].toString();
							
							appId=getAppGUID();
							
							//Get Available Online Device
							isEventSuccessful=GoToDevicesPage(); // Navigates to Device List page
							
							isEventSuccessful=selectPlatform(Platform[j]); //apply filter for Platform
							isEventSuccessful=selectStatus("Available");	//apply status filter as available	
				
							values=GoTofirstDeviceDetailsPage();
							isEventSuccessful=(boolean) values[0];
							deviceName=values[1].toString();
										
							deviceId=getAppGUID();
							
							
							/*---------------------------------------------------------------------------------------------------------------------------------------------------------
							****************************Code to Install Application - POSITIVE Scenario Starts here *****************************************************************
							---------------------------------------------------------------------------------------------------------------------------------------------------------*/
				
							isEventSuccessful=PerformAction("browser","navigate","http://"+serverIP+"/#/Device/Detail/" + deviceId);
							if(isEventSuccessful)
							{
								strActualResult= "Navigate to Device Details page of the Device using GUID. ID= " + deviceId +" server used= " +serverIP;
							}
							else
							{
								strActualResult= "PerformAction---Navigate to URL. ID= " + deviceId +" server used= " +serverIP+ strErrMsg_GenLib;
							}
							reporter.ReportStep("Navigates to Device details page", "Navigates successfully", strActualResult, isEventSuccessful);
										
							//checking if the app with same name already exists , uninstall it
							//installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
							installedAppsList=apiMethods.getInstalledApps();
							System.out.println(installedAppsList);
							
							if (!(installedAppsList.indexOf(appName) ==-1))
							{
								installedAppsList=installedAppsList.replace("Name Version Build","");
								String appsInstallSeq[] =installedAppsList.split("Uninstall");
								System.out.println(appsInstallSeq);
								int seq=1;
								for(String app : appsInstallSeq)
								{
									if (app.indexOf(appName) >0)
										break;
									else
										seq++;
								}
								PerformAction("//*[@class='installed-apps-table-body']/tr["+seq+"]/td[4]/button" ,Action.Click);
								PerformAction(dicOR.get("Continuebutton_Uninstallapp_devciedetails") ,Action.Click);
								PerformAction("browser", Action.Refresh);
								
							}
							//installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
							installedAppsList=apiMethods.getInstalledApps();
							installedAppsList=installedAppsList.replace("Uninstall", "<br>");
							System.out.println(installedAppsList);
							reporter.ReportStep("Get the installed app list on device before calling API request", "If the app is present uninstall it.", "Installed list is "+installedAppsList, true);
							apiReporter.apiAddBlock("Before API Request Installed app list = <br>"+installedAppsList);
						
							executeInstallCurlCommand(deviceId,appId,forceVal);
				
							//installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
							installedAppsList=apiMethods.getInstalledApps();
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
							installedAppsList=installedAppsList.replace("Uninstall", ", ");	
							reporter.ReportStep("Get the installed app list on device after calling API request", "App should be available.", "Installed list is "+installedAppsList, isEventSuccessful);
							apiReporter.apiAddBlock("After API Request Installed app list = <br>"+installedAppsList);
							
				
							
							/*---------------------------------------------------------------------------------------------------------------------------------------
							****************************************Launch Code Starts here***********************************************************************
							 --------------------------------------------------------------------------------------------------------------------------------------*/
							try
							{
								
							
								if (forceVal.equals("true"))
								{
								 //Below code runs for Online/InUse device for Android
								//isEventSuccessful = PerformAction("txtDeviceSearch", Action.Clear);
								//PerformAction("Browser", Action.Refresh);
								//isEventSuccessful = PerformAction("chkPlatform_Devices", Action.SelectCheckbox);
									apiReporter.apiHeading2("<b>For "+Platform[j]+"- launch </b>");
									
									Object[] Values = ExecuteCLICommand("run",Platform[j],"","",deviceId,appName, "");
									isEventSuccessful = (boolean)Values[2];
									deviceName=(String)Values[3];
									if(isEventSuccessful)
									{
									   
									}
									else
									{
									  throw new RuntimeException("Could not connected a device");
									}
									
									/*try {
										Thread.sleep(50000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										continue;
									}*/
									/*
									// Store the current window handle
									String winHandleBefore = driver.getWindowHandle();
						
									// Perform the click operation that opens new window
						
									// Switch to new window opened
									for(String winHandle : driver.getWindowHandles()){
										if (!winHandle.equals(winHandleBefore))
											driver.switchTo().window(winHandle);
									}
						
									// Perform the actions on new window
									PerformAction("//span[@class='dc-icon-viewer-action-home']", Action.Click);*/
									
									// Continue with original browser (first window)
									
									//************************Code for getting actual result from API Request starts here *******************************************************************
									
									
									 Date dNow = new Date( );
								     SimpleDateFormat ft = new SimpleDateFormat ("M/dd/yyyy hh:mm:ss a");
								     String currentDateTime=ft.format(dNow);
								     System.out.println("Current Date: " + ft.format(dNow));
									
									reporter.ReportStep("<b>For "+Platform[j]+"- launch</b>", "", "", true);	
									apiReporter.apiNewHeading("<b>For "+Platform[j]+"- launch</b>");
									
									parameterList="verb,action,Id,deviceid";
									valueList="action,launch,"+appId+","+deviceId; //declaration and intialization of curl command varaiables.
									command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST" ,"");	//Creates a curl command with component, parameterList and valueList.
									
									//adding heading in APIOverallComparison HTML for the curl command created
									apiReporter.apiNewHeading(command);
									
									// Step : Execute curl command to get JSON response of the request
									jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
									
									apiReporter.apiNewHeading(jsonResponse);
									
									// Step : Verifying record count is equal to 1 as using Id as a parameter
									apiMethods.apiVerifyGettingSuccessMessage(jsonResponse);
									
									
									try {
									Thread.sleep(50000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										continue;
									}
									
									
									//wait(100);
									
									
									String sSscreenshotFile = reporter.SaveScreenShot();
									String sDesktopScreenshotFilename = reporter.SaveDeskTopScreen();
									apiReporter.apiAddBlock("<br><a href='" +dicConfig.get("ReportPath").toString() + "\\Main\\1004\\" +  sSscreenshotFile + "'>View Screenshot. </a><br> OR <br><a href='" +dicConfig.get("ReportPath").toString() + "\\Main\\1004\\" +  sDesktopScreenshotFilename + "'>View desktop screenshot.</a> ");
									
									/* Close the new window, if that window no more required
									driver.close();
						
									// Switch back to original browser (first window)
									driver.switchTo().window(winHandleBefore);*/
						
									// Add code to go to History Tab
									// get first rows Event *(2nd column) Application (5th column)  verify them to be "Application Launched" and "Trust Browser"
									
									//lnkHistory_DevcieIndexPage
									
									PerformAction("lnkHistory_DeviceDetailsPage", Action.Click);  // Click on History link available on Device Details page.
									if (!PerformAction("nohistorydisplaymsg", Action.Exist))  // Verifying that no history message is not displayed.
									{
										eventDateTime=GetTextOrValue(dicOR.get("eleDeviceHistoryTable_FirstRow").replace("__INDEX__", "1"), "text");
										System.out.println(eventDateTime);
										
										
										Date dateEvent=ft.parse(eventDateTime);
										
										
										//Getting value inside Event column in History table's First row
										latestEventName=GetTextOrValue(dicOR.get("eleDeviceHistoryTable_FirstRow").replace("__INDEX__", "2"), "text");
										//Getting value inside Appliaction column in Hitory table's First row
										latestApplication=GetTextOrValue(dicOR.get("eleDeviceHistoryTable_FirstRow").replace("__INDEX__", "5"), "text");
															
										
										if (latestEventName.equals("Application Launched")  && latestApplication.equals("Trust Browser") && dateEvent.after(dNow) )
										{
											apiReporter.apiPassBlock("Application launched successfully. <br> Event time="+eventDateTime+" Timestamp before API Request="+currentDateTime+ " Event="+latestEventName+" Application="+latestApplication);
											isEventSuccessful=true;
										}
										else
										{
											apiReporter.apiErrorBlock("Application not launched successfully. <br> Event time="+eventDateTime+" Timestamp before API Request="+currentDateTime+ " Event="+latestEventName+" Application="+latestApplication);
											isEventSuccessful=false;
										}
										
										reporter.ReportStep("Launch app on the device using API Query", "App should get launched successfully.", "Last Event is "+latestEventName+" App name "+latestApplication +" dateTime " +eventDateTime, isEventSuccessful);
									}
									
									/*if (!(jsonResponse.indexOf("\"isSuccess\":false") > 0))
									{
										for(int iwaitcounter=0; iwaitcounter<=25; iwaitcounter++) 
										{
											PerformAction("browser", Action.Refresh);
											installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
											System.out.println(installedAppsList);
											
											if (installedAppsList.indexOf(appName) == -1)
												break;
										}
						            }
									
									installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
									System.out.println(installedAppsList);
									
									if (installedAppsList.indexOf(appName) == -1)
									{
										apiReporter.apiPassBlock("Application uninstalled successfully.");
										isEventSuccessful=true;
									}			
									else
									{
										apiReporter.apiErrorBlock("Application not uninstalled successfully.");
										isEventSuccessful=false;
									}
									installedAppsList=installedAppsList.replace("Uninstall", ", ");	
									reporter.ReportStep("Get the installed app list on device after calling API request", "App should not be available.", "Installed list is "+installedAppsList, isEventSuccessful);
									apiReporter.apiAddBlock("After API Request Installed app list = <br>"+installedAppsList);*/
						
									
									ExecuteCLICommand("release", "Android", "", "", deviceName, "","","" );
									Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
									
									PerformAction(dicOR.get("lnkApplications_DeviceDetailsPage"), Action.Click);
								}
								
							}catch(Exception ex)
							{
								System.out.println("Error launching the device  .continuing with uninstall" + ex.getMessage());
								apiReporter.apiErrorBlock("Error launching the device  .continuing with uninstall" );
								continue;
							}
						/*---------------------------------------------------------------------------------------------------------------------------------------
						*****************************************Uninstall Code Starts here***********************************************************************
						 --------------------------------------------------------------------------------------------------------------------------------------*/
						
							reporter.ReportStep("<b>For "+Platform[j]+"- uninstall</b>", "", "", true);	
							apiReporter.apiHeading2("<b>For "+Platform[j]+"- uninstall-- preservedata (RemoveData for 7.2 and above) true </b>");
							
							//parameterList="verb,action,Id,deviceid,wait,preservedata"; ------ jaishree : Commenting it as preservedata is now RemoveData
							parameterList="verb,action,Id,deviceid,wait,removedata";
							valueList="action,uninstall,"+appId+","+deviceId+",true," + forceVal; //declaration and intialization of curl command varaiables.
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
									//installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
									installedAppsList=apiMethods.getInstalledApps();
									System.out.println(installedAppsList);
									
									if (installedAppsList.indexOf(appName) == -1)
										break;
								}
				            }
							
							//installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
							installedAppsList=apiMethods.getInstalledApps();
							System.out.println(installedAppsList);
							
							if (installedAppsList.indexOf(appName) == -1)
							{
								apiReporter.apiPassBlock("Application uninstalled successfully.");
								isEventSuccessful=true;
							}			
							else
							{
								if ((Platform[j].equals("Android")) && (forceVal.equals("false")))
								{
									apiReporter.apiWarningBlock("Warning: removedata = false, the app remains in installed app list known issue ML-4395");
									isEventSuccessful=true;
								}else
								{
									apiReporter.apiErrorBlock("Application not uninstalled successfully.");
									isEventSuccessful=false;
								}
							}
							installedAppsList=installedAppsList.replace("Uninstall", ", ");	
							reporter.ReportStep("Get the installed app list on device after calling API request", "App should not be available.", "Installed list is "+installedAppsList, isEventSuccessful);
							apiReporter.apiAddBlock("After API Request Installed app list = <br>"+installedAppsList);
						}catch(RuntimeException re)
						{
							apiReporter.apiErrorBlock("Error message: "+re.getMessage());
						}
						
					}
				}catch(Exception ex)
				{
					System.out.println("Iteration terminated in between  .continuing next iteration" + ex.getMessage());
					apiReporter.apiErrorBlock("Iteration terminated in between  .continuing next iteration" );
					continue;
					
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
