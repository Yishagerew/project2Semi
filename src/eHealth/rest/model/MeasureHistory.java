package eHealth.rest.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import eHealth.rest.dao.HealthInfoDao;

/**
 * The persistent class for the MEASUREHISTORY database table.
 * 
 */
@Entity
@Table(name = "MeasureHistory")
@NamedQueries({
		@NamedQuery(name = "MeasureHistory.findAll", query = "SELECT m FROM MeasureHistory m"),
		@NamedQuery(name = "MeasureHistory.findByPersonId", query = "SELECT m FROM MeasureHistory m WHERE m.person=:person and m.measuredefinition=:measuredefinition"),
		@NamedQuery(name="MeasureHistory.findByMID",query="SELECT m.historyMeasureId,m.value FROM MeasureHistory m WHERE m.historyMeasureId=:historyMeasureId and m.person=:person"),
		@NamedQuery(name="MeasureHistory.findByDateRange",query="SELECT m FROM MeasureHistory m WHERE m.person=:person and m.measuredefinition=:measuredefinition and m.dateCreated BETWEEN :startDate and :endDate"),
		@NamedQuery(name="findMeasuredValueByMidNamed",query="SELECT m FROM MeasureHistory m WHERE m.person=:person and m.historyMeasureId=:historyMeasureId"),
		@NamedQuery(name="findHistoryByPersonMDefinition",query="SELECT m FROM MeasureHistory m WHERE m.person=:person and m.measuredefinition=:measureDefinition")
})
@NamedNativeQuery(name = "findMeasuredValueByMid", query = "SELECT m.historyMeasureId,m.value FROM MeasureHistory m WHERE m.historyMeasureId=? and m.personId=?",resultClass=MeasureHistory.class)
@XmlRootElement(name = "Measures")
@XmlAccessorType(XmlAccessType.FIELD)
public class MeasureHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableGenerator(name = "sqlite_measureHistory", table = "sqlite_sequence", pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "Measure_History_Gen")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sqlite_measureHistory")
	@Column(name = "\"historyMeasureId\"")
	private int historyMeasureId;
	@Column(name = "\"dateCreated\"")
	private String dateCreated;
	/**
	 * @Column(name="\"measureDefId\"") private int measureDefId;
	 */

	@Column(name = "\"updateDate\"")
	private String updateDate;

	@Column(name = "\"value\"")
	private double value;

	// bi-directional one-to-one association to MeasureDefinition
	@OneToOne
	@JoinColumn(name = "measureDefId", referencedColumnName = "measureDefId", insertable = true, updatable = true)
    @XmlTransient private MeasureDefinition measuredefinition;

	// bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name = "personId", referencedColumnName = "personId", insertable = true, updatable = true)
	@XmlTransient private Person person;

	public MeasureHistory() {
	}
/**
 * 
 * @param measuredValue
 *        Value of the particular measured history 
 * @param person
 *        Person object for this history
 * @param mDef
 *        Measure definition object for getting definitions of the measure
 */
	public MeasureHistory(double measuredValue, Person person,
			MeasureDefinition mDef) {
		this.person= person;
		this.measuredefinition=mDef;
		this.value=measuredValue;
	}

	public String getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getHistoryMeasureId() {
		return this.historyMeasureId;
	}

	public void setHistoryMeasureId(int historyMeasureId) {
		this.historyMeasureId = historyMeasureId;
	}

	/**
	 * public int getMeasureDefId() { return this.measureDefId; }
	 * 
	 * public void setMeasureDefId(int measureDefId) { this.measureDefId =
	 * measureDefId; }
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	public MeasureDefinition getMeasuredefinition() {
		return this.measuredefinition;
	}

	public void setMeasuredefinition(MeasureDefinition measuredefinition) {
		this.measuredefinition = measuredefinition;
	}
	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	public static void addMeasureHistory(MeasureHistory mHistory)
	{
		EntityManager em=HealthInfoDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		em.persist(mHistory);
		tx.commit();
	}

}