
package com.test.java;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*
 * Jira Test Case Id: QA-832
 */
public class _790_Verify_users_are_displayed_in_dropdown_on_Reservations_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;

	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "",outputText="",deviceName="",XPath="",XPathValue="";
	private Object[] firstdeviceSelected = null;
	Object[] Values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("testerEmailAddress");
		String Password = dicCommon.get("testerPassword");
		String strActualResult = "";
		String ProjectedSchedule = "";
		String devicename;
		Object[] values = new Object[6];
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with Tester user.
		//*************************************************************//  
		isEventSuccessful = Login();
		if(!isEventSuccessful)
			return;
		
		//*************************************************************//                     
		// Step 2: Get the first Available device name.
		//*************************************************************//    
		strstepDescription = "Get the first Available device name";
		strexpectedResult = "Get the first Available device name & store it in deviceName";
	
		values =  GetDeviceDetails(1,"devicename","list","Available");
		isEventSuccessful  = (boolean) values[0] ;
		devicename = (String) values[1];
		GoToReservationsPage();

		//*************************************************************//                     
		// Step 3 : Create a Daily Reservation & verify if it got created for future time
		//*************************************************************//
		strstepDescription = "Users dropdonw";
		strexpectedResult = "Verify Users dropdown contains list of Users.";
		if(isEventSuccessful)
		{	
			if (PerformAction("drpUsers_ReservationsPage", Action.WaitForElement, "2"))
			{
				if (PerformAction("drpUsers_ReservationsPage", Action.Click))
				{
					
					XPath = getValueFromDictionary(dicOR, "eleUserOptions_ReservationsPage");
					XPathValue = XPath.replace("__USERNAME__", "Admin deviceConnect");
					if (dicCommon.get("BrowserName").toLowerCase().contains("ie") && (GenericLibrary.IEversion.equals("11")))
					{
						PerformAction(XPathValue, Action.WaitForElement,"5");
						isEventSuccessful = PerformAction(XPathValue, Action.ClickUsingJS);
					}
					else
					{
						isEventSuccessful = PerformAction(XPathValue, Action.ClickUsingJS);
					}
					if(isEventSuccessful)
						strActualResult = "The Users dropdown contains the Admin User.";
					else
						strActualResult = "The Users dropdown doesn't contains the Admin User";
				}
				else
					strActualResult = "Unable to click the Users dropdown on Reservations Index page.";
			}
			else
				strActualResult = "The Users dropdown didn't display.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);			
	}
}