package com.test.java;

import java.io.IOException;

import com.Reporting.Reporter;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;
/*
 * Jira Test Case Id: QA-1795
 */
public class _714_User_Roles_Verify_the_functionality_of_the_Confirm_Navigation_dialog extends ScriptFuncLibrary {

	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strExpectedResult = "";
	private String strActualResult = "";
	String emailID,deviceName,deviceStatus,reservedByName,deviceStatus_DI,reservedByName_DI,recurringIcon;

	




	public final void testScript() throws InterruptedException, IOException
	{
		try{
			String EmailAddress = dicCommon.get("testerEmailAddress");
			String Password = dicCommon.get("testerPassword");

			//*************************************************************//                     
			// Step 1 : login to deviceConnect with admin user.
			//*************************************************************//  


			isEventSuccessful = Login();


			//*************************************************************/                     
			// Step 2 : Go to Users Page and Navigate to roles
			//*************************************************************//
			isEventSuccessful=GoToUsersPage();
			if(isEventSuccessful){
				GoToUserRolesPage();
			}
			else
			{
				strActualResult = "Navigate to Users Roles---" + strErrMsg_AppLib;
			}

			//*************************************************************/                     
			// Step 3 : Go to tester Role and edit Application area entitleMent
			//*************************************************************//
			strstepDescription="Go to tester area and edit Application area entitlement";
			strExpectedResult="Detail Edited successfully";
			isEventSuccessful=PerformAction(dicOR.get("testerRole_RolesPage"), Action.Click);
			if(isEventSuccessful){
				isEventSuccessful=PerformAction(dicOR.get("eleApplicationAreaCB_RolesPage"), Action.Click);
				if(isEventSuccessful){
					strActualResult="edited Application Area entitlement for tester role";
				}
				else{
					strActualResult="Unable to edit Appliaction area entitlement";
				}
			}
			else{
				strActualResult="Tester role not found";
			}
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
			//*************************************************************/                     
			// Step 3 : Navigate to Some Other Page and Verify popup appeared
			//*************************************************************//
			strstepDescription="Navigate to Devices Page and verify Save changes Dialogue Appears";
			strExpectedResult="Save Changes Dialogue appeared";
			navigateToNavBarPages("Devices", "eleDevicesTab_Devices");
			//Thread.sleep(5000);
			isEventSuccessful=PerformAction(dicOR.get("SaveChangesDialogue_RolesPage"), Action.isDisplayed);
			if(!isEventSuccessful){
				strActualResult="Navigated to Devices page no popup appeared";
			}
			else{
				
					strActualResult="Save Changes Dialogue Displyaed";
					reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
					//*************************************************************/                     
					// Step 4 : Click on cancel and Verify User is in same page
					//*************************************************************//
					strstepDescription="Click on Cancel and Verify User remains on Same Page";
					strExpectedResult="Clicked on Cancel and User Remains on Same Page";
					isEventSuccessful=PerformAction(dicOR.get("btnCancel_SaveChangesDialogue"), Action.Click);
					if(isEventSuccessful){
						isEventSuccessful=PerformAction(dicOR.get("RolesList"), Action.isDisplayed);
						if (isEventSuccessful){
							strActualResult = "User is on Same Roles Page";
						}
						else{
							strActualResult = "User Navigated to Some Other Page";
						}
						reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
					
						//*************************************************************/                     
						// Step 3 : Navigate to Some Other Page and Verify X icon from Save Changes Dialogue
						//*************************************************************//
						strstepDescription="Click on X and Verify User is in same page";
						strExpectedResult="Clicked on X and user is in same page";
						isEventSuccessful=navigateToNavBarPages("Devices", "eleDevicesTab_Devices");
						if(isEventSuccessful){
							strActualResult="Navigated to Devices page no popup appeared";
						}
						else{
							isEventSuccessful=PerformAction(dicOR.get("btnX_SaveChangesDialogue"), Action.Click);
							isEventSuccessful=PerformAction(dicOR.get("RolesList"), Action.isDisplayed);
							if (isEventSuccessful){
								strActualResult = "User is on Same Roles Page";
							}
							else{
								strActualResult = "User Navigated to Some Other Page";
							}
							reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
							//*************************************************************/                     
							// Step 3 : Navigate to Some Other Page and Verify Leave Discard Changes button
							//*************************************************************//
							strstepDescription="Click on Leave Button and Verify User is navigated to devices page";
							strExpectedResult="Clicked on Leave and User is in Devices Page";
							isEventSuccessful=navigateToNavBarPages("Devices", "eleDevicesTab_Devices");
							if(isEventSuccessful){
								strActualResult="Navigated to Devices page";
							}
							else{
								
									strActualResult = "User Not Navigated to devices Page";
								}
								reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
						}
						
					}
				}
			
			reporter.ReportStep(strstepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Verify Error Message--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}


}
