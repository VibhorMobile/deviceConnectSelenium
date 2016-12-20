package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-103
 */

public class _472_Verify_timezones_count extends ScriptFuncLibrary
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
		// Step 2 : Connect to an android device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("timezones", "Android", EmailAddress, Password, "", "");
	     	 //isEventSuccessful = (boolean)Values[4];
	     	 //outputText=(String)Values[1];
	     	 count=(Integer)Values[0];
	     	 deviceName=(String)Values[3];
			 if (count==582)
				{
				   isEventSuccessful=true;
				   strActualResult = "Timezones count verified : 582";
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
		reporter.ReportStep("Verify timezones count using -timezones command" , "Count should be 582", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
	}
}