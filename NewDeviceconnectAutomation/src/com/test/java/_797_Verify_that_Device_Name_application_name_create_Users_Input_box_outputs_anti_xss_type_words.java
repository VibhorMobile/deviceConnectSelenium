package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-168
 */
public class _797_Verify_that_Device_Name_application_name_create_Users_Input_box_outputs_anti_xss_type_words extends ScriptFuncLibrary {
	
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "",deviceName,editedDeviceName,editedAppName,appName;
	private boolean isStepSuccessful=false;
	private String [] Name={"<script>","</script>","<SCRIPT>","</SCRIPT>","<script-->","<"
			+ "script> ","< / script>","</script >","<</script<>>>","<</script<script>>> ","<</script>","<script>>>" ,"</</script><script>"};

	public final void testScript() throws InterruptedException, IOException
	{
		// Variables from datasheet//////////////////
		try{


			String EmailAddress = dicCommon.get("EmailAddress");
			String Password = dicCommon.get("Password");


			//*************************************************************//                     
			// Step 1 : login to deviceConnect with Tester user.
			//*************************************************************//                     
			isEventSuccessful = Login();
			//*************************************************************/                     
			// Step 4 : Go to Applications Page and Get Name for First App
			//*************************************************************//
			GoToApplicationsPage();
			GoToFirstAppDetailsPage();
			appName=GetTextOrValue(dicOR.get("eleAppNameDisplay"),"text");

			//*************************************************************/                     
			// Step 3 : Edit AppName and Verify
			//*************************************************************//
			for(int i=0;i<=Name.length-1;i++){
			editedAppName=appName+Name[i];
			EditAndVerifyAppName(editedAppName);
			
			}
			
			EditAndVerifyAppName(appName);
			//*************************************************************/                     
			// Step 2 : Go to Devices and first device details page
			//*************************************************************//
			GoToDevicesPage();
			selectStatus("Available");
			GoTofirstDeviceDetailsPage();
			deviceName=GetTextOrValue(dicOR.get("deviceName_detailsPage"), "text");

			//*************************************************************/                     
			// Step 3 : Edit DeviceName and verify it Changed
			//*************************************************************//
			for(int i=0;i<=Name.length-1;i++){
				
				editedDeviceName=deviceName+Name[i];
				System.out.println(editedDeviceName);
				EditAndVerifyDeviceName(editedDeviceName);
			}
			
			EditAndVerifyDeviceName(deviceName);
			
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "eDIT dEVICE AND APP NAME--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
