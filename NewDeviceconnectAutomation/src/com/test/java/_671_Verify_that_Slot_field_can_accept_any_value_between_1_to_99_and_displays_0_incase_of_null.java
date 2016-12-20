package com.test.java;

import java.io.IOException;
import java.util.Random;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-223
 */
public class _671_Verify_that_Slot_field_can_accept_any_value_between_1_to_99_and_displays_0_incase_of_null extends ScriptFuncLibrary {
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private boolean isStepSuccessful=false;
	int slotNumber;
	String slotValue;



	public final void testScript() throws InterruptedException, IOException
	{
		try{


			String EmailAddress = dicCommon.get("EmailAddress");
			String Password = dicCommon.get("Password");


			//*************************************************************//                     
			// Step 1 : login to deviceConnect with admin user.
			//*************************************************************//                     
			isEventSuccessful = Login();


			//*************************************************************/                     
			// Step 2 : Click to Devices and open First Device and Go to details Page
			//*************************************************************//
			strstepDescription="Click on First Device displayed and go to device detaiils page";
			strExpectedResult="Clicked on first device and navigated to details page. ";
			isEventSuccessful = (boolean)GoTofirstDeviceDetailsPage()[0]; //Navigating to first Available or Inuse, Offline Or Disabled DeviceDetails page
			String deviceName = GetTextOrValue(dicOR.get("eleEnterDeviceName_DeviceDetailsPage"), "text");
			isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
			if(isEventSuccessful){
				strActualResult="Navigated to details of device"+deviceName;
				isEventSuccessful=true;
			}
			else{
				strActualResult="Details page not opened for device"+deviceName;
				isEventSuccessful=false;
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful); 
			//*************************************************************/                     
			// Step 3 : Edit slot # and provide random values between 1 to 99
			//*************************************************************//
			
			
			for(int slotValueCounter=1;slotValueCounter<=5;slotValueCounter++){
				Random r=new Random();
				slotNumber=r.nextInt(100);
				String slot=String.valueOf(slotNumber);
				strstepDescription="Edit slot no and provide value "+slot;
				strExpectedResult="Slot # gets changed to :"+slot;
				
				isEventSuccessful = PerformAction("eleSlotMousehover_deviceDetailsPage", Action.DoubleClick);
				isEventSuccessful = PerformAction( "eleEditSlot_deviceDetailsPage", Action.Clear);
				isEventSuccessful = PerformAction( "eleEditSlot_deviceDetailsPage", Action.sendkeys , slot);
				isEventSuccessful = PerformAction("eleSlotSaveChangesButton_DeviceDetailsPage", Action.Click);
				slotValue=GetTextOrValue("eleSlot_ShowDetails_DeviceDetailsPage", "text");
				if(slotValue.equals(slot)){
					strActualResult="slot # changed to :"+slot;
					isEventSuccessful=true;
				}
				else{
					strActualResult="slot # has not accepted value :"+slot;
					isEventSuccessful=false;
				}
				reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful); 
			}
			//*************************************************************/                     
			// Step 4 : Edit slot # and provide null
			//*************************************************************//
			strstepDescription="Edit slot no and provide null";
			strExpectedResult="Slot # changed to 0";
			
			isEventSuccessful = PerformAction("eleSlotMousehover_deviceDetailsPage", Action.DoubleClick);
			isEventSuccessful = PerformAction( "eleEditSlot_deviceDetailsPage", Action.Clear);
			isEventSuccessful = PerformAction("eleSlotSaveChangesButton_DeviceDetailsPage", Action.Click);
			PerformAction("browser", Action.Refresh);
			isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
			slotValue=GetTextOrValue("eleSlot_ShowDetails_DeviceDetailsPage", "text");
			if(slotValue.equals("0")){
				strActualResult="when null is provided slot # changed to :"+slotValue;
				isEventSuccessful=true;
			}
			else{
				strActualResult="when null is provided slot # changed to :"+slotValue;
				isEventSuccessful=false;
			}
		 
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful); 
			 
			


		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "View Token Field for User--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}

	
	
	
}
