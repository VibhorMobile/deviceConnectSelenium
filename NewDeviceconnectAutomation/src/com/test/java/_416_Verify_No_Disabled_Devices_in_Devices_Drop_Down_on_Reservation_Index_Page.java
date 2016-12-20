package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ApplicationLibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2161
 */
public class _416_Verify_No_Disabled_Devices_in_Devices_Drop_Down_on_Reservation_Index_Page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";
	private String reportStepDesc;
	private String reportStepExpResult;
	private String device_Dropdown = "Devices"; // This variable is used to replace it for device drop down
	private java.util.List<String> DisabledDevices = new java.util.ArrayList<String>();
	private java.util.List<String> devicesDropDown_list = new java.util.ArrayList<String>();
	private String errorDevices = "";
	
	///####################################################################################################################################
	///# Test Script Description: Verify the disabled devices are not displayed in Devices drop down on Reservation Index Page
	///####################################################################################################################################

	public final void testScript()
	{
		/////////////////////////////////////////////////////////////////////
		// Step 1 : Launch deviceConnect and verify homepage.
		/////////////////////////////////////////////////////////////////////
		genericLibrary.LaunchWebDriver();
		isEventSuccessful = PerformAction("imgDCLogo", Action.Exist);
		if (isEventSuccessful)
		{
			strActualResult = "Navigated to " + dicCommon.get("ApplicationURL") + ". deviceConnect homepage is opened.";
		}
		else
		{
			strActualResult = "deviceConnect homepage is not opened.";
		}
		reporter.ReportStep("Launch deviceConnect and verify homepage.", "deviceConnect homepage should be opened.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}

		//////////////////////////////
		// Step 2 : Login as an admin
		//////////////////////////////
		isEventSuccessful = LoginToDC();
		if (isEventSuccessful)
		{
			strActualResult = "User - " + dicCommon.get("EmailAddress") + " is logged in to deviceConnect.";
		}
		else
		{
			strActualResult = "User - " + dicCommon.get("EmailAddress") + " is not logged in to deviceConnect.";
		}
		reporter.ReportStep("Login to deviceConnect", "User should be logged in.", strActualResult, isEventSuccessful);

		
		////////////////////////////////////////////////////////////////////////////
		// Step 3 : Select the Disabled checkbox from Statuses on Devices Index page 
		////////////////////////////////////////////////////////////////////////////
		isEventSuccessful = selectStatus("Disabled");
//		noOfDevices	 = getelementCount(dicOR.get("eleDevicesHolderListView")) - 1; // Get number of devices' rows . '-1' because it counts the header also.
		
		/////////////////////////////////////////////////////////////////////
		// Step 3 : Get the list of Disabled Devices from the Devices Page
		/////////////////////////////////////////////////////////////////////
		reportStepDesc = "Get the disabled devices from the devices page";
		reportStepExpResult = "Disabled devices list should be feteched successfully if present";
		strActualResult = "Disabled device list is fetched successfully";
			DisabledDevices = GetAllDevicesDetails("devicename", "list");
			if (strErrMsg_AppLib.equals(""))
			{
				isEventSuccessful = true;
			}
			else
			{
				isEventSuccessful = false;
				strActualResult = "GetAllDevicesDetails -- " + strErrMsg_AppLib;
			}

		reporter.ReportStep(reportStepDesc, reportStepExpResult, strActualResult, isEventSuccessful);

		/////////////////////////////////////////////////////////////////////////////
		// Step 4: Verify that 'Devices' field should exist on Reservation Index Page
		/////////////////////////////////////////////////////////////////////////////
		reportStepDesc = "Verify 'Devices' filter on Reservation Index Page";
		reportStepExpResult = "'Devices' filter should be displayed on Reservation Index Page";

		isEventSuccessful = GoToReservationsPage();
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction(dicOR.get("eleReservationIndexPgLabels").replace("__LABEL__", "Devices"), Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "'Devices' element is displayed successfully on Reservation Index Page";
			}
			else
			{
				strActualResult = "'Devices' element is not displayed successfully on Reservation Index Page";
			}
		}
		else
		{
			strActualResult = "SelectFromMenu-- " + strErrMsg_AppLib + "";
		}
		reporter.ReportStep(reportStepDesc, reportStepExpResult, strActualResult, isEventSuccessful);

		//////////////////////////////////////////////////////////////////////////////////////////////
		// Step 5 : Get the list of Devices from the Devices drop down from Reservation Index Page
		///////////////////////////////////////////////////////////////////////////////////////////////
		// which is Device Drop down.
		reportStepDesc = "Get the devices list from the devices drop down on Reservation Index Page";
		reportStepExpResult = "Drop down should contains some devices";

		isEventSuccessful = PerformAction(dicOR.get("eleDropdownFilter").replace("__FILTER__", "Devices"), Action.isDisplayed);
		if (isEventSuccessful)
		{
			devicesDropDown_list = getDropDownOptions("Devices");
			if (!strErrMsg_AppLib.equals(""))
			{
				strActualResult = "";
			}
			else
			{
				strActualResult = "Devices in the 'Devices' dropdown added to list.";
			}
			//for (int count = 1; count < devicesDropDown_list.Count; count++)
			//{
			//    if (!string.IsNullOrEmpty(devicesDropDown_list[count]))
			//        devicelist = devicelist + " , " + devicesDropDown_list[count];
			//}

		}
		else
		{
			strActualResult = "Devices drop down does not exit hence can not fetch devices list";
		}
		reporter.ReportStep(reportStepDesc, reportStepExpResult, strActualResult, isEventSuccessful);


		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Step 5 : Verify that disabled devices should not exit in Devices drop down in Reservation index page
		//////////////////////////////////////////////////////////////////////////////////////////////////////////

		reportStepDesc = "Verify devices drop down on Reservation index page";
		//string disabledDevice_list = "";
		for (String listObj : DisabledDevices)
		{
			if (devicesDropDown_list.contains(listObj))
			{
				isEventSuccessful = false;
				errorDevices = errorDevices + ", " + listObj;
			}
		}
		if (!errorDevices.equals(""))
		{
			strActualResult = "These disabled devices are displayed in the Devices dropdown : " + errorDevices;
		}
		else
		{
			strActualResult = "Disabled devices are not displayed in 'Devices' dropdown on Reservations page.";
		}
		//var difference = devicesDropDown_list.Intersect(DisabledDevices).ToList();
		//if (difference.Count == 0)
		//    isEventSuccessful = true;

		//if (isEventSuccessful)
		//{
		//    disabledDevice_list = DisabledDevices[0];
		//    for (int count = 1; count < DisabledDevices.Count; count++)
		//    {
		//        if (!string.IsNullOrEmpty(DisabledDevices[count]))
		//            disabledDevice_list = disabledDevice_list + " , " + DisabledDevices[count];
		//    }
		//    strActualResult = "'<b> " + disabledDevice_list + "</b>' disabled devices are not present in devices drop down";
		//}
		//else
		//    strActualResult = "'<b> " + disabledDevice_list + "</b>' disabled devices are present in devices drop down";
		//reportStepExpResult = " Devices drop down should not contain'<b> " + disabledDevice_list + "</b>'";
		reporter.ReportStep("Verify that disabled devices should not exit in Devices drop down in Reservation index page", "Disabled devices should not exit in Devices drop down in Reservation index page.", strActualResult, isEventSuccessful);
	}
}