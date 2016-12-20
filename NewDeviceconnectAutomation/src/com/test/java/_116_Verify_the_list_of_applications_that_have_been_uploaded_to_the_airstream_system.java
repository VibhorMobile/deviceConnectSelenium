package com.test.java;

import java.util.ArrayList;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2155
 */
public class _116_Verify_the_list_of_applications_that_have_been_uploaded_to_the_airstream_system extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "";

	public final void testScript()
	{
		//**************************************************************************//
		//******  Step 1 - Launch deviceConnect with Admin credentials *************//
		//**************************************************************************//            
		isEventSuccessful = Login();

		//**************************************************//
		//******* Step 2 - Select Available Filter and then Click Connect button ***********//
		//*************************************************//
		isEventSuccessful = selectStatus("Available");
		if (isEventSuccessful)
		{
			isEventSuccessful = OpenLaunchAppDialog("first", "", "list");
			//SelectDevice("first","connect");
			if (isEventSuccessful)
			{
				ArrayList<String> appList = getApplistOnLaunchAppPage();
				isEventSuccessful = appList.size() > 0;
				if (isEventSuccessful)
				{
					strActualResult = "Following applications is displayed for selected device -<br>" + appList.toString() ;//DotNetToJavaStringHelper.join(",", appList);
				}
				else
				{
					strActualResult = "Application list is not displayed for selected device.";
				}
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		reporter.ReportStep("Click Connect button.", "The user should be presented with a list of applications installed on the device.", strActualResult, isEventSuccessful);

	}

}