package com.common.utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

import net.sourceforge.htmlunit.corejs.javascript.regexp.SubString;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.hamcrest.object.IsEventFrom;
import org.openqa.selenium.*;
//import Microsoft.VisualBasic.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.remote.server.handler.FindElements;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary.Action;
import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;
import com.sun.jna.platform.win32.WinUser.FLASHWINFO;
//import ICSharpCode.SharpZipLib.Zip.*;

public class ApplicationLibrary extends GenericLibrary
{
	public static String strErrMsg_AppLib = "";
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter =new Reporter();
	private GenericLibrary.Browser browser= new GenericLibrary.Browser();
	private boolean isEventSuccessful;	

	public final boolean ClickUsingJSAndVerifyPage(String strelement, String objectToBeVerified)
	{
		boolean isEventSuccessful = false;
		strErrMsg_AppLib = "";
		try
		{
			isEventSuccessful = PerformAction(strelement, Action.ClickUsingJS);
			if (isEventSuccessful)
			{
				isEventSuccessful = PerformAction(objectToBeVerified, Action.WaitForElement, "5");
				if (!isEventSuccessful)
				{
					strErrMsg_AppLib = "Not navigated to destination after clicking on ";
				}
			}
			else
			{
				strErrMsg_AppLib = "Could not click on ";
			}
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "ClickUsingJSAndVerifyPage--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return isEventSuccessful;
	}

	/** 
	 sets the filter from the drowpdown eg. dropdown = "status", filter = "Available", whichPage = "devices" will set the filter to show only available devices
	 <p>It will not affect any other filter ie. in the above example, if the platform filter is set to iOS this function will show available iOS devices</p>
	 <p>***** NOTE : STRING IN THE OPTION PARAMETER SHOULD BE IN THE CORRECT CASE AS IS SHOWN IN THE WEB UI.</p>
	 <p>ALSO, IT DOES NOT CHECK IF THE FILTER IS APPLIED PROPERLY OR NOT for APPLICATIONS filter</p>

	 <!--Created By : Mandeep Mann-->
	 @param dropdown Takes values : "status" for status dropdown and "platform" for platform dropdown.
	 @param option for "status" filter - takes values: "Available", "In Use", "Disabled", "Reserved", "Offline", "All" 
	 <p> *NOTE : 'All' works in case of Applications page ONLY</p>
	 @param whichPage Page on which verification is to be done. Takes values : "Devices" and "Applications".
	 @param gridOrListView View for which verification is to be done . Takes values : "list" and "grid".
	 @param only It is to be passed only when user wants to uncheck all other selected options in the multiselect dropdown selected.
	 <p>It takes values : 'yes' if only one option should be selected and all others should be unchecked.</p>
	 <p>'no' if the already selected values are not to be unchecked.</p>
	 @return 
	 */

	public final boolean SelectFromFilterDropdowns(String dropdown, String option, String whichPage, String gridOrListView)
	{
		return SelectFromFilterDropdowns(dropdown, option, whichPage, gridOrListView, "No");
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public bool SelectFromFilterDropdowns(string dropdown, string option, string whichPage, string gridOrListView, string uncheckOtherOptions = "No")
	public final boolean SelectFromFilterDropdowns(String dropdown, String option, String whichPage, String gridOrListView, String uncheckOtherOptions)
	{
		boolean flag = false;
		String dropdownForXpath = "";
		String droptionOptionXpath = "";
		String dropdownButtonXpath = "";
		String dropdownButtonText = "";
		String dropdownArrowXpath = "";
		strErrMsg_AppLib = "";

		//// modifying to correct case
		if (dropdown.toLowerCase().equals("status"))
		{
			dropdown = "Status";
		}
		else if (dropdown.toLowerCase().equals("platform"))
		{
			dropdown = "Platform";
		}
		else if (dropdown.toLowerCase().equals("used by"))
		{
			dropdown = "Used By";
		}
		else if (dropdown.toLowerCase().equals("devices"))
		{
			dropdown = "Devices";
		}
		else if (dropdown.toLowerCase().equals("users"))
		{
			dropdown = "Users";
		}

		// Modifying the string 'dropdownForXpath' to be used in finding xpath of any option in a particular dropdown.
		if (dropdown.toLowerCase().equals("devices"))
		{
			dropdownForXpath = "device";
		}
		else if (dropdown.toLowerCase().equals("used by"))
		{
			dropdownForXpath = "user";
		}
		else
		{
			dropdownForXpath = dropdown;
		}

		//Modifying options to correct case
		if (option.toLowerCase().equals("available"))
		{
			option = "Available";
		}
		else if (option.toLowerCase().equals("in use"))
		{
			option = "In Use";
		}
		else if (option.toLowerCase().equals("reserved"))
		{
			option = "Reserved";
		}
		else if (option.toLowerCase().equals("disabled"))
		{
			option = "Disabled";
		}
		else if (option.toLowerCase().equals("offline"))
		{
			option = "Offline";
		}
		else if (option.toLowerCase().equals("android"))
		{
			option = "Android";
		}
		else if (option.toLowerCase().equals("ios"))
		{
			option = "iOS";
		}
		else if (option.toLowerCase().equals("all"))
		{
			option = "All";
		}
		/**
		 */

		// Getting identifications for filter and the respective dropdown option to be selected.
		dropdownButtonXpath = dicOR.get("eleDropdownFilter").replace("__FILTER__", dropdown);
		dropdownArrowXpath = dicOR.get("eledropdownArrow").replace("__FILTER__", dropdown);
		droptionOptionXpath = dicOR.get("eleDropdownFilter").replace("__OPTION__", option).replace("__FILTER__", dropdownForXpath.toLowerCase());

		// This piece of code is specifically for Applications page. remove this when improvement for changing the dropdown on applications page is done.
		if (whichPage.toLowerCase().equals("applications"))
		{
			flag = PerformAction(dicOR.get("elePlatformDropdown_appsPage"), Action.ClickAtCenter);
			if (flag)
			{
				String temp = dicOR.get("elePlatformOptionAndroid_Applications");
				flag = PerformAction(dicOR.get("elePlatformOption_appsPage").replace("__OPTION__", temp), Action.ClickUsingJS);

				if (!flag)
				{
					strErrMsg_AppLib = "Could not click on " + dropdownForXpath + " filter.";
				}
			}
			else
			{
				strErrMsg_AppLib = "Unable to click on Platform dropdown.";
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		//This block is for all other dropdowns as they are multiselect.
		else
		{
			if (uncheckOtherOptions.toLowerCase().equals("yes")) // If only parameter is 'yes', --
			{
				flag = UncheckAllOptionsInDropdown(dropdown); // -- then uncheck all the selected options in the drodown and as this function updated the strErrMsg_Applib, it is not required to assign value to strErrMsg_appLib again if this function fails.
			}
			else
			{
				flag = true; // If only is 'no', then there is no need to uncheck the selected options, then mark flag as true.
			}

			if (flag)
			{
				flag = PerformAction(dropdownArrowXpath, Action.Click);
				if (flag)
				{
					//Thread.Sleep("1000");
					// Clicking on the required option in dropdown
					flag = PerformAction(droptionOptionXpath, Action.WaitForElement, "5");
					if (flag)
					{
						flag = PerformAction(droptionOptionXpath, Action.Click);
						if (flag)
						{
							flag = PerformAction(dropdownArrowXpath, Action.Click);
							if (!flag)

							{
								strErrMsg_AppLib = "Could not click on dropdown button to collapse the " + dropdown + " filter.";
							}
						}
						else
						{
							strErrMsg_AppLib = "Could not click on the selected filter from " + dropdown + " dropdown.";
							return flag;
						}
					}
					else
					{
						strErrMsg_AppLib = dropdown + " dropdown not opened after user clicked on the dropdown button.";
					}
				}
				else
				{
					strErrMsg_AppLib = "Could not click on " + dropdown + " filter.";
				}
			}
			//else  //****** No need of else as the strErrMsg_AppLib is modified as when the UncheckAllOptionsInDropdown function is called.
		}

		// Verifying if this filter is applied properly
		if (flag)
		{
			switch (whichPage.toLowerCase())
			{
			case "devices":
				switch (uncheckOtherOptions.toLowerCase())
				{
				case "yes":
					if (dropdown.toLowerCase().equals("status"))
					{
						flag = VerifyDeviceDetailsInGridAndListView("devicestatus", option, gridOrListView);
					}
					else if (dropdown.toLowerCase().equals("platform") && !option.toLowerCase().equals("all"))
					{
						flag = VerifyDeviceDetailsInGridAndListView("deviceplatform", option, gridOrListView);
					}
					else if (dropdown.toLowerCase().equals("used by") && !option.toLowerCase().equals("all"))
					{
						flag = VerifyDeviceDetailsInGridAndListView("deviceUser", option, gridOrListView);
					}
					break;
				case "no":
					dropdownButtonText = GetTextOrValue(dropdownButtonXpath, "text");
					if (dropdownButtonText.contains(option))
					{
						flag = true;
					}
					else
					{
						flag = false;
						strErrMsg_AppLib = "Could not select '" + option + "' from '" + dropdown + "' filter.";
					}
					break;
					//case "applications":
					//    if (!option.ToLower().Equals("all"))
					//        flag = VerifyAppDetailsInListView("platform", option);
					//    break;
				}
				break;
			case "applications":
				if (!option.toLowerCase().equals("all"))
				{
					flag = VerifyAppDetailsInListView("platform", option);
				}
				break;
			}
		}

		//If there are no devices related to the given filter, then 'true' should be returned.
		if (strErrMsg_AppLib.contains("deviceConnect currently has no configured"))
		{
			flag = true;
		}
		return flag;
	}

	/** 
	 This function unchecks all the checked options for the given dropdown.
	 <p>**NOTE : It does not work for Platform dropdown on Applications page right now as it is not a multiselect dropdown.</p>

	 <!--Created By : Hitesh Ghai-->
	 @param dropdownName Dropdown (label) name for which all the options are to be unchecked.
	 @return True or False
	 */
	public final List<String> GetSelectedOptionsInDropdown(String dropdownName) //**** put option , string optionToLeaveSelected="" so that it becomes more efficient and unchecks all options except this option so that the scripts work faster . also put option that of "" is passed then uncheck all the options
	{
		List<String> SelectedOptions = new ArrayList<String>();
		boolean flag = false;
		int optionsCount = 0;
		String errorIndices = "";
		strErrMsg_AppLib = "";
		try
		{
			flag = PerformAction((dicOR.get("eleDropdownFilter").replace("__FILTER__", dropdownName)), Action.isDisplayed);
			if (flag)
			{
				flag = PerformAction((dicOR.get("eleDropdownFilter").replace("__FILTER__", dropdownName)), Action.Click);
				if (flag)
				{
					if ((optionsCount = getelementCount(dicOR.get("eleAllDropdownOptions").replace("__FILTER__", dropdownName))) != 0)
					{
						for (int i = 1; i <= optionsCount; i++)
						{
							flag = PerformAction(dicOR.get("eleCheckMarkDropdownOption").replace("__FILTER__", dropdownName).replace("__INDEX__", (new Integer(i)).toString()), Action.isDisplayed);
							if (flag)
							{

								SelectedOptions.add(GetTextOrValue(dicOR.get("eleAllDropdownOptions").replace("__FILTER__", dropdownName) + "[" + i + "]", "text"));
								//flag = PerformAction(dicOR["eleAllDropdownOptions"].replace("__FILTER__", dropdownName) + "[" + i + "]", Action.Click);
								if (!flag)
								{
									errorIndices = errorIndices + i + ",";
								}
							}
						}
						if (!strErrMsg_AppLib.equals(""))
						{
							throw new RuntimeException("Could not click on options at indices :" + errorIndices + "for " + dropdownName + " dropdown.");
						}
					}
					else
					{
						throw new RuntimeException("No options are there under the selected dropdown OR dropdown list is not open.");
					}

					flag = PerformAction((dicOR.get("eleDropdownFilter").replace("__FILTER__", dropdownName)), Action.Click); //Click on the dropdown again so as the options close
					if (!flag)
					{
						throw new RuntimeException("Could not click on " + dropdownName + " dropdown to close.");
					}
					if (!GetTextOrValue(dicOR.get("eleDropdownFilter").replace("__FILTER__", dropdownName), "text").equals("All"))
					{
						writeToLog("Text displayed on the dropdown is not 'All' which means all options may not have been unchecked.");
					}
					//******Insert a verification if the options are hidden after click or not. They should hide after the above step
				}
				else
				{
					throw new RuntimeException("Could not click on dropdown : " + dropdownName);
				}
			}
			else
			{
				throw new RuntimeException("Dropdown " + dropdownName + "not displayed on the opened page.");
			}
		}
		catch (RuntimeException e)
		{
			//flag = false;                
			strErrMsg_AppLib = "GetSelectedOptionsInDropdown--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return SelectedOptions;
	}

	/** 
	 This function unchecks all the checked options for the given dropdown and closes the dropdown by clicking on the top nav bar where there are no clickable controls.
	 <p>**NOTE : It does not work for Platform dropdown on Applications page right now as it is not a multiselect dropdown.</p>

	 <!--Created By : Mandeep Mann-->
	 @param dropdownName Dropdown (label) name for which all the options are to be unchecked.
	 @return True or False
	 */
	public final boolean UncheckAllOptionsInDropdown(String dropdownName) //**** put option , string optionToLeaveSelected="" so that it becomes more efficient and unchecks all options except this option so that the scripts work faster . also put option that of "" is passed then uncheck all the options
	{
		boolean flag = false, loopEntered = false;
		int optionsCount = 0;
		String errorIndices = "";
		strErrMsg_AppLib = "";
		try
		{
			flag = PerformAction((dicOR.get("eleDropdownFilter").replace("__FILTER__", dropdownName)), Action.isDisplayed);
			if (flag)
			{
				flag = PerformAction((dicOR.get("eleDropdownFilter").replace("__FILTER__", dropdownName)), Action.Click);
				if (flag)
				{
					if ((optionsCount = getelementCount(dicOR.get("eleAllDropdownOptions").replace("__FILTER__", dropdownName))) != 0)
					{
						for (int i = 1; i <= optionsCount; i++)
						{
							loopEntered = true;
							flag = PerformAction(dicOR.get("eleCheckMarkDropdownOption").replace("__FILTER__", dropdownName).replace("__INDEX__", (new Integer(i)).toString()), Action.isDisplayed);
							if (flag)
							{
								flag = PerformAction(dicOR.get("eleCheckMarkDropdownOption").replace("__FILTER__", dropdownName).replace("__INDEX__", (new Integer(i)).toString()), Action.Click);
								//flag = PerformAction(dicOR["eleAllDropdownOptions"].replace("__FILTER__", dropdownName) + "[" + i + "]", Action.Click);
								flag = !PerformAction(dicOR.get("eleCheckMarkDropdownOption").replace("__FILTER__", dropdownName).replace("__INDEX__", (new Integer(i)).toString()), Action.isDisplayed);
								if (!flag)
								{
									errorIndices = errorIndices + i + ",";
								}
							}
						}
						if ((!errorIndices.equals("")) || (loopEntered == false))
						{
							throw new RuntimeException("Could not click on options at indices :" + errorIndices + "for " + dropdownName + " dropdown.");
						}
					}
					else
					{
						throw new RuntimeException("No options are there under the selected dropdown OR dropdown list is not open.");
					}

					//Close the dropdown if it is open and verify that the dropdown options are hidden.
					if (PerformAction(dicOR.get("eleAllDropdownOptions").replace("__FILTER__", dropdownName), Action.isDisplayed))
					{
						flag = PerformAction((dicOR.get("eleDropdownFilter").replace("__FILTER__", dropdownName)), Action.Click); //Click on the dropdown again so as the options close
						if (flag)
						{
							//Code to check if the options are hidden or not after clicking on dropdown again.
							flag = !PerformAction(dicOR.get("eleAllDropdownOptions").replace("__FILTER__", dropdownName), Action.isDisplayed);
							if (!flag)
							{
								throw new RuntimeException("Dropdown not closed after clicking on the dropdown again.");
							}
						}
						else
						{
							throw new RuntimeException("Could not click on " + dropdownName + " dropdown to close.");
						}
					}
					if (!GetTextOrValue(dicOR.get("eleDropdownFilter").replace("__FILTER__", dropdownName), "text").equals("All"))
					{
						throw new RuntimeException("UncheckAllOptionsInDropdown -- Text displayed on the dropdown is not 'All' which means all options may not have been unchecked.");
					}
				}
				else
				{
					throw new RuntimeException("Could not click on dropdown : " + dropdownName);
				}
			}
			else
			{
				throw new RuntimeException("Dropdown " + dropdownName + "not displayed on the opened page.");
			}
		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "UncheckAllOptionsInDropdown--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return flag;
	}

	// ######*********Obsolete function

	public final boolean SelectFilter(String strFilter)
	{
		return SelectFilter(strFilter, "all");
	}

	public final boolean SelectFilter()
	{
		return SelectFilter("NoFilter", "all");
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool SelectFilter(string strFilter = "NoFilter", string strResetFilter = "all")
	public final boolean SelectFilter(String strFilter, String strResetFilter)
	{
		boolean isEventSuccessful = false;
		try
		{
			PerformAction("browser", Action.Refresh);
			switch (strResetFilter.toLowerCase())
			{
			case "all":
				selectPlatform("all");
				if (PerformAction("chkAvailable", Action.isSelected))
				{
					PerformAction("chkAvailable", Action.Click);
				}
				if (PerformAction("chkInUse", Action.isSelected))
				{
					PerformAction("chkInUse", Action.Click);
				}
				break;
			case "available":
				if (PerformAction("chkAvailable", Action.isSelected))
				{
					PerformAction("chkAvailable", Action.Click);
				}
				break;
			case "inuse":
				if (PerformAction("chkInUse", Action.isSelected))
				{
					PerformAction("chkInUse", Action.Click);
				}
				break;
			case "platform":
				selectPlatform("all");
				break;
			case "none":
				break;
			}
			switch (strFilter.toLowerCase())
			{

			case "available":
				isEventSuccessful = PerformAction("chkAvailable", Action.Click);
				if (!isEventSuccessful)
				{
					throw new RuntimeException("Could not select filter 'Available'.");
				}
				break;
			case "inuse":
				isEventSuccessful = PerformAction("chkInUse", Action.Click);
				if (!isEventSuccessful)
				{
					throw new RuntimeException("Could not select filter 'Available'.");
				}
				break;
			case "platform":
				isEventSuccessful = selectPlatform("all");
				if (!isEventSuccessful)
				{
					throw new RuntimeException(strErrMsg_AppLib);
				}
				break;
			case "nofilter":
				isEventSuccessful = true;
				break;
			}
		}
		catch (RuntimeException e)
		{
			isEventSuccessful = false;
			strErrMsg_AppLib = "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return isEventSuccessful;
	}



	/** 
	 This function performs various activities on Users page . eg. 

	 @param strUser
	 @param strAction
	 @param strObject
	 @param strView
	 @return 
	 */

	public final boolean PerformActionsInUsersPage(String strUser, String strAction, String strObject)
	{
		return PerformActionsInUsersPage(strUser, strAction, strObject, "card");
	}

	public final boolean PerformActionsInUsersPage(String strUser, String strAction)
	{
		return PerformActionsInUsersPage(strUser, strAction, "", "card");
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool PerformActionsInUsersPage(string strUser, string strAction, string strObject = "", string strView = "card")
	public final boolean PerformActionsInUsersPage(String strUser, String strAction, String strObject, String strView)
	{
		boolean isEventSuccessful = false;
		String strErr = "";
		strErrMsg_AppLib = "";
		int ActiveUsersCount = 0;
		try
		{
			StopAutoRefresh(strView);
			int intRange = 0;
			if (strUser.contains("@") || strUser.startsWith("USER:") || strUser.startsWith("EMAIL:"))
			{
				String xPath = "";
				if (strUser.contains("@"))
				{
					xPath = "//*[text()='" + strUser + "']/../.." + strObject;
				}
				else if (strUser.startsWith("USER:") || strUser.startsWith("EMAIL:"))
				{
					xPath = "//*[text()='" + strUser.split("[:]", -1)[1] + "']/../../.." + strObject;
				}
				if ((dicCommon.get("BrowserName").toLowerCase().equals("ie")) && strAction.toLowerCase().equals("click"))
				{
					isEventSuccessful = PerformAction(xPath, Action.ClickUsingJS);
				}
				else
				{
					isEventSuccessful = PerformAction(xPath, strAction);
				}
				if (!isEventSuccessful)
				{
					throw new RuntimeException("Could not perform action : '" + strAction + "' on object : '" + strObject + "' for user : '" + strUser);
				}
			}
			else
			{
				if (strUser.toLowerCase().equals("first"))
				{
					intRange = 1;
				}
				else if (strUser.toLowerCase().equals("all"))
				{
					if (strView.toLowerCase().equals("card"))
					{
						intRange = driver.findElements(By.className("user-card")).size();
						if (strAction.toLowerCase().equals("count"))
						{
							if (dicOutput.containsKey("UsersCount"))
							{
								dicOutput.remove("UsersCount");
							}
							dicOutput.put("UsersCount", (new Integer(intRange)).toString());
							return true;
						}
					}
					else
					{
						intRange = driver.findElements(By.xpath("//tbody/tr")).size();
						if (strAction.toLowerCase().equals("count"))
						{
							if (dicOutput.containsKey("UsersCount"))
							{
								dicOutput.remove("UsersCount");
							}
							dicOutput.put("UsersCount", (new Integer(intRange)).toString());
							return true;
						}
					}

				}

				for (int i = 1; i < intRange; i++)
				{
					String xPathUsersHolder = "";
					if (strView.toLowerCase().equals("card"))
					{
						xPathUsersHolder = "//li[" + i + "]/div";
					}
					else
					{
						xPathUsersHolder = "//tbody/tr[" + i + "]/td";
					}
					switch (strAction.toLowerCase())
					{
					case "exist":
						isEventSuccessful = PerformAction(xPathUsersHolder + strObject, Action.Exist);
						if (!isEventSuccessful)
						{
							strErr = strErr + (new Integer(i)).toString() + ", ";
						}
						break;

					case "getallactiveusernames":
						String userId = null;
						if (GetTextOrValue(xPathUsersHolder + "//p[1]//span", "text").contains("Active"))
						{
							ActiveUsersCount++;
							userId = GetTextOrValue(xPathUsersHolder + "//p[2]//span", "text");
							if (userId == null)
							{
								throw new RuntimeException("Could not get the userID from Users Page.");
							}
							try
							{
								dicOutput.put("userId" + ActiveUsersCount, userId);
								isEventSuccessful = true;
							}
							catch (RuntimeException e)
							{
								throw new RuntimeException("Could not put userID in dicOutput dictionary.");
							}
						}
						break;
					}
				}
				dicOutput.put("ActiveUsersCount", (new Integer(ActiveUsersCount)).toString());
			}


			if (strErr.equals(""))
			{
				isEventSuccessful = true;
			}
			else
			{
				throw new RuntimeException("Action " + strAction + " failed for object " + strObject + " for user(s) " + strErr);
			}
		}
		catch (RuntimeException e)
		{
			dicOutput.put("ActiveUsersCount", "0");
			isEventSuccessful = false;
			strErrMsg_AppLib = "PerformActionsInUsersPage--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return isEventSuccessful;
	}


	// blnCallQTPScript variable is false in case user only needs to click on app name in app list that appears after clicking on Connect button
	/** 
	 Connects to a device of given platform with given application.

	 @param strPlatform Platform of the device which is required to be connected.
	 @param strApplication Name of the application to be connected with. Provide exact name that appears on 'Launch application' dialog, eg. 'Phone Lookup'
	 @param whichViewer Defines which viewer to connect the device with : desktop or webviewer.
	 @return 
	 */

	/*public final boolean ConnectDevice(String strPlatform, String strApplication, boolean blnCallQTPScript)
	{
		return ConnectDevice(strPlatform, strApplication, blnCallQTPScript, "desktop");
	}
	 */
	/*public final boolean ConnectDevice(String strPlatform, String strApplication)
	{
		return ConnectDevice(strPlatform, strApplication, true, "desktop");
	}*/

	/*public final boolean ConnectDevice(String strPlatform)
	{
		return ConnectDevice(strPlatform, "", true, "desktop");
	}*/

	/*public final boolean ConnectDevice()
	{
		return ConnectDevice("iOS", "", true, "desktop");
	}*/

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool ConnectDevice(string strPlatform = "iOS", string strApplication = "", bool blnCallQTPScript = true, string whichViewer = "desktop")
	/*public final boolean ConnectDevice(String strPlatform, String strApplication, boolean blnCallQTPScript, String whichViewer)
	{
		strErrMsg_AppLib = "";
		String strStepDescription = "Connect a device and launch an application to it.";
		String strExpectedResult = "Selected application should be launched on Ai Display.";
		if (strPlatform.toLowerCase().equals("ios"))
		{
			strPlatform = "iOS";
		}

		boolean isEventSuccessful = false;
		//PerformAction("browser", Action.Refresh);
		String xpathApplication = "";
		String deviceSelected = "";
		if (strApplication.equals(""))
		{
			xpathApplication = dicOR.get("eleAppListItem");
		}
		else
		{
			xpathApplication = "//a[contains(text(),'" + strApplication + "')]";
		}
		try
		{
			if (!PerformAction("eleDevicesHeader", Action.isDisplayed))
			{
				if (!selectFromMenu("Devices", "eleDevicesHeader"))
				{
					throw new RuntimeException("On selecting 'Devices' menu, 'Devices' page is not opened.");
				}
			}
			//if (SelectFilter("Available"))
			if (SelectFromFilterDropdowns("status", "Available", "devices", "grid"))
			{
				if (!SelectFromFilterDropdowns("platform", strPlatform, "devices", "grid"))
				{
					throw new RuntimeException(strErrMsg_AppLib);
				}
			}
			else
			{
				throw new RuntimeException(strErrMsg_AppLib);
			}
			if (SelectDevice("first", "connect", "grid"))
			{
				if (dicOutput.containsKey("selectedDeviceName"))
				{
					deviceSelected = dicOutput.get("selectedDeviceName");
				}
				Thread.sleep(5000);
				if (PerformAction(xpathApplication, Action.WaitForElement, "30"))
				{
					//KillObjectInstances("MobileLabs.deviceviewer");
					if (PerformAction(xpathApplication, Action.Click))
					{
						isEventSuccessful = PerformAction("eleNotificationRightBottom", Action.WaitForElement, "120");
						if (isEventSuccessful)
						{
							String notificationText = GetTextOrValue("eleNotificationRightBottom", "text");
							isEventSuccessful = notificationText.contains("Application installed successfully.");
							if (!isEventSuccessful)
							{
								throw new RuntimeException("Notification - 'Application install successful' is not displayed. Following notification is displayed - '" + notificationText + "'.");
							}
						}
						else
						{
							throw new RuntimeException("No success notification displayed on devices page.");
						}
					}
					else
					{
						throw new RuntimeException("Could not click on application " + strApplication);
					}
				}
				else
				{
					throw new RuntimeException("Application list is not displayed on clicking on connect button.");
				}
			}
			else
			{
				throw new RuntimeException(strErrMsg_AppLib);
			}
			if (blnCallQTPScript)
			{
				if (genericLibrary.LaunchQTPScript("LaunchApplication", strStepDescription + " <br> Device name : '" + deviceSelected + "'.", strExpectedResult))
				{
					isEventSuccessful = true;
				}
				else
				{
					throw new RuntimeException(strErrMsg_GenLib);
				}
			}
		}
		catch (RuntimeException e)
		{
			isEventSuccessful = false;
			strErrMsg_AppLib = "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			if (blnCallQTPScript)
			{
				//(new Reporter()).ReportStep("Connect to device.", "Application should gets launched in device.", strErrMsg_AppLib, "Fail");
				writeToLog("ConnectDevice -- " + e.getMessage());
			}
		}
		//SelectFilter();
		SelectFromFilterDropdowns("status", "All", "devices", "grid");
		SelectFromFilterDropdowns("platform", "All", "devices", "grid");
		//PerformAction("browser", Action.Refresh);
		return isEventSuccessful;
	}*/


	public final boolean StopAutoRefresh()
	{
		return StopAutoRefresh("grid");
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool StopAutoRefresh(string strView = "grid")
	public final boolean StopAutoRefresh(String strView)
	{
		//bool flag = false;
		//string strJavascript = "";
		//if (strView.ToLower() == "grid")
		//    strJavascript = "document.getElementsByClassName('cards-layout')[0].setAttribute('data-bind','')";
		//else
		//    strJavascript = "document.getElementsByTagName('tbody')[0].setAttribute('data-bind','')";
		//try
		//{
		//    genericLibrary.ExecuteJavascript(strJavascript);
		//    flag = true;
		//}
		//catch (Exception e)
		//{
		//    flag = false;
		//    strErrMsg_AppLib = e.Message;
		//}
		return true;
	}


	/** 
	 This function creates a User with specified parameters
	 If emailID is not appended with "@deviceconnect.com", function will append the current date & time and create a new emailID accordingly.
	 If emailID is simple "newuser", then it will append, if emailID is "newuser@deviceconnect.com" then it won't append the current date time.
	 The newly created User with emailID with current date and time is saved in the output dictionary named as EmailID

	 <!--Modified By : Vinita Mahajan-->
	 @param firstName First Name of the User
	 @param lastName Last Name of the User
	 @param emailID email ID with which to perform Login
	 @param password
	 @param userType Role : admin, testuser - testuser is by default created if not specified
	 @param enabled This indicated whether the User created should be active : enabled or inactive : disbaled
	 @return This function returns boolean value.
	 */

	public final boolean createUser(String firstName, String lastName, String password, String [] userType, boolean enabled, boolean check)
	{
		return createUser(firstName, lastName, "", password, userType, enabled, check);
	}
	
	public final boolean createUser(String firstName, String lastName, String password, String [] userType, boolean enabled)
	{
		return createUser(firstName, lastName, "", password, userType, enabled, false);
	}

	public final boolean createUser(String firstName, String lastName, String emailID, String password)
	{
			//return createUser(firstName, lastName, emailID, password, "testuser", true,false); //MERGING COMMENTED (API)
		
		String [] userType={"Tester"}; //MERGING SELENIUM
		return createUser(firstName, lastName, emailID, password, userType, true,true); //MERGING SELENIUM
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public bool createUser(string firstName, string lastName, string emailID, string password, string userType = "testuser", bool enabled = true)
	public final boolean createUser(String firstName, String lastName, String emailID, String password, String [] userType, boolean enabled, boolean anotherUser)
	{
		boolean flag = false;
		strErrMsg_AppLib = "";
		List<WebElement> roleName=new ArrayList<WebElement>();
		List<WebElement> rolechkBox=new ArrayList<WebElement>();
		int roleIndex=0;
		//default value of emailID
		try
		{
			if (dicOutput.containsKey("EmailID"))
			{
				dicOutput.remove("EmailID");
			}

			if (firstName.equals(""))
			{
				firstName = "selenium";
			}
			if (lastName.equals(""))
			{
				lastName = "user";
			}
			if (password.equals(""))
			{
				password = "deviceconnect";
			}
			/*if (userType.equals("") )
			{
				userType = "testuser";
			}
			 */
			
			
			if (emailID.equals("")) //If email ID is not given by user, then assign it value and put to dictionary
			{
				emailID = FetchDateTimeInSpecificFormat("MMddyy_hhmmss") + "@deviceconnect.com";
				//dicOutput.put("EmailID", emailID);
			}


			if (!(emailID.contains("@deviceconnect.com"))) //If email ID is not in correct format, then correct it and put the value in dicOutput
			{
				emailID = emailID + FetchDateTimeInSpecificFormat("MMddyy_hhmmss") + "@deviceconnect.com";
				//dicOutput.put("EmailID", emailID);
			}

			dicOutput.put("EmailID", emailID);
			if (PerformAction("lnkLogout", Action.isDisplayed))
			{
				flag = PerformAction("btnMenu", Action.Click);
			}
			if (!PerformAction("inpFirstNameCreateUser", Action.isDisplayed))
			{
				flag = PerformAction("btnCreateUser", Action.Click);
			}
			else
			{
				flag = true;
			}
			if (flag)
			{
				waitForPageLoaded();
				flag = PerformAction("inpFirstNameCreateUser", Action.WaitForElement);
				if (flag)
				{
					//PerformAction("txtFirstName", Action.Click);
					flag = PerformAction("inpFirstNameCreateUser", Action.Type, firstName);
					if (!flag)
					{
						throw new RuntimeException("Could not type in First Name field");
					}

					//PerformAction("txtLastName", Action.Click);
					flag = PerformAction("inpLastNameCreateUser", Action.Type, lastName);
					if (!flag)
					{
						throw new RuntimeException("Could not type in Last Name field");
					}

					//PerformAction("txtLogin", Action.Click);
					flag = PerformAction("txtLogin", Action.Type, emailID);
					if (!flag)
					{
						throw new RuntimeException("Could not type in Login field");
					}

					//PerformAction("txtPassword", Action.Click);
					flag = PerformAction("txtPassword", Action.Type, password);
					if (!flag)
					{
						throw new RuntimeException("Could not type in Password field");
					}

					//PerformAction("txtConfirmPassword", Action.Click);
					flag = PerformAction("txtConfirmPassword", Action.Type, password);
					if (!flag)
					{
						throw new RuntimeException("Could not type in Confirm Password field");
					}

					// Select roles
					if(userType.length>0)
					{
						roleName=getelementsList("roleOnUserDetailsPage");
						rolechkBox=getelementsList(dicOR.get("roleOnUserDetailsPage").replace("/label", "/label/input"));
						flag=false;
						for(WebElement role: roleName)
						{
							for(String roleValue : userType)
							{
								if(role.getText().equals(roleValue))
								{
									flag=true;
									rolechkBox.get(roleIndex).click();
									break;
								}
							}
							roleIndex++;
						}
						if(!flag)
						{
							throw new RuntimeException("Role "+userType+" not found");
						}
					} 

/* API Code Block 
MERGING COMMENTED (API)

// in case admin type user is to be created, then click  on dropdown , select 'Admin' and 
					if (userType.toLowerCase().equals("admin"))
					{
						flag = PerformAction("adminrole", Action.WaitForElement, "10");
						if (flag)
						{
							flag = PerformAction("adminrole", Action.Click);
							if (flag)
							{
								//Verify Admin is selected.
								flag = GetTextOrValue(dicOR.get("adminrole").replace("/input", ""), "text").equals("Administrator");
								if (!flag)
								{
									throw new RuntimeException("Admin option is not selected.");
								}
							}
							else
							{
								throw new RuntimeException("Could not click on 'Admin' option on dropdown.");
							}
						}
						else
						{
							throw new RuntimeException("Dropdown not displayed after clicking on 'Tester/Admin' dropdown.");
						}
					}
					else


					{
						flag = PerformAction("testerrole", Action.WaitForElement, "10");
						if (flag)




						{
							flag = PerformAction("testerrole", Action.Click);
							if (flag)

							{
								//Verify Admin is selected.
								flag = GetTextOrValue(dicOR.get("testerrole").replace("/input", ""), "text").equals("Tester");
								if (!flag)

								{
									throw new RuntimeException("Tester option is not selected.");



								}
							}
							else
							{
								throw new RuntimeException("Could not click on 'Tester' option on dropdown.");
							}

						}
						else

						{
							throw new RuntimeException("Tester option did not found on the page.");
						}
					}
					*/

					// now select if the user is to be disabled as by default it is enabled
					if (enabled == false)
					{
						flag = PerformAction("chkActive_CreateUser", Action.Click);
						if (flag)
						{
							flag = !PerformAction("chkActive_CreateUser", Action.isSelected);
							if (!flag)
							{
								throw new RuntimeException("Checkbox is not unchecked.");
							}
						}
						else
						{
							throw new RuntimeException("Could not click on Checkbox for active.");
						}
					}
					if(anotherUser)
					{
						PerformAction("CreateAnotherUser",Action.Click);
					}

					PerformAction("browser", Action.Scroll, "0");
					if(dicCommon.get("BrowserName").toLowerCase().equals("chrome")||dicCommon.get("BrowserName").toLowerCase().equals("ie"))
					{
						flag = PerformAction("btnSave", Action.Click);
						if(!flag)
						{
							throw new RuntimeException("Could not click on 'Save' button"); 
						}
						flag = PerformAction("eleNotificationRightBottom", Action.WaitForElement);
						if(flag)
						{
							String text = GetTextOrValue(dicOR.get("eleNotificationRightBottomtext"), "text");
							flag = text.contains("User created.");
							if (!flag)
							{
								throw new RuntimeException("'Notification does not read : 'User created.' but : " + text); 
							}
						}
						else
						{
							throw new RuntimeException("'User updated.' notification did not appear on the page.");
						}
					}
					else
					{
						driver.findElement(By.xpath("//button[@class='btn btn-success' and @type='submit']")).click();
						String text = GetTextOrValue(dicOR.get("eleNotificationRightBottomtext"), "text");
						flag = text.contains("User created.");
						if (!flag)
						{
							throw new RuntimeException("'Notification does not read : 'User created.' but : " + text); 
						}
					}
					if(anotherUser)
					{
						PerformAction("inpFirstNameCreateUser", Action.isDisplayed);
					}
				}
				else
				{
					throw new RuntimeException("Create User page not displayed after clicking on 'Create User' button");
				}
			}
			else
			{
				throw new RuntimeException("Could not click on 'Create User' button ");
			}
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "createUser---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			flag = false;
		}
		return flag;
	}

	/** 
	 This Function fetches all the details of all devices

	 <!--Modified By : Vinita  Mahajan-->
	 @param strDetailName
	 @param strView
	 @return 
	 */

	public final ArrayList<String> GetAllDevicesDetails(String strDetailName)
	{
		return GetAllDevicesDetails(strDetailName, "grid");
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public List<string> GetAllDevicesDetails(string strDetailName, string strView = "grid")
	public final ArrayList<String> GetAllDevicesDetails(String strDetailName, String strView)
	{
		strErrMsg_AppLib = "";
		String xpathDevicesHolder;
		ArrayList<String> lstDevicesDetails = new ArrayList<String>();
		if (strView.toLowerCase().equals("list"))
		{
			xpathDevicesHolder = GenericLibrary.dicOR.get("eleDevicesHolderListView");
		}
		else
		{
			xpathDevicesHolder = GenericLibrary.dicOR.get("eleDevicesHolderGridView");
		}
		try
		{
			if (PerformAction("eleDevicesTab_Devices", Action.WaitForElement))
			{
				//Verifying devices are displayed
				//if (GetTextOrValue("eleNoTableRowsWarning", "text").contains("deviceConnect currently has no configured devices or your filter produced no results.")) //MERGING COMMENTED (API)
				if (VerifyMessage_On_Filter_Selection())
				{
					throw new RuntimeException("deviceConnect currently has no configured devices or your filter produced no results.");
				}
				StopAutoRefresh(strView);
				int noOfDevices = getelementCount(xpathDevicesHolder)-1;
				//int noOfDevices = driver.FindElements(By.XPath(xpathDevicesHolder)).Count;
				for (int i = 1; i <= noOfDevices; i++)
				{
					String strValue = GetDeviceDetailInGridAndListView(i, strDetailName);
					if (!strValue.equals(""))
					{
						lstDevicesDetails.add(strValue);
					}
					else
					{
						throw new RuntimeException(strErrMsg_AppLib);
					}
				}
			}
			else
			{
				throw new RuntimeException("Devices page is not displayed");
			}
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "GetAllDevicesDetails---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			lstDevicesDetails.clear();
		}
		return lstDevicesDetails;
	}


	public final boolean RebootDevice(String strAction, boolean gotoDeviceDetails)
	{
		return RebootDevice(strAction, gotoDeviceDetails, "all");
	}

	public final boolean RebootDevice(String strAction)
	{
		return RebootDevice(strAction, false, "all");
	}

	public final boolean RebootDevice()
	{
		return RebootDevice("reboot", false, "all");
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool RebootDevice(string strAction = "reboot", bool gotoDeviceDetails = false, string strPlatform = "all")
	public final boolean RebootDevice(String strAction, boolean gotoDeviceDetails, String strPlatform)
	{
		strErrMsg_AppLib = "";
		boolean isEventSuccessful = false;
		try
		{
			if (gotoDeviceDetails)
			{
				//PerformAction(dicOR["chkAvailable"], Action.Click);
				SelectFromFilterDropdowns("status", "Available", "devices", "grid");
				if (SelectFromFilterDropdowns("platform", strPlatform, "device details", "grid"))
				{
					if (!SelectDevice("first", "devicedetails"))
					{
						throw new RuntimeException(strErrMsg_AppLib);
					}
				}
				else
				{
					throw new RuntimeException(strErrMsg_AppLib);
				}
			}
			switch (strAction.toLowerCase())
			{
			case "reboot":
				if (PerformAction("btnReboot", Action.isDisplayed))
				{
					if (PerformAction("btnReboot", Action.isDisplayed))
					{
						if (PerformAction("btnReboot", Action.Click))
						{
							//Verifying confirmation popup is opened.
							isEventSuccessful = PerformAction("eleConfirmReboot", Action.WaitForElement);
							if (isEventSuccessful)
							{
								isEventSuccessful = PerformAction("btnContinue", Action.Click);
								if (isEventSuccessful)
								{
									//Verify device status is changed to 'Offline'.
									//isEventSuccessful = WaitTillDeviceStatusIsChanged(30,"Offline");
									isEventSuccessful = PerformAction("//span[text()='Offline']", Action.WaitForElement, "60");
									if (!isEventSuccessful)
									{
										throw new RuntimeException("Device status is not changed to 'Offline'.");
									}
								}
								else
								{
									throw new RuntimeException("Could not click on 'Continue' button on 'Confirm Reboot' popup.");
								}
							}
							else
							{
								throw new RuntimeException("'Confirm Reboot' popup is not opened.");
							}
						}
						else
						{
							throw new RuntimeException("Could not click on reboot button.");
						}
					}
					else
					{
						throw new RuntimeException("Reboot button is not enabled.");
					}
				}
				else
				{
					throw new RuntimeException("Reboot button is not displayed.");
				}
				break;

			case "verifyrebootdisable":
				if (!PerformAction("btnReboot", Action.isEnabled))
				{
					isEventSuccessful = true;
				}
				else
				{
					throw new RuntimeException("Reboot button is enabled.");
				}
				break;
			}

		}
		catch (RuntimeException e)
		{
			isEventSuccessful = false;
			strErrMsg_AppLib = "RebootDevice---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return isEventSuccessful;
	}

	/** 
	 Verifies that delete button is visible for all apps displayed on Applications index page.

	 <!--Created By : Mandeep Mann-->
	 @return True if all apps have this button enabled and False if any one of them does not have either 'Install' button enabled or 'Delete' button accessible.
	 */

	public final boolean VerifybtnDeleteOnApplicationsPage()
	{
		return VerifybtnDeleteOnApplicationsPage(true);
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool VerifybtnDeleteOnApplicationsPage(bool chkDisplayed = true)
	public final boolean VerifybtnDeleteOnApplicationsPage(boolean chkDisplayed)
	{
		boolean flag = false;
		boolean clickable = false; // displayed = false;
		int AppsCount = 0;
		String errorIndices = "";
		boolean strVerification = false;
		strErrMsg_AppLib = "";
		AppsCount = getelementCount(dicOR.get("eleAppTableRows"));
		String deletebtnXPath = getValueFromDictionary(dicOR, "eleDeleteOption_AppPage");

		if (AppsCount != 0)
		{
			for (int i = 1; i <= AppsCount; i++)
			{
				clickable = PerformAction(dicOR.get("eleInstallAppDropdown") + "[" + i + "]", Action.ClickUsingJS);
				// Conditional verification according to whether it is required to check that delete button is displayed or it is hidden
				if (chkDisplayed)
				{
					strVerification = PerformAction(deletebtnXPath , Action.isDisplayed);
				}
				else
				{
					strVerification = PerformAction(deletebtnXPath , Action.isNotDisplayed);
				}
				if (!strVerification && !clickable)
				{
					errorIndices = errorIndices + ", " + i;
				}
			}
		}
		else
		{
			strErrMsg_AppLib = "VerifybtnDeleteOnApplicationsPage--" + "Applications list is empty or is not found on page.";
			return false;
		}

		// Check if there is error message . If no error is appended to errorIndices it means everything went alright function is pass.
		if (errorIndices.equals(""))
		{
			flag = true;
		}
		else
		{
			if (chkDisplayed)
			{
				strErrMsg_AppLib = "VerifybtnDeleteOnApplicationsPage---" + "Delete button is not visible for application at these indices: " + errorIndices;
			}
			else
			{
				strErrMsg_AppLib = "VerifybtnDeleteOnApplicationsPage---" + "Delete button is visible for applications at these indices: " + errorIndices;
			}
		}
		return flag;
	}


	//Verifies that the value of 'Provisioned' matches the value passed, for each device under 'Compatible Devices' section on app details page.
	//
	//<!--Created By : Mandeep Mann-->
	//@return True if all devices have the given status for provisioned column.
	//
	public final boolean VerifyProvisionedValue_AppDetails(String value)
	{
		boolean flag = false;
		int RowsCount = 0;
		String errorIndices = "";
		String strProvisionedValue = "";
		strErrMsg_AppLib = "";
		RowsCount = getelementCount(dicOR.get("eleDeviceListNameCol"));

		if (RowsCount != 0)
		{
			for (int i = 2; i <= RowsCount + 1; i++)
			{
				strProvisionedValue = GetTextOrValue(dicOR.get("ProvisionedValue_Appdetails").replace("__INDEX__", (new Integer(i)).toString()), "text");
				if ( ! strProvisionedValue.trim().equals(value))
				{
					errorIndices = errorIndices + ", " + i;
				}
			}
		}
		else
		{
			if (PerformAction("CompatibleDevWarning_appDetails", Action.isDisplayed))
			{
				strErrMsg_AppLib = "VerifyProvisionedValue_AppDetails---" + "There are no compatible devices for the application.";
			}
			else
			{
				strErrMsg_AppLib = "VerifyProvisionedValue_AppDetails---" + "Compatible devices list is empty or is not found on page.";
			}
			return false;
		}

		// Check if there is error message . Empty errorIndices means everything is as expected
		if (errorIndices.equals(""))
		{
			flag = true;
		}
		else
		{
			strErrMsg_AppLib = "VerifyProvisionedValue_AppDetails---" + "Provisioned value is not '" + value + "' for devices at indices : " + errorIndices;
		}
		return flag;
	}


	//public bool ReleaseDevice(string userName, string Platform = "All")
	//{

	//    bool flag = false;
	//    try
	//    {


	//        // go to devices page if it is not already displayed.
	//        if (!PerformAction("eleDevicesHeader", Action.isDisplayed))
	//        {

	//            if (!selectFromMenu("Devices", "eleDevicesHeader"))
	//                throw new Exception("On selecting 'Devices' menu, 'Devices' page is not opened.");
	//        }
	//        else


	//            PerformAction("browser", Action.Refresh);
	//        flag = PerformAction("chkInUse", Action.SelectCheckbox);
	//        if (flag)
	//        {


	//            flag = PerformAction("btnSelect", Action.Click);
	//            if (flag)
	//            {


	//                flag = PerformAction("//a[text()='" + userName + "']", Action.Click);
	//                if (flag)
	//                {


	//                    flag = selectPlatform(Platform);
	//                    if (flag || strErrMsg_AppLib.contains("deviceConnect currently has no configured devices or your filter produced no results."))
	//                    {

	//                        if (strErrMsg_AppLib.contains("deviceConnect currently has no configured devices or your filter produced no results."))
	//                        {

	//                            PerformAction("browser", Action.Refresh);
	//                            CloseWindow("MobileLabs.deviceviewer");
	//                            return true;
	//                        }
	//                        else
	//                        {




	//                            flag = SelectDevice("first");
	//                            if (flag)
	//                            {


	//                                flag = PerformAction("btnRelease", Action.Click);
	//                                if (flag)
	//                                {


	//                                    if (PerformAction("eleNotificationRightBottom", Action.WaitForElement, "10"))
	//                                    {

	//                                        flag = GetTextOrValue("eleNotificationRightBottom", "text").contains("Device released");
	//                                        if (!flag)

	//                                            throw new Exception("Device not released as 'Device released' notification not displayed");
	//                                    }
	//                                    else


	//                                        throw new Exception("'Device released' notification not displayed even after waiting for 10 seconds.");
	//                                }
	//                                else


	//                                    throw new Exception("Could not click on 'Release' button.");
	//                            }
	//                            else


	//                                throw new Exception(strErrMsg_AppLib);
	//                        }
	//                    }
	//                    else



	//                        throw new Exception(strErrMsg_AppLib);
	//                }
	//                else


	//                    throw new Exception("Could not select the username from dropdown.");
	//            }
	//            else


	//                throw new Exception("Coud not click on 'Select' button.");
	//        }
	//        else


	//            throw new Exception("Could not select 'In Use' checkbox");

	//    }

	//    catch (Exception e)
	//    {

	//        flag = false;
	//        strErrMsg_AppLib = e.Message;
	//        selectFromMenu("Devices", "eleDevicesHeader");
	//        return flag;
	//    }

	//    flag = ReleaseDevice(userName, Platform);
	//    return flag;
	//}


	//Logs in the given user to dC. If no username is specified, it logs in using the defult admin.
	//
	//<!--Last Modified : 9/2/2015 by Mandeep Kaur-->
	//@param strEmailAddress Email Address of user to be logged in.
	//@param strPassword Password of the user.
	//@param GoToDevicesPage Give 'true if user needs to be on Devices page just after login.
	//@return 
	//


	public final boolean LoginToDC(String strEmailAddress, String strPassword)
	{
		return LoginToDC(strEmailAddress, strPassword, true);
	}

	public final boolean LoginToDC(String strEmailAddress)
	{
		return LoginToDC(strEmailAddress, "", true);
	}

	public final boolean LoginToDC()
	{
		return LoginToDC("", "", true);
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool LoginToDC(string strEmailAddress = "", string strPassword = "", bool GoToDevicesPage = true)
	public final boolean LoginToDC(String strEmailAddress, String strPassword, boolean GoToDevicesPage)
	{
		strErrMsg_AppLib = "";
		if (strEmailAddress.equals(""))
		{
			strEmailAddress = GenericLibrary.dicCommon.get("EmailAddress");
		}
		if (strPassword.equals(""))
		{
			strPassword = GenericLibrary.dicCommon.get("Password");
		}
		boolean isEventSuccessful = false;
		try
		{
			//Check if browser is not already opened
			if (GenericLibrary.driver == null)
			{
				genericLibrary.LaunchWebDriver();
			}
			//Verify login page is opened.
			waitForPageLoaded();
			
			if (PerformAction("btnLogin", Action.isDisplayed))
			{
				if (!PerformAction("inpEmailAddress", Action.Type, strEmailAddress))
				{
					throw new RuntimeException("Could not enter Email Address " + strEmailAddress + " in correponding field.");
				}
				if (!PerformAction("inpPassword", Action.Type, strPassword))
				{
					throw new RuntimeException("Could not enter Email Address " + strPassword + " in correponding field.");
				}
				if (PerformAction("btnLogin", Action.ClickUsingJS))
				{
					waitForPageLoaded();
					PerformAction("browser", Action.WaitForPageToLoad);
					isEventSuccessful = PerformAction("btnMenu", Action.WaitForElement);
					if (isEventSuccessful)
					{
						if (GoToDevicesPage)
						{
							if (!PerformAction("eleDevicesTab_Devices", Action.isDisplayed)) // Checking if the 'Devices side tab is displayed to amke sure that the user is on Devices page.
							{
								//isEventSuccessful = selectFromMenu("Devices", dicOR["eleDevicesHeader"]);
								isEventSuccessful = navigateToNavBarPages("Devices", "eleDevicesTab_Devices");
								if (!isEventSuccessful)
								{
									throw new RuntimeException("Could not click on 'Devices' on top nav bar.");
								}
							}
						}
					}
					else
					{
						throw new RuntimeException("User " + strEmailAddress + " is not logged in on clicking on 'Login' button.");
					}
				}
				else
				{
					throw new RuntimeException("Could not click on 'Login' button.");
				}

            }
            else
            {
                throw new RuntimeException("Login page is not opened.");
            }

        }
        catch (RuntimeException e)
        {
            strErrMsg_AppLib = "LoginToDC--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
            isEventSuccessful = false;
        }
        return isEventSuccessful;
    }

     
    //It opens the device details page of the either first displayed device or the first device that matches the verison or deviceName supplied.
    //<p>It also puts the name of selected device in 'dicOutput["selectedDeviceName"]'</p>
    //
    //<!--Modified By : Mandeep Mann-->
    //@param strDeviceOption It takes three values : 'first', 'version'or 'name'. If user wants to select a device with specific version, he can provide 'version' in this parameter.
    //@param strActionOrValue <p>If strDeviceOption = "first" then it takes values = '1'</p>
    //<p>If strDeviceOption = "version", then its value should be the full OS version, eg. "Android 3.2"</p>
    //<p>If strDeviceOption = "devicename", then its value should be the full name of device as displayed on devices page IN CORRECT CASE. </p>
    //
    //@param strView It is the devices index view open at the moment, i.e. grid or list.
    //@return True or False
    //

	public final boolean SelectDevice(String strDeviceOption, String strValue)
	{
		return SelectDevice(strDeviceOption, strValue, "list");
	}
	public final boolean SelectDevice(String strDeviceOption)
	{
		return SelectDevice(strDeviceOption, "", "list");
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool SelectDevice(string strDeviceOption, string strValue = "1", string strView = "grid")
	public final boolean SelectDevice(String strDeviceOption, String strValue, String strView)
	{
	boolean isEventSuccessful = false;
	String strDeviceName = "", xpathDevicesHolder = "", deviceNameLink = "",valuetext="";
	strErrMsg_AppLib = "";
	//WebElement element = null, childElement= null;
	try
	{
		waitForPageLoaded();
	if (PerformAction("eleDevicesTab_Devices", Action.WaitForElement))
	{
	//Verifying some devices are displayed for the applied filter
	valuetext = GetTextOrValue("eleNoTableRowsWarning", "text");
	if (valuetext!=null)
	{
	if(valuetext.contains("deviceConnect currently has no configured devices or your filter produced no results."))
					{
							//strErrMsg_AppLib = "deviceConnect currently has no configured devices or your filter produced no results.";
							throw new RuntimeException("deviceConnect currently has no configured devices or your filter produced no results.");
						}
					}
					//Get xpath for devices in card/list page
					if (strView.toLowerCase().equals("list"))
					{
						xpathDevicesHolder = dicOR.get("eleDevicesHolderListView");
					}
					else
					{
						xpathDevicesHolder = dicOR.get("eleDevicesHolderGridView");
					}
					
					// cases for opening device details page of a device with given specification i.e. strDeviceOption
					switch (strDeviceOption.toLowerCase())
					{
					case "first":
						//First put element xpath from OR in variable , according to sView and browser, element property is taken differently in case of IE
						if (strView.toLowerCase().equals("list"))
						{
							deviceNameLink = dicOR.get("eleDeviceName_ListView").replace("__INDEX__", "1");
						}
						else if (dicCommon.get("BrowserName").toLowerCase().equals("ie"))
						{
							deviceNameLink = "css=.card-detail-link";
						}
						else
						{
							deviceNameLink = dicOR.get("eleDeviceName_CardsView").replace("__INDEX__", "1");
						}
						break;

					case "version":
						if (strView.toLowerCase().equals("list"))
						{                    
							deviceNameLink = "(//td[@title='" + strValue + "']/../td/div[2]/div[@class='status-description-row'])[1]/a";//"(/td[@title='" + strValue + "'])[1]/../td[2]/a";
						}
						else
						{
							deviceNameLink = "(" + xpathDevicesHolder + "/div/p[@title='" + strValue + "'])[1]/../div/a";
						}
						break;

					case "devicename":
						if (strValue.equals(""))
						{
							deviceNameLink = "(//a[starts-with(@href,'#/Device/Detail/')])[1]";

						}
						else
						{
							deviceNameLink =   "//a[starts-with(@href,'#/Device/Detail/') and text() = '"+strValue+"']";
						}
						break;
					}

					//Get device name and put it to dicOutput
					strDeviceName = GetTextOrValue(deviceNameLink, "text");
					try
					{
						if (dicOutput.containsKey("selectedDeviceName"))
						{
							dicOutput.remove("selectedDeviceName");
						}
						dicOutput.put("selectedDeviceName", strDeviceName);
					}
					catch (RuntimeException e)
					{
						writeToLog("SelectDevice -- Unable to put devicename to dicOutput dictionary." + e.getStackTrace());
					}

					//Click on device name and verify correct device details page is opened.
					if ( ! strDeviceName.equals(""))
					{

						if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
						{
							isEventSuccessful = PerformAction(deviceNameLink, Action.ClickUsingJS);
						}
						else
						{
							isEventSuccessful = PerformAction(deviceNameLink, Action.Click);
						}
						if (isEventSuccessful)
						{
							isEventSuccessful = PerformAction("eleDeviceNameinDeviceDetailsHeader", Action.WaitForElement);
							if (isEventSuccessful)
							{
								isEventSuccessful = GetTextOrValue("eleDeviceNameinDeviceDetailsHeader", "text").equals(strDeviceName);

								if (!isEventSuccessful)
								{
									throw new RuntimeException("Correct Device details page is not opened for device: " + strDeviceName + " in " + strView + " view.");
								}
							}
							else
							{
								throw new RuntimeException("Device details page not opened after clicking on device name link for device : " + strDeviceName + " in " + strView + " view.");
							}
						}
						else
						{
							throw new RuntimeException("Could not click  on device name link (cards view on devices page) for device : " + strDeviceName + " in " + strView + " view.");
						}
					}
					else
					{
						throw new RuntimeException("Could not find any device with the given parameters.");
					}
				}
				else
				{
					throw new RuntimeException("Devices page is not displayed");
				}
			}
			catch (RuntimeException e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "SelectDevice---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return isEventSuccessful;
		}


	//Clicks on Connect button (on devices card/list view) for the first device displayed OR first device that matches the given option.
	//<p>It also verifies if 'Launch Application' dialog is opened."</para>
	//
	//@param strDeviceOption It takes values : "first", "version" and "deviceName".
	//@param strValue It takes value "1" for 'first', exact value for 'version', or exact name in correct case for 'deviceName' 
	//@param strView Devices page view open at the moment. i.e. 'grid' or 'list'
	//@return True or false
	//

	public final boolean OpenLaunchAppDialog(String strDeviceOption, String strValue)
	{
		return OpenLaunchAppDialog(strDeviceOption, strValue, "grid");
	}

	public final boolean OpenLaunchAppDialog(String strDeviceOption)
	{
		return OpenLaunchAppDialog(strDeviceOption, "", "list");
	}
	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool OpenLaunchAppDialog(string strDeviceOption, string strValue = "", string strView = "grid")
	public final boolean OpenLaunchAppDialog(String strDeviceOption, String strValue, String strView)
	{
		boolean isEventSuccessful = false;
		String xpathDevicesHolder = "", connectBtn = "";
		strErrMsg_AppLib = "";
		try
		{
			if (PerformAction("eleDevicesTab_Devices", Action.WaitForElement))
			{
				//Verifying some devices are displayed for the applied filter
				if (VerifyMessage_On_Filter_Selection())
				{
					throw new RuntimeException("deviceConnect currently has no configured devices or your filter produced no results.");
				}
				//Get xpath for devices in card/list page
				if (strView.toLowerCase().equals("list"))
				{
					xpathDevicesHolder = dicOR.get("eleDevicesHolderListView");
				}
				else
				{
					xpathDevicesHolder = dicOR.get("eleDevicesHolderGridView");
				}
				// cases for opening device details page of a device with given specification i.e. strDeviceOption
				switch (strDeviceOption.toLowerCase())
				{
				case "first":
					//First put element xpath from OR in variable , according to sView and browser, element property is taken differently in case of IE
					if (strView.toLowerCase().equals("list"))
					{
						//connectBtn = dicOR.get("btnConnect_ListView") + "[1]";
						String buttonName = GetTextOrValue(dicOR.get("btnConnectGridView").replace("[__INDEX__]", ""), "text");
						if(buttonName.equalsIgnoreCase("connect"))
						{
							connectBtn = dicOR.get("btnConnect_ListView") + "[1]";
						}
						else 
						{
							isEventSuccessful = PerformAction(dicOR.get("btnToggle_DevicesPage").replace("_index_","1"), Action.Click);
							//isEventSuccessful = PerformAction(dicOR.get("btnSelectAny_DevicesPage").replace("buttonName","Connect"), Action.Click);
							connectBtn = dicOR.get("btnSelectAny_DevicesPage").replace("buttonName","Connect");
						}

					}
					//else if (dicCommon["BrowserName"].ToLower().Equals("ie"))
					//    connectBtn = "css=.card-detail-link";
					else
					{
						connectBtn = dicOR.get("btnConnectGridView").replace("__INDEX__", "1");
					}
					break;

				case "version":
					if (strView.toLowerCase().equals("list"))
					{
						connectBtn = "(//td[@title='" + strValue + "'])[1]/following-sibling::td[2]/div/button[1]";
					}
					else
					{
						connectBtn = "//p[@title='" + strValue + "']/following-sibling::div/div/button[1]";
					}
					break;

				case "devicename":
					if (strView.toLowerCase().equals("list"))
					{
						connectBtn = "//td[@title='" + strValue + "']/a/../following-sibling::td[5]/div/button[1]";
					}
					else
					{
						connectBtn = "//a[@class='card-detail-link' and text()='" + strValue + "']/../following-sibling::div/div/button[1]";
					}
					break;
				}

				//Click on the connect button and check if launch application dialog is opened
				isEventSuccessful = PerformAction(connectBtn, Action.WaitForElement, "2");
				if (isEventSuccessful)
				{
					isEventSuccessful = PerformAction(connectBtn, Action.Click);
					if (isEventSuccessful)
					{
						isEventSuccessful = PerformAction("appConnectList", Action.WaitForElement, "10");
						if (!isEventSuccessful)
						{
							throw new RuntimeException("App list not displayed after clicking on 'Connect' button for device with options: " + strDeviceOption + " " + strValue + ".");
						}
					}
					else
					{
						throw new RuntimeException("Unable to click on 'Connect' button for device with options: " + strDeviceOption + " " + strValue + ".");
					}
				}
				else
				{
					throw new RuntimeException("Unable to find 'Connect' button for device with options: " + strDeviceOption + " " + strValue + ".");
				}
			}
			else
			{
				throw new RuntimeException("Devices page is not displayed");
			}
		}
		catch (RuntimeException e)
		{
			isEventSuccessful = false;
			strErrMsg_AppLib = "OpenLaunchAppDialog---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return isEventSuccessful;
	}


	//This function returns true if the text on details page matches the Value passed as parameter. Also, it returns true if actual state of Item(Button) matches the 'Enabled' parameter value.
	//<p><I>Parameter "Enabled" takes two values : "true" or "false"  , Value = null if only existence of label is to be verified.</I></p> 
	//<p>Pre-Requisite : Need to click on Show Details Link before calling the function</p>
	//
	//<!--Modified By : Vinita Mahajan-->
	//@param Item In case some text value is to be verified, Item is name displayed on the details page eg. Serial Number
	//Otherwise, it is the object(Button) whose state is to be verified to be Enabled or Disabled 
	//ItemTextOnPage : Battert Status
	//@param Value In case of text verification, it is the text to be verified against the Item, otherwise its value should be "" .
	//@param Enabled In case of verifying state of button, "true" or "false" should be passed, otherwise, it can be left.
	//@return true or false
	//

	public final boolean VerifyOnDeviceDetailsPage(String ItemTextOnPage)
	{
		return VerifyOnDeviceDetailsPage(ItemTextOnPage, "");
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool VerifyOnDeviceDetailsPage(String ItemTextOnPage, String Value = "")
	public final boolean VerifyOnDeviceDetailsPage(String ItemTextOnPage, String Value)
	{
		System.out.println("");
		strErrMsg_AppLib = "";
		boolean flag = false;
		String SendItemParentIdentification = dicOR.get("elelabel_anypage").replace("__VALUE__", ItemTextOnPage);
		String actualValue = "";
		String ValueExpected = "";

		//Verifying devices are displayed
		try
		{
			/*if (GetTextOrValue("eleNoTableRowsWarning", "text").contains("deviceConnect currently has no configured devices or your filter produced no results."))
			{
				strErrMsg_AppLib = "VerifyOnDeviceDetailsPage---" + "deviceConnect currently has no configured devices or your filter produced no results.";
				return false;
			}*/
			switch (ItemTextOnPage)
			{
			case "Name":
			case "Model Name":
			case "Platform":
				if (!(Value.isEmpty())) //  to verify that the value in front of label is same as which is passed in th function
				{
					if (ItemTextOnPage.equals("Name"))
						actualValue = GetTextOrValue(dicOR.get("lblDeviceName_DeviceDetails"), "text");
					else
						actualValue = GetTextOrValue(dicOR.get("eleDeviceModel"), "text");
				}
				else
					flag = !GetTextOrValue("deviceName_detailsPage", "text").equals("");
				break;

			case "Slot #":
			case "Idle timeout":
				SendItemParentIdentification = "(" + SendItemParentIdentification + ")[1]";
				flag = PerformAction(SendItemParentIdentification, Action.isDisplayed);
				if (flag)
				{
					String send = "(" + SendItemParentIdentification + "//..//span)[3]";
					flag = PerformAction(send, Action.Exist);
					if (flag)
					{
						if (!(Value.isEmpty())) //  to verify that the value in front of label is same as which is passed in th function
						{
							actualValue = GetTextOrValue("(" + SendItemParentIdentification + "//..//span)[3]", "text");
						}
						else
						{
							flag = !GetTextOrValue("" + SendItemParentIdentification + "/following::span", "text").equals("");
						}
					}
					else
					{
						strErrMsg_AppLib = "Object Label : " + ItemTextOnPage + "'s Value is not displayed.";
					}
				}
				else
				{
					strErrMsg_AppLib = "Object Label : " + ItemTextOnPage + " is not displayed.";
				}
				break;

			case "Vendor Name ":
			case "Status ":
				flag = PerformAction(SendItemParentIdentification, Action.isDisplayed);
				if (flag)
				{
					if (!(Value.isEmpty())) //  to verify that the value in front of label is same as which is passed in th function
					{
						flag = PerformAction(("//span[contains(normalize-space(text()),'__text__')]").replace("__text__", Value), Action.isDisplayed);
						actualValue = Value;
					}
					else
					{
						flag = !GetTextOrValue("" + SendItemParentIdentification + "/following::span", "text").equals("");
					}
				}
				else
				{
					strErrMsg_AppLib = "Object Label : " + ItemTextOnPage + " is not displayed.";
				}
				break;

			case "Offline Since":
			case "In Use Since":
			case "Online Since":
			case "Model #":
			case "Serial Number":
			case "Disk Usage ":
				flag = PerformAction(SendItemParentIdentification, Action.isDisplayed);
				if (flag)
				{
					if (!(Value.isEmpty())) //  to verify that the value in front of label is same as which is passed in th function
					{
						flag = PerformAction(("//span[contains(normalize-space(text()),'__text__')]").replace("__text__", Value), Action.isDisplayed);
						actualValue = Value;
					}
					else
					{
						flag = !GetTextOrValue("" + SendItemParentIdentification + "/following::span", "text").equals("");
					}
				}
				else
				{
					strErrMsg_AppLib = "Object Label : " + ItemTextOnPage + " is not displayed.";
				}
				break;

			case "Next Reservation":
				SendItemParentIdentification = "//label[text()='" + ItemTextOnPage + "']";
				flag = PerformAction(SendItemParentIdentification, Action.isDisplayed);
				if (flag)
				{
					flag = PerformAction("" + SendItemParentIdentification + "//..//span", Action.isDisplayed);
					if (flag)
					{
						if (!Value.isEmpty()) //  to verify that the value in front of label is same as which is passed in th function
						{
							actualValue = GetTextOrValue("" + SendItemParentIdentification + "/following-sibling::span", "text");
						}
						else
						{
							flag = !GetTextOrValue("" + SendItemParentIdentification + "/following-sibling::span", "text").equals("");
						}
					}
					else
					{
						strErrMsg_AppLib = "Object Label : " + ItemTextOnPage + "'s Value is not displayed.";
					}
				}
				else
				{
					strErrMsg_AppLib = "Object Label : " + ItemTextOnPage + " is not displayed.";
				}
				break;

			case "Battery Status":
				if (!(Value.isEmpty()))
				{
					Object[] StatusValues = GetBatteryStatus();
					/*flag = (boolean)GetBatteryStatus()[0];
                    	String StatusValues = (String) GetBatteryStatus()[1];*/
					flag = (boolean)StatusValues[0];
					if (flag)
					{
						actualValue = StatusValues[1].toString() ;

						String t = actualValue.split(" ")[3];
						actualValue = t.replace("(", "").replace(")","");
						// Battery Charge: 97% (Charging)
						dicOutput.put("BatteryStatusText", actualValue);
					}
					else
					{
						//No need to do anything as the strErrMsg_AppLib is modified in GetBatteryStatus already
					}
				}
				else
				{
					//TODO OWN: to be implemented - implement GetBatteryStatus() first
					/*Object StatusValues = GetBatteryStatus();
                        if (StatusValues.Item1 == false || (StatusValues.Item2).isEmpty())
                        {
                            strErrMsg_AppLib = strErrMsg_AppLib + "Object Value : " + ItemTextOnPage + "'s Value is not displayed.";
                        }
                        if (dicOutput.containsKey("BatteryStatusText"))
                        {
                            dicOutput.remove("BatteryStatusText");
                        }
                        dicOutput.put("BatteryStatusText", (StatusValues.Item2).isEmpty());
                        return StatusValues.Item1;*/
				}
				break;
			}
			//----------------------------------------------------------------------------------------------------------//
			// COMPARING ACTUAL VALUE TO THE EXPECTED VALUE
			if (Value.contains("||"))
			{
				if (Value.contains(actualValue))
				{
					flag = true;
				}
				else
				{
					throw new Exception("Actual value of " + ItemTextOnPage + " on device details page is: '" + actualValue + "' and not '" + Value + "'.");
				}
			}
			else if (Value.startsWith("CONTAINS__"))
			{
				ValueExpected = Value.split("CONTAINS__")[1];
				if (actualValue.contains(ValueExpected))
				{
					flag = true;
				}
				else
				{
					strErrMsg_AppLib = "Actual value of " + ItemTextOnPage + " on device details page is: '" + actualValue + "' and not '" + ValueExpected + "'.";
					flag = false;
				}
			}
			else
			{
				if (actualValue.equals(Value))
				{
					flag = true;
				}
				else
				{
					strErrMsg_AppLib = "Actual value of " + ItemTextOnPage + " on device details page is: '" + actualValue + "' and not '" + Value + "'.";
					flag = false;
				}
			}
			if (!flag)
			{
				strErrMsg_AppLib = "Value for '" + ItemTextOnPage + "' is empty.";
			}
			//----------------------------------------------------------------------------------------------------------//
			//if (Value == "")  // to verify that the value in front of the label is not blank on details page.
			//{
			//    if (ItemTextOnPage == ("Name"))
			//    {
			//        flag = !GetTextOrValue("deviceName_detailsPage", "text").Equals("");
			//    }
			//    else
			//    {
			//        flag = !GetTextOrValue("" + SendItemParentIdentification + "/following::span", "text").Equals("");
			//    }
		}
		catch (Exception e)
		{
			strErrMsg_AppLib = "VerifyOnDeviceDetailsPage---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			flag = false;
		}
		return flag;
	}

	//Returns the detail of device at given index in the given view(grid/list) as a string.
	//<p>*Note : Index is ignored in case we need to find devices count.</p>
	//
	//<!--Modified By : Vinita Mahajan-->
	//@param index index of the device for which detail is to be captured.
	//@param DetailName Detail which is to be found out. 
	//<p>Permitted values for DetailName - devicescount, devicename, devicereservation, devicemodel, deviceplatform, devicestatus</p>
	//@param sView View for which the detail is to be found out : take values - "list" or "grid".
	//@param status This parameter is only for finding the devices count of devices with a specific status like offline, disabled, available, In Use, pass these for status but not reserved.
	//@return It returns the required detail of device(s) in the form of string.
	//

	public final String GetDeviceDetailInGridAndListView(int index, String DetailToBeFound, String sView)
	{
		return GetDeviceDetailInGridAndListView(index, DetailToBeFound, sView, "all");
	}

	public final String GetDeviceDetailInGridAndListView(int index, String DetailToBeFound)
	{
		return GetDeviceDetailInGridAndListView(index, DetailToBeFound, "list", "all");
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public string GetDeviceDetailInGridAndListView(int index, string DetailToBeFound, string sView = "grid", string status = "all")
	public final String GetDeviceDetailInGridAndListView(int index, String DetailToBeFound, String sView, String status)
	{
		String DetailValue = "";
		strErrMsg_AppLib = "";
		WebElement element;
		String xpathDevicesHolder;
		boolean flag = false;

		if (sView.toLowerCase().equals("list"))
		{
			xpathDevicesHolder = dicOR.get("eleDevicesHolderListView");
		}
		else
		{
			xpathDevicesHolder = dicOR.get("eleDevicesHolderGridView");
		}

		//////// to find number of devices with a specific status in any view, list or grid
		if (DetailToBeFound.toLowerCase().contains("devicescount"))
		{
			int devicesCountTotal = 0;
			int devicesCount = 0;
			try
			{
				if (PerformAction("eleDevicesTab_Devices", Action.WaitForElement))
				{
					if (GetTextOrValue("eleNoTableRowsWarning", "text").contains("deviceConnect currently has no configured devices or your filter produced no results."))
					{
						throw new RuntimeException("deviceConnect currently has no configured devices or your filter produced no results.");
					}
					StopAutoRefresh(sView);

					//**Finding number of devices in grid or list view
					devicesCountTotal = getelementCount(xpathDevicesHolder); //GenericLibrary.driver.FindElements(By.XPath(xpathDevicesHolder)).Count;
					if (status.equals("all"))
					{
						return (new Integer(devicesCountTotal)).toString();
					}
					else
					{
						////flag = SelectFromFilterDropdowns("status", status, "devices", sView); //### check if it itself handles the view selected
						//devicesCount = getelementCount(xpathDevicesHolder);
						//return devicesCount.ToString();
						if (SelectFromFilterDropdowns("status", status, "devices", sView))
						{
							for (int i = 1; i <= devicesCountTotal; i++)
							{
								if (sView.toLowerCase().equals("grid"))
								{
									if (GetTextOrValue(dicOR.get("eleDeviceStatus_CardView").replace("__INDEX__", (new Integer(i)).toString()), "text").toLowerCase().startsWith(status.toLowerCase()))
									{
										devicesCount++;
									}
								}
								else if (sView.toLowerCase().equals("list"))
								{
									//if (SelectFromFilterDropdowns("status", status, "devices", sView))
									//{
									if (GetTextOrValue(dicOR.get("eleDeviceStatus_ListView").replace("__INDEX__", (new Integer(i)).toString()), "text").toLowerCase().startsWith(status.toLowerCase()))
									{
										devicesCount++;
									}
									//}
									//else
									//    throw new Exception("Status not selected.");
								}
							}
						}
						else
						{
							throw new RuntimeException("Status not selected.");
						}
						return (new Integer(devicesCount)).toString();
					}
				}
				else
				{
					throw new RuntimeException("Filters not selected");
				}
			}
			catch (RuntimeException e)
			{
				strErrMsg_AppLib = "GetDeviceDetailInGridAndListView---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
				return "0";
			}
		}

		// cases in which we need to find details of devices other than devicescount.
		else
		{
			try
			{
				if (PerformAction("eleDevicesTab_Devices", Action.WaitForElement))
				{
					/*if (GetTextOrValue("eleNoTableRowsWarning", "text").contains("deviceConnect currently has no configured devices or your filter produced no results."))
					{
						throw new RuntimeException("No devices are displayed with status - 'In Use'");
					}*/
					//StopAutoRefresh(sView);
					element = genericLibrary.GetElement(xpathDevicesHolder + "[" + index + "]");
					if (element != null)
					{
						switch (DetailToBeFound.toLowerCase())
						{
						case "devicename":
							if (sView.toLowerCase().equals("list"))
							{
								if (PerformAction(dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(index)).toString()), Action.Exist))
								{
									DetailValue = GetTextOrValue(dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(index)).toString()), "text");
								}
								else
								{
									strErrMsg_AppLib = DetailToBeFound + " is not displayed on devices page";
								}
							}
							else
							{
								if (PerformAction(dicOR.get("eleDeviceName_CardsView").replace("__INDEX__", (new Integer(index)).toString()), Action.Exist))
								{
									DetailValue = GetTextOrValue(dicOR.get("eleDeviceName_CardsView").replace("__INDEX__", (new Integer(index)).toString()), "text");
								}
								else
								{
									strErrMsg_AppLib = DetailToBeFound + " is not displayed on devices page";
								}
							}
							break;


						case "notes" :

							if (sView.toLowerCase().equals("list"))
							{
								if (PerformAction(dicOR.get("eleDeviceNotes_ListView").replace("__INDEX__", (new Integer(index+1)).toString()), Action.Exist))
								{
									DetailValue = GetTextOrValue(dicOR.get("eleDeviceNotes_ListView").replace("__INDEX__", (new Integer(index+1)).toString()), "text");
								}
								else
								{
									strErrMsg_AppLib = DetailToBeFound + " is not displayed on devices page";
								}
							}

							break ;


						case "devicereservation":
							if (sView.toLowerCase().equals("list"))
							{
								if (PerformAction(dicOR.get("eleDeviceReservation_ListView").replace("__INDEX__", (new Integer(index)).toString()), Action.Exist))
								{
									DetailValue = GetTextOrValue(dicOR.get("eleDeviceReservation_ListView").replace("__INDEX__", (new Integer(index)).toString()), "text");
								}
								else
								{
									strErrMsg_AppLib = DetailToBeFound + " is not displayed on devices page";
								}
							}
							else
							{
								if (PerformAction(dicOR.get("eleDeviceReservation_CardView").replace("__INDEX__", (new Integer(index)).toString()), Action.Exist))
								{
									DetailValue = GetTextOrValue(dicOR.get("eleDeviceReservation_CardView").replace("__INDEX__", (new Integer(index)).toString()), "text");
								}
								else
								{
									strErrMsg_AppLib = DetailToBeFound + " is not displayed on devices page";
								}
							}
							break;
						case "devicemodel":
							if (sView.toLowerCase().equals("list"))
							{
								if (PerformAction(dicOR.get("eleDeviceModel_ListView").replace("__INDEX__", (new Integer(index+1)).toString()), Action.Exist))
								{
									DetailValue = GetTextOrValue(dicOR.get("eleDeviceModel_ListView").replace("__INDEX__", (new Integer(index+1)).toString()), "text");
								}
								else
								{
									strErrMsg_AppLib = DetailToBeFound + " is not displayed on devices page";
								}
							}
							else
							{
								if (PerformAction(dicOR.get("eleDeviceModel_CardView").replace("__INDEX__", (new Integer(index)).toString()), Action.Exist))
								{
									DetailValue = GetTextOrValue(dicOR.get("eleDeviceModel_CardView").replace("__INDEX__", (new Integer(index)).toString()), "text");
								}
								else
								{
									strErrMsg_AppLib = DetailToBeFound + " is not displayed on devices page";
								}
							}
							break;
						case "deviceplatform":
							if (sView.toLowerCase().equals("list"))
							{
								if (PerformAction(dicOR.get("eleDevicePlatform_ListView").replace("__INDEX__", (new Integer(index+1)).toString()), Action.Exist))
								{
									DetailValue = GetTextOrValue(dicOR.get("eleDevicePlatform_ListView").replace("__INDEX__", (new Integer(index+1)).toString()), "text");
								}
								else
								{
									strErrMsg_AppLib = DetailToBeFound + " is not displayed on devices page";
								}
							}
							else
							{
								if (PerformAction(dicOR.get("eleDevicePlatform_CardView").replace("__INDEX__", (new Integer(index)).toString()), Action.Exist))
								{
									DetailValue = GetTextOrValue(dicOR.get("eleDevicePlatform_CardView").replace("__INDEX__", (new Integer(index)).toString()), "text");
								}
								else
								{
									strErrMsg_AppLib = DetailToBeFound + " is not displayed on devices page";
								}
							}
							break;
						case "devicestatus":
							if (sView.toLowerCase().equals("list"))
							{
								if (PerformAction(dicOR.get("eleDeviceStatus_ListView").replace("__INDEX__", (new Integer(index)).toString()), Action.Exist))
								{
									DetailValue = GetTextOrValue(dicOR.get("eleDeviceStatus_ListView").replace("__INDEX__", (new Integer(index)).toString()), "text");
								}
								else
								{
									strErrMsg_AppLib = DetailToBeFound + " is not displayed on devices page";
								}
							}
							else
							{
								if (PerformAction(dicOR.get("eleDeviceStatus_CardView").replace("__INDEX__", (new Integer(index)).toString()), Action.Exist))
								{
									DetailValue = GetTextOrValue(dicOR.get("eleDeviceStatus_CardView").replace("__INDEX__", (new Integer(index)).toString()), "text");
								}
								else
								{
									strErrMsg_AppLib = DetailToBeFound + " is not displayed on devices page";
								}
							}
							break;
						}
					}
					else
					{
						throw new RuntimeException("Element " + xpathDevicesHolder + "[" + index + "] is not found in page.");
					}
				}
				else
				{
					throw new RuntimeException("Devices page is not displayed");
				}
			}
			catch (RuntimeException e)
			{
				strErrMsg_AppLib = "GetDeviceDetailInGridAndListView---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return DetailValue;
		}
	}

	// Old implementation
	//public string GetDeviceDetailInGridAndListView(int index, string DetailName, string sView = "grid", string status = "all")
	//{
	//    string DetailValue = "";
	//    strErrMsg_AppLib = "";
	//    WebElement element;
	//    string xpathDevicesHolder;


	//    if (sView.ToLower() == "list")
	//        xpathDevicesHolder = "//table[contains(@class,'table')]//tbody/tr";

	//    else
	//        xpathDevicesHolder = "//ul[@class='cards-layout']/li";


	//    //////// to find number of devices with a specific status in any view, list or grid
	//    if (DetailName.ToLower().contains("devicescount"))
	//    {
	//        int devicesCountTotal = 0;
	//        int devicesCount = 0;
	//        try
	//        {
	//            if (PerformAction("eleDevicesHeader", Action.WaitForElement))
	//            {
	//                if (GetTextOrValue("eleNoTableRowsWarning", "text").contains("deviceConnect currently has no configured devices or your filter produced no results."))
	//                    throw new Exception("deviceConnect currently has no configured devices or your filter produced no results.");
	//                StopAutoRefresh(sView);

	//                //**Finding number of devices in grid or list view
	//                //if (sView.ToLower().Equals("grid"))
	//                    devicesCountTotal = GenericLibrary.driver.FindElements(By.XPath(xpathDevicesHolder)).Count;
	//                //else if (sView.ToLower().Equals("list"))
	//                //    devicesCountTotal = GenericLibrary.driver.FindElements(By.XPath(xpathDevicesHolder)).Count - 1;

	//                if (!status.Equals("all"))


	//                {
	//                    for (int i = 1; i <= devicesCountTotal; i++)
	//                    {
	//                        if (sView.ToLower().Equals("grid"))
	//                        {
	//                            if ((GetTextOrValue(xpathDevicesHolder + "[" + i + "]//div[@class='location spec']", "text")).ToLower().Equals(status.ToLower()))
	//                                devicesCount++;
	//                        }
	//                        else if (sView.ToLower().Equals("list"))
	//                        {
	//                            if ((GetTextOrValue(xpathDevicesHolder + "[" + i + "]/td[1]", "text")).ToLower().Equals(status))
	//                                devicesCount++;
	//                        }
	//                    }


	//                    return devicesCount.ToString();
	//                }
	//                else
	//                    return devicesCountTotal.ToString();



















	//            }
	//            else
	//                throw new Exception("Devices page is not displayed");
	//        }
	//        catch (Exception e)
	//        {
	//            strErrMsg_AppLib = e.Message;
	//            return "0";
	//        }

	//    }


	//    else
	//    {
	//        try
	//        {
	//            if (PerformAction("eleDevicesHeader", Action.WaitForElement))
	//            {
	//                if (GetTextOrValue("eleNoTableRowsWarning", "text").contains("deviceConnect currently has no configured devices or your filter produced no results."))
	//                    throw new Exception("No devices are displayed with status - 'In Use'");
	//                StopAutoRefresh(sView);
	//                element = genericLibrary.GetElement(xpathDevicesHolder + "[" + index + "]");
	//                if (element != null)
	//                {
	//                    switch (DetailName.ToLower())
	//                    {
	//                        case "devicename":
	//                            if (sView.ToLower() == "list")
	//                            {
	//                                if (PerformAction(xpathDevicesHolder + "[" + index + "]/td[2]/a", Action.Exist))
	//                                    //ul[@class='cards-layout']/li[1]/div/div/a
	//                                    DetailValue = GetTextOrValue(xpathDevicesHolder + "[" + index + "]/td[2]/a", "text");


	//                                else
	//                                    strErrMsg_AppLib = DetailName + " is not displayed on devices page";
	//                            }
	//                            else
	//                            {
	//                                if (PerformAction(xpathDevicesHolder + "[" + index + "]/div/div", Action.Exist))
	//                                    DetailValue = GetTextOrValue(xpathDevicesHolder + "[" + index + "]/div/div/a", "text");


	//                                else
	//                                    strErrMsg_AppLib = DetailName + " is not displayed on devices page";
	//                            }
	//                            break;
	//                        case "devicemodel":
	//                            if (sView.ToLower() == "list")
	//                            {
	//                                if (PerformAction(xpathDevicesHolder + "[" + index + "]/td[3]", Action.Exist))
	//                                    DetailValue = GetTextOrValue(xpathDevicesHolder + "[" + index + "]/td[3]", "text");


	//                                else
	//                                    strErrMsg_AppLib = DetailName + " is not displayed on devices page";



	//                            }
	//                            else
	//                            {
	//                                if (PerformAction(xpathDevicesHolder + "[" + index + "]//div[@class='hardware spec']", Action.Exist))
	//                                    DetailValue = GetTextOrValue(xpathDevicesHolder + "[" + index + "]//div[@class='hardware spec']", "text");


	//                                else
	//                                    strErrMsg_AppLib = DetailName + " is not displayed on devices page";
	//                            }
	//                            break;
	//                        case "deviceplatform":
	//                            if (sView.ToLower() == "list")
	//                            {
	//                                if (PerformAction(xpathDevicesHolder + "[" + index + "]/td[4]", Action.Exist))
	//                                    DetailValue = GetTextOrValue(xpathDevicesHolder + "[" + index + "]/td[4]", "text");


	//                                else
	//                                    strErrMsg_AppLib = DetailName + " is not displayed on devices page";
	//                            }
	//                            else
	//                            {
	//                                if (PerformAction(xpathDevicesHolder + "[" + index + "]//div[@class='platform spec']", Action.Exist))
	//                                    DetailValue = GetTextOrValue(xpathDevicesHolder + "[" + index + "]//div[@class='platform spec']", "text");


	//                                else
	//                                    strErrMsg_AppLib = DetailName + " is not displayed on devices page";
	//                            }
	//                            break;
	//                        case "devicestatus":
	//                            if (sView.ToLower() == "list")
	//                            {
	//                                if (PerformAction(xpathDevicesHolder + "[" + index + "]/td[1]", Action.Exist))
	//                                    DetailValue = GetTextOrValue(xpathDevicesHolder + "[" + index + "]/td[1]", "text");


	//                                else
	//                                    strErrMsg_AppLib = DetailName + " is not displayed on devices page";
	//                            }
	//                            else
	//                            {
	//                                if (PerformAction(xpathDevicesHolder + "[" + index + "]//div[@class='location spec']", Action.Exist))
	//                                    DetailValue = GetTextOrValue(xpathDevicesHolder + "[" + index + "]//div[@class='location spec']", "text");


	//                                else
	//                                    strErrMsg_AppLib = DetailName + " is not displayed on devices page";
	//                            }
	//                            break;
	//                    }
	//                }
	//                else
	//                    throw new Exception("Element " + xpathDevicesHolder + "[" + index + "] is not found in page.");
	//            }
	//            else
	//                throw new Exception("Devices page is not displayed");
	//        }
	//        catch (Exception e)
	//        {
	//            strErrMsg_AppLib = e.Message;
	//        }
	//        return DetailValue;
	//    }
	//}


	//Verifies on devices page that the sVerificationObjectName has the same value/text as sVerificationObjectValue in grid/list view
	//
	//<!--Modified By : Vinita Mahajan-->
	//<!--Last updated : 10/2/2015 by Mandeep Kaur , Modifying Connect case, changed loop scope to i<noOfDevices because table header is also getting in the fetched list-->
	//@param sVerificationObjectName It takes values: connect, statusicon, devicename, devicemodel, deviceplatform, devicestatus, existence of status
	//<p>***NOTE : Use "devicestatus" instead of "statusicon" when it is required to verify the status of the device in text</p>
	//<p>Use "statusicon" in case it is required to verify the status icon</p>
	//@param sVerificationObjectValue It takes values : enable, disable(for sVerificationObjectName = connect), "link" (for devicename), any value (for devicemodel), any value (for deviceplatform), Available, Disabled, Offline, In Use (for existenceofstatus)
	//@param sView It is the view for which verification is to be done. It takes values : "grid", "list", "both"
	//@return Returns true or false if the verification passes or fails respectively.
	//

	public final boolean VerifyDeviceDetailsInGridAndListView(String sVerificationObjectName, String sVerificationObjectValue)
	{
		return VerifyDeviceDetailsInGridAndListView(sVerificationObjectName, sVerificationObjectValue, "list");
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool VerifyDeviceDetailsInGridAndListView(string sVerificationObjectName, string sVerificationObjectValue, string sView = "grid")
	public final boolean VerifyDeviceDetailsInGridAndListView(String sVerificationObjectName, String sVerificationObjectValue, String sView)
	{
		//StopAutoRefresh(sView);
		String hrefValue = "";
		strErrMsg_AppLib = "";
		boolean isEventSuccessful = false, loopEntered = false;
		WebElement element, childElement = null;
		String xpathDevicesHolder = "", ElementProperty = "", strDeviceName = "", classname = "", text = "", srcAttribute = "";

		try
		{
			if (PerformAction("eleDevicesTab_Devices", Action.WaitForElement))
			{
				if (sView.toLowerCase().equals("list"))
				{
					xpathDevicesHolder = dicOR.get("eleDevicesHolderListView");
				}
				else if (sView.toLowerCase().equals("grid"))
				{
					xpathDevicesHolder = dicOR.get("eleDevicesHolderGridView");
				}

				// verification in case user wants to verify on both grid and list view
				else if (sView.toLowerCase().equals("both"))
				{
					String strErr = "";
					//First check for grid view
					isEventSuccessful = VerifyDeviceDetailsInGridAndListView(sVerificationObjectName, sVerificationObjectValue, "grid");
					if (!isEventSuccessful)
					{
						strErr = strErrMsg_AppLib;
					}
					// now open list view and verify for list view
					isEventSuccessful = PerformAction("lnkListView", Action.Click);
					if (isEventSuccessful)
					{
						isEventSuccessful = VerifyDeviceDetailsInGridAndListView(sVerificationObjectName, sVerificationObjectValue, "list");
						if (!isEventSuccessful)
						{
							strErr = strErr + strErrMsg_AppLib;
						}
					}
					else
					{
						strErr = strErrMsg_AppLib + "Could not click on icon for List view.";
					}
					if (strErr.equals(""))
					{
						return true;
					}
					else
					{
						strErrMsg_AppLib = strErr;
						return false;
					}
				}
				/////////////////## Verification for "both" ends. ##///////////////////////

				///#################### Verification for Specifically 'Grid' or 'list' view ###################//

				//Verifying devices are displayed
				if (PerformAction("eleNoDevicesWarning_Devices", Action.isDisplayed))
				{
					throw new RuntimeException("deviceConnect currently has no configured devices or your filter produced no results.");
				}
				//StopAutoRefresh(sView);
				int noOfDevices = getelementCount(xpathDevicesHolder) - 1; // Get number of devices' rows . '-1' because it counts the header also.

				for (int i = 1; i <= noOfDevices; i++)
				{
					loopEntered = true;
					element = GetElement(xpathDevicesHolder + "[" + i + "]");
					if (element != null)
					{
						if (sView.toLowerCase().equals("list"))
						{
							strDeviceName = GetTextOrValue(dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(i)).toString()), "text");
						}
						else
						{
							strDeviceName = GetTextOrValue(dicOR.get("eleDeviceName_CardsView").replace("__INDEX__", (new Integer(i)).toString()), "text");
						}

						// Now verify the value of the element required using different cases
						switch (sVerificationObjectName.toLowerCase())
						{
						case "connect": // Verification for checking if Connect button is enabled or disabled

							//Getting the xpath of connect button for i'th device in list/grid view
							//if (sView.ToLower() == "list")
							//    ElementProperty = dicOR["btnConnect_ListView"] + "[" + i + "]";
							//else
							//    ElementProperty = dicOR["btnConnectGridView"].Replace("__INDEX__", i.ToString());

							//childElement = GetElement(ElementProperty);
							//if (childElement != null && PerformAction(ElementProperty, Action.isDisplayed))
							//{
							//classname = getAttribute(ElementProperty, "class");// childElement.GetAttribute("class");
							// checking button is not disabled when it should be enabled.
							if (sVerificationObjectValue.toLowerCase().contains("enable") && !(PerformAction(dicOR.get("btnConnectDisabled_ListView") + "[" + i + "]", Action.isNotDisplayed) && PerformAction(dicOR.get("btnConnect_ListView") + "[" + i + "]", Action.isDisplayed))) // If we need to check that the connect button is enabled and disabled connect button is displayed, then the condition is fail for that device
							{
								strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
							}
							//checking button is not enabled when it should be disabled.
							else if (sVerificationObjectValue.toLowerCase().contains("disable") && (!PerformAction(dicOR.get("btnConnectDisabled_ListView") + "[" + i + "]", Action.isDisplayed))) // If we need to check that the connect button is disabled and disabled connect button is not displayed, then the condition is fail for that device
							{
								strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
							}
							//}
							//else
							//    strErrMsg_AppLib = strErrMsg_AppLib + "; Connect button is not found for device " + i.ToString() + " in devices list view.";
							break;

						case "statusicon":
							if (sView.toLowerCase().equals("list"))
							{
								ElementProperty = dicOR.get("eleStatusIcon_ListView").replace("__INDEX__", (new Integer(i + 1)).toString());
								srcAttribute = getAttribute(ElementProperty, "class");
							}
							else
							{
								ElementProperty = dicOR.get("eleStatusIcon_CardView").replace("__INDEX__", (new Integer(i + 1)).toString());
								srcAttribute = getAttribute(ElementProperty, "class");
							}
							if (PerformAction(ElementProperty, Action.isDisplayed))
							{
								if (sVerificationObjectValue.toLowerCase().contains("available") || sVerificationObjectValue.toLowerCase().contains("green"))
								{
									if (!srcAttribute.contains("dc-icon-device-status-available status-icon"))
									{
										strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
									}
								}
								else if (sVerificationObjectValue.toLowerCase().contains("offline") || sVerificationObjectValue.toLowerCase().contains("grey"))
								{
									if (!srcAttribute.contains("dc-icon-device-status-offline status-icon"))
									{
										strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
									}
								}
								else if (sVerificationObjectValue.toLowerCase().contains("in use") || sVerificationObjectValue.toLowerCase().contains("red"))
								{
									if (!srcAttribute.contains("dc-icon-device-status-in-use status-icon"))
									{
										strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
									}
								}
								else if (sVerificationObjectValue.toLowerCase().contains("disabled") || sVerificationObjectValue.toLowerCase().contains("red-cross"))
								{
									if (!srcAttribute.contains("dc-icon-device-status-disabled status-icon"))
									{
										strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
									}
								}
							}
							else
							{
								strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", "; // Here it puts name of device if Status icon is not displayed(But this case is not displaye in the HTML report.
							}
							break;

						case "devicename":
							if (sVerificationObjectValue.toLowerCase().contains("link"))
							{
								if (sView.toLowerCase().equals("list"))
								{
									hrefValue = getAttribute(dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(i)).toString()), "href");
									if ( ! hrefValue.equals(""))
									{
										if (!GetElement(dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(i)).toString())).isEnabled())
										{
											strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
										}
									}
								}
								else
								{
									if (!GetElement(dicOR.get("eleDeviceName_CardsView").replace("__INDEX__", (new Integer(i)).toString())).isEnabled())
									{
										strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
									}
								}
							}
							break;

						case "devicemodel":
							if (sView.toLowerCase().equals("list"))
							{
								if (PerformAction(dicOR.get("eleDeviceModel_ListView").replace("__INDEX__", (new Integer(i + 1)).toString()), Action.isNotDisplayed))
								{
									strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
								}
							}
							else
							{
								if (!PerformAction(dicOR.get("eleDeviceModel_CardView").replace("__INDEX__", (new Integer(i)).toString()), Action.isDisplayed))
								{
									strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
								}
							}
							break;

						case "deviceplatform":
							if (sView.toLowerCase().equals("list"))
							{
								childElement = GetElement(ElementProperty = (dicOR.get("eleDevicePlatform_ListView").replace("__INDEX__", (new Integer(i + 1)).toString())));
							}
							else
							{
								childElement = GetElement(ElementProperty = (dicOR.get("eleDevicePlatform_CardView").replace("__INDEX__", (new Integer(i)).toString())));
							}
							if (i < noOfDevices) // this check is applied because only for this case 'deviceplatform', "__INDEX__" needs to be replaced with (i+1) instead of i. so if this check is not there then error will be thrown.
							{
								if (PerformAction(ElementProperty, Action.isDisplayed))
								{
									text = GetTextOrValue(ElementProperty, "text");
									if ( ! sVerificationObjectValue.equals("") && !text.toLowerCase().contains(sVerificationObjectValue.toLowerCase()))
									{
										strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
									}
								}
								else
								{
									strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + "(Not displayed), ";
								}
							}
							break;

						case "devicestatus":
							sVerificationObjectValue.split(",");
							if (sView.toLowerCase().equals("list"))
							{
								if (!(GetElement(dicOR.get("eleDeviceStatus_ListView") + "[" + i + "]").getText().toLowerCase().startsWith(sVerificationObjectValue.toLowerCase()) || GetElement(dicOR.get("eleDeviceStatus_ListView") + "[" + i + "]").getText().toLowerCase().startsWith("Reserved".toLowerCase()) ))
								{
									strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
								}
							}
							else
							{
								if (!(GetElement(dicOR.get("eleDeviceStatus_CardView").replace("__INDEX__", (new Integer(i)).toString())).getText().toLowerCase().startsWith(sVerificationObjectValue.toLowerCase())))
								{
									strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
								}
							}
							break;

						case "deviceuser":
							if (sView.toLowerCase().equals("list"))
							{
								if (!GetElement(dicOR.get("eleDeviceStatus_ListView").replace("__INDEX__", (new Integer(i)).toString())).getText().toLowerCase().endsWith("(" + sVerificationObjectValue.toLowerCase() + ")"))
								{
									strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
								}
							}
							else
							{
								if (!(GetElement(dicOR.get("eleDeviceStatus_CardView").replace("__INDEX__", (new Integer(i)).toString())).getText().toLowerCase().endsWith("(" + sVerificationObjectValue.toLowerCase() + ")")))
								{
									strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
								}
							}
							break;


						case "devicereservation":
							if (sView.toLowerCase().equals("list"))
							{
								if (sVerificationObjectValue.toLowerCase().equals("present"))
								{
									if ((GetElement(dicOR.get("eleDeviceReservation_ListView").replace("__INDEX__", (new Integer(i)).toString())).getText().toLowerCase()).equals("-")) //StartsWith(sVerificationObjectValue.ToLower())))
									{
										strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
									}
								}
								else
								{
									if ( ! (GetElement(dicOR.get("eleDeviceReservation_ListView").replace("__INDEX__", (new Integer(i)).toString())).getText().toLowerCase()).equals("-")) //StartsWith(sVerificationObjectValue.ToLower())))
									{
										strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
									}
								}
							}
							else
							{
								if (sVerificationObjectValue.toLowerCase().equals("present"))
								{
									if ((GetElement(dicOR.get("eleDeviceReservation_CardView").replace("__INDEX__", (new Integer(i)).toString())).getText().toLowerCase()).equals("-"))
									{
										strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
									}
								}
								else
								{
									if ( ! (GetElement(dicOR.get("eleDeviceReservation_CardView").replace("__INDEX__", (new Integer(i)).toString())).getText().toLowerCase()).equals("-"))
									{
										strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
									}
								}
							}
							break;

						case "existenceofstatus": // This case checks if the given status's device exists on the UI or not, the moment it finds one, case breaks and returns "True"
							isEventSuccessful = false;
							strErrMsg_AppLib = "";
							if (sView.toLowerCase().equals("list"))
							{
								if (GetTextOrValue(dicOR.get("eleDeviceStatus_ListView") + "[" + i + "]", "text").toLowerCase().startsWith(sVerificationObjectValue.toLowerCase()))
								{
									return true;
								}
								//if (!driver.findElement(By.xpath(xpathDevicesHolder + "[" + i + "]/td[1]")).getText().ToLower().contains(sVerificationObjectValue))
								//    return true;
							}
							else
							{
								if (GetTextOrValue(dicOR.get("eleDeviceStatus_CardView").replace("__INDEX__", (new Integer(i)).toString()), "text").toLowerCase().startsWith(sVerificationObjectValue.toLowerCase()))
								{
									return true;
								}
								//if (!driver.FindElement(By.XPath(xpathDevicesHolder + "[" + i + "]//div[@class='location spec']")).getText().ToLower().contains(sVerificationObjectValue))
								//    return true;
							}
							if (!isEventSuccessful)
							{
								strErrMsg_AppLib = "'" + sVerificationObjectValue + "' is not present on  the UI.";
							}
							break;

						case "existenceofplatform": // This case checks if the given platform device exists on the UI or not, the moment it finds one, case breaks and returns "True"
							isEventSuccessful = false;
							strErrMsg_AppLib = "";
							if (sView.toLowerCase().equals("list"))
							{
								if (GetTextOrValue(dicOR.get("eleDevicePlatform_ListView").replace("__INDEX__", (new Integer(i+1)).toString()), "text").toLowerCase().startsWith(sVerificationObjectValue.toLowerCase()))
								{
									return true;
								}
								//if (!driver.FindElement(By.XPath(xpathDevicesHolder + "[" + i + "]/td[1]")).getText().ToLower().contains(sVerificationObjectValue))
								//    return true;
							}
							else
							{
								if (GetTextOrValue(dicOR.get("eleDevicePlatform_CardView").replace("__INDEX__", (new Integer(i)).toString()), "text").toLowerCase().startsWith(sVerificationObjectValue.toLowerCase()))
								{
									return true;
								}
								//if (!driver.FindElement(By.XPath(xpathDevicesHolder + "[" + i + "]//div[@class='location spec']")).getText().ToLower().contains(sVerificationObjectValue))
								//    return true;
							}
							if (!isEventSuccessful)
							{
								strErrMsg_AppLib = "'" + sVerificationObjectValue + "' is not present on  the UI.";
							}
							break;
						}
					}
					else
					{
						throw new RuntimeException("Element " + xpathDevicesHolder + "[" + i + "] is not found in page.");
					}
				}
				// Final check if the validatio was pass or not.
				if (loopEntered) // If loop is entered then check if the error message is empty otherwise throw error. If loop is not entered then throw error that no devices match the xpath
				{
					if (strErrMsg_AppLib.equals(""))
					{
						isEventSuccessful = true;
					}
					else
					{
						throw new RuntimeException("Following devices' " + sVerificationObjectName + " is not displayed or is not correct: <br/>" + sVerificationObjectValue + " - " + strErrMsg_AppLib);
					}
				}
				else
				{
					throw new RuntimeException("No devices found matching the xpath : '" + xpathDevicesHolder + "'.");
				}
			}
			else // if devices page is not displayed then throw exception and return with flag set to false.
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "VerifyDeviceDetailsInGridAndListView--" + "Devices page is not displayed";
				return isEventSuccessful;
			}
		}
		catch (RuntimeException e)
		{
			isEventSuccessful = false;
			strErrMsg_AppLib = "VerifyDeviceDetailsInGridAndListView--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return isEventSuccessful;
	}

	//Old implementation
	//public bool VerifyDeviceDetailsInGridAndListView(string sVerificationObjectName, string sVerificationObjectValue, string sView = "grid")
	//{
	//        //StopAutoRefresh(sView);
	//        strErrMsg_AppLib = "";
	//        bool isEventSuccessful = false;
	//        WebElement element, childElement = null;
	//        string xpathDevicesHolder = "", ElementProperty = "", strDeviceName = "", classname = "", text = "", srcAttribute= "";
	//        if (PerformAction("eleDevicesHeader", Action.WaitForElement))
	//        {
	//            if (sView.ToLower() == "list")
	//                xpathDevicesHolder = "//table[contains(@class,'table')]//tbody/tr";
	//            else if (sView.ToLower() == "grid")
	//                xpathDevicesHolder = "//ul[@class='cards-layout']/li";
	//            else if (sView.ToLower() == "both")
	//            {
	//                string strErr = "";
	//                isEventSuccessful = VerifyDeviceDetailsInGridAndListView(sVerificationObjectName, sVerificationObjectValue, "grid");
	//                if (!isEventSuccessful)
	//                    strErr = strErrMsg_AppLib;
	//                isEventSuccessful = PerformAction("lnkListView", Action.Click);
	//                if (isEventSuccessful)
	//                {
	//                    isEventSuccessful = VerifyDeviceDetailsInGridAndListView(sVerificationObjectName, sVerificationObjectValue, "list");
	//                    if (!isEventSuccessful)
	//                        strErr = strErr + strErrMsg_AppLib;
	//                }
	//                else
	//                {
	//                    strErr = strErrMsg_AppLib + "Could not click on icon for List view.";
	//                }
	//                if (strErr == "")
	//                    return true;
	//                else
	//                {
	//                    strErrMsg_AppLib = strErr;
	//                    return false;
	//                }
	//            }
	//            try
	//            {
	//                //Verifying devices are displayed
	//                if (GetTextOrValue("eleNoTableRowsWarning", "text").contains("deviceConnect currently has no configured devices or your filter produced no results."))
	//                    throw new Exception("deviceConnect currently has no configured devices or your filter produced no results.");
	//                //StopAutoRefresh(sView);
	//                int noOfDevices = driver.FindElements(By.XPath(xpathDevicesHolder)).Count;
	//                //if (sView.ToLower() == "list")
	//                //    noOfDevices = noOfDevices - 1; // ** one <tr>tag(row) is extra only in list view, so actual number of devices is 1 less than the tr tag count.
	//                for (int i = 1; i <= noOfDevices; i++)
	//                {
	//                    element = GetElement(xpathDevicesHolder + "[" + i + "]");
	//                    if (element != null)
	//                    {
	//                        if (sView.ToLower() == "list")
	//                            strDeviceName = GetTextOrValue(dicOR["eleDeviceName_ListView"].Replace("__INDEX__", i.ToString()), "text");
	//                        else   //ul[@class='cards-layout']/li[1]//div/div/a
	//                            strDeviceName = GetTextOrValue(dicOR["eleDeviceName_CardsView"].Replace("__INDEX__", i.ToString()), "text");
	//                        //string strDeviceName = genericLibrary.GetElement(".//h4/a", element).getText();
	//                        switch (sVerificationObjectName.ToLower())
	//                        {
	//                            case "connect":
	//                                //Getting the connect button for i'th device
	//                                ElementProperty = dicOR["btnConnectGridView"].Replace("__INDEX__", i.ToString());
	//                                childElement = GetElement(ElementProperty);
	//                                if (childElement != null)
	//                                {
	//                                    classname = getAttribute(ElementProperty, "class");// childElement.GetAttribute("class");
	//                                    // checking button is not disabled when it should be enabled.
	//                                    if(sVerificationObjectValue.ToLower().contains("enable") && !((classname == "connect-btn btn btn-info btn-sm") ||(classname == "connect-btn btn btn-info btn-sm ")))
	//                                        strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
	//                                    //checking button is not enabled when it should be disabled.
	//                                    else if(sVerificationObjectValue.ToLower().contains("disable") && classname != "connect-btn btn btn-info btn-sm disabled")
	//                                        strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
	//                                }
	//                                else
	//                                    strErrMsg_AppLib = strErrMsg_AppLib + "; Connect button is not found for device " + i.ToString() + " in devices card view.";
	//                                break;

	//                            case "status":
	//                                string srcAttribute = getAttribute(dicOR["eleStatusIcon_CardView"].Replace("__INDEX__", "1"), "class");
	//                                if (sVerificationObjectValue.ToLower().contains("available") || sVerificationObjectValue.ToLower().contains("green"))
	//                                {
	//                                    if (!srcAttribute.contains("dc-icon-device-status-available"))
	//                                        strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
	//                                }
	//                                else if (sVerificationObjectValue.ToLower().contains("offline") || sVerificationObjectValue.ToLower().contains("grey"))
	//                                {
	//                                    if (!srcAttribute.contains("dc-icon-device-status-offline"))
	//                                        strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
	//                                }
	//                                else if (sVerificationObjectValue.ToLower().contains("inuse") || sVerificationObjectValue.ToLower().contains("red"))
	//                                {
	//                                    if (!srcAttribute.contains("dc-icon-device-status-in-use"))
	//                                        strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
	//                                }
	//                                else if (sVerificationObjectValue.ToLower().contains("disabled") || sVerificationObjectValue.ToLower().contains("red-cross"))
	//                                {
	//                                    if (!srcAttribute.contains("dc-icon-device-status-disabled"))
	//                                        strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
	//                                }
	//                                break;

	//                            case "devicename":
	//                                if (sVerificationObjectValue.ToLower().contains("link"))
	//                                {
	//                                    if (sView.ToLower() == "list")
	//                                    {
	//                                        if (!GetElement(dicOR["eleDeviceName_ListView"].Replace("__INDEX__", i.ToString())).Enabled)
	//                                            strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
	//                                    }
	//                                    else
	//                                    {
	//                                        if (!GetElement(dicOR["eleDeviceName_CardsView"].Replace("__INDEX__", i.ToString())).Enabled)
	//                                            strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";

	//                                    }
	//                                }
	//                                break;
	//                            case "devicemodel":
	//                                if (sView.ToLower() == "list")
	//                                {
	//                                    if (!PerformAction(dicOR["eleDeviceModel_ListView"].Replace("__INDEX__", i.ToString()), Action.isDisplayed))
	//                                        strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
	//                                }
	//                                else
	//                                {
	//                                    if(!PerformAction(dicOR["eleDeviceModel_CardView"].Replace("__INDEX__", i.ToString()), Action.isDisplayed))
	//                                        strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
	//                                }
	//                                break;
	//                            case "deviceplatform":
	//                                if (sView.ToLower() == "list")
	//                                    childElement = GetElement(ElementProperty = (dicOR["eleDevicePlatform_ListView"].Replace("__INDEX__", i.ToString())));
	//                                else
	//                                    childElement = GetElement(ElementProperty = (dicOR["eleDevicePlatform_CardView"].Replace("__INDEX__", i.ToString())));

	//                                if (PerformAction(ElementProperty, Action.isDisplayed))
	//                                {
	//                                    text = GetTextOrValue(ElementProperty, "text");
	//                                    if(sVerificationObjectValue != "" && !text.ToLower().contains(sVerificationObjectValue.ToLower()))
	//                                        strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
	//                                }
	//                                else
	//                                    strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + "(Not displayed), ";
	//                                break;

	//                            case "devicestatus":
	//                                if (sView.ToLower() == "list")
	//                                {
	//                                    if (!GetElement(dicOR["eleDeviceStatus_ListView"].Replace("__INDEX__", i.ToString())).getText().ToLower().contains(sVerificationObjectValue))
	//                                        strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
	//                                }
	//                                else
	//                                {
	//                                    if (!GetElement(dicOR["eleDeviceStatus_CardView"].Replace("__INDEX__", i.ToString())).getText().ToLower().contains(sVerificationObjectValue))
	//                                        strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
	//                                }
	//                                break;
	//                            case "existenceofstatus": // This case checks if the given status's device exists on the UI or not, the moment it finds one, case breaks and returns "True"
	//                                isEventSuccessful = false;
	//                                if (sView.ToLower() == "list")
	//                                {
	//                                    if (GetElement(dicOR["eleDeviceStatus_ListView"].Replace("__INDEX__", i.ToString())).getText().ToLower().contains(sVerificationObjectValue))
	//                                        return true;
	//                                    //if (!driver.FindElement(By.XPath(xpathDevicesHolder + "[" + i + "]/td[1]")).getText().ToLower().contains(sVerificationObjectValue))
	//                                    //    return true;
	//                                }
	//                                else
	//                                {
	//                                    if (GetElement(dicOR["eleDeviceStatus_CardView"].Replace("__INDEX__", i.ToString())).Text.ToLower().contains(sVerificationObjectValue))
	//                                        return true;
	//                                    //if (!driver.FindElement(By.XPath(xpathDevicesHolder + "[" + i + "]//div[@class='location spec']")).getText().ToLower().contains(sVerificationObjectValue))
	//                                    //    return true;
	//                                }
	//                                if (!isEventSuccessful)
	//                                    strErrMsg_AppLib = "'" + sVerificationObjectValue + "' is not present on  the UI.";                                    
	//                                    break;
	//                        }
	//                    }
	//                    else
	//                        throw new Exception("Element " + xpathDevicesHolder + "[" + i + "] is not found in page.");
	//                }
	//                if (strErrMsg_AppLib == "")
	//                    isEventSuccessful = true;
	//                else
	//                    throw new Exception("Following devices' " + sVerificationObjectName + " is not displayed or is not correct: <br/>" + sVerificationObjectValue + " - " + strErrMsg_AppLib);
	//            }
	//            catch (Exception e)
	//            {
	//                isEventSuccessful = false;
	//                strErrMsg_AppLib = e.Message;
	//            }
	//            return isEventSuccessful;
	//        }
	//        else
	//        {
	//            isEventSuccessful = false;
	//            strErrMsg_AppLib = "Devices page is not displayed";
	//            return isEventSuccessful;
	//        }
	//}


	//It gives the list of uploaded apps for any OS version passed.
	//*NOTE : It only gives those application name that are displayed on applications page and not the builds.
	//
	//@param platform Platform for which uploaded applications are to be fetched, i.e. 'iOS', 'Android' or 'All'(for getting all apps listed on applications index page.)
	//@return List of all applications matching the OS platform.
	//
	public final ArrayList<String> getPlatformSpecificAppsLists(String platform)
	{
		ArrayList<String> appList = new ArrayList<String>();
		int rowCount = 0;
		String OS = "";
		String TableRows_Xpath = "eleAppTableRows";
		strErrMsg_AppLib = "";
		try
		{
			if (platform.toLowerCase().equals("android"))
			{
				OS = "Android";
			}
			else if (platform.toLowerCase().equals("ios"))
			{
				OS = "iOS";
			}
			else if (platform.toLowerCase().equals("all"))
			{
				OS = "All";
			}

			if (PerformAction("eleApplicationsHeader", Action.isDisplayed)) // check if applications page is displayed
			{
				TableRows_Xpath = dicOR.get("eleAppTableRows");
				/*if (!GetTextOrValue("eleNoTableRowsWarning", "text").contains("deviceConnect currently has no configured applications.")) //Check if there are applications uploaded to system.
				{*/
					rowCount = getelementCount(TableRows_Xpath);

					for (int i = 1; i <= rowCount; i++)
					{
						if (platform.toLowerCase().equals("all"))
						{
							appList.add(GetTextOrValue(TableRows_Xpath + "[" + i + "]//td[1]/a", "text")); //If All apps are required, then put all the apps irrespective of the
						}
						else if (GetTextOrValue(TableRows_Xpath + "[" + i + "]//td[2]", "text").equals(OS)) //If OS of current row matches the required OS
						{
							appList.add(GetTextOrValue(TableRows_Xpath + "[" + i + "]//td[1]/div[2]/a", "text")); // then put it to the appList
						}
					}
				//}
				/*else
				{
					throw new RuntimeException("deviceConnect currently has no configured applications.");
				}*/
			}
			else
			{
				throw new RuntimeException("Applications page not displayed.");
			}
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "getPlatformSpecificAppsLists---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			writeToLog("getPlatformSpecificAppsLists -- " + strErrMsg_AppLib + "\r\n            " + e.getStackTrace());
		}
		return appList;
	}

	public final boolean verifyAppsList(ArrayList<String> appList)
	{
		boolean flag = false;
		try
		{ // in case no applications are uploaded.
			if (PerformAction("eleNoAppWarning", Action.isDisplayed))
			{
				if (appList.isEmpty())
				{
					flag = true;
				}
			}
			// in case applications list is displayed
			else if (PerformAction("eleLaunchApplicationHeader", Action.isDisplayed))
			{
				//            	(//div[starts-with(@class,'application-icon-container')]/../a)[1]
				//                int appCount = GenericLibrary.driver.findElements(By.xpath("//tr[starts-with(@class,'app-list-item')]/td[1]")).size(); // get count of list elements
				int appCount = GenericLibrary.driver.findElements(By.xpath("//div[starts-with(@class,'application-icon-container')]/../a[1]")).size(); // get count of list elements
				for (int i = 1; i <= appCount; i++)
				{
					flag = false;
					for (int j = 0; j < appList.size(); j++)
					{
						//                    	String temp = (GenericLibrary.driver.findElement(By.xpath("(//div[starts-with(@class,'application-icon-container')]/../..)[" + i + "]/td[2]/a")).getText());
						if ((GenericLibrary.driver.findElement(By.xpath("(//div[starts-with(@class,'application-icon-container')]/../..)[" + i + "]/td[2]/a")).getText()).contains(appList.get(j)));
						{
							flag = true;
							break;
						}
					}
					if (!flag)
					{
						strErrMsg_AppLib = "Application is not as per the platform selected.";
					}
				}
			}
			return flag;
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "verifyAppsList---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			return false;
		}
	}

	public final ArrayList<String> getApplistOnLaunchAppPage()
	{
		ArrayList<String> appList = new ArrayList<String>();
		strErrMsg_AppLib = "";
		try
		{
			int appCount = GenericLibrary.driver.findElements(By.xpath("//div[starts-with(@class,'application-icon-container')]/../a[1]")).size(); // get count of list elements
			//        	int appCount = GenericLibrary.driver.findElements(By.xpath("//tr[starts-with(@class,'app-list-item')]/td[1]")).size();
			for (int i = 1; i <= appCount; i++)
			{
				appList.add(GenericLibrary.driver.findElement(By.xpath("(//div[starts-with(@class,'application-icon-container')]/../..)[" + i + "]/td[2]/a")).getText());
			}

		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "getApplistOnLaunchAppPage---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			appList.clear();
		}
		return appList;
	}

	public final boolean Logout()
	{
		boolean flag = false;
		if (dicCommon.get("BrowserName").toLowerCase().contains("ie") && (GenericLibrary.IEversion.equals("11")))
		{
			flag=LogoutIE();
		}
		else
		{
			flag=LogoutAll();
		}
		return flag;
	}
	public final boolean LogoutAll()
	{
		boolean flag = false; //PerformAction("lnkLogout", Action.WaitForElement
		strErrMsg_AppLib = "";
		try
		{
			flag = PerformAction("lnkLogout", Action.isDisplayed);
			if (!flag)
			{
				flag = PerformAction("btnMenu", Action.Click);
				if (!flag)
				{
					strErrMsg_AppLib = "Menu button does not exist on the page.";
				}
			}
			if (flag)
			{
				flag = PerformAction("lnkLogout", Action.Click);
				if (flag)
				{
					waitForPageLoaded();
					PerformAction("btnNo", Action.WaitForElement, "5");
					if (PerformAction("btnNo", Action.Exist)) // click on confirmation button if it appears.
					{
						if (dicCommon.get("BrowserName").toLowerCase().contains("ie"))
						{
							PerformAction("btnNo", Action.ClickAtCenter);
						}
						else
						{
							PerformAction("btnNo", Action.Click);
						}
					}
					waitForPageLoaded();
					flag = PerformAction("inpEmailAddress", Action.WaitForElement, "30");
					if (!flag)

					{
						strErrMsg_AppLib = "Login page not displayed properly after clicking on Logout link.";
					}
				}
				else
				{
					strErrMsg_AppLib = "Logout link does not exist in the 'Menu' button's dropdown.";
				}
			}
		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "Logout---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return flag;
	}
	public final boolean LogoutIE()
	{
		boolean flag = false;
		strErrMsg_AppLib = "";
		try
		{
			waitForPageLoaded();
			PerformAction("LogoutMenuLinkIE", Action.WaitForElement);
			flag=PerformAction("LogoutMenuLinkIE",Action.ClickUsingJS);
			if(flag)
			{
				waitForPageLoaded();
				PerformAction("", Action.WaitForElement,"3");
				flag=PerformAction("LogoutLinkIE",Action.ClickUsingJS);
				if(!flag)
				{
					strErrMsg_AppLib = "Logout link does not exist in the 'Menu' button's dropdown.";
				}
			}
			else
			{
				strErrMsg_AppLib = "'Menu' button's dropdown not appeared on DI page";
			}

		}
		catch(Exception e)
		{
			flag=false;
			strErrMsg_AppLib = "Logout---" + "Exception : '" + e.getMessage();
		}
		return flag;
	}

	//This function is used to select any value from the 'Menu'dropdown on any page
	//<p>*NOTE :  In case of 'Download Mobile Labs Trust' and 'Download deviceConnect CLI', it only clicks on the option but does not verify anything.</p>
	//<p>put putitional steps in script for verification</p>
	//
	//@param menuItemText Option inside the 'Menu' dropdown that needs to be selected.It should be in exact case as it appears in the dropdown.
	//@param expectedPageElement Identification of some unique element of the destination page.
	//<example>selectFromMenu("Logout", "btnLogin")</example>
	//@return True or false
	//
	public final boolean selectFromMenu(String menuItemText, String expectedPageElement)
	{
		boolean flag = false;
		strErrMsg_AppLib = "";
		try
		{
			if (dicCommon.get("BrowserName").toLowerCase().contains("ie"))
			{
				PerformAction("browser", "waitforpagetoload", "10");
			}
			waitForPageLoaded();
			flag = PerformAction("lnkDevicesMenu", Action.isDisplayed);
			if (flag)
			{
				if (!dicCommon.get("BrowserName").contains("IE"))
				{
					flag = PerformAction("btnMenu", Action.ClickUsingJS);
				}
				else
				{
					flag = PerformAction("btnMenu", Action.ClickUsingJS);
				}

				if (!flag)
				{
					strErrMsg_AppLib = "Could not click on 'Menu' button.";
				}
				//Click on the required option under 'Menu' dropdown
				waitForPageLoaded();
				String element = "//a[text()='"+menuItemText+"']";
				//PerformAction(element, Action.WaitForElement);
				//flag = PerformAction(element, Action.Click);

				// Cases need to click under 'Username' dropdown on every page.
				switch (menuItemText)
				{

				case "Manage your account":
					if(flag)
					{
						flag = PerformAction(element, Action.ClickUsingJS);
					}
					break;
				case "android":
					flag = PerformAction("lnkAndroid", Action.ClickAtCenter);
					break;
				case "ios":
					PerformAction("browser", Action.Scroll, "30");
					flag = PerformAction("lnkiOS", Action.ClickAtCenter);
					break;
				}

				/*if (menuItemText.equals("Download Mobile Labs Trust") || menuItemText.equals("Download deviceConnect CLI"))
                    {
                         //*******put verification code when the function for verifying the downloaded zip file is created.
                        //
                    }
                    //case for which user is navigated to some page, this block verifies if correct page is opened.
                    else
                    {
                        flag = PerformAction(expectedPageElement, Action.WaitForElement);
                        if (!flag)
                        {
                            strErrMsg_AppLib = menuItemText + " page not displayed after clicking on " + menuItemText + "link.";
                        }
                    }*/

				if(!flag)
				{
					throw new RuntimeException(menuItemText + "link does not exist in the 'Menu' button's dropdown.");
				}
			}
		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "selectFromMenu---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return flag;
	}

	public final boolean returnToDevicesPage()
	{
		boolean flag = false;
		strErrMsg_AppLib = "";
		try
		{
			flag = PerformAction("lnkDevicesMenu", Action.isDisplayed);
			if (!flag)
			{
				flag = PerformAction("btnMenu", Action.Click);
				if (!flag)
				{
					strErrMsg_AppLib = "Menu button does not exist on the page.";
				}
			}
			if (flag)
			{
				flag = PerformAction("lnkDevicesMenu", Action.Click);
				if (flag)
				{
					flag = PerformAction("eleDevicesHeader", Action.WaitForElement);
					if (!flag)
					{
						strErrMsg_AppLib = "Devices page not displayed after clicking on Devices link.";
					}
				}
				else
				{
					strErrMsg_AppLib = "Devices link does not exist in the 'Menu' button's dropdown.";
				}
			}
		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "returnToDevicesPage---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return flag;
	}

	public boolean selectPlatform(String Platform)
	{
		boolean flag = false;
		strErrMsg_AppLib = "";
		try
		{
			PerformAction("browser", Action.WaitForPageToLoad);
			if (GenericLibrary.dicCommon.get("BrowserName").toLowerCase().equals("ie"))
			{
				switch (Platform.toLowerCase().trim())
				{
				case "all":
					flag = PerformAction("lnkAll", Action.ClickAtCenter);
					break;
				case "android":
					flag = PerformAction("lnkAndroid", Action.ClickAtCenter);
					break;
				case "ios":
					PerformAction("browser", Action.Scroll, "30");
					flag = PerformAction("lnkiOS", Action.ClickAtCenter);
					break;
				}
			}
			else
			{
				switch (Platform.toLowerCase().trim())
				{
				case "all":
					flag = PerformAction("lnkAll", Action.Click);
					break;
				case "android":
					flag = PerformAction("lnkAndroid", Action.Click);
					break;
				case "ios":
					PerformAction("browser", Action.Scroll, "30");
					try
					{
						driver.findElement(By.xpath("//a[text()='iOS']")).click();
						PerformAction("browser", Action.WaitForPageToLoad, "120");
						flag = true;
					}
					catch (RuntimeException e)
					{
						//
					}
					break;
				}
			}
			if (!flag)
			{
				throw new RuntimeException("Could not select platform - " + Platform);
			}
			PerformAction("browser", Action.WaitForPageToLoad);
			//Verifying devices are displayed
			if (GetTextOrValue("eleNoTableRowsWarning", "text").contains("deviceConnect currently has no configured devices or your filter produced no results."))
			{
				throw new RuntimeException("deviceConnect currently has no configured devices or your filter produced no results.");
			}
			if (flag && Platform.toLowerCase().equals("all"))
			{
				flag = getAttribute("lnkAll", "class").equals("platform-filter active");
				if (!flag)
				{
					throw new RuntimeException("Could not select platform - 'All'.");
				}
			}
			else if (flag)
			{
				flag = VerifyDeviceDetailsInGridAndListView("deviceplatform", Platform.toLowerCase());
			}
		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "selectPlatform---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return flag;
	}

	public final boolean VerifyLogoutPopup()
	{
		strErrMsg_AppLib = "";
		boolean flag = false;
		try
		{

			flag = PerformAction("lnkLogout", Action.isDisplayed);
			if (!flag)
			{
				flag = PerformAction("btnMenu", Action.Click);
				if (!flag)
				{
					strErrMsg_AppLib = "Menu button does not exist on the page.";
					return flag;
				}

			}
			if (flag)
			{
				flag = PerformAction("lnkLogout", Action.Click);
				if (flag)
				{
					if (!PerformAction("btnNo", Action.Exist)) // click on confirmation button if it appears.
					{
						strErrMsg_AppLib = strErrMsg_AppLib + "No button ,";
					}
					if (!PerformAction("btnYes", Action.Exist))
					{
						strErrMsg_AppLib = strErrMsg_AppLib + "Yes button ,";
					}
					if (!PerformAction("btnCancel", Action.Exist))
					{
						strErrMsg_AppLib = strErrMsg_AppLib + "Cancel button ,";
					}
					if (!GetTextOrValue("eleWarningOrConfirmationPopUpBody", "text").toLowerCase().contains("you currently have one or more active device sessions. do you want to release your current device sessions?"))
					{
						strErrMsg_AppLib = strErrMsg_AppLib + "Logout message - 'You currently have one or more active device sessions. Do you want to release your current device sessions?' ";
					}

					if ( ! strErrMsg_AppLib.equals(""))
					{
						strErrMsg_AppLib = "Following are not displayed on logout pop up - " + strErrMsg_AppLib;
					}
					else
					{
						flag = true;
					}

				}
				else
				{
					strErrMsg_AppLib = "Logout link does not exist in the 'Menu' button's dropdown.";
				}
			}
		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "VerifyLogoutPopup---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return flag;
	}

	public final boolean DeleteFile(String strFileName)
	{
		strErrMsg_AppLib = "";
		boolean flag = false;
		int intCount = 0, i = 1;
		String strText = "";
		try
		{
			intCount = driver.findElements(By.xpath("//table[@class='table data-grid tablesorter']/tbody/tr")).size();
			if (intCount == 0)
			{
				strErrMsg_AppLib = "No data displayed in the table.";
				throw new RuntimeException(strErrMsg_AppLib);
			}
			else
			{
				for (i = 0; i <= intCount; i++)
				{
					if (strFileName.toLowerCase().contains(".ipa") || strFileName.toLowerCase().contains(".apk"))
					{
						strText = GetTextOrValue("//table[@class='table data-grid']//tbody/tr[" + (new Integer(i)).toString() + "]/td[2]", "text");
						if (strText.toLowerCase().equals(strFileName.toLowerCase()))
						{
							break;
						}
					}
					else
					{
						strText = GetTextOrValue("//table[@class='table data-grid tablesorter']/tbody/tr[1]/td[1]/a[1]", "text");
						if (strText.toLowerCase().equals(strFileName.toLowerCase()))
						{
							break;
						}
					}
				}

				if (i > intCount)
				{
					strErrMsg_AppLib = "Application - '" + strFileName + " not found on page";
					return flag;
				}
				flag = PerformAction("//table[@class='table data-grid tablesorter']/tbody/tr[" + (new Integer(i)).toString() + "]/td[5]//..//button[contains(@class,'btn btn-sm btn-info dropdown-toggle')]", Action.Click);
				if (flag)
				{
					flag = PerformAction("//table[@class='table data-grid tablesorter']/tbody/tr[1]/td[5]//ul//..//a[contains(text(),'Delete')]", Action.Click);
					if (flag)
					{
						flag = PerformAction("btnContinue_Disable", Action.Click);
						if (flag)
						{
							PerformAction("eleNotificationRightBottom", Action.WaitForElement, "10");
							if (GetTextOrValue("eleNotificationRightBottom", "text").contains("was successfully deleted."))
							{
								flag = true;
							}
							else
							{
								strErrMsg_AppLib = GetTextOrValue("eleNotificationRightBottom", "text");
								flag = false;
							}
						}
						else
						{
							strErrMsg_AppLib = "Unable to click on continue button of Delete application";
						}
					}
				}
				else
				{
					strErrMsg_AppLib = "Unable to click on Delete button for application - " + strFileName;
				}

			}
		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "DeleteFile---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return flag;
	}


	//<p>PRE-Requisite : IWebDriver.ipa and Webdriver.apk should be present at DeviceConnectCLI folder</p>         
	//<p>This Function is for usage of CLI commands</p>
	//<p>This Function will perform Upload,Connect, Disable, Enable, Release on Android / iOS device using CLI</p>         
	//<p>Default CLIOption is Connect Command</p>                  
	//
	//<!--Created By : Vinita Mahajan-->
	//<!--Last modified : 10/2/2015 by Mandeep Kaur-->
	//@param CLIOption OPTIONAL : default value is connect - This parameter accepts values : connect, upload, release, disable, enable
	//@param platform OPTIONAL : default value is android
	//@param UserName OPTIONAL : default value is dicCommon["EmailAddress"] 
	//@param Password OPTIONAL : default value is dicCommon["Password"]
	//@param deviceName OPTIONAL - but in case of enable CLIOPTION, devicename is mandatory
	//@return 
	//

	 public final Object[] ExecuteCLICommand(String CLIOption, String platform, String UserName, String Password,String deviceName, String client)
	    {
	        return ExecuteCLICommand(CLIOption, platform, UserName, Password, deviceName,"",client, "");
	    }

	        public final Object[] ExecuteCLICommand(String CLIOption, String platform, String user, String password)
	    {
	        return ExecuteCLICommand(CLIOption, platform, user, password, "","","");
	    }

	   	public final Object[] ExecuteCLICommand(String CLIOption, String platform, String UserName, String Password,String deviceName, String appName, String client, String userid)
	    {
	      	return ExecuteCLICommand(CLIOption, platform, UserName, Password, deviceName,appName,"", client,userid);
	    }
	    
	    public final Object[] ExecuteCLICommand(String CLIOption, String platform)
	    {
	        return ExecuteCLICommand(CLIOption, platform, "", "", "","","");
	    }

	    public final Object[] ExecuteCLICommand(String CLIOption)
	    {
	        return ExecuteCLICommand(CLIOption, "android", "", "", "","","");
	    }
	    public final Object[] ExecuteCLICommand(String CLIOption,String platform,String appname)
	    {
	        return ExecuteCLICommand(CLIOption, "android", "", "", "",appname,"");
	    }
	    public final Object[] ExecuteCLICommand()
	    {
	        return ExecuteCLICommand("run", "android", "", "", "","","");
	    }
	    public final Object[] ExecuteCLICommand(String CLIOption,String platform,String UserName,String Password,String deviceName,String appName, String client)
	    {
	        return ExecuteCLICommand(CLIOption, platform, UserName, Password, deviceName, appName ,client,"");
	    }
	    
/*	    public final Object[] ExecuteCLICommand(String CLIOption, String platform, String UserName, String Password, String deviceName,String appName, String status, String client, String userid )
	    {
	        String CLI_Command = dicConfig.get("Artifacts") + "\\DeviceConnectCLI\\";
	        strErrMsg_AppLib = "";
	        String appUpload= dicConfig.get("Artifacts")+"\\uploadCSV.csv";
	        String appTester= dicConfig.get("Artifacts")+"\\Dollar_General.ipa";
	        String appAdmin= dicConfig.get("Artifacts")+"\\com.aldiko.android.apk";
	        String additionalFolder=dicConfig.get("Artifacts")+"\\additionalFolder\\";
	        String importUser=dicConfig.get("Artifacts")+"\\	.csv";
	        String importInUsercsv=dicConfig.get("Artifacts")+"\\userImport_Invalid.csv";
	        String FileName = "", command = "", deviceSelected = "",  line="";
	        boolean isEventSuccessful = true, flag=false;
	        int devicePort=3001;
	        Object[] returnValue = new Object[7]; 
			try
	        {
	 		  if (UserName.equals(""))
	            {
	                UserName = dicCommon.get("EmailAddress");
	            }
	            if (Password.equals(""))
	            {
	                Password = dicCommon.get("Password");
	            }
	            if (appName.equals(""))
	            {
	            	appName = "\"" + "deviceControl" +"\"";

				}
				if (status.equals(""))
				{
					status = "Available";
				}
				if (platform.toLowerCase().equals("android"))
				{
					FileName = "\"" + CLI_Command + "android-server-2.38.0.apk" + "\"";
					devicePort=8080;
					//appName = "\"" + "deviceControl" + "\"";
				}
				else if (platform.toLowerCase().equals("ios"))
				{
					FileName = "\"" + CLI_Command + "iWebDriver.ipa" + "\"";
					devicePort=3001;
					//appName = "\"" + "deviceControl iOS5" + "\"";
				}

			
	     	if (deviceName.equals(""))
				{
					isEventSuccessful = PerformAction("eleDevicesTab_Devices", Action.isDisplayed);
					if (!isEventSuccessful)
					{
						if (!navigateToNavBarPages("Devices", "eleDevicesTab_Devices"))
						{
							throw new RuntimeException("On selecting 'Devices' menu, 'Devices' page is not opened.");
						}
					}
					
					PerformAction("btnRefresh_Devices", Action.Click);
					if (selectStatus_DI(status))
					{
						if (!selectPlatform_DI(platform))
						{
							throw new RuntimeException(strErrMsg_AppLib);
						}
						if (PerformAction("eleNoDevicesWarning_Devices", Action.isDisplayed))
						{
							throw new RuntimeException("No devices displayed.");
						}
						deviceSelected = GetDeviceDetailInGridAndListView(1, "devicename");
						returnValue[3] = deviceSelected;
						deviceName=deviceSelected;
						deviceSelected = "\"" + deviceSelected + "\"" ;
					}
					else
					{
						isEventSuccessful = false;
						throw new RuntimeException(strErrMsg_AppLib);
					}
				}
				else
				{
					returnValue[3] = deviceName;
					deviceSelected = "\"" + deviceName + "\"" ;
				}

				String cliCmdAuthString="";
				System.out.println(CLIOption);

				//--------------code added by jaishree for authentication type
				if (CLIOption.toLowerCase().contains("authtype"))
				{
					System.out.println(CLIOption.toLowerCase().substring(8, 9));
					String apiKey;
					switch(CLIOption.toLowerCase().substring(8, 9))
					{
					case "1": //MobileLabs.DeviceConnect.Cli.exe <host> <username> <password> [options]
						cliCmdAuthString=dicCommon.get("ApplicationURL") + " " + UserName + " " + Password;
						CLIOption=CLIOption.replace("authtype1", "");
						break;
					case "2": //MobileLabs.DeviceConnect.Cli.exe <host> <username> <api key> [options]
						if (Pattern.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", Password))
							apiKey=Password;
						else
							throw new RuntimeException("Please pass apikey in password argument");

						cliCmdAuthString=dicCommon.get("ApplicationURL") + " " + UserName + " " + Password; 
						CLIOption=CLIOption.replace("authtype2", "");
						break;
					case "3": //MobileLabs.DeviceConnect.Cli.exe <username>:<password>@<host> [options]
						cliCmdAuthString=UserName + ":" + Password + "@" + dicCommon.get("ApplicationURL");
						CLIOption=CLIOption.replace("authtype3", "");
						break;
					case "4": //MobileLabs.DeviceConnect.Cli.exe <username>:<api key>@<host> [options]
						if (Pattern.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", Password))
							apiKey=Password;
						else
							throw new RuntimeException("Please pass apikey in password argument");
						cliCmdAuthString= UserName + ":" + Password + "@" + dicCommon.get("ApplicationURL");
						CLIOption=CLIOption.replace("authtype4", "");
						break;
					default:
						cliCmdAuthString=dicCommon.get("ApplicationURL") + " " + UserName + " " + Password;
						break;
					}
				}else
				{
					cliCmdAuthString=dicCommon.get("ApplicationURL") + " " + UserName + " " + Password;
				}



				switch (CLIOption.toLowerCase())
				{
				case "userinfodefault":
	        		 command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details";
	            		break;
	            	case "userinfolist":
	            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details -format list";
	            		break;
	            	case "userinfojson":	
	            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details -format json";
	            		break;
	            	case "userinfooutputjson":
	            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details -format json -o "+additionalFolder+ "UserInformationJson.json";
	            		break;
	            	case "userinfooutputlist":
	            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details -format list -o "+additionalFolder+ "UserInformationList.txt";
	            		break;
	            	case "userinfooutput":
	            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details  -o "+additionalFolder+ "UserInformationList.txt";
	            		break;
	            	case "userinfooutputcsv":
	            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details -format csv -o "+additionalFolder+ "UserInformationCSV.csv";
	            		break;
	            	case "userinfotext":
	            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details -format text";
	            		break;
	            	case "log":
	            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -device " + deviceSelected + " -log > \"" + additionalFolder+"DeviceLogs.txt\"";
	            		break;	
					case "upload":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " +cliCmdAuthString + " -upload " + FileName;
					break;

				case "uploadcheck":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -upload " + appUpload;
					break;

				case "uploadtester":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -upload " + appTester;
					break;

				case "uploadadmin":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -upload " + appAdmin;
					break;

				case "importtester":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -userimport " + importUser;
					break;

				case "importadmin":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -userimport " + importUser;
					break;

				case "importinvalidcsv":
					command=CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -userimport " + importInUsercsv;
					break;

				case "importwithwrongfilepath":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -userimport D:\\wrong.csv";
					break;

				case "userexport":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -userexport " +additionalFolder+ "exportUser.csv";
					break;

				case "userexporttester":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -userexport " +additionalFolder+ "exportUser.csv";
					break;

				case "incorrecttimezone":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -usage -days 2 -o " + additionalFolder+"incorrectTimezone.csv -timezone America/New_York Asia/Calcutta Asia/calcutta";
					break;

				case "timezones":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -timezones";
					break;

				case "usagejsonfile":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -usage -days 2 -o " + additionalFolder+"JSONFile.txt -timezone local -format json";
					break;

				case "usagejsoncmd":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -usage -days 1 -timezone local -format json";
					break;

				case "usagejsonfilewithouttimezone":
	                command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -device " + deviceSelected + " -usage -days 2 -o " + additionalFolder+"JSONFile.txt -format json";
	                break;

				case "devicelist":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -devicelist ";
					break;

				case "devicelistall":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -devicelist all";
					break;

				case "history": 
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -history";
					break;

				case "historyfrom":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -history -from "+getDate("from") + " -o "+additionalFolder+"historyfrom.csv";
					returnValue[6]=additionalFolder+"historyfrom.csv";
					returnValue[5]=getDate("reportfrom");
					break;

				case "historytilldate":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -history -to "+getDate("to") + " -o "+additionalFolder+"historytilldate.csv";
					returnValue[6]=additionalFolder+"historytilldate.csv";
					returnValue[5]=getDate("to");
					break;

				case "historyfromto":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -history -from "+getDate("from") + " -to "+getDate("to") + " -o "+additionalFolder+"historyfromto.csv";
					returnValue[6]=additionalFolder+"historyfromto.csv";
					returnValue[5]=getDate("reportfrom")+","+getDate("to");
					break;

				case "historydevicefromto":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -history -from "+getDate("from") + " -to "+getDate("to") + " -o "+additionalFolder+"historydevicefromto.csv";
					returnValue[6]=additionalFolder+"historydevicefromto.csv";
					returnValue[5]=getDate("reportfrom")+","+getDate("to");
					break;

				case "historydays":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -history -days 2 -o "+additionalFolder+"historydays.csv";
					returnValue[6]=additionalFolder+"historydays.csv";
					returnValue[5]=getDays("reportfrom");
					break;

				case "devicehistorydays":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -history -days 2 -o "+additionalFolder+"devicehistorydays.csv";
					returnValue[6]=additionalFolder+"devicehistorydays.csv";
					returnValue[5]=getDays("reportfrom");
					break;

				case "usage":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -usage";
					break;

				case "usagefrom":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -usage -from "+getDate("from") + " -o "+additionalFolder+"usagefrom.csv";
					returnValue[6]=additionalFolder+"usagefrom.csv";
					returnValue[5]=getDate("reportfrom");
					break;

				case "usagetilldate":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -usage -to "+getDate("to") + " -o "+additionalFolder+"usagetilldate.csv";
					returnValue[6]=additionalFolder+"usagetilldate.csv";
					returnValue[5]=getDate("to");
					break;

				case "usagefromto":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -usage -from "+getDate("from") + " -to "+getDate("to") + " -o "+additionalFolder+"usagefromto.csv";
					returnValue[6]=additionalFolder+"usagefromto.csv";
					returnValue[5]=getDate("reportfrom")+","+getDate("to");
					break;

				case "usagedays":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -usage -days 2 -o "+additionalFolder+"usagedays.csv";
					returnValue[6]=additionalFolder+"usagedays.csv";
					returnValue[5]=getDays("reportfrom");
					break;

				case "deviceusagedays":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -usage -days 2 -o "+additionalFolder+"deviceusagedays.csv";
					returnValue[6]=additionalFolder+"deviceusagedays.csv";
					returnValue[5]=getDays("reportfrom");
					break;

				case "usagedevicefromto":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -usage -from "+getDate("from") + " -to "+getDate("to") + " -o "+additionalFolder+"usagedevicefromto.csv";
					returnValue[6]=additionalFolder+"usagedevicefromto.csv";
					returnValue[5]=getDate("reportfrom")+","+getDate("to");
					break;

				case "connect":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -run " + appName;
					break;

				case "install":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -install " + appName;
					break;

				case "uninstall":
	                command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -device " + deviceSelected + " -uninstall " + appName;
	                break;

				case "disable":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -disable";
					break;

				case "enable":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -enable " + deviceSelected;
					break;

				case "release":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -release";
					break;

				case "renametester":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -renamedevice DeviceTesting";
					break;

				case "deletetester":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -deleteapp deviceControl";
					break;

				case "run":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -run"+ " "+ "\"" + "deviceControl" +"\"";
					break;

				case "client":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -client "+ client+" -run"+ " "+ "\"" + "deviceControl" +"\"";
					break;

				case "forward":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -forward "+ 10919+" "+ devicePort+"\"";
					break;

				case "apitoken":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -apitoken";
					break;

				case "adminapitoken":
					isEventSuccessful=true;
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + "admin" + " " + "deviceconnect" + " -apitoken ";
					break;

				case "empty":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " +  cliCmdAuthString + " ";
					break;

				default:
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + dicCommon.get("EmailAddress") + " " + dicCommon.get("Password") + " -device " + deviceSelected + " -retain -install \"deviceControl\" -autoconnect \"deviceControl\"";
					break;
				}

				AddToDictionary(dicOutput, "executedCommand", command);
				//*** connecting the Device with DeviceViewer using CMD *** 


				Runtime runtime = Runtime.getRuntime();
				InputStream is =   runtime.exec(command).getInputStream();
				if(CLIOption.equals("history") || (CLIOption.equals("log")))
				{
					Thread.sleep(30000);
				}
				else if(CLIOption.contains("userinfo"))
				{
					Thread.sleep(40000);
				}
				else
				{
					Thread.sleep(10000);
				}
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				if(!(br.ready()))
				{
					returnValue[0] = null;
					returnValue[2] = true;
				}
				else
				{
					if(CLIOption.toLowerCase().equals("devicelist") || CLIOption.toLowerCase().equals("devicelistapikeyauth"))
					{
						flag=true;
						while ((line = br.readLine()) != null)
						{
							if(line.contains("Online:") || line.contains("Offline:"))
							{
								continue;
							}
							if(!(line.contains("Enabled") || (line.contains("Disabled"))))
							{
								flag=false;
								returnValue[0] = line;
								returnValue[2] = false;
								break;
							}
						}
						if(flag)
						{
							returnValue[0] ="True";
							returnValue[2] = true;
						}
					}
					else if(CLIOption.toLowerCase().equals("devicelistall"))
					{
						int count=0;
						while ((line = br.readLine()) != null)
						{
							if(line.contains("Online:") || line.contains("Offline:"))
							{
								continue;
							}
							if(line.contains("Enabled") || line.contains("Disabled"))
							{
								count++;
							}
						}
						if(count>0)
						{
							returnValue[0] =count;
							returnValue[2] = true;
						}
						else
						{
							returnValue[0] =count;
							returnValue[2] = false;
						}
					}
					else if(CLIOption.toLowerCase().equals("uploadtester") || CLIOption.toLowerCase().equals("renametester") || CLIOption.toLowerCase().equals("deletetester") || CLIOption.toLowerCase().equals("importtester") || CLIOption.toLowerCase().equals("userexporttester") || CLIOption.toLowerCase().equals("resetrebootuninstall"))
					{
						while ((line = br.readLine()) != null)
						{
							if(line.contains("User does not have required entitlement") || line.contains("Last method was SaveApplication"))
							{
								flag=true;
								returnValue[0] = "User does not have required entitlement";
								returnValue[2] = true;
								break;
							}
						}
						if(!flag)
						{
							returnValue[0] ="Exception did not occurred";
							returnValue[2] = false;
						}
					}
					else if(CLIOption.toLowerCase().equals("incorrecttimezone") )
					{
						while ((line = br.readLine()) != null)
						{
							if(line.contains("System.NullReferenceException: Unable to find timezone"))
							{
								flag=true;
								returnValue[0] = line;
								returnValue[2] = true;
								break;
							}
						}
						if(!flag)
						{
							returnValue[0] ="Exception did not occurred";
							returnValue[2] = false;
						}
					}
					else if(CLIOption.toLowerCase().equals("userexport") )
					{
						while ((line = br.readLine()) != null)
						{
							if(line.contains("File in use"))
							{
								flag=false;
								returnValue[0] = line;
								returnValue[2] = false;
								break;
							}
						}
						if(flag)
						{
							returnValue[0] ="Exported successfully";
							returnValue[2] = true;
						}
					}
					else if(CLIOption.toLowerCase().equals("importwithwrongfilepath") )
					{
						while ((line = br.readLine()) != null)
						{
							if(line.contains("File not found"))
							{
								flag=false;
								returnValue[0] = line;
								returnValue[2] = false;
								break;
							}
						}
						if(flag)
						{
							returnValue[0] ="Imported successfully";
							returnValue[2] = true;
						}
					}
					else if(CLIOption.toLowerCase().equals("importinvalidcsv") )
					{
						while ((line = br.readLine()) != null)
						{
							if(line.contains("Boolean value expected"))
							{
								flag=false;
								returnValue[0] = line;
								returnValue[2] = false;
								break;
							}
						}
						if(flag)
						{
							returnValue[0] ="Imported successfully";
							returnValue[2] = true;
						}
					}
					else if(CLIOption.toLowerCase().equals("usagejsoncmd") )
					{
						while ((line = br.readLine()) != null)
						{
							if(line.contains("Asia/Calcutta"))
							{
								flag=true;
								returnValue[0] = "Asia/Calcutta";
								returnValue[2] = true;
								break;
							}
						}
						if(!flag)
						{
							returnValue[0] ="Usage is in different time format than local";
							returnValue[2] = false;
						}
					}
					else if(CLIOption.toLowerCase().equals("history") || CLIOption.toLowerCase().equals("usage") )
					{
						while ((line = br.readLine()) != null)
						{
							if(line.contains("Device"))
							{
								flag=true;
								returnValue[0] = line;
								returnValue[2] = true;
								break;
							}
						}
						if(!flag)
						{
							returnValue[0] ="History/Usage not printed on console";
							returnValue[2] = false;
						}
					}
					else if(CLIOption.toLowerCase().equals("timezones") )
					{
						int count=0;
						while ((line = br.readLine()) != null)
						{
							count++;
							flag=true;
							returnValue[0] = count;
						}
						if(!flag)
						{
							returnValue[0] =count;
							returnValue[2] = false;
						}
						else
						{
							returnValue[2] = true;
						}
					}
					else if(CLIOption.toLowerCase().equals("userinfodefault") || CLIOption.toLowerCase().equals("userinfolist"))
					{
						while((line = br.readLine()) != null)
						{
							if(line.contains("[") || line.contains("{") )
							{
								returnValue[0] ="Output is in json format.";
								returnValue[2] = false;
								break;
							}
							else if(line.contains("Unknown format requested"))
							{
								returnValue[0] ="Exception thrown.";
								returnValue[2] = false;
								break;
							}
							else if(line.contains("\tEmail:\t"+userid))
							{
								returnValue[0] ="Output is in list format.";
								returnValue[2] = true;
								break;
							}
							else if(line.contains("Unable to locate user"))
							{
								returnValue[0] ="Unable to locate user";
								returnValue[2] = true;
								break;
							}
						}
					}
					else if(CLIOption.toLowerCase().equals("userinfojson"))
					{
						int count=0;
						while((line = br.readLine()) != null)
						{
							if(line.contains("[") && count==0)
							{
								returnValue[0] ="Output is in json format.";
								returnValue[2] = true;
								break;
							}
							count++;         			 
						}
					}
					else if(CLIOption.toLowerCase().equals("userinfotext"))
					{
						while((line = br.readLine()) != null)
						{
							if(line.contains("Unknown format requested"))
							{
								returnValue[0] ="Unknown format requested";
								returnValue[2] = true;
								break;
							}
						}
					}
					else if(CLIOption.toLowerCase().equals("apitoken") || CLIOption.toLowerCase().equals("apikeyauth") || CLIOption.toLowerCase().equals("adminapitoken"))
					{
						while ((line = br.readLine()) != null)
						{
							returnValue[0] = line;
							returnValue[2] = true;
							break;
						}
					} else if(CLIOption.toLowerCase().equals("empty"))
					{
						line=br.readLine();
						returnValue[0] = line;
						returnValue[2] = true;
					} 
					else
					{
						while ((line = br.readLine()) != null)
						{
							returnValue[0] = line;
						}
						returnValue[2] = true;
					}
				}


				// *** Read the task lists from Window Task Manager ***
				Thread.sleep(10000);  
				String processName="";
				if(CLIOption.toLowerCase().equals("forward"))
				{
					processName="MobileLabs.DeviceConnect.Cli.exe";
				}
				else
				{
					processName="MobileLabs.deviceViewer.exe";
				}
				if(!client.equals("web"))
				{
					Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
					BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while ((line = reader.readLine()) != null) 
					{
						line = line.substring(1);
						if (line.startsWith(processName)) 
						{
							returnValue[1] = line;
							returnValue[4] = true;
							break;
						}
					}
				}
				else
				{
					String[] winHandler=WindowNames.WindowNames();
					for(String title:winHandler)
					{
						if(title==null)
						{
							continue;
						}
						if(title.length()<0)
						{
							continue;
						}
						else
						{
							if(title.contains(deviceName))
							{
								deviceName=title;
								flag=true;
								break;
							}
						}
					}
					if(flag)
					{
						returnValue[1] = deviceName+" - deviceConnect opened in web viewer";
						returnValue[4] = true;
					}
					else
					{
						returnValue[1] = deviceName+" - deviceConnect browser not found";
						returnValue[4] = false;
					}
				}

			}
			catch (RuntimeException e)
			{
				returnValue[2] = false;
				strErrMsg_AppLib = "ExecuteCLICommand---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			} catch (Exception e)
			{
				returnValue[2] = false;
				strErrMsg_AppLib = "ExecuteCLICommand---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}

			return returnValue;
		}*/
	    
	    //merged code
	    public final Object[] ExecuteCLICommand(String CLIOption, String platform, String UserName, String Password, String deviceName,String appName, String status, String client, String userid )
		{
			String CLI_Command = dicConfig.get("Artifacts") + "\\DeviceConnectCLI\\";
			strErrMsg_AppLib = "";
			String appUpload= dicConfig.get("Artifacts")+"\\uploadCSV.csv";
			String appTester= dicConfig.get("Artifacts")+"\\Dollar_General.ipa";
			String appAdmin= dicConfig.get("Artifacts")+"\\com.aldiko.android.apk";
			String additionalFolder=dicConfig.get("Artifacts")+"\\additionalFolder\\";
			String importUser=dicConfig.get("Artifacts")+"\\userImport.csv";
			String importInUsercsv=dicConfig.get("Artifacts")+"\\userImport_Invalid.csv";
			String FileName = "", command = "", deviceSelected = "",  line="";
			boolean isEventSuccessful = true, flag=false;
			int devicePort=3001;
			Object[] returnValue = new Object[7]; 
			try
			{
				if (UserName.equals(""))
				{
					UserName = dicCommon.get("EmailAddress");
				}
				if (Password.equals(""))
				{
					Password = dicCommon.get("Password");
				}
				if (appName.equals(""))
				{
					appName = "\"" + "deviceControl" +"\"";

					}
					if (status.equals(""))
					{
						status = "Available";
					}
					if (platform.toLowerCase().equals("android"))
					{
						FileName = "\"" + CLI_Command + "android-server-2.38.0.apk" + "\"";
						devicePort=8080;
						//appName = "\"" + "deviceControl" + "\"";
					}
					else if (platform.toLowerCase().equals("ios"))
					{
						FileName = "\"" + CLI_Command + "iWebDriver.ipa" + "\"";
						devicePort=3001;
						//appName = "\"" + "deviceControl iOS5" + "\"";
					}

				
		     	if (deviceName.equals(""))
					{
						isEventSuccessful = PerformAction("eleDevicesTab_Devices", Action.isDisplayed);
						if (!isEventSuccessful)
						{
							if (!navigateToNavBarPages("Devices", "eleDevicesTab_Devices"))
							{
								throw new RuntimeException("On selecting 'Devices' menu, 'Devices' page is not opened.");
							}
						}
						
						PerformAction("btnRefresh_Devices", Action.Click);
						if (selectStatus_DI(status))
						{
							if (!selectPlatform_DI(platform))
							{
								throw new RuntimeException(strErrMsg_AppLib);
							}
							if (PerformAction("eleNoDevicesWarning_Devices", Action.isDisplayed))
							{
								throw new RuntimeException("No devices displayed.");
							}
							deviceSelected = GetDeviceDetailInGridAndListView(1, "devicename");
							returnValue[3] = deviceSelected;
							deviceName=deviceSelected;
							deviceSelected = "\"" + deviceSelected + "\"" ;
						}
						else
						{
							isEventSuccessful = false;
							throw new RuntimeException(strErrMsg_AppLib);
						}
					}
					else
					{
						returnValue[3] = deviceName;
						deviceSelected = "\"" + deviceName + "\"" ;
					}

					String cliCmdAuthString="";
					System.out.println(CLIOption);

					//--------------code added by jaishree for authentication type
					if (CLIOption.toLowerCase().contains("authtype"))
					{
						System.out.println(CLIOption.toLowerCase().substring(8, 9));
						String apiKey;
						switch(CLIOption.toLowerCase().substring(8, 9))
						{
						case "1": //MobileLabs.DeviceConnect.Cli.exe <host> <username> <password> [options]
							cliCmdAuthString=dicCommon.get("ApplicationURL") + " " + UserName + " " + Password;
							CLIOption=CLIOption.replace("authtype1", "");
							break;
						case "2": //MobileLabs.DeviceConnect.Cli.exe <host> <username> <api key> [options]
							if (Pattern.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", Password))
								apiKey=Password;
							else
								throw new RuntimeException("Please pass apikey in password argument");

							cliCmdAuthString=dicCommon.get("ApplicationURL") + " " + UserName + " " + Password; 
							CLIOption=CLIOption.replace("authtype2", "");
							break;
						case "3": //MobileLabs.DeviceConnect.Cli.exe <username>:<password>@<host> [options]
							cliCmdAuthString=UserName + ":" + Password + "@" + dicCommon.get("ApplicationURL");
							CLIOption=CLIOption.replace("authtype3", "");
							break;
						case "4": //MobileLabs.DeviceConnect.Cli.exe <username>:<api key>@<host> [options]
							if (Pattern.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", Password))
								apiKey=Password;
							else
								throw new RuntimeException("Please pass apikey in password argument");
							cliCmdAuthString= UserName + ":" + Password + "@" + dicCommon.get("ApplicationURL");
							CLIOption=CLIOption.replace("authtype4", "");
							break;
						default:
							cliCmdAuthString=dicCommon.get("ApplicationURL") + " " + UserName + " " + Password;
							break;
						}
					}else
					{
						cliCmdAuthString=dicCommon.get("ApplicationURL") + " " + UserName + " " + Password;
					}



					switch (CLIOption.toLowerCase())
					{
					case "userinfodefault":
		        		 command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details";
		            		break;
		            	case "userinfolist":
		            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details -format list";
		            		break;
		            	case "userinfojson":	
		            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details -format json";
		            		break;
		            	case "userinfooutputjson":
		            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details -format json -o "+additionalFolder+ "UserInformationJson.json";
		            		break;
		            	case "userinfooutputlist":
		            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details -format list -o "+additionalFolder+ "UserInformationList.txt";
		            		break;
		            	case "userinfooutput":
		            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details  -o "+additionalFolder+ "UserInformationList.txt";
		            		break;
		            	case "userinfooutputcsv":
		            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details -format csv -o "+additionalFolder+ "UserInformationCSV.csv";
		            		break;
		            	case "userinfotext":
		            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -user "+userid+" -details -format text";
		            		break;
		            	case "log":
		            		command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -device " + deviceSelected + " -log > \"" + additionalFolder+"DeviceLogs.txt\"";
		            		break;	
						case "upload":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " +cliCmdAuthString + " -upload " + FileName;
						break;

					case "uploadcheck":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -upload " + appUpload;
						break;

					case "uploadtester":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -upload " + appTester;
						break;

					case "uploadadmin":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -upload " + appAdmin;
						break;

					case "importtester":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -userimport " + importUser;
						break;

					case "importadmin":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -userimport " + importUser;
						break;

					case "importinvalidcsv":
						command=CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -userimport " + importInUsercsv;
						break;

					case "importwithwrongfilepath":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -userimport D:\\wrong.csv";
						break;

					case "userexport":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -userexport " +additionalFolder+ "exportUser.csv";
						break;

					case "userexporttester":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -userexport " +additionalFolder+ "exportUser.csv";
						break;

					case "incorrecttimezone":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -usage -days 2 -o " + additionalFolder+"incorrectTimezone.csv -timezone America/New_York Asia/Calcutta Asia/calcutta";
						break;

					case "timezones":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -timezones";
						break;

					case "usagejsonfile":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -usage -days 2 -o " + additionalFolder+"JSONFile.txt -timezone local -format json";
						break;

					case "usagejsoncmd":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -usage -days 1 -timezone local -format json";
						break;

					case "usagejsonfilewithouttimezone":
		                command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -device " + deviceSelected + " -usage -days 2 -o " + additionalFolder+"JSONFile.txt -format json";
		                break;

					case "devicelist":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -devicelist ";
						break;

					case "devicelistall":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -devicelist all";
						break;

					case "history": 
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -history";
						break;

					case "historyfrom":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -history -from "+getDate("from") + " -o "+additionalFolder+"historyfrom.csv";
						returnValue[6]=additionalFolder+"historyfrom.csv";
						returnValue[5]=getDate("reportfrom");
						break;

					case "historytilldate":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -history -to "+getDate("to") + " -o "+additionalFolder+"historytilldate.csv";
						returnValue[6]=additionalFolder+"historytilldate.csv";
						returnValue[5]=getDate("to");
						break;

					case "historyfromto":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -history -from "+getDate("from") + " -to "+getDate("to") + " -o "+additionalFolder+"historyfromto.csv";
						returnValue[6]=additionalFolder+"historyfromto.csv";
						returnValue[5]=getDate("reportfrom")+","+getDate("to");
						break;

					case "historydevicefromto":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -history -from "+getDate("from") + " -to "+getDate("to") + " -o "+additionalFolder+"historydevicefromto.csv";
						returnValue[6]=additionalFolder+"historydevicefromto.csv";
						returnValue[5]=getDate("reportfrom")+","+getDate("to");
						break;

					case "historydays":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -history -days 2 -o "+additionalFolder+"historydays.csv";
						returnValue[6]=additionalFolder+"historydays.csv";
						returnValue[5]=getDays("reportfrom");
						break;

					case "devicehistorydays":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -history -days 2 -o "+additionalFolder+"devicehistorydays.csv";
						returnValue[6]=additionalFolder+"devicehistorydays.csv";
						returnValue[5]=getDays("reportfrom");
						break;

					case "usage":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -usage";
						break;

					case "usagefrom":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -usage -from "+getDate("from") + " -o "+additionalFolder+"usagefrom.csv";
						returnValue[6]=additionalFolder+"usagefrom.csv";
						returnValue[5]=getDate("reportfrom");
						break;

					case "usagetilldate":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -usage -to "+getDate("to") + " -o "+additionalFolder+"usagetilldate.csv";
						returnValue[6]=additionalFolder+"usagetilldate.csv";
						returnValue[5]=getDate("to");
						break;

					case "usagefromto":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -usage -from "+getDate("from") + " -to "+getDate("to") + " -o "+additionalFolder+"usagefromto.csv";
						returnValue[6]=additionalFolder+"usagefromto.csv";
						returnValue[5]=getDate("reportfrom")+","+getDate("to");
						break;

					case "usagedays":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -usage -days 2 -o "+additionalFolder+"usagedays.csv";
						returnValue[6]=additionalFolder+"usagedays.csv";
						returnValue[5]=getDays("reportfrom");
						break;

					case "deviceusagedays":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -usage -days 2 -o "+additionalFolder+"deviceusagedays.csv";
						returnValue[6]=additionalFolder+"deviceusagedays.csv";
						returnValue[5]=getDays("reportfrom");
						break;

					case "usagedevicefromto":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -usage -from "+getDate("from") + " -to "+getDate("to") + " -o "+additionalFolder+"usagedevicefromto.csv";
						returnValue[6]=additionalFolder+"usagedevicefromto.csv";
						returnValue[5]=getDate("reportfrom")+","+getDate("to");
						break;

					case "connect":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -run " + appName;
						break;

					case "install":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -install " + appName;
						break;

					case "uninstall":
		                command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -device " + deviceSelected + " -uninstall " + appName;
		                break;

					case "disable":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -disable";
						break;

					case "enable":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -enable " + deviceSelected;
						break;

					case "release":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -release";
						break;

					case "renametester":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -renamedevice DeviceTesting";
						break;

					case "deletetester":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -deleteapp deviceControl";
						break;

					case "run":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -run"+ " "+ "\"" + "deviceControl" +"\"";
						break;

					case "client":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -client "+ client+" -run"+ " "+ "\"" + "deviceControl" +"\"";
						break;

					case "forward":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -device " + deviceSelected + " -forward "+ 10919+" "+ devicePort+"\"";
						break;

					case "apitoken":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + cliCmdAuthString + " -apitoken";
						break;

					case "adminapitoken":
						isEventSuccessful=true;
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + "admin" + " " + "deviceconnect" + " -apitoken ";
						break;

					case "empty":
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " +  cliCmdAuthString + " ";
						break;

					default:
						command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + dicCommon.get("EmailAddress") + " " + dicCommon.get("Password") + " -device " + deviceSelected + " -retain -install \"deviceControl\" -autoconnect \"deviceControl\"";
						break;
					}

					AddToDictionary(dicOutput, "executedCommand", command);
					//*** connecting the Device with DeviceViewer using CMD *** 

				AddToDictionary(dicOutput, "executedCommand", command);
				//*** connecting the Device with DeviceViewer using CMD *** 

					Runtime runtime = Runtime.getRuntime();
					InputStream is =   runtime.exec(command).getInputStream();
					if(CLIOption.equals("history") || (CLIOption.equals("log")))
					{
						Thread.sleep(30000);
					}
					else if(CLIOption.contains("userinfo"))
					{
						Thread.sleep(40000);
					}
					else
					{
						Thread.sleep(10000);
					}
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					if(!(br.ready()))
					{
						returnValue[0] = null;
						returnValue[2] = true;
					}
					else
					{
						if(CLIOption.toLowerCase().equals("devicelist") || CLIOption.toLowerCase().equals("devicelistapikeyauth"))
						{
							flag=true;
							while ((line = br.readLine()) != null)
							{
								if(line.contains("Online:") || line.contains("Offline:"))
								{
									continue;
								}
								if(!(line.contains("Enabled") || (line.contains("Disabled"))))
								{
									flag=false;
									returnValue[0] = line;
									returnValue[2] = false;
									break;
								}
							}
							if(flag)
							{
								returnValue[0] ="True";
								returnValue[2] = true;
							}
						}
						else if(CLIOption.toLowerCase().equals("devicelistall"))
						{
							int count=0;
							while ((line = br.readLine()) != null)
							{
								if(line.contains("Online:") || line.contains("Offline:"))
								{
									continue;
								}
								if(line.contains("Enabled") || line.contains("Disabled"))
								{
									count++;
								}
							}
							if(count>0)
							{
								returnValue[0] =count;
								returnValue[2] = true;
							}
							else
							{
								returnValue[0] =count;
								returnValue[2] = false;
							}
						}
						else if(CLIOption.toLowerCase().equals("uploadtester") || CLIOption.toLowerCase().equals("renametester") || CLIOption.toLowerCase().equals("deletetester") || CLIOption.toLowerCase().equals("importtester") || CLIOption.toLowerCase().equals("userexporttester") || CLIOption.toLowerCase().equals("resetrebootuninstall"))
						{
							while ((line = br.readLine()) != null)
							{
								if(line.contains("User does not have required entitlement") || line.contains("Last method was SaveApplication"))
								{
									flag=true;
									returnValue[0] = "User does not have required entitlement";
									returnValue[2] = true;
									break;
								}
							}
							if(!flag)
							{
								returnValue[0] ="Exception did not occurred";
								returnValue[2] = false;
							}
						}
						else if(CLIOption.toLowerCase().equals("incorrecttimezone") )
						{
							while ((line = br.readLine()) != null)
							{
								if(line.contains("System.NullReferenceException: Unable to find timezone"))
								{
									flag=true;
									returnValue[0] = line;
									returnValue[2] = true;
									break;
								}
							}
							if(!flag)
							{
								returnValue[0] ="Exception did not occurred";
								returnValue[2] = false;
							}
						}
						else if(CLIOption.toLowerCase().equals("userexport") )
						{
							while ((line = br.readLine()) != null)
							{
								if(line.contains("File in use"))
								{
									flag=false;
									returnValue[0] = line;
									returnValue[2] = false;
									break;
								}
							}
							if(flag)
							{
								returnValue[0] ="Exported successfully";
								returnValue[2] = true;
							}
						}
						else if(CLIOption.toLowerCase().equals("importwithwrongfilepath") )
						{
							while ((line = br.readLine()) != null)
							{
								if(line.contains("File not found"))
								{
									flag=false;
									returnValue[0] = line;
									returnValue[2] = false;
									break;
								}
							}
							if(flag)
							{
								returnValue[0] ="Imported successfully";
								returnValue[2] = true;
							}
						}
						else if(CLIOption.toLowerCase().equals("importinvalidcsv") )
						{
							while ((line = br.readLine()) != null)
							{
								if(line.contains("Boolean value expected"))
								{
									flag=false;
									returnValue[0] = line;
									returnValue[2] = false;
									break;
								}
							}
							if(flag)
							{
								returnValue[0] ="Imported successfully";
								returnValue[2] = true;
							}
						}
						else if(CLIOption.toLowerCase().equals("usagejsoncmd") )
						{
							while ((line = br.readLine()) != null)
							{
								if(line.contains("Asia/Calcutta"))
								{
									flag=true;
									returnValue[0] = "Asia/Calcutta";
									returnValue[2] = true;
									break;
								}
							}
							if(!flag)
							{
								returnValue[0] ="Usage is in different time format than local";
								returnValue[2] = false;
							}
						}
						else if(CLIOption.toLowerCase().equals("history") || CLIOption.toLowerCase().equals("usage") )
						{
							while ((line = br.readLine()) != null)
							{
								if(line.contains("Device"))
								{
									flag=true;
									returnValue[0] = line;
									returnValue[2] = true;
									break;
								}
							}
							if(!flag)
							{
								returnValue[0] ="History/Usage not printed on console";
								returnValue[2] = false;
							}
						}
						else if(CLIOption.toLowerCase().equals("timezones") )
						{
							int count=0;
							while ((line = br.readLine()) != null)
							{
								count++;
								flag=true;
								returnValue[0] = count;
							}
							if(!flag)
							{
								returnValue[0] =count;
								returnValue[2] = false;
							}
							else
							{
								returnValue[2] = true;
							}
						}
						else if(CLIOption.toLowerCase().equals("adminapitoken") )
						{
							while ((line = br.readLine()) != null)
							{
								returnValue[0] = line;
								returnValue[2] = true;
								break;
							}
						} else if(CLIOption.toLowerCase().equals("empty"))
						{
							line=br.readLine();
							returnValue[0] = line;
							returnValue[2] = true;
						} 
						else
						{
							while ((line = br.readLine()) != null)
							{
								returnValue[0] = line;
							}
							returnValue[2] = true;
						}
					}


					// *** Read the task lists from Window Task Manager ***
					Thread.sleep(10000);  
					String processName="";
					if(CLIOption.toLowerCase().equals("forward"))
					{
						processName="MobileLabs.DeviceConnect.Cli.exe";
					}
					else
					{
						processName="MobileLabs.deviceViewer.exe";
					}
					if(!client.equals("web"))
					{
						Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
						BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
						while ((line = reader.readLine()) != null) 
						{
							line = line.substring(1);
							if (line.startsWith(processName)) 
							{
								returnValue[1] = line;
								returnValue[4] = true;
								break;
							}
						}
					}
					else
					{
						String[] winHandler=WindowNames.WindowNames();
						for(String title:winHandler)
						{
							if(title==null)
							{
								continue;
							}
							if(title.length()<0)
							{
								continue;
							}
							else
							{
								if(title.contains(deviceName))
								{
									deviceName=title;
									flag=true;
									break;
								}
							}
						}
						if(flag)
						{
							returnValue[1] = deviceName+" - deviceConnect opened in web viewer";
							returnValue[4] = true;
						}
						else
						{
							returnValue[1] = deviceName+" - deviceConnect browser not found";
							returnValue[4] = false;
						}
					}

				}
				catch (RuntimeException e)
				{
					returnValue[2] = false;
					strErrMsg_AppLib = "ExecuteCLICommand---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
				} catch (Exception e)
				{
					returnValue[2] = false;
					strErrMsg_AppLib = "ExecuteCLICommand---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
				}

				return returnValue;
			}
	    
	    
	//<p>This Function returns the detail value for selected device by device display name</p>
	//<p>Permitted values for detailName - devicename, devicereservation, devicemodel, deviceplatform, devicestatus</p>
	//
	//<!--Created By : Vinita Mahajan-->
	//@param deviceName Display Name of the device
	//@param detailName Detail Name which User wants to get for particular device
	//@param strView From which View to verify and fetch the value
	//@return 
	//
	public final String GetSingleDeviceDetails(String deviceName, String detailName)
	{
		return GetSingleDeviceDetails(deviceName, detailName, "grid");
	}

	public final String GetSingleDeviceDetails(String deviceName, String detailName, String strView)
	{
		strErrMsg_AppLib = "";
		String strErr = "";
		WebElement element;
		boolean isEventSuccessful = false;
		String xpathDevicesHolder = "";
		String DevicesDetail = "";
		try
		{
			if (PerformAction("eleDevicesHeader", Action.WaitForElement))
			{
				if (strView.toLowerCase().equals("list"))
				{
					xpathDevicesHolder = GenericLibrary.dicOR.get("eleDeviceName_ListView");
				}
				else if (strView.toLowerCase().equals("grid"))
				{
					xpathDevicesHolder = GenericLibrary.dicOR.get("eleDeviceName_CardsView");
				}

				else if (strView.toLowerCase().equals("both"))
				{
					//First check for grid view
					DevicesDetail = GetSingleDeviceDetails(deviceName, detailName, "grid");
					if (DevicesDetail.equals(""))
					{
						strErr = strErrMsg_AppLib;
					}
					// now open list view and verify for list view
					isEventSuccessful = PerformAction("lnkListView", Action.Click);
					if (isEventSuccessful)
					{
						DevicesDetail = GetSingleDeviceDetails(deviceName, detailName, "list");
						if (DevicesDetail.equals(""))
						{
							strErr = strErr + strErrMsg_AppLib;
						}
					}
					else
					{
						strErr = strErrMsg_AppLib + "Could not click on icon for List view.";
					}
					if (strErr.equals(""))
					{
						return DevicesDetail;
					}
					else
					{
						strErrMsg_AppLib = strErr;
						return DevicesDetail;
					}
				}


				//Verifying devices are displayed
				if (VerifyMessage_On_Filter_Selection())
				{
					throw new RuntimeException("deviceConnect currently has no configured devices or your filter produced no results.");
				}
				StopAutoRefresh(strView);
				int noOfDevices = getelementCount(xpathDevicesHolder.replace("[__INDEX__]", ""));
				for (int i = 1; i <= noOfDevices; i++)
				{
					element = genericLibrary.GetElement(xpathDevicesHolder.replace("__INDEX__", (new Integer(i)).toString()));
					if (deviceName.equals(element.getText()))
					{
						String strValue = GetDeviceDetailInGridAndListView(i, detailName, strView);
						if ( ! strValue.equals(""))
						{
							DevicesDetail = strValue;
						}
						else
						{
							throw new RuntimeException(strErrMsg_AppLib);
						}
					}
				}
			}
			else
			{
				throw new RuntimeException("Devices page is not displayed");
			}
		}
		catch (RuntimeException e)
		{
			DevicesDetail = "";
			strErrMsg_AppLib = "GetSingleDeviceDetails---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return DevicesDetail;
	}

	//
	//This is only for verifying the Mobile Labs Trust and DeviceConnect CLI zip Folder
	//This works for only zip files which dont have any folders
	// 
	//\<!--Created By : Vinita Mahajan-->
	//\@param zipFilePath Downloaded path of the Zip file
	//\@param filesToBeVerified Verify all the Files under the zip folder : as a string array
	//@return 
	// TODO OWN : To be implemented
	public final boolean VerifyDownloadedZip(String zipFilePath, String[] filesToBeVerified)
	{
		boolean flag = false;
		strErrMsg_AppLib = "";
		String fileExtn = "zip";
		String fileName = "MobileLabsTrust";
		String fileNameIE = "";
		String[] ItemsNotFound = new String[] { };
		List<String> FailList = null;
		try
		{

			/* This code can be utilized
        	File zip = new File(zipFilePath);
        	ZipFile zip1 = new ZipFile(zipFilePath);
        	zip1.getInputStream(entry)
			 */

			/*String strTempPath = Path.GetTempPath();
            String[] strPath = strTempPath.split("[\\\\]", -1);
            if (dicCommon.get("BrowserName").equals("IE"))
            {
                fileName = fileNameIE;
            }
            File zip = new File(strPath[0] + "\\" + strPath[1] + "\\" + strPath[2] + "\\Downloads");
            File[] files = di.GetFiles().OrderByDescending(p -> p.CreationTime).ToArray();
            for (File file : files)
            {
                file.Attributes = FileAttributes.Normal;
                zipFilePath = file.getPath();
                break;
            }

            ZipInputStream zip = new ZipInputStream(File.OpenRead(zipFilePath));

            ZipEntry item;
            int i = 0, j = 0;
            for (j = 0; j < filesToBeVerified.length; j++)
            {
                while ((item = zip.GetNextEntry()) != null)
                {
                    if (item.Name.startsWith(filesToBeVerified[j]))
                    {
                        ItemsNotFound[j] = filesToBeVerified[j];
                        i++;
                        break;
                    }
                }
            }

            // This saves the values in a List which are not found in the Itemstobeverified
            List list1 = filesToBeVerified.ToList(), list2 = ItemsNotFound.ToList();
            for (int x = 0; x < list1.size(); x++)
            {
                if (!(list1.contains(list2.get(x))))
                {
                    FailList.put(list2.get(x).toString() + ", ");
                }
            }
            if (i == j)
            {
                flag = true;
            }
            else
            {
                strErrMsg_AppLib = "All Items are not found" + FailList.toString();
            }
            if ( ! strErrMsg_AppLib.equals(""))
            {
                flag = false;
                strErrMsg_AppLib = "VerifyDownloadedZip-- Following files are extra in the downloaded zip folder: " + strErrMsg_AppLib;
                //writeToLog("VerifyDownloadedZip-- " + strErrMsg_AppLib);
            }*/
		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "VerifyDownloadedZip---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			writeToLog("VerifyDownloadedZip --" + e.getStackTrace());
		}
		return flag;
	}

	//This Function verified that the Devices Multifunction dropdown options are present
	//
	//<!--Created By : Vinita Mahajan-->
	//@param objectNameToVerify List of string to verify under devices dropdown
	//@param status Status of the device on which the verification is to be made : available, disabled, offline, inuse
	//@param index OPTIONAL - Index of the device on the devices page : Pass 1 if want to verify for first device on page - Default value is 1
	//@param sView OPTIONAL - grid, list, both : By default it takes grid view
	//@return 
	//

	public final boolean VerifyDeviceOptions(ArrayList<String> objectNameToVerify, String status, int index)
	{
		return VerifyDeviceOptions(objectNameToVerify, status, index, "grid");
	}
	public final boolean VerifyDeviceOptions(ArrayList<String> objectNameToVerify, String status)
	{
		return VerifyDeviceOptions(objectNameToVerify, status, 1, "grid");
	}

	public final boolean VerifyDeviceOptions(ArrayList<String> objectNameToVerify, String status, int index, String sView)
	{
		strErrMsg_AppLib = "";
		boolean isEventSuccessful = false;
		WebElement element;
		List<WebElement> DropdownObjects = null; //List of WebElements for connect button DropDown
		List<String> DropdownValues = new ArrayList<String>(); // List of Text present on Connect button dropdown elements
		String DropDownListxpath = "";
		String xpathDevicesHolder = "", strDeviceName = "";
		if (PerformAction("eleDevicesHeader", Action.WaitForElement))
		{
			if (sView.toLowerCase().equals("list"))
			{
				xpathDevicesHolder = dicOR.get("eleDevicesHolderListView");
				//DropDownListxpath = xpathDevicesHolder + "/td[5]/div/ul/li";
			}
			else if (sView.toLowerCase().equals("grid"))
			{
				xpathDevicesHolder = dicOR.get("eleDevicesHolderGridView");
				//DropDownListxpath = "(" + xpathDevicesHolder + "//..//following-sibling::ul[@class='dropdown-menu'])";
			}
			// verification in case user wants to verify on both grid and list view
			else if (sView.toLowerCase().equals("both"))
			{
				String strErr = "";
				//First check for grid view
				//isEventSuccessful = VerifyPerformActionsOnDevicesPage(objectNameToVerify, sVerificationObjectValue,status,UserRole,index, "grid");
				isEventSuccessful = VerifyDeviceOptions(objectNameToVerify, status, index, "grid");
				if (!isEventSuccessful)
				{
					strErr = strErrMsg_AppLib;
				}
				// now open list view and verify for list view
				isEventSuccessful = PerformAction("lnkListView", Action.Click);
				if (isEventSuccessful)
				{
					//isEventSuccessful = VerifyPerformActionsOnDevicesPage(objectNameToVerify, sVerificationObjectValue,status,UserRole,index,"list");
					isEventSuccessful = VerifyDeviceOptions(objectNameToVerify, status, index, "list");
					if (!isEventSuccessful)
					{
						strErr = strErr + strErrMsg_AppLib;
					}
				}
				else
				{
					strErr = strErrMsg_AppLib + "Could not click on icon for List view.";
				}
				if (strErr.equals(""))
				{
					return true;
				}
				else
				{
					strErrMsg_AppLib = strErr;
					return false;
				}
			}


			///#################### Verification for Specifically 'Grid' or 'list' view ###################//
			try
			{
				//Verifying devices are displayed
				if (VerifyNoRowsWarningOnTable())
				{
					throw new RuntimeException("deviceConnect currently has no configured devices or your filter produced no results.");
				}
				//StopAutoRefresh(sView);
				int noOfDevices = getelementCount(xpathDevicesHolder); // Get number of devices' rows/cards displayed in given view
				for (int i = 1; i < noOfDevices; i++)
				{
					if (i == index)
					{
						//element = GetElement(xpathDevicesHolder + "[" + i + "]" + "//..//following-sibling::button[contains(@class,'btn btn-info dropdown-toggle')]");
						if (sView.toLowerCase().equals("list"))
						{
							element = GetElement(xpathDevicesHolder + "[" + (i+1) + "]" + "/td[5]/div/button[2]");
							strDeviceName = GetTextOrValue(dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(i)).toString()), "text");
						}
						else
						{
							element = GetElement("(" + xpathDevicesHolder + "//..//following-sibling::button[contains(@class,'btn btn-info dropdown-toggle')])" + "[" + i + "]");
							strDeviceName = GetTextOrValue(dicOR.get("eleDeviceName_CardsView").replace("__INDEX__", (new Integer(i)).toString()), "text");
						}
						if (element != null)
						{

							if (sView.toLowerCase().equals("list"))
							{
								isEventSuccessful = PerformAction(xpathDevicesHolder + "[" + (i+1) + "]" + "/td[5]/div/button[2]", Action.Click);
							}
							else
							{
								isEventSuccessful = PerformAction("(" + xpathDevicesHolder + "//..//following-sibling::button[contains(@class,'btn btn-info dropdown-toggle')])" + "[" + i + "]", Action.Click);
							}
							if (isEventSuccessful)
							{
								DropdownObjects = getelementsList(xpathDevicesHolder + "[" + (i+1) + "]" + "/td[5]/div/ul/li");
								for (int j = 0; j < DropdownObjects.size(); j++)
								{
									DropdownValues.add(DropdownObjects.get(j).getText());
								}
								if (DropdownValues.containsAll(objectNameToVerify)) //.SequenceEqual(objectNameToVerify)
								{
									isEventSuccessful = true;
								}
								else
								{
									isEventSuccessful = false;
									strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
								}
							}
							else
							{
								DropdownObjects = getelementsList(xpathDevicesHolder + "[" + (i+1) + "]" + "/td[5]/div/ul/li");
								if (DropdownObjects == null)
								{
									isEventSuccessful = true;
								}
								else
								{	
									isEventSuccessful = false;
									strErrMsg_AppLib = strErrMsg_AppLib + strDeviceName + ", ";
								}
							}

						}
						else
						{
							throw new RuntimeException("Connect Button Dropdown Element " + xpathDevicesHolder + "[" + i + "] is not found in page.");
						}
					}
				}
				if (strErrMsg_AppLib.equals("") || strErrMsg_AppLib.contains("No warning message displayed on table"))
				{
					isEventSuccessful = true;
				}
				else
				{
					throw new RuntimeException("DropDown Values for device " + strDeviceName + "is not displayed or is not correct: <br/>" + " - " + strErrMsg_AppLib);
				}
			}
			catch (RuntimeException e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "VerifyDeviceOptions---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return isEventSuccessful;
		}
		else
		{
			isEventSuccessful = false;
			strErrMsg_AppLib = "VerifyDeviceOptions---" + "Devices page is not displayed";
			return isEventSuccessful;
		}
	}

	// It verifies if all the apps displayed are of the given platform OR no cell is empty inside the applications page.
	//
	//<!--Modified By : Mandeep Mann-->
	//@param sVerificationObjectName It takes values "platform" and "cellvalues"
	//@param sVerificationObjectValue For "platform", it accepts values : "Android" and "iOS" and verifies that only the apps with given OS platform are displayed.
	//<p>For "cellvalues", it requires no value and verifies that no cell is empty for any application displayed.</p>
	//@param sView
	//@return 
	//

	public final boolean VerifyAppDetailsInListView(String sVerificationObjectName)
	{
		return VerifyAppDetailsInListView(sVerificationObjectName, "");
	}
	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool VerifyAppDetailsInListView(string sVerificationObjectName, string sVerificationObjectValue = "")
	public final boolean VerifyAppDetailsInListView(String sVerificationObjectName, String sVerificationObjectValue)
	{
		boolean flag = false;
		String TableRows_Xpath = "";
		int rowCount = 0;
		int columnsCount = 0;
		String errorColumnIndex = "", errorRows = "";
		strErrMsg_AppLib = "";

		//Modifying ot correct case : 
		if (sVerificationObjectValue.toLowerCase().equals("android"))
		{
			sVerificationObjectValue = "Android";
		}
		else if (sVerificationObjectValue.toLowerCase().equals("ios"))
		{
			sVerificationObjectValue = "iOS";
		}
		try
		{
			if (PerformAction("eleApplicationsHeader", Action.isDisplayed)) // check if applications page is displayed
			{
				flag = true;
			}
			else
			{
				throw new RuntimeException("Applications page not displayed.");
			}

			// If no apps are displayed at the moment then return true and exit. Also, write to logs file that there were no applications
			if(PerformAction("eleNoTableRowsWarning",Action.isDisplayed))
			{
				if (GetTextOrValue("eleNoTableRowsWarning", "text").contains("No applications match your filter criteria")) //Check if there are applications uploaded to system.
				{
				throw new RuntimeException("No applications match your filter criteria.");
				}
			}
			//If applications page is open, then proceed further and verify that for all apps, the sVerificationObjectName has sVerificationObjectValue as value
			TableRows_Xpath = dicOR.get("eleAppTableRows");
			rowCount = getelementCount(TableRows_Xpath); // Count of all the rows displayed at the moment;
			if (rowCount != 0)
			{
				switch (sVerificationObjectName)
				{
				case "platform":
					for (int i = 1; i <= rowCount; i++)
					{
						if (!GetTextOrValue(TableRows_Xpath + "[" + i + "]//td[2]", "text").equals(sVerificationObjectValue))
						{
							errorRows = errorRows + i + ",";
						}
					}
					if ( ! errorRows.equals("")) //Throw exception if there are some rows with wrong platform.
					{
						throw new RuntimeException("Platform is not correct for apps at row number  " + errorRows);
					}
					break;
				case "cellvalues":
					for (int i = 1; i <= rowCount; i++) //For each row, --
					{
						errorColumnIndex = "";
						columnsCount = getelementCount(TableRows_Xpath + "[" + i + "]//td"); //--Get the number of columns.
						if (columnsCount != 0) // If column count is 0, then put to error message that no columns exist for this particular row
						{
							for (int j = 1; j <= columnsCount; j++) // Iterate over all the columns of this row--
							{
								if ((GetTextOrValue(TableRows_Xpath + "[" + i + "]//td[" + j + "]", "text").isEmpty())) //--and verify if any cell is empty or white space
								{
									errorColumnIndex = errorColumnIndex + j + ",";
								}
							}
							if ( ! errorColumnIndex.equals("")) //Check how to replace __AND__ with new line operator to display new line in report.
							{
								strErrMsg_AppLib = strErrMsg_AppLib + " _ AND_ Column numbers  " + errorColumnIndex + " are empty for app at row number: " + i;
							}
						}
						else
						{
							strErrMsg_AppLib = strErrMsg_AppLib + "_AND_ No columns exist for row number  " + i;
						}
					}
					if ( ! strErrMsg_AppLib.equals(""))
					{
						throw new RuntimeException(strErrMsg_AppLib);
					}
					break;
				}
			}
			else
			{
				throw new RuntimeException("No rows found on reservations page.");
			}
		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "VerifyAppDetailsInListView---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			//writeToLog(strErrMsg_AppLib);
		}

		System.out.println("Verified all values in all columns ... there are no empty values");
		return flag;
	}

	//This method will fetch and return the list of drop down options
	//
	//<!--Created By : Hitesh Ghai-->
	//@param dropdownName
	//@return 
	//
	public final List<String> getDropDownOptions(String dropdownName)
	{
		strErrMsg_AppLib = "";
		List<String> optionsFound = new ArrayList<String>();
		List<WebElement> DropdownObjects = new ArrayList<WebElement>();
		try
		{
			switch (dropdownName)
			{
			case "eleMenuHolder":
				DropdownObjects = getelementsList(dicOR.get("eleMenubtnOptions"));
				break;

			default:
				if (PerformAction(dicOR.get("eleDropdownFilter").replace("__FILTER__", dropdownName), Action.Click))
				{
					DropdownObjects = getelementsList(dicOR.get("eleAllDropdownOptions").replace("__FILTER__", dropdownName));
				}
				else
				{
					throw new RuntimeException("Could not click on '" + dropdownName + "' filter.");
				}
				break;
			}
			for (WebElement optionSelect : DropdownObjects)
			{
				optionsFound.add(optionSelect.getAttribute("text"));
			}
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "getDropDownOptions---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			//writeToLog(strErrMsg_AppLib);
		}
		return optionsFound;
	}


	//Returns all elements in a one-dimensional list to a string containing &lt;&lt;&gt;&gt; delimiter separated values in string form.
	//
	//<!--Created By : Mandeep Mann-->
	//@param list list of type List&lt;object&gt;
	//@return String with comma separated elements(in string form)
	//
	public final String ListToString(List<Object> list)
	{
		String elementsString = "";
		strErrMsg_AppLib = "";
		try
		{
			for (Object ele : list)
			{
				elementsString = elementsString + "<<>>" + ele.toString();
			}
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "ListToString -- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return elementsString;
	}


	//Converts the given array of string values to single string containing all array values separated by ','
	//
	//<!-- Created by : Mandeep Kaur -->
	//<!-- Last modified : 9/2/2015 by Mandeep Kaur -->
	//@param arr String array that needs to be converted to single string.
	//@return Single string containing all array values each separated by ',' .
	//
	public final String ArrayToString(String[] arr)
	{
		String elementsString = "";
		strErrMsg_AppLib = "";
		try
		{
			for (String str : arr)
			{
				elementsString = elementsString + "," + str;
			}
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "ArrayToString -- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return elementsString;
	}

	//This function verifies the field value on Users page in Grid/List View
	//
	//<!--Created By : Vinita Mahajan-->
	//@param sVerificationObjectName Fields on Users card/list to verify : statusicon,firstname,lastname,emailid,userrole,edit
	//status icon: green, grey, firstname : exepcted value,lastname : exepceted value, emailid : exepcetd value,userrole, edit button checks only the existence
	//@param sVerificationObjectValue Value which is expected
	//@param sView View in which to be verified
	//@return 
	//
	public final boolean VerifynUsersPage(String sVerificationObjectName, String sVerificationObjectValue)
	{
		return VerifynUsersPage(sVerificationObjectName, sVerificationObjectValue, "list");
	}
	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool VerifynUsersPage(string sVerificationObjectName, string sVerificationObjectValue, string sView = "grid")
	public final boolean VerifynUsersPage(String sVerificationObjectName, String sVerificationObjectValue, String sView)
	{
		strErrMsg_AppLib = "";
		String[] arrSplitString;
		boolean isEventSuccessful = false;
		WebElement element;
		String xpathDevicesHolder = "", strUserName = "", text = "", srcAttribute = "";
		try
		{
			if (PerformAction("eleUsersHeader", Action.isDisplayed))
			{
				if (sView.toLowerCase().equals("list"))
				{
					xpathDevicesHolder = dicOR.get("eleUsersHolderListView");
				}
				else if (sView.toLowerCase().equals("grid"))
				{
					xpathDevicesHolder = dicOR.get("eleUsersHolderGridView");
				}
				// verification in case user wants to verify on both grid and list view
				else if (sView.toLowerCase().equals("both"))
				{
					String strErr = "";
					//First check for grid view
					isEventSuccessful = VerifynUsersPage(sVerificationObjectName, sVerificationObjectValue, "grid");
					if (!isEventSuccessful)
					{
						strErr = strErrMsg_AppLib;
					}
					// now open list view and verify for list view
					isEventSuccessful = PerformAction("lnkListView", Action.Click);
					if (isEventSuccessful)
					{
						isEventSuccessful = VerifynUsersPage(sVerificationObjectName, sVerificationObjectValue, "list");
						if (!isEventSuccessful)
						{
							strErr = strErr + strErrMsg_AppLib;
						}
					}
					else
					{
						strErr = strErrMsg_AppLib + "Could not click on icon for List view.";
					}
					if (strErr.equals(""))
					{
						return true;
					}
					else
					{
						strErrMsg_AppLib = strErr;
						return false;
					}
				}
				/////////////////## Verification for "both" ends. ##///////////////////////

				///#################### Verification for Specifically 'Grid' or 'list' view ###################//
				int noOfUsers = getelementCount(xpathDevicesHolder); // Get number of devices' rows/cards displayed in given view
				//System.out.println(noOfUsers);
				for (int i = 1; i <noOfUsers; i++)
				{
					element = GetElement(xpathDevicesHolder + "[" + i + "]");
					if (element != null)
					{
						if (sView.toLowerCase().equals("list"))
						{
							strUserName = GetTextOrValue(dicOR.get("eleFirstNameUsers_ListView").replace("__INDEX__", (new Integer(i)).toString()), "text");
						}
						else
						{
							strUserName = GetTextOrValue(dicOR.get("eleFirstLastName_GridView").replace("__INDEX__", (new Integer(i)).toString()), "text");
						}
						// Now verify the value of the element required using different cases
						switch (sVerificationObjectName.toLowerCase())
						{
						case "statusiconandtext":
							if (sView.toLowerCase().equals("list"))
							{
								srcAttribute = getAttribute(dicOR.get("eleStatusIconUsers_ListView").replace("__INDEX__", (new Integer(i)).toString()), "class");
								if ((GetTextOrValue(dicOR.get("eleUserStatus_ListView").replace("__INDEX__", (new Integer(i)).toString()), "text").equals("Active")))
								{
									if (!srcAttribute.contains("status-icon dc-icon-user-status-active"))
									{
										strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
									}
								}
								else if ((GetTextOrValue(dicOR.get("eleUserStatus_ListView").replace("__INDEX__", (new Integer(i)).toString()), "text").equals("Inactive")))
								{
									if (!srcAttribute.contains("status-icon dc-icon-user-status-inactive"))
									{
										strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
									}
								}
							}
							else
							{
								srcAttribute = getAttribute(dicOR.get("eleStatusIconUsers_GridView").replace("__INDEX__", (new Integer(i)).toString()), "class");
								if ((GetTextOrValue(dicOR.get("eleUserStatus_GridView").replace("__INDEX__", (new Integer(i)).toString()), "text").equals("Active")))
								{
									if (!srcAttribute.contains("dc-icon-device-status-available"))
									{
										strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
									}
								}
								else if ((GetTextOrValue(dicOR.get("eleUserStatus_GridView").replace("__INDEX__", (new Integer(i)).toString()), "text").equals("Inactive")))
								{
									if (!srcAttribute.contains("dc-icon-device-status-offline"))
									{
										strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
									}
								}
							}
							if ( ! sVerificationObjectValue.equals(""))
							{
								return true;
							}
							break;

						case "statusicon":
							if (sView.toLowerCase().equals("list"))
							{
								srcAttribute = getAttribute(dicOR.get("eleUserStatus_ListView").replace("__INDEX__", "1"), "class");
							}
							else
							{
								srcAttribute = getAttribute(dicOR.get("eleUserStatus_GridView").replace("__INDEX__", "1"), "class");
							}
							if (sVerificationObjectValue.toLowerCase().contains("available") || sVerificationObjectValue.toLowerCase().contains("green"))
							{
								if (!srcAttribute.contains("dc-icon-device-status-available"))
								{
									strErrMsg_AppLib = strErrMsg_AppLib + strUserName;
								}
							}
							else if (sVerificationObjectValue.toLowerCase().contains("offline") || sVerificationObjectValue.toLowerCase().contains("grey"))
							{
								if (!srcAttribute.contains("dc-icon-device-status-offline"))
								{
									strErrMsg_AppLib = strErrMsg_AppLib + strUserName;
								}
							}
							break;

						case "firstname":
							if (sView.toLowerCase().equals("list"))
							{
								text = GetElement(dicOR.get("eleFirstNameUsers_ListView").replace("__INDEX__", (new Integer(i)).toString())).getText().toLowerCase();
								//System.out.println(text);
								if (!text.isEmpty())
								{
									if ( ! sVerificationObjectValue.toLowerCase().equals("exist"))
									{
										if (text.startsWith(sVerificationObjectValue.toLowerCase()))
										{
											strErrMsg_AppLib = "";
											isEventSuccessful = true;
											return isEventSuccessful;
										}
										else
										{
											strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
										}
									}
									else
									{
										isEventSuccessful = PerformAction(dicOR.get("eleFirstNameUsers_ListView").replace("__INDEX__", (new Integer(i)).toString()), Action.isDisplayed);
									}
								}
							}
							else
							{
								text = GetElement(dicOR.get("eleFirstLastName_GridView").replace("__INDEX__", (new Integer(i)).toString())).getText().toLowerCase().split(" ", 2)[0];
								if (!text.isEmpty())
								{
									if ( ! sVerificationObjectValue.toLowerCase().equals("exist"))
									{
										if (text.startsWith(sVerificationObjectValue.toLowerCase()))
										{
											strErrMsg_AppLib = "";
											isEventSuccessful = true;
											return isEventSuccessful;
										}
										else
										{
											strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
										}
									}
									else
									{
										isEventSuccessful = PerformAction(dicOR.get("eleFirstLastName_GridView").replace("__INDEX__", (new Integer(i)).toString()), Action.isDisplayed);
									}
								}
							}
							break;

						case "lastname":
							if (sView.toLowerCase().equals("list"))
							{
								text = GetElement(dicOR.get("eleLastNameUsers_ListView").replace("__INDEX__", (new Integer(i)).toString())).getAttribute("innerHTML");
								if (!text.isEmpty())
								{
									String temp = text.trim().split("</b>")[0];
									if ( ! sVerificationObjectValue.toLowerCase().equals("exist"))
									{
										if (temp.startsWith(sVerificationObjectValue.toLowerCase()))
										{
											strErrMsg_AppLib = "";
											isEventSuccessful = true;
											return isEventSuccessful;
										}
										else
										{
											strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
										}
									}
									else
									{
										isEventSuccessful = PerformAction(dicOR.get("eleLastNameUsers_ListView").replace("__INDEX__", (new Integer(i)).toString()), Action.isDisplayed);
									}
								}
							}
							else
							{
								arrSplitString = GetElement(dicOR.get("eleFirstLastName_GridView").replace("__INDEX__", Integer.toString(i))).getText().toLowerCase().split(" ", 0); // Splits the string by empty space character. split(" ", 0) specifies that the 
								text = arrSplitString[arrSplitString.length - 1]; // Gives the last value split from the above string.
								if (!text.isEmpty())
								{
									if ( ! sVerificationObjectValue.toLowerCase().equals("exist"))
									{
										if (text.startsWith(sVerificationObjectValue.toLowerCase()))
										{
											strErrMsg_AppLib = "";
											isEventSuccessful = true;
											return isEventSuccessful;
										}
										else
										{
											strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
										}
									}
									else
									{
										isEventSuccessful = PerformAction(dicOR.get("eleFirstLastName_GridView").replace("__INDEX__", (new Integer(i)).toString()), Action.isDisplayed);
									}
								}
							}
							break;

						case "email":
							if (sView.toLowerCase().equals("list"))
							{
								text = GetElement(dicOR.get("eleEmailIDUsers_ListView").replace("__INDEX__", (new Integer(i)).toString())).getAttribute("title");;
								if (!text.isEmpty())
								{
									String temp = text.trim().split("/")[0];
									if ( ! sVerificationObjectValue.toLowerCase().equals("exist"))
									{
										if (temp.startsWith(sVerificationObjectValue.toLowerCase()))
										{
											strErrMsg_AppLib = "";
											isEventSuccessful = true;
											return isEventSuccessful;
										}
										else
										{
											strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
										}
										//if (!text.StartsWith(sVerificationObjectValue.ToLower()))
										//    strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
									}
									else
									{
										isEventSuccessful = PerformAction(dicOR.get("eleEmailIDUsers_ListView").replace("__INDEX__", (new Integer(i)).toString()), Action.isDisplayed);
									}
								}
							}
							else
							{
								text = GetElement(dicOR.get("eleEmailIDUsers_GridView").replace("__INDEX__", (new Integer(i)).toString())).getText().toLowerCase();
								if (!text.isEmpty())
								{
									if ( ! sVerificationObjectValue.toLowerCase().equals("exist"))
									{
										if (text.startsWith(sVerificationObjectValue.toLowerCase()))
										{
											strErrMsg_AppLib = "";
											isEventSuccessful = true;
											return isEventSuccessful;
										}
										else
										{
											strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
										}
									}
									else
									{
										isEventSuccessful = PerformAction(dicOR.get("eleEmailIDUsers_GridView").replace("__INDEX__", (new Integer(i)).toString()), Action.isDisplayed);
									}
								}
							}

							if (!isEventSuccessful)
							{
								strErrMsg_AppLib = "'" + sVerificationObjectValue + "' is not present on  the UI.";
							}
							break;

						case "userrole":
							if (sView.toLowerCase().equals("list"))
							{
								isEventSuccessful = PerformAction(dicOR.get("eleRoleUsers_ListView").replace("__INDEX__", (new Integer(i)).toString()), Action.isDisplayed);
								if (!isEventSuccessful)
								{
									strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
								}
							}
							else
							{
								isEventSuccessful = PerformAction(dicOR.get("eleRoleUsers_GridView").replace("__INDEX__", (new Integer(i)).toString()), Action.isDisplayed);
								if (!isEventSuccessful)
								{
									strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
								}
							}
							break;

						case "edit":
							if (sView.toLowerCase().equals("list"))
							{
								isEventSuccessful = PerformAction(dicOR.get("btnEdit_ListView").replace("__INDEX__", (new Integer(i)).toString()), Action.isDisplayed);
								if (!isEventSuccessful)
								{
									strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
								}
							}
							else
							{
								isEventSuccessful = PerformAction(dicOR.get("btnEdit_CardView").replace("__INDEX__", (new Integer(i)).toString()), Action.isDisplayed);
								if (!isEventSuccessful)
								{
									strErrMsg_AppLib = strErrMsg_AppLib + strUserName + ", ";
								}
							}
							break;

						case "existenceofstatus": // This case checks if the given status's User exists on the UI or not, the moment it finds one, case breaks and returns "True"
							isEventSuccessful = false;
							strErrMsg_AppLib = "";
							if (sView.toLowerCase().equals("list"))
							{
								if (GetTextOrValue(dicOR.get("eleUserStatus_ListView").replace("__INDEX__", (new Integer(i)).toString()), "text").toLowerCase().startsWith(sVerificationObjectValue.toLowerCase()))
								{
									return true;
								}
								//if (!driver.FindElement(By.XPath(xpathDevicesHolder + "[" + i + "]/td[1]")).getText().ToLower().contains(sVerificationObjectValue))
								//    return true;
							}
							else
							{
								if (GetTextOrValue(dicOR.get("eleUserStatus_GridView").replace("__INDEX__", (new Integer(i)).toString()), "text").toLowerCase().startsWith(sVerificationObjectValue.toLowerCase()))
								{
									return true;
								}
								//if (!driver.FindElement(By.XPath(xpathDevicesHolder + "[" + i + "]//div[@class='location spec']")).getText().ToLower().contains(sVerificationObjectValue))
								//    return true;
							}
							if (!isEventSuccessful)
							{
								strErrMsg_AppLib = "'" + sVerificationObjectValue + "' is not present on  the UI.";
							}
							break;
						}
					}
					else
					{
						throw new RuntimeException("Element " + xpathDevicesHolder + "[" + i + "] is not found on page.");
					}
					//System.out.println(i);
				}
				if (strErrMsg_AppLib.equals(""))
				{
					isEventSuccessful = true;
				}
				else
				{
					throw new RuntimeException("Following Users' " + sVerificationObjectName + " is/are not displayed or is/are not correct: <br/>" + sVerificationObjectValue + " - " + strErrMsg_AppLib);
				}

			}
			else
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "Users page is not displayed";
				return isEventSuccessful;
			}
		}
		catch (RuntimeException e)
		{
			isEventSuccessful = false;
			strErrMsg_AppLib = "VerifynUsersPage---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return isEventSuccessful;
	}

	//Open details page of any application according to the options given
	//
	//<!--Created By : Mandeep Mann-->
	//@param strAppOption It takes values : first and appname
	//@param strValue For strAppOption= first, it should be "1"; For strAppOption=appname, it should be the name of application as displayed on the applications table.
	//@return True or False
	//

	public final boolean SelectApplication(String strAppOption)
	{
		return SelectApplication(strAppOption, "1");
	}
	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool SelectApplication(string strAppOption, string strValue = "1")
	public final boolean SelectApplication(String strAppOption, String strValue)
	{
		boolean flag = false;
		String strAppName = "", xpathAppsHolder = "", AppNameLink = "";
		strErrMsg_AppLib = "";
		//WebElement element = null, childElement= null;

		try
		{
			waitForPageLoaded();
			if (PerformAction("eleApplicationsHeader", Action.WaitForElement))
			{
				//Verifying some apps are displayed for the applied filter
				if(PerformAction("eleNoTableRowsWarning",Action.isDisplayed))
				{
					if (GetTextOrValue("eleNoTableRowsWarning", "text").contains("No applications match your filter criteria")) //Check if there are applications uploaded to system.
					{
					throw new RuntimeException("No applications match your filter criteria.");
					}
				}
				// cases for opening details page of an Application with given specification i.e. strAppOption
				switch (strAppOption)
				{
				case "first":
					AppNameLink = dicOR.get("eleAppNameAppTable").replace("__APP_INDEX__", "1");
					//else if (dicCommon["BrowserName"].ToLower().Equals("ie"))
					//    AppNameLink = "css=.card-detail-link";
					break;
				case "appame":
					AppNameLink = dicOR.get("eleAppName_AppsPage").replace("__APPNAME__", strValue);
					break;
				}
				//Get app name and put it to dicOutput
				strAppName = GetTextOrValue(AppNameLink, "text");
				if (!AddToDictionary(dicOutput, "selectedAppName", strAppName))
				{
					throw new RuntimeException("SelectApplication -- Unable to put appName to dicOutput dictionary.");
				}

				//Click on app name and verify correct app details page is opened.
				if ( ! strAppName.equals(""))
				{
					waitForPageLoaded();
					flag = PerformAction(AppNameLink, Action.Click);
					if (flag)
					{
						waitForPageLoaded();
						flag = PerformAction("eleAppNameDisplay", Action.WaitForElement);
						if (flag)
						{
							flag = GetTextOrValue("eleAppNameDisplay", "text").equals(strAppName);
							if (!flag)
							{
								throw new RuntimeException("Correct App details page is not opened for app: " + strAppName + ".");
							}
						}
						else
						{
							throw new RuntimeException("app details page not opened after clicking on app name link for app : " + strAppName + ".");
						}
					}
					else
					{
						throw new RuntimeException("Could not click  on app name link (cards view on apps page) for app : " + strAppName + " view.");
					}
				}
				else
				{
					throw new RuntimeException("Could not find any app with the given parameters.");
				}
			}
			else
			{
				throw new RuntimeException("apps page is not displayed");
			}
		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "SelectApplication---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return flag;
	}

	//This function verifies All Builds section
	//
	//<!--Created By : Mandeep Mann-->
	//@param sVerificationObjectName
	//@param sVerificationObjectValue
	//@return 
	//

	public final boolean verifyOnAllBuildsSection(String sVerificationObjectName)
	{
		return verifyOnAllBuildsSection(sVerificationObjectName, "");
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool verifyOnAllBuildsSection(string sVerificationObjectName, string sVerificationObjectValue = "")
	public final boolean verifyOnAllBuildsSection(String sVerificationObjectName, String sVerificationObjectValue)
	{
		boolean flag = false;
		String TableRows_Xpath = "";
		int rowCount = 0;
		int columnsCount = 0;
		String errorColumnIndex = "", errorRows = "";
		strErrMsg_AppLib = "";

		try
		{
			if (PerformAction(dicOR.get("eleEmbeddedTableHeaders").replace("__HEADER__", "All Builds"), Action.isDisplayed)) // check if applications page is displayed
			{
				flag = true;
			}
			else
			{
				throw new RuntimeException("All Builds section is not displayed.");
			}
			//// If no apps are displayed at the moment then return true and exit. Also, write to logs file that there were no applications
			//if (GetTextOrValue("eleNoTableRowsWarning", "text").contains("deviceConnect currently has no configured applications."))   //Check if there are applications uploaded to system.                
			//    throw new Exception("deviceConnect currently has no configured applications.");

			//If applications page is open, then proceed further and verify that for all apps, the sVerificationObjectName has sVerificationObjectValue as value
			TableRows_Xpath = dicOR.get("eleAllBuildsRows_AllBuilds");
			rowCount = getelementCount(TableRows_Xpath); // Count of all the rows displayed at the moment;
			if (rowCount != 0)
			{
				switch (sVerificationObjectName)
				{
				case "cellvalues":
					for (int i = 1; i <= rowCount; i++) //For each row, --
					{
						errorColumnIndex = "";
						columnsCount = getelementCount(TableRows_Xpath + "[" + i + "]//td"); //--Get the number of columns.
						if (columnsCount != 0) // If column count is 0, then put to error message that no columns exist for this particular row
						{
							for (int j = 1; j <= columnsCount; j++) // Iterate over all the columns of this row--
							{
								if ((GetTextOrValue(TableRows_Xpath + "[" + i + "]//td[" + j + "]", "text")).isEmpty()) //--and verify if any cell is empty or white space
								{
									errorColumnIndex = errorColumnIndex + j + ",";
								}
							}
							if ( ! errorColumnIndex.equals("")) //Check how to replace __AND__ with new line operator to display new line in report.
							{
								strErrMsg_AppLib = strErrMsg_AppLib + " _ AND_ Column numbers  " + errorColumnIndex + " are empty for app at row number: " + i;
							}
						}
						else
						{
							strErrMsg_AppLib = strErrMsg_AppLib + "_AND_ No columns exist for row number  " + i;
						}
					}
					if ( ! strErrMsg_AppLib.equals(""))
					{
						throw new RuntimeException(strErrMsg_AppLib);
					}
					break;

				case "deletebutton":
					//for (int i = 1; i <= rowCount; i++)                                           //For each row, --
					//{

					//    columnsCount = getelementCount(TableRows_Xpath + "[" + i + "]//td");      //--Get the number of columns.
					//    if (columnsCount != 0)                                                    // If column count is 0, then put to error message that no columns exist for this particular row
					//    {
					for (int j = 1; j <= rowCount; j++) // Iterate over all the columns of this row--
					{
						errorColumnIndex = "";
						flag = PerformAction(dicOR.get("eleInstallAppDropdown") + "[" + j + "]", Action.Click);
						if (flag)
						{
							if (sVerificationObjectValue.toLowerCase().equals("disabled"))
							{
								flag = !PerformAction(dicOR.get("eleDeleteOption_AppPage") + "[" + j + "]", Action.isDisplayed);
							}
							else if (sVerificationObjectValue.toLowerCase().equals("enabled"))
							{
								flag = PerformAction(dicOR.get("eleDeleteOption_AppPage") + "[" + j + "]", Action.isDisplayed);
							}

							if (!flag)
							{
								errorColumnIndex = errorColumnIndex + ", " + j;
							}
						}
						else
						{
							errorColumnIndex = errorColumnIndex + ", " + j;
						}
					}

					if ( ! errorColumnIndex.equals(""))
					{
						strErrMsg_AppLib = "Delete button is not " + sVerificationObjectValue + " for row numbers : " + errorColumnIndex;
					}
					//    }
					//    else
					//        strErrMsg_AppLib ="No columns exist for row number  " + i;
					//}
					if ( ! strErrMsg_AppLib.equals(""))
					{
						throw new RuntimeException(strErrMsg_AppLib);
					}
					break;
				}
			}
			else
			{
				throw new RuntimeException("No rows found on All Builds section under application details page.");
			}
		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "verifyOnAllBuildsSection---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			//writeToLog(strErrMsg_AppLib);
		}
		return flag;
	}

	//This function verifies the data under specified column is sorted correctly or not : ascending/descending
	//The columns containing any special character other than '-' is not handled.
	//
	//<!--Created By : Vinita Mahajan-->
	//@param TableContainerXpath Exact Identification of Table container on whose columns you need to perform sorting.
	//@param columnIndex Index of the columns which need to be sorted
	//@param SortByASCorDESC Expected type of sorting - Ascending : By Default Value, 'descending' value
	//@param NumericComparison If the Column values contains numeric values
	//@param rowElements name of element in OR containing xpath of the column elements whose sorting needs to be verified.
	//@return Returns boolean value : Data is sorted correctly or not
	//

	public final boolean VerifySortingAsc(String TableContainerXpath, int columnIndex, String SortByASCorDESC, String NumericComparison)
	{
		return VerifySortingAsc(TableContainerXpath, columnIndex, SortByASCorDESC, NumericComparison, "");
	}

	public final boolean VerifySortingAsc(String TableContainerXpath, int columnIndex, String SortByASCorDESC)
	{
		return VerifySortingAsc(TableContainerXpath, columnIndex, SortByASCorDESC, "no", "");
	}

	public final boolean VerifySortingAsc(String TableContainerXpath, int columnIndex)
	{
		return VerifySortingAsc(TableContainerXpath, columnIndex, "ascending", "no", "");
	}

	// TODO OWN : to be implemented (Vinita)
	public final boolean VerifySortingAsc(String TableContainerXpath, int columnIndex, String SortByASCorDESC, String NumericComparison, String rowElements)
	{
		List<WebElement> TableStatus = new ArrayList<WebElement>();
		List<String> ListDisplayed = new ArrayList<String>();
		List<String> ListSorted = new ArrayList<String>();
		List<String> ListSorted_int = new ArrayList<String>();
		boolean flag = false;
		strErrMsg_AppLib = "";

		try
		{
			/*
            if (rowElements.equals(""))
            {
                TableStatus = driver.findElements(By.xpath(TableContainerXpath + "//tr"));
                for (int i = 1; i < TableStatus.size(); i++)
                {
                    ListDisplayed.add(driver.findElement(By.xpath(TableContainerXpath + "//tr[" + (new Integer(i)).toString() + "]/td[" + (new Integer(columnIndex)).toString() + "]")).getText());
                }
            }
            else
            {
                TableStatus = getelementsList(dicOR.get(rowElements));
                for (int i = 1; i < TableStatus.size(); i++)
                {
                    ListDisplayed.add(TableStatus.get(i).getText());
                }
            }

            if (SortByASCorDESC.toLowerCase().equals("") || SortByASCorDESC.toLowerCase().equals("ascending"))
            {
                if (NumericComparison.toLowerCase().equals("yes"))
                {
                    for (String listdis : ListDisplayed)
                    {
                        if (listdis.equals("-"))
                        {
                            ListSorted_int.add(listdis.replace("-", "0"));
                        }
                        else
                        {
                            ListSorted_int.add(listdis);
                        }
                    }
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
                    ListSorted = ListSorted_int.OrderBy(q -> Integer.parseInt(q)).<String>ToList();
                }
                else
                {
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
                    ListSorted = ListDisplayed.OrderBy(q -> q).ToList();
                }

                if (ListSorted.SequenceEqual(ListSorted_int))
                {
                    flag = true;
                }
                else
                {
                    strErrMsg_AppLib = "List is not sorted correctly.";
                    flag = false;
                }

            }
            else if (SortByASCorDESC.toLowerCase().equals("descending"))
            {
                if (NumericComparison.toLowerCase().equals("yes"))
                {
                    for (String listdis : ListDisplayed)
                    {
                        if (listdis.equals("-"))
                        {
                            ListSorted_int.add(listdis.replace("-", "0"));
                        }
                        else
                        {
                            ListSorted_int.add(listdis);
                        }
                    }
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
                    ListSorted = ListSorted_int.OrderByDescending(q -> Integer.parseInt(q)).<String>ToList();
                }
                else
                {
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
                    ListSorted = ListDisplayed.OrderByDescending(q -> q).ToList();
                }

                if (ListSorted.SequenceEqual(ListSorted_int))
                {
                    flag = true;
                }
                else
                {
                    strErrMsg_AppLib = "List is not sorted correctly.";
                    flag = false;
                }
            }
            if (NumericComparison.equals("no"))
            {
                if (ListSorted.SequenceEqual(ListDisplayed))
                {
                    flag = true;
                }
                else
                {
                    strErrMsg_AppLib = "List is not sorted correctly.";
                    flag = false;
                }
            }*/
		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "VerifySortingAsc---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return flag;
	}
	//Verifies if there is warning message displayed on any page like applications, devices, reservations index page.
	//
	//<example>isEventSuccessful = !VerifyNoRowsWarningOnTable() && strErrMsg_AppLib.Equals("No warning message displayed on table.");
	//<p>This example verifies that there is not warning message on applications index page.</p></example>
	//@return True if correct warning is displayed, otherwise False
	//
	public final boolean VerifyNoRowsWarningOnTable()
	{
		boolean flag = false;
		strErrMsg_AppLib = "";
		String warningMessage = "";

		try
		{
			flag = PerformAction("eleNoTableRowsWarning", Action.isDisplayed);
			if (flag)
			{
				warningMessage = GetTextOrValue("eleNoTableRowsWarning", "text");
				flag = warningMessage.startsWith("deviceConnect currently has no configured");
				if (!flag)
				{
					throw new RuntimeException("Warning message is incorrect. It is : '" + warningMessage + "'. ");
				}
			}
			else if (PerformAction("eleNoTableRowsWarning", Action.isNotDisplayed))
			{
				strErrMsg_AppLib = "No warning message displayed on table.";
				flag=false;
			}
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "VerifyNoRowsWarningOnTable---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			flag=false;
		}
		return flag;
	}

	/**
	 *  This functions returns the battery status of the device. [Battery status text]
	 *  This function is internally called in VerifyOnDeviceDetailsPage() - Value cannot be null, expected value need to be used along with contains__
	 * @return Returns boolean and string value
	 * @author vinitam
	 */
	// TODO OWN : To be implemented (Find equivalent for Tuple return type)
	public final Object[] GetBatteryStatus()
	{
		boolean flag = false;
		strErrMsg_AppLib = "";
		String BatterytooltipValue ="";
		// String batteryStatus = "";
		//ArrayList<String> batteryStatus = new ArrayList<String>();
		try
		{
			PerformAction(dicOR.get("lblDeviceName_DeviceDetails"), Action.WaitForElement);
			flag = PerformAction(dicOR.get("lblDeviceName_DeviceDetails"), Action.isDisplayed);
			if (flag) // check if device details page is displayed
			{
				PerformAction("eleBatteryStatusIcon", Action.WaitForElement);
				flag = PerformAction("eleBatteryStatusIcon", Action.isDisplayed); // check if battery icon is displayed
				if(flag)
				{
					BatterytooltipValue = getAttribute("eleBatteryStatusIcon", "title");       
					flag = !BatterytooltipValue.isEmpty() && !BatterytooltipValue.equals(null);   //Check that the title attribute of battery icon is not empty or null
					if(!flag)
						throw new Exception("Title attribute for battery status icon is empty");
				}
				else
					throw new Exception("Battery icon is not displayed.");

			}
			else
				throw new Exception("Device Details page not displayed.");
		}
		catch (Exception e)
		{
			flag = false;
			strErrMsg_AppLib = "GetBatteryStatus---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return new Object[] {flag,BatterytooltipValue};//Pair<boolean.class[flag], String.class[batteryStatus]>; //Pair<Boolean, String>(flag, batteryStatus);
	}


	//This function creates reservation of type - Weekly
	//NOT COMPLETE YET 
	//<!--Created By : Vinita Mahajan-->
	//
	//@param weekday /
	//@param deviceName
	//@param everyWeekValue
	//@return 
	//

	public final boolean RSVD_CreateReservationWeekly(String[] weekday, String startdate, String enddate, String startTimeFormat, String endTimeFormat, String startTime, String endTime, String deviceName, String everyWeekValue)
	{
		return RSVD_CreateReservationWeekly(weekday, startdate, enddate, startTimeFormat, endTimeFormat, startTime, endTime, deviceName, everyWeekValue, false);
	}

	public final boolean RSVD_CreateReservationWeekly(String[] weekday, String startdate, String enddate, String startTimeFormat, String endTimeFormat, String startTime, String endTime, String deviceName)
	{
		return RSVD_CreateReservationWeekly(weekday, startdate, enddate, startTimeFormat, endTimeFormat, startTime, endTime, deviceName, "1", false);
	}

	public final boolean RSVD_CreateReservationWeekly(String[] weekday, String startdate, String enddate, String startTimeFormat, String endTimeFormat, String startTime, String endTime)
	{
		return RSVD_CreateReservationWeekly(weekday, startdate, enddate, startTimeFormat, endTimeFormat, startTime, endTime, "1", "1", false);
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool RSVD_CreateReservationWeekly(string[] weekday, string startdate, string enddate, string startTimeFormat, string endTimeFormat, string startTime, string endTime, string deviceName = "1", string everyWeekValue = "1", bool SelectFromCalendar = false)
	public final boolean RSVD_CreateReservationWeekly(String[] weekday, String startdate, String enddate, String startTimeFormat, String endTimeFormat, String startTime, String endTime, String deviceName, String everyWeekValue, boolean SelectFromCalendar)
	//, DateAndTime startdate, DateAndTime enddate, Tuple<string, string> startTime, Tuple<string, string> endTime, string deviceName = "1")
	{
		if (weekday == null)
		{
			throw new IllegalArgumentException("weekday");
		}
		boolean flag = false;
		strErrMsg_AppLib = "";
		String xPath, xPathReplaced,reservationerrromessage;
		Object[] values = new Object[3];
		try
		{

			flag = PerformAction("eleCreateRsrvtnHeader", Action.isDisplayed);
			if (flag)
			{
				flag = RSVD_SelectDevice(deviceName, true );
				if (flag)
				{
					flag = RSVD_SelectReservationType("Weekly");
					if (flag)
					{
						flag = PerformAction("eleEveryWeekInput_CreateReservation", Action.Type, everyWeekValue);
						if (flag)
						{
							flag = RSVD_SelectWeekDay(weekday);
							if (flag)
							{
								values = RSVD_SelectReservationDate1(startdate,enddate);
								flag = (boolean) values[0];
								//flag = RSVD_SelectReservationDate(startdate, enddate, "Current", "Current", SelectFromCalendar);
								if (flag)
								{
									values = RSVD_SelectReservationTime(startTimeFormat, endTimeFormat, startTime, endTime);
									flag = (boolean) values[0];
									reservationerrromessage = (String) values[1];
									if (flag)
									{
										flag=PerformAction("btnCreate_CreateReservation", Action.Click);
									}
									else
									{
										strErrMsg_AppLib = "Not able to select the start time and end time. " + reservationerrromessage;
									}
								}
							}
							else
							{
								strErrMsg_AppLib = "Not able to select the weekday/s.";
							}
						}
						else
						{
							strErrMsg_AppLib = "Not able to input value in Every n Weeks field.";
						}
					}
					else
					{
						strErrMsg_AppLib = strErrMsg_AppLib + "Not able to select Weekly from Repeats dropdown.";
					}

				}
				else
				{
					strErrMsg_AppLib = "Could not select the device from drop-down";
				}
			}
			else
			{
				strErrMsg_AppLib = "New Reservation page is not displayed.";
			}


		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "RSVD_CreateReservationWeekly---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return flag;
	}


	public final Object[] RSVD_CreateQuickReservationMonthly(String deviceName,String startDate,String endDate)
	{
		boolean flag = false;
		strErrMsg_AppLib = "";
		String returnValue = "";
		String endDateValue = "";
		try
		{
			flag = PerformAction("eleCreateRsrvtnHeader", Action.isDisplayed);
			if (flag)
			{
				flag = RSVD_SelectDevice(deviceName, true );
 				if (flag)
				{
 					flag = RSVD_SelectReservationType("Monthly");
					if (flag)
					{
						String [] dd=endDate.split("/");
						//startDateValue = (String) ((org.openqa.selenium.JavascriptExecutor)GenericLibrary.driver).executeScript("return document.querySelector('div.input-group.date.start-date-picker > input.form-control').value='07/25/16';");

						String queryStartDate = "document.querySelector('div.input-group.date.start-date-picker > input.form-control').value='" + startDate + "'";
						String startDateValue = (String) ((org.openqa.selenium.JavascriptExecutor)GenericLibrary.driver).executeScript("return " + queryStartDate);

						PerformAction("calendarWeeklyEndDate",Action.Click);
						PerformAction("nextmonthLocatorWeeklyenddateCalendar",Action.Click);
						List<WebElement> lidates=getelementsList("datesLocatorWeeklyenddateCalendar");
						for(int i=0;i<lidates.size();i++)
						{
						  if(lidates.get(i).getAttribute("class").contains("old"))
						  {
							  continue;
						  }
						  if(lidates.get(i).getText().equals(dd[0]))
						  {
							  lidates.get(i).click();
							  break;
						  }
						  
						}
						flag = PerformAction("eleDurationCol_CreateReservation",Action.WaitForElement);
						if(flag)
						{	
							String ProjectedSchedule = GetTextOrValue("eleDurationCol_CreateReservation");
							returnValue=ProjectedSchedule;
							if(ProjectedSchedule!=null)
							{
								flag=PerformAction("btnCreate_CreateReservation", Action.ClickUsingJS);
								if(!flag)
								{
									strErrMsg_AppLib = "Not able to click on Create Reservation button. " + strErrMsg_GenLib;
								}
							}
							else
							{
								strErrMsg_AppLib = "The current configuration results in no reservations.";
								flag = false;
							}
						}
						else
						{
							strErrMsg_AppLib = "Not able to click on the Enddate provided on EndDate Calendar. " + strErrMsg_GenLib;
							flag = false;
						}
					}
					else
						strErrMsg_AppLib = "Not able to select Monthly from type of dropdown." + strErrMsg_AppLib;
				}
			}
		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "RSVD_CreateQuickReservationMonthly---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return new Object[] {flag,returnValue};
	}
	//, DateAndTime startdate, DateAndTime enddate, Tuple<string, string> startTime, Tuple<string, string> endTime, string deviceName = "1")


	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool RSVD_CreateReservationMonthly(string[] weekday, string startdate, string enddate, string startTimeFormat, string endTimeFormat, string startTime, string endTime, string deviceName = "1", string everyWeekValue = "1", bool SelectFromCalendar = false)
	public final boolean RSVD_CreateReservationMonthly(String OnIndex,String OnDay,String startdate, String enddate, String startTimeFormat, String endTimeFormat, String startTime, String endTime, String deviceName, String everyMonthValue, boolean SelectFromCalendar,boolean defaultOnValue)
	//, DateAndTime startdate, DateAndTime enddate, Tuple<string, string> startTime, Tuple<string, string> endTime, string deviceName = "1")
	{
		boolean flag = false;
		strErrMsg_AppLib = "";
		String xPath, xPathReplaced,reservationerrromessage;
		Object[] values = new Object[3];
		try
		{
			flag = PerformAction("eleCreateRsrvtnHeader", Action.isDisplayed);
			if (flag)
			{
				flag = RSVD_SelectDevice(deviceName, true );
				if (flag)
				{
					flag = RSVD_SelectReservationType("Monthly");
					if (flag)
					{
						flag = PerformAction("eleEveryWeekInput_CreateReservation", Action.Type, everyMonthValue);
						if (flag)
						{
							if(!defaultOnValue)
								flag = RSVD_SelectOnMonthDay(OnIndex,OnDay);                                    	 
							if (flag)
							{
								if(SelectFromCalendar)
								{
									flag = RSVD_SelectMonthYear("","","");
								}
								else
								{
									flag = RSVD_SelectReservationDate(startdate, enddate, "Current", "Current", SelectFromCalendar);
									if (flag)
									{
										values = RSVD_SelectReservationTime(startTimeFormat, endTimeFormat, startTime, endTime);
										flag = (boolean) values[0];
										reservationerrromessage = (String) values[1];
										if (flag)
										{
											flag=PerformAction("btnCreate_CreateReservation", Action.Click);
										}
										else
										{
											strErrMsg_AppLib = "Not able to select the start time and end time. " + reservationerrromessage;
										}
									}
								}
							}
							else
							{
								strErrMsg_AppLib = "Not able to select the weekday/s.";
							}
						}
						else
						{
							strErrMsg_AppLib = "Not able to input value in Every n Weeks field.";
						}
					}
					else
					{
						strErrMsg_AppLib = strErrMsg_AppLib + "Not able to select Weekly from Repeats dropdown.";
					}

				}
				else
				{
					strErrMsg_AppLib = "Could not select the device from drop-down";
				}
			}
			else
			{
				strErrMsg_AppLib = "New Reservation page is not displayed.";
			}


		}
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "RSVD_CreateReservationWeekly---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return flag;
	}


	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public bool RSVD_SelectOnMonthDay(String OnEvery, String Day)
	public final boolean RSVD_SelectOnMonthDay(String OnEvery, String OnDay)
	{
		boolean flag = false;
		String XPath;
		String XPathValue;
		strErrMsg_AppLib = "";
		String dName;
		try
		{
			flag = PerformAction("drpMonthlyOn_CreateReservation", Action.WaitForElement, "2");
			if(flag)
			{
				flag = PerformAction("drpMonthlyOn_CreateReservation", Action.Click);
				if(flag)
				{	
					flag = PerformAction(dicOR.get("eleOnOptionMonthly_CreateReservation").replace("__STRINGTOSELECT__", OnEvery), Action.Click);
					if(flag)
					{
						flag = PerformAction("drpMonthlyDay_CreateReservation", Action.WaitForElement, "2");
						if(flag)
						{
							flag = PerformAction("drpMonthlyDay_CreateReservation", Action.Click);
							if(flag)
							{
								flag = PerformAction(dicOR.get("eleOnOptionMonthly_CreateReservation").replace("__STRINGTOSELECT__", OnDay), Action.Click);
								if(!flag)
									strErrMsg_AppLib = "Not able to select the \r\n " + OnDay + "from the dropdown." + strErrMsg_GenLib;
							}
							strErrMsg_AppLib = "Not able to locate the Monthly On Day dropdown : Monthly Create Reservation." + strErrMsg_GenLib;
						}
						else
							strErrMsg_AppLib = "Not able to locate the Monthly On Day dropdown : Monthly Create Reservation." + strErrMsg_GenLib;
					}
					else
						strErrMsg_AppLib = "Not able to select the \r\n " + OnEvery + "from the dropdown." + strErrMsg_GenLib;
				}
				else
					strErrMsg_AppLib = "Not able to select the Monthly On Index dropdown : Monthly Create Reservation." + strErrMsg_GenLib;
			}
			else
				strErrMsg_AppLib = "Not able to locate the Monthly On Index dropdown : Monthly Create Reservation." + strErrMsg_GenLib;
		}      
		catch (RuntimeException e)
		{
			flag = false;
			strErrMsg_AppLib = "RSVD_SelectOnMonthDay---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return flag;
		}


		//This function creates reservation of type - Never
		//<!--Created By : Mandeep Kaur-->
		//
		//@param deviceName
		//@return 
		//

		public final boolean RSVD_CreateReservationNever(String deviceName, String startDate, String startTime, String endDate, boolean byValue)
		{
			return RSVD_CreateReservationNever(deviceName, startDate, startTime, endDate, "",byValue);
		}

		public final boolean RSVD_CreateReservationNever(String deviceName, String endTime, String startTime,boolean byValue)
		{
			return RSVD_CreateReservationNever(deviceName, "", startTime, "", endTime,byValue);
		}

		public final boolean RSVD_CreateReservationNever(String deviceName, String startDate,boolean byValue)
		{
			return RSVD_CreateReservationNever(deviceName, startDate, "", "", "",byValue);
		}

		public final boolean RSVD_CreateReservationNever(String deviceName,boolean byValue)
		{
			return RSVD_CreateReservationNever(deviceName, "", "", "", "",byValue);
		}

		public final boolean RSVD_CreateReservationNever(boolean byValue)
		{
			return RSVD_CreateReservationNever("1", "", "", "", "",byValue);
		}

		//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
		//ORIGINAL LINE: public bool RSVD_CreateReservationNever(string deviceName = "1", string startDate = "", string startTime = "", string endDate = "", string endTime = "")
		public final boolean RSVD_CreateReservationNever(String deviceName, String startDate, String startTime, String endDate, String endTime,boolean byValue)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			Object[] values = new Object[3];
			String devicename = "",ProjectedSchedule = "";
			try
			{
				waitForPageLoaded();
				flag = PerformAction("eleCreateRsrvtnHeader", Action.isDisplayed); //Check if user is navigated to create reservation page, otherwise throw exception and escape the function
				if (flag)
				{
					flag = RSVD_SelectDevice(deviceName, byValue); // Select the first device in 'Devices' dropdown.
					if (flag)
					{
						values =  RSVD_SelectReservationTime(startDate, endDate, startTime,  endTime);
						flag = (boolean) values[0];
						devicename = (String) values[1];

						if(flag)
						{
							ProjectedSchedule = GetTextOrValue("eleDurationCol_CreateReservation","text");
							flag = PerformAction("btnCreate_CreateReservation", Action.Click);
							waitForPageLoaded();
							dicOutput.clear();
							dicOutput.put("devcie", devicename);
							dicOutput.put("ProjectedSchedule", ProjectedSchedule);
						}
						else
						{
							strErrMsg_AppLib = devicename + " Please change the reservation cheduled time";
							throw new RuntimeException(strErrMsg_AppLib );
						}
					}
					else
					{
						throw new RuntimeException(strErrMsg_AppLib);
					}
				}
				else
				{
					throw new RuntimeException("New Reservation page is not displayed.");
				}

			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "RSVD_CreateReservationWeekly---" + "Exception at line number : '"  + strErrMsg_AppLib + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return flag;
		}






		//This function creates reservation of type - Never
		//<!--Created By : Mandeep Kaur-->
		//
		//@param deviceName
		//@return 
		//

		public final boolean RSVD_CreateReservationDaily()
		{
			return RSVD_CreateReservationDaily("1",false);
		}

		//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
		//ORIGINAL LINE: public bool RSVD_CreateReservationDaily(string deviceName = "1")
		public final boolean RSVD_CreateReservationDaily(String deviceName, boolean byValue)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			try
			{
				if (PerformAction("eleReservationsHeader", Action.isDisplayed)) // check if usr is on the reservations page , otherwise skip the next steps
				{
					flag = PerformAction("btnCreateReservation", Action.Click); // Click on 'Create' reservation button
					if (flag)
					{
						flag = PerformAction("eleCreateRsrvtnHeader", Action.isDisplayed); //Check if user is navigated to create reservation page, otherwise throw exception and escape the function
						if (flag)
						{
							flag = RSVD_SelectDevice(deviceName,byValue); // Select the first device in 'Devices' dropdown.
							if (flag)
							{
								//flag = 
							}
							else
							{
								throw new RuntimeException(strErrMsg_AppLib);
							}
						}
						else
						{
							throw new RuntimeException("New Reservation page is not displayed.");
						}
					}
					else
					{
						throw new RuntimeException("Not able to click on Create Reservation button.");
					}
				}
				else
				{
					throw new RuntimeException("Reservation Page is not displayed.");
				}
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "RSVD_CreateReservationWeekly---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return flag;
		}


		//This function Selects the Device from the Devices dropdown on Create New Reservation page
		//This also verifies that the selected deviceName is displayed on the device dropdown after selection
		//<!--Created By : Vinita Mahajan-->
		//
		//@param deviceName deviceName = Name of the device OR deviceName not specified then selects the first device from dropdown.
		//@return Returns true if able to select device from devices dropdown
		//

		public final boolean RSVD_SelectDevice(boolean byValue)
		{
			return RSVD_SelectDevice("1",byValue);
		}

		//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
		//ORIGINAL LINE: public bool RSVD_SelectDevice(string deviceName = "1")
		public final boolean RSVD_SelectDevice(String deviceName, boolean byValue)
		{
			boolean flag = false;
			String XPath;
			String XPathValue;
			strErrMsg_AppLib = "";
			String dName;
			try
			{
				if (PerformAction("eleDeviceDrpArrow_CreateReservation", Action.WaitForElement, "2"))
				{
					if (PerformAction("eleDeviceDrpArrow_CreateReservation", Action.Click))
					{
						if(byValue==true)
						{
							XPath = getValueFromDictionary(dicOR, "eleDevicesOption_CreateReservation");
							XPathValue = XPath.replace("__DEVICENAME__", deviceName);
							if (dicCommon.get("BrowserName").toLowerCase().contains("ie") && (GenericLibrary.IEversion.equals("11")))
							{
								PerformAction(XPathValue, Action.WaitForElement,"5");
								flag = PerformAction(XPathValue, Action.ClickUsingJS);
							}
							else
							{
								flag = PerformAction(XPathValue, Action.ClickUsingJS);
							}
						}
						else
						{
							XPathValue = "(//div[contains(@class,'device-picker')]//..//a/span)[__DEVICENAME__]".replace("__DEVICENAME__", deviceName);
							flag = PerformAction(XPathValue, Action.Click);
						}
						if (flag)
					{
							dName = GetTextOrValue("drpDevice_CreateReservationText", "text");
							if (dName.isEmpty())
							{
								flag = false;
								strErrMsg_AppLib = "Selected Device Name is not displayed on Devices dropdown.";
							}
							else
							{
								AddToDictionary(dicOutput, "ReservedDeviceName", dName);
							}
						}
						else
						{
							strErrMsg_AppLib = "Not Able to select the Device : " + deviceName + " from Devices dropdown.";
						}
					}
					else
					{
						strErrMsg_AppLib = "Not able to click on Devices dropdown.";
					}
				}
				else
				{
					strErrMsg_AppLib = "Devices dropdown is not displayed.";
				}
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "RSVD_SelectDevice---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return flag;
		}


		//
		// This function Selects Reservation Type from Repeats dropdown on Create New Reservation page
		// This also verifies that the selected Reservation Type is displayed on the Repeats dropdown after selection
		//<!--Created By : Vinita Mahajan-->
		//
		//@param reservationType Reservation Type by default accepts Never if not specified else need to specified exactlty as per UI
		//@return 
		//

		public final boolean RSVD_SelectReservationType()
		{
			return RSVD_SelectReservationType("Never");
		}

		//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
		//ORIGINAL LINE: public bool RSVD_SelectReservationType(string reservationType = "Never")
		public final boolean RSVD_SelectReservationType(String reservationType)
		{
			boolean flag = false;
			String XPath;
			String XPathValue;
			strErrMsg_AppLib = "";
			String type;
			try
			{
				if (PerformAction("eleRepeatsDrpArrow_CreateReservation", Action.WaitForElement, "2"))
				{
					if (PerformAction("eleRepeatsDrpArrow_CreateReservation", Action.Click))
					{
						XPath = getValueFromDictionary(dicOR, "eleRepeatsOption_CreateReservation");
						XPathValue = XPath.replace("__TYPE__", reservationType);
						flag = PerformAction(XPathValue, Action.Click);
						if (flag)
						{
							type = GetTextOrValue("drpRepeats_CreateReservation", "text");
							if (type.isEmpty())
							{
								flag = false;
								strErrMsg_AppLib = "Selected Reservation Type is not displayed on Repeats dropdown.";
							}
							else
							{
								AddToDictionary(dicOutput, "ReserveType", reservationType);
							}
						}
						else
						{
							strErrMsg_AppLib = "Not Able to select the Reservation Type : " + reservationType + " from Repeats dropdown.";
						}
					}
					else
					{
						strErrMsg_AppLib = "Not able to click on Repeats dropdown.";
					}
				}
				else
				{
					strErrMsg_AppLib = "Repeats dropdown is not displayed.";
				}
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "RSVD_SelectReservationType---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return flag;
		}

		//button[contains(@class,'weekly-day-option') and @data-value='SU']

		//This function selects the week days for creating reservation of type weekly
		//<!--Created By : Vinita Mahajan-->
		//
		//@param weekDay Value for weekday array should be : MO,TU,WE,TH,FR,SA
		//@return 
		//
		public final boolean RSVD_SelectWeekDay(String[] weekDay)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			String WeekDayXPath, ClassName;
			String weekDaysAlreadySelected = "";
			try
			{
				for (int i = 0; i <weekDay.length; i++)
				{
					WeekDayXPath = dicOR.get("btnWeekDay_CreateReservation").replace("__WEEKDAY__", weekDay[i].toUpperCase());
					if (PerformAction(WeekDayXPath, Action.Click))
					{
						ClassName = getAttribute(WeekDayXPath, "class");
						if (!ClassName.isEmpty())
						{
							if (!ClassName.contains("active"))
							{

								if (!flag)
								{
									strErrMsg_AppLib = "Not able to select the button for WeekDay : " + weekDay[i].toUpperCase();
								}
							}
							else
							{
								weekDaysAlreadySelected = weekDaysAlreadySelected + weekDay[i].toUpperCase();
								flag = true;
							}
						}
						else
						{
							throw new RuntimeException("Not able to get the ClassName for the Weekday XPath or is returned as null.");
						}
					}
					else
					{
						throw new RuntimeException("Not able to replace the WeekDay Value in WeekDayXPath.");
					}
				}
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "RSVD_SelectWeekDay---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return flag;
		}



		//This function sets the Reservation Time. Like Say : 2:00 PM or 3:30 AM
		//<!--Created By : Vinita Mahajan-->
		//
		//@param startTimeFormat This is the value AM or PM for start time
		//@param endTimeFormat This is the value for AM or PM for end time
		//@param startTime Start Time to be selected from the Start Time dropdown to be set to
		//@param endTime End Time to be selected from the End Time dropdown to be set to
		//@return Returns Flag boolean value
		//
		public final Object[] RSVD_SelectReservationTime(String startTimeFormat, String endTimeFormat, String startTime, String endTime)
		{
		boolean flag = false;
		strErrMsg_AppLib = "";
		int startTimeValue = 0;
		int endTimeValue = 0;
		String startTimeValueXPath = "";
		String endTimeValueXPath = "";
		Object[] values = new Object[3];
		String starttimeAMPM="", endtimeAMPM="";

		try
		{
		if (PerformAction("eleProposedWarning_CreateReservation", Action.isDisplayed) || (startTime.isEmpty()))
		{
		startTime = FetchDateTime("time");
		String [] temp=startTime.split(":");
		if(Integer.parseInt(temp[1])<30)
		{
		startTime=get12HourTime_in_Format(temp[0])+":"+"00";
								endTime=get12HourTime_in_Format(temp[0])+":"+"30";
							}
							else
							{
								startTime=get12HourTime_in_Format(temp[0])+":"+"30";
								int val=Integer.parseInt(temp[0])+1;
								endTime=(get12HourTime_in_Format(""+val))+":"+"00";
							}
							//startTimeValue = Integer.parseInt(startTime) + 1;
							//startTime = (new Integer(startTimeValue)).toString();
							/*endTime = FetchDateTime("time");
				endTimeValue = Integer.parseInt(endTime) + 2;
				endTime = (new Integer(endTimeValue)).toString();*/



						}
						if(startTimeFormat.isEmpty()||endTimeFormat.isEmpty())
						{
							startTimeFormat=FetchDateTime("timeformat");
							endTimeFormat=FetchDateTime("timeformat");
						}
						if(startTime.contains("AM") || startTime.contains("PM"))
						{
							String [] temp=startTime.split(" ");
							starttimeAMPM=temp[1];
							startTime = temp[0].split(":")[0];
							startTime = startTime+":"+"00";
							if(endTime.contains("AM") || endTime.contains("PM"))
							{
								String [] tempend=endTime.split(" ");
								endtimeAMPM=tempend[1];
								endTime = tempend[0].split(":")[0];
								endTime = endTime+":"+"00";
							}
						}

						if (PerformAction("eleStartTimeDrpArrow_CreateReservation", Action.Click))
						{
							if (!startTime.isEmpty())
							{
								startTimeValueXPath = getValueFromDictAndReplace(dicOR, "eleStartTime_CreateReservation", "__TIME__", startTime);
								flag = PerformAction(startTimeValueXPath, Action.Click);
								if (flag)
								{
									flag = PerformAction("eleProposedWarning_CreateReservation", Action.isDisplayed);
									if(flag)
									{
										String DeviceReservation = GetTextOrValue("//table[@class='table data-grid']//..//td", "text");
										System.err.println(DeviceReservation);
										for(int i = 1 ; i< devicesSelected.size()-1; i++)
										{
											flag = RSVD_SelectDevice(devicesSelected.get(i),true);
											flag = PerformAction(startTimeValueXPath, Action.Click);
											if( !PerformAction("eleProposedWarning_CreateReservation", Action.isDisplayed))
											{
												values[1] = devicesSelected.get(i);
												break;
											} 
										}

										if (!startTimeFormat.isEmpty())
										{
											String element = getValueFromDictAndReplace(dicOR, "rbtnstartTimeFormat_CreateReservation", "__FORMAT__", startTimeFormat);
											flag = PerformAction(element, Action.Click);
											if (!flag)
											{
												strErrMsg_AppLib = "Not able to select the AM/PM radio button : " + startTimeFormat;
											}
										}
										else
										{
											String element = getValueFromDictAndReplace(dicOR, "rbtnstartTimeFormat_CreateReservation", "__FORMAT__", starttimeAMPM);
											flag = PerformAction(element, Action.Click);
											if (!flag)
											{
												strErrMsg_AppLib = "Not able to select the AM/PM radio button : " + starttimeAMPM;
											}
										}

										/*flag = false;
				 values[0] = flag;
				 values[1] = DeviceReservation;
				 return values;
										 */ 
									}
									else
									{
										if (!startTimeFormat.isEmpty())
										{
											String element = getValueFromDictAndReplace(dicOR, "rbtnstartTimeFormat_CreateReservation", "__FORMAT__", startTimeFormat);
											flag = PerformAction(element, Action.Click);
											if (!flag)
											{
												strErrMsg_AppLib = "Not able to select the AM/PM radio button : " + startTimeFormat;
											}
										}
										else
										{
											String element = getValueFromDictAndReplace(dicOR, "rbtnstartTimeFormat_CreateReservation", "__FORMAT__", starttimeAMPM);
											flag = PerformAction(element, Action.Click);
											if (!flag)
											{
												strErrMsg_AppLib = "Not able to select the AM/PM radio button : " + starttimeAMPM;
											}
										}
									}

								}
								else
								{
									strErrMsg_AppLib = "Not able to select the desired time value from Start Time dropdown.";
								}
							}
							else
							{
								strErrMsg_AppLib = "StartTime is null or empty.";
							}
						}
						else
						{
							strErrMsg_AppLib = "Not able to open the Start Time Dropdown.";
						}

						if (PerformAction("eleEndTimeDrpArrow_CreateReservation", Action.Click))
						{
							if (!endTime.isEmpty())
							{
								endTimeValueXPath = getValueFromDictAndReplace(dicOR, "eleEndTime_CreateReservation", "__TIME__", endTime);
								flag = PerformAction(endTimeValueXPath, Action.ClickUsingBuilder);
								if (flag)
								{
									if (!endTimeFormat.isEmpty())
									{
										String element = getValueFromDictAndReplace(dicOR, "rbtnendTimeFormat_CreateReservation", "__FORMAT__", endTimeFormat);
										flag = PerformAction(element, Action.Click);
										if (!flag)
										{
											strErrMsg_AppLib = "Not able to select the AM/PM radio button : " + endTimeFormat;
										}

										if(PerformAction("//div[@class='start-time-warning']", Action.isDisplayed))
										{
											String errormesaage = GetTextOrValue("//div[@class='start-time-warning']", "text");
											flag = false;
											values[0] = flag;
											values[1] = errormesaage+ "Please change the schedule timing. ";
											return values;
										}
										values[0] = flag;
									}
									else
									{
										strErrMsg_AppLib = "endTimeFormat is null or empty.";
									}
								}
								else
								{
									strErrMsg_AppLib = "Not able to select the desired time value from End Time dropdown.";
								}
							}
							else
							{
								strErrMsg_AppLib = "EndTime is null or empty.";
							}
						}
						else
						{
							strErrMsg_AppLib = "Not able to open the End Time Dropdown.";
						}
					}
					catch (RuntimeException e)
					{
						flag = false;
						strErrMsg_AppLib = "RSVD_SelectReservationTime--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
						values[0] = flag;
					}
					return values;
				}




		//This function sets the start date and end date on Create Reservation page
		//This function can set the values in the textbox as well as select from the calendar
		//
		//<!--Created By : Vinita Mahajan-->
		//@param startdate startdate : Value will be only a date. Say For Example : Only 22 OR 24, if the iscalendar = true 
		//@param enddate endate : Value will be only a date. Say For Example : Only 22 OR 24, if the iscalendar = true. If the isCalendar value is false then need to send the value in MM/dd/yyyy
		//@param startMonth By Default the value will be Current : as for other months not implemented yet
		//@param endMonth By Default the value will be Current : as for other months not implemented yet
		//@param isCalendar By Default the value will be False : if User wants to select a date explicitly from calendar for the current month then need to set this to True
		//@return returns boolean value flag
		//

		public final boolean RSVD_SelectReservationDate(String startdate, String enddate, String startMonth, String endMonth)
		{
			return RSVD_SelectReservationDate(startdate, enddate, startMonth, endMonth, false);
		}

		public final boolean RSVD_SelectReservationDate(String startdate, String enddate, String startMonth)
		{
			return RSVD_SelectReservationDate(startdate, enddate, startMonth, "Current", false);
		}

		public final boolean RSVD_SelectReservationDate(String startdate, String enddate)
		{
			return RSVD_SelectReservationDate(startdate, enddate, "Current", "Current", false);
		}

		public final boolean RSVD_SelectMonthYear(String Date, String Month, String Year)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			String DatetoSelect = "";

			if(Year!=null)
			{	
				DatetoSelect = getText("lnkMonthYear_Reseravation"); //June 2016
				if(!DatetoSelect.contains(Year))
				{
					flag = PerformAction("lnkMonthYear_Reseravation", Action.DoubleClick);
					if(flag)
					{
						List<WebElement> YearDisplayedForSelection = getelementsList(dicOR.get("lnkYearSelection_CreateReservation"));
						for(int j = 0; j < YearDisplayedForSelection.size() ; j++)
						{	
							String YeartobeSelected = getText(dicOR.get("lnkYearSelection_CreateReservation") + "[" + j + "]");
							if(Year.contains(YeartobeSelected))
							{
								flag = PerformAction(dicOR.get("lnkYearSelection_CreateReservation") + "[" + j + "]", Action.Click);
								if(Month!=null)
								{
									if(!DatetoSelect.contains(Month))
									{
										List<WebElement> MonthDisplayed = getelementsList(dicOR.get("lnkMonthsSelection_CreateReservation"));

										for(int i = 0; i < MonthDisplayed.size() ; i++)
										{	
											String MonthtobeSelected = getText(dicOR.get("lnkMonthsSelection_CreateReservation") + "[" + i + "]");
											if(Month.contains(MonthtobeSelected))
											{
												flag = PerformAction(dicOR.get("lnkMonthsSelection_CreateReservation") + "[" + i + "]", Action.Click);
												if(flag)
												{
													if(Date!=null)
													{
														List<WebElement> DaysDisplayedforSelection = getelementsList(dicOR.get("lnkDaySelection_CreateReservation"));
														for(int k = 0; k < DaysDisplayedforSelection.size() ; k++)
														{
															String DaytobeSelected = getText(dicOR.get("lnkDaySelection_CreateReservation") + "[" + k + "]");
															if(Date.contains(DaytobeSelected))
															{
																flag = PerformAction(dicOR.get("lnktoSelectDate_Reseravation").replace("__DAY__",Date ), Action.Click);
																break;
															}
														}
														if(!flag)
															strErrMsg_AppLib = strErrMsg_GenLib + "The provided date is not available for selection for creating a reservation.";
													}
												}
												break;
											}
										}
										if(!flag)
											strErrMsg_AppLib = strErrMsg_GenLib + "The provided month is not available for selection for creating a reservation.";
									}
								}
								break;
							}
						}
						if(!flag)
							strErrMsg_AppLib = strErrMsg_GenLib + "The provided year is not available for selection for creating a reservation.";
					}	
				}
			}
			return flag;
		}

		
		//startdate & enddate should be in mm//dd/yy format
		public final Object[] RSVD_SelectReservationDate1(String startdate, String enddate)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			String startDateXPath = "", endDateXPath = "",startDateValue="",endDateValue="",FormatedTodayDate="";

			String [] dd=enddate.split("/");
			String [] sd= startdate.split("/");

			try
			{
				PerformAction("calendarWeeklyStartDate",Action.Click);
				List<WebElement> listartdates=getelementsList("datesLocatorWeeklyenddateCalendar");
				for(int i=0;i<listartdates.size();i++)
				{
					if(listartdates.get(i).getAttribute("class").contains("old") || listartdates.get(i).getAttribute("class").contains("disabled"))
					{
						continue;
					}
					if(listartdates.get(i).getText().equals(sd[1]))
					{
						listartdates.get(i).click();
						break;
					}

				}

				PerformAction("calendarWeeklyEndDate",Action.Click);
				PerformAction("nextmonthLocatorWeeklyenddateCalendar",Action.Click);
				List<WebElement> lienddates=getelementsList("datesLocatorWeeklyenddateCalendar");
				for(int i=0;i<lienddates.size();i++)
				{
					if(lienddates.get(i).getAttribute("class").contains("old") || lienddates.get(i).getAttribute("class").contains("disabled"))
					{
						continue;
					}
					if(lienddates.get(i).getText().equals(dd[1]))
					{
						lienddates.get(i).click();
						break;
					}

				}
				System.out.println(startDateValue);
				System.out.println(endDateValue);
				flag= true;
			}
			catch(RuntimeException e)
			{
				strErrMsg_AppLib = e.getMessage();
				flag=false;
			}

			return new Object[] {flag,startDateValue,endDateValue};
		}
		
		
		//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
		//ORIGINAL LINE: public bool RSVD_SelectReservationDate(string startdate, string enddate, string startMonth = "Current", string endMonth = "Current", bool isCalendar = false)
		public final boolean RSVD_SelectReservationDate(String startdate, String enddate, String startMonth, String endMonth, boolean isCalendar)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			String startDateXPath = "", endDateXPath = "";

			try
			{
				if (isCalendar)
				{
					String startDateCalendar = getValueFromDictionary(dicOR, "eleStartDateCalendar_CreateReservation");
					if (!startDateCalendar.isEmpty())
					{
						flag = PerformAction(startDateCalendar, Action.Click);
						if (flag)
						{
							if (!startMonth.equals("Current"))
							{
								if(PerformAction("lnkMonthYear_Reseravation", Action.Click))
								{
									String startM = startMonth.substring(0, 2);
									flag =	PerformAction("lnktoSelectMonth_Reseravation".replace("_Month_",startM ), Action.Click);
									if(flag)
									{
										flag = PerformAction("lnktoSelectDate_Reseravation".replace("__day__",startdate ), Action.Click);
									}
									else
									{
										throw new RuntimeException("Could not click select Month in Calender on reservation page.");
									}
								}
								else
								{
									throw new RuntimeException("Could not click on Link to select Month or a Year in Calender on reservation page.");
								}

							}
							// startDateXPath = getValueFromDictAndReplace(dicOR, "btnDatetoSelectCalendar_Reseravation", "__DATE__", startdate);
							//if (!startDateXPath.isEmpty())
							//new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='datepicker-days']")));
							// flag = PerformAction("//div[@class='datepicker-days']", Action.WaitForElement);
							// flag = PerformAction("//div[@class='datepicker-days']", Action.isDisplayed);
							//List<WebElement> list_AllDateToBook = driver.findElements(By.xpath("//div[@class='datepicker']//..//table[@class='table-condensed']//..//td[not(contains(@class,'disabled'))]"));
							//list_AllDateToBook.size();
							//list_AllDateToBook.get(Integer.parseInt(startdate)-1).click();
							
							flag =  PerformAction(dicOR.get("lnktoSelectDate_Reseravation").replace("_day_",startdate), Action.isDisplayed);
							flag = PerformAction(dicOR.get("lnktoSelectDate_Reseravation").replace("_day_",startdate), Action.Click);

							if (flag)
							{
								String endDateCalendar = getValueFromDictionary(dicOR, "eleEndDateCalendar_CreateReservation");
								if (!endDateCalendar.isEmpty())
								{
									flag = PerformAction(endDateCalendar, Action.Click);
									if (flag)
									{
										if (endMonth.equals("Current"))
										{
											//endDateXPath = getValueFromDictAndReplace(dicOR, "btnDatetoSelectCalendar_Reseravation", "__DATE__", enddate);
											// if (!endDateXPath.isEmpty())
											//flag =  PerformAction(dicOR.get("lnktoSelectDate_Reseravation").replace("__day__",enddate), Action.WaitForElement);
											if(!enddate.equals("")) {
												flag = PerformAction(dicOR.get("lnktoSelectDate_Reseravation").replace("_day_",enddate), Action.ClickUsingJS);
												//String calxpath = dicOR.get("lnktoSelectDate_Reseravation").replace("__day__",enddate);
												// ((JavascriptExecutor)driver).executeScript("arguments[0].checked = true;", driver.findElement(By.xpath(calxpath)));
												// driver.findElement(By.linkText(enddate)).click()
											} 
											if (!flag)
											{
												strErrMsg_AppLib = "Not able to select end date from the calendar.";
											}

										}
										//else
										//{
										//    //Need to implement a function and call here
										//}
									}
									else
									{
										strErrMsg_AppLib = "Not able to click on endDateCalendar.";
									}
								}
							}
							else 
							{
								strErrMsg_AppLib = "Could not selected date on startDateCalendar & Please change the Date.";
							}

						}
						else
						{
							strErrMsg_AppLib = "Not able to click on startDateCalendar.";
						}
					}
					else
					{
						throw new RuntimeException("Not able to get the value of : 'eleStartDateCalendar_CreateReservation' from OR");
					}
				}
				else if ( ! startdate.equals("") && ! enddate.equals("")) //Note this date will be set only for current month
				{
					flag = PerformAction("txtStartDate_CreateReservation", startdate, Action.Type);
					if (flag)
					{
						flag = PerformAction("txtEndDate_CreateReservation", enddate, Action.Type);
					}
				}
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "RSVD_SelectReservationDate--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return flag;
		}


		//Function to check the checkboxes given in array passed and uncheck all other checkboxes of that kind.
		//
		//<!-- Created by : Mandeep Kaur -->
		//<!-- Last modified : 6/2/2015 by Mandeep Kaur-->
		//@param checkboxesToBeChecked Array of names of the checkboxes that need to be checked.
		//@param xpathOfChkBoxesInOR Key to the locator of the checkboxes in OR.xls
		//@return True or False
		//<example>selectCheckboxes_DI(new string[]{"Android"}, "chkPlatform_Device") : This will make the Android checkbox to be checked and iOS to be Unchecked.</example>
		//
		public boolean selectCheckboxes_DI(String[] checkboxesToBeChecked, String xpathOfChkBoxesInOR)
		{
			boolean flag = false, shouldBeChecked = false;
			strErrMsg_AppLib = "";
			String Xpath_checkboxGeneral = "", Xpath_checkbox = "", currentCheckboxText = "";
			String errorIndex = "";
			int chkBoxCount = 0;
			try
			{
				Xpath_checkboxGeneral = getValueFromDictionary(dicOR, xpathOfChkBoxesInOR); //Getting xpath of the checkboxes from Or
				chkBoxCount = getelementCount(Xpath_checkboxGeneral); //getting number of checkboxes of the same type (eg. all checkboxes under platform
				for (int i = 1; i <= chkBoxCount; i++)
				{
					flag = false; // Set flag to false for every iteration
					Xpath_checkbox = Xpath_checkboxGeneral + "[" + i + "]"; // Getting xpath of each checkbox by puting index to the general xpath
					currentCheckboxText = GetTextOrValue(Xpath_checkbox + "/..", "text"); //Getting text in front of the checkbox, i.e. text of the parent of the checkbox that has text associated with the checkbox

					for(String chkName : checkboxesToBeChecked)
					{
						/*shouldBeChecked = p.matcher(chkName).matches();*/
						shouldBeChecked = currentCheckboxText.startsWith(chkName);
						if(shouldBeChecked)
							break;
					}
					if (shouldBeChecked)
					{
						flag = PerformAction(Xpath_checkbox, Action.SelectCheckbox); //Check or leave checked if the checkbox text is passed int he function
						waitForPageLoaded();
						//break;
					}
					else
					{
						flag = PerformAction(Xpath_checkbox, Action.DeSelectCheckbox); //Uncheck the checkbox if it is not there in the array passed to the function
						waitForPageLoaded();
						//break;
					}


					//put checkbox index to list if there is any error while checking or unchecking the checkbox
					if (!flag)
						errorIndex = errorIndex + ", " + i;
				}
				// If errorIndex is not empty then function is not pass and so, throw an exception including the errorIndex string containing the checkbox indices where check/uncheck could not be performed
				PerformAction("btnRefresh_Devices",Action.Click);
				if ( ! errorIndex.equals(""))
					throw new RuntimeException("Could not check/uncheck checkboxes at indices : " + errorIndex);
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "Exception : '" + e.getMessage();
			}
			return flag;
		}


		//Checks the checkboxes for the given platform(s), unchecks the ones not provided. Also, it verifies if the platform filter was applied properly or not
		//<p>If there are more than one values passed as input to function, then it does not verify that devices of only those platforms are present.</p>
		//
		//<!-- Created by : Mandeep Kaur -->
		//<!-- Last modified : 9/2/2015 by Mandeep Kaur, changed input parameter type from string array to string.-->
		//@param platform Comma separated names of the status(s) to be selected (eg. "Android,iOS" . Provide values in exact case.
		//@return True or False
		//
		public final boolean selectPlatform_DI(String platforms)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			try
			{
				String[] platformsArray = platforms.split("[,]", -1);
				waitForPageLoaded();
				flag = PerformAction("chkPlatform_Devices", Action.WaitForElement, "5");
				if (flag)
				{
					flag = selectCheckboxes_DI(platformsArray, "chkPlatform_Devices"); // Call function to select the required platform checkboxes.
					if (flag && (platformsArray.length == 1)) // Verify that displayed devices are of given platform only when a single platform needs to be selected.
					{
						flag = PerformAction("eleNoDevicesWarning_Devices", Action.WaitForElement, "5"); // If warning message showing that there are no devices matching the filter applied, then the function is pass.
						if (!flag) // If some devices are displayed, then check that devices of only the given platform are displayed.
						{
							PerformAction("browser",Action.Refresh);
							flag = VerifyDeviceDetailsInGridAndListView("deviceplatform", platformsArray[0], "list");
							if (!flag)
							{
								throw new RuntimeException(strErrMsg_AppLib);
							}
						}
					}
					else if (!flag) // selectPlatform function failed then throw error.
					{
						throw new RuntimeException(strErrMsg_AppLib);
					}
				}
				else
				{
					throw new RuntimeException("Platform checkboxes are not displayed on the page.");
				}
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "selectPlatform_DI--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return flag;
		}


		//Checks the checkboxes for the given status(s), unchecks the ones not provided. Also, it verifies if the status filter was applied properly or not
		//<p>If there are more than one values passed as input to function, then it does not verify that devices of only those statuses are present.</p>
		//
		//<!-- Created by : Mandeep Kaur -->
		//<!-- Last modified : 3/6/2015 by Mandeep Kaur, changed input parameter type from string array to string.-->
		//@param status Comma separated names of the status(s) to be selected (eg. "Available,Offline" . Provide values in exact case.
		//@return True or False
		//
		public final boolean selectStatus_DI(String status)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			try
			{
				String[] statusesArray = status.split("[,]", -1);
				flag = PerformAction("chkStatus_Devices", Action.WaitForElement, "5");
				if (flag)
				{
					flag = selectCheckboxes_DI(statusesArray, "chkStatus_Devices");
					if (!flag)
					{
						throw new RuntimeException(strErrMsg_AppLib);
					}
				}
				else
				{
					throw new RuntimeException("Status checkboxes are not displayed on the page.");
				}
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "selectStatus_DI--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return flag;
		}


		//Clicks on the required tab on top nav bar and verifies if the expected page element is loaded or not.
		//
		//@param TabName Name of page to be navigated to : "Devices", "Applications", "Reservations", "Users", "System"
		//@param expectedPageElement Locator of the 
		//@return 
		//
		protected final boolean navigateToNavBarPages(String TabName, String expectedPageElement)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			try
			{
				if (dicCommon.get("BrowserName").toLowerCase().contains("ie") && (GenericLibrary.IEversion.equals("11")))
				{
					PerformAction(dicOR.get("eleTopNavTab").replace("__TABNAME__", TabName),Action.WaitForElement,"5");
				}
				flag = PerformAction(dicOR.get("eleTopNavTab").replace("__TABNAME__", TabName), Action.ClickUsingJS);
				if (flag)
				{
					waitForPageLoaded();
					flag = PerformAction(expectedPageElement, Action.WaitForElement);
					flag = PerformAction("browser", Action.WaitForPageToLoad);
					if (!flag)
					{
						throw new RuntimeException("'" + TabName + "' page not loaded.");
					}
				}
				else
				{
					throw new RuntimeException("Could not click on '" + TabName + "' tab.");
				}
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "navigateToNavBarPages--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return flag;
		}


		//Selects the select all checkbox and verifies that checkboxes in front of all the devices are also selected.
		//
		//<!--Created by : Mandeep Kaur-->
		//<!--Last updated : 12/2/2015 by Mandeep Kaur-->
		//@param devicesSelected List in which the names all selected devices are to be stored for further usage.
		//@return True or false
		//
		public final Object[] selectAllCheckboxAndVerify_DI()
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			//int devicesCount = 0;
			ArrayList<String> devicesSelected = new ArrayList<String>();
			//string strErrorIndex = "", strErrorIndexName = "", deviceName = "";
			Object[] values = new Object[2];
			try
			{
				//Select the select all checkbox
				waitForPageLoaded();
				flag = PerformAction("chkSelectAll_Devices", Action.SelectCheckbox);
				if (flag) // If it is selected then get the names of all the devices which are displayed and verify that the checkbox in front of each device got selected.
				{
					waitForPageLoaded();
					Object[] objresult =  VerifyAllCheckedOrUnchecked_DI(Action.isSelected);
					flag = (boolean) objresult[0];
					devicesSelected = (ArrayList<String>) objresult[1];  // Warning not suppressed because the runtime will always return ArrayList of StringType
					if (!flag)
						throw new RuntimeException(strErrMsg_AppLib);
				}
				else
					throw new RuntimeException("Could not select the select all checkbox.");
				values[0]= flag;
				values[1] = devicesSelected; 
			}
			catch (RuntimeException e)
			{
				flag = false;
				values[0]= flag;
				values[1] = devicesSelected; 
				strErrMsg_AppLib = "selectAllCheckboxAndVerify_DI--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return values;

		}

		/** 
    //Verifies if all checkboxes in front of all the displayed devices are checked or not.
    //
    //@author mandeepm
    //@since 23/6/2015
    //@param devicesSelected List of string type in which names of all displayed de
    //@param checkStatusToVerify It takes values : Action.isSelected if it needs to be verfied that all checkboxes are selected or Action.isNotSelected to check that all checkboxes are not selected. 
    //@return True or False
		 */
		public final  Object[] VerifyAllCheckedOrUnchecked_DI(String checkStatusToVerify)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			int devicesCount = 0;
			ArrayList<String> devicesSelected = new ArrayList<String>();
			String strErrorIndex = "", strErrorIndexName = "", deviceName = "";
			try
			{
				devicesCount = getelementCount("eleDevicesHolderListView") - 1;
				if (devicesCount > 0) // If number of rows are obtained then check if the check-status of all the checkboxes match the given check status
				{
					for (int i = 1; i <= devicesCount; i++)
					{
						if (!PerformAction(dicOR.get("chkDeviceName_Devices") + "[" + i + "]", checkStatusToVerify)) // If the checkbox is not selected/deselected then put the index to errorVariable
						{
							strErrorIndex = strErrorIndex + ", " + i;
						}
						deviceName = GetTextOrValue(dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(i)).toString()), "text");
						devicesSelected.add(deviceName);
						if (deviceName.equals(""))
						{
							strErrorIndexName = strErrorIndexName + ", " + i;
						}
					}
					if ( ! strErrorIndex.equals(""))
					{
						throw new RuntimeException("Checkbox is not in correct checked-state for device at index(s) : '" + strErrorIndex + "'.");
					}
					if ( ! strErrorIndexName.equals(""))
					{
						throw new RuntimeException("Could not get name of device at index(s) : '" + strErrorIndexName + "'.");
					}
					flag = true;
				}
				else
				{
					throw new RuntimeException(strErrMsg_AppLib);
				}
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "VerifyAllCheckedOrUnchecked_DI--- " + " + " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return new Object[] {flag,devicesSelected};
		}


		//Verifies that none of the element with xpath elementXpath has text elementText>
		//
		//<!--Created by : Mandeep Kaur 17/2/2015-->
		//@param elementXpath xpath of elements whose text needs to be verified.
		//@param elementText Text that needs to be verified
		//@return 
		//
		protected final boolean verifyElementWithTextNotPresentOnPage(String elementXpath, String elementText)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			int elementsCount = 0;
			try
			{
				elementsCount = getelementCount(elementXpath); //Get number of elements with given xpath
				if (elementsCount != 0)
				{
					for (int i = 1; i <= elementsCount; i++)
					{
						if (elementText.equals(GetTextOrValue(elementXpath + "[" + i + "]", "text")))
						{
							throw new RuntimeException("Element with text '" + elementText + "' exists on page. Element xpath is : '" + elementXpath + "[" + i + "]'");
						}
					}
					flag = true; // Set flag to True if there is no element with such text.
				}
				else
				{
					throw new RuntimeException("Could not get number of elements with xpath '" + elementXpath + "'");
				}
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "VerifyAllCheckedOrUnchecked_DI--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return flag;
		}


		//It verifies that devices with only the given statuses or platforms are displayed on devices index page list view.
		//<!--Created by : Mandeep Kaur 18/2/2015-->
		//<!--Last modified : 13/03/2015 by Mandeep Kaur-->
		//@param property What aspect to be verified : Status or Platform
		//@param strRequiredValues Value of statuses or platforms that need to be verified.
		//<example>eg. </example>
		//@return VerifyMultiplePlatformOrStatus_DI("Status", "Available,Offline"); -- It verifies that only those devices are displayed whose status is either Available or Offline.
		// Possible values for device status : Available, Offline, removed, In Use (<username>), Reserved (<username>), In Use (<username>), by reservation
		// Possible values for device platform :
		// 
		public final boolean VerifyMultipleValuesOfProperty_DI(String property, String strRequiredValues)
		{
			boolean flag = false, isValueCorrrect = false, loopEntered = false;
			strErrMsg_AppLib = "";
			int devicesCount = 0;
			String propertyValue = "", strErrorIndex = "";
			List<String> lstdeviceProperty = new ArrayList<String>();
			String[] arrRequiredValues;
			try
			{
				// Return true if no devices are displayed on devices index page
				if (PerformAction("eleNoDevicesWarning_Devices", Action.isDisplayed))
					throw new RuntimeException("No devices are displayed on devices index page.");

				// If there are devices then get number of devices displayed on the devices index page
				devicesCount = getelementCount("eleDevicesHolderListView") - 1; // getting number of displayed devices. (-1 because this functions counts the header row also.)
				if (devicesCount <= 0)
					throw new RuntimeException("Could not get the number of devices."); // throw exception if number of displayed devices could not be fetched.

				// Switch to the property that needs to be verified and put the property value of all devices to the list lstdeviceProperty
				switch (property.toLowerCase())
				{
				case "status":
					for (int i = 1; i <= devicesCount; i++) // For each device, get status or platform value and put in variable 'deviceStatusOrplatform'
					{
						propertyValue = GetTextOrValue(dicOR.get("eleDeviceStatus_ListView") + "[" + i + "]", "text"); //Getting status value for each devices displayed.
						if (propertyValue.equals(""))
							throw new RuntimeException("Could not get the status of device at index '" + i + "'.");
						else
							lstdeviceProperty.add(propertyValue);
					}
					break;
				case "platform":
					for (int i = 1; i <= devicesCount; i++) // For each device, get status or platform value and put in variable 'deviceStatusOrplatform'
					{
						propertyValue = GetTextOrValue(dicOR.get("eleDevicePlatform_ListView").replace("__INDEX__", (new Integer(i + 1)).toString()), "text");
						if (propertyValue.equals(""))
							throw new RuntimeException("Could not get the platform of device at index '" + (i + 1) + "'.");
						else
							lstdeviceProperty.add(propertyValue);
					}
					break;
				}

				// Now that the required property of all devices is added to the list lstdeviceProperty, compare each value to the required value(ValuesToCheck)
				arrRequiredValues = strRequiredValues.split(",");
				for (int i = 0; i < lstdeviceProperty.size(); i++)
				{
					loopEntered = true;
					isValueCorrrect = false;
					//Check if lstdeviceProperty[i] starts with any of the values in arrRequiredValues
					//i.e. check if the value obtained for each device is one of the required values.

					for(String propertyVal : arrRequiredValues)
					{
						//if(propertyVal.startsWith(lstdeviceProperty.get(i))) // If the current property value matches with any of the required values, then set variable to true and break the loop for this value
						if((lstdeviceProperty.get(i)).toString().startsWith(propertyVal))
						{
							isValueCorrrect = true;
							break;
						}
					}
					if(!isValueCorrrect)
						strErrorIndex = strErrorIndex + ", " + i;

					//if(!arrRequiredValues.Any(x=> lstdeviceProperty.get(i).startsWith(x)))
					//   strErrorIndex = strErrorIndex + ", " + i;
				}
				if(!strErrorIndex.equals("") || !loopEntered)  // throw exception if any device's property is not in the required values OR the loop is not entered at all.
					throw new RuntimeException("'" + property + "' of device at index(es) '" + strErrorIndex + "' is not any of these : '" + strRequiredValues + "'.");
				else
					flag = true;
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "VerifyMultipleValuesOfProperty_DI--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return flag;
		}



		/* CREATED BY Dolly DATE*/
		public final boolean SelectColumn_Devices(String columnsName){
			String[] columnsNameArray = columnsName.split("[,]", -1);
			strErrMsg_AppLib = "";
			boolean isEventSuccessful = false;
			try
			{
				//Verify Devices Index page is opened.
				waitForPageLoaded();
				if(!PerformAction("eleDevicesTab_Devices", Action.isDisplayed))
				{
					isEventSuccessful = navigateToNavBarPages("Devices", "eleDevicesTab_Devices");
					if (!isEventSuccessful)
					{
						throw new RuntimeException("Could not click on 'Devices' on top menu bar.");
					}
				}
				//Verify Settings button is displayed.
				if(PerformAction("btnSettings_Devices", Action.isDisplayed))
				{
					//Verify Settings button is clicked.
					if(PerformAction("btnSettings_Devices", Action.Click))
					{
						waitForPageLoaded();
						//Verify Columns checkboxes is displayed.
						if(PerformAction("drpSettings_Devices", Action.isDisplayed)) 
						{
							isEventSuccessful = selectCheckboxes_DI(columnsNameArray, "chkColumns_Devices");
							if(isEventSuccessful && dicCommon.get("BrowserName").toLowerCase().equals("ie"))
							{
								//isEventSuccessful = PerformAction("btnSettings_Devices", Action.ClickUsingJS);
								if(PerformAction("drpSettings_Devices", Action.isDisplayed)) 
								{
									throw new RuntimeException("Columns check boxes is displayed.");
								}
							}
							if (isEventSuccessful && dicCommon.get("BrowserName").toLowerCase() != "ie")
							{
								if(PerformAction("drpSettings_Devices", Action.isDisplayed)) 
								{
									throw new RuntimeException("Columns check boxes is displayed.");
								}
							}
							else
							{
								throw new RuntimeException("Could not select check boxes.");
							}
						}
						else
						{
							throw new RuntimeException("Columns check boxes is not displayed.");
						}
					}
					else
					{
						throw new RuntimeException("Settings button is not clicked on devices index page.");
					}
				}
				else
				{
					throw new RuntimeException("Settings button is not displayed on devices index page.");
				}
				CoulmnsDisplayed_Devices(columnsName);
			}
			catch (RuntimeException e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "CoulmnsDisplayed_Devices--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return isEventSuccessful;

		}

		/* CREATED BY Dolly DATE*/

		public final boolean CoulmnsDisplayed_Devices(String columnsNames)
		{
			boolean isValueCorrrect = false,isEventSuccessful =false;
			String ColumnNames = "";
			StringBuilder strColumnHeadersNotDisplayed = new StringBuilder();
			List<String> lstColNames = new ArrayList<String>();
			String[] arrRequiredValues;
			try
			{ 
				//getting total number of displayed columns.
				List<WebElement> lstColHeaderElements = getelementsList("eleCoulmnsdisplayed_Devices");
				for(WebElement colWebElement : lstColHeaderElements)
					lstColNames.add(colWebElement.getText());

				//Append "Status / Name " to the required values as this column is present by default but not there in the settings dropdown.
				columnsNames = "Name / Status," + columnsNames + ",";
				arrRequiredValues = columnsNames.split(",", -1);   //Splitting the column names.
				if(arrRequiredValues.length==lstColNames.size()) // Comparing the displayed columns and required columns
				{   
					for(String ColVal : arrRequiredValues)
					{
						if(!lstColNames.contains(ColVal))
							strColumnHeadersNotDisplayed = strColumnHeadersNotDisplayed.append(ColVal + ", ");
					}
					if(strColumnHeadersNotDisplayed.length()!= 0)
						throw new RuntimeException("Following colummns not displayed: " + strColumnHeadersNotDisplayed);
					else
						isEventSuccessful = true;
				}
				else 
					throw new RuntimeException("arrRequiredValues & lstColNames sizes are different. Number of columns displayed are :" + lstColNames.size());

			}
			catch (RuntimeException e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "CoulmnsDisplayed_Devices--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return isEventSuccessful;
		}

		/* CREATED BY Dolly DATE  for sorting on Device Index page*/
		public final boolean VerifySorting(String TableContainerXpath, String columnNames, String sortingOrder)
		{
			boolean flag = false;
			int columnsCountAfterSorting = 0, columnsCountBeforeSorting = 0;
			strErrMsg_AppLib = "";
			List<String> ListDisplayed = new ArrayList<String>();
			List<String> SortedListDisplayed = new ArrayList<String>();
			String[] arrcolumnName = columnNames.split(",", -1);
			int columntoclick = 0;
			try
			{
				waitForPageLoaded();
				for(String columnName :arrcolumnName)
				{
					SortedListDisplayed.removeAll(SortedListDisplayed);
					ListDisplayed.removeAll(ListDisplayed);
					List<WebElement> columnNameValues = getelementsList("eleCoulmnsdisplayed_Devices");
					for(int i=0;i<columnNameValues.size();i++)
					{
						if(columnNameValues.get(i).getText().equals("Name / Status"))
						{
							String c[] = columnNameValues.get(i).getText().split("/");
							if(c[0].trim().equals(columnName) ||c[1].trim().equals(columnName))
							{
								columntoclick = i+1 ;
								break;
							}

						}
						else if(columnNameValues.get(i).getText().equals(columnName))
						{
							columntoclick = i+1;
							break;
						}
					}

					List<WebElement>  TotalRows = getelementsList(TableContainerXpath+"/td["+columntoclick+"]");
					columnsCountBeforeSorting = TotalRows.size();
					if(columnsCountBeforeSorting==1)
					{
						throw new RuntimeException("Cannot validate with a single row in table.");
					}

					switch(columnName)
					{
					case "Name":
						for(int i=0;i<TotalRows.size();i++)
						{
							ListDisplayed.add(TotalRows.get(i).getAttribute("title"));
						}
						Collections.sort(ListDisplayed, String.CASE_INSENSITIVE_ORDER); 
						flag = PerformAction(TableContainerXpath+dicOR.get("lnk_OtherColumnsOnDevicesPage").replace("_ColumnNameSorting_", columnName),Action.Click);
						if(sortingOrder.equalsIgnoreCase("descending"))
						{
							flag = PerformAction(TableContainerXpath+dicOR.get("lnk_OtherColumnsOnDevicesPage").replace("_ColumnNameSorting_", columnName),Action.Click);
							Collections.reverse(ListDisplayed);
						}
						TotalRows = getelementsList(TableContainerXpath+"/td["+columntoclick+"]");
						columnsCountAfterSorting = TotalRows.size();
						if(columnsCountBeforeSorting==columnsCountAfterSorting)
						{
							for(int i=0;i<TotalRows.size();i++)
							{
								SortedListDisplayed.add(TotalRows.get(i).getAttribute("title"));
							}
						}

						for(int i = 0; i < SortedListDisplayed.size(); i++)
						{
							if(!SortedListDisplayed.get(i).equals(ListDisplayed.get(i)))
							{
								throw new RuntimeException("SortedListDisplayed :-" + SortedListDisplayed.get(i) +"not equals to ListDisplayed :-" + ListDisplayed.get(i));
							}
						}
						break;

					default :
						for(int i=0;i<TotalRows.size();i++)
						{
							ListDisplayed.add(TotalRows.get(i).getText());
						}
						Collections.sort(ListDisplayed, String.CASE_INSENSITIVE_ORDER); 
						//flag = PerformAction(TableContainerXpath + "/th/a[text()='"+columnName+"']", Action.Click);
						flag = PerformAction(TableContainerXpath+dicOR.get("lnk_OtherColumnsOnDevicesPage").replace("_ColumnNameSorting_", columnName),Action.Click);
						if(sortingOrder.equalsIgnoreCase("descending"))
						{
							flag = PerformAction(TableContainerXpath+dicOR.get("lnk_OtherColumnsOnDevicesPage").replace("_ColumnNameSorting_", columnName),Action.Click);
							Collections.reverse(ListDisplayed);
						}
						TotalRows = getelementsList(TableContainerXpath+"/td["+columntoclick+"]");
						columnsCountAfterSorting = TotalRows.size();
						if(columnsCountBeforeSorting==columnsCountAfterSorting)
						{
							for(int i=0;i<TotalRows.size();i++)
							{
								SortedListDisplayed.add(TotalRows.get(i).getText());
							}
						} 

						for(int i = 0; i < SortedListDisplayed.size(); i++)
						{
							if(!SortedListDisplayed.get(i).equals(ListDisplayed.get(i)))
							{
								throw new RuntimeException("SortedListDisplayed :-" + SortedListDisplayed.get(i) +"not equals to ListDisplayed :-" + ListDisplayed.get(i));
							}
						}
						break;
					}
				}
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "Columns are displayed--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage() ;
			} 

			return flag;

		}   



		/* CREATED BY Dolly DATE*/
		public final boolean VerifyDevicesName(ArrayList<String> devicesSelected)
		{
			int devicesCount =0;
			boolean isEventSuccessful = false;
			try {
				if(!PerformAction("eleNoDevicesWarning_Devices", Action.isDisplayed))
				{		       		       
					devicesCount = getelementCount("eleDevicesHolderListView") - 1; 
					if (!(devicesCount == 0))
					{
						boolean isvaluepresent = false;
						for(String disabledevice : devicesSelected)
						{
							for (int i = 1; i <= devicesCount; i++)
							{ 
								String DeviceName = GetTextOrValue(dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(i)).toString()) , "text");
								if(DeviceName.equals(disabledevice) )
								{
									isvaluepresent = true;
									break;
								}
							}
							if(!isvaluepresent)
								throw new RuntimeException(disabledevice + "is not present in devices index page."); 
							isvaluepresent = false;
						}
						isEventSuccessful = true;
					}
					else
					{
						throw new RuntimeException("Could not get the number of devices.");
					}
				}
				else
				{
					throw new RuntimeException("Could not get the number of devices.");
				}   	 

			}
			catch (RuntimeException e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "VerifyDevicesName--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return isEventSuccessful;

		}
		/* CREATED BY Dolly DATE*/ 

		public final boolean VerifyOSonDeviceINdexpage(String[] OSneedtobeverfied)
		{
			int devicesCount =0;
			boolean isEventSuccessful = false;
			try {
				if(!PerformAction("eleNoDevicesWarning_Devices", Action.isDisplayed))
				{		       		       
					devicesCount = getelementCount("eleDevicesHolderListView") - 1; 
					if (!(devicesCount == 0))
					{
						boolean isvaluepresent = false;
						for(String OS : OSneedtobeverfied)
						{
							for (int i = 2; i <= devicesCount; i++)
							{ 
								String OSName = GetTextOrValue(dicOR.get("eleDevicesOSListView").replace("__INDEX__", (new Integer(i)).toString()) , "text");
								if(OSName.equals(OS) )
								{
									isvaluepresent = true;
									String DeviceName = GetTextOrValue(dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(i)).toString()) , "text");
									String deviceNameLink = dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(i-1)).toString());
									dicOutput.put("devicename",DeviceName);
									dicOutput.put("OSName",OSName);
									dicOutput.put("deviceNameLink",deviceNameLink);
									break;
								}
							}
							if(!isvaluepresent)
								throw new RuntimeException(OS + "is not present in devices index page."); 
							isvaluepresent = false;
						}
						isEventSuccessful = true;
					}
					else
					{
						throw new RuntimeException("Could not get the number of devices.");
					}
				}
				else
				{
					throw new RuntimeException("Could not get the number of devices.");
				}   	 

			}
			catch (RuntimeException e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "VerifyDevicesName--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return isEventSuccessful;

		}

		/* CREATED BY Dolly DATE*/
		public final boolean DisplayDisabledDevices()

		{
			boolean isEventSuccessful = false;
			Object[] Values = new Object[4];
			String strActualResult = "",deviceName="";
			Reporter reporter = new Reporter();
			try
			{
				isEventSuccessful = selectStatus_DI("Available");
				if(isEventSuccessful)
				{
					Values = ExecuteCLICommand("disable", "Android");
					isEventSuccessful = (boolean)Values[2];
					deviceName=(String)Values[3];
					PerformAction("", Action.WaitForElement,"10");
					if(isEventSuccessful)
					{
						isEventSuccessful = selectStatus_DI("Disabled");
						if(isEventSuccessful)
						{
							isEventSuccessful = VerifyDeviceDetailsInGridAndListView("connect", "disable", "list");

							if (isEventSuccessful)
							{
								strActualResult = "Disbled devices is being dispalyed.";
							}
							else
							{
								strActualResult = "VerifyDeviceDetailsInGridAndListView---" + strErrMsg_AppLib;
							}

							reporter.ReportStep("Verify Disabled status device dispalyed", "Disabled status device should be dispalyed.", strActualResult, isEventSuccessful);

						}
						else
						{
							throw new RuntimeException("Could not selected 'Disabled' staus checkbox.");
						}
					}
					else
					{
						throw new RuntimeException("Could not make a device status to 'Disable'");
					}
				}
				else
				{
					throw new RuntimeException("Could not selected 'Available' staus checkbox.");
				}
			}
			catch (RuntimeException e)
			{
				isEventSuccessful = false;
				strActualResult = "Disable the device" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
				reporter.ReportStep("Disabled status device is not being dispalyed, connecting with CLI command", "Disabled status device should be dispalyed", strActualResult, isEventSuccessful);
			}


			return isEventSuccessful;

		}

		/* CREATED BY Dolly DATE*/
		public final boolean DisplayInUSeDevices()

		{
			boolean isEventSuccessful = false;
			Object[] Values = new Object[4];
			String strActualResult = "";
			Reporter reporter = new Reporter();

			try
			{
				isEventSuccessful = selectStatus_DI("Available");
				if(isEventSuccessful)
				{
					Values = ExecuteCLICommand("run", "Android");
					isEventSuccessful = (boolean)Values[2];
					if(isEventSuccessful)
					{
						PerformAction("", Action.WaitForElement,"10");
						isEventSuccessful = selectStatus_DI("In Use");
						if(isEventSuccessful)
						{
							isEventSuccessful = VerifyDeviceDetailsInGridAndListView("statusicon", "Red", "list");

							if (isEventSuccessful)
							{
								strActualResult = "The indicator for devices Available is Red.";
							}
							else
							{
								strActualResult = "VerifyDeviceDetailsInGridAndListView---" + strErrMsg_AppLib;
							}

							reporter.ReportStep("Verify that when device is Available the indicator is RED", "Indicator for devices Available should be RED.", strActualResult, isEventSuccessful);

						}
						else
						{
							throw new RuntimeException("Could not selected 'In Use' staus checkbox.");
						}
					}
					else
					{
						throw new RuntimeException("Could not connected a device");
					}
				}
				else
				{
					throw new RuntimeException("Could not selected 'Available' staus checkbox.");
				}
			}
			catch (RuntimeException e)
			{
				isEventSuccessful = false;
				strActualResult = "Connecting a device" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
				reporter.ReportStep("In Use status device not dispalyed, connecting with CLI command", "In Use status device should be dispalyed", strActualResult, isEventSuccessful);

			}

			return isEventSuccessful;
		}

		//<!--Created by : Deepak Solanki 25/11/2015-->
		//<!--Last modified : 25/11/2015 by Deepak Solanki-->
		//Search the device for the given input value
		public final boolean searchDevice_DI(String deviceSearch)
		{
			boolean isEventSuccessful = false;
			strErrMsg_AppLib = "";
			try
			{
				isEventSuccessful = PerformAction("txtDeviceSearch",Action.Type,deviceSearch);
				waitForPageLoaded();
				if (!isEventSuccessful)
				{
					strErrMsg_AppLib="Unable to type in search box";
					throw new RuntimeException(strErrMsg_AppLib);
				}
			}
			catch (RuntimeException e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "searchDevice_DI--- " + "Exception occurred at : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return isEventSuccessful;
		}

		public final boolean searchApp_deviceconnectDialogbox(String appname)
		{
			boolean isEventSuccessful = false;
			strErrMsg_AppLib = "";
			try
			{
				char[] c = appname.toCharArray();
				for(int i =0 ; i<c.length;i++)
				{
					isEventSuccessful = PerformAction("//input[@placeholder='Search...' and @class='floatright']",Action.sendkeys, String.valueOf(c[i]));
				}

				if (!isEventSuccessful)
				{
					throw new RuntimeException("Could not type searching app name into search box" );
				}
				else
				{
					String Apps =   GetTextOrValue("//div[contains(@class,'application-icon-container')]/../a", "text");
					if(Apps.toLowerCase().startsWith(appname.toLowerCase()))
					{
						isEventSuccessful = true;
					}
					else
					{
						throw new RuntimeException("Searched application is not bieng dispalyed");
					}
				}

			}
			catch (RuntimeException e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "searchDevice_DI--- " + "Exception occurred at : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return isEventSuccessful;
		}

		public final boolean Searchuser_Users(String userid) 
		{
			boolean isEventSuccessful = false;
			strErrMsg_AppLib = "";
			try
			{
				Thread.sleep(1000);
				PerformAction("",Action.WaitForElement,"10");
				isEventSuccessful = PerformAction("//input[@placeholder='Search...']",Action.Type, userid);
				
				PerformAction("",Action.WaitForElement,"10");
				if (!isEventSuccessful)
				{
					throw new RuntimeException("Could not type searching app name into search box" );
				}
				else
				{
					PerformAction("",Action.WaitForElement,"5"); 
					String username =   GetTextOrValue("//div[@class='status-description-row']//a", "text");
					if(username.toLowerCase().startsWith(userid.toLowerCase()))
					{
						isEventSuccessful = true;
					}
					else
					{
						throw new RuntimeException("Searched application is not bieng dispalyed");
					}
				}

			}
			catch (RuntimeException e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "searchDevice_DI--- " + "Exception occurred at : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			catch(Exception e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "searchDevice_DI--- " + "Exception: " + e.getMessage();
			}
			return isEventSuccessful;
		}

		//<!--Created by : Deepak Solanki 25/11/2015-->
		//<!--Last modified : 27/11/2015 by Deepak Solanki-->
		//Verifies selection of status checkboxes on Device Index page.
		public final boolean verifyselectionof_StatusFilter(String [] checkboxesToBeChecked, String [] checkboxesToBeUnChecked, String xpathOfChkBoxesInOR)
		{
			boolean isEventSuccessful = false, flag=false, shouldBeChecked=false, shouldBeUnChecked=false;
			strErrMsg_AppLib = "";
			String Xpath_checkboxGeneral="", Xpath_checkbox="", currentCheckboxText="";
			int chkBoxCount=0;
			try
			{
				Xpath_checkboxGeneral = getValueFromDictionary(dicOR, xpathOfChkBoxesInOR); //Getting xpath of the checkboxes from Or
				chkBoxCount = getelementCount(Xpath_checkboxGeneral); //getting number of checkboxes of the same type (eg. all checkboxes under platform
				if(checkboxesToBeChecked.length>0)
				{
					for (int i = 1; i <= chkBoxCount; i++)
					{
						flag = false; // Set flag to false for every iteration
						Xpath_checkbox = Xpath_checkboxGeneral + "[" + i + "]"; // Getting xpath of each checkbox by puting index to the general xpath
						currentCheckboxText = GetTextOrValue(Xpath_checkbox + "/..", "text"); //Getting text in front of the checkbox, i.e. text of the parent of the checkbox that has text associated with the checkbox

						for(String chkName : checkboxesToBeChecked)
						{
							shouldBeChecked = currentCheckboxText.startsWith(chkName);
							if(shouldBeChecked)
							{
								flag=PerformAction(Xpath_checkbox, Action.isSelected);
								if(!flag)
								{
									throw new RuntimeException("Default status of "+chkName+" checkbox is unchecked");
								}
								break;
							}
						}
					}
				}
				if(checkboxesToBeUnChecked.length>0)
				{
					for (int i = 1; i <=chkBoxCount; i++)
					{
						flag = false; // Set flag to false for every iteration
						Xpath_checkbox = Xpath_checkboxGeneral + "[" + i + "]"; // Getting xpath of each checkbox by puting index to the general xpath
						currentCheckboxText = GetTextOrValue(Xpath_checkbox + "/..", "text"); //Getting text in front of the checkbox, i.e. text of the parent of the checkbox that has text associated with the checkbox

						for(String chkName : checkboxesToBeUnChecked)
						{
							shouldBeUnChecked = currentCheckboxText.startsWith(chkName);
							if(shouldBeUnChecked)
							{
								flag=PerformAction(Xpath_checkbox, Action.isSelected);
								if(flag)
								{
									throw new RuntimeException("Default status of "+chkName+" checkbox is checked");
								}
								break;
							}
						}
					}
				}
				isEventSuccessful=true;
			}
			catch(Exception e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib ="default selection of status checkboxes -- Exception occured : "+e.getMessage();
			}
			return isEventSuccessful;
		}

		//<!--Created by : Deepak Solanki 25/11/2015-->
		//<!--Last modified : 27/11/2015 by Deepak Solanki-->
		//Verifies selection of platform checkboxes on Device Index page.
		public final boolean verifyselectionof_PlatformFilter(String [] checkboxesToBeChecked, String [] checkboxesToBeUnChecked, String xpathOfChkBoxesInOR)
		{
			boolean isEventSuccessful = false, flag=false, shouldBeChecked=false, shouldBeUnChecked=false;
			strErrMsg_AppLib = "";
			String Xpath_checkboxGeneral="", Xpath_checkbox="", currentCheckboxText="";
			int chkBoxCount=0;
			try
			{
				Xpath_checkboxGeneral = getValueFromDictionary(dicOR, xpathOfChkBoxesInOR); //Getting xpath of the checkboxes from Or
				chkBoxCount = getelementCount(Xpath_checkboxGeneral); //getting number of checkboxes of the same type (eg. all checkboxes under platform
				if(checkboxesToBeChecked.length>0)
				{
					for (int i = 1; i <= chkBoxCount; i++)
					{
						flag = false; // Set flag to false for every iteration
						Xpath_checkbox = Xpath_checkboxGeneral + "[" + i + "]"; // Getting xpath of each checkbox by puting index to the general xpath
						currentCheckboxText = GetTextOrValue(Xpath_checkbox + "/..", "text"); //Getting text in front of the checkbox, i.e. text of the parent of the checkbox that has text associated with the checkbox

						for(String chkName : checkboxesToBeChecked)
						{
							shouldBeChecked = currentCheckboxText.startsWith(chkName);
							if(shouldBeChecked)
							{
								flag=PerformAction(Xpath_checkbox, Action.isSelected);
								if(!flag)
								{
									throw new RuntimeException("Default status of "+chkName+" checkbox is unchecked");
								}
								break;
							}
						}
					}
				}
				if(checkboxesToBeUnChecked.length>0)
				{
					for (int i = 1; i <=chkBoxCount; i++)
					{
						flag = false; // Set flag to false for every iteration
						Xpath_checkbox = Xpath_checkboxGeneral + "[" + i + "]"; // Getting xpath of each checkbox by puting index to the general xpath
						currentCheckboxText = GetTextOrValue(Xpath_checkbox + "/..", "text"); //Getting text in front of the checkbox, i.e. text of the parent of the checkbox that has text associated with the checkbox

						for(String chkName : checkboxesToBeUnChecked)
						{
							shouldBeUnChecked = currentCheckboxText.startsWith(chkName);
							if(shouldBeUnChecked)
							{
								flag=PerformAction(Xpath_checkbox, Action.isSelected);
								if(flag)
								{
									throw new RuntimeException("Default status of "+chkName+" checkbox is checked");
								}
								break;
							}
						}
					}
				}
				isEventSuccessful=true;
			}
			catch(Exception e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib ="default selection of platform checkboxes -- Exception occured : "+e.getMessage();
			}
			return isEventSuccessful;
		}

		//Created by Deepak Solanki
		public final boolean VerifyMessage_On_Filter_Selection()
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			String warningMessage = "";

			try
			{
				PerformAction("btnRefresh_Devices",Action.Click);
				PerformAction("eleNoTableRowsWarning", Action.WaitForElement,"4");
				if (dicCommon.get("BrowserName").toLowerCase().contains("ie") && (GenericLibrary.IEversion.equals("11")))
				{
					PerformAction("", Action.WaitForElement,"4");
				}
				flag = PerformAction("eleNoTableRowsWarning", Action.isDisplayed);
				if (flag)
				{
					warningMessage = GetTextOrValue("eleNoTableRowsWarning", "text");
					flag = warningMessage.startsWith("No devices match your filter criteria");
					if (!flag)
					{   
						throw new RuntimeException("Warning message is incorrect. It is : '" + warningMessage + "'. ");
					}
					else
					{
						strErrMsg_AppLib = "Correct warning message displayed on table";
					}
				}
				else if (PerformAction("eleNoTableRowsWarning", Action.isNotDisplayed))
				{
					strErrMsg_AppLib = "No warning message displayed on table.";
				}
			}
			catch (RuntimeException e)
			{
				strErrMsg_AppLib = "VerifyNoRowsWarningOnTable---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return flag;
		}

		//This function verfiy Applicatioin Details on AppDetails page.
		//<!--Created By : Dolly Agarwal-->
		//
		//@param fields name has to be verified.
		//@return 
		//
		public final Object[]  VerifyAppDetailsOnApplicationPage(String AppDetails)
		{
			strErrMsg_AppLib = "";
			boolean flag = true;
			String actualValue = "";
			String ValueExpected = "";
			Object[] values = new Object[15];
			String Appfields[]=  AppDetails.split(",");
			try
			{
				for(String field : Appfields)
				{
					switch (field) 
					{ 

					case "Application Name":
						String AppName =  GetTextOrValue(dicOR.get("eleAppName_AppDetailsPage"), "text");
						values[0] = AppName;
						if(!AppName.equals(""))
						{
							values[1]= flag;
						}
						else
							throw new RuntimeException("Application name not found");
						break;

					case "OS":
						String OS =  GetTextOrValue(dicOR.get("eleOSName_AppDetailsPage"), "text");
						values[2] = OS;
						if(!OS.equals(""))
						{
							values[3]= flag;
						}
						else
							throw new RuntimeException("OS name not found");
						break;  

					case "File name":
						String FileName =  GetTextOrValue(dicOR.get("eleFileName_AppDetailsPage"), "text");
						values[4] = FileName;
						if(!FileName.equals(""))
						{
							values[5]= flag;
						}
						else
							throw new RuntimeException("FileName name not found");
						break;   

					case "Version":
						String Version =  GetTextOrValue(dicOR.get("eleVersion_AppDetailsPage"), "text");
						values[6] = Version;
						if(!Version.equals(""))
						{
							values[9]= flag;
						}
						else
							throw new RuntimeException("OS name not found");
						break;

					}
				}

			}catch (Exception e)
			{
				strErrMsg_AppLib = "VerifyAppDetailsOnApplicationPage---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
				flag = false;
				values[7]= flag;
				values[8] = strErrMsg_AppLib;
			}

			return values;

		}
		public final boolean Verify_timezone_JSON()
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			String filePath=dicConfig.get("Artifacts")+"\\additionalFolder\\JSONFile.txt";
			try
			{
				flag=WindowNames.readTxtFile(filePath);
				strErrMsg_AppLib ="Report is in file local time format";
			}
			catch(Exception e)
			{
				flag=false;
				strErrMsg_AppLib = "Verify_TimeZone --- " +e.getMessage();
			}
			return flag;
		}

		public final boolean Verify_CLI_Guide_File_Existence()
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			String filePath=dicConfig.get("Artifacts")+"\\additionalFolder\\";
			try
			{
				flag=WindowNames.getFileExistence(filePath);
			}
			catch(Exception e)
			{
				flag=false;
				strErrMsg_AppLib = "Verify_TimeZone --- " +e.getMessage();
			}
			return flag;
		}
		public final Boolean verifyUsage_HistoryToFrom(String type, String filePath, String colNameVerify, String data_to_verify)
		{
			return verifyUsage_HistoryToFrom(type, filePath, colNameVerify, data_to_verify, ""); 
		}
		public final Boolean verifyUsage_HistoryToFrom(String type, String filePath, String colNameVerify, String data_to_verify, String device) 
		{
			Boolean flag=false;
			strErrMsg_AppLib = "";
			try
			{
				String [] columnName=new String[2];	
				String [] result=new String [5];
				String csvFilename = filePath;
				int index=0 ;
				if(type.contains("usage") && type.contains("usagetilldate"))
				{
					columnName[0]="End Date";
					columnName[1]="Device";
				}
				else
				{
					columnName[0]="Date";
					columnName[1]="Device";
				}
				CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
				String[] row = null;

				String [] dates=data_to_verify.split(",");
				if(device.length()<1)
				{
					int rowCount=0;
					while((row = csvReader.readNext()) != null) 
					{
						for(int i=0;i<row.length;i++)
						{
							if(rowCount==0 && row[i].contains(columnName[0]))
							{
								index=i;
								break;
							}
							else if(rowCount>0 && i==index)
							{
								if(type.contains("usagetilldate") || type.contains("historytilldate"))
								{
									if(verifyDatesnDevice(getDateFormat(row[i]),dates[0]))
									{
										flag=true;   
										break;
									}
									else
									{
										flag=false;	 
										strErrMsg_AppLib="Date : "+getDateFormat(row[i])+" not found in range in given range";
										break;
									}
								}
								else
								{
									if(verifyDateInRange(getDateFormat(row[i]),dates[0],dates[dates.length-1]))
									{
										flag=true;   
										break;
									}
									else
									{
										flag=false;	 
										strErrMsg_AppLib="Date : "+getDateFormat(row[i])+" not found in range in given range";
										System.out.println("row number: "+rowCount);
										break;
									}
								}
							}
						}
						rowCount++;
					}
				}
				else
				{
					int rowCount=0;
					int index2=1;
					index=2;
					while((row = csvReader.readNext()) != null) 
					{
						for(int i=0;i<row.length;i++)
						{
							if(rowCount==0 && row[i].contains(columnName[0]))
							{
								index=i;
							}
							if(rowCount==0 && row[i].equals(columnName[1]))
							{
								index2=i;
							}
							else if(rowCount>0 && i==index)
							{
								if(verifyDateInRange(getDateFormat(row[index]),dates[0],dates[dates.length-1]) && row[index2].contains(device))
								{
									flag=true;	   
									break;
								}
								else
								{
									flag=false; 
									strErrMsg_AppLib="Date : "+getDateFormat(row[index])+" not found in range in given range";
									System.out.println("row number: "+rowCount);
									break;
								}
							}
						}
						rowCount++;
					}
				}

				csvReader.close();
			}
			catch(Exception e)
			{
				flag=false;
				strErrMsg_AppLib = "Exception : "+e.getMessage();
			}
			return flag;
		}

		public final String getLastUpdateDetails()
		{
			String date="";
			strErrMsg_AppLib = "";
			try
			{
				PerformAction("appLastUpdateDetails", Action.WaitForElement,"4");
				date=getText("appLastUpdateDetails");
				strErrMsg_AppLib = date+" is the last updated date of the given app";
			}
			catch(Exception e)
			{
				strErrMsg_AppLib = ""+e.getMessage();
			}
			return date;
		}

		public final Boolean installnUninstallApp_On_Device(String action, Boolean preserveData)
		{
			Boolean flag=false;
			strErrMsg_AppLib = "";
			String appName="";
			switch(action.toLowerCase())
			{
			case "uninstall":
				try
				{
					waitForPageLoaded();
					PerformAction("browser","waitforpagetoload");
					appName=getText((dicOR.get("deviceDetailsUnInstallFirstApp"))+"/td/a[2]");
					if(PerformAction((dicOR.get("deviceDetailsUnInstallFirstApp"))+"/td[4]/button",Action.isDisplayed))
					{
						flag=PerformAction((dicOR.get("deviceDetailsUnInstallFirstApp"))+"/td[4]/button",Action.ClickUsingJS);
						if(flag)
						{
							waitForPageLoaded();
							if(preserveData)
							{
								flag=PerformAction("preserveDataUninstallApp",Action.Click);
								if(!flag)
								{
									strErrMsg_AppLib = "Preserve Data checkbox not found the page";
									flag=false;
									break;
								}
							}
							flag=PerformAction("UninstallAppContinuebtn",Action.isDisplayed);
							if(flag)
							{
								flag=PerformAction("UninstallAppContinuebtn",Action.Click);
								if(flag)
								{
									waitForPageLoaded();
									PerformAction("browser","waitforpagetoload");
									PerformAction("browser","refresh");
									waitForPageLoaded();
									PerformAction("browser","wait");
									if (dicCommon.get("BrowserName").toLowerCase().contains("ie") && (GenericLibrary.IEversion.equals("11")))
									{
										PerformAction("browser","refresh");
									}
									if(!(appName.equals(getText((dicOR.get("deviceDetailsUnInstallFirstApp"))+"/td/a[2]"))))
									{
										strErrMsg_AppLib = "Application uninstalled successfully";
										flag=true;
									}
									else
									{
										strErrMsg_AppLib = "Application did not uninstalled successfully";
										flag=false;
									}
								}
								else
								{
									strErrMsg_AppLib = "Unable to click on Continue button.";
									flag=false;
								}
							}
							else
							{
								strErrMsg_AppLib = "Unable to locate Continue button.";
								flag=false;
							}
						}
						else
						{
							strErrMsg_AppLib = "Uninstall button for the app "+appName+" not found";
							flag=false;
						}
					}
					else
					{
						strErrMsg_AppLib = "Uninstall button for the app "+appName+" not found";
						flag=false;
					}
				}
				catch(Exception e)
				{
					strErrMsg_AppLib = ""+e.getMessage();
					flag=false;
				}
				break;

			case "install":
				try
				{
					waitForPageLoaded();
					PerformAction("browser","waitforpagetoload");
					appName=getText((dicOR.get("deviceDetailsInstallFirstApp"))+"/td[1]/a[2]");
					if(PerformAction((dicOR.get("deviceDetailsInstallFirstApp"))+"/td[4]/button",Action.isDisplayed))
					{
						flag=PerformAction((dicOR.get("deviceDetailsInstallFirstApp"))+"/td[4]/button",Action.Click);
						if(flag)
						{
							waitForPageLoaded();
							PerformAction("browser","waitforpagetoload");
							PerformAction("browser","refresh");
							waitForPageLoaded();
							PerformAction("browser","wait");
							PerformAction("browser","refresh");
							if (dicCommon.get("BrowserName").toLowerCase().contains("ie") && (GenericLibrary.IEversion.equals("11")))
							{
								PerformAction("browser","refresh");
							}
							if((getColumnsValue((dicOR.get("deviceDetailsUnInstallFirstApp").replace("tr[1]", "tr[*]"))+"/td/a[2]")).contains(appName))
							{
								strErrMsg_AppLib = "Application installed successfully";
								flag=true;
							}
							else
							{
								strErrMsg_AppLib = "Application not installed";
								flag=false;
							}
						}
						else
						{
							strErrMsg_AppLib = "Unable to click install button of first app "+appName;
							flag=false;
						}
					}
					else
					{
						strErrMsg_AppLib = "Install button for the app "+appName+" not found";
						flag=false;
					}
				}
				catch(Exception e)
				{
					strErrMsg_AppLib = ""+e.getMessage();
					flag=false;
				}
				break;
			}
			return flag;
		}

		public final boolean navigateToNavLinkPages(String index, String expectedPageElement)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			try
			{
				if (dicCommon.get("BrowserName").toLowerCase().contains("ie") && (GenericLibrary.IEversion.equals("11")))
				{
					waitForPageLoaded();
					PerformAction(dicOR.get("navlinkDeviceIndexPage").replace("_index_", index),Action.WaitForElement,"5");
				}
				flag = PerformAction(dicOR.get("navlinkDeviceIndexPage").replace("_index_", index), Action.ClickUsingJS);
				if (flag)
				{
					waitForPageLoaded();
					flag = PerformAction(expectedPageElement, Action.WaitForElement);
					if (!flag)
					{
						throw new RuntimeException("'" + expectedPageElement + "' not found.");
					}
				}
				else
				{
					throw new RuntimeException("link not found");
				}
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "navigateToNavLinkPages--- " + "Exception : '" + e.getMessage();
			}
			return flag;
		}

		public final boolean setCustomizedDates(String StartDate, String EndDate)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			try
			{
				waitForPageLoaded();
				flag=PerformAction("DatesDrpDwnonUsagePage",Action.isDisplayed);
				if(flag)
				{
					flag=PerformAction("DatesDrpDwnonUsagePage",Action.Select,"Custom");
					if(flag)
					{
						waitForPageLoaded();
						PerformAction("startDateUsageCustom",Action.WaitForElement);
						PerformAction("startDateUsageCustom",Action.Type,StartDate);
						waitForPageLoaded();
						PerformAction("browser","waitforpagetoload");
						PerformAction("endDateUsageCustom",Action.PressEsc);
						/*PerformAction("endDateUsageCustom",Action.PressEsc);*/
						waitForPageLoaded();
						//PerformAction("endDateUsageCustom",Action.Click);
						PerformAction("endDateUsageCustom",Action.Type,EndDate);
						waitForPageLoaded();
						PerformAction("browser","waitforpagetoload");
						/*PerformAction("endDateUsageCustom",Action.PressEsc);
						PerformAction("endDateUsageCustom",Action.PressEsc);*/
						waitForPageLoaded();
						strErrMsg_AppLib = "Dates set successfully";
						flag=true;
					}
					else
					{
						strErrMsg_AppLib = "Unable to select custom option";
						flag=false;
					}
				}
				else
				{
					strErrMsg_AppLib = "Currently you are not on usage page";
					flag=false;
					return flag;
				}
			}
			catch(Exception e)
			{
				strErrMsg_AppLib = ""+e.getMessage();
				flag=false;
			}
			return flag;
		}

		public final boolean verifyUsagenHistoryDates(String from, String to,String type)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			List<String> li=new ArrayList<String>();
			int index=1;
			try
			{
				if(type.equalsIgnoreCase("usage"))
				{
					li=getUsageColumnValues("DatesinUsageTable");
				}
				else
				{
					li=getHistoryFirstLastPageColumnValues("DatesinUsageTable");
				}
				if(!li.isEmpty())
				{
					for(String date:li)
					{

						if(!verifyDateInRange(getDateFormat(new Date(date.split(" ")[0])),from,to))
						{
							if(GetTextOrValue(dicOR.get("DurationinUsageTable").replace("tr[*", "tr["+String.valueOf(index)),"text").contains("d"))
							{
								flag=true;
							}
							else
							{
								flag=false;
								strErrMsg_AppLib="Date : "+date+" not found in range in given range";
								break;
							}
						}
						else
						{
							flag=true;
						}
						index++;
					}
				}
				else
				{
					strErrMsg_AppLib = "Failed to extract dates.";
				}
			}
			catch(Exception e)
			{
				flag=false;
				strErrMsg_AppLib = ""+e.getMessage();
			}
			return flag;
		}

		public final boolean verifyUsageColumns()
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			List<String> li=new ArrayList<String>();
			try
			{
				li=getUsageColumnValues("DatesinUsageTable");
				if(!li.isEmpty())
				{
					for(String date:li)
					{

						if(date.length()<1)
						{
							flag=false;
							strErrMsg_AppLib = "Dates column contains null value";
							break;
						}
						else
						{
							flag=true;
						}
					}
				}
				else
				{
					flag=false;
					strErrMsg_AppLib = "Dates column blank";
				}
				if(flag)
				{
					li.clear();
					li=getUsageColumnValues("DurationinUsageTable");
					if(!li.isEmpty())
					{
						for(String duration:li)
						{

							if(duration.length()<1)
							{
								flag=false;
								strErrMsg_AppLib = "Duration column contains null value";
								break;
							}
							else
							{
								flag=true;
							}
						}
					}
					else
					{
						flag=false;
						strErrMsg_AppLib = "Duration column blank";
					}
				}
				if(flag)
				{
					li.clear();
					li=getUsageColumnValues("DeviceinUsageTable");
					if(!li.isEmpty())
					{
						for(String device:li)
						{

							if(device.length()<1)
							{
								flag=false;
								strErrMsg_AppLib = "Devcie column contains null value";
								break;
							}
							else
							{
								flag=true;
							}
						}
					}
					else
					{
						flag=false;
						strErrMsg_AppLib = "Devcie column blank";
					}
				}
				if(flag)
				{
					li.clear();
					li=getUsageColumnValues("UsersinUsageTable");
					if(!li.isEmpty())
					{
						for(String device:li)
						{

							if(device.length()<1)
							{
								flag=false;
								strErrMsg_AppLib = "User column contains null value";
								break;
							}
							else
							{
								flag=true;
							}
						}
					}
					else
					{
						flag=false;
						strErrMsg_AppLib = "User column blank";
					}
				}
               if(flag)
         	   {
    		   li.clear();
    		   li=getUsageColumnValues("ApplicationinUsageTable");
        	   if(!li.isEmpty())
        	   {
        		   for(int i=0;i<li.size();i++)
        		   {
        			   if(li.get(i).length()<1)
        			   {
        				   strErrMsg_AppLib = "Application column contains null value";
        				   flag=true;
        			   }
        			   else
        			   {
        				   flag=PerformAction(dicOR.get("ApplicationinUsageTable").replace("tr[*]/td[5]", "tr["+(i+1)+"]/td[5]/a"),Action.Exist);
        			   }
        		   }
        	   }
        	   else
        	   {
        		   flag=false;
        		   strErrMsg_AppLib = "Application column blank";
        	   }
    	   }
			}
			catch(Exception e)
			{
				flag=false;
				strErrMsg_AppLib = ""+e.getMessage();
			}
			return flag;
		}
		public final boolean verifyUsageReservedColumn()
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			List<String> li=new ArrayList<String>();
			try
			{
				li=getUsageColumnValues("ReservedinUsageTable");
				if(!li.isEmpty())
				{
					for(String reserved:li)
					{

						if(reserved.length()>0)
						{
							if(reserved.equals("Yes"))
							{
								flag=true;
							}
							else
							{
								flag=false;
								strErrMsg_AppLib = "Reserved column has value "+reserved;
							}
						}
						else
						{
							flag=true;
						}
					}
				}
				else
				{
					flag=false;
					strErrMsg_AppLib = "Unable to find Reserved column";
				}
			}
			catch(Exception e)
			{
				flag=false;
				strErrMsg_AppLib = ""+e.getMessage();
			}
			return flag;
		}


		public final boolean uploadApplication()
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			try
			{
				PerformAction("uploadApplicationlnk",Action.WaitForElement);
				flag=PerformAction("uploadApplicationlnk",Action.isDisplayed);
				if(flag)
				{
					flag=PerformAction("uploadApplicationlnk",Action.Click);
					if(flag)
					{
						flag=PerformAction("uploadApplicationlnk",Action.UploadApplication);
						PerformAction("uploadProgressBarApplicationPage",Action.WaitForElement);
                                        if(flag){
					  WebDriverWait wait = new WebDriverWait(driver, 300);
					  wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='alert in fade alert-success']")));
				                }
						flag=PerformAction("uploadProgressBarApplicationPage",Action.isDisplayed);
						return flag;
					}
					else
					{
						flag=false;
						strErrMsg_AppLib = "Upload Application Button did not clicked";
					}
				}
				else
				{
					flag=false;
					strErrMsg_AppLib = "Upload Application Button not found";
				}

			}
			catch(Exception e)
			{
				flag=false;
				strErrMsg_AppLib = ""+e.getMessage();
			}
			return flag;
		}



		public final boolean verifyApplicationsFilteredOSColumn()
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			List<String> li=new ArrayList<String>();
			try
			{
				li=getColumnsValue("appOSDetails");
				if(!li.isEmpty())
				{
					for(String OS:li)
					{

						if(OS.length()>0)
						{
							if(OS.contains("Android"))
							{
								flag=true;
							}
							else
							{
								flag=false;
								strErrMsg_AppLib = "OS column has value "+OS;
							}
						}
						else
						{
							flag=false;
							strErrMsg_AppLib = "OS column value blank.";
						}
					}
				}
				else
				{
					flag=false;
					strErrMsg_AppLib = "Unable to find Reserved column";
				}
			}
			catch(Exception e)
			{
				flag=false;
				strErrMsg_AppLib = ""+e.getMessage();
			}
			return flag;
		}

		public final boolean VerifySortingonApplicationsPage(String xPath, String column, String sortingOrder)
		{
			boolean flag = false;
			int columnsCountAfterSorting = 0, columnsCountBeforeSorting = 0;
			strErrMsg_AppLib = "";
			List ListDisplayed = new ArrayList();
			List SortedListDisplayed = new ArrayList();
			List<WebElement> columnNameValues=new ArrayList<WebElement>();
			try
			{
				waitForPageLoaded();
				SortedListDisplayed.removeAll(SortedListDisplayed);
				ListDisplayed.removeAll(ListDisplayed);
				switch(column.toLowerCase())
				{
				case "name":
					columnNameValues = getelementsList("appNameColumnDetails");
					break; 
				case "os":
					columnNameValues = getelementsList("appOSDetails");
					break;
				case "latest version":
					columnNameValues = getelementsList("appLatestVersionDetails");
					break;
				case "last updated":
					columnNameValues = getelementsList("appLastUpdateDetails");
					break;
				}

				List<WebElement>  TotalRows = columnNameValues;
				columnsCountBeforeSorting = TotalRows.size();
				if(columnsCountBeforeSorting==1)
				{
					throw new RuntimeException("Cannot validate with a single row in table.");
				}

				switch(column.toLowerCase())
				{
				case "name":
					for(int i=0;i<TotalRows.size();i++)
					{
						ListDisplayed.add(TotalRows.get(i).getText());
					}
					Collections.sort(ListDisplayed, String.CASE_INSENSITIVE_ORDER); 
					//flag = PerformAction(TableContainerXpath+dicOR.get("lnk_OtherColumnsOnDevicesPage").replace("_ColumnNameSorting_", columnName),Action.Click);
					if(sortingOrder.equalsIgnoreCase("descending"))
					{
						flag = PerformAction(xPath,Action.Click);
						Collections.reverse(ListDisplayed);
					}
					TotalRows = getelementsList("appNameColumnDetails");
					columnsCountAfterSorting = TotalRows.size();
					if(columnsCountBeforeSorting==columnsCountAfterSorting)
					{
						for(int i=0;i<TotalRows.size();i++)
						{
							SortedListDisplayed.add(TotalRows.get(i).getText());
						}
					}

					for(int i = 0; i < SortedListDisplayed.size(); i++)
					{
						if(!SortedListDisplayed.get(i).equals(ListDisplayed.get(i)))
						{
							throw new RuntimeException("SortedListDisplayed :-" + SortedListDisplayed.get(i) +"not equals to ListDisplayed :-" + ListDisplayed.get(i));
						}
						else
						{
							flag=true;
						}
					}
					break;

				case "os":
					for(int i=0;i<TotalRows.size();i++)
					{
						ListDisplayed.add(TotalRows.get(i).getText());
					}
					Collections.sort(ListDisplayed, String.CASE_INSENSITIVE_ORDER); 
					flag = PerformAction(xPath,Action.Click);
					if(sortingOrder.equalsIgnoreCase("descending"))
					{
						flag = PerformAction(xPath,Action.Click);
						Collections.reverse(ListDisplayed);
					}
					TotalRows = getelementsList("appOSDetails");
					columnsCountAfterSorting = TotalRows.size();
					if(columnsCountBeforeSorting==columnsCountAfterSorting)
					{
						for(int i=0;i<TotalRows.size();i++)
						{
							SortedListDisplayed.add(TotalRows.get(i).getText());
						}
					}

					for(int i = 0; i < SortedListDisplayed.size(); i++)
					{
						if(!SortedListDisplayed.get(i).equals(ListDisplayed.get(i)))
						{
							throw new RuntimeException("SortedListDisplayed :-" + SortedListDisplayed.get(i) +"not equals to ListDisplayed :-" + ListDisplayed.get(i));
						}
						else
						{
							flag=true;
						}
					}
					break;

				case "latest version":
					List<Version> versions = new ArrayList<Version>();
					List<String> Sortedversions = new ArrayList<String>();
					for(int i=0;i<TotalRows.size();i++)
					{
						versions.add(new Version(TotalRows.get(i).getAttribute("title").split(" ")[0]));
					}
					Collections.sort(versions); 
					flag = PerformAction(xPath,Action.Click);
					if(sortingOrder.equalsIgnoreCase("descending"))
					{
						flag = PerformAction(xPath,Action.Click);
						Collections.reverse(ListDisplayed);
					}
					TotalRows = getelementsList("appLatestVersionDetails");
					columnsCountAfterSorting = TotalRows.size();
					if(columnsCountBeforeSorting==columnsCountAfterSorting)
					{
						for(int i=0;i<TotalRows.size();i++)
						{
							Sortedversions.add(TotalRows.get(i).getAttribute("title").split(" ")[0]);
						}
					}

					for(int i = 0; i < SortedListDisplayed.size(); i++)
					{
						if(!Sortedversions.get(i).equals(versions.get(i)))
						{
							throw new RuntimeException("SortedListDisplayed :-" + SortedListDisplayed.get(i) +"not equals to ListDisplayed :-" + ListDisplayed.get(i));
						}
						else
						{
							flag=true;
						}
					}
					break;

				case "last updated":
					for(int i=0;i<TotalRows.size();i++)
					{
						ListDisplayed.add(getAppUploadDateFormat(TotalRows.get(i).getText()));
					}
					Collections.sort(ListDisplayed); 
					flag = PerformAction(xPath,Action.Click);
					if(sortingOrder.equalsIgnoreCase("descending"))
					{
						flag = PerformAction(xPath,Action.Click);
						Collections.reverse(ListDisplayed);
					}
					TotalRows = getelementsList("appLastUpdateDetails");
					columnsCountAfterSorting = TotalRows.size();
					if(columnsCountBeforeSorting==columnsCountAfterSorting)
					{
						for(int i=0;i<TotalRows.size();i++)
						{
							SortedListDisplayed.add(getAppUploadDateFormat(TotalRows.get(i).getText()));
						}
					}

					for(int i = 0; i < SortedListDisplayed.size(); i++)
					{
						if(!SortedListDisplayed.get(i).equals(ListDisplayed.get(i)))
						{
							throw new RuntimeException("SortedListDisplayed :-" + SortedListDisplayed.get(i) +"not equals to ListDisplayed :-" + ListDisplayed.get(i));
						}
						else
						{
							flag=true;
						}
					}
					break;
				}

			}
			catch (Exception e)
			{
				flag = false;
				strErrMsg_AppLib = "Columns are displayed--- " + "Exception : '" + e.getMessage() ;
			} 
			return flag;
		}  

		public final boolean verifyallAppDetails_ApplicationDetails(String appName, String version, String Date)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			List<String> li=new ArrayList<String>();
			try
			{
				li=getColumnsValue("appAllInforAppDetails");
				if(!li.isEmpty())
				{
					for(int i=0;i<li.size();i++)
					{
						switch(i)
						{
						case 1:
							if(li.get(i).toString().equals(appName))
							{
								return true;
							}
							else
							{
								strErrMsg_AppLib = "App name is different than Display name";
								return false;
							}
						case 2:
							if(li.get(i).toString().equals("iOS"))
							{
								return true;
							}
							else
							{
								strErrMsg_AppLib = "OS version is different than selected filter";
								return false;
							}
						case 3:
							if(li.get(i).toString().length()>0)
							{
								return true;
							}
							else
							{
								strErrMsg_AppLib = "Min OS version is blank";
								return false;
							}
						case 4:
							if(li.get(i).toString().contains(version))
							{
								return true;
							}
							else
							{
								strErrMsg_AppLib = "Version mis-match";
								return false;
							}
						case 5:
							if(li.get(i).toString().length()>0)
							{
								return true;
							}
							else
							{
								strErrMsg_AppLib = "Identifier value is blank";
								return false;
							}
						case 6:
							if(li.get(i).toString().length()>0)
							{
								return true;
							}
							else
							{
								strErrMsg_AppLib = "Form Factor value is blank";
								return false;
							}
						case 7:
							if(li.get(i).toString().length()>0)
							{
								return true;
							}
							else
							{
								strErrMsg_AppLib = "Certificate Identity value is blank";
								return false;
							}
						case 8:
							if(li.get(i).toString().length()>0)
							{
								return true;
							}
							else
							{
								strErrMsg_AppLib = "Team Identifier value is blank";
								return false;
							}
						case 9:
							if(li.get(i).toString().equals(Date))
							{
								return true;
							}
							else
							{
								strErrMsg_AppLib = "Uploaded date is different than on Applications page";
								return false;
							}
						case 10:
							if(li.get(i).toString().length()>0)
							{
								return true;
							}
							else
							{
								strErrMsg_AppLib = "File Name is blank";
								return false;
							}
						case 11:
							if(li.get(i).toString().length()>0)
							{
								return true;
							}
							else
							{
								strErrMsg_AppLib = "File Size value is blank";
								return false;
							}
						}
					}
				}
				else
				{
					strErrMsg_AppLib = "Unable to find details on Applications page";
				}

			}
			catch(Exception e)
			{
				flag=false;
				strErrMsg_AppLib = ""+e.getMessage();
			}
			return flag;
		}

		public final boolean verifyProvisionedValues(String platform)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			List<String> li=new ArrayList<String>();
			try
			{
				li=getColumnsValue("provsionedvalueAppDetails");
				if(!li.isEmpty())
				{
					if(platform.equalsIgnoreCase("android"))
					{
						for(String txt:li)
						{
							if(txt.contains("Yes"))
							{
								flag=true;
							}
							else if(txt.contains("No"))
							{
								flag=false;
								strErrMsg_AppLib = "Provisoned value is "+txt;
							}
						}
					}
					else
					{
						for(String txt:li)
						{
							if(txt.contains("Yes"))
							{
								flag=true;
							}
							else if(txt.contains("No"))
							{
								if(PerformAction("provisionedErrMsgAppDetails", Action.isDisplayed))
								{
									flag=true;
								}
								else
								{
									flag=false;
									strErrMsg_AppLib = "Provisoned value is "+txt+ " and no message displayed.";
								}
							}
						}
					}
				}
				else
				{
					strErrMsg_AppLib = "Unable to provisioned details on Applications page";
				}

			}
			catch(Exception e)
			{
				flag=false;
				strErrMsg_AppLib = ""+e.getMessage();
			}
			return flag;
		}

		public final boolean VerifySortingonApplicationDetailsPage()
		{
			boolean flag = false;
			int columnsCountAfterSorting = 0, columnsCountBeforeSorting = 0;
			strErrMsg_AppLib = "";
			List<WebElement> columnNameValues=new ArrayList<WebElement>();
			try
			{
				for(int j=2;j<6;j++)
				{
					switch(j)
					{
					case 3:
						columnNameValues = getelementsList(dicOR.get("versionBuildUploadDateAppDetailsPage").replace("td[2]", "td[3]"));
						break;
					case 4:
						columnNameValues = getelementsList(dicOR.get("versionBuildUploadDateAppDetailsPage").replace("td[2]", "td[4]"));
						break;
					case 2:
						columnNameValues = getelementsList(dicOR.get("versionBuildUploadDateAppDetailsPage"));
						break;
					}
					List<WebElement>  TotalRows = columnNameValues;
					columnsCountBeforeSorting = TotalRows.size();
					if(columnsCountBeforeSorting==1)
					{
						return true;
					}
					if(j==2)
					{
						List<Version> versions = new ArrayList<Version>();
						List<Version> Sortedversions = new ArrayList<Version>();
						for(int i=0;i<TotalRows.size();i++)
						{
							versions.add(new Version(TotalRows.get(i).getText().split(" ")[0]));
						}
						Sortedversions=versions;
						Collections.sort(versions); 

						for(int i = 0; i < Sortedversions.size(); i++)
						{
							if(!Sortedversions.get(i).equals(versions.get(i)))
							{
								flag=false;
							}
							else
							{
								flag=true;
							}
						}
					}
					else
					{
						List<Integer> list = new ArrayList<Integer>();
						List<Integer> Sortedlist = new ArrayList<Integer>();
						for(int i=0;i<TotalRows.size();i++)
						{
							list.add(Integer.parseInt(TotalRows.get(i).getText().split(" ")[0]));
						}
						Sortedlist=list;
						Collections.sort(list); 
						Collections.reverse(list);

						for(int i = 0; i < Sortedlist.size(); i++)
						{
							if(!Sortedlist.get(i).equals(list.get(i)))
							{
								flag=false;
							}
							else
							{
								flag=true;
							}
						}
					}
				}
			}
			catch (Exception e)
			{
				flag = false;
				strErrMsg_AppLib = "Exception : '" + e.getMessage() ;
			} 
			return flag;
		}  
		public final boolean verifyAllEventTypeSelected()
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			List<WebElement> columnNameValues=new ArrayList<WebElement>();
			try
			{
				columnNameValues = getelementsList("eventTypelistchkbox");
				for(WebElement ele:columnNameValues)
				{
					flag=ele.isSelected();
					if(!flag)
					{
						strErrMsg_AppLib = "All the event type not selected by default";
						return flag;
					}
				}

			}
			catch(Exception e)
			{
				flag = false;
				strErrMsg_AppLib = "Exception : '" + e.getMessage() ;
			}
			return flag;
		}
		public final boolean verifyHistorColHeaders()
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			List<WebElement> columnNameValues=new ArrayList<WebElement>();
			try
			{
				waitForPageLoaded();
				columnNameValues = getelementsList("historycolumnheaderlocator");
				for(WebElement ele:columnNameValues)
				{
					if(!(ele.getText().contains("Date") || ele.getText().contains("Event") || ele.getText().contains("Device") || ele.getText().contains("User") || ele.getText().contains("Application")))
					{
						flag=false;
						strErrMsg_AppLib = "Column name : "+ele.getText()+" displayed";
					}
					else
					{
						flag=true;
					}
				}
			}
			catch(Exception e)
			{
				flag = false;
				strErrMsg_AppLib = "Exception : '" + e.getMessage() ;
			}
			return flag;
		}

		public final boolean selectEvents_History(String events)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			try
			{
				String[] statusesArray = events.split("[,]", -1);
				waitForPageLoaded();
				flag = PerformAction("chkEventDevices", Action.WaitForElement, "5");
				if (flag)
				{
					flag = selectCheckboxes_DI(statusesArray, "chkEventDevices");
					if (!flag)
					{
						throw new RuntimeException(strErrMsg_AppLib);
					}
				}
				else
				{
					throw new RuntimeException("Events checkboxes are not displayed on the page.");
				}
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib ="Exception : '"+ e.getMessage();
			}
			return flag;
		}

		public final Boolean verifyHistoryEventColumnValue(String val1, String val2, String xPath)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			List<WebElement> columnNameValues=new ArrayList<WebElement>();
			try
			{
				if(PerformAction("nohistorydisplaymsg", Action.isDisplayed))
				{
					if(GetTextOrValue("nohistorydisplaymsg","text").contains("There is no history to display"))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					columnNameValues = getelementsList(xPath);
					for(WebElement ele:columnNameValues)
					{
						if(!(ele.getText().contains(val1) || ele.getText().contains(val2)))
						{
							flag=false;
							strErrMsg_AppLib = "Column contains : "+ele.getText();
						}
						else
						{
							flag=true;
						}
					}
				}
			}
			catch(Exception e)
			{
				flag = false;
				strErrMsg_AppLib ="Exception : '"+ e.getMessage();
			}
			return flag;
		}

		public final Boolean verifyOrderDropDownHistory()
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			List<String> newestcolumnValues=new ArrayList<String>();
			List<String> oldestcolumnValues=new ArrayList<String>();
			try
			{
				newestcolumnValues=getUsageColumnValues("DatesinUsageTable");
				flag=PerformAction("orderdropdown",Action.Select,"Oldest first");
				if(flag)
				{
					oldestcolumnValues=getUsageColumnValues("DatesinUsageTable");
					Collections.reverse(newestcolumnValues);
					if(oldestcolumnValues==newestcolumnValues)
					{
						return true;
					}
					else
					{
						strErrMsg_AppLib ="Drop down values are not working";
					}
				}
				else
				{
					strErrMsg_AppLib ="Unable to select value from order drop down";
				}
			}
			catch(Exception e)
			{
				flag = false;
				strErrMsg_AppLib ="Exception : '"+ e.getMessage();
			}
			return flag;
		}

		//Verify battery status on all Available and In Use devices
		public final boolean VerifyBatteryStatusDisplayedOnAllDevices(int deviceCount)
		{
			boolean isEventSuccessful = false;
			String strDeviceName = "", deviceNameLink = "";
			strErrMsg_AppLib = "";

			try
			{
				for(int i=1;i<=deviceCount;i++)
				{
					if (PerformAction("eleDevicesTab_Devices", Action.WaitForElement))
					{

						deviceNameLink = dicOR.get("eleDeviceName_ListView").replace("__INDEX__", String.valueOf(i));

						//Get device name and put it to dicOutput
						strDeviceName = GetTextOrValue(deviceNameLink, "text");
						try
						{
							if (dicOutput.containsKey("selectedDeviceName"))
							{
								dicOutput.remove("selectedDeviceName");
							}
							dicOutput.put("selectedDeviceName", strDeviceName);
						}
						catch (RuntimeException e)
						{
							writeToLog("SelectDevice -- Unable to put devicename to dicOutput dictionary." + e.getStackTrace());
						}

						//Click on device name and verify correct device details page is opened.
						if ( ! strDeviceName.equals(""))
						{

							if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
							{
								isEventSuccessful = PerformAction(deviceNameLink, Action.ClickUsingJS);
							}
							else
							{
								isEventSuccessful = PerformAction(deviceNameLink, Action.Click);
							}
							if (isEventSuccessful)
							{
								isEventSuccessful = PerformAction("eleDeviceNameinDeviceDetailsHeader", Action.WaitForElement);
								if (isEventSuccessful)
								{
									isEventSuccessful = GetTextOrValue("eleDeviceNameinDeviceDetailsHeader", "text").equals(strDeviceName);
									if (!isEventSuccessful)
									{
										throw new RuntimeException("Correct Device details page is not opened for device: " + strDeviceName);
									}
									else
									{
										isEventSuccessful=(Boolean)GetBatteryStatus()[0];
										if(!isEventSuccessful)
										{
											throw new RuntimeException("Battery Status did not displayed");
										}
										else
										{
											isEventSuccessful = navigateToNavBarPages("Devices", "eleDevicesTab_Devices");
										}
									}
								}
								else
								{
									throw new RuntimeException("Device details page not opened after clicking on device name link for device : " + strDeviceName);
								}
							}
							else
							{
								throw new RuntimeException("Could not click  on device name link (cards view on devices page) for device : " + strDeviceName);
							}
						}
						else
						{
							throw new RuntimeException("Could not find any device with the given parameters.");
						}
					}
					else
					{
						throw new RuntimeException("Devices page is not displayed");
					}
				}
			}
			catch (RuntimeException e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "SelectDevice---" + "Exception : "+e.getMessage();
			}
			return isEventSuccessful;
		}

		////Verify Model name are friendly name of device on Device Index Page
		public final boolean VerifyDevicesFriendlyNameDisplayedAsModel(int deviceCount)
		{
			boolean isEventSuccessful = false;
			String strDeviceName = "", deviceNameLink = "";
			strErrMsg_AppLib = "";

			try
			{
				for(int i=1;i<=deviceCount;i++)
				{
					if (PerformAction("eleDevicesTab_Devices", Action.WaitForElement))
					{

						deviceNameLink = dicOR.get("eleDeviceName_ListView").replace("__INDEX__", String.valueOf(i));

						//Get device name and put it to dicOutput
						strDeviceName = GetTextOrValue(dicOR.get("eleDeviceModel_ListView").replace("__INDEX__", String.valueOf(i+1)),"text");

						//Click on device name and verify correct device details page is opened.
						if (!strDeviceName.equals(""))
						{

							if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
							{
								isEventSuccessful = PerformAction(deviceNameLink, Action.ClickUsingJS);
							}
							else
							{
								isEventSuccessful = PerformAction(deviceNameLink, Action.Click);
							}
							if (isEventSuccessful)
							{
								isEventSuccessful = PerformAction("eleDeviceNameinDeviceDetailsHeader", Action.WaitForElement);
								if (isEventSuccessful)
								{
									isEventSuccessful = GetTextOrValue("deviceFriendlyName", "text").contains(strDeviceName);
									if (!isEventSuccessful)
									{
										throw new RuntimeException("Correct Device friendly name is not displayed: " + strDeviceName);
									}
									else
									{
										isEventSuccessful = navigateToNavBarPages("Devices", "eleDevicesTab_Devices");
									}
								}
								else
								{
									throw new RuntimeException("Device details page not opened after clicking on device name link for device : " + strDeviceName);
								}
							}
							else
							{
								throw new RuntimeException("Could not click  on device name link (cards view on devices page) for device : " + strDeviceName);
							}
						}
						else
						{
							throw new RuntimeException("Could not find any device with the given parameters.");
						}
					}
					else
					{
						throw new RuntimeException("Devices page is not displayed");
					}
				}
			}
			catch (RuntimeException e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "SelectDevice---" + "Exception : "+e.getMessage();
			}
			return isEventSuccessful;
		}

		//Add or modify notes on device details page
		public final Boolean Add_Modify_Notes(String content)
		{
			boolean isEventSuccessful = false;
			strErrMsg_AppLib = "";
			try
			{
				waitForPageLoaded();
				isEventSuccessful=PerformAction("notesLocatorDeviceDetails",Action.isDisplayed);
				if(isEventSuccessful)
				{
					PerformAction("notesLocatorDeviceDetails",Action.Click);
					waitForPageLoaded();
					isEventSuccessful=PerformAction(dicOR.get("notesLocatorDeviceDetails").replace("]/span", "]/div[1]"),Action.DoubleClick);
					if(isEventSuccessful)
					{
						waitForPageLoaded();
						isEventSuccessful=PerformAction("noteseditorLocator",Action.WaitForElement);
						isEventSuccessful=PerformAction("noteseditorLocator",Action.isDisplayed);
						if(isEventSuccessful)
						{
							isEventSuccessful=PerformAction("noteseditorLocator",Action.Type,content);
							if(isEventSuccessful)
							{
								waitForPageLoaded();
								isEventSuccessful=PerformAction("notesSaveChanges",Action.WaitForElement);
								if(isEventSuccessful)
								{
									isEventSuccessful=PerformAction("notesSaveChanges",Action.Click);
									if(!isEventSuccessful)
									{
										strErrMsg_AppLib="Unable to click on Save button.";
									}
								}
								else
								{
									strErrMsg_AppLib="Save button did not found on the page.";
								}
							}
							else
							{
								strErrMsg_AppLib="Notes text field did not opened.";
							}
						}
						else
						{
							strErrMsg_AppLib="Notes field is uneditable.";
						}
					}
					else
					{
						strErrMsg_AppLib="Unable to double click on notes.";
					}
				}
				else
				{
					strErrMsg_AppLib="Notes section did not found on device details page.";
				}
			}
			catch(Exception e)
			{
				isEventSuccessful = false;
				strErrMsg_AppLib = "Exception : "+e.getMessage();
			}
			return isEventSuccessful;

		}

		public final Boolean verifyReservationDateColumnValue(String xPath)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			List<WebElement> columnNameValues=new ArrayList<WebElement>();
			try
			{
				if(GetTextOrValue("noReservationMessage","text").contains("No reservations are scheduled"))
				{
					return true;
				}
				else
				{
					columnNameValues = getelementsList(xPath);
					for(WebElement ele:columnNameValues)
					{
						getReservationDateFormat(ele.getText().split(" ")[0]);
						flag=true;
					}
				}
			}
			catch(Exception e)
			{
				flag = false;
				strErrMsg_AppLib ="Exception : '"+ e.getMessage();
			}
			return flag;
		}
		public final boolean uploadApplication(String Appname)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			try
			{
				waitForPageLoaded();
				PerformAction("uploadApplicationlnk",Action.WaitForElement);
				flag=PerformAction("uploadApplicationlnk",Action.isDisplayed);
				if(flag)
				{
					flag=PerformAction("uploadApplicationlnk",Action.Click);
					if(flag)
					{
						flag = PerformAction("uploadApplicationlnk",Action.UploadApplication,Appname);
						// PerformAction("uploadProgressBarApplicationPage",Action.WaitForElement);
						flag = PerformAction("uploadProgressBarApplicationPage",Action.isDisplayed);
						//PerformAction("browser", Action.WaitForPageToLoad);
						Thread.sleep(60000);
						flag = PerformAction("browser", Action.WaitForPageToLoad);
						return flag;
					}
					else
					{
						flag=false;
						strErrMsg_AppLib = "Upload Application Button did not clicked";
					}
				}
				else
				{
					flag=false;
					strErrMsg_AppLib = "Upload Application Button not found";
				}

			}
			catch(Exception e)
			{
				flag=false;
				strErrMsg_AppLib = ""+e.getMessage();
			}
			return flag;
		}


		public final boolean SelectvaluesonSystemPAge(String object ,String value)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";  
			try
			{
				flag = PerformAction(object.replace("values", value),Action.Select);
				if(flag)
				{

				}
				else
				{
					throw new RuntimeException("Could not select the drop down values");
				}



			}catch(Exception e)
			{
				flag=false;
				strErrMsg_AppLib = ""+e.getMessage();
			}

			return flag;

		}

		public final boolean importUser() throws IOException, AWTException, InterruptedException

		{
			return importUser("", "", "", "", "", 1);
		}

		public final boolean importUser(int rows) throws IOException, AWTException, InterruptedException

		{
			return importUser("", "", "", "", "", rows);
		} 

		public final boolean importUser(String Email, String IsActive, String Role, String FirstName, String Password, int rows) throws IOException, AWTException, InterruptedException

		{
			boolean flag = true;
			strErrMsg_AppLib = "";
			CSVWriter writer = null;

			try
			{
				if (dicOutput.containsKey("Email"))
				{
					dicOutput.remove("Email");
				}

				if (IsActive.equals(""))
				{
					IsActive = "TRUE";
				}

				if (Role.equals(""))
				{
					Role = "Tester";
				}


				if(FirstName.equals(""))
				{
					FirstName = "seleniumImport";

				}


				if (Password.equals(""))
				{
					Password = "deviceconnect";
					dicOutput.put("Password", Password);
				}

				if ((new File(dicConfig.get("Artifacts") +"\\Applications\\userImport.csv")).isFile())
				{
					(new File(dicConfig.get("Artifacts")+"\\Applications\\userImport.csv")).delete();
				}
				File file = new File(dicConfig.get("Artifacts") +"\\Applications\\userImport.csv");

				writer = new CSVWriter(new FileWriter(dicConfig.get("Artifacts") +"\\Applications\\userImport.csv"));
				for(int j = 1  ; j<= rows +1; j++ )
				{

					if(j==1)
					{
						String [] UserDetails = {"Email","IsActive","FirstName","MiddleName","LastName","Notes","Organization","Title","Location","Address1","Address2","City","Region","PostalCode","Country","HomePhone","MobilePhone","OfficePhone","FaxPhone","Password","PasswordHash","PasswordSalt","PasswordHashType","Roles"};

						writer.writeNext(UserDetails);
					}
					else
					{
						if (Email.equals("")) //If email ID is not given by user, then assign it value and put to dictionary
						{
							Email =  RandomStringUtils.randomAlphabetic(12)+"@ml.com";
							dicOutput.put("Email", "test"+Email);
						}

						String [] UserDetails = {"test"+Email,IsActive,FirstName,"","Selenium","","","","","","","","","","","","","","",Password,"","","","\""+Role+"\""};

						writer.writeNext(UserDetails);
						Email = "";
					}	
				}
				writer.flush();
				writer.close();
				if(flag)
				{
					flag = PerformAction("Users", Action.WaitForElement);
					flag = navigateToNavBarPages("Users", "eleUsersHeader");
					if(flag)
					{
						flag = PerformAction("browser","waitforpagetoload");
						if(flag)
						{
							flag = PerformAction("ImportUserbtn",Action.Click);
							if(flag)
							{
								flag =PerformAction("uploaduserlistbtn",Action.isDisplayed);
								if(flag)
								{
									flag = PerformAction("uploaduserlistbtn",Action.Click);
									if(flag)
									{
										StringSelection stringSelection = new StringSelection(dicConfig.get("Artifacts") +"\\Applications\\userImport.csv");
										Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
										Robot robot = new Robot();
										robot.keyPress(KeyEvent.VK_CONTROL);
										robot.keyPress(KeyEvent.VK_V);
										robot.keyRelease(KeyEvent.VK_V);
										robot.keyRelease(KeyEvent.VK_CONTROL);
										robot.keyPress(KeyEvent.VK_ENTER);
										robot.keyRelease(KeyEvent.VK_ENTER);
										Thread.sleep(2000);
										flag=true;

										if(flag)
										{
											PerformAction("browser",Action.Refresh);
											flag = Logout();
										}
										else
										{
											throw new RuntimeException("Unable to import user list");
										}
										if(!flag)
										{
											throw new RuntimeException("Not logged out successfully");
										}

									}
									else
									{
										throw new RuntimeException("Unable to click on import user button");
									}
								}
								else
								{
									throw new RuntimeException("Upload dialog box did not displayed");
								}
							}
							else
							{
								throw new RuntimeException("Unable to click import user button");
							}
						}
						else
						{
							throw new RuntimeException("User Page could not loaded properly");
						}
					}
					else
					{
						throw new RuntimeException("User Page is not displayed");
					}
				}
				else
				{
					throw new RuntimeException("Exception occurred while updating csv file.");
				}



			} catch (RuntimeException e)
			{
				strErrMsg_AppLib = "createUser---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
				flag = false;
			}

			return flag; 

		}

		public final Object[] AvailableDevices_DIpage()
		{
			boolean flag = false;
			strErrMsg_AppLib = ""; 
			ArrayList l = new ArrayList<String>() ;
			Object[] objresult = new Object[2] ;
			try
			{
				waitForPageLoaded();
				String xpathDevicesHolder = dicOR.get("eleDevicesHolderListView");
				int devicesCountTotal = getelementCount(xpathDevicesHolder);
				for (int i = 1; i <= devicesCountTotal; i++)
				{
					String DeviceName = GetTextOrValue(dicOR.get("eleDeviceName_ListView").replace("__INDEX__", (new Integer(i)).toString()) , "text");
					l.add(DeviceName);
				}
				flag = true;  
			} 
			catch(Exception e)
			{
				flag  = false;
				strErrMsg_AppLib = ""+ e.getMessage();
			}
			objresult[0] = flag;
			objresult[1] = l;
			return objresult; 
		}

		/*Function to ExecuteCLIResets Commands
		 *  Created by Deepak(23-May-2016)
		 */
		public final Object[] ExecuteCLIResetCommands(String CLIOption, String platform, String UserName, String Password, String deviceName, String status, String client )
		{
			String CLI_Command = dicConfig.get("Artifacts") + "\\DeviceConnectCLI\\";
			strErrMsg_AppLib = "";
			String command = "", deviceSelected = "",s="";
			boolean isEventSuccessful = true, flag=false;
			Object[] returnValue = new Object[7]; 
			try
			{
				if (UserName.equals(""))
				{
					UserName = dicCommon.get("EmailAddress");
				}
				if (Password.equals(""))
				{
					Password = dicCommon.get("Password");
				}

				if (status.equals(""))
				{
					status = "Available";

				}
				if (deviceName.equals(""))
				{
					isEventSuccessful = PerformAction("eleDevicesTab_Devices", Action.isDisplayed);
					if (!isEventSuccessful)
					{
						if (!navigateToNavBarPages("Devices", "eleDevicesTab_Devices"))
						{
							throw new RuntimeException("On selecting 'Devices' menu, 'Devices' page is not opened.");
						}
					}
					else
					{
						PerformAction("btnRefresh_Devices", Action.Click);
						if (selectStatus_DI(status))
						{
							if (!selectPlatform_DI(platform))
							{
								throw new RuntimeException(strErrMsg_AppLib);
							}
							if (PerformAction("eleNoDevicesWarning_Devices", Action.isDisplayed))
							{
								throw new RuntimeException("No devices displayed.");
							}
							deviceSelected = GetDeviceDetailInGridAndListView(1, "devicename");
							returnValue[3] = deviceSelected;
							deviceName=deviceSelected;
							deviceSelected = "\"" + deviceSelected + "\"" ;
						}
						else
						{
							isEventSuccessful = false;
							throw new RuntimeException(strErrMsg_AppLib);
						}
					}
				}
				else
				{
					returnValue[3] = deviceName;
					deviceSelected = "\"" + deviceName + "\"" ;
				}

				switch (CLIOption.toLowerCase())
				{

				case "resetrebootuninstall":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -device " + deviceSelected + " -reset -reboot -uninstallAll";
					break;

				case "resetuninstall":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -device " + deviceSelected + " -reset -uninstallAll";
					break;

				case "resetreboot":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -device " + deviceSelected + " -reset -reboot";
					break;

				case "rebootuninstallall":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -device " + deviceSelected + " -release -reset -reboot -uninstallAll";
					break;

				case "releaseuninstall":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -device " + deviceSelected + " -release -reset -uninstallAll";
					break;

				case "releasereboot":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -device " + deviceSelected + " -release -reboot";
					break;

				case "releasereset":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + UserName + " " + Password + " -device " + deviceSelected + " -release -reset";
					break;

				default:
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " + dicCommon.get("ApplicationURL") + " " + dicCommon.get("EmailAddress") + " " + dicCommon.get("Password") + " -device " + deviceSelected + " -retain -install \"deviceControl\" -autoconnect \"deviceControl\"";
					break;
				}
				Runtime rt = Runtime.getRuntime();
				Process proc = rt.exec(command);

				BufferedReader stdInput = new BufferedReader(new	InputStreamReader(proc.getInputStream()));
				BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

				while ((s = stdInput.readLine()) != null) 
				{
					if(s.contains("User does not have required entitlement"))
					{
						returnValue[0] = s;
						break;
					}
					
				}
				while ((s = stdError.readLine()) != null) 
				{
					if(s.contains("User does not have required entitlement"))
					{
						returnValue[0] = s;
						break;
					}
					
				}
				returnValue[2] = true;
			}
			catch (Exception e)
			{
				returnValue[2] = false;
				strErrMsg_AppLib = "ExecuteCLICommand: " + e.getMessage();
			}

			return returnValue;
		}

		/* Function for Managing Device state and related options.
		 * Created by Deepak(23-May-2016)
		 */
		public final Boolean setDeviceStateSettings(Boolean deviceReset, Boolean uninstallAll, Boolean rebootDevice)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			try
			{
				waitForPageLoaded();
				flag=PerformAction("deviceresetLocator",Action.isSelected);
				if(deviceReset && !flag)
				{
					flag = PerformAction("deviceresetLocator",Action.SelectCheckbox);
				}
				if(!deviceReset && flag)
				{
					flag = PerformAction("deviceresetLocator",Action.DeSelectCheckbox);
				}
				waitForPageLoaded();
				flag=PerformAction("uninstallAllresetLocator",Action.isSelected);
				if(uninstallAll && !flag)
				{
					flag = PerformAction("uninstallAllresetLocator",Action.SelectCheckbox);
				}
				if(!uninstallAll && flag)
				{
					flag = PerformAction("uninstallAllresetLocator",Action.DeSelectCheckbox);
				}
				waitForPageLoaded();
				flag=PerformAction("rebootDeviceResetLocator",Action.isSelected);
				if(rebootDevice && !flag)
				{
					flag = PerformAction("rebootDeviceResetLocator",Action.SelectCheckbox);
				}
				if(!rebootDevice && flag)
				{
					flag = PerformAction("rebootDeviceResetLocator",Action.DeSelectCheckbox);
				}
				return true;
			}
			catch(Exception e)
			{
				strErrMsg_AppLib ="Exception : '"+ e.getMessage();
				return false;
			}
		}

		/*Function to Delete given Role
		 *  Created by Deepak(26-May-2016)
		 */
		public Boolean deleteRole(String Role)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			List<WebElement> roles=new ArrayList<WebElement>();
			try
			{
				waitForPageLoaded();
				flag=PerformAction("RolesLocatorUsersPage",Action.Click);
				
				if(flag)
				{
					roles=getelementsList("RolesList");
					if(!(roles.isEmpty()))
					{

						for(WebElement role: roles)
						{
							if(role.getText().equals(Role))
							{
								flag=true;
								role.click();
								break;
							}
							else
							{
								flag=false;
							}
						}
					}
					else
					{
						strErrMsg_AppLib = "List of Roles is empty.";
						return false;
					}
				}
				if(flag)
				{
					flag=PerformAction("deleteRolebtn",Action.Click);
					if(flag)
					{
						waitForPageLoaded();
						flag=PerformAction("deleterolebtnPopup",Action.Click);
						if(flag)
						{
							waitForPageLoaded();
							flag=PerformAction("SavebtnRolesPage",Action.Click);
							if(flag)
							{
								waitForPageLoaded();
								flag=PerformAction("errorPopup",Action.isNotDisplayed);
								if(!flag)
								{
									strErrMsg_AppLib = "Error pop up shown on UI.";
								}
							}
							else
							{
								strErrMsg_AppLib = "Unable to click on Save button.";
							}
						}
						else
						{
							strErrMsg_AppLib = "Unable to click on delete role button on pop up.";
						}
					}
					else
					{
						strErrMsg_AppLib = "Unable to click on delete role button.";
					}
				}
			}
			catch(Exception e)
			{
				strErrMsg_AppLib ="Exception : '"+ e.getMessage();
				flag=false;
			}
			return flag;
		}

		/*Function to Rename given Role
		 *  Created by Deepak(26-May-2016)
		 */
		public Boolean renameRole(String Role, String newRole)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			List<WebElement> roles=new ArrayList<WebElement>();
			try
			{
				flag=PerformAction("RolesLocatorUsersPage",Action.Click);
				if(flag)
				{
					roles=getelementsList("RolesList");
					if(!(roles.isEmpty()))
					{

						for(WebElement role: roles)
						{
							if(role.getText().equals(Role))
							{
								flag=true;
								role.click();
								break;
							}
							else
							{
								flag=false;
							}
						}
					}
					else
					{
						strErrMsg_AppLib = "List of Roles is empty.";
						return false;
					}
				}
				if(flag)
				{
					flag=PerformAction("renameRolebtn",Action.Click);
					if(flag)
					{
						flag=PerformAction("nameRole",Action.Type, newRole);
						if(flag)
						{
							flag=PerformAction("renameRoleBtnPopup",Action.Click);
							if(flag)
							{
								flag=PerformAction("SavebtnRolesPage",Action.Click);
								if(flag)
								{
									flag=PerformAction("errorPopup",Action.isNotDisplayed);
									if(!flag)
									{
										strErrMsg_AppLib = "Error pop up shown on UI.";
									}
								}
								else
								{
									strErrMsg_AppLib = "Unable to click on Save button.";
								}
							}
							else
							{
								strErrMsg_AppLib = "Unable to click on renameRoleBtnPopup.";
							}
						}
						else
						{
							strErrMsg_AppLib = "Unable to type new name for Role.";
						}
					}
					else
					{
						strErrMsg_AppLib = "Unable to click on renameRolebtn.";
					}
				}
			}
			catch(Exception e)
			{
				strErrMsg_AppLib ="Exception : '"+ e.getMessage();
				flag=false;
			}
			return flag;
		}

		public String getInstalledApps()
		{
			strErrMsg_AppLib = "";
			try{
				waitForPageLoaded();
				String installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
				int waitTime=0;
				while (installedAppsList.contains("Loading...") || waitTime==25)
				{ 
					waitTime++;
					installedAppsList=GetTextOrValue(dicOR.get("DeviceDetails_Install_App_Table"),"text");
				}
				System.out.println(installedAppsList);
				return installedAppsList;
			}
			catch(Exception e)
			{
				strErrMsg_AppLib ="Exception : '"+ e.getMessage();
				return "";		
			}

		}

		public final Object[] uploadAppThroughCLI(String fileName)
		{
			return uploadAppThroughCLI("upload","", "", fileName);
		}

		public final Object[] uploadAppThroughCLI(String CLIOption,String fileName)
		{
			return uploadAppThroughCLI(CLIOption,"", "", fileName);
		}

		public final Object[] uploadAppThroughCLI(String CLIOption,String UserName, String Password, String fileName)
		{

			strErrMsg_AppLib = "";
			String CLI_Command = dicConfig.get("Artifacts") + "\\DeviceConnectCLI\\";
			String filePath = "\"" + dicConfig.get("Artifacts")+ "\\"+ fileName + "\"";
			//String appAdmin= dicConfig.get("Artifacts")+"\\com.aldiko.android.apk";
			String command = "", apiKey="";
			boolean isEventSuccessful = true, flag=false;

			Object[] returnValue = new Object[7]; 
			try
			{
				if (UserName.equals(""))
				{
					UserName = dicCommon.get("EmailAddress");
				}
				if (Password.equals(""))
				{
					Password = dicCommon.get("Password");
				}

				String cliCmdAuthString="";
				//--------------code added by jaishree for authentication type
				if (CLIOption.toLowerCase().contains("authtype"))
				{
					System.out.println(CLIOption.toLowerCase().substring(8, 9));
					switch(CLIOption.toLowerCase().substring(8, 9))
					{
					case "1": //MobileLabs.DeviceConnect.Cli.exe <host> <username> <password> [options]
						cliCmdAuthString=dicCommon.get("ApplicationURL") + " " + UserName + " " + Password;
						CLIOption=CLIOption.replace("authtype1", "");
						break;
					case "2": //MobileLabs.DeviceConnect.Cli.exe <host> <username> <api key> [options]
						if (Pattern.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", Password))
							apiKey=Password;
						else
							throw new RuntimeException("Please pass apikey in password argument");

						cliCmdAuthString=dicCommon.get("ApplicationURL") + " " + UserName + " " + Password; 
						CLIOption=CLIOption.replace("authtype2", "");
						break;
					case "3": //MobileLabs.DeviceConnect.Cli.exe <username>:<password>@<host> [options]
						cliCmdAuthString=UserName + ":" + Password + "@" + dicCommon.get("ApplicationURL");
						CLIOption=CLIOption.replace("authtype3", "");
						break;
					case "4": //MobileLabs.DeviceConnect.Cli.exe <username>:<api key>@<host> [options]
						if (Pattern.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", Password))
							apiKey=Password;
						else
							throw new RuntimeException("Please pass apikey in password argument");

						cliCmdAuthString= UserName + ":" + Password + "@" + dicCommon.get("ApplicationURL");
						CLIOption=CLIOption.replace("authtype4", "");
						break;
					default:
						cliCmdAuthString=dicCommon.get("ApplicationURL") + " " + UserName + " " + Password;
						break;

					}
				}else
				{
					cliCmdAuthString=dicCommon.get("ApplicationURL") + " " + UserName + " " + Password;
				}

				System.out.println(CLIOption);
				//--------------code added by jaishree for authentication type

				switch (CLIOption.toLowerCase())
				{
				case "upload":
					command = CLI_Command + "MobileLabs.DeviceConnect.Cli.exe " +cliCmdAuthString + " -upload " + filePath;
					break;
				}

				AddToDictionary(dicOutput, "executedCommand", command);
				//*** connecting the Device with DeviceViewer using CMD *** 

				if(isEventSuccessful)
				{
					Runtime runtime = Runtime.getRuntime();
					InputStream is =   runtime.exec(command).getInputStream();
					if(CLIOption.equals("history"))
					{
						Thread.sleep(30000);
					}
					else
					{
						Thread.sleep(10000);
					}
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					if(!(br.ready()))
					{
						returnValue[0] = null;
						returnValue[2] = true;
					}
					else
					{
					}
					// *** Read the tasklists from Window Task Manager ***
					Thread.sleep(10000);  
					String processName="";
					if(CLIOption.toLowerCase().equals("forward"))
					{
						processName="MobileLabs.DeviceConnect.Cli.exe";
					}
					else
					{
						processName="MobileLabs.deviceViewer.exe";
					}
				}

			}
			catch (RuntimeException e)
			{

				returnValue[2] = false;
				strErrMsg_AppLib = "ExecuteCLICommand---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			} catch (Exception e)
			{
				returnValue[2] = false;
				strErrMsg_AppLib = "ExecuteCLICommand---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}

			return returnValue;
		}

		/** 
	 Uploads an output file from Systems page.
	 <pre>
	 	isEventSuccessful =   uploadOutputFile_SystemPage("OuputFile.zip");
	</pre>

	 <!--Created by : Jaishree Patidar-->
	 <!--Last updated :-->
	 @param 
	 @return boolean
		 */
		public final boolean uploadOutputFile_SystemPage(String outputFileName)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			try
			{
				PerformAction("uploadoutputFileSystemTab",Action.WaitForElement);
				flag=PerformAction("uploadoutputFileSystemTab",Action.isDisplayed);
				if(flag)
				{
					flag=PerformAction("uploadoutputFileSystemTab",Action.Click);
					if(flag)
					{
						flag = PerformAction("uploadoutputFileSystemTab",Action.UploadApplication,outputFileName);
						// PerformAction("uploadProgressBarApplicationPage",Action.WaitForElement);
						flag = PerformAction("uploadProgressBarApplicationPage",Action.isDisplayed);
						//PerformAction("browser", Action.WaitForPageToLoad);
						Thread.sleep(15000);
						flag = PerformAction("browser", Action.WaitForPageToLoad);
						return flag;
					}
					else
					{
						flag=false;
						strErrMsg_AppLib = "Upload Output file Button did not clicked";
					}
				}
				else
				{
					flag=false;
					strErrMsg_AppLib = "Upload Output file Button not found";
				}

			}
			catch(Exception e)
			{
				flag=false;
				strErrMsg_AppLib = ""+e.getMessage();
			}
			return flag;
		}


	//<!--Created By :Deepak Solanki-->
	//@param Role :  It takes the RoleName
	//@return True or False
	public final Boolean verifyRoleExistence(String Role)
		{
		boolean isEventSuccessful = false;
		strErrMsg_AppLib = "";
		for(WebElement role: getelementsList("RolesList"))
		{
			if(role.getText().equals(Role))
			{
				isEventSuccessful=true;
				break;
			}
		}
		if(!isEventSuccessful)
		{
			strErrMsg_AppLib="Role: "+Role+" did not found in list.";
		}
		return isEventSuccessful;
	}

    
    /*Add or modify notes on device details page using Ctrl + Enter keys
     *  Created by Deepak(08-June-2016)
     */
    public final Boolean Add_Modify_Notes_using_Ctrl_Enter_keys(String content)
    {
    	System.out.println();
  	  boolean isEventSuccessful = false;
        strErrMsg_AppLib = "";
        try
        {
      	  isEventSuccessful=PerformAction("notesLocatorDeviceDetails",Action.isDisplayed);
      	  if(isEventSuccessful)
      	  {
      		  PerformAction("notesLocatorDeviceDetails",Action.Click);
      		  isEventSuccessful=PerformAction(dicOR.get("notesLocatorDeviceDetails").replace("]/span", "]/div[1]"),Action.DoubleClick);
      		  if(isEventSuccessful)
      		  {
      			  isEventSuccessful=PerformAction("noteseditorLocator",Action.WaitForElement);
      			  isEventSuccessful=PerformAction("noteseditorLocator",Action.isDisplayed);
      			  if(isEventSuccessful)
          		  {
      				  isEventSuccessful=PerformAction("noteseditorLocator",Action.Type,content);
      				  if(isEventSuccessful)
      				  {
      					  isEventSuccessful=PerformAction("notesSaveChanges",Action.WaitForElement);
      					  if(isEventSuccessful)
      					  {
      						  isEventSuccessful=PerformAction("notesSaveChanges",Action.CtrlPlusEnter);
      						  if(!isEventSuccessful)
      						  {
      							  strErrMsg_AppLib="Unable to click on Save button.";
      						  }
      					  }
      					  else
      					  {
      						  strErrMsg_AppLib="Save button did not found on the page.";
      					  }
      				  }
      				  else
      				  {
      					  strErrMsg_AppLib="Notes text field did not opened.";
      				  }
          		  }
      			  else
      			  {
      				  strErrMsg_AppLib="Notes field is uneditable.";
      			  }
      		  }
      		  else
      		  {
      			  strErrMsg_AppLib="Unable to double click on notes.";
      		  }
      	  }
      	  else
      	  {
      		  strErrMsg_AppLib="Notes section did not found on device details page.";
      	  }
        }
        catch(Exception e)
        {
      	  isEventSuccessful = false;
            strErrMsg_AppLib = "Exception : "+e.getMessage();
        }
        return isEventSuccessful;
      		  
    }
    
    /*Add or modify Slot field on device details 
    *  Created by Deepak(08-June-2016)
    */
    public final Boolean Add_Modify_Slot(String content)
    {
  	  boolean isEventSuccessful = false;
        strErrMsg_AppLib = "";
        try
        {
        	waitForPageLoaded();
      	  isEventSuccessful=PerformAction("slotLocatorDeviceDetails",Action.isDisplayed);
      	  if(isEventSuccessful)
      	  {
      		  PerformAction("slotLocatorDeviceDetails",Action.Click);
      		  waitForPageLoaded();
      		  isEventSuccessful=PerformAction(dicOR.get("slotLocatorDeviceDetails").replace("]/span", "]/div[1]"),Action.DoubleClick);
      		  if(isEventSuccessful)
      		  {
      			  waitForPageLoaded();
      			  isEventSuccessful=PerformAction("slotEditorLocator",Action.WaitForElement);
      			  isEventSuccessful=PerformAction("slotEditorLocator",Action.isDisplayed);
      			  if(isEventSuccessful)
          		  {
      				  isEventSuccessful=PerformAction("slotEditorLocator",Action.Type,content);
      				  waitForPageLoaded();
      				  if(isEventSuccessful)
      				  {
      					  isEventSuccessful=PerformAction("slotSaveLocator",Action.WaitForElement);
      					  if(isEventSuccessful)
      					  {
      						  isEventSuccessful=PerformAction("slotSaveLocator",Action.Click);
      						  waitForPageLoaded();
      						  if(!isEventSuccessful)
      						  {
      							  strErrMsg_AppLib="Unable to click on Save button.";
      						  }
      					  }
      					  else
      					  {
      						  strErrMsg_AppLib="Save button did not found on the page.";
      					  }
      				  }
      				  else
      				  {
      					  strErrMsg_AppLib="Slot field did not opened.";
      				  }
          		  }
      			  else
      			  {
      				  strErrMsg_AppLib="Slot field is uneditable.";
      			  }
      		  }
      		  else
      		  {
      			  strErrMsg_AppLib="Unable to double click on Slot.";
      		  }
      	  }
      	  else
      	  {
      		  strErrMsg_AppLib="Slot section did not found on device details page.";
      	  }
        }
        catch(Exception e)
        {
      	  isEventSuccessful = false;
            strErrMsg_AppLib = "Exception : "+e.getMessage();
        }
        return isEventSuccessful;
      		  
    }
    
   
     /* Function for Role vaues Verification on Users Page.
      * Created by Deepak(08-June-2016)
      */
     public final Boolean verifyModifiedRoleExistence(String Role, String [] Entitlement, Boolean [] value)
     {
   	   boolean flag = false;
         strErrMsg_AppLib = "";
         List<WebElement> roles=new ArrayList<WebElement>();
         List<WebElement> entitlements=new ArrayList<WebElement>();
         List<WebElement> entitlementschkbox=new ArrayList<WebElement>();
         int roleIndex=0;
         try
         {
      	   flag=PerformAction("RolesLocatorUsersPage",Action.Click);
      	   if(flag)
      	   {
      		   roles=getelementsList("RolesList");
      		   if(!(roles.isEmpty()))
      		   {
      			   
      			   for(WebElement role: roles)
      			   {
      				  roleIndex++;
      				  if(role.getText().equals(Role))
      				  {
      					  flag=true;
      					  roles.get(roleIndex-1).click();
      					  break;
      				  }
      				  else
      				  {
      					  flag=false;
      				  }
      				  
      			   }
      			   if(!flag)
      			   {
      				   flag=addNewRole(Role);
      			   }
      		   }
      		   else
      		   {
      			   strErrMsg_AppLib = "List of Roles is empty.";
      			   return false;
      		   }
      	   
      		   
      		   entitlements=getelementsList("entitlements");
      		   entitlementschkbox=getelementsList(dicOR.get("entitlements").replace("label", "label/input"));
      		   if(entitlementschkbox.size()<=0)
      		   {
      			   strErrMsg_AppLib = "Checkbox list of Roles is empty.";
      			   flag=false;
      			   return flag;
      		   }
      		   for(int i=0;i<entitlements.size();i++)
      		   {
      			   String entitlement=entitlements.get(i).getText();
      			   for(int j=0;j<Entitlement.length;j++)
      			   {
      				   if(entitlement.contains(Entitlement[j]))
      				   {
      					   flag=entitlementschkbox.get(i).isSelected();
      					   if((!flag) && !value[j])
      					   {
      						   flag=true;
      					   }
      					   if(flag && value[j])
      					   {
      						   flag=true;
      					   }
      					   if(flag && !value[j])
      					   {
      						   strErrMsg_AppLib = "It should be checked: "+entitlement;
      						   return false;
      					   }
      					   if(!flag && value[j])
      					   {
      						   strErrMsg_AppLib = "It should be unchecked: "+entitlement;
      						   return false;
      					   }
      				   }
      			   }
      		   }
      		   
      	   }
      	   else
      	   {
      		   strErrMsg_AppLib ="Did not find Roles";
      	   }
         }
         catch(Exception e)
         {
       	  flag = false;
            strErrMsg_AppLib ="Exception : '"+ e.getMessage();
         }
         return flag;
     }
     
     /*Remove offline device and verify it marked for deletion.
      * Pre-requisite: Device details page of offline device should be opened
      *  Created by Deepak(09-June-2016)
      */
      public final Boolean verifyofflineRemoveDeviceMarkDeletion()
      {
     	  boolean flag = false;
          strErrMsg_AppLib = "";
          try
          {
        	  flag=PerformAction("removedeviceLocator",Action.Click);
  			  if(flag)
  			  {
  				  flag = PerformAction(dicOR.get("eleDialogDeleteAll_ApplicationPage").replace("__EXPECTED_HEADER__", "Remove device"),Action.Click);
  				  if(flag)
  				  {
  					  if(!(GetTextOrValue("deviceDeletionText","text").contains("This device was marked as removed")))
  					  {
  						  strErrMsg_AppLib="Offline device not marked for deletion when removed";
  					  }
  				  }
  				  else
  				  {
  					 strErrMsg_AppLib="Unable to click on Remove device confirmation.";
  				  }
  			  }
  			  else
  			  {
  				 strErrMsg_AppLib="Unable to click on Remove button.";
  			  }
          }
          catch(Exception e)
          {
        	  strErrMsg_AppLib=""+e.getMessage();
          }
 		  return flag;
      }
      
      /*Verify App with given name available on Device Connect pop up window.
       * Created by Deepak(09-June-2016)
       */
       public final Boolean verifAppAvailability_On_ConnectPage(String appName)
       {
      	  boolean flag = false;
           strErrMsg_AppLib = "";
           List <WebElement> apps=new ArrayList<WebElement>();
           try
           {
         	  apps=getelementsList("appListOnConnectDeviceWindow");
         	  for(int i=0;i<apps.size();i++)
         	  {
         		  if(apps.get(i).getText().equals(appName))
         		  {
         			  flag=true;
         			  break;
         		  }
         	  }
         	  if(!flag)
         	  {
         		 strErrMsg_AppLib="Did not found "+appName+" app on device connect page.";
         	  }
           }
           catch(Exception e)
           {
         	  strErrMsg_AppLib=""+e.getMessage();
           }
  		  return flag;
       }
       
       /*Function to Verify double error message of same kind do not display 
        *  Created by Deepak(14-June-2016)
        */
       public final Boolean verifyDoubleRoleError(String Role)
       {
    	   boolean flag = false;
    	   strErrMsg_AppLib = "";
    	   try
    	   {
    		   flag=PerformAction("AddRolebtn",Action.isDisplayed);
    		   if(!flag)
    		   {
    			   flag=navigateToNavBarPages("Users", "eleUsersHeader");
    			   if(flag)
    			   {
    				   flag=PerformAction("RolesLocatorUsersPage",Action.Click);
    			   }
    			   else
    			   {
    				   strErrMsg_AppLib = "Cannot navigate to Users page";
    				   return false;
    			   }
    		   }
    		   if(flag)
    		   {
    			   PerformAction("AddRolebtn",Action.WaitForElement);
    			   for(int i=0;i<2;i++)
    			   {
    				   flag=PerformAction("AddRolebtn",Action.Click);
    				   if(flag)
    				   {
    					   flag=PerformAction("nameRole",Action.Type,Role);
    					   if(flag)
    					   {
    						   flag=PerformAction("AddbtnPopupRole",Action.Click);
    						   if(!flag)
    						   {
    							   strErrMsg_AppLib = "Unable to click on Add btn on Pop Up.";
    							   return flag;
    						   }
    					   }
    					   else
    					   {
    						   strErrMsg_AppLib = "Unable to type role in input box.";
    						   return flag;
    					   }
    				   }
    				   else
    				   {
    					   strErrMsg_AppLib = "Unable to click on Add Role button.";
    					   return flag;
    				   }
    			   }
    			   flag=PerformAction("SavebtnRolesPage",Action.Click);
    			   if(flag)
    			   {
    				   if(getelementsList("errorPopup").size()>1)
    				   {
    					   strErrMsg_AppLib = "Multiple Error pop up shown on UI.";
    					   flag=false;
    				   }
    			   }
    			   else
    			   {
    				   strErrMsg_AppLib = "Unable to click on Save button.";
    			   }
    		   }  

    	   }
    	   catch(Exception e)
    	   {
    		   flag = false;
    		   strErrMsg_AppLib ="Exception : '"+ e.getMessage();
    	   }
    	   return flag;
       }
       
       /*Function to Verify user's history details 
        *  Created by Deepak(15-June-2016)
        */
       
       public final boolean VerifyUsersHistoryPage(String sVerificationObjectName, String sVerificationObjectValue)
       {
    	   strErrMsg_AppLib = "";
    	   boolean isEventSuccessful = false;
    	   String xpathUserRecords = "",text="";

    	   try
    	   {
    		   if (PerformAction("usersHeaderInfo", Action.Exist))
    		   {
    			   xpathUserRecords = dicOR.get("userhistorytable").replace("__INDEX__", "2");

    			   int noOfRows = getelementCount(xpathUserRecords); // Get number of devices' rows/cards displayed in given view
    			   //System.out.println(noOfRows);
    			   for (int i = 1; i <noOfRows; i++)
    			   {
    				   // Now verify the value of the element required using different cases
    				   switch (sVerificationObjectName.toLowerCase())
    				   {

    				   case "event":
    					   text = GetElement(dicOR.get("usereventhistory").replace("__INDEX__", (new Integer(i)).toString())).getText();
    					   if (!text.isEmpty())
    					   {
    						   if (sVerificationObjectValue.toLowerCase().equals("user created"))
    						   {
    							   if (text.toLowerCase().startsWith(sVerificationObjectValue.toLowerCase()))
    							   {
    								   strErrMsg_AppLib = "";
    								   isEventSuccessful = true;
    								   return isEventSuccessful;
    							   }
    							   else
    							   {
    								   strErrMsg_AppLib = "User Created event not found in the history list.";
    							   }
    						   }


    					   }
    					   if (!isEventSuccessful)
    					   {
    						   strErrMsg_AppLib = "'" + sVerificationObjectValue + "' is not present on  the UI.";
    					   }
    					   break;
    				   case "user":
    					   text = GetElement(dicOR.get("usernamehistory").replace("__INDEX__", (new Integer(i)).toString())).getText();
    					   if (!text.isEmpty())
    					   {
    						   if (text.toLowerCase().startsWith(sVerificationObjectValue.toLowerCase()))
    						   {
    							   strErrMsg_AppLib = "";
    							   isEventSuccessful = true;
    						   }
    						   else
    						   {
    							   strErrMsg_AppLib = "User "+text+" found in the history list.";
    							   isEventSuccessful = false;
    							   return isEventSuccessful;
    						   }
    					   }
    					   if (!isEventSuccessful)
    					   {
    						   strErrMsg_AppLib = "'" + sVerificationObjectValue + "' is not present on  the UI.";
    					   }
    					   break;
    				   }

    				   //System.out.println(i);
    			   }
    		   }
    		   else
    		   {
    			   isEventSuccessful = false;
    			   strErrMsg_AppLib = "Users history page did not displayed";
    			   return isEventSuccessful;
    		   }
    	   }
    	   catch (Exception e)
    	   {
    		   isEventSuccessful = false;
    		   strErrMsg_AppLib = "VerifyUsersHistoryPage---" + e.getMessage();
    	   }
    	   return isEventSuccessful;
       }
       
       /*Function to Verify tooltip text of columns on DI page 
        *  Created by Deepak(13-July-2016)
        */
       
       public final boolean VerifyTooltip_DI_Columns(String columns)
       {
    	   boolean isEventSuccessful =false;
    	   strErrMsg_AppLib="";
    	   String ColumnNames = "", tooltipValue="";
    	   WebElement element;
    	   String[] arrRequiredValues;
           try
    	   { 
        	   ColumnNames = "Status / Name," + columns;
        	   arrRequiredValues = ColumnNames.split(",");   //Splitting the column names.
        	   //getting total number of displayed columns.
        	   List<WebElement> lstColHeaderElements = getelementsList("eleCoulmnsdisplayed_Devices");
        	   for(int i=0;i<(lstColHeaderElements.size()-1);i++)
        	   {
        		   PerformAction(lstColHeaderElements.get(i),Action.MouseHover);
        		   tooltipValue=getAttribute(lstColHeaderElements.get(i),"title","");
        		   if(!(tooltipValue.equals(arrRequiredValues[i])))
        		   {
        			   strErrMsg_AppLib = "tooltipValue: "+tooltipValue+" is not equals to: "+arrRequiredValues[i];
        			   isEventSuccessful = false;
        			   break;
        		   }
        		   else
        		   {
        			   isEventSuccessful = true;
        		   }
        	   }
    	   }
           catch (Exception e)
           {
    		 isEventSuccessful = false;
             strErrMsg_AppLib = "VerifyTooltip_DI_Columns--- "+ e.getMessage();
           }
    	   return isEventSuccessful;
       }
       
       /*Function for selection and deselection of user roles on user edition page
        * Created by Deepak(13-July-2016)
        */
       public final boolean selection_Deselection_Role(String [] roles)
       {
    	   boolean flag=false, shouldbeChecked=false;
    	   strErrMsg_AppLib="";
    	   List<WebElement> roleName, rolechkBox;
    	   String errorIndex = "";
    	   try
    	   {
    		   waitForPageLoaded();
    		   if(!(PerformAction("roleOnUserDetailsPage",Action.isDisplayed)))
    		   {
    			   strErrMsg_AppLib="Roles locator did not found";
    			   return false;
    		   }
    		   roleName=getelementsList("roleOnUserDetailsPage");
    		   rolechkBox=getelementsList(dicOR.get("roleOnUserDetailsPage").replace("/label", "/label/input"));
    		   for(int i=0;i<roleName.size();i++)
    		   {
    			   for(String roleValue : roles)
    			   {
    				   shouldbeChecked=roleName.get(i).getText().startsWith(roleValue);
    			   }
    			   if(shouldbeChecked)
    			   {
    				   flag=PerformAction(rolechkBox.get(i),Action.SelectCheckbox);
    				   waitForPageLoaded();
    			   }
    			   else
    			   {
    				   flag=PerformAction(rolechkBox.get(i),Action.DeSelectCheckbox);
    				   waitForPageLoaded();
    			   }
    			   if (!flag)
                       errorIndex = errorIndex + ", " + i;
               }
    		   if(dicCommon.get("BrowserName").toLowerCase().equals("chrome")||dicCommon.get("BrowserName").toLowerCase().equals("ie"))
    		   {
				   flag = PerformAction("btnSave", Action.ClickUsingJS);
				   waitForPageLoaded();
				   if(!flag)
				   {
					   throw new RuntimeException("Could not click on 'Save' button"); 
				   }
    		   }
    		   else
    		   {
    			   driver.findElement(By.xpath("//button[@class='btn btn-success' and @type='submit']")).click();
    			   if (!flag)
    			   {
    				   throw new RuntimeException("Could not click on 'Save' button"); 
    			   } 
    		   }
               // If errorIndex is not empty then function is not pass and so, throw an exception including the errorIndex string containing the checkbox indices where check/uncheck could not be performed
               if ( ! errorIndex.equals(""))
                   throw new RuntimeException("Could not check/uncheck checkboxes at indices : " + errorIndex);
    	   }
    	   catch(Exception e)
    	   {
    		   flag=false;
    		   strErrMsg_AppLib="selection_Deselection_Role: "+e.getMessage();
    	   }
    	   return flag;
       }
       
       /*Function to verify user information is in json format
        * Created by Deepak
        */
       public final boolean Verify_UserInfo_JSON()
       {
    	   boolean flag = false;
           strErrMsg_AppLib = "";
           String filePath=dicConfig.get("Artifacts")+"\\additionalFolder\\UserInformationJson.json";
           try
           {
        	   flag=WindowNames.readTxtFile(filePath);
        	   strErrMsg_AppLib ="Report is in json format";
           }
           catch(Exception e)
           {
        	   flag=false;
        	   strErrMsg_AppLib = "Verify_UserInfo_Json --- " +e.getMessage();
           }
           return flag;
       }
       

       /*Function to verify user information is in list format
        * Created by Deepak
        */
       public final boolean Verify_UserInfo_List()
       {
    	   boolean flag = false;
           strErrMsg_AppLib = "";
           String filePath=dicConfig.get("Artifacts")+"\\additionalFolder\\UserInformationList.txt";
           try
           {
        	   flag=WindowNames.readTxtFile(filePath);
        	   strErrMsg_AppLib ="Report is in list format";
           }
           catch(Exception e)
           {
        	   flag=false;
        	   strErrMsg_AppLib = "Verify_UserInfo_List --- " +e.getMessage();
           }
           return flag;
       }
       
       /*Function to verify user information is in csv format
        * Created by Deepak
        */
       public final boolean Verify_UserInfo_CSV()
       {
    	   boolean flag = false;
           strErrMsg_AppLib = "";
           String filePath=dicConfig.get("Artifacts")+"\\additionalFolder\\UserInformationCSV.csv";
           try
           {
        	   flag=WindowNames.readTxtFile(filePath);
        	   strErrMsg_AppLib ="Report is in csv format";
           }
           catch(Exception e)
           {
        	   flag=false;
        	   strErrMsg_AppLib = "Verify_UserInfo_CSV --- " +e.getMessage();
           }
           return flag;
       }
       
       /*Function to verify device logs generated
        * Created by Deepak
        */
       public final boolean Verify_Logs_Generated()
       {
    	   boolean flag = false;
           strErrMsg_AppLib = "";
           String filePath=dicConfig.get("Artifacts")+"\\additionalFolder\\DeviceLogs.txt";
           try
           {
        	   flag=WindowNames.readTxtFile(filePath);
        	   strErrMsg_AppLib ="Logs generated";
           }
           catch(Exception e)
           {
        	   flag=false;
        	   strErrMsg_AppLib = "Verify_Logs_generated --- " +e.getMessage();
           }
           return flag;
       }
       
       public final boolean RSVD_DeleteReservationbydeviceName(String deviceName)
       {
    	   boolean flag = false;
    	   strErrMsg_AppLib = "";
    	   String startDateXPath = "", endDateXPath = "",startDateValue="",endDateValue="";

    	   try
    	   {
    		   String XPATHdeviceNameCol = getValueFromDictAndReplace(dicOR, "eleReservationTableCol_ReservationIndex", "__COLUMNHEADER__", "Device");
    		   //String XPATHNextReservationCol = getValueFromDictAndReplace(dicOR, "eleDeviceTableCol_DevicesIndex", "__COLUMNHEADER__", "Next Reservation");
    		   List <WebElement> ReservationDeviceList = getelementsList(XPATHdeviceNameCol);
    		   List <WebElement> CancelButtons = getelementsList("eleReservationTableColCancel_ReservationIndex");
    		   int counter = 0;
    		   for(int i=0; i<=ReservationDeviceList.size();i++)
    		   {
    			   String dName = GetTextOrValue(ReservationDeviceList.get(i), "text");
    			   if(dName.equals(deviceName))
    			   {
    				   counter ++;
    				   waitForPageLoaded();
    				   flag = PerformAction(CancelButtons.get(i), Action.Click);
    				   if(flag)
    				   {
    					   String Value = getValueFromDictAndReplace(dicOR, "eleHeader", "__EXPECTED_HEADER__", "Cancel Reservation");
    					   flag = PerformAction(Value, Action.WaitForElement);
    					   if(flag)
    					   {
    						   waitForPageLoaded();
    						   flag = PerformAction("btnCancelReservationYes", Action.isDisplayed);
    						   if(flag)
    						   {
    							   waitForPageLoaded();
    							   flag = PerformAction("btnCancelReservationYes", Action.ClickUsingJS);								
    							   if(!flag)
    								   strErrMsg_AppLib = "Not able to click on YES on confirmation dialog.";
    							   break;
    						   }
    						   else
    						   {
    							   waitForPageLoaded();
    							   flag = PerformAction("btnCancelReservationseries", Action.isDisplayed);
    							   if(flag)
    							   {
    								   waitForPageLoaded();
    								   flag = PerformAction("btnCancelReservationseries", Action.ClickUsingJS);								
    								   if(!flag)
    									   strErrMsg_AppLib = "Not able to click on series button on confirmation dialog.";
    								   break;
    							   }
    						   }
    					   }
    				   }
    				   else
    					   strErrMsg_AppLib = "Not able to click on Cancel button.";
    			   }					
    		   }		
    		   if(!(counter>0))
    			   strErrMsg_AppLib = "Device Name not found under Reservations list.";
    	   }
    	   catch(RuntimeException e)
    	   {
    		   strErrMsg_AppLib = e.getMessage();
    		   flag=false;
    	   }

    	   return flag;
       }
       
       
       public final boolean RSVD_DeleteAllReservations()
       {
    	   boolean flag = false;
    	   strErrMsg_AppLib = "";
    	   String startDateXPath = "", endDateXPath = "",startDateValue="",endDateValue="";

    	   try
    	   {
    		   String XPATHdeviceNameCol = dicOR.get("eleReservationTableCol_ReservationIndex").replace("__COLUMNHEADER__", "Device");
    		   //String XPATHNextReservationCol = getValueFromDictAndReplace(dicOR, "eleDeviceTableCol_DevicesIndex", "__COLUMNHEADER__", "Next Reservation");
    		   List <WebElement> ReservationDeviceList = getelementsList(XPATHdeviceNameCol);
    		   List <WebElement> CancelButtons = getelementsList("eleReservationTableColCancel_ReservationIndex");
    		   int counter = 0;
    		   for(int i=0; i<ReservationDeviceList.size();i++)
    		   {
    			   counter ++;
    				   flag = PerformAction("eleReservationTableColCancel_ReservationIndex", Action.Click);
    				   if(flag)
    				   {
    					   String Value = getValueFromDictAndReplace(dicOR, "eleHeader", "__EXPECTED_HEADER__", "Cancel Reservation");
    					   flag = PerformAction(Value, Action.WaitForElement);
    					   if(flag)
    					   {
    						   flag = PerformAction("btnCancelReservationYes", Action.isDisplayed);
    						   if(flag)
    						   {
    							   flag = PerformAction("btnCancelReservationYes", Action.ClickUsingJS);								
    							   if(!flag)
    								   strErrMsg_AppLib = "Not able to click on YES on confirmation dialog.";
    							   break;
    						   }
    						   else
    						   {
    							   flag = PerformAction("btnCancelReservationseries", Action.isDisplayed);
    							   if(flag)
    							   {
    								   flag = PerformAction("btnCancelReservationseries", Action.ClickUsingJS);								
    								   if(!flag)
    									   strErrMsg_AppLib = "Not able to click on series button on confirmation dialog.";
    								   break;
    							   }
    						   }
    					   }
    				   }
    				   else
    					   strErrMsg_AppLib = "Not able to click on Cancel button.";
    			  					
    		   }		
    		   if(!(counter>0))
    			   strErrMsg_AppLib = "Reservations list is empty.";
    	   }
    	   catch(RuntimeException e)
    	   {
    		   strErrMsg_AppLib = e.getMessage();
    		   flag=false;
    	   }

    	   return flag;
       }
       
       public final Boolean selectAllCheckboxAndVerify_Users()
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			ArrayList<String> devicesSelected = new ArrayList<String>();
			Object[] values = new Object[2];
			try
			{
				//Select the select all checkbox on users page
				flag = PerformAction("chkSelectAll_Devices", Action.SelectCheckbox);
				if (flag) // If it is selected then get the names of all the devices which are displayed and verify that the checkbox in front of each device got selected.
				{
					flag =  VerifyAllCheckedOrUnchecked_Users(Action.isSelected);
					if (!flag)
					{
						throw new RuntimeException(strErrMsg_AppLib);
					}
				}
				else
				{
					throw new RuntimeException("Could not select the select all checkbox.");
				}
				 
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "selectAllCheckboxAndVerify_DI--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return flag;

		}

		/** 
   //Verifies if all checkboxes in front of all the displayed users are checked or not.
   //
   //@author deepak
   //@since 6/9/2016
   //@param checkStatusToVerify It takes values : Action.isSelected if it needs to be verfied that all checkboxes are selected or Action.isNotSelected to check that all checkboxes are not selected. 
   //@return True or False
		 */
		public final  Boolean VerifyAllCheckedOrUnchecked_Users(String checkStatusToVerify)
		{
			boolean flag = false;
			strErrMsg_AppLib = "";
			int usersCount = 0;
			String strErrorIndex = "";
			try
			{
				usersCount = getelementCount("usersSelectBox") - 1;
				if (usersCount > 0) // If number of rows are obtained then check if the check-status of all the checkboxes match the given check status
				{
					for (int i = 1; i <= usersCount; i++)
					{
						if (!PerformAction(dicOR.get("usersSelectBox").replace("tr[*]", "tr["+i+"]"), checkStatusToVerify)) // If the checkbox is not selected/deselected then put the index to errorVariable
						{
							strErrorIndex = strErrorIndex + ", " + i;
						}
					}
					if ( ! strErrorIndex.equals(""))
					{
						throw new RuntimeException("Checkbox is not in correct checked-state for device at index(s) : '" + strErrorIndex + "'.");
					}
					flag = true;
				}
				else
				{
					throw new RuntimeException(strErrMsg_AppLib);
				}
			}
			catch (RuntimeException e)
			{
				flag = false;
				strErrMsg_AppLib = "VerifyAllCheckedOrUnchecked_Users--- " + " + " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			}
			return flag;
		}

		
		//Modify AppName on Applications page
	    /*Function to change device Name
		 * Created by Ritdhwaj Chandel
		 * @Parameter takes deviceName to be edited
		 * Returns true or false
		 */
			public final Boolean editAppName(String editedAppName)
			{
				boolean isEventSuccessful = false;
				strErrMsg_AppLib = "";
				try
				{
					isEventSuccessful=PerformAction(dicOR.get("lnkEdit_ApplicationDetailsPage"), Action.isDisplayed);;
					if(isEventSuccessful)
					{
						isEventSuccessful=PerformAction(dicOR.get("lnkEdit_ApplicationDetailsPage"), Action.Click);

						if(isEventSuccessful)
						{
							isEventSuccessful=PerformAction(dicOR.get("eleAppNameInputBox"),Action.Type,editedAppName);
							if(isEventSuccessful)
							{
								isEventSuccessful=PerformAction(dicOR.get("lnkSaveAppName_ApplicationDetailsPage"), Action.Click);
								if(!isEventSuccessful)
								{
									strErrMsg_AppLib="Unable to click on Save link.";
								}
							}
							else
							{
								strErrMsg_AppLib="Unable to edit appName";
							}
						}
						else
						{
							strErrMsg_AppLib="Unable to click on edit link";
						}
					}
					else
					{
						strErrMsg_AppLib="Edit link not displayed";
					}


				}
				catch(Exception e)
				{
					isEventSuccessful = false;
					strErrMsg_AppLib = "Exception : "+e.getMessage();
				}
				return isEventSuccessful;

			}

		
			/*Function to change device Name
			 * Created by Ritdhwaj Chandel
			 * @Parameter takes deviceName to be edited
			 * Returns true or false
			 */

				public final Boolean editDeviceName(String editedDeviceName)
				{
					boolean isEventSuccessful = false;
					strErrMsg_AppLib = "";
					try
					{
						waitForPageLoaded();
						isEventSuccessful=PerformAction(dicOR.get("deviceName_DeviceDetails"),Action.MouseHover);
						if(isEventSuccessful)
						{
							waitForPageLoaded();
							isEventSuccessful=PerformAction(dicOR.get("lnkEditDeviceName_DeviceDetailPage"),Action.Click);

							if(isEventSuccessful)
							{
								waitForPageLoaded();
								isEventSuccessful=PerformAction("editNameBox_DeviceDetailsPage",Action.Type,editedDeviceName);
								if(isEventSuccessful)
								{
									waitForPageLoaded();
									isEventSuccessful=PerformAction("saveDeviceName",Action.Click);
									if(!isEventSuccessful)
									{
										strErrMsg_AppLib="Unable to click on Save button.";
									}
								}
								else
								{
									strErrMsg_AppLib="Save button did not found on the page.";
								}
							}
							else
							{
								strErrMsg_AppLib="Device name edit field did not open";
							}
						}
						else
						{
							strErrMsg_AppLib="Unable to mousehover on deviceName";
						}


					}
					catch(Exception e)
					{
						isEventSuccessful = false;
						strErrMsg_AppLib = "Exception : "+e.getMessage();
					}
					return isEventSuccessful;

				}

				
				/*Function to get any detail from applications details page
				 * Created by Ritdhwaj
				 * @parameter detailName-Name of the details to be found on the page
				 * returns-detail on the page.
				 */
				public final String getDetailFromApplicationDetailsPage(String detailName){

					String fieldValue="";
					strErrMsg_AppLib = "";

					try{
						waitForPageLoaded();
						fieldValue=GetTextOrValue(dicOR.get("appDetail_ApplicationDetailsPage").replace("detailName", detailName), "text");
					}
					catch(Exception e)
					{
						strErrMsg_AppLib = "getDetailFromApplicationDetailsPage --- " +e.getMessage();
					}

					return fieldValue;
				}

				
				/*Function to verify Multiple Instances for application in device details page
				 * Created by Ritdhwaj
				 * Returns true or false
				 */
				public final boolean verifyMultipleInstanceofApp_DeviceDetailsPage(String appName)
				{
					boolean flag = false;
					strErrMsg_AppLib = "";
					try
					{
						waitForPageLoaded();
						flag=PerformAction(dicOR.get("multipleInstanceIcon_DeviceDetailsPage").replace("appName", appName), Action.isDisplayed);
					}
					catch(Exception e)
					{
						flag=false;
						strErrMsg_AppLib = "Verify Multiple Instances--- " +e.getMessage();
					}
					return flag;
				}

				
				/*Function to verify Multiple Instances for application in Launch Dialog
				 * Created by Ritdhwaj Chandel
				 * Returns true or false
				 */
				public final boolean verifyMultipleInstanceofApp_LaunchDialog(String appName)
				{
					boolean flag = false;
					strErrMsg_AppLib = "";
					try
					{
						flag=PerformAction(dicOR.get("multipleInstanceIcon_LaunchDialog").replace("appName", appName), Action.isDisplayed);
					}
					catch(Exception e)
					{
						flag=false;
						strErrMsg_AppLib = "Verify Multiple Instances --- " +e.getMessage();
					}
					return flag;
				}

				
				/*Function to verify If date is enabled in calender or not
				 * Created by Ritdhwaj Chandel
				 * @Parameter takes date in DD format
				 * Returns true or false
				 */
				public final boolean verifyDateDisabled(int Date)
				{
					boolean flag = false;
					strErrMsg_AppLib = "";
					try
					{
						flag=PerformAction(dicOR.get("dateDisabled_ReservationCalender").replace("date", Integer.toString(Date)), Action.isDisplayed);
					}
					catch(Exception e)
					{
						flag=false;
						strErrMsg_AppLib = "Verify Multiple Instances --- " +e.getMessage();
					}
					return flag;
				}

				
				/*Function to change Verify Multiple App Uploads
				 * Created by Ritdhwaj Chandel
				 * @Parameter takes appName to be checked
				 * Returns true or false
				 */

					public final Boolean verifyMultipleAppUploads(String appName)
					{
						boolean isEventSuccessful = false;
						strErrMsg_AppLib = "";
						try
						{
							
							isEventSuccessful=PerformAction(dicOR.get("multipleAppUpload").replace("__APP_Name__", appName),Action.isDisplayed);
							 
						}
						catch(Exception e)
						{
							isEventSuccessful = false;
							strErrMsg_AppLib = "Exception : "+e.getMessage();
						}
						return isEventSuccessful;

					}
					
					//This function gets all the Applicatioin Details available on AppDetails page.
					//<!--Created By : Jaishree Patidar-->
					//
					//@param 
					//@return Map having application details in the form of key value pair.
					//
					public final Map<String,String> getAppDetailsFromApplicationPage()
					{
						strErrMsg_AppLib = "";
						Map<String,String> expectedResultMap= new HashMap<String,String>();
						try
						{	
							expectedResultMap.put("displayName", GetTextOrValue(dicOR.get("eleDisplayName_AppDetailsPage"), "text"));
							expectedResultMap.put("vendorApplicationName", GetTextOrValue(dicOR.get("eleAppName_AppDetailsPage"), "text"));
							expectedResultMap.put("operatingSystem",GetTextOrValue(dicOR.get("eleOSName_AppDetailsPage"), "text"));
							expectedResultMap.put("fileName", GetTextOrValue(dicOR.get("eleFileName_AppDetailsPage"), "text"));

							String VersionValueUI= GetTextOrValue(dicOR.get("eleVersion_AppDetailsPage"), "text");
							String versionCounter= VersionValueUI.substring(VersionValueUI.indexOf("#")+1, VersionValueUI.length());
							String buildVersion= VersionValueUI.substring(VersionValueUI.indexOf("(")+1, VersionValueUI.indexOf(")"));
							String version=VersionValueUI.replace("("+buildVersion+")"+" #"+versionCounter, "");
							System.out.println(version);
							expectedResultMap.put("version",version);
							expectedResultMap.put("buildVersion", buildVersion);
							expectedResultMap.put("versionCounter", versionCounter);

							expectedResultMap.put("minimumOperatingSystemVersion", GetTextOrValue(dicOR.get("eleMinimumOSVersion_AppDetailsPage"), "text"));
							expectedResultMap.put("applicationIdentifier", GetTextOrValue(dicOR.get("eleApplicationIdentifier_AppDetailsPage"), "text"));
							expectedResultMap.put("supportedFormFactors", GetTextOrValue(dicOR.get("eleFormFactor_AppDetailsPage"), "text"));

							String fileSizeInMB= GetTextOrValue(dicOR.get("eleFileSize_AppDetailsPage"), "text");
							/*fileSizeInMB = fileSizeInMB.replace("MB", "");
							float fileSize = Float.parseFloat(fileSizeInMB);
							int fileSizeBytes = (int) (fileSize * 1000000);
							System.out.println(fileSizeBytes);
							 */	
							expectedResultMap.put("fileByteCount", fileSizeInMB);
							expectedResultMap.put("appTeamIdentifier", GetTextOrValue(dicOR.get("eleTeamIdentifier_AppDetailsPage"), "text"));
							String signingCertificateName=GetTextOrValue(dicOR.get("eleCertificateIdentity_AppDetailsPage"), "text");
							expectedResultMap.put("signingCertificateName", signingCertificateName);

							expectedResultMap.put("createdDate", GetTextOrValue(dicOR.get("eleUploadDate_AppDetailsPage"), "text"));
							try
							{
								boolean bolVal = PerformAction(dicOR.get("eleAppErrors_AppDetailsPage"), Action.Exist);	
								if (bolVal)
									expectedResultMap.put("applicationErrors", "[\"" +GetTextOrValue(dicOR.get("eleAppErrors_AppDetailsPage"), "text") +"\"]");
								else
									expectedResultMap.put("applicationErrors", "null");
							}catch (RuntimeException ex){
								expectedResultMap.put("applicationErrors", "null");
							}


							System.out.println(expectedResultMap);	  	  

						}catch (Exception e)
						{
							strErrMsg_AppLib = "getAppDetailsFromApplicationPage---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
						}

						return expectedResultMap;
					}
					public Map<String,String> getApplicationExpectedMap(String id, String serverIP)
					{
						Map<String,String> expectedResultMap= new HashMap<String,String>();
						String strActualResult,strExpectedResult,strStepDescription;
						try
						{
							isEventSuccessful=true;
							isEventSuccessful=PerformAction("browser","navigate","http://"+serverIP+"/#/Application/Detail/" + id);
							if(isEventSuccessful)
							{
								strActualResult= "Navigate to Application Details page of the app using GUID. ID= " + id +" server used= " +serverIP;
							}
							else
							{
								strActualResult= "PerformAction---Navigate to URL. ID= " + id +" server used= " +serverIP+ strErrMsg_GenLib;
							}
							reporter.ReportStep("Navigates to App details page", "Navigates successfully", strActualResult, isEventSuccessful);

							// Step 4 : Verify application information on application details page.
							strStepDescription = "Getting application details <br> (Application Name,OS, File name,Version, MinimumOSVersion,ApplicationIdentifier ,FormFactor,FileSize, TeamIdentifier,CertificateIdentity) from application details page. ";
							strExpectedResult = " A HashMap having all the values should be returned.";
							expectedResultMap = getAppDetailsFromApplicationPage();
							strActualResult="Map returned having value = "+ expectedResultMap;
							reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

							expectedResultMap.put("enabled", "true"); // enabled is true for all apps
							expectedResultMap.put("remotePort", "0"); // remotePort is 0 for all apps
							expectedResultMap.put("id", id); //adding Id key in the expected Map


							//below model properties are Not Verified
							expectedResultMap.put("applicationBlob", "Not Verified");
							expectedResultMap.put("iconBlob", "Not Verified");
							expectedResultMap.put("originalApplicationBlob", "Not Verified");
							expectedResultMap.put("agentHash", "Not Verified");	
							expectedResultMap.put("notes", "Not Verified");	

							//adding more keys for Android App
							if (expectedResultMap.get("operatingSystem").equalsIgnoreCase("android"))
							{
								expectedResultMap.put("isSigningCertificatePresentInEmbeddedProvisionProfile", "false"); //isSigningCertificatePresentInEmbeddedProvisionProfile is always false for Android App
								expectedResultMap.put("provisionsAllDevices", "false"); //provisionsAllDevices is always false for Android Apps
								expectedResultMap.put("provisionExpirationDate", "null");
								expectedResultMap.put("trustDylibTeamIdentifier", "null");
								expectedResultMap.put("isTrustDylibEmbeddedInApp", "false"); //isTrustDylibEmbeddedInApp is always false for any App
							}
							else
							{
								if (!expectedResultMap.get("signingCertificateName").equals(""))
									expectedResultMap.put("isSigningCertificatePresentInEmbeddedProvisionProfile", "true");
								else
									expectedResultMap.put("isSigningCertificatePresentInEmbeddedProvisionProfile", "false");
								expectedResultMap.put("provisionsAllDevices", "true"); //provisionsAllDevices if used XC: * (Test Apps, expires 5/3/2016)
								expectedResultMap.put("provisionExpirationDate", "Not Verified");
								expectedResultMap.put("trustDylibTeamIdentifier", "Not Verified");
								expectedResultMap.put("isTrustDylibEmbeddedInApp", "Not Verified"); //isTrustDylibEmbeddedInApp is always false for any App
							}
						}
						catch(Exception ex)
						{
							isEventSuccessful=false;
							writeToLog("getApplicationExpectedMap---" + ex.getMessage());
						}

						return expectedResultMap;
					}

					/* CREATED BY Jaishree Patidar*/
					public final boolean SelectColumn_Application(String columnsName){
						String[] columnsNameArray = columnsName.split("[,]", -1);
						strErrMsg_AppLib = "";
						boolean isEventSuccessful = false;
						try
						{

							//Verify Settings button is displayed.
							if(PerformAction("btnSettings_Devices", Action.isDisplayed))
							{
								//Verify Settings button is clicked.
								if(PerformAction("btnSettings_Devices", Action.Click))
								{
									//Verify Columns checkboxes is displayed.
									if(PerformAction("drpSettings_Devices", Action.isDisplayed)) 
									{
										isEventSuccessful = selectCheckboxes_DI(columnsNameArray, "chkColumns_Devices");
										if(isEventSuccessful && dicCommon.get("BrowserName").toLowerCase().equals("ie"))
										{
											//isEventSuccessful = PerformAction("btnSettings_Devices", Action.ClickUsingJS);
											if(PerformAction("drpSettings_Devices", Action.isDisplayed)) 
											{
												throw new RuntimeException("Columns check boxes is displayed.");
											}
										}
										if (isEventSuccessful && dicCommon.get("BrowserName").toLowerCase() != "ie")
										{
											if(PerformAction("drpSettings_Devices", Action.isDisplayed)) 
											{
												throw new RuntimeException("Columns check boxes is displayed.");
											}
										}
										else
										{
											throw new RuntimeException("Could not select check boxes.");
										}
									}
									else
									{
										throw new RuntimeException("Columns check boxes is not displayed.");
									}
								}
								else
								{
									throw new RuntimeException("Settings button is not clicked on devices index page.");
								}
							}
							else
							{
								throw new RuntimeException("Settings button is not displayed on devices index page.");
							}
							CoulmnsDisplayed_Devices(columnsName);
						}
						catch (RuntimeException e)
						{
							isEventSuccessful = false;
							strErrMsg_AppLib = "CoulmnsDisplayed_Devices--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
						}
						return isEventSuccessful;

					}


					/*Tarun Ahuja : This Function Calls getDetailsFromDeviceDetailsPage internally to fetch Device properties from device Details page and devices index page */
					public final Map<String,String> getDeviceExpectedMap(String DeviceID , String DeviceName, String serverIP)
					{
						strErrMsg_AppLib = "";
						String strActualResult,strExpectedResult,strStepDescription;
						Map<String,String> expectedResultMap= new HashMap<String,String>();
						APILibrary api = new APILibrary();
						String ServerIP = serverIP;

						try
						{   
							String devieId = DeviceID;

							expectedResultMap.put("id", devieId);
							isEventSuccessful=true;
							isEventSuccessful=PerformAction("browser","navigate","http://"+ServerIP+"/#/Device/Detail/" + devieId);
							if(isEventSuccessful)
							{
								strActualResult= "Navigate to Devie Details page of the app using GUID. ID= " + devieId +" server used= " +serverIP;
							}
							else
							{
								strActualResult= "PerformAction---Navigate to URL. ID= " + devieId +" server used= " +serverIP+ strErrMsg_GenLib;
							}
							reporter.ReportStep("Navigates to Device details page", "Navigates successfully", strActualResult, isEventSuccessful);

							// Step 4 : Verify device information on application details page.
							strStepDescription = "Getting device details <br> (Device Name, DeviceID, BatteryStatus, BatteryPercentage,  Disk Usage, Slot, Notes, Next Reservation, DeviceMemory, Availability, Enabled, Deleted) from application details page. ";
							strExpectedResult = " A HashMap having all the values should be returned.";
							String deviceName = DeviceName;
							if(deviceName.equals(""))
							{
								deviceName= GetTextOrValue(dicOR.get("eleDeviceNamebox_DeviceDetailsPage"), "text");
							}
							expectedResultMap = getDetailsFromDeviceDetailsPage(deviceName,devieId);
							strActualResult="Map returned having value = "+ expectedResultMap;
							reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
						}
						catch(Exception ex)
						{
							isEventSuccessful=false;
							writeToLog("getApplicationExpectedMap---" + ex.getMessage());
						}

						return expectedResultMap;
					}


					/* Tarun Ahuja : Fetching data for device properties from device details and device indexes page 
					 * Modified By-Ritdhwaj Chandel*/

					/* Tarun Ahuja : Fetching data for device properties from device details and device indexes page 
					 * Modified By-Ritdhwaj Chandel*/
					public final Map<String,String> getDetailsFromDeviceDetailsPage(String devicename, String deviceid)
					{
						APILibrary apiMethods = new APILibrary();
						ScriptFuncLibrary sfl=new ScriptFuncLibrary();
						String diskSpaceUsedGB="";
						strErrMsg_AppLib = "";
						Map<String,String> expectedResultMap= new HashMap<String,String>();
						try
						{
							String deviceName = devicename;
							String deviceID = deviceid;
							// Code to Get Battery Status and charge Percentage
							isEventSuccessful = PerformAction("eleBatteryStatusValue", Action.Exist);
							if (isEventSuccessful){
								String battery = getAttribute("eleBatteryStatusValue","title");
								String BatteryStatus = battery.split("\\(")[1].replace(")","").replace(" ", "");
								String Batterypercentage =  battery.split(" ")[2];
								Batterypercentage =  Batterypercentage.replace("%", "");
								expectedResultMap.put("batteryStatus",BatteryStatus);
								expectedResultMap.put("batteryPercentCharged",Batterypercentage);
							}
							else
							{
								expectedResultMap.put("batteryStatus","Unknown");
								expectedResultMap.put("batteryPercentCharged","0");
							}

							//Navigating to device details

							isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);

							expectedResultMap.put("id",deviceid);   //Putting id to expected map
							expectedResultMap.put("name",deviceName.replace("’", "â€™")); //Putting device name to expected map

							// Code to get manufacturer and friendly model for device
							String deviceModel = GetTextOrValue(dicOR.get("eleDeviceModel"), "text");

							String deviceModelname = deviceModel.split(",")[1];

							System.out.println("device Model is -------- " + deviceModelname);

							String Status=GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text");

							// code to get model # serial# and vendor Unique id based on defferent scenario's


							String model=GetTextOrValue(dicOR.get("eleModelNo_DeviceDetailsPage"), "text");
							expectedResultMap.put("model",model);
							String serialNumber=GetTextOrValue(dicOR.get("eleSerialNo_DeviceDetailsPage"), "text");
							expectedResultMap.put("serialNumber",serialNumber);
							String vendorName=GetTextOrValue(dicOR.get("eleVendorName_DeviceDetailsPage"), "text");
							expectedResultMap.put("vendorDeviceName",vendorName.replace("’", "â€™"));
							if(deviceModel.contains("Android"))
							{
								expectedResultMap.put("vendorUniqueIdentifier",serialNumber);
							}
							else{
								String Vendor=GetTextOrValue(dicOR.get("eleVendorId_DeviceDetailsPage"), "text");
								expectedResultMap.put("vendorUniqueIdentifier",Vendor);
							}



							if(Status.equalsIgnoreCase("disabled") ||Status.equalsIgnoreCase("Removed")||Status.equalsIgnoreCase("Offline")){
								expectedResultMap.put("availability","Offline");
							}
							else{
								expectedResultMap.put("availability","online");
							}
							//enabled Property

							if(Status.contains("In Use") || Status.contains("Available") ||Status.contains("Offline")||Status.contains("Reserved")){
								expectedResultMap.put("enabled","true");
							}
							else{

								expectedResultMap.put("enabled","false");

							}
							//removed Property
							if(Status.equalsIgnoreCase("removed")){
								expectedResultMap.put("deleted","true");
							}
							else{
								expectedResultMap.put("deleted","false");
							}

							//Slot # of device

							String slotNumber=GetTextOrValue(dicOR.get("eleSlot_deviceDetailsPage"), "text");
							if(slotNumber.equals(0) || slotNumber.equals("None")){
								expectedResultMap.put("slotNumber", "null");
							}
							else{
								expectedResultMap.put("slotNumber", slotNumber);
							}

							//Notes of devices

							String notes=GetTextOrValue(dicOR.get("eleNotes_deviceDetailsPage"), "text");

							if(notes.equalsIgnoreCase("none")){
								expectedResultMap.put("notes", "null");
							}
							else{
								expectedResultMap.put("notes", notes);
							}

							/*Fetcing the Memory already consumed on device*/


							if(deviceModel.contains("Android"))
							{

								//expectedResultMap.put("macAddress", "null");
								expectedResultMap.put("operatingSystem", "Android");
								expectedResultMap.put("operatingSystemVersion",deviceModelname.split(" ")[2]);
								//PerformAction("", Action.WaitForElement,"2");
								String DeviceSpaceUsedUI;
								DeviceSpaceUsedUI = GetTextOrValue(dicOR.get("eleDiskUsage_DeviceDetailsPage"), "text");

								if(DeviceSpaceUsedUI.equals("Not Available"))
								{
									expectedResultMap.put("diskSpaceUsed","0.0GB");
									expectedResultMap.put("diskSpace","0.0GB");
								}
								else{
									String diskspaceUsed = DeviceSpaceUsedUI.split(" ")[0];
									String deviceMemory = DeviceSpaceUsedUI.split(" ")[2];

									if(diskspaceUsed.contains("MB"))
									{
										diskspaceUsed =diskspaceUsed.replace("MB", "");
										float diskSpaceMB=Float.parseFloat(diskspaceUsed);
										float diskSpaceBytes =diskSpaceMB*1000000;
										diskspaceUsed=Float.toString(diskSpaceBytes);
										diskSpaceUsedGB = apiMethods.convertFromBytes(diskspaceUsed, "gb");
										expectedResultMap.put("diskSpaceUsed",diskSpaceUsedGB);


									}
									if(deviceMemory.contains("MB"))
									{
										deviceMemory =deviceMemory.replace("MB", "");
										float diskMemoryMB=Float.parseFloat(deviceMemory);
										float diskMemoryBytes =diskMemoryMB*1000000;
										deviceMemory=Float.toString(diskMemoryBytes);
										String deviceMemoryGB = apiMethods.convertFromBytes(deviceMemory, "gb");
										expectedResultMap.put("diskSpaceUsed",deviceMemoryGB);


									}

									else 
									{
										expectedResultMap.put("diskSpaceUsed",diskspaceUsed);   
										expectedResultMap.put("diskSpace",deviceMemory);
									}
								}
							}

							if(deviceModel.contains("Apple"))
							{
								expectedResultMap.put("operatingSystem", "IOS");
								expectedResultMap.put("operatingSystemVersion",deviceModelname.split(" ")[2]);
								String DeviceSpaceUsedUI;
								// if(Status.contains("Available")){
								DeviceSpaceUsedUI = GetTextOrValue(dicOR.get("eleDiskUsage_DeviceDetailsPage"), "text");

								if(DeviceSpaceUsedUI.equals("Not Available"))
								{
									expectedResultMap.put("diskSpaceUsed","0.0GB");
									expectedResultMap.put("diskSpace","0.0GB");
								}
								else{

									String diskspaceUsed = DeviceSpaceUsedUI.split(" ")[0];
									String deviceMemory = DeviceSpaceUsedUI.split(" ")[2];

									if(diskspaceUsed.contains("MB"))
									{
										diskspaceUsed =diskspaceUsed.replace("MB", "");
										float diskSpaceMB=Float.parseFloat(diskspaceUsed);
										float diskSpaceBytes =diskSpaceMB*1000000;
										diskspaceUsed=Float.toString(diskSpaceBytes);
										diskSpaceUsedGB = apiMethods.convertFromBytes(diskspaceUsed, "gb");
										expectedResultMap.put("diskSpaceUsed",diskSpaceUsedGB);


									}
									if(deviceMemory.contains("MB"))
									{
										deviceMemory =deviceMemory.replace("MB", "");
										float diskMemoryMB=Float.parseFloat(deviceMemory);
										float diskMemoryBytes =diskMemoryMB*1000000;
										deviceMemory=Float.toString(diskMemoryBytes);
										String deviceMemoryGB = apiMethods.convertFromBytes(deviceMemory, "gb");
										expectedResultMap.put("diskSpaceUsed",deviceMemoryGB);


									}

									else 
									{
										expectedResultMap.put("diskSpaceUsed",diskspaceUsed);   
										expectedResultMap.put("diskSpace",deviceMemory);
									}
								}
							}
							//Code to get inuse last connected last disconnected.

							if(Status.contains("In Use")){
								String lastInUse=GetTextOrValue(dicOR.get("eleInUseSince_DeviceDetailsPage"), "text");
								String lastInUseAt=lastInUse.split(" ")[0]+" "+lastInUse.split(" ")[1]+" "+lastInUse.split(" ")[2];
								expectedResultMap.put("lastInuseAt",lastInUseAt);
							}
							else{
								expectedResultMap.put("lastInuseAt","null");
							}

							//For offfline devices last Disconnected
							if(Status.equals("Offline")){
								String lastDisConnected=GetTextOrValue(dicOR.get("eleLastDisconnectedAt_DeviceDetailsPage"), "text");
								String lastDisConnectedAt=lastDisConnected.split(" ")[0]+" "+lastDisConnected.split(" ")[1]+" "+lastDisConnected.split(" ")[2];
								expectedResultMap.put("lastDisconnectedAt",lastDisConnectedAt);
							}

							//Last Connected at

							String lastConnected=GetTextOrValue(dicOR.get("eleOnlineSince_DeviceDetailsPage"), "text");
							String lastConnectedAt=lastConnected.split(" ")[0]+" "+lastConnected.split(" ")[1]+" "+lastConnected.split(" ")[2];
							expectedResultMap.put("lastConnectedAt",lastConnectedAt);

							//Script starts to get reservation details

							if(Status.contains("In Use") || Status.contains("Available")||Status.contains("Reserved")){
								System.out.println("Inside the loop");
								String nextReserve=GetTextOrValue(dicOR.get("eleNextReservation_DeviceDetailsPage"), "text");
								System.out.println("Value of nextReserve is:"+nextReserve);
								if(nextReserve.equals("None")){
									expectedResultMap.put("nextReservationStartTime","null");
									expectedResultMap.put("nextReservationEndTime","null");
									expectedResultMap.put("reservedById","null");
									expectedResultMap.put("reservedByDisplayName","null");
								}

								else{
									PerformAction("eleReservationDetails_DeviceDetailsPage", Action.Click);
									String reservationDateTime=driver.findElement(By.xpath("//*[@id='actual-content-body']/div/div/div/div[2]/div/div[1]/div[2]/div/table/tbody/tr[1]/td[1]")).getAttribute("title");
									// String reservationDateTime=getAttribute(dicOR.get("eleReservationDateTime_DeviceDetailsPage"), "title");
									System.out.println("reservation timings are:"+reservationDateTime);
									String reservationDate=reservationDateTime.split(" ")[0];

									String reservationStartTime=reservationDateTime.split(" ")[2]+":00";
									String reservationEndTime=reservationDateTime.split(" ")[5]+":00";
									String reservationStartMeridiem=reservationDateTime.split(" ")[3];
									String reservationEndMeridiem=reservationDateTime.split(" ")[6];
									String reservationStartDateTime=reservationDate+" "+reservationStartTime+" "+reservationStartMeridiem.toUpperCase();
									String reservationEndDateTime=reservationDate+" "+reservationEndTime+" " +reservationEndMeridiem.toUpperCase();

									String reservedByDisplayName=driver.findElement(By.xpath("//*[@id='actual-content-body']/div/div/div/div[2]/div/div[1]/div[2]/div/table/tbody/tr[1]/td[3]")).getText();
									//String reservedByDisplayName=GetTextOrValue(dicOR.get("eleReservedBy_DeviceDetailsPage"), "text");
									System.out.println("reserved by display name is:"+reservedByDisplayName);
									expectedResultMap.put("reservedByDisplayName",reservedByDisplayName);
									isEventSuccessful = sfl.GoToUsersPage();
									WebDriverWait wait = new WebDriverWait(driver, 20);
									wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='userListTable table data-grid user-list-table']/tbody/tr[1]/td[1]"))); 
									//Thread.sleep(5000);
									isEventSuccessful=sfl.searchUser(reservedByDisplayName);

									sfl.GoToFirstUserDetailsPage();
									String reservedById=sfl.GetUserID();
									expectedResultMap.put("reservedById",reservedById);
									expectedResultMap.put("nextReservationStartTime",reservationStartDateTime);
									expectedResultMap.put("nextReservationEndTime",reservationEndDateTime);
								}

							}
							else{
								expectedResultMap.put("nextReservationStartTime","null");
								expectedResultMap.put("nextReservationEndTime","null");
								expectedResultMap.put("reservedById","null");
								expectedResultMap.put("reservedByDisplayName","null");
							}

							//Getting retained by details based on different scenario's

							if(Status.contains("In Use")){

								String retainedByDisplayName=Status.substring(Status.indexOf("(")+1,Status.indexOf(")"));
								expectedResultMap.put("retainedByDisplayName", retainedByDisplayName);
								isEventSuccessful = sfl.GoToUsersPage();
								WebDriverWait wait = new WebDriverWait(driver, 20);
								wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='userListTable table data-grid user-list-table']/tbody/tr[1]/td[1]"))); 
								isEventSuccessful=sfl.searchUser(retainedByDisplayName);
								sfl.GoToFirstUserDetailsPage();
								String retainedById=sfl.GetUserID();
								expectedResultMap.put("retainedById", retainedById);
								expectedResultMap.put("retainedByUserName", GetTextOrValue(dicOR.get("eleUserName_UserDetailsPage"), "value"));
							}
							else{
								expectedResultMap.put("retainedByDisplayName", "null");
								expectedResultMap.put("retainedById", "null");
								expectedResultMap.put("retainedByUserName", "null");
							}
							System.out.println(expectedResultMap);	  

						}
						catch (Exception e)
						{
							strErrMsg_AppLib = "getAppDetailsFromApplicationPage---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
						}

						return expectedResultMap;
					}


					public final Map<String,String> deviceExpectedMap(String DeviceID , String DeviceName, String serverIP)
					{
						strErrMsg_AppLib = "";
						String strActualResult,strExpectedResult,strStepDescription;
						Map<String,String> expectedResultMap= new HashMap<String,String>();
						APILibrary api = new APILibrary();
						String ServerIP = serverIP;

						try
						{   
							String devieId = DeviceID;

							expectedResultMap.put("id", devieId);
							isEventSuccessful=true;
							isEventSuccessful=PerformAction("browser","navigate","http://"+ServerIP+"/#/Device/Detail/" + devieId);
							if(isEventSuccessful)
							{
								strActualResult= "Navigate to Devie Details page of the app using GUID. ID= " + devieId +" server used= " +serverIP;
							}
							else
							{
								strActualResult= "PerformAction---Navigate to URL. ID= " + devieId +" server used= " +serverIP+ strErrMsg_GenLib;
							}
							reporter.ReportStep("Navigates to Device details page", "Navigates successfully", strActualResult, isEventSuccessful);

							// Step 4 : Verify device information on application details page.
							strStepDescription = "Getting device details <br> (Device Name,Slot, Notes, Enabled, Deleted) from application details page. ";
							strExpectedResult = " A HashMap having Device Name,Slot, Notes, Enabled, Deleted  values should be returned.";


							String deviceName= GetTextOrValue(dicOR.get("eleDeviceNamebox_DeviceDetailsPage"), "text");
							expectedResultMap.put("name", deviceName);
							isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);

							//Getting Enabled Property
							String Status=GetTextOrValue(dicOR.get("eleAvailability_DeviceDetailsPage"), "text");
							if(Status.contains("In Use") || Status.contains("Available") ||Status.contains("Offline")){
								expectedResultMap.put("enabled","true");
							}
							else{

								expectedResultMap.put("enabled","false");

							}

							//Slot # of device

							String slotNumber=GetTextOrValue(dicOR.get("eleSlot_ShowDetails_DeviceDetailsPage"), "text");
							if(slotNumber.equals(0) || slotNumber.equals("None")){
								expectedResultMap.put("slotNumber", "null");
							}
							else{
								expectedResultMap.put("slotNumber", slotNumber);
							}

							//Notes of devices

							String notes=GetTextOrValue(dicOR.get("notesLocatorDeviceDetails"), "text");

							if(notes.equalsIgnoreCase("none")){
								expectedResultMap.put("notes", "null");
							}
							else{
								expectedResultMap.put("notes", notes);
							}  
							//Deleted property
							String removedMsg=GetTextOrValue(dicOR.get("eleDeviceRemovedMsg_DeviceDetailsPage"), "text");
							if(removedMsg.contains("This device was marked as removed.")){
								expectedResultMap.put("deleted", "true");
							}
							else{
								expectedResultMap.put("deleted", "false");
							}



							strActualResult="Map returned having value = "+ expectedResultMap;
							reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
						}
						catch(Exception ex)
						{
							isEventSuccessful=false;
							writeToLog("getApplicationExpectedMap---" + ex.getMessage());
						}

						return expectedResultMap;
					}




					//This function gets all the User Details available on User Details page.
					//<!--Created By : Ritdhwaj Chandel-->
					//
					//@param 
					//@return Map having User details in the form of key value pair.
					//
					public final Map<String,String> getUserDetailsFromUsersPage()
					{
						strErrMsg_AppLib = "";
						Map<String,String> expectedResultMap= new HashMap<String,String>();
						try
						{	

							//List<WebElement> roleCount=driver.findElements(By.xpath("//input[@type='checkbox']"));
							List<WebElement> roleCount=getelementsList("eleUserRoleBlock_UserDetailsPage");
							System.out.println(roleCount.size());
							System.out.println(roleCount);
							int noOfRoles=roleCount.size()-1;
							System.out.println("roles are their total count of roles:"+noOfRoles);
							//System.out.println("value:"+ driver.findElement(By.xpath("//*[@id='actual-content-body']/div/div[2]/div/div[2]/form/div/div/div[2]/div/div/ul/li[1]")).getAttribute("title"));
							List<String> roles=new ArrayList<String>();
							if(noOfRoles>0){
								for (int _role_index_=1;_role_index_<=noOfRoles;_role_index_++){
									String isRole=driver.findElement(By.xpath("//*[@id='actual-content-body']/div/div[2]/div/div[2]/form/div/div/div[2]/div/div/ul/li["+_role_index_+"]/label/input")).getAttribute("checked");
									//if(driver.findElement(By.xpath("//*[@id='actual-content-body']/div/div[2]/div/div[2]/form/div/div/div[2]/div/div/ul/li["+_role_index_+"]/label")).isSelected()){

									if(isRole !=null){
										String roleName=driver.findElement(By.xpath("//*[@id='actual-content-body']/div/div[2]/div/div[2]/form/div/div/div[2]/div/div/ul/li["+_role_index_+"]")).getAttribute("title");

										/*String role = "\""+roleName+"\"";
									   System.out.println(role);*/
										roles.add(roleName);
									}


								}

							}
							else{
								System.out.println("no roles assigned to user");
							}
							System.out.println(roles);
							Collections.sort(roles,String.CASE_INSENSITIVE_ORDER);
							System.out.println("Sorted Roles"+roles);
							//expectedResultMap.put("roles", Roles);
							String userRoles=roles.toString().replace(" ", "");
							if(userRoles.equals("[]")){
								userRoles=null;
							}
							expectedResultMap.put("roles",userRoles);
							expectedResultMap.put("email", GetTextOrValue(dicOR.get("eleUserName_UserDetailsPage"), "value"));


							//********** Code to get all the values starts from here and if value is empty will hard code some value in it**********

							String firstName=GetTextOrValue(dicOR.get("eleFirstName_UserDetailsPage"), "value");
							expectedResultMap.put("firstName", firstName);
							if(firstName.isEmpty()){
								PerformAction(dicOR.get("eleFirstName_UserDetailsPage"), Action.Type,"firstName");
								firstName=GetTextOrValue(dicOR.get("eleFirstName_UserDetailsPage"), "value");
							}


							String middleName=GetTextOrValue(dicOR.get("eleMiddleName_UserDetailsPage"), "value");
							expectedResultMap.put("middleName", middleName);
							if(middleName.isEmpty()){
								PerformAction(dicOR.get("eleMiddleName_UserDetailsPage"), Action.Type,"middleName");
								middleName=GetTextOrValue(dicOR.get("eleMiddleName_UserDetailsPage"), "value");
							}


							String lastName=GetTextOrValue(dicOR.get("eleLastName_UserDetailsPage"), "value");
							expectedResultMap.put("lastName", lastName);
							if(lastName.isEmpty()){
								PerformAction(dicOR.get("eleLastName_UserDetailsPage"), Action.Type,"lastName");
								lastName=GetTextOrValue(dicOR.get("eleLastName_UserDetailsPage"), "value");
							}


							String notes=GetTextOrValue(dicOR.get("eleNotes_UserDetailsPage"), "value");
							expectedResultMap.put("notes", notes);
							if(notes.isEmpty()){
								PerformAction(dicOR.get("eleNotes_UserDetailsPage"), Action.Type,"notes");
								notes=GetTextOrValue(dicOR.get("eleNotes_UserDetailsPage"), "value");
							}


							String organization=GetTextOrValue(dicOR.get("eleOrganization_UserDetailsPage"), "value");
							expectedResultMap.put("organization", organization);
							if(organization.isEmpty()){
								PerformAction(dicOR.get("eleOrganization_UserDetailsPage"), Action.Type,"organization");
								organization=GetTextOrValue(dicOR.get("eleOrganization_UserDetailsPage"), "value");
							}


							String title=GetTextOrValue(dicOR.get("eleTitle_UserDetailsPage"), "value");
							expectedResultMap.put("title", title);
							if(title.isEmpty()){
								PerformAction(dicOR.get("eleTitle_UserDetailsPage"), Action.Type,"title");
								title=GetTextOrValue(dicOR.get("eleTitle_UserDetailsPage"), "value");
							}


							String location=GetTextOrValue(dicOR.get("eleLocation_UserDetailsPage"), "value");
							expectedResultMap.put("location", location);
							if(location.isEmpty()){
								PerformAction(dicOR.get("eleLocation_UserDetailsPage"), Action.Type,"location");
								location=GetTextOrValue(dicOR.get("eleLocation_UserDetailsPage"), "value");
							}


							String address1=GetTextOrValue(dicOR.get("eleAddress1_UserDetailsPage"), "value");
							expectedResultMap.put("address1", address1);
							if(address1.isEmpty()){
								PerformAction(dicOR.get("eleAddress1_UserDetailsPage"), Action.Type,"address1");
								address1=GetTextOrValue(dicOR.get("eleAddress1_UserDetailsPage"), "value");
							}


							String address2=GetTextOrValue(dicOR.get("eleAddress2_UserDetailsPage"), "value");
							expectedResultMap.put("address2", address2);
							if(address2.isEmpty()){
								PerformAction(dicOR.get("eleAddress2_UserDetailsPage"), Action.Type,"address2");
								address2=GetTextOrValue(dicOR.get("eleAddress2_UserDetailsPage"), "value");
							}


							String city=GetTextOrValue(dicOR.get("eleCity_UserDetailsPage"), "value");
							expectedResultMap.put("city", city);
							if(city.isEmpty()){
								PerformAction(dicOR.get("eleCity_UserDetailsPage"), Action.Type,"city");
								city=GetTextOrValue(dicOR.get("eleCity_UserDetailsPage"), "value");
							}


							String region=GetTextOrValue(dicOR.get("eleRegion_UserDetailsPage"), "value");
							expectedResultMap.put("region", region);
							if(region.isEmpty()){
								PerformAction(dicOR.get("eleRegion_UserDetailsPage"), Action.Type,"region");
								region=GetTextOrValue(dicOR.get("eleRegion_UserDetailsPage"), "value");
							}


							String postalCode=GetTextOrValue(dicOR.get("elePostalCode_UserDetailsPage"), "value");
							expectedResultMap.put("postalCode", postalCode);
							if(postalCode.isEmpty()){
								PerformAction(dicOR.get("elePostalCode_UserDetailsPage"), Action.Type,"postalCode");
								postalCode=GetTextOrValue(dicOR.get("elePostalCode_UserDetailsPage"), "value");
							}


							String country=GetTextOrValue(dicOR.get("eleCountry_UserDetailsPage"), "value");
							expectedResultMap.put("country", country);
							if(country.isEmpty()){
								PerformAction(dicOR.get("eleCountry_UserDetailsPage"), Action.Type,"country");
								country=GetTextOrValue(dicOR.get("eleCountry_UserDetailsPage"), "value");
							}


							String homePhone=GetTextOrValue(dicOR.get("eleHomePhone_UserDetailsPage"), "value");
							expectedResultMap.put("homePhone", homePhone);
							if(homePhone.isEmpty()){
								PerformAction(dicOR.get("eleHomePhone_UserDetailsPage"), Action.Type,"homePhone");
								homePhone=GetTextOrValue(dicOR.get("eleHomePhone_UserDetailsPage"), "value");
							}


							String mobilePhone=GetTextOrValue(dicOR.get("eleMobilePhone_UserDetailsPage"), "value");
							expectedResultMap.put("mobilePhone", mobilePhone);
							if(mobilePhone.isEmpty()){
								PerformAction(dicOR.get("eleMobilePhone_UserDetailsPage"), Action.Type,"mobilePhone");
								mobilePhone=GetTextOrValue(dicOR.get("eleMobilePhone_UserDetailsPage"), "value");
							}


							String officePhone=GetTextOrValue(dicOR.get("eleOfficePhone_UserDetailsPage"), "value");
							expectedResultMap.put("officePhone", officePhone);
							if(officePhone.isEmpty()){
								PerformAction(dicOR.get("eleOfficePhone_UserDetailsPage"), Action.Type,"officePhone");
								officePhone=GetTextOrValue(dicOR.get("eleOfficePhone_UserDetailsPage"), "value");
							}
							// expectedResultMap.put("officePhone", officePhone);

							String faxPhone=GetTextOrValue(dicOR.get("eleFaxPhone_UserDetailsPage"), "value");
							expectedResultMap.put("faxPhone", faxPhone);
							if(faxPhone.isEmpty()){
								PerformAction(dicOR.get("eleFaxPhone_UserDetailsPage"), Action.Type,"faxPhone");
								faxPhone=GetTextOrValue(dicOR.get("eleFaxPhone_UserDetailsPage"), "value");
							}


							boolean isActive=PerformAction(dicOR.get("eleIsActive_UserDetailsPage"),Action.isSelected);

							if(isActive){
								expectedResultMap.put("isActive", "True");
							}
							else{
								expectedResultMap.put("isActive", "False");
							}
							PerformAction("btnSave", Action.Click);

							System.out.println("expected Map is:"+expectedResultMap);	  	  

						}catch (Exception e)
						{
							strErrMsg_AppLib = "getUserDetailsFromUsersPage---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
						}

						return expectedResultMap;
					}

					public final Map<String,String> userDetailsFromUsersPage()
					{
						strErrMsg_AppLib = "";
						Map<String,String> expectedResultMap= new HashMap<String,String>();
						try
						{	

							//List<WebElement> roleCount=driver.findElements(By.xpath("//input[@type='checkbox']"));
							List<WebElement> roleCount=getelementsList("eleUserRoleBlock_UserDetailsPage");
							System.out.println(roleCount.size());
							System.out.println(roleCount);
							int noOfRoles=roleCount.size()-1;
							System.out.println("roles are their total count of roles:"+noOfRoles);
							//System.out.println("value:"+ driver.findElement(By.xpath("//*[@id='actual-content-body']/div/div[2]/div/div[2]/form/div/div/div[2]/div/div/ul/li[1]")).getAttribute("title"));
							List<String> roles=new ArrayList<String>();
							if(noOfRoles>0){
								for (int _role_index_=1;_role_index_<=noOfRoles;_role_index_++){
									String isRole=driver.findElement(By.xpath("//*[@id='actual-content-body']/div/div[2]/div/div[2]/form/div/div/div[2]/div/div/ul/li["+_role_index_+"]/label/input")).getAttribute("checked");
									//if(driver.findElement(By.xpath("//*[@id='actual-content-body']/div/div[2]/div/div[2]/form/div/div/div[2]/div/div/ul/li["+_role_index_+"]/label")).isSelected()){

									if(isRole !=null){
										String roleName=driver.findElement(By.xpath("//*[@id='actual-content-body']/div/div[2]/div/div[2]/form/div/div/div[2]/div/div/ul/li["+_role_index_+"]")).getAttribute("title");

										String role = "\""+roleName+"\"";
										System.out.println(role);
										roles.add(role);
									}


								}
								//System.out.println("roles are their total count of roles:"+roleCount.size());
							}
							else{
								System.out.println("no roles assigned to user");
							}
							System.out.println(roles);
							//expectedResultMap.put("roles", Roles);
							String userRoles=roles.toString().replace(" ", "");
							if(userRoles.equals("[]")){
								userRoles=null;
							}
							expectedResultMap.put("roles",userRoles);
							//expectedResultMap.put("roles", Roles);

							expectedResultMap.put("email", GetTextOrValue(dicOR.get("eleUserName_UserDetailsPage"), "value"));


							//********** Code to get all the values starts from here and if value is empty will hard code some value in it**********

							String firstName=GetTextOrValue(dicOR.get("eleFirstName_UserDetailsPage"), "value");

							expectedResultMap.put("firstName", firstName);
							String middleName=GetTextOrValue(dicOR.get("eleMiddleName_UserDetailsPage"), "value");

							expectedResultMap.put("middleName", middleName);

							String lastName=GetTextOrValue(dicOR.get("eleLastName_UserDetailsPage"), "value");

							expectedResultMap.put("lastName", lastName);

							String notes=GetTextOrValue(dicOR.get("eleNotes_UserDetailsPage"), "value");

							expectedResultMap.put("notes", notes);

							String organization=GetTextOrValue(dicOR.get("eleOrganization_UserDetailsPage"), "value");

							expectedResultMap.put("organization", organization);

							String title=GetTextOrValue(dicOR.get("eleTitle_UserDetailsPage"), "value");

							expectedResultMap.put("title", title);

							String location=GetTextOrValue(dicOR.get("eleLocation_UserDetailsPage"), "value");

							expectedResultMap.put("location", location);

							String address1=GetTextOrValue(dicOR.get("eleAddress1_UserDetailsPage"), "value");

							expectedResultMap.put("address1", address1);

							String address2=GetTextOrValue(dicOR.get("eleAddress2_UserDetailsPage"), "value");

							expectedResultMap.put("address2", address2);

							String city=GetTextOrValue(dicOR.get("eleCity_UserDetailsPage"), "value");

							expectedResultMap.put("city", city);

							String region=GetTextOrValue(dicOR.get("eleRegion_UserDetailsPage"), "value");

							expectedResultMap.put("region", region);

							String postalCode=GetTextOrValue(dicOR.get("elePostalCode_UserDetailsPage"), "value");

							expectedResultMap.put("postalCode", postalCode);

							String country=GetTextOrValue(dicOR.get("eleCountry_UserDetailsPage"), "value");

							expectedResultMap.put("country", country);

							String homePhone=GetTextOrValue(dicOR.get("eleHomePhone_UserDetailsPage"), "value");

							expectedResultMap.put("homePhone", homePhone);

							String mobilePhone=GetTextOrValue(dicOR.get("eleMobilePhone_UserDetailsPage"), "value");

							expectedResultMap.put("mobilePhone", mobilePhone);

							String officePhone=GetTextOrValue(dicOR.get("eleOfficePhone_UserDetailsPage"), "value");

							expectedResultMap.put("officePhone", officePhone);

							String faxPhone=GetTextOrValue(dicOR.get("eleFaxPhone_UserDetailsPage"), "value");

							expectedResultMap.put("faxPhone", faxPhone);

							boolean isActive=PerformAction(dicOR.get("eleIsActive_UserDetailsPage"),Action.isSelected);

							if(isActive){
								expectedResultMap.put("isActive", "True");
							}
							else{
								expectedResultMap.put("isActive", "False");
							}


						}catch (Exception e)
						{
							strErrMsg_AppLib = "getUserDetailsFromUsersPage---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
						}

						return expectedResultMap;
					}
					public Map<String,String> getUserExpectedMap(String id, String serverIP)
					{
						Map<String,String> expectedResultMap= new HashMap<String,String>();
						String strActualResult,strExpectedResult,strStepDescription;
						try
						{
							isEventSuccessful=true;
							isEventSuccessful=PerformAction("browser","navigate","http://"+serverIP+"/#/User/Detail/" + id);
							if(isEventSuccessful)
							{
								strActualResult= "Navigate to User Details page of the app using GUID. ID= " + id +" server used= " +serverIP;
							}
							else
							{
								strActualResult= "PerformAction---Navigate to URL. ID= " + id +" server used= " +serverIP+ strErrMsg_GenLib;
							}
							reporter.ReportStep("Navigates to User details page", "Navigates successfully", strActualResult, isEventSuccessful);

							// Step 4 : Verify user information on user details page.
							strStepDescription = "Getting User details <br> (email,firstName, lastName,middleName,organization,title,address1,address2,City,Region,Postal Code,Country,Mobile Phone,OffcePhone,FaxPhone) from User details page. ";
							strExpectedResult = " A HashMap having all the values should be returned.";
							expectedResultMap = getUserDetailsFromUsersPage();
							strActualResult="Map returned having value = "+ expectedResultMap;
							reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
							expectedResultMap.put("id", id); //adding Id key in the expected Map

						}
						catch(Exception ex)
						{
							isEventSuccessful=false;
							writeToLog("getApplicationExpectedMap---" + ex.getMessage());
						}

						return expectedResultMap;
					}
					public Map<String,String> userExpectedMap(String id, String serverIP)
					{
						Map<String,String> expectedResultMap= new HashMap<String,String>();
						String strActualResult,strExpectedResult,strStepDescription;
						try
						{
							isEventSuccessful=true;
							isEventSuccessful=PerformAction("browser","navigate","http://"+serverIP+"/#/User/Detail/" + id);
							isEventSuccessful=PerformAction(dicOR.get("eleUserName_UserDetailsPage"), Action.WaitForElement,"20");
							if(isEventSuccessful)
							{
								strActualResult= "Navigate to User Details page of the app using GUID. ID= " + id +" server used= " +serverIP;
							}
							else
							{
								strActualResult= "PerformAction---Navigate to URL. ID= " + id +" server used= " +serverIP+ strErrMsg_GenLib;
							}
							reporter.ReportStep("Navigates to User details page", "Navigates successfully", strActualResult, isEventSuccessful);

							// Step 4 : Verify user information on user details page.
							strStepDescription = "Getting User details <br> (email,firstName, lastName,middleName,organization,title,address1,address2,City,Region,Postal Code,Country,Mobile Phone,OffcePhone,FaxPhone) from User details page. ";
							strExpectedResult = " A HashMap having all the values should be returned.";
							expectedResultMap = userDetailsFromUsersPage();
							strActualResult="Map returned having value = "+ expectedResultMap;
							reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
							expectedResultMap.put("id", id); //adding Id key in the expected Map

						}
						catch(Exception ex)
						{
							isEventSuccessful=false;
							writeToLog("getApplicationExpectedMap---" + ex.getMessage());
						}

						return expectedResultMap;
					}

					//<!--Created by : Ritdhwaj Chandel -->
					//Search the user for the given input value
					public final boolean searchUser(String userSearch) throws InterruptedException
					{
						boolean isEventSuccessful = false;
						strErrMsg_AppLib = "";
						try
						{
							isEventSuccessful = PerformAction("eleUserSearch_UsersPage",Action.Type,userSearch);
							//Thread.sleep(2000);
							if (!isEventSuccessful)
							{
								strErrMsg_AppLib="Unable to type in search box";
								throw new RuntimeException(strErrMsg_AppLib);
							}
							//PerformAction("txtNoUser_UsersPage",Action.WaitForElement);
							PerformAction("txtNoUser_UsersPage",Action.WaitForElement);
							if(PerformAction("txtNoUser_UsersPage",Action.isDisplayed)){
								isEventSuccessful = false;
							}
							//PerformAction("eleUserSearch_UsersPage",Action.Type,userSearch);
						}
						catch (RuntimeException e)
						{
							isEventSuccessful = false;
							strErrMsg_AppLib = "searchUser--- " + "Exception occurred at : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
						}
						return isEventSuccessful;
					}




					/*public final boolean createUserWithCompleteDetails(String firstName, String lastName, String password, String userType, boolean enabled)
					{
						return createUser(firstName, lastName, "", password, userType, enabled, false);
					}*/

					/*public final boolean createUser(String firstName, String lastName, String emailID, String password)
					{
						return createUser(firstName, lastName, emailID, password, "testuser", true,false);
					}
					 */


					//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
					//ORIGINAL LINE: public bool createUser(string firstName, string lastName, string emailID, string password, string userType = "testuser", bool enabled = true)
					/** 
					 This function creates a User with complete details



					 <!--Created By : Ritdhwaj Chandel-->
					 @param email id should be passed as parameter
					 @return This function returns boolean value.
					 */
					public final boolean CreateUserWithCompleteDetails(String userName)
					{
						boolean flag = false;
						boolean isEventSuccessful = true;
						strErrMsg_AppLib = "";
						//default value of emailID
						try
						{
							if(PerformAction("btnCreateUser", Action.isDisplayed)){
								flag=PerformAction("btnCreateUser", Action.Click);

								if(flag){
									flag = PerformAction("inpFirstNameCreateUser", Action.WaitForElement,"10");
									if (flag)
									{
										flag = PerformAction("txtLogin", Action.Type, userName);
										if (!flag)
										{
											throw new RuntimeException("Could not type in userName field");
										}

										flag = PerformAction("inpFirstNameCreateUser", Action.Type, "fName1");
										if (!flag)
										{
											throw new RuntimeException("Could not type in First Name field");
										}

										flag = PerformAction("eleMiddleName_UserDetailsPage", Action.Type, "mName");
										if (!flag)
										{
											throw new RuntimeException("Could not type in Middle Name field");
										}

										flag = PerformAction("inpLastNameCreateUser", Action.Type, "lName");
										if (!flag)
										{
											throw new RuntimeException("Could not type in Last Name field");
										}

										//PerformAction("txtLogin", Action.Click);


										flag = PerformAction("eleNotes_UserDetailsPage", Action.Type, "NotesOn");
										if (!flag)
										{
											throw new RuntimeException("Could not type in Notes field");
										}

										flag = PerformAction("eleOrganization_UserDetailsPage", Action.Type, "Organization");
										if (!flag)
										{
											throw new RuntimeException("Could not type in Organization field");
										}
										flag = PerformAction("eleTitle_UserDetailsPage", Action.Type, "Organization");
										if (!flag)
										{
											throw new RuntimeException("Could not type in Title field");
										}
										flag = PerformAction("eleLocation_UserDetailsPage", Action.Type, "location");
										if (!flag)
										{
											throw new RuntimeException("Could not type in location field");
										}
										flag = PerformAction("eleAddress1_UserDetailsPage", Action.Type, "address1");
										if (!flag)
										{
											throw new RuntimeException("Could not type in address1 field");
										}
										flag = PerformAction("eleAddress2_UserDetailsPage", Action.Type, "address2");
										if (!flag)
										{
											throw new RuntimeException("Could not type in address2 field");
										}
										flag = PerformAction("eleCity_UserDetailsPage", Action.Type, "city");
										if (!flag)
										{
											throw new RuntimeException("Could not type in city field");
										}
										flag = PerformAction("eleRegion_UserDetailsPage", Action.Type, "Region");
										if (!flag)
										{
											throw new RuntimeException("Could not type in Region field");
										}
										flag = PerformAction("elePostalCode_UserDetailsPage", Action.Type, "postalCode");
										if (!flag)
										{
											throw new RuntimeException("Could not type in postalCode field");
										}
										flag = PerformAction("eleCountry_UserDetailsPage", Action.Type, "Country");
										if (!flag)
										{
											throw new RuntimeException("Could not type in Country field");
										}
										flag = PerformAction("eleHomePhone_UserDetailsPage", Action.Type, "HomePhone");
										if (!flag)
										{
											throw new RuntimeException("Could not type in HomePhone field");
										}
										flag = PerformAction("eleOfficePhone_UserDetailsPage", Action.Type, "OfficePhone");
										if (!flag)
										{
											throw new RuntimeException("Could not type in OfficePhone field");
										}
										flag = PerformAction("eleMobilePhone_UserDetailsPage", Action.Type, "MobilePhone");
										if (!flag)
										{
											throw new RuntimeException("Could not type in MobilePhone field");
										}
										flag = PerformAction("eleFaxPhone_UserDetailsPage", Action.Type, "FaxPhone");
										if (!flag)
										{
											throw new RuntimeException("Could not type in FaxPhone field");
										}

										flag = PerformAction("txtPassword", Action.Type, "deviceconnect");
										if (!flag)
										{
											throw new RuntimeException("Could not type in Password field");
										}

										//PerformAction("txtConfirmPassword", Action.Click);
										flag = PerformAction("txtConfirmPassword", Action.Type, "deviceconnect");
										if (!flag)
										{
											throw new RuntimeException("Could not type in Confirm Password field");
										}	 

									}

									PerformAction("browser", Action.Scroll, "0");
									if(dicCommon.get("BrowserName").toLowerCase().equals("chrome")||dicCommon.get("BrowserName").toLowerCase().equals("ie"))
									{
										flag = PerformAction("btnSave", Action.Click);
										if(!flag)
										{
											throw new RuntimeException("Could not click on 'Save' button"); 
										}
										flag = PerformAction("eleNotificationRightBottom", Action.WaitForElement);
										if(flag)
										{
											String text = GetTextOrValue(dicOR.get("eleNotificationRightBottomtext"), "text");
											flag = text.contains("User created.");
											if (!flag)
											{
												throw new RuntimeException("'Notification does not read : 'User created.' but : " + text); 
											}
										}
										else
										{
											throw new RuntimeException("'User updated.' notification did not appear on the page.");
										}
									}
									else
									{
										driver.findElement(By.xpath("//button[@class='btn btn-success' and @type='submit']")).click();
										String text = GetTextOrValue(dicOR.get("eleNotificationRightBottomtext"), "text");
										flag = text.contains("User created.");
										if (!flag)
										{
											isEventSuccessful=false;
											throw new RuntimeException("'Notification does not read : 'User created.' but : " + text); 

										}
									}
								}
								else
								{
									throw new RuntimeException("Could not click on 'Create User' button ");
								}

							}
						}
						catch (RuntimeException e)
						{
							strErrMsg_AppLib = "createUser---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
							flag = false;
						}
						return isEventSuccessful;
						//return flag;

					}


					//Open details page of any User according to the options given
					//
					//<!--Created By : Ritdhwaj Chandel-->
					//@param strAppOption It takes values : first and username
					//@param strValue For strAppOption= first, it should be "1"; For strAppOption=username, it should be the user name of the user as displayed on the users table.
					//@return True or False
					//

					public final boolean SelectUser(String strUserOption)
					{
						return SelectUser(strUserOption, "1");
					}

					//ORIGINAL LINE: public bool SelectUser(string strUserOption, string strValue = "1")
					public final boolean SelectUser(String strUserOption, String strValue)
					{
						boolean flag = false;
						String strUserName = "", xpathAppsHolder = "", UserNameLink = "";
						strErrMsg_AppLib = "";
						//WebElement element = null, childElement= null;

						try
						{
							if (PerformAction("eleUsersHeader", Action.WaitForElement))
							{

								switch (strUserOption)
								{
								case "first":
									PerformAction("eleUserNameUserTable", Action.WaitForElement,"2");
									UserNameLink = dicOR.get("eleUserNameUserTable").replace("_User_Index_", "1");

									break;



								case "userame":
									UserNameLink = dicOR.get("eleUserName_UsersPage").replace("__USERNAME__", strValue);
									break;
								}

								//Get app name and put it to dicOutput
								strUserName = GetTextOrValue(UserNameLink, "text");
								if ( ! strUserName.equals(""))
								{
									flag = PerformAction(UserNameLink, Action.Click);
									if (flag)
									{
										flag = PerformAction("eleUserName_UserDetailsPage", Action.WaitForElement);
										if (flag)
										{
											flag = GetTextOrValue("eleUserName_UserDetailsPage", "value").equals(strUserName);
											if (!flag)
											{
												throw new RuntimeException("Correct User details page is not opened for user: " + strUserName + ".");
											}
										}
										else
										{
											throw new RuntimeException("user details page not opened after clicking on user name link for user : " + strUserName + ".");
										}
									}
									else
									{
										throw new RuntimeException("Could not click  on user name link (cards view on users page) for app : " + strUserName + " view.");
									}
								}
								else
								{
									throw new RuntimeException("Could not find any user with the given parameters.");
								}
							}
							else
							{
								throw new RuntimeException("users page is not displayed");
							}
						}
						catch (RuntimeException e)
						{
							flag = false;
							strErrMsg_AppLib = "SelectUser---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
						}
						return flag;
					}
					//This function gets hub details from Device Gateways Page
					//<!--Created By : Ritdhwaj Chandel-->
					//
					//@param hub
					//@return Map having User details in the form of key value pair.
					//   
					public final Map<String,String> hubExpectedMap(String hubNo)
					{


						String strActualResult,strExpectedResult,strStepDescription;
						Map<String,String> expectedResultMap= new HashMap<String,String>();
						APILibrary api = new APILibrary();


						try{

							expectedResultMap = getHubDetailsFromGatewaysPage(hubNo);
							//Gateway Name

							String gatewayName=GetTextOrValue("//h3", "text").split(":")[1];
							expectedResultMap.put("GatewayName", gatewayName);

							//public port
							String publicPort=getAttribute(dicOR.get("pubicPort_Gateways"),"title");
							System.out.println("Value of public port:"+publicPort);

							expectedResultMap.put("publicPort", publicPort.split(":")[1]);

							//
							strActualResult="Map returned having value = "+ expectedResultMap;
							reporter.ReportStep("Get Values from Device Gateways Page", "Values fetched from Device Gateways Page", strActualResult, isEventSuccessful);
						}
						catch(Exception ex)
						{
							isEventSuccessful=false;
							writeToLog("getApplicationExpectedMap---" + ex.getMessage());
						}

						return expectedResultMap;
					}


					//This function gets hub details from Device Gateways Page
					//<!--Created By : Ritdhwaj Chandel-->
					//
					//@param hub
					//@return Map having User details in the form of key value pair.
					//
					public final Map<String,String> getHubDetailsFromGatewaysPage(String hub)
					{
						strErrMsg_AppLib = "";
						Map<String,String> expectedResultMap= new HashMap<String,String>();
						try
						{	

							String HubDescription=GetTextOrValue(dicOR.get("UsbHubDetail_Gateways").replace("*", hub).replace("__columnValue__", "1"), "text");
							expectedResultMap.put("description", HubDescription.split(":")[1]);
							expectedResultMap.put("model", HubDescription.split(":")[0]);
							expectedResultMap.put("name", HubDescription.split(":")[0]);
							String firmWare=GetTextOrValue(dicOR.get("UsbHubDetail_Gateways").replace("*", hub).replace("__columnValue__", "4"), "text");
							expectedResultMap.put("firmware", firmWare);
							String location=GetTextOrValue(dicOR.get("UsbHubDetail_Gateways").replace("*", hub).replace("__columnValue__", "3"), "text");
							expectedResultMap.put("location", location);
							String fiveVoltNow=GetTextOrValue(dicOR.get("UsbHubDetail_Gateways").replace("*", hub).replace("__columnValue__", "5"), "text");
							expectedResultMap.put("fiveVoltNow", fiveVoltNow.replace("v", ""));
							String fiveVoltMax=GetTextOrValue(dicOR.get("UsbHubDetail_Gateways").replace("*", hub).replace("__columnValue__", "7"), "text");
							expectedResultMap.put("fiveVoltMax", fiveVoltMax.replace("v", ""));
							String fiveVoltMin=GetTextOrValue(dicOR.get("UsbHubDetail_Gateways").replace("*", hub).replace("__columnValue__", "6"), "text");
							expectedResultMap.put("fiveVoltMin", fiveVoltMin.replace("v", ""));
							String serialNumber=GetTextOrValue(dicOR.get("UsbHubDetail_Gateways").replace("*", hub).replace("__columnValue__", "6").concat("/../../../../..//h4"), "text");
							expectedResultMap.put("serialNumber", serialNumber.substring(serialNumber.indexOf("(")+1, serialNumber.indexOf(")")));
							System.out.println("expected Map is:"+expectedResultMap);	  	  

						}catch (Exception e)
						{
							strErrMsg_AppLib = "getUserDetailsFromUsersPage---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
						}

						return expectedResultMap;
					}
					//This function gets port details from device gateways page.
					//<!--Created By : Ritdhwaj Chandel-->
					//
					//@param portNo and hubNo
					//@return Map having User details in the form of key value pair.
					//
					public final Map<String,String> portExpectedMap(int portNumber,String hubNo)
					{


						String strActualResult,strExpectedResult,strStepDescription;
						Map<String,String> expectedResultMap= new HashMap<String,String>();
						APILibrary api = new APILibrary();


						try{

							expectedResultMap = getPortDetailsFromGatewaysPage(String.valueOf(portNumber),hubNo);

							strActualResult="Map returned having value = "+ expectedResultMap;
							reporter.ReportStep("Get Values from Device Gateways Page", "Values fetched from Device Gateways Page", strActualResult, isEventSuccessful);
						}
						catch(Exception ex)
						{
							isEventSuccessful=false;
							writeToLog("getApplicationExpectedMap---" + ex.getMessage());
						}

						return expectedResultMap;
					}
					//This function gets port details from device gateways page.
					//<!--Created By : Ritdhwaj Chandel-->
					//
					//@param portNo and hubNo
					//@return Map having User details in the form of key value pair.
					//
					public final Map<String,String> getPortDetailsFromGatewaysPage(String portNo,String hubNo)
					{
						strErrMsg_AppLib = "";
						Map<String,String> expectedResultMap= new HashMap<String,String>();
						try
						{	
							//name,serialNumber,location,usbHubSerialNumber,usbHubPortNumber,milliamps,state,flags,chargingTime,chargingProfile

							String PortNo=GetTextOrValue(dicOR.get("usbPortDetail_Gateways").replace("__hubNo__", hubNo).replace("__portNo__", portNo).replace("*", "2").concat("/span"), "text");
							expectedResultMap.put("usbHubPortNumber", PortNo.split(" ")[0].replace(".",""));
							String name=GetTextOrValue(dicOR.get("usbPortDetail_Gateways").replace("__hubNo__", hubNo).replace("__portNo__", portNo).replace("*", "2").concat("/span/a"), "text");
							expectedResultMap.put("name", name); 
							String serialNumber=GetTextOrValue(dicOR.get("usbPortDetail_Gateways").replace("__hubNo__", hubNo).replace("__portNo__", portNo).replace("*", "3"), "text");
							expectedResultMap.put("serialNumber", serialNumber); 

							String location=GetTextOrValue(dicOR.get("usbPortDetail_Gateways").replace("__hubNo__", hubNo).replace("__portNo__", portNo).replace("*", "4"), "text");
							expectedResultMap.put("location", location);
							String Amperage=GetTextOrValue(dicOR.get("usbPortDetail_Gateways").replace("__hubNo__", hubNo).replace("__portNo__", portNo).replace("*", "6"), "text");
							expectedResultMap.put("milliamps", Amperage.replace("mA", "")); 

							String state=GetSelectedDropDownValue(dicOR.get("usbPortDetail_Gateways").replace("__hubNo__", hubNo).replace("__portNo__", portNo).replace("*", "8").concat("/select"));
							if(state.equals("Data")){
								state="Sync";
							}

							String USBHubSerial=GetTextOrValue(dicOR.get("usbPortDetail_Gateways").replace("__hubNo__", hubNo).replace("__portNo__", portNo).replace("*", "6").concat("/../../../../..//h4"), "text");
							expectedResultMap.put("usbHubSerialNumber", USBHubSerial.substring(USBHubSerial.indexOf("(")+1, USBHubSerial.indexOf(")")));
							expectedResultMap.put("state", state);
							System.out.println("expected Map is:"+expectedResultMap);	  	  

						}catch (Exception e)
						{
							strErrMsg_AppLib = "getUserDetailsFromUsersPage---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
						}

						return expectedResultMap;
					}


					/* Function for Role Management on Users Page.
					 * 
					 */
					public final Boolean setUserRoleSettings(String Role, String [] Entitlement, Boolean [] value)
					{
						boolean flag = false;
						strErrMsg_AppLib = "";
						List<WebElement> roles=new ArrayList<WebElement>();
						List<WebElement> entitlements=new ArrayList<WebElement>();
						List<WebElement> entitlementschkbox=new ArrayList<WebElement>();
						int roleIndex=0;
						try
						{
							flag=PerformAction("RolesLocatorUsersPage",Action.Click);
							if(flag)
							{
								roles=getelementsList("RolesList");
								if(!(roles.isEmpty()))
								{

									for(WebElement role: roles)
									{
										roleIndex++;
										if(role.getText().equals(Role))
										{
											flag=true;
											roles.get(roleIndex-1).click();
											break;
										}
										else
										{
											flag=false;
										}

									}
									if(!flag)
									{
										flag=addNewRole(Role);
									}
								}
								else
								{
									strErrMsg_AppLib = "List of Roles is empty.";
									return false;
								}


								entitlements=getelementsList("entitlements");
								entitlementschkbox=getelementsList(dicOR.get("entitlements").replace("label", "label/input"));
								if(entitlementschkbox.size()<=0)
								{
									flag=false;
									return flag;
								}
								for(int i=0;i<entitlements.size();i++)
								{
									String entitlement=entitlements.get(i).getText();
									for(int j=0;j<Entitlement.length;j++)
									{
										if(entitlement.contains(Entitlement[j]))
										{
											flag=entitlementschkbox.get(i).isSelected();
											if((!flag) && value[j])
											{
												entitlementschkbox.get(i).click();
											}
											if(flag && (!value[j]))
											{
												entitlementschkbox.get(i).click();
											}
										}
									}
								}
								flag=PerformAction("SavebtnRolesPage",Action.Click);
							}
							else
							{
								strErrMsg_AppLib ="Did not find Roles";
							}
						}
						catch(Exception e)
						{
							flag = false;
							strErrMsg_AppLib ="Exception : '"+ e.getMessage();
						}
						return flag;
					}
					/*Function to Add New Role
					 * 
					 */
					public final Boolean addNewRole(String Role)
					{
						boolean flag = false;
						strErrMsg_AppLib = "";
						try
						{
							flag=PerformAction("AddRolebtn",Action.isDisplayed);
							if(!flag)
							{
								flag=navigateToNavBarPages("Users", "eleUsersHeader");
								if(flag)
								{
									flag=PerformAction("RolesLocatorUsersPage",Action.Click);
								}
								else
								{
									strErrMsg_AppLib = "Cannot navigate to Users page";
									return false;
								}
							}
							if(flag)
							{
								PerformAction("AddRolebtn",Action.WaitForElement);
								flag=PerformAction("AddRolebtn",Action.Click);
								if(flag)
								{
									flag=PerformAction("nameRole",Action.Type,Role);
									if(flag)
									{
										flag=PerformAction("AddbtnPopupRole",Action.Click);
										if(flag)
										{
											flag=PerformAction("SavebtnRolesPage",Action.Click);
											if(flag)
											{
												flag=PerformAction("errorPopup",Action.isNotDisplayed);
												if(!flag)
												{
													strErrMsg_AppLib = "Error pop up shown on UI.";
													return flag;
												}
											}
											else
											{
												strErrMsg_AppLib = "Unable to click on Save button.";
											}
											int index=0;
											for(WebElement role: getelementsList("RolesList"))
											{
												index++;
												if(role.getText().equals(Role))
												{
													flag=true;
													role.click();
													break;
												}
											}
										}
										else
										{
											strErrMsg_AppLib = "Did not find Add Role button.";
											flag=false;
										}
									}
									else
									{
										strErrMsg_AppLib = "Did not Role input box.";
										flag=false;
									}
								}
								else
								{
									strErrMsg_AppLib = "Did not find Add button.";
									flag=false;
								}
							}
							else
							{
								strErrMsg_AppLib = "Did not find Add button.";
								flag=false;
							}

						}
						catch(Exception e)
						{
							flag = false;
							strErrMsg_AppLib ="Exception : '"+ e.getMessage();
						}
						return flag;
					}



}