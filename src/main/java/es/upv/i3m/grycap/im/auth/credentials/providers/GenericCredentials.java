/**
 * Copyright (C) GRyCAP - I3M - UPV 
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
