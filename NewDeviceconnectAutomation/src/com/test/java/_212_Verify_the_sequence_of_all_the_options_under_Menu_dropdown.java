package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;


public class _212_Verify_the_sequence_of_all_the_options_under_Menu_dropdown extends ScriptFuncLibrary
{
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";
	private java.util.List<String> runTimeoptionsList = new java.util.ArrayList<String>();
	private int optionsCount =  10;

	public final void testScript()
	{

		//************************************************************//
		// Verify that sequence of options under 'Menu' dropdown 
		//*************************************************************//


		//*******************************//
		//Step 1 - Login to deviceConnect//
		//*******************************//
		isEventSuccessful = Login();

		/////////////////////////////////////////////////////////////////////////////////////////////////
		// Step 2 : Get the options in UserName drop-down in a list, report fail and return if it is empty
		/////////////////////////////////////////////////////////////////////////////////////////////////
		strstepDescription = "Get options from UserName drop-down and put in list 'runTimeoptionsList'";
		runTimeoptionsList = getDropDownOptions("eleMenuHolder");
		System.out.println(runTimeoptionsList);
		try
		{
			if (runTimeoptionsList.size()>0)
			{
				isEventSuccessful=true;
				reporter.ReportStep(strstepDescription, "Options should be added to list successfully.", " Options added to list successfully.", isEventSuccessful);
			}
			else
			{
				isEventSuccessful=false;
				reporter.ReportStep(strstepDescription, "Options should be added to list successfully.", "Options not added to list.", isEventSuccessful);
				return;
			}

			///////////////////////////////////////////////////////////////////////////
			// Step 3 : Verify the option's in UserName drop down.
			///////////////////////////////////////////////////////////////////////////
			strstepDescription = "Verify the option's in UserName dropdown.";
			for(int i= 0; i<runTimeoptionsList.size(); i++ )
			{
				strexpectedResult = dicTestData.get("Option"+i) + " should be the option in 'Menu' dropdown.";
				System.out.println(runTimeoptionsList.get(i));
				System.out.println(dicTestData.get("Option"+i));
				isEventSuccessful = runTimeoptionsList.get(i).equals(dicTestData.get("Option"+i));
				
				if (isEventSuccessful)
				{
					strActualResult = i + "option is : " + runTimeoptionsList.get(i) + ".";
				}
				else
				{
					strActualResult = i+ "option is : '" + runTimeoptionsList.get(i) + " and not"  + dicTestData.get("Option"+i);
				}
				
				reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
			}
		
		}
		catch (RuntimeException e)
		{
			// This is to handle any exception that comes if there is no key in the dictionary when we need to fetch
			reporter.ReportStep("", "", e.getMessage() + "\r\n" + e.getStackTrace(), isEventSuccessful);
		}
	}
	
}
