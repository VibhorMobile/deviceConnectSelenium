package com.test.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-362
 */
public class _702_Verify_admin_can_create_User_with_special_characters extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", deviceName = "";
	private Object[] firstdeviceSelected = null;
	
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
		// Step 2 : Navigate to Users Page
		//*************************************************************// 
		strstepDescription = "Navigate to Users page & create User with special characters";
		strexpectedResult = "Admin User should be able to create User with special character";
		isEventSuccessful = GoToUsersPage(); 
		if(isEventSuccessful)
		{
			isEventSuccessful = createUser("@#%^&*)(^%", "µ??§,?", "Äääbc@deviceconnect.com", "deviceconnect");
			if(isEventSuccessful)
			{
				GoToUsersPage();
				//PerformAction("",Action.WaitForElement,"");
 				isEventSuccessful = Searchuser_Users(dicOutput.get("EmailID"));
				if(isEventSuccessful)
					strActualResult = "User got created with special character. ";
				else
					strActualResult = strErrMsg_AppLib;				
			}
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}
}