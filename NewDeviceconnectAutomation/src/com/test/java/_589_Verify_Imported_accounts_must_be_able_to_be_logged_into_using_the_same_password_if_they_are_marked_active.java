package com.test.java;

import java.awt.AWTException;
import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */

public class _589_Verify_Imported_accounts_must_be_able_to_be_logged_into_using_the_same_password_if_they_are_marked_active extends ScriptFuncLibrary

{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ;
	private String strexpectedResult = "";
	private String strActualResult = "", emailId , password;
	
	 public final void testScript() throws IOException, AWTException, InterruptedException
	 
	 {
		 
		 //*********************************//
		 //***** Step 1 - Login to DC. *****//
		 //*********************************//       
		 isEventSuccessful = Login();
		 
		 // Step 2 : Create user into userimport file.
		 strstepDescription = "Creating data into csv file";
		 strexpectedResult = "Data should be created and updated into csv file. ";
	     isEventSuccessful = importUser();
		   
	     if(isEventSuccessful)
	     {
	    	 strActualResult = "Data is created and updated successfully  into csv file. ";
	     }
	     else
	     {
	    	 strActualResult = "Data is not created and updated into csv file." + strErrMsg_AppLib;
	     }
		 reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		   
		 //*********************************//
		 //***** Step 3 - Login to DC. *****//
		 //*********************************// 
		 emailId = dicOutput.get("Email");
		 password = dicOutput.get("Password");
		 isEventSuccessful = Login(emailId , password);
 }	

}
