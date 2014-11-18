/**
 * @author Yishagerew L.
 * @DateModified Nov 13, 201411:45:26 AM
 */
package eHealth.rest.business;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import eHealth.rest.dao.HealthInfoDao;
import eHealth.rest.model.MeasureDefinition;
import eHealth.rest.model.MeasureHistory;
import eHealth.rest.model.Person;

public class MeasureHistoryImpl {
	
	/**
	 * The following method return the history of the person identified by MId,id of the measure history
	 * and id of the person,personId
	 * 
	 * @param mid
	 *        Measure name Id like Id of Height or Weight
	 * @param personId
	 *        Id of person 
	 * @return
	 */
public static MeasureHistory getHistoryByMid(int mid,int personId)
{
	EntityManager em=HealthInfoDao.instance.getEntityManager();
	Query historyQuery=em.createNamedQuery("findMeasuredValueByMid",MeasureHistory.class)
			.setParameter(1, mid)
			.setParameter(2,personId );
		MeasureHistory mHistory=(MeasureHistory) historyQuery.getSingleResult();
	return mHistory;
}

public static MeasureHistory getHistoryByMidNamed(int mid,Person person)
{
	System.out.println("Person"+person);
	EntityManager em=HealthInfoDao.instance.getEntityManager();
	//Person person2=PersonImpl.getPersonById(personId)
	System.out.println("Mid is"+mid);
	Query historyQuery=em.createNamedQuery("findMeasuredValueByMidNamed",MeasureHistory.class)
			.setParameter("person",person)
			.setParameter("historyMeasureId", mid);
	System.out.println("After query build");
	try
	{
		MeasureHistory mHistory=(MeasureHistory) historyQuery.getSingleResult();
		return mHistory;
	}
	catch(Exception e)
	{
		return null;
	}
}
@SuppressWarnings("unchecked")
public static List<MeasureHistory> getPersonMeasureHistory(Person person,MeasureDefinition mDef)
{
	EntityManager em=HealthInfoDao.instance.getEntityManager();
	//Person person2=PersonImpl.getPersonById(personId)
	Query historyQuery=em.createNamedQuery("findHistoryByPersonMDefinition",MeasureHistory.class)
			.setParameter("person", person)
			.setParameter("measureDefinition",mDef );
		List<MeasureHistory> mHistory=historyQuery.getResultList();
	return mHistory;
}

/**
 * 
 * @param mHistory
 *        Measure history object
 */
public static void  updateMeasureHistory(MeasureHistory mHistory)
{
	EntityManager em=HealthInfoDao.instance.getEntityManager();
	EntityTransaction tx=em.getTransaction();
	tx.begin();
	em.merge(mHistory);
	tx.commit();
}

/**
 * 
 * @param mHistory
 *        Measure History object to persist
 */
public static void addMeasureHistory(MeasureHistory mHistory)
{
	EntityManager em=HealthInfoDao.instance.getEntityManager();
	EntityTransaction tx=em.getTransaction();
	tx.begin();
	em.persist(mHistory);
	tx.commit();
}

/**
 * 
 * @param startDate
 *        A start date for getting measures recorded from this time 
 * @param endDate
 *        An end date for getting measures recorded upto this time
 * @return
 *       Returns a measure history object
 */
public static List<MeasureHistory> getMeasureHistoryByDateRange(Date startDate,Date endDate,int personId,String measureName)
{
	EntityManager em=HealthInfoDao.instance.getEntityManager();
	Query query=em.createNamedQuery("MeasureHistory.findByDateRange",MeasureHistory.class);
	query.setParameter("startDate", startDate);
	query.setParameter("endDate", endDate);
	query.setParameter("person", PersonImpl.getPersonById(personId));
	query.setParameter("measuredefinition", MeasureResolverImpl.getMeasureNameId(measureName));
	@SuppressWarnings("unchecked")
	List<MeasureHistory>mHistory=query.getResultList();
	return mHistory;
}
}
