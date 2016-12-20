package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-43
 */

public class _476_Verify_deviceConnect_CLI_UserGuide_pdf_is_not_included_in_the_CLI_zip_downloaded extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	int count=0;
	Object[] Values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{
		

		//*************************************************************//                     
		// Step 1 : Verify deviceConnect_CLI_UserGuide.pdf is not located in CLI zip file.
		//*************************************************************//                     
		isEventSuccessful=Verify_CLI_Guide_File_Existence();
		if(isEventSuccessful)
		{
			strActualResult="deviceConnect_CLI_UserGuide.pdf is not there in zip";
		}
		else
		{
			strActualResult="deviceConnect_CLI_UserGuide.pdf is there in zip file";
		}
		reporter.ReportStep("Verify deviceConnect_CLI_UserGuide.pdf is not included in the CLI zip" , "deviceConnect_CLI_UserGuide.pdf should not be there in zip", strActualResult, isEventSuccessful);
	}
}