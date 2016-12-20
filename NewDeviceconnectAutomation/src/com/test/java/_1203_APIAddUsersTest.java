package com.test.java;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.lang.Math.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.Reporting.APIReporter;
import com.Reporting.Reporter;
import com.common.utilities.APILibrary;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
import com.sun.jna.platform.win32.COM.Dispatch.ByReference;

import java.util.ArrayList;
import java.util.Date;

import org.openqa.jetty.jetty.servlet.Default;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * Author : Ritdhwaj Chandel
 * Creation Date: 11 May 2016
 * Last Modified Date: NA
 * Pre-requisite:Tester Role should be their in Application
 * Admin user can only be used
 */
public class _1203_APIAddUsersTest extends ScriptFuncLibrary{

	private GenericLibrary genericLibrary = new GenericLibrary();
	private APILibrary apiMethods = new APILibrary();
	private APIReporter apiReporter = new APIReporter();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful;	
	String userId;
	//String userName;
	//Boolean value;
	//Object[] values = new Object[2];
	public Map<String,String> expectedResultMap= new HashMap<String,String>();
	public Map<String,String> actualResultMap = new HashMap<String,String>();
	boolean isEventSuccessfulRecordCount;
	// Creating an array having all the model properties available for Application component


	public final void testScript()
	{
		try{
			//Adding heading for the Script in API Overall Comparison HTML file
			apiReporter.apiScriptHeading("Add Users");


			String serverIP=dicCommon.get("ApplicationURL");



			//*********************** Case1-  Adding User with only UserName and Password  *********************************************************


			System.out.println("*************************** Case1-Starts Here(Adding User with only Mandatory Fields*************************");
			apiReporter.apiAddBlock("Adding a User with Only userName and Password");
			String email="addUser@email.com"+apiMethods.getDateTime();
			String password="Password";

			String component= "User", parameterList="verb,wait", valueList="add,true", dataTag="{Email:\\\""+email+"\\\""+","+"Password:\\\""+password+"\\\""+"}"; //declaration and intialization of curl command varaiables.
			String jsonResponse; //declaration of string to capture JSON Response of the API request.
			String command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json", dataTag);	//Creates a curl command with component, parameterList and valueList.

			//adding heading in APIOverallComparison HTML for the curl command created

			apiReporter.apiNewHeading(command);
			jsonResponse=apiMethods.execCurlCmd(command);
			apiReporter.apiNewHeading(jsonResponse);
			System.out.println("json response is:"+jsonResponse);

			if (jsonResponse.equals("{\"isSuccess\":true}")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
			{
				strActualResult="As expected getting success message.";
				isEventSuccessful=true;
				System.out.println("pass");
			}

			else
			{
				strActualResult= "Not getting success message. Hence failing stop test. <br> JSON Response =" +jsonResponse;
				isEventSuccessful=false;
			}
			reporter.ReportStep("Verifying success in JSON reponse", "Should get success message in JSON Response",strActualResult ,isEventSuccessful );
			isEventSuccessful = Login();
			//*******Code to get the User details Page ********
			isEventSuccessful = GoToUsersPage();
			isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__","Active"), Action.SelectCheckbox);
			isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__","Inactive"), Action.SelectCheckbox);

			isEventSuccessful=searchUser(email);
			if(!isEventSuccessful){
				strActualResult="User is not their in DC UI";
				apiReporter.apiErrorBlock("User is not present at DC UI with Email:"+email);
			}
			else{
				strActualResult="User is available in DC UI";
				reporter.ReportStep("Verifying if newly added user is their in DC UI", "User Should be their in UI", strActualResult, isEventSuccessful);
				GoToFirstUserDetailsPage();
				userId=GetUserID(); //Gets the Id of the User whose  details page is displayed

				expectedResultMap=userExpectedMap(userId, serverIP);
				apiReporter.apiAddBlock("Details at DC UI are:"+expectedResultMap);

				if(expectedResultMap.get("email").equals(email) && expectedResultMap.get("roles").equals("["+"\""+"Tester"+"\""+"]")){
					strActualResult="User details from DC UI matched with details provided in API req.";
					isEventSuccessful=true;
					apiReporter.apiPassBlock("Details provided in API request and DC UI Matches");
				}
				else{
					apiReporter.apiErrorBlock("Details provided in API request and DC UI does not Match");
				}



				//************************************************ Case 2- Code to Add User with All Details Starts Here***********************************

				System.out.println("*************************** Case2-Starts Here(Adding User with all the Fields*************************");
				apiReporter.apiAddBlock("Adding a User with all the fields");
				email="addUser@email.com"+apiMethods.getDateTime();
				password="Password";

				component= "User";
				parameterList="verb,wait";
				valueList="add,true";
				//Providing values for all the mandatory fields....
				String firstName="firstName"+apiMethods.getDateTime();
				String middleName="middleName"+apiMethods.getDateTime();
				String isActive="True";
				String lastName="lastName"+apiMethods.getDateTime();
				String notes="notes"+apiMethods.getDateTime();
				String title="title"+apiMethods.getDateTime();
				String organization="organization"+apiMethods.getDateTime();
				String address2="address2"+apiMethods.getDateTime();
				String region="region"+apiMethods.getDateTime();
				String location="location"+apiMethods.getDateTime();
				String address1="address1"+apiMethods.getDateTime();
				String city="city"+apiMethods.getDateTime();
				String postalCode="postalCode"+apiMethods.getDateTime();
				String mobilePhone="mobilePhone"+apiMethods.getDateTime();
				String country="country"+apiMethods.getDateTime();
				String homePhone="homePhone"+apiMethods.getDateTime();
				String officePhone="officePhone"+apiMethods.getDateTime();
				String faxPhone="faxPhone"+apiMethods.getDateTime();
				String roles="Administrator";

				dataTag="{Email:\\\""+email+"\\\""+","+"Password:\\\""+password+"\\\""+","+"firstName:\\\""+firstName+"\\\""
						+","+"lastName:\\\""+lastName+"\\\""+","+"middleName:\\\""+middleName+"\\\""+","+"isActive:\\\""+isActive+"\\\""
						+","+"notes:\\\""+notes+"\\\"" +","+"organization:\\\""+organization+"\\\""+","+"title:\\\""+title+"\\\""+","
						+"location:\\\""+location+"\\\""+","+"address1:\\\""+address1+"\\\""+","+"address2:\\\""+address2+"\\\""+","
						+"city:\\\""+city+"\\\""+","+"region:\\\""+region+"\\\""+","+"postalCode:\\\""+postalCode+"\\\""+","
						+"country:\\\""+country+"\\\""+","+"homephone:\\\""+homePhone+"\\\""+","+"mobilePhone:\\\""+mobilePhone+"\\\""
						+","+"officePhone:\\\""+officePhone+"\\\""+","+"faxPhone:\\\""+faxPhone+"\\\""+","+"roles:[\\\""+roles+"\\\"]"+"}"; //declaration and intialization of curl command varaiables.
				//Creating curl command
				command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json", dataTag);	//Creates a curl command with component, parameterList and valueList.

				//adding heading in APIOverallComparison HTML for the curl command created

				apiReporter.apiNewHeading(command);
				jsonResponse=apiMethods.execCurlCmd(command);
				apiReporter.apiNewHeading(jsonResponse);
				System.out.println("json response is:"+jsonResponse);

				if (jsonResponse.equals("{\"isSuccess\":true}")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
				{
					strActualResult="As expected getting success message.";
					isEventSuccessful=true;
					System.out.println("pass");
				}

				else
				{
					strActualResult= "Not getting success message. Hence failing stop test. <br> JSON Response =" +jsonResponse;
					isEventSuccessful=false;
				}
				reporter.ReportStep("Verifying success in JSON reponse", "Should get success message in JSON Response",strActualResult ,isEventSuccessful );
				//isEventSuccessful = Login();
				//*******Code to get the User details Page ********
				isEventSuccessful = GoToUsersPage();
				isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__","Active"), Action.SelectCheckbox);
				isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__","Inactive"), Action.SelectCheckbox);
				//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='userListTable table data-grid user-list-table']/tbody/tr[1]/td[1]"))); 

				isEventSuccessful=searchUser(email);
				if(!isEventSuccessful){
					strActualResult="User is not their in DC UI";
					apiReporter.apiErrorBlock("User is not present at DC UI with Email:"+email);
				}
				else{
					strActualResult="User is available in DC UI";
					reporter.ReportStep("Verifying if newly added user is their in DC UI", "User Should be their in UI", strActualResult, isEventSuccessful);
					GoToFirstUserDetailsPage();
					userId=GetUserID(); //Gets the Id of the User whose  details page is displayed

					expectedResultMap=userExpectedMap(userId, serverIP);
					apiReporter.apiAddBlock("Details at DC UI are:"+expectedResultMap);

					if(expectedResultMap.get("email").equals(email) && expectedResultMap.get("roles").equals("["+"\""+roles+"\""+"]") 
							&& expectedResultMap.get("firstName").equals(firstName)&& expectedResultMap.get("lastName").equals(lastName)
							&& expectedResultMap.get("middleName").equals(middleName)&& expectedResultMap.get("isActive").equals(isActive)
							&& expectedResultMap.get("notes").equals(notes)&& expectedResultMap.get("organization").equals(organization)
							&& expectedResultMap.get("title").equals(title)&& expectedResultMap.get("location").equals(location)
							&& expectedResultMap.get("address1").equals(address1)&& expectedResultMap.get("address2").equals(address2)
							&& expectedResultMap.get("city").equals(city)&& expectedResultMap.get("region").equals(region)
							&& expectedResultMap.get("postalCode").equals(postalCode)&& expectedResultMap.get("country").equals(country)
							&& expectedResultMap.get("officePhone").equals(officePhone)&& expectedResultMap.get("homePhone").equals(homePhone)
							&& expectedResultMap.get("mobilePhone").equals(mobilePhone)&& expectedResultMap.get("faxPhone").equals(faxPhone)){
						strActualResult="User details from DC UI matched with details provided in API req.";
						isEventSuccessful=true;
						apiReporter.apiPassBlock("Details provided in API request and DC UI Matches");
					}
					else{
						apiReporter.apiErrorBlock("Details provided in API request and DC UI does not Match");
					}



					//************************************************Case3:Code to Add User with Multiple Roles Starts Here***********************************

					System.out.println("*************************** Case3-Starts Here(Adding User with multiple roles*************************");
					apiReporter.apiAddBlock("Adding a User with multiple roles");
					email="addUser@email.com"+apiMethods.getDateTime();
					password="Password";

					component= "User";
					parameterList="verb,wait";
					valueList="add,true";

					roles="Administrator"+"\\\""+","+"\\\""+"Tester";

					dataTag="{Email:\\\""+email+"\\\""+","+"Password:\\\""+password+"\\\""+","+"roles:[\\\""+roles+"\\\"]"+"}"; //declaration and intialization of curl command varaiables.
					//declaration of string to capture JSON Response of the API request.
					command=apiMethods.createCurlCommand(component, parameterList, valueList, "POST", "Content-Type: application/json", dataTag);	//Creates a curl command with component, parameterList and valueList.

					//adding heading in APIOverallComparison HTML for the curl command created

					apiReporter.apiNewHeading(command);
					jsonResponse=apiMethods.execCurlCmd(command);
					apiReporter.apiNewHeading(jsonResponse);
					System.out.println("json response is:"+jsonResponse);

					if (jsonResponse.equals("{\"isSuccess\":true}")) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
					{
						strActualResult="As expected getting success message.";
						isEventSuccessful=true;
						System.out.println("pass");
					}

					else
					{
						strActualResult= "Not getting success message. Hence failing stop test. <br> JSON Response =" +jsonResponse;
						isEventSuccessful=false;
					}
					reporter.ReportStep("Verifying success in JSON reponse", "Should get success message in JSON Response",strActualResult ,isEventSuccessful );
					//isEventSuccessful = Login();
					//*******Code to get the User details Page ********
					isEventSuccessful = GoToUsersPage();
					isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__","Active"), Action.SelectCheckbox);
					isEventSuccessful=PerformAction(dicOR.get("CB_UserState_Users").replace("__User_State__","Inactive"), Action.SelectCheckbox);
					//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='userListTable table data-grid user-list-table']/tbody/tr[1]/td[1]"))); 
					isEventSuccessful=searchUser(email);
					if(!isEventSuccessful){
						strActualResult="User is not their in DC UI";
						apiReporter.apiErrorBlock("User is not present at DC UI with Email:"+email);
					}
					else{
						strActualResult="User is available in DC UI";
						reporter.ReportStep("Verifying if newly added user is their in DC UI", "User Should be their in UI", strActualResult, isEventSuccessful);
						GoToFirstUserDetailsPage();
						userId=GetUserID(); //Gets the Id of the User whose  details page is displayed

						expectedResultMap=userExpectedMap(userId, serverIP);
						apiReporter.apiAddBlock("Details at DC UI are:"+expectedResultMap);

						if(expectedResultMap.get("email").equals(email) && expectedResultMap.get("roles").equals("["+"\""+roles.replace("\\", "")+"\""+"]") ){
							strActualResult="User details from DC UI matched with details provided in API req.";
							isEventSuccessful=true;
							apiReporter.apiPassBlock("Details provided in API request and DC UI Matches");
						}
						else{
							apiReporter.apiErrorBlock("Details provided in API request and DC UI does not Match");
						}

					}





				}

			}
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


