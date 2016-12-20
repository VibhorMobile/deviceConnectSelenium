package com.test.java;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-1816
 */
public class _757_Verify_that_the_device_order_does_not_change_when_list_refresh_button_is_pressed_and_nothing_has_changed extends ScriptFuncLibrary {


	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strStepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	private ArrayList<String> deviceList = new ArrayList<String>();
	private ArrayList<String> refreshedDeviceList = new ArrayList<String>();
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
			// Step 3 : Verify devices Order and Store it to List
			//**************************************************************************//
			strStepDescription="Verify devices order and store it to list";
			strExpectedResult="Devices Order Stored";
			int rowCount=getelementCount(dicOR.get("eleDevice_List_rows"));
			int noOfDevices=rowCount-2;
			System.out.println(noOfDevices);

			for(int row=2;row<=noOfDevices+1;row++){
				String rowValue=String.valueOf(row);
				String deviceRow=dicOR.get("eleDeviceName_DeviceIndexPage").replace("__INDEX__", rowValue);
				deviceName=GetTextOrValue(deviceRow, "text");
				System.out.println(deviceName);


				deviceList.add(deviceName);

			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 4 : Click on Refresh button
			//**************************************************************************//
			strStepDescription="Click on Refresh button";
			strExpectedResult="Clicked on Refresh button";
			isEventSuccessful=PerformAction(dicOR.get("btnRefresh_Devices"), Action.Click);
			if(isEventSuccessful){
				strActualResult="Clicked on devices refresh button";
			}
			else{
				strActualResult=" Unable to Click on devices refresh button";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 5 : Verify After refresh device order did not changed
			//**************************************************************************//

			strStepDescription="Verify after refresh device order did not changed";
			strExpectedResult="device order remains same after clicking on refresh";
			rowCount=getelementCount(dicOR.get("eleDevice_List_rows"));
			int noOfDevicesAfterRefresh=rowCount-2;
			System.out.println(noOfDevicesAfterRefresh);
			if(noOfDevices==noOfDevicesAfterRefresh){
				for(int row=2;row<=noOfDevicesAfterRefresh+1;row++){



					String rowValue=String.valueOf(row);
					String deviceRow=dicOR.get("eleDeviceName_DeviceIndexPage").replace("__INDEX__", rowValue);
					deviceName=GetTextOrValue(deviceRow, "text");
					refreshedDeviceList.add(deviceName);
					System.out.println(deviceName);
					if(!deviceName.equals(deviceList.get(row-2))){
						strActualResult="Device order got changed after refresh "+ deviceList.get(row-2)+ "changed to "+deviceName;
						isEventSuccessful=false;
						break;
					}
					else{
						strActualResult="Device order remained same";
						isEventSuccessful=true;
					}
				}
	
			}
			else{
				strActualResult="No of devices changed after refresh new device count is "+noOfDevicesAfterRefresh+"while no of devices before was:"+noOfDevices;
				isEventSuccessful=false;
			}


			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
