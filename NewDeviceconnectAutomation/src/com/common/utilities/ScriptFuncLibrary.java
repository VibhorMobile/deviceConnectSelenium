package com.common.utilities;

import java.awt.List;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary.Action;

public class ScriptFuncLibrary extends ApplicationLibrary
{
	// Objects of different classes :
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();

	// Variables for reporting
	boolean isEventSuccessful;
	protected String strStepDescription, strExpectedResult, strActualResult, labelsXpath = "";

	/** 
	 Navigates the logged in user to reservations index page. It adds a step in the HTML report.

	 <!--Created by : Mandeep Kaur-->
	 <!--Last updated : 9/2/2015 by Mandeep Kaur-->
	 @param isEventSuccessful
	 */
	public final boolean GoToReservationsPage()
	{
		strStepDescription = "Navigate to reservations index page.";
		strExpectedResult = "Reservations index page should be displayed.";
		strActualResult = "";
		isEventSuccessful = false;

		isEventSuccessful = navigateToNavBarPages("Reservations", "eleReservationsHeader");

		if (isEventSuccessful)
			strActualResult = "'Devices' element is displayed successfully on Reservation Index Page";
		else
			strActualResult = strErrMsg_AppLib;

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 <!--Created by : Mandeep Kaur-->

	 @param isEventSuccessful
	 */
	public final boolean GoToCreateReservationPage()
	{
		strStepDescription = "Click on Çreate'button to go to Create Reservation page.";
		strExpectedResult = "Create Reservation page should be opened.";
		isEventSuccessful = false;
		strActualResult = "";
		waitForPageLoaded();
		isEventSuccessful = PerformAction("btnCreateReservation", Action.Click);
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			isEventSuccessful = PerformAction("eleCreateRsrvtnHeader", Action.WaitForElement, "10");
			if (isEventSuccessful)
			{
				strActualResult = "Create Reservation page displayed successfully after clicking on Çreate button.";
			}
			else
			{
				strActualResult = "Create Reservation page not displayed after clicking on Çreate button.";
			}
		}
		else
		{
			strActualResult = "Could not click on Çreate button on Reservations page.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 Navigates the logged in user to devices index page. It adds a step in the HTML report.

	 <!--Created by : Mandeep Kaur-->
	 <!--Last updated : 9/2/2015 by Mandeep Kaur-->
	 @param isEventSuccessful
	 */
	public final boolean GoToDevicesPage()
	{
		strStepDescription = "Go to devices index page.";
		strExpectedResult = "Devices page should be displayed.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = navigateToNavBarPages("Devices", "eleDevicesTab_Devices");
		if (isEventSuccessful)
		{
			strActualResult = "Devices index page opened successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 Navigates the logged in user to Applications index page and adds a step in the HTML report.

	 <!--Created by : Mandeep Kaur-->
	 <!--Last updated : 9/2/2015 by Mandeep Kaur-->
	 @param isEventSuccessful
	 */
	public final boolean GoToApplicationsPage()
	{
		strStepDescription = "Go to applications index page.";
		strExpectedResult = "Applications page should be displayed.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = navigateToNavBarPages("Applications", "eleApplicationsHeader");
		if (isEventSuccessful)
		{
			strActualResult = "Applications index page opened successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 Navigates the logged in user to System page and adds a step in the HTML report.

	 <!--Created by : Mandeep Kaur-->
	 <!--Last updated : 9/2/2015 by Mandeep Kaur-->
	 @param isEventSuccessful
	 */
	public final boolean GoToSystemPage()
	{
		strStepDescription = "Go to system index page.";
		strExpectedResult = "System page should be displayed.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = navigateToNavBarPages("System", "eleSystemHeader");
		if (isEventSuccessful)
		{
			strActualResult = "System page opened successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}


	/** 
	 Navigates the logged in user to detail page of the first device displayed on Devices index page.It adds a step in the HTML report.
	 This function returns the Object array which contains the Boolean and String values. Boolean needs to be added first and then String

	 <!--Created by: Tarun Ahuja-->
	 @param isEventSuccessful and DeviceName
	 */

	public final Object[] GoTofirstDeviceDetailsPage()
	{
		strStepDescription = "Go to device details page of first displayed device.";
		strExpectedResult = "Device details page should be opened.";
		strActualResult = "";
		isEventSuccessful = false;
		String deviceName = "";
		Object[] values = new Object[2];
		isEventSuccessful = SelectDevice("first");
		waitForPageLoaded();
		deviceName = getValueFromDictionary(dicOutput, "selectedDeviceName");

		if (isEventSuccessful)
		{
			strActualResult = "Navigated to device details page of first displayed device : '" + deviceName;
		}
		else
		{
			strActualResult = strErrMsg_AppLib + "\r\n Device name : '" + deviceName + "'. ";
		}
		values[0] = isEventSuccessful;
		values[1]= deviceName;
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return values;
	}




	/** 
	 Connects the first device displayed on the devices index page using CLI and adds a step in the HTML report.

	 <!--Created by : Mandeep Kaur-->
	 @param isEventSuccessful Variable in which the boolean result of function execution is to be stored.
	 @param selectedDevice Variable in which the name of the connected device is to be stored.
	 @param platform Platform of the device to be connected.
	 @param userName Username with which the device needs to be connected.
	 @param password Password of the user.
	 */

	public final Object[] connectFirstDevice_CLI(String platform, String userName)
	{
		return  connectFirstDevice_CLI(platform, userName, "");
	}

	public final Object[] connectFirstDevice_CLI(String platform)
	{
		return connectFirstDevice_CLI(platform, "", "");
	}

	public final Object[] connectFirstDevice_CLI()
	{
		return connectFirstDevice_CLI("android", "", "");
	}


	/* Created by : Tarun Ahuja
	   Returning array Object with Boolean and String Value	 
	 */
	public final Object[] connectFirstDevice_CLI(String platform, String userName, String password)
	{
		strActualResult = "";
		isEventSuccessful = false;
		String executedCommand = "";
		strStepDescription = "Connect to first displayed device.";
		strExpectedResult = "First displayed device should be connect.";

		Object[] CLIResult = ExecuteCLICommand("connect", platform, userName, password);
		isEventSuccessful = (boolean) CLIResult[0];
		String selectedDevice = (String) CLIResult[1];
		executedCommand = getValueFromDictionary(dicOutput, "executedCommand");
		if (isEventSuccessful)
		{
			strActualResult = "Device : '" + selectedDevice + "' connected successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib + "\r\n Command executed : '" + executedCommand + "'.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return new Object[] {isEventSuccessful,selectedDevice } ; 
	}

	/** 
	 Connects the specified device using CLI and adds a step in the HTML report.

	 <!--Created by : Mandeep Kaur-->
	 @param isEventSuccessful Variable in which the boolean result of function execution is to be stored.
	 @param selectedDevice Variable in which the name of the connected device is to be stored.
	 @param platform Platform of the device to be connected.
	 @param userName Username with which the device needs to be connected.
	 @param password Password of the user.
	 @param device Name of the device to be connected. It should be provided and not left empty.
	 */

	public final boolean ConnectSpecificDevice_CLI(  String platform, String userName, String password)
	{
		return isEventSuccessful= ConnectSpecificDevice_CLI( platform, userName, password, "");
	}

	public final boolean ConnectSpecificDevice_CLI(  String platform, String userName)
	{
		return isEventSuccessful=ConnectSpecificDevice_CLI( platform, userName, "", "");
	}

	public final boolean ConnectSpecificDevice_CLI(  String platform)
	{
		return isEventSuccessful=ConnectSpecificDevice_CLI( platform, "", "", "");
	}

	public final boolean ConnectSpecificDevice_CLI( )
	{
		return isEventSuccessful=ConnectSpecificDevice_CLI( "android", "", "", "");
	}

	public final boolean ConnectSpecificDevice_CLI(  String platform, String userName, String password, String device)
	{
		strStepDescription = "Connect to device : '" + device + "'.";
		strExpectedResult = "Device should get connected.";
		strActualResult = "";
		isEventSuccessful = false;
		String selectedDevice = "";
		String executedCommand = "";

		Object [] CLIResult = ExecuteCLICommand("connect", platform, userName, password);
		isEventSuccessful = (boolean) CLIResult[0];
		selectedDevice = (String) CLIResult[1];
		executedCommand = getValueFromDictionary(dicOutput, "executedCommand");
		if (isEventSuccessful)
		{
			strActualResult = "Device : '" + selectedDevice + "' connected successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib + "\r\n Command executed : '" + executedCommand + "'.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 This function is for Releasing Device. It adds a step in the HTML report.
	 <!--Created By : Vinita Mahajan-->

	 @param isEventSuccessful out isEventSuccessful
	 @param devicename Devicename to be released
	 */
	public final boolean ReleaseDevice(String devicename)
	{
		strStepDescription = "Release device : '" + devicename + "'.";
		strExpectedResult = "Device should be released.";
		String selectedDevice = "", executedCommand = "";
		strActualResult = "";
		isEventSuccessful = false;
		Object[] CLIResult = ExecuteCLICommand("release", "", "", "", devicename,"","");
		isEventSuccessful = (boolean) CLIResult[0];
		selectedDevice = (String) CLIResult[1];
		executedCommand = getValueFromDictionary(dicOutput, "executedCommand");
		if (isEventSuccessful)
		{
			strActualResult = "Device : '" + selectedDevice + "' released successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib = "\r\n Command executed : '" + executedCommand + "'.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 This function is for disabling a specific Device by DeviceName. It adds a step in the HTML report.
	 <!--Created By : Vinita Mahajan-->

	 @param isEventSuccessful out isEventSuccessful
	 @param devicename Devicename to be released
	 */
	public final boolean DisableSpecificDeviceCLI(String devicename)
	{
		strStepDescription = "Disable device : '" + devicename + "'.";
		strExpectedResult = "Device should be disabled.";
		String selectedDevice = "", executedCommand = "";
		strActualResult = "";
		Object[] CLIResult = ExecuteCLICommand("disable", "", "", "", devicename,"","");
		isEventSuccessful = (boolean) CLIResult[0];
		selectedDevice = (String) CLIResult[1];
		executedCommand = getValueFromDictionary(dicOutput, "executedCommand");
		if (isEventSuccessful)
		{
			strActualResult = "Device : '" + selectedDevice + "' disabled successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib = "\r\n Command executed : '" + executedCommand + "'.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 This function is for disabling first available device. It adds a step in the HTML report.
	 <!--Created By : Vinita Mahajan-->

	 @param isEventSuccessful out isEventSuccessful
	 @param devicename out devicename of the device which is disabled
	 */
	public final Object[] DisableDeviceCLI()
	{
		strStepDescription = "Disbale first available device.";
		strExpectedResult = "Device should be disabled.";
		String executedCommand = "";
		strActualResult = "";

		Object[] CLIResult = ExecuteCLICommand("disable");
		isEventSuccessful = (boolean) CLIResult[0];
		String devicename = (String) CLIResult[1];
		executedCommand = getValueFromDictionary(dicOutput, "executedCommand");
		if (isEventSuccessful)
		{
			strActualResult = "Device : '" + devicename + "' disbaled successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib = "\r\n Command executed : '" + executedCommand + "'.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return new Object[] {isEventSuccessful,devicename } ;
	}

	/** 
	 This function is for enabling a specific Device by DeviceName. It adds a step in the HTML report.
	 <!--Created By : Vinita Mahajan-->

	 @param isEventSuccessful out isEventSuccessful
	 @param devicename Devicename to be enabled - should be disabled prior
	 */
	public final boolean EnableDevice(String devicename)
	{
		strStepDescription = "Enable device : '" + devicename + "'.";
		strExpectedResult = "Device should be enabled.";
		String selectedDevice = "", executedCommand = "";
		strActualResult = "";
		//		Object CLIResult = ExecuteCLICommand("enable", "", "", "", devicename);

		Object[] CLIResult = ExecuteCLICommand("enable", "", "", "", devicename,"","");

		isEventSuccessful = (boolean) CLIResult[0];
		selectedDevice = (String) CLIResult[1];
		executedCommand = getValueFromDictionary(dicOutput, "executedCommand");
		if (isEventSuccessful)
		{
			strActualResult = "Device : '" + selectedDevice + "' enabled successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib = "\r\n Command executed : '" + executedCommand + "'.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 This function is for login to DC. It adds a step in the HTML report.
	 <!--Created By : Vinita Mahajan-->

	 @param isEventSuccessful out isEventSuccessful
	 @param UserName Username for login : if not specified then takes the default logged in value specified in TestData sheet
	 @param Password Password for login
	 @param NavigatetoDevices Navigated to devices check
	 */

	public final boolean Login(  String UserName, String Password)
	{
		return isEventSuccessful= Login( UserName, Password, true);
	}

	public final boolean Login(  String UserName)
	{
		return isEventSuccessful=Login( UserName, "", true);
	}

	public final boolean Login( )
	{
		return isEventSuccessful=Login( "", "", true);
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public void Login(out bool isEventSuccessful, string UserName = "", string Password = "", bool NavigatetoDevices = true)
	public final boolean Login(String UserName, String Password, boolean NavigatetoDevices)
	{
		strActualResult = "";
		isEventSuccessful = false;
		if (UserName.isEmpty())
		{
			UserName = getValueFromDictionary(dicCommon, "EmailAddress");
			Password = getValueFromDictionary(dicCommon, "Password");
			strStepDescription = "Login to deviceConnect with " + UserName + " and verify Devices page.";
			strExpectedResult = "User " + UserName + " should be logged in and navigated to Devices page.";
		}
		else
		{
			strStepDescription = "Login to deviceConnect with " + UserName + " and verify Devices page.";
			strExpectedResult = "User : " + UserName + " should be logged in and navigated to Devices page.";
		}
		isEventSuccessful = LoginToDC(UserName, Password, NavigatetoDevices);
		if (isEventSuccessful)
		{
			strActualResult = "User : " + UserName + " is logged in to DC.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib + "User : " + UserName + " is not able to login to DC.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 Logs out the logged in User from dC application. It adds a step in the HTML report.
	 <!--Created By : Vinita Mahajan-->

	 @param isEventSuccessful out isEventSuccessful
	 */
	public final boolean LogoutDC()
	{
		strStepDescription = "Logout from deviceConnect web UI.";
		strExpectedResult = "User is logged out of deviceConnect and login page is displayed.";
		isEventSuccessful = false;
		strActualResult = "";
		isEventSuccessful = Logout();
		if (isEventSuccessful)
		{
			strActualResult = "Logout is successful.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib + "Not able to logout.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 Navigates the logged in user to the Users index page and adds a step in the HTML report.

	 <!--Created by : Mandeep Kaur-->
	 <!--Last updated : 9/2/2015 by Mandeep Kaur-->
	 @param isEventSuccessful Variable in which the boolean result of function execution is to be stored.
	 */
	public final boolean GoToUsersPage()
	{
		strStepDescription = "Go to 'Users' page";
		strExpectedResult = "'Users page should be opened.";
		isEventSuccessful = false;
		strActualResult = "";
		isEventSuccessful = navigateToNavBarPages("Users", "eleUsersHeader");
		if (isEventSuccessful)
		{
			waitForPageLoaded();
			if(dicCommon.get("BrowserName").toLowerCase().equals("ie"))
			{
				isEventSuccessful = PerformAction("btnCreateUser", Action.WaitForElement,"20");
			}
			isEventSuccessful = PerformAction("btnCreateUser", Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult = "Users page is opened.";
			}
			else
			{
				strActualResult = "Users page is not opened.";
			}
		}
		else
		{
			strActualResult = "selectFromMenu()-- " + strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 Clicks on 'Create User' button and verifies that create user page is open. It adds a step in the HTML report.

	 <!--Created by : Mandeep Kaur-->
	 @param isEventSuccessful
	 */
	public final boolean GoToCreateUserPage( )
	{
		strStepDescription = "Click on'create' button and verify Create user page is opened.";
		strErrMsg_AppLib = "Create user page should be opened.";
		isEventSuccessful = false;
		strActualResult = "";
		isEventSuccessful = PerformAction("btnCreateUser", Action.Click);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction("inpFirstNameCreateUser", Action.WaitForElement, "5");
			if (isEventSuccessful)
			{
				strActualResult = "Create user page displayed successfully after clicking on 'create user' button on 'Users' page.";
			}
			else
			{
				strActualResult = "create user page not displayed after clicking on 'Create user' button.";
			}
		}
		else
		{
			strActualResult = "Could not click on 'Create user' button.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 Navigates the logged in user to details page of the given user by clicking on 'Edit' button and verifies that user details page is opened. It adds a step in HTML report.

	 <!--Created by : Mandeep Kaur-->
	 @param isEventSuccessful
	 @param strUserID Email ID of the user whose details page is to be opened.
	 */
	public final boolean GoToSpecificUserDetailsPage(  String strUserID)
	{
		strStepDescription = "Go to User details page of the user : '" + strUserID + "'.";
		isEventSuccessful = false;
		strActualResult = "";
		/*		String editBtnXpath = getValueFromDictionary(dicOR, "btnEditUser_GridView");
		editBtnXpath = editBtnXpath.replace("__EMAILID__", strUserID);*/
		waitForPageLoaded();
		String editBtnXpath = dicOR.get("btnEditUser_ListView").replace("__EMAILID__", strUserID);
		PerformAction(editBtnXpath, Action.WaitForElement);
		isEventSuccessful = PerformAction(editBtnXpath, Action.isDisplayed);
		if (isEventSuccessful)
		{
			isEventSuccessful = PerformAction(editBtnXpath, Action.Click);
			PerformAction("browser", Action.Scroll,"0");
			String Username =  getAttribute("eleUsersName_UsersPage", "value");
			//GetTextOrValue("eleUserName_UsersPage", "text");
			//isEventSuccessful = PerformAction("eleUserEditHeader", Action.WaitForElement, "20");

			if (Username.startsWith(strUserID))
			{
				strActualResult = "User details page of user : " + strUserID + " is displayed successfully.";
				isEventSuccessful = true;
			}
			else
			{
				strActualResult = "User details page of user : " + strUserID + " is not displayed ";
				isEventSuccessful = false;
			}
		}
		else
		{
			strActualResult = "Could not click on 'Edit' button for user : '" + strUserID + "'. \r\n Locator used : '" + editBtnXpath + "'.";
		}
		reporter.ReportStep(strStepDescription,  strUserID + " :- User Page should be opend" , strActualResult, isEventSuccessful);
		return isEventSuccessful;

	}

	/** 
	 Navigates the logged in user to details page of first displayed application and verifies that the correct details page is opened. It adds a step in the HTML report.

	 <!--Created by : Tarun Ahuja-->
	 @param isEventSuccessful
	 @param selectedApp Variable in which the name of application selected by code is to be stored.
	 */
	public final Object[] GoToFirstAppDetailsPage()
	{
		strStepDescription = "Go to details page of first application displayed on Applications index page.";
		strExpectedResult = "Application details page of first application should be opened.";
		isEventSuccessful = false;
		strActualResult = "";
		Object[] values = new Object[2];
		isEventSuccessful = SelectApplication("first");
		String selectedApp = getValueFromDictionary(dicOutput, "selectedAppName");
		if (isEventSuccessful)
		{
			strActualResult = "Application details page of application : '" + selectedApp + "' is opened successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib + "\r\n Application name : '" + selectedApp + "'.";
		}

		values[0] = isEventSuccessful;
		values[1]= selectedApp;
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return values;
	}

	/** 
	 Navigates the logged in user to details page of given application and verifies that the correct details page is opened. It adds a step in the HTML report.

	 <!--Created by : Mandeep Kaur-->
	 @param isEventSuccessful
	 @param appName Name of the application(as displayed on applications index page) whose details page is to be opened.
	 */
	public final boolean GoToSpecificAppDetailsPage(  String appName)
	{
		strStepDescription = "Go to details page of '" + appName + "'.";
		strExpectedResult = "Application details page of the given application should be opened.";
		isEventSuccessful = false;
		strActualResult = "";
		String selectedApp = "";
		isEventSuccessful = SelectApplication("appname", appName);
		selectedApp = getValueFromDictionary(dicOutput, "selectedAppName");
		if (isEventSuccessful)
		{
			strActualResult = "Application details page of application : '" + selectedApp + "' is opened successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib + "\r\n Application name : '" + selectedApp + "'.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 This function is to get the device details as per the index. It adds a step in the HTML report.
	 <!--Created By : Vinita Mahajan-->

	 @param isEventSuccessful out isEventSuccessful
	 @param detailValue Details Value : Exoected
	 @param index Index of the device whose value to be fetched
	 @param detailName Details Name to be fetched
	 @param view In which View to verify
	 @param status Status of the device whose detail is to be verified
	 */

	public final Object[] GetDeviceDetails( int index, String detailName, String view)
	{
		return GetDeviceDetails(index, detailName, view, "");  
	}

	public final Object[] GetDeviceDetails(int index, String detailName)
	{
		return GetDeviceDetails(index, detailName, "", "");
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//ORIGINAL LINE: public void GetDeviceDetails(out bool isEventSuccessful, out string detailValue, int index, string detailName, string view = "", string status = "")

	/**
	  <!--Created by: Tarun Ahuja-->
	 @param isEventSuccessful and detailValue
	 */
	public final Object[] GetDeviceDetails(int index, String detailName, String view, String status)
	{
		strStepDescription = "Get the details : '" + detailName + "'.";
		strExpectedResult = "User should be able to get the details : '" + detailName + "'.";
		isEventSuccessful = false;
		Object[] values = new Object[2];
		String detailValue = GetDeviceDetailInGridAndListView(index, detailName, view, status);
		if (!(detailValue.isEmpty()))
		{
			strActualResult = "Not able to get the details value.";
			isEventSuccessful = true;
		}
		else
		{
			strActualResult = strErrMsg_AppLib + "\r\n Not able to get the Detail Name : '" + detailName + "'.";
			isEventSuccessful = false;
		}
		values[0]= isEventSuccessful;
		values[1]=detailValue;
		reporter.ReportStep(strStepDescription,strExpectedResult,strActualResult,isEventSuccessful);
		return values;
	}

	/** 
	 This function is to get the device details as per the deviceName specified. It adds a step in the HTML report.
	 <!--Created By : Vinita Mahajan-->

	 @param isEventSuccessful out isEventSuccessful
	 @param deviceName Get the details as per the devicename specified
	 */
	public final boolean GoToSpecificDeviceDetailsPage(String deviceName)
	{
		strStepDescription = "Go to device details page of " + deviceName + " device.";
		strExpectedResult = "Device details page for " + deviceName + " should be opened.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = SelectDevice("devicename",deviceName);
		deviceName = getValueFromDictionary(dicOutput, "selectedDeviceName");
		if (isEventSuccessful)
		{
			strActualResult = "Navigated to device details page of : '" + deviceName;
		}
		else
		{
			strActualResult = strErrMsg_AppLib + "\r\n Device name : '" + deviceName + "'. ";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	public final boolean GoToSpecificVersionDeviceDetailsPage(String version)
	{
		strStepDescription = "Go to details page of the device with OS version : " + version + ".";
		strExpectedResult = "Corresponding details page of the device should be opened.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = SelectDevice("version", version);
		if(isEventSuccessful)
			strActualResult = "Device details page of device with version : " + version + "opened successfully";
		else
			strActualResult = strErrMsg_AppLib;

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 This function verifies the given device detail name and its value(if given) on device details page. It adds a step in the HTML report.
	 <!--Created By : Vinita Mahajan-->

	 @param isEventSuccessful
	 @param detailName Detail Name to be verified
	 @param detailValue The Expected Value to be verified or else it checks the existence
	 */

	public final boolean VerifyonDeviceDetails(  String detailName)
	{
		return isEventSuccessful= VerifyonDeviceDetails(detailName, "");
	}

	public final boolean VerifyonDeviceDetails(  String detailName, String detailValue)
	{
		strStepDescription = "Verify the detail : " + detailName + " + " + detailValue + " on device details page.";
		strExpectedResult = "Detail : " + detailName + " + " + detailValue  + " should be displayed on device details page.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = VerifyOnDeviceDetailsPage(detailName, detailValue);
		if (!detailName.equals("Battery Status") && detailValue.equals("Not Displayed"))
		{
			detailValue = getValueFromDictionary(dicOutput, "BatteryStatusText");
		}

		else if(detailName.equals("Battery Status") && detailValue.equals("Not Displayed"))
		{
			strActualResult = strErrMsg_AppLib + "\n Detail : " + detailName + "  &  " +  detailValue + "  " + " is not displayed on device details page.";
			isEventSuccessful = true;
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			return true;
		}
		if (isEventSuccessful)
		{
			strActualResult = detailName + " - " + detailValue + " : is displayed for the device on device details page.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib + "\r\n Detail : " + detailName + "  &  " +  detailValue + "is not displayed on device details page..";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 Clicks on List view icon. It does not verify anything. It adds a step in the HTML report.

	 <!--Created by : Mandeep Kaur-->
	 @param isEventSuccessful
	 */
	public final boolean clickListViewIcon( )
	{
		strStepDescription = "Click on 'List View' icon.";
		strExpectedResult = "User should be able to click on 'List view' icon.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = PerformAction("lnkListView", Action.Click);
		if (isEventSuccessful)
		{
			strActualResult = "Successfully clicked on 'List' view icon.";
		}
		else
		{
			strActualResult = "Could not click on 'List' view icon.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 Clicks on Card view icon. It does not verify anything. It adds a step in the HTML report.

	 <!--Created by : Mandeep Kaur-->
	 @param isEventSuccessful
	 */
	public final boolean clickCardViewIcon( )
	{
		strStepDescription = "Click on 'Card View' icon.";
		strExpectedResult = "User should be able to click on 'Card view' icon.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = PerformAction("lnkGridView", Action.Click);
		if (isEventSuccessful)
		{
			strActualResult = "Successfully clicked on 'Card' view icon.";
		}
		else
		{
			strActualResult = "Could not click on 'Card' view icon.";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 Selects the checkbox(s) for platform(s) given as parameter in array and unchecks all other platform checkboxes on Devices index page. It adds a step in the HTML report.

	 <!-- Created by : Mandeep Kaur -->
	 <!-- Last updated : 2/6/2015 by Mandeep Kaur -->
	 @param isEventSuccessful
	 @param devicePlatform Comma separated values of platforms to be selected. eg. "Android,iOS"
	 @param checkOrUncheckAll If the value is specified "uncheckall" then it unchecks all the Platforms checkboxes.
	 */

	public final boolean selectPlatform(String devicePlatform)
	{
		return isEventSuccessful= selectPlatform(devicePlatform, "checkall");
	}

	public final boolean selectPlatform(  String devicePlatform, String checkOrUncheckAll)
	{
		//string Platforms = ArrayToString(devicePlatform);
		strStepDescription = "Select platform(s) checkboxes : " + devicePlatform;
		strExpectedResult = "Checkbox for platform(s) : " + devicePlatform + " should be checked.";
		strActualResult = "";
		isEventSuccessful = false;
		if (checkOrUncheckAll.equals("uncheckall"))
		{
			isEventSuccessful = selectPlatform_DI("Inmjskdjflksdjfl"); //unselects all the checkboxes as no Platform starts with Inmjskdjflksdjfl
		}
		else
		{
			isEventSuccessful = selectPlatform_DI(devicePlatform);
		}
		if (isEventSuccessful)
		{
			strActualResult = "Checkboxes for platforms : " + devicePlatform + " selected successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 Selects the checkbox(s) for platform(s) given as parameter in array and unchecks all other platform checkboxes on Devices index page. It adds a step in the HTML report.

	 <!-- Created by : Mandeep Kaur -->
	 <!-- Last updated : 2/6/2015 by Mandeep Kaur -->
	 @param isEventSuccessful
	 @param deviceStatus Comma separated platforms that need to be selected. eg. "Available,Offline"
	 @param checkOrUncheckAll If the value is specified "uncheckall" then it unchecks all the status checkboxes.
	 */

	/*public final boolean selectStatus(String deviceStatus)
	{
		return isEventSuccessful= selectStatus(deviceStatus, "checkall");
	}*/

	/*public final boolean selectStatus(String deviceStatus, String checkOrUncheckAll)*/
	public final boolean selectStatus(String deviceStatus)
	{
		//string status = ArrayToString(deviceStatus);
		strStepDescription = "Select status(es) checkboxes : " + deviceStatus;
		strExpectedResult = deviceStatus + " checkbox should be selected.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = selectStatus_DI(deviceStatus);
		if (isEventSuccessful)
		{
			strActualResult = "Checkboxes for status(es) : " + deviceStatus + " selected successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	public final boolean VerifyMultipleValof_DI_UI(String status, String val)
	{
		strStepDescription = "Verify number of devices, status and platform of device";
		strExpectedResult = "Device count, status and platform should be shown on UI";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = VerifyMultipleValuesOfProperty_DI(status, val);
		if (isEventSuccessful)
		{
			strActualResult = "Device count, status and platform shown on UI.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 Selects the checkbox in front of the first displayed device. It adds report step to the HTMl report.

	 <!--Created by : Tarun Ahuja-->
	 <!--Last updated : 11/2/2015 by Mandeep Kaur-->
	 @param isEventSuccessful
	 @param selectedDeviceName Variable in which the name of selected(i.e. First displayed) device needs to be stored.
	 */
	public final Object[] selectFirstDeviceChk_DI()
	{
		strStepDescription = "Select checkbox of first displayed device.";
		strExpectedResult = "Checkbox in front of the first displayed device should be selected.";
		strActualResult = "";
		String selectedDeviceName = "";
		isEventSuccessful = false;
		Object[] values = new Object[2];
		waitForPageLoaded();
		isEventSuccessful = PerformAction("chkDeviceName_Devices", Action.SelectCheckbox);
		if (isEventSuccessful)
		{
			selectedDeviceName = GetTextOrValue(dicOR.get("eleDeviceName_ListView").replace("__INDEX__", "1"), "text");
			if (selectedDeviceName == null || selectedDeviceName == " ")
			{
				isEventSuccessful = false;
				strActualResult = "Could not get name of selected device.";
			}
			else
			{
				strActualResult = "Checkbox in front of device '" + selectedDeviceName + "' selected successfully.";
			}
		}
		else
		{
			strActualResult = "Could not select checkbox in front of first displayed device.";
		}
		values[0] = isEventSuccessful;
		values[1] = selectedDeviceName;
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return values;
	}

	/** 
	 Clicks on the select all checkbox on devices index page, verifies that checkboxes in front of all displayed devices are also selected and adds names of the .

	 <!--Created by : Mandeep Kaur-->
	 <!--Last updated : 2/6/2015 by Mandeep Kaur-->
	 @param isEventSuccessful
	 */
	public final Object[] selectAllDevicesCheckbox_DI()
	{
		strStepDescription = "Select the select all checkbox on devices index page.";
		strExpectedResult = "Select All checkbox should get selected.";
		ArrayList devicesSelected = new ArrayList();
		strActualResult = "";
		isEventSuccessful = false;
		//Pair<Boolean, ArrayList<String>> returnedPairvalues;
		try{

			waitForPageLoaded();
			Object[] objresult = selectAllCheckboxAndVerify_DI(); // PerformAction("chkSelectAll_Devices", Action.SelectCheckbox);
			isEventSuccessful = (boolean) objresult[0];
			devicesSelected = (ArrayList) objresult[1];
			if (isEventSuccessful)
			{
				strActualResult = "Select all checkbox selected successfully.";
			}
			else
			{
				strActualResult = "Could not select the select all checkbox." ;
			}

		}catch(Exception e)
		{	
			isEventSuccessful=false;
			strActualResult = "Error occured at Line Number " +e.getStackTrace()[0].getLineNumber() + "\r\n" + e.getMessage();
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return new Object[] {isEventSuccessful,devicesSelected}; 
	}

	/** 
	 Verifies that no element with given xpath has the given text.

	 <!--Created by : Mandeep Kaur 2/6/2015-->
	 @param isEventSuccessful Bool variable in which result of execution is to be stored.
	 @param elementXpath Common xpath of the elements whose text is to be checked.
	 @param text Text on the elements that needs to be verified.
	 <example>eg. VerifyNoelementWithText(out isEventSuccessful, "//button", "Add");</example>
	 */
	public final boolean VerifyNoelementWithGivenText(  String elementXpath, String text)
	{
		strStepDescription = "Verify that no element with xpath '" + elementXpath + "' has text '" + text + "'";
		strExpectedResult = "None of the elements matching locator '" + elementXpath + "' should have the given text.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = verifyElementWithTextNotPresentOnPage(elementXpath, text);
		if (isEventSuccessful)
		{
			strActualResult = "No element with xpath '" + elementXpath + "' has text '" + text + "'";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}


	/** 
	 Refresh and verifies the page refresh.

	 <!--Created by : Tarun Ahuja 7/206/2015-->
	 @param isEventSuccessful Bool variable in which result of execution is to be stored.
	 **/
	public final boolean RefreshPage()
	{
		strStepDescription = "Refresh and verify the page refresh is completed'";
		strExpectedResult = "Page should be refreshed";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = PerformAction("Browser", Action.Refresh);
		if (isEventSuccessful)
		{
			strActualResult = "Page is successfully refreshed";
		}
		else
		{
			strActualResult = "Error occured while refreshing the page";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	  Select columns name on Devices Index page, by clicking on Settings buuton.
	  <!--Created by : Dolly Agarwal-->
	 @param columnsName :- Pass the columns name, which should be displayed on Devices Index page.
	 **/
	public boolean SelectColumn_Devices_SFL(String columnsName){
		strStepDescription = "Verify the selected columns name are displayed on Devices Index page" ;
		strExpectedResult = "Selected columns name are displayed on Devices Index page";
		strActualResult = "";
		isEventSuccessful =	SelectColumn_Devices(columnsName);
		if (isEventSuccessful)
		{
			strActualResult = " Successfully selected columns name are displayed on Devices Index page .";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	  Sorting the Table columns. 
	  <!--Created by : Dolly Agarwal-->
	  @param columnsName :- Pass the table xpath & columns name, which should be sorted on page.
	 **/	
	public final boolean VerifySorting_sfl(String TableContainerXpath, String columnNames)
	{
		strStepDescription = "Verify the columns are clickable on Devices Index page" ;
		strExpectedResult =  "Columns are clickable on Devices Index page";
		strActualResult = "";
		isEventSuccessful =	VerifySorting(TableContainerXpath,columnNames,"");
		if (isEventSuccessful)
		{
			strActualResult = " Successfully Columns are clickable on Devices Index page .";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;

	}
	/** 
  Show Details link on Device details page after clicking on any of the device on Device Index page.. 
  <!--Created by : Dolly Agarwal-->
	 **/	
	public final boolean ShowDetails()
	{
		strStepDescription = "Click on 'Show details...' link to display hidden device details";
		strExpectedResult = "User should be able to click on the 'Show details...' link";
		strActualResult = "";
		waitForPageLoaded();
		isEventSuccessful = PerformAction("lnkShowDetails", Action.isDisplayed);
		if(isEventSuccessful)
		{
			isEventSuccessful = PerformAction("lnkShowDetails", Action.Click);
			waitForPageLoaded();
			if (isEventSuccessful)
			{
				strActualResult = "User is able to click on 'Show details...' link successfully.";
			}
			else
			{
				strActualResult = "Could not click on 'Show details...' link on device details page.";
			}
		}
		else
		{
			strActualResult = "'Show details...' link is not displayed.";
		}
		reporter.ReportStep(strStepDescription,strExpectedResult, strActualResult, isEventSuccessful);

		return isEventSuccessful;

	}
	/** 
  Create the Admin or Tester user on Users page. 
  <!--Created by : Dolly Agarwal-->
  @param columnsName :- Pass the firstnanme,lastname,email-id,password, usertype, active or inactive..
	 **/	
	public final boolean createUser_SFL(String firstName, String lastName, String emailID, String password, String [] userType, boolean enabled)
	{
		isEventSuccessful = false;
		strActualResult = "";
		String strPassedActualResult = "";
		isEventSuccessful = createUser(firstName, lastName, emailID, password, userType, enabled, true);
		if(dicOutput.containsKey("EmailID"))
			emailID = dicOutput.get("EmailID");

		if(userType.length<1 && !enabled)  //If userType is empty & enable is False. 
		{
			strStepDescription = "Create disabled Tester user.";
			strExpectedResult = "A disabled Tester user should be created.";
			strPassedActualResult = "Disabled tester : " + emailID + "created successfully.";
		}
		else if(userType.length>0 && enabled) //If userType is not empty & enable is True. 
		{
			strStepDescription = "Create enabled Administrator user.";
			strExpectedResult = "Enabled Administrator user should be created.";
			strPassedActualResult = "Enabled Administrator: " + emailID + "created successfully.";
		}
		else if(userType.length>0 && !enabled) //If userType is not empty & enable is false. 
		{
			strStepDescription = "Create disabled Administrator user.";
			strExpectedResult = "Disabled Administrator user should be created.";
			strPassedActualResult = "Disabled Administrator: " + emailID + "created successfully.";
		}
		else //If userType is empty & enable is True. 
		{
			strStepDescription = "Create Active/Enabled Tester user.";
			strExpectedResult = "An Active/Enabled Tester user should be created.";
			strPassedActualResult = "Active/Enabled tester : " + emailID + "created successfully.";
		}


		if(isEventSuccessful)
			strActualResult = strPassedActualResult;
		else
			strActualResult = strErrMsg_AppLib;
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		return isEventSuccessful;
	}


	/** 
	 Verfiy the devices given as parameter in array on Device index page. It adds a step in the HTML report.

	 <!-- Created by : Dolly Agarwal -->
	 <!-- Last updated : 12/7/2015 by Dolly Agarwal -->
	 @param isEventSuccessful
	 @param devicesSelected argument that need to be passed . eg. "devices name"
	 */
	public final boolean VerifyDevicesName_sfl(ArrayList<String> devicesSelected)
	{
		strStepDescription = "Verify that all the Diabled devices are added under 'Available' status after enabling.";
		strExpectedResult  = "Disabled devices should be dispalyed under 'Available' devices after enabling.";
		strActualResult = "";
		isEventSuccessful = false;

		isEventSuccessful = VerifyDevicesName(devicesSelected);

		if (isEventSuccessful)
		{
			strActualResult = "Disabled:"+ devicesSelected +  "is displayed under 'Available' status";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/*
   Search device on Device Index Page using filter
   <!-- Created by : Deepak Solanki -->
   @param deviceSearch :- Pass the device  
	 */
	public final boolean searchDevice(String deviceSearch, String detailToBeFound)
	{
		//string status = ArrayToString(deviceStatus);
		boolean flag=false; 
		strStepDescription = "Type "+deviceSearch+" in search text box";
		strExpectedResult =  "device matching with search text "+ deviceSearch+ " should be shown on Device Index page.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = searchDevice_DI(deviceSearch);
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			PerformAction("browser",Action.WaitForPageToLoad);
			String txt=GetDeviceDetailInGridAndListView(1, detailToBeFound, "list", "");
			if(txt.contains(deviceSearch))
				flag=true;

		}
		if(flag)
		{
			strActualResult = "device matching with search text "+ deviceSearch+ " shown on Device Index page.";
		}
		else
		{
			strActualResult = ""+strErrMsg_AppLib;
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, flag);
		return flag;
	}

	/*
   Verify default selection of platform and status checkboxes
   <!-- Created by : Deepak Solanki -->
	 */

	public final boolean verify_default_selected_Status_of_filters()
	{
		strStepDescription = "Verify 'Available', 'In Use' and 'Offline' checkboxes should be selected by default";
		strExpectedResult =  "'Available', 'In Use' and 'Offline' checkboxes selected by default";
		strActualResult = "";
		String [] check={"Available", "In Use", "Offline"};
		String [] uncheck={"Disabled"};
		isEventSuccessful = false;
		isEventSuccessful=verifyselectionof_StatusFilter(check,uncheck,"chkStatus_Devices");
		if(isEventSuccessful)
		{
			strActualResult="'Available', 'In Use' and 'Offline' checkboxes selected by default";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	public final boolean verify_default_selected_Status_of_platform()
	{
		strStepDescription = "Verify 'Android' and 'iOS checkboxes should be selected by default";
		strExpectedResult =  "'Android' and 'iOS checkboxes selected by default";
		strActualResult = "";
		String [] check={"iOS", "Android"};
		String [] uncheck={};
		isEventSuccessful = false;
		isEventSuccessful=verifyselectionof_PlatformFilter(check, uncheck,"chkPlatform_Devices");
		if(isEventSuccessful)
		{
			strActualResult="'Android' and 'iOS checkboxes selected by default";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	public final boolean verifyselectedfilters(String statusFilter)
	{
		strStepDescription = "Verify 'Available' and 'iOS'  filter checkbox should be selected.";
		strExpectedResult =  "'Available' and 'iOS' filter checkbox selected.";
		strActualResult = "";
		String [] check={"Available"};
		String [] uncheck={"In Use", "Offline", "Disabled"};
		String [] checkplatform={"iOS"};
		String [] uncheckplatform={"Android"};
		isEventSuccessful = false;
		boolean flag=false;
		isEventSuccessful=verifyselectionof_StatusFilter(check, uncheck,"chkStatus_Devices");
		flag=verifyselectionof_PlatformFilter(checkplatform, uncheckplatform,"chkPlatform_Devices");
		if(isEventSuccessful && flag)
		{
			strActualResult="'Available' and 'iOS checkboxes selected.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	//Sorting verification on Device Index Page
	public final boolean VerifySorting_in_Order(String TableContainerXpath, String columnNames, String order)
	{
		strActualResult = "";
		isEventSuccessful =	VerifySorting(TableContainerXpath,columnNames, order);
		return isEventSuccessful;
	}


	public final String GetAppGUID()
	{
		String GUID="";
		GUID =	getAppGUID();
		return GUID;
	}

	public final boolean GoToUsagePage(String index)
	{
		strStepDescription = "Navigate to usage page.";
		strExpectedResult = "Usage page should be displayed.";
		strActualResult = "";
		isEventSuccessful = false;

		isEventSuccessful = navigateToNavLinkPages(index, "DatesDrpDwnonUsagePage");

		if (isEventSuccessful)
			strActualResult = "Usage page displayed successfully";
		else
			strActualResult = strErrMsg_AppLib;

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	public final boolean GoToHistoryPage(String index)
	{
		strStepDescription = "Navigate to history page.";
		strExpectedResult = "History page should be displayed.";
		strActualResult = "";
		isEventSuccessful = false;

		isEventSuccessful = navigateToNavLinkPages(index, "DatesDrpDwnonUsagePage");

		if (isEventSuccessful)
			strActualResult = "History page displayed successfully";
		else
			strActualResult = strErrMsg_AppLib;

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	public final boolean GoToReportPage(String index)
	{
		strStepDescription = "Navigate to report page.";
		strExpectedResult = "Report page should be displayed.";
		strActualResult = "";
		isEventSuccessful = false;

		isEventSuccessful = navigateToNavLinkPages(index, "operatinSYSReport");

		if (isEventSuccessful)
			strActualResult = "Report page displayed successfully";
		else
			strActualResult = strErrMsg_AppLib;

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	public final boolean selectEventType(String events)
	{
		//string status = ArrayToString(deviceStatus);
		strStepDescription = "Select events(es) checkboxes : " + events;
		strExpectedResult = events + " checkbox should be selected.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = selectEvents_History(events);
		if (isEventSuccessful)
		{
			strActualResult = "Checkboxes for events(es) : " + events + " selected successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/*
   Edit notes on Device Details Page 
   <!-- Created by : Deepak Solanki -->
	 */
	public final boolean EditAndVerifyNotes(String content)
	{
		boolean flag=false; 
		strStepDescription = "Type "+content+" in edit note text field";
		strExpectedResult =  "Notes field should be modified with content '"+content+"'.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = Add_Modify_Notes(content);
		if(isEventSuccessful)
		{
			String txt=GetTextOrValue("notesLocatorDeviceDetails", "text");
			if(txt.contains(content))
				flag=true;

		}
		if(flag)
		{
			strActualResult = "Notes field modified with content '"+content+"'.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return flag;
	}
	/** 
	 Navigates the logged in user to details page of first displayed application and verifies that the correct details page is opened. It adds a step in the HTML report.

	 <!--Created by : Ritdhwaj Chandel-->
	 @param isEventSuccessful
	 @param selectedUser Variable in which the name of user selected by code is to be stored.
	 */
	public final Object[] GoToFirstUserDetailsPage()
	{
		strStepDescription = "Go to details page of first User displayed on User index page.";
		strExpectedResult = "User details page of first User should be opened.";
		isEventSuccessful = false;
		strActualResult = "";
		Object[] values = new Object[2];
		isEventSuccessful = SelectUser("first");
		String selectedUser =GetTextOrValue(dicOR.get("eleUserName_UserDetailsPage"), "value");

		if (isEventSuccessful)
		{
			strActualResult = "User details page of application : '" + selectedUser + "' is opened successfully.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib + "\r\n User name : '" + selectedUser + "'.";
		}

		values[0] = isEventSuccessful;
		values[1]= selectedUser;
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return values;

	}

	/** 
	 Navigates to iOS Management page and verifies 'iOS System Application Management' header on the page. It adds a step in the HTML report.

	 <!--Created by : Jaishree Patidar-->
	 @param isEventSuccessful
	 @param 
	 */
	public final boolean GoToiOSManagementPage()
	{
		isEventSuccessful = GoToSystemPage();

		strStepDescription = "Click on iOS Management tab.";
		strExpectedResult =  "Successfully clicked on iOS Management tab";
		waitForPageLoaded();
		isEventSuccessful=PerformAction(dicOR.get("lnkiOSMgmnt"), Action.Click);
		waitForPageLoaded();
		if (isEventSuccessful)
		{
			isEventSuccessful=PerformAction(dicOR.get("hdrIOSMgmnt_System"), Action.isDisplayed);
			if (isEventSuccessful)
			{
				strActualResult="Successfully navigates to iOS Management tab.";
			}
			else
				strActualResult="Unable to navigate to iOS Management tab.";
		}
		else
			strActualResult="Unable to click on iOS Management tab.";
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	/** 
	 Selects the checkbox(s) for platform(s) given as parameter in array and unchecks all other platform checkboxes on Application index page. It adds a step in the HTML report.

	 <!-- Created by : Jaishree Patidar -->
	 @param isEventSuccessful
	 @param applicationPlatform Comma separated values of platforms to be selected. eg. "Android,iOS"
	 */
	public final boolean selectPlatform_Application(String devicePlatform)
	{
		String [] platform = devicePlatform.split(",");
		strStepDescription = "Select platform android";
		strExpectedResult = "Only android platform checkbox selected";
		isEventSuccessful=selectCheckboxes_DI(platform, "chkPlatform_Devices");
		if(isEventSuccessful)
		{
			strActualResult="Only "+devicePlatform+" platform checkbox selected";
		}
		else
		{
			strActualResult=""+strErrMsg_AppLib;
		}
		reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, isEventSuccessful);

		return isEventSuccessful;
	}

	public final boolean selectApplicationOption_MultiFunctionDropdown (String appName, String option)
	{
		int iIndex=1;
		if (appName!="")
		{
			iIndex = driver.findElements(By.xpath(dicOR.get("elePrecedingRowSibling_SpecificApp_AppListPage").replace("__APPNAME__", appName))).size();
			iIndex++;
		}
		else
		{
			appName=GetTextOrValue(dicOR.get("eleAppNameAppTable").replace("__APP_INDEX__", "1"),"text");
			System.out.println("Selecting one app-----"+appName);
		}

		isEventSuccessful =  PerformAction(dicOR.get("btnMultiFunctionDropdown_AppListPage").replace("__INDEX__", Integer.toString(iIndex)), Action.Click);
		if(isEventSuccessful)
		{
			isEventSuccessful= PerformAction(dicOR.get("lnkSpecificApp_MultiFuntionalDropdown_Option").replace("__APPNAME__",appName).replace("__LINKTEXT__", option), Action.Exist);
			if (isEventSuccessful)
			{
				isEventSuccessful =  PerformAction(dicOR.get("lnkSpecificApp_MultiFuntionalDropdown_Option").replace("__APPNAME__",appName).replace("__LINKTEXT__", option), Action.Click);
				if(isEventSuccessful)
				{

					isEventSuccessful = PerformAction(dicOR.get("UninstallAppContinuebtn"), Action.Click);
					if(isEventSuccessful)
					{
						strActualResult="Successfully uninstalled 1 instance";
					}
					else
					{
						strActualResult = "Unable to delete 1 instance of Aldiko App.";
					}

				}
				else
				{
					strActualResult = "Could not clicked on Delete button in multi function dropdown";
				}
			}
			else
				strActualResult="No such option available in Multifunctional dropdown. Option= "+option;

		}
		else
		{
			strActualResult = "Could not clicked on multi funtion dropdown arrow button on Application List page";
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);

		return isEventSuccessful;
	}

	//Navigates to Users Role Page

	//<!--Created By : Ritdhwaj Chandel-->
	//

	//@return True or False
	//


	public final boolean GoToUserRolesPage()
	{
		strStepDescription = "Navigate to User Roles.";
		strExpectedResult = "User Roles should be displayed.";
		strActualResult = "";
		isEventSuccessful = false;

		isEventSuccessful = PerformAction(dicOR.get("lnkRoleUsersPage"), Action.Click);
		if(isEventSuccessful){
			isEventSuccessful=PerformAction(dicOR.get("RolesList"), Action.isDisplayed);
			if (isEventSuccessful){
				strActualResult = "Roles page displayed successfully";
			}
			else{
				strActualResult = strErrMsg_AppLib;
			}
		}
		else{
			strActualResult = strErrMsg_AppLib;
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	//Cancel Reservation for any Device for the most recent time slot

	//<!--Created By : Ritdhwaj Chandel-->
	//@param strDeviceName takes value of device whose reservation needs to be canceled

	//@return True or False
	//



	public final boolean CancelReservation(String strDeviceName)
	{
		strStepDescription="Cancel Reservation for device: "+strDeviceName;
		strExpectedResult="Reservation should be canceled";
		boolean flag = false;
		String msgReservationCancel;


		try{
			isEventSuccessful=GoToReservationsPage();
			if(isEventSuccessful){
				//Verify Reservation exists for the device
				isEventSuccessful=PerformAction(dicOR.get("lnkDevice_ResrvationsPage").replace("deviceName", strDeviceName), Action.isDisplayed);
				if(isEventSuccessful){
					//click on cancel button against the device Name
					isEventSuccessful=PerformAction(dicOR.get("btnCancelReservation").replace("deviceName", strDeviceName), Action.Click);
					if(isEventSuccessful){   
						isEventSuccessful=PerformAction(dicOR.get("btnCancelReservationYes"), Action.Click);
						if(isEventSuccessful){
							isEventSuccessful=PerformAction(dicOR.get("eleNotificationBlock"), Action.isDisplayed);
							if(isEventSuccessful){
								msgReservationCancel=GetTextOrValue(dicOR.get("eleMsgNotificationBlock"), "text");
								if(msgReservationCancel.contains("Occurrence cancelled")){
									strActualResult="Reservation Canceled for device"+strDeviceName;
									isEventSuccessful=true;
									flag=true;
								}
								else{
									strActualResult="Reservation Cancel Message for device did not appeared";
								}
							}
							else{
								strActualResult="Notification block for reservation cancellation did  not appeared"+strDeviceName;
							}
						}
						else{
							strActualResult="Unable to click on Yes button on Cancel Reservation Popup";
						}
					}
					else{
						strActualResult="Unable to click on cancel button against device resrvation";
					}
				}
				else{
					strActualResult="No Rservations found for device:"+strDeviceName;
				}

			}
			else{
				strActualResult="Unable to Navigate to Reservations Page";

			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}

		catch (RuntimeException e)
		{
			flag = false;
			strActualResult = "Cancel Reservation---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
		return flag;

	}
	public final boolean EditAndVerifySlot(String slotVal)
	{
		boolean flag=false; 
		strStepDescription = "Type "+slotVal+" in edit slot field";
		strExpectedResult =  "Slot field should be modified with content '"+slotVal+"'.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = Add_Modify_Slot(slotVal);
		if(isEventSuccessful)
		{
			waitForPageLoaded();
			String txt=GetTextOrValue("slotLocatorDeviceDetails", "text");
			if(txt.contains(slotVal))
				flag=true;

		}
		if(flag)
		{
			strActualResult = "Slot field modified with content '"+slotVal+"'.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return flag;
	}

	/*
	   Search user on Users Page using search box
	   <!-- Created by : Deepak Solanki -->
	   @param UserSearch :- Pass the user name  
	 */
	public final boolean searchUser(String userSearch, String detailToBeFound)
	{
		//string status = ArrayToString(deviceStatus);
		boolean flag=false; 
		strStepDescription = "Type "+userSearch+" in search text box";
		strExpectedResult =  "device matching with search text "+ userSearch+ " should be shown on Users page.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = searchDevice_DI(userSearch);
		if(isEventSuccessful)
		{
			PerformAction("",Action.WaitForElement);
			isEventSuccessful=VerifynUsersPage(userSearch, "email", "list");
			if(isEventSuccessful)
				flag=true;
			else
				flag=false;
		}
		if(flag)
		{
			strActualResult = "User matching with search text "+ userSearch+ " shown on Users page.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return flag;
	}

	public final Boolean selectAllUsersCheckbox_Users()
	{
		strStepDescription = "Select the select all checkbox on users page.";
		strExpectedResult = "Select All checkbox should get selected.";
		ArrayList users = new ArrayList();
		strActualResult = "";
		isEventSuccessful = false;
		try{

			isEventSuccessful = selectAllCheckboxAndVerify_Users(); 
			if (isEventSuccessful)
			{
				strActualResult = "Select all checkbox selected successfully.";
			}
			else
			{
				strActualResult = "Could not select the select all checkbox." ;
			}

		}catch(Exception e)
		{	
			isEventSuccessful=false;
			strActualResult = "Error occured at Line Number " +e.getStackTrace()[0].getLineNumber() + "\r\n" + e.getMessage();
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful; 
	}

	/*Function to Install any app from device details uninstalled section to device.
	 * Created by Ritdhwaj Chandel
	 * @Param provide the appName
	 * Returns true or false
	 */
	public final boolean installAppOnDevice(String appName)
	{
		boolean flag = false;
		String uploadMessage;
		strStepDescription = "Install "+appName+ " on device";
		strExpectedResult =  "App installed";
		strActualResult = "";
		try
		{
			flag=PerformAction(dicOR.get("installApp_DeviceDetailsPage").replace("__appName__", appName), Action.Click);
			waitForPageLoaded();
			if(flag)
			{
				WebDriverWait wait = new WebDriverWait(driver, 50);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='alert in fade alert-success']"))); 
				uploadMessage=GetTextOrValue(dicOR.get("uploadMessage"), "text");
				if(uploadMessage.equals("Application installed successfully.")){
					strActualResult="App installed succesfully";
					isEventSuccessful=true;
					flag=true;
				}
				else{
					strActualResult="App did not installed ";
					isEventSuccessful=false;
					flag=false;
				}
			}
			else{
				strActualResult="Unable to click install button for app/App not available in uninstalled section";
				isEventSuccessful=false;

			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		catch(Exception e)
		{
			flag=false;
			strErrMsg_AppLib = "Verify Multiple Instances --- " +e.getMessage();
		}
		return flag;
	}

	/*Function to Edit app Name from Application Details Page
	 * Created by Ritdhwaj Chandel
	 * @Param provide the appName
	 * Returns true or false
	 */
	public final boolean EditAndVerifyAppName(String editedAppName)
	{
		boolean flag=false; 
		strStepDescription = "Edit DeviceName to "+editedAppName;
		strExpectedResult =  "Device Name should be changed to '"+editedAppName+"'.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = editAppName(editedAppName);
		if(isEventSuccessful)
		{
			String txt=GetTextOrValue(dicOR.get("eleAppNameDisplay"),"text");
			if(txt.contains(editedAppName))
				flag=true;

		}
		if(flag)
		{
			strActualResult = "Device Name changed to '"+editedAppName+"'.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return flag;
	}


	public final boolean EditAndVerifyDeviceName(String editedDeviceName)
	{
		boolean flag=false; 
		strStepDescription = "Edit DeviceName to "+"/"+editedDeviceName+"\"'.";
		strExpectedResult =  "Device Name should be changed to '"+"/"+editedDeviceName+"\"'.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = editDeviceName(editedDeviceName);
		if(isEventSuccessful)
		{
			String txt=GetTextOrValue("deviceName_detailsPage", "text");
			if(txt.contains(editedDeviceName))
				flag=true;

		}
		if(flag)
		{
			strActualResult = "Device Name changed to '"+editedDeviceName+"'.";
		}
		else
		{
			strActualResult = strErrMsg_AppLib;
		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return flag;
	}


	/*Function to Select User State CB from Users Page
	 * Created by Ritdhwaj Chandel
	 * @Param provide the State
	 * Returns true or false
	 */
	public final boolean selectUserStatus(String userState)
	{
		boolean flag=false; 
		strStepDescription = "Select "+userState+" CB from users Page";
		strExpectedResult =  userState+" CB from users Page should be selected";
		strActualResult = "";
		isEventSuccessful = false;
		switch (userState.toLowerCase())
		{

		case "active":
			isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__", "Active"), Action.isDisplayed);
			if(isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__", "Active"), Action.SelectCheckbox);
				if(isEventSuccessful){
					isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__", "Inactive"), Action.DeSelectCheckbox);
					if(isEventSuccessful){
						strActualResult="Active Checkbox Selected from Users Page";
					}
					else{
						strActualResult="Active Checkbox Selected from Users Page But Unable to Deselect Inactive";
					}
				}
				else{
					strActualResult="Unable to check Active checkbox from users page.";
				}
			}
			else{
				strActualResult="Active CB not displayed on page.";
			}
			break;
		case "inactive":	
			isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__", "Inactive"), Action.isDisplayed);
			if(isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__", "Inactive"), Action.SelectCheckbox);
				if(isEventSuccessful){
					isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__", "Active"), Action.DeSelectCheckbox);
					if(isEventSuccessful){
						strActualResult="Inactive Checkbox Selected from Users Page";
					}
					else{
						strActualResult="Inactive Checkbox Selected from Users Page But Unable to Deselect active";
					}
				}
				else{
					strActualResult="Unable to check Inctive checkbox from users page.";
				}
			}
			else{
				strActualResult="Inactive CB not displayed on page.";
			}
			break;

		}

		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return flag;
	}

	/*Function to verify if app is installed on device.
	 * Created by Ritdhwaj Chandel
	 * @Param provide the appName
	 * Returns true or false
	 */
	public final boolean verifyAppInstalledOnDevice(String appName)
	{
		boolean flag = false;

		strStepDescription = "Verify "+appName+" is present on installed apps section for device";
		strExpectedResult =  "App is there in installed apps section";
		strActualResult = "";
		try
		{
			flag=PerformAction(dicOR.get("appInstalled_Device").replace("__appName__", appName), Action.isDisplayed);

			if(flag){

				strActualResult=appName+" is present in installed section";
				isEventSuccessful=true;

			}
			else{
				strActualResult=appName+" is not present in installed section";
				isEventSuccessful=false;

			}
			reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		catch(Exception e)
		{
			flag=false;
			strErrMsg_AppLib = "Verify appInstalled --- " +e.getMessage();
		}
		return flag;
	}
	
	/** 
	 Navigates the logged in user to Devices Gateways screen.
	 <!--Created by : Ritdhwaj Chandel-->
	 <!--Date : 26/9/2016 by Ritdhwaj Chandel-->
	 @param isEventSuccessful
	 */
	public final boolean GoToDeviceGatewaysPage()
	{
		strStepDescription = "Navigate to Device Gateways page.";
		strExpectedResult = "Device Gateways page should be displayed.";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful = GoToSystemPage();
		if(isEventSuccessful){
			isEventSuccessful=PerformAction(dicOR.get("lnkDeviceGateways_Systems"), Action.isDisplayed);
			if(isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("lnkDeviceGateways_Systems"), Action.Click);
				if(isEventSuccessful){
					PerformAction(dicOR.get("table_Gateways"), Action.WaitForElement,"10");
					isEventSuccessful=PerformAction(dicOR.get("table_Gateways"), Action.isDisplayed);
					if(isEventSuccessful){
						strActualResult="Devices Gateway Screen displayed";
					}
					else{
						strActualResult="Devices Gateway Screen did not loaded correctly";
					}
				}
				else{
					strActualResult="Unable to click on Device Gateways link from systems page";
				}
			}
			else{
				strActualResult="Devices Gateway link not displayed on systems page";
			}
		}
		else{
			strActualResult="Systems page did not loaded";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}
	/** 
	 Verifies if the given device is connected to specific  mode
	 <!--Created by : Ritdhwaj Chandel-->
	 <!--Date : 26/9/2016 by Ritdhwaj Chandel-->
	 @param portMode-Can be charge,data,off
	 @return true or false
	 */
	public final boolean verifyDevicePortStatus(String portMode,String deviceName,String hubSerial,String portNo)
	{
		strStepDescription = "Verify port mode for "+deviceName+" connected in hub:"+hubSerial+" at port location:"+portNo+" is set to "+portMode;
		strExpectedResult = "Port mode for "+deviceName+" should be set to "+portMode;
		strActualResult = "";
		isEventSuccessful = false;
		if(!(deviceName.equals("")&&portMode.equals("")&&hubSerial.equals("")&&portNo.equals(""))){
			isEventSuccessful=GoToDeviceGatewaysPage();
			if(isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("deviceName_Gateways").replace("__hubID__", hubSerial).replace("__portNo__", portNo).replace("__deviceName__", deviceName), Action.isDisplayed);
				if(isEventSuccessful){
					String ddValue=GetSelectedDropDownValue(dicOR.get("DeviceRow_Gateways").replace("__hubID__", hubSerial).replace("__portNo__", portNo).replace("__deviceName__", deviceName).concat("/td[8]/select"));
					switch (portMode.toLowerCase())
					{
					case "charge":
						if(ddValue.equalsIgnoreCase("charge")){
							isEventSuccessful=PerformAction(dicOR.get("batteryIcon_Gateways").replace("__hubID__", hubSerial).replace("__portNo__", portNo).replace("__deviceName__", deviceName),Action.isDisplayed);
							if(isEventSuccessful){
								String Serial=GetTextOrValue(dicOR.get("DeviceRow_Gateways").replace("__hubID__", hubSerial).replace("__portNo__", portNo).replace("__deviceName__", deviceName).concat("/td[3]"),"text");
								if(Serial.equals("")){
									isEventSuccessful=true;
									strActualResult="Device is connected in charge mode only, value at dropdown is: "+ddValue;
								}
								else{
									isEventSuccessful=false;
									strActualResult="Serial # for the device "+deviceName+" is not null value in table "  + Serial;
								}
							}
							else{
								strActualResult="No Battery icon for the device is present while mode is set to:"+portMode;
							}
						}
						break;
					case "data":
						if(ddValue.equalsIgnoreCase("data")){
							isEventSuccessful=!PerformAction(dicOR.get("batteryIcon_Gateways").replace("__hubID__", hubSerial).replace("__portNo__", portNo).replace("__deviceName__", deviceName),Action.isDisplayed);
							if(isEventSuccessful){
								String Serial=GetTextOrValue(dicOR.get("DeviceRow_Gateways").replace("__hubID__", hubSerial).replace("__portNo__", portNo).replace("__deviceName__", deviceName).concat("/td[3]"),"text");
								if(Serial.equals("")){
									isEventSuccessful=false;
									strActualResult="Device--"+ deviceName+"-- is not connected in data mode value at dropdown is: "+ddValue+" and  serial# is there "+Serial;
								}
								else{
									isEventSuccessful=true;
									strActualResult="Device--"+ deviceName+"-- is connected data mode value at dropdown is: "+ddValue+" and serial# is there "+Serial;
								}
							}
							else{
								strActualResult="Battery icon for the device is present while mode is set to:"+portMode;
							}
						}	
						else{
							isEventSuccessful=false;
							strActualResult="Value in Mode dropdown for device--- "+deviceName+"--- is set to "+portMode;
						}
						break;
					case "off":
						if(ddValue.equalsIgnoreCase("off")){
							isEventSuccessful=!PerformAction(dicOR.get("batteryIcon_Gateways").replace("__hubID__", hubSerial).replace("__portNo__", portNo).replace("__deviceName__", deviceName),Action.isDisplayed);
							if(isEventSuccessful){
								String Serial=GetTextOrValue(dicOR.get("DeviceRow_Gateways").replace("__hubID__", hubSerial).replace("__portNo__", portNo).replace("__deviceName__", deviceName).concat("/td[3]"),"text");
								if(Serial.equals("")){
									isEventSuccessful=true;
									String ampValue=GetTextOrValue(dicOR.get("DeviceRow_Gateways").replace("__hubID__", hubSerial).replace("__portNo__", portNo).replace("__deviceName__", deviceName).concat("/td[6]"),"text");
									if(ampValue.equals("0 mA")){
										String wattValue=GetTextOrValue(dicOR.get("DeviceRow_Gateways").replace("__hubID__", hubSerial).replace("__portNo__", portNo).replace("__deviceName__", deviceName).concat("/td[7]"),"text");
										if(wattValue.equals("0 mW")){
											isEventSuccessful=true;
											strActualResult="Device ---"+deviceName+"--- is connected in: "+ddValue;
										}
										else{
											isEventSuccessful=false;
											strActualResult="Device ---"+deviceName+"--- is connected in: "+ddValue +"value of Wattage is: "+wattValue;
										}
									}
									else{
										isEventSuccessful=false;
										strActualResult="Device ---"+deviceName+"--- is connected in: "+ddValue +"value of Wattage is: "+ampValue;
									}
								}
								else{
									isEventSuccessful=false;
									strActualResult="Device--"+ deviceName+"-- is not connected off mode value at dropdown is: "+ddValue+" and serial# is  "+Serial;
								}
							}
							else{
								strActualResult="Battery icon for the device is present while mode is set to:"+portMode;
							}
						}	
						else{
							isEventSuccessful=false;
							strActualResult="Value in Mode dropdown for device--- "+deviceName+"--- is set to "+portMode;
						}
						break;	
					}
				}
				else{
					isEventSuccessful=false;
					strActualResult="Device ---"+deviceName+"--- is not present in list displayed on Devices Gateways Page.";
				}
			}
			else{
				isEventSuccessful=false;
				strActualResult="Device ---"+deviceName+"--- is not provided in query.";
			}
		}
		else{
			strActualResult="Unable to Navigate to Device Gateways screen";
		}
		reporter.ReportStep("Verify port mode is set to:"+portMode+" for device "+deviceName, "Port mode should be set to:"+portMode+" for device "+deviceName, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}
	/*Function to Delete app from Applications page.
	 * Created by Ritdhwaj Chandel
	 * @Param provide the appName
	 * Returns true or false
	 */
	public final boolean deleteApplication(String appName)
	{
		boolean isEventSuccessful=false; 
		strStepDescription = "Delete "+appName+" app from Applications Page";
		strExpectedResult = appName+" should be deleted from Applications page";
		strActualResult = "";
		isEventSuccessful = false;
		isEventSuccessful=PerformAction(dicOR.get("eleAppName_AppsPage").replace("__APPNAME__", appName), Action.isDisplayed);
		if(isEventSuccessful){
			isEventSuccessful= PerformAction(dicOR.get("appOptionsDropdown_Applications").replace("__APP_Name__", appName), Action.Click);
			if(isEventSuccessful){
				isEventSuccessful= PerformAction(dicOR.get("eleDeleteOption_AppPage"), Action.Click);
				if(isEventSuccessful){
					isEventSuccessful= PerformAction(dicOR.get("DeletAll_Application"), Action.Click);
					if(isEventSuccessful){
						WebDriverWait wait = new WebDriverWait(driver, 10);
						wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='alert in fade alert-success']")));
						/* isEventSuccessful= PerformAction(dicOR.get("eleNotificationBlock"), Action.isDisplayed);
								  if(isEventSuccessful){*/
						String Message= GetTextOrValue(dicOR.get("eleMsgNotificationBlock"), "text");
						if(Message.equals("Application(s) deleted.")){
							strActualResult="Successfully deleted "+appName+" app from Application message received:"+Message;
							isEventSuccessful=true;
						}
						else{
							strActualResult="Unable to delete "+appName+" app from Application message received:"+Message;
							isEventSuccessful=false;
						}
						/* }
								  else{
									  strActualResult="No Notification for app deletion displayed after clicking on delete all";
								  }*/
					}
					else{
						strActualResult="Unable to click on Delete All button";
					}
				}
				else{
					strActualResult="Unable to click on Delete from app dropdown options";
				}
			}
			else{
				strActualResult="Unable to click on App options Dropdown";
			}
		}
		else{
			strActualResult=appName+" App is not Displayed in Applications page";
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}

	
	public final boolean setCustomizedDates_SFL(String StartDate, String EndDate)
	{
		strStepDescription = "Set customizing dates";
		strExpectedResult =  "Dates should be set in start and end date boxes";
		if(isEventSuccessful)
		{
			isEventSuccessful=setCustomizedDates(StartDate,EndDate);
			if(isEventSuccessful)
			{
				strActualResult = "Dates set in start and end date boxes.";
			}
			else
			{
				strActualResult = strErrMsg_AppLib;
			}
		}
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return isEventSuccessful;
	}
	
	 /*
	   Get Userid of the User
	   <!-- Created by : Ritdhwaj Chandel -->
	   */
	 public final String GetUserID()
	   {
		    String UserID="";
		    UserID =getUserID();
			return UserID;
	   }
	 
	
	
	


}