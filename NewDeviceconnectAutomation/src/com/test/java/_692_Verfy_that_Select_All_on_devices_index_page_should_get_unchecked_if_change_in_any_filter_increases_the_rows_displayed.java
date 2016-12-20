package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1403
 */
public class _692_Verfy_that_Select_All_on_devices_index_page_should_get_unchecked_if_change_in_any_filter_increases_the_rows_displayed extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private boolean isStepSuccessful=false;
	Object[] Values = new Object[5]; 




	public final void testScript() throws InterruptedException, IOException
	{
		try{

			
			//*************************************************************//                     
			// Step 1 : login to deviceConnect with admin user.
			//*************************************************************//                     
			isEventSuccessful = Login();

			//*************************************************************/                     
			// Step 2 : Go to Devices and select iOS platform and avilable checkbox
			//*************************************************************//
			selectPlatform("iOS");
			selectStatus_DI("Available");
			//*************************************************************/                     
			// Step 3 : click on select ALL
			//*************************************************************//
			selectAllDevicesCheckbox_DI();
			//*************************************************************/                     
			// Step 4 : Select all platforms 
			//*************************************************************//
			strstepDescription="Select all Platforms";
			strexpectedResult="All Platforms selected";
			isEventSuccessful=selectPlatform_DI("iOS,Android");
			if(isEventSuccessful){
				 strActualResult="All Platforms selected";
				 isEventSuccessful=true;
			 }
			else{
				 strActualResult="All platforms not selected";
				 isEventSuccessful=false;
			}
			
			//*************************************************************/                     
			// Step 4 : Select all status
			//*************************************************************//
			strstepDescription="Select all status check boxes";
			strexpectedResult="All status checkboxes should be selected";
			isEventSuccessful=selectStatus_DI("Available,In Use,Disabled,Offline");
			if(isEventSuccessful){
				 strActualResult="All status checkboxes selected";
				 isEventSuccessful=true;
			 }
			else{
				 strActualResult="All status checkboxes not selected";
				 isEventSuccessful=false;
			}
			//*************************************************************/                     
			// Step 5 : Verify that SelectAll checkbox is unchecked
			//*************************************************************//
			strstepDescription="Verify that select all checkbox is unchecked";
			strExpectedResult="Select All checkbox should be unchecked";
			isEventSuccessful=PerformAction(dicOR.get("chkSelectAll_Devices"), Action.isNotSelected);
			if(isEventSuccessful){
				strActualResult="Select ALL check box Unchecked.";
				isEventSuccessful=true;
			}
			else{
				strActualResult="Select ALL check box is checked.";
				isEventSuccessful=false;
			}
			
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful); 

			 
        }
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Select All checkbox verification--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

	
}
