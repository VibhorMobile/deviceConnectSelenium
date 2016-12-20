package com.test.java;

import com.Reporting.Reporter;
import com.common.utilities.GenericLibrary;
import com.common.utilities.ScriptFuncLibrary;
/*
 * Jira Test Case Id: QA-2192
 */
public class _196_Verify_testeruser_will_see_disabled_devices_and_not_able_to_perform_any_of_the_operations_on_disabled_devices extends  ScriptFuncLibrary
{   		 
	private GenericLibrary genericLibrary = new GenericLibrary();
	private Reporter reporter = new Reporter();
	private boolean isEventSuccessful = false;
	private String strstepDescription = "";
	private String strexpectedResult = "";
	private String strActualResult = "";     
	private String srcAttribute="";
	private String numberOfDevices = "";
	private int devicesCount =0;
	private String strErrorIndex = "";
	private String btnDisalbedValue= "";

	public final void testScript()
	{
		
		String disabledDevicesCount = "";

		//********************************************************//
		//******   Step 1 - Login to deviceConnect   *************//
		//********************************************************//
		String EmailAddress = dicTestData.get("EmailAddress");
		String Password = dicTestData.get("Password");
		isEventSuccessful = Login(EmailAddress,Password);
				
		//*************************************************************//
		// Step 2: Verify that test user is not able to view disabled devices.
		//*************************************************************//
		strstepDescription = "Verify that test user is able to view disabled devices.";
		strexpectedResult = "Disabled devices should be visible to the test user.";
		isEventSuccessful = selectStatus_DI("Disabled");
		if (isEventSuccessful)
		{
		   strActualResult = "Test user is able to view disabled devices.";
		}
		else
		{
		   strActualResult = "Test user is not able to view disabled devices.";
		}
		
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//*************************************************************//
		// Step : Make it devices 'Disabled', if not been displayed.
		//*************************************************************//

		if (!isEventSuccessful) 
		{
			isEventSuccessful = DisplayDisabledDevices();
		}
		

		//*************************************************************************************//
		// Step 3: Verify TesterUser not able to click on connect button for any of the devices.
		//************************************************************************************//
		strstepDescription = "Verify Connect button is disabled for TesterUser.";
		strexpectedResult = "TesterUser should not be able to click on connect button for any of the devices.";
		try
		{
		devicesCount = getelementCount("eleDevicesHolderListView") - 1; 
		for (int i = 1; i <= devicesCount; i++) // For each device, verify connect button is not clickable.
        {
	        isEventSuccessful =((!PerformAction("btnConnect_ListView"+ "[" + i + "]", Action.Click)) && (!PerformAction(("//h3[text()='__EXPECTED_HEADER__']").replace("__EXPECTED_HEADER__", "Device Connect"), Action.isDisplayed)));
	        srcAttribute = getAttribute(dicOR.get("btnConnect_ListView")+ "[" + i + "]", "disabled");
	        if((srcAttribute.equalsIgnoreCase(("True")) && isEventSuccessful))
	    	 {
	    	     isEventSuccessful = true;
	    	  }
	           else if(srcAttribute.equalsIgnoreCase(("null")) && isEventSuccessful)
	           {
	    	      isEventSuccessful = false;
	           }
	              else if(srcAttribute.equalsIgnoreCase(("null")) && !isEventSuccessful)
	              {
	    	        isEventSuccessful = false;
	              }
	                 else if((srcAttribute.equalsIgnoreCase(("True")) && !isEventSuccessful))
		             {
	                   isEventSuccessful = false;
		             }
		               if(!isEventSuccessful)
		               {
			               strErrorIndex = strErrorIndex + ", " + i;
   		               }     
        }
     		if(!strErrorIndex.equals(""))
            {
			  throw new RuntimeException("TesterUser is able to click on connect button for" + strErrorIndex + "device.");
            }
		   else
			  strActualResult = "TesterUser is not able to click on connect button for any of the devices.";
		} catch (RuntimeException e)
          {
			strActualResult = "Connect button for diabled devices--- " + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
          }
		
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		
		//*************************************************************************************//
		// Step 4: Verify TesterUser is not to perform "Enable" or "Remove" operations on Device index page.
		//************************************************************************************//
		strstepDescription = "Verify TesterUser is not to perform 'Enable' or 'Remove' operations on Device index page.";
		strexpectedResult = "TesterUser should not be able to perform 'Enable' or 'Remove' operations on Device index page.";
		
	    isEventSuccessful = PerformAction("chkSelectAll_Devices", Action.Click); 
	     if(isEventSuccessful)
	      {
	    	 btnDisalbedValue =  getAttribute("btnEnable_Devices", "style");
	    	 
	           if(btnDisalbedValue.equals("display: none;"))
	           {
	        	   btnDisalbedValue =  getAttribute("btnRemove_Devices", "style");
	              if(btnDisalbedValue.equals("display: none;"))
	              {
	                strActualResult = "TesterUser is not able to perform 'Enable' or 'Remove' operations on Device index page.";
	              }
	            else
	            {
	        	  strActualResult = "'Remove' button is dispalyed on Device index page.";
	            }
	         }
	       else
	       {
	      strActualResult = "'Enable' button is dispalyed on Device index page.";
	       }
	      }
	    else
	    {
	      strActualResult = "Could not select checkbox on Device index page.";
	    }
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		

		//*************************************************************************************//
		// Step 5: Verify TesterUser is not able to perform "Enable" or "Remove" operations on Device details page.
		//************************************************************************************//
		strstepDescription = "Verify TesterUser is not able to perform 'Enable' or 'Remove' operations on Device details page.";
		strexpectedResult = "TesterUser should not be able to perform 'Enable' or 'Remove' operations on Device details page.";
		try
		{
		   devicesCount = getelementCount("eleDevicesHolderListView"); 
		   for (int i = 2; i <= devicesCount; i++) 
           {
	          isEventSuccessful = PerformAction(dicOR.get("eleDeviceName_DeviceIndexPage").replace("__INDEX__", String.valueOf(i)), Action.Click);  
		      if(isEventSuccessful)
		      {
			     if(PerformAction("btnEnable_Devices", Action.isNotDisplayed) && PerformAction("btnRemove_Devices", Action.isNotDisplayed))
			     {
				   if(!GoToDevicesPage())
				   {
					 isEventSuccessful = false;
					 throw new RuntimeException("Could not load the page after clicking on Devices menu." + i + "th" + "device." ); 
				   }
			     }
			   else
			   {
				   isEventSuccessful = false;
				 throw new RuntimeException("Enable or Remove button is being displayed on Device details page"  + i + "th" + "device.");  
			   }
			
		     } 
		   else
		   {
		     isEventSuccessful = false;
		     throw new RuntimeException("Could not click on device link"  + i + "th" + "device.");  
		   }
        }
		  
		   strActualResult = "TesterUser is not able to perform 'Enable' or 'Remove' operations on Device details page for any of the devices.";
		} catch (RuntimeException e)
          {
			strActualResult =  "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
			e.printStackTrace();
          }
		
		reporter.ReportStep(strstepDescription, strexpectedResult, strActualResult, isEventSuccessful);
		

		
		
	}
}