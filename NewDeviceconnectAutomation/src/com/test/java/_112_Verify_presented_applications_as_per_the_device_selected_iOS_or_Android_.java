package com.test.java;

import java.util.ArrayList;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2155
 */
public class _112_Verify_presented_applications_as_per_the_device_selected_iOS_or_Android_ extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		// Variables from datasheet//////////////////

		ArrayList<String> androidAppList = new ArrayList<String>();
		ArrayList<String> iOSAppList = new ArrayList<String>();
		String CloseBtnNameInOR = "";

		//********************************************************//
		//******   Step 1 - Login to deviceConnect   *************//
		//********************************************************//
		isEventSuccessful = Login();

		//*****************************************************************//
		//**** Step 2 - Put platform specific applications in lists. ******//
		//*****************************************************************//
		isEventSuccessful = GoToApplicationsPage();

		//****************************************************************************************************//
		//****Step 3 - When applications list table has appeared, calculate the number of ipa's and apk's. ***//
		//****************************************************************************************************//
		if (isEventSuccessful) 
		{
			androidAppList = getPlatformSpecificAppsLists("android");
			iOSAppList = getPlatformSpecificAppsLists("ios");
			System.out.println(androidAppList.size());
			System.out.println(iOSAppList.size());
		}

		if (androidAppList.isEmpty() && iOSAppList.isEmpty())
		{
			reporter.ReportStep("Pre-condition", "Applications should be loaded in the system.", "No applications are loaded in the system.", "Fail");
			return;
		}

		//**********************************************************************************************//
		//** Step 4 : Go to Devices page and click on Connect button for an available Android device. **//
		//**********************************************************************************************//
		strstepDescription = "Go to Devices page and click on Connect button for an available Android device.";
		strexpectedResult = "Either Launch application or No Applications page should be opened.";

		isEventSuccessful = GoToDevicesPage();
			//isEventSuccessful = PerformAction("chkAvailable", Action.SelectCheckbox);
			isEventSuccessful = selectStatus("Available");

				isEventSuccessful = ! VerifyMessage_On_Filter_Selection();
				if (isEventSuccessful) // continue only if there are some devices under android platform.
				{
					PerformAction("browser", Action.Scroll, "30");
					//isEventSuccessful = selectPlatform("android");
					isEventSuccessful = selectPlatform("Android");
					isEventSuccessful = !VerifyNoRowsWarningOnTable() && strErrMsg_AppLib.equals("No warning message displayed on table."); //!GetTextOrValue("class=message", "text").Contains("deviceConnect currently has no configured devices or your filter produced no results.");
						if (isEventSuccessful) // continue only if there are some devices under android platform.
						{
							/*if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
					        {
								isEventSuccessful = PerformAction("", Action.WaitForElement,"15");
					        }*/
							
							isEventSuccessful = PerformAction(dicOR.get("btnConnectGridView").replace("__INDEX__","1"), Action.Click);
							if (isEventSuccessful)
							{
								isEventSuccessful = PerformAction("eleLaunchApplicationHeader", Action.WaitForElement, "20");
								if (!isEventSuccessful)
								{
									isEventSuccessful = PerformAction("eleNoAppWarning", Action.WaitForElement, "20");
									if (!isEventSuccessful)
									{
										strActualResult = "None of the two pages is displayed on clicking on Connect button.";
									}
								}
							}
							else
								strActualResult = "'Launch Application' page not displayed on clicking on 'Connect' button.";
						}
						else
							strActualResult = "No available Android devices displayed on devices page.";
				}
				else
				{
					strActualResult = "No available devices displayed on devices page.";
					reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
					return;
				}
		if (isEventSuccessful)
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Launch Application' page displayed successfully on clicking on 'Connect' button.", "Pass");
		else
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");

		//***********************************************************************************//
		//** Step 5 : Verify only android applications are displayed in applications list. **//
		//***********************************************************************************//
		strstepDescription = "Verify only android applications are displayed in applications list.";
		strexpectedResult = "Only android applications should be displayed in applications list.";
		isEventSuccessful = verifyAppsList(androidAppList);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Only android applications are displayed in applications list.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		}

		//********************************************************//
		//***** Step 6 : Close the 'Launch Application' page. ****//
		//********************************************************//
		strstepDescription = "Close the 'Launch Application' page.";
		strexpectedResult = "'Launch Application' page should be closed.";
		/*if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
        {
			isEventSuccessful = PerformAction("btnClose", Action.isDisplayed) && PerformAction("btnClose", Action.Click);
        }
		else
		{*/
		   isEventSuccessful = PerformAction("btnClose", Action.isDisplayed) && PerformAction("btnClose", Action.Click);
		
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnClose", Action.isNotDisplayed);
			if (!isEventSuccessful)
			{
				strActualResult = "'Launch Application' page not closed after clicking on 'Close' button.";
			}
		}
		else
		{
			strActualResult = "Close button does not exist on the page.";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Launch Application' page closed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//************************************************************************//
		//***** Step 7 : Click on Connect button for an available iOS device. ****//
		//************************************************************************//
		strstepDescription = "Click on Connect button for an available iOS device.";
		strexpectedResult = "Either Launch application or No apps page should be opened.";
		PerformAction("browser", Action.Refresh); // refresh the devices page.
		
		
		
		isEventSuccessful = selectPlatform("iOS");
			isEventSuccessful =  ! VerifyMessage_On_Filter_Selection();
			if (isEventSuccessful) // continue only if there are some devices under android platform.
			{
				PerformAction("browser", Action.Scroll, "30");
				isEventSuccessful =  ! VerifyMessage_On_Filter_Selection();
				if (isEventSuccessful) // continue only if there are some devices under android platform.
				{
					isEventSuccessful = PerformAction(dicOR.get("btnConnectGridView").replace("__INDEX__","1"), Action.Click);
					if (isEventSuccessful)
					{
						isEventSuccessful = PerformAction("eleLaunchApplicationHeader", Action.WaitForElement, "20");
						if (!isEventSuccessful)
						{
							isEventSuccessful = PerformAction("eleNoAppWarning", Action.WaitForElement, "20");
							if (isEventSuccessful)
							{
								CloseBtnNameInOR = "btnCloseNoApps";
							}
							else
							{
								strActualResult = "None of the two pages is displayed on clicking on Connect button.";
							}
						}
						else
						{
							CloseBtnNameInOR = "btnClose";
						}
					}
					else
					{
						strActualResult = "'Launch Application' page not displayed on clicking on 'Connect' button.";
					}
				}
				else
				{
					strActualResult = "No available iOS devices displayed on devices page.";
				}
			}
			else
			{
				strActualResult = "No iOS devices displayed on devices page.";
			}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Launch Application' page displayed successfully on clicking 'Connect' button.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//**********************************************************************************//
		//*** Step 8 : Verify only iOS applications are displayed in applications list. ***//
		//**********************************************************************************//
		strstepDescription = "Verify only iOS applications are displayed in applications list.";
		strexpectedResult = "Only iOS applications should be displayed in applications list.";
		isEventSuccessful = verifyAppsList(iOSAppList);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Only iOS applications are displayed in applications list.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strErrMsg_AppLib, "Fail");
		}

		//**************************************************************************//
		//************ Step 9 : Close the 'Launch Application' page. ***************//
		//**************************************************************************//
		strstepDescription = "Close the 'Launch Application' page.";
		strexpectedResult = "'Launch Application' page should be closed.";
		isEventSuccessful = PerformAction("btnClose", Action.Click);
		if (!isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnCloseNoApps", Action.Click);
		}
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("btnCloseNoApps", Action.isNotDisplayed);
			if (!isEventSuccessful)
			{
				strActualResult = "'Launch Application' page not closed after clicking on 'Close' button.";
			}
		}
		else
		{
			strActualResult = "Close button does not exist on the page.";
		}

		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "'Launch Application' page closed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
	}
}