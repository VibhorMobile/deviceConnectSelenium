package com.test.java;

import java.util.ArrayList;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-766
 */
public class _777_Verify_that_applications_list_is_sorted_properly_when_user_clicks_on_Name_link_in_table_header extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private  ArrayList<String> appList = new ArrayList<String>();
	private String strErrMgs_Script = "", strActualResult = "",appName,editedAppName;
	public final void testScript()
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();

			//**********************************************************//
			// Step 2 - Go to Applications Page and select iOS Platform.
			//**********************************************************//                                   
			isEventSuccessful = GoToApplicationsPage();

			//**********************************************************//
			// Step 3 - Get the App list and check if it is in ascending order
			//**********************************************************//     
			strStepDescription="Get the App list and check if it is in ascending order";
			strExpectedResult="Apps should be in ascending order";
			int appCount=getelementCount(dicOR.get("appNameLink_ApplicationsPage"));
			System.out.println(appCount);
			if(appCount>0){
				isEventSuccessful = VerifySortingonApplicationsPage("appcolumnName","Name", "descending");
				System.out.println("List in ascending order:"+isEventSuccessful);
				if(isEventSuccessful){
					strActualResult="Apps are displayed in alphabetically ascending order";
				}
				else{
					strActualResult="Apps are not displayed in alphabetically ascending order";
				}
			}
			else{
				strActualResult="No apps available on page can not verify ordering";
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//**********************************************************//
			// Step 4 - Click on Name Header and verify order is reversed
			//**********************************************************//     
			strStepDescription="Click on Name Header and verify order changed to descending";
			strExpectedResult="After click on Nameheader app order changed to descending";
			isEventSuccessful=PerformAction(dicOR.get("eleNameHeader_ApplicationsPage"), Action.Click);
			if(isEventSuccessful){
				appCount=getelementCount(dicOR.get("appNameLink_ApplicationsPage"));
				System.out.println(appCount);
				if(appCount>0){
					 
					isEventSuccessful = VerifySortingonApplicationsPage("appcolumnName","Name", "ascending");
					System.out.println("List in descending order:"+isEventSuccessful);
					if(isEventSuccessful){
						strActualResult="Apps are displayed in alphabetically descending order";
					}
					else{
						strActualResult="Apps are not displayed in alphabetically descending order";
					}
				}
				else{
					strActualResult="No apps available on page can not verify ordering";
					isEventSuccessful=false;
				}
			}
			else{
				strActualResult="Unable to click on Name header link";
			}

			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//***************************

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Form factor--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}

	}

}
