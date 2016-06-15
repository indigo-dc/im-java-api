package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class PublicKeyProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "private_key";
  private static final String ERROR_MESSAGE = "Private key must not be blank";

  public PublicKeyProperty(Credentials credential, String privateKey) {
    super(credential, PROPERTY_NAME, privateKey, ERROR_MESSAGE);
  }

}
