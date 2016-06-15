package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class GenericProperty implements Credentials {

  private final Credentials credential;
  private String propertyName;
  private String propertyValue;

  /**
   * Generic constructor for all the properties that the authorization header
   * can have.
   */
  public GenericProperty(Credentials credential, String propertyName,
      String propertyValue, String errorMessage) {
    this.credential = credential;
    this.propertyName = propertyName;
    if (isNullOrEmpty(propertyValue)) {
      throw new IllegalArgumentException(errorMessage);
    }
    this.propertyValue = propertyValue;
  }

  protected String getPropertyValue() {
    return propertyValue;
  }

  protected Credentials getCredentials() {
    return credential;
  }

  @Override
  public String serialize() {
    StringBuilder credentials = new StringBuilder(credential.serialize());
    if (!isNullOrEmpty(propertyValue)) {
      credentials.append(" ; " + propertyName + " = ").append(propertyValue);
    }
    return credentials.toString();
  }

}
