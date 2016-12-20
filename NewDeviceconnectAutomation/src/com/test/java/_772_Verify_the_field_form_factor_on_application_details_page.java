package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-1011
 */
public class _772_Verify_the_field_form_factor_on_application_details_page extends ScriptFuncLibrary {


	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "", appFormFactorPhone="Dollar General 2.1.ipa",appFormFactorTablet="DLITE_18.0.6-resigned.ipa",formFactor;
	String [] android={"Android"};

	public final void testScript()
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();

			//**********************************************************//
			// Step 2 - Go to Applications page //
			//**********************************************************//                                   
			isEventSuccessful = GoToApplicationsPage();

			//**********************************************************//
			// Step 3- Upload application For which form factor is Phone
			//**********************************************************//  

			strStepDescription = "Upload application with Form factor Phone ";
			strExpectedResult = "App uploaded successfully";
			isEventSuccessful =   uploadApplication(appFormFactorPhone);

			if(isEventSuccessful)
			{
				strActualResult="Application Uploaded ";

			}
			else
			{
				strActualResult = "Application not uploaded";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//**********************************************************//
			// Step 3- Upload application For which form factor is Tablet
			//**********************************************************//  

			strStepDescription = "Upload application with Form factor Tablet ";
			strExpectedResult = "App uploaded successfully";
			isEventSuccessful =   uploadApplication(appFormFactorTablet);

			if(isEventSuccessful)
			{
				strActualResult="Application Uploaded ";

			}
			else
			{
				strActualResult = "Application not uploaded";
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);


			//**********************************************************//
			// Step 4 - Search DeviceControl App and Verify Form Factor is Universal
			//**********************************************************//   
			searchDevice_DI("Device Control");
			GoToFirstAppDetailsPage();
			strStepDescription = "Verify Form Factor is universal for the application";
			strExpectedResult = "Form factor should be universal for the app";
			//isEventSuccessful = GetTextOrValue(dicOR.get("appAllInforAppDetails").replace("*", "8"),"text").equals("Apple iPhone OS Application Signing");
			formFactor=getDetailFromApplicationDetailsPage("Form Factor");
			System.out.println("Form Factor is :"+formFactor);
			if (formFactor.equals("Universal"))
			{
				strActualResult = "Correct Form Factor shown for application : "+formFactor;
				isEventSuccessful=true;
			}
			else
			{
				strActualResult =  "Correct Form Factor not shown for application: "+formFactor;
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);	

			//**********************************************************//
			// Step 5 - Search Dollar General app and Verify Form Factor is Phone
			//**********************************************************// 
			GoToApplicationsPage();
			selectPlatform_Application("iOS");
			searchDevice_DI("Dollar General");
			GoToFirstAppDetailsPage();
			strStepDescription = "Verify Form Factor is Phone for the application";
			strExpectedResult = "Form factor should be Phone for the app";
			//isEventSuccessful = GetTextOrValue(dicOR.get("appAllInforAppDetails").replace("*", "8"),"text").equals("Apple iPhone OS Application Signing");
			formFactor=getDetailFromApplicationDetailsPage("Form Factor");
			System.out.println("Form Factor is :"+formFactor);
			if (formFactor.equals("Phone"))
			{
				strActualResult = "Correct Form Factor shown for application : "+formFactor;
				isEventSuccessful=true;
			}
			else
			{
				strActualResult =  "Correct Form Factor not shown for application: "+formFactor;
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);	

			//**********************************************************//
			// Step 4 - Search Dlite App and Verify Form Factor is Tablet
			//**********************************************************//   
			GoToApplicationsPage();
			selectPlatform_Application("iOS");
			searchDevice_DI("Dlite");
			GoToFirstAppDetailsPage();
			strStepDescription = "Verify Form Factor is Tablet for the application";
			strExpectedResult = "Form factor should be Tablet for the app";
			//isEventSuccessful = GetTextOrValue(dicOR.get("appAllInforAppDetails").replace("*", "8"),"text").equals("Apple iPhone OS Application Signing");
			formFactor=getDetailFromApplicationDetailsPage("Form Factor");
			System.out.println("Form Factor is :"+formFactor);
			if (formFactor.equals("Tablet"))
			{
				strActualResult = "Correct Form Factor shown for application : "+formFactor;
				isEventSuccessful=true;
			}
			else
			{
				strActualResult =  "Correct Form Factor not shown for application: "+formFactor;
				isEventSuccessful=false;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);	



		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Form factor--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}



}
