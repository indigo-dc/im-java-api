package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;
import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;
import es.upv.i3m.grycap.im.auth.credentials.properties.BaseProperties;
import es.upv.i3m.grycap.im.auth.credentials.properties.HostProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.PasswordProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.ProxyProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.TokenProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.UsernameProperty;

public abstract class GenericCredentials<T> implements Credentials {

  protected Credentials credentials;

  protected GenericCredentials(ServiceProvider provider) {
    credentials = new BaseProperties(provider.getId(), provider.getType());
  }

  @Override
  public String serialize() {
    return credentials.serialize();
  }

  public Credentials getCredentials() {
    return credentials;
  }

  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }

  // Below the most common methods used in all the credentials

  @SuppressWarnings("unchecked")
  public T withUsername(String username) {
    setCredentials(new UsernameProperty(getCredentials(), username));
    return (T) this;
  }

  @SuppressWarnings("unchecked")
  public T withPassword(String password) {
    setCredentials(new PasswordProperty(getCredentials(), password));
    return (T) this;
  }

  @SuppressWarnings("unchecked")
  public T withHost(String host) {
    setCredentials(new HostProperty(getCredentials(), host));
    return (T) this;
  }

  @SuppressWarnings("unchecked")
  public T withToken(String token) {
    setCredentials(new TokenProperty(getCredentials(), token));
    return (T) this;
  }

  @SuppressWarnings("unchecked")
  public T withProxy(String proxy) {
    setCredentials(new ProxyProperty(getCredentials(), proxy));
    return (T) this;
  }

}
