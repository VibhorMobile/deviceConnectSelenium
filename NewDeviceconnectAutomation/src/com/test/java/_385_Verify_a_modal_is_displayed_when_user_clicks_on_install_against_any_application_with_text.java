package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2181
 */
public class _385_Verify_a_modal_is_displayed_when_user_clicks_on_install_against_any_application_with_text extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String text = "";
	private String applicationName = "";

	public final void testScript()
	{
		//Step 1 - Login to deviceConnect
		isEventSuccessful = Login(); 
		
		// Step 2 : Go to applications page.
		isEventSuccessful=GoToApplicationsPage();
		 if (isEventSuccessful)
		 {
			 isEventSuccessful = PerformAction("eleNoTableRowsWarning", Action.isNotDisplayed);
			 if (isEventSuccessful)
			 {
				 isEventSuccessful = PerformAction("eleListViewTable", Action.WaitForElement);
				 if (isEventSuccessful)
				 {
					 strActualResult = "Applications table displayed successfully.";
				 }
				 else
				 {
					 strActualResult = "Applications table not displayed.";
				 }
			 }
			 else
			 {
				 strActualResult = "no applications uploaded in dC box.";
			 }
		 }
		 else
		 {
			 strActualResult = "Could not navigate to applications page.";
		 }

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Step 3 : Verify 'Install application' modal is displayed after clicking on 'Install' button.
		strstepDescription = "Verify 'Install application' modal is displayed after clicking on 'Install' button.";
		strexpectedResult = "'Install Application' dialog should be displayed";
		applicationName = GetTextOrValue(dicOR.get("eleAppNameAppTable").replace("__APP_INDEX__", "1"), "text");
		if ((!(applicationName ==null )) || (applicationName== " "))
		{
			isEventSuccessful = PerformAction(dicOR.get("btnInstall_appsPage") + "[1]", Action.isDisplayed);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction(dicOR.get("btnInstall_appsPage") + "[1]", Action.Click);
				if (isEventSuccessful)
				{
					isEventSuccessful = PerformAction(dicOR.get("eleHeader").replace("__EXPECTED_HEADER__", "Install Application"), Action.WaitForElement);
					if (isEventSuccessful)
					{
						strActualResult = "Install app dialog is displayed successfully.";
					}
					else
					{
						strActualResult = "Install application dialog is not displayed after clicking on 'Install' button in front of first application.";
					}
				}
				else
				{
					strActualResult = "Unable to click on install button.";
				}
			}
			else
			{
				strActualResult = "Install button for first application is not displayed.";
			}
		}
		else
		{
			strActualResult = "Either application name of first application displayed is empty OR script could not fetch it from web UI.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//Step 4 : Verify text on install application dialog : "Install will overwrite any current installation of [appName]. Install [appName] on device?".
		strstepDescription = "Verify text on install application dialog :::   'Install will overwrite any current installation of [appName]. Install [appName] on device?'";
		strexpectedResult = "Text should be properly displayed on the install application dialog.";
		text = GetTextOrValue("eleWarningMsg_InstallApplication", "text");
		String t = "Install will overwrite any current installation of " + applicationName + ". Install " + applicationName + " on device?";
		isEventSuccessful = text.contains(t);
		if (isEventSuccessful)
		{
			strActualResult = "Correct message is displayed on the install application dialog, i.e. '" + text + "'";
		}
		else
		{
			strActualResult = "Text displayed is not correct. It is : " + text;
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}