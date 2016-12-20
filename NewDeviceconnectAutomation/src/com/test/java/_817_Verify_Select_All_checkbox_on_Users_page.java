package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Jira Test Case Id: QA-2026
 */
public class _817_Verify_Select_All_checkbox_on_Users_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String oldDeviceName="",Appname="";
	String [] Entitlement={"User area access","System configuration area access"};
	Boolean [] value={true, true};
	String [] role={"zTemporary"};

	public final void testScript()
	{
		//*********************************//
		// Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();

		//*************************************************************//                     
		// Step 2 : Go to user page and add new role .
		//*************************************************************// 
		isEventSuccessful=GoToUsersPage();
		
		
		//*************************************************************//                     
		// Step 3 : Click to select all users and verify all users selected or not.
		//*************************************************************// 
		if(isEventSuccessful)
		{
			isEventSuccessful=selectAllUsersCheckbox_Users();
		}
		
		
		
	}	
}