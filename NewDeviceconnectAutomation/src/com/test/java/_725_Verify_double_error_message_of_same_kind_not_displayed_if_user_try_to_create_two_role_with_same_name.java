package com.test.java;

import java.util.ArrayList;
import java.util.List;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1804
 */
public class _725_Verify_double_error_message_of_same_kind_not_displayed_if_user_try_to_create_two_role_with_same_name extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",outputText="";
	private String oldDeviceName="",Appname="";
	List<String> roles=new ArrayList<String>(); 
	List<String> userroles=new ArrayList<String>(); 
	
	public final void testScript()
	{
		//*********************************//
		// Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();

		//*************************************************************//                     
		// Step 2 : Go to user page extract  roles list .
		//*************************************************************// 
		isEventSuccessful=GoToUsersPage();
		if(isEventSuccessful)
		{
			strstepDescription = "Verify multiple errors did not displayed.";
			strexpectedResult = "Multiple errors should not be displayed on UI.";
			isEventSuccessful=verifyDoubleRoleError("Testing");
			if(isEventSuccessful)
			{
				strActualResult = "Multiple errors did not displayed on UI..";
			}
			else
			{
				strActualResult="Multiple errors displayed on UI.";
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
	}	
}