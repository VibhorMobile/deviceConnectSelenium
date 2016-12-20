package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-1649
 */
public class _770_Verify_by_default_two_roles_Administrator_Tester_should_be_visible extends ScriptFuncLibrary {
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String  strActualResult = "",pageHeader;
 

	public final void testScript() throws InterruptedException
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();

			//**********************************************************//
			// Step 2 - Go to Users Page
			//**********************************************************//  
			GoToUsersPage();
			
			//**********************************************************//
			// Step 3 - Go to UserRoles Page.
			//**********************************************************//  
			GoToUserRolesPage();
			
			//**********************************************************//
			// Step 4 - Verify Administrator Role is available
			//**********************************************************//  	
			strStepDescription="Verified Administrator role is available on page. ";
			strExpectedResult="Administartaor role is available";
			isEventSuccessful=verifyRoleExistence("Administrator");
			if(isEventSuccessful){
				strActualResult="Administrator role available";
			}
			else{
				strActualResult="Administrator role not available on page";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//**********************************************************//
			// Step 4 - Verify Tester Role is available
			//**********************************************************//  	
			strStepDescription="Verified Tester role is available on page. ";
			strExpectedResult="Tester role is available";
			isEventSuccessful=verifyRoleExistence("Tester");
			if(isEventSuccessful){
				strActualResult="Tester role available";
			}
			else{
				strActualResult="Tester role not available on page";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			 


		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}



}
