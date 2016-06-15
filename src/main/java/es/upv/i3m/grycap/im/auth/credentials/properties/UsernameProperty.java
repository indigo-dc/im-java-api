package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class UsernameProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "username";
  private static final String ERROR_MESSAGE = "Username must not be blank";

  public UsernameProperty(Credentials credential, String username) {
    super(credential, PROPERTY_NAME, username, ERROR_MESSAGE);
  }

}