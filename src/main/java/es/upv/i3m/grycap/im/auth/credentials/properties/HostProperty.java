package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class HostProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "host";
  private static final String ERROR_MESSAGE = "Host must not be blank";

  public HostProperty(Credentials credential, String host) {
    super(credential, PROPERTY_NAME, host, ERROR_MESSAGE);
  }

}
