package com.test.java;

import java.util.regex.Pattern;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor.GetterSetterReflection;
/*
 * Jira Test Case Id: QA-615
 */
public class _800_Verify_Inuse_offline_online_since_label_timestamp_appears_in_proper_Date_Time_format_on_Device_detail_page extends ScriptFuncLibrary {
	
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String  strActualResult = "",pageHeader,onlineSince,offlineSince, outputText = "",InUseSince,deviceNameUI,deviceName;
	private String [] Options={"Connected Since","Offline Since","In Use Since"};
	private  String pattern ="^(0?[1-9]|1[012])/([1-9]|([012][0-9])|(3[01]))/\\d\\d\\d\\d [012]{0,1}[0-9]:[0-6][0-9]:[0-6][0-9] (AM|PM)$";
	//private  String date="(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\d\d)\s+([0-1]\d|2[0-3]):[0-5]\d:[0-5]\d)";
	Object[] Values = new Object[5]; 
	public final void testScript() throws InterruptedException
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();

			//**********************************************************//
			// Step 2 - GetOnline Since Value and check if it is in proper DD/MM/YYYY format
			//**********************************************************//  
			 GoToDevicesPage();
			 GoTofirstDeviceDetailsPage();
			 PerformAction(dicOR.get("lnkShowDetails"), Action.Click);
			 onlineSince=GetTextOrValue(dicOR.get("eleOnlineSince_DeviceDetailsPage"), "text");
			 System.out.println(onlineSince+"value at first is");
			 onlineSince=onlineSince.split(" ")[0]+" "+onlineSince.split(" ")[1]+" "+onlineSince.split(" ")[2];
			 System.out.println(onlineSince);
			 isEventSuccessful = Pattern.matches("^(0?[1-9]|1[012])/([1-9]|([012][0-9])|(3[01]))/\\d\\d\\d\\d [012]{0,1}[0-9]:[0-6][0-9]:[0-6][0-9] (AM|PM)$", onlineSince);  
			 if(isEventSuccessful){
				 strActualResult="OnlineSince Value is in valid Date time format";
			 }
			 else{
				 strActualResult="OnlineSince Value is not in valid Date time format";
			 }
			 reporter.ReportStep("Verify online since is in valid date time format", "Online since should be in MM/DD/YYYY HH:MM:SS AM/PM format", strActualResult, isEventSuccessful);

		 
		//**********************************************************//
		// Step 2 - GetOnline Since Value and check if it is in proper DD/MM/YYYY format
		//**********************************************************//  
		 GoToDevicesPage();
		 selectStatus("Offline");
		 GoTofirstDeviceDetailsPage();
		 PerformAction(dicOR.get("lnkShowDetails"), Action.Click);
		 offlineSince=GetTextOrValue(dicOR.get("eleOfflineSince_DeviceDetailsPage"), "text");
		 offlineSince=offlineSince.split(" ")[0]+" "+offlineSince.split(" ")[1]+" "+offlineSince.split(" ")[2];
		 
		 isEventSuccessful = Pattern.matches(pattern, offlineSince);  
		 if(isEventSuccessful){
			 strActualResult="Offline Value is in valid Date time format";
		 }
		 else{
			 strActualResult="Offline Since Value is not in valid Date time format";
		 }
		 reporter.ReportStep("Verify Offline since is in valid date time format", "Oflline since should be in MM/DD/YYYY HH:MM:SS AM/PM format", strActualResult, isEventSuccessful);
		 //**********************************************************//
		 // Step 4 - connect a device
		 //**********************************************************//  
		 GoToDevicesPage();
		 selectPlatform("iOS");
		 selectStatus("Available");
		 GoTofirstDeviceDetailsPage();
		 deviceNameUI=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");
		 Values = ExecuteCLICommand("run", "Android", "", "", deviceNameUI, "");
		 isEventSuccessful = (boolean)Values[4];
		 outputText=(String)Values[1];
		 deviceName=(String)Values[3];
		 if (isEventSuccessful)
		 {
			 strActualResult = "Viewer launched after connecting to an iOS device:  " + Values[3] + " & processfound : " +  Values[1];
		 }
		 else
		 {
			 strActualResult = "ExecuteCLICommand---" + strErrMsg_AppLib;
		 }
		 //**********************************************************//
		 // Step 4 - Verify In use since value is in proper format
		 //**********************************************************//  
		 PerformAction(dicOR.get("lnkShowDetails"), Action.Click);
		InUseSince=GetTextOrValue(dicOR.get("eleInUse_DeviceDetailsPage"), "text");
		InUseSince=InUseSince.split(" ")[0]+" "+InUseSince.split(" ")[1]+" "+InUseSince.split(" ")[2];
		 
		 isEventSuccessful = Pattern.matches(pattern, InUseSince);  
		 if(isEventSuccessful){
			 strActualResult="InUse Value is in valid Date time format";
		 }
		 else{
			 strActualResult="InUse since Value is not in valid Date time format";
		 }
		 reporter.ReportStep("Verify Inuse since is in valid date time format", "Inuse since should be in MM/DD/YYYY HH:MM:SS AM/PM format", strActualResult, isEventSuccessful);

}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

}
