package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: Third week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-1120
 */
public class _621_The_filters_must_persist_across_browser_sessions extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "", devicename;
	Object[] values = new Object[2];
	
	
	public final void testScript()
	{
	
		//Step 1 - Login to deviceConnect
		isEventSuccessful = Login(); 
	 

		isEventSuccessful = selectStatus("Available,In Use");
	 
		isEventSuccessful = Logout();
	 
		isEventSuccessful = Login();
	 
		strStepDescription = "Verify The_filters_must_persist_across_browser_sessions";
		strExpectedResult = "The_filters_should be_persist_across_browser_sessions";
	 
		//String checkbox =  GetTextOrValue("(//input[starts-with(@name,'options.statusFilter')])", "text");
		isEventSuccessful = PerformAction("//label[text()[normalize-space()='Available']]//input", Action.isSelected);
	 	if(isEventSuccessful)
	 	{
	 		isEventSuccessful = PerformAction("//label[text()[normalize-space()='In Use']]//input", Action.isSelected);
	 		if(isEventSuccessful)
	 		{
	 			strActualResult = "Both checkboxes are selected aftr logging out session";
	 		}
	 		else
	 		{
	 			strActualResult = "Both checkboxes are not selected aftr logging out session";
	 		}
	 	}
	 	else
	 	{
	 		strActualResult = "Available checkbox is not selected aftr logging out session";
	 	}
	 	reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);	 

		 
	}
}
