package com.test.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;


public class _383_Verify_install_app_table_column_names extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "";
//	private String[] RequiredTableHeaders = {"Name", "Version", "Build", ""};
	private ArrayList<String> RequiredTableHeaders = new ArrayList<String>(Arrays.asList("Name", "Version", "Build", ""));
	private java.util.List<WebElement> ActualColumnHeaders = new java.util.ArrayList<WebElement>();
	private String IncorrectHdrIndex = "";
	private List<String> lstHeaderNames = new ArrayList<String>();

	public final void testScript()
	{
		//Step 1 - Launch deviceConnect and verify homepage.
		isEventSuccessful = Login();

		// Step 3 : Select Available from Status dropdown
		isEventSuccessful = selectStatus("Available");

		//Step 3 - Select Android selection filter
		isEventSuccessful =  selectPlatform("Android");		

		//Step 4 - Go to device details page of first displayed android device
		isEventSuccessful = SelectDevice("first");
		if (isEventSuccessful)
		{
			strActualResult = "Device Details page is opened.";
		}
		else
		{
			strActualResult = "SelectDevice()-- " + strErrMsg_AppLib;
		}
		reporter.ReportStep("Go to device details page of first displayed android device.", "'Device Details' page should be opened.", strActualResult, isEventSuccessful);

		//Step 5 - Verify Installed Applications Table is displayed
		if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
		{
			PerformAction("DeviceDetails_Install_App_heading", Action.WaitForElement, "10");
			isEventSuccessful = PerformAction("DeviceDetails_Install_App_heading", Action.isDisplayed);
		}
		else
		{
			isEventSuccessful = PerformAction("DeviceDetails_Install_App_heading", Action.isDisplayed);
		}
     	   if (isEventSuccessful)
		   {
			isEventSuccessful = PerformAction("DeviceDetails_Install_App_Table", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Install Application table rows are displayed.";
			}
			else
			{
				strActualResult = "Install Application table rows are not displayed on Device Details Page.";
			}
		  }
		else
		{
			strActualResult = "Install Application header is not displayed on Device Details Page.";
		}
		reporter.ReportStep("Verify Install Applications Table", "'Installed Applications' table should be displayed.", strActualResult, isEventSuccessful);

		// verify number of columns is correct
		verifyNumberOfColumns();

		// Verify Column headers are correct
		verifyHeadersAreCorrect();

		//Step 6 - Navigate to Devices page
		isEventSuccessful = GoToDevicesPage();

		//Step 7 - Select iOS from Platform filter
		if (isEventSuccessful)
			isEventSuccessful =  selectPlatform("iOS");
			lstHeaderNames.clear();
		
		//Step 8 - Select First available iOS Device
		isEventSuccessful = SelectDevice("first");
		if (isEventSuccessful)
		{
			strActualResult = "Device Details page is opened.";
		}
		else
		{ 
			strActualResult = "SelectDevice() -- " + strErrMsg_AppLib;
		}
		reporter.ReportStep("Go to details page of first available device displayed.", "'Device Details' page should be opened.", strActualResult, isEventSuccessful);

		//Step 9 - Verify Installed Applications Table is displayed
		if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
		{
			PerformAction("DeviceDetails_Install_App_heading", Action.WaitForElement, "10");
			isEventSuccessful = PerformAction("DeviceDetails_Install_App_heading", Action.isDisplayed);
		}
		else
		{
			isEventSuccessful = PerformAction("DeviceDetails_Install_App_heading", Action.isDisplayed);
		}
		//isEventSuccessful = PerformAction("DeviceDetails_Install_App_heading", Action.isDisplayed);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("DeviceDetails_Install_App_Table", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Install Application table rows are displayed.";
			}
			else
			{
				strActualResult = "Install Application table rows are not displayed on Device Details Page.";
			}
		}
		else
		{
			strActualResult = "Install Application header is not displayed on Device Details Page.";
		}
		reporter.ReportStep("Verify Install Applications Table", "'Installed Applications' table should be displayed.", strActualResult, isEventSuccessful);

		// Step 10 : Verify that number of columns is correct
		verifyNumberOfColumns();

		//Step 10 - Verify Install Application table column names
		verifyHeadersAreCorrect();
	}


	//******************* Script level functions **********************************************************//

	/*public final void selectAvailableFilter()
	{
		isEventSuccessful =  selectStatus("Available");
		isEventSuccessful = SelectFromFilterDropdowns("status", "available", "devices", "grid");
		if (isEventSuccessful)
		{
			strActualResult = "Only available devices are displayed.";
		}
		else
		{
			strActualResult = "SelectFromFilterDropdowns()--" + strErrMsg_AppLib;
		}
		reporter.ReportStep("Select available from platform filter.", "Only available devices should be displayed.", strActualResult, isEventSuccessful);
	}*/

	public final void verifyNumberOfColumns() 
	{
		//Step 3 - Verify number of Table Column headers is correct.
		ActualColumnHeaders = getelementsList("DeviceDetails_Install_col_names");
		if (ActualColumnHeaders.size() != 0)
		{
			for(WebElement eleHeader : ActualColumnHeaders)   //Putting names of all columns in list lstHeaderNames
				lstHeaderNames.add(eleHeader.getText());

			for(int i=0; i<lstHeaderNames.size();i++)
			{
				String temp = lstHeaderNames.get(i);
				System.out.println("columns names are ---  " + temp );
			}
			
    		System.out.println();
			System.out.println();
			System.out.println(lstHeaderNames.size());
			System.out.println(RequiredTableHeaders.size());
			System.out.println();
			System.out.println();
			
				isEventSuccessful = lstHeaderNames.size()==RequiredTableHeaders.size();
			if (isEventSuccessful)
				strActualResult = "Number of columns headers in 'Installed' applications table is correct i.e. " + RequiredTableHeaders.size();
			else
				strActualResult = "Number of columns are not equal to " + RequiredTableHeaders.size();
		}
		else
		{
			strActualResult = "getelementsList() -- Could not get column headers on 'Installed' applications table.";
		}
		reporter.ReportStep("Verify number of 'Installed' applications Table Column headers is correct.", " Number of 'Installed' applications table headers are correct i.e. " + RequiredTableHeaders, strActualResult, isEventSuccessful);
	}

	public final void verifyHeadersAreCorrect()
	{
		try
		{
			for (int i = 0; i < ActualColumnHeaders.size(); i++)
			{
				isEventSuccessful = ActualColumnHeaders.get(i).getText().equals(RequiredTableHeaders.get(i));
				if (!isEventSuccessful)
				{
					IncorrectHdrIndex = IncorrectHdrIndex + ", " + ActualColumnHeaders.get(i).getText();
				}
			}
			if (IncorrectHdrIndex.equals(""))
			{
				isEventSuccessful = true;
				strActualResult = "Correct header is displayed for all columns in applications table.";
			}
			else
			{
				isEventSuccessful = false;
				strActualResult = "Header is incorrect for the following columns : \r\n" + IncorrectHdrIndex;
			}
		}
		catch (RuntimeException e)
		{
			isEventSuccessful = false;
			strActualResult = "Error in 'for loop' for iterating over application table headers. : " + e.getMessage();
		}
		reporter.ReportStep("Verify 'Installed' table column headers are correct.", "Headers of all the columns should be correct.", strActualResult, isEventSuccessful);
	}

	//public void clearList()
	//{
	//    try
	//    {
	//        ActualColumnHeaders.Clear();
	//        isEventSuccessful = true;
	//    }
	//    catch (Exception e)
	//    {
	//        isEventSuccessful = false;
	//        strActualResult = e.Message;
	//    }
	//    reporter.ReportStep("Clear the list containing runtime values of headers on Installed applications table.", "List should be cleared.", strActualResult, isEventSuccessful);
	//}
}