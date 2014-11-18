package eHealth.rest.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import eHealth.rest.business.MeasureResolverImpl;
import eHealth.rest.dao.HealthInfoDao;
import eHealth.rest.model.MeasureDefinition;
import eHealth.rest.model.MeasureHistory;
import eHealth.rest.model.Person;

public class MeasureHistoryTest {
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private EntityTransaction tx;

	@BeforeClass
	public static void beforeClass() {
		System.out
				.println("Testing JPA on lifecoach database using 'introsde-jpa' persistence unit");
		emf = Persistence.createEntityManagerFactory("healthbook-jpa");
		System.out.println("Created EMF is " + emf);
		em = emf.createEntityManager();
		System.out
				.println("Entity manager created++++++++++++++++++++++++++++++++++"
						+ em);
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
	public void getListOfMeasureHistory() {
		MeasureResolverImpl resolver = new MeasureResolverImpl();
		// int measureId = resolver.getMeasureNameId(measureName);
		EntityManager em = HealthInfoDao.instance.getEntityManager();
		@SuppressWarnings("static-access")
		MeasureDefinition mDef = resolver.getMeasureNameId("Height");
		Query personQuery = em.createNamedQuery("Person.findByPersonId",
				Person.class).setParameter("personId", 12);
		Person person = (Person) personQuery.getSingleResult();
		Query queryMeasure = em.createNamedQuery(
				"MeasureHistory.findByPersonId", MeasureHistory.class);
		queryMeasure.setParameter("measuredefinition", mDef);
		queryMeasure.setParameter("person", person);
		@SuppressWarnings("unchecked")
		List<MeasureHistory> mHistory = queryMeasure.getResultList();
		for (MeasureHistory mhistory : mHistory) {
			System.out.println(mhistory.getHistoryMeasureId());
			System.out.println(mhistory.getValue());
		}
	}
@Ignore
@Test
	public void addMeasureHistory()
	{
		MeasureResolverImpl resolver = new MeasureResolverImpl();
		// int measureId = resolver.getMeasureNameId(measureName);
		EntityManager em = HealthInfoDao.instance.getEntityManager();
		Query personQuery = em.createNamedQuery("Person.findByPersonId",
				Person.class).setParameter("personId", 12);
		Person person = (Person) personQuery.getSingleResult();
		@SuppressWarnings("static-access")
		MeasureDefinition mDef = resolver.getMeasureNameId("Height");
		MeasureHistory mHistory=new MeasureHistory();
		mHistory.setMeasuredefinition(mDef);
		mHistory.setPerson(person);
		mHistory.setValue(1.89);
		tx.begin();
		em.persist(mHistory);
		tx.commit();	
		em.getTransaction().begin();
        Query query3=em.createNamedQuery("MeasureHistory.findAll",MeasureHistory.class);
		@SuppressWarnings("unchecked")
		List<MeasureHistory>mhistories=query3.getResultList();
		
		System.out.println("===============================================");
		for(MeasureHistory mhistory:mhistories)
		{
			System.out.println(mhistory.getHistoryMeasureId());
			System.out.println(mhistory.getUpdateDate());
		}
		
		em.getTransaction().commit();
		
	}
/**
 * get measure history identified by person id and measure id
 * 
 */
@Test
public void getListOfMeasureHistoryByPersonandMID() {
	EntityManager em=HealthInfoDao.instance.getEntityManager();
	Query historyQuery=em.createNamedQuery("findMeasuredValueByMid",MeasureHistory.class)
			.setParameter(1, 951)
			.setParameter(2,12 );
		MeasureHistory mHistory=(MeasureHistory) historyQuery.getSingleResult();
	System.out.println(mHistory.getHistoryMeasureId());
	System.out.println(mHistory.getValue());
}

}
