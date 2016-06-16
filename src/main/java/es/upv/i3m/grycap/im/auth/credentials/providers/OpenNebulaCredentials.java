package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;

public class OpenNebulaCredentials
    extends GenericCredentials<OpenNebulaCredentials> {

  private OpenNebulaCredentials() {
    super(ServiceProvider.OPENNEBULA);
  }

  public static OpenNebulaCredentials buildCredentials() {
    return new OpenNebulaCredentials();
  }

}
