package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-273
 */
public class _687_Verify_new_display_name_reflected_throughout_the_deviceConnect_system extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String oldDeviceName="",Appname="";
	String [] android={"Android"};

	public final void testScript()
	{
		//*********************************//
		// Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();

		//*********************************//
		// Step 2 - Go to Applications page. *****//
		//*********************************//   
		if(isEventSuccessful)
		{
			isEventSuccessful=GoToApplicationsPage();
		}
		
		//**********************************************************//
		// Step 3 - Select platform Android //
		//**********************************************************//  
		if(isEventSuccessful)
		{
			strStepDescription = "Select platform android";
			strExpectedResult = "Only android platform checkbox selected";
			isEventSuccessful=selectCheckboxes_DI(android, "chkPlatform_Devices");
			if(isEventSuccessful)
			{
				strActualResult="Only android platform checkbox selected";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		}

		//**********************************************************//
		// Step 4 - Click on first application link and verify user on application details page //
		//**********************************************************// 
		if(isEventSuccessful)
		{
			strStepDescription = "Click on first app link and app details page get displayed";
			strExpectedResult = "App details page should be displayed.";
			isEventSuccessful = SelectApplication("first");
			if (isEventSuccessful)
			{
				strActualResult = "App details page displayed";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		
		//******************************************************************//
		// Step 5 : Edit application name*****//
		//******************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify application name updated.";
			strexpectedResult =  "Application name should be updated.";
			isEventSuccessful = PerformAction("lnkEdit_ApplicationDetailsPage", Action.Click);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("txtAppName_appDetails", Action.Clear);
				isEventSuccessful = PerformAction("txtAppName_appDetails", Action.Type, "newNameApp");
				if (isEventSuccessful) 
				{
					isEventSuccessful = PerformAction("lnkSaveAppName_ApplicationDetailsPage", Action.Click);
					if (isEventSuccessful)
					{
						Appname = GetTextOrValue("eleAppname_ApplicationDetailsPage","text");
						if (Appname.endsWith("newNameApp"))
						{
							strActualResult = "Application name updated";
						}
						else
						{
							strActualResult = "Application name did not updated";
						}
					}
					else
					{
						strActualResult = "Unable to click on Save link.";
					}
				}
				else
				{
					strActualResult = "Unable to enter new app name.";
				}
			}
			else
			{
				strActualResult = "Unable to click on edit link..";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}

		//*********************************//
		// Step 6 - Go to Applications page. *****//
		//*********************************//   
		if(isEventSuccessful)
		{
			isEventSuccessful=GoToApplicationsPage();
		}
		
		//******************************************************************//
		// Step 7 : Verify updated name displayed on applications page. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Verify updated name displayed on applications page.";
			strexpectedResult =  "Updated app name should be displayed on applications page.";
			isEventSuccessful=searchDevice_DI(Appname);
			if(isEventSuccessful)
			{
				isEventSuccessful = GetTextOrValue(dicOR.get("eleAppNameAppTable").replace("__APP_INDEX__", "1"), "text").equals(Appname);
				if(isEventSuccessful)
				{
					strActualResult="Application with App name: "+Appname+" found on the page.";
				}
				else
				{
					strActualResult="Application with App name: "+Appname+" did not found.";
				}
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
				
		//**********************************************************//
		//** Step 8 - Go to Device Index page **********//
		//**********************************************************// 
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToDevicesPage();
		}

		//**********************************************************//
		//** Step 9 - Select 'Available' status from Filters dropdown **********//
		//**********************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful =  selectStatus("Available");
		}
		
		//**********************************************************//
		//** Step 9 - Select 'Android' platform from Filters dropdown **********//
		//**********************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful =  selectPlatform("Android");
		}


		//****************************************************************************************//
		//** Step 11 - Click on Connect button on first available device **//
		//****************************************************************************************//
		strstepDescription = "Verify application launch dialog box displayed.";
		strexpectedResult =  "Application launch dialog box should be displayed.";
		if (isEventSuccessful)
		{
			isEventSuccessful = OpenLaunchAppDialog("first");
			if (isEventSuccessful) 
			{
				strActualResult = "Application launch dialog box displayed.";
			}
			else
			{
				strActualResult = "Application launch dialog box did not displayed.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		// Step 12 : Verify updated name displayed on connect device page. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Verify updated name displayed on connect device page.";
			strexpectedResult =  "Updated app name should be displayed on connect device page.";
			isEventSuccessful = verifAppAvailability_On_ConnectPage(Appname);
			if(isEventSuccessful)
			{
				strActualResult="Application with App name: "+Appname+" found on the page.";
			}
			else
			{
				strActualResult="Application with App name: "+Appname+" did not found.";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
	}	
}