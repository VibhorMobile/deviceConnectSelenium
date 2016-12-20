package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-362
 */

public class _401_Verify_name_textbox_accepts_every_type_of_input_special_characters extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";
	private boolean clickable = false;
	private boolean flag = false;
	private int errorAppIndex = 0;
	private String AppNameInput = "aA10!@#$%^&*()_+-={}|[]\\;'./,{}|:\"<>?";

	public final void testScript()
	{
		//Step 1 - Launch deviceConnect and verify homepage.
		genericLibrary.LaunchWebDriver();
		isEventSuccessful = PerformAction("imgDCLogo", Action.Exist);
		if (isEventSuccessful)
		{
			strActualResult = "Navigated to " + dicCommon.get("ApplicationURL") + ". deviceConnect homepage is opened.";
		}
		else
		{
			strActualResult = "deviceConnect homepage is not opened.";
		}
		reporter.ReportStep("Launch deviceConnect and verify homepage.", "deviceConnect homepage should be opened.", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}

		//Step 2 - Login to deviceConnect
		isEventSuccessful = LoginToDC();
		if (isEventSuccessful)
		{
			strActualResult = "User - " + dicCommon.get("EmailAddress") + " is logged in to deviceConnect.";
		}
		else
		{
			strActualResult = "User - " + dicCommon.get("EmailAddress") + " is not logged in to deviceConnect.";
		}
		reporter.ReportStep("Login to deviceConnect", "User should be logged in.", strActualResult, isEventSuccessful);

		//Step 3 - Navigate to Device Menu -> Application page
		/*isEventSuccessful = selectFromMenu("Applications", "eleApplicationsHeader");
		if (isEventSuccessful)
		{
			strActualResult = "Applications Page is opened.";
		}
		else
		{
			strActualResult = "Application Page is not opened.";
		}
		reporter.ReportStep("Verify Applications Page is loaded", "'Applications' Page should be loaded.", strActualResult, isEventSuccessful);*/

		
		
		//////////////////////////////////////////////////////////////////////////
		//Step 3 - Navigate to Applications Page
		//////////////////////////////////////////////////////////////////////////
		
		isEventSuccessful = PerformAction("hdrApplicationLink", Action.Click);
		
		
		// Step 4 : Go to details page of any application
		isEventSuccessful = SelectApplication("first");
		if (isEventSuccessful)
		{
			strActualResult = "Details page of first displayed application is displayed successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		//Step 4 - Edit the application name
		String actualAppName = GetTextOrValue("eleAppNameDisplay", "text");
		isEventSuccessful = !actualAppName.equals("");
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("lnkEdit_ApplicationDetailsPage", Action.Click);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleAppNameInputBox", Action.isDisplayed);
				if (isEventSuccessful)
				{
					isEventSuccessful = PerformAction("eleAppNameInputBox", Action.Clear);
					if (isEventSuccessful)
					{
						isEventSuccessful = PerformAction("eleAppNameInputBox", Action.Type, AppNameInput);
						if (isEventSuccessful)
						{
							isEventSuccessful = PerformAction("lnkSaveAppName_ApplicationDetailsPage", Action.Click);
							if (isEventSuccessful)
							{
								strActualResult = "changed the app name to " + AppNameInput + ". ";
							}
							else
							{
								strActualResult = "Could not click on 'Save' link.";
							}
						}
						else
						{
							strActualResult = "Could not enter special characters in application name field.";
						}
					}
					else
					{
						strActualResult = "Could not clear the application name.";
					}
				}
				else
				{
					strActualResult = "App name edit box not displayed after clicking on 'Edit' link.";
				}
			}
			else
			{
				strActualResult = "Could not click on 'Edit' link.";
			}
		}
		else
		{
			strActualResult = "Application name is empty string i.e. \"\" ";
		}

		reporter.ReportStep("Enter string containing special characters in application name input box and save.", "Special characters should be allowed.", strActualResult, isEventSuccessful);

		//Step 5 - Verify that application name with special characters got saved successfully
		isEventSuccessful = PerformAction("eleAppNameDisplay", Action.isDisplayed);
		if (isEventSuccessful)
		{
			String editedAppName = GetTextOrValue("eleAppNameDisplay", "text");
			isEventSuccessful = (!editedAppName.equals("")) && (strErrMsg_AppLib.equals(""));
			if (isEventSuccessful)
			{
				isEventSuccessful = (AppNameInput.equals(editedAppName));
				if (isEventSuccessful)
				{
					strActualResult = "Application Name got saved.";
					System.out.println("APPLICATION NAME GOT SAVED");
					
				}
				else
				{
					strActualResult = "Application Name not saved i.e. " + editedAppName;
				}
			}
			else
			{
				strActualResult = "Could not get appname saved.";
			}
		}
		else
		{
			strActualResult = "Application name input box not replaced by non-editable label after clicking on 'Save' link.";
		}
		reporter.ReportStep("Verify Application Name with special characters got saved", "'Application Name' with special characters should get saved.", strActualResult, isEventSuccessful);

		//Step 5 - Change the Application Name to previous original name actualAppName
		isEventSuccessful = PerformAction("lnkEdit_ApplicationDetailsPage", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleAppNameInputBox", Action.isDisplayed);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleAppNameInputBox", Action.Clear);
				if (isEventSuccessful)
				{
					isEventSuccessful = PerformAction("eleAppNameInputBox", Action.Type, actualAppName);
					if (isEventSuccessful)
					{
						isEventSuccessful = PerformAction("lnkSaveAppName_ApplicationDetailsPage", Action.Click);
						if (isEventSuccessful)
						{
							strActualResult = "Changed the app name to " + AppNameInput + ". ";
						}
						else
						{
							strActualResult = "Could not click on 'Save' link.";
						}
					}
					else
					{
						strActualResult = "Could not enter special characters in application name field.";
					}
				}
				else
				{
					strActualResult = "Could not clear the application name.";
				}
			}
			else
			{
				strActualResult = "App name edit box not displayed after clicking on 'Edit' link.";
			}
			
		}
		
		strActualResult = "Could not click on 'edit' link in front of application name on application details page.";
		reporter.ReportStep("Post-condition : Change the name of app to original name.", "Application's name should be changed back to original name.", strActualResult, isEventSuccessful);
	}
}