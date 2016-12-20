package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1706
 */

public class _109_Verify_that_when_device_is_in_use_the_indicator_is_RED extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "",deviceName="'";
	Object[] Values = new Object[4]; 

	public final void testScript() throws IOException
	{
		//********************************************************//
		//******   Step 1 - Login to deviceConnect   *************//
		//********************************************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		//** Step 2 - Select Available status **********//
		//**********************************************************//          
		isEventSuccessful = selectStatus("In Use");
		
		//****************************************************************************************//
		//*********  Step 3 - Verify that when device is In Use the indicator is Red. *******//
		//****************************************************************************************//
		isEventSuccessful = VerifyDeviceDetailsInGridAndListView("statusicon", "Red", "list");
		
		if (!isEventSuccessful)
		{
			try
			{
				isEventSuccessful = selectStatus("Available");
				if(isEventSuccessful)
				{
					Values = ExecuteCLICommand("run", "Android");
					isEventSuccessful = (boolean)Values[2];
					deviceName=(String)Values[3];
					if(isEventSuccessful)
					{
						 isEventSuccessful = selectStatus("In Use");
						 if(isEventSuccessful)
						 {
							 isEventSuccessful = VerifyDeviceDetailsInGridAndListView("statusicon", "Red", "list");
							
							 if (isEventSuccessful)
							 {
								strActualResult = "The indicator for devices Available is Red.";
							 }
							 else
							 {
								strActualResult = "VerifyDeviceDetailsInGridAndListView---" + strErrMsg_AppLib;
							 }

							reporter.ReportStep("Verify that when device is Available the indicator is RED", "Indicator for devices Available should be RED.", strActualResult, isEventSuccessful);
							
						}
						else
						{
							throw new RuntimeException("Could not selected 'In Use' staus checkbox.");
						}
					}
					else
					{
						throw new RuntimeException("Could not connected a device");
					}
					ExecuteCLICommand("release", "Android", "", "", deviceName, "","","" );
					Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
				}
				else
				{
					throw new RuntimeException("Could not selected 'Available' staus checkbox.");
				}
			}
			catch (RuntimeException e)
			{
				isEventSuccessful = false;
				strActualResult = "Connecting a device" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			reporter.ReportStep("In Use status device not dispalyed, connecting with CLI command", "In Use status device should be dispalyed", strActualResult, isEventSuccessful);
			
		}
		else
		{
			strActualResult = "The indicator for devices 'In Use' is Red.";
		}

		reporter.ReportStep("Verify that when device is 'In Use' the indicator is RED", "Indicator for devices 'In Use' should be RED.", strActualResult, isEventSuccessful);
	 
       
		
  }
}