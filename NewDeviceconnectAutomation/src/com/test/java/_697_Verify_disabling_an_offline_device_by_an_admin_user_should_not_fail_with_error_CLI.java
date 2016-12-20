package com.test.java;

import java.io.IOException;
import java.util.regex.Pattern;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-134
 */
public class _697_Verify_disabling_an_offline_device_by_an_admin_user_should_not_fail_with_error_CLI extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	Object[] values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{
		
		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login();

		
		//*************************************************************//                     
		// Step 2 : Navigate to first available device details page
		//*************************************************************//  
		isEventSuccessful =GoToDevicesPage();  //Navigates to Devices page
        
        //Applying filters
        isEventSuccessful = selectPlatform("Android,iOS");
        isEventSuccessful = selectStatus("Offline");
        
        //Selecting first device
        values=GoTofirstDeviceDetailsPage(); 
        deviceName=(String)values[1];

		//**************************************************************************//
		// Step 3 : Execute CLI Command to disable an offline device
		//**************************************************************************//
		values=ExecuteCLICommand("disable", "", "", "", deviceName, "");
     	 isEventSuccessful = (boolean)values[2];
     	 outputText=(String)values[0];
     	 deviceName=(String)values[3];
     	 strActualResult="Command executed is <br><blockquote><div style=\"background-color:#DCDCDC; color:#000000; font-style: normal; font-family: Georgia; \">"+dicOutput.get("executedCommand") +"</div></blockquote> <br>";
		 if (isEventSuccessful)
			{
			   strActualResult += "Output compared from CLI is "+outputText;
			}
			else
			{
				isEventSuccessful=false;
				strActualResult += "ExecuteCLICommand--- CLI Output - "+ outputText+ strErrMsg_AppLib;
			}
		reporter.ReportStep("Execute CLI Command to disable an offline device" , "Command executed successfully.", strActualResult, isEventSuccessful);

		strStepDescription = "No error message should be thrown by CLI Command.";
		strexpectedResult = "No error message.";
		
		if (outputText==null)
		{
			isEventSuccessful=true;
			strActualResult="No error message. CLI output is "+outputText;
		}else
		{
			isEventSuccessful=false;
			strActualResult="Error message. CLI output is "+outputText;
		}
	        
		reporter.ReportStep(strStepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		strStepDescription = "Verify Device status is now Disabled.";
		strexpectedResult = "Device status is Disabled.";
		
		PerformAction("browser", Action.Refresh);
		PerformAction("browser","waitforpagetoload");
		PerformAction(dicOR.get("lnkShowDetails"),Action.Click);
		isEventSuccessful = VerifyOnDeviceDetailsPage("Status ", "CONTAINS__" + "Disabled");
		if (isEventSuccessful)
		{
		   strActualResult ="Device status is Disabled";
		}
		else
		{
			strActualResult = "Device status is not disabled."+ strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strexpectedResult, strActualResult, isEventSuccessful);
	
		
	}
}