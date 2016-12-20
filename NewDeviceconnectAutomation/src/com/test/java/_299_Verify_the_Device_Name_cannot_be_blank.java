package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-2164
 */
public class _299_Verify_the_Device_Name_cannot_be_blank extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", text = "";
	Object[] arrResult;

	public final void testScript()
	{
		
		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();
		if(!isEventSuccessful)
			return;

		//**************************************************************************//
		// Step 2: Select Status filter : Offline 
		//**************************************************************************//
		isEventSuccessful = selectStatus("Offline");

		
		//**************************************************************************//
		// Step 3: Go to details page of an offline device.
		//**************************************************************************//
		arrResult = GoTofirstDeviceDetailsPage();
		isEventSuccessful = (Boolean)arrResult[0];
		

		//**************************************************************************//
		// Step 4: Click on Edit link in front of device name.
		//**************************************************************************//
         strStepDescription = "Verify device name doesnt accept blank spaces";
         strExpectedResult =  "'Device Name' should not accept blank spaces.";
         isEventSuccessful = PerformAction("lnkEditDeviceName_DeviceDetailPage",Action.ClickUsingJS);
         if(isEventSuccessful)
	     {
	         isEventSuccessful = PerformAction("txtDeviceName", Action.isEnabled); 
	         if(isEventSuccessful)
	         {
	        	 isEventSuccessful = PerformAction("txtDeviceName", Action.Clear);
		         if (isEventSuccessful)
		         {	
			         isEventSuccessful = PerformAction("btnSaveDeviceName_DeviceDetails", Action.Click);
				     if(isEventSuccessful)
				     {
				       isEventSuccessful = PerformAction("txtDeviceName", Action.isDisplayed); 
				       if(isEventSuccessful)
				       {
				    	   text = GetTextOrValue("txtDeviceName", "text");
				    	   isEventSuccessful = text.isEmpty();
				    	   if(isEventSuccessful)
				    		   strActualResult = "Textbox for device name is still empty. ie. blank name is not saved.";
				    	   else
				    		   strActualResult = "Text of editasble device name textbox is wrongly populated with value:" + text;
				       }
				       else
				    	   strActualResult = "Editable device name textbox disappeared after clicking on Save devicename button.";
				     }
				     else
				    	 strActualResult = "Could not click on tick mark button for saving device name.";
		         }
		         else
		 			strActualResult = "Could not clear the device name textbox.";
	         }
	         else
	  	       strActualResult = "Editable device name textbox not displayed."; 
	     }
         else
 			strActualResult ="Could not double click on device name label.";
		
		reporter.ReportStep(strStepDescription,strExpectedResult, strActualResult, isEventSuccessful);
   }
	
}
