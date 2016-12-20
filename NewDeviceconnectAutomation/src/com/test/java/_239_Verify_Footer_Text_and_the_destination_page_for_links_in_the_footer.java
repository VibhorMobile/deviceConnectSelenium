package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2208
 */
public class _239_Verify_Footer_Text_and_the_destination_page_for_links_in_the_footer extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "", strRequiredFooterText = "", strActualFooterText = "";

	public final void testScript()
	{
		///Step 1 - Login to deviceConnect
		isEventSuccessful = Login();

		
		//Step 2 - Click on Mobile Labs link and verify it redirects the user to the mobilelabs page
			
			isEventSuccessful = PerformAction("lnkMobileLabs_Footer", Action.Click);
			if (isEventSuccessful)
			{
			 isEventSuccessful = PerformAction("browser", Action.WindowHandles,"mobilelabsinc.com");
			  if (isEventSuccessful)
				{
				  strActualResult = "Clicking on 'Mobile Labs' link navigate user to mobilelabs page";
				}
				else
				{
					strActualResult = "Could not get the URL of newly opened window.";
				}
				
			}
			else
			{
				strActualResult = "Unable to click on Mobile Labs footer link";
			}
	reporter.ReportStep("Click on 'Mobile Labs' link", "User should be navigated to mobilelabs page", strActualResult, isEventSuccessful);

						
		//Step 3 - Click on About deviceConnect link and verify it redirects the user to the About deviceConnect page
		isEventSuccessful = false;
		isEventSuccessful = PerformAction("lnkAbout", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("eleDeviceConnectLogoOnAbout", Action.WaitForElement, "5");
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction("btnClose_aboutPage", Action.Click);
				if (isEventSuccessful)
				{
					strActualResult = "Clicking on 'About deviceConnect' link navigate user to 'About deviceConnect' page. Also, pop up removed successfully by clicking on 'Close' button on pop-up.";
				}
				else
				{
					strActualResult = "Could not click on 'Close' button on About deviceConnect pop-up.";
				}
			}
			else

			{
				strActualResult = "About deviceConnect pop up not opened after clicking on 'About deviceConnect' link.";
			}
		}
		else
		{
			strActualResult = "Unable to click on 'About deviceConnect' footer link";
		}
		reporter.ReportStep("Click on 'About deviceConnect' link", "User should be navigated to 'About deviceConnect' page", strActualResult, isEventSuccessful);

		//Step 4 - Click on Help link and verify it redirects the user to the UserGuide page
		
		
			isEventSuccessful = false;
			PerformAction("", Action.WaitForElement,"10");
			isEventSuccessful = PerformAction("lnkHelp", Action.Click);
			if (isEventSuccessful)
			{
			  isEventSuccessful = PerformAction("browser", Action.WindowHandles,"http://"+dicCommon.get("ApplicationURL")+"/Content/documents/deviceConnectUserGuide.pdf");
										
		      if(isEventSuccessful)
			  {
			     strActualResult = "Clicking on 'Help link' UserGuide page is displayed";
			  }
		     else
		     {
		    	 strActualResult = "Clicking on 'Help link' UserGuide page is not displayed";	
		     }
		  }
		else
		{
		   strActualResult = " Unable to click on Help link";
		}
				
	reporter.ReportStep("Click on 'Help' link", "User should be navigated to UserGuide page", strActualResult, isEventSuccessful);
	}
}