package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1061
 */
public class _747_Verify_Disk_usage_should_not_be_shown_as_0_MB_or_0_percent_on_Device_detail_page_of_iOS_devices extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String diskUsgae;
	Object[] Values = new Object[5]; 
	boolean isRetained,isReleased;






	public final void testScript() throws InterruptedException, IOException
	{
		try{
			String EmailAddress = dicCommon.get("testerEmailAddress");
			String Password = dicCommon.get("testerPassword");

			//*************************************************************//                     
			// Step 1 : login to deviceConnect with Admin user 
			//*************************************************************//  


			isEventSuccessful = Login();
			//*************************************************************//      
			// Step 2 : Go to Devices Page and open details page for any Available  iOS device
			//*************************************************************//
			GoToDevicesPage();
			selectPlatform("iOS");
			selectStatus("Available");
			isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0];
			//**************************************************************************//
			// Step 3 : Verify DiskUsage for the device
			//**************************************************************************//
			strstepDescription = "Verify Disk Usage is not 0 Percent";
			strExpectedResult = "Disk Usage is not 0 Percent";
			isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
			diskUsgae=GetTextOrValue(dicOR.get("eleDiskUsage_DeviceDetailsPage"), "text");
			
			if(diskUsgae.contains("0MB")){
				strActualResult="Disk Usage is shown as O MB, Disk Usgae shown on UI is:"+diskUsgae;
				isEventSuccessful=false;
			}
			else{
				strActualResult="Disk Usage is not shown as OMB, Actual Sisk Usgae is shown as :"+diskUsgae;
				isEventSuccessful=true;
			}
			
			
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);						 

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Disk Usge for iOS devices--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
