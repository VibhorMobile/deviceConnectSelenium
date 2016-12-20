package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: Last week of January
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-1149
 */
public class _585_Verfy_that_notes_fiield_is_visible_searchable_and_sortable_on_devices_index_page extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ;
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String editedNotes = "Trying to serach the notes on device index page";
	Object[] values = new Object[2];
	
	public final void testScript()
	{
		
		// Step 1 - Login to deviceConnect//
		isEventSuccessful = Login();

		//*********************************************//
		//Step 2 - Verify Notes columns is visible//
		//********************************************//
		SelectColumn_Devices_SFL("Notes");
		
		//**************************************************************************//
		// Step 3 : Go to device and add the notes.
		//**************************************************************************//
		strStepDescription = "On the device details page, add the notes.";
		strExpectedResult = "Notes should be added for device..";
		GoTofirstDeviceDetailsPage();
		
		isEventSuccessful =  PerformAction("lnkShowDetails", Action.Click);
		if(isEventSuccessful)
		{
			isEventSuccessful =  PerformAction("//span[contains(@class,'notesEditor listitem-value')]", Action.DoubleClick);
			if(isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleNotefield_DEviceDetailsPage", Action.Clear);
				if(isEventSuccessful)
				{
					isEventSuccessful = PerformAction("eleNotefield_DEviceDetailsPage", Action.Type, editedNotes);
					if(isEventSuccessful)
					{
						isEventSuccessful = PerformAction("(//button[@title='Save changes'])[3]", Action.Click);
						if(isEventSuccessful)
						{
							strActualResult = "Succeesfully wrote on Notes field. ";
						}
					}
					else
					{
						strActualResult = "Could not write on Notes field. ";
					}
				}
				else
				{
					strActualResult = "Could not clear on Notes field. ";
				}
			}
			else
			{
				strActualResult = "Could not click on Notes field. ";
			}
			
		}
		else
		{
			strActualResult = "Could not click on Show dEtails link. ";
		}
		
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		// Step 4 : Go to devices page.
		isEventSuccessful = GoToDevicesPage();
		
		//**************************************************************************//
		// Step 5 : Search for the device which we have connected using CLI and verify release button not available.
		//**************************************************************************//
		strStepDescription = "Verify that searched device name is being dispalyed.";
		strExpectedResult = "Searched device name should be dispalyed.";
		isEventSuccessful = searchDevice(editedNotes, "Notes");
		if(isEventSuccessful)
		{
			strActualResult = "Notes are  displayed.";
		}
		else
		{
			strActualResult = "Notes are not displayed.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
	}		
		
}

