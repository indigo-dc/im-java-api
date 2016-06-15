package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class PrivateKeyProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "public_key";
  private static final String ERROR_MESSAGE = "Public key must not be blank";

  public PrivateKeyProperty(Credentials credential, String publicKey) {
    super(credential, PROPERTY_NAME, publicKey, ERROR_MESSAGE);
  }

}
