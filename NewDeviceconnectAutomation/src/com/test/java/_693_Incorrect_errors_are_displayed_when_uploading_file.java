package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1365
 */
public class _693_Incorrect_errors_are_displayed_when_uploading_file extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private boolean isStepSuccessful=false;
	String errorMessage,appName="user-export.csv";





	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//*************************************************************//                     
			// Step 1 : login to deviceConnect with admin user.
			//*************************************************************//  


			isEventSuccessful = Login();

			//*************************************************************/                     
			// Step 2 : Go to Applications Page
			//*************************************************************//

			isEventSuccessful = GoToApplicationsPage();


			//*************************************************************/                     
			// Step 2 : Upload an invalid file/other than apk or ipa
			//*************************************************************//

			strStepDescription = "Click on upload Application button and upload the invalid application on Applications page.";
			strExpectedResult =  "Upload action should be performed";
			isEventSuccessful=PerformAction("uploadApplicationlnk",Action.Click);
			if(isEventSuccessful)
			{
				isEventSuccessful = PerformAction("uploadApplicationlnk",Action.UploadApplication,appName);
				if(isEventSuccessful)
				{
					strActualResult="Upload action performed";

				}
				else
				{
					strActualResult = "Upload action not performed";
				}
			}
			else{
				strActualResult="Upload button not visible";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//*************************************************************/                     
			// Step 4:Verify  Error Popup appeared
			//*************************************************************//
			strStepDescription = "Verify error popup appears with invalid file type message";
			strExpectedResult =  "Error popup should appear with invalid file type message.";
			Thread.sleep(2000);
			isEventSuccessful=PerformAction(dicOR.get("errorBlock_DCUI"), Action.isDisplayed);
			if(isEventSuccessful){


				errorMessage=GetTextOrValue(dicOR.get("errorMessage_ErrorBlock"), "text");
				if(errorMessage.contains("Invalid file type: "+appName)){
					strActualResult="Error Popup appeared with message :"+errorMessage;
					isEventSuccessful=true;
				}
				else{
					strActualResult="Error popup appeared with message:"+errorMessage;
					isEventSuccessful=false;
				}
			}
			else{
				strActualResult="Error popup did not appeared";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful); 


		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
