package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-749
 */
public class _799_Verify_Online_since_column_on_device_details_page_is_aligned_properly extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", deviceName="";
	Object[] Values = new Object[5]; 

	public final void testScript()
	{
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();

		//******************************************************************//
		//*** Step 2 : Select Status Available. *****//
		//******************************************************************//  
		if (isEventSuccessful)
		{
			isEventSuccessful = selectStatus("Available");
		}

		//******************************************************************//
		//*** Step 3 : Go to device details page of first device *****//
		//******************************************************************// 
		if(isEventSuccessful)
		{
			isEventSuccessful=(Boolean)GoTofirstDeviceDetailsPage()[0];
		}
		
		//******************************************************************//
		//** Step 4 : click on 'Show Details...' link to display hidden details ****//
		//******************************************************************//
		strstepDescription = "Click on 'Show Details...' link to display hidden device details";
		strexpectedResult = "User should be able to click on the 'Show Details...' link";
		isEventSuccessful = PerformAction("lnkShowDetails", Action.isDisplayed);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
			if (isEventSuccessful)
			{
				strActualResult = "User is able to click on 'Show Details...' link successfully.";
			}
			else
			{
				strActualResult = "Could not click on 'Show Details...' link on device details page.";
			}
		}
		else
		{
			strActualResult = "'Show Details...' link is not displayed.";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//******************************************************************//
		//** Step 5 : Verify 'Online Since' displayed properly on device details page ****//
		//******************************************************************//
		strstepDescription = "Verify 'Online Since' displayed properly on device details page.";
		strexpectedResult = "'Online Since' should display properly on device details page.";
		isEventSuccessful = VerifyonDeviceDetails("Online Since");
		if (isEventSuccessful)
		{
				strActualResult = "'Online Since' displayed properly on device details page";
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Pass");
		}
		else
		{
			strActualResult = "'Online Since' did not displayed properly on device details page.";
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
		
	}	
}