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
 * Jira Test Case Id: QA-323
 */
public class _746_Verify_LastUpdated_field_Applist_and_Added_field_under_All_Builds_sec_on_Appdetails_is_notempty extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String serverIP=dicCommon.get("ApplicationURL");
	String appName="PhoneLookup"; 
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
		


		//**********************************************************************************************//                     
		// Step 3 : Verify no empty data record available in Last Updated column on App List page.
		//**********************************************************************************************//  
		
		strStepDescription="Verify no empty data record available in Last Updated column on App List page.";
		strExpectedResult="No Empty data record should be available in Last Updated column.";
		isEventSuccessful=true;
		strActualResult="No Empty data records available in Last Updated column.";
			

		//table[Contains(@class,'applicationList')]/tbody/tr[count(//th[@title='__COLUMNNAME__'])>0]/td[count(//th[@title='__COLUMNNAME__']/preceding-sibling::th)+1]
		List<String> LastUpdatedColDataList = getColumnsValue(dicOR.get("eleColumnData_SpecificColumn_AppListTable").replace("__COLUMNNAME__", "Last Updated"));
		System.out.println(LastUpdatedColDataList);
		
		if ((LastUpdatedColDataList.contains(null)) || (LastUpdatedColDataList.contains("")))
		{
			isEventSuccessful=false;
			strActualResult="Empty data records available in Last Updated column on App List page.";
		}
			
		
		if (isEventSuccessful)
		{
			Iterator<String> iterator=LastUpdatedColDataList.iterator();
			while (iterator.hasNext())
			{
				if (Pattern.matches("^\\s+$", iterator.next()))
					isEventSuccessful=false;
					strActualResult="Empty data records available in Last Updated column on App List page.";
			}
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			
		GoToFirstAppDetailsPage();
		
		//**********************************************************************************************//                     
		// Step 4 : Verify no empty data record available in Added field under All Build section App details page.
		//**********************************************************************************************//  
		
		strStepDescription="Verify no empty data record available  in Added field under All Build section App details page.";
		strExpectedResult="No Empty data record should be available  in Added field under All Build section App details page.";
		isEventSuccessful=true;
		strActualResult="No Empty data records available in Added field under All Build section App details page.";
			

		//table[Contains(@class,'applicationList')]/tbody/tr[count(//th[@title='__COLUMNNAME__'])>0]/td[count(//th[@title='__COLUMNNAME__']/preceding-sibling::th)+1]
		List<String> AddedColumData = getColumnsValue(dicOR.get("eleColumnData_SpecificColumn_AppDetailsAllBuildTable").replace("__COLUMNNAME__", "Added"));
		System.out.println(AddedColumData);
		
		if ((AddedColumData.contains(null)) || (AddedColumData.contains("")))
		{
			isEventSuccessful=false;
			strActualResult="Empty data records available in Added field under All Build section App details page.";
		}
			
		
		if (isEventSuccessful)
		{
			Iterator<String> iterator=AddedColumData.iterator();
			while (iterator.hasNext())
			{
				if (Pattern.matches("^\\s+$", iterator.next()))
					isEventSuccessful=false;
					strActualResult="Empty data records available in in Added field under All Build section App details page.";
			}
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

	}
}