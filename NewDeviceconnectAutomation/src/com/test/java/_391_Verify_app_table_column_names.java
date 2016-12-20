package com.test.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2174
 */
public class _391_Verify_app_table_column_names extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";
	private ArrayList<String> RequiredTableHeaders = new ArrayList<String>(Arrays.asList("Name", "OS", "Latest Version", "Last Updated", ""));
	private List<WebElement> ActualColumnHeaders = new ArrayList<WebElement>();
	private List<String> lstHeaderNames = new ArrayList<String>();
	private String IncorrectHdrIndex = "";

	public final void testScript()
	{
		//Step 2 - Login to deviceConnect
		isEventSuccessful = Login(); 
		
		//Step 3 - Navigate to Applications Page
		isEventSuccessful = GoToApplicationsPage();
		
		// Step 4 - Verify number of application Table Column headers is correct.
		ActualColumnHeaders = getelementsList("eleAppTableHeader");
		if (ActualColumnHeaders.size() != 0)
		{
			for(WebElement eleHeader : ActualColumnHeaders)   //Putting names of all columns in list lstHeaderNames
				lstHeaderNames.add(eleHeader.getText());
			
			isEventSuccessful = lstHeaderNames.size()==RequiredTableHeaders.size();
			if (isEventSuccessful)
				strActualResult = "Number of columns headers in applications table is correct i.e. " + RequiredTableHeaders.size();
			else
				strActualResult = "Number of columns are not equal to " + RequiredTableHeaders.size() + " but " + lstHeaderNames.size();
		}
		else
			strActualResult = "getelementsList() -- Could not get column headers on applications table.";		
		
		reporter.ReportStep("Verify number of application Table Column headers is correct.", " Number of application table headers are correct i.e. " + RequiredTableHeaders, strActualResult, isEventSuccessful);
		if (!isEventSuccessful)  // Do not perform the steps below if the number of columns is not correct
			return;

		// Step 4 : Verify application table column headers are correct
		isEventSuccessful = lstHeaderNames.equals(RequiredTableHeaders);
		if(isEventSuccessful)
			strActualResult = "Column headers are displayed in the correct order.";
		else
			strActualResult = "Columns do not match the required sequence and values : 'Name', 'OS', 'Latest Version', 'Last Updated',  ' '";
		
		reporter.ReportStep("Verify application table column headers are correct.", "Headers of all the columns should be correct.", strActualResult, isEventSuccessful);
	}
}