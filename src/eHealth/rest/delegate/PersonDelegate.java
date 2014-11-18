/**
 * @author Yishagerew L.
 * @DateModified Nov 12, 20142:04:43 AM
 */

package eHealth.rest.delegate;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

import eHealth.rest.model.Person;
import eHealth.rest.transferObjects.TPerson;

public class PersonDelegate {
	public List<TPerson> mapFromPerson(List<Person> sourceObject) {
		/***************************************************************
		 * myMappingFiles holds the mapping attributes as an arraylist *
		 * which is used for mapping                                   *
		 ***************************************************************/
		List<String> myMappingFiles = new ArrayList<String>();
		/** Mapping xml file containing source and transfer object mapping*/
		myMappingFiles.add("DozerMapping.xml"); 
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.setMappingFiles(myMappingFiles);
		List<TPerson> mappedPeoples = new ArrayList<TPerson>();
		/************************************************************
		 * Map over **each object** of person to its transfer object*
		 * On a row basis                                           *
		 ************************************************************/
		for (Person p : sourceObject) {
				mappedPeoples.add(mapper.map(p, TPerson.class));
				}
		return mappedPeoples;
	}
}
