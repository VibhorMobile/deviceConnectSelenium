package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.WindowNames;
/*
 * Jira Test Case Id: QA-348
 */
public class _759_Verify_Download_link_is_present_on_the_devices_index_page_to_download_CSV_of_all_devices extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";


	boolean isLinkAvailable;

	public final void testScript() throws Exception
	{
		try{
			String EmailAddress = dicCommon.get("testerEmailAddress");
			String Password = dicCommon.get("testerPassword");
			//*************************************************************//                     
			// Step 1 : login to deviceConnect with Tester User
			//*************************************************************//  


			isEventSuccessful = Login();
			//*************************************************************//      
			// Step 2 : Go to Devices Page 
			//*************************************************************//
			GoToDevicesPage();


			//**************************************************************************//
			// Step 3 : Verify Download all devices CSV link is present
			//**************************************************************************//
			strStepDescription="Verify Download all devices CSV link is present";
			strExpectedResult="Download devices list link is present on page";

			isLinkAvailable=PerformAction(dicOR.get("lnkFullDeviceList_DevicesPage"), Action.isDisplayed);

			if(isLinkAvailable){
				strActualResult="Link Available on DI page";
				isEventSuccessful=true;
			}
			else{
				strActualResult="Link not Available on DI page";
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
