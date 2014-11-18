/**
 * @author Yishagerew L.
 * @DateModified Nov 14, 20146:17:19 PM
 */

package eHealth.rest.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONFormatter {
	
	/**
	 * 
	 * @param source
	 *        The source string,a string of json file which is not formatted
	 * @return
	 *       Returns formatted json
	 */
	public static String toJsonFormat(String source) {
		ObjectMapper mapper = new ObjectMapper();
		Object jsonObject=null;
		String outputText="";
		try {
			jsonObject = mapper.readValue(source, Object.class);
		} catch (JsonParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		try {
			outputText=mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(jsonObject);
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
 			e.printStackTrace();
		}
		return outputText;
	}
}
