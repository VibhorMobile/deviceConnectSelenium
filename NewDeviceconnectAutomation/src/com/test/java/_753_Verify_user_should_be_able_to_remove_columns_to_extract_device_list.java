package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1289
 */
public class _753_Verify_user_should_be_able_to_remove_columns_to_extract_device_list extends ScriptFuncLibrary {
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private boolean isHardwareModelSelected,isModelDisplayed,isOSDisplayed;




	public final void testScript() throws InterruptedException, IOException
	{
		try{


			String EmailAddress = dicCommon.get("EmailAddress");
			String Password = dicCommon.get("Password");


			//*************************************************************//                     
			// Step 1 : login to deviceConnect with admin user.
			//*************************************************************//                     
			isEventSuccessful = Login();


			//*************************************************************/                     
			// Step 2 : Click on settings Button to display settings menu
			//*************************************************************//
			GoToDevicesPage();
			strStepDescription="Click on Settings to display menu ";
			strExpectedResult="Clicked on settings button and menu displayed";
			isEventSuccessful=PerformAction(dicOR.get("btnSettings_DevicesPage"), Action.Click);
			if(isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("DeviceSettingsMenu_DevicesPage"), Action.isDisplayed);
				if(isEventSuccessful){
					strActualResult="Clicked on settings button and Menu dispalyed";
					isEventSuccessful=true;
				}
				else{
					strActualResult="Clicked on settings button but menu not dispalyed";
					isEventSuccessful=false;
				}
			}
			else{
				strActualResult="Unable to click on settings button";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful); 

			//*************************************************************/                     
			// Step 3 : Uncheck Model and OS and Verify they are not available in List
			//*************************************************************//
			strStepDescription="Uncheck Model and OS from Menu and Verify they are not available in List";
			strExpectedResult="Model and OS unchecked and Removed from List";
			PerformAction(dicOR.get("eleModelCB_DeviceSettings"), Action.DeSelectCheckbox);
			PerformAction(dicOR.get("eleOSCB_DeviceSettings"), Action.DeSelectCheckbox);
			PerformAction(dicOR.get("btnSettings_DevicesPage"), Action.Click);
			isModelDisplayed=PerformAction("//a[contains(text()='Model')]", Action.isDisplayed);
			isOSDisplayed=PerformAction("//a[contains(text()='OS')]", Action.isDisplayed);

			if(isModelDisplayed && isOSDisplayed){
				strActualResult="Model and OS Displayed on List";
				isEventSuccessful=false;

			}
			else{
				strActualResult="Model and OS not Displayed on List";
				isEventSuccessful=true;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful); 
			//*************************************************************/                     
			// Step 4 : Revert Changes Check Model and OS
			//*************************************************************//
			strStepDescription="Check Model and OS from Menu and Verify they are available in List";
			strExpectedResult="Model and OS checked and available in List";
			PerformAction(dicOR.get("eleModelCB_DeviceSettings"), Action.Select);
			PerformAction(dicOR.get("eleOSCB_DeviceSettings"), Action.Select);
			PerformAction(dicOR.get("btnSettings_DevicesPage"), Action.Click);

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "View Token Field for User--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

}
