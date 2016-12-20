package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1656
 */
public class _713_Verify_more_than_one_role_can_be_selected_for_user_during_creation extends ScriptFuncLibrary {
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String emailID,deviceName,deviceStatus,reservedByName,deviceStatus_DI,reservedByName_DI,recurringIcon;

	String weekday[] = {"TH"};




	public final void testScript() throws InterruptedException, IOException
	{
		try{
			String EmailAddress = dicCommon.get("testerEmailAddress");
			String Password = dicCommon.get("testerPassword");

			//*************************************************************//                     
			// Step 1 : login to deviceConnect with admin user.
			//*************************************************************//  


			isEventSuccessful = Login();

			
			//*************************************************************/                     
			// Step 2 : Go to Users Page and Click on Create User
			//*************************************************************//
			isEventSuccessful=GoToUsersPage();
			if(isEventSuccessful){
			GoToCreateUserPage();
			}
			else
			{
				strActualResult = "Navigate to Users Page---" + strErrMsg_AppLib;
			}
		
			//*************************************************************/                     
			// Step 3 : Create User with Multiple Roles
			//*************************************************************//
			strstepDescription="Create User with Multiple Roles";
			strExpectedResult="User with Multiple Roles Created Successfully";
			String [] userType={"Administrator","Tester"};
			isEventSuccessful=createUser("", "", "deviceconnect",userType , true);
			if(isEventSuccessful){
				strActualResult="User with Multiple role created Successfully";
			}
			else{
				strActualResult="User with Multiple role not created ";
			}
			 
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

}
