package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class TenantProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "tenant";
  private static final String ERROR_MESSAGE = "Tenant must not be blank";

  public TenantProperty(Credentials credential, String tenant) {
    super(credential, PROPERTY_NAME, tenant, ERROR_MESSAGE);
  }

}
