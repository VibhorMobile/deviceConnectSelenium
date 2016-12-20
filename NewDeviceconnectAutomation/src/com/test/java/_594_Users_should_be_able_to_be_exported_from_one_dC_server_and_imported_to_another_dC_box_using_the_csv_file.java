package com.test.java;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
import com.opencsv.CSVReader;

/*
 * Author : Dolly
 * Creation Date: First week of February
 * Last Modified Date: Same as creation date
 */

public class _594_Users_should_be_able_to_be_exported_from_one_dC_server_and_imported_to_another_dC_box_using_the_csv_file extends ScriptFuncLibrary
{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "" ;
	private String strexpectedResult = "" , strActualResult ;
	
	public final void testScript() throws InterruptedException, AWTException, IOException 
	 
	 {
		
		//***** Step 1 - Login to DC. *****//
	    
		isEventSuccessful = Login(); 
		
		isEventSuccessful = GoToUsersPage();
		isEventSuccessful  = PerformAction("browser","waitforpagetoload");
		isEventSuccessful = PerformAction("ExportUserbtn",Action.Click);
		if(isEventSuccessful)
		{
			Thread.sleep(5000);
			if(dicCommon.get("BrowserName").toLowerCase().equals("firefox"))
			{
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_DOWN);
				robot.keyPress(KeyEvent.VK_ENTER);
				/*robot.delay(5000);
				robot.keyPress(KeyEvent.VK_F6);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyPress(KeyEvent.VK_ENTER);*/
				Thread.sleep(12000);
			}
			File sourceDir = new File("C:\\Users\\dsolanki\\Downloads");
			File destinationDir = new File("D:\\Default_Code\\Latest_Default\\NewDeviceconnectAutomation\\Artifacts\\Others\\Applications");
				
			MoveFilesToAnotherDirectory(sourceDir, destinationDir, new String[]{"csv"}, false);
			Thread.sleep(10000);
			File destinationDir1 = new File("D:\\Default_Code\\Latest_Default\\NewDeviceconnectAutomation\\Artifacts\\Others\\Applications\\user-export.csv");
			CSVReader reader = new CSVReader(new FileReader(destinationDir1),',');
			List<String[]> csvBody = reader.readAll();
			for(String[] row : csvBody)
			{
				if(Arrays.toString(row).contains(dicCommon.get("EmailAddress")+", True, Administrator, , , , , , , , , , , ,"))
				{
					isEventSuccessful = Logout();
					driver.navigate().to("10.10.0.33");
					PerformAction("browser", Action.WaitForPageToLoad);
					PerformAction("inpEmailAddress", Action.Type, "admin");
					PerformAction("inpPassword", Action.Type, "deviceconnect");
					PerformAction("btnLogin", Action.Click);
					isEventSuccessful = PerformAction("Users", Action.WaitForElement);
					isEventSuccessful = navigateToNavBarPages("Users", "eleUsersHeader");
					isEventSuccessful = PerformAction("browser","waitforpagetoload");
					isEventSuccessful = PerformAction("ImportUserbtn",Action.Click);
	 						
					isEventSuccessful =PerformAction("uploaduserlistbtn",Action.isDisplayed);
						   
					isEventSuccessful = PerformAction("uploaduserlistbtn",Action.Click);
					StringSelection stringSelection = new StringSelection(dicConfig.get("Artifacts") +"\\Applications\\user-export.csv");
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					Thread.sleep(10000);
					isEventSuccessful = PerformAction("//button[text()='Close']",Action.Click);
					isEventSuccessful = Logout();
					isEventSuccessful = Login("deepak_admin@ml.com" , "deviceconnect");
										
				}
			}
			reader.close();
				
			strActualResult = "File downloaded successfully";
		}
		else
		{
			strActualResult = "Unable to click on 'Full table' link";
		}

   }	
}
