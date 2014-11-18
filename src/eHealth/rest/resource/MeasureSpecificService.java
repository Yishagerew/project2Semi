/**
 * @author Yishagerew L.
 * @DateModified Nov 12, 20142:09:29 AM
 */
package eHealth.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;

import eHealth.rest.business.MeasureHistoryImpl;
import eHealth.rest.business.MeasureResolverImpl;
import eHealth.rest.business.PersonImpl;
import eHealth.rest.model.MeasureDefinition;
import eHealth.rest.model.MeasureHistory;
import eHealth.rest.model.Person;


public class MeasureSpecificService {
	
@Context
UriInfo measureSpecificUriInfo;
@Context
Request measureSpecificrequest;
int personId;
int mid;

/**
 * @measureName is used for put operation since we have to know the exact URL
 */
String measureName;

	public MeasureSpecificService(int measureId, int personId,
			UriInfo measureUriInfo, Request measureRequest,String measureName) {
		this.measureSpecificUriInfo=measureUriInfo;
		this.personId=personId;
		this.mid=measureId;
		this.measureSpecificrequest=measureRequest;
		this.measureName=measureName;
		

	}
	
									/******************
									 * **REQUEST #7****
									 ******************/
	
/**
 * The following method allows to retrieve the value of a specific measure value
 * Identified by Mid
 * @return
 */
@GET
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public Response getSpecificMeasureValue()
{
	System.out.println("Atleast we are under get");
	Person person2=PersonImpl.getPersonById(this.personId);
	System.out.println("Person returned");
		MeasureHistory mHistory=MeasureHistoryImpl.getHistoryByMidNamed(this.mid, person2);
		System.out.println("Measure history returned");
		Response res=null;
		System.out.println("Measure History with MID and person id is retrieved from here");
		System.out.println(mHistory.getPerson());
		System.out.println(mHistory.getHistoryMeasureId());
		System.out.println("MID is"+mHistory.getHistoryMeasureId());
		res=Response.status(Response.Status.OK)
			    .entity(mHistory)
			    .header("Location", measureSpecificUriInfo.getAbsolutePath().getPath()).build();
		return res ;	
	
}

                            /*****************
                             ****REQUEST #10**
                             *****************/
/**
 * The following updates a measure history row .Since it is implemented with PUT,it will be
 * a full update
 * @param mhistory
 *        A measure history object
 * @return
 */
@PUT
@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public Response updateMeasure(MeasureHistory mhistory)
{
	System.out.println("We are under put of specific resource");
	Response res=null;
	Person person=PersonImpl.getPersonById(this.personId);
	System.out.println("Person returned");
	MeasureHistory mHistory=MeasureHistoryImpl.getHistoryByMidNamed(this.mid, person);
	
	/**
	 * make a change of the new value to meausre history
	 */
	
	/**
	 * If resource is not found,create one,based on REST Architecture spec.
	 */
	if(mHistory==null)
	{
		System.out.println("History is null");
		mhistory.setPerson(person);
		
		/**
		 * retrieve measuredefinition object
		 */
		MeasureDefinition mDef=MeasureResolverImpl.getMeasureNameId(this.measureName);
		/**
		 * add to measure history object
		 */
		mhistory.setMeasuredefinition(mDef);
		
	MeasureHistoryImpl.addMeasureHistory(mhistory);	
	
	res=Response.status(Response.Status.CREATED)
			    .entity(mHistory)
			    .tag("Ok")
			    .header("Location", measureSpecificUriInfo.getAbsolutePath().getPath()).build();
	 
	
	}
	else
	{
		mHistory.setValue(mhistory.getValue());
		MeasureHistoryImpl.updateMeasureHistory(mHistory);
		res=Response.status(Response.Status.OK)
				    .entity("Resource successfully updated")
				    .header("Location", measureSpecificUriInfo.getAbsolutePath().getPath()).build();
	}
	return res;
	
}

}
