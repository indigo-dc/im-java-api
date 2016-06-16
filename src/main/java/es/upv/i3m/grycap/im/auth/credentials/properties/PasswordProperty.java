package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class PasswordProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "password";
  private static final String ERROR_MESSAGE = "Password must not be blank";

  public PasswordProperty(Credentials credential, String password) {
    super(credential, PROPERTY_NAME, password, ERROR_MESSAGE);
  }

}
