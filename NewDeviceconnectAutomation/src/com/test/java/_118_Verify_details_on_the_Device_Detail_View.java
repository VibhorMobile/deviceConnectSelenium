package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1367
 */

public class _118_Verify_details_on_the_Device_Detail_View extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		//*********************************//
		// Variables 
		//*********************************//
/*		String EmailAddress = dicCommon["EmailAddress"];
		String Password = dicCommon["Password"];*/
		String devicename = "";
		String devicemodel = "";
		String deviceplatform = "";
		String devicestatus = "";
		String actualValue = "";

		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();

		//******************************************************************//
		//*** Step 2 : Open device details page for an Android device. *****//
		//******************************************************************//                   
		strstepDescription = "Open device details page for an Android device.";
		strexpectedResult = "Device details page for the corresponding Android device should be displayed.";

		isEventSuccessful = selectPlatform("Android");
		if (isEventSuccessful)
		{
			isEventSuccessful = !GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results.");
			if (isEventSuccessful) // continue only if there are some devices under android platform.
			{
				devicename = GetDeviceDetailInGridAndListView(1, "devicename");
				devicemodel = GetDeviceDetailInGridAndListView(1, "devicemodel" );
				deviceplatform = GetDeviceDetailInGridAndListView(1, "deviceplatform");
				devicestatus = GetDeviceDetailInGridAndListView(1, "devicestatus");
				isEventSuccessful = SelectDevice("first");
				if (!isEventSuccessful)
				{
					strActualResult = strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "No Android devices displayed on devices page.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}


		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Device details page is displayed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//******************************************************************//
		//**** Step 3 : Verify device name on the device details page. *****//
		//******************************************************************//
		strstepDescription = "Verify device name on the device details page.";
		strexpectedResult = "Correct name should be displayed on the device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Name", devicename);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Name on devices and device details page is: " + devicename, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//******************************************************************//
		//** Step 4 : click on 'more...' link to display hidden details ****//
		//******************************************************************//
		strstepDescription = "Click on 'more...' link to display hidden device details";
		strexpectedResult = "User should be able to click on the 'more...' link";
		isEventSuccessful = PerformAction("lnkShowDetails", Action.isDisplayed);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
			if (isEventSuccessful)
			{
				strActualResult = "User is able to click on 'more...' link successfully.";
			}
			else
			{
				strActualResult = "Could not click on 'more...' link on device details page.";
			}
		}
		else
		{
			strActualResult = "'more...' link is not displayed.";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//******************************************************************//
		//**** Step 5 : Verify device Model on the device details page. ****//
		//******************************************************************//
		strstepDescription = "Verify device Model on the device details page.";
		strexpectedResult = "Correct Model should be displayed on the device details page.";
		//actualValue = GetTextOrValue("eleDeviceNameinDeviceDetailsHeader", "text");
		//isEventSuccessful = actualValue.Contains(GetTextOrValue("" + "//label[text()='Model']" + "/following-sibling::span", "text"));
		isEventSuccessful = VerifyOnDeviceDetailsPage("Model Name", "CONTAINS__" + devicemodel);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Model on devices and device details page is: " + devicemodel, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//*******************************************************************//
		//*** Step 6 : Verify device Model # on the device details page. ***//
		//******************************************************************//
		strstepDescription = "Verify device Model # on the device details page.";
		strexpectedResult = "Correct Model # should be displayed on the device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Model #");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Model # is displayed on device details page.", "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//********************************************************************//
		//*** Step 7 : Verify device platform on the device details page. ****//
		//********************************************************************//
		strstepDescription = "Verify device platform name on the device details page.";
		strexpectedResult = "Correct platform should be displayed on the device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Platform", "CONTAINS__" + deviceplatform);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Platform on devices and device details page is: " + deviceplatform, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//******************************************************************//
		//*** Step 8 : Verify device status on the device details page. ****//
		//******************************************************************//
		strstepDescription = "Verify device status on the device details page.";
		strexpectedResult = "Correct status should be displayed on the device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Status ", "CONTAINS__" + devicestatus);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Status on devices and device details page is: " + devicestatus, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
		//******************************************************************//
		//***** Step 9 : Verify Vendor Name on the device details page. ****//
		//******************************************************************//
		strstepDescription = "Verify Vendor Name on the device details page.";
		strexpectedResult = "Correct Vendor Name should be displayed on the device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Vendor Name");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Vendor Name is displayed on device details page.", "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//******************************************************************//
		//*** Step 10 : Verify Serial Number on the device details page. ***//
		//******************************************************************//
		strstepDescription = "Verify Serial Number on the device details page.";
		strexpectedResult = "Correct Serial Number should be displayed on the device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Serial Number");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Serial Number is displayed on device details page.", "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//******************************************************************//
		//****** Step 11 : Verify Vendor ID on the device details page. ****//
		//******************************************************************//
		//strstepDescription = "Verify Vendor ID is not displayed for android on the device details page.";
		//strexpectedResult = "Vendor ID should not be displayed on android device details page.";
		//isEventSuccessful = !VerifyOnDeviceDetailsPage("Vendor ID");
		//if (isEventSuccessful)
		//    reporter.ReportStep(strstepDescription, strexpectedResult, "Vendor ID is not displayed on device details page.", "Pass");
		//else
		//{
		//    strActualResult = strErrMsg_AppLib;
		//    reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		//}

		//******************************************************************//
		//******* Step 11 : Verify Slot # on the device details page. ******//
		//******************************************************************//
		strstepDescription = "Verify Slot # is not displayed for android on the device details page.";
		strexpectedResult = "Slot # should not be null on android device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Slot #");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Slot # is not null on device details page.", "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//******************************************************************//
		//*** Step 13 : Verify Idle timeout  on the device details page. ***//
		//******************************************************************//
		//strstepdescription = "verify idle timeout is not displayed for android on the device details page.";
		//strexpectedresult = "idle timeout should not be displayed on android device details page.";
		//iseventsuccessful = !verifyondevicedetailspage("idle timeout");
		//if (iseventsuccessful)
		//    reporter.reportstep(strstepdescription, strexpectedresult, "idle timeout is not displayed on device details page.", "pass");
		//else
		//{
		//    stractualresult = strerrmsg_applib;
		//    reporter.reportstep(strstepdescription, strexpectedresult, stractualresult, "fail");
		//}

		//******************************************************************//
		//************* Step 12 : Go back to "Devices" page. ***************//
		//******************************************************************//
		isEventSuccessful = GoToDevicesPage();
		
		//******************************************************************//
		// Step 13 : Open device details page for an iOS device.
		//******************************************************************//
		strstepDescription = "Open device details page for an iOS device.";
		strexpectedResult = "Device details page for the corresponding iOS device should be displayed.";

		PerformAction("browser", Action.Scroll, "30");
		//isEventSuccessful = selectPlatform("ios");
		//isEventSuccessful = SelectFromFilterDropdowns("status", "All", "devices", "grid");
		isEventSuccessful = selectPlatform("iOS");
		if (isEventSuccessful)
		{
			isEventSuccessful = !(GetTextOrValue("class=message", "text").contains("deviceConnect currently has no configured devices or your filter produced no results."));
			if (isEventSuccessful) // continue only if there are some devices under android platform.
			{
				devicename = GetDeviceDetailInGridAndListView(1, "devicename");
				devicemodel = GetDeviceDetailInGridAndListView(1, "devicemodel");
				deviceplatform = GetDeviceDetailInGridAndListView(1, "deviceplatform");
				devicestatus = GetDeviceDetailInGridAndListView(1, "devicestatus");
				isEventSuccessful = SelectDevice("first");
				if (!isEventSuccessful)
				{
					strActualResult = strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "No iOS devices displayed on devices page.";
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
				return;
			}
		}
		else
		{
			strActualResult = "SelectFromFilterDropdowns -- " + strErrMsg_AppLib;
		}
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Device details page is displayed successfully.", "Pass");
		}
		else
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//******************************************************************//
		// Step 14 : Verify device name on the device details page.
		//******************************************************************//

		strstepDescription = "Verify device name on the device details page.";
		strexpectedResult = "Correct name should be displayed on the device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Name", devicename);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Name on devices and device details page is: " + devicename, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//******************************************************************//
		// Step 15 : click on 'more...' link to display hidden details
		//******************************************************************//
		strstepDescription = "Click on 'more...' link to display hidden device details";
		strexpectedResult = "User should be able to click on the 'more...' link";
		isEventSuccessful = PerformAction("lnkShowDetails", Action.isDisplayed);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
			if (isEventSuccessful)
			{
				strActualResult = "User is able to click on 'more...' link successfully.";
			}
			else
			{
				strActualResult = "Could not click on 'more...' link on device details page.";
			}
		}
		else
		{
			strActualResult = "'more...' link is not displayed.";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//******************************************************************//
		// Step 16 : Verify device model on the device details page.
		//******************************************************************//
		strstepDescription = "Verify device model on the device details page.";
		strexpectedResult = "Correct model should be displayed on the device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Model Name", "CONTAINS__" + devicemodel);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Model on devices and device details page is: " + devicemodel, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//******************************************************************//
		// Step 17 : Verify device Model # on the device details page.
		//******************************************************************//
		strstepDescription = "Verify device Model # on the device details page.";
		strexpectedResult = "Correct Model # should be displayed on the device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Model #", "");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Model # is displayed on device details page.", "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//******************************************************************//
		// Step 18 : Verify device platform on the device details page.
		//******************************************************************//
		strstepDescription = "Verify device platform name on the device details page.";
		strexpectedResult = "Correct platform should be displayed on the device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Platform", "CONTAINS__" + deviceplatform);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Platform on devices and device details page is: " + deviceplatform, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//******************************************************************//
		// Step 19 : Verify device status on the device details page.
		//******************************************************************//
		strstepDescription = "Verify device status on the device details page.";
		strexpectedResult = "Correct status should be displayed on the device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Status ", "CONTAINS__" + devicestatus);
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Status on devices and device details page is: " + devicestatus, "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//******************************************************************//
		// Step 20 : Verify Vendor Name on the device details page.
		//******************************************************************//
		strstepDescription = "Verify Vendor Name on the device details page.";
		strexpectedResult = "Correct Vendor Name should be displayed on the device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Vendor Name");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Vendor Name is displayed on device details page.", "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//******************************************************************//
		// Step 21 : Verify Serial Number on the device details page.
		//******************************************************************//
		strstepDescription = "Verify Serial Number on the device details page.";
		strexpectedResult = "Correct Serial Number should be displayed on the device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Serial Number");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Serial Number is displayed on device details page.", "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
		//******************************************************************//
		// Step 24 : Verify Vendor ID on the device details page.
		//******************************************************************//
		//strstepDescription = "Verify Vendor ID on the device details page.";
		//strexpectedResult = "Correct Vendor ID should be displayed on the device details page.";
		//isEventSuccessful = VerifyOnDeviceDetailsPage("Vendor ID");
		//if (isEventSuccessful)
		//    reporter.ReportStep(strstepDescription, strexpectedResult, "Vendor ID is displayed on device details page.", "Pass");
		//else
		//{
		//    strActualResult = strErrMsg_AppLib;
		//    reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		//}

		//******************************************************************//
		// Step 22 : Verify Slot # on the device details page.
		//******************************************************************//
		strstepDescription = "Verify Slot # is not displayed for android on the device details page.";
		strexpectedResult = "Slot # should not be null on iOS device details page.";
		isEventSuccessful = VerifyOnDeviceDetailsPage("Slot #");
		if (isEventSuccessful)
		{
			reporter.ReportStep(strstepDescription, strexpectedResult, "Slot # is not null on device details page.", "Pass");
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}

		//******************************************************************//
		// Step 26 : Verify Idle timeout  on the device details page.
		//******************************************************************//
		//strstepDescription = "Verify Idle timeout is not displayed for android on the device details page.";
		//strexpectedResult = "Idle timeout should not be displayed on android device details page.";
		//isEventSuccessful = !VerifyOnDeviceDetailsPage("Idle timeout");
		//if (isEventSuccessful)
		//    reporter.ReportStep(strstepDescription, strexpectedResult, "Idle timeout is not displayed on device details page.", "Pass");
		//else
		//{
		//    strActualResult = strErrMsg_AppLib;
		//    reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		//}
	}
}