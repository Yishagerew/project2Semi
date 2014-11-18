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

import eHealth.rest.dao.HealthInfoDao;
import eHealth.rest.model.MeasureDefinition;

public class MeasureDefinitionTest {
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
	public void addMeasureDefinition() {
		MeasureDefinition mDefinition = new MeasureDefinition();
		mDefinition.setMeasureDefName("BMI");
		tx.begin();
		em.persist(mDefinition);
		tx.commit();
	}

	
	@Test
	public void getMeasureDefinitionList() {
		EntityManager em = HealthInfoDao.instance.getEntityManager();
		Query query = em.createNamedQuery("MeasureDefinition.findAll",
				MeasureDefinition.class);
		@SuppressWarnings("unchecked")
		List<MeasureDefinition> mDefinitions = query.getResultList();
		for (MeasureDefinition definitions : mDefinitions) {
			System.out.println("Definition Id" + definitions.getMeasureDefId());
			System.out.println("Definition Name"
					+ definitions.getMeasureDefName());
		}
	}
@Ignore
	@Test
	public void getMeasureIdFromName() {
		String MeasureName = "Height";
		int measureId;
		EntityManager em = HealthInfoDao.instance.getEntityManager();
		Query query = em.createNamedQuery("MeasureDefinition.getMeasureId",
				MeasureDefinition.class);
		query.setParameter("measureDefName",MeasureName);
		query.setParameter("personId", 122);
		MeasureDefinition mDef = new MeasureDefinition();
		mDef = (MeasureDefinition) query.getSingleResult();
		measureId = mDef.getMeasureDefId();
		System.out.println("Measure Id iss===============" + measureId);
	}

}
