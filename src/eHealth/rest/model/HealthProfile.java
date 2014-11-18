package eHealth.rest.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The persistent class for the HEALTHPROFILE database table.
 * 
 */
@Entity
@Table(name = "HealthProfile")
@NamedQueries ({@NamedQuery(name = "HealthProfile.findAll", query = "SELECT h FROM HealthProfile h"),
	            @NamedQuery(name= "HealthProfile.findByMesDef",query="SELECT h FROM HealthProfile h WHERE h.measuredefinition=:measureDefinition"),
	            @NamedQuery(name="HealthProfileByMinMaxValue",query="SELECT h FROM HealthProfile h WHERE h.measuredefinition=:measureDefinition and h.measuredValue BETWEEN :min and :max "),
	            @NamedQuery(name="HealthProfileByMinValue",query="SELECT h FROM HealthProfile h WHERE h.measuredefinition=:measureDefinition and h.measuredValue >= :min"),
                @NamedQuery(name="HealthProfileByMaxValue",query="SELECT h FROM HealthProfile h WHERE h.measuredefinition=:measureDefinition and h.measuredValue <= :max"),
                @NamedQuery(name="HealthProfileByMedDefPerson",query="SELECT h FROM HealthProfile h WHERE h.measuredefinition=:measureDefinition and h.person=:person")
                
})

@XmlRootElement
public class HealthProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableGenerator(name = "sqlite_healthProfile", table = "sqlite_sequence", pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "Measure_HealthProfile_Gen")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sqlite_healthProfile")
	@Column(name = "\"measureId\"")
	private int measureId;
	@Column(name = "\"dateCreated\"")
	private String dateCreated;
	@Column(name = "\"measuredValue\"")
	private double measuredValue;
	@Column(name = "\"updateDate\"")
	private String updateDate;

	/**
	 * Used to set values from POST methods for adding new person
	 * @param measuredValue
	 * @param measuredefinition
	 */
	public HealthProfile(double measuredValue,
			MeasureDefinition measuredefinition) {
		super();
		this.measuredValue = measuredValue;
		this.measuredefinition = measuredefinition;
	}

	// bi-directional one-to-one association to MeasureDefinition
	@OneToOne
	@JoinColumn(name = "measureDefId", referencedColumnName = "measureDefId", insertable = true, updatable = true)
	private MeasureDefinition measuredefinition;

	// bi-directional many-to-one association to Person
	/**
	 * @ManyToOne
	 * @JoinColumns({ })
	 */
	@ManyToOne
	@JoinColumn(name = "personId", referencedColumnName = "personId", insertable = true, updatable = true)
	private Person person;

	public HealthProfile() {
	}

	public String getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public double getMeasuredValue() {
		return this.measuredValue;
	}

	public void setMeasuredValue(double measuredValue) {
		this.measuredValue = measuredValue;
	}

	public int getMeasureId() {
		return this.measureId;
	}

	public void setMeasureId(int measureId) {
		this.measureId = measureId;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public MeasureDefinition getMeasuredefinition() {
		return this.measuredefinition;
	}

	public void setMeasuredefinition(MeasureDefinition measuredefinition) {
		this.measuredefinition = measuredefinition;
	}

	@XmlTransient
	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	public static void addHealthProfile()
	{
		
	}

}