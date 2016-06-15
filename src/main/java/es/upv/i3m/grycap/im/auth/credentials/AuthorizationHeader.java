package es.upv.i3m.grycap.im.auth.credentials;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AuthorizationHeader {

  private static final String ERROR_MESSAGE = "Credentials must not be null";
  private List<Credentials> credentials = new ArrayList<>();

  public List<Credentials> getCredentials() {
    return credentials;
  }

  /**
   * Sets the credentials information.
   */
  public void setCredentialsAuthInfos(List<Credentials> credentials) {
    if (credentials == null) {
      throw new IllegalArgumentException(ERROR_MESSAGE);
    }
    this.credentials = credentials;
  }

  public void addCredential(Credentials credential) {
    credentials.add(credential);
  }

  /**
   * Returns a string with the credentials information.
   */
  public String serialize() {
    StringBuilder sb = new StringBuilder();
    Iterator<Credentials> it = credentials.iterator();
    while (it.hasNext()) {
      String serializedAuthInfo = it.next().serialize();
      sb.append(serializedAuthInfo);
      if (it.hasNext()) {
        sb.append("\\n");
      }
    }
    return sb.toString();
  }

}
