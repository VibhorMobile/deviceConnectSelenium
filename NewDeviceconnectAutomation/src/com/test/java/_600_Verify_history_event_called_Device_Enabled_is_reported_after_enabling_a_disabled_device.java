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
 * JIRA ID --> QA-1085
 */
public class _600_Verify_history_event_called_Device_Enabled_is_reported_after_enabling_a_disabled_device extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" , DeviceName , Event , EventDeviceName  ;
	private String strexpectedResult = "" , strActualResult ;
	
	public final void testScript() 
	 
	{
		// Step 1
		isEventSuccessful = Login();
		
		// Step 2
		isEventSuccessful = selectStatus("Available");
		
		// Step 3
		waitForPageLoaded();
		isEventSuccessful = PerformAction(dicOR.get("chkDeviceName_Devices")+"[" + 1 + "]", Action.SelectCheckbox);
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = PerformAction("//button[text()='Disable']", Action.Click);
			if(isEventSuccessful)
			{
				waitForPageLoaded();
				isEventSuccessful = PerformAction("btnDisableDevices_DisableDevice", Action.Click);
				if(isEventSuccessful)
				{
					waitForPageLoaded();
					isEventSuccessful = PerformAction("//button[text()='Enable']", Action.Click);
					if(isEventSuccessful)
					{
						waitForPageLoaded();
						isEventSuccessful = PerformAction("btnEnableDevices_EnableDevice", Action.Click);
						waitForPageLoaded();
						DeviceName = GetTextOrValue("(//a[starts-with(@href,'/#/Device/Detail/')])[1]", "text");
						isEventSuccessful = PerformAction("lnkHistory_DevcieIndexPage", Action.Click);
						waitForPageLoaded();
						isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
						Event = GetTextOrValue("//table[contains(@class,'table-striped table')]/tbody/tr[1]/td[2]", "text");
						EventDeviceName = GetTextOrValue("//table[contains(@class,'table-striped table')]/tbody/tr[1]/td[3]/a", "text");
						if(Event.equalsIgnoreCase("Device Enabled") && EventDeviceName.equalsIgnoreCase(DeviceName))
						{
							isEventSuccessful = true;
							strActualResult = "Successfully history_event_called_Device_Disabled_is_reported_after_disabling_a_non_disabled_device" ;
						}
						else
						{
							strActualResult = "Could not reported history event after making devcie disabled."; 
						}
					}
					else
					{
						strActualResult = "Could not clicked on Enable button."; 
					}
				}
				else
				{
					strActualResult = "Could not clicked on bulk disable devices button."; 
				}
			}
			else
			{
				strActualResult = "Could not clicked on disable button."; 
			}
		}
		else
		{
			strActualResult = "Could not selected first device."; 
		}
		reporter.ReportStep("Verify_history_event_called_Device_Disabled_is_reported_after_disabling_a_non_disabled_device", "history_event_called_Device_Disabled_should be_reported_after_disabling_a_non_disabled_device" , strActualResult ,  isEventSuccessful );
	

	}
}
