package com.test.java;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 26 Sep 2016
 * Last Modified Date: NA
 * Pre-requisite: Server should have Atleast one USB Hub configured with it.
 *                Admin user can only be used
 * Jira Test Case Id: QA-1999
 * Coverage from Jira Case: Complete
 */
public class _1107_API_Verify_that_information_of_Gateways_USB_hubs_and_USB_hub_ports_can_be_retrieved_in_details_using_REST_API extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	


	public Map<String,String> expectedResultMapHub= new HashMap<String,String>();//Result Map for Hubs From UI
	public Map<String,String> expectedResultMapPort= new HashMap<String,String>();//Result Map for Ports from UI
	public Map<String,String> actualResultMapPort = new HashMap<String,String>();//Result Map for Hub from API
	Map<String,String> actualResultMapHub = new HashMap<String,String>();//Result Map for Port from API

    String[] hubModel ={"publicPort","GatewayName","serialNumber","model","location","description","firmware","errorMessage","fiveVoltNow","fiveVoltMax","fiveVoltMin","uptime","firmwareDate","isErrored","name"};
	String[] portModel ={"name","serialNumber","location","usbHubSerialNumber","usbHubPortNumber","milliamps","state","flags","chargingTime","chargingProfile"};

	public final void testScript() throws InterruptedException, IOException
	{
		try{


			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("Device Gateways - Get Device Gateway Information");

			//Login to DC Server
			isEventSuccessful = Login();

			String serverIP=dicCommon.get("ApplicationURL");


			apiReporter.apiNewHeading(" Device Gateways - Get Device Gateway Information");

			//Create the curl command to get Gateway Details
			String component= "Gateway";
			String jsonResponse; //declaration of string to capture JSON Response of the API request.
			String command=apiMethods.createCurlCommand(component, "", "");	//Creates a curl command with component, parameterList and valueList.

			//adding heading in APIOverallComparison HTML for the curl command created
			apiReporter.apiNewHeading(command);

			// Step : Execute curl command to get JSON response of the request
			jsonResponse=apiMethods.execCurlCmd(command); // Executes curl command and stores its JSON ouput in jsonResponse string varaiable.
			System.out.println(jsonResponse);
			//Adding Response received against API request to API Overall report
			apiReporter.apiNewHeading(jsonResponse);
			// Step : Verifying record count is equal to 1 as using Id as a parameter
			if (apiMethods.getRecordCount(jsonResponse) > 0) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
			{
				strActualResult="As expected getting atleast 1 record.";
				isEventSuccessful=true;
			}
			else
			{
				strActualResult= "Either getting no records which is not expected. Hence failing stop test. <br> JSON Response =" +jsonResponse;
				isEventSuccessful=false;
			}
			reporter.ReportStep("Verifying record count in JSON reponse", "Record count should be equal to 1 as using Id as a parameter",strActualResult ,isEventSuccessful );
			//Creating Actual Map for Hub and ports based o values received
			String[]tempArray;
			tempArray=jsonResponse.split(",\"devices");
			int hubs=tempArray.length-1;
			for(int hub=0;hub<hubs;hub++){
				actualResultMapHub=apiMethods.getUSBHubDetails(jsonResponse, hub);

				apiReporter.apiNewHeading("USB Hub Details from API:"+actualResultMapHub);
				GoToDeviceGatewaysPage();
				int hubNo=hub+1;
				expectedResultMapHub=hubExpectedMap(String.valueOf(hubNo));
				apiReporter.apiNewHeading("USB Hub Details from UI for hub:"+hubNo+":"+expectedResultMapHub);
				expectedResultMapHub.put("fiveVoltNow", "Not Verified");
				Map<String,String> errorMap=new HashMap<String,String>();
				isEventSuccessful=apiMethods.compareActualAndExpectedMap(hubModel, expectedResultMapHub,actualResultMapHub,errorMap);
				System.out.println(errorMap);
				if(isEventSuccessful)
				{
					System.out.println("pass");
					strActualResult= "All the properties verified are matching";
					apiReporter.apiPassBlock("Successfully Verified API Gateway and Hub property for hub: "+hubNo);
				}
				else
				{
					System.out.println("Fail");
					strActualResult= "compareActualAndExpectedMap---" + errorMap + com.common.utilities.APILibrary.strErrMsg_ApiLib;
					apiReporter.apiErrorBlock("There is some Mismatch when getting Gateway details for hub:"+hubNo);
				}
				reporter.ReportStep("Compares Actual and Expected Map for hub:"+hubNo+" properties", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);
				apiReporter.CreateAppModelTableInReport(hubModel,expectedResultMapHub,actualResultMapHub,errorMap);
				for(int port=0;port<=15;port++){
					actualResultMapPort=apiMethods.getUSBPortDetails(jsonResponse, port,hubNo);
					apiReporter.apiNewHeading("USB Port Details from API for port:"+(port+1)+" from hub: "+hubNo+" "+actualResultMapPort);
					expectedResultMapPort=portExpectedMap(port+1,String.valueOf(hubNo));
					apiReporter.apiNewHeading("USB Port Details from UI for port:"+(port+1)+":"+expectedResultMapPort);
					if(!expectedResultMapPort.get("name").equals("")){
						expectedResultMapPort.put("name", "Not Verified");
					}
					if(!expectedResultMapPort.get("milliamps").equals("0 ")){
						expectedResultMapPort.put("milliamps", "Not Verified");
					}

					isEventSuccessful=apiMethods.compareActualAndExpectedMap(portModel, expectedResultMapPort,actualResultMapPort,errorMap);
					System.out.println(errorMap);
					if(isEventSuccessful)
					{
						System.out.println("pass");
						strActualResult= "All the properties verified are matching";
						apiReporter.apiPassBlock("Successfully Verified Gateway Hub Port Details for port: "+(port+1));
					}
					else
					{
						System.out.println("Fail");
						strActualResult= "compareActualAndExpectedMap---" + errorMap + com.common.utilities.APILibrary.strErrMsg_ApiLib;
						apiReporter.apiErrorBlock("There is some Mismatch when getting GatewayHub port details through API for port:"+port+1);
					}
					apiReporter.CreateAppModelTableInReport(portModel,expectedResultMapPort,actualResultMapPort,errorMap);
					reporter.ReportStep("Compares Actual and Expected Map", "Actual and Expected Map should match.", strActualResult, isEventSuccessful);

				}
			}

			apiReporter.apiOverallHtmlLinkStep();



		}
		catch(Exception ex)
		{
			apiReporter.apiErrorBlock("Something went wrong... Error Message "+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			//Adds a step Provides API Overall Comparison file link in DC Selenium Execution Report
			apiReporter.apiOverallHtmlLinkStep();

		}

	}	

}
