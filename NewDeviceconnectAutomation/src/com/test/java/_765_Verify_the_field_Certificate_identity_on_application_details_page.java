package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-780
 */
public class _765_Verify_the_field_Certificate_identity_on_application_details_page extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strErrMgs_Script = "", strActualResult = "", appNameApple="Dollar General 2.1.ipa",CertIdentity;
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
			// Step 3- Upload application with certificate identity  Apple iPhone OS Application Signing 
			//**********************************************************//  

			strStepDescription = "Upload application with certificate identity  Apple iPhone OS Application Signing ";
			strExpectedResult = "App uploaded successfully";
			isEventSuccessful =   uploadApplication(appNameApple);

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
			// Step 4 - Select platform Android //
			//**********************************************************//   
			strStepDescription = "Select platform android";
			strExpectedResult = "Only android platform checkbox selected";
			isEventSuccessful=selectCheckboxes_DI(android, "chkPlatform_Devices");
			if(isEventSuccessful)
			{
				strActualResult="Only android platform checkbox selected";
			}
			else
			{
				strActualResult=""+strErrMsg_AppLib;
			}
			reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);

			//**********************************************************//
			// Step 4 - Click on first application link and verify user on application details page //
			//**********************************************************// 
			strStepDescription = "Click on first app link and app details page get displayed";
			strExpectedResult = "App details page should be displayed.";
			isEventSuccessful = SelectApplication("first");
			if (isEventSuccessful)
			{
				strActualResult = "App details page displayed";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

			//**********************************************************//
			// Step 5 - Verify Certificate Identity blank for android app //
			//**********************************************************//        
			strStepDescription = "Verify Certificate Identity blank for android app";
			strExpectedResult = "Certificate Identity should be blank for android app";
			isEventSuccessful = GetTextOrValue(dicOR.get("appAllInforAppDetails").replace("*", "8"),"text").length()<1;
			if (isEventSuccessful)
			{
				strActualResult = "Certificate Identity is blank for android app";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);


			//**********************************************************//
			// Step 8 - Verify Certificate Identity  Apple iPhone OS Application Signing
			//**********************************************************//      


			GoToApplicationsPage();
			selectPlatform_DI("iOS");
			searchDevice_DI("Dollar General");
			GoToFirstAppDetailsPage();
			strStepDescription = "Verify Certificate Identity should be  Apple iPhone OS Application Signing";
			strExpectedResult = "Certificate Identity should be  Apple iPhone OS Application Signing";
			//isEventSuccessful = GetTextOrValue(dicOR.get("appAllInforAppDetails").replace("*", "8"),"text").equals("Apple iPhone OS Application Signing");
			CertIdentity=getAttribute(dicOR.get("eleCertificateIdentity_AppDetailsPage"), "title");
			System.out.println("certificate identity is :"+CertIdentity);
			if (CertIdentity.equals("Apple iPhone OS Application Signing"))
			{
				strActualResult = "Correct Certificate identity shown for application : "+CertIdentity;
			}
			else
			{
				strActualResult =  "Correct Certificate identity not shown for application: "+CertIdentity;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);	



			//**********************************************************//
			// Step 8 - Verify Certificate Identity  Apple iPhone OS Application Signing
			//**********************************************************//      


			GoToApplicationsPage();
			selectPlatform_DI("iOS");
			searchDevice_DI("PhoneLookup");
			GoToFirstAppDetailsPage();
			strStepDescription = "Verify Certificate Identity should be  iPhone Distribution: Mobile Labs LLC";
			strExpectedResult = "Certificate Identity should be  iPhone Distribution: Mobile Labs LLC";
			//isEventSuccessful = GetTextOrValue(dicOR.get("appAllInforAppDetails").replace("*", "8"),"text").equals("Apple iPhone OS Application Signing");
			CertIdentity=getAttribute(dicOR.get("eleCertificateIdentity_AppDetailsPage"), "title");
			System.out.println("certificate identity is :"+CertIdentity);
			if (CertIdentity.equals("iPhone Distribution: Mobile Labs LLC"))
			{
				strActualResult = "Correct Certificate identity shown for application : "+CertIdentity;
			}
			else
			{
				strActualResult =  "Correct Certificate identity not shown for application: "+CertIdentity;
			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);	

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}



}
