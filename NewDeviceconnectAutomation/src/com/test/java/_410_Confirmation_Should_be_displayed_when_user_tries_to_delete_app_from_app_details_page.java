package com.test.java;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.Reporting.Reporter;
import com.common.utilities.ApplicationLibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-90
 */

public class _410_Confirmation_Should_be_displayed_when_user_tries_to_delete_app_from_app_details_page extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strActualResult = "", EmailAddress = dicCommon.get("EmailAddress"), PassWord = dicCommon.get("Password");
	private String confirmationHeader = "";
	private String PopUptext = "";
	private String TempErrMsg =""; 

	public final void testScript()
	{
		
		//////////////////////////////////////////////////////////////////////////
		//Step 1 - Login to deviceConnect
		//////////////////////////////////////////////////////////////////////////
		isEventSuccessful = Login();
		
		if(!isEventSuccessful)
			return;
		//////////////////////////////////////////////////////////////////////////
		//Step 3 - Navigate to Applications Page
		//////////////////////////////////////////////////////////////////////////
		
		isEventSuccessful = GoToApplicationsPage();
		
		
		//////////////////////////////////////////////////////////////////////////
		//Step 4 - Verify Confirmation Message for delete application
		//////////////////////////////////////////////////////////////////////////
		try
		{
			List<WebElement> TableRows = getelementsList(dicOR.get("eleAppTableRows"));
			if (TableRows.isEmpty())
				throw new RuntimeException("No applications uploaded at the moment.");
			for (int j = 1; j <= TableRows.size(); j++)
			{
				String AppName = GetTextOrValue(dicOR.get("eleAppNameAppTable").replace("__APP_INDEX__", String.valueOf(j)), "text");	
				if(AppName.equals(""))
				{
				    AppName = GetTextOrValue(("//table[contains(@class,'applicationList table')]//tr[__APP_INDEX__]/td[1]/div[2]/div/a").replace("__APP_INDEX__", String.valueOf(j)), "text");
				}
				String WarningMsg = "This will delete all uploaded versions of the application from the server. This action can not be undone.";
				isEventSuccessful = PerformAction(dicOR.get("eleInstallAppDropdown") + "[" + (j) + "]", Action.Click);
				//System.out.println("dolly1");
				if (isEventSuccessful)
				{
					//isEventSuccessful = PerformAction(dicOR.get("eleDeleteOption_AppPage") + "[" + (j) + "]", Action.ClickUsingJS);
					isEventSuccessful = PerformAction(dicOR.get("eleDeleteOption_AppPage"), Action.ClickUsingJS);
					//System.out.println("dolly2");
					if (isEventSuccessful)
					{
						//Verifying confirmation popup is opened.
						PerformAction(dicOR.get("eleDialog"), Action.WaitForElement,"10");
					    confirmationHeader = GetTextOrValue(dicOR.get("eleDialog"),"text");
						//System.out.println("dolly3");
						
						//System.out.println("dolly4");
						System.out.println(confirmationHeader);
						//System.out.println("dolly5");
						
						if (confirmationHeader.equals("Delete"+" " +AppName+"?"))
						{
							//System.out.println("dolly6");
							System.out.println("Delete"+" " +AppName+"?");
							//System.out.println("dolly7");
							PopUptext = GetTextOrValue("eleConfirmDisableMsg", "text");
							if(!(PopUptext.equals(WarningMsg)))
							{	
								strActualResult =  AppName + " :"+ "Warning message :  " + PopUptext;
								TempErrMsg = TempErrMsg+strActualResult;
							}
						}
						else
						{
							strActualResult = "Appname:" +  AppName+  " 'Confirm Delete' popup is not opened.";
							TempErrMsg = 	TempErrMsg + strActualResult;
						}
					}
					else
					{
						strActualResult = "AppName " + AppName+ " :"+ "'Could not click on Delete option";
						TempErrMsg = 	TempErrMsg + strActualResult;
					}
						
				}
				else
				{
					strActualResult = " AppName " + AppName+ " :"+ " 'Could not click on Install Dropdown";
					TempErrMsg = 	TempErrMsg + strActualResult;
				}
			PerformAction("btnCancel", Action.Click);		    
			}
			if(TempErrMsg.equals(""))
			{
				isEventSuccessful=true;
				strActualResult = "Correct Warning message is displayed for all the displayed applications"; 
			}
			else
				throw new Exception(TempErrMsg);
		}
		catch (Exception e)
		{
			isEventSuccessful = false;
			/*strErrMsg_AppLib = "Exception at line number : " + tangible.DotNetToJavaStringHelper.substring(e.StackTrace, e.StackTrace.getLength() - 7, 7) + "; " + e.getMessage();*/
			strActualResult = "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		reporter.ReportStep("Verify confirmation message is displayed", "'Confirmation Message' should be displayed when deleting any application", strActualResult, isEventSuccessful);
	}
}