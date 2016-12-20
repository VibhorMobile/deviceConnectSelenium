package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-52
 */
public class _798_Verify_More_link_is_no_longer_available_for_disabled_devices extends ScriptFuncLibrary
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
			strstepDescription = "Disabled and Android device.";
			strexpectedResult =  "Device should get disabled.";


			isEventSuccessful = !VerifyMessage_On_Filter_Selection();
			if (isEventSuccessful) // continue only if there are some devices under android platform.
			{
				Values = ExecuteCLICommand("disable","Android");
				isEventSuccessful=(Boolean)Values[2];
				deviceName=(String)Values[3];
				if (!isEventSuccessful)
				{
					strActualResult = strErrMsg_AppLib;
				}
				else
				{
					strActualResult="Device disabled successfully";
				}
			}
			else
			{
				strActualResult = "Warning messgae displayed on devices page.";
			}
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		}

		//******************************************************************//
		//*** Step 3 : Go to device details page of disabled device *****//
		//******************************************************************// 
		if(isEventSuccessful)
		{
			selectStatus("Disabled");
			GoToSpecificDeviceDetailsPage(deviceName);
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
		//** Step 5 : Verify 'More' link is not available ****//
		//******************************************************************//
		strstepDescription = "Verify 'More' link is not available.";
		strexpectedResult = "'More' link should not be displayed.";
		isEventSuccessful = !PerformAction("linkMoreonDeviceDatils", Action.isEnabled);
		if (!isEventSuccessful)
		{
				strActualResult = "'More' link available for disabled device.";
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Fail");
		}
		else
		{
			strActualResult = "'More' link did not displayed.";
			reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, "Pass");
		}
		
		
		
		//Post tescase, enable the device
		ExecuteCLICommand("enable","Android","","",deviceName,"","","","");

	}	
}