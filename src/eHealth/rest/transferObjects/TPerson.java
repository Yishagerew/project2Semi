package eHealth.rest.transferObjects;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="Person")
@XmlAccessorType(XmlAccessType.FIELD)
public class TPerson {
private String FirstName;
private String MiddleName;
private String LastName;
private int PersonId;
public String getFirstName() {
	return FirstName;
}
public void setFirstName(String firstName) {
	FirstName = firstName;
}
public String getMiddleName() {
	return MiddleName;
}
public void setMiddleName(String middleName) {
	MiddleName = middleName;
}
public String getLastName() {
	return LastName;
}
public void setLastName(String lastName) {
	LastName = lastName;
}
public int getPersonId() {
	return PersonId;
}
public void setPersonId(int personId) {
	PersonId = personId;
}


}
