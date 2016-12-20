package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-720
 */
public class _813_Verify_that_the_error_is_improved_for_HTML_responses extends ScriptFuncLibrary {
	
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String  strActualResult = "",pageURL,wrongURL,errorMessage,GUID,wrongGUID;
	

	Object[] Values = new Object[5]; 
	public final void testScript() throws InterruptedException
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();


			//*************************************************************//                     
			// Step 1 : Go to Devices Page and Open Details page for first device
			//*************************************************************//                     
			GoToDevicesPage();
			GoTofirstDeviceDetailsPage();


			//*************************************************************/                     
			// Step 2 : Provide wrong URL and Verify that error popup appears 
			//*************************************************************//
			strStepDescription="Provide wrong URL and Verify Error Message Displayed";
			strExpectedResult="After giving Wrong URL error Message should be displayed";
			pageURL=driver.getCurrentUrl();
			GUID=pageURL.split("/")[6];
			wrongGUID=GUID+"abds";
			wrongURL=pageURL.replace(GUID, wrongGUID);
			System.out.println(wrongURL);
			PerformAction("browser", Action.Navigate,wrongURL);
			isEventSuccessful=PerformAction(dicOR.get("errorBlock_DCUI"), Action.isDisplayed);
			if(isEventSuccessful){
				strActualResult="Error Message  Displayed on DC UI";
				isEventSuccessful=true;
			}
			else{
				strActualResult="Error Message not Displayed on DC UI";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful); 

			//*************************************************************/                     
			// Step 3 : Verify Details Link is present on Error
			//*************************************************************//
			strStepDescription="Verify details link is present on UI";
			strExpectedResult="Details link is present on UI";
		    isEventSuccessful=PerformAction(dicOR.get("lnkDetails_ErrorPopup"), Action.isDisplayed);
		    if(isEventSuccessful){
		    	strActualResult="Error displayed with Details link";
		    }
		    else{
		    	strActualResult="Details link not present with error Message";
		    }
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful); 			



		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "View Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

}
