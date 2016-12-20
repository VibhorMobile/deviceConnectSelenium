package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: Third week of February
 * Last Modified Date: Same as creation date
 * Jira Test Case ID-QA-1032,1414,1110
 */

public class _631_Verify_bulk_release_on_devices_list_filtered_on_the_basis_of_Platform_value extends ScriptFuncLibrary {
	
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	Object[] Values = new Object[5]; 
	private String strActualResult = "", devicename = "",EmailAddress, Password;
	
	public final void testScript() throws IOException
	{
		
		// Step 1 - Login to deviceConnect//
				// /////////////////////////////////////////////////////////////////////////////////////
				   isEventSuccessful = Login();
				   
				  
				   
				   if (isEventSuccessful)
					{	
				     	 Values = ExecuteCLICommand("run", "iOS");
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

					 strStepDescription = "Verify_an_admin_user_can_release_all_In_Use_devices";
					 strExpectedResult = "an_admin_user_should be able to_release_all_In_Use_devices";
					 
					 isEventSuccessful = selectStatus("iOS");
					   
					isEventSuccessful = selectStatus("In Use");
					
				   selectAllDevicesCheckbox_DI();
					
				   if(isEventSuccessful)
					{	
					
					   isEventSuccessful = PerformAction("//button[text()='Release']", Action.Click);
					   if(isEventSuccessful)
					   {
						   isEventSuccessful = PerformAction("btnReleaseDevices", Action.Click);
						   if(isEventSuccessful)
						   {
							   isEventSuccessful = PerformAction("//div[@class='status-description-row'][2]", Action.WaitForElement);
							   String status = GetTextOrValue("//div[@class='status-description-row'][2]", "text");
							   if(isEventSuccessful)
							   {
								   strActualResult = "an_admin_user_is able to_release_all_In_Use_devices" + status ;
							   }
							   else
							   {
								   strActualResult = "an_admin_user_is not able to_release_all_In_Use_devices" + status ; 
							   }
						   }
						   else
						   {
							   strActualResult = "Could not clicked on bulk Release button";  
						   }
					   }
					   else
					   {
						   strActualResult = "Could not clicked on Release button";
					   }
					
					}
					else
					{
						strActualResult = "Could not clicked on Check-box";
					}
					
					reporter.ReportStep(strStepDescription, strExpectedResult , strActualResult,isEventSuccessful);
					
					//**************************************************************************//
					// Step 3 : Release device.
					//**************************************************************************//
					ExecuteCLICommand("release", "", "", "", devicename, "","","" );
					Runtime.getRuntime().exec("taskkill /F /IM MobileLabs.deviceViewer.exe");

	}
}
