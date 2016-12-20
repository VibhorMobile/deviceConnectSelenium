package com.test.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-366
 */
public class _696_Verify_device_notes_are_representation_on_device_list extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", deviceName = "";
	private Object[] firstdeviceSelected = null;
	
	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");
		
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with admin user.
		//*************************************************************//                     
		isEventSuccessful = Login();


		//*************************************************************//                     
		// Step 2 : Navigate to device details & add note
		//*************************************************************// 
		strstepDescription = "Navigate to device details & add note";
		strexpectedResult = "User should be able to add a note if not added & fetch the deviceName.";
		firstdeviceSelected = GoTofirstDeviceDetailsPage();
		isEventSuccessful = (boolean) firstdeviceSelected[0];
		deviceName = (String) firstdeviceSelected[1];
		isEventSuccessful=ShowDetails();
		if(isEventSuccessful)
		{
			isEventSuccessful = EditAndVerifyNotes("Sample Verification Notes");
			if(!isEventSuccessful)
				reporter.ReportStep(strstepDescription ,strexpectedResult , strErrMsg_AppLib, isEventSuccessful);
		}
		
		//*************************************************************//                     
		// Step 3 : Go to Devices Index page & check for the device name for which notes is added
		//*************************************************************//
		strstepDescription = "Verify representation of Notes on devices Index page.";
		strexpectedResult = "User should be able to see the notes representation on device details page.";
		if(isEventSuccessful)
		{
			isEventSuccessful = GoToDevicesPage();
			if(isEventSuccessful)
			{
				isEventSuccessful = searchDevice(deviceName, "devicename");
				if(isEventSuccessful)
				{
					isEventSuccessful = SelectColumn_Devices("Notes");
					if(isEventSuccessful)
					{	
						isEventSuccessful = PerformAction(dicOR.get("eleNotesIcons_DevicesIndexPage").replace("__INDEX__", "[1]"), Action.Exist);
						if(isEventSuccessful)
						{
							String Tooltip = getAttribute(dicOR.get("eleNotesIcons_DevicesIndexPage").replace("__INDEX__", "[1]"), "title");
							if(Tooltip!=null)
								strActualResult = "Notes tooltip is displayed on devices Index page.";
						}
						else
							strActualResult = "Notes icon is not displayed for the device selected : " + deviceName;
					}
					else
						strActualResult = strErrMsg_AppLib;
				}
			}
		}
	}
}