package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1135
 */
public class _745_Verify_that_on_Firefox_browser_Filter_checkboxes_should_retain_after_browser_refresh extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String deviceName,inUse,outputText;
	Object[] Values = new Object[5]; 
	boolean isIOSSelected,isAndroidSelected;






	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//*************************************************************//                     
			// Step 1 : login to deviceConnect with Admin User
			//*************************************************************//  


			isEventSuccessful = Login();
			//*************************************************************//      
			// Step 2 : Go to Devices Page 
			//*************************************************************//
			GoToDevicesPage();

			//**************************************************************************//
			// Step 3 : Uncheck iOS platform Checkbox
			//**************************************************************************//
			//isIOSSelected= PerformAction("//h4[contains(text(),'Status')]/../ul[3]/li[1]/label/input", Action.isSelected);

			isEventSuccessful=PerformAction("//h4[contains(text(),'Platform')]/../ul[2]/li[1]/label/input", Action.DeSelectCheckbox);
			if(isEventSuccessful){
				strActualResult="iOS platform checkbox Unchecked";
			}
			else{
				strActualResult="iOS platform checkbox Not Unchecked";
			}
			reporter.ReportStep("Uncheck iOS checkbox", "iOS checkbox unchecked", strActualResult, isEventSuccessful);
			//**************************************************************************//
			// Step 3 : Refresh Browser and Verify iOS checkbox is unchecked
			//**************************************************************************//
			PerformAction("browser", Action.Refresh);


			isIOSSelected= PerformAction("//h4[contains(text(),'Platform')]/../ul[2]/li[1]/label/input", Action.isSelected);
			if(isIOSSelected){
				strActualResult="iOS platform is not unchecked";
				isEventSuccessful=false;
			}
			else{
				strActualResult="iOS platform is unchecked";
				isEventSuccessful=true;
			}
			reporter.ReportStep("Verify iOS platform is unchecked after refresh", "iOS checkbox unchecked", strActualResult, isEventSuccessful);


		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify checkbox is unchecked after refresh--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
