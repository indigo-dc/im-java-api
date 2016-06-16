package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;

public class OcciCredentials extends GenericCredentials<OcciCredentials> {

  private OcciCredentials() {
    super(ServiceProvider.OCCI);
  }

  public static OcciCredentials buildCredentials() {
    return new OcciCredentials();
  }

}
