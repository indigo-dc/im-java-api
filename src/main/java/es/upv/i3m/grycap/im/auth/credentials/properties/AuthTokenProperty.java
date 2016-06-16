package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class AuthTokenProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "auth_token";
  private static final String ERROR_MESSAGE =
      "Authorization token must not be blank";

  public AuthTokenProperty(Credentials credential, String authToken) {
    super(credential, PROPERTY_NAME, authToken, ERROR_MESSAGE);
  }

}
