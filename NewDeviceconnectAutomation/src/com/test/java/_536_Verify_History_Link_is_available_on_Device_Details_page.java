package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1008, QA-567
 */

public class _536_Verify_History_Link_is_available_on_Device_Details_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();

		//******************************************************************//
		//*** Step 2 : Select Status Available. and select first device*****//
		//******************************************************************//  
		isEventSuccessful = selectStatus("Available");
		strstepDescription = "Open device details page for first Available device.";
		strexpectedResult =  "Device details page for the first available device should be displayed.";
				
		if (isEventSuccessful)
		{
			isEventSuccessful = !VerifyMessage_On_Filter_Selection();
			if (isEventSuccessful) // continue only if there are some devices under android platform.
			{
				isEventSuccessful = SelectDevice("first");
				if (isEventSuccessful)
				{
					strActualResult = "Device details page displayed";
				}
				else
				{
					strActualResult = "Device details page did not displayed";
				}
			}
			else
			{
				strActualResult = "Warning messgae displayed on devices page.";
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//******************************************************************//
		//*** Step 3 : Verify history page link available. *****//
		//******************************************************************//
		strStepDescription = "Verify History link displayed on Device Index Page";
		strExpectedResult = "History link should be displayed on Device Index page.";
		if(isEventSuccessful)
		{
			isEventSuccessful = GetTextOrValue(dicOR.get("navlinkDeviceIndexPage").replace("_index_", "3"),"text").equals("History");
			if(isEventSuccessful)
			{
				strActualResult="History link displayed on Device Index page";
			}
			else
			{
				strActualResult="History link not displayed on Device Index page";
			}
		}
		else
		{
			strActualResult="You are not logged in.";
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);
	}	
}