package eHealth.rest.servers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import eHealth.rest.business.PersonImpl;
import eHealth.rest.model.Person;
import eHealth.rest.utils.JSONFormatter;

public class MyPostman {
	static int first_person_id;
	static int last_person_id;
	static ClientConfig config = new ClientConfig();
	static Client client = ClientBuilder.newClient(config);
	static WebTarget service = client.target(UriBuilder.fromUri(
			"http://localhost:8089/").build());
	static WebTarget peopleResource = service.path("person");
	static int first_Person_Count=0;
	static int last_Person_Count=0;
	/**
	 * @measureType used for taking one measure name from Request 7
	 * @measureID used for taking one measure Id from Request 7
	 */
	static StringBuilder measure_Type=new StringBuilder();
	static long measure_ID=0;

	public static void main(String[] args) throws ParseException {
		StringBuilder resultStatus = new StringBuilder("OK");

		/************************
		 *********** ************ TESTING REQUESTS**** ************
		 ************************/
		/**
		 * Instantiates a new client
		 */

		/**
		 * creates a resource for call
		 */
		
		Invocation.Builder invocationBuilder = peopleResource
				.request(MediaType.APPLICATION_JSON);
		invocationBuilder.accept(MediaType.APPLICATION_JSON);
		invocationBuilder.header("Accept", "application/json");
		invocationBuilder.header("Content-Type", "application/json");
		Response response = invocationBuilder.get();
		String personListString = response.readEntity(String.class);
		/**
		 * Parse string to object array
		 */
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(personListString);
		JSONArray jsonArray = (JSONArray) obj;
		/**
		 * Calculate total size of the response
		 */
		int personListSize = jsonArray.size();
		/**
		 * Check result validation
		 */
		if (personListSize < 3) {
			resultStatus.append("ERROR");
		}
		/**
		 * Getting the first person
		 */
		JSONObject firstPerson = new JSONObject();
		firstPerson = (JSONObject) jsonArray.get(0);
		first_person_id = Integer.parseInt(firstPerson.get("personId")
				.toString());
		/**
		 * Getting the last person
		 */
		JSONObject lastPerson = new JSONObject();
		lastPerson = (JSONObject) jsonArray.get(personListSize - 1);
		last_person_id = Integer
				.parseInt(lastPerson.get("personId").toString());
		/**
		 * Formatted output
		 */
		System.out
				.println("REQUEST #1 " + "GET" + " " + " " + "Accept:"
						+ "APPLICATION/XML" + " " + "Content-type:"
						+ "APPLICATION/XML");
		System.out.println("=>Result:" + resultStatus);
		System.out.println("=>Http Status:" + response.getStatus());
		System.out.println(JSONFormatter.toJsonFormat(personListString));

		response.close();
		/*************************
		 ******** REQUEST #2*******
		 *************************/
		
		/**
		 * Call requestTwo for specific resource
		 */
		Response req2JsonResponse = requestTwo(first_person_id);
		/**
		 * Formatted output
		 */
		String req2ResponseStatus = (req2JsonResponse.getStatus() == 200) ? "OK"
				: "ERROR";
		System.out.println("REQUEST #2 " + "GET" + " " + " " + "Accept:"
				+ "APPLICATION/JSON" + " " + "Content-type:"
				+ "APPLICATION/JSON");
		System.out.println("=>Result:" + req2ResponseStatus);
		System.out.println("=>Http Status:" + response.getStatus());
		System.out.println(req2JsonResponse.readEntity(String.class));
		// System.out.println(JSONFormatter.toJsonFormat(JSONFormatter.toJsonFormat(req2JsonResponse.readEntity(String.class).toString())));

		/***********************
		 ******** REQUEST #3*****
		 ***********************/
		
		String req3InputString = "{" + "\"firstName\"" + ": " + "\"nibret\"," + "\"middleName\""
				+ ": null," + "\"lastName\"" + ": null," + "\"email\"" + ": "
				+ "\"tultula.com\"" + "\"userName\"" + ": " + "\"mammy\""
				+ "\"password\"" + ": " + "\"mammy\"" + "}";		
		System.out.println(req3InputString);
		JSONObject req3jsonobject = new JSONObject();
		JSONParser parser3 = new JSONParser();
		req3jsonobject = (JSONObject) parser3.parse(req3InputString);
		System.out.println(req3jsonobject.toJSONString());
//		Person person = new Person();
//		person = PersonImpl.getPersonById(first_person_id);
//		person.setFirstName("Dawit");

		resultStatus.setLength(0);
		WebTarget personResource = peopleResource.path(String
				.valueOf(first_person_id));
		Response req3JsonResponse = personResource.request()
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.header("Content-Type", "application/json")
				.accept("Accept","application/json")
				.put(Entity.entity(req3jsonobject, MediaType.APPLICATION_JSON));
		String personreq3 = req3JsonResponse.readEntity(String.class);
		/**
		 * Parsing response to json array
		 */
		System.out.println(personreq3.toString());
		System.out
				.println("*******************************************************");
		Object req3Object = parser.parse(personreq3);
		JSONObject req2PersonObject = new JSONObject();
		req2PersonObject = (JSONObject) req3Object;
		/**
		 * Get the updated name
		 */
		String updatedName = (String) req2PersonObject.get("firstName");
		/**
		 * Check if the name changed and equal to the given name
		 */

		if ("nibret".equalsIgnoreCase(updatedName)) {
			resultStatus.append("OK");
		} else {
			resultStatus.append("ERROR");
		}
		/**
		 * Print formatted output
		 */
		String req3JsonResponseStatus = (req3JsonResponse.getStatus() == 200) ? "OK"
				: resultStatus.toString();
		System.out.println("REQUEST #3 " + "GET" + " " + " " + "Accept:"
				+ "APPLICATION/JSON" + " " + "Content-type:"
				+ "APPLICATION/JSON");
		System.out.println("=>Result:" + req3JsonResponseStatus);
		System.out.println("=>Http Status:" + req3JsonResponse.getStatus());
		 System.out.println(JSONFormatter.toJsonFormat(JSONFormatter.toJsonFormat(personreq3)));

		/***********************
		 ***** REQUEST #4********
		 ***********************/

		String req4InputString = "{" + "\"personId\"" + ": 28,"
				+ "\"firstName\"" + ": " + "\"sultan\"," + "\"middleName\""
				+ ": null," + "\"lastName\"" + ": null," + "\"email\"" + ": "
				+ "\"tultula.com\"" + "\"userName\"" + ": " + "\"mammy\""
				+ "\"password\"" + ": " + "\"mammy\"" + "}";
		System.out.println(req4InputString);
		JSONObject req4jsonobject = new JSONObject();
		JSONParser parser4 = new JSONParser();
		req4jsonobject = (JSONObject) parser4.parse(req4InputString);

		Invocation.Builder invocationBuilderReq4 = peopleResource
				.request(MediaType.APPLICATION_JSON);
		invocationBuilderReq4.accept(MediaType.APPLICATION_JSON);
		invocationBuilderReq4.header("Accept", "application/json");
		invocationBuilderReq4.header("Content-Type", "application/json");
		Response responseReq4 = invocationBuilderReq4.post(Entity.entity(
				req4jsonobject, MediaType.APPLICATION_JSON));
		System.out.println(responseReq4.getStatus());

		/**********************
		 ***** REQUEST #5*******
		 **********************/

		WebTarget personReq5Resource = peopleResource.path(String.valueOf(28));
		Invocation.Builder invocationBuilderReq5 = personReq5Resource
				.request(MediaType.APPLICATION_JSON);
		invocationBuilderReq5.header("Accept", "application/json");
		invocationBuilderReq5.header("Content-Type", "application/json");
		Response req5JsonResponse = invocationBuilderReq5.delete();
		System.out.println("Response for 5 "+req5JsonResponse.getStatus());
		Response req5_1Response=requestTwo(28);
		System.out.println("response for 5 for rquest 1"+req5_1Response.getStatus());
		/**
		 * Repeat request 1 and see the result
		 * 
		 * This should be temporary and unless a repeatition
		 */

		/**************************
		 ********** REQUEST #9******
		 **************************/
		WebTarget measureTypes = service.path("measureTypes");
		Invocation.Builder invocationBuilderReq9 = measureTypes
				.request(MediaType.APPLICATION_JSON);
		invocationBuilderReq9.accept(MediaType.APPLICATION_JSON);
		invocationBuilderReq9.header("Accept", "application/json");
		invocationBuilderReq9.header("Content-Type", "application/json");
		Response req9JsonResponse = invocationBuilderReq9.get();
		String req9JsonString = req9JsonResponse.readEntity(String.class);
		System.out.println("The result of the query before object submission");
		System.out.println(req9JsonResponse.getStatus());
		/**
		 * Check if the result is intended result
		 */
		resultStatus.delete(0, resultStatus.length());

		Object req9JsonObject = parser.parse(req9JsonString);
		JSONArray jsonArray9 = (JSONArray) req9JsonObject;
		System.out.println("Json array content");
		System.out.println(jsonArray9);
		int total_Size_MeasureTypes = jsonArray9.size();
		if (total_Size_MeasureTypes > 2) {
			resultStatus.append("OK");
		} else {
			resultStatus.append("ERROR");
		}
		System.out.println("Number of returns :" +total_Size_MeasureTypes);
		/**
		 * Iterate over elements and map to array
		 * and save to an array
		 */
		String[] measures = new String[total_Size_MeasureTypes];
		for (int i = 0; i < total_Size_MeasureTypes; i++) {
			JSONObject Object9 = (JSONObject) jsonArray9.get(i);
			measures[i] = (String) Object9.get("measureDefName");
			System.out.println(measures[i]);

		}
		/**
		 * Send request to the measure type of the person for the first and last
		 * person obtained above
		 */
		/**
		 * Variables for holding the result of the response for each request and each measure type
		 */
		int responseCode=0;
		/**
		 * check for first person measure histories
		 */
		
		/**
		 * TEST of person ID's
		 */
		System.out.println("First person Id"+first_person_id);
		System.out.println("last person Id"+ last_person_id);
		int person1_status=0;
		
		/**
		 * set some character for the string builder because we can't get length for null 
		 * used in requestSix method
		 */
		measure_Type.append("ac");
		System.out.println("=========================First Person==============");
		for(int i=0;i<total_Size_MeasureTypes;i++)
		{
			responseCode=0;
			responseCode=requestSix(String.valueOf(first_person_id),measures[i]);
			System.out.println(responseCode);
			if (responseCode==0)
			{
				break;
			}
			/**
			 * If it reached here,it means that all measures has atleast one return
			 */
			if(i==(total_Size_MeasureTypes-1))
			{
				person1_status=1;
			}
		}
		/**
		 * Check for last person measure histories
		 */
		int lastPerson_status=0;
		int lastResponseCode=0;
		System.out.println("======================================Last===========");
		for(int i=0;i<total_Size_MeasureTypes;i++)
		{
			lastResponseCode=0;
			lastResponseCode=requestSix(String.valueOf(last_person_id),measures[i]);
			System.out.println(lastResponseCode);
			if (lastResponseCode==0)
			{
				break;
			}
			/**
			 * If it reached here,it means that all measures has atleast one return
			 */
			if(i==(total_Size_MeasureTypes-1))
			{
				lastPerson_status=1;
			}
		}
		/**
		 * Result from the two
		 */
		System.out.println("Person one status is");
		System.out.println(person1_status==1?"OK":"ERROR");
		System.out.println("last person status is");
		System.out.println(lastPerson_status==1?"OK":"ERROR");
		System.out.println("Our MID is"+measure_ID);
		System.out.println("our measure name is"+measure_Type.toString());
		
												/**************************
												 ********** REQUEST 8******
												 **************************/
		
		WebTarget PersonIdTarget = peopleResource.path(String.valueOf(first_person_id));
		WebTarget personMeasuretarget=PersonIdTarget.path(measure_Type.toString());
		WebTarget measureMidTarget=personMeasuretarget.path(String.valueOf(measure_ID));
		Invocation.Builder invocationBuilderReq8=measureMidTarget.request().accept(MediaType.APPLICATION_JSON);
		invocationBuilderReq8.header("Content_Type", "application/json");
		invocationBuilderReq8.header("Accept","application/json");
		Response responseReq8=invocationBuilderReq8.get();
		//String responseReq8String=responseReq8.readEntity(String.class);
		/**
		 * Format result string into JSON format
		 */
		System.out
		.println("REQUEST #1 " + "GET" + " " + " " + "Accept:"
				+ "APPLICATION/XML" + " " + "Content-type:"
				+ "APPLICATION/XML");
		System.out.println("=>Result:" + resultStatus);
		System.out.println("=>Http Status:" + responseReq8.getStatus());
		//System.out.println(JSONFormatter.toJsonFormat(responseReq8String));
		
		
		
		
		/************************
		 *****Part 3.9 **********
		 ************************/
		String randomMeasureType=measures[0];
		int Original_Count=request_Six_Eight(first_person_id,randomMeasureType);
		/**
		 * create json object for request 8
		 */
		String measurehistoryrecord="{" + "\"measuredValue\"" + ": " + "1.9"+ "}";
		System.out.println(measurehistoryrecord);
		JSONObject req8jsonobject = new JSONObject();
		JSONParser parser8 = new JSONParser();
		req8jsonobject = (JSONObject) parser8.parse(measurehistoryrecord);
		/**
		 * Sending a put request
		 */
		Response res8= peopleResource.path(String.valueOf(first_person_id)).path(randomMeasureType)
				.request(MediaType.APPLICATION_JSON)
		        .accept(MediaType.APPLICATION_JSON)
		        .header("Content-Type", "application/json").post(Entity.entity(req8jsonobject, MediaType.APPLICATION_JSON));
		String res8String=res8.readEntity(String.class);
		/**
		 * Get MID for further query processing of the current value
		 */
		Object req10_Object = parser.parse(res8String);
		JSONObject req10Object = new JSONObject();
		req10Object = (JSONObject) req10_Object;
		/**
		 * Get the mid for further processes
		 */
		long mid = (long) req10Object.get("historyMeasureId");
		System.out.println("Returned Mid is" +mid);
		
		System.out.println("AM working status"+res8.getStatus());
		System.out.println(res8.getMetadata());
		System.out.println(res8.getAllowedMethods());
		/**
		 * manpulte the response if new record is added to measure history
		 */
  		int new_Count=request_Six_Eight(first_person_id,randomMeasureType);
  		/**
  		 * Compare the original and new count for record change
  		 */
  		String result8=new_Count==(Original_Count+1)?"OK":"ERROR";
  		System.out.println("request 3.9");
  		System.out.println(result8);	
  		
  		
  		/********************************
  		 ********** Request 10    *******
  		 ********************************/
  		
  		String measurehistoryUpdateString="{" + "\"value\"" + ": " + "1.5"+ "}";
  		/**
  		 * Change the string to json object
  		 */
  		JSONObject req10jsonobject = new JSONObject();
		JSONParser parser10 = new JSONParser();
		req10jsonobject = (JSONObject) parser10.parse(measurehistoryUpdateString);
		/**
		 * Original value of measure Id
		 */
  		double passedValue=1.5;
  		Response res10=peopleResource.path(String.valueOf(first_person_id)).path(randomMeasureType)
  				                     .path(String.valueOf(mid)).request(MediaType.APPLICATION_JSON)
  				                     .accept(MediaType.APPLICATION_JSON)
  				                     .header("Content-Type", MediaType.APPLICATION_JSON)
  				                     .put(Entity.entity(req10jsonobject, MediaType.APPLICATION_JSON));
  		System.out.println("Response 10 status");
  		System.out.println(res10.getStatus());
  		/**
  		 * Check for the new value
  		 */
  		Response res11=peopleResource.path(String.valueOf(first_person_id))
  				                     .path(randomMeasureType)
                                     .path(String.valueOf(mid)).request(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON)
                                     .get();
  		String res11Result=res11.readEntity(String.class);
  		System.out.println("Resulting string"+res11Result);
  		Object req11JsonObject = parser.parse(res11Result);
  		JSONObject req11=new JSONObject();
  		req11=(JSONObject)req11JsonObject;
  		
  		
  		
		/**
		 * Get the new value
		 */
		double newmeasureValue= (double) req11.get("value");
		String result11=(newmeasureValue==passedValue)?"OK":"ERROR";
		System.out.println("Result 11:"+result11);

  		
		/**********************
		 *******REQUEST 11*****
		 **********************/
		/**
		 * Pass the date range as a parameter and see the result
		 */
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String startDateString = "11/09/2014";
		String endDateString="11/13/2014";
		Date beforedate=null;
		Date afterDate=null;
		try {
			beforedate = formatter.parse(startDateString);
		} catch (java.text.ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		try {
			afterDate=formatter.parse(endDateString);
		} catch (java.text.ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		Response res14= peopleResource.path(String.valueOf(first_person_id)).path(randomMeasureType)
				.queryParam("before", beforedate)
				.queryParam("after", afterDate)
				.request(MediaType.APPLICATION_JSON)
		        .accept(MediaType.APPLICATION_JSON)
		        .header("Content-Type", "application/json").post(Entity.entity(req8jsonobject, MediaType.APPLICATION_JSON));
		String res14AsString=res14.readEntity(String.class);
		System.out.println("Formatted output of Json for daterange");
		System.out.println(JSONFormatter.toJsonFormat(res14AsString));
		
		
		
		
		/***********************
		 *****REQUEST 12 *******
		 ***********************/
		String measureType="Height";
		double min=1.6;
		double max=2.0;
		
		Response res12=peopleResource.queryParam("measureType", measureType)
				                     .queryParam("min", min)
				                     .queryParam("max",max)
				                     .request(MediaType.APPLICATION_JSON)
				                     .accept(MediaType.APPLICATION_JSON)
				                     .header("Content-Type", "application/json")
				                     .get();
		String res12AsString=res12.readEntity(String.class);
		System.out.println("Formatted output of Json");
		System.out.println(JSONFormatter.toJsonFormat(res12AsString));
}
public static int request_Six_Eight(int personId,String measureName)
{
	int number_Of_Records=0;
	/**
	 * Create a response object
	 */
	WebTarget personTarget=peopleResource.path(String.valueOf(personId));
	WebTarget measureTarget=personTarget.path(measureName);
	Response res=measureTarget.request()
	                        .accept(MediaType.APPLICATION_JSON)
	                        .header("Content-Type",MediaType.APPLICATION_JSON)
	                        .get();
	System.out.println("First Request"+res.getStatus());
	/**
	 * Create a json object and corresponding json array
	 */
	String resResult=res.readEntity(String.class);
	JSONParser parser=new JSONParser();
	Object req9JsonObject=null;
	try {
		req9JsonObject = parser.parse(resResult);
	} catch (ParseException e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	}
	JSONArray jsonArray9 = (JSONArray)req9JsonObject;
	number_Of_Records=jsonArray9.size();
	System.out.println("Number of records:"+number_Of_Records);
	return number_Of_Records;
	
}
	
	/**
	 * 
	 * @param personId
	 * @param measureName
	 * @return
	 * @throws ParseException
	 */
	public static int requestSix(String personId, String measureName) throws ParseException {
		/**
		 * Variables used for string manipulation
		 * reset variales everytime ther are called
		 * @mid takes the mid of one measure for further query processes
		 * @measureType takes the measure name of measure type
		 */
		
		StringBuilder responseResult=new StringBuilder();
		int totalMeasureRecords=0;
		/**
		 * first path for /person/{id}
		 * second path for /person/{id}/{measureName}
		 */
		WebTarget PersonIdTarget = peopleResource.path(personId);
		WebTarget personMeasuretarget=PersonIdTarget.path(measureName);
		
		Invocation.Builder invocationBuilderReq6 = personMeasuretarget
				.request(MediaType.APPLICATION_JSON);
		invocationBuilderReq6.accept(MediaType.APPLICATION_JSON);
		invocationBuilderReq6.header("Accept", "application/json");
		invocationBuilderReq6.header("Content-Type", "application/json");
		
		/**
		 * Parse response into string and determine the amount of records returned
		 * per query/response
		 */
		Response req9JsonResponse = invocationBuilderReq6.get();
		System.out.println(req9JsonResponse);
		responseResult.append(req9JsonResponse.readEntity(String.class).toString());
		JSONParser parser=new JSONParser();
		Object reqJsonObject = parser.parse(responseResult.toString());
		JSONArray jsonArray9 = (JSONArray) reqJsonObject;
		totalMeasureRecords=jsonArray9.size();
		/**
		 * Once we get an object containing a measure,we take mid and measure name for further queries
		 */
		System.out.println("+========================================");
		System.out.println("totalMeasureRecords"+totalMeasureRecords);
		System.out.println("Measure id"+measure_ID);
		System.out.println("measure type"+measure_Type);
		System.out.println(measure_Type.length());
		System.out.println(measure_Type.length()<3);
		if((totalMeasureRecords>0)&&(measure_ID<1)&&(measure_Type.length()<3))
		{
			measure_Type.setLength(0);
			JSONObject req7JsonObject=(JSONObject)jsonArray9.get(0);
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(req7JsonObject.toJSONString());
			measure_ID=(long) req7JsonObject.get("historyMeasureId");
			System.out.println();
			measure_Type.append(measureName);
		}
		System.out.println("Our mid is"+measure_ID);
		System.out.println("our measure name"+measure_Type);
		/**
		 * Clear responseResult string for further query result string
		 */
		responseResult.setLength(0);
		return totalMeasureRecords;
	}
	/**
	 * 
	 * @param PersonId
	 *        Passed person Id as a resource path
	 * @return
	 */
	public static Response requestTwo(int PersonId)
	{
		WebTarget personResource = peopleResource.path(String
				.valueOf(first_person_id));
		Invocation.Builder request2Builder = personResource
				.request(MediaType.APPLICATION_JSON);
		request2Builder.accept(MediaType.APPLICATION_JSON);
		request2Builder.header("Accept", "application/json");
		request2Builder.header("Content-Type", "application/json");
		Response req2JsonResponse = request2Builder.get();
		return req2JsonResponse;
	}
}
