package com.test.java;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;

import net.sf.cglib.asm.Label;

import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-991
 */
public class _760_Verify_only_Admin_users_are_allowed_to_download_application_from_Application_List_page extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String serverIP=dicCommon.get("ApplicationURL");
	String appName; 
	int columnCount;
	Object[] values = new Object[5]; 
	Object[] appvalues = new Object[5];


	public final void testScript() throws InterruptedException, IOException
	{

		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strStepDescription = "Login to deviceConnect with valid user.";
		strExpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login();


		//*************************************************************//                     
		// Step 2 : Navigate to Applications page
		//*************************************************************//  
		isEventSuccessful=GoToApplicationsPage();
				
		/*System.out.println(driver.findElements(By.xpath("//table[contains(@class,'applicationList')]//th[@title='Last Updated']/preceding-sibling::th")).size());
		
		int iLastUpdatedColIndex = 0;
		iLastUpdatedColIndex = driver.findElements(By.xpath(dicOR.get("eleSpecificColumnPrecedingHeader_AppListTable").replace("__COLUMNNAME__", "Last Updated"))).size();
		iLastUpdatedColIndex ++;
		System.out.println(iLastUpdatedColIndex);
		    	
		List<String> LastUpdatedColDataList = getColumnsValue(dicOR.get("eleColumnData_WithIndex_AppListTable").replace("__INDEX__", Integer.toString(iLastUpdatedColIndex)));
		
		System.out.println(LastUpdatedColDataList);
		*/
		
		
		//isEventSuccessful=selectApplicationOption_MultiFunctionDropdown("","Download"); // Download the app (can cause pop-up in firefox hence commenting)
		
		strStepDescription="Verify Download link is available for admin user and  href is correct";
		strExpectedResult="Download link should be available and href should be correct.";

		appName=GetTextOrValue(dicOR.get("eleAppNameAppTable").replace("__APP_INDEX__", "1"),"text");
		System.out.println("Selecting one app-----"+appName);
		isEventSuccessful =  PerformAction(dicOR.get("btnMultiFunctionDropdown_AppListPage").replace("__INDEX__", "1"), Action.Click);
		
		isEventSuccessful= PerformAction(dicOR.get("lnkSpecificApp_MultiFuntionalDropdown_Option").replace("__APPNAME__",appName).replace("__LINKTEXT__", "Download"), Action.Exist);
	
		if (isEventSuccessful)
		{
			String strHref=getAttribute(dicOR.get("lnkSpecificApp_MultiFuntionalDropdown_Option").replace("__APPNAME__",appName).replace("__LINKTEXT__", "Download"), "href");			

			GoToFirstAppDetailsPage();
			waitForPageLoaded();
			String appId=GetAppGUID();
			if (strHref.equalsIgnoreCase("http://"+dicCommon.get("ApplicationURL")+"/Application/Download/"+appId))
			{
				isEventSuccessful=true;
				strActualResult="Download link is available and href is correct. expected " +"/Application/Download/"+appId +" and actual =" +strHref;
			}
			else
			{
				isEventSuccessful=false;
				strActualResult="Download link is available but href is not correct. expected " +"/Application/Download/"+appId +" and actual =" +strHref;
			}
				
		}
		else
			strActualResult="Download link inside multifunction dropdown is not available.";
		
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		LogoutDC();

		//*************************************************************//                     
		// Step  : login to deviceConnect with test user.
		//*************************************************************//    

		String testerEmailAddress = dicCommon.get("testerEmailAddress");
		String testerPassword = dicCommon.get("testerPassword");
		
		strStepDescription = "Login to deviceConnect with valid tester user.";
		strExpectedResult = "Tester User should be logged in successfully.";
		isEventSuccessful = Login(testerEmailAddress,testerPassword);
		
		isEventSuccessful=GoToApplicationsPage();
		
		strStepDescription="Verify Download link is not available for tester user";
		strExpectedResult="Download link should not be available.";

		appName=GetTextOrValue(dicOR.get("eleAppNameAppTable").replace("__APP_INDEX__", "1"),"text");
		System.out.println("Selecting one app-----"+appName);
		
		isEventSuccessful =  PerformAction(dicOR.get("btnMultiFunctionDropdown_AppListPage").replace("__INDEX__", "1"), Action.Click);
		
		isEventSuccessful= PerformAction(dicOR.get("lnkSpecificApp_MultiFuntionalDropdown_Option").replace("__APPNAME__",appName).replace("__LINKTEXT__", "Download"), Action.isNotDisplayed);
	
		if (isEventSuccessful)
			strActualResult="Download link inside multifunction dropdown is not available for tester user.";	
		else
			strActualResult="Download link inside multifunction dropdown is available for tester user.";
		
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		
		
	}
}