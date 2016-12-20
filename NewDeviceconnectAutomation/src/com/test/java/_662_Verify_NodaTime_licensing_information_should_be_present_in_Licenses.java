package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-707
 */
public class _662_Verify_NodaTime_licensing_information_should_be_present_in_Licenses extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	Object[] Values = new Object[5]; 
	String [] Entitlement={"Device application management","Device reboot"};
	Boolean [] value={true, true};
	
	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful = Login();


		//*************************************************************//                     
		// Step 2 : Go to licenses page .
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify licenses information pop up displayed.";
			strexpectedResult = "Licenses information pop up should be displayed.";
			isEventSuccessful=PerformAction("licenseLocator",Action.Click);
			if(isEventSuccessful)
			{
				if(GetTextOrValue("LicenseTextLocator","text").equals("Licenses"))
				{
					isEventSuccessful=true;
					strActualResult = "Licenses information pop up displayed.";
				}
				else
				{
					strActualResult="Licenses information pop up did not displayed.";
					isEventSuccessful=false;
				}
			}
			else
			{
				strActualResult="licenseLocator did not displayed";	
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		//*************************************************************//                     
		// Step 3 : Verify NodaTime license message displayed .
		//*************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Verify NodaTime licenses information displayed.";
			strexpectedResult = "NodaTime licenses information should be displayed.";
			if(GetTextOrValue("nodaTimeLicLocator","text").equals("NodaTime"))
			{
				isEventSuccessful=true;
				strActualResult = "NodaTime licenses information displayed.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult = "NodaTime licenses information did not displayed.";
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
			PerformAction("AddbtnPopupRole",Action.Click);
			PerformAction("browser",Action.Refresh);
		}
	}
}