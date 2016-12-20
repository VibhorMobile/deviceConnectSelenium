package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Deepak
 * Creation Date: 
 * Last Modified Date: Same as creation date
 * Jira Test Case Id: QA-123
 */

public class _668_Verify_User_is_able_to_save_device_notes_through_ctrl_and_enter_shortcut extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strexpectedResult = "", strActualResult = "",strstepDescription="";
	String [] android={"Android"};
	
	public final void testScript()
	{
		//*******************************//
		// Step 1 - Login to deviceConnect//
		//*******************************//
		if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
		{
			return;
		}
		isEventSuccessful = Login();
		
		//**********************************************************//
		// Step 2 - Go to first device details page //
		//**********************************************************//   
		if(isEventSuccessful)
		{
			isEventSuccessful = (Boolean)GoTofirstDeviceDetailsPage()[0];
		}
		
		//**********************************************************//
		// Step 3 - Expand details section //
		//**********************************************************//                                   
		if(isEventSuccessful)
		{
			isEventSuccessful = ShowDetails();
		}
		
		//**********************************************************//
		// Step 4 - Verify notes can be add or modify by administrator  //
		//**********************************************************// 
		strstepDescription = "Verify user is able to save note using Ctrl + Enter";
		strexpectedResult =  "User should be able to save device note using Ctrl + Enter.";
		isEventSuccessful=Add_Modify_Notes_using_Ctrl_Enter_keys("Notes field can be save using Ctrl plus Enter");
		if(isEventSuccessful)
		{
			String txt=GetTextOrValue("notesLocatorDeviceDetails", "text");
			if(txt.contains("Notes field can be save using Ctrl plus Enter"))
			{
				isEventSuccessful=true;
				strActualResult="User is able to save device note using Ctrl plus Enter.";
			}
			else
			{
				isEventSuccessful=false;
				strActualResult="User is not able to save device note using Ctrl plus Enter.";
			}
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);		
		
	}
}