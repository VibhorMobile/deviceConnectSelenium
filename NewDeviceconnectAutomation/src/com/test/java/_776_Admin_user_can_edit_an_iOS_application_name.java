package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-1467
 */
public class _776_Admin_user_can_edit_an_iOS_application_name extends ScriptFuncLibrary {
	
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "",appName,editedAppName;
	 

	public final void testScript()
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();

			//**********************************************************//
			// Step 2 - Go to Applications Page and select iOS Platform.
			//**********************************************************//                                   
			isEventSuccessful = GoToApplicationsPage();
			selectPlatform_Application("iOS");
		    GoToFirstAppDetailsPage();
			//**********************************************************//
			// Step 2 - Edit App Name
			//**********************************************************//     
		 
			appName=GetTextOrValue(dicOR.get("eleAppNameDisplay"),"text");
			EditAndVerifyAppName("edited"+appName); 
			 
			
			
			 
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Form factor--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}

	}

}
