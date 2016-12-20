package com.test.java;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-765
 */
public class _741_Applications_tab_Verify_that_Version_Column_name_is_as_per_the_column_selection_menu extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	private String installedApps = "", deviceName="", xpathWithIndex="";
	String serverIP=dicCommon.get("ApplicationURL");
	String appName="PhoneLookup"; 
	int columnCount;
	Object[] values = new Object[5]; 
	Object[] appvalues = new Object[5];


	public final void testScript() throws InterruptedException, IOException
	{

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strStepDescription = "Login to deviceConnect with valid user.";
		strExpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login();


		//*************************************************************//                     
		// Step 2 : Navigate to Applications page
		//*************************************************************//  
		isEventSuccessful=GoToApplicationsPage();
		
		String expectedColumnName="Latest Version"; //---------------Setting expected column name
		

		//*************************************************************//                     
		// Step 3 : Verify expectedColumnName exists in column selection menu
		//*************************************************************//  
		strStepDescription="Verify Latest Version column is present in selection menu.";
		strActualResult="Latest Version column is available in selection menu.";
		
		//Verify Settings button is displayed.
		if(PerformAction("btnSettings_Devices", Action.isDisplayed))
		{
			//Verify Settings button is clicked.
			if(PerformAction("btnSettings_Devices", Action.Click))
			{
				//Verify Columns checkboxes is displayed.
				if(PerformAction("drpSettings_Devices", Action.isDisplayed)) 
				{

					isEventSuccessful=PerformAction(dicOR.get("eleSpecificColumn_ColumnDropdown").replace("__COLUMNNAME__",expectedColumnName), Action.isDisplayed);
					if (isEventSuccessful)
					{
						isEventSuccessful=PerformAction(dicOR.get("eleSpecificColumn_ColumnDropdown").replace("__COLUMNNAME__",expectedColumnName), Action.SelectCheckbox);
						if (isEventSuccessful)
							strActualResult="Successfully selected "+expectedColumnName +" column checkbox.";
						else
							strActualResult="Unable to select "+expectedColumnName+ " column checkbox.";
					}
					else
					{
						strActualResult=expectedColumnName+" column checkbox not available in column selection.";
					}

				}
				else
				{
					strActualResult="Columns check boxes is not displayed.";
				}
			}
			else
			{
				strActualResult="Settings button is not clicked on devices index page.";
			}
		}
		else
		{
			strActualResult="Settings button is not displayed on devices index page.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		

		//*************************************************************//                     
		// Step 3 : Verify expectedColumnName exists in column headers available on app list page
		//*************************************************************//  
		
		strStepDescription="Verify Latest Version column name is same as per column selection menu.";
		strExpectedResult="Column names are same in both column and selection menu. (Latest Version)";
		
		List<String> columnList=getColumnsValue(dicOR.get("eleAppTableHeader"));
		
		isEventSuccessful=columnList.contains(expectedColumnName);
		if (isEventSuccessful)
			strActualResult="Latest Version Column name is same as column selection menu. Name is: "+expectedColumnName;
		else
			strActualResult="Latest Version column does exits on App list table. Column available are : "+columnList;
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
	}
}