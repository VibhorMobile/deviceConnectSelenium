package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Deepak
 * Creation Date: 18-Feb-2016
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-711, QA-118, QA-358, QA-1139, QA-778
 */

public class _574_Verify_Install_Application_modal_functionality extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";
	String [] android={"Android"};
	int total;
	
	public final void testScript()
	{
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to Applications page //
		//**********************************************************//                                   
		isEventSuccessful = GoToApplicationsPage();
			
		//**********************************************************//
		// Step 3 - Select platform Android //
		//**********************************************************//   
		strStepDescription = "Select platform android";
		strExpectedResult = "Only android platform checkbox selected";
		isEventSuccessful=selectCheckboxes_DI(android, "chkPlatform_Devices");
		if(isEventSuccessful)
		{
			strActualResult="Only android platform checkbox selected";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 4  - Click on first application link and verify user on application details page //
		//**********************************************************// 
		strStepDescription = "Verify install applications page displayed on clicking install button";
		strExpectedResult = "Install applications page displayed";
		PerformAction(dicOR.get("installdropdownApplicationsPage").replace("2", "1"),Action.WaitForElement);
		isEventSuccessful = PerformAction(dicOR.get("installdropdownApplicationsPage").replace("2", "1"),Action.Click);
		if (isEventSuccessful)
		{
			strActualResult = "Install applications page displayed";
		}
		else
		{
			strActualResult = "Unable to click on install button";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 5 - Verify Continue button gets enabled as soon as we select one device //
		//**********************************************************// 
		strStepDescription = "Verify Continue button gets enabled as soon as user select one device.";
		strExpectedResult = "Continue button should gets enabled as soon as user select one device.";
		isEventSuccessful = PerformAction("installRadbtnInstallDialog",Action.isDisplayed);
		if (isEventSuccessful)
		{
			isEventSuccessful = getAttribute("ContinuebtnInstallDialog","class").contains("disabled");
			if(isEventSuccessful)
			{
				isEventSuccessful = PerformAction("installRadbtnInstallDialog",Action.Click);
				isEventSuccessful = PerformAction("ContinuebtnInstallDialog",Action.isEnabled);
				if(isEventSuccessful)
				{
					strActualResult = "Continue button gets enabled as soon as user select one device.";
				}
				else
				{
					strActualResult = "Continue button disabled.";
				}
			}
			else
			{
				strActualResult = "Continue button enabled by default.";
			}
		}
		else
		{
			strActualResult = "Install dialog box does not have any device to install app.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 6 - Verify Total value should change accordingly to number of yes selected //
		//**********************************************************// 
		strStepDescription = "Verify Total value should change accordingly to number of yes selected.";
		strExpectedResult = "Total value should change accordingly to number of yes selected.";
		total=Integer.parseInt(GetTextOrValue("totalselectedDevicesInstallDialog","text").split(" ")[1]);
		if (total==1)
		{
			strActualResult = "Total value changed accordingly to number of yes selected.";
		}
		else
		{
			strActualResult = "Total value did not change accordingly to number of yes selected.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 7 - Verify Continue button gets enabled as soon as we select one device //
		//**********************************************************// 
		strStepDescription = "Verify Check mark after installing app on devices.";
		strExpectedResult = "Check mark should displayed after installing app on devices.";
		isEventSuccessful = PerformAction("SelectAllDeviceInstall",Action.Click);
		total=Integer.parseInt(GetTextOrValue("totalselectedDevicesInstallDialog","text").split(" ")[1]);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("ContinuebtnInstallDialog",Action.Click);
			isEventSuccessful = PerformAction("browser",Action.WaitForPageToLoad);
			isEventSuccessful = PerformAction("browser","wait");
			isEventSuccessful = PerformAction("browser","wait");
			isEventSuccessful = PerformAction("browser","wait");
			isEventSuccessful = PerformAction("browser","wait");
			if(isEventSuccessful)
			{
				isEventSuccessful = PerformAction("checkmarkInstallDialog",Action.WaitForElement);
				
				if(total==Integer.parseInt(GetTextOrValue("checkmarkInstallDialog","text").split(" ")[1]))
				{
					strActualResult = "Check mark displayed after installing app on devices.";
				}
				else
				{
					strActualResult = "Check mark did not displayed after installing app on devices.";
				}
			}
			else
			{
				strActualResult = "Unable to click on continue button.";
			}
		}
		else
		{
			strActualResult = "Unable to click on 'Select All' button.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 8 - Verify Continue button gets enabled as soon as we select one device //
		//**********************************************************// 
		strStepDescription = "Verify Total and Success count are same.";
		strExpectedResult = "Total and Success count should be same.";
		if (total==Integer.parseInt(GetTextOrValue("successinstallDevicesInstallDialog","text").split(" ")[1]))
		{
			strActualResult = "Total and Success count are same.";
		}
		else
		{
			strActualResult = "Total and Success count are different.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}
}