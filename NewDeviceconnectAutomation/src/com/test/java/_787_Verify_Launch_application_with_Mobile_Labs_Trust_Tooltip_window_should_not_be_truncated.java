package com.test.java;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: Last week of August
 * Last Modified Date: 29-August-2016
 * Jira Test Case Id: QA-1177
 */

public class _787_Verify_Launch_application_with_Mobile_Labs_Trust_Tooltip_window_should_not_be_truncated extends ScriptFuncLibrary

{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	Object[] Values = new Object[5]; 
	private String strActualResult = "",strStepDescription="",strExpectedResult="";
	
	public final void testScript() throws AWTException
	{

		// Step 1 - Login to deviceConnect//
		isEventSuccessful =  Login();

		// Step 2 - Select Status as Available  
		if(isEventSuccessful)
		{
			selectStatus("Available");
		}

		// Step 3 - Click on Connect button
		if(isEventSuccessful)
		{
			strStepDescription="Verify connect window opened on clicking Connect button";
			strExpectedResult="Connect window should be opened on clicking Connect button";
			waitForPageLoaded();
			isEventSuccessful =  PerformAction(dicOR.get("btnConnect_ListView") + "[" + 1 + "]", Action.Click);
			if(isEventSuccessful)
			{
				waitForPageLoaded();
				isEventSuccessful=PerformAction("TooltipOnConnectWindow",Action.Exist);
				if(isEventSuccessful)
				{
					reporter.ReportStep(strStepDescription, strExpectedResult, "Connect window displayed", isEventSuccessful);
				}
				else
				{
					reporter.ReportStep(strStepDescription, strExpectedResult,"Connect window did not displayed", isEventSuccessful);
				}
			}
			else
			{
				reporter.ReportStep(strStepDescription, strExpectedResult, "Unable to click on Connect button", isEventSuccessful);
			}
		} 
		
		// Step 4 - Verify tooltip text displayed
		if(isEventSuccessful)
		{
			strStepDescription="Verify tooltip text displayed";
			strExpectedResult="tooltip text should be displayed";
			PerformAction("TooltipOnConnectWindow",Action.MouseHover);
			waitForPageLoaded();
			if(getAttribute("TootipTextLocator","style").contains("display: block; opacity: 1;"))
			{
				if(GetTextOrValue("TootipTextLocator","text").contains("Mobile Labs Trust is Windows software for manual testing and automated testing with HP UFT."))
				{
					reporter.ReportStep(strStepDescription, strExpectedResult, "Tooltip text displayed.", isEventSuccessful);
				}
				else
				{
					reporter.ReportStep(strStepDescription, strExpectedResult, "Tooltip displayed with wrong text.", isEventSuccessful);
				}
			}
			else
			{
				reporter.ReportStep(strStepDescription, strExpectedResult, "Tooltip did not displayed.", isEventSuccessful);
			}
		}
	}

}
