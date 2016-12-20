package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2164
 */
public class _289_Verify_that_if_the_user_clicks_Save_the_Device_Name_should_be_replaced_with_the_new_name extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", strText = "", strTextAfterwards = "";
	Object[] values = new Object[2];
	String olddeviceName = "";

	public final void testScript()
	{
		///**************************************************************************//
		//Step 1 - : login to deviceConnect with valid user and verify Devices page.
		//**************************************************************************//
		isEventSuccessful = Login();

		//**************************************************************************//
		// Step 2 : Click on any device name & Go to details page of device.
		//**************************************************************************//
		values = GoTofirstDeviceDetailsPage();
	    isEventSuccessful = (boolean) values[0] ;
	    olddeviceName =(String) values[1];
	    if (!isEventSuccessful)
	     {
	 	     return;
	     }
		//**************************************************************************//
		// Step 3 : Click on edit link
		//**************************************************************************//
		strstepDescription = "Click on edit link";
		strexpectedResult = "Device name should be editable";
		isEventSuccessful = PerformAction("lnkEditDeviceName_DeviceDetailPage",Action.ClickUsingJS);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("txtDeviceName", Action.isEnabled);
			if (isEventSuccessful)
			{
				strActualResult = "Device name is editable";
			}
			else
			{
				strActualResult = "Device name is not editable";
			}
		}
		else
		{
			strActualResult = "Unable to click on Edit link";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 4 : Enter a device name
		//**************************************************************************//
		strstepDescription = "Enter a device name";
		strexpectedResult = "New device name should be entered";
		//strText = GetTextOrValue("txtDeviceName", "value");

		isEventSuccessful = PerformAction("txtDeviceName", Action.Type,"newDevice");
		if (isEventSuccessful)
		{
			strActualResult = "Device Name entered is 'Device1' replacing - " + strText;
		}
		else
		{
			strActualResult = "Unable to enter Device name";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 5 : Click on Save link
		//**************************************************************************//
		strstepDescription = "Click on Save link";
		strexpectedResult = "New Device name should be displayed";
		isEventSuccessful = PerformAction("btnSaveDeviceName_DeviceDetails", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("lnkEditDeviceName_DeviceDetailPage",Action.ClickUsingJS);
			   if(isEventSuccessful)
			   {
			       if (!olddeviceName.equals("Device1"))
			       {
			         	 isEventSuccessful = true;
			          	 strActualResult = "New Device name 'Device1' is displayed";
			       }
			      else
			      {
				       isEventSuccessful = false;
				        strActualResult = "New Device name 'Device1' is not displayed";
	              }
			  }
			  else
			  {
				strActualResult = "Unable to click on edit link";
			  }
		}
		else
		{
			strActualResult = "Unable to click on Save link";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************//
		// Step 6 : Post- Condition - Restore the Device name
		//**************************************************************************//
		/*strstepDescription = "Post- Condition - Restore the Device name";
		strexpectedResult = "Actual Device name should be displayed";

		isEventSuccessful = PerformAction("lnkEdit", tangible.Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("txtDeviceName", tangible.Action.Type, strText);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("lnkSave", tangible.Action.Click);
				if (PerformAction("lnkSave", tangible.Action.isDisplayed))
				{
					isEventSuccessful = PerformAction("lnkSave", tangible.Action.Click);
				}

				strTextAfterwards = GetTextOrValue("deviceName_detailsPage", "text");
				isEventSuccessful = strTextAfterwards.equals(strText);
				if (isEventSuccessful)
				{
					strActualResult = "Actual device name - " + strText + " is displayed";
				}
				else
				{
					strActualResult = "Device name could not be changed back to - '" + strText + "' but it is changed to '" + strTextAfterwards + "'. ";
				}
			}
			else
			{
				strActualResult = "Unable to enter actual device name - " + strText;
			}
		}
		else
		{
			strActualResult = "Unable to click on Edit link";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}*/
	}
}