package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-1117
 */
public class _768_Device_History_No_record_displayed_for_Application_Install_event_type extends ScriptFuncLibrary {
	
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String  strActualResult = "", appNameApple="Dollar General 2.1.ipa",CertIdentity;
	String [] android={"Android"};

	public final void testScript() throws InterruptedException
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();

			//**********************************************************//
			// Step 2 - Go to Applications page //
			//**********************************************************//                                   
			isEventSuccessful = GoToDevicesPage();
			GoToHistoryPage("4");
			selectEventType("Application Install");

			//**********************************************************//
			// Step 3- Verify  Application launch details are present table is not blank 
			//**********************************************************//  

			strStepDescription = "Verify Application Install History is available";
			strExpectedResult = "Application install History is available";
			int noOfInstallHistory=getelementCount("//table[@class='table table-striped table-condensed']/tbody/tr");

			if(noOfInstallHistory>0)
			{
				strActualResult="Install History is not blank no of records available : "+noOfInstallHistory;
				isEventSuccessful=true;

			}
			else
			{  
				strActualResult = "Install History is blank no of records available : "+noOfInstallHistory;
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			 

			//**********************************************************//
			// Step 3- Go to any device and verify application install history available for device
			//**********************************************************//  
            GoToDevicesPage();
            selectStatus("Available");
            GoTofirstDeviceDetailsPage();
            GoToHistoryPage("3");
            selectEventType("Application Install");

			strStepDescription = "Verify Application Install History is available";
			strExpectedResult = "Application install History is available";
			Thread.sleep(2000);
            noOfInstallHistory=getelementCount("//table[@class='table table-striped table-condensed']/tbody/tr");

			if(noOfInstallHistory>0)
			{
				strActualResult="Install History is not blank no of records available : "+noOfInstallHistory;
				isEventSuccessful=true;

			}
			else
			{  
				strActualResult = "Install History is blank no of records available : "+noOfInstallHistory;
				isEventSuccessful=false;
				
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);



		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
