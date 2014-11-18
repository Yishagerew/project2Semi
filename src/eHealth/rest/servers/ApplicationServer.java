/**
 * @author Yishagerew L.
 * @DateModified Nov 13, 201410:59:51 PM
 */
package eHealth.rest.servers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationServer {  
	static  String urlParam;
	
	/**The following main method allows to take parameters from the command line
	 * 
	 * @param args
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
    public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException
    {
    	/**
    	 * If the user does not provide the URI ,it default to http://localhost:8089/
    	 */
    	if(args.length==0)
    	{
    		urlParam="http://localhost:8089/";
    	}
    	/**
    	 * The user enters a URI and taken as base URI
    	 */
    	else
    	{
    		urlParam=args[0];
    	}
    	final URI BASE_URI=URI.create(urlParam);
        System.out.println("Starting standalone HTTP server...");
        JdkHttpServerFactory.createHttpServer(BASE_URI, createApp());
        System.out.println("Server started on " + BASE_URI + "\n[kill the process to exit]");
    }
    public static ResourceConfig createApp() {
        System.out.println("Starting sdelab REST services...");
        return new App_Config();
    }
}
