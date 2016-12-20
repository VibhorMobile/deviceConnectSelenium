package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-683
 */
public class _743_Verify_that_tester_users_can_view_device_logs_from_device_details_page extends ScriptFuncLibrary {
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,inUse,outputText;
	Object[] Values = new Object[5]; 
	boolean isViewLogDisplayed;

 




	public final void testScript() throws InterruptedException, IOException
	{
		try{
			String EmailAddress = dicCommon.get("testerEmailAddress");
			String Password = dicCommon.get("testerPassword");

			//*************************************************************//                     
			// Step 1 : login to deviceConnect with tester user 
			//*************************************************************//  


			isEventSuccessful = Login(EmailAddress,Password);
			//*************************************************************//      
			// Step 2 : Go to Devices Page and click on first device details page.
			//*************************************************************//
			GoToDevicesPage();
			isEventSuccessful = (boolean) GoTofirstDeviceDetailsPage()[0];
			
			//**************************************************************************//
			// Step 3 : Verify View Log Option is present for the user.
			//**************************************************************************//
			strstepDescription="Verify View Log option is avilaable for user";
			strExpectedResult="View Log option is present for tesr user";
            isViewLogDisplayed=PerformAction(dicOR.get("btnViewLog"), Action.isDisplayed);
            if(isViewLogDisplayed){
            	isEventSuccessful=true;
            	strActualResult="View Log Button is displayed for tester user";
            }
            else{
            	isEventSuccessful=false;
            	strActualResult="View Log Button not avilable for test users";
            }
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);    
		
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
