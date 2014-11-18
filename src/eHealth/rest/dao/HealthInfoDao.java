/**
 * @author Yishagerew.L
 * @DateModified Nov 6, 20147:26:02 PM
 */
package eHealth.rest.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

                                   /*********************
                                    * ****EXTRA #1 ******
                                    * *******************/
                                    
public enum HealthInfoDao {
	 instance;
	 private static EntityManagerFactory emf;
	 /**
	  * Demonstrates the use of ThreadLocal design pattern 
	  * EntityManager is not threadsafe
	  * We can implement ThreadLocal pattern for avoiding thread safety issues.
	  * An entity manager will be checked if its alive
	  */
	 public static final ThreadLocal<EntityManager>threadlocal=new ThreadLocal<EntityManager>();
	 public static EntityManagerFactory getEntityManagerfactory()
	 {
	 if(emf==null)
	 {
	 emf=Persistence.createEntityManagerFactory("healthbook-jpa");
	 }
	 return emf;
	 }
	 public EntityManager getEntityManager()
	 {
	 EntityManager em = threadlocal.get();
	 if(em==null)
	 {
	 em=getEntityManagerfactory().createEntityManager();
	 threadlocal.set(em);
	  
	 }
	 return em;
}
	
}
