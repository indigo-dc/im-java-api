package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;

public class ImCredentials extends GenericCredentials<ImCredentials> {

  private ImCredentials() {
    super(ServiceProvider.INFRASTRUCTURE_MANAGER);
  }

  public static ImCredentials buildCredentials() {
    return new ImCredentials();
  }

}
