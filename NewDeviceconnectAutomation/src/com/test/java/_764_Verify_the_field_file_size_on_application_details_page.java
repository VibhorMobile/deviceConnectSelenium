package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-779
 */
public class _764_Verify_the_field_file_size_on_application_details_page extends ScriptFuncLibrary {
	
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";

    private String fileSize;
	boolean isFileSizeDisplayed;

	public final void testScript() throws Exception
	{
		try{
			String EmailAddress = dicCommon.get("testerEmailAddress");
			String Password = dicCommon.get("testerPassword");
			//*************************************************************//                     
			// Step 1 : login to deviceConnect with Admin User
			//*************************************************************//  


			isEventSuccessful = Login();
			//*************************************************************//      
			// Step 2 : Go to Applications Page 
			//*************************************************************//
			GoToApplicationsPage();
		
			//**************************************************************************//
			// Step 3 : Select iOS and go to details page of first iOS device
			//**************************************************************************//
			
			
			selectPlatform_DI("iOS");
			GoToFirstAppDetailsPage();
		    
			
			//**************************************************************************//
			// Step 4 : Verify File Size on app details page
			//**************************************************************************//
			strStepDescription="Verify File Size of iOS app from details page";
			strExpectedResult="File size of iOS is in correct format";

			isEventSuccessful=PerformAction(dicOR.get("eleFileSize_AppDetailsPage"), Action.isDisplayed);

			if(isEventSuccessful){
				fileSize=GetTextOrValue(dicOR.get("eleFileSize_AppDetailsPage"), "text");
				
				if(fileSize.endsWith("MB") ||fileSize.endsWith("KB")||fileSize.endsWith("GB")){
					strActualResult="File Size is displayed in correct form at UI:"+fileSize;
					isEventSuccessful=true;
				}
				else{
					strActualResult="File Size is not displayed in correct form at UI:"+fileSize;
					isEventSuccessful=false;
				}
			}
			else{
				strActualResult="File Size not displayed for application";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			
			//*************************************************************//      
			// Step 5: Go to Applications Page 
			//*************************************************************//
			GoToApplicationsPage();
		
			//**************************************************************************//
			// Step 6 : Select iOS and go to details page of first iOS device
			//**************************************************************************//
			
			selectPlatform_DI("Android");
			GoToFirstAppDetailsPage();
			
			//**************************************************************************//
			// Step 7 : Verify File Size on app details page
			//**************************************************************************//
			strStepDescription="Verify File Size of Android app from details page";
			strExpectedResult="File size of Android app is in correct format";

			isEventSuccessful=PerformAction(dicOR.get("eleFileSize_AppDetailsPage"), Action.isDisplayed);

			if(isEventSuccessful){
				fileSize=GetTextOrValue(dicOR.get("eleFileSize_AppDetailsPage"), "text");
				fileSize="0KB";
				if(fileSize.endsWith("MB") ||fileSize.endsWith("KB")||fileSize.endsWith("GB")){
					strActualResult="File Size is displayed in correct form at UI:"+fileSize;
					isEventSuccessful=true;
				}
				else{
					strActualResult="File Size is not displayed in correct form at UI:"+fileSize;
					isEventSuccessful=false;
				}
			}
			else{
				strActualResult="File Size not displayed for application in UI";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify File Size--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}



}
