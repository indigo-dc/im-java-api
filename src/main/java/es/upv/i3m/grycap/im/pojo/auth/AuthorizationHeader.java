package es.upv.i3m.grycap.im.pojo.auth;

import com.google.common.collect.Lists;

import es.upv.i3m.grycap.im.pojo.auth.credential.Credential;

import java.util.Iterator;
import java.util.List;

public class AuthorizationHeader {

  private List<Credential<?>> credentials = Lists.newArrayList();

  public List<Credential<?>> getCredentials() {
    return credentials;
  }

  public void setCredentialsAuthInfos(List<Credential<?>> credentials) {
    if (credentials == null) {
      throw new IllegalArgumentException("credentials must not be null");
    }
    this.credentials = credentials;
  }

  public void addCredential(Credential<?> credential) {
    credentials.add(credential);
  }

  public String serialize() {
    StringBuilder sb = new StringBuilder();
    Iterator<Credential<?>> it = credentials.iterator();
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
