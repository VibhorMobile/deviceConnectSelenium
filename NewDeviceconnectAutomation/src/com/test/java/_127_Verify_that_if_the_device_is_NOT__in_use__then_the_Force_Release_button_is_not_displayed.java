package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Jira Test Case ID: QA-2165
 */
public class _127_Verify_that_if_the_device_is_NOT__in_use__then_the_Force_Release_button_is_not_displayed extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "";
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private Object[] arrvalues = new Object[2];

	public final void testScript()
	{
		
		//*************************************************************//     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//     
		isEventSuccessful = Login();

		//********************************************************************//                 
		// Step 2 : Open device details page for an available Android device.
		//********************************************************************//                 
		strstepDescription = "Open device details page for an Available Android device.";
		strexpectedResult = "Device details page for the available android device should be displayed.";
		isEventSuccessful = selectPlatform("Android");
		if (isEventSuccessful)
		{
			isEventSuccessful = selectStatus("Available");
			if (isEventSuccessful) 
			{
				arrvalues =  GoTofirstDeviceDetailsPage();
				isEventSuccessful = (boolean) arrvalues[0];
			}	
			  if(isEventSuccessful)
			  {
			   strActualResult = "Device details page for the available android device is displayed.";
			  }
				 
			 else
			 {
				strActualResult = "SelectStatus---" + strErrMsg_AppLib;
			 }
		}
	 else
	 {
			strActualResult = "SelectPlatform---" + strErrMsg_AppLib;
	 }

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		

		//*******************************************************************//                 
		// Step 3 : Verify force Release button on the page is not displayed.
		//*******************************************************************//                 
		strstepDescription = "Verify force Release button on the page is disabled.";
		strexpectedResult = "Force Release button should not be displayed on device details page.";

		isEventSuccessful = !PerformAction("btnRelease", Action.isDisplayed, "10");
		if (isEventSuccessful)
		{
			strActualResult = "Force release button is not displayed for not in use devices.";
		}
		else
		{
			strActualResult = "Release button is displayed on device details page.";
		}
			
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		

		//*************************************************************//                 
		// Step 4 : Go back to "Devices" page.
		//*************************************************************//                 
		strstepDescription = "Go back to 'Devices' page.";
		strexpectedResult = "Devices Page should be displayed.";
		isEventSuccessful = returnToDevicesPage();
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleDevicesHeader", Action.isDisplayed);
			if (!isEventSuccessful)
			{
				strActualResult = "Devices page is not displayed after clicking on 'Devices' tab.";
			}
			else
				strActualResult = "Devices page is displayed after clicking on 'Devices' tab.";
		}
		else
		{
			strActualResult =  "Not returned to Devices page " + strErrMsg_AppLib;
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		
		//********************************************************************//                 
		// Step 2 : Open device details page for an available iOS device.
		//********************************************************************//                 
		strstepDescription = "Open device details page for an Available iOS device.";
		strexpectedResult = "Device details page for the available iOS device should be displayed.";
		isEventSuccessful = selectPlatform("iOS");
		if (isEventSuccessful)
		{
			isEventSuccessful = selectStatus("Available");
			if (isEventSuccessful) 
			{
				arrvalues =  GoTofirstDeviceDetailsPage();
				isEventSuccessful = (boolean) arrvalues[0];
			}	
			else
			{
				strActualResult = "SelectStatus---" + strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = "SelectPlatform---" + strErrMsg_AppLib;
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		

		//*******************************************************************//                 
		// Step 3 : Verify force Release button on the page is not displayed.
		//*******************************************************************//                 
		strstepDescription = "Verify force Release button on the page is disabled.";
		strexpectedResult = "Force Release button should not be displayed on device details page.";

		isEventSuccessful = !PerformAction("btnRelease", Action.isDisplayed, "10");
		if (isEventSuccessful)
		{
			strActualResult = "Force release button is not displayed for not in use devices.";
		}
		else
		{
			strActualResult = "Release button is displayed on device details page.";
		}
			
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
				
	}
}