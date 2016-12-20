package com.Reporting;

/*import java.io.BufferedWriter;
import java.io.FileInputStream;*/
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.ini4j.Reg;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.common.utilities.GenericLibrary;
import com.common.utilities.WinRegistry;
import com.common.utilities.xlsReader;
import com.sun.jna.platform.win32.WinReg;
import com.common.utilities.WinRegistry;

/*import Microsoft.VisualBasic.*;
import Microsoft.CSharp.*;*/

//using System.Windows.Forms;

//C# TO JAVA CONVERTER NOTE: There is no Java equivalent to C# namespace aliases:
//using Excel = Microsoft.Office.Interop.Excel;


public class APIReporter extends Reporter
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	public static String strCurrentFileName="";
	public static ArrayList<String> scriptsExecuted = new ArrayList<String>();
	
	public APIReporter()
	{
		
	}


	public final void apiReportStep(String sStepDescription, String sExpectedResult, String sActualResult, String sStepStatus)
	{
		String sSscreenshotFile = "";
		String sDesktopScreenshotFilename = "";
		try
		{
		if (sStepStatus.toLowerCase().contains("pass") || sStepStatus.toLowerCase().contains("true") || sStepStatus.toLowerCase().contains("done"))
		{
			dicReporting.put("StepStatus", "pass");
		}
		else if (sStepStatus.toLowerCase().contains("warning"))
		{
			dicReporting.put("StepStatus","warning");
		}
		else
		{
			dicReporting.put("StepStatus","fail");
		}
		//new addition
		if (!(new File(dicConfig.get("ReportPath") + "\\" + GenericLibrary.strCurrentTestSet + "\\")).isDirectory())
		{
			(new File(dicConfig.get("ReportPath") + "\\" + GenericLibrary.strCurrentTestSet + "\\")).mkdirs();

		}
		if (!dicCommon.containsKey("ScreenShot"))
		{
			dicCommon.put("ScreenShot", "No");
		}

		oAPIReportWriter.write("<tr>");

		oAPIReportWriter.write("<TD class='tdborder_1'  align='cent er'>" + ++iStepCount + "</TD>");
		oAPIReportWriter.write("<td class ='tdborder_1'>" + sStepDescription + "</td>");
		oAPIReportWriter.write("<td class ='tdborder_1'>" + sExpectedResult + "</td>");
		if (dicReporting.get("StepStatus").toLowerCase().equals("pass") || dicReporting.get("StepStatus").toLowerCase().equals("done"))
		{
			if (dicCommon.get("ScreenShot").toLowerCase().contains("yes"))
			{
				sSscreenshotFile = SaveScreenShot();
				oAPIReportWriter.write("<td class ='tdborder_1'>" + sActualResult + "<br><a href='" + sSscreenshotFile + "'>View Screenshot. </a></td>  ");
			}
			else
			{
				oAPIReportWriter.write("<td class ='tdborder_1'>" + sActualResult + "</td>");
			}
			oAPIReportWriter.write(" <td  class ='tdborder_1_Pass' width ='20%' align='center'>" + dicReporting.get("StepStatus").toUpperCase() + "</td>");
			iApiPassStepCount += 1;
		}
		else if (dicReporting.get("StepStatus").toLowerCase().equals("warning"))
		{
			if (dicCommon.get("ScreenShot").toLowerCase().contains("yes"))
			{
				SaveScreenShot();
			}
			oAPIReportWriter.write("<td class ='tdborder_1'>" + sActualResult + "</td>");
			oAPIReportWriter.write(" <td  class ='tdborder_1_Warning' width ='20%' align='center'>" + dicReporting.get("StepStatus").toUpperCase() + "</td>");
			iApiPassStepCount += 1;
		}
		else
		{
			sSscreenshotFile = SaveScreenShot();
			sDesktopScreenshotFilename = SaveDeskTopScreen();
			oAPIReportWriter.write("<td class ='tdborder_1'>" + sActualResult + "<br><a href='" + sSscreenshotFile + "'>View Screenshot. </a><br> OR <br><a href='" + sDesktopScreenshotFilename + "'>View desktop screenshot.</a></td>  ");
			oAPIReportWriter.write(" <td  class ='tdborder_1_Fail' width ='20%' align='center'>" + dicReporting.get("StepStatus").toUpperCase() + "</td>");
			iApiFailStepCount += 1;
		}
		oAPIReportWriter.write("</tr>");
		System.out.println("-- " + sStepDescription);
		}
		catch(Exception e)
		{
			
		}
	}


	public final void apiReportStep(String sStepDescription, String sExpectedResult, String sActualResult, boolean sStepStatus)
	{
		if (sStepStatus)
		{
			apiReportStep(sStepDescription, sExpectedResult, sActualResult, "pass");
		}
		else
		{
			apiReportStep(sStepDescription, sExpectedResult, sActualResult, "fail");
		}
	}


	public final void apiStartReportFileFormatting()
	{
		Calendar cal;
		//Deleting all reports from Last Run directory
		try
		{ 			
			if (!(new File(dicConfig.get("ReportPath") + "\\" +"APIOverallReportLastRun")).isDirectory())
			{
				(new File(dicConfig.get("ReportPath") + "\\" +"APIOverallReportLastRun")).mkdirs();

			}
			
			File LastRunFolder = new File(dicConfig.get("ReportPath") + "\\" +"APIOverallReportLastRun"); //** check
			
			
			for(File file : LastRunFolder.listFiles())
			{
				try{
					
					File destinationDir = new File(dicConfig.get("ReportPath") + "\\APIOverallReportMain");
					FileUtils.moveFileToDirectory(file, destinationDir,false );
				}
				
				catch(Exception e)
				{
					genericLibrary.writeToLog(e.getMessage() + e.getStackTrace() + "Could not copy files from Last Run to required reports folder.");
				}
				//file.delete();
			}
		
		cal = Calendar.getInstance();
		//Creating New report file
		String sReportFileName = dicConfig.get("ReportPath").toString() + "\\APIOverallReportLastRun\\API" + GenericLibrary.strCurrentTestCaseFormattedName + "_" + cal.get(Calendar.DAY_OF_MONTH) + "_" + cal.get(Calendar.MONTH) + "_" + cal.get(Calendar.YEAR) + "_" + cal.get(Calendar.HOUR_OF_DAY) + "H_" + cal.get(Calendar.MINUTE) + "M_" + cal.get(Calendar.SECOND) + ".html" ;
		strCurrentFileName=sReportFileName;
		oAPIReportWriter = new FileWriter(sReportFileName);
		oAPIReportWriter.write("<html>");
		oAPIReportWriter.write("<style>");

		oAPIReportWriter.write(".subheading { BORDER-RIGHT:#000000 1px solid;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-WEIGHT: bold;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid;COLOR: #000000;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri,helvetica, sans-serif;HEIGHT: 20px;BACKGROUND-COLOR: #FAC090;Color: #000000}");

		oAPIReportWriter.write(".subheading1{BORDER-RIGHT: #000000 1px solid;BACKGROUND-COLOR: #CCC0DA;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-WEIGHT: bold;FONT-SIZE: 13pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid;COLOR: #000000;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri,helvetica, sans-serif;HEIGHT: 10px;}");

		oAPIReportWriter.write(".subheading2{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 2px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 2px;FONT-WEIGHT: bold;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid;COLOR: #000000;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri,helvetica, sans-serif;HEIGHT: 10px;BACKGROUND-COLOR: #C2DC9A;Color: #000000}");
		
		oAPIReportWriter.write(".subheading3{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 2px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 2px;FONT-WEIGHT: bold;FONT-SIZE: 16pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid;COLOR: #000000;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri,helvetica, sans-serif;HEIGHT: 10px;BACKGROUND-COLOR: #FDB45C;Color: #000000}");
		
		oAPIReportWriter.write(".tdborder_1{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid;COLOR: #000000;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri, helvetica,  sans-serif;HEIGHT: 20px}");

		oAPIReportWriter.write(".tdborder_1_Pass{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid;COLOR: #00ff00;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri,  helvetica, sans-serif;HEIGHT: 20px}");

		oAPIReportWriter.write(".SnapShotLink_style{PADDING-RIGHT: 4px;PADDING-LEFT: 4px;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;COLOR: #0000EE;PADDING-TOP: 0px;FONT-FAMILY: Calibri, helvetica, sans-serif;HEIGHT: 20px}");

		oAPIReportWriter.write(".tdborder_1_Fail{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid; COLOR: #ff0000;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri, helvetica, sans-serif;HEIGHT: 20px}");

		oAPIReportWriter.write(".tdborder_1_Done{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid; COLOR: #ffcc00;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri,  helvetica, sans-serif;HEIGHT: 20px}");

		oAPIReportWriter.write(".tdborder_1_Skipped{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px  solid;COLOR: #00ccff;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri, helvetica, sans-serif;HEIGHT: 20px}");

		oAPIReportWriter.write(".tdborder_1_Warning{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid;COLOR: #660066;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri, helvetica, sans-serif;HEIGHT: 20px}");

		oAPIReportWriter.write(".heading {FONT-WEIGHT: bold; FONT-SIZE: 17px; COLOR: #005484;FONT-FAMILY: Calibri, Verdana, Tahoma, Calibri;}");

		oAPIReportWriter.write(".style1 { border: 1px solid #8eb3d8;padding: 0px 4px;FONT-WEIGHT: bold;FONT-SIZE: 12pt;COLOR: #000000;FONT-FAMILY: Calibri, helvetica, sans-serif;HEIGHT: 20px;width: 180px;}");

		oAPIReportWriter.write(".style3 { border: 1px solid #8eb3d8;padding: 0px 4px;FONT-WEIGHT: bold;FONT-SIZE: 12pt;COLOR: #000000;FONT-FAMILY: Calibri, helvetica, sans-serif;HEIGHT: 20px;width: 2px;}");

		oAPIReportWriter.write("</style>");


		oAPIReportWriter.write("<head><title>" + "APIOverall" + " Test Result</title></head>");

		oAPIReportWriter.write("<body>");

		oAPIReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%;" + " margin-left:20px;'><td class='subheading1' colspan=5 align=center><p style='font-size:1.8em'>" + "<body link='#00ff00'>" + "API Overall Comparison Report </body></td><tr></tr></table>");
		dicReporting.put("ReportPath", sReportFileName);
		}
		catch(Exception e)
		{
			writeToLog("apiStartReportFileFormatting()-- " + e.getStackTrace());
		}
	}
	
	public final void apiNewHeading(String headingText) {
		try{
			oAPIReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%;" + " margin-left:20px;'><td class='subheading1' colspan=5 align=center><p style='font-size:0.8em'>" + headingText + " </td><tr></tr></table>");
		}catch(Exception ex){
			writeToLog("apiNewHeading() ---" + ex.getStackTrace());
		}
		
	}
	
	public final void apiHeading2(String headingText) {
		try{
			oAPIReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%;" + " margin-left:20px;'><td class='subheading3' colspan=5 align=center><p style='font-size:0.8em'>" + headingText + " </td><tr></tr></table>");
		}catch(Exception ex){
			writeToLog("apiNewHeading() ---" + ex.getStackTrace());
		}
		
	}

	public final void apiScriptHeading(String headingText) {
		try{
			scriptsExecuted.add(headingText);
			System.out.println(scriptsExecuted);
			oAPIReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%;" + " margin-left:20px;'><td class='subheading1' colspan=5 align=center><p style='font-size:1.8em'>" + headingText + " </td><tr></tr></table>");
		}catch(Exception ex){
			writeToLog("apiNewHeading() ---" + ex.getStackTrace());
		}
		
	}
	
	public final void apiAddBlock(String headingText) {
		try{
			System.out.println(headingText);
			oAPIReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%;" + " margin-left:20px;'><td class='tdborder_1'  vAlign=center  align=middle bgcolor='#FFFFFF' ><p style='font-size:0.8em'>" + headingText + " </td><tr></tr></table>");
		}catch(Exception ex){
			writeToLog("apiNewHeading() ---" + ex.getStackTrace());
		}
		
	}
	
	public final void apiErrorBlock(String headingText) {
		try{	
			System.out.println(headingText);
			oAPIReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%;" + " margin-left:20px;'> <TD class='tdborder_1'  vAlign=center  align=middle bgcolor='#FF0000' ><p style='font-size:0.8em'> <b>" + headingText + "</b> </td><tr></tr></table>");
			iApiFailStepCount += 1;
		}catch(Exception ex){
			writeToLog("apiNewHeading() ---" + ex.getStackTrace());
		}
		
	}
	
//	#FFFF00
	public final void apiWarningBlock(String headingText) {
		try{	
			System.out.println(headingText);
			oAPIReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%;" + " margin-left:20px;'> <TD class='tdborder_1'  vAlign=center  align=middle bgcolor='#FFFF00' ><p style='font-size:0.8em'> <b>" + headingText + "</b> </td><tr></tr></table>");
		}catch(Exception ex){
			writeToLog("apiNewHeading() ---" + ex.getStackTrace());
		}
		
	}
	
	public final void apiPassBlock(String headingText) {
		try{
			System.out.println(headingText);
			oAPIReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%;" + " margin-left:20px;'> <TD class='tdborder_1'  vAlign=center  align=middle bgcolor='#66CD00' ><p style='font-size:0.8em'> <b>" + headingText + " </b></td><tr></tr></table>");
			iApiPassStepCount += 1;
		}catch(Exception ex){
			writeToLog("apiNewHeading() ---" + ex.getStackTrace());
		}
		
	}
	
	public final void apiFormatReportFile()
	{
		//25-06-2015 PM 2:03:25
		try
		{
		sStartDate = Calendar.getInstance(); // Change where it is inserted in the report footer

		oAPIReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%; margin-" + "left:20px;'>");

		oAPIReportWriter.write("<TR>" + " <TD class='subheading2' width ='10%' align='center' >Test Case Id</TD>" + " <TD class='subheading2' align='center' >Test Case Name</TD>" + " <TD class='subheading2' align='center'>Environment</TD>");

		oAPIReportWriter.write(" <TD class='subheading2'align='center'>Browser</TD>");

		oAPIReportWriter.write(" <TD class='subheading2' align='center'>Application URL</TD>" + " </TR>");

		oAPIReportWriter.write("<TR>" + " <TD class='tdborder_1'  vAlign=center  align=middle >" + GenericLibrary.strCurrentTestCaseID + "</TD>" + " <TD class='tdborder_1'  vAlign=center  align=middle >" + GenericLibrary.strCurrentTestCaseName + "</TD>" + " <TD class='tdborder_1'  vAlign=center  align=middle >" + dicCommon.get("Environment") + "</TD>");

		//oAPIReportWriter.write(" <TD class='tdborder_1'  vAlign=center  align=middle >" + dicCommon.get("BrowserName") + "<br> Version( " + GetBrowserVersion() + " )</TD>");

		oAPIReportWriter.write(" <TD class='tdborder_1'  vAlign=center  align=middle >" + dicCommon.get("ApplicationURL") + "</TD>" + " </TR>");

		oAPIReportWriter.write("</table>");

		oAPIReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%;" + " margin-left:20px;'>");

		oAPIReportWriter.write("<tr></tr>");

		oAPIReportWriter.write("<tr>" + " <td class='subheading2' width ='5%' align='center'>Steps</td>" + " <td class='subheading2'  width ='30%' align='center'>Description</td>" + " <td class='subheading2'  width ='30%' align='center'>Expected Result</td>" + " <td class='subheading2'  width ='30%' align='center'>Actual Result</td>" + " <td class='subheading2' width ='5%' align='center'>Step Status</td>" + " </tr>");
		}
		catch(Exception e)
		{
			writeToLog("apiFormatReportFile()-- " +e.getStackTrace()) ;
		}

	}


	public final void apiEndReportFileFormatting()
	{
		try
		{
		oAPIReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:20%; " + " margin-left:20px;'>");
		oAPIReportWriter.write("<TD class='subheading2' align='center'  width='40px'>List of Scripts Executed</TD>");
		 Iterator<String> itr=scriptsExecuted.iterator();//getting Iterator from arraylist to traverse elements  
		  while(itr.hasNext()){    
			oAPIReportWriter.write("<TR><TD class='tdborder_1'  align='center' width='40px'>" + itr.next() +"</TD></TR>");
		  }  
		 oAPIReportWriter.write("</table>");
		
		
		String strHour="0", strMin="0", strSec="0";
		int iHour=0, iMin=0, iSec=0;
		
		
		//if (DDiff.Minutes == 0)
			
		DDiff = (int)(Calendar.getInstance().getTime().getTime()- sStartDate.getTime().getTime());
	
		iSec = DDiff/1000; strSec = String.valueOf(iSec);
			//iSec = totalTime/1000; strSec = String.valueOf(iSec);	
		iMin = iSec/60;    strMin = String.valueOf(iMin);
		iHour = iMin/60;   strHour = String.valueOf(iHour); 
		
		if (iHour <= 9)
			strHour = "0" + strHour;
		if (iMin <= 9)
			strMin = "0" + strMin;
		if (iSec <= 9)
			strSec = "0" + strSec;


		String sTestDur = strHour + ":" + strMin + ":" + strSec; //DDiff.Hours.ToString("HH") + ":" + DDiff.Minutes.ToString("mm") + ":" + DDiff.Seconds.ToString("ss");
		

		oAPIReportWriter.write("</table>");

		oAPIReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%; " + " margin-left:20px;'>");

		oAPIReportWriter.write("<TR></TR>");

		oAPIReportWriter.write("<TR>");
		oAPIReportWriter.write("<TD class='subheading2' align='center'>Test Step-Pass</TD>");
		oAPIReportWriter.write("<TD class='subheading2' align='center'>Test Step-Fail</TD>");
		oAPIReportWriter.write("<TD class='subheading2' align='center'>Execution date & time</TD>");
		oAPIReportWriter.write("<TD class='subheading2' align='center'>Execution Machine name</TD>");
		oAPIReportWriter.write("<TD class='subheading2' align='center'>Browser</TD>");
		oAPIReportWriter.write("<TD class='subheading2' align='center'>Test run duration</TD>");
		oAPIReportWriter.write("<TD class='subheading2' align='center'>Iteration</TD>");

		oAPIReportWriter.write("</TR>");

		oAPIReportWriter.write("<TR>");
		oAPIReportWriter.write("<TD class='tdborder_1'  align='center' >" + iApiPassStepCount + "</TD>");
		oAPIReportWriter.write("<TD class='tdborder_1'  align='center'>" + iApiFailStepCount + "</TD>");
		oAPIReportWriter.write("<TD class='tdborder_1'  align='center'>" + sStartDate.getTime() + "</TD>");
		oAPIReportWriter.write("<TD class='tdborder_1'  align='center'>" + InetAddress.getLocalHost().getHostName() + "</TD>");
		oAPIReportWriter.write("<TD class='tdborder_1'  align='center'>" + dicCommon.get("BrowserName") + "<br> Version( " + GetBrowserVersion()  + "</TD>");
		oAPIReportWriter.write("<TD class='tdborder_1' align='center' >" + sTestDur + "</TD>");
		oAPIReportWriter.write("<TD class='tdborder_1' align='center' >" + GenericLibrary.iScriptCount + "</TD>");
		oAPIReportWriter.write("</TR>");
		oAPIReportWriter.write("<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>");

		oAPIReportWriter.write("</table>");
		//Finishing Report
		oAPIReportWriter.write("</table></body></html>");
		oAPIReportWriter.flush();
		oAPIReportWriter.close();
		}
		catch(Exception e)
		{
			writeToLog("apiEndReportFileFormatting()-- " + e.getStackTrace());
		}
		finally
		{
			try {
				//oAPIReportWriter.flush();
				oAPIReportWriter.close();
			} catch (IOException e) {
				writeToLog("apiEndReportFileFormatting()-- Error Closing the report writer object-- " + e.getStackTrace());
			}
			
		}
	}

	//TODO OWN : Add background color of cells and also add hyperlinks in the 'html report link cell'
	public final void apiUpdateTestSuiteWithExecutionStatus()
	{
		String htmlreportLink = "";
		int rowsCount = 0;
		String TestCaseName = "";
		//KillObjectInstances("Excel");
		// Changing Test Suite Status
		xlsReader reader = new xlsReader(dicConfig.get("TestSuitePath") + "\\" + dicCommon.get("TestSuiteName") + ".xlsx");
		rowsCount = reader.getRowCount(strCurrentTestSet);
		
		// Loop through all the rows of current sheet
		for(int rowNum=2; rowNum<=rowsCount; rowNum++)
		{
			TestCaseName = reader.getCellData(strCurrentTestSet, "TestCaseName", rowNum);
			if(TestCaseName.equals(strCurrentTestCaseName))
			{
				if(iApiFailStepCount == 0 && iApiPassStepCount > 0)
					reader.setCellData(strCurrentTestSet, "Status", rowNum, "Pass");
				else
					reader.setCellData(strCurrentTestSet, "Status", rowNum, "Fail");
				
				// Code for generating Html Link In TestSuite
				htmlreportLink = dicReporting.get("ReportPath") ;
				htmlreportLink = htmlreportLink.replace("LastRun", strCurrentTestSet + "\\" + strCurrentTestCaseID);
				reader.setCellData(strCurrentTestSet, "Html Reports Link", rowNum, htmlreportLink);
//				reader.setCellData(strCurrentTestSet, "Html Reports Link", rowNum, htmlreportLink, htmlreportLink); //TODO : Mandeep : Check why this is corrupting the file.
				break;
			}
		}
		
		/*Microsoft.Office.Interop.Excel.Workbook myBook;
		Microsoft.Office.Interop.Excel.Worksheet mySheet;
		myExcel = new Microsoft.Office.Interop.Excel.Application();
		myExcel.DisplayAlerts = false;
		myBook = myExcel.Workbooks.Open(dicConfig.get("TestSuitePath") + dicCommon.get("TestSuiteName"), java.lang.Class.Missing, java.lang.Class.Missing, java.lang.Class.Missing, java.lang.Class.Missing, java.lang.Class.Missing, java.lang.Class.Missing, java.lang.Class.Missing, java.lang.Class.Missing, java.lang.Class.Missing, java.lang.Class.Missing, java.lang.Class.Missing, java.lang.Class.Missing, java.lang.Class.Missing, java.lang.Class.Missing);
		mySheet = (Microsoft.Office.Interop.Excel.Worksheet)myBook.Worksheets.get_Item(strCurrentTestSet);
		//Looping through each row to get item name
		Microsoft.Office.Interop.Excel.Range usedRange = mySheet.UsedRange;
		int nRows = usedRange.Rows.size();
		Object[][] values = (Object[][])usedRange.Value2;
		for (int i = 1; i <= nRows; i++)
		{
			String value = String.valueOf(values[i][2]);
			if (value.equals(GenericLibrary.strCurrentTestCaseName))
			{
				if (iApiFailStepCount == 0 && iApiPassStepCount > 0)
				{
					mySheet.Cells[i][4] = "Pass";
					System.out.println("Pass");
					Microsoft.Office.Interop.Excel.Range chartRange = (Microsoft.Office.Interop.Excel.Range)usedRange.Cells[i][4];
					chartRange.Interior.Color = System.Drawing.ColorTranslator.ToOle(System.Drawing.Color.Green);
				}
				else
				{
					mySheet.Cells[i][4] = "Fail";
					System.out.println("Fail");
					Microsoft.Office.Interop.Excel.Range chartRange = (Microsoft.Office.Interop.Excel.Range)usedRange.Cells[i][4];
					chartRange.Interior.Color = System.Drawing.ColorTranslator.ToOle(System.Drawing.Color.Red);

				}
				// Code for generating Html Link In TestSuite
				htmlreportLink = GenericLibrary.dicReporting.get("ReportPath");
				htmlreportLink = htmlreportLink.replace("LastRun", GenericLibrary.strCurrentTestSet + "//" + GenericLibrary.strCurrentTestCaseID);
				mySheet.Cells[i][5] = htmlreportLink;
				mySheet.Hyperlinks.Add(mySheet.Cells[i][5], htmlreportLink);
				break;
			}
		}
		myBook.Save();
		myBook.Close(0, 0, 0);
		KillObjectInstances("Excel");
		Microsoft.Win32.RegistryKey key;
		key = Microsoft.Win32.Registry.CurrentUser.CreateSubKey("strStatus");
		key.SetValue("strStatus", iApiFailStepCount);
		key.Close();*/
	}

	/**
	 * Copies files from Last run folder and pastes them to the reports folder 
	 * @author mandeepm
	 * @since 26/8/2015
	 */
	public final void apiCopyReportFileToArchive()
	{
		try
		{
			File sourceDir = new File(dicConfig.get("ReportPath") + "\\APIOverallReportLastRun");
			File destinationDir = new File(dicConfig.get("ReportPath") + "\\APIOverallReportMain");
			genericLibrary.MoveFilesToAnotherDirectory(sourceDir, destinationDir, new String[]{"html","png", "bmp"}, true);
		}
		
		catch(Exception e)
		{
			genericLibrary.writeToLog(e.getMessage() + e.getStackTrace() + "Could not copy files from Last Run to required reports folder.");
		}
	}
	
	public final void CreateAppModelTableInReport(String[] appModel, Map<String,String> expectedResultMap,Map<String,String> actualResultMap,Map<String,String> errorMap)
	{
		
		try
		{
		//new addition
			if (!(new File(dicConfig.get("ReportPath") + "\\" +"APIOverallReportLastRun")).isDirectory())
			{
				(new File(dicConfig.get("ReportPath") + "\\" +"APIOverallReportLastRun")).mkdirs();

			}
			
		//h4[text()='Provisioning Errors']/p
		oAPIReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center'  style='width:30%; margin-" + "left:20px;'>");
		
		int itemCounter=0;
		String headerBlock="<TR>" + "<TD class='subheading2' align='center'> </TD>";
		String expectedBlock="<TR>" + "<TD class='subheading2' align='center'> Expected values (Web) </TD>";
		String actualBlock="<TR>" + "<TD class='subheading2' align='center'> Actual values (API) </TD>";
		String style;
		for (String prop: appModel)
		{
			style="";
			if (expectedResultMap.get(prop) != null)
			{
				if (expectedResultMap.get(prop).equalsIgnoreCase("Not Verified")) {
					style="bgcolor='#FDB45C'"; //#FFFF00 - yellow color
			}
		}
			if (errorMap.containsKey(prop))
			{
				style="bgcolor='#FF0000'"; //#FF0000 -red color  #BF384F
				iApiFailStepCount++; //if handled in scripts can comment it
			}
			headerBlock+= " <TD class='subheading2' width ='10%' align='center'  >"+prop+"</TD>";
			expectedBlock+= "<TD class='tdborder_1'  vAlign=center  align=middle "+style+" >"+expectedResultMap.get(prop)+" </TD>";
			actualBlock+= "<TD class='tdborder_1'  vAlign=center  align=middle "+style+" >"+actualResultMap.get(prop)+" </TD>";
			itemCounter++;
			if ((itemCounter%4 ==0) || (itemCounter==appModel.length))
			{
				headerBlock+= " </TR>";
				expectedBlock+= " </TR>";
				actualBlock+= " </TR>";
				oAPIReportWriter.write(headerBlock);
				oAPIReportWriter.write(expectedBlock);
				oAPIReportWriter.write(actualBlock);
				
				headerBlock="<TR>" + "<TD class='subheading2' align='center'> "+style+"> </TD>";
				expectedBlock="<TR>" + "<TD class='subheading2' align='center' "+style+"> Expected values (Web) </TD>";
				actualBlock="<TR>" + "<TD class='subheading2' align='center' "+style+"> Actual values (API) </TD>";
			}
			
		}
		
		oAPIReportWriter.write("</table>");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void apiOverallHtmlLinkStep()
	{
		
		reporter.ReportStep("Added Entry in API Overall Comparison file", "Entry added successfully", "Refer <a href=\"" +strCurrentFileName+"\"" +"> API Overall Comparison file </a>", true);
	}
	
}