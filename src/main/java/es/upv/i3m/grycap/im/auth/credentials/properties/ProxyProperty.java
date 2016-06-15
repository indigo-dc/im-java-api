package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class ProxyProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "proxy";
  private static final String ERROR_MESSAGE = "Proxy must not be blank";

  public ProxyProperty(Credentials credential, String proxy) {
    super(credential, PROPERTY_NAME, proxy, ERROR_MESSAGE);
  }

}
