package com.test.java;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1620
 */
public class _755_Verify_users_and_device_links_are_underlined extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,appName,appNameEdited,deviceStatus;
	Object[] Values = new Object[5]; 
	boolean isRetained,isRebooted;

	public final void testScript() throws InterruptedException, IOException
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
			// Step 3 : Verify device name link at index underlined
			//**************************************************************************//
			strStepDescription="Verify devices name are underlined";
			strExpectedResult="Devices name link should be underlined";
			int rowCount=getelementCount(dicOR.get("eleDevice_List_rows"));
			int noOfDevices=rowCount-2;
			System.out.println(noOfDevices);

			for(int row=2;row<=noOfDevices+1;row++){
				String rowValue=String.valueOf(row);
				String deviceName=dicOR.get("eleDeviceName_DeviceIndexPage").replace("__INDEX__", rowValue);
				String isUnderLine=getAttribute(deviceName, "css", "text-decoration");
				System.out.println(isUnderLine);
				if(!isUnderLine.equals("underline")){
					strActualResult="Device Not Underlined at position :"+row;
					isEventSuccessful=false;
					break;
				}
				else{
					strActualResult="Device Name links are underlined";
					isEventSuccessful=true;
				}
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 4 : Go to Users Page
			//**************************************************************************//
             GoToUsersPage();
         	WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='userListTable table data-grid user-list-table']/tbody/tr[1]/td[1]"))); 
			
         	//**************************************************************************//
 			// Step 5 : Verify UserName link is Underlined
 			//**************************************************************************//
             
            strStepDescription="Verify username link are underlined";
 			strExpectedResult="user name link should be underlined";
 			rowCount=getelementCount(dicOR.get("eleUserListRows_UsersPage"));
 		    int noOfUsers=rowCount;
 			System.out.println(noOfUsers);

 			for(int row=1;row<=100;row++){
 				String rowValue=String.valueOf(row);
 				String userName=dicOR.get("eleUserNameUserTable").replace("_User_Index_", rowValue);
 				String isUnderLine=getAttribute(userName, "css", "text-decoration");
 				System.out.println(isUnderLine);
 				if(!isUnderLine.equals("underline")){
 					strActualResult="UserName Not Underlined at position :"+row;
 					isEventSuccessful=false;
 					break;
 				}
 				else{
 					strActualResult="User Name links are underlined";
 					isEventSuccessful=true;
 				}
 			}
 			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
             
             
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}



}
