package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * JIRA ID --> QA-1
 */

public class _473_Verify_that_online_all_option_in_devicelist_in_CLI_should_work extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "", outputText = "", deviceName="";
	private String xpath = "";
	int count=0;
	Object[] Values = new Object[5]; 

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		String EmailAddress = dicCommon.get("EmailAddress");
		String Password = dicCommon.get("Password");

		//*************************************************************//                     
		// Step 1 : login to deviceConnect with test user.
		//*************************************************************//                     
		strstepDescription = "Login to deviceConnect with valid user.";
		strexpectedResult = "User should be logged in successfully.";
		isEventSuccessful = Login();


		//**************************************************************************//
		// Step 2 : Connect to an android device and verify deviceViewer is launched on the windows desktop.
		//**************************************************************************//
		if (isEventSuccessful)
		{	
	     	 Values = ExecuteCLICommand("devicelistall", "Android", EmailAddress, Password, "", "");
	     	 //isEventSuccessful = (boolean)Values[4];
	     	 //outputText=(String)Values[1];
	     	 count=(Integer)Values[0];
	     	 deviceName=(String)Values[3];
	     	 isEventSuccessful=selectStatus("Available,In Use,Offline,Disabled");
			 isEventSuccessful=selectPlatform("iOS,Android");
	     	 int noOfDevices = getelementCount(GenericLibrary.dicOR.get("eleDevicesHolderListView"))-1;
			 if (count==noOfDevices)
				{
				   isEventSuccessful=true;
				   strActualResult = "CLI count matches with DC UI count : "+noOfDevices;
				}
				else
				{
					isEventSuccessful=false;
					strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
				}
			
		}
		else
		{
			return; // Return if in use is not selected.
		}
		reporter.ReportStep("Verify DC UI count with status Available, In Use, Offline, Disabled with CLi device count " , "CLI count should match with DC UI count", strActualResult, isEventSuccessful);
		if (!isEventSuccessful)
		{
			return;
		}
	}
}