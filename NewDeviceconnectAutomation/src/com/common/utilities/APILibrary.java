package com.common.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.common.utilities.GenericLibrary;
import com.Reporting.APIReporter;
import com.Reporting.Reporter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class APILibrary extends GenericLibrary {
	
	public enum Component { Application, Device, User}
	private Reporter reporter = new Reporter();
	private APIReporter apiReporter= new APIReporter();
	public static String strErrMsg_ApiLib = "";
	private GenericLibrary genericLibrary = new GenericLibrary();
	String strActualResult="";
	boolean isEventSuccessful;
	ApplicationLibrary appLib=new ApplicationLibrary();
	
	
	/*
	public static void main (String args[])
	{
		String curlUsername= "jaishreeAdmin@gmail.com";
		String apiToken= "0244cb54-61d0-4fcd-9af2-1e75858d7bdb";
		String serverIP= "10.10.0.33";
		String command= "curl -u " + curlUsername + ":" 
				+ apiToken + " -X POST -H \"Content-Type: application/json\" \"http://" + serverIP
				+ "/apiv1/User\"";
		System.out.println(command);
		String output= new APILibrary().execCurlCmd(command);
		System.out.println(output);
		System.out.println(new APILibrary().getmodel(output));
		System.out.println(new APILibrary().getRecordCount(output));
		System.out.println(new APILibrary().getmodel(output,1));
		System.out.println(new APILibrary().getmodel(output));
	}
	
	*/
	//For getting list of keys in a particular record
	//Input: JSONResponse returned by execCurlCmd
	
	
	public JSONArray getJSONArray(String jsonResponse)
	{
		
		JSONObject obj;
		JSONArray arr= new JSONArray();
		boolean isReturningDetails=true,isErrorMessage=false,isSuccessMessage=false;
		String tempResponse="",object="";
		try
		{
			
		  if (jsonResponse.indexOf("\"isSuccess\":false") > 0)
          {
          	isReturningDetails=false;
          	isErrorMessage=true;
          	System.out.println("API Request returning error");
          }
          if (jsonResponse.indexOf("\"isSuccess\":true") > 0)
          {
          	isReturningDetails=false;
          	isSuccessMessage=true;
          	System.out.println("API Request returning success");
          }
          if (jsonResponse.equals("{\"isSuccess\":true}"))                {
          	isReturningDetails=false;
          	isSuccessMessage=false;
          	System.out.println("API Request returning only success no data");
          }
          
          
          if (isSuccessMessage)
          {
          	tempResponse=jsonResponse.replace("\"isSuccess\":true,", "");
          	tempResponse=tempResponse.replace(":{", ":[{");
          	tempResponse=tempResponse.replace("}}", "}]}");
          	object=tempResponse.split("\"")[1];
          	object=object.replace("\"", "");
          	
         /* 	obj = new JSONObject(tempResponse);	
          	arr = obj.getJSONArray(object);
          	System.out.println("ar= " +arr);*/
          	//System.out.println("Fetching " + arr.length() + " record(s)");
             

              // read any errors from the attempted command

              /*System.out.println("Here is the standard error of the command (if any):\n");
              while ((s = stdError.readLine()) != null) {
                  System.out.println(s);
              }*/
          }
         if(isReturningDetails)
         {
      	   tempResponse= "{\"results\":" + jsonResponse + "}" ;
             System.out.println("Response : "+tempResponse);
             object="results";
             
            /*obj = new JSONObject(tempResponse);	
          	arr = obj.getJSONArray(object);
          	System.out.println("ar= " +arr);*/
          	//System.out.println("Fetching " + arr.length() + " record(s)");

              /*System.out.println("Here is the standard error of the command (if any):\n");
              while ((s = stdError.readLine()) != null) {
                  System.out.println(s);
              }*/
         }
         
         if (isErrorMessage)
         {
      	   	tempResponse=jsonResponse.replace("\"isSuccess\":false,", "");
      	   	
      	   	tempResponse=tempResponse.replaceAll("\"data.*", "");
      	   	System.out.println(tempResponse);
      	   	tempResponse= "{\"results\":[" + tempResponse.replace(",", "") + "}]}" ;
      	   	System.out.println(tempResponse);
      	   	object="results";
      	  
           
             //System.out.println("Error message is : " + arr.length() + " record(s)");
                
                 // read any errors from the attempted command

                 /*System.out.println("Here is the standard error of the command (if any):\n");
                 while ((s = stdError.readLine()) != null) {
                     System.out.println(s);
                 }*/
         }

         obj = new JSONObject(tempResponse);	
         arr = obj.getJSONArray(object);
         
      }
	         catch( NullPointerException ex)
	 		{
	 			System.out.println("Either argument passed jsonResponse is incorrect or API response have zero records.");
	 		}
   		catch(Exception exp) {
   			System.out.println("exception happened - here's what I know: ");
   			exp.printStackTrace();
   		
   		}
		System.out.println("ar= " +arr + " arr length "+ arr.length());
   return arr;
	}
	
	/** 
	 Gets the list of model properties in a particular json record number.
	 <pre>
	 	Map<String,String> applicationDetails = new HashMap<String,String>();
		applicationDetails=	methods.getKeyValuePair(jsonResponse,1);
	</pre>
	 
	 <!--Created by : Jaishree Patidar-->
	 <!--Last updated :-->
	 @param jsonResponse,recordNumber
	 @return ArrayList<String>
	*/
	public ArrayList<String> getmodel(String jsonResponse, int recordNumber)
	{
		ArrayList<String> modelArrayList = new ArrayList<String>();
		String tempResponse;
		String strobject;
		try
		{
			 if (jsonResponse.indexOf("\"isSuccess\":true") > 0)
             {
				tempResponse=jsonResponse.replace("\"isSuccess\":true,", "");
             	tempResponse=tempResponse.replace(":{", ":[{");
             	tempResponse=tempResponse.replace("}}", "}]}");
             	strobject=tempResponse.split("\"")[1];
             	strobject=strobject.replace("\"", "");
             	jsonResponse=tempResponse;
             }else if(jsonResponse.indexOf("\"isSuccess\":false") > 0)
             {
            	 tempResponse=jsonResponse.replace("\"isSuccess\":false,", "");
            	 tempResponse=tempResponse.replaceAll("\"data.*", "");
            	 System.out.println(tempResponse);
            	 tempResponse= "{\"results\":[" + tempResponse.replace(",", "") + "}]}" ;
            	 strobject="results";
            	 jsonResponse=tempResponse;
             }
			 else
			 {
				 jsonResponse= "{\"results\":" + jsonResponse + "}" ;
				 strobject="results";
			 }
			 JSONObject obj = new JSONObject(jsonResponse);	
	         JSONArray arr = obj.getJSONArray(strobject);
	         	        
	  	      JSONObject object = arr.optJSONObject(recordNumber);
	  	      Iterator<String> iterator = object.keys();
	  	      while(iterator.hasNext()) 
	  	      {
	  	        String currentKey = iterator.next();
	  	        System.out.println(currentKey);
	  	 	    modelArrayList.add(currentKey);  
	  	 	   
	  	      }
         }
		catch( NullPointerException ex)
		{
			System.out.println("Either argument passed jsonResponse is incorrect or API response have zero records.");
		}
		catch( Exception ex)
		{
			ex.printStackTrace();
		}
		return (modelArrayList);
		
	}
	
	/** 
	 Gets the list of model properties in first json record.
	 <pre>
	 	Map<String,String> applicationDetails = new HashMap<String,String>();
		applicationDetails=	methods.getKeyValuePair(jsonResponse,1);
	</pre>
	 <!--Created by : Jaishree Patidar-->
	 <!--Last updated :-->
	 @param jsonResponse
	 @return ArrayList<String>
	*/
	public ArrayList<String> getmodel(String jsonResponse)
	{
	
		return new APILibrary().getmodel(jsonResponse,0);
		
	}
	
		//For getting value of a particular key
		//Input: JSONResponse returned by execCurlCmd
		public String getValue(String jsonResponse, String key, int recordNumber)
		{
			String tempResponse;
			String strobject;
			String value="";
			try
			{
				/*if (jsonResponse.indexOf("\"isSuccess\":true") > 0)
	            {
					tempResponse=jsonResponse.replace("\"isSuccess\":true,", "");
	             	tempResponse=tempResponse.replace(":{", ":[{");
	             	tempResponse=tempResponse.replace("}}", "}]}");
	             	strobject=tempResponse.split("\"")[1];
	             	strobject=strobject.replace("\"", "");
	             	jsonResponse=tempResponse;
	            }
				else
				{
					 strobject="results";
				}
				
			  JSONObject obj = new JSONObject(jsonResponse);	
		      JSONArray arr = obj.getJSONArray(strobject);*/
				
				if (jsonResponse.indexOf("\"isSuccess\":true") > 0)
	             {
					tempResponse=jsonResponse.replace("\"isSuccess\":true,", "");
	             	tempResponse=tempResponse.replace(":{", ":[{");
	             	tempResponse=tempResponse.replace("}}", "}]}");
	             	strobject=tempResponse.split("\"")[1];
	             	strobject=strobject.replace("\"", "");
	             	jsonResponse=tempResponse;
	             }else if(jsonResponse.indexOf("\"isSuccess\":false") > 0)
	             {
	            	 tempResponse=jsonResponse.replace("\"isSuccess\":false,", "");
	            	 tempResponse=tempResponse.replaceAll("\"data.*", "");
	            	 System.out.println(tempResponse);
	            	 tempResponse= "{\"results\":[" + tempResponse.replace(",", "") + "}]}" ;
	            	 strobject="results";
	            	 jsonResponse=tempResponse;
	             }
				 else
				 {
					 jsonResponse= "{\"results\":" + jsonResponse + "}" ;
					 strobject="results";
				 }
				
				if (!(jsonResponse.equals("{\"isSuccess\":true}") | jsonResponse.equals("\"results\":[]")) )
                {
				 JSONObject obj = new JSONObject(jsonResponse);	
		         JSONArray arr = obj.getJSONArray(strobject);
		         	        
		  	      JSONObject object = arr.optJSONObject(recordNumber);
		  	      value=object.getString(key);
                }
	         }
			catch( NullPointerException ex)
			{
				System.out.println("Either argument passed jsonResponse is incorrect or API response have zero records.");
			}
			catch( Exception ex)
			{
				ex.printStackTrace();
			}
			return (value);
			
		}
		
		public String getValue(String jsonResponse, String key)
		{
		
			return new APILibrary().getValue(jsonResponse, key, 0);
			
		}
	
	//For getting list of keys in a particular record
		//Input: JSONResponse returned by execCurlCmd
		/** 
		 Gets the map of model properties and their respective values in a particular json record number.
		 <pre>
		 	Map<String,String> applicationDetails = new HashMap<String,String>();
			applicationDetails=	methods.getKeyValuePair(jsonResponse,1);
		</pre>
		 
		 <!--Created by : Jaishree Patidar-->
		 <!--Last updated :-->
		 @param jsonResponse,recordNumber
		 @return 
		*/
		
		public Map<String,String> getKeyValuePair(String jsonResponse, int recordNumber)
		{
			return getKeyValuePair(jsonResponse,recordNumber,"results");
		}
		
		
		
		public Map<String,String> getKeyValuePair(String jsonResponse, int recordNumber,String strobject)
		{
			String tempResponse;
			//String strobject;
			Map<String,String> recordMap= new HashMap<String,String>();
			try
			{
					/*strobject="results";
					if (jsonResponse.indexOf("\"isSuccess\":true") > 0)
		            {
						tempResponse=jsonResponse.replace("\"isSuccess\":true,", "");
		             	tempResponse=tempResponse.replace(":{", ":[{");
		             	tempResponse=tempResponse.replace("}}", "}]}");
		             	strobject=tempResponse.split("\"")[1];
		             	strobject=strobject.replace("\"", "");
		             	jsonResponse=tempResponse;
		            }
					if (jsonResponse.indexOf("\"isSuccess\":false") > 0)
		            {
						strobject="results";
		            }
					
				  JSONObject obj = new JSONObject(jsonResponse);	
			      JSONArray arr = obj.getJSONArray(strobject);*/
				
				
				if (jsonResponse.indexOf("\"isSuccess\":true") > 0)
	             {
					tempResponse=jsonResponse.replace("\"isSuccess\":true,", "");
	             	tempResponse=tempResponse.replace(":{", ":[{");
	             	tempResponse=tempResponse.replace("}}", "}]}");
	             	strobject=tempResponse.split("\"")[1];
	             	strobject=strobject.replace("\"", "");
	             	jsonResponse=tempResponse;
	             }else if(jsonResponse.indexOf("\"isSuccess\":false") > 0)
	             {
	            	 tempResponse=jsonResponse.replace("\"isSuccess\":false,", "");
	            	 tempResponse=tempResponse.replaceAll("\"data.*", "");
	            	 System.out.println(tempResponse);
	            	 tempResponse= "{\"results\":[" + tempResponse.replace(",", "") + "}]}" ;
	            	 //strobject="results";
	            	 jsonResponse=tempResponse;
	             }
				 else
				 {
					 jsonResponse= "{\"results\":" + jsonResponse + "}" ;
					 //strobject="results";
				 }
				
				if (!(jsonResponse.equals("{\"isSuccess\":true}") | jsonResponse.equals("{\"results\":[]}")))
               {
				 JSONObject obj = new JSONObject(jsonResponse);	
		         JSONArray arr = obj.getJSONArray(strobject);
		         	        
		  	      JSONObject object = arr.optJSONObject(recordNumber);
		  	      Iterator<String> iterator = object.keys();
		  	      while(iterator.hasNext()) 
		  	      {
		  	        String currentKey = iterator.next();
		  	        String currentValue = object.getString(currentKey);
		  	        recordMap.put(currentKey,currentValue);  
		  	        		  	 	   
		  	      }
               }
	         }
			catch( NullPointerException ex)
			{
				System.out.println("Either argument passed jsonResponse is incorrect or API response have zero records.");
			}
			catch( Exception ex)
			{
				ex.printStackTrace();
			}
			return (recordMap);
			
		}
		
		/** 
		 Gets the map of model properties and their respective values in a particular json record number.
		 <pre>
		 	Map<String,String> applicationDetails = new HashMap<String,String>();
			applicationDetails=	methods.getKeyValuePair(jsonResponse);
		</pre>
		 
		 <!--Created by : Jaishree Patidar-->
		 <!--Last updated :-->
		 @param jsonResponse
		 @return 
		*/
		public Map<String,String> getKeyValuePair(String jsonResponse)
		{
		
			return new APILibrary().getKeyValuePair(jsonResponse,0);
			
		}
		
	
	//For getting number of record(s) returned by an API request
	//Input: JSONResponse returned by execCurlCmd
		/** 
		 Gets the record count of items returned in a particular json record number.
		 <pre>
		 	int recordCount;
			recordCount=	methods.getRecordCount(jsonResponse);
		</pre>
		 
		 <!--Created by : Jaishree Patidar-->
		 <!--Last updated :-->
		 @param jsonResponse
		 @return 
		*/
	public int getRecordCount(String jsonResponse)
	{
		
		try
		{	
			String tempResponse="";
			String strobject="";
			if (jsonResponse.indexOf("\"isSuccess\":true") > 0)
            {
				tempResponse=jsonResponse.replace("\"isSuccess\":true,", "");
            	tempResponse=tempResponse.replace(":{", ":[{");
            	tempResponse=tempResponse.replace("}}", "}]}");
            	strobject=tempResponse.split("\"")[1];
            	strobject=strobject.replace("\"", "");
            	jsonResponse=tempResponse;
            }else if(jsonResponse.indexOf("\"isSuccess\":false") > 0)
            {
           	 tempResponse=jsonResponse.replace("\"isSuccess\":false,", "");
           	 tempResponse=tempResponse.replaceAll("\"data.*", "");
           	 System.out.println(tempResponse);
           	 tempResponse= "{\"results\":[" + tempResponse.replace(",", "") + "}]}" ;
           	 strobject="results";
           	 jsonResponse=tempResponse;
            }
			 else
			 {
				 tempResponse= "{\"results\":" + jsonResponse + "}" ;
				 strobject="results";
			 }
			
			if (!(tempResponse.equals("{\"isSuccess\":true}") | tempResponse.equals("{\"results\":[]}")))
          {
			 
			 JSONObject obj = new JSONObject(tempResponse);	
	         JSONArray arr = obj.getJSONArray(strobject);
	         return (arr.length());	 
          }
			
		return 0;	 
	  	      
         }
		catch( NullPointerException ex)
		{
			System.out.println("Either argument passed jsonResponse is incorrect or API response have zero records.");
			return -1;
		}
		catch( Exception ex)
		{
			ex.printStackTrace();
			return -1;
		}
		
		
	}
	
		
		//Example Call: curlCommand = createCurlCommand("Application" , "Id,pretty" , "58a03deb-affe-41c4-a731-b4bdf7742fd8,true");
		/** 
		 This function creates a curl command on the basis of the arguments values passed. Returns the created curl command
		 <pre>
		 	Example Call: 
		 	curlCommand = createCurlCommand("Application" , "Id,pretty" , "58a03deb-affe-41c4-a731-b4bdf7742fd8,true");
		</pre>
		 <!--Created by : Jaishree Patidar-->
		 <!--Last updated :-->
		 @param 
		 @return String
		*/
		public String createCurlCommand(String component , String parameterList , String valueList)
		{
			return createCurlCommand(component , parameterList , valueList,  "", "", "","");
		}
		
		/** 
		 This function creates a curl command on the basis of the arguments values passed. Returns the created curl command
		 <pre>
		 	Example Call: 
		 	createCurlCommand("User" , "verb,Id,pretty" , "update,59ac3ac3-5c5e-4814-9a63-4b9ea2edbffe,true", "POST", "Content-Type: application/json", "{UserName:\\\"apiAddUSer@gmail.com\\\",Password:\\\"password\\\"}");
		</pre>
		 <!--Created by : Jaishree Patidar-->
		 <!--Last updated :-->.
		 @param 
		 @return String
		*/
		public String createCurlCommand(String component , String parameterList , String valueList, String requestType, String headerTag, String dataTag)
		{
			return createCurlCommand(component , parameterList , valueList,  requestType,  headerTag, dataTag, "");
		}
		
		/** 
		 This function creates a curl command on the basis of the arguments values passed. Returns the created curl command
		 <pre>
		 	Example Call: 
		 	curlCommand = createCurlCommand("Application" , "verb,pretty" , "add,true", "POST", "upload=@D:\\Jaishree\\apks and ipas\\com.ebay.mobile.apk");
		</pre>
		 <!--Created by : Jaishree Patidar-->
		 <!--Last updated :-->
		 @param 
		 @return String
		*/
		public String createCurlCommand(String component , String parameterList , String valueList, String requestType, String formTag)
		{
			return createCurlCommand(component , parameterList , valueList,  requestType,  "", "", formTag);
		}
		
		
		/** 
		 This function creates a curl command on the basis of the arguments values passed. Returns the created curl command
		 <!--Created by : Jaishree Patidar-->
		 <!--Last updated :-->
		 @param 
		 @return String
		*/
	   public String createCurlCommand(String component , String parameterList , String valueList, String requestType, String headerTag, String dataTag, String formTag)
	   {
		   return createCurlCommand(component , parameterList , valueList, requestType, headerTag, dataTag, formTag, "admin");
	   }
		/** 
		 This function creates a curl command on the basis of the arguments values passed. Returns the created curl command
		 <!--Created by : Jaishree Patidar-->
		 <!--Last updated :-->
		 @param 
		 @return String
		*/
	   public String createCurlCommand(String component , String parameterList , String valueList, String requestType, String headerTag, String dataTag, String formTag, String userType)
	   {
		   //mandatory parameters- URL - macvm
		   //authentication
		   
		   
		   /* Optional header, requestType, dataTag, formTag
		    * URL- component
		    * parameterlist valuelist
		    */
		   strErrMsg_ApiLib = "";
		   String curlcommand="";
		   String curlUsername, apiToken,serverIP;
		   
		   if (userType.equals("tester"))
		   {
			   curlUsername= com.common.utilities.GenericLibrary.dicCommon.get("testerEmailAddress");
			   apiToken= com.common.utilities.GenericLibrary.dicCommon.get("testerApiToken");
		   }else if(userType.equals("other")){
			   curlUsername= "admin";
			   //apiToken= com.common.utilities.GenericLibrary.dicCommon.get("testerApiToken");
			   
			   Object[] Values = appLib.ExecuteCLICommand("adminapitoken", "", "", "", "device", "" ,"", "");
				isEventSuccessful = (boolean)Values[2];
				apiToken=(String)Values[0];
				System.out.println("apiToken-----------------"+apiToken);
				if(isEventSuccessful)
				{
				   
				}
				else
				{
				  throw new RuntimeException("not able to get Api Token of adsmin user");
				}
		   }else
		   {
			   //-- updated by jaishree added apiToken in TestData.xlsx
				curlUsername= com.common.utilities.GenericLibrary.dicCommon.get("EmailAddress");
				apiToken= com.common.utilities.GenericLibrary.dicCommon.get("apiToken");
		   }

		  
			serverIP= com.common.utilities.GenericLibrary.dicCommon.get("ApplicationURL");
		   
		   String arguments= "";
		   String valuesWithSpaceReplaced="";
		   try
		   {
			   if (!parameterList.equals(""))
			   {
				   String parameters[]=  parameterList.split(",");
				   String values[]=  valueList.split(",");
				   	arguments="?";
					   int iCounter=0;
					   for(String parameter : parameters)
				       {
						   valuesWithSpaceReplaced=values[iCounter].replace(" ", "%20");
						   valuesWithSpaceReplaced=values[iCounter].replace(":", "%3A");
						   valuesWithSpaceReplaced=values[iCounter].replace("+", "%2B");
						   valuesWithSpaceReplaced=values[iCounter].replace(",", "%2C");
//						   valuesWithSpaceReplaced=values[iCounter].replace(".", "%2E");
						   //valuesWithSpaceReplaced=values[iCounter].replace("-", "%2D");
						   
						   //jaishree:---------Below code added due to changes done in API (Refer ML-4849)
						   //Below code will handle verb=update and verb=action&action=install
						   if ((parameter.equals("verb")) || (parameter.equals("")))  // added parameter="" for new scripts as verb no more exists
						   {
							   if (valuesWithSpaceReplaced.equals("action"))
							   {
								   iCounter++; //Ignoring verb=action statement as it is no longer required
								   continue;
							   }
								  
							   arguments= arguments+ "&" +valuesWithSpaceReplaced; //If anything other than verb=action is handled here
						   }
						   else //jaishree:--------- code block end
							   arguments= arguments+ "&" +parameter +"=" + valuesWithSpaceReplaced.trim();
						   iCounter++;
				       }
					 arguments=arguments.replace("?&", "?");
			   }
			   
			   if (!requestType.equals("")){
				   requestType="-X "+requestType;
			   }
		   
			   if (!headerTag.equals("")){
				   headerTag="-H \""+headerTag + "\"";
			   }
			   
			   if (!dataTag.equals("")){
				   dataTag="-d \""+dataTag + "\"";
			   }
			   
			   if (!formTag.equals("")){
				   formTag="--form \""+formTag + "\"";
			   }
		   
			curlcommand= "curl -u " + curlUsername  +":" + apiToken + " "
					   + requestType +" " + headerTag + " " + dataTag + " " + formTag
					   + " \"http://" + serverIP + "/apiv1/" + component + arguments + "\"";
			curlcommand=curlcommand.replace("  ", " ");
			   System.out.println(curlcommand);
			   
			   	String strStepDescription = "Create a curl command.";
			   	String strExpectedResult = "Correctly formatted curl command created based on the inputs given \n :component=" + component + "; parameterList=" +  parameterList 
			   				+ "; valueList=" +  valueList + "; requestType=" +  requestType + "; headerTag=" + headerTag  + "; dataTag=" + dataTag  + "; formTag=" + formTag ;
			   	String strActualResult="curl command created: <br><blockquote><div style=\"background-color:#DCDCDC; color:#000000; font-style: normal; font-family: Georgia;\">" +curlcommand +"</div></blockquote>"; 
				reporter.ReportStep(strStepDescription ,strExpectedResult , strActualResult, true);
			   
			   return curlcommand;
			  
		   }catch (Exception e)
		   {
			   strErrMsg_ApiLib = "createCurlCommand---" + "Exception at line number : '" + e.getStackTrace()[0].getLineNumber() + "'.; " + e.getMessage();
		   }
		   return curlcommand;
	   }	  
			   
	   /** 
		 Executed the curl command and returns its json Reponse.
		 <pre>
			String jsonResponse; //declaration of string to capture JSON Response of the API request.
			String command=apiMethods.createCurlCommand(component, parameterList, valueList);	//Creates a curl command with component, parameterList and valueList.
			jsonResponse=apiMethods.execCurlCmd(command);
		</pre>
		 
		 <!--Created by : Jaishree Patidar-->
		 <!--Last updated :-->
		 @param curl command
		 @return 
		*/
	public String execCurlCmd(String command)
    {
         String s = null;
         String entireResponse = new String();
         String jsonResponse = new String();
         /*String tempResponse = new String();
         boolean isReturningDetails=true;
         boolean isSuccessMessage=false;
         boolean isErrorMessage=false;
         String object="";*/
         try {

             // run the Unix "ps -ef" command

                Process p = Runtime.getRuntime().exec(command);
                BufferedReader stdInput = new BufferedReader(new
                     InputStreamReader(p.getInputStream()));
                
                BufferedReader stdError = new BufferedReader(new
                     InputStreamReader(p.getErrorStream()));

                // read the output from the command
           
                
                System.out.println("Here is the standard output of the command:\n");
                while ((s = stdInput.readLine()) != null) {
                    
                	System.out.println(s);
                	//System.out.println("Next line");
                	entireResponse += s;
                	jsonResponse=entireResponse;
                }
                
                /*if (jsonResponse.indexOf("\"isSuccess\":false") > 0)
                {
                	isReturningDetails=false;
                	isErrorMessage=true;
                	System.out.println("API Request returning error");
                }
                if (jsonResponse.indexOf("\"isSuccess\":true") > 0)
                {
                	isReturningDetails=false;
                	isSuccessMessage=true;
                	System.out.println("API Request returning success");
                }
                if (jsonResponse.equals("{\"isSuccess\":true}"))                {
                	isReturningDetails=false;
                	isSuccessMessage=false;
                	System.out.println("API Request returning only success no data");
                }
                
                
                if (isSuccessMessage)
                {
                	tempResponse=jsonResponse.replace("\"isSuccess\":true,", "");
                	tempResponse=tempResponse.replace(":{", ":[{");
                	tempResponse=tempResponse.replace("}}", "}]}");
                	object=tempResponse.split("\"")[1];
                	object=object.replace("\"", "");
                	JSONObject obj = new JSONObject(tempResponse);	
                	JSONArray arr = obj.getJSONArray(object);
                	System.out.println("ar= " +arr);
                	System.out.println("Fetching " + arr.length() + " record(s)");
                   

                    // read any errors from the attempted command

                    System.out.println("Here is the standard error of the command (if any):\n");
                    while ((s = stdError.readLine()) != null) {
                        System.out.println(s);
                    }
                }
               if(isReturningDetails)
               {
            	   tempResponse= "{\"results\":" + jsonResponse + "}" ;
                   System.out.println("Response : "+tempResponse);
                   
                   
	                JSONObject obj = new JSONObject(tempResponse);	
	            	JSONArray arr = obj.getJSONArray("results");
	            	System.out.println("ar= " +arr);
	            	System.out.println("Fetching " + arr.length() + " record(s)");
	
	                System.out.println("Here is the standard error of the command (if any):\n");
	                while ((s = stdError.readLine()) != null) {
	                    System.out.println(s);
	                }
               }
               
               if (isErrorMessage)
               {
            	   	tempResponse=jsonResponse.replace("\"isSuccess\":false,", "");
            	   	
            	   	tempResponse=tempResponse.replaceAll("\"data.*", "");
            	   	System.out.println(tempResponse);
            	   	tempResponse= "{\"results\":[" + tempResponse.replace(",", "") + "}]}" ;
            	   	System.out.println(tempResponse);
	               	JSONObject obj = new JSONObject(tempResponse);	
	               	JSONArray arr = obj.getJSONArray("results");
	               	System.out.println("ar= " +arr);
	               	System.out.println("Error message is : " + arr.length() + " record(s)");
	                  
	                   // read any errors from the attempted command
	
	                   System.out.println("Here is the standard error of the command (if any):\n");
	                   while ((s = stdError.readLine()) != null) {
	                       System.out.println(s);
	                   }
               }

            
            }
		         catch( NullPointerException ex)
		 		{
		 			System.out.println("Either argument passed jsonResponse is incorrect or API response have zero records.");
		 		}
            	catch (IOException e) {
                    System.out.println("exception happened - here's what I know: ");
                    e.printStackTrace();
                    
            	}
         		catch(Exception exp) {
         			System.out.println("exception happened - here's what I know: ");
         			exp.printStackTrace();
         		
         		}
         		
*/         
                //JSONArray arr =getJSONArray(jsonResponse);
                //System.out.println(arr.length());
         }
  		catch(Exception exp) {
  			System.out.println("exception happened - here's what I know: ");
  			exp.printStackTrace();
  		
  		}
                
                return jsonResponse;
    }
	
	/** 
	 Compares expectedResultMap and actualResultMap for the properties present in appModel array. Adds failing properties to errorMap
	 <pre>
		Map<String,String> errorMap=new HashMap<String,String>();
		isEventSuccessful=apiMethods.compareActualAndExpectedMap(appModel, expectedResultMap,actualResultMap,errorMap);
		System.out.println(errorMap);
	</pre>
	 
	 <!--Created by : Jaishree Patidar-->
	 <!--Last updated :-->
	 @param appModel, expectedResultMap, actualResultMap, errorMap
	 @return 
	*/
	public boolean compareActualAndExpectedMap(String[] appModel, Map<String,String> expectedResultMap,Map<String,String> actualResultMap,Map<String,String> errorMap)
	{
		strErrMsg_ApiLib="";
		boolean isEventSuccessful=true;
//		Map<String,String> errorMap=new HashMap<String,String>();
		try{
			for (String prop: appModel)
			{
				System.out.println("***************************"+prop);
				System.out.println("=============================="+actualResultMap.get(prop));
				System.out.println("=!@!@@@@@@@@@@@@@@@@@@@@@@@@@@"+expectedResultMap.get(prop));
				if (!actualResultMap.containsKey(prop))
					actualResultMap.put(prop,"null");
				if (!expectedResultMap.containsKey(prop))
					expectedResultMap.put(prop,"Not Verified");
				
				if (expectedResultMap.get(prop).equals(""))
					expectedResultMap.put(prop,"null");
				if (actualResultMap.get(prop).equals(""))
					actualResultMap.put(prop,"null");
				
				if(!expectedResultMap.get(prop).equals("Not Verified"))
				{
					String expectedValue=expectedResultMap.get(prop).trim();
					String actualValue=actualResultMap.get(prop).trim();
					/*if (prop.indexOf("Date")>0)
					{
						actualValue=convertDateUTCToLocalTimezone(actualValue);
						actualResultMap.put(prop,actualValue);
					}*/
					if (!expectedValue.equalsIgnoreCase(actualValue))
					{
						isEventSuccessful=false;
						errorMap.put(prop, actualResultMap.get(prop));
						
					}
				}
			}
		}catch(Exception e)
		{
			strErrMsg_ApiLib= e.getMessage();
		}
		return isEventSuccessful;
		
	}
	
	/** 
	 Converts Date in UTC format to the systems local timezone.
	 <pre>
		actualValue=apiMethods.convertDateUTCToLocalTimezone("2016-03-24T17:09:58.865984Z");
	</pre>
	 
	 <!--Created by : Jaishree Patidar-->
	 <!--Last updated :-->
	 @param utcDate
	 @return 
	*/
	public String convertDateUTCToLocalTimezone (String utcDate)
	{
		String pattern = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";
		String localDate="";
		try
		{			
		      // Create a Pattern object
		      Pattern r = Pattern.compile(pattern);
	
		      // Now create matcher object.
		      Matcher m = r.matcher(utcDate);
		      if (m.find( )) {
		    	  utcDate=m.group(0);
		      } else {
		    	  throw new RuntimeException("Not a date " + utcDate);
		      }
			
			System.out.println(utcDate);
			
			
			DateFormat formatterIST = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			formatterIST.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date date = formatterIST.parse(utcDate);
	
			DateFormat formatterUTC = new SimpleDateFormat("M/d/yyyy h:mm:ss a");
			formatterUTC.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); 
			localDate =formatterUTC.format(date); 
			
		
		}catch (ParseException e)
		{
			e.printStackTrace();
		}catch (Exception e) {
			strErrMsg_ApiLib+=e.getMessage();
		}
		return localDate;
	}
	
	/** 
	 Compares bytes to MB/GB
	 <pre>
		String bytesVal=actualResultMap.get("fileByteCount");
		String fileSizeMB = apiMethods.convertFromBytes(bytesVal, "mb");
		actualResultMap.put("fileByteCount", fileSizeMB);
	</pre>
	 
	 <!--Created by : Jaishree Patidar-->
	 <!--Last updated :-->
	 @param appModel, expectedResultMap, actualResultMap, errorMap
	 @return 
	*/
	public String convertFromBytes (String bytesVal, String strConvertTo)
	{
		String convertedFileSize="";
		try
		{	
			float fileSize=Float.parseFloat(bytesVal);
			switch (strConvertTo.toLowerCase())
			{
				case "mb":
					//Handling fileByteCount conversion from Bytes to MB
					fileSize= fileSize / (1024*1024);
					fileSize=(float) (Math.round(fileSize*100.00)/100.00);
					convertedFileSize=Float.toString(fileSize) + "MB";
					if (convertedFileSize.substring(convertedFileSize.length()-4).equals("00MB"))
						convertedFileSize=convertedFileSize.substring(0, convertedFileSize.length()-5) + "0MB";
					
					break;
				case "gb":
					//Handling fileByteCount conversion from Bytes to GB
					fileSize= fileSize / (1024*1024*1024);
					fileSize=(float) (Math.round(fileSize*10.0)/10.0);
					convertedFileSize=Float.toString(fileSize) + "GB";
					break;
				case "none":
					break;
			}
			
		}catch (Exception e) {
			strErrMsg_ApiLib+=e.getMessage();
		}
		return convertedFileSize;
	}
	

	/*<!--Created by : Ritdhwaj Chandel -->
	 <!--Last updated :-->
	 @param 
	 @return current datetime
	*/
	
	public String getDateTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String currentDate=dateFormat.format(date);
		return currentDate;
		
	}


	/** 
	 Verifies if getting success message in api json response
	 <pre>
		apiVerifyGettingSuccessMessage(jsonResponse);
	</pre>
	 
	 <!--Created by : Jaishree Patidar-->
	 <!--Last updated :-->
	 @param 
	 @return 
	*/
	public void apiVerifyGettingSuccessMessage (String jsonResponse)
	{
		if (jsonResponse.indexOf("\"isSuccess\":true") > 0) //As we are executing curl command with Id parameter only 1 record should be returned in JSON Response
			{
				strActualResult="As expected getting success message.";
				isEventSuccessful=true;
			}
			else
			{
				strActualResult= "Not getting success message. Hence failing stop test. <br> JSON Response =" +jsonResponse;
				isEventSuccessful=false;
			}
			reporter.ReportStep("Verifying success in JSON reponse", "Should get success message in JSON Response",strActualResult ,isEventSuccessful );
 
	}
	
	public String getInstalledApps()
	{
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
	
	//-------------------Jaishree Newly Added
	
	/** 
	 Gets the map of model properties and their respective values in a particular json record number.
	 <pre>
	 	Map<String,String> applicationDetails = new HashMap<String,String>();
		applicationDetails=	methods.getKeyValuePair_Report(jsonResponse,1);
	</pre>
	 
	 <!--Created by : Jaishree Patidar-->
	 <!--Last updated :-->
	 @param jsonResponse,recordNumber
	 @return 
	*/
	public Map<String,String> getKeyValuePair_Report(String jsonResponse, int recordNumber,String reportType)
	{
		String tempResponse;
		String strobject;
		Map<String,String> recordMap= new HashMap<String,String>();
		try
		{
				strobject="results";
//				boolean isMatch = Pattern.matches("^Invalid device control package. Incident ID ([A-Z]|[0-9]){4}$", errorMessage.trim());
//				"count":39457,"pageSize":5,"events":
				Pattern pattern = Pattern.compile("\"count\":[0-9]+,\"pageSize\":[0-9]+,");
				Matcher matcher = pattern.matcher(jsonResponse);
				if (matcher.find())
				{
				    System.out.println(matcher.group(0));
				    tempResponse=jsonResponse.replace(matcher.group(0), "");
	             	tempResponse=tempResponse.replace(":{", ":[{");
	             	tempResponse=tempResponse.replace("}}", "}]}");
	             	strobject=tempResponse.split("\"")[1];
	             	strobject=strobject.replace("\"", "");
	             	jsonResponse=tempResponse;
				}
				
				switch(reportType)
				{
					case "history":
						strobject="events";
						break;
					case "usage":
						strobject="deviceUsage";
						break;
					default:
						System.out.println("Incorrect report type");
				}
				
				System.out.println("--------------------"+jsonResponse);
			  JSONObject obj = new JSONObject(jsonResponse);	
		      JSONArray arr = obj.getJSONArray(strobject);
			
	         	        
	  	      JSONObject object = arr.optJSONObject(recordNumber);
	  	      Iterator<String> iterator = object.keys();
	  	      while(iterator.hasNext()) 
	  	      {
	  	        String currentKey = iterator.next();
	  	        String currentValue = object.getString(currentKey);
	  	        recordMap.put(currentKey,currentValue);  
	  	        		  	 	   
	  	      }
        }
		catch( NullPointerException ex)
		{
			System.out.println("Either argument passed jsonResponse is incorrect or API response have zero records.");
		}
		catch( Exception ex)
		{
			ex.printStackTrace();
		}
		return (recordMap);
		
	}
	
	
	/** 
	 Gets the count of the records of history report API Response
	 
	 <!--Created by : Jaishree Patidar-->
	 <!--Last updated :-->
	 @param jsonResponse
	 @return 
	*/
	public int getRecordCount_Report(String jsonResponse)
	{
		int count;
				Pattern pattern = Pattern.compile("\"count\":[0-9]+,");
				Matcher matcher = pattern.matcher(jsonResponse);
				if (matcher.find())
				{
				    System.out.println(matcher.group(0));
				    count=Integer.parseInt(matcher.group(0).replace("\"count\":", "").replace(",", ""));
				}
				else
					count=-1;
				
		return count;
		
	}
	
	
	
	/** 
	 Converts Date in systems local timezone to UTC format.
	 <pre>
		actualValue=apiMethods.convertDateLocalTimezoneToUTC("2016-03-24");
	</pre>
	 
	 <!--Created by : Jaishree Patidar-->
	 <!--Last updated :-->
	 @param localDate
	 @return 
	*/
	public String convertDateLocalTimezoneToUTC (String localDate,String formattype)
	{
		String pattern = "\\d{4}-\\d{2}-\\d{2}";
		String UTCDate="";
		try
		{			
		      // Create a Pattern object
		      Pattern r = Pattern.compile(pattern);
	
		      // Now create matcher object.
		      Matcher m = r.matcher(localDate);
		      if (m.find( )) {
		    	  localDate=m.group(0);
		      } else {
		    	  throw new RuntimeException("Not a date " + localDate);
		      }
			
			System.out.println(localDate);
			
			
			DateFormat formatterUTC = new SimpleDateFormat("yyyy-MM-dd");
			formatterUTC.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); 
			Date date =formatterUTC.parse(localDate); 
			
			
			DateFormat formatterIST = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			formatterIST.setTimeZone(TimeZone.getTimeZone("UTC"));
			UTCDate = formatterIST.format(date);
	
		}catch (ParseException e)
		{
			e.printStackTrace();
		}catch (Exception e) {
			strErrMsg_ApiLib+=e.getMessage();
		}
		return UTCDate;
	}
	
	public String getTodaysDate(String format)
	{
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date todaydate = new Date();
		String todaydt="";
		try
		{
			todaydt=dateFormat.format(todaydate);
		}catch(Exception ex)
		{
			todaydt="Incorrect format";
		}
		
		return todaydt;
	}
	public String getDate(int days, String format)
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		Date calDate = cal.getTime();  
		DateFormat dateFormat = new SimpleDateFormat(format);
	    String scalDate = dateFormat.format(calDate);
	    return scalDate;
	}
	
	public String getDeviceIDOfFirstUsageWithDevice()
	{
		
		String strStepDescription = "Get Device ID of the First usage record with device field not null/empty";
		String strExpectedResult =  "Dates should be set in start and end date boxes";
		String Id;
			
		if (!(PerformAction(dicOR.get("eleNoUsageErrorMessage"), Action.Exist)))
		{
			Id=getAttribute("("+dicOR.get("eleRowHavingDeviceURL_Report")+")[1]", "href"); //getting device ID for the first usage 	
			Id=Id.substring(Id.lastIndexOf("/")+1);
			isEventSuccessful=true;
			strActualResult="Successfully fetched device ID "+Id;
		}
		else
		{
			Id="";
			isEventSuccessful=false;
			strActualResult="No usage available.";
		}
			
		reporter.ReportStep(strStepDescription, strExpectedResult, strActualResult, isEventSuccessful);
		return Id;
	}
	
	public boolean navigateToDetailsPageWithGUID(String gUID, Component comp)
	{
		String serverIP=dicCommon.get("ApplicationURL");
		String output = comp.toString().substring(0, 1).toUpperCase() + comp.toString().substring(1).toLowerCase();
		
		isEventSuccessful=genericLibrary.PerformAction("browser","navigate","http://"+serverIP+"/#/Device/Detail/" + gUID);
		if(isEventSuccessful)
		{
			strActualResult= "Navigate to Device Details page of the Device using GUID. ID= " + gUID +" server used= " +serverIP;
		}
		else
		{
			strActualResult= "PerformAction---Navigate to URL. ID= " + gUID +" server used= " +serverIP+ strErrMsg_GenLib;
		}
		reporter.ReportStep("Navigates to Device details page", "Navigates successfully", strActualResult, isEventSuccessful);
		
		genericLibrary.PerformAction("browser","waitforpagetoload");
		return isEventSuccessful;
	}
	
	public int getReportRecordFromUIForSpecificApp(String Id,ArrayList<String> udeviceNameRecords, ArrayList<String> uIEventDateRecords, ArrayList<String> uIPerfomedByNameRecords, ArrayList<String> uIAppNameRecords) throws InterruptedException
	{
		ArrayList<String> uIHistoryRecords = new ArrayList<>();
		
		//-------------------------------------------Getting UI history records for the app Id------------------------				
		int expectedAppSpecificRecordCount=driver.findElements(By.xpath(dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id))).size();
//		uIHistoryRecords.add(GetTextOrValue(dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id), "text"));
		for (int ui=1; ui<=expectedAppSpecificRecordCount; ui++)
		{
			uIHistoryRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+ui+"]", "text"));
			udeviceNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[3]", "text"));
			uIEventDateRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[1]", "text"));
			uIPerfomedByNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[4]", "text"));
			uIAppNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[5]", "text"));
			
		}
			
		Thread.sleep(10000);
		
		//If more pages are available.. adding data to the expected 
		int curcount=0;
		while (PerformAction(dicOR.get("NextUsagePagebtn"), Action.isDisplayed))
		{
			System.out.println("Clicked on next");
			PerformAction(dicOR.get("NextUsagePagebtn"), Action.Click);
			Thread.sleep(30000);
			curcount=driver.findElements(By.xpath(dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id))).size();
			expectedAppSpecificRecordCount=expectedAppSpecificRecordCount+curcount;
			for (int ui=1; ui<=curcount; ui++)
			{
				uIHistoryRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+ui+"]", "text"));
				udeviceNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[3]", "text"));
				uIEventDateRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[1]", "text"));
				uIPerfomedByNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[4]", "text"));
				uIAppNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[5]", "text"));
				
			}
			
		}
		
		System.out.println("app specific rows count on UI :"+expectedAppSpecificRecordCount);
		apiReporter.apiAddBlock("app specific rows count on UI :"+expectedAppSpecificRecordCount);
		apiReporter.apiAddBlock("UI Records are: "+uIHistoryRecords);
		System.out.println(uIHistoryRecords);
		return expectedAppSpecificRecordCount;
	}

	
	public boolean compareAPIAndUIReportData(String reportType, String jsonResponse,String Id,Map<String,String> actualResultMap,ArrayList<String> udeviceNameRecords, ArrayList<String> uIEventDateRecords, ArrayList<String> uIPerfomedByNameRecords, ArrayList<String> uIAppNameRecords)
	{
		String[] expectedArray=new String[5];
		String expdeviceName,expeventDate,expperformedByName,expappName;
		String actdeviceName,acteventDate,actperformedByName,actappName;
		int count=0;
		int apiRecordCount=getRecordCount_Report(jsonResponse);
		boolean flagMatch=true;
		int verificationRowCount=5;
		if (apiRecordCount<verificationRowCount)
			verificationRowCount=apiRecordCount;
		
		String parentJson="results", dateVarName="";
		switch(reportType.toLowerCase())
		{
			case "history":
				parentJson="history";
				dateVarName="eventDate";
				break;
			case "usage":
				parentJson="deviceUsage";
				dateVarName="startDate";
				break;
			default:
				System.out.println("Incorrect report type");
		}
		
		
		for (int row=1; row<=verificationRowCount ; row++)
		{
			if (row>apiRecordCount)
				break;
			
			actualResultMap=getKeyValuePair_Report(jsonResponse,count,parentJson); //getting key value pair from the JSON response in a HashMap
			System.out.println(actualResultMap);
			expectedArray[count]=GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(row)+"]", "text");
			
			
			expdeviceName=GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(row)+"]/td[3]", "text");
			expeventDate=GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(row)+"]/td[1]", "text");
			expperformedByName=GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(row)+"]/td[4]", "text");
			expappName=GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(row)+"]/td[5]", "text");
			System.out.println(expdeviceName+" "+expeventDate+" "+expperformedByName+" "+expappName);
			//			apiReporter.apiAddBlock("Expected (from UI): "+expdeviceName+" "+expeventDate+" "+expperformedByName+" "+expappName);
			

			expdeviceName=udeviceNameRecords.get(count);
			expeventDate=uIEventDateRecords.get(count);
			expperformedByName=uIPerfomedByNameRecords.get(count);
			expappName=uIAppNameRecords.get(count);
			System.out.println(expdeviceName+" "+expeventDate+" "+expperformedByName+" "+expappName);
			//			apiReporter.apiAddBlock("Expected (from UI): "+expdeviceName+" "+expeventDate+" "+expperformedByName+" "+expappName);
			
			actdeviceName=actualResultMap.get("deviceName").replace("â€™", "’");
//			acteventDate=convertDateUTCToLocalTimezone(actualResultMap.get("eventDate"));
			acteventDate=convertDateUTCToLocalTimezone(actualResultMap.get(dateVarName));
			actperformedByName=actualResultMap.get("performedByName");
			actappName=actualResultMap.get("applicationName");
			System.out.println(actdeviceName+" "+acteventDate+" "+actperformedByName+" "+actappName);
			
			//			apiReporter.apiAddBlock("Actual (from API): " +actdeviceName+" "+acteventDate+" "+actperformedByName+" "+actappName);
			
			if ((expappName.equals(actappName)|| actappName==null) && (expdeviceName.equals(actdeviceName) || actdeviceName==null) && (expeventDate.equals(acteventDate)) && (expperformedByName.equals(actperformedByName) || actperformedByName==null))
			{
				apiReporter.apiPassBlock("Comparison Passed <br>Expected (from UI): "+expdeviceName+" "+expeventDate+" "+expperformedByName+" "+expappName +"<br> Actual (from API): " +actdeviceName+" "+acteventDate+" "+actperformedByName+" "+actappName);
			}
			else
			{
				flagMatch=false;
				apiReporter.apiErrorBlock("Comparison failed <br>Expected (from UI): "+expdeviceName+" "+expeventDate+" "+expperformedByName+" "+expappName +"<br> Actual (from API): " +actdeviceName+" "+acteventDate+" "+actperformedByName+" "+actappName);
				break;
			}
			System.out.println(expectedArray[count]);
			count++;

		}
		return flagMatch;
	}
	
	public int getPageSize_Report(String jsonResponse)
	{
		int count;
				Pattern pattern = Pattern.compile("\"pageSize\":[0-9]+,");
				Matcher matcher = pattern.matcher(jsonResponse);
				if (matcher.find())
				{
				    System.out.println(matcher.group(0));
				    count=Integer.parseInt(matcher.group(0).replace("\"pageSize\":", "").replace(",", ""));
				}
				else
					count=-1;
				
		return count;
		
	}

	public int getHistoryRecordFromUIForSpecificApp(String Id,ArrayList<String> udeviceNameRecords, ArrayList<String> uIEventDateRecords, ArrayList<String> uIPerfomedByNameRecords, ArrayList<String> uIAppNameRecords) throws InterruptedException
	{
		ArrayList<String> uIHistoryRecords = new ArrayList<>();
		
		
		//-------------------------------------------Getting UI history records for the app Id------------------------				
		int expectedAppSpecificRecordCount=driver.findElements(By.xpath(dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id))).size();
//			uIHistoryRecords.add(GetTextOrValue(dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id), "text"));
		for (int ui=1; ui<=expectedAppSpecificRecordCount; ui++)
		{
			uIHistoryRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+ui+"]", "text"));
			udeviceNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[3]", "text"));
			uIEventDateRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[1]", "text"));
			uIPerfomedByNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[4]", "text"));
			uIAppNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[5]", "text"));
			
		}
			
		Thread.sleep(10000);
		
		//If more pages are available.. adding data to the expected 
		int curcount=0;
		while (PerformAction(dicOR.get("NextUsagePagebtn"), Action.isDisplayed))
		{
			System.out.println("Clicked on next");
			PerformAction(dicOR.get("NextUsagePagebtn"), Action.Click);
			Thread.sleep(30000);
			curcount=driver.findElements(By.xpath(dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id))).size();
			expectedAppSpecificRecordCount=expectedAppSpecificRecordCount+curcount;
			for (int ui=1; ui<=curcount; ui++)
			{
				uIHistoryRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+ui+"]", "text"));
				udeviceNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[3]", "text"));
				uIEventDateRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[1]", "text"));
				uIPerfomedByNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[4]", "text"));
				uIAppNameRecords.add(GetTextOrValue("("+dicOR.get("eleApplicationSpecificHistoryRecordRow_History").replace("__APPID__",Id)+")["+Integer.toString(ui)+"]/td[5]", "text"));
				
			}
			
		}	
	
		System.out.println("app specific rows count on UI :"+expectedAppSpecificRecordCount);
		apiReporter.apiAddBlock("app specific rows count on UI :"+expectedAppSpecificRecordCount);
		apiReporter.apiAddBlock("UI Records are: "+uIHistoryRecords);
		System.out.println(uIHistoryRecords);
		return expectedAppSpecificRecordCount;
	}

	/** 
	 Verifies some fields are not null in Report component's API Response.
	 <pre>
		flag=	methods.verifyfieldsNotNullInAPIResponse(jsonResponse,notNullFields,reportType);
	</pre>
	 
	 <!--Created by : Jaishree Patidar-->
	 <!--Last updated :-->
	 @param jsonResponse,notNullFields,reportType
	 @return 
	*/
	public boolean verifyfieldsNotNullInAPIResponse(String jsonResponse, String notNullFields,String reportType)
	{
		boolean flag=true;
		String[] fieldArray= notNullFields.split(",");
		int recordCount= getRecordCount_Report(jsonResponse);
		Map<String,String> apiResponse = new HashMap<String,String>();
		
		for(int iterator=0; iterator<recordCount; iterator++)
		{
			apiResponse=getKeyValuePair_Report(jsonResponse, iterator, reportType);
			//System.out.println("Row : "+ iterator+ "  " +apiResponse);
			//apiReporter.apiAddBlock("Row : "+ iterator+ "  " +apiResponse); //--uncomment this line of code for debugging
			for (String field : fieldArray)
			{
				if ((apiResponse.get(field)==null) || (apiResponse.get(field).equals("")))
				{
					flag=false;
					apiReporter.apiErrorBlock("Record number: " +iterator+" field name: "+field+ "Response Record: " +apiResponse.toString());
				}
			}
		}
		if (flag)
			apiReporter.apiPassBlock("No null/junk entries are present in response returned");
		else
			apiReporter.apiErrorBlock("null/junk entries are present in response returned");
		return flag;
	}
	
	/** 
	 Gets the map of model properties and their respective values in a particular json record number
	 
	 <!--Created by : Ritdhwaj Chandel-->
	 <!--Last updated :-->
	 @param jsonResponse,recordNumber
	 @return 
	*/
	public Map<String,String> getUSBHubDetails(String jsonResponse, int recordNumber)
	{
		String tempResponse;
		String strobject;
		Map<String,String> recordMap= new HashMap<String,String>();
		try
		{
			
			if (jsonResponse.indexOf("\"isSuccess\":true") > 0)
            {
				tempResponse=jsonResponse.replace("\"isSuccess\":true,", "");
            	tempResponse=jsonResponse.replace(":{", ":[{");
            	tempResponse=tempResponse.replace("}}", "}]}");
            	strobject=tempResponse.split("\"")[1];
            	strobject=strobject.replace("\"", "");
            	jsonResponse=tempResponse;
            }else if(jsonResponse.indexOf("\"isSuccess\":false") > 0)
            {
           	 tempResponse=jsonResponse.replace("\"isSuccess\":false,", "");
           	 tempResponse=tempResponse.replaceAll("\"data.*", "");
           	 System.out.println(tempResponse);
           	 tempResponse= "{\"results\":[" + tempResponse.replace(",", "") + "}]}" ;
           	 strobject="results";
           	 jsonResponse=tempResponse;
            }
			 else
			 {
				
				 if(recordNumber==0){
					 jsonResponse=jsonResponse.split(",\"devices")[0];
					 jsonResponse=jsonResponse.replace("usbHubList\":[{\"", "").replaceFirst("name", "GatewayName").concat("}]");
					 jsonResponse= "{\"results\":" + jsonResponse + "}" ;
				 }
				 else{
					 tempResponse=jsonResponse.split("\"usbHubList")[0].replace("name", "GatewayName").concat("\"");
					 jsonResponse=jsonResponse.split("}]},")[recordNumber];
					 jsonResponse=jsonResponse.split(",\"devices")[0];
					 jsonResponse=jsonResponse.concat("}]");
					 jsonResponse=tempResponse+jsonResponse;
					 jsonResponse= "{\"results\":" + jsonResponse.replace("\"{", "") + "}" ;
				 }
				 	
				 
				 
				 strobject="results";
			 }
			
			if (!(jsonResponse.equals("{\"isSuccess\":true}") | jsonResponse.equals("{\"results\":[]}")))
          {
			 JSONObject obj = new JSONObject(jsonResponse);	
	         JSONArray arr = obj.getJSONArray(strobject);
	         	        
	  	      JSONObject object = arr.optJSONObject(0);
	  	      Iterator<String> iterator = object.keys();
	  	      while(iterator.hasNext()) 
	  	      {
	  	        String currentKey = iterator.next();
	  	        String currentValue = object.getString(currentKey);
	  	        recordMap.put(currentKey,currentValue);  
	  	        		  	 	   
	  	      }
          }
        }
		catch( NullPointerException ex)
		{
			System.out.println("Either argument passed jsonResponse is incorrect or API response have zero records.");
		}
		catch( Exception ex)
		{
			ex.printStackTrace();
		}
		return (recordMap);
		
	}
	
	/** 
	 Gets the map of model properties and their respective values in a particular json record number.
	 
	 <!--Created by : Ritdhwaj Chandel-->
	 <!--Last updated :-->
	 @param jsonResponse,recordNumber,and hubNo
	 @return 
	*/
	public Map<String,String> getUSBPortDetails(String jsonResponse, int recordNumber,int hubNo)
	{
		String tempResponse;
		String strobject;
		Map<String,String> recordMap= new HashMap<String,String>();
		try
		{
			
			if (jsonResponse.indexOf("\"isSuccess\":true") > 0)
           {
				tempResponse=jsonResponse.replace("\"isSuccess\":true,", "");
           	tempResponse=jsonResponse.replace(":{", ":[{");
           	tempResponse=tempResponse.replace("}}", "}]}");
           	strobject=tempResponse.split("\"")[1];
           	strobject=strobject.replace("\"", "");
           	jsonResponse=tempResponse;
           }else if(jsonResponse.indexOf("\"isSuccess\":false") > 0)
           {
          	 tempResponse=jsonResponse.replace("\"isSuccess\":false,", "");
          	 tempResponse=tempResponse.replaceAll("\"data.*", "");
          	 System.out.println(tempResponse);
          	 tempResponse= "{\"results\":[" + tempResponse.replace(",", "") + "}]}" ;
          	 strobject="results";
          	 jsonResponse=tempResponse;
           }
			 else
			 {
				 jsonResponse=jsonResponse.split(",\"devices\":")[hubNo];
				 jsonResponse=jsonResponse.replace("]}]}", "");
				 jsonResponse= "{\"results\":" + jsonResponse + "}" ;
				 
				 strobject="results";
			 }
			
			if (!(jsonResponse.equals("{\"isSuccess\":true}") | jsonResponse.equals("{\"results\":[]}")))
         {
			 JSONObject obj = new JSONObject(jsonResponse);	
	         JSONArray arr = obj.getJSONArray(strobject);
	         	        
	  	      JSONObject object = arr.optJSONObject(recordNumber);
	  	      Iterator<String> iterator = object.keys();
	  	      while(iterator.hasNext()) 
	  	      {
	  	        String currentKey = iterator.next();
	  	        String currentValue = object.getString(currentKey);
	  	        recordMap.put(currentKey,currentValue);  
	  	        		  	 	   
	  	      }
         }
       }
		catch( NullPointerException ex)
		{
			System.out.println("Either argument passed jsonResponse is incorrect or API response have zero records.");
		}
		catch( Exception ex)
		{
			ex.printStackTrace();
		}
		return (recordMap);
		
	}
	
	public String readXML(String Entitlement){
		String data="";
		try {
			File fXmlFile = new File("C:/Users/ritdhwajs/Desktop/ErrorMessage.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("Entitlement");
			System.out.println(nList.getLength());
			System.out.println("----------------------------");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				System.out.println(nNode);
				//System.out.println("Current Element :" + nNode.getNodeName());
				//System.out.println("gfdgfdg:"+Node.ELEMENT_NODE);
				//if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				if(eElement.getAttribute("id").equals(Entitlement)){
					data=eElement.getElementsByTagName("ErrorMessage").item(0).getTextContent();
					break;
				}
				//System.out.println("Staff id : " + eElement.getAttribute("id"));
				//System.out.println("First Name : " + eElement.getElementsByTagName("EntitlementName").item(0).getTextContent());
				//System.out.println("Last Name : " + eElement.getElementsByTagName("ErrorMessage").item(0).getTextContent());
				//System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
				//System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
				//}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public String getErrorMessage(String command)
	{
		String s = null;
		String entireResponse = new String();
		String jsonResponse = new String();
		try {
			// run the Unix "ps -ef" command
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new
					InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new
					InputStreamReader(p.getErrorStream()));
			// read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
				//System.out.println("escaped :"+StringEscapeUtils.unescapeJava(s));
				//System.out.println(unescapeJava(s));
				//System.out.println("Next line");
				entireResponse += s;
				jsonResponse=entireResponse;
				//jsonResponse=StringEscapeUtils.unescapeJava(jsonResponse);
				jsonResponse=jsonResponse.split(":")[2]+jsonResponse.split(":")[3];
				jsonResponse=jsonResponse.split(",\"data\"")[0];
				jsonResponse=jsonResponse.replace("\"", "");
				//jsonResponse=entireResponse;
			}
			/*if (jsonResponse.indexOf("\"isSuccess\":false") > 0)
                {
                	isReturningDetails=false;
                	isErrorMessage=true;
                	System.out.println("API Request returning error");
                }
                if (jsonResponse.indexOf("\"isSuccess\":true") > 0)
                {
                	isReturningDetails=false;
                	isSuccessMessage=true;
                	System.out.println("API Request returning success");
                }
                if (jsonResponse.equals("{\"isSuccess\":true}"))                {
                	isReturningDetails=false;
                	isSuccessMessage=false;
                	System.out.println("API Request returning only success no data");
                }
                if (isSuccessMessage)
                {
                	tempResponse=jsonResponse.replace("\"isSuccess\":true,", "");
                	tempResponse=tempResponse.replace(":{", ":[{");
                	tempResponse=tempResponse.replace("}}", "}]}");
                	object=tempResponse.split("\"")[1];
                	object=object.replace("\"", "");
                	JSONObject obj = new JSONObject(tempResponse);	
                	JSONArray arr = obj.getJSONArray(object);
                	System.out.println("ar= " +arr);
                	System.out.println("Fetching " + arr.length() + " record(s)");
                    // read any errors from the attempted command
                    System.out.println("Here is the standard error of the command (if any):\n");
                    while ((s = stdError.readLine()) != null) {
                        System.out.println(s);
                    }
                }
               if(isReturningDetails)
               {
            	   tempResponse= "{\"results\":" + jsonResponse + "}" ;
                   System.out.println("Response : "+tempResponse);
	                JSONObject obj = new JSONObject(tempResponse);	
	            	JSONArray arr = obj.getJSONArray("results");
	            	System.out.println("ar= " +arr);
	            	System.out.println("Fetching " + arr.length() + " record(s)");
	                System.out.println("Here is the standard error of the command (if any):\n");
	                while ((s = stdError.readLine()) != null) {
	                    System.out.println(s);
	                }
               }
               if (isErrorMessage)
               {
            	   	tempResponse=jsonResponse.replace("\"isSuccess\":false,", "");
            	   	tempResponse=tempResponse.replaceAll("\"data.*", "");
            	   	System.out.println(tempResponse);
            	   	tempResponse= "{\"results\":[" + tempResponse.replace(",", "") + "}]}" ;
            	   	System.out.println(tempResponse);
	               	JSONObject obj = new JSONObject(tempResponse);	
	               	JSONArray arr = obj.getJSONArray("results");
	               	System.out.println("ar= " +arr);
	               	System.out.println("Error message is : " + arr.length() + " record(s)");
	                   // read any errors from the attempted command
	                   System.out.println("Here is the standard error of the command (if any):\n");
	                   while ((s = stdError.readLine()) != null) {
	                       System.out.println(s);
	                   }
               }
            }
		         catch( NullPointerException ex)
		 		{
		 			System.out.println("Either argument passed jsonResponse is incorrect or API response have zero records.");
		 		}
            	catch (IOException e) {
                    System.out.println("exception happened - here's what I know: ");
                    e.printStackTrace();
            	}
         		catch(Exception exp) {
         			System.out.println("exception happened - here's what I know: ");
         			exp.printStackTrace();
         		}
			 */         
			//JSONArray arr =getJSONArray(jsonResponse);
			//System.out.println(arr.length());
		}
		catch(Exception exp) {
			System.out.println("exception happened - here's what I know: ");
			exp.printStackTrace();
		}
		return jsonResponse;
	}

	
}


