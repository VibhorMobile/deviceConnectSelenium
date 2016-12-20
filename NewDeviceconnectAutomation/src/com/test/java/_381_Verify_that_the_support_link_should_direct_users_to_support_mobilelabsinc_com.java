package com.test.java;

import java.util.ArrayList;
import java.util.Set;
 
import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

/*
 * JIRA ID --> QA-1182
 */

public class _381_Verify_that_the_support_link_should_direct_users_to_support_mobilelabsinc_com extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private String lnkSupport = "//a[text()='Mobile Labs Support']";
	private String oldTab ="";
	public final void testScript()
	{
		// Step 1 : login to deviceConnect with valid user and verify Devices page.
		isEventSuccessful = Login();

		// Step 2 : Go to systems page
		GoToSystemPage();

		//// Step 4 : Verify System Services option
		//strstepDescription = "Verify System Services option";
		//strexpectedResult = "System Services option should be present";
		//isEventSuccessful = PerformAction("btnSystemManagement", Action.isDisplayed);
		//if (isEventSuccessful)
		//    strActualResult = "System Services option is displayed";
		//else
		//    strActualResult = "System Services option is not displayed";

		//reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		//// Step 5 : Click on System Services option
		//strstepDescription = "Click on System Services option";
		//strexpectedResult = "Restart Services button should be displayed on the page.";
		//isEventSuccessful = PerformAction("btnSystemManagement", Action.Click);
		//if (isEventSuccessful)
		//{
		//    isEventSuccessful = PerformAction("btnRestartServices", Action.isDisplayed);
		//    if (isEventSuccessful)
		//        strActualResult = "restart services button is displayed on page.";
		//    else
		//        strActualResult = "restartservices button does not exist.";
		//}
		//else
		//    strActualResult = "Could not click on system services option.";
		//reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);

		// Step 6 : Verify 'Mobile Labs Support' link directs user to 'support.mobilelabsinc.com'.
		strstepDescription = "Verify 'Mobile Labs Support' link directs user to 'support.mobilelabsinc.com'.";
		strexpectedResult = "'Mobile Labs Support' link should appear on the systems page after clicking on 'System Services' option.";
		isEventSuccessful = PerformAction("lnkSupport_SystemPage", Action.WaitForElement);
		if (isEventSuccessful)
		{
			oldTab = driver.getWindowHandle();
			System.out.println("OLD TAB HANDLE IS ------ " + oldTab);
			isEventSuccessful = PerformAction("lnkSupport_SystemPage", Action.Click);
			if (isEventSuccessful)
			{
				ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
				newTab.remove(oldTab);
				System.out.println("NEW WINDOW ID IS  --------- "+ newTab.get(0));
				driver.switchTo().window(newTab.get(0));
				isEventSuccessful = verifysupport_mobilelabsinc_comPage();
				if (isEventSuccessful)
				{
					strActualResult = "User successfully directed to 'support.mobilelabsinc.com' after clicking on 'Mobile Labs Support' link .";
				}
				else
				{
					strActualResult = "verifysupport_mobilelabsinc_comPage()--" + strErrMsg_AppLib;
				}
			}
			else
			{
				strActualResult = "Could not click on Support link.";
			}
		}
		else
		{
			strActualResult = "'Mobile Labs Support' did not appear on the Systems page after clicking on 'System Services' option; waited for 120 seconds.";
		}
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	}

	public final boolean verifysupport_mobilelabsinc_comPage()
	{
		boolean flag = false;
		int windows = 0;
		try
		{
			windows = driver.getWindowHandles().size();
			if (windows != 2)
			{
				try {
					Thread.sleep(Integer.parseInt(dicCommon.get("iWait")) * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				windows = driver.getWindowHandles().size();
			}
		}
		catch (RuntimeException e)
		{
		}
		if (windows == 2)
		{			
		  isEventSuccessful = verifyPageTitle();
		 flag = isEventSuccessful;
		}
		return flag;
	}
	
	public boolean verifyPageTitle()
	{
		boolean temp = false;
		String childWindowTitle = "Mobile Labs"; 
		String currentWindowTitle = driver.getTitle().trim();
		
		System.out.println("current window title is " + currentWindowTitle);
		if  (childWindowTitle.equals(currentWindowTitle))
		{
			temp=true;	
		}
		return temp;
	}
		
}
	