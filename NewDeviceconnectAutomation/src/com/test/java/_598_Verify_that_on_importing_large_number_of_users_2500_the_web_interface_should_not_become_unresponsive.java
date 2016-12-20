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

public class _598_Verify_that_on_importing_large_number_of_users_2500_the_web_interface_should_not_become_unresponsive extends ScriptFuncLibrary

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
			     isEventSuccessful = importUser(2500);
				   
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
