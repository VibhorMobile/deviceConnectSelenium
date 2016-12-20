package com.test.java;

import java.awt.AWTException;
import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-796
 */
public class _595_Verify_user_import_or_export_should_have_Administrator_only_access extends ScriptFuncLibrary {
	
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ;
	private String strexpectedResult = "" , strActualResult ;
	
	public final void testScript() 
	 
	{
		//Step 1
		isEventSuccessful = Login(); 
	
		//Step 2
		isEventSuccessful = GoToUsersPage();
		
		//Step3
		isEventSuccessful  = PerformAction("browser","waitforpagetoload");
		isEventSuccessful = PerformAction("ExportUserbtn",Action.isDisplayed);
		if(isEventSuccessful)
		{
			isEventSuccessful = PerformAction("ImportUserbtn",Action.isDisplayed);
			if(isEventSuccessful)
			{
				strActualResult = "ExportUserbtn & ImportUserbtn is displayed" ;
			}
			else
			{
				strActualResult = "ImportUserbtn is not displayed" ; 
			}
		}
		else
		{
			strActualResult = "ExportUserbtn is not displayed" ;
		}
		reporter.ReportStep("Verify_user_import_or_export_should_have_Administrator_only_access", "User_import_or_export_should_have_Administrator_only_access" , strActualResult, isEventSuccessful);
		
		//Step 3
		isEventSuccessful = Logout();
	      
		//Step 4
		isEventSuccessful = Login(dicCommon.get("testerEmailAddress"), dicCommon.get("testerPassword")); 
	      
		//Step 5
		strStepDescription = "Go to 'Users' page";
		strExpectedResult = "'Users page should be opened.";
		isEventSuccessful = navigateToNavBarPages("Users", "eleUsersHeader");
		if (!isEventSuccessful)
		{
			if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
			{
				isEventSuccessful = PerformAction("btnCreateUser", Action.WaitForElement,"20");
			}
			isEventSuccessful = !PerformAction("btnCreateUser", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Users page is not opened.";
			}
			else
			{
				strActualResult = "Users page is  opened.";
			}
		}
		else
		{
			strActualResult = "selectFromMenu()-- " + strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

	}
}
