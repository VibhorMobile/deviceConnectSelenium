package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2196
 */
public class _320_Verify_character_limit_is_set_to_50_for_device_name extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";

	public final void testScript()
	{
		// Step 1 : login to deviceConnect with valid user and verify Devices page.
		isEventSuccessful = Login();
		
		// Step 2 : Click on any device name
		strstepDescription = "Click on any device name";
		strexpectedResult = "Device details page should be displayed";
		isEventSuccessful = SelectDevice("first");
		if (isEventSuccessful)
		{
			strActualResult = "Device details page is displayed";
		}
		else
		{
			strActualResult = "Device details page is not displayed";
		}

		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Step 3 : Click on edit link
		strstepDescription = "Click on edit link";
		strexpectedResult = "Device name should be editable";
		isEventSuccessful = PerformAction("lnkEditDeviceName_DeviceDetailPage",Action.ClickUsingJS);
		//isEventSuccessful = PerformAction("lnkEditDeviceName_DeviceDetailPage", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("txtDeviceName", Action.Exist);
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

		// Step 4 : Enter a device name with more than 50 characters.
		strstepDescription = "Enter a device name with more than 50 characters";
		strexpectedResult = "New device name should be entered";
		String strActualName = GetTextOrValue("txtDeviceName", "value");
		isEventSuccessful = PerformAction("txtDeviceName", Action.Type, "Device1111");
		if (isEventSuccessful)
		{
			strActualResult = "Device name  with more than 50 characters entered successfully.";
		}
		else
		{
			strActualResult = "Could not enter new name for device in edit field.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);


		// Step 5 : Verify that error message is displayed.
		strstepDescription = "Verify that error message is displayed.";
		strexpectedResult = "Correct error message should be displayed below the name edit box.";
		isEventSuccessful = PerformAction("eleErrorMsgDevNam_DevDetailsPage", Action.isNotDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Error message is not displayed after entering more than 50 characters.";
			/*String errorMsg = GetTextOrValue("eleErrorMsgDevNam_DevDetailsPage", "text");
			isEventSuccessful = errorMsg.equals("Maximum 50 characters.");
			if (isEventSuccessful)
			{
				strActualResult = "Correct message is displayed for more than 50 characters.";
			}
			else
			{
				strActualResult = "Error message displayed is not correct. It is : " + errorMsg;
			}*/
		}
		else
		{
			strActualResult = "Error message is displayed after entering more than 50 characters.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//step 6 : Click on 'Save' link
		strstepDescription = "Click on 'Save' link.";
		strexpectedResult = "Editable textbox should disappear.";
		isEventSuccessful = PerformAction("btnSaveDeviceName_DeviceDetails", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = !PerformAction("txtDeviceName", Action.isDisplayed) && PerformAction("deviceName_detailsPage", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Device name label replaced the editable devcicename text box successfully.";
			}
			else
			{
				strActualResult = "Device name label did not replaced the editable devcicename text box.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Save' link.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//step 7 : Verify that error message is not displayed.
		strstepDescription = "Verify that error message is not displayed.";
		strexpectedResult = "No error message should be there now.";
		isEventSuccessful = !PerformAction("eleErrorMsgDevNam_DevDetailsPage", Action.isDisplayed);
		if (isEventSuccessful)
		{
			strActualResult = "Error message is not displayed now.";
		}
		else
		{
			strActualResult = "Error message is still displayed.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//step 8 : Verify that device name is not changed and cahnged back to previously saved value.
		strstepDescription = "Verify that device name is not changed and changed back to previously saved value.";
		strexpectedResult = "The device name should revert back to previously saved value.";
		String strSavedName = GetTextOrValue("deviceName_detailsPage", "text");
		isEventSuccessful = !strSavedName.equals(strActualName);
		if (isEventSuccessful)
		{
			strActualResult = "Device name is not saved and changed back to previously saved value i.e. '" + strSavedName + "' .";
		}
		else
		{
			strActualResult = "Device name is not changed back to previously saved value '" + strActualName + "' . It is changed to : '" + strSavedName + "'. ";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}
}