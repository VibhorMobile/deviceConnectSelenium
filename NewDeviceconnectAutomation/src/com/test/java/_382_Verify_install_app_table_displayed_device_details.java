package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

public class _382_Verify_install_app_table_displayed_device_details extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";

	public final void testScript()
	{
		///////////////////////////////////////////////////////////
		//Step : 1 Login to Device Connect with Admin Credentails
		///////////////////////////////////////////////////////////
		isEventSuccessful = Login();
		
		///////////////////////////////////////////////////////////
		//Step : 2 Select Platform Android
		///////////////////////////////////////////////////////////		
		
		isEventSuccessful = selectPlatform("Android");

		///////////////////////////////////////////////////////////
		//Step : 3 Select first Android Device
		///////////////////////////////////////////////////////////
		isEventSuccessful = SelectDevice("first");
		if (isEventSuccessful)
		{
			strActualResult = "Device Details page is opened.";
		}
		else
		{
			strActualResult = "SelectDevice() -- " + strErrMsg_AppLib + "Device name : " + dicOutput.get("selectedDeviceName");
		}
		reporter.ReportStep("Select first available device.", "'Device Details' page should be opened.", strActualResult, isEventSuccessful);

		///////////////////////////////////////////////////////////
		//Step : 4 Verify Install Application Table is displayed
		///////////////////////////////////////////////////////////
		 isEventSuccessful = PerformAction("DeviceDetails_Install_App_heading", Action.WaitForElement,"10");
		  if(!isEventSuccessful)
		   {
			 return;
		   }
		isEventSuccessful = PerformAction("DeviceDetails_Install_App_heading", Action.isDisplayed);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("DeviceDetails_Install_App_Table", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Installed Application table is displayed on device details page.";
			}
			else
			{
				strActualResult = "Installed Application table is not displayed on Device Details Page of device : " + dicOutput.get("selectedDeviceName");
			}
		}
		else
		{
			strActualResult = "Installed Application header is not displayed on Device Details Page of device : " + dicOutput.get("selectedDeviceName");
		}
		reporter.ReportStep("Verify Installed Application Table is displayed", "'Installed Application' table should be displayed.", strActualResult, isEventSuccessful);
	}
}