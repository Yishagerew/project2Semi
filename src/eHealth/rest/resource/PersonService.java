/**
 * @author Yishagerew L.
 * @DateModified Nov 12, 20142:08:54 AM
 */
package eHealth.rest.resource;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import eHealth.rest.business.PersonImpl;
import eHealth.rest.dao.HealthInfoDao;
import eHealth.rest.model.Person;
public class PersonService {

	@Context
	Request request;
	@Context
	UriInfo uriInfo;
	int personId;

	public PersonService(UriInfo uriInfo, Request request, int personId) {
		this.request = request;
		this.uriInfo = uriInfo;
		this.personId = personId;

	}
	
						/********************
						 * *****REQUEST #2***
						 *******************/
								
@SuppressWarnings("static-access")
/**
 * Accepts GET requests and returns a particular person identified by person Id
 * @return
 */
@GET
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public Person getPersonDetails()
{
	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
	System.out.println("Atleast called");
	EntityManager em=HealthInfoDao.instance.getEntityManager();
	Person person=new Person();
	Query personQuery=em.createNamedQuery("Person.findByPersonId",Person.class);
	personQuery.setParameter("personId", personId);
	person=(Person) personQuery.getSingleResult();
	System.out.println("First Name"+person.getFirstName());
	System.out.println("Id" +person.getPersonId());
	Response res = null;
	res.status(Response.Status.OK).entity(person).header("Location", uriInfo.getAbsolutePath().getPath()).build();
	return person;
}
@Path("{MeasureId}")
public MeasureService getMeasureDetails(@PathParam("MeasureId") String MeasureName)
{
	return new MeasureService(uriInfo,request,personId,MeasureName);
}

                       /*********************
                        * *****REQUEST #3****
                        *********************/

/**
 * 
 * @param person
 *        A person object
 * @return
 */
@PUT
@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public Response updatePersonResource(Person person)
{
/**
 * calls a method which returns the person with the given id
 */
	System.out.println("Put method called");
Person oldPerson=PersonImpl.getPersonById(this.personId);
System.out.println("Person returned");
Response res;
if(oldPerson==null)
{
	/**
	 * Add the new person 
	 */
	res=Response.status(Response.Status.NO_CONTENT).entity(person).build();
	System.out.println("No person returned");	
}
else
{
	/**
	 * update the field
	 */
	person.setPersonId(this.personId);
	Person.updatePerson(person);
	res=Response.status(Response.Status.OK).entity(person).build();

	System.out.println("Person returned and updated successfully");
	
}
return res;
}

                                     /******************
                                      *****REQUEST #5*** 
                                      ******************/

/**
 * The following method allows to server a delete request
 * Which removes the person object identified by PersonId
 */
@DELETE
public void removePerson()
{
	Person person=PersonImpl.getPersonById(personId);
	EntityManager em=HealthInfoDao.instance.getEntityManager();
	EntityTransaction tx=em.getTransaction();
	tx.begin();
	em.remove(person);
	tx.commit();
	System.out.println("Person Removed from database");
}


}
