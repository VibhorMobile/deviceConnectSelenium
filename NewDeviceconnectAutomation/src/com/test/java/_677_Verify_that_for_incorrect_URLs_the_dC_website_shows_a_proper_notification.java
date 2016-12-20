package com.test.java;

import java.io.IOException;
import java.util.Random;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-528
 */
public class _677_Verify_that_for_incorrect_URLs_the_dC_website_shows_a_proper_notification extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private boolean isStepSuccessful=false;

	String pageURL,errorMessage,wrongURL;



	public final void testScript() throws InterruptedException, IOException
	{
		try{


			String EmailAddress = dicCommon.get("EmailAddress");
			String Password = dicCommon.get("Password");


			//*************************************************************//                     
			// Step 1 : login to deviceConnect with admin user.
			//*************************************************************//                     
			isEventSuccessful = Login();


			//*************************************************************/                     
			// Step 2 : Provide wrong URL and Verify that error popup appears 
			//*************************************************************//
			strstepDescription="Provide wrong URL and Verify Error Message Displayed";
			strExpectedResult="After giving Wrong URL error Message should be displayed";
			pageURL=driver.getCurrentUrl();
			wrongURL=pageURL.replace("Index","Ind");
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
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful); 

			//*************************************************************/                     
			// Step 3 : Verify error Message
			//*************************************************************//
			strstepDescription="Verify Error Message Displayed";
			strExpectedResult="Error Message Should be Displayed that :Unknown page requested or Page you have requested is not avilable";
			errorMessage=GetTextOrValue(dicOR.get("errorMessage_ErrorBlock"), "text");
			if(errorMessage.contains("Unknown page requested")||errorMessage.contains("The page you have requested is not available")){
				strActualResult="Error Message displayed :"+errorMessage;
				isEventSuccessful=true;
			}
			else{
				strActualResult="Incorrect Message Dispalyed :"+errorMessage;
				isEventSuccessful=false;
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful); 			



		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "View Token Field for User--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
