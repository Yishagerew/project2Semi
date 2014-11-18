

package eHealth.rest.business;

public class RequestNotificationUtil {
	/**
	 * 
	 * @param requestNumber
	 *            The number of request as shown in the assignment /IntroSDE lab
	 *            assignment 2/
	 * @param httpMethod
	 *            The type of http method called ex.GET
	 * @param relativeUrl
	 *            The relative url of the resource requested
	 * @param AcceptType
	 *            The type format our service could accept Ex:APPLICATION_XML
	 * @param ContentType
	 *            The content type the service could produce for response
	 */
	public static void showMessages(int requestNumber, String httpMethod,
			String relativeUrl, String acceptType, String contentType)
	{	
		System.out.println("Request # " + requestNumber + httpMethod + " "
				+ relativeUrl + " " + "Accept:" + acceptType + "Content-Type+"
				+ contentType);

	}
}
