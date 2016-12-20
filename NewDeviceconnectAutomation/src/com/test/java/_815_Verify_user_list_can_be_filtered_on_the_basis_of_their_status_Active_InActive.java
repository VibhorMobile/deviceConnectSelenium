package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
import com.common.utilities.GenericLibrary.Action;

/*
 * Jira Test Case Id: QA-2024
 */
public class _815_Verify_user_list_can_be_filtered_on_the_basis_of_their_status_Active_InActive extends ScriptFuncLibrary {

	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String  strActualResult = "";



	public final void testScript() throws InterruptedException
	{

		try{
			//*******************************//
			//Step 1 - Login to deviceConnect//
			//*******************************//
			isEventSuccessful = Login();


			//*************************************************************//                     
			// Step 1 : Go to Users Page and Select Active
			//*************************************************************//                     
			GoToUsersPage();
			selectUserStatus("Active");

			//*************************************************************//                     
			// Step 2 : Verify Only Active Users showup with selected filter
			//*************************************************************//     
			int count=getelementCount(dicOR.get("userStatus_UsersPage"));
			for(int i=1;i<=count;i++){
				String Status=GetTextOrValue(dicOR.get("userStatus_UsersPage").replace("*", String.valueOf(i)), "text");
				//System.out.println(Status);
				if(Status.equalsIgnoreCase("inactive")){
					strActualResult="Inactive users are present in active filter";
					isEventSuccessful=false;
					break;

				}
				else{
					strActualResult="Only active users are available inside active filter";
					isEventSuccessful=true;
				}
			}
			reporter.ReportStep("Verify Only active users are present inside active filter", "Only active users should be there when ACTIVE filter selected", strActualResult, isEventSuccessful);;


			//*************************************************************/                     
			// Step4: Select Inactive from filter and verify only inactive are present in  user list
			//**************************************************************/

			selectUserStatus("Inactive");
			count=getelementCount(dicOR.get("userStatus_UsersPage"));
			for(int i=1;i<=count;i++){
				String Status=GetTextOrValue(dicOR.get("userStatus_UsersPage").replace("*", String.valueOf(i)), "text");
				System.out.println(Status);
				if(Status.equalsIgnoreCase("active")){
					strActualResult="Active users are present in Inactive filter";
					isEventSuccessful=false;
					break;

				}
				else{
					strActualResult="Only Inactive users are available inside Inactive filter";
					isEventSuccessful=true;
				}
			}
			reporter.ReportStep("Verify Only active users are present inside active filter", "Only active users should be there when ACTIVE filter selected", strActualResult, isEventSuccessful);;




		}
		catch (RuntimeException e)
		{
			strErrMsg_AppLib = "Sort Users on Basis Active and Inactive Filter--" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		}
	}




}
