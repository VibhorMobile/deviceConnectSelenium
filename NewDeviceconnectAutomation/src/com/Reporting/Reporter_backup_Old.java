package com.Reporting;

/*import java.io.BufferedWriter;
import java.io.FileInputStream;*/
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
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


public class Reporter_backup_Old extends GenericLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();


	public final void ReportStep(String sStepDescription, String sExpectedResult, String sActualResult, String sStepStatus)
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
		if (!(new File(dicConfig.get("ReportPath") + "/" + GenericLibrary.strCurrentTestSet + "\\")).isDirectory())
		{
			(new File(dicConfig.get("ReportPath") + "/" + GenericLibrary.strCurrentTestSet + "\\")).mkdirs();

		}
		if (!dicCommon.containsKey("ScreenShot"))
		{
			dicCommon.put("ScreenShot", "No");
		}

		oReportWriter.write("<tr>");

		oReportWriter.write("<TD class='tdborder_1'  align='center'>" + ++iStepCount + "</TD>");
		oReportWriter.write("<td class ='tdborder_1'>" + sStepDescription + "</td>");
		oReportWriter.write("<td class ='tdborder_1'>" + sExpectedResult + "</td>");
		if (dicReporting.get("StepStatus").toLowerCase().equals("pass") || dicReporting.get("StepStatus").toLowerCase().equals("done"))
		{
			if (dicCommon.get("ScreenShot").toLowerCase().contains("yes"))
			{
				sSscreenshotFile = SaveScreenShot();
				oReportWriter.write("<td class ='tdborder_1'>" + sActualResult + "<br><a href='" + sSscreenshotFile + "'>View Screenshot. </a></td>  ");
			}
			else
			{
				oReportWriter.write("<td class ='tdborder_1'>" + sActualResult + "</td>");
			}
			oReportWriter.write(" <td  class ='tdborder_1_Pass' width ='20%' align='center'>" + dicReporting.get("StepStatus").toUpperCase() + "</td>");
			iPassStepCount += 1;
		}
		else if (dicReporting.get("StepStatus").toLowerCase().equals("warning"))
		{
			if (dicCommon.get("ScreenShot").toLowerCase().contains("yes"))
			{
				SaveScreenShot();
			}
			oReportWriter.write("<td class ='tdborder_1'>" + sActualResult + "</td>");
			oReportWriter.write(" <td  class ='tdborder_1_Warning' width ='20%' align='center'>" + dicReporting.get("StepStatus").toUpperCase() + "</td>");
			iPassStepCount += 1;
		}
		else
		{
			sSscreenshotFile = SaveScreenShot();
			sDesktopScreenshotFilename = SaveDeskTopScreen();
			oReportWriter.write("<td class ='tdborder_1'>" + sActualResult + "<br><a href='" + sSscreenshotFile + "'>View Screenshot. </a><br> OR <br><a href='" + sDesktopScreenshotFilename + "'>View desktop screenshot.</a></td>  ");
			oReportWriter.write(" <td  class ='tdborder_1_Fail' width ='20%' align='center'>" + dicReporting.get("StepStatus").toUpperCase() + "</td>");
			iFailStepCount += 1;
		}
		oReportWriter.write("</tr>");
		System.out.println("-- " + sStepDescription);
		}
		catch(Exception e)
		{
			
		}
	}


	public final void ReportStep(String sStepDescription, String sExpectedResult, String sActualResult, boolean sStepStatus)
	{
		if (sStepStatus)
		{
			ReportStep(sStepDescription, sExpectedResult, sActualResult, "pass");
		}
		else
		{
			ReportStep(sStepDescription, sExpectedResult, sActualResult, "fail");
		}
	}


	public final void StartReportFileFormatting()
	{
		Calendar cal;
		//Deleting all reports from Last Run directory
		try
		{ //TODO Tarun Ahuja : below is the code to handle if the LastRun is not present
			//function refrence "ReportStep"
			
			if (!(new File(dicConfig.get("ReportPath") + "/" +"LastRun")).isDirectory())
			{
				(new File(dicConfig.get("ReportPath") + "/" +"LastRun")).mkdirs();

			}
			
			File LastRunFolder = new File(dicConfig.get("ReportPath") + "/" +"LastRun"); //** check
			
			for(File file : LastRunFolder.listFiles())
			{
				file.delete();
			}
		
		cal = Calendar.getInstance();
		//Creating New report file
		String sReportFileName = dicConfig.get("ReportPath").toString() + "\\LastRun\\" + GenericLibrary.strCurrentTestCaseFormattedName + "_" + cal.get(Calendar.DAY_OF_MONTH) + "_" + cal.get(Calendar.MONTH) + "_" + cal.get(Calendar.YEAR) + "_" + cal.get(Calendar.HOUR_OF_DAY) + "H_" + cal.get(Calendar.MINUTE) + "M_" + cal.get(Calendar.SECOND) + "S.html";
		oReportWriter = new FileWriter(sReportFileName);
		oReportWriter.write("<html>");
		oReportWriter.write("<style>");

		oReportWriter.write(".subheading { BORDER-RIGHT:#000000 1px solid;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-WEIGHT: bold;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid;COLOR: #000000;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri,helvetica, sans-serif;HEIGHT: 20px;BACKGROUND-COLOR: #FAC090;Color: #000000}");

		oReportWriter.write(".subheading1{BORDER-RIGHT: #000000 1px solid;BACKGROUND-COLOR: #CCC0DA;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-WEIGHT: bold;FONT-SIZE: 13pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid;COLOR: #000000;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri,helvetica, sans-serif;HEIGHT: 10px;}");

		oReportWriter.write(".subheading2{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 2px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 2px;FONT-WEIGHT: bold;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid;COLOR: #000000;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri,helvetica, sans-serif;HEIGHT: 10px;BACKGROUND-COLOR: #C2DC9A;Color: #000000}");

		oReportWriter.write(".tdborder_1{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid;COLOR: #000000;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri, helvetica,  sans-serif;HEIGHT: 20px}");

		oReportWriter.write(".tdborder_1_Pass{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid;COLOR: #00ff00;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri,  helvetica, sans-serif;HEIGHT: 20px}");

		oReportWriter.write(".SnapShotLink_style{PADDING-RIGHT: 4px;PADDING-LEFT: 4px;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;COLOR: #0000EE;PADDING-TOP: 0px;FONT-FAMILY: Calibri, helvetica, sans-serif;HEIGHT: 20px}");

		oReportWriter.write(".tdborder_1_Fail{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid; COLOR: #ff0000;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri, helvetica, sans-serif;HEIGHT: 20px}");

		oReportWriter.write(".tdborder_1_Done{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid; COLOR: #ffcc00;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri,  helvetica, sans-serif;HEIGHT: 20px}");

		oReportWriter.write(".tdborder_1_Skipped{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px  solid;COLOR: #00ccff;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri, helvetica, sans-serif;HEIGHT: 20px}");

		oReportWriter.write(".tdborder_1_Warning{BORDER-RIGHT: #000000 1px solid;PADDING-RIGHT: 4px;BORDER-TOP: #000000 1px solid;PADDING-LEFT: 4px;FONT-SIZE: 12pt;PADDING-BOTTOM: 0px;BORDER-LEFT: #000000 1px solid;COLOR: #660066;PADDING-TOP: 0px;BORDER-BOTTOM: #000000 1px solid;FONT-FAMILY: Calibri, helvetica, sans-serif;HEIGHT: 20px}");

		oReportWriter.write(".heading {FONT-WEIGHT: bold; FONT-SIZE: 17px; COLOR: #005484;FONT-FAMILY: Calibri, Verdana, Tahoma, Calibri;}");

		oReportWriter.write(".style1 { border: 1px solid #8eb3d8;padding: 0px 4px;FONT-WEIGHT: bold;FONT-SIZE: 12pt;COLOR: #000000;FONT-FAMILY: Calibri, helvetica, sans-serif;HEIGHT: 20px;width: 180px;}");

		oReportWriter.write(".style3 { border: 1px solid #8eb3d8;padding: 0px 4px;FONT-WEIGHT: bold;FONT-SIZE: 12pt;COLOR: #000000;FONT-FAMILY: Calibri, helvetica, sans-serif;HEIGHT: 20px;width: 2px;}");

		oReportWriter.write("</style>");


		oReportWriter.write("<head><title>" + dicCommon.get("ProjectName") + " Test Result</title></head>");

		oReportWriter.write("<body>");

		oReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%;" + " margin-left:20px;'><td class='subheading1' colspan=5 align=center><p style='font-size:1.8em'>" + "<body link='#00ff00'>" + dicCommon.get("ProjectName") + " Execution Report </body></td><tr></tr></table>");
		dicReporting.put("ReportPath", sReportFileName);
		}
		catch(Exception e)
		{
			writeToLog("StartReportFileFormatting()-- " + e.getStackTrace());
		}
	}


	public final void FormatReportFile()
	{
		//25-06-2015 PM 2:03:25
		try
		{
		sStartDate = Calendar.getInstance(); // Change where it is inserted in the report footer
		iPassStepCount = 0;
		iFailStepCount = 0;
		oReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%; margin-" + "left:20px;'>");

		oReportWriter.write("<TR>" + " <TD class='subheading2' width ='10%' align='center' >Test Case Id</TD>" + " <TD class='subheading2' align='center' >Test Case Name</TD>" + " <TD class='subheading2' align='center'>Environment</TD>");

		oReportWriter.write(" <TD class='subheading2'align='center'>Browser</TD>");

		oReportWriter.write(" <TD class='subheading2' align='center'>Application URL</TD>" + " </TR>");

		oReportWriter.write("<TR>" + " <TD class='tdborder_1'  vAlign=center  align=middle >" + GenericLibrary.strCurrentTestCaseID + "</TD>" + " <TD class='tdborder_1'  vAlign=center  align=middle >" + GenericLibrary.strCurrentTestCaseName + "</TD>" + " <TD class='tdborder_1'  vAlign=center  align=middle >" + dicCommon.get("Environment") + "</TD>");

		oReportWriter.write(" <TD class='tdborder_1'  vAlign=center  align=middle >" + dicCommon.get("BrowserName") + "<br> Version( " + GetBrowserVersion() + " )</TD>");

		oReportWriter.write(" <TD class='tdborder_1'  vAlign=center  align=middle >" + dicCommon.get("ApplicationURL") + "</TD>" + " </TR>");

		oReportWriter.write("</table>");

		oReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%;" + " margin-left:20px;'>");

		oReportWriter.write("<tr></tr>");

		oReportWriter.write("<tr>" + " <td class='subheading2' width ='5%' align='center'>Steps</td>" + " <td class='subheading2'  width ='30%' align='center'>Description</td>" + " <td class='subheading2'  width ='30%' align='center'>Expected Result</td>" + " <td class='subheading2'  width ='30%' align='center'>Actual Result</td>" + " <td class='subheading2' width ='5%' align='center'>Step Status</td>" + " </tr>");
		}
		catch(Exception e)
		{
			writeToLog("FormatReportFile()-- " +e.getStackTrace()) ;
		}

	}


	public final void EndReportFileFormatting()
	{
		String strHour="0", strMin="0", strSec="0";
		int iHour=0, iMin=0, iSec=0;
		
		try
		{
		//if (DDiff.Minutes == 0)
		DDiff = (int)(Calendar.getInstance().getTime().getTime()- sStartDate.getTime().getTime());
		iSec = DDiff/1000; strSec = String.valueOf(iSec);
		iMin = iSec/60;    strMin = String.valueOf(iMin);
		iHour = iMin/60;   strHour = String.valueOf(iHour); 
		
		if (iHour <= 9)
			strHour = "0" + strHour;
		if (iMin <= 9)
			strMin = "0" + strMin;
		if (iSec <= 9)
			strSec = "0" + strSec;


		String sTestDur = strHour + ":" + strMin + ":" + strSec; //DDiff.Hours.ToString("HH") + ":" + DDiff.Minutes.ToString("mm") + ":" + DDiff.Seconds.ToString("ss");

		oReportWriter.write("</table>");

		oReportWriter.write("<table cellSpacing='0' cellPadding='0' border='0' align='center' style='width:96%; " + " margin-left:20px;'>");

		oReportWriter.write("<TR></TR>");

		oReportWriter.write("<TR>");
		oReportWriter.write("<TD class='subheading2' align='center'>Test Step-Pass</TD>");
		oReportWriter.write("<TD class='subheading2' align='center'>Test Step-Fail</TD>");
		oReportWriter.write("<TD class='subheading2' align='center'>Execution date & time</TD>");
		oReportWriter.write("<TD class='subheading2' align='center'>Execution Machine name</TD>");
		oReportWriter.write("<TD class='subheading2' align='center'>Test run duration</TD>");
		oReportWriter.write("<TD class='subheading2' align='center'>Iteration</TD>");

		oReportWriter.write("</TR>");

		oReportWriter.write("<TR>");
		oReportWriter.write("<TD class='tdborder_1'  align='center' >" + iPassStepCount + "</TD>");
		oReportWriter.write("<TD class='tdborder_1'  align='center'>" + iFailStepCount + "</TD>");
		oReportWriter.write("<TD class='tdborder_1'  align='center'>" + sStartDate.getTime() + "</TD>");
		oReportWriter.write("<TD class='tdborder_1'  align='center'>" + InetAddress.getLocalHost().getHostName() + "</TD>");

		oReportWriter.write("<TD class='tdborder_1' align='center' >" + sTestDur + "</TD>");
		oReportWriter.write("<TD class='tdborder_1' align='center' >" + GenericLibrary.iScriptCount + "</TD>");
		oReportWriter.write("</TR>");
		oReportWriter.write("<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>");

		oReportWriter.write("</table>");
		//Finishing Report
		oReportWriter.write("</table></body></html>");
		oReportWriter.flush();
		oReportWriter.close();
		}
		catch(Exception e)
		{
			writeToLog("EndReportFileFormatting()-- " + e.getStackTrace());
		}
		finally
		{
			try {
				//oReportWriter.flush();
				oReportWriter.close();
			} catch (IOException e) {
				writeToLog("EndReportFileFormatting()-- Error Closing the report writer object-- " + e.getStackTrace());
			}
			
		}
	}

	//TODO OWN : Add background color of cells and also add hyperlinks in the 'html repot link cell'
	public final void UpdateTestSuiteWithExecutionStatus()
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
				if(iFailStepCount == 0 && iPassStepCount > 0)
					reader.setCellData(strCurrentTestSet, "Status", rowNum, "Pass");
				else
					reader.setCellData(strCurrentTestSet, "Status", rowNum, "Fail");
				
				// Code for generating Html Link In TestSuite
				htmlreportLink = dicReporting.get("ReportPath") + ".html";
				htmlreportLink = htmlreportLink.replace("LastRun", strCurrentTestSet + "//" + strCurrentTestCaseID);
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
				if (iFailStepCount == 0 && iPassStepCount > 0)
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
		key.SetValue("strStatus", iFailStepCount);
		key.Close();*/
	}


	/**
	* Copies files from Last run folder and pastes them to the reports folder 
	* @author mandeepm
	* @since 26/8/2015
	*/
	public final void CopyReportFileToArchive()
	{
	try
	{
	File sourceDir = new File(dicConfig.get("ReportPath") + "\\LastRun");
	File destinationDir = new File(dicConfig.get("ReportPath") + "\\" +GenericLibrary.strCurrentTestSet + "\\" + GenericLibrary.strCurrentTestCaseID);
	genericLibrary.MoveFilesToAnotherDirectory(sourceDir, destinationDir, new String[]{"html","png", "bmp"}, true);
	}

	catch(Exception e)
	{
	genericLibrary.writeToLog(e.getMessage() + e.getStackTrace() + "Could not copy files from Last Run to required reports folder.");
	}
	}
	

	//TODO OWN : Still need to implement as the correct path of all browser versions could not be found in the registry.
	public final String GetBrowserVersion()
	{
		String strBrowserVersion = "";
		/*try
		{
			switch (dicCommon.get("BrowserName").toLowerCase())
			{
				
				case "firefox":
					String regKey1 = WinRegistry.readString(WinRegistry.HKEY_LOCAL_MACHINE, "SOFTWARE\\Mozilla\\Mozilla Firefox", "CurrentVersion");
					String regKey = (((new Reg("HKEY_LOCAL_MACHINE\\SOFTWARE")).get("Citrix")).getChild("COLVideo")).get("UniqueEndpointId");
					strBrowserVersion = Microsoft.Win32.Registry.LocalMachine.OpenSubKey("Software\\Mozilla\\Mozilla Firefox").GetValue("CurrentVersion").toString();
					break;
				case "ie":
					strBrowserVersion = Microsoft.Win32.Registry.LocalMachine.OpenSubKey("Software\\Microsoft\\Internet Explorer").GetValue("Version").toString();
					break;
				case "chrome":
					strBrowserVersion = Microsoft.Win32.Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\Google Chrome").GetValue("Version").toString();
					break;
				case "safari":
					strBrowserVersion = Microsoft.Win32.Registry.LocalMachine.OpenSubKey("Software\\Apple Computer, Inc.\\Safari").GetValue("Version").toString();
					break;
				case "opera":
					strBrowserVersion = "";
					break;
			}
		}
		catch (RuntimeException e)
		{
			//For 64 bit systems
			try
			{
				switch (dicCommon.get("BrowserName").toLowerCase())
				{

					case "firefox":
						strBrowserVersion = Microsoft.Win32.Registry.LocalMachine.OpenSubKey("Software\\Wow6432Node\\Mozilla\\Mozilla Firefox").GetValue("CurrentVersion").toString();
						break;
					case "ie":
						strBrowserVersion = Microsoft.Win32.Registry.LocalMachine.OpenSubKey("Software\\Microsoft\\Internet Explorer").GetValue("Version").toString();
						break;
					case "chrome":
						strBrowserVersion = Microsoft.Win32.Registry.CurrentUser.OpenSubKey("Software\\Wow6432Node\\Microsoft\\Active Setup\\Installed Components\\{8A69D345-D564-463c-AFF1-A69D9E530F96}").GetValue("Version").toString().split("[,]", -1)[0];
						break;
					case "safari":
						strBrowserVersion = Microsoft.Win32.Registry.LocalMachine.OpenSubKey("Software\\Apple Computer, Inc.\\Safari").GetValue("Version").toString();
						break;
					case "opera":
						strBrowserVersion = "";
						break;
				}
			}
			catch (RuntimeException ex)
			{
				strBrowserVersion = "";
			}

		}*/
		return strBrowserVersion;
	}



	public final String SaveScreenShot()
	{
		return SaveScreenShot("");
	}

	//ORIGINAL LINE: public string SaveScreenShot(string sSscreenshotFileName = "")
	public final String SaveScreenShot(String sSscreenshotFileName)
	{
		try
		{
			if (sSscreenshotFileName.equals(""))
			{
				try
				{
					sSscreenshotFileName = GenericLibrary.strCurrentTestCaseID + "_" + genericLibrary.ReplaceSpecialCharacterAndSpaces(String.valueOf(Calendar.getInstance().getTime()), "_") + "_sel.png";
					//int TestCasenameLength = GenericLibrary.strCurrentTestCaseName.Length;
					//sSscreenshotFileName = GenericLibrary.strCurrentTestCaseID + "_" + genericLibrary.ReplaceSpecialCharacterAndSpaces(GenericLibrary.strCurrentTestCaseName.Substring(0, TestCasenameLength / 2), "_") + "_" +
					//DateAndTime.Day(DateAndTime.Now) + "_" + DateAndTime.Month(DateAndTime.Now) + "_" +
					//DateAndTime.Year(DateAndTime.Now) + "_" + DateAndTime.Hour(DateAndTime.Now) + "H_" +
					//DateAndTime.Minute(DateAndTime.Now) + "M_" + DateAndTime.Second(DateAndTime.Now) + "S.png";
				}
				catch (RuntimeException ee)
				{
					sSscreenshotFileName = GenericLibrary.strCurrentTestCaseID + Calendar.getInstance().getTime() + "_sel.png";
					writeToLog(ee.getMessage() + "\r\n" + ee.getStackTrace());
				}
			}
			
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(dicConfig.get("ReportPath").toString() + "LastRun\\" + sSscreenshotFileName + ".png"));
			
			//OpenQA.Selenium.Screenshot screenshot = ((OpenQA.Selenium.ITakesScreenshot)GenericLibrary.driver).GetScreenshot();
			//screenshot.SaveAsFile(dicConfig["ReportPath"].toString() + "LastRun\\" + sSscreenshotFileName, ImageFormat.Png);
		}
		catch (Exception e)
		{
			sSscreenshotFileName = "";
			genericLibrary.writeToLog(e.getMessage() + e.getStackTrace());
		}
		return sSscreenshotFileName;
	}


	public final String SaveDeskTopScreen()
	{
		return SaveDeskTopScreen("");
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public string SaveDeskTopScreen(string sSscreenshotFileName = "")
	public final String SaveDeskTopScreen(String sSscreenshotFileName)
	{
		try
		{
			if (sSscreenshotFileName.equals(""))
			{
				try
				{
					sSscreenshotFileName = GenericLibrary.strCurrentTestCaseID + "_" + genericLibrary.ReplaceSpecialCharacterAndSpaces(String.valueOf(Calendar.getInstance().getTime()), "_") + "_desktop.png";
				}
				catch (RuntimeException ee)
				{
					sSscreenshotFileName = GenericLibrary.strCurrentTestCaseID + String.valueOf(Calendar.getInstance().getTime()) + "_desktop.png";
					writeToLog(ee.getMessage() + "\r\n" + ee.getStackTrace());
				}
			}

			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", new File(sSscreenshotFileName));
		}
		catch (Exception e)
		{
			sSscreenshotFileName = "";
			genericLibrary.writeToLog(e.getMessage() + e.getStackTrace());
		}
		return sSscreenshotFileName;
	}
	
}