package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */

public class _591_Verify_that_only_a_valid_txt_or_iosfw_file_type_is_allowed_to_be_uploaded_as_iOS_firmware_database extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ;
	private String strexpectedResult = "" ;
	
	 public final void testScript() 
	 
	 {

		 //*********************************//
		 //***** Step 1 - Login to DC. *****//
		 //*********************************//       
		 isEventSuccessful = Login();
		 	
		 //Step 2
		 isEventSuccessful =    GoToSystemPage();
		
		 //Step 3
		 try
		 {  
			 isEventSuccessful =    PerformAction("lnkiOSMgmnt", Action.Click);
			 if(isEventSuccessful)
			 {
				 isEventSuccessful =    PerformAction("lnkUploadfirmwardatabase_SystemPage", Action.Click);
				 if(isEventSuccessful)
				 {
					 isEventSuccessful =   PerformAction("lnkUploadfirmwardatabase_SystemPage",Action.UploadApplication,"ios92.iosfw");
					 if(!isEventSuccessful)
					 {
						 throw new RuntimeException("Could not uploaded iOS firmwardatabase file");  
					 }	
					 else
			       		{	
						 strActualResult = "Successfully uploaded the .iosfw file" ;
			       		}
				 }
				 else
				 {
					 throw new RuntimeException("Could not click on Uploadfirmwardatabase link."); 
				 }	
			 }
			 else
			 {
				 throw new RuntimeException("Could not click on iOSMgmnt link.");
			 }
		 }
		 catch (RuntimeException e)
		 {
			 isEventSuccessful = false;
			 strActualResult =  "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		 }
		 reporter.ReportStep("Verify user should be able to upload the iOS firmwardatabase .iosfw file on iOS managment page" , "User should be able to upload the iOS firmwardatabase .iosfw file on iOS managment page.",strActualResult,isEventSuccessful); 	 
		 
		 //Step 4
		 try
		 {  
			 isEventSuccessful = PerformAction("browser", Action.WaitForPageToLoad);
			 isEventSuccessful = PerformAction("lnkUploadfirmwardatabase_SystemPage", Action.Click);
			 if(isEventSuccessful)
			 {
				 isEventSuccessful =   PerformAction("lnkUploadfirmwardatabase_SystemPage",Action.UploadApplication,"ios92.txt");
				 if(!isEventSuccessful)
				 {
					 throw new RuntimeException("Could not uploaded iOS firmwardatabase file");  
				 }
				 else
				 {
					 strActualResult = "Successfully uploaded the .txt file" ;
				 }
			 }
			 else
			 {
				 throw new RuntimeException("Could not click on Uploadfirmwardatabase link."); 
			 }
			 
			 
		 }
		 catch (RuntimeException e)
		 {
			 isEventSuccessful = false;
			 strActualResult =  "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		 }
		 reporter.ReportStep("Verify user should be able to upload the iOS firmwardatabase .txt file on iOS managment page" , "User should be able to upload the iOS firmwardatabase .txt file on iOS managment page.",strActualResult,isEventSuccessful); 	 
		 
		 //Step 5
		 try
		 {  
			 isEventSuccessful =    PerformAction("lnkUploadfirmwardatabase_SystemPage", Action.Click);
			 if(isEventSuccessful)
			 {
				 isEventSuccessful =   PerformAction("lnkUploadfirmwardatabase_SystemPage",Action.UploadApplication,"javacsv-2.0.jar.zip");
				 if(!isEventSuccessful)
				 {
					 throw new RuntimeException("Could not uploaded iOS firmwardatabase file");  
				 }
				 else
				 {
					 strActualResult = GetTextOrValue("Errorhandling_everyPAge", "text");
				 }
			 }
			 else
			 {
				 throw new RuntimeException("Could not click on Uploadfirmwardatabase link."); 
			 }
			 
			 
		 }
		 catch (RuntimeException e)
		 {
			 isEventSuccessful = false;
			 strActualResult =  "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		 }
		 reporter.ReportStep("Verify user should not be able to upload the iOS firmwardatabase .zip file on iOS managment page" , "User should not be able to upload the iOS firmwardatabase .zip file on iOS managment page.",strActualResult,isEventSuccessful); 	 
		 

	 }	
}
