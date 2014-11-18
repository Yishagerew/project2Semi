/**
 * @author Yishagerew L.
 * @DateModified Nov 12, 20142:09:10 AM
 */
package eHealth.rest.resource;

//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;







import eHealth.rest.business.HealthProfileResolverImpl;
//import eHealth.rest.business.HealthProfileResolverImpl;
import eHealth.rest.business.PersonImpl;
import eHealth.rest.dao.HealthInfoDao;
import eHealth.rest.delegate.PersonDelegate;
import eHealth.rest.model.HealthProfile;
//import eHealth.rest.model.HealthProfile;
import eHealth.rest.model.Person;
import eHealth.rest.transferObjects.TPerson;
@Path("/person")
public class PeopleService {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	                              /**************************
	                               * ***REQUEST #1 **********
	                               **************************/
	
	/**
	 * The following accepts get requests and return all peoples with some basic parameters
	 * It is implemented using Transfer object design pattern with the help of
	 * Dozer mappings
	 * @return
	 */
/**
@GET
@Produces({MediaType.TEXT_XML,MediaType.TEXT_HTML,MediaType.APPLICATION_JSON})
public static List<TPerson> getListOfPerson()
{
	
	EntityManager em=HealthInfoDao.instance.getEntityManager();
	Query query=em.createNamedQuery("Person.findAll",Person.class);
	@SuppressWarnings("unchecked")
	List<Person> peoples=query.getResultList();
	/**Delegate class for mapping entity class to transfer object 
	PersonDelegate pDelegate=new PersonDelegate();
	List<TPerson>mappedPeoples=pDelegate.mapFromPerson(peoples);
	return mappedPeoples ;
} */
@Path("{personId}")
public PersonService getPersonResource(@PathParam("personId") int personId)
{
	return new PersonService(uriInfo,request,personId);
}
							/********************
							 * REQUEST #4********
							 * ******************/
 /**
@POST
@Produces(MediaType.TEXT_HTML)
@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
public void addNewPerson(@FormParam("personId") int personId,
						 @FormParam("firstName") String firstName,
						 @FormParam("middleName") String middleName,
						 @FormParam("lastName") String lastName,
						 @FormParam("measureDefId") int measureDefId,
						 @FormParam("measuredValue") double measuredValue,
						 @FormParam("email") String email,
						 @FormParam("userName") String userName,
						 @FormParam("password") String password,
						 @Context HttpServletResponse response) throws IOException
{
	if((measureDefId>0)&&(measuredValue>0))
	{
		MeasureDefinition mDef=new MeasureDefinition();
		mDef=MeasureResolverImplUtil.getMeasureDefinition(measureDefId);
		List<HealthProfile> healthprofiles=new ArrayList<HealthProfile>();
		HealthProfile hprofile=new HealthProfile(measuredValue,mDef);
		healthprofiles.add(hprofile);
		Person newPerson=new Person(personId,firstName,middleName,lastName,
									email,userName,password,healthprofiles);
		Person.addNewPerson(newPerson);
		response.sendRedirect("../Confirmation.html");
	}
	else
	{
	Person newPerson=new Person(personId,firstName,middleName,lastName,email,userName,password);
	Person.addNewPerson(newPerson);
	response.sendRedirect("../confirmation.html");
	}
	
} */

@SuppressWarnings("static-access")
@POST
@Produces(MediaType.APPLICATION_JSON)
public  Response addNewPerson(Person person)
{
	System.out.println("Called from client");
	Response res = null;
	PersonImpl.addNewPerson(person);
	res.status(Response.Status.OK)
	        .entity("New person added successfully")
	        .header("Location", uriInfo.getAbsolutePath().getPath()+String.valueOf(person.getPersonId())).build();
	return  res;
}


                               /*******************
                                *****REQUEST #12***
                                *******************/
/**
 * 
 * @param measureName
 *        The name of the measure: Example:Height
 * @param maxMeasureValue
 *        The maximum value of the measured value 
 * @param minMeasureValue
 *        The minimum value of the measured value 
 * @return
 */

@GET
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public Response getListOfPeopleOnMeasures(@QueryParam("measureType") String measureName,
											 @QueryParam("max") Double maxMeasureValue,
											 @QueryParam("min") Double minMeasureValue)
 {
	Response res=null;
	/**
	 * If measure name is not given or both ranges are empty,select all persons
	 */
	if((measureName==null)||((maxMeasureValue==null)) && (minMeasureValue==null))
	{
		int numberOfPeoples;
		String statusTag="OK";
		EntityManager em=HealthInfoDao.instance.getEntityManager();
		Query query=em.createNamedQuery("Person.findAll",Person.class);
		@SuppressWarnings("unchecked")
		List<Person> peoples=query.getResultList();
		/**
		 * If the number of people returned less than 3,it given an error for the client
		 */
		numberOfPeoples=peoples.size();
		if(numberOfPeoples<3)
		{
			statusTag="ERROR";
		}
		/**Delegate class for mapping entity class to transfer object */
	//	PersonDelegate pDelegate=new PersonDelegate();
		//List<TPerson>mappedPeoples=pDelegate.mapFromPerson(peoples);
		res=Response.status(Response.Status.OK)
			    .entity(peoples)
			    .tag(statusTag)
			    .header("Location", uriInfo.getPath()).build();
		return res ;	
	}
	else
	{
	List<Person>person=new ArrayList<>();
	List<HealthProfile>hprofiles=new ArrayList<>();
	/**
	 * Select all health profile objects satisfying the criteria
	 */
	/**
	 * If minMeasureValue is given only
	 */
	if(minMeasureValue==null)
	{
		hprofiles=HealthProfileResolverImpl.getHealthProfileOnMin(minMeasureValue, measureName);	
	}
	/**
	 * If max Measure Value is only given
	 */
	else if(maxMeasureValue==null)
	{
		hprofiles=HealthProfileResolverImpl.getHealthProfileOnMax(maxMeasureValue, measureName);	

	}
	/**
	 * If both max and min are given
	 */
	else
	{
	 hprofiles=HealthProfileResolverImpl.getHealthProfileOnMinMax(minMeasureValue, maxMeasureValue, measureName);
	}
	/**
	 * Add each person object from healthprofile object to person list
	 * Note:Person is unique for each health profile and measure type
	 * because duplicates are on measure history not on healthprofile model
	 */
	for(HealthProfile profiles:hprofiles)
	{
		person.add(profiles.getPerson());
	}

	res=Response.status(Response.Status.CREATED)
		    .entity(person)
		    .header("Location", uriInfo.getAbsolutePath().getPath()).build();
	return res ;	
	}
}


}

