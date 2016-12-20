package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1738
 */
public class _778_Verify_Users_tab_Clicking_on_Create_User_button_does_not_throw_an_error extends ScriptFuncLibrary {
	

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
			// Step 2 - Go to User Page
			//**********************************************************//                                   
		     GoToUsersPage();
			//**********************************************************//
			// Step 3 - Click on Create Users button and Verify no Error 
			//**********************************************************//     
			 
		     GoToCreateUserPage();
			
			 
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Form factor--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}

	}

}
