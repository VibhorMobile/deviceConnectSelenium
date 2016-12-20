package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */
/*
 * JIRA ID --> QA-1380
 */
public class _592_Device_Details_History_Verifying_UI_Events_columns_Date_Event_Device_User_Application extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ;
	private String strexpectedResult = "" , strActualResult ;
	String headername[] = {"Date", "Event", "Device", "User", "Application"};
	
	 public final void testScript() 
	 
	 {
		 
		 //***** Step 1 - Login to DC. *****//
		 isEventSuccessful = Login(); 
		 
		 //Step 2
		 isEventSuccessful = PerformAction("lnkHistory_DevcieIndexPage", Action.Click);
		 if(isEventSuccessful)
		 {
			 waitForPageLoaded();
			 isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
			 if(isEventSuccessful)
			 {
				 for(int i = 1; i <= 5 ; i++)
				 {
					 String headervalue = GetTextOrValue("//table[contains(@class,'table-striped table')]//thead//tr[1]//th[header]".replace("header", String.valueOf(i)), "text");
					 for(String s : headername)
					 {	
		    			isEventSuccessful = s.equalsIgnoreCase(headervalue);
		    			if(isEventSuccessful)
		    				{
		    					strActualResult = s + " is displayed on history table" ; 
		    					break;
		    				}
		    				else
		    				{	
		    					strActualResult = s + " is not displayed on history table" ; 
		    				}
					 }
					 reporter.ReportStep("Device_Details_History_Verifying_UI_Events_columns_Date_Event_Device_User_Application", "Device_Details_History_UI_columns_Date_Event_Device_User_Application should be displayed on history details page" , strActualResult , isEventSuccessful);
				 } 
			 }
			 else
			 {
				 reporter.ReportStep("Device_Details_History_Verifying_UI_Events_columns_Date_Event_Device_User_Application", "Device_Details_History_UI_columns_Date_Event_Device_User_Application should be displayed on history details page" , "Cannot wait for page load." , isEventSuccessful);
			 }
	      }
	      else
	      {
	    	  reporter.ReportStep("Device_Details_History_Verifying_UI_Events_columns_Date_Event_Device_User_Application", "Device_Details_History_UI_columns_Date_Event_Device_User_Application should be displayed on history details page" , "Unable to click on Histor link" , isEventSuccessful);
	      }
		 
	 }
}
