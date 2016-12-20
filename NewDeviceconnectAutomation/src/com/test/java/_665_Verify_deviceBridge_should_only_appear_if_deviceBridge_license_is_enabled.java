package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-609
 */

public class _665_Verify_deviceBridge_should_only_appear_if_deviceBridge_license_is_enabled extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private java.util.List<String> runTimeoptionsList = new java.util.ArrayList<String>();
	private int optionsCount =  10;

	public final void testScript()
	{

		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();

		//*******************************//
		//Step 2 - Verify deviceBridge enabled in license//
		//*******************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Verify deviceBridge enabled in license.";
			strexpectedResult = "deviceBridge should be enabled in license.";
			isEventSuccessful=PerformAction("lnkAbout",Action.Click);
			if(isEventSuccessful)
			{
				isEventSuccessful=GetTextOrValue("deviceBridgedetailsAboutDeviceConnect","text").contains("Enabled");
				if(isEventSuccessful)
				{
					strActualResult="device Bridge enable in license.";
				}
				else
				{
					strActualResult="device Bridge disabled.";
				}
				PerformAction("btnClose_aboutPage",Action.Click);
				PerformAction("browser",Action.Refresh);
			}
			else
			{
				strActualResult="Unable to open About deviceConnect pop up.";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////
		// Step 3 : Get the options in UserName drop-down in a list, report fail and return if it is empty
		/////////////////////////////////////////////////////////////////////////////////////////////////
		if(isEventSuccessful)
		{
			strstepDescription = "Get options from UserName drop-down and put in list 'runTimeoptionsList'";
			runTimeoptionsList = getDropDownOptions("eleMenuHolder");
			System.out.println(runTimeoptionsList);
			if (runTimeoptionsList.size()>0)
			{
				isEventSuccessful=true;
				reporter.ReportStep(strstepDescription, "Options should be added to list successfully.", " Options added to list successfully.", isEventSuccessful);
			}
			else
			{
				isEventSuccessful=false;
				reporter.ReportStep(strstepDescription, "Options should be added to list successfully.", "Options not added to list.", isEventSuccessful);
				return;
			}
		}
		
		///////////////////////////////////////////////////////////////////////////
		// Step 4 : Verify the option's in UserName drop down.
		///////////////////////////////////////////////////////////////////////////
		if(isEventSuccessful)
		{
			strstepDescription = "Verify the 'Download deviceBridge' option in UserName dropdown.";
			strexpectedResult = "'Download deviceBridge' option should be available in user name dropdown.";
			isEventSuccessful = runTimeoptionsList.get(2).contains("Download deviceBridge");
			if (isEventSuccessful)
			{
				strActualResult = "'Download deviceBridge' option available in user name dropdown.";
			}
			else
			{
				strActualResult = "'Download deviceBridge' option did not available in user name dropdown.";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
				
		}
		
	}
	
}
