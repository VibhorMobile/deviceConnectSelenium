package com.test.java;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;

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
 * JIRA ID --> QA-550
 */
public class _590_Device_details_Reserve_button_is_not_displayed_on_any_device_details_page extends ScriptFuncLibrary

{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ;
	private String strexpectedResult = "";
	private String strActualResult = "", deviceNameLink;
	int devicesCount ;
	
	public final void testScript() 
	 
	{
	
		//*********************************//
		//***** Step 1 - Login to DC. *****//
		//*********************************//       
		isEventSuccessful = Login();
			    
			 
			    
	    //Step 2 : Verify Reserve button is present on device details page after clicking on device link from DEvice index page..  
		isEventSuccessful = selectStatus_DI("Available,In Use");
		try
		{
			if(isEventSuccessful)
			{
				if(!PerformAction("eleNoDevicesWarning_Devices", Action.isDisplayed))
				{		       		       
					devicesCount = getelementCount("eleDevicesHolderListView") - 1; 
					if (!(devicesCount == 0))
					{
						for (int i = 1; i <= devicesCount; i++)
						{ 
	           	     
							deviceNameLink = dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(i)).toString());
							isEventSuccessful = PerformAction(deviceNameLink, Action.ClickUsingJS);
							if(isEventSuccessful)
							{
								isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
								if(isEventSuccessful)
								{
									isEventSuccessful = PerformAction("btnReserve_devciedetails", Action.isDisplayed); 
									if(isEventSuccessful)
									{
										isEventSuccessful = GoToDevicesPage();
										if(!isEventSuccessful)
										{
											throw new RuntimeException("Could not loaded Devices index page");  
										}
									}
									else
									{
										throw new RuntimeException("Reserve button is not displayed" + i);  
									}
								}
								else
								{
									throw new RuntimeException("Page could not loaded");  
								}
							}
							else
							{
								throw new RuntimeException("Could not clicked on device Name.");  
							}
	     		       	}
						isEventSuccessful = true;
						if(isEventSuccessful)
						{
							strActualResult = "Reserve button is present on device details page after clicking on device link from DEvice index page.";
						}
					}
					else
					{
						throw new RuntimeException("Could not get the number of devices.");
					}
				}
				else
				{
					throw new RuntimeException("Devices are not dispalyed.");
				}   	 
	 	   	}
		}
		catch (RuntimeException e)
		{
			isEventSuccessful = false;
			strActualResult =  "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		reporter.ReportStep("Verify Reserve button is present on device details page after clicking on device link from DEvice index page.", "Reserve button should be present on device details page after clicking on device link from DEvice index page.", strActualResult, isEventSuccessful);
		
	} 	
}
