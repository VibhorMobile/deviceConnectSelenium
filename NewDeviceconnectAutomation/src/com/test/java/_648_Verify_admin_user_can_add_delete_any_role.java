package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1645,1640
 */
public class _648_Verify_admin_user_can_add_delete_any_role extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	Object[] Values = new Object[5]; 
	
	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful = Login();


		//*************************************************************//                     
		// Step 2 : Go to user page and add new role .
		//*************************************************************// 
		isEventSuccessful=GoToUsersPage();
		if(isEventSuccessful)
		{
			strstepDescription = "Verify admin can add role";
			strexpectedResult = "Admin user should be able to add role.";
			isEventSuccessful=addNewRole("zTemporary");
			if(isEventSuccessful)
			{
				strActualResult = "Admin user is able to add new role: zTemporary";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		//*************************************************************//                     
		// Step 3 : Go to user page and delete role .
		//*************************************************************// 
		if(isEventSuccessful)
		{
			strstepDescription = "Verify admin can delete role";
			strexpectedResult = "Admin user should be able to delete role.";
			isEventSuccessful= deleteRole("zTemporary");
			if(isEventSuccessful)
			{
				strActualResult = "Admin user is able to delete role: zTemporary";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
			
		
		
	}
}