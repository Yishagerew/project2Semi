package eHealth.rest.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.*;

import eHealth.rest.dao.HealthInfoDao;
import eHealth.rest.model.Person;

public class PersonTest {
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private EntityTransaction tx;
@Test
public void getListOfPersons()
{
	List<Person>peoples=em.createNamedQuery("Person.findAll",Person.class).getResultList();
	for(Person people:peoples)
	{
		System.out.println("First Name:"+people.getFirstName());
	}
}
@Ignore
@Test
public void addNewPerson()
{
	//List<Person>peoples=em.createNamedQuery("Person.findAll",Person.class).getResultList();
	Person p= new Person();
	p.setPersonId(12);
	p.setUserName("lulie");
	p.setPassword("lulie");
	p.setFirstName("lulie");
	tx.begin();
	em.persist(p);
	tx.commit();
	assertNotNull("Id cannot be null",p.getPersonId());
	//int NewPersonId=p.getPersonId();
System.out.println("=============Querying for the new person===============");
/**
em.getTransaction().begin();
Person newPerson=em.createNamedQuery("Person.findByPersonId",Person.class).setParameter("personId", NewPersonId).getSingleResult();
assertEquals("New added person returned by the query",1222,newPerson.getPersonId());
System.out.println("===============Deleting the added row=================");
System.out.println("Person details");
System.out.println("First Name"+newPerson.getFirstName());
System.out.println("Created Date"+newPerson.getCreatedDate());
em.remove(newPerson);
em.getTransaction().commit();
*/

}
@BeforeClass
public static void beforeClass() {
System.out.println("Testing JPA on lifecoach database using 'introsde-jpa' persistence unit");
emf = Persistence.createEntityManagerFactory("healthbook-jpa");
System.out.println("Created EMF is "+emf);
em = emf.createEntityManager();
System.out.println("Entity manager created++++++++++++++++++++++++++++++++++"+ em);
}
@AfterClass
public static void afterClass() {
em.close();
emf.close();
}
@Before
public void before() {
	tx = em.getTransaction();
}
@Ignore
@Test
public void getSelectedColumnFromPerson()
{
	System.out.println("We are called");
	EntityManager em=HealthInfoDao.instance.getEntityManager();
	System.out.println("Entity manager created");
	Query query=em.createNamedQuery("Person.findMainInfo",Person.class);
	System.out.println("Query Building tried");
	@SuppressWarnings("unchecked")
	List<Object[]>allpersons=query.getResultList();
	assertEquals("siez of person should be one",1,allpersons.size());
	System.out.println(allpersons.get(0)[1]);
	System.out.println("================================================");
	List<Person>person=new ArrayList<Person>();
	
	for(int i=0;i<allpersons.size();i++)
	{
		person.get(0).setFirstName(allpersons.get(i)[1].toString());
		person.get(0).setMiddleName(allpersons.get(i)[1].toString());;
		
	}
	System.out.println("===============================================");
	for(Person persons:person)
	{
		System.out.println("My first Name"+persons.getLastName());
	}
	
	//System.out.println("List tried to retrieved from the query");
	//Person person=(Person)peoples.get(0);
	
//	for(Person p:peoples)
//	{
//	System.out.println("First Name:"+person.getFirstName());
//	System.out.println("Middle Name"+person.getMiddleName());
//	System.out.println("Last Name:"+person.getLastName());
////	}
}

}
