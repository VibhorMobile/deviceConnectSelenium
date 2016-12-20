package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1643
 */
public class _653_Verify_none_of_the_user_can_modify_Administrator_role extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	Object[] Values = new Object[5]; 
	String [] Entitlement={"Device application management","Device reboot"};
	Boolean [] value={true, true};
	
	public final void testScript() throws InterruptedException, IOException
	{
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful = Login();

		
		//*************************************************************/                     
		// Step 5 : Go to user page and set entitlement .
		//*************************************************************//
		isEventSuccessful=GoToUsersPage();
		if(isEventSuccessful)
		{
			strstepDescription = "Set entitlements for role: Tester";
			strexpectedResult = "User should be able to set entitlements.";
			isEventSuccessful=setUserRoleSettings("Administrator", Entitlement, value);
			if(isEventSuccessful)
			{
				strActualResult = "User is able to set entitlements.";
				isEventSuccessful=false;
			}
			else
			{
				strActualResult="User cannot change Administrator role.";
				isEventSuccessful=true;
			}
			reporter.ReportStep(strstepDescription ,strexpectedResult , strActualResult, isEventSuccessful);
		}
		
		
	}
}