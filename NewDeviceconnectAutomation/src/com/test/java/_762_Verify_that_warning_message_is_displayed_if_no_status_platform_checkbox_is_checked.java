package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1402
 */
public class _762_Verify_that_warning_message_is_displayed_if_no_status_platform_checkbox_is_checked extends ScriptFuncLibrary {
	
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";

    private String warningMessage;
	boolean isWarningDisplayed;

	public final void testScript() throws Exception
	{
		try{
			//*************************************************************//                     
			// Step 1 : login to deviceConnect with Admin User
			//*************************************************************//  


			isEventSuccessful = Login();
			//*************************************************************//      
			// Step 2 : Go to Devices Page 
			//*************************************************************//
			GoToDevicesPage();
		
			//**************************************************************************//
			// Step 3 : Uncheck All Platforms and Status
			//**************************************************************************//
			
			selectPlatform("","uncheckall");
			
		    
			selectStatus("None");
			//**************************************************************************//
			// Step 4 : Verify No devices Available Message
			//**************************************************************************//
			strStepDescription="Verify No Device Available Message displayed";
			strExpectedResult="No devices avilable with selected filter displayed";

			waitForPageLoaded();
			isWarningDisplayed=PerformAction(dicOR.get("eleNoDevice_DevicesPage"), Action.isDisplayed);

			if(isWarningDisplayed){
				warningMessage=GetTextOrValue(dicOR.get("eleNoDevice_DevicesPage"), "text");
				if(warningMessage.contains("No devices match your filter criteria.")){
					strActualResult="No Device AVAILABLE  Message Displayed, Text Message at UI  :"+warningMessage;
					isEventSuccessful=true;
				}
				else{
					strActualResult="Message not Displayed "+warningMessage;
					isEventSuccessful=false;
				}
			}
			else{
				strActualResult="Message not Displayed";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}



}
