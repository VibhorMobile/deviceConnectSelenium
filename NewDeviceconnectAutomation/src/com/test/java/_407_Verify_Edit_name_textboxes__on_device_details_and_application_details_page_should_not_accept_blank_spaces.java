package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ApplicationLibrary;
import com.common.utilities.GenericLibrary; 
import com.common.utilities.GenericLibrary.Action;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2164
 */

public class _407_Verify_Edit_name_textboxes__on_device_details_and_application_details_page_should_not_accept_blank_spaces extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "", strStepDescription = "", strExpectedResult = "", Appname ="";
	Object[] arrResult;

	public final void testScript()
	{
		//Step 1 - Login to deviceConnect
		isEventSuccessful = Login();

		
		//Step 5 - Navigate to applications Page
		isEventSuccessful = PerformAction("hdrApplicationLink", Action.Click);
		reporter.ReportStep("Verify Applications Page is loaded", "'Applications' page should be loaded.", "Applications' page is loaded.", isEventSuccessful);
		
		//Step 6 - Select first application and navigate to application details page
		isEventSuccessful = SelectApplication("first");
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("lnkEdit_ApplicationDetailsPage", Action.Click);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("txtAppName_appDetails", Action.isDisplayed);
				if (isEventSuccessful)
				{
					isEventSuccessful = PerformAction("txtAppName_appDetails", Action.Clear);
					if (isEventSuccessful)
					{
						isEventSuccessful = PerformAction("txtAppName_appDetails", Action.Type, "     ");
						if (isEventSuccessful)
						{
							isEventSuccessful = PerformAction("lnkSaveAppName_ApplicationDetailsPage", Action.Click);
							if (isEventSuccessful)
							{
								 Appname = GetTextOrValue("eleAppname_ApplicationDetailsPage","text");
								if (!Appname.isEmpty())
								{
									strActualResult = "Application name does not accept blank spaces & default deleted app name is displayed.";
								}
								else
								{
									strActualResult = "Application name accepts blank spaces.";
								}
							}
							else
							{
								strActualResult = "Not able to click on Save button.";
							}
						}
						else
						{
							strActualResult = "Not able to type blank spaces in input name.";
						}
					}
					else
					{
						strActualResult = "Appname is empty or could not get the appname using GetTextOrValue().";
					}
				}
				else
				{
					strActualResult = "Editable textbox is not displayed after clicking on 'Edit' link.";
				}
			}
			else
			{
				strActualResult = "Not able to click on edit button.";
			}
		}
		else
		{
			strActualResult = "Not able to select first application.";
		}
		
		
		reporter.ReportStep("Verify application name does not accept blank spaces", "'application Name' should not accept blank spaces.", strActualResult, isEventSuccessful);


	}
	
	}
	