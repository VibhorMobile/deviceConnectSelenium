package com.test.java;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1235	
 */
public class _795_Verify_Remove_button_is_not_displayed_for_User_who_doesnt_have_Device_delete_entiltement extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,inUse,outputText,appAreaText,appUninstallMsg;
	Object[] Values = new Object[5]; 
	boolean flag,isAndroidSelected;






	public final void testScript() throws InterruptedException, IOException
	{
		try{

			//*************************************************************//                     
			// Step 1 : login to deviceConnect with Admin User
			//*************************************************************//  


			isEventSuccessful = Login(dicCommon.get("testerEmailAddress"),dicCommon.get("testerPassword"));
			//*************************************************************//      
			// Step 2 : Go to Device Page and Open details page of first device displayed
			//*************************************************************//
			GoToDevicesPage();
			selectStatus("Offline");
			GoTofirstDeviceDetailsPage();

			//**************************************************************************//
			// Step 3 : Verify Remove button not available for tester user.
			//**************************************************************************//
			flag=PerformAction(dicOR.get("removedeviceLocator"), Action.isDisplayed);
			if(flag){
				strActualResult="Remove button displayed for tester user";
				isEventSuccessful=false;
			}
			else{
				strActualResult="Remove button not  displayed for tester user";
				isEventSuccessful=true;
			}
			reporter.ReportStep("Verify Remove button not displayed for tester user", "Remove button should not be displayed", strActualResult, isEventSuccessful);
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify checkbox is unchecked after refresh--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

}
