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
/*
 * JIRA ID --> QA-1344
 */
public class _601_Verifying_the_Left_Navigation_for_default_values_of_Date_Event_Type_and_Order_when_clicking_history_tab_link_on_device_details_page  extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" , DeviceName , Event , EventDeviceName  ;
	private String strexpectedResult = "" , strActualResult , Xpath_checkbox , currentCheckboxText;
	//String headername[] = {"Dates", "Event Type", "Order"};
	
	public final void testScript() 
	 
	{
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();
				
		
		// Step 2
		isEventSuccessful = PerformAction("lnkHistory_DevcieIndexPage", Action.Click);
		isEventSuccessful = PerformAction("//label[text()='Dates']", Action.isDisplayed);
		if(isEventSuccessful)
		{
			strActualResult =  "_the_Left_Navigation_for_default_values_of_Date is displayed when_clicking_history_tab_link_on_device_details_page";
		}
		else
		{
			strActualResult =  "_the_Left_Navigation_for_default_values_of_Date is not displayed when_clicking_history_tab_link_on_device_details_page";
		}
		reporter.ReportStep("Verifying_the_Left_Navigation_for_default_values_of_Date when_clicking_history_tab_link_on_device_details_page", "_the_Left_Navigation_for_default_values_of_Date should be displayed when_clicking_history_tab_link_on_device_details_page",strActualResult,isEventSuccessful);
				
		// Step 3
		isEventSuccessful = PerformAction("//h4[text()='Order']", Action.isDisplayed);
		if(isEventSuccessful)
		{
			strActualResult =  "_the_Left_Navigation_for_default_values_of_Order is displayed when_clicking_history_tab_link_on_device_details_page";
		}
		else
		{
			strActualResult =  "_the_Left_Navigation_for_default_values_of_Order is not displayed when_clicking_history_tab_link_on_device_details_page";
		}
		reporter.ReportStep("Verifying_the_Left_Navigation_for_default_values_of_Order when_clicking_history_tab_link_on_device_details_page", "_the_Left_Navigation_for_default_values_of_Order should be displayed when_clicking_history_tab_link_on_device_details_page",strActualResult,isEventSuccessful);
			
		// Step 4
		int chkBoxCount = getelementCount("chkEventDevices");
		for (int i = 1; i <= chkBoxCount; i++)
		{
			Xpath_checkbox = dicOR.get("chkEventDevices") + "[" + i + "]"; // Getting xpath of each checkbox by puting index to the general xpath
			currentCheckboxText = GetTextOrValue(Xpath_checkbox + "/..", "text"); //Getting text in front of the checkbox, i.e. text of the parent of the checkbox that has text associated with the checkbox
			if(!currentCheckboxText.isEmpty())
			{
				strActualResult = currentCheckboxText;
				isEventSuccessful = true;
			}
			else
			{
				isEventSuccessful = false;
				strActualResult = null;
			}
			reporter.ReportStep("Verifying_the_Left_Navigation_for_default_values_of_Event Type when_clicking_history_tab_link_on_device_details_page", "_the_Left_Navigation_for_default_values_of_Event Type should be displayed when_clicking_history_tab_link_on_device_details_page",strActualResult,isEventSuccessful);	
		}

	 
	}
}
