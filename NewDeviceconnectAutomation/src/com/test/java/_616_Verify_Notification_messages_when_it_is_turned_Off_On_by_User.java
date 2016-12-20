package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */

public class _616_Verify_Notification_messages_when_it_is_turned_Off_On_by_User extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	String [] filter={"iOS"};

	public final void testScript() throws InterruptedException
	
	{
		
		// Step 1 : login to deviceConnect with valid user credentials.
		isEventSuccessful = Login();

		// Step 2 : Navigate to System tab.
		isEventSuccessful =  GoToSystemPage();
		
		isEventSuccessful = PerformAction("lnkiOSMgmnt", Action.Click);
		if(isEventSuccessful)
		{
			isEventSuccessful = PerformAction("//label[text()[normalize-space()='Disable notices about iOS deviceControl issues']]/input", Action.SelectCheckbox);
			if(isEventSuccessful)
			{
				isEventSuccessful = GoToApplicationsPage();
				isEventSuccessful= selectCheckboxes_DI(filter, "chkPlatform_Devices"); 
				if(isEventSuccessful)
				{
					isEventSuccessful = searchDevice_DI("deviceControl");
		 
					strStepDescription = "Verify_Notification_messages_when_it_is_turned_Off__by_User";
					strExpectedResult =  "Verify_Notification_messages_when_it_is_turned_Off__by_User";
				 
					isEventSuccessful = PerformAction(dicOR.get("eleInstallAppDropdown")+"[1]", Action.ClickUsingJS);
					isEventSuccessful = PerformAction(dicOR.get("eleDeleteOption_AppPage"), Action.ClickUsingJS);
					if(isEventSuccessful)
					{
						isEventSuccessful = PerformAction("//button[text()='Delete all']", Action.ClickUsingJS);
						isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
						isEventSuccessful = !PerformAction("deviceControlerror_Anypage", Action.isDisplayed);
						if(isEventSuccessful)
						{
							strActualResult = "deviceControlerror message is not being dispalyed on pages."; 
						}
						else
						{
							strActualResult = "deviceControlerror message is being dispalyed on pages.";
						}
					}
					else
					{
						strActualResult = "Could not clicked on delete button.";
			}
				}
				else
				{
					strActualResult = "Could not checked iOS platform on APpplications page.";
				}
			}
			else
			{
				strActualResult = "Could not checked 'Disable notices about iOS deviceControl issues' check-box .";	
			}
		}
		else
		{
			strActualResult = "Could not clicked on lnkiOSMgmnt link.";
		}
	  

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);


		isEventSuccessful =  GoToSystemPage();
		isEventSuccessful = PerformAction("lnkiOSMgmnt", Action.Click);
		isEventSuccessful = PerformAction("//label[text()[normalize-space()='Disable notices about iOS deviceControl issues']]/input", Action.DeSelectCheckbox);
		isEventSuccessful = GoToApplicationsPage();
		isEventSuccessful = PerformAction("deviceControlerror_Anypage", Action.isDisplayed);
		if(isEventSuccessful)
		{
			strActualResult = "deviceControlerror message is being dispalyed on pages."; 
		}
		else
		{
			strActualResult = "deviceControlerror message is not being dispalyed on pages.";
		}

		strStepDescription = "Verify_Notification_messages_when_it_is_turned_On__by_User";
		strExpectedResult =  "Verify_Notification_messages_when_it_is_turned_On__by_User";
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

	}
}
