package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-433, QA-981, QA-743
 */

public class _520_Verify_Certificate_Identity_and_Team_Identifier_is_blank_for_Android_applications extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "", AppName="",Version="",Date="";
	String [] android={"Android"};

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
		// Step 4 - Click on first application link and verify user on application details page //
		//**********************************************************// 
		strStepDescription = "Click on first app link and app details page get displayed";
		strExpectedResult = "App details page should be displayed.";
		isEventSuccessful = SelectApplication("first");
		if (isEventSuccessful)
		{
			strActualResult = "App details page displayed";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 5 - Verify Certificate Identity blank for android app //
		//**********************************************************//        
		strStepDescription = "Verify Certificate Identity blank for android app";
		strExpectedResult = "Certificate Identity should be blank for android app";
		isEventSuccessful = GetTextOrValue(dicOR.get("appAllInforAppDetails").replace("*", "8"),"text").length()<1;
		if (isEventSuccessful)
		{
			strActualResult = "Certificate Identity is blank for android app";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		//**********************************************************//
		// Step 6 - Verify Team Identifier blank for android app //
		//**********************************************************//        
		strStepDescription = "Verify Team Identifier blank for android app";
		strExpectedResult = "Team Identifier should be blank for android app";
		isEventSuccessful = GetTextOrValue(dicOR.get("appAllInforAppDetails").replace("*", "9"),"text").length()<1;
		if (isEventSuccessful)
		{
			strActualResult = "Team Identifier is blank for android app";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}

}