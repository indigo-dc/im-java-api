package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class BaseUrlProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "base_url";
  private static final String ERROR_MESSAGE = "Base url must not be blank";

  public BaseUrlProperty(Credentials credential, String baseUrl) {
    super(credential, PROPERTY_NAME, baseUrl, ERROR_MESSAGE);
  }

}
