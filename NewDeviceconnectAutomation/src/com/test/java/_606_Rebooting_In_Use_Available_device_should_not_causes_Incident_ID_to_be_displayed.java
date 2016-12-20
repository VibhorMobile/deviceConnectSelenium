package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-1315
 */
public class _606_Rebooting_In_Use_Available_device_should_not_causes_Incident_ID_to_be_displayed extends ScriptFuncLibrary {
	
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	Object[] Values = new Object[5]; 
	private String strActualResult = "", devicename = "",EmailAddress, Password;
	
	public final void testScript() throws IOException
	{
		
		// Step 1 - Login to deviceConnect//
		isEventSuccessful = Login();
		
		// Step 2
		if (isEventSuccessful)
		{	
			Values = ExecuteCLICommand("run", "Android");
			isEventSuccessful = (boolean)Values[2];
			devicename= (String)Values[3];
			if (isEventSuccessful)
			{
				strActualResult = "Viewer launched after connecting to an Android device:  " + Values[0] + " & processfound : " +  Values[1];
			}
			else
			{
				strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
			}
						
		}
		else
		{
			return; // Return if in use is not selected.
		}
		reporter.ReportStep("Connect to an android device and verify deviceViewer is launched with" + dicOutput.get("executedCommand"), "User should get connected and deviceviewer should get launched.", strActualResult, isEventSuccessful);
					
		if (!isEventSuccessful)
		{
			return;
		}

		
		// Step 
		strStepDescription = "Rebooting_In_Use_device_should_not_causes_Incident_ID_to_be_displayed";
		strExpectedResult = "Rebooting_In_Use_Available_device_should_not_causes_Incident_ID_to_be_displayed";
		
		isEventSuccessful = selectStatus("In Use");
		isEventSuccessful = PerformAction(dicOR.get("chkDeviceName_Devices")+"[" + 1 + "]", Action.SelectCheckbox);
		if(isEventSuccessful)
		{	
			
			isEventSuccessful = PerformAction("//button[text()='Reboot']", Action.Click);
			if(isEventSuccessful)
			{
				isEventSuccessful = PerformAction("btnRebootDevice", Action.Click);
				if(isEventSuccessful)
				{
					isEventSuccessful = PerformAction("//div[@class='status-description-row'][2]", Action.WaitForElement, "120");
					isEventSuccessful = PerformAction("//div[@class='status-description-row'][2]", Action.isDisplayed);
					if(isEventSuccessful)
					{
						strActualResult = "Rebooting_In_Use_Available_device_is not_caused_Incident_ID_to_be_displayed" ;
					}
					else
					{
						strActualResult = "Rebooting_In_Use_Available_device_is caused_Incident_ID_to_be_displayed" ; 
					}
				}
				else
				{
					strActualResult = "Could not clicked on bulk Reboot button";  
				}
			}
			else
			{
				strActualResult = "Could not clicked on Reboot button";
			}
					
		}
		else
		{
			strActualResult = "Could not clicked on Check-box";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult , strActualResult,isEventSuccessful);
			
		//**************************************************************************//
		//Release device.
		//**************************************************************************//
		ExecuteCLICommand("release", "Android", "", "", devicename, "","","" );
		Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");
		   
	}
}
