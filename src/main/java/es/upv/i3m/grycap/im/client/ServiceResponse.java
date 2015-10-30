package es.upv.i3m.grycap.im.client;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

/**
 * Response returned by the REST service 
 */
public class ServiceResponse {

	private Response response;
	private String serviceResult;

	private Response getResponse() {
		return response;
	}

	public ServiceResponse(Response response) {
		this.response = response;
		this.serviceResult = getResponse().readEntity(String.class);
	}

	/**
	 * Reads the returned string by the server
	 */
	@Override
	public String toString() {
		return serviceResult;
	}

	/**
	 * Gets the returned string by the server
	 */
	public String getResult() {
		return serviceResult;
	}

	/**
	 * Status code of the service (e.g. 200, 404, ...)
	 */
	public int getServiceStatusCode() {
		return getResponse().getStatus();
	}

	public StatusType getServiceStatusInfo() {
		return getResponse().getStatusInfo();
	}
	
	/**
	 * Reason phrase of the service (e.g. 'Not Found', 'Bad request', ...)
	 */
	public String getReasonPhrase(){
		return getServiceStatusInfo().getReasonPhrase();
	}

	
	/**
	 * Return true if the message is successful, i.e. the message has one of the following status codes 2xx.
	 * Return false otherwise
	 */
	public boolean isReponseSuccessful() {
		switch (getServiceStatusInfo().getFamily()) {
		case SUCCESSFUL:
			return true;
		default:
			return false;
		}
	}

}
