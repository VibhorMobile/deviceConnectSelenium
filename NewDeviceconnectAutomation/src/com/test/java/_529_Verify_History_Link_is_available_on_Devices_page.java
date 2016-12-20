package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-567
 */

public class _529_Verify_History_Link_is_available_on_Devices_page extends ScriptFuncLibrary
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
		//*** Step 2 : navigate to history page. *****//
		//******************************************************************//
		strStepDescription = "Verify History link displayed on Device Index Page";
		strExpectedResult = "History link should be displayed on Device Index page.";
		if(isEventSuccessful)
		{
			isEventSuccessful = GetTextOrValue(dicOR.get("navlinkDeviceIndexPage").replace("_index_", "4"),"text").equals("History");
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