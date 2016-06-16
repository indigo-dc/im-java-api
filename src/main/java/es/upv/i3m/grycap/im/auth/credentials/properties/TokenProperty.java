package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class TokenProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "token";
  private static final String ERROR_MESSAGE = "Token must not be blank";

  public TokenProperty(Credentials credential, String token) {
    super(credential, PROPERTY_NAME, token, ERROR_MESSAGE);
  }

}
