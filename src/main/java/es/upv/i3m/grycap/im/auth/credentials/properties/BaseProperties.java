package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class BaseProperties implements Credentials {

  private final String id;
  private final String type;

  public BaseProperties(final String id, final String type) {
    this.id = id;
    this.type = type;
  }

  @Override
  public String serialize() {
    StringBuilder credentials = new StringBuilder();
    if (!isNullOrEmpty(id)) {
      credentials.append("id = ").append(id).append(" ; ");
    }
    if (!isNullOrEmpty(type)) {
      credentials.append("type = ").append(type);
    }
    return credentials.toString();
  }

}
