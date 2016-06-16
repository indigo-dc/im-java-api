package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class ServiceRegionProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "service_region";
  private static final String ERROR_MESSAGE =
      "Service region must not be blank";

  public ServiceRegionProperty(Credentials credential, String serviceRegion) {
    super(credential, PROPERTY_NAME, serviceRegion, ERROR_MESSAGE);
  }

}
