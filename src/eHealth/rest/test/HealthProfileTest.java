package eHealth.rest.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eHealth.rest.dao.HealthInfoDao;
import eHealth.rest.model.HealthProfile;
import eHealth.rest.model.MeasureDefinition;
import eHealth.rest.model.Person;

public class HealthProfileTest {
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private EntityTransaction tx;
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
	@Test
	public void addHealthProfileHistory()
	{
		EntityManager em=HealthInfoDao.instance.getEntityManager();
		Query query=em.createNamedQuery("Person.findByPersonId",Person.class).setParameter("personId", 12);
		Person person=(Person) query.getSingleResult();
		assertEquals(" The id is not retrieced",12,person.getPersonId());
		Query query2=em.createNamedQuery("MeasureDefinition.findByMeasureDefId",MeasureDefinition.class).setParameter("measureDefId", 12);
		MeasureDefinition mDef=new MeasureDefinition();
		mDef=(MeasureDefinition) query2.getSingleResult();
		assertEquals("The Measure definition id is 12",12,mDef.getMeasureDefId());
		HealthProfile hProfile=new HealthProfile();
		hProfile.setMeasuredValue(1.74);
		hProfile.setPerson(person);
		hProfile.setMeasuredefinition(mDef);
		tx=em.getTransaction();
		tx.begin();
		em.persist(hProfile);
		tx.commit();
		
		em.getTransaction().begin();
		//HealthProfile hprofile=new HealthProfile();
		Query query3=em.createNamedQuery("HealthProfile.findAll",HealthProfile.class);
		@SuppressWarnings("unchecked")
		List<HealthProfile>hprofile=query3.getResultList();
		for(HealthProfile hprofiles:hprofile)
		{
			//System.out.println(hprofiles.getMeasuredefinition().getMeasureDefName());
			System.out.println(hprofiles.getMeasuredValue());
		}
		assertEquals("The number of records should be one",2,hprofile.size());
		
		
	}
}
