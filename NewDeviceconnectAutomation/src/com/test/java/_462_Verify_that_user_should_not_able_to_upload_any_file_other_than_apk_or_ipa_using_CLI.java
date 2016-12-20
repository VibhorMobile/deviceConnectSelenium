package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-20
 */

public class _462_Verify_that_user_should_not_able_to_upload_any_file_other_than_apk_or_ipa_using_CLI extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="", cmdText="";
	private String xpath = "";
	Object[] Values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login();


		//**************************************************************************//
		// Step 2 : Connect to an iOS device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("uploadcheck", "iOS", EmailAddress, Password, "", "");
	     	 //isEventSuccessful = (boolean)Values[4];
	     	 //outputText=(String)Values[1];
	     	 cmdText=(String)Values[0];
	     	 deviceName=(String)Values[3];
			 if (cmdText.contains("File type '.csv' not allowed"))
				{
				   isEventSuccessful=true;
				   strActualResult = "User can not upload file other than .ipa and .apk";
				}
				else
				{
					isEventSuccessful=false;
					strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
				}
			
		}
		else
		{
			return; // Return if in use is not selected.
		}
		reporter.ReportStep("User can not upload any file other than .ipa and.apk" , "No file uploaded file other than .ipa and .apk", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
	}
}