package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-686
 */
public class _719_Verify_graph_should_be_located_on_the_reports_section_of_the_device_index_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();

		//******************************************************************//
		//*** Step 2 : navigate to report page. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToReportPage("2");
		}
		
		
		
		//******************************************************************//
		//*** Step 3 : Verify graph present on page. *****//
		//******************************************************************//
		if(isEventSuccessful)
		{
			strstepDescription = "Verify graph present on page";
			strexpectedResult =  "Graph should be present and displayed on page.";
			waitForPageLoaded();
			isEventSuccessful=PerformAction("operatinSYSReport",Action.Click);
			if(isEventSuccessful)
			{
				waitForPageLoaded();
				PerformAction("browser",Action.WaitForPageToLoad);
				isEventSuccessful=PerformAction("pieChart",Action.Exist);
				if(isEventSuccessful)
				{
					strActualResult = "Graph displayed on the page.";
				}
				else
				{
					strActualResult = "Graph did not displayed on the page.";
				}
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}
		
	}	
}