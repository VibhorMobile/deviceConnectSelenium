package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1353
 */
public class _698_Verify_User_friendly_error_message_is_thrown_on_uploading_Malformed_ipa extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "", errorMessage = "";;

	public final void testScript() throws InterruptedException, IOException
	{
		// Step 1 : login to deviceConnect with valid user credentials.
				isEventSuccessful = Login();
				 
				// Step 2 : Navigate to Application page.
				isEventSuccessful = GoToApplicationsPage();
				
				// Step 3 : Click on upload and malformed ipa 'Malformed_SunTrust.ipa'
				strStepDescription = "Upload the malformed ipa on Applications page and verify error message.";
				strExpectedResult =  "Correct error message should be displayed. Expected Error message - 'iOS application is malformed and lacks a Payload.'";
				isEventSuccessful =   uploadApplication("Malformed_SunTrust.ipa");
				if(isEventSuccessful)
				{
					errorMessage = GetTextOrValue("errorPopup", "text");
					System.out.println(errorMessage);
					
					boolean isMatch = Pattern.matches("^iOS application is malformed and lacks a Payload. Incident ID ([A-Z]|[0-9]){4}$", errorMessage.trim());  
					if (isMatch)
					{
						isEventSuccessful=true;
						strActualResult="Getting error message for malformed ipa. Error Message -"+errorMessage;
					}else
					{
						isEventSuccessful=false;
						strActualResult="Incorrect error message. Actual Error Message -"+errorMessage;
					}	
				}
				else
				{
					isEventSuccessful=false;
					strActualResult = "App uploaded but should return error message for malformed ipa";
				}
				reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}
}