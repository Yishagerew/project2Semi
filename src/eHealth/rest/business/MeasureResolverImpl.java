/**
 * @author Yishagerew L.
 * @DateModified Nov 12, 20147:39:17 PM
 */

package eHealth.rest.business;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import eHealth.rest.dao.HealthInfoDao;
import eHealth.rest.model.MeasureDefinition;

public class MeasureResolverImpl {
	/**
	 * getMeasureNameId used to return measuredefinition object given 
	 * a measure name example:Height
	 * @param MeasureName
	 * @return
	 */
public static MeasureDefinition getMeasureNameId(String MeasureName)
{
	EntityManager em=HealthInfoDao.instance.getEntityManager();
	Query query=em.createNamedQuery("MeasureDefinition.getMeasureId",MeasureDefinition.class).setParameter("measureDefName", MeasureName);
	MeasureDefinition mDef=new MeasureDefinition();
	mDef=(MeasureDefinition) query.getSingleResult();
	return mDef;
	
}
/**
 * getMeasureDefinition returns measureDefinition object identified by measureDefId
 * @param measureDefId
 *        Measure Definition id
 * @return
 */
public static MeasureDefinition getMeasureDefinition(int measureDefId)
{
	EntityManager em=HealthInfoDao.instance.getEntityManager();
	Query query=em.createNamedQuery("MeasureDefinition.findByMeasureDefId",MeasureDefinition.class).setParameter("measureDefId", measureDefId);
	MeasureDefinition mDef=new MeasureDefinition();
	mDef=(MeasureDefinition) query.getSingleResult();
	return mDef;
}
}
