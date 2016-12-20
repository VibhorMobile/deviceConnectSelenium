package com.test.java;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-200,198
 */
public class _669_Verify_Hardware_Model_and_OS_Build_ID_and_Vendor_Unique_Id_fields_are_in_setting_menu_on_device_index_page extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private boolean isStepSuccessful=false;
	String hardwareModel,OSBuildID,vendorUniqueID;



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
			strstepDescription="Click on Settings to display menu ";
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
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful); 

			//*************************************************************/                     
			// Step 3 : Verify Hardware Model is Present in Menu
			//*************************************************************//
			strstepDescription="Verify Hardware Model Option is present in Menu ";
			strExpectedResult="Hardware Model option is present in Menu";
			isEventSuccessful=PerformAction(dicOR.get("eleHardwareModel_DeviceSettings"), Action.isDisplayed);
			if(isEventSuccessful){
				hardwareModel=GetTextOrValue(dicOR.get("eleHardwareModel_DeviceSettings"), "text");
				if(hardwareModel.equals("Hardware Model")){
					strActualResult="Hardware Model is present in Menu with text:"+hardwareModel;
					isEventSuccessful=true;
				}
				else{
					strActualResult="Hardware Model is not present in Menu with text:"+hardwareModel;
					isEventSuccessful=false;
				}
			}
			else{
				strActualResult="Hardware Model is not present in Menu";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful); 
			
			//*************************************************************/                     
			// Step 4 : Verify OS Build ID is Present in Menu
			//*************************************************************//
			strstepDescription="Verify OS Build ID Option is present in Menu ";
			strExpectedResult="OS Build ID option is present in Menu";
			isEventSuccessful=PerformAction(dicOR.get("eleOSBuildID_DeviceSettings"), Action.isDisplayed);
			if(isEventSuccessful){
				OSBuildID=GetTextOrValue(dicOR.get("eleOSBuildID_DeviceSettings"), "text");
				if(OSBuildID.equals("OS Build ID")){
					strActualResult="OS Build ID is present in Menu with text:"+OSBuildID;
					isEventSuccessful=true;
				}
				else{
					strActualResult="OS Build ID is not present in Menu with text:"+OSBuildID;
					isEventSuccessful=false;
				}
			}
			else{
				strActualResult="OS Build ID is not present in Menu";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful); 
			//*************************************************************/                     
			// Step 5 : Verify Vendor Unique ID is Present in Menu
			//*************************************************************//
			strstepDescription="Verify Vendor Unique ID Option is present in Menu ";
			strExpectedResult="Vendor Unique IDoption is present in Menu";
			isEventSuccessful=PerformAction(dicOR.get("eleVendorUniqueID_DeviceSettings"), Action.isDisplayed);
			if(isEventSuccessful){
				vendorUniqueID=GetTextOrValue(dicOR.get("eleVendorUniqueID_DeviceSettings"), "text");
				if(vendorUniqueID.equals("Vendor Unique ID")){
					strActualResult="Vendor Unique ID is present in Menu with text:"+vendorUniqueID;
					isEventSuccessful=true;
				}
				else{
					strActualResult="Vendor Unique ID is not present in Menu with text:"+vendorUniqueID;
					isEventSuccessful=false;
				}
			}
			else{
				strActualResult="Vendor Unique ID is not present in Menu";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful); 


		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "View Token Field for User--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
