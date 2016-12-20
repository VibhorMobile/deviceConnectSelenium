package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2190
 */
public class _380_Verify_that_there_should_not_be_column_header_for_Connect_in_device_table_and_Delete_in_application_table extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String text = "dummy text";
	private String ConnectHeaderInDevicesTable = "//table[@class='deviceList table data-grid']/tbody/tr/th[5]";
	private String DeleteHeaderInApplicationsTable = "//table[@class='applicationList table data-grid application-list-container']/thead/tr/th[5]";

	public final void testScript()
	{
		// Step 1 : login to deviceConnect with valid user credentials.
		isEventSuccessful = Login();

		// Step 2 : Verify there is no column header for Connect in devices list view.
		strstepDescription = "Verify there is no column header for Connect in devices list view.";
		strexpectedResult = "There should be no column header for Connect in devices list view.";
		isEventSuccessful = PerformAction(ConnectHeaderInDevicesTable, Action.WaitForElement, "5");
		
		text = GetTextOrValue(ConnectHeaderInDevicesTable, "text");
		isEventSuccessful = (strErrMsg_GenLib.equals("")) && (text.trim().equals(""));
		if (isEventSuccessful)
		{
			strActualResult = "There is no column header for Connect in devices list view.";
		}
		else
		{
			strActualResult = "There is column header for 'Connect' : '" + text + "'.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Step 3 : Go to Applications page.
		isEventSuccessful = GoToApplicationsPage();
		
		// Step 4 : Verify there is no column header for Connect in devices list view.
		strstepDescription = "Verify there is no column header for Connect in devices list view.";
		strexpectedResult = "There should be no column header for Connect in devices list view.";
		text = "dummy text 2"; // Initializing so that if the text is not retrieved using function getTextOrValue, then validation should not pass incorrectly.
		text = GetTextOrValue(DeleteHeaderInApplicationsTable, "text");
		isEventSuccessful = (strErrMsg_GenLib.equals("")) && (text.trim().equals(""));
		if (isEventSuccessful)
		{
			strActualResult = "There is no column header for Connect in devices list view.";
		}
		else
		{
			strActualResult = "There is column header for 'Connect' : '" + text + "'.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}