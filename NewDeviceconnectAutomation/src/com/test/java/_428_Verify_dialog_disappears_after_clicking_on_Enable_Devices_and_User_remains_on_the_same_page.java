package com.test.java;

import java.util.ArrayList;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2156
 */
public class _428_Verify_dialog_disappears_after_clicking_on_Enable_Devices_and_User_remains_on_the_same_page extends ScriptFuncLibrary
{
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String selectedDeviceName = "";
	private int devicesCount =0;
	private java.util.ArrayList<String> devicesSelected1 = new java.util.ArrayList<String>();
	private java.util.ArrayList<String> devicesSelected2 = new java.util.ArrayList<String>();

	//*****************************************************************************************************************************************************//
	// Select removed status checkbox, click on Enable button, then click on 'Cancel' button on dialog and verify that the user returns to devices index page.
	//*****************************************************************************************************************************************************//
	public final void testScript()
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		//********************************************************//
		//** Step 1 : Launch deviceConnect and verify homepage. **//
		//********************************************************//
	
		isEventSuccessful = Login(EmailAddress, Password);
		
		//*********************************************//
		// Step 2 : Select removed from status filters //
		//*********************************************//
		
 		isEventSuccessful =  selectStatus_DI("Disabled");

 		
		if (!isEventSuccessful) // Return if no removed devices are displayed on the page.
		{
			isEventSuccessful = DisplayDisabledDevices();
		}

		String strErrorIndex = "" , strErrorIndexName = "", deviceName = "";
		try
        {
            devicesCount = getelementCount("eleDevicesHolderListView") - 1;
            if (devicesCount > 0) // If number of rows are obtained then check if the check-status of all the checkboxes match the given check status
            {
                for (int i = 1; i <= devicesCount; i++)
                {
                    if (!PerformAction(dicOR.get("chkDeviceName_Devices") + "[" + i + "]", Action.Click)) // If the checkbox is not selected/deselected then put the index to errorVariable
                    {
                        strErrorIndex = strErrorIndex + ", " + i;
                    }
                       if(GetTextOrValue(dicOR.get("eleDeviceStatus_ListView").replace("__INDEX__", (new Integer(i)).toString()), "text").equals("Connected"))
                       {
                          deviceName = GetTextOrValue(dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(i)).toString()), "text");
                          devicesSelected1.add(deviceName);
                            if (deviceName.equals(""))
                            {
                               strErrorIndexName = strErrorIndexName + ", " + i;
                            }
                      }
                      else
                      {
                    	   deviceName = GetTextOrValue(dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(i)).toString()), "text");
                           devicesSelected2.add(deviceName);
                             if (deviceName.equals(""))
                             {
                                strErrorIndexName = strErrorIndexName + ", " + i;
                             }
                      }
                }
                if ( ! strErrorIndex.equals(""))
                {
                	isEventSuccessful = false;
                	throw new RuntimeException("Checkbox is not in correct checked-state for device at index(s) : '" + strErrorIndex + "'.");
                }
                if ( ! strErrorIndexName.equals(""))
                {
                	isEventSuccessful = false;
                	throw new RuntimeException("Could not get name of device at index(s) : '" + strErrorIndexName + "'.");
                }
                isEventSuccessful = true;
                strActualResult = "Successfully devices are added into list";
            }
            else
            {
                throw new RuntimeException(strErrMsg_AppLib);
            }
        }
        catch (RuntimeException e)
        {
        	isEventSuccessful = false;
        	strActualResult = "VerifyAllCheckedOrUnchecked_DI--- " + " + " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
        }
		
		reporter.ReportStep("Adding disabled/connected devices into list", "Disabled/connected devices should be added  into list", strActualResult, isEventSuccessful);
		//****************************************************************//
		// Step 5 : Click on 'Enable' button and verify 'Enable devices' dialog.//
		//****************************************************************//
		strstepDescription = "Click on 'Enable' button and verify 'Enable devices' dialog.";
		strexpectedResult = "'Enable Devices' dialog should be displayed.";
		isEventSuccessful = PerformAction("btnEnable_Devices", Action.Click);
		
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("hdrEnableDevice", Action.WaitForElement, "20");
			if (isEventSuccessful)
			{
				strActualResult = "Enable device dialog displayed successfully after clicking on 'Enable' button.";
			}
			else
			{
				strActualResult = "'Enable Device' dialog not displayed after clicking on 'Enable' button.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Enable' button.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//**************************************************************************************//
		// Step 6 : Click on 'Enable devices' button and verify user returns to devices index page.//
		//**************************************************************************************//
		strstepDescription = "Click on 'Enable devices' button and verify user returns to devices index page.";
		strexpectedResult = "'Enable Device' dialog should disappear and user should return to devices index page.";
		isEventSuccessful = PerformAction("btnEnableDevices_EnableDevice", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("hdrEnableDevice", Action.isNotDisplayed);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("eleDevicesTab_Devices", Action.isDisplayed);
				if (isEventSuccessful)
				{
					strActualResult = "'Enable Device' dialog closed and user returned to devices index page after clicking on 'Enable devices' button.";
				}
				else
				{
					strActualResult = "Devices index page is not displayed.";
				}
			}
			else
			{
				strActualResult = "Enable device dialog did not disappear after clicking on 'Enable devices' button." + "<br> Reason (GenLib error): '" + strErrMsg_GenLib + "'.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Cancel' button.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	
	
		//*********************************************************************************//
		// Step 7 : Verify that all the selected devices are added under 'Available' status.//
		//**********************************************************************************//
		
		isEventSuccessful  = selectStatus("Available");
		
     	
       
		
		strstepDescription = "Verify that all the Disabled devices are added under 'Offline' status after enabling.";
		strexpectedResult  = "Disabled devices should be dispalyed under 'Offline' devices after enabling.";
		isEventSuccessful  = selectStatus("Offline");
				
		     	if (isEventSuccessful)
				 {
		     		  if(devicesSelected2.size()!=0)
		     		  {
		     		     isEventSuccessful = VerifyDevicesName(devicesSelected2);
		     		     
		     		 }
				 
	     		    else
	     		    {
	     		     	strActualResult = "Diabled devices with 'Connected' status are not dislpayed. ";
	     		    }
				}
		     	
			   else
			   {
					strActualResult = strErrMsg_AppLib;
			   }
		    	
			
		       reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult , isEventSuccessful);
		       
	}
}