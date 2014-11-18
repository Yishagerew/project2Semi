package eHealth.rest.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the MEASUREDEFINITION database table.
 * 
 */
@Entity
@Table(name="MeasureDefinition")
@NamedQueries({
			@NamedQuery(name="MeasureDefinition.findAll", query="SELECT m FROM MeasureDefinition m"),
			@NamedQuery(name="MeasureDefinition.findByMeasureDefId",query="SELECT m FROM MeasureDefinition m WHERE m.measureDefId=:measureDefId"),
			@NamedQuery(name="MeasureDefinition.getMeasureId",query="SELECT m FROM MeasureDefinition m WHERE m.measureDefName=:measureDefName")
})
@NamedNativeQuery(name="MeasureDefinition.findAllMeasureNames",query="SELECT m.measureDefId,m.measureDefName FROM measureDefinition m",resultClass=MeasureDefinition.class)
@XmlRootElement
public class MeasureDefinition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="\"createdDate\"")
	@XmlTransient private String createdDate;
	
	@TableGenerator(name="sqlite_measureDef", table="sqlite_sequence",
	pkColumnName="name", valueColumnName="seq",
	pkColumnValue="Measure_Def_Gen")
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="sqlite_measureDef")
	@Column(name="\"measureDefId\"")
	@XmlTransient private int measureDefId;

	@Column(name="\"measureDefName\"")
	private String measureDefName;

	@Column(name="\"updateDate\"")
	@XmlTransient private String updateDate;
/**
	//bi-directional one-to-one association to HealthProfile
	@OneToOne(mappedBy="measuredefinition")
	private HealthProfile healthprofile;

	//bi-directional one-to-one association to MeasureHistory
	@OneToOne(mappedBy="measureDefId")
	private MeasureHistory measurehistory;
*/
	public MeasureDefinition() {
	}

	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public int getMeasureDefId() {
		return this.measureDefId;
	}

	public void setMeasureDefId(int measureDefId) {
		this.measureDefId = measureDefId;
	}

	public String getMeasureDefName() {
		return this.measureDefName;
	}

	public void setMeasureDefName(String measureDefName) {
		this.measureDefName = measureDefName;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
/**
	public HealthProfile getHealthprofile() {
		return this.healthprofile;
	}

	public void setHealthprofile(HealthProfile healthprofile) {
		this.healthprofile = healthprofile;
	}

	public MeasureHistory getMeasurehistory() {
		return this.measurehistory;
	}

	public void setMeasurehistory(MeasureHistory measurehistory) {
		this.measurehistory = measurehistory;
	}
	*/

}