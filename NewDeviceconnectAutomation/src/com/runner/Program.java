package com.runner;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.ApplicationLibrary;
import com.common.utilities.GenericLibrary;
//import Microsoft.VisualBasic.*;

public class Program
{
	public static void main(String[] args) throws InterruptedException
	{
		//""("Starting");
		//Thread.sleep(10800000);
		GenericLibrary genericLibrary = new GenericLibrary();
		ApplicationLibrary applicationLibrary=new ApplicationLibrary();
		Reporter reporter = new Reporter();
		APIReporter apiReporter = new APIReporter(); // added for API scripts- jaishree
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		boolean apiReportStart=false;
		int  temp = 0;
		
		try
		{
			//Get framework directories from 'App.config' file
			genericLibrary.GetAppConfigData(args.length);

			//Extracting the CLI contents and saving to the 'Artifacts' folder
			//RunConfigurations.ConfigureRunSettings();
			
			//Deleting log file
			if ((new File(GenericLibrary.dicConfig.get("LogsPath") + "/log.txt")).isFile())
				(new File(GenericLibrary.dicConfig.get("LogsPath") + "/log.txt")).delete();

			//Reading Common Data from 'Common' sheet in 'Datasheet.xls' and writing to dicCommon
			genericLibrary.GetItemValuesFromExcel(GenericLibrary.dicConfig.get("DatasheetPath"), "TestData.xlsx", "Common", GenericLibrary.dicCommon);

			//If test is running through ALM, Configure execution constraints
			//Arguments sequence: Browsername ParentFolderName TestCaseID "TestCaseName"
			if (args.length >= 4)
			{
				//replacing browsername from testsuite to browsername passed from ALM arguments in dicCommon["TestSuiteName"]
				GenericLibrary.dicCommon.put("TestSuiteName", GenericLibrary.dicCommon.get("TestSuiteName").replace("_" + GenericLibrary.dicCommon.get("BrowserName"), "_" + args[0]));
				 genericLibrary.ConfigureALMExecutionContraints(args);
			}

			//Reading Test Suite
			genericLibrary.ReadTestSuite();

			if (GenericLibrary.MapTestSuite.size() > 0)
				//Reading OR
				genericLibrary.GetItemValuesFromExcel(GenericLibrary.dicConfig.get("ObjectRepositoryPath"), GenericLibrary.dicCommon.get("ObjectRepositoryName") + ".xlsx", "OR", GenericLibrary.dicOR);
			else
				return;
			
			
			//Iterating till all the test cases has been executed
			for(Map.Entry<String, String> entry : GenericLibrary.MapTestSuite.entrySet())
			{
				GenericLibrary.strCurrentTestCaseID = entry.getKey().split("_")[0];
				GenericLibrary.strCurrentTestCaseName = entry.getKey().replace(GenericLibrary.strCurrentTestCaseID + "_", "");
				/*String name = GenericLibrary.strCurrentTestCaseName = entry.getKey().split("_")[1];*/
				GenericLibrary.strCurrentTestCaseFormattedName = genericLibrary.ReplaceSpecialCharacterAndSpaces(GenericLibrary.strCurrentTestCaseName, "_");
				GenericLibrary.strCurrentTestSet = entry.getValue();
				
				//Displaying script number on console:
				System.out.println(GenericLibrary.strCurrentTestCaseID);
				if (Integer.parseInt(GenericLibrary.strCurrentTestCaseID) >1000 && apiReportStart== false)
				{
					apiReporter.apiStartReportFileFormatting();
					apiReporter.apiFormatReportFile();
					apiReportStart=true;
				}
					
				
				genericLibrary.sStartDate = Calendar.getInstance();
				
				//Getting test case specific data
				genericLibrary.ReadTestData();
				//Report file formatting
				reporter.StartReportFileFormatting();
				//Adding header to the HTML the start of report
				reporter.FormatReportFile();
				//Calling script
				
				
				/*//if (GenericLibrary.strCurrentTestCaseName.contains("API"))  //Added for API Script- jaishree
				if (Integer.parseInt(GenericLibrary.strCurrentTestCaseID) >1000)
					apiReporter.apiFormatReportFile();*/
				
				genericLibrary.CallTestScript();
				//Inserting Execution Details to Report File
				reporter.EndReportFileFormatting();
				//Shutting down Selenium Server/driver. Killing processes for browser and workbook
				if(GenericLibrary.dicCommon.get("BrowserName").toLowerCase().equals("ie"))
				{
					applicationLibrary.LogoutIE();
				}

				genericLibrary.TeardownTest();
				//Script execution time
				String strHour="0", strMin="0", strSec="0";
				int iHour=0, iMin=0, iSec=0;
		       	int	DDiff = (int)(Calendar.getInstance().getTime().getTime()- genericLibrary.sStartDate.getTime().getTime());
		       	iSec = DDiff/1000; strSec = String.valueOf(iSec);
				iMin = iSec/60;    strMin = String.valueOf(iMin);
				iHour = iMin/60;   strHour = String.valueOf(iHour); 
				
				if (iHour <= 9)
					strHour = "0" + strHour;
				if (iMin <= 9)
					strMin = "0" + strMin;
				if (iSec <= 9)
					strSec = "0" + strSec;
				String sTestDur = strHour + ":" + strMin + ":" + strSec;
				System.out.println(sTestDur);
				//Copying Report File from Last Run to respective directory in Reports/<TestSet name> folder
				reporter.CopyReportFileToArchive();
				//Update Test Suite with Execution Status
				reporter.UpdateTestSuiteWithExecutionStatus();
				//If script is running through ALM save script status in environment variable
				if (GenericLibrary.dicConfig.get("isRunningThroughALM").equals("true"))
				{
					String strScriptStatus = GenericLibrary.iPassStepCount != 0 && GenericLibrary.iFailStepCount == 0 ? "0" : "1";
					//***** Commenting for now as there is no 
					//System.Environment.SetEnvironmentVariable("ScriptExecutionStatus", strScriptStatus, System.EnvironmentVariableTarget.User);
				}
				//Clearing dictionary
				GenericLibrary.dicTestData.clear();
				//Re-setting values to base state for handling multiple iterations
				GenericLibrary.iStepCount = 0;
				GenericLibrary.iPassStepCount = 0;
				GenericLibrary.iFailStepCount = 0;
				//Increasing script count value
				GenericLibrary.iScriptCount += 1;

			}
			
			//Added for API Script- jaishree
			if (apiReportStart == true)
				apiReporter.apiEndReportFileFormatting();
			
			GenericLibrary.dicOutput.clear();
			GenericLibrary.dicDict.clear();
		}
		catch (RuntimeException e)
		{
			genericLibrary.writeToLog(e.getMessage() + "\n" + e.getStackTrace());
			try
			{
				GenericLibrary.dicReporting.put("StepStatus", "fail");
				if (GenericLibrary.dicDict.get("ReportMsgFail").trim().length() == 0)
				{
					reporter.ReportStep("Error occurred", "", e.getMessage(), "fail");
				}
				System.out.println(e.getMessage());
				reporter.EndReportFileFormatting();
				reporter.CopyReportFileToArchive();
				genericLibrary.TeardownTest();
				
			}
			catch (RuntimeException exc)
			{
				System.out.println(exc.getMessage());
			}
		}
		try
		{
			GenericLibrary.oReportWriter.close();
			
		}
		catch (RuntimeException | IOException excp)
		{
			System.out.println(excp.getMessage());
		}
	}
}