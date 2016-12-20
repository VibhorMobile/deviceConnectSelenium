package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2157
 */
public class _113_Verify_Connect_button_on_the_List_view_for__In_Use__status_device extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "", devicename = "", deviceStatus = "",user ="", userFirstLastName;
	Object[] Values = new Object[4]; 

	public final void testScript()
	{
		//*************************************************************//     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//     
		isEventSuccessful = Login();

		//*************************************************//
		// Step 2 : Get the User first name And last name //
		//************************************************//
		try
		{
			isEventSuccessful = GoToUsersPage();
			if(isEventSuccessful)
			{
				isEventSuccessful = GoToSpecificUserDetailsPage(dicCommon.get("EmailAddress"));
				if(isEventSuccessful)
				{
					userFirstLastName = GetTextOrValue("//div[@class='page-titlebar']//.", "text");
					System.out.println(userFirstLastName);
					if(!userFirstLastName.equals(null))
					{
						isEventSuccessful = true;
					}
					else
					{
						isEventSuccessful = false;
					}
				}
				else
				{
					throw new RuntimeException("could click on user" + dicCommon.get("EmailAddress") );
				}
			}
			else
			{
				throw new RuntimeException("could click on user page");
			}
		}
		catch (RuntimeException e)
		{
			isEventSuccessful = false;
			strErrMsg_AppLib = "User First name last name--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			reporter.ReportStep("Get the User first name And last name.", "User first name And last name should be dispalyed.", strErrMsg_AppLib, isEventSuccessful);
		}



		//***************************************************************************************//
		//Step 2 : Connect to any android available device using CLI if NO In Use device present //
		//***************************************************************************************//
		if (isEventSuccessful)
		{	
			Values = ExecuteCLICommand("connect", "Android");
			isEventSuccessful = (boolean)Values[2];
			devicename=(String)Values[3];
			if (isEventSuccessful)
			{
				strActualResult = "Viewer launched after connecting to an Android device:  " + Values[0] + " & processfound : " +  Values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
			reporter.ReportStep("Connect to an android device and verify deviceViewer is launched with" + dicOutput.get("executedCommand"), "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
		}

		//**************************************//
		// Step 3 : Select In Use device status //
		//**************************************//
		isEventSuccessful = PerformAction("browser", "waitforpagetoload","10");
		isEventSuccessful = selectStatus( "In Use");

		if (!isEventSuccessful)
		{
			return;
		}

		//*************************************************************//
		//Step 4 - Verify connect button in Grid View
		//*************************************************************//  
		isEventSuccessful=searchDevice(devicename, "devicename");
		try
		{
			String xpathDevicesHolder = dicOR.get("eleDevicesHolderListView");
			int Devicescount = getelementCount(xpathDevicesHolder);
			for(int i =1; i<Devicescount; i++)
			{
				deviceStatus = GetTextOrValue(dicOR.get("eleUserStatus_ListView").replace("__INDEX__", String.valueOf(i)), "text");
				user = deviceStatus.split(" ")[2];
				user = user.replace("(", "").replace(")","");
				if(dicCommon.get("EmailAddress").contains(user)|| userFirstLastName.contains(user) )
				{
					isEventSuccessful = true;
				}
				if(isEventSuccessful)
				{

					isEventSuccessful = !PerformAction(dicOR.get("btnConnectDisabled_ListView") + "[" + i + "]", Action.isDisplayed);
					if (isEventSuccessful)
					{
						strActualResult = "Connect button is enabled for: " + user + "under 'In Use' devices.";
						reporter.ReportStep("Verify Connect button under the List view for all 'In Use' status devices.", "Connect button should be enabled for :" + user +  "'In Use' devices in list view.", strActualResult, isEventSuccessful);
					}
					else
					{
						strActualResult = "VerifyDeviceDetailInListView---" + strErrMsg_AppLib;
						reporter.ReportStep("Verify Connect button under the List view for all 'In Use' status devices.", "Connect button should be enabled for :" + user +  "'In Use' devices in list view.", strActualResult, isEventSuccessful);
					}
				}
				else
				{
					isEventSuccessful = PerformAction(dicOR.get("btnConnectDisabled_ListView") + "[" + i + "]", Action.isDisplayed);
					if (isEventSuccessful)
					{
						strActualResult = "Connect button is disabled for: " + user + "under 'In Use' devices.";
						reporter.ReportStep("Verify Connect button under the List view for all 'In Use' status devices.", "Connect button should be disabled for :" + user +  "'In Use' devices in list view.", strActualResult, isEventSuccessful);
					}
					else
					{
						strActualResult = "VerifyDeviceDetailsInListView---" + strErrMsg_AppLib;
						reporter.ReportStep("Verify Connect button under the List view for all 'In Use' status devices.", "Connect button should be disabled for :" + user +  "'In Use' devices in list view.", strActualResult, isEventSuccessful);
					}
				}
			}
		}
		catch (RuntimeException e)
		{
			isEventSuccessful = false;
			strErrMsg_AppLib = "Battery Status--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			reporter.ReportStep("Verify Connect button under the List view for all 'In Use' status devices.", "Connect button should be disabled for :" + user +  "'In Use' devices in list view.", strErrMsg_AppLib, isEventSuccessful);
		}



		//*************************************************************//
		//Step 4 - Post-Condition - Close device Viewer
		//*************************************************************//
		//CloseWindow("MobileLabs.deviceviewer");
		ExecuteCLICommand("release", "Android", "", "", devicename, "","","" );
		isEventSuccessful =	KillObjectInstances("MobileLabs.deviceViewer.exe");
		reporter.ReportStep("Verfiy Device is not connected after closing devcieViewer window","Device should not be connected after closing devcieViewer window","Device is not connected after closing devcieViewer window",isEventSuccessful);
	}
}