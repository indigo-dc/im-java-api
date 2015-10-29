package es.upv.i3m.grycap.im.client;

import java.util.Arrays;
import java.util.List;

/**
 * Stores the parameters passed to the REST calls in a (name, ...values) structure 
 */
public class RestCallParameter {

	String parameterName;
	List<Object> parameterValue;

	public RestCallParameter(String parameterName) {
		this.parameterName = parameterName;
	}

	public RestCallParameter(String parameterName, Object parameterValue) {
		this.parameterName = parameterName;
		this.parameterValue = Arrays.asList(parameterValue);
	}

	public String getParameterName() {
		return parameterName;
	}

	public Object[] getParameterValues() {
		return parameterValue.toArray();
	}

	public void addValue(Object value) {
		if (parameterValue == null) {
			parameterValue = Arrays.asList(value);
		} else {
			parameterValue.add(value);
		}
	}
}
