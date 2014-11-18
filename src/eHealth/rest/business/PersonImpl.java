/**
 * @author Yishagerew L.
 * @DateModified Nov 12, 20145:00:54 PM
 */
package eHealth.rest.business;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import eHealth.rest.dao.HealthInfoDao;
import eHealth.rest.model.Person;

public class PersonImpl {
	/**
	 * This method is for finding the person identified by its Id,personId 
	 * and returned to the caller
	 * @param personId
	 *        Person id used for search
	 * @return
	 */
public static Person getPersonById(int personId)
{
	System.out.println("I am under person");
	EntityManager em=HealthInfoDao.instance.getEntityManager();
	System.out.println("Person Id"+personId);
	Query query=em.createNamedQuery("Person.findByPersonId",Person.class).setParameter("personId", personId);
	System.out.println("Query executed");
	try
	{
	Person person=(Person) query.getSingleResult();
	System.out.println("Found person is"+person);
	return person;
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
		return null;
	}
}
public static void addNewPerson(Person person) {
	EntityManager em = HealthInfoDao.instance.getEntityManager();
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	em.persist(person);
	tx.commit();
}
}
