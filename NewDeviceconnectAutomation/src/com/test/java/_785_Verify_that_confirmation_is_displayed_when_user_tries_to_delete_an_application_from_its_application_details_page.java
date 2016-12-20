package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-751
 */
public class _785_Verify_that_confirmation_is_displayed_when_user_tries_to_delete_an_application_from_its_application_details_page extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,inUse,outputText;
	Object[] Values = new Object[5]; 
	boolean isIOSSelected,isAndroidSelected;






	public final void testScript() throws InterruptedException, IOException
	{
		try{

			//*************************************************************//                     
			// Step 1 : login to deviceConnect with Admin User
			//*************************************************************//  


			isEventSuccessful = Login();
			//*************************************************************//      
			// Step 2 : Go to Applications Page and Open details p[age of first app displayed
			//*************************************************************//
			GoToApplicationsPage();
			GoToFirstAppDetailsPage();

			//**************************************************************************//
			// Step 3 : Click on Delete and Verify Dialog appears.
			//**************************************************************************//
			isEventSuccessful=PerformAction(dicOR.get("appOptionsDropdown_AppDetailsPage"), Action.Click);
			if (isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("appDelete_AppDetailsPage"), Action.Click);
				if(isEventSuccessful){
					isEventSuccessful=PerformAction(dicOR.get("confirmationDialog_DC"), Action.isDisplayed);
					if(isEventSuccessful){
						strActualResult="Confirmation Dialog for  App Deletion Displayed";
					}
					else{
						strActualResult="Confirmation Dialog for App Deletion not Displayed";
					}
				}
				else{
					strActualResult="Unable to click on Delete option for the app";
				}
				
			}
			else{
				strActualResult="Unable to click on Options Dropdown from app details page";
			}
 
			reporter.ReportStep("Verify Confirmation Dialog appears when deleting an app", "Confirmation dialog should get displayed", strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 4 : Verify two options Continue and Delete are available on Dialog
			//**************************************************************************//
			
			if (isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("ContinueButton_ConfirmationDialog"), Action.isDisplayed);
				if(isEventSuccessful){
					isEventSuccessful=PerformAction(dicOR.get("CancelButton_ConfirmationDialog"), Action.isDisplayed);
					if(isEventSuccessful){
						strActualResult="Continue and Cancel button available on confirmation dialog";
					}
					else{
						strActualResult="No Cancel button on dialog";
					}
				}
				else{
					strActualResult="No Continue button on Dialog";
				}
				
			}
			else{
				strActualResult="No Confirmation Dialog can not verify buttons";
			}
 
			reporter.ReportStep("Verify Continue an cancel buttons on confirmation dialog", "Continue and cancel buttons should be there", strActualResult, isEventSuccessful);



		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify checkbox is unchecked after refresh--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

}
