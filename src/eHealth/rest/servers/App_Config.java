package eHealth.rest.servers;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
@ApplicationPath("HealthBook")
public class App_Config extends ResourceConfig {
	public App_Config () {
		 /**
		  * The package containing all available resources
		  */
	        packages("eHealth"); 
	    }
}
