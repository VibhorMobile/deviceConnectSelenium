package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1743
 */
public class _699_Verify_System_Tab_Upload_the_output_file_iOS_Management_is_working_properly_when_an_invalid_zip_is_uploaded extends ScriptFuncLibrary
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
				 
				// Step 2 : Go to iOS Management page.
				isEventSuccessful=GoToiOSManagementPage();
				
								
				// Step 3 : 
				strStepDescription = "Upload the invalid zip file and verify error message.";
				strExpectedResult =  "Correct error message should be displayed. Expected Error message - 'Invalid device control package.'";
				isEventSuccessful =   uploadOutputFile_SystemPage("javacsv-2.0.jar.zip");
				if(isEventSuccessful)
				{
					errorMessage = GetTextOrValue("errorPopup", "text");
					System.out.println(errorMessage);
					
					boolean isMatch = Pattern.matches("^Invalid device control package. Incident ID ([A-Z]|[0-9]){4}$", errorMessage.trim());  
					if (isMatch)
					{
						isEventSuccessful=true;
						strActualResult="Getting error message for invalid zip file. Error Message -"+errorMessage;
					}else
					{
						isEventSuccessful=false;
						strActualResult="Incorrect error message. Actual Error Message -"+errorMessage;
					}	
				}
				else
				{
					isEventSuccessful=false;
					strActualResult = "Output file successfully uploaded but should return error message for invalid zip file";
				}
				reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
				
		
	}
}