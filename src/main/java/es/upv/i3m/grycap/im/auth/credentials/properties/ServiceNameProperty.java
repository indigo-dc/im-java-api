package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class ServiceNameProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "service_name";
  private static final String ERROR_MESSAGE = "Service name must not be blank";

  public ServiceNameProperty(Credentials credential, String serviceName) {
    super(credential, PROPERTY_NAME, serviceName, ERROR_MESSAGE);
  }

}
